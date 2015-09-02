//Players are immutable

package poker;

public class Player {

	private final String name;
	private int money;
	private Card [] holeCards;
	private int position;
	private boolean DEALER;
	private boolean turnToAct; //should we make it mutable? and allow PlayGame to modify it?
	//add more variables
	
	public Player(String name, int money, int position) {
		
		this.name = name;
		this.money = money;
		this.position = position;
		DEALER = (position == 0) ? true : false;
		holeCards = new Card[2];
		turnToAct = false; //modify this later

	}
	
	public int getMoney() {
		
		return money;
		
	}

	public void receiveHand(Card[] hand) {
		
		holeCards[0] = hand[0];
		holeCards[1] = hand[1];
		
	}
	
	public Card getCardAtPosition(int pos) {
		
		if (pos != 0 || pos != 1) { 
			//throw an exception
		}
		
		return holeCards[pos];
		
	}
	
	public void postBB() {
		
		if (money == 1) {
			money -= 1;
		} else {
			money -= PokerGame.BIG_BLIND;
		}
		
	}
	
	public void postSB() {
		
		money -= PokerGame.SMALL_BLIND;
		
	}
	
	
	public void raise(int amount) {
		if (amount>money){
			System.out.println("Incorrect Bet Size");
		}
		else{
			money -= amount;
		}
		
	}
	
	public String toString() {
		
		String retVal = "";
		retVal += name + ": " + "$" + money + ", " + holeCards[0] + holeCards[1] + ", " + position;
		return retVal;
		
	}

}
