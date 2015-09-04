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
					System.out.print("Size: ");
					betSize = in.nextInt();
					//Required since nextInt() doesn't actually read a next line
					in.nextLine();
					if(betSize < 2*minimumBet || betSize == 0) { 
						System.out.print("Illegal bet size");
						betSize = minimumBet;
					} else if (money < betSize) {
						System.out.print("Not enough money");
						betSize = minimumBet;
					} else {
						this.spendMoney(betSize);
						isCorrect = true;
					}
				}
				//we need a way for BB to check b/c minbet is still > 0 for him
				else if(action.equalsIgnoreCase("Check")) {
					if(minimumBet > 0){
						System.out.print("You cannot check when the pot is raised");
					} else{
						isCorrect = true;
						betSize = 0;
					}
				}
				else if(action.equalsIgnoreCase("Call")) {
					this.spendMoney(betSize);
					isCorrect = true;
					betSize = minimumBet;
				}
				else if(action.equalsIgnoreCase("Fold")) {
					this.fold();
					isCorrect = true;
					betSize = 0;
					System.out.print("Nit");

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
	
	public String toString() {
		
		String retVal = "";
		retVal += name + ":" + "$" + money + "--" + 
					holeCards[0] + holeCards[1] + "--" + position;
		return retVal;
		
	}



}
