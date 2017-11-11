package sc06;

public class Circle {
	
	private int x, y;
	private int voteCount = 0;
	private int radius;
	
	public Circle(int x, int y, int radius, int voteCount)
	{
		this.x = x;
		this.y = y;
		this.radius = radius;
		this.voteCount = voteCount;
	}
	
	public int getX()
	{
		return this.x;
	}
	
	public int getY()
	{
		return this.y;
	}
	
	public int getRadius()
	{
		return this.radius;
	}
	
	public int getVoteCount()
	{
		return this.voteCount;
	}
}
