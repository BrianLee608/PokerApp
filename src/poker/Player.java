//Players are immutable

package poker;

public class Player {

	
	private int money;
	private Card [] wholeCards;
	private int position;
	private boolean DEALER;
	private boolean turnToAct; //should we make it mutable? and allow PlayGame to modify it?
	//add more variables
	
	
	public Player(int startingMoney, int positionIn) {
		
		money = startingMoney;
		wholeCards = new Card[2];
		position = positionIn;
		DEALER = (positionIn == 1) ? true : false;
		
		turnToAct = false; //modify this later

	}
	
	
	public int getMoney() {
		
		return money;
		
	}
	
	
	public void receiveCard(Card card) {
		
		if (wholeCards[0] == null) {
			wholeCards[0] = card;
		} else {
			wholeCards[1] = card;
		}
		
	}
	
	public Card getCardAtPosition(int pos) {
		
		if (pos != 0 || pos != 1) { 
			//throw an exception
		}
		
		return wholeCards[pos];
		
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
		
		money -= amount;
		
	}
	
	public String toString() {
		
		String retVal = "";
		retVal += "Money: " + money + ", Whole Cards: " + wholeCards[0] 
						+ " and " + wholeCards[1];
		return retVal;
		
	}

	
	
}
