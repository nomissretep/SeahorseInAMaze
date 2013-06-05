package spieler;

import generated.BoardType;
import generated.MoveMessageType;
import generated.ObjectFactory;

public class TestKI implements Spieler {

	ObjectFactory obf = new ObjectFactory();

	@Override
	public MoveMessageType doTurn(BoardType bt) {
		MoveMessageType mmt = this.obf.createMoveMessageType();
		return mmt;
	}

	@Override
	public String getName() {
		return "Testing Seahorse";
	}

}
