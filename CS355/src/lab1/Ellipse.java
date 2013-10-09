package lab1;

import java.awt.Color;

public class Ellipse extends Shape
{
	private double height;
	private double width;
	
	public Ellipse(Color color, MyPoint center, double height, double width)
	{
		super(color, center, 0);
		this.height = height;
		this.width = width;
	}
	
	public double getHeight()
	{
		return height;
	}
	
	public void setHeight(double height)
	{
		this.height = height;
	}
	
	public double getWidth()
	{
		return width;
	}
	
	public void setWidth(double width)
	{
		this.width = width;
	}
}
