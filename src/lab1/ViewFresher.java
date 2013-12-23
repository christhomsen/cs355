package lab1;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.List;

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
		List<Shape> shapeList = (ArrayList<Shape>) shapes.getShapes();
		for(int i =0; i < shapeList.size(); i ++)
		{
			String temp = shapeList.get(i).getClass().getSimpleName();
			double zoom = Controller.inst().getZoom();
			AffineTransform a = new AffineTransform(zoom, 0, 0, zoom, -Controller.inst().getHorizontal()/* * zoom*/, -Controller.inst().getVertical()/* * zoom*/);
			AffineTransform b = new AffineTransform();
			switch (temp)
			{
				case "Line":
					MyPoint start = ((Line) shapeList.get(i)).getCenter();
					MyPoint end = ((Line) shapeList.get(i)).getEndPoint();
					g2d.setColor(shapeList.get(i).getColor());
					b.concatenate(a);
					g2d.setTransform(b);
					g2d.drawLine((int)start.x, (int)start.y, (int)end.x, (int)end.y);
					break;
				case "Rectangle":
					
					MyPoint corner = ((Rectangle) shapeList.get(i)).getCenter();
					int height = (int) ((Rectangle) shapeList.get(i)).getHeight();
					int width = (int) ((Rectangle) shapeList.get(i)).getWidth();
					corner = new MyPoint(-width/2, -height/2);
					g2d.setColor(shapeList.get(i).getColor());
					b = shapeList.get(i).objectToWorld();
					b.concatenate(a);
					g2d.setTransform(b);
					g2d.fillRect((int)corner.x, (int)corner.y, width, height);
					g2d.setTransform(new AffineTransform());
					break;
				case "Square":
					corner = ((Square) shapeList.get(i)).getCenter();
					height = (int) ((Square) shapeList.get(i)).getHeight();
					width = (int) ((Square) shapeList.get(i)).getWidth();
					corner = new MyPoint(-width/2, -height/2);
					g2d.setColor(shapeList.get(i).getColor());
					b = shapeList.get(i).objectToWorld();
					b.concatenate(a);
					g2d.setTransform(b);
					g2d.fillRect((int)corner.x, (int)corner.y, width, height);
					g2d.setTransform(new AffineTransform());
					break;
				case "Ellipse":
					corner = ((Ellipse) shapeList.get(i)).getCenter();
					height = (int) ((Ellipse) shapeList.get(i)).getHeight();
					width = (int) ((Ellipse) shapeList.get(i)).getWidth();
					corner = new MyPoint(-width/2, -height/2);
					g2d.setColor(shapeList.get(i).getColor());
					b = shapeList.get(i).objectToWorld();
					b.concatenate(a);
					g2d.setTransform(b);
					g2d.fillOval((int)corner.x, (int)corner.y, width, height);
					g2d.setTransform(new AffineTransform());
					break;
				case "Circle":
					corner = ((Circle) shapeList.get(i)).getCenter();
					height = (int) ((Circle) shapeList.get(i)).getHeight();
					width = (int) ((Circle) shapeList.get(i)).getWidth();
					corner = new MyPoint(-width/2, -height/2);
					g2d.setColor(shapeList.get(i).getColor());
					b = shapeList.get(i).objectToWorld();
					b.concatenate(a);
					g2d.setTransform(b);
					g2d.fillOval((int)corner.x, (int)corner.y, width, height);
					g2d.setTransform(new AffineTransform());
					break;
				case "Triangle":
					int[] x = new int[3];
					int[] y = new int[3];
					x[0] = (int) (((Triangle) shapeList.get(i)).getFirstPoint().x);
					y[0] = (int) (((Triangle) shapeList.get(i)).getFirstPoint().y);
					x[1] = (int) (((Triangle) shapeList.get(i)).getSecPoint().x);
					y[1] = (int) (((Triangle) shapeList.get(i)).getSecPoint().y);
					x[2] = (int) (((Triangle) shapeList.get(i)).getThirdPoint().x);
					y[2] = (int) (((Triangle) shapeList.get(i)).getThirdPoint().y);
					Polygon triangle = new Polygon(x, y, 3);
					g2d.setColor(shapeList.get(i).getColor());
					b = shapeList.get(i).objectToWorld();
					b.concatenate(a);
					g2d.setTransform(b);
					g2d.fillPolygon(triangle);
					g2d.setTransform(new AffineTransform());
					break;
			}
		}
		
		if(Controller.inst().isShapeSelected())
		{
			Shape crrntShape = Controller.inst().getCurrentShape();
			List<Handle> handles = Controller.inst().getHandles();
			String temp = crrntShape.getClass().getSimpleName();
			List<MyPoint> points = new ArrayList<MyPoint>();
			double shapeHeight = 0;
			MyPoint pointHeight = new MyPoint();
			Color color = null;
			double zoom = Controller.inst().getZoom();
			AffineTransform a = new AffineTransform(zoom, 0, 0, zoom, -Controller.inst().getHorizontal()/* * zoom*/, -Controller.inst().getVertical()/* * zoom*/);
			AffineTransform b = new AffineTransform();
			
			if(crrntShape.getColor() != Color.WHITE)
				color = new Color(255 - crrntShape.getColor().getRGB());
			else
				color = Color.GREEN;
			
			switch (temp)
			{
				case "Line":
					break;
				case "Rectangle":
					MyPoint corner = ((Rectangle) crrntShape).getCenter();
					int height = (int) ((Rectangle) crrntShape).getHeight();
					int width = (int) ((Rectangle) crrntShape).getWidth();
					corner = new MyPoint(-width/2, -height/2);
					b = crrntShape.objectToWorld();
					b.concatenate(a);
					g2d.setTransform(b);
					g2d.setColor(color);
					g2d.drawRect((int)corner.x, (int)corner.y, width, height);
					g2d.setTransform(new AffineTransform());
					shapeHeight = height/2;
					points.add(new MyPoint(corner.x, corner.y));
					points.add(new MyPoint(corner.x, corner.y + height));
					points.add(new MyPoint(corner.x + width, corner.y));
					points.add(new MyPoint(corner.x + width, corner.y + height));
					break;
				case "Square":
					corner = ((Square) crrntShape).getCenter();
					height = (int) ((Square) crrntShape).getHeight();
					width = (int) ((Square) crrntShape).getWidth();
					corner = new MyPoint(-width/2, -height/2);
					g2d.setColor(color);
					b = crrntShape.objectToWorld();
					b.concatenate(a);
					g2d.setTransform(b);
					g2d.drawRect((int)corner.x, (int)corner.y, width, height);
					g2d.setTransform(new AffineTransform());
					shapeHeight = height/2;
					points.add(new MyPoint(corner.x, corner.y));
					points.add(new MyPoint(corner.x, corner.y + height));
					points.add(new MyPoint(corner.x + width, corner.y));
					points.add(new MyPoint(corner.x + width, corner.y + height));
					break;
				case "Ellipse":
					corner = ((Ellipse) crrntShape).getCenter();
					height = (int) ((Ellipse) crrntShape).getHeight();
					width = (int) ((Ellipse) crrntShape).getWidth();
					corner = new MyPoint(-width/2, -height/2);
					g2d.setColor(color);
					b = crrntShape.objectToWorld();
					b.concatenate(a);
					g2d.setTransform(b);
					g2d.drawOval((int)corner.x, (int)corner.y, width, height);
					g2d.setTransform(new AffineTransform());
					shapeHeight = height/2;
					points.add(new MyPoint(corner.x, corner.y));
					points.add(new MyPoint(corner.x, corner.y + height));
					points.add(new MyPoint(corner.x + width, corner.y));
					points.add(new MyPoint(corner.x + width, corner.y + height));
					break;
				case "Circle":
					corner = ((Circle) crrntShape).getCenter();
					height = (int) ((Circle) crrntShape).getHeight();
					width = (int) ((Circle) crrntShape).getWidth();
					corner = new MyPoint(-width/2, -height/2);
					g2d.setColor(color);
					b = crrntShape.objectToWorld();
					b.concatenate(a);
					g2d.setTransform(b);
					g2d.drawOval((int)corner.x, (int)corner.y, width, height);
					g2d.setTransform(new AffineTransform());
					shapeHeight = height/2;
					points.add(new MyPoint(corner.x, corner.y));
					points.add(new MyPoint(corner.x, corner.y + height));
					points.add(new MyPoint(corner.x + width, corner.y));
					points.add(new MyPoint(corner.x + width, corner.y + height));
					break;
				case "Triangle":
					int[] x = new int[3];
					int[] y = new int[3];
					x[0] = (int) (((Triangle) crrntShape).getFirstPoint().x);
					y[0] = (int) (((Triangle) crrntShape).getFirstPoint().y);
					x[1] = (int) (((Triangle) crrntShape).getSecPoint().x);
					y[1] = (int) (((Triangle) crrntShape).getSecPoint().y);
					x[2] = (int) (((Triangle) crrntShape).getThirdPoint().x);
					y[2] = (int) (((Triangle) crrntShape).getThirdPoint().y);
					Polygon triangle = new Polygon(x, y, 3);
					g2d.setColor(color);
					b = crrntShape.objectToWorld();
					b.concatenate(a);
					g2d.setTransform(b);
					g2d.drawPolygon(triangle);
					g2d.setTransform(new AffineTransform());
					pointHeight = new MyPoint(-((Triangle) crrntShape).getFirstPoint().x, -((Triangle) crrntShape).getFirstPoint().y);
					points.add(new MyPoint(x[0], y[0]));
					points.add(new MyPoint(x[1], y[1]));
					points.add(new MyPoint(x[2], y[2]));
					break;
			}
			
			if(!temp.equals("Circle") && !temp.equals("Line"))
			{
				for(int i = 1; i < handles.size(); i ++)
				{
					MyPoint corner = ((Circle) handles.get(i).getShape()).getCenter();
					int height = (int) ((Circle) handles.get(i).getShape()).getHeight();
					int width = (int) ((Circle) handles.get(i).getShape()).getWidth();
					corner = new MyPoint(-width/2, -height/2);
					g2d.setColor(color);
					b = crrntShape.objectToWorld();
					b.concatenate(a);
					g2d.setTransform(b);
					g2d.fillOval((int)(points.get(i - 1).x + corner.x), (int)(points.get(i - 1).y + corner.y), width, height);
					g2d.setTransform(new AffineTransform());
				}
			}
			else if(!temp.equals("Line"))
			{
				for(int i = 0; i < handles.size(); i ++)
				{
					MyPoint corner = ((Circle) handles.get(i).getShape()).getCenter();
					int height = (int) ((Circle) handles.get(i).getShape()).getHeight();
					int width = (int) ((Circle) handles.get(i).getShape()).getWidth();
					corner = new MyPoint(-width/2, -height/2);
					g2d.setColor(color);
					b = crrntShape.objectToWorld();
					b.concatenate(a);
					g2d.setTransform(b);
					g2d.fillOval((int)(points.get(i).x + corner.x), (int)(points.get(i).y + corner.y), width, height);
					g2d.setTransform(new AffineTransform());
				}
			}
			else
			{
				for(int i = 0; i < handles.size(); i ++)
				{
					MyPoint corner = ((Circle) handles.get(i).getShape()).getCenter();
					int height = (int) ((Circle) handles.get(i).getShape()).getHeight();
					int width = (int) ((Circle) handles.get(i).getShape()).getWidth();
					corner = new MyPoint(corner.x - width/2, corner.y - height/2);
					g2d.setColor(color);
					g2d.fillOval((int)(corner.x), (int)(corner.y), width, height);
				}
			}
			
			if(!temp.equals("Triangle") && !temp.equals("Circle") && !temp.equals("Line"))
			{
				MyPoint corner = ((Circle) handles.get(0).getShape()).getCenter();
				int height = (int) ((Circle) handles.get(0).getShape()).getHeight();
				int width = (int) ((Circle) handles.get(0).getShape()).getWidth();
				corner = new MyPoint(-width / 2, -height / 2);
				MyPoint point = new MyPoint(0, -shapeHeight - 20);
				g2d.setColor(color);
				b = crrntShape.objectToWorld();
				b.concatenate(a);
				g2d.setTransform(b);
				g2d.fillOval((int) (point.x + corner.x), (int) (point.y + corner.y), width, height);
				g2d.setTransform(new AffineTransform());
			}
			else if(!temp.equals("Circle") && !temp.equals("Line"))
			{
				MyPoint corner = ((Circle) handles.get(0).getShape()).getCenter();
				int height = (int) ((Circle) handles.get(0).getShape()).getHeight();
				int width = (int) ((Circle) handles.get(0).getShape()).getWidth();
				corner = new MyPoint(-width/2, -height/2);
				g2d.setColor(color);
				b = crrntShape.objectToWorld();
				b.concatenate(a);
				g2d.setTransform(b);
				g2d.fillOval((int)(pointHeight.x + corner.x), (int)(pointHeight.y + corner.y), width, height);
				g2d.setTransform(new AffineTransform());
			}
			
		}
	}

}
