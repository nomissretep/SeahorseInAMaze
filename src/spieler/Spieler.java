package spieler;

import generated.BoardType;
import generated.MoveMessageType;

public interface Spieler {
	public MoveMessageType doTurn(BoardType bt);
	public String getName();
	
}
