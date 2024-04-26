import java.util.ArrayList;

public class Main {

	public static void main(String[] args){

		Deck deck = new Deck(); //Shuffles deck
		deck.shuffle();

		Player player1 = new Player("Player 1");
		Player player2 = new Player("Player 2");

		Queue[] players = {player1, player2};

		//deal(deck, players);

		deal(deck, player1);
		deal(deck, player2);

		Board b = new Board();

		//playNotchWar(players);
		playNotchWar(player1, player2);

		Player winner = determineWinner(player1, player2);
		System.out.println();
		System.out.println("The winner is... " + winner.getName() + "!");

	}

	public static void deal(Deck deck, Player player) { // Gives a player 26 cards (half of the deck)
		for (int i = 0; i < 26; i++) {
			player.put(deck.getCard());
		}
	}

	//	public static void deal(Deck deck, Queue<Player> players) { // Gives each player 26 cards (equally splits deck)
	//		for (int j = 0; j < players.size(); j++) {
	//			Player player = players.get(j);
	//			for (int i = 0; i < 26; i++) {
	//				player.put(deck.getCard());
	//			}
	//		}
	//	}

	public static void playNotchWar(Player player1, Player player2) { // Keep playing until one player has 0 cards

		while (!player1.isEmpty() && !player2.isEmpty()) {
			playRound(player1, player2);
		}

	}

	public static void playRound(Player player1, Player player2) {
		Card card1 = player1.peek();
		Card card2 = player2.peek();

		System.out.println(player1.getName() + " plays " + card1 + ", " + player2.getName() + " plays " + card2);

		if (card1.compareTo(card2) == 0) { // Cards are the same
			war(player1, player2);
		} 
		else if (Math.abs(card1.getRank() - card2.getRank()) == 1) { // one player has a card one below their opponent
			System.out.println("Notched!");
			if (card1.getRank() < card2.getRank()) {
				player1.addCards(player1.get(), player2.get());
			} else {
				player1.addCards(player1.get(), player2.get());
			}
		} else if (card1.getRank() > card2.getRank()) {
			player1.addCards(player1.get(), player2.get());
		} else {
			player1.addCards(player1.get(), player2.get());
		}

		System.out.println(player1.getName() + " has " + player1.size() + " cards, " +
				player2.getName() + " has " + player2.size() + " cards");
	}


	public static void war(Player player1, Player player2) {
		System.out.println("WAR!");
		ArrayList<Card> cardsInPlay = new ArrayList<>();

		boolean isTie;
		do {
			cardsInPlay.clear();
			isTie = false;

			if (player1.size() >= 4) {  // Play 3 cards face down and 1 face up 
				for (int i = 0; i < 3; i++) {
					cardsInPlay.add(player1.get());
				}
				cardsInPlay.add(player1.get());
			} else {
				for (int i = 0; i < player1.size(); i++) {
					cardsInPlay.add(player1.get());
				}
			}

			if (player2.size() >= 4) {  // Play 3 cards face down and 1 face up
				for (int i = 0; i < 3; i++) {
					cardsInPlay.add(player2.get());
				}
				cardsInPlay.add(player2.get());
			} else {
				for (int i = 0; i < player2.size(); i++) {
					cardsInPlay.add(player2.get());
				}
			}

			if (!player1.isEmpty() && !player2.isEmpty() &&
					player1.peek().compareTo(player2.peek()) == 0) {
				isTie = true;
				System.out.println(player1.getName() + " plays " + player1.peek() + ", " +
						player2.getName() + " plays " + player2.peek());
				System.out.println("Another tie! WAR!");
			} else {
				break; // Exit the loop if no tie
			}
		} while (isTie && cardsInPlay.size() < 52 && player1.size() >= 4 && player2.size() >= 4);

//		  while (!cardsInPlay.isEmpty()) {
//		        player1.put(cardsInPlay.remove(0));
//		        player2.put(cardsInPlay.remove(0));
//		    }
//	}
		
		 // Distribute the cards in play to the winner
		Card player1Card = player1.isEmpty() ? null : player1.peek();
		Card player2Card = player2.isEmpty() ? null : player2.peek();

		if (player1Card != null && player2Card != null) {
			if (player1Card.compareTo(player2Card) > 0) {
                player1.put(player1Card);
                System.out.println(player1.getName() + " plays " + player1Card + ", " +
                        player2.getName() + " plays " + player2Card);
                player1.addAll(cardsInPlay);
            } else {
                player2.put(player2Card);
                System.out.println(player1.getName() + " plays " + player1Card + ", " +
                        player2.getName() + " plays " + player2Card);
                player2.addAll(cardsInPlay);
            }
        } else if (player1Card != null) {
            player1.put(player1Card);
            player1.addAll(cardsInPlay);
            System.out.println(player1.getName() + " plays " + player1Card + ", " +
                    player2.getName() + " plays " + player2Card);


        } else {
            player2.put(player2Card);
            player2.addAll(cardsInPlay);
            System.out.println(player1.getName() + " plays " + player1Card + ", " +
                    player2.getName() + " plays " + player2Card);
        }
	}


	public static Player determineWinner(Player player1, Player player2) { // Whichever player has 0 cards first loses
		if (player1.isEmpty()) {
			return player2;
		} else {
			return player1;
		}
	}

}
