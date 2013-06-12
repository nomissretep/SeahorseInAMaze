package assessment;

import ourGenerated.Board;

public class Strategie {

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
		
		h.value+=10*filds+traeses;
		
		
	}
	
}
