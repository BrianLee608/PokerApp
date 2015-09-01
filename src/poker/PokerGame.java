package poker;
import java.util.Random;

public class PokerGame {

	//Constants
	public static final int STARTING_CASH = 200;
	public static final int BIG_BLIND = 2;
	public static final int SMALL_BLIND = 1;
	
	//variables
	public static int numOfRoundsPlayed; 
	public static int pot;  //does it matter static or instance?
	
	public Player [] players;
	public Card [] board;
	
	//Instantiate this when a fresh new game starts
	public PokerGame(int numOfPlayers) {
		
		pot = 0;
		numOfRoundsPlayed = 0;
		
		Deck deck = new Deck();
		deck.shuffle();

		//randomly initialize the positions of the players
		Random rn = new Random();
		int pos = rn.nextInt(2);	//returns 0 or 1

		//Initialize players and alternates the distribution of whole cards
		//this loop is not completed yet. this loop is faulty at the moment
		players = new Player[numOfPlayers];
		for (int i = 0; i < 2*numOfPlayers; i++) {

			if (i < numOfPlayers) { 
				players[i] = new Player(STARTING_CASH, pos);
			}
			
			players[i].receiveCard(deck.deal(1)[0]);
		}
		
		
		players[0] = new Player(STARTING_CASH, pos);
		players[1] = new Player(STARTING_CASH, pos);
		
		
		//initialize a blank deck (all null)
		board = new Card[5]; 
		
	}
	
	

	
	
	
	
}
