package lab1;

import java.awt.geom.Point2D;

public class MyPoint extends Point2D
{
	double x;
	double y;
	
	public MyPoint(double x, double y)
	{
		setLocation(x, y);
	}
	
	public MyPoint(){}

	@Override
	public double getX()
	{
		return x;
	}

	@Override
	public double getY()
	{
		return y;
	}

	@Override
	public void setLocation(double arg0, double arg1)
	{
		this.x = arg0;
		this.y = arg1;
	}

	public double dotProduct(MyPoint dotProduct)
	{
		return 0;
	}
	
	public MyPoint subtract(MyPoint sub)
	{
		MyPoint temp = new MyPoint(x - sub.x, y - sub.y);
		return temp;
	}
	
	public MyPoint add(MyPoint add)
	{
		MyPoint temp = new MyPoint(add.x + x, add.y + y);
		return temp;
	}
	
	public MyPoint multiply(double value)
	{
		MyPoint temp = new MyPoint(value * x, value * y);
		return temp;
	}
}
