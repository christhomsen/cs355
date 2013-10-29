package lab1;

import java.awt.Color;
import java.awt.geom.AffineTransform;

public abstract class Shape
{
	private Color color;
	private MyPoint center;
	private double rotation;

	public Shape(Color color, MyPoint center, double rotation)
	{
		this.color = color;
		this.center = center;
		this.rotation = rotation;
	}
	
	public double getRotation()
	{
		return rotation;
	}

	public void setRotation(double rotation)
	{
		this.rotation = rotation;
	}

	public MyPoint getCenter()
	{
		return center;
	}

	public void setCenter(MyPoint center)
	{
		this.center = center;
	}

	public Color getColor() 
	{
		return color;
	}

	public void setColor(Color color)
	{
		this.color = color;
	}
	
	public AffineTransform objectToWorld()
	{
		AffineTransform affine = new AffineTransform(Math.cos(rotation), Math.sin(rotation), 
				-Math.sin(rotation), Math.cos(rotation), center.x, center.y);
		return affine;
	}
}
