package lab1;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class MouseListen implements MouseListener
{
	@Override
	public void mouseClicked(MouseEvent arg0)
	{
		Controller.inst().clicked(arg0.getPoint());
	}

	@Override
	public void mouseEntered(MouseEvent arg0)
	{
	}

	@Override
	public void mouseExited(MouseEvent arg0)
	{
	}

	@Override
	public void mousePressed(MouseEvent arg0)
	{
		Controller.inst().startShape(arg0.getPoint());
	}

	@Override
	public void mouseReleased(MouseEvent arg0)
	{
		Controller.inst().finishUpdate();		
	}

}
