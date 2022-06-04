package cells.Sandbox;
import javax.swing.JPanel;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseMotionListener;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.ArrayList;
import javax.swing.Timer;

import cells.cell;
public class CellHandler extends JPanel implements ActionListener{
	private int cellSize;
	private Dimension frame;
	private Timer timer;
	private int FPS;
	ArrayList<cell> cells;
	public CellHandler(Dimension d, int s,int fps)
	{
		super();
		frame=d;
		cellSize=s;
		FPS=fps;
		timer=new Timer((int)(1000/FPS),this);
		int cellsInRow=(int)((d.getWidth()/cellSize));
		int cellsInColumn=(int)((d.getHeight()/cellSize));
		cells=new ArrayList<cell>();
		addMouseListener(new Mouse());
		addMouseMotionListener(new mouseMotion());
		for (int i=0;i<cellsInColumn;i++)
		{
			for(int j=0;j<cellsInRow;j++)
			{
				cells.add(new cell(cellSize/2+(j*cellSize),cellSize/2+(i*cellSize),cellSize));
			}
		}
		timer.start();
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		update();
		repaint();
		
	}
	@Override
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		Graphics2D g2=(Graphics2D)g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
		update();
		drawCells(g2);
	}
	public void drawCells(Graphics2D g)
	{
		g.setColor(Color.BLACK);
		g.fillRect(0,0,(int)frame.getWidth(),(int)frame.getHeight());
		for(int i=0;i<cells.size();i++)
		{
			
			float x=cells.get(i).xPos();
			float y=cells.get(i).yPos();
			if(cells.get(i).isOccupied())
				g.setColor(cells.get(i).getOccupiedColor());
			else
				g.setColor(cells.get(i).getFreeColor());
			g.fillRect((int)(x-cellSize/2),(int)(y-cellSize/2), cellSize, cellSize);
		}
	}
	private class Mouse extends MouseAdapter 
	{
		public void mouseClicked(MouseEvent e) {
			int index=(int)((e.getX()/cellSize)+((frame.getWidth()/cellSize)*(e.getY()/cellSize)));
			boolean inPanel=((e.getX()>=0&&e.getX()<frame.getWidth())&&(e.getY()>=0&&e.getY()<frame.getHeight()));
			if(index<cells.size()&& inPanel)
			{
				if(e.isShiftDown())
					brush(index,"Water");
				else
					brush(index,"Sand");
			}
		}
	}
	private class mouseMotion extends MouseMotionAdapter
	{


		public void mouseDragged(MouseEvent e) {
			boolean inPanel=((e.getX()>=0&&e.getX()<frame.getWidth())&&(e.getY()>=0&&e.getY()<frame.getHeight()));
			int index=(int)((e.getX()/cellSize)+((frame.getWidth()/cellSize)*(e.getY()/cellSize)));
			if(index<cells.size()&& inPanel)
			{
				if(e.isShiftDown())
					brush(index,"Water");
				else
					brush(index,"Sand");
			}
		}
	}
	
	//Function to handle different materials
	void handleMaterials(cell c,int index)
	{
		String type=c.getType();
		if(type=="")
			return;
		else if(type=="Sand")
		{
			Sand(c,index);	
		}
		else if(type=="Water")
		{
			Water(c,index);
		}
	}
	
	public void update()
	{
		for(int i=cells.size()-1;i>=0;i--)
		{
			handleMaterials(cells.get(i),i);
		}
	}
	public void Sand(cell c,int index)
	{
		String type=c.getType();
		Color col=c.getOccupiedColor();
		int cellsInRow=(int)(frame.getWidth()/cellSize);
		int maxY= (int)(frame.getHeight()/cellSize)-1;
		int yIndex=(int)(c.yPos()/cellSize);
		int xIndex=(int)((c.xPos()/cellSize));
		//Bottom cell index
		int bIndex=index+cellsInRow;
		//Bottom right cell index
		int brIndex=-1,blIndex=-1;
		if(xIndex<cellsInRow-1)
			brIndex=index+(cellsInRow+1);
		if(xIndex>0)
			blIndex=index+(cellsInRow-1);
		if(bIndex>=0 && bIndex<cells.size())
		{
			
			if(!cells.get(bIndex).isOccupied()||cells.get(bIndex).getType()=="Water")
			{
				if(cells.get(bIndex).getType()=="Water")
				{
					cell temp=cells.get(bIndex);
					cells.get(index).setAttributes(temp.getType(),true,temp.getOccupiedColor());
				}
				else
					cells.get(index).clear();
				cells.get(bIndex).setAttributes(type, true,col);
				return;
			}
			if(blIndex>=0 && blIndex<cells.size())
			{
				if(!cells.get(blIndex).isOccupied()||cells.get(blIndex).getType()=="Water")
				{
					if(cells.get(blIndex).getType()=="Water")
					{
						cell temp=cells.get(blIndex);
						cells.get(index).setAttributes(temp.getType(),true,temp.getOccupiedColor());
					}
					else
						cells.get(index).clear();
					cells.get(blIndex).setAttributes(type, true,col);
					return;
				}
			}
			if(brIndex>=0 && brIndex<cells.size())
			{
					if(!cells.get(brIndex).isOccupied()||cells.get(brIndex).getType()=="Water")
					{
						if(cells.get(brIndex).getType()=="Water")
						{
							cell temp=cells.get(brIndex);
							cells.get(index).setAttributes(temp.getType(),true,temp.getOccupiedColor());
						}
						else
							cells.get(index).clear();
						cells.get(brIndex).setAttributes(type, true,col);
						return;
					}
			}
		}
	}
	
	public void Water(cell c,int index)
	{
		String type=c.getType();
		Color col=c.getOccupiedColor();
		int cellsInRow=(int)(frame.getWidth()/cellSize);
		int maxY= (int)(frame.getHeight()/cellSize)-1;
		int yIndex=(int)(c.yPos()/cellSize);
		int xIndex=(int)((c.xPos()/cellSize));
		//Bottom cell index
		int bIndex=index+cellsInRow;
		//Bottom right , bottom left, top right and top left cell indexes
		int brIndex=-1,blIndex=-1;
		if(xIndex<cellsInRow-1)
			brIndex=index+(cellsInRow+1);
		if(xIndex>0)
			blIndex=index+(cellsInRow-1);
		if(bIndex>=0 && bIndex<cells.size())
		{
			
			if(!cells.get(bIndex).isOccupied())
			{
				cells.get(index).clear();
				cells.get(bIndex).setAttributes(type, true,col);
				return;
			}
			if(blIndex>=0 && blIndex<cells.size())
			{
				if(!cells.get(blIndex).isOccupied())
				{
					cells.get(index).clear();
					cells.get(blIndex).setAttributes(type, true,col);
					return;
				}
			}
			if(brIndex>=0 && brIndex<cells.size())
			{
					if(!cells.get(brIndex).isOccupied())
					{
						cells.get(index).clear();
						cells.get(brIndex).setAttributes(type, true,col);
						return;
					}
			}
			if(yIndex<maxY+1)
			{
				if(xIndex-1>=0&&xIndex+1<=(cellsInRow-1))
				{
					if(!cells.get(index-1).isOccupied())
					{
						cells.get(index).clear();
						cells.get(index-1).setAttributes(type, true,col);
						return;
					}
					if(!cells.get(index+1).isOccupied())
					{
						cells.get(index).clear();
						cells.get(index+1).setAttributes(type, true,col);
						return;
					}
				}
			}
		}
	}
 	public void brush(int index, String t)
	{
		Color col=Color.BLACK;
		if(index>0&&index<cells.size())
		{
			int yIndex=(int)(cells.get(index).yPos()/cellSize);
			int xIndex=(int)((cells.get(index).xPos()/cellSize));
			int cellsInRow=(int)(frame.getWidth()/cellSize);
			if(t=="Sand")
			{
				col= new Color(244,164,96);
			}
			if(t=="Water")
			{
				col= new Color(83,109,238);
			}
			cells.get(index).setAttributes(t,true,col);
			if(xIndex>0)
			{
				cells.get(index-1).setAttributes(t,true,col);
				if(yIndex>0)
				{
					cells.get(index-cellsInRow).setAttributes(t,true,col);
					cells.get(index-cellsInRow-1).setAttributes(t,true,col);
				}
				
			}
		}
	}
}
