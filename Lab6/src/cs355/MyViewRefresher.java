package cs355;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
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
		if(Controller.inst().getDisplayImage())
		{
			MyImage pixels = Controller.inst().getImage();
			
			if(pixels != null)
			{
				AffineTransform Tx = new AffineTransform();
				BufferedImage bi = pixels.getBufferedImage();
				g2d.scale(Controller.inst().getZoom()*4, Controller.inst().getZoom()*4);
				g2d.translate(256 - pixels.width/2, 256 - pixels.height/2);
				g2d.translate(-Controller.inst().getHorizontal(), -Controller.inst().getVertical());
				//g2d.setTransform(Tx);
				g2d.drawImage(bi, null, 0, 0);
			}
		}
		
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
