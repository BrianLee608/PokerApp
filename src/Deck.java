

import java.util.Random;

/* This class represents a deck of card objects in an array. 
 * 
 */

public class Deck {

	private Card[] cards;

	
	public Deck() {
		
		this.cards = new Card[52];
		
		int index = 0;
		for (int suit = 0; suit < 4; suit++) {
			for (int value = 1; value <=13; value++) {
				this.cards[index++] = new Card(value, suit);
			}
		}
	}
	
	//Copy constructor. Instantiates an aliased new deck of cards. 
	public Deck(Deck other) {
		
		this.cards = new Card[other.cards.length];
		for (int i = 0; i < cards.length; i++) {
			this.cards[i] = other.cards[i];
		}
		
	}

	public Card getCardAt(int position) {
		
		return this.cards[position];
		
	}

	public int getNumCards() {
		
		return this.cards.length;
	}

	//Shuffles the deck in the following manner: 
	//"the new array of cards will consist of:  the first card from the top 
	//packet, followed by the first card from the bottom packet, followed by
	//the second card from the top packet, followed by the second card from the
	//bottom packet, etc."
	//Algorithm: 
	//Create a blank array of the same size as the current cards' length.
	//if the deck is odd, we must set counter to 1 instead of 0 in order 
	//to round up for subsequent operations on length/2. 
	public void shuffle() {
		
		Card[] tempDeck = new Card[this.cards.length];
		
		int counter = 0;
		
		if (this.cards.length % 2 != 0) {
			counter = 1;
		}

		for (int i = 0; i < tempDeck.length; i++) {
			if (i % 2 == 0) {         //i is even
				tempDeck[i] = this.cards[i/2];
			} else {				  //i is odd
				tempDeck[i] = this.cards[tempDeck.length/2 + counter++];
			}
		} 
		
		for (int i = 0; i < this.cards.length; i++) {
			this.cards[i] = tempDeck[i];
		}
		
		
		/*
		//This is me practicing the modern shuffling algorithm
		
		Random rn = new Random();
		
		for (int i = cards.length-1; i > 0; i--) {
			
			int swapIdx = rn.nextInt(i+1);
			
			Card temp = cards[swapIdx];
			cards[swapIdx] = cards[i];
			cards[i] = temp; 
			
		}
		
		*/
		
	}

	//Cuts the deck at a given position. If the position is 4, then the newly
	//cut deck will begin with cards[4] to the end... followed by cards[0] to
	// cards[3]
	public void cut(int position) {
		
		Card[] tempDeck = new Card[this.cards.length];

		int belowCut = position;
		int aboveCut = 0;
		for (int i = 0; i < tempDeck.length; i++) {
			if (i < tempDeck.length - position) {
				tempDeck[i] = this.cards[belowCut++];
			} else {
				tempDeck[i] = this.cards[aboveCut++];
			}
		}
		
		for (int i = 0; i < this.cards.length; i++) {
			this.cards[i] = tempDeck[i];
		}
		
	}

	
	//Returns an array of cards containing the cards that were dealt.
	//Also updates the current deck of cards to have an appropriate array of
	//cards after dealing numCards.
	public Card[] deal(int numCards) {
		
		Card[] smaller = new Card[this.cards.length - numCards];
		
		int smallerIndex = 0;
		for (int i = numCards; i < this.cards.length; i++) {
			smaller[smallerIndex++] = this.cards[i];
		}
		
		Card[] dealtCards = new Card[numCards];
		
		for (int i = 0; i < numCards; i++) {
			dealtCards[i] = this.cards[i];
		}
		
		this.cards = smaller;
		return dealtCards;
		
		
		
	}
		
	
	
}
