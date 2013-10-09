package lab1;

import java.awt.Color;

public class Triangle extends Shape
{
	private MyPoint firstPoint;
	private MyPoint secPoint;
	private MyPoint thirdPoint;
	
	public Triangle(Color color, MyPoint first, MyPoint sec, MyPoint third)
	{
		super(color, new MyPoint((first.x + sec.x + third.x)/3, (first.y + sec.y + third.y)/3), 0);
		/*this.firstPoint = first;
		this.secPoint = sec;
		this.thirdPoint = third;*/
		MyPoint temp = new MyPoint((first.x + sec.x + third.x)/3, (first.y + sec.y + third.y)/3);
		this.firstPoint = new MyPoint(first.x - temp.x, first.y - temp.y);
		this.secPoint = new MyPoint(sec.x - temp.x, sec.y - temp.y);
		this.thirdPoint = new MyPoint(third.x - temp.x, third.y - temp.y);
	}
	
	public MyPoint getFirstPoint()
	{
		return firstPoint;
	}

	public void setFirstPoint(MyPoint firstPoint)
	{
		MyPoint tmp1 = this.secPoint.add(getCenter());
		MyPoint tmp2 = this.thirdPoint.add(getCenter());
		this.setCenter(new MyPoint((tmp1.x + tmp2.x + firstPoint.x)/3, (tmp1.y + tmp2.y +firstPoint.y)/3));
		this.firstPoint = firstPoint.subtract(getCenter());
		this.secPoint = tmp1.subtract(getCenter());
		this.thirdPoint = tmp2.subtract(getCenter());
	}

	public MyPoint getSecPoint()
	{
		return secPoint;
	}

	public void setSecPoint(MyPoint secPoint)
	{
		MyPoint tmp1 = this.firstPoint.add(getCenter());
		MyPoint tmp2 = this.thirdPoint.add(getCenter());
		this.setCenter(new MyPoint((tmp1.x + tmp2.x + secPoint.x)/3, (tmp1.y + tmp2.y +secPoint.y)/3));
		this.firstPoint = tmp1.subtract(getCenter());
		this.secPoint = secPoint.subtract(getCenter());
		this.thirdPoint = tmp2.subtract(getCenter());
	}

	public MyPoint getThirdPoint()
	{
		return thirdPoint;
	}

	public void setThirdPoint(MyPoint thriPoint)
	{
		MyPoint tmp1 = this.firstPoint.add(getCenter());
		MyPoint tmp2 = this.secPoint.add(getCenter());
		this.setCenter(new MyPoint((tmp1.x + tmp2.x + thriPoint.x)/3, (tmp1.y + tmp2.y +thriPoint.y)/3));
		this.firstPoint = tmp1.subtract(getCenter());
		this.secPoint = tmp2.subtract(getCenter());
		this.thirdPoint = thriPoint.subtract(getCenter());
	}
}
