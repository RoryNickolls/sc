package com.nickolls.sc05;

public class Hypocycloid {
	
	private float fixedCircleRadius, movingCircleRadius;
	private float penOffset;
	
	public Hypocycloid(float fixedCircleRadius, float movingCircleRadius, float penOffset)
	{
		this.fixedCircleRadius = fixedCircleRadius;
		this.movingCircleRadius = movingCircleRadius;
		this.penOffset = penOffset;
	}
	
	public double getPenPosX(double t)
	{
		return ((fixedCircleRadius + movingCircleRadius) * Math.cos(t)
				- (movingCircleRadius + penOffset) * Math.cos(((fixedCircleRadius + movingCircleRadius) / movingCircleRadius) * t));
	}
	
	public double getPenPosY(double t)
	{
		return ((fixedCircleRadius + movingCircleRadius) * Math.sin(t)
				- (movingCircleRadius + penOffset) * Math.sin(((fixedCircleRadius + movingCircleRadius) / movingCircleRadius) * t));
	}
	
	public float getPenOffset()
	{
		return this.penOffset;
	}
	
	public float getFixedCircleRadius()
	{
		return this.fixedCircleRadius;
	}
	
	public float getMovingCircleRadius()
	{
		return this.movingCircleRadius;
	}
	
	public void setPenOffset(float penOffset)
	{
		this.penOffset = penOffset;
	}
	
	public void setFixedCircleRadius(float fixedCircleRadius)
	{
		this.fixedCircleRadius = fixedCircleRadius;
	}
	
	public void setMovingCircleRadius(float movingCircleRadius)
	{
		this.movingCircleRadius = movingCircleRadius;
	}

}
