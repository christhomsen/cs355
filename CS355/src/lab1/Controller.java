package lab1;

import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import cs355.CS355Controller;
import cs355.GUIFunctions;

public class Controller implements CS355Controller
{
	
	private static Controller instance;
	Shapes shapes = Shapes.inst();
	Color currentColor = null;
	ControllerState state;
	int currentShape;
	MyPoint first = null;
	MyPoint sec = null;
	MyPoint third = null;
	int totalNumPoints = 0;
	MyPoint currentStart = null;
	Shape crrntShape = null;
	MyPoint currentPoint = null;
	double maxError = 5;
	int handle = -1;
	List<Handle> handles = new ArrayList<Handle>();
	
	enum ControllerState
	{
		LINE, RECT, SQUA, ELLI, CIRC, TRIA, FREE, NONE, SELE
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
			if(crrntShape != null)
			{
				crrntShape.setColor(c);
				GUIFunctions.refresh();
			}
		}
	}

	@Override
	public void triangleButtonHit()
	{
		state = ControllerState.TRIA;
		crrntShape = null;
		GUIFunctions.refresh();
	}

	@Override
	public void squareButtonHit()
	{
		state = ControllerState.SQUA;
		this.totalNumPoints = 0;
		crrntShape = null;
		GUIFunctions.refresh();
	}

	@Override
	public void rectangleButtonHit()
	{
		state = ControllerState.RECT;
		this.totalNumPoints = 0;
		crrntShape = null;
		GUIFunctions.refresh();
	}

	@Override
	public void circleButtonHit()
	{
		state = ControllerState.CIRC;
		this.totalNumPoints = 0;
		this.crrntShape = null;
		GUIFunctions.refresh();
	}

	@Override
	public void ellipseButtonHit()
	{
		state = ControllerState.ELLI;
		this.totalNumPoints = 0;
		this.crrntShape = null;
		GUIFunctions.refresh();
	}

	@Override
	public void lineButtonHit()
	{
		state = ControllerState.LINE;
		this.totalNumPoints = 0;
		this.crrntShape = null;
		GUIFunctions.refresh();
	}

	@Override
	public void selectButtonHit()
	{
		state = ControllerState.SELE;
		this.totalNumPoints = 0;
		this.crrntShape = null;
		GUIFunctions.refresh();
	}

	@Override
	public void zoomInButtonHit()
	{
		state = ControllerState.FREE;
		//this.totalNumPoints = 0;
		//this.crrntShape = null;
		//GUIFunctions.refresh();
	}

	@Override
	public void zoomOutButtonHit()
	{
		state = ControllerState.FREE;
		//this.totalNumPoints = 0;
		//this.crrntShape = null;
		//GUIFunctions.refresh();
	}

	@Override
	public void hScrollbarChanged(int value)
	{
		state = ControllerState.FREE;
		//this.totalNumPoints = 0;
		//this.crrntShape = null;
		//GUIFunctions.refresh();
	}

	@Override
	public void vScrollbarChanged(int value)
	{
		state = ControllerState.FREE;
		//this.totalNumPoints = 0;
		///this.crrntShape = null;
		//GUIFunctions.refresh();
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
			case SELE:
				if(this.crrntShape != null)
				{
					if(handleHitTest(new MyPoint(topCorner.x, topCorner.y)))
					{
						selectHandle(new MyPoint(topCorner.x, topCorner.y));						
					}
				}
				else
				{
					currentPoint = new MyPoint(topCorner.x, topCorner.y);
					findSelected(this.currentPoint);
				}
				break;
			default:
				break;
		}
	}

	private void selectHandle(MyPoint myPoint)
	{
		handle = -1;
		for(int i = 0; i < handles.size(); i ++)
		{
			double val = Math.pow((myPoint.x - handles.get(i).getShape().getCenter().x), 2);
			val += Math.pow(myPoint.y - handles.get(i).getShape().getCenter().y, 2);
			double radius = ((Circle) handles.get(i).getShape()).getHeight()/2;
			if(val <= Math.pow(radius, 2))
			{
				handle = i;
				break;
			}
		}
	}

	private void startLine(Point topCorner)
	{
		Shape temp = new Line(currentColor, new MyPoint(topCorner.x, topCorner.y), new MyPoint(topCorner.x, topCorner.y));
		currentShape = shapes.addShape(temp);
	}

	private void startRect(Point topCorner)
	{
		currentStart = new MyPoint();
		Shape temp = new Rectangle(currentColor, new MyPoint(topCorner.x, topCorner.y), 0, 0);
		currentShape = shapes.addShape(temp);
		currentStart = new MyPoint(topCorner.x, topCorner.y);
	}

	private void startSquare(Point topCorner)
	{
		currentStart = new MyPoint();
		Shape temp = new Square(currentColor, new MyPoint(topCorner.x, topCorner.y), 0);
		currentShape = shapes.addShape(temp);
		currentStart = new MyPoint(topCorner.x, topCorner.y);
	}

	private void startEllipse(Point topCorner)
	{
		currentStart = new MyPoint();
		Shape temp = new Ellipse(currentColor, new MyPoint(topCorner.x, topCorner.y), 0, 0);
		currentShape = shapes.addShape(temp);
		currentStart = new MyPoint(topCorner.x, topCorner.y);
	}

	private void startCircle(Point topCorner)
	{
		currentStart = new MyPoint();
		Shape temp = new Circle(currentColor, new MyPoint(topCorner.x, topCorner.y), 0);
		currentShape = shapes.addShape(temp);
		currentStart = new MyPoint(topCorner.x, topCorner.y);
	}

	public void makeTriangle(MyPoint point)
	{
		switch (totalNumPoints)
		{
			case 0:
				first = new MyPoint(point.x, point.y);
				totalNumPoints++;
				break;
			case 1:
				sec = new MyPoint(point.x, point.y);
				totalNumPoints++;
				break;
			case 2:
				third = new MyPoint(point.x, point.y);
				shapes.addShape(new Triangle(currentColor, first, sec, third));
				totalNumPoints = 0;
				GUIFunctions.refresh();
				break;
		}
	}
	
	public void updateShape(Point point)
	{
		MyPoint myPoint = new MyPoint(point.x, point.y);
		switch (state)
		{
		case LINE:
			updateLine(myPoint);
			break;
		case RECT:
			updateRect(myPoint);
			break;
		case SQUA:
			updateSquare(myPoint);
			break;
		case ELLI:
			updateEllipse(myPoint);
			break;
		case CIRC:
			updateCircle(myPoint);
			break;
		case SELE:
			if(this.crrntShape != null)
				if(handle != -1)
				{
					this.currentShape = shapes.indexOf(this.crrntShape);
					updateHandle(myPoint);
				}
				else
				{
					moveShape(new MyPoint(point.x, point.y));
				}
			break;
		default:
			break;
		}
	}

	private void updateHandle(MyPoint myPoint)
	{		
		String temp = this.crrntShape.getClass().getSimpleName();
		switch(temp)
		{
			case "Line":
				if(handle == 0)
				{
					this.crrntShape.setCenter(new MyPoint(myPoint.x, myPoint.y));
					double x = myPoint.x - handles.get(handle).getShape().getCenter().x;
					double y = myPoint.y - handles.get(handle).getShape().getCenter().y;
					MyPoint tempPoint = new MyPoint(handles.get(handle).getShape().getCenter().x + x, handles.get(handle).getShape().getCenter().y + y);
					handles.get(handle).getShape().setCenter(tempPoint);
					GUIFunctions.refresh();
				}
				else
				{
					this.currentStart = this.crrntShape.getCenter();
					this.updateLine(myPoint);
					double x = myPoint.x - handles.get(handle).getShape().getCenter().x;
					double y = myPoint.y - handles.get(handle).getShape().getCenter().y;
					MyPoint tempPoint = new MyPoint(handles.get(handle).getShape().getCenter().x + x, handles.get(handle).getShape().getCenter().y + y);
					handles.get(handle).getShape().setCenter(tempPoint);
					GUIFunctions.refresh();
				}
				break;
			case "Rectangle":
				MyPoint center = this.crrntShape.getCenter();
				double width = ((Rectangle) this.crrntShape).getWidth()/2;
				double height = ((Rectangle) this.crrntShape).getHeight()/2;
				if(handles.get(handle).getVert() == 0)
				{
					rotate(myPoint);
					updateHandlePos();
				}
				if(handles.get(handle).getVert() == 1)
				{
					this.currentStart = new MyPoint(center.x + width, center.y + height);
					this.updateRect(myPoint);
					updateHandlePos();
				}
				else if(handles.get(handle).getVert() == 2)
				{
					this.currentStart = new MyPoint(center.x + width, center.y - height);
					this.updateRect(myPoint);
					updateHandlePos();
				}
				else if(handles.get(handle).getVert()== 3)
				{
					this.currentStart = new MyPoint(center.x - width, center.y + height);
					this.updateRect(myPoint);
					updateHandlePos();
					
				}
				else if(handles.get(handle).getVert() == 4)
				{
					this.currentStart = new MyPoint(center.x - width, center.y - height);
					this.updateRect(myPoint);
					updateHandlePos();
					
				}
				break;
			case "Square":
				center = this.crrntShape.getCenter();
				width = ((Square) this.crrntShape).getWidth()/2;
				height = ((Square) this.crrntShape).getHeight()/2;
				if(handles.get(handle).getVert() == 0)
				{
					rotate(myPoint);
					updateHandlePos();
				}
				if(handles.get(handle).getVert() == 1)
				{
					this.currentStart = new MyPoint(center.x + width, center.y + height);
					this.updateSquare(myPoint);
					updateHandlePos();
				}
				else if(handles.get(handle).getVert() == 2)
				{
					this.currentStart = new MyPoint(center.x + width, center.y - height);
					this.updateSquare(myPoint);
					updateHandlePos();
				}
				else if(handles.get(handle).getVert()== 3)
				{
					this.currentStart = new MyPoint(center.x - width, center.y + height);
					this.updateSquare(myPoint);
					updateHandlePos();
					
				}
				else if(handles.get(handle).getVert() == 4)
				{
					this.currentStart = new MyPoint(center.x - width, center.y - height);
					this.updateSquare(myPoint);
					updateHandlePos();
					
				}
				break;
			case "Ellipse":
				center = this.crrntShape.getCenter();
				width = ((Ellipse) this.crrntShape).getWidth()/2;
				height = ((Ellipse) this.crrntShape).getHeight()/2;
				if(handles.get(handle).getVert() == 0)
				{
					rotate(myPoint);
					updateHandlePos();
				}
				if(handles.get(handle).getVert() == 1)
				{
					this.currentStart = new MyPoint(center.x + width, center.y + height);
					this.updateEllipse(myPoint);
					updateHandlePos();
				}
				else if(handles.get(handle).getVert() == 2)
				{
					this.currentStart = new MyPoint(center.x + width, center.y - height);
					this.updateEllipse(myPoint);
					updateHandlePos();
				}
				else if(handles.get(handle).getVert()== 3)
				{
					this.currentStart = new MyPoint(center.x - width, center.y + height);
					this.updateEllipse(myPoint);
					updateHandlePos();
				}
				else if(handles.get(handle).getVert() == 4)
				{
					this.currentStart = new MyPoint(center.x - width, center.y - height);
					this.updateEllipse(myPoint);
					updateHandlePos();
				}
				break;
			case "Circle":
				center = this.crrntShape.getCenter();
				width = ((Circle) this.crrntShape).getWidth()/2;
				height = ((Circle) this.crrntShape).getHeight()/2;
				if(handles.get(handle).getVert() == 1)
				{
					this.currentStart = new MyPoint(center.x + width, center.y + height);
					this.updateCircle(myPoint);
					updateHandlePos();
				}
				else if(handles.get(handle).getVert() == 2)
				{
					this.currentStart = new MyPoint(center.x + width, center.y - height);
					this.updateCircle(myPoint);
					updateHandlePos();
				}
				else if(handles.get(handle).getVert()== 3)
				{
					this.currentStart = new MyPoint(center.x - width, center.y + height);
					this.updateCircle(myPoint);
					updateHandlePos();
					
				}
				else if(handles.get(handle).getVert() == 4)
				{
					this.currentStart = new MyPoint(center.x - width, center.y - height);
					this.updateCircle(myPoint);
					updateHandlePos();
					
				}
				break;
			case "Triangle":
				if(handles.get(handle).getVert() == 0)
				{
					rotate(myPoint);
					updateHandlePos();
				}
				if(handles.get(handle).getVert() == 1)
				{
					((Triangle) this.crrntShape).setFirstPoint(myPoint);
					handles.get(handle).getShape().setCenter(new MyPoint(myPoint.x, myPoint.y));
				}
				else if(handles.get(handle).getVert() == 2)
				{
					((Triangle) this.crrntShape).setSecPoint(myPoint);
					handles.get(handle).getShape().setCenter(new MyPoint(myPoint.x, myPoint.y));
				}
				else if(handles.get(handle).getVert() == 3)
				{
					((Triangle) this.crrntShape).setThirdPoint(myPoint);
					handles.get(handle).getShape().setCenter(new MyPoint(myPoint.x, myPoint.y));
				}
				GUIFunctions.refresh();
				break;
		}
		state = ControllerState.SELE;
	}

	private void rotate(MyPoint myPoint)
	{
		double x = this.crrntShape.getCenter().x - myPoint.x;
		double y = this.crrntShape.getCenter().y - myPoint.y;
		this.crrntShape.setRotation(Math.atan2(y, x));
		updateHandlePos();
		GUIFunctions.refresh();
	}

	private void updateHandlePos()
	{
		MyPoint center = new MyPoint(this.crrntShape.getCenter().x, this.crrntShape.getCenter().y);
		String temp = this.crrntShape.getClass().getSimpleName();
		double width = 0;
		double height = 0;
		switch(temp)
		{
			case "Rectangle":
				Rectangle rect = ((Rectangle) this.crrntShape);
				width = rect.getWidth()/2;
				height = rect.getHeight()/2;
				break;
			case "Square":
				rect = ((Rectangle) this.crrntShape);
				width = rect.getWidth()/2;
				height = rect.getHeight()/2;
				break;
			case "Ellipse":
				Ellipse oval = ((Ellipse) this.crrntShape);
				width = oval.getWidth()/2;
				height = oval.getHeight()/2;
				break;
			case "Circle":
				oval = ((Ellipse) this.crrntShape);
				width = oval.getWidth()/2;
				height = oval.getHeight()/2;
				break;
		}
		if(temp.equals("Circle"))
		{
			handles.get(0).getShape().setCenter(new MyPoint(center.x - width, center.y - height));
			handles.get(1).getShape().setCenter(new MyPoint(center.x - width, center.y + height));
			handles.get(2).getShape().setCenter(new MyPoint(center.x + width, center.y - height));
			handles.get(3).getShape().setCenter(new MyPoint(center.x + width, center.y + height));
		}
		else
		{
			handles.get(0).getShape().setCenter(new MyPoint(center.x, center.y - height - 20));
			handles.get(1).getShape().setCenter(new MyPoint(center.x - width, center.y - height));
			handles.get(2).getShape().setCenter(new MyPoint(center.x - width, center.y + height));
			handles.get(3).getShape().setCenter(new MyPoint(center.x + width, center.y - height));
			handles.get(4).getShape().setCenter(new MyPoint(center.x + width, center.y + height));
		}
	}

	private boolean handleHitTest(MyPoint myPoint)
	{
		boolean hit = false;
		for(int i = 0; i < handles.size(); i ++)
		{
			double val = Math.pow((myPoint.x - handles.get(i).getShape().getCenter().x), 2);
			val += Math.pow(myPoint.y - handles.get(i).getShape().getCenter().y, 2);
			double radius = ((Circle) handles.get(i).getShape()).getHeight()/2;
			if(val <= Math.pow(radius, 2))
			{
				hit = true;
				break;
			}
		}
		return hit;
	}

	private void updateLine(MyPoint point)
	{
		Shape temp = shapes.getShape(currentShape);
		((Line) temp).setEndPoint(point);
		GUIFunctions.refresh();
	}

	private void updateRect(MyPoint point)
	{
		Shape temp = shapes.getShape(currentShape);
		double diffx = point.x - currentStart.x;
		double diffy = point.y - currentStart.y;
		if(diffx < 0 && diffy < 0)
		{
			diffy *= -1;
			diffx *= -1;
			if(state == ControllerState.SELE)
				this.handle = 1;
			((Rectangle) temp).setCenter(new MyPoint(point.x + diffx/2, point.y + diffy/2));
			((Rectangle) temp).setHeight(diffy);
			((Rectangle) temp).setWidth(diffx); 
		}
		else if(diffx < 0 && diffy >= 0)
		{
			diffx *= -1;
			if(state == ControllerState.SELE)
				this.handle =2;
			((Rectangle) temp).setCenter(new MyPoint(point.x + diffx/2, currentStart.y + diffy/2));
			((Rectangle) temp).setHeight(diffy);
			((Rectangle) temp).setWidth(diffx);
		}
		else if(diffx >= 0 && diffy < 0)
		{
			diffy *= -1;
			if(state == ControllerState.SELE)
				this.handle = 3;
			((Rectangle) temp).setCenter(new MyPoint(currentStart.x + diffx/2, point.y + diffy/2));
			((Rectangle) temp).setHeight(diffy);
			((Rectangle) temp).setWidth(diffx);
		}
		else
		{
			if(state == ControllerState.SELE)
				this.handle = 4;
			((Rectangle) temp).setCenter(new MyPoint(currentStart.x + diffx/2, currentStart.y + diffy/2));
			((Rectangle) temp).setHeight(diffy);
			((Rectangle) temp).setWidth(diffx);
		}
		GUIFunctions.refresh();
	}

	private void updateSquare(MyPoint point)
	{
		Shape temp = shapes.getShape(currentShape);
		double diffx = point.x - currentStart.x;
		double diffy = point.y - currentStart.y;
		if(diffx < 0 && diffy < 0)
		{
			if(state == ControllerState.SELE)
				handle = 1;
			if(diffx >= diffy)
			{
				((Square) temp).setCenter(new MyPoint(currentStart.x + diffx/2, currentStart.y + diffx/2));
				((Square) temp).setHeight(diffx * -1);
				((Square) temp).setWidth(diffx * -1);
			}
			else
			{
				((Square) temp).setCenter(new MyPoint(currentStart.x + diffy/2, currentStart.y + diffy/2));
				((Square) temp).setHeight(diffy * -1);
				((Square) temp).setWidth(diffy * -1);
			}
		}
		else if(diffx < 0 && diffy >= 0)
		{
			if(state == ControllerState.SELE)
				handle = 2;
			diffx *= -1;
			if(diffx <= diffy)
			{
				((Square) temp).setCenter(new MyPoint(currentStart.x - diffx/2, currentStart.y + diffx/2));
				((Square) temp).setHeight(diffx);
				((Square) temp).setWidth(diffx);
			}
			else
			{
				((Square) temp).setCenter(new MyPoint(currentStart.x - diffy/2, currentStart.y + diffy/2));
				((Square) temp).setHeight(diffy);
				((Square) temp).setWidth(diffy);
			}
		}
		else if(diffx >= 0 && diffy < 0)
		{
			if(state == ControllerState.SELE)
				handle = 3;
			diffy *= -1;
			if(diffx <= diffy)
			{
				((Square) temp).setCenter(new MyPoint(currentStart.x + diffx/2, currentStart.y - diffx/2));
				((Square) temp).setHeight(diffx);
				((Square) temp).setWidth(diffx);
			}
			else
			{
				((Square) temp).setCenter(new MyPoint(currentStart.x + diffy/2, currentStart.y - diffy/2));
				((Square) temp).setHeight(diffy);
				((Square) temp).setWidth(diffy);
			}
		}
		else
		{
			if(state == ControllerState.SELE)
				handle = 4;
			if(diffx < diffy)
			{
				((Square) temp).setCenter(new MyPoint(currentStart.x + diffx/2, currentStart.y + diffx/2));
				((Square) temp).setHeight(diffx);
				((Square) temp).setWidth(diffx);
			}
			else
			{
				((Square) temp).setCenter(new MyPoint(currentStart.x + diffy/2, currentStart.y + diffy/2));
				((Square) temp).setHeight(diffy);
				((Square) temp).setWidth(diffy);
			}
		}
		GUIFunctions.refresh();
	}

	private void updateEllipse(MyPoint point)
	{
		Shape temp = shapes.getShape(currentShape);
		double diffx = point.x - currentStart.x;
		double diffy = point.y - currentStart.y;
		if(diffx < 0 && diffy < 0)
		{
			if(state == ControllerState.SELE)
				handle = 1;
			diffx *= -1;
			diffy *= -1;
			((Ellipse) temp).setHeight(diffy);
			((Ellipse) temp).setWidth(diffx);
			((Ellipse) temp).setCenter(new MyPoint(point.x + diffx/2, point.y + diffy/2));
		}
		else if(diffx < 0 && diffy >= 0)
		{
			if(state == ControllerState.SELE)
				handle = 2;
			diffx *= -1;
			((Ellipse) temp).setHeight(diffy);
			((Ellipse) temp).setWidth(diffx);
			((Ellipse) temp).setCenter(new MyPoint(point.x + diffx/2, currentStart.y + diffy/2));
		}
		else if(diffx >= 0 && diffy < 0)
		{
			if(state == ControllerState.SELE)
				handle = 3;
			diffy *= -1;
			((Ellipse) temp).setHeight(diffy);
			((Ellipse) temp).setWidth(diffx);
			((Ellipse) temp).setCenter(new MyPoint(currentStart.x + diffx/2, point.y +diffy/2));
		}
		else
		{
			if(state == ControllerState.SELE)
				handle = 4;
			((Ellipse) temp).setHeight(diffy);
			((Ellipse) temp).setWidth(diffx);
			((Ellipse) temp).setCenter(new MyPoint(currentStart.x + diffx/2, currentStart.y + diffy/2));
		}
		GUIFunctions.refresh();
	}

	private void updateCircle(MyPoint point)
	{
		Shape temp = shapes.getShape(currentShape);
		double diffx = point.x - currentStart.x;
		double diffy = point.y - currentStart.y;
		if(diffx < 0 && diffy < 0)
		{
			if(state == ControllerState.SELE)
				handle = 0;
			if(diffx >= diffy)
			{
				((Circle) temp).setHeight(diffx * -1);
				((Circle) temp).setWidth(diffx * -1);
				((Circle) temp).setCenter(new MyPoint(currentStart.x + diffx/2, currentStart.y + diffx/2));
			}
			else
			{
				((Circle) temp).setHeight(diffy * -1);
				((Circle) temp).setWidth(diffy * -1);
				((Circle) temp).setCenter(new MyPoint(currentStart.x + diffy/2, currentStart.y + diffy/2));
			}
		}
		else if(diffx < 0 && diffy >= 0)
		{
			if(state == ControllerState.SELE)
				handle = 1;
			diffx *= -1;
			if(diffx <= diffy)
			{
				((Circle) temp).setHeight(diffx);
				((Circle) temp).setWidth(diffx);
				((Circle) temp).setCenter(new MyPoint(currentStart.x - diffx/2, currentStart.y + diffx/2));
			}
			else
			{
				((Circle) temp).setHeight(diffy);
				((Circle) temp).setWidth(diffy);
				((Circle) temp).setCenter(new MyPoint(currentStart.x - diffy/2, currentStart.y + diffy/2));
			}
		}
		else if(diffx >= 0 && diffy < 0)
		{
			if(state == ControllerState.SELE)
				handle = 2;
			diffy *= -1;
			if(diffx <= diffy)
			{
				((Circle) temp).setHeight(diffx);
				((Circle) temp).setWidth(diffx);
				((Circle) temp).setCenter(new MyPoint(currentStart.x + diffx/2, currentStart.y - diffx/2));
			}
			else
			{
				((Circle) temp).setHeight(diffy);
				((Circle) temp).setWidth(diffy);
				((Circle) temp).setCenter(new MyPoint(currentStart.x + diffy/2, currentStart.y - diffy/2));
			}
		}
		else
		{
			if(state == ControllerState.SELE)
				handle = 3;
			if(diffx < diffy)
			{
				((Circle) temp).setHeight(diffx);
				((Circle) temp).setWidth(diffx);
				((Circle) temp).setCenter(new MyPoint(currentStart.x + diffx/2, currentStart.y + diffx/2));
			}
			else
			{
				((Circle) temp).setHeight(diffy);
				((Circle) temp).setWidth(diffy);
				((Circle) temp).setCenter(new MyPoint(currentStart.x + diffy/2, currentStart.y + diffy/2));
			}
		}
		GUIFunctions.refresh();
	}

	private void moveShape(MyPoint myPoint)
	{
		double x =  myPoint.x - this.currentPoint.x;
		double y = myPoint.y - this.currentPoint.y;
		if(!crrntShape.getClass().getSimpleName().equals("Line"))
		{
			this.crrntShape.setCenter(new MyPoint(this.crrntShape.getCenter().x + x, this.crrntShape.getCenter().y + y));
		}
		else
		{
			Line tmp = ((Line) this.crrntShape);
			tmp.setCenter(new MyPoint(tmp.getCenter().x + x, tmp.getCenter().y + y));
			tmp.setEndPoint(new MyPoint(tmp.getEndPoint().x + x, tmp.getEndPoint().y + y));
		}
		this.currentPoint = myPoint;
		moveHandles(x, y);
		GUIFunctions.refresh();
	}

	private void moveHandles(double x, double y)
	{
		for(int i = 0; i < handles.size(); i ++)
		{
			MyPoint center = handles.get(i).getShape().getCenter();
			center.setLocation(center.x + x, center.y + y);
			handles.get(i).getShape().setCenter(center);
		}
	}

	public void clicked(Point point)
	{
		MyPoint myPoint = new MyPoint(point.x, point.y);
		switch(state)
		{
			case TRIA:
				this.makeTriangle(myPoint);
				break;
			case SELE:
				findSelected(myPoint);
				break;
			default:
				break;
		}
	}

	private void findSelected(MyPoint myPoint)
	{
		List<Shape> shapeList = shapes.getShapes();
		Shape shape = null;
		this.crrntShape = null;
		for (int i = shapeList.size() - 1; i > -1; i --)
		{
			shape = shapeList.get(i);
			String temp = shape.getClass().getSimpleName();
			if(temp.equals("Line"))
			{
					if(lineHitTest(shape, myPoint))
					{
						this.crrntShape = shape;
						break;
					}
			}
			else if(temp.equals("Rectangle") || temp.equals("Square"))
			{
				MyPoint point = worldToObject(myPoint, shape);
				if(Math.abs(point.x) <= ((Rectangle) shape).getWidth()/2 && Math.abs(point.y) <= ((Rectangle) shape).getHeight()/2)
				{
					this.crrntShape = shape;
					break;
				}
			}
			else if(temp.equals("Ellipse"))
			{
				MyPoint point = worldToObject(myPoint, shape);
				double val = (point.x)/(((Ellipse) shape).getWidth()/2);
				val = Math.pow(val, 2);
				double val2 = (point.y)/(((Ellipse) shape).getHeight()/2);
				val2 = Math.pow(val2, 2);
				if((val + val2) <= 1)
				{
					this.crrntShape = shape;
					break;
				}
			}
			else if(temp.equals("Circle"))
			{
					double val = Math.pow((myPoint.x - shape.getCenter().x), 2);
					val += Math.pow(myPoint.y - shape.getCenter().y, 2);
					double radius = ((Circle) shape).getHeight()/2;
					if(val <= Math.pow(radius, 2))
					{
						this.crrntShape = shape;
						break;
					}
			}
			else if(temp.equals("Triangle"))
			{
				MyPoint firstV = ((Triangle) shape).getFirstPoint().add(shape.getCenter());
				MyPoint secV = ((Triangle) shape).getSecPoint().add(shape.getCenter());
				MyPoint thirdV = ((Triangle) shape).getThirdPoint().add(shape.getCenter());
				if(sign(myPoint, firstV, secV) && sign(myPoint, secV, thirdV) && sign(myPoint, thirdV, firstV))
				{
					this.crrntShape = shape;
					break;
				}
			}
		}
		
		if(crrntShape != null)
		{
			drawHandles(crrntShape);
		}
	}

	private MyPoint worldToObject(MyPoint myPoint, Shape shape)
	{
		double x = Math.cos(shape.getRotation()) * myPoint.x + Math.sin(shape.getRotation()) * myPoint.y + (-Math.cos(shape.getRotation()) * shape.getCenter().x - Math.sin(shape.getRotation()) * shape.getCenter().y);
		double y = -Math.sin(shape.getRotation()) * myPoint.x + Math.cos(shape.getRotation()) * myPoint.y + (Math.sin(shape.getRotation()) * shape.getCenter().x - Math.cos(shape.getRotation()) * shape.getCenter().y);
		return new MyPoint(x, y);
	}

	private boolean lineHitTest(Shape shape, MyPoint myPoint)
	{
		boolean closeEnough = false;
		MyPoint firstPoint = shape.getCenter();
		MyPoint secPoint = ((Line) shape).getEndPoint();
		double num = Math.abs((secPoint.x - firstPoint.x) * (firstPoint.y - myPoint.y) 
				- (firstPoint.x - myPoint.x) * (secPoint.y - firstPoint.y));
		double dom = Math.sqrt(Math.pow(secPoint.x - firstPoint.x, 2) + Math.pow(secPoint.y - firstPoint.y, 2));
		if(num / dom <= this.maxError)
			closeEnough = true;
		return closeEnough;
	}

	private boolean sign(MyPoint p1, MyPoint p2, MyPoint p3)
	{
		boolean inside = false;
		double val = (p1.x - p3.x) * (p2.y - p3.y) - (p2.x - p3.x) * (p1.y - p3.y);
		if(val < 0)
			inside = true;
		return inside;
	}

	private void drawHandles(Shape shape)
	{
		handles.clear();
		String temp = shape.getClass().getSimpleName();
		switch(temp)
		{
			case "Line":
				handles.add(new Handle(new Circle(Color.YELLOW, shape.getCenter(), 10), 1));
				handles.add(new Handle(new Circle(Color.YELLOW, ((Line) shape).getEndPoint(), 10), 2));
				break;
			case "Rectangle":
				double width = ((Rectangle) shape).getWidth();
				double height = ((Rectangle) shape).getHeight();
				MyPoint topCorner = new MyPoint(shape.getCenter().x - width/2 ,
						shape.getCenter().y - height/2);
				handles.add(new Handle(new Circle(Color.YELLOW, new MyPoint(shape.getCenter().x, shape.getCenter().y - height/2 - 20), 10), 0));
				handles.add(new Handle(new Circle(Color.YELLOW, topCorner, 10), 1));
				handles.add(new Handle(new Circle(Color.YELLOW, new MyPoint(topCorner.x, topCorner.y + height), 10), 2));
				handles.add(new Handle(new Circle(Color.YELLOW, new MyPoint(topCorner.x + width, topCorner.y), 10), 3));
				handles.add(new Handle(new Circle(Color.YELLOW, new MyPoint(topCorner.x + width, topCorner.y + height), 10), 4));
				break;
			case "Square":
				width = ((Square) shape).getWidth();
				height = ((Square) shape).getHeight();
				topCorner = new MyPoint(shape.getCenter().x - width/2 ,
						shape.getCenter().y - height/2);
				handles.add(new Handle(new Circle(Color.YELLOW, new MyPoint(shape.getCenter().x, shape.getCenter().y - height/2 - 20), 10), 0));
				handles.add(new Handle(new Circle(Color.YELLOW, topCorner, 10), 1));
				handles.add(new Handle(new Circle(Color.YELLOW, new MyPoint(topCorner.x, topCorner.y + height), 10), 2));
				handles.add(new Handle(new Circle(Color.YELLOW, new MyPoint(topCorner.x + width, topCorner.y), 10), 3));
				handles.add(new Handle(new Circle(Color.YELLOW, new MyPoint(topCorner.x + width, topCorner.y + height), 10), 4));
				break;
			case "Ellipse":
				width = ((Ellipse) shape).getWidth();
				height = ((Ellipse) shape).getHeight();
				topCorner = new MyPoint(shape.getCenter().x - width/2 ,
						shape.getCenter().y - height/2);
				handles.add(new Handle(new Circle(Color.YELLOW, new MyPoint(shape.getCenter().x, shape.getCenter().y - height/2 - 20), 10), 0));
				handles.add(new Handle(new Circle(Color.YELLOW, topCorner, 10), 1));
				handles.add(new Handle(new Circle(Color.YELLOW, new MyPoint(topCorner.x, topCorner.y + height), 10), 2));
				handles.add(new Handle(new Circle(Color.YELLOW, new MyPoint(topCorner.x + width, topCorner.y), 10), 3));
				handles.add(new Handle(new Circle(Color.YELLOW, new MyPoint(topCorner.x + width, topCorner.y + height), 10), 4));
				break;
			case "Circle":
				width = ((Circle) shape).getWidth();
				height = ((Circle) shape).getHeight();
				topCorner = new MyPoint(shape.getCenter().x - width/2 , shape.getCenter().y - height/2);
				handles.add(new Handle(new Circle(Color.YELLOW, topCorner, 10), 1));
				handles.add(new Handle(new Circle(Color.YELLOW, new MyPoint(topCorner.x, topCorner.y + height), 10), 2));
				handles.add(new Handle(new Circle(Color.YELLOW, new MyPoint(topCorner.x + width, topCorner.y), 10), 3));
				handles.add(new Handle(new Circle(Color.YELLOW, new MyPoint(topCorner.x + width, topCorner.y + height), 10), 4));
				break;
			case "Triangle":
				Triangle tri = ((Triangle) shape);
				MyPoint first = new MyPoint(tri.getFirstPoint().x + tri.getCenter().x, tri.getFirstPoint().y + tri.getCenter().y);
				MyPoint sec = new MyPoint(tri.getSecPoint().x + tri.getCenter().x, tri.getSecPoint().y + tri.getCenter().y);
				MyPoint third = new MyPoint(tri.getThirdPoint().x + tri.getCenter().x, tri.getThirdPoint().y + tri.getCenter().y);
				handles.add(new Handle(new Circle(Color.yellow, new MyPoint(-first.x, -first.y), 10), 0));
				handles.add(new Handle(new Circle(Color.YELLOW, first, 10), 1));
				handles.add(new Handle(new Circle(Color.YELLOW, sec, 10), 2));
				handles.add(new Handle(new Circle(Color.YELLOW, third, 10), 3));
				break;
		}
		GUIFunctions.refresh();
	}

	public boolean isShapeSelected()
	{
		boolean haveShape = false;
		if(this.crrntShape != null)
			haveShape = true;
		return haveShape;
	}

	public List<Handle> getHandles()
	{
		return handles;
	}

	public Shape getCurrentShape()
	{
		return this.crrntShape;
	}

	public void finishUpdate()
	{
		handle = -1;
		this.currentStart = null;
	}

}
