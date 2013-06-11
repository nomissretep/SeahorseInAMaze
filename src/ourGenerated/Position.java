package ourGenerated;

import generated.PositionType;

public class Position {

	public final int x;
	public final int y;

	public Position(PositionType p) {
		this.x = p.getCol();
		this.y = p.getRow();
	}

	@Override
	public boolean equals(Object o) {
		if (o instanceof Position) {
			Position other = (Position) o;
			return (this.x == other.x && this.y == other.y);
		}
		return false;
	}

	public Position(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public PositionType getPositionType() {
		PositionType pt = new PositionType();
		pt.setCol(this.x);
		pt.setRow(this.y);
		return pt;
	}

	@Override
	public String toString() {
		return String.format("(%d, %d)", this.x, this.y);
	}

}
