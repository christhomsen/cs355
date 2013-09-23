package lab1;

import java.awt.Color;
import java.awt.Point;

import cs355.CS355Controller;
import cs355.GUIFunctions;

public class Controller implements CS355Controller
{
	
	private static Controller instance;
	Shapes shapes = Shapes.inst();
	Color currentColor = null;
	ControllerState state;
	int currentShape;
	Point first = null;
	Point sec = null;
	Point third = null;
	int totalNumPoints = 0;
	Point currentStart = null;
	
	enum ControllerState
	{
		LINE, RECT, SQUA, ELLI, CIRC, TRIA, FREE, NONE
	}
    
    public static Controller inst()
    {
        if (instance == null)
        {
            instance = new Controller();
        }
        return instance;
    }
    
    public Controller()
    {
    	state = ControllerState.NONE;
    	currentColor = Color.white;
    	currentShape = 0;
    }

	@Override
	public void colorButtonHit(Color c)
	{
		if(c != null)
		{
			currentColor = c;
			GUIFunctions.changeSelectedColor(currentColor);
		}
	}

	@Override
	public void triangleButtonHit()
	{
		state = ControllerState.TRIA;
	}

	@Override
	public void squareButtonHit()
	{
		state = ControllerState.SQUA;
		this.totalNumPoints = 0;
	}

	@Override
	public void rectangleButtonHit()
	{
		state = ControllerState.RECT;
		this.totalNumPoints = 0;
	}

	@Override
	public void circleButtonHit()
	{
		state = ControllerState.CIRC;
		this.totalNumPoints = 0;
	}

	@Override
	public void ellipseButtonHit()
	{
		state = ControllerState.ELLI;
		this.totalNumPoints = 0;
	}

	@Override
	public void lineButtonHit()
	{
		state = ControllerState.LINE;
		this.totalNumPoints = 0;
	}

	@Override
	public void selectButtonHit()
	{
		state = ControllerState.FREE;
		//this.totalNumPoints = 0;
	}

	@Override
	public void zoomInButtonHit()
	{
		state = ControllerState.FREE;
		//this.totalNumPoints = 0;
	}

	@Override
	public void zoomOutButtonHit()
	{
		state = ControllerState.FREE;
		//this.totalNumPoints = 0;
	}

	@Override
	public void hScrollbarChanged(int value)
	{
		state = ControllerState.FREE;
		//this.totalNumPoints = 0;
	}

	@Override
	public void vScrollbarChanged(int value)
	{
		state = ControllerState.FREE;
		//this.totalNumPoints = 0;
	}

	public void startShape(Point topCorner)
	{
		switch (state)
		{
			case LINE:
				startLine(topCorner);
				break;
			case RECT:
				startRect(topCorner);
				break;
			case SQUA:
				startSquare(topCorner);
				break;
			case ELLI:
				startEllipse(topCorner);
				break;
			case CIRC:
				startCircle(topCorner);
				break;
			default:
				break;
		}
	}

	private void startLine(Point topCorner)
	{
		Shape temp = new Line(currentColor, topCorner, topCorner);
		currentShape = shapes.addShape(temp);
	}

	private void startRect(Point topCorner)
	{
		currentStart = new Point();
		Shape temp = new Rectangle(currentColor, topCorner, 0, 0);
		currentShape = shapes.addShape(temp);
		currentStart = topCorner;
	}

	private void startSquare(Point topCorner)
	{
		currentStart = new Point();
		Shape temp = new Square(currentColor, topCorner, 0);
		currentShape = shapes.addShape(temp);
		currentStart = topCorner;
	}

	private void startEllipse(Point topCorner)
	{
		currentStart = new Point();
		Shape temp = new Ellipse(currentColor, topCorner, 0, 0);
		currentShape = shapes.addShape(temp);
		currentStart = topCorner;
	}

	private void startCircle(Point topCorner)
	{
		currentStart = new Point();
		Shape temp = new Circle(currentColor, topCorner, 0);
		currentShape = shapes.addShape(temp);
		currentStart = topCorner;
	}

