package lab1;

import java.awt.Color;
import java.awt.Point;

public class Triangle extends Shape
{
	private Point firstPoint;
	private Point secPoint;
	private Point thirdPoint;
	
	public Triangle(Color color, Point first, Point sec, Point third)
	{
		super(color);
		this.firstPoint = first;
		this.secPoint = sec;
		this.thirdPoint = third;
	}
	
	public Point getFirstPoint()
	{
		return firstPoint;
	}

	public void setFirstPoint(Point firstPoint)
	{
		this.firstPoint = firstPoint;
	}

	public Point getSecPoint()
	{
		return secPoint;
	}

	public void setSecPoint(Point secPoint)
	{
		this.secPoint = secPoint;
	}

	public Point getThirdPoint()
	{
		return thirdPoint;
	}

	public void setThirdPoint(Point thriPoint)
	{
		this.thirdPoint = thriPoint;
	}
}
