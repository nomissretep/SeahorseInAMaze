package spieler;

import java.util.Map;

import com.sun.java.swing.plaf.gtk.GTKConstants.PositionType;

import generated.BoardType;
import generated.MoveMessageType;
import generated.TreasuresToGoType;
import ourGenerated.Board;
import ourGenerated.Card;

public class SimpleKI extends Spieler {

	public String name = "Simple";

	public Board shift(Board b, Card c, int i, int j) {

		return b;
	}

	// (0,1)(0,3)(0,5)
	// (1,0)(1,6)
	// (3,0)(3,6)
	// (5,0)(5,6)
	// (6,1)(6,3)(6,5)

	
	public MoveMessageType doTurn(BoardType bt, TreasuresToGoType ttg) {
		Board original=null;// = new Board(bt, ttg,0);
		Board modified;
		Bewerter bewerter;
		Card card = original.getShiftCard();
		for (int i = 0; i < original.getCards().length; i += 6) {
			for (int j = 1; j < original.getCards()[i].length; j += 2) {
				modified = new Board(original);
				bewerter = new Bewerter(modified, card, ttg);
				// einschieben an den Positionen(0,1)(0,3)(0,5) und
				// (6,1)(6,3)(6,5)
				modified = shift(original, card, i, j);

				// bearbeiten TODO

				// einschieben an den Positionen (1,0)(1,6), (3,0)(3,6) und
				// (5,0)(5,6)
				modified = shift(original, card, j, i);
			}

		}

		return null;
	}

	@Override
	public String getName() {
		return this.name;
	}

	private static class Bewerter {

		private Board board;
		private Card shiftCard;
		private TreasuresToGoType treasure;

		public Bewerter(Board b, Card c, TreasuresToGoType ttg) {
			board = new Board(b);
			shiftCard = c;
			treasure = ttg;
		}

		public int bewerten(PositionType toGo, PositionType shiftPosition) {

			return 0;
		}
	}

	@Override
	public MoveMessageType doTurn(Board bt,
			Map<Integer, Integer> idHasNTreasuresleft) {
		// TODO Auto-generated method stub
		return null;
	}

}
