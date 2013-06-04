package client.types;

import generated.MazeCom;

public class RecievedWrongTypeException extends RuntimeException {
	private static final long serialVersionUID = 5854434582015518003L;
	MazeCom packet;
	public RecievedWrongTypeException(MazeCom packet) {
		this.packet = packet;
	}
	public MazeCom getFailPacket() {
		return packet;
	}
}
