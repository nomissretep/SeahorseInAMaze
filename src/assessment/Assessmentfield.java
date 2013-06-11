package assessment;

import ourGenerated.Position;

public class Assessmentfield {
	public static int[][] mult(boolean a[][],boolean b[][])
	{
		int j;
		int c[][]=new int[7][7];
		for(int i=0;i<7;i++)
			for(j=0;j<7;j++)
				if(a[i][j]&&b[i][j])
					c[i][j]=1;
		return c;
	}
	
	public static int[][] mult(boolean a[][],int b[][])
	{ return mult(a,b,1);}
	
	public static int[][] mult(boolean a[][],int b[][],int alpha)
	{
		int j;
		int c[][]=new int[7][7];
		for(int i=0;i<7;i++)
			for(j=0;j<7;j++)
				if(a[i][j])
					c[i][j]=alpha*b[i][j];
		return c;
	}
	
	public static int[][] mult(int a[][],int b[][],int alpha)
	{
		int j;
		int c[][]=new int[7][7];
		for(int i=0;i<7;i++)
			for(j=0;j<7;j++)
				c[i][j]=alpha*(b[i][j]*a[i][j]);
		return c;
	}
	
	
	public static int[][] add(int a[][],int b[][])
	{return add(a,b,1);}
	
	public static int[][] add(int a[][],int b[][],int alpha)
	{
		int j;
		int c[][]=new int[7][7];
		for(int i=0;i<7;i++)
			for(j=0;j<7;j++)
				c[i][j]=alpha*(b[i][j]+a[i][j]);
		return c;
	}
	


	
	
	
	public static int cound(boolean a[][])
	{
		int j;
		int c=0;
		for(int i=0;i<7;i++)
			for(j=0;j<7;j++)
				if(a[i][j])
					c++;
		return c;
	}
	
	public static int cound(int a[][])
	{
		int j;
		int c=0;
		for(int i=0;i<7;i++)
			for(j=0;j<7;j++)
				if(a[i][j]>0)
					c++;
		return c;
	}
	
	public High findHigh(int[][] field)
	{
		High high = new High();
		int j;
		for(int i=0;i<7;i++)
			for(j=0;j<7;j++)
				if(field[i][j]>high.value)
					high=new High(i,j,field[i][j]);
		return high;
	}
	
	public class High{
		public Position pos;
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
}
