package lab1;

import java.awt.Color;
import java.awt.Point;

public class Circle extends Ellipse
{
	public Circle(Color color, Point center, int radius)
	{
		super(color, center, radius, radius);
	}
}
