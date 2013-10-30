package CS355.LWJGL;


//You might notice a lot of imports here.
//You are probably wondering why I didn't just import org.lwjgl.opengl.GL11.*
//Well, I did it as a hint to you.
//OpenGL has a lot of commands, and it can be kind of intimidating.
//This is a list of all the commands I used when I implemented my project.
//Therefore, if a command appears in this list, you probably need it.
//If it doesn't appear in this list, you probably don't.
//Of course, your milage may vary. Don't feel restricted by this list of imports.
import java.util.Iterator;

import org.lwjgl.input.Keyboard;

import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_LINES;
import static org.lwjgl.opengl.GL11.GL_MODELVIEW;
import static org.lwjgl.opengl.GL11.GL_PROJECTION;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glColor3f;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glLoadIdentity;
import static org.lwjgl.opengl.GL11.glMatrixMode;
import static org.lwjgl.opengl.GL11.glPushMatrix;
import static org.lwjgl.opengl.GL11.glRotatef;
import static org.lwjgl.opengl.GL11.glTranslatef;
import static org.lwjgl.opengl.GL11.glVertex3d;
import static org.lwjgl.opengl.GL11.glViewport;
import static org.lwjgl.opengl.GL11.glOrtho;
import static org.lwjgl.util.glu.GLU.gluPerspective;

/**
 *
 * @author Brennan Smith
 */
public class StudentLWJGLController implements CS355LWJGLController 
{
  
  
//This is a model of a house.
  //It has a single method that returns an iterator full of Line3Ds.
  //A "Line3D" is a wrapper class around two Point2Ds.
  //It should all be fairly intuitive if you look at those classes.
  //If not, I apologize.
  private WireFrame model = new HouseModel();
  private Camera camera = new Camera();

  //This method is called to "resize" the viewport to match the screen.
  //When you first start, have it be in perspective mode.
  @Override
  public void resizeGL() 
  {
	  glMatrixMode(GL_PROJECTION);
	  glLoadIdentity();
	  float fovy = 60.0f;
	  float aspect = (float)LWJGLSandbox.DISPLAY_WIDTH/(float)LWJGLSandbox.DISPLAY_HEIGHT;
	  float  zNear = 1f;
	  float zFar = 100f;
	  gluPerspective(fovy, aspect, zNear, zFar);
	  glMatrixMode(GL_MODELVIEW);
	  glLoadIdentity();
  }

    @Override
    public void update() 
    {
        //TODO later lab
    }

    //This is called every frame, and should be responsible for keyboard updates.
    //An example keyboard event is captured below.
    //The "Keyboard" static class should contain everything you need to finish
    // this up.
    @Override
    public void updateKeyboard() 
    {
    	//TODO
        if(Keyboard.isKeyDown(Keyboard.KEY_W)) 
        {
            this.camera.forward(1);
        }
        if(Keyboard.isKeyDown(Keyboard.KEY_S))
        {
        	this.camera.backwards(1);
        }
        if(Keyboard.isKeyDown(Keyboard.KEY_A))
        {
        	this.camera.strafeLeft(1);
        }
        if(Keyboard.isKeyDown(Keyboard.KEY_D))
        {
        	this.camera.strafeRight(1);
        }
        if(Keyboard.isKeyDown(Keyboard.KEY_Q))
        {
        	this.camera.setYaw(-1);
        }
        if(Keyboard.isKeyDown(Keyboard.KEY_E))
        {
        	this.camera.setYaw(1);
        }
        if(Keyboard.isKeyDown(Keyboard.KEY_R))
        {
            this.camera.setY(1);
        }
        if(Keyboard.isKeyDown(Keyboard.KEY_F))
        {
        	this.camera.setY(-1);
        }
        if(Keyboard.isKeyDown(Keyboard.KEY_O))
        {
            glMatrixMode(GL_PROJECTION);
            glLoadIdentity();
            glOrtho(-6f, 6f, -6f, 6f, 1f, 100f);
      	  	glMatrixMode(GL_MODELVIEW);
      	  	glLoadIdentity();
        }
        if(Keyboard.isKeyDown(Keyboard.KEY_P))
        {
        	glMatrixMode(GL_PROJECTION);
            glLoadIdentity();
      	  	float fovy = 60.0f;
      	  	float aspect = (float)LWJGLSandbox.DISPLAY_WIDTH/(float)LWJGLSandbox.DISPLAY_HEIGHT;
      	  	float  zNear = 1f;
      	  	float zFar = 100f;
      	  	gluPerspective(fovy, aspect, zNear, zFar);
      	  	glMatrixMode(GL_MODELVIEW);
      	  	glLoadIdentity();
        }
        if(Keyboard.isKeyDown(Keyboard.KEY_X))
        {
        	this.camera = new Camera();
        }
    }

    //This method is the one that actually draws to the screen.
    @Override
    public void render() 
    {
    	//TODO
        //This clears the screen.
        glClear(GL_COLOR_BUFFER_BIT);
        
        glLoadIdentity();
        this.lookThrough();
        
        glColor3f(0, 0, 255);
        
        glBegin(GL_LINES);
        
        Iterator<Line3D> it = this.model.getLines();
        
        while(it.hasNext())
        {
        	Line3D line = it.next();
        	glVertex3d(line.start.x, line.start.y, line.start.z);
        	glVertex3d(line.end.x, line.end.y, line.end.z);
        }
        
        glEnd();
        
        //Do your drawing here.
    }
    
    public void lookThrough()
    {
    	float yaw = this.camera.getYaw();
    	Point3D position = this.camera.getLocation();

        //roatate the yaw around the Y axis
        glRotatef(yaw, 0.0f, 1.0f, 0.0f);
        //translate to the position vector's location
        glTranslatef((float)position.x, (float)position.y, (float)position.z);
    }
}
