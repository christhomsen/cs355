package CS355.LWJGL;

public class Camera
{
	Point3D location;
	float yaw;
	
	public Camera()
	{
		location = new Point3D(0, 0, 0);
		yaw = 0;
	}

	public Point3D getLocation()
	{
		return location;
	}

	public void setLocation(Point3D location)
	{
		this.location = location;
	}
	
	public void setX(double x)
	{
		this.location.x += x;
	}
	
	public void setY(double y)
	{
		this.location.y += y;
	}
	
	public void setZ(double z)
	{
		this.location.z += z;
	}

	public float getYaw()
	{
		return yaw;
	}

	public void setYaw(float rotation)
	{
		this.yaw += rotation;
	}
	
	public void forward(float distance)
	{
	    location.x -= distance * (float)Math.sin(Math.toRadians(yaw));
	    location.z += distance * (float)Math.cos(Math.toRadians(yaw));
	}
	 
	//moves the camera backward relative to its current rotation (yaw)
	public void backwards(float distance)
	{
	    location.x += distance * (float)Math.sin(Math.toRadians(yaw));
	    location.z -= distance * (float)Math.cos(Math.toRadians(yaw));
	}
}
