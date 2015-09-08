package poker;

import javax.swing.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Hand implements Serializable {
	
	private static final int NUMBER_OF_SHUFFLES = 3;
	private static final int PRE_FLOP = 9;
	private static final int FLOP = 10;
	private static final int TURN = 11;
	private static final int RIVER = 12;
	
	public int pot;
	private ArrayList<Player> activePlayers;
	public Card [] board;
	public int startingIndex;
	
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
	
	private void printBoard(int street, int handNum) {
		
		System.out.print("\n\t\t\tHand Number: " + handNum);
		switch(street) {
		case PRE_FLOP: 
			System.out.println("\n\t\t\tPREFLOP");
			break;
		case FLOP:
			System.out.println("\n\t\t\tFLOP" + Arrays.toString(Arrays.copyOfRange(board,0,3)));
			break;
		case TURN:
			System.out.println("\n\t\t\tTURN" + Arrays.toString(Arrays.copyOfRange(board,0,4)));
			break;
		case RIVER:
			System.out.println("\n\t\t\tRIVER" + Arrays.toString(board));
			break;
		}
		System.out.println("\t\t\tActive Players: " + activePlayers);
		System.out.println("\t\t\tPot: $" + pot + "\n");
		
		
	}
	
	private void addToPot(int amount) {
		
		pot += amount;
		
	}
	
	private void startStreet(PokerGame game, int streetIn, int startingIndex) {
		
		//Output board
		printBoard(streetIn, game.handNumber);
		//initialize currentBet according to street. 
		int currentBet = (streetIn == PRE_FLOP) ? game.BIG_BLIND : 0;
		int tempBet;
		int tempActionCounter = startingIndex;

		//Reset how much each player has bet on a particular street, and 
		//set endAction to last player (skip if preflop)
		if(streetIn != PRE_FLOP){
			for (int j = 0; j < game.players.length; j++) {
				//Reset all other endAction to false
				game.players[j].resetStreetMoney();
				game.players[j].setEndAction(false);
			}
		}
		game.players[startingIndex].setEndAction(true);

		while(true) {
			for (int i = 0; i < game.players.length; i++) {
				//Print out pot and allow player to act
				System.out.println("pot: " + pot);
				int playerBet = game.players[tempActionCounter].act(currentBet);
				//TempBet is used to gauge whether player action was a 
				//bet, call, or check/fold (see end of method)
				tempBet = currentBet;

				//Constantly update who's folded
				removePlayers(game);
				//End while loop when only one player remains
				if (activePlayers.size() == 1) {
					game.players[activePlayers.get(0).id].winPot(pot);
					return;
				}

				//Bet - sets where action ends
				if (playerBet > currentBet) {
					//update the current bet if someone bet larger
					currentBet = playerBet; 
					this.addToPot(currentBet);
					//Set whoever bets as end of action (sets everyone else to false)
					for (int k = 0; k < game.players.length; k++) {
						if(k == tempActionCounter){
							game.players[k].setEndAction(true);
						}
						else{
							game.players[k].setEndAction(false);
						}
					}
				}
				//Call - doesn't affect where action ends
				else if (playerBet != 0){
					this.addToPot(playerBet);
				}

				//Cycle through whose turn it is (different from how many players)
				if (tempActionCounter == game.players.length - 1) {
					//If action is on the last player, check if next player (0) is last to act
					if (game.players[0].endAction) {
						//Break out of while loop
						return;
					}
					//Otherwise move to next player (0)
					tempActionCounter = 0;
				} else {
					//If action is on the last player, check if next player is last to act
					if (game.players[tempActionCounter + 1].endAction) {
						//Break out of while loop
						return;
					}
					//Otherwise move to next player
					tempActionCounter++;
				}

				//If currentBet > tempBet then a bet has been made 
				//(as opposed to a check/call), and the for loop is broken
				if(currentBet > tempBet){
					break;
				}
			}
		}
			
	}
	
	
	private void startPreFlop(PokerGame game) {
		
		//Post sb and set how much sb has bet
		game.players[game.sbIndex].postSB();
		game.players[game.sbIndex].setStreetMoney(PokerGame.SMALL_BLIND);
		
		//Post bb and set how much bb has bet
		game.players[game.bbIndex].postBB();
		game.players[game.bbIndex].setStreetMoney(PokerGame.BIG_BLIND);

		this.addToPot(PokerGame.SMALL_BLIND + PokerGame.BIG_BLIND);

		startStreet(game, PRE_FLOP, game.actionIndex);
		//Only moves to flop if there are still people in pot
		if(activePlayers.size()!=1){
			startFlop(game);
		}

	}

	private void startFlop(PokerGame game){
		//Flip order of who acts postflop (headsup)
		if(game.totalPlayers==2){
			if(game.actionIndex == 0){
				startingIndex = 1;
			}
			else{
				startingIndex = 0;
			}
		}
		else{
			if(game.dealerIndex == game.players.length - 1){
				startingIndex = 0;
			}
			else{
				startingIndex = game.dealerIndex + 1;
			}
		}

		startStreet(game, FLOP, startingIndex);
		//Only moves to turn if there are still people in pot
		if(activePlayers.size()!=1){
			startTurn(game);
		}

	}

	private void startTurn(PokerGame game){

		startStreet(game, TURN, startingIndex);
		//Only moves to river if there are still people in pot
		if(activePlayers.size()!=1){
			startRiver(game);
		}

	}

	private void startRiver(PokerGame game){

		startStreet(game, RIVER, startingIndex);
	
		//Only need to evaluate final hand strengths if by the time action
		//is over during the river, there is more than 1 player remaining
		if (activePlayers.size() > 1) {
		Player winner;
		winner = HandEvaluator.evaluateHands((Player[]) activePlayers.toArray(), board);
		//Players will need to be updated in game class
		winner.winPot(pot);
		}

	}

	private void removePlayers(PokerGame game){
		//Cycle through all activePlayers and remove them as they fold
		for(int k = 0; k < activePlayers.size(); k++){
			if (activePlayers.get(k).playerFolded()) {
				activePlayers.remove(activePlayers.get(k));
			}
		}

	}

}