	public void makeTriangle(Point point)
	{
		if (state == ControllerState.TRIA)
		{
			switch (totalNumPoints)
			{
			case 0:
				first = point;
				totalNumPoints++;
				break;
			case 1:
				sec = point;
				totalNumPoints++;
				break;
			case 2:
				third = point;
				shapes.addShape(new Triangle(currentColor, first, sec, third));
				totalNumPoints = 0;
				GUIFunctions.refresh();
				break;
			}
		}
	}
	
	public void updateShape(Point point)
	{
		switch (state)
		{
		case LINE:
			updateLine(point);
			break;
		case RECT:
			updateRect(point);
			break;
		case SQUA:
			updateSquare(point);
			break;
		case ELLI:
			updateEllipse(point);
			break;
		case CIRC:
			updateCircle(point);
			break;
		default:
			break;
		}
	}

	private void updateLine(Point point)
	{
		Shape temp = shapes.getShape(currentShape);
		((Line) temp).setEndPoint(point);
		GUIFunctions.refresh();
	}

	private void updateRect(Point point)
	{
		Shape temp = shapes.getShape(currentShape);
		int diffx = point.x - currentStart.x;
		int diffy = point.y - currentStart.y;
		if(diffx < 0 && diffy < 0)
		{
			((Rectangle) temp).setCorner(point);
			((Rectangle) temp).setHeight(diffy * -1);
			((Rectangle) temp).setWidth(diffx * -1); 
		}
		else if(diffx < 0 && diffy >= 0)
		{
			((Rectangle) temp).setCorner(new Point(point.x, currentStart.y));
			((Rectangle) temp).setHeight(diffy);
			((Rectangle) temp).setWidth(diffx * -1);
		}
		else if(diffx >= 0 && diffy < 0)
		{
			((Rectangle) temp).setCorner(new Point(currentStart.x, point.y));
			((Rectangle) temp).setHeight(diffy * -1);
			((Rectangle) temp).setWidth(diffx);
		}
		else
		{
			((Rectangle) temp).setHeight(diffy);
			((Rectangle) temp).setWidth(diffx);
		}
		GUIFunctions.refresh();
	}

	private void updateSquare(Point point)
	{
		Shape temp = shapes.getShape(currentShape);
		int diffx = point.x - currentStart.x;
		int diffy = point.y - currentStart.y;
		if(diffx < 0 && diffy < 0)
		{
			if(diffx >= diffy)
			{
				((Square) temp).setCorner(new Point(currentStart.x + diffx, currentStart.y + diffx));
				((Square) temp).setHeight(diffx * -1);
				((Square) temp).setWidth(diffx * -1);
			}
			else
			{
				((Square) temp).setCorner(new Point(currentStart.x + diffy, currentStart.y + diffy));
				((Square) temp).setHeight(diffy * -1);
				((Square) temp).setWidth(diffy * -1);
			}
		}
		else if(diffx < 0 && diffy >= 0)
		{
			diffx *= -1;
			if(diffx <= diffy)
			{
				((Square) temp).setCorner(new Point(currentStart.x - diffx, currentStart.y));
				((Square) temp).setHeight(diffx);
				((Square) temp).setWidth(diffx);
			}
			else
			{
				((Square) temp).setCorner(new Point(currentStart.x - diffy, currentStart.y));
				((Square) temp).setHeight(diffy);
				((Square) temp).setWidth(diffy);
			}
		}
		else if(diffx >= 0 && diffy < 0)
		{
			diffy *= -1;
			if(diffx <= diffy)
			{
				((Square) temp).setCorner(new Point(currentStart.x, currentStart.y - diffx));
				((Square) temp).setHeight(diffx);
				((Square) temp).setWidth(diffx);
			}
			else
			{
				((Square) temp).setCorner(new Point(currentStart.x, currentStart.y - diffy));
				((Square) temp).setHeight(diffy);
				((Square) temp).setWidth(diffy);
			}
		}
		else
		{
			if(diffx < diffy)
			{
				((Square) temp).setHeight(diffx);
				((Square) temp).setWidth(diffx);
			}
			else
			{
				((Square) temp).setHeight(diffy);
				((Square) temp).setWidth(diffy);
			}
		}
		GUIFunctions.refresh();
	}

