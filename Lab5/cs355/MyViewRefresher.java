package cs355;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.Iterator;
import java.util.List;

public class MyViewRefresher implements ViewRefresher
{
	
	private static MyViewRefresher instance;
	
	public static MyViewRefresher inst()
    {
        if (instance == null)
        {
            instance = new MyViewRefresher();
        }
        return instance;
    }


	@Override
	public void refreshView(Graphics2D g2d)
	{
		WireFrame house = new HouseModel();
		Iterator<Line3D> it = house.getLines();
		Matrix matrix = new Matrix();
		if(Controller.inst().canDisplayHouse())
		{
			while(it.hasNext())
			{
				List<Point> points = matrix.transformFromCameraToWorld(it.next());
				if(points.size() > 1)
				{
					g2d.setColor(Color.GREEN);
					g2d.drawLine(points.get(0).x, points.get(0).y, points.get(1).x, points.get(1).y);
				}
			}
		}
	}

}
