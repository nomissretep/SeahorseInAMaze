package spieler;

import generated.BoardType;
import generated.MoveMessageType;

public abstract class Spieler {
	public abstract MoveMessageType doTurn(BoardType bt);
	public abstract String getName();
	protected int id;
	public Spieler(int id){
		this.id=id;
	}
	
}