	private void updateEllipse(Point point)
	{
		Shape temp = shapes.getShape(currentShape);
		int diffx = point.x - currentStart.x;
		int diffy = point.y - currentStart.y;
		if(diffx < 0 && diffy < 0)
		{
			diffx *= -1;
			diffy *= -1;
			((Ellipse) temp).setHeight(diffy);
			((Ellipse) temp).setWidth(diffx);
			((Ellipse) temp).setCenter(new Point(point.x + diffx/2, point.y + diffy/2));
		}
		else if(diffx < 0 && diffy >= 0)
		{
			diffx *= -1;
			((Ellipse) temp).setHeight(diffy);
			((Ellipse) temp).setWidth(diffx);
			((Ellipse) temp).setCenter(new Point(point.x + diffx/2, currentStart.y + diffy/2));
		}
		else if(diffx >= 0 && diffy < 0)
		{
			diffy *= -1;
			((Ellipse) temp).setHeight(diffy);
			((Ellipse) temp).setWidth(diffx);
			((Ellipse) temp).setCenter(new Point(currentStart.x + diffx/2, point.y +diffy/2));
		}
		else
		{
			((Ellipse) temp).setHeight(diffy);
			((Ellipse) temp).setWidth(diffx);
			((Ellipse) temp).setCenter(new Point(currentStart.x + diffx/2, currentStart.y + diffy/2));
		}
		GUIFunctions.refresh();
	}

	private void updateCircle(Point point)
	{
		Shape temp = shapes.getShape(currentShape);
		int diffx = point.x - currentStart.x;
		int diffy = point.y - currentStart.y;
		if(diffx < 0 && diffy < 0)
		{
			if(diffx >= diffy)
			{
				((Circle) temp).setHeight(diffx * -1);
				((Circle) temp).setWidth(diffx * -1);
				((Circle) temp).setCenter(new Point(currentStart.x + diffx/2, currentStart.y + diffx/2));
			}
			else
			{
				((Circle) temp).setHeight(diffy * -1);
				((Circle) temp).setWidth(diffy * -1);
				((Circle) temp).setCenter(new Point(currentStart.x + diffy/2, currentStart.y + diffy/2));
			}
		}
		else if(diffx < 0 && diffy >= 0)
		{
			diffx *= -1;
			if(diffx <= diffy)
			{
				((Circle) temp).setHeight(diffx);
				((Circle) temp).setWidth(diffx);
				((Circle) temp).setCenter(new Point(currentStart.x - diffx/2, currentStart.y + diffx/2));
			}
			else
			{
				((Circle) temp).setHeight(diffy);
				((Circle) temp).setWidth(diffy);
				((Circle) temp).setCenter(new Point(currentStart.x - diffy/2, currentStart.y + diffy/2));
			}
		}
		else if(diffx >= 0 && diffy < 0)
		{
			diffy *= -1;
			if(diffx <= diffy)
			{
				((Circle) temp).setHeight(diffx);
				((Circle) temp).setWidth(diffx);
				((Circle) temp).setCenter(new Point(currentStart.x + diffx/2, currentStart.y - diffx/2));
			}
			else
			{
				((Circle) temp).setHeight(diffy);
				((Circle) temp).setWidth(diffy);
				((Circle) temp).setCenter(new Point(currentStart.x + diffy/2, currentStart.y - diffy/2));
			}
		}
		else
		{
			if(diffx < diffy)
			{
				((Circle) temp).setHeight(diffx);
				((Circle) temp).setWidth(diffx);
				((Circle) temp).setCenter(new Point(currentStart.x + diffx/2, currentStart.y + diffx/2));
			}
			else
			{
				((Circle) temp).setHeight(diffy);
				((Circle) temp).setWidth(diffy);
				((Circle) temp).setCenter(new Point(currentStart.x + diffy/2, currentStart.y + diffy/2));
			}
		}
		GUIFunctions.refresh();
	}

}
