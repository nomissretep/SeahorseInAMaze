package spieler.strategies;

import ourGenerated.Board;
import assessment.High;
import assessment.IStrategie;
import assessment.Strategie;

public class NearTheWayStrategie implements IStrategie {

	@Override
	public High bewerte(Board b) {
		int gegner[]={2,2,2};
		return Strategie.bewerte(b, 3, 15, 4, 2, gegner);
	}

}

