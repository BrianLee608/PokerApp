
public class Player {

	
	private int money;
	private Card [] wholeCards;
	private boolean DEALER;
	private boolean turnToAct; //should we make it mutable? and allow PlayGame to modify it?
	//add more variables
	
	
	public Player(Card card1, Card card2, int startingMoney, boolean position) {
		
		wholeCards = new Card[2];
		wholeCards[0] = card1;
		wholeCards[1] = card2;
		money = startingMoney;
		DEALER = position;
		
		if (DEALER = true) {
			turnToAct = true;
		} else {
			turnToAct = false;
		}

	}
	
	
	public int getMoney() {
		
		return money;
		
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
			money -= PlayGame.BIG_BLIND;
		}
		
	}
	
	public void postSB() {
		
		money -= PlayGame.SMALL_BLIND;
		
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
