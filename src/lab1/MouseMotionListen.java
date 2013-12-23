package lab1;

import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

public class MouseMotionListen implements MouseMotionListener
{

	@Override
	public void mouseDragged(MouseEvent arg0)
	{
		Controller.inst().updateShape(arg0.getPoint());	
	}

	@Override
	public void mouseMoved(MouseEvent arg0)
	{
	}

}
