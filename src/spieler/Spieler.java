package spieler;

import generated.AwaitMoveMessageType;
import generated.MoveMessageType;
import generated.TreasureType;
import generated.TreasuresToGoType;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import ourGenerated.Board;
import ourGenerated.Position;

public abstract class Spieler implements ISpieler {
	public abstract MoveMessageType doTurn(Board bt,
			Map<Integer, Integer> idHasNTreasuresleft);

	@Override
	public abstract String getName();

	protected int id;

	@Override
	public void setId(int id) {
		this.lastIdHasNTreasuresleft = null;
		this.id = id;
	}

	private Map<Integer, Integer> lastIdHasNTreasuresleft = null;
	protected List<TreasureType> alreadyFoundTreasures = new LinkedList<TreasureType>();

	@Override
	public MoveMessageType doTurn(AwaitMoveMessageType awaitMoveMessageType) {
		Board board = new Board(awaitMoveMessageType.getBoard(),
				awaitMoveMessageType.getTreasure(), this.id);
		board.outputPretty();
		TreeMap<Integer, Integer> idHasNTreasuresleft = new TreeMap<Integer, Integer>();
		for (TreasuresToGoType ttgt : awaitMoveMessageType.getTreasuresToGo()) {
			idHasNTreasuresleft.put(ttgt.getPlayer(), ttgt.getTreasures());
			if (this.lastIdHasNTreasuresleft != null) {
				if (ttgt.getTreasures() < this.lastIdHasNTreasuresleft.get(ttgt
						.getPlayer())) {
					// Spieler hat einen Schatz gefunden.
					Position spielerPos = board.getSpielerPositions().get(
							ttgt.getPlayer());
					// Nur eine Bewegung Pro Runde => Er steht jetzt gerade auf
					// dem Schatz
					this.alreadyFoundTreasures
							.add(board.getCards()[spielerPos.y][spielerPos.x]
									.getTreasure());
				}
			}
		}
		this.lastIdHasNTreasuresleft = idHasNTreasuresleft;
		MoveMessageType moveMessage = this.doTurn(board, idHasNTreasuresleft);
		if (moveMessage.getNewPinPos() == null) {
			throw new RuntimeException("KI did not set new pin pos!");
		}
		return moveMessage;
	}

}
