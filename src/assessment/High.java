package assessment;

import ourGenerated.Position;

	public class High{
		public final Position pos;
		int value;
		
		High()
		{
			pos=new Position(0,0);
			this.value=0;
		}
		
		High(int x,int y, int value)
		{
			pos=new Position(x,y);
			this.value=value;
		}
	}

