//Players are mutable

package poker;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Scanner;

public class Player implements Serializable {

	private final String name;
	private int money;
	private Card [] holeCards;
	private boolean folded;
	private int streetMoney;
	public boolean endAction;
	public boolean isAllIn;
	//ID is essentially their seat number
	public final int id;
	//should we make it mutable? and allow PlayGame to modify it?
	//add more variables

	public Player(String name, int money, int id) {
		
		this.name = name;
		this.money = money;
		this.id = id;
		holeCards = new Card[2];
		folded = false;
		isAllIn = false;
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

	public Card[] getHoleCards(){
		return holeCards;
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
	public int act(int minimumBet, int pot, Hand hand, PokerGame game, int streetIn) {

		boolean isCorrect = false;
		String action;
		int betSize = minimumBet;

		if(!this.folded && !this.isAllIn) {
			while(!isCorrect){
				//Output board
				hand.printBoard(game, streetIn, game.handNumber);
				// Output hand and player stats
				game.out.println(this);
				game.out.println("Bet/Check/Call/Fold" + "\nnewline");
				action = game.in.nextLine();
				// Checks what action user inputs
				if(action.equalsIgnoreCase("Bet")) {
					while(true){
						//Output board
						hand.printBoard(game, streetIn, game.handNumber);
						// Output hand and player stats
						game.out.println(this);
						try{
							game.out.println("Size" + "\nnewline");
							betSize = game.in.nextInt();
							//Required since nextInt() doesn't actually read a next line
							game.in.nextLine();
							if(betSize < 2*minimumBet || betSize == 0) {
								game.out.println("Illegal bet size" + "\nnewline");
								//Reset betsize to what was previously bet (miniumum bet)
								betSize = minimumBet;
							} else if (money <= betSize) {
								game.out.println("All in" + "\nnewline");
								//If betsize is greater than money, player is all in
								betSize = money;
								this.spendMoney(betSize);
								hand.addToPot(betSize);
								streetMoney = betSize;
								isAllIn = true;
								hand.increaseAllInCounter();
								isCorrect = true;
							} else {
								//Any additional bet is total (don't have to remember previous bet)
								this.spendMoney(betSize-streetMoney);
								hand.addToPot(betSize-streetMoney);
								//Total streetmoney becomes betsize
								streetMoney = betSize;
								isCorrect = true;
							}
							break;
						}
						catch(java.util.InputMismatchException e) {
							game.out.println("Not a number" + "\nnewline");
							game.in.next();
							continue;
						}
					}
				}
				//we need a way for BB to check b/c minbet is still > 0 for him
				else if(action.equalsIgnoreCase("Check")) {
					if(minimumBet - streetMoney > 0){
						game.out.println("You cannot check when the pot is raised");
					} else{
						isCorrect = true;
						betSize = 0;
					}
				}
				else if(action.equalsIgnoreCase("Call")) {
					if(minimumBet == 0 || minimumBet - streetMoney == 0){
						game.out.println("You cannot call when there is no bet");
					}
					else if(money <= minimumBet){
						game.out.println("All in" + "\nnewline");
						betSize = money;
						this.spendMoney(betSize);
						hand.addToPot(betSize);
						isAllIn = true;
						hand.increaseAllInCounter();
						isCorrect = true;
					}
					else{
						this.spendMoney(minimumBet - streetMoney);
						hand.addToPot(minimumBet-streetMoney);
						isCorrect = true;
						betSize = minimumBet;
					}
				}
				else if(action.equalsIgnoreCase("Fold")) {
					this.fold();
					isCorrect = true;
					betSize = 0;
				}
				else {
					game.out.println("Incorrect action, please try again");
				}
			}
		}
		else{
			betSize = 0;
			//If player has folded we need to tell client where output ends
			game.out.println("" + "\nnewline" );
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
					holeCards[0] + holeCards[1] + "--" + id;
		return retVal;
		
	}

}
