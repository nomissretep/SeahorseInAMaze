package assessment;


import java.util.ArrayList;

import ourGenerated.*;
import generated.PositionType;
import generated.TreasureType;

public class Assessment {
	
	Board board;
	
	
	
	public Assessment(Board karte) {		
		board=karte;	
	}	
	
	public int[][] weights(Position pos)//28-100
	{
		int x=pos.x;
		int y=pos.y;
		int[][] weights=new int[7][7];
		int iw;
		int x_;
		for(int y_=0;y_<7;y_++){
			iw=100-(y_-y)*(y_-y);
			for(x_=0;x_<7;x_++)
				weights[y_][x_]=iw-(x_-x)*(x_-x);
		}
		return weights;
	}
	
	
	public int[][] randWeights(Position pos)//28-100
	{
		int x=pos.x;
		int y=pos.y;
		int[][] weights=new int[7][7];
		int iw;
		int x_;
		for(int y_=1;y_<7;y_+=2){
			weights[y_][6]=(y_-y)*(y_-y)-(-1-x)*(-1-x);
			weights[y_][0]=(y_-y)*(y_-y)-(7-x)*(7-x);
	}
		for(x_=1;x_<7;x_+=2){
				weights[6][x_]=(-1-y)*(-1-y)-(x_-x)*(x_-x);
				weights[0][x_]=(7-y)*(7-y)-(x_-x)*(x_-x);
		}
		
		return weights;
	}
	
	public int[][] comboWeights(Position pos)//28-100
	{
		return Assessmentfield.higherField(randWeights(pos), weights(pos));
	}
	
	
	public boolean[][] findTreasures()
	{
		Card[][] Cards=board.getCards();
		boolean karten[][]=new boolean[7][7];
		for(int y=0;y<7;y++)
			for(int x=0;x<7;x++)
				if(Cards[y][x].getTreasure() != null)
					karten[y][x]=true;				
		return karten;
	}
	
	
	public boolean[][] whereToGo(Position pos)
	{
		boolean[][] marked=new boolean[7][7];
		whereToGo(board.getCards(),pos.y, pos.x,marked);
		return marked;
	}
	
	
	public boolean[][] whereICanGo()
	{
		ArrayList<Position> list=(ArrayList<Position>) board.getPossiblePositionsFromPosition(board.getMyPosition());
		 boolean marked[][]=new boolean[7][7];
		for(Position pos:list)
			marked[pos.y][pos.x]=true;
		return marked;
	}
	
	private void whereToGo(Card[][] Cards,int y, int x,boolean marked[][])
	{
		marked[y][x]=true;
		if(y<6&&Cards[y+1][x].getOpenings()[0]&&!marked[y+1][x]){
			y++;		
			whereToGo(Cards,y, x,marked);
			y--;
		}
		if(y>0&&Cards[y-1][x].getOpenings()[2]&&!marked[y][x]){
			y--;		
			whereToGo(Cards,y, x,marked);
			y++;
		}
			
		if(x<6&&Cards[y][x+1].getOpenings()[1]&&!marked[y][x+1]){
			x++;		
			whereToGo(Cards,y,x,marked);
			x--;
		}
				
		if(x>0&&Cards[y][x-1].getOpenings()[3]&&!marked[y][x-1])
		{
			x--;		
			whereToGo(Cards,y,x,marked);
			y++;
		}
	}
	
	
	//vorsicht sehr aufwendig
    public int[][] nearTheWay(boolean[][] marked,int umfeld){
    	Position pos=board.getMyPosition();
    	int stx,endx,sty,endy;
    	if(pos.x<umfeld)
    		stx=0;
    	else
    		stx=pos.x-umfeld;
    	if(pos.y<umfeld)
    		sty=0;
    	else
    		sty=pos.y-umfeld;
    	if(pos.x+umfeld<6)
    		endx=pos.x+umfeld;
    	else
    		endx=6;
    	if(pos.y+umfeld<6)
    		endy=pos.y+umfeld;
    	else
    		endy=6;
    	int[][] result=new int[7][7];
    	for(;stx<endx;stx++)
    		for(;sty<endy;sty++)
    			if(marked[stx][sty])
    				result=Assessmentfield.higherField(weights(new Position(stx,sty)),result);
    	
    	
    	return result;
    }
	
	
	
}

