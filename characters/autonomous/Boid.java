package autonomous;
import java.util.ArrayList;
import processing.core.PVector;
import java.awt.Dimension;
public class Boid 
{
	private PVector pos,vel,acc,steer,target;
	private int size,radius;
	private float maxSteer,maxSpeed;
	private double windowWidth,windowHeight;
	private Dimension window;
	private boolean wandering;
	//Default constructor
	Boid(Dimension d)
	{
		window=d;
		windowWidth=d.getWidth();
		windowHeight=d.getHeight();
		//Generate the Boid in any position on the window
		pos=new PVector((float)(size+Math.random()*(d.getWidth()-size)),
				(float)(size+Math.random()*(d.getHeight()-size)));
		size=10;
		maxSteer=0.1f;
		maxSpeed=5;
		radius=size*3/2;
		wandering=true;
	}
	//Constructor with position,size,max force, and max speed. The velocity, acceleration, force,
	//and target vectors will be generated later. The dimension object is used to detect the window size
	//to set border awareness.
	Boid(PVector position,int size,float maxSteer,float maxSpeed,Dimension d)
	{
		window=d;
		windowWidth=d.getWidth();
		windowHeight=d.getHeight();
		pos=position.copy();
		this.size=size;
		this.maxSteer=maxSteer;
		this.maxSpeed=maxSpeed;
		radius=size*3/2;
		wandering=true;
	}
	//Constructor with two floats for position
	Boid(float x, float y,int size,float maxSteer,float maxSpeed,Dimension d)
	{
		window=d;
		windowWidth=d.getWidth();
		windowHeight=d.getHeight();
		pos=new PVector(x,y);
		this.size=size;
		this.maxSteer=maxSteer;
		this.maxSpeed=maxSpeed;
		radius=size*3/2;
		wandering=true;
	}
	//Constructor with random generation and parameters
	Boid(int size,float maxSteer,float maxSpeed,Dimension d)
	{
		window=d;
		windowWidth=d.getWidth();
		windowHeight=d.getHeight();
		pos=new PVector((float)(size+Math.random()*(d.getWidth()-size)),
				(float)(size+Math.random()*(d.getHeight()-size)));
		this.size=size;
		this.maxSteer=maxSteer;
		this.maxSpeed=maxSpeed;
		radius=size*3/2;
		wandering=true;
	}
	
	//Getters
	public PVector getPos()
	{
		return pos;
	}
	public PVector getVel()
	{
		return vel;
	}
	public PVector getTarget()
	{
		return target;
	}
	public int getSize()
	{
		return size;
	}
	//Return a a Dimension object representing the window
	public Dimension getDimension()
	{
		return window;
	}
	public boolean getWandering()
	{
		return wandering;
	}
	
	//Setters
	
	//Function to set the Dimension object to get the updated size of the window
	void setDimensions(Dimension d)
	{
		window=d;
		windowWidth=d.getWidth();
		windowHeight=d.getHeight();
	}
	//Function to set position
	void setPos(PVector p)
	{
		pos=p.copy();
	}
	//function to set position using two floats
	void setPos(int x, int y)
	{
		pos.x=x;
		pos.y=y;
	}
	//Function to set target
	void setTarget(PVector t)
	{
		target=t.copy();
	}
	//Function to set target with two floats
	void setTarget(int x, int y)
	{
		target.x=x;
		target.y=y;
	}
	//Function to set the size
	void setSize(int s)
	{
		size=s;
	}
	//Function to set radius of the circle used to generate wandering vector 
	void setRadius(int r)
	{
		radius=r;
	}
	//function to set the max speed of the Boid
	void setMaxSpeed(float ms)
	{
		maxSpeed=ms;
	}
	//Function to set the maximum steering force of the Boid
	void setMaxSteer(float ms)
	{
		maxSteer=ms;
	}
	//Function to set the wandering boolean in charge of activating
	//the wandering behaviour in the update function 
	void setWandering(boolean w)
	{
		wandering=w;
	}
	
	
	//Movements methods
	
	//Functions to seek a target. The apply boolean is responsible to see whether you want to apply the force to the
	//acceleration or not
	public void seek(PVector t, boolean apply)
	{
		target=t.copy();
		seek(apply);
	}
	public void seek(int x, int y,boolean apply)
	{
		target.x=x;
		target.y=y;
		seek(apply);
	}
	public void seek(boolean apply)
	{
		PVector desired= PVector.sub(target,pos);
	    desired.mult((float)0.05);
	    desired.limit(maxSpeed);
	    steer=PVector.sub(desired,vel);
	    steer.limit(maxSteer);
	    if(apply)
	    applyForce();
	}
	//Functions to apply the force/steering force to the acceleration vector
	public void applyForce()
	{
		acc.add(steer);
	}
	public void applyForce(PVector force)
	{
			acc.add(force);
	}
	public void applyForce(float x,float y)
	{
			acc.add(new PVector(x,y));
	}
	
	//function responsible for movement and updating positions
	void move()
	{
		vel.add(acc);
	    vel.limit(maxSpeed);
	    pos.add(vel);
	    acc.mult(0);
	}
	private void avoidWall()
	{
		PVector desired=PVector.sub(target,pos);
	    if(pos.x>windowWidth-size*2)
	      desired=new PVector(-maxSpeed,vel.y);
	    else if(pos.x<size*2)
	      desired=new PVector(maxSpeed,vel.y);
	    else if(pos.y>windowHeight-size*2)
	      desired=new PVector(vel.x,-maxSpeed);
	    else if(pos.y<size*2)
	      desired=new PVector(vel.x,maxSpeed);
	    steer=PVector.sub(desired,vel);
	    steer.limit(maxSteer);
	    applyForce();
	}
	void wander(boolean seek,boolean applyForce)
	  {
	      PVector temp=PVector.mult(vel,radius);
	      temp.normalize();
	      temp.mult(radius*4);
	      PVector newPos=PVector.add(pos,temp);
	      PVector newVel= new PVector(radius,radius);
	      newVel.limit(radius);
	      float angle= (float)(-2*Math.PI+Math.random()*4*Math.PI);
	      newVel.rotate(angle);
	      newVel.limit(radius);
	      newPos.add(newVel);
	      target=newPos.copy();
	      if(seek)
	    	  seek(applyForce);
	  }
	//Function to perform the wandering behaviour
	void wander()
	  {
	      PVector temp=PVector.mult(vel,radius);
	      temp.normalize();
	      temp.mult(radius*4);
	      PVector newPos=PVector.add(pos,temp);
	      PVector newVel= new PVector(radius,radius);
	      newVel.limit(radius);
	      float angle= (float)(-2*Math.PI+Math.random()*4*Math.PI);
	      newVel.rotate(angle);
	      newVel.limit(radius);
	      newPos.add(newVel);
	      target=newPos.copy();
	  }
	//Update function
	public void update()
	 {	if(wandering)
		 wander();
		seek(false);
	    avoidWall();
	    applyForce();
	    move();
	 }

}
