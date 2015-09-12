package poker;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

//Hand strength will be stored in an Integerarray of size 5.
// index 0 = strength rank
// index 1 = value of quad,highest straight, 
// index 2 = depends. if has2pair, lower of the 2 pair
// index 3 = high card
// index 4 = high card


public class HandEvaluator {

	public static final int STRAIGHTFLUSH = 22;
	public static final int QUAD = 21;
	public static final int FULLHOUSE = 20;
	public static final int FLUSH = 19;
	public static final int STRAIGHT = 18;
	public static final int SET = 17;
	public static final int TWOPAIR = 16;
	public static final int PAIR = 15;
	//This method will eventually evaluate the hand strengths of each player
	//in the array players. These players are the activePlayers. 
	//Inside this method, a heirarchy of method calls to:
	//Royal Flush --> Straight Flush --> 4 of a kind ...---> Pair
	//will be made
	public static Player evaluateHands(Player[] players, Card[] board) {

		//playerStrengths represents the numbering system for each player
		int[][] playerStrengths = new int[players.length][5];

		//initialize with all 5 cards of the board
		Card[] allCards = new Card[7];
		allCards[0] = board[0];
		allCards[1] = board[1];
		allCards[2] = board[2];
		allCards[3] = board[3];
		allCards[4] = board[4];

		
		for (int i = 0; i < players.length; i++) {

			//unique for each player
			allCards[5] = players[i].holeCards[0];
			allCards[6] = players[i].holeCards[1];

			ArrayList<Card> allCardz = new ArrayList<Card>(Arrays.asList(allCards));

			sort(allCardz);

			//playerStrengths[i] is player[i]'s strength
			playerStrengths[i] = determineStrength(allCardz);
			
			}
		
		return players[determineWinner(playerStrengths)];

	}

	private static int determineWinner(int[][] playerStrengths) {
		return 0;
	}

	public static int[] hasStraightFlush(ArrayList<Card> cards) {

		ArrayList<Card> tempCards = new ArrayList<Card>(cards);
		
		int straightFlushCounter = 0;
		int sfValue = 0; //straightflush value
		//at this point tempCards should be sorted SuitandNumeral-wise
		for (int i = tempCards.size()-1; i > 0; i--) {

			if (straightFlushCounter == 4) {
				break;
			}

			//if card[i] and card[i-1] are 1 apart
			if (tempCards.get(i).getRank().getNumeral() - 
					tempCards.get(i-1).getRank().getNumeral() == 1) {
				//if card[i] and card[i-1] have same suit
				if (tempCards.get(i).getSuit().getValue().equals(
						tempCards.get(i-1).getSuit().getValue())) {
					
					straightFlushCounter++;
					
					if (sfValue == 0) { //set the value of the sf
						sfValue = tempCards.get(i).getRank().getNumeral();
					}
				}
			} else { //if card[i-1] cannot possibly yield a straight flush
				sfValue = 0;
				straightFlushCounter = 0;
			}
		}

		//if you don't have a straight (other than A,2,3,4,5) 
		if (straightFlushCounter != 4) {

			int AceSFCounter = 0;
			for (int i = 0; i < tempCards.size(); i++) {
				switch(AceSFCounter){
				case 0: //DEUCE
					if (tempCards.get(i).getRank().getNumeral() == 2) {
						AceSFCounter++;
					}
					break; //THREE
				case 1: 
					if (tempCards.get(i).getRank().getNumeral() == 3) {
						AceSFCounter++;
					}
					break;
				case 2:  //FOUR
					if (tempCards.get(i).getRank().getNumeral() == 4) {
						AceSFCounter++;
					}
					break;
				case 3: //FIVE
					if (tempCards.get(i).getRank().getNumeral() == 5) {
						AceSFCounter++;
					}
					break;	
				case 4:  //ACE
					if (tempCards.get(i).getRank().getNumeral() == 14) {
						AceSFCounter++;
					}
					sfValue = 5;
					straightFlushCounter = 4;
					break;
				}
			}
		}


		
		
		int[] retVal = new int[5];
		if (straightFlushCounter == 4) {
			retVal[0] = STRAIGHTFLUSH;
			retVal[1] = sfValue;
			return retVal;
		}
		
		return retVal;

		
		
	}

