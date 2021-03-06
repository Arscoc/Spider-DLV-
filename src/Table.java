import java.util.ArrayList;
import java.util.Collections;

public class Table {

	ArrayList<Card> mainDeck;
	ArrayList<Column> columns;

	public Table() {
		mainDeck = new ArrayList<Card>();
		columns = new ArrayList<Column>();
		shuffleDeck();
		createColumns();

	}

	public void stampa() {

		for (int x = 0; x < 5; x++) {

			for (int z = 0; z < 10; z++) {

				System.out.print(columns.get(z).get(x).value + " " + columns.get(z).get(x).encode + "     ");

			}
			System.out.println(x);

		}
		for (int z = 0; z < 4; z++) {

			System.out.print(columns.get(z).get(5).value + " " + columns.get(z).get(5).encode + "     ");

		}
	}

	public void shuffleDeck() {
		for (int x = 1; x <= 13; x++) {

			mainDeck.add(new Card(x, "spades"));
			mainDeck.add(new Card(x, "clubs"));
			mainDeck.add(new Card(x, "diamonds"));
			mainDeck.add(new Card(x, "clubs"));
			mainDeck.add(new Card(x, "hearts"));
			mainDeck.add(new Card(x, "diamonds"));
			mainDeck.add(new Card(x, "hearts"));
			mainDeck.add(new Card(x, "spades"));

		}

		Collections.shuffle(mainDeck); 		Collections.shuffle(mainDeck); 		Collections.shuffle(mainDeck);

	}

	public void createColumns() {

		int q = 6;
		columns.add(new Column());
		columns.add(new Column());
		columns.add(new Column());
		columns.add(new Column());
		columns.add(new Column());
		columns.add(new Column());
		columns.add(new Column());
		columns.add(new Column());
		columns.add(new Column());
		columns.add(new Column());

		for (int z = 0; z < 10; z++) {
			if (z >= 4)
				q = 5;
			for (int x = 0; x < q; x++) {
				columns.get(z).add(mainDeck.get(0));
				mainDeck.remove(0);

			}

		}
		for (int z = 0; z < 10; z++)

			columns.get(z).get(columns.get(z).size() - 1).hide = false;

	}
	

	
	
	
	
	
}