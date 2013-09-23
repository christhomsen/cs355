package lab1;

import java.awt.Color;
import java.awt.Point;

public class Rectangle extends Shape
{
	private Point corner;
	private int height;
	private int width;
	
	public Rectangle(Color color, Point corner, int height, int width)
	{
		super(color);
		this.corner = corner;
		this.height = height;
		this.width = width;
	}

	public Point getCorner()
	{
		return corner;
	}

	public void setCorner(Point corner)
	{
		this.corner = corner;
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