	//the passed in cards must be sorted
	public static int[] hasFourOfAKind(ArrayList<Card> cards) {
		//Store temporary arraylist of cards
		ArrayList<Card> tempCards = new ArrayList<Card>(cards);

		int quadCounter = 0;
		int quadValue = 0;
		int kickerValue = 0;
		//if you have 4 of a kind, it must be in indicies 0-3 through 3-6
		outerloop : for (int i = 0; i <= 3; i++) {
			quadCounter = 0; //reset quadCounter;
			for (int a = i+1; a < tempCards.size(); a++) {
				if (tempCards.get(i).getRank().equals(tempCards.get(a).getRank())) {
					quadCounter++;
					if (quadCounter == 3) { //if you have quads
						//retVal[1]
						quadValue = tempCards.get(i).getRank().getNumeral();
						//delete all cards that are included in the quad (4 cards)
						//sublist(inclusive, exclusive)
						tempCards.subList(i, i+4).clear();
						//last value in this array is high card
						kickerValue = tempCards.get(2).getRank().getNumeral();
						break outerloop;
					}
				} else {
					break;
				}
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
			return retVal;
		}

}

	public static int[] hasFullHouse(ArrayList <Card> cards) {
		//Store temporary arraylist of cards
		ArrayList<Card> tempCards = new ArrayList<Card>(cards);

		int tripsCounter = 0;
		int pairCounter = 0;
		//Fullhouse is comprised of trips + pair
		int tripsValue = 0;
		int pairValue = 0;
		//Find highest 3 of a kind
		//Uses same method as quads, but starts from the end (finds highest trips possible)
		outerloop : for(int i = tempCards.size()-1; i >= 2; i--){
			tripsCounter = 0;
			for (int a = i-1; a >= 0; a--){
				if(tempCards.get(i).getRank().equals(tempCards.get(a).getRank())){
					tripsCounter++;
					if (tripsCounter == 2){
						tripsValue = tempCards.get(i).getRank().getNumeral();
						tempCards.subList(i - 2, i+1).clear();
						break outerloop;
					}
				} else{
					break;
				}
			}
		}

		if(tripsValue != 0){
			//Find highest remaining pair
			outerloop : for(int i = tempCards.size()-1; i >= 1; i--) {
				pairCounter = 0;
				for (int a = i - 1; a >= 0; a--) {
					if (tempCards.get(i).getRank().equals(tempCards.get(a).getRank())) {
						pairCounter++;
						//Must be greater or equal to one (if two trips are present lower one is counted as a pair)
						if (pairCounter >= 1) {
							pairValue = tempCards.get(i).getRank().getNumeral();
							break outerloop;
						}
					} else {
						break;
					}
				}
			}
		}

		int [] retVal = new int [5];
		if(tripsValue != 0 && pairValue != 0){
			retVal[0] = FULLHOUSE;
			retVal[1] = tripsValue;
			retVal[2] = pairValue;
			return retVal;
		}
		else{
			return retVal;
		}
	}

	public static int[] hasFlush(ArrayList <Card> cards) {
		//Store temporary arraylist of cards
		ArrayList<Card> tempCards = new ArrayList<Card>(cards);

		//sort.suit 
		sortSuit(tempCards);
		int flushCounter = 0;
		int flushValue = 0;
		//Same method as fullhouse, starts from end (highest flush)
		//Requires sort by suit
		outerloop : for (int i = tempCards.size() - 1; i >= 4; i--) {
			flushCounter = 0;
			for (int a = i - 1; a >= 0; a--) {
				if (tempCards.get(i).getSuit().equals(tempCards.get(a).getSuit())) {
					flushCounter++;
					if (flushCounter == 4) {
						flushValue = tempCards.get(i).getRank().getNumeral();
						break outerloop;
					}
				} else {
					break;
				}
			}
		}
		int[] retVal = new int[5];
		if (flushCounter == 4) {
			retVal[0] = FLUSH;
			retVal[1] = flushValue;
			return retVal;
		} else {
			return retVal;
		}
	}

	//pre-condition: cards are numerically sorted
	public static int[] hasStraight(ArrayList<Card> cards) {

		ArrayList<Card> tempCards = new ArrayList<Card>(cards);


		int straightCounter = 0;
		int straightValue = 0;
		for (int i = tempCards.size()-1; i > 0; i--) {

			if (straightCounter == 4) {
				straightValue = tempCards.get(i+4).getRank().getNumeral();
				break;
			}

			if (tempCards.get(i).getRank().getNumeral() - 
					tempCards.get(i-1).getRank().getNumeral() == 1) {

				straightCounter++;
			} else {
				continue;
			}
		}

		//if you don't have a straight (other than A,2,3,4,5) and
		//cards[0] is a deuce and cards[6] is an ace
		if (straightCounter != 4 && tempCards.get(0).getRank().getNumeral()==2 
				&&tempCards.get(6).getRank().getNumeral()==14) {

			int AceStraightCounter = 0;
			for (int i = 0; i < tempCards.size(); i++) {
				switch(AceStraightCounter){
				case 0: //DEUCE
				if (tempCards.get(i).getRank().getNumeral() == 2) {
					AceStraightCounter++;
				}
				break; //THREE
				case 1: 
					if (tempCards.get(i).getRank().getNumeral() == 3) {
						AceStraightCounter++;
					}
					break;
				case 2:  //FOUR
					if (tempCards.get(i).getRank().getNumeral() == 4) {
						AceStraightCounter++;
					}
					break;
				case 3: //FIVE
					if (tempCards.get(i).getRank().getNumeral() == 5) {
						AceStraightCounter++;
					}
					break;	
				case 4:  //ACE
					if (tempCards.get(i).getRank().getNumeral() == 14) {
						AceStraightCounter++;
					}
					straightValue = 5;
					break;
				}
			}
		}
		
		

		int[] retVal = new int[5];
		if (straightValue != 0) {
			retVal[0] = 18;
			retVal[1] = straightValue;
	
			return retVal;
		} 

		return retVal;


	}
	

//	public static int hasThreeOfAKind(Card[] cards) {
//
//		return false;
//
//	}
//
	public static int[] hasTwoPair(ArrayList<Card> cards) {
		
		ArrayList<Card> tempCards = new ArrayList<Card>(cards);

		int pairCounter = 0; 
		int higherPair = 0;
		int lowerPair = 0;
		int kicker = 0;
		
		//iterate backwards until you get to index 1
		for (int i = tempCards.size()-1; i > 0; i--) {

			
			if (pairCounter == 2) {
				if (kicker != 0) { //kicker has already been found
					break;
				} else { //kicker has not been set yet (kicker == 0)
					kicker = tempCards.get(i).getRank().getNumeral();
					break;
				}
			}
			
			//if the current card has same numeral as the card before
			if (tempCards.get(i).getRank().getNumeral() == 
					tempCards.get(i-1).getRank().getNumeral()) {
				//i>1 b/c we want to check if the card at i-2 is not 
				//the same as card i and i-1
				if (i > 1) { 
					if (tempCards.get(i-1).getRank().getNumeral() !=
							tempCards.get(i-2).getRank().getNumeral()) {

						pairCounter++;
						i--; //eliminate a redundant iteration of the loop
						
						if (pairCounter == 1) {
							higherPair = tempCards.get(i).getRank().getNumeral();
						} else { //if pairCounter == 2 {
							lowerPair = tempCards.get(i).getRank().getNumeral();
						}
					}
				} else { //if i == 1
					pairCounter++;
					lowerPair = tempCards.get(i).getRank().getNumeral();
				}
			} else if (kicker == 0) { //set kicker if one hasn't been found yet
				kicker = tempCards.get(i).getRank().getNumeral();
				
			}
		}
		
		int[] retVal = new int[5];
		if (pairCounter == 2) {
			retVal[0] = TWOPAIR;
			retVal[1] = higherPair;
			retVal[2] = lowerPair;
			retVal[3] = kicker;
			return retVal;
		}
		
		return retVal;
		

	}
//
//	public static int hasOnePair(Card[] cards) {
//
//		return false;
//
//	}
//
	public static int[] determineStrength(ArrayList<Card> cards) {

		if (hasFourOfAKind(cards)[0] == QUAD) {
			return hasFourOfAKind(cards);
		} else if (hasFullHouse(cards)[0] == FULLHOUSE){
			return hasFullHouse(cards);
		} else if (hasFlush(cards)[0] == FLUSH){
			return hasFlush(cards);
		} else if (hasStraight(cards)[0] == STRAIGHT) {
			return hasStraight(cards);
		} else{
			int[] retVal = new int [5];
			return retVal;
		}

	}

	public static void sort(ArrayList <Card> cardsIn) {

		Collections.sort(cardsIn);

	}

	public static void sortSuit(ArrayList <Card> cardsIn) {

		Collections.sort(cardsIn, new SuitCompare());

	}
	
	public static void sortSuitAndNumeral(ArrayList <Card> cardsIn) {
		
		Collections.sort(cardsIn, new SuitAndNumeralCompare());
	}

}
