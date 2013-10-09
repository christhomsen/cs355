package lab1;

import java.awt.Color;

public class Circle extends Ellipse
{
	public Circle(Color color, MyPoint center, double radius)
	{
		super(color, center, radius, radius);
	}
}
