package spieler;

import generated.MoveMessageType;
import generated.ObjectFactory;

import java.util.Map;

import client.types.IllegalTurnException;

import ourGenerated.Board;
import ourGenerated.Card;
import ourGenerated.Position;

public class MaximumDamageKI extends Spieler {

	double currentAverageMin;
	Position minShiftPosition;
	int minRotation;
	
	ObjectFactory obf = new ObjectFactory();
	@Override
	public MoveMessageType doTurn(Board bt,
			Map<Integer, Integer> idHasNTreasuresleft) {
		MoveMessageType mmt = obf.createMoveMessageType();
		currentAverageMin = Double.MAX_VALUE;

		Card c = bt.getShiftCard();
		for(int rotation = 0; rotation < 4; ++rotation) {
			for(int i = 5; i >= 0; i-=2) {
				tryShift(bt, true, 1, i, c, rotation);
				tryShift(bt, true, -1, i, c, rotation);
				tryShift(bt, false, 1, i, c, rotation);
				tryShift(bt, false, -1, i, c, rotation);
			}
			c.turnCounterClockwise(1);
		}
		
		c.turnCounterClockwise(minRotation);
		mmt.setShiftCard(c.getCardType());
		mmt.setShiftPosition(minShiftPosition.getPositionType());
		
		return mmt;
	}
	
	private void tryShift(Board b,boolean vertikal, int direction, int position, Card c, int rotation) {
		Board shiftetBoard;
		Position shiftPosition = getShiftPosition(vertikal, direction, position);
		if(b.isValidMove(shiftPosition, c)) {
			try {
				shiftetBoard = b.shift(shiftPosition, c);
			} catch (IllegalTurnException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return;
			}
		} else {
			System.out.println("Skipping one Impossible turn "+shiftPosition);
			return;
		}
		double average = calcAveragePossibleFields(shiftetBoard);
		if(average < currentAverageMin) {
			currentAverageMin = average;
			minShiftPosition = shiftPosition;
			minRotation = rotation;
			System.out.println("New Min: "+currentAverageMin + "\n"+minShiftPosition +":"+minRotation);
		}
	}
	
	private Position getShiftPosition(boolean vertikal, int direction, int position) {
		if(vertikal) {
			if(direction > 0) {
				return new Position(0, position); 
			} else {
				return new Position(6, position); 
			}
		} else {
			if(direction > 0) {
				return new Position(position, 0); 
			} else {
				return new Position(position, 6); 
			}
		}
	}
	private double calcAveragePossibleFields(Board bt) {
		Map<Integer, Position> spielerPosition = bt.getSpielerPositions();
		int possibleFieldsCurrent = 0;
		for(int spieler: spielerPosition.keySet()) {
			if(spieler != this.id) {
				possibleFieldsCurrent+=bt.getPossiblePositionsFromPosition(spielerPosition.get(spieler)).size();
			}
		}
		return possibleFieldsCurrent/spielerPosition.keySet().size(); 
	}
	@Override
	public String getName() {
		return "Annoying Seahorse";
	}

}
