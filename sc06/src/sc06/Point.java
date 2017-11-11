package sc06;

public class Point {
	
	private int x, y;
	private int vote;
	
	public Point(int x, int y)
	{
		this.x = x;
		this.y = y;
		this.vote = 1;
	}
	
	public int getX()
	{
		return this.x;
	}
	
	public int getY()
	{
		return this.y;
	}
	
	public int getVote()
	{
		return this.vote;
	}
	
	public void addVote()
	{
		this.vote++;
	}

}
