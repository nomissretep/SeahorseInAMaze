package client.types;

public class IllegalTurnException extends Exception {

	private static final long serialVersionUID = -9023142527742687298L;

	public IllegalTurnException(String name) {
		super(name);
	}

}
