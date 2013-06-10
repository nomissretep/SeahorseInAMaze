package spieler;

import generated.AwaitMoveMessageType;
import generated.MoveMessageType;

public interface ISpieler {
	public MoveMessageType doTurn(AwaitMoveMessageType awaitMoveMessageType);
	public void setId(int id);
	public String getName();
}
