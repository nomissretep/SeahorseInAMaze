package assessment;

import ourGenerated.Board;
import ourGenerated.Position;

public class Strategie {

	High high;
	Strategie(Board board)
	{
		Assessment as=new Assessment(board);
		boolean t[][]=as.findTreasures();		
		boolean[][] iGo=as.whereICanGo();
		boolean[][] tWay=as.whereToGo(board.getTreasurePosition());
		int [][]tWeights=as.weights(board.getTreasurePosition());//28-100
		int [][]tWayWeigths=as.nearTheWay(tWay, 1);
		
		int[][]tadd=Assessmentfield.add(tWayWeigths, tWeights, 10);
		int[][]tposible=Assessmentfield.mult(iGo,tadd);
		High h=Assessmentfield.findHigh(tposible);
		
		
		
		int filds=Assessmentfield.cound(iGo);
		int traeses=Assessmentfield.cound(Assessmentfield.and(iGo,t));
		
		h.value+=filds+10*traeses;
		
		Position gegner=board.getSpielerPositions().values().iterator().next();
		
		boolean[][] gway=as.whereToGo(gegner);
		int gtraeses=Assessmentfield.cound(Assessmentfield.and(gway,t));
		h.value=h.value*10+(24-gtraeses);
		
		
	}
	
	public int getValue()
	{return high.value;}
	public Position getPosition()
	{return high.pos;}
}
