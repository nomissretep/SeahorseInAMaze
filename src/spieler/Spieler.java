package spieler;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import ourGenerated.Board;
import ourGenerated.Card;
import ourGenerated.Position;
import generated.AwaitMoveMessageType;
import generated.BoardType;
import generated.MoveMessageType;
import generated.TreasureType;
import generated.TreasuresToGoType;


public abstract class Spieler implements ISpieler{
	public abstract MoveMessageType doTurn(Board bt, Map<Integer, Integer> idHasNTreasuresleft);
	public abstract String getName();
	protected int id;
	
	
	public void setId(int id){
		lastIdHasNTreasuresleft = null;
		this.id=id;
	}
	
	private Map<Integer, Integer> lastIdHasNTreasuresleft = null;
	protected List<TreasureType> alreadyFoundTreasures = new LinkedList<TreasureType>();
	@Override
	public MoveMessageType doTurn(AwaitMoveMessageType awaitMoveMessageType) {
		Board board = new Board(awaitMoveMessageType.getBoard(), awaitMoveMessageType.getTreasure(), this.id);
		TreeMap<Integer, Integer> idHasNTreasuresleft = new TreeMap<Integer, Integer>();
		for(TreasuresToGoType ttgt: awaitMoveMessageType.getTreasuresToGo()) {
			idHasNTreasuresleft.put(ttgt.getPlayer(), ttgt.getTreasures());
			if(lastIdHasNTreasuresleft != null) {
				if(ttgt.getTreasures() < lastIdHasNTreasuresleft.get(ttgt.getPlayer())) {
					//Spieler hat einen Schatz gefunden.
					Position spielerPos = board.getSpielerPositions().get(ttgt.getPlayer());
					//Nur eine Bewegung Pro Runde => Er steht jetzt gerade auf dem Schatz
					alreadyFoundTreasures.add(board.getCards()[spielerPos.y][spielerPos.x].getTreasure()); 
				}
			}
		}
		lastIdHasNTreasuresleft = idHasNTreasuresleft;
		MoveMessageType moveMessage = doTurn(board, idHasNTreasuresleft);
		if(moveMessage.getNewPinPos() == null) {
			System.out.println("Don't want to move");
			moveMessage.setNewPinPos(board.getMyPosition().getPositionType());
		}
		return moveMessage;
	}
	
	
}
