import java.util.Random;


public class PlayGame {

	//Constants
	public static final int STARTING_CASH = 200;
	public static final int BIG_BLIND = 2;
	public static final int SMALL_BLIND = 1;

	
	public static void main(String[] args) {
		
		//initialize 52 card deck and shuffle it
		Deck deck = new Deck();
		deck.shuffle();
		
		//randomly initialize the positions of the players
		Random rn = new Random();
		int dealerAssignment = rn.nextInt(2);	//returns 0 or 1
		boolean pos;
		if (dealerAssignment == 0) {
			pos = true;		//Dealer
		} else { 
			pos = false;	//not Dealer
		}
		
		
		//Alternates the distribution of whole cards
		Card player1FirstCard = deck.deal(1)[0];
		Card player2FirstCard = deck.deal(1)[0];
		Card player2SecondCard = deck.deal(1)[0];
		Card player1SecondCard = deck.deal(1)[0];
		
		//instantiate the players
		//idea: how about we store players in a Player [] array.
		// 	    this way in the future, it wouldn't be that hard to modify
		//		the code to accommodate more than 2 players. 
		Player player1 = new Player(player1FirstCard, player1SecondCard,
									STARTING_CASH, pos);
		Player player2 = new Player(player2FirstCard, player2SecondCard,
									STARTING_CASH, !pos);
		
		System.out.println(player1.getCardAtPosition(0));
		System.out.println(player1.getCardAtPosition(1));
		System.out.println(player2.getCardAtPosition(0));
		System.out.println(player2.getCardAtPosition(1));
		
		System.out.println(player1);
		System.out.println(player2);
		
		
	}
	
	
	
}
