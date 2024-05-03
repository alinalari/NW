
import java.util.ArrayList;


public class Main {

	static Player player1 = new Player("Player 1");
	static Player player2 = new Player("Player 2");

	public static void main(String[] args){

		// Shuffles deck
		Deck deck = new Deck(); 
		deck.shuffle();

		// Deals each player half the deck
		deal(deck, player1);  
		deal(deck, player2);

		//Begins game
		playNotchWar(player1, player2);

		//Prints winner at the end of the game
		Player winner = determineWinner(player1, player2);
		System.out.println();
		System.out.println("The winner is... " + winner.getName() + "!");

	}

	// Gives a player 26 cards (half of the deck)
	public static void deal(Deck deck, Player player) { 
		for (int i = 0; i < 26; i++) {
			player.put(deck.getCard());
		}
	}

	// Keep playing until one player has 0 cards
	public static void playNotchWar(Player player1, Player player2) { 
		while (!player1.isEmpty() && !player2.isEmpty()) {
			playRound(player1, player2);
		}

	}

	public static void playRound(Player player1, Player player2) {
		Card card1 = player1.peek();
		Card card2 = player2.peek();

		System.out.println(player1.getName() + " plays " + card1 + ", " + player2.getName() + " plays " + card2);

		// Cards are the same = tie -> War!
		if (card1.compareTo(card2) == 0) { 
			war(player1, player2);
		}

		// One player has a card 1 below their opponent -> Notched!
		else if (Math.abs(card1.getRank() - card2.getRank()) == 1) { 
			System.out.println("Notched!");
			if (card1.getRank() < card2.getRank()) {
				player1.addCards(player1.get(), player2.get());

			} else {
				player2.addCards(player1.get(), player2.get());

			}
		} else if (card1.getRank() > card2.getRank()) {
			player1.addCards(player1.get(), player2.get());

		} else {
			player2.addCards(player1.get(), player2.get());

		}

		System.out.println(player1.getName() + " has " + player1.size() + " cards, " +
				player2.getName() + " has " + player2.size() + " cards");
	}


	public static void war(Player player1, Player player2) {
		System.out.println("WAR!");
		ArrayList<Card> cardsInPlay = new ArrayList<>();
		ArrayList<Card> player1Last = new ArrayList<>();
		ArrayList<Card> player2Last = new ArrayList<>();

		boolean isTie;

		do {

			isTie = false;

			// Both players play 3 cards face down and 1 face up
			if (player1.size() >= 4) {  
				for (int i = 0; i < 4; i++) {
					Card card = player1.get();
					cardsInPlay.add(card);
					player1Last.add(card);
				}

			} else {
				for (int i = 0; i < player1.size(); i++) {
					Card card = player1.get();
					cardsInPlay.add(card);
					player1Last.add(card);
				}
			}

			if (player2.size() >= 4) {  
				for (int i = 0; i < 4; i++) {
					Card card = player2.get();
					cardsInPlay.add(card);
					player2Last.add(card);
				}
			} else {
				for (int i = 0; i < player2.size(); i++) {
					Card card = player2.get();
					cardsInPlay.add(card);
					player2Last.add(card);
				}
			}

			// If there is a tie and then another tie
			if (!player1.isEmpty() && !player2.isEmpty() &&
					player1.peek().compareTo(player2.peek()) == 0) {
				isTie = true;
				System.out.println(player1.getName() + " plays " + player1.peek() + ", " +
						player2.getName() + " plays " + player2.peek());
				System.out.println("Another tie! WAR!");
			} else {
				if (isTie) {
					isTie = false;
				}
				break; // Exit the loop if no tie
			}
		} while (isTie && cardsInPlay.size() < 52 && player1.size() >= 4 && player2.size() >= 4);

		// Distribute the cards in play to the winner
		Card player1Card = player1.isEmpty() ? null : player1.peek();
		Card player2Card = player2.isEmpty() ? null : player2.peek();
		if (player1Card != null) {
			cardsInPlay.add(player1.get());
		}
		if (player2Card != null) {
			cardsInPlay.add(player2.get());
		}

		if (player1Card != null && player2Card != null) {

			if (player1Card.compareTo(player2Card) > 0) {
				System.out.println(player1.getName() + " plays " + player1Card + ", " +
						player2.getName() + " plays " + player2Card);
				player1.addAll(cardsInPlay);

			} else {
				System.out.println(player1.getName() + " plays " + player1Card + ", " +
						player2.getName() + " plays " + player2Card);
				player2.addAll(cardsInPlay);
			}

		} else if (player1Card != null) {
			Card card2 = player2Last.get(player2Last.size() - 1);
			System.out.println(player1.getName() + " plays " + player1Card + ", " +
					player2.getName() + " plays " + card2);
			player1.addAll(cardsInPlay);

		} else {
			Card card1 = player1Last.get(player1Last.size() - 1);
			System.out.println(player1.getName() + " plays " + card1 + ", " +
					player2.getName() + " plays " + player2Card);
			player2.addAll(cardsInPlay);
		}
	}


	// Whichever player has 0 cards first loses
	public static Player determineWinner(Player player1, Player player2) { 
		if (player1.isEmpty()) {
			return player2;
		} else {
			return player1;
		}
	}

}

