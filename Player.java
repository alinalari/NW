
public class Player extends Queue<Card>{ 

	private String name;

	public Player(String name) {
		super();
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void clear() {
	    super.clear();
	}

	public void addCards(Card card, Card card2) {
		    add(card);
		    add(card2);
	}
	
	



}




