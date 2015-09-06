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
	//private String action;
	//private Scanner in;
	//private boolean isCorrect;
	//private int betsize;
	
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
		
		startPreFlop(game);
		
	}
	
	private void printBoard(int street) {
		
		switch(street) {
		case PRE_FLOP: 
			System.out.println("\n\t\t\tPreflop");
			break;
		case FLOP:
			System.out.println("\n\t\t\t" + Arrays.toString(Arrays.copyOfRange(board,0,3)));
			break;
		case TURN:
			System.out.println("\n\t\t\t" + Arrays.toString(Arrays.copyOfRange(board,0,4)));
			break;
		case RIVER:
			System.out.println("\n\t\t\t" + Arrays.toString(board));
			break;
		}
		System.out.println("\t\t\tActive Players: " + activePlayers);
		System.out.println("\t\t\tPot: $" + pot + "\n");
		
		
	}
	
	private void addToPot(int amount) {
		
		pot += amount;
		
	}
	
	private void startStreet(PokerGame game, int streetIn) {
		//Output board
		printBoard(streetIn);
		//initialize currentBet according to street. 
		int currentBet = (streetIn == PRE_FLOP) ? game.BIG_BLIND : 0;
		int tempBet;
		int tempActionCounter = game.actionIndex;

		//Reset how much each player has bet on a particular street, and set endAction to last player (skip if preflop)
		if(streetIn != 9){
			for (int j = 0; j < activePlayers.size(); j++) {
				//Reset money
				activePlayers.get(j).resetStreetMoney();
				//If sbIndex is 0, set endAction as last player in hand
				if(j == game.sbIndex){
					//For headsup, sb is the dealer
					if(game.totalPlayers == 2){
						game.dealerIndex = game.sbIndex;
						activePlayers.get(game.dealerIndex).setEndAction(true);
						activePlayers.get(game.dealerIndex).hasDealerActed(false);
					}
					//For all other cases, if sb is in the 0 spot, set dealer/endAction to last position
					else if(game.sbIndex == 0){
						game.dealerIndex = activePlayers.size()-1;
						activePlayers.get(activePlayers.size()-1).setEndAction(true);
						activePlayers.get(activePlayers.size()-1).hasDealerActed(false);
					}
					//Otherwise, set dealer to one behind the small blind
					else{
						game.dealerIndex = game.sbIndex-1;
						activePlayers.get(game.dealerIndex-1).setEndAction(true);
						activePlayers.get(game.dealerIndex-1).hasDealerActed(false);
					}
				}
				//Reset all other endAction to false
				else{
					activePlayers.get(j).setEndAction(false);
				}
			}
		}

		while(true) {
			for (int i = 0; i < activePlayers.size(); i++) {
				//If it's not preflop and action ends on player break out of loop
				if (activePlayers.get(tempActionCounter).endAction && streetIn != PRE_FLOP) {
					//Once last to act has acted preflop actions ends (unless pot is raised)
					if(activePlayers.get(game.dealerIndex).dealerActed){
						return;
					}
				}
				//If it's preflop and action ends on player check if he is BB (allows BB to still act)
				else if (activePlayers.get(tempActionCounter).endAction && streetIn == PRE_FLOP) {
					//Once BB has acted preflop actions ends (unless pot is raised)
					if(activePlayers.get(game.bbIndex).BBActed){
						return;
					}
				}

				//Print out pot and allow player to act
				System.out.println("pot: " + pot);
				int playerBet = activePlayers.get(tempActionCounter).act(currentBet);
				//TempBet is used to gauge whether player action was a bet, call, or check/fold (see end of method)
				tempBet = currentBet;

				//!!! Consolidate BBActed and dealerActed into just dealerActed
				//Set hasBBActed to true the first time BB has acted (only during preflop)
				if(tempActionCounter == game.bbIndex && streetIn == PRE_FLOP){
					activePlayers.get(game.bbIndex).hasBBActed(true);
					//Arbitrarily set next player to act as endAction (allows BB to act)
					if(tempActionCounter == activePlayers.size()-1){
						activePlayers.get(0).setEndAction(true);
					}
					else{
						activePlayers.get(tempActionCounter+1).setEndAction(true);
					}
					//If BB bets then action is reset (occurs in if statement below)
				}
				//Set hasDealerActed to true the first time dealer has acted
				else if(tempActionCounter == game.dealerIndex && streetIn != PRE_FLOP){
					activePlayers.get(game.dealerIndex).hasDealerActed(true);
					//Arbitrarily set next player to act as endAction (allows dealer to act)
					if(tempActionCounter == activePlayers.size()-1){
						activePlayers.get(0).setEndAction(true);
					}
					else{
						activePlayers.get(tempActionCounter+1).setEndAction(true);
					}
					//If dealer bets then action is reset (occurs in if statement below)
				}

				//Bet - sets where action ends
				if (playerBet > currentBet) {
					currentBet = playerBet;
					this.addToPot(currentBet);
					//Set whoever bets as end of action (sets everyone else to false)
					for (int k = 0; k < activePlayers.size(); k++) {
						if(k == tempActionCounter){
							activePlayers.get(k).setEndAction(true);
						}
						else{
							activePlayers.get(k).setEndAction(false);
						}
					}
				}
				//Call - doesn't affect where action ends
				else if (playerBet <= currentBet && playerBet != 0){
					currentBet = playerBet;
					this.addToPot(currentBet);
				}

				//Remove players from arraylist as they fold
				if (activePlayers.get(tempActionCounter).playerFolded()) {
					//Change action index if it's starts on a deleted player
					if(tempActionCounter == game.actionIndex) {
						//If action index is on player 0 then the last player gets the action index
						if(game.actionIndex == 0) {
							game.actionIndex = activePlayers.size();
						}
						//Otherwise shift action index back by 1
						else{
							game.actionIndex -= 1;
						}
					}
					activePlayers.remove(activePlayers.get(tempActionCounter));
				}

				//End while loop when only one player remains
				if (activePlayers.size() == 1) {
					activePlayers.get(0).winPot(pot);
					return;
				}

				//Cycle through whose turn it is (different from how many players)
				if (tempActionCounter == activePlayers.size()-1) {
					tempActionCounter = 0;
				} else {
					tempActionCounter++;
				}

				//If currentBet > tempBet then a bet has been made (as opposed to a check/call), and the for loop is broken
				if(currentBet > tempBet){
					break;
				}
			}
		}
			
	}
	
	
	private void startPreFlop(PokerGame game) {
		//Post sb and set how much sb
		activePlayers.get(game.sbIndex).postSB();
		activePlayers.get(game.sbIndex).setStreetMoney(PokerGame.SMALL_BLIND);

		activePlayers.get(game.bbIndex).postBB();
		activePlayers.get(game.bbIndex).setStreetMoney(PokerGame.BIG_BLIND);
		activePlayers.get(game.bbIndex).setEndAction(true);
		activePlayers.get(game.bbIndex).hasBBActed(false);

		this.addToPot(PokerGame.SMALL_BLIND + PokerGame.BIG_BLIND);

		startStreet(game, PRE_FLOP);
		if(activePlayers.size()!=1){
			startFlop(game);
		}

	}

	private void startFlop(PokerGame game){
		//Flip order of who acts postflop (headsup)
		if(game.totalPlayers==2){
			if(game.actionIndex == 0){
				game.actionIndex = 1;
			}
			else{
				game.actionIndex = 0;
			}
		}

		startStreet(game, FLOP);

		if(activePlayers.size()!=1){
			startTurn(game);
		}

	}

	private void startTurn(PokerGame game){

		startStreet(game, TURN);

		if(activePlayers.size()!=1){
			startRiver(game);
		}

	}

	private void startRiver(PokerGame game){

		startStreet(game, RIVER);

		//Change action index for next hand (doesn't change for headsup since we already flipped on the flop)
		if(game.totalPlayers != 2){
			if(game.actionIndex == game.totalPlayers-1){
				game.actionIndex = 0;
			}
			else{
				game.actionIndex += 1;
			}
		}

		//Change sb index for next hand
		if(game.sbIndex == game.totalPlayers-1){
			game.sbIndex = 0;
		}
		else{
			game.sbIndex += 1;
		}

		//Change bb index for next hand
		if(game.bbIndex == game.totalPlayers-1){
			game.bbIndex = 0;
		}
		else{
			game.bbIndex += 1;
		}

	}

}
