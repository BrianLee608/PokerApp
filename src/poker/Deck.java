package poker;

import java.util.Random;

public class Deck {

	private Card[] cards;

	public Deck() {
		cards = new Card[52];
        int i = 0;
        for (Suit suit : Suit.values()) {
            for (Rank rank : Rank.values()) {
                cards[i++] = new Card(rank, suit);
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


	//Does the Fisher-Yates shuffling algorithm on this deck of cards
	public void shuffle() {

		Random rn = new Random();
		
		for (int i = cards.length-1; i > 0; i--) {
			
			int swapIdx = rn.nextInt(i+1);
			
			Card temp = cards[swapIdx];
			cards[swapIdx] = cards[i];
			cards[i] = temp; 
			
		}
		
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
	//Re-indexes this deck such that there are no null pointers in the beginning.
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
	
	
	
	//Just calls this class' deal method to get rid of one card. 
	//The card that was burned can never be accessed.
	public void burn() {
		
		this.deal(1);
		
	}
	

		
	
	
}
