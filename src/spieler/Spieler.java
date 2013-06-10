package spieler;

import java.util.Map;
import java.util.TreeMap;

import ourGenerated.Board;
import generated.AwaitMoveMessageType;
import generated.BoardType;
import generated.MoveMessageType;
import generated.TreasuresToGoType;


public abstract class Spieler implements ISpieler{
	public abstract MoveMessageType doTurn(Board bt, Map<Integer, Integer> idHasNTreasuresleft);
	public abstract String getName();
	protected int id;
	
	
	public void setId(int id){
		this.id=id;
	}
	@Override
	public MoveMessageType doTurn(AwaitMoveMessageType awaitMoveMessageType) {
		TreeMap<Integer, Integer> idHasNTreasuresleft = new TreeMap<Integer, Integer>();
		for(TreasuresToGoType ttgt: awaitMoveMessageType.getTreasuresToGo()) {
			idHasNTreasuresleft.put(ttgt.getPlayer(), ttgt.getTreasures());
		}
		return doTurn(new Board(awaitMoveMessageType.getBoard(), awaitMoveMessageType.getTreasure(), this.id), idHasNTreasuresleft);
	}
	
	
}
