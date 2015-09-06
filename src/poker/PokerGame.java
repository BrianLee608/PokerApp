//The PokerGame class represents a session of poker


package poker;
import java.util.ArrayList;

public class PokerGame {

	//Constants
	public static final int STARTING_CASH = 200;
	public static final int BIG_BLIND = 2;
	public static final int SMALL_BLIND = 1;
	private static final int NUMBER_OF_SHUFFLES = 3;
	
	//variables
	public boolean gameIsLive;
	public int handNumber; 
	public int totalPlayers;
	public int sbIndex;
	public int bbIndex;
	public int actionIndex;
	public Player [] players;
	public Card [] board;
	public ArrayList<Hand> hand;
	public int dealerIndex;
	
	//Instantiate this when a fresh new game starts
	public PokerGame(int numOfPlayers) {

		//Initialize a blank array of hands
		hand = new ArrayList<Hand>();
		
		totalPlayers = numOfPlayers;

		handNumber = 0; 
		sbIndex = 0;
		bbIndex = 1;
		//Action starts on UTG
		actionIndex = 2;

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
			//Assigning random positions makes it difficult to access the right correct player based on position using an arraylist
			players[i] = new Player("Player " + i, STARTING_CASH, i);
		}

		//initialize where dealer and action is (preflop)
		if (numOfPlayers == 2) {
			//Small blind is first to act headsup
			actionIndex = 0;
			dealerIndex = 1;
		}
		else{
			//Action index is after BB
			actionIndex = 2;
			dealerIndex = players.length - 1;
		}

		//Initially, game will always be live.... 
		//until only 1 player is remaining
		gameIsLive = true;
		
		startNewHand();
		
	}
	
	public void startNewHand() {
		
		while (gameIsLive) {
			hand.add(new Hand(this));
			handNumber++;
			//After every hand change indexes
			this.changeIndex();
		}

		
	}

	private void changeIndex(){
		//Change action index for next hand
		if(actionIndex == totalPlayers-1){ //if action was at end of array?
			actionIndex = 0;
		}
		else {
			actionIndex += 1;
		}

		//Change sb index for next hand
		if(sbIndex == totalPlayers-1){
			sbIndex = 0;
		}
		else{
			sbIndex += 1;
		}

		//Change bb index for next hand
		if(bbIndex == totalPlayers-1){
			bbIndex = 0;
		}
		else{
			bbIndex += 1;
		}

		//Change dealer index for next hand
		if(dealerIndex == totalPlayers-1){
			dealerIndex = 0;
		}
		else{
			dealerIndex += 1;
		}
	}

}
