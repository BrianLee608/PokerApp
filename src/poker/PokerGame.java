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
	public static int dealerIndex;
	public static int actionIndex;
	public static int pot;  //does it matter static or instance?
	
	public Player [] players;
	public Card [] board;
	
	//Instantiate this when a fresh new game starts
	public PokerGame(int numOfPlayers) {
		
		pot = 0;
		handNumber = 1;
		
		//Create a new deck and shuffle it NUMBER_OF_SHUFFLES times
		Deck deck = new Deck();
		for (int shuffleNum = 0; shuffleNum < NUMBER_OF_SHUFFLES; shuffleNum++){
			deck.shuffle();
		}
		
		//the arraylist posAssign will contain numOfPlayer integers
		//which are shuffled and distributed to each player to give
		//each player a unique position 
		//position 0 = dealer
		//position 1 = small blind
		//position 2 = big blind
		//position 3,4,5,6,7,8 ....
		ArrayList <Integer> posAssign = new ArrayList<Integer>(numOfPlayers); 
		for (int i = 0; i < numOfPlayers; i++) {
			posAssign.add(i);
		}
		java.util.Collections.shuffle(posAssign);

		//Initialize the players
		players = new Player[numOfPlayers];
		for (int i = 0; i < numOfPlayers; i++) {
			//assign random position to each player
			int pos = posAssign.get(i); 	
			players[i] = new Player("Player" + i,STARTING_CASH, pos);
			//deal 2 cards to each player
			players[i].receiveHand(deck.deal(2));  
			//if a player is assigned a 0 index, he is the dealer
			if (pos == 0) {
				dealerIndex = i;
			}

		}

		//initialize where current action is
		if (numOfPlayers == 2) {
			actionIndex=(dealerIndex == numOfPlayers-1) ? dealerIndex:0;
		} // need more code to deal with 3,4,5,6,7,8,9 players

		//initialize a blank deck (all null)
		board = new Card[5]; 
		
	}
	
	

	
	
}
