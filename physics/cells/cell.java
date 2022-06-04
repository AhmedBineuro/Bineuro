package cells;
import java.awt.Color;

import processing.core.PVector;
public class cell {
	private int size;
	//A vector that contains the location of the cell
	private PVector pos;
	//Boolean value indicating if the cell is occupied
	private boolean occupied;
	//String variable to contain the type of the cell
	//This will be used to add "an element" into the cell
	/*By using the types, you can determine how each cell
	 * will be drawn and how each cell behaves*/
	private String type;
	private Color occupiedColor,freeColor;
	//Constructor that uses PVector
	public cell(PVector p,int s)
	{
		pos=p.copy();
		size=s;
		type="";
		occupied=false;
		occupiedColor= Color.BLACK;
		freeColor= Color.WHITE;
	}
	//Constructor that uses PVector floats
	public cell(float x,float y,int s)
	{
		pos=new PVector(x,y);
		size=s;
		type="";
		occupiedColor= Color.BLACK;
		freeColor= Color.WHITE;
	}
	//Getters
	public PVector getPos()
	{
		return pos;
	}
	public float xPos()
	{
		return pos.x;
	}
	public float yPos()
	{
		return pos.y;
	}
	public String getType()
	{
		return type;
	}
	public int getSize()
	{
		return size;
	}
	public boolean isOccupied()
	{
		return occupied;
	}
	//Function to return the color of the cell when occupied
	public Color getOccupiedColor()
	{
		return occupiedColor;
	}
	//Function to return the color of the cell when free
	public Color getFreeColor()
	{
		return freeColor;
	}
	public void clear()
	{
		type="";
		occupiedColor=Color.BLACK;
		occupied=false;
	}
	//Setters
	public void setType(String t)
	{
		type=t;
	}
	//Function to set the color when the cell is occupied
	public void setOccupied(boolean o)
	{
		occupied=o;
	}
	//Function to set the color of the cell when it's free
	public void setFreeColor(Color c)
	{
		freeColor=c;
	}
	public void setAttributes(String t, boolean o)
	{
		type=t;
		occupied=o;
	}
	public void setAttributes(String t, boolean o,Color c)
	{
		type=t;
		occupied=o;
		occupiedColor=c;
	}
	public boolean inCell(PVector obj)
	{
		boolean in= false;
		if(obj.x>(pos.x-size/2)&&obj.x<(pos.x+size/2))
		{
			if(obj.y>(pos.y-size/2)&&obj.y<(pos.y+size/2))
				in=true;
		}
		return in;
	}
	public boolean inCell(float x, float y)
	{
		boolean in= false;
		if(x>(pos.x-size/2)&&x<(pos.x+size/2))
		{
			if(y>(pos.y-size/2)&&y<(pos.y+size/2))
				in=true;
		}
		return in;
	}
}
