package lab1;

public class Handle
{
	Shape shape;
	int vert;
	public Handle(Shape shape, int vert)
	{
		this.shape = shape;
		this.vert = vert;
	}
	public Shape getShape()
	{
		return shape;
	}
	public void setShape(Shape shape)
	{
		this.shape = shape;
	}
	public int getVert()
	{
		return vert;
	}
	public void setVert(int vert)
	{
		this.vert = vert;
	}
}
