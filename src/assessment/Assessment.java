package assessment;


import ourGenerated.*;
import generated.PositionType;
import generated.TreasureType;

public class Assessment {
	
	Board board;
	
	
	
	Assessment(Board karte) {		
		board=karte;	
	}	
	
	public int[][] Weights(Position pos)
	{
		int x=pos.x;
		int y=pos.y;
		int[][] weights=new int[7][7];
		int iw;
		int j;
		for(int i=0;i<7;i++){
			iw=(i-x)*(i-x);
			for(j=0;j<7;j++)
				weights[i][j]=iw+(j-y)*(j-y);
		}
		return weights;
	}
	
	public boolean[][] findTreasures(Board b)
	{
		Card[][] Cards=b.getCards();
		boolean karten[][]=new boolean[7][7];
		for(int i=0;i<7;i++)
			for(int j=0;j<7;j++)
				if(Cards[i][j].getTreasure().value().contains("sym"))
					karten[i][j]=true;				
		return karten;
	}
	
	
	public boolean[][] WhereICanGo(Position pos)
	{
		boolean[][] marked=new boolean[7][7];
		WhereToGo(board.getCards(),pos,marked);
		return marked;
	}
	
	
	public boolean[][] WhereICanGo()
	{
		boolean[][] marked=new boolean[7][7];
		WhereToGo(board.getCards(),board.getMyPosition(),marked);
		return marked;
	}
	
	private void WhereToGo(Card[][] Cards,Position pos,boolean marked[][])
	{
		int x=pos.x;
		int y=pos.y;
		marked[x][y]=true;
		if(x<8&&Cards[x+1][y].getOpenings()[0]&&!marked[x+1][y]){
			x++;		
			WhereToGo(Cards,pos,marked);
			x--;
		}
		if(x>=0&&Cards[x-1][y].getOpenings()[2]&&!marked[x][y]){
			x--;		
			WhereToGo(Cards,pos,marked);
			x++;
		}
			
		if(y<8&&Cards[x][y+1].getOpenings()[1]&&!marked[x][y+1]){
			y++;		
			WhereToGo(Cards,pos,marked);
			y--;
		}
				
		if(y>=0&&Cards[x][y-1].getOpenings()[3]&&!marked[x][y-1])
		{
			y--;		
			WhereToGo(Cards,pos,marked);
			x++;
		}
	}
	
}

