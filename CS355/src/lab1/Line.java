package lab1;

import java.awt.Color;

public class Line extends Shape 
{
	private MyPoint endPoint;
	
	public Line(Color color, MyPoint startPoint, MyPoint endPoint)
	{
		super(color, startPoint, 0);
		this.endPoint = endPoint;
	}
	
	public MyPoint getEndPoint() 
	{
		return endPoint;
	}
	
	public void setEndPoint(MyPoint endPoint) 
	{
		this.endPoint = endPoint;
	}
}
