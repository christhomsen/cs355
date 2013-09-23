package lab1;

import java.awt.Color;
import java.awt.Point;

public class Ellipse extends Shape
{
	private Point center;
	private int height;
	private int width;
	
	public Ellipse(Color color, Point center, int height, int width)
	{
		super(color);
		this.center = center;
		this.height = height;
		this.width = width;
	}
	
	public Point getCenter()
	{
		return center;
	}
	public void setCenter(Point temp)
	{
		this.center = temp;
	}
	public int getHeight()
	{
		return height;
	}
	public void setHeight(int height)
	{
		this.height = height;
	}
	public int getWidth()
	{
		return width;
	}
	public void setWidth(int width)
	{
		this.width = width;
	}
}
