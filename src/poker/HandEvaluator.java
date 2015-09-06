package poker;

public class HandEvaluator {


	//This method will eventually evaluate the hand strengths of each player
	//in the array players. These players are the activePlayers. 
	//Inside this method, a heirarchy of method calls to:
	//Royal Flush --> Straight Flush --> 4 of a kind ...---> Pair
	//will be made
	public static Player evaluateHands(Player[] players, Card[] board) {

		return players[0];

	}

	public boolean hasStraightFlush(Card[] cards) {

		return false;

	}

	public boolean hasFourOfAKind(Card[] cards) {

		return false;

	}

	public boolean hasFullHouse(Card[] cards) {

		return false;

	}

	public boolean hasFlush(Card[] cards) {

		return false;

	}

	public boolean hasStraight(Card[] cards) {

		return false;

	}

	public boolean hasThreeOfAKind(Card[] cards) {

		return false;

	}

	public boolean hasTwoPair(Card[] cards) {

		return false;

	}

	public boolean hasOnePair(Card[] cards) {

		return false;

	}



}
