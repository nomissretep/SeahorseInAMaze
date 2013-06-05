package client.types;

import generated.WinMessageType;

public class GameHasEndedException extends Exception {
	private static final long serialVersionUID = -1652185360416662244L;
	protected WinMessageType msg;

	public GameHasEndedException(WinMessageType msg) {
		this.msg = msg;
	}

	public WinMessageType getWinMessage() {
		return this.msg;
	}
}
