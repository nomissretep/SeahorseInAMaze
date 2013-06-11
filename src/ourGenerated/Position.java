package ourGenerated;

import generated.PositionType;


public class Position {
	
	public final int x;
	public final int y;
	
	public Position(PositionType p) {
		x=p.getCol();
		y=p.getRow();
	}
	
	public boolean equals(Object o){
		if(o instanceof Position){
			Position other=(Position)o;
			return(this.x==other.x && this.y==other.y);
		}
		return false;
	}
	public Position(int x, int y){
		this.x=x;
		this.y=y;
	}
	public PositionType getPositionType(){
		PositionType pt = new PositionType();
		pt.setCol(x);
		pt.setRow(y);
		return pt;
	}

	
	public String toString() {
		return String.format("(%d, %d)",x,y);
	}

}
