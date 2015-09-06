//Players are mutable

package poker;

import java.util.Arrays;
import java.util.Scanner;

public class Player {

	private final String name;
	private int money;
	private Card [] holeCards;
	private int position;
	private boolean DEALER;
	private boolean turnToAct;
	private boolean folded;
	public int streetMoney;
	public boolean endAction;
	//should we make it mutable? and allow PlayGame to modify it?
	//add more variables

	public Player(String name, int money, int position) {
		
		this.name = name;
		this.money = money;
		this.position = position;
		DEALER = (position == 0) ? true : false;
		holeCards = new Card[2];
		turnToAct = false;
		folded = false; 
		//modify this later

	}
	
	public int getMoney() {
		
		return money;
		
	}

	public void receiveHand(Card[] hand) {
		
		holeCards[0] = hand[0];
		holeCards[1] = hand[1];
		
		folded = false;
		
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
	
	
	private void spendMoney(int amount) {

		money -= amount;

	}
	
	public boolean playerFolded() {
		
		return this.folded;
		
	}
	
	private void fold() {
		
		folded = true;
		
	}
	
	//This method gets called from a method in the Hand object (startStreet())
	//In that method, each player is looped through to act();
	public int act(int minimumBet) {
		
		Scanner in = new Scanner(System.in);
		boolean isCorrect = false;
		String action;
		int betSize = minimumBet;

		if(!this.folded) {
			// Output board and player stats
			System.out.println(this);

			while(isCorrect == false){
				System.out.print("Bet/Check/Call/Fold: ");
				action = in.nextLine();

				// Checks what action user inputs
				if(action.equalsIgnoreCase("Bet")) {
					while(true){
						try{
							System.out.print("Size: ");
							betSize = in.nextInt();
							//Required since nextInt() doesn't actually read a next line
							in.nextLine();
							if(betSize < 2*minimumBet || betSize == 0) {
								System.out.print("Illegal bet size\n");
								betSize = minimumBet;
							} else if (money < betSize) {
								System.out.print("Not enough money\n");
								betSize = minimumBet;
							} else {
								this.spendMoney(betSize);
								isCorrect = true;
							}
							break;
						}
						catch(Exception e) {
							System.out.print("Not a number\n");
							in.next();
							continue;
						}
					}
				}
				//we need a way for BB to check b/c minbet is still > 0 for him
				else if(action.equalsIgnoreCase("Check")) {
					if(minimumBet - streetMoney > 0){
						System.out.print("You cannot check when the pot is raised\n");
					} else{
						isCorrect = true;
						betSize = 0;
					}
				}
				else if(action.equalsIgnoreCase("Call")) {
					if(minimumBet == 0 || minimumBet - streetMoney == 0){
						System.out.print("You cannot call when there is no bet\n");
					}
					else{
						this.spendMoney(minimumBet - streetMoney);
						isCorrect = true;
						betSize = minimumBet - streetMoney;
					}
				}
				else if(action.equalsIgnoreCase("Fold")) {
					this.fold();
					isCorrect = true;
					betSize = 0;
				}
				else {
					System.out.print("Incorrect Action, Please Try Again\n");
				}
			}
		}
		return betSize;
	}
	
	public void winPot(int amount) {
		
		money += amount;
		
	}

	public void setStreetMoney(int amount){
		streetMoney += amount;
	}

	public void resetStreetMoney(){
		streetMoney = 0;
	}

	public void setEndAction(boolean bool){
		endAction = bool;
	}

	public String toString() {
		
		String retVal = "";
		retVal += name + ": " + "$" + money + "--" +
					holeCards[0] + holeCards[1];
		return retVal;
		
	}

}
