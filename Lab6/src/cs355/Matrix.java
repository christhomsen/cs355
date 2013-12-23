package cs355;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

public class Matrix
{

	private Camera camera = Controller.inst().getCamera();
	private double camRolate = camera.getYaw();
	private double cameraX = camera.getLocation().x;
	private double cameraY = camera.getLocation().y;
	private double cameraZ = camera.getLocation().z;
	private double scale = Controller.inst().getZoom();
	private double w_x = Controller.inst().getHorizontal();
	private double w_y = Controller.inst().getVertical();

	public List<Point> transformFromCameraToWorld(Line3D next)
	{
		List<Point> points = new ArrayList<Point>();
		Point3D startPoint = this.getCanonical(next.start);
		Point3D endPoint = this.getCanonical(next.end);
		
		if(this.validatePoints(startPoint, endPoint))
		{
			return points;
		}
		
		Point start = convertToView(startPoint);
		Point end = convertToView(endPoint);
		
		points.add(start);
		points.add(end);
		
		return points;
	}

	private Point convertToView(Point3D point)
	{
		double tempX = -w_x + (1024 + 1024 * point.x) * scale;
		double tempY = -w_y + 1024 * scale - 1024 * point.y * scale;
		Point newPoint = new Point((int) tempX, (int) tempY);
		return newPoint;
	}

	private boolean validatePoints(Point3D startPoint, Point3D endPoint)
	{
		double sX = startPoint.x;
		double sY = startPoint.y;
		double sZ = startPoint.z;
		
		double eX = endPoint.x;
		double eY = endPoint.y;
		double eZ = endPoint.z;
		
		if((sX < -1 && eX < -1) || (sX > 1 && eX > 1))
			return true;
		if((sY < -1 && eY < -1) || (sY > 1 && eY > 1))
			return true;
		if((sZ < -1 && eZ < -1) || (sZ > 1 && eZ > 1))
			return true;
		if(sZ < -1 || eZ < -1)
			return true;
		return false;
	}

	private Point3D getCanonical(Point3D start)
	{
		double x = -Math.sqrt(3) * this.cameraX * Math.cos(this.camRolate) + 
				Math.sqrt(3) * start.x * Math.cos(this.camRolate) - Math.sqrt(3) * this.cameraZ *
				Math.sin(this.camRolate) + Math.sqrt(3) * start.z * Math.sin(this.camRolate);
		
		x /= -this.cameraZ * Math.cos(this.camRolate) + start.z * Math.cos(this.camRolate) + 
				this.cameraX * Math.sin(this.camRolate) - start.x * Math.sin(this.camRolate);
		
		double y = Math.sqrt(3) * start.y - Math.sqrt(3) * this.cameraY;
		y /= -this.cameraZ * Math.cos(this.camRolate) + start.z * Math.cos(this.camRolate) + 
				this.cameraX * Math.sin(this.camRolate) - start.x * Math.sin(this.camRolate);
				
		double z = ((- 200 / 99) - cameraZ * (101 / 99) * Math.cos(this.camRolate) + 
				(101 / 99) * start.z * Math.cos(this.camRolate) + cameraX * (101 / 99) * 
				Math.sin(this.camRolate) - (101 / 99) * start.x * Math.sin(this.camRolate));
		z /= ( - cameraZ * Math.cos(this.camRolate) + start.z * Math.cos(this.camRolate) + cameraX * Math.sin(this.camRolate) - start.x * Math.sin(this.camRolate));
		
		return new Point3D(x,y,z);
	}
}
