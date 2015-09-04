package poker;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Hand {
	
	
	private static final int NUMBER_OF_SHUFFLES = 3;
	private static final int PRE_FLOP = 9;
	private static final int FLOP = 10;
	private static final int TURN = 11;
	private static final int RIVER = 12;
	
	Deck deck;
	public int pot;
	public int street;
	private ArrayList<Player> activePlayers;
	public Card [] board;
	private String action;
	private Scanner in;
	private boolean isCorrect;
	private int betsize;
	
	//This hand lives inside an array which a PokerGame object has access to.
	//This constructor will create a temporary array of players which will be
	//updated as players fold.
	//This Hand object will have its own deck and the board/burn cards
	//will be pre-loaded in this constructor. They will become visible accordingly
	//as action proceeds.
	public Hand(PokerGame game) {
		
		//copy over players from the game class as a shallow copy
		//so we can remove players that folded
		activePlayers = new ArrayList<Player>(Arrays.asList(game.players));
		
		Deck deck = new Deck();
		for (int shuffleNum = 0; shuffleNum < NUMBER_OF_SHUFFLES; shuffleNum++){
			deck.shuffle();
		}
		
		//deal 2 cards to each player
		for (int i = 0; i < activePlayers.size(); i++) {
			game.players[i].receiveHand(deck.deal(2));
		}
		
		//pre-load the board
		board = new Card[5];
		for (int i = 0; i < board.length; i++) {
			board[i] = deck.deal(1)[0];
		}
		
		//pre-burn 3 cards
		deck.deal(3);
		
		
		printBoard(PRE_FLOP);
		
		startPreFlop(game);
		
	}
	
	private void printBoard(int street) {
		
		switch(street) {
		case PRE_FLOP: 
			System.out.println("Preflop: [ ]");
			break;
		case FLOP:
			System.out.println(Arrays.toString(Arrays.copyOfRange(board,0,4)));
			break;
		case TURN:
			System.out.println(Arrays.toString(Arrays.copyOfRange(board,0,5)));
			break;
		case RIVER:
			System.out.println(Arrays.toString(board));
			break;
		}
		
	}
	
	private void addToPot(int amount) {
		
		pot += amount;
		
	}
	
	private void startStreet(PokerGame game, int street) {
		
		//is this right?
		int currentBet = (street == PRE_FLOP) ? game.BIG_BLIND : 0;
		int tempBet;
		int tempActionCounter = game.actionIndex;
			
		while(true) {
		
			for (int i = 0; i < activePlayers.size(); i++) {
				tempBet = currentBet;
				
				int currBet = activePlayers.get(tempActionCounter).act(currentBet);
				if (currBet > 0) {
					currentBet = currBet;
					this.addToPot(currentBet);
				}

				if (activePlayers.get(tempActionCounter).playerFolded()) {
					activePlayers.remove(activePlayers.get(tempActionCounter));
				}
				//If there is only 1 player remaining, he wins the pot and we
				//must return from this method so the loop does not continue
				if (activePlayers.size() == 1) {
					activePlayers.get(0).winPot();
					return;
				}

				if (tempActionCounter == activePlayers.size()-1) {
					tempActionCounter = 0;
				} else {
					tempActionCounter++;
				}
				if(currentBet > tempBet){
					break;
				}
		
			}
		}
			
	}
	
	
	private void startPreFlop(PokerGame game) {
		
		activePlayers.get(game.sbIndex).postSB();
		activePlayers.get(game.sbIndex).postSB();
		this.addToPot(PokerGame.SMALL_BLIND + PokerGame.BIG_BLIND);

		System.out.println("PREFLOP");
		startStreet(game,FLOP);
		
	}

	private void dealFlop(PokerGame game) {
		
	}

	private void dealTurn(PokerGame game) {

	}

	private void dealRiver(PokerGame game) {

	}

	
	

}
