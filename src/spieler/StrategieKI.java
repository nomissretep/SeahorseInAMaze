package spieler;

import generated.MoveMessageType;

import java.util.Map;

import client.types.IllegalTurnException;

import assessment.High;
import assessment.IStrategie;

import ourGenerated.Board;
import ourGenerated.Card;
import ourGenerated.Position;

public class StrategieKI extends Spieler{

	IStrategie strat;
	public StrategieKI(IStrategie strat) {
		this.strat = strat;
	}
	@Override
	public MoveMessageType doTurn(Board bt,
			Map<Integer, Integer> idHasNTreasuresleft) {
		Card c = bt.getShiftCard();
		for (int rotation = 0; rotation < 4; ++rotation) {
			for(int i = 5; i > 0; i-=2) {
				tryShift(bt, new Position(i, 0), c, rotation);
				tryShift(bt, new Position(i, 6), c, rotation);
				tryShift(bt, new Position(0, i), c, rotation);
				tryShift(bt, new Position(6, i), c, rotation);
			}
			c.turnCounterClockwise(1);
		}
		return null;
	}

	private void tryShift(Board bt, Position shiftPos, Card c,
			int rotation) {
		Board shiftetBoard;
		try {
			shiftetBoard = bt.shift(shiftPos, c);
		} catch (IllegalTurnException e) {
			return;
		}
		High h = strat.bewerte(shiftetBoard);
		
	}
	@Override
	public String getName() {
		return "Strategic Seahorse";
	}


}
