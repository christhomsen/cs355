package lab1;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon;
import java.util.ArrayList;

import cs355.ViewRefresher;

public class ViewFresher implements ViewRefresher
{
	
	private static ViewFresher instance;
	private Shapes shapes = Shapes.inst();
    
    public static ViewFresher inst()
    {
        if (instance == null)
        {
            instance = new ViewFresher();
        }
        return instance;
    }

	@Override
	public void refreshView(Graphics2D g2d)
	{
		ArrayList<Shape> shapeList = (ArrayList<Shape>) shapes.getShapes();
		for(int i =0; i < shapeList.size(); i ++)
		{
			String temp = shapeList.get(i).getClass().getSimpleName();
			switch (temp)
			{
				case "Line":
					Point start = ((Line) shapeList.get(i)).getStartPoint();
					Point end = ((Line) shapeList.get(i)).getEndPoint();
					g2d.setColor(shapeList.get(i).getColor());
					g2d.drawLine(start.x, start.y, end.x, end.y);
					break;
				case "Rectangle":
					Point corner = ((Rectangle) shapeList.get(i)).getCorner();
					int height = ((Rectangle) shapeList.get(i)).getHeight();
					int width = ((Rectangle) shapeList.get(i)).getWidth();
					g2d.setColor(shapeList.get(i).getColor());
					g2d.fillRect(corner.x, corner.y, width, height);
					break;
				case "Square":
					corner = ((Square) shapeList.get(i)).getCorner();
					height = ((Square) shapeList.get(i)).getHeight();
					width = ((Square) shapeList.get(i)).getWidth();
					g2d.setColor(shapeList.get(i).getColor());
					g2d.fillRect(corner.x, corner.y, width, height);
					break;
				case "Ellipse":
					Point center = ((Ellipse) shapeList.get(i)).getCenter();
					height = ((Ellipse) shapeList.get(i)).getHeight();
					width = ((Ellipse) shapeList.get(i)).getWidth();
					center = new Point(center.x - width/2, center.y - height/2);
					g2d.setColor(shapeList.get(i).getColor());
					g2d.fillOval(center.x, center.y, width, height);
					break;
				case "Circle":
					center = ((Circle) shapeList.get(i)).getCenter();
					height = ((Circle) shapeList.get(i)).getHeight();
					width = ((Circle) shapeList.get(i)).getWidth();
					center = new Point(center.x - width/2, center.y - height/2);
					g2d.setColor(shapeList.get(i).getColor());
					g2d.fillOval(center.x, center.y, width, height);
					break;
				case "Triangle":
					int[] x = new int[3];
					int[] y = new int[3];
					x[0] = ((Triangle) shapeList.get(i)).getFirstPoint().x;
					y[0] = ((Triangle) shapeList.get(i)).getFirstPoint().y;
					x[1] = ((Triangle) shapeList.get(i)).getSecPoint().x;
					y[1] = ((Triangle) shapeList.get(i)).getSecPoint().y;
					x[2] = ((Triangle) shapeList.get(i)).getThirdPoint().x;
					y[2] = ((Triangle) shapeList.get(i)).getThirdPoint().y;
					Polygon triangle = new Polygon(x, y, 3);
					g2d.setColor(shapeList.get(i).getColor());
					g2d.fillPolygon(triangle);
					break;
			}
		}
	}

}
