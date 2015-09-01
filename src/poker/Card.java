package poker;

/*
 @author Brian Lee & Dennis Deng (c) 2015
*/

public class Card {

	private final Suit suit;    
	private final Rank rank; 
	                          

	public Card(Rank rank, Suit suit) {

		this.suit = suit;
		this.rank = rank;
		
	}


	public Rank getRank() {
		return rank;
	}
	

	public Suit getSuit() {
		return suit;
	}
	

	public String toString() {
		return rank + " of " + suit;
	}
	
	public boolean equals(Object o) {
		
		if (!(o instanceof Card)) {
			return false;
		}
		
		Card c = (Card)o;
		return rank.equals(c.rank) && suit.equals(c.suit);
		
	}

}