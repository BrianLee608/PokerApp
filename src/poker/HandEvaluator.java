package poker;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

//Hand strength will be stored in an Integerarray of size 5.
// index 0 = strength rank
// index 1 = value of quad,highest straight, 
// index 2 = depends. if has2pair, lower of the 2 pair
// index 3 = high card
// index 4 = high card


public class HandEvaluator {

	public static final int QUAD = 8;

	//This method will eventually evaluate the hand strengths of each player
	//in the array players. These players are the activePlayers. 
	//Inside this method, a heirarchy of method calls to:
	//Royal Flush --> Straight Flush --> 4 of a kind ...---> Pair
	//will be made
	public static Player evaluateHands(Player[] players, Card[] board) {

		int[] playerStrengths = new int[players.length];
		
		ArrayList <Card> allCards = new ArrayList <Card> ();
		for (int i = 0; i < players.length; i++) {
			
			allCards.addAll(Arrays.asList(players[i].holeCards[0], players[i].holeCards[1],
					board[0], board[1], board[2], board[3], board[4]));
			
	
			//playerStrengths[i] = determineStrength(allCards);
			}
		
		
		
		return players[determineWinner(playerStrengths)];

	}

	private static int determineWinner(int[] playerStrengths) {
		return 0;
	}

	public static int hasStraightFlush(Card[] cards) {

		return false;

	}

	//the passed in cards must be sorted
	public static int[] hasFourOfAKind(ArrayList<Card> cards) {

		int quadCounter = 0;
		int quadValue = 0;
		int kickerValue = 0;
		//if you have 4 of a kind, it must be in indicies 0-3 through 3-6
		for (int i = 0; i <= 3; i++) {
			quadCounter = 0; //reset quadCounter;
			for (int a = i+1; a < cards.size(); a++) {
				if (cards.get(i).getRank().equals(cards.get(a).getRank())) {
					quadCounter++;
				} else {
					break;
				}
			}
			if (quadCounter == 3) { //if you have quads
				//retVal[1]
				quadValue = cards.get(i).getRank().getNumeral();
				
				//delete all cards that are included in the quad (4 cards)
				cards.subList(i, i+3).clear();
				
				//last value in this array is high card
				kickerValue = cards.get(2).getRank().getNumeral();
				break;
			}
		}
		int [] retVal = new int [5];
		if (quadCounter == 3) { //if it is quads {
			retVal[0] = QUAD;
			retVal[1] = quadValue;
			retVal[2] = kickerValue;
			retVal[3] = 0;
			retVal[4] = 0;
			return retVal;
		} else {
			retVal[0] = 0;
			return retVal;		
		}

}

	public static int hasFullHouse(Card[] cards) {

		return false;

	}

	public static int hasFlush(Card[] cards) {

		return false;

	}

	public static int hasStraight(Card[] cards) {

		return false;

	}

	public static int hasThreeOfAKind(Card[] cards) {

		return false;

	}

	public static int hasTwoPair(Card[] cards) {

		return false;

	}

	public static int hasOnePair(Card[] cards) {

		return false;

	}
	
	public static int[] determineStrength(ArrayList<Card> cards) {
		
		
		if (hasFourOfAKind(cards)[0] == QUAD) {
			return hasFourOfAKind(cards);
		} else { //if high card
			return hasHighCard(cards);
		}
		
		
		
	}



	
	
	
	
}
