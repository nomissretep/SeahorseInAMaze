package assessment;


import ourGenerated.Board;
import ourGenerated.Card;
import ourGenerated.Position;

public class Assessment {
	
	Board board;
	Card[][] cards;
	
	
	public Assessment(Board karte) {		
		board=karte;
		this.cards = board.getCards();
	}	
	
	public int[][] weights(Position pos)//28-100
	{
		int x=pos.x;
		int y=pos.y;
		int[][] weights=new int[7][7];
		int iw;
		int x_;
		for(int y_=0;y_<7;y_++){
			iw=100-(y_-x)*(y_-x);
			for(x_=0;x_<7;x_++)
				weights[y_][x_]=iw-(x_-y)*(x_-y);
		}
		return weights;
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
	
	
	public boolean[][] whereToGo(Position position)
	{
		//canVisit is actually used as a Queue. 
		int canVisit[] = new int[7 * 7];
		int canVisitSize = 0;
		int haveRevisited = 0;
		int currentIndex;
		canVisit[canVisitSize++] = position.y * 7 + position.x;
		int x, y;
		boolean[] currentCardOpenings;
		boolean[] visited = new boolean[7 * 7];
		visited[position.y * 7 + position.x] = true;

		while (haveRevisited < canVisitSize) {
			currentIndex = canVisit[haveRevisited++];
			x = currentIndex % 7;
			y = currentIndex / 7;
			currentCardOpenings = this.cards[y][x].openings;

			if (y > 0 && !visited[currentIndex - 7] && currentCardOpenings[0]
					&& this.cards[y - 1][x].openings[2]) { // Oben
				canVisit[canVisitSize++] = (currentIndex - 7);
				visited[currentIndex - 7] = true;
			}

			if (x < 6 && !visited[currentIndex + 1]	&& currentCardOpenings[1]
					&& this.cards[y][x + 1].openings[3]) { // Rechts
				canVisit[canVisitSize++] = (currentIndex + 1);
				visited[currentIndex + 1] = true;
			}

			if (y < 6 && !visited[currentIndex + 7]	&& currentCardOpenings[2]
					&& this.cards[y + 1][x].openings[0]) { // Unten
				canVisit[canVisitSize++] = (currentIndex + 7);
				visited[currentIndex + 7] = true;
			}

			if (x > 0 && !visited[currentIndex - 1] && currentCardOpenings[3]
					&& this.cards[y][x - 1].openings[1]) { // Links
				canVisit[canVisitSize++] = currentIndex - 1;
				visited[currentIndex - 1] = true;
			}
		}
		boolean[][] whereToGo = new boolean[7][7];
		for (int i = canVisitSize - 1; i >= 0; --i) {
			whereToGo[canVisit[i] / 7][canVisit[i] % 7] = true;
		}
		return whereToGo;
	}
	
	
	public boolean[][] whereICanGo()
	{
		return whereToGo(board.getMyPosition());
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

