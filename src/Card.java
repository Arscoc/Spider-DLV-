

public class Card {
	
	



	public int value;
	public String suit;
	public Boolean hide=true;
	public String encode;
	
	public Card(int value, String suit)
	{
		this.value=value; 
		this.suit =suit;
		switch(suit) {
		case "spades":
			encode="\u2660";
			break;
		case "clubs":
			encode="\u2663";
			break;
		case "hearts":
			encode="\u2665";
			break;
		case "diamonds":
			encode="\u2666";

			break;
		}
}}