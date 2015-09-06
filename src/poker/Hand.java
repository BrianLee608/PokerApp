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
	
	private void startStreet(PokerGame game, int streetIn, int startingIndex) {
		//Output board
		printBoard(streetIn);
		//initialize currentBet according to street. 
		int currentBet = (streetIn == PRE_FLOP) ? game.BIG_BLIND : 0;
		int tempBet;
		int tempActionCounter = startingIndex;

		//Reset how much each player has bet on a particular street, and set endAction to last player (skip if preflop)
		if(streetIn != PRE_FLOP){
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
						activePlayers.get(game.dealerIndex).setEndAction(true);
						activePlayers.get(game.dealerIndex).hasDealerActed(false);
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
				//If action ends on player break out of loop
				if (activePlayers.get(tempActionCounter).endAction) {
					//Once last to act has acted preflop actions ends (unless pot is raised)
					if(activePlayers.get(game.dealerIndex).dealerActed){
						return;
					}
				}

				//Print out pot and allow player to act
				System.out.println("pot: " + pot);
				int playerBet = activePlayers.get(tempActionCounter).act(currentBet);
				//TempBet is used to gauge whether player action was a bet, call, or check/fold (see end of method)
				tempBet = currentBet;

				//Set hasDealerActed to true the first time dealer has acted
				if(tempActionCounter == game.dealerIndex){
					activePlayers.get(game.dealerIndex).hasDealerActed(true);
					//Arbitrarily set next player to act as endAction (allows dealer to act)
					if(tempActionCounter == activePlayers.size()-1){
						activePlayers.get(0).setEndAction(true);
					}
					else{
						activePlayers.get(tempActionCounter+1).setEndAction(true);
					}
					//If dealer bets then action is reset (occurs in bet if statement below)
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
				else if (playerBet != 0){
					this.addToPot(playerBet);
				}

				if (activePlayers.get(tempActionCounter).playerFolded()) {
					//Remove players from arraylist as they fold
					activePlayers.remove(activePlayers.get(tempActionCounter));
					//Reset dealer index (one less player) if player removed is before dealer index
					if(tempActionCounter < game.dealerIndex) {
						game.dealerIndex -= 1;
					}
					//Check if dealer is removed
					else if(tempActionCounter == game.dealerIndex) {
						if(game.dealerIndex == activePlayers.size()){
							game.dealerIndex = 0;
							activePlayers.get(0).hasDealerActed(true);
						}
						else{activePlayers.get(game.dealerIndex).hasDealerActed(true);}
					}
					//If tempActionCounter is the last player then reset to 0
					if(tempActionCounter == activePlayers.size()) {
						tempActionCounter = 0;
					}
				}
				//Cycle through whose turn it is (different from how many players)
				else if (tempActionCounter == activePlayers.size()-1) {
					tempActionCounter = 0;
				} else {
					tempActionCounter++;
				}

				//End while loop when only one player remains
				if (activePlayers.size() == 1) {
					activePlayers.get(0).winPot(pot);
					return;
				}

				//If currentBet > tempBet then a bet has been made (as opposed to a check/call), and the for loop is broken
				if(currentBet > tempBet){
					break;
				}
			}
		}
			
	}
	
	
	private void startPreFlop(PokerGame game) {
		//Post sb and set how much sb has bet
		activePlayers.get(game.sbIndex).postSB();
		activePlayers.get(game.sbIndex).setStreetMoney(PokerGame.SMALL_BLIND);
		
		//Post bb and set how much sb has bet
		activePlayers.get(game.bbIndex).postBB();
		activePlayers.get(game.bbIndex).setStreetMoney(PokerGame.BIG_BLIND);
		
		//Set BB as last to act preflop and reset BBAction
		activePlayers.get(game.bbIndex).setEndAction(true);
		//Set dealer as BB (only for preflop)
		//??? 
		game.dealerIndex = game.bbIndex;
		//Note for preflop that last to act is the BB (not the dealer)
		activePlayers.get(game.bbIndex).hasDealerActed(false);

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
			//If not headsup first player in array
			startingIndex = 0;
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

	}

}
