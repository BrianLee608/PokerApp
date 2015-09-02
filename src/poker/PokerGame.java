package poker;
import java.util.ArrayList;

public class PokerGame {

	//Constants
	public static final int STARTING_CASH = 200;
	public static final int BIG_BLIND = 2;
	public static final int SMALL_BLIND = 1;
	private static final int NUMBER_OF_SHUFFLES = 3;
	
	//variables
	public static int handNumber; 
	public static int actionIndex;
	public static int pot;  //does it matter static or instance?
	public static int totalPlayers;
	public static int street;
	
	public Player [] players;
	public Card [] board;
	public Deck myDeck;
	
	//Instantiate this when a fresh new game starts
	public PokerGame(int numOfPlayers) {

		totalPlayers = numOfPlayers;
		pot = 0;
		handNumber = 1;
		//Action starts on UTG
		actionIndex = 3;
		//Pre = 0, flop = 1, turn = 2, river = 3
		street = 0;

		//Create a new deck and shuffle it NUMBER_OF_SHUFFLES times
		Deck deck = new Deck();
		myDeck = deck;

		for (int shuffleNum = 0; shuffleNum < NUMBER_OF_SHUFFLES; shuffleNum++){
			myDeck.shuffle();
		}
		
		//the arraylist posAssign will contain numOfPlayer integers
		//which are shuffled and distributed to each player to give
		//each player a unique position 
		//position 0 = dealer
		//position 1 = small blind
		//position 2 = big blind
		//position 3,4,5,6,7,8 ....
		ArrayList <Integer> posAssign = new ArrayList <Integer> (numOfPlayers);
		for (int i = 0; i < numOfPlayers; i++) {
			posAssign.add(i);
		}

		java.util.Collections.shuffle(posAssign);

		//Initialize the players
		players = new Player[numOfPlayers];
		for (int i = 0; i < numOfPlayers; i++) {
			//assign random position to each player
			int pos = posAssign.get(i); 	
			players[i] = new Player("Player " + i, STARTING_CASH, pos);
			//deal 2 cards to each player
			players[i].receiveHand(myDeck.deal(2));
		}

		//initialize where current action is
		if (numOfPlayers == 2) {
			//Small blind is first to act headsup
			actionIndex = 0;
		} // need more code to deal with 3,4,5,6,7,8,9 players

		//initialize a blank deck (all null)
		board = new Card[5]; 
		
	}

	public void nextTurn(){
		// Reset actionIndex
		if (actionIndex == totalPlayers-1){
			actionIndex = 0;
		}
		else{
			actionIndex = actionIndex + 1;
		}
	}

	public void fillBoard(){
		switch(street){
			//flop
			case 1: board[0] = myDeck.deal(1)[0];
					board[1] = myDeck.deal(1)[0];
					board[2] = myDeck.deal(1)[0];
				break;
			//turn
			case 2: board[3] = myDeck.deal(1)[0];
				break;
			//river
			case 3: board[4] = myDeck.deal(1)[0];
				break;
		}
	}

	public void changeStreet(){
		if(street==3){
			street = 0;
		}
		else{
			street = street + 1;
		}
	}

}
