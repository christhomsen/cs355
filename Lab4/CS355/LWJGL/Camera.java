package CS355.LWJGL;

public class Camera
{
	private Point3D location;
	private float yaw;
	
	public Camera()
	{
		location = new Point3D(0, -3, -15);
		yaw = 0.0f;
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
	
	//strafes the camera left relitive to its current rotation (yaw)
	public void strafeLeft(float distance)
	{
	    location.x -= distance * (float)Math.sin(Math.toRadians(yaw-90));
	    location.z += distance * (float)Math.cos(Math.toRadians(yaw-90));
	}
	 
	//strafes the camera right relitive to its current rotation (yaw)
	public void strafeRight(float distance)
	{
	    location.x -= distance * (float)Math.sin(Math.toRadians(yaw+90));
	    location.z += distance * (float)Math.cos(Math.toRadians(yaw+90));
	}
}
