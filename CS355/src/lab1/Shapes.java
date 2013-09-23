package lab1;

import java.util.ArrayList;
import java.util.List;

public class Shapes
{
	private static Shapes instance;
	private ArrayList<Shape> shapes = new ArrayList<Shape>();
    
    public static Shapes inst()
    {
        if (instance == null)
        {
            instance = new Shapes();
        }
        return instance;
    }
    
    public int addShape(Shape shape)
    {
    	shapes.add(shape);
    	return shapes.indexOf(shape);
    }
    
    public Shape getShape(int index)
    {
    	return shapes.get(index);
    }
    
    public List<Shape> getShapes()
    {
    	return shapes;
    }
}
