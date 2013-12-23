package cs355;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.util.Iterator;

public class Controller implements CS355Controller
{
	
	private static Controller instance;
	private Color currentColor = Color.GREEN;
	private boolean displayHouse = true;
	private double zoom = 1;
	private int base = 512;
	private double horizontal = base;
	private double vertical = base;
	private Camera camera = new Camera();

	
	public static Controller inst()
    {
        if (instance == null)
        {
            instance = new Controller();
        }
        return instance;
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
	}

	@Override
	public void squareButtonHit()
	{
	}

	@Override
	public void rectangleButtonHit()
	{
	}

	@Override
	public void circleButtonHit()
	{
	}

	@Override
	public void ellipseButtonHit()
	{
	}

	@Override
	public void lineButtonHit()
	{
	}

	@Override
	public void selectButtonHit()
	{
	}

	@Override
	public void zoomInButtonHit()
	{
		if(this.zoom < 4)
		{
			zoom *= 2;
			base *= 2;
		}
		GUIFunctions.setHScrollBarMax(base);
		GUIFunctions.setVScrollBarMax(base);
		GUIFunctions.refresh();
	}

	@Override
	public void zoomOutButtonHit()
	{
		if(zoom > .25)
		{
			zoom /= 2;
			base /= 2;
		}
		GUIFunctions.setHScrollBarMax(base);
		GUIFunctions.setVScrollBarMax(base);
		GUIFunctions.refresh();
	}

	@Override
	public void hScrollbarChanged(int value)
	{
		this.horizontal = value;
		GUIFunctions.refresh();
	}

	@Override
	public void vScrollbarChanged(int value)
	{
		this.vertical = value;
		GUIFunctions.refresh();
	}

	@Override
	public void toggle3DModelDisplay()
	{
		this.displayHouse = !this.displayHouse;
		GUIFunctions.refresh();
	}

	@Override
	public void keyPressed(Iterator<Integer> iterator)
	{
		if(this.displayHouse)
		{
			while(iterator.hasNext())
			{
				switch(iterator.next())
				{
					case KeyEvent.VK_W:
						this.camera.forward(1);
						break;
					case KeyEvent.VK_S:
						this.camera.backwards(1);
						break;
					case KeyEvent.VK_A:
						this.camera.strafeRight(1);
						break;
					case KeyEvent.VK_D:
						this.camera.strafeLeft(1);
						break;
					case KeyEvent.VK_Q:
						this.camera.setYaw(-1);
						break;
					case KeyEvent.VK_E:
						this.camera.setYaw(1);
						break;
					case KeyEvent.VK_R:
						this.camera.setY(1);
						break;
					case KeyEvent.VK_F:
						this.camera.setY(-1);
						break;
					case KeyEvent.VK_H:
						this.camera = new Camera();
						break;
				}
			}
		}
		GUIFunctions.refresh();
	}

	public boolean canDisplayHouse()
	{
		return this.displayHouse;
	}
	
	public double getZoom()
	{
		return this.zoom;
	}
	
	public double getHorizontal()
	{
		return this.horizontal;
	}
	
	public double getVertical()
	{
		return this.vertical;
	}

	public Camera getCamera()
	{
		return this.camera;
	}
}