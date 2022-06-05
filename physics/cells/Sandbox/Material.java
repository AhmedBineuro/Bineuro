package cells.Sandbox;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;

import cells.cell;
import processing.core.PVector;
public class Material extends cell{
	private boolean fluid,solid,grainy,flammable,gas;
	private Dimension window;
	public Material(PVector p,int s,Dimension d)
	{
		super(p,s);
		window=d;
		flammable=false;
		fluid=false;
		solid=false;
		grainy=false;
		gas=false;
		setOccupied(false);
	}
	public Material(float x, float y,int s,Dimension d)
	{
		super(x,y,s);
		window=d;
		flammable=false;
		fluid=false;
		solid=false;
		grainy=false;
		gas=false;
		setOccupied(false);
	}
	public boolean isFlammable()
	{
		return flammable;
	}
	public void setMaterial(String t)
	{
		if(t=="Sand")
		{
			setAttributes(t,true,new Color(244,164,96));
			flammable=false;
			solid=false;
			grainy=true;
			fluid=false;
			gas=false;
		}
		else if(t=="Water")
		{
			setAttributes(t,true,new Color(83,109,238));
			flammable=false;
			solid=false;
			grainy=false;
			fluid=true;
			gas=false;
		}
		else if(t=="Gun Powder")
		{
			setAttributes(t,true,new Color(77,77,77));
			flammable=true;
			solid=false;
			grainy=true;
			fluid=false;
			gas=false;
		}
		else if(t=="Gasoline")
		{
			setAttributes(t,true,new Color(179,179,0));
			flammable=true;
			solid=false;
			grainy=false;
			fluid=true;
			gas=false;
		}
		else if(t=="Fire")
		{
			setAttributes(t,true,new Color(255, 51, 0));
			flammable=false;
			solid=false;
			grainy=false;
			fluid=false;
			gas=false;
		}
		else if(t=="Stone")
		{
			setAttributes(t,true,new Color(115, 115, 115));
			flammable=false;
			solid=true;
			grainy=false;
			fluid=false;
			gas=false;
		}
		else if (t=="Wood")
		{
			setAttributes(t,true,new Color(128, 64, 0));
			flammable=true;
			solid=true;
			grainy=false;
			fluid=false;
			gas=false;
		}
		else if (t=="Ash")
		{
			setAttributes(t,true,new Color(0,0,0));
			flammable=false;
			solid=false;
			grainy=true;
			fluid=false;
			gas=false;
		}
		else if(t=="Smoke")
		{
			setAttributes(t,true,new Color(180,180,180));
			flammable=false;
			solid=false;
			grainy=false;
			fluid=false;
			gas=true;
		}
		else if(t=="")
		{
			clear();
			flammable=false;
			solid=false;
			grainy=false;
			fluid=false;
			gas=false;
		}
	}
	public void grainMove(ArrayList<Material> m,int index)
	{
		int cellsInRow=(int)(window.getWidth()/getSize());
		int maxIndex=m.size()-1-cellsInRow;//To avoid updating the bottom row
		int yIndex=(int)(yPos()/m.get(index).getSize());
		int xIndex=(int)((xPos()/m.get(index).getSize()));
		String type=m.get(index).getType();
		Color col=m.get(index).getOccupiedColor();
		if(index>=maxIndex)
			return;
		else
		{
			//Bottom cell index
			int bIndex=index+cellsInRow;
			//Bottom right cell index
			int brIndex=-1,blIndex=-1;
			if(xIndex<cellsInRow-1)
				brIndex=index+(cellsInRow+1);
			if(xIndex>0)
				blIndex=index+(cellsInRow-1);
			if(bIndex>=0 && bIndex<m.size())
				if(!m.get(bIndex).isOccupied()||m.get(bIndex).getType()=="Water")
				{
					if(m.get(bIndex).getType()=="Water")
					{
						cell temp=m.get(bIndex);
						m.get(index).setAttributes(temp.getType(),true,temp.getOccupiedColor());
					}
					else
						m.get(index).setMaterial("");
					m.get(bIndex).setMaterial(type);
					return;
				}
				if(blIndex>=0 && blIndex<m.size())
				{
					if(!m.get(blIndex).isOccupied()||m.get(blIndex).getType()=="Water")
					{
						if(m.get(blIndex).getType()=="Water")
						{
							cell temp=m.get(blIndex);
							m.get(index).setMaterial(temp.getType());
						}
						else
							m.get(index).setMaterial("");
						m.get(blIndex).setMaterial(type);
						return;
					}
				}
				if(brIndex>=0 && brIndex<m.size())
				{
						if(!m.get(brIndex).isOccupied()||m.get(brIndex).getType()=="Water")
						{
							if(m.get(brIndex).getType()=="Water")
							{
								cell temp=m.get(brIndex);
								m.get(index).setMaterial(temp.getType());
							}
							else
								m.get(index).setMaterial("");
							m.get(brIndex).setMaterial(type);
							return;
						}
				}
		}
	}
	public void fluidMove(ArrayList<Material> m,int index)
	{
		int cellsInRow=(int)(window.getWidth()/getSize());
		int maxIndex=m.size()-1-cellsInRow;//To avoid updating the bottom row
		int yIndex=(int)(yPos()/m.get(index).getSize());
		int xIndex=(int)((xPos()/m.get(index).getSize()));
		String type=m.get(index).getType();
		Color col=m.get(index).getOccupiedColor();
		if(index>=maxIndex)
			return;
		else
		{
			//Bottom cell index
			int bIndex=index+cellsInRow;
			//Bottom right cell index
			int brIndex=-1,blIndex=-1;
			if(xIndex<cellsInRow-1)
				brIndex=index+(cellsInRow+1);
			if(xIndex>0)
				blIndex=index+(cellsInRow-1);
			if(bIndex>=0 && bIndex<m.size())
				if(!m.get(bIndex).isOccupied())
				{
					m.get(index).setMaterial("");
					m.get(bIndex).setMaterial(type);
					return;
				}
			if(blIndex>=0 && blIndex<m.size())
			{
				if(!m.get(blIndex).isOccupied())
				{
					m.get(index).setMaterial("");
					m.get(blIndex).setMaterial(type);
					return;
				}
			}
			if(brIndex>=0 && brIndex<m.size())
			{
					if(!m.get(brIndex).isOccupied())
					{
						m.get(index).setMaterial("");
						m.get(brIndex).setMaterial(type);
						return;
					}
			}
			if(xIndex>0)
			{
				if(!m.get(index-1).isOccupied())
				{
					m.get(index).setMaterial("");
					m.get(index-1).setMaterial(type);
					return;
				}
			}
			if(xIndex<(cellsInRow-1))
			{
				if(!m.get(index+1).isOccupied())
				{
					m.get(index).setMaterial("");
					m.get(index+1).setMaterial(type);
					return;
				}
			}
		}
	}
	public void gasMove(ArrayList<Material> m,int index)
	{
		int cellsInRow=(int)(window.getWidth()/getSize());
		int maxIndex=cellsInRow-1;//To avoid updating the bottom row
		int yIndex=(int)(yPos()/m.get(index).getSize());
		int xIndex=(int)((xPos()/m.get(index).getSize()));
		String type=m.get(index).getType();
		Color col=m.get(index).getOccupiedColor();
		if(index<=maxIndex)
			return;
		else
		{
			//Bottom cell index
			int uIndex=index-cellsInRow;
			//Bottom right cell index
			int urIndex=-1,ulIndex=-1;
			if(xIndex<cellsInRow-1)
				urIndex=index-(cellsInRow+1);
			if(xIndex>0)
				ulIndex=index-(cellsInRow-1);
			if(uIndex>=0 && uIndex<m.size())
				if(!m.get(uIndex).isOccupied())
				{
					m.get(index).setMaterial("");
					m.get(uIndex).setMaterial(type);
					return;
				}
			if(ulIndex>=0 && ulIndex<m.size())
			{
				if(!m.get(ulIndex).isOccupied())
				{
					m.get(index).setMaterial("");
					m.get(ulIndex).setMaterial(type);
					return;
				}
			}
			if(urIndex>=0 && urIndex<m.size())
			{
					if(!m.get(urIndex).isOccupied())
					{
						m.get(index).setMaterial("");
						m.get(urIndex).setMaterial(type);
						return;
					}
			}
			if(xIndex>0)
			{
				if(!m.get(index-1).isOccupied())
				{
					m.get(index).setMaterial("");
					m.get(index-1).setMaterial(type);
					return;
				}
			}
			if(xIndex<(cellsInRow-1))
			{
				if(!m.get(index+1).isOccupied())
				{
					m.get(index).setMaterial("");
					m.get(index+1).setMaterial(type);
					return;
				}
			}
		}
	}
	public void update(ArrayList<Material> m,int index)
	{
		if(grainy)
			grainMove(m,index);
		if(fluid)
			fluidMove(m,index);
		if(gas)
			gasMove(m,index);
		if(solid)
			return;
	}
	
	public void draw(Graphics2D g)
	{
		float x=xPos();
		float y=yPos();
		if(isOccupied())
			g.setColor(getOccupiedColor());
		else
			g.setColor(getFreeColor());
		g.fillRect((int)(x-getSize()/2),(int)(y-getSize()/2), getSize(), getSize());
	}
}
