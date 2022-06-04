package cells.GameOfLife;
import cells.cell;
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

public class GOLHandler extends JPanel implements ActionListener {
	private int cellSize;
	private Dimension frame;
	private Timer timer;
	private boolean paused;
	private int FPS;
	ArrayList<cell> cells;
	public GOLHandler(Dimension d, int s,int fps)
	{
		super();
		frame=d;
		cellSize=s;
		FPS=fps;
		paused=true;
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
			g.setColor(Color.BLACK);
			g.drawRect((int)(x-cellSize/2),(int)(y-cellSize/2), cellSize, cellSize);
		}
	}
	private class Mouse extends MouseAdapter 
	{
		public void mouseClicked(MouseEvent e) 
		{
			if(e.isShiftDown())
			{
				paused=!paused;
			}
			else if(paused)
				handleClick(e.getX(),e.getY());
		}
	}
	private class mouseMotion extends MouseMotionAdapter
	{
		public void mouseDragged(MouseEvent e) 
		{
			if(e.isShiftDown())
			{
				paused=!paused;
			}
			else if(paused)
				handleClick(e.getX(),e.getY());
		}
	}
	public void advanceCells(cell c)
	{
		int cellsInRow=(int)(frame.getWidth()/cellSize);
		int cellsInColumn= (int)(frame.getHeight()/cellSize)-1;
		int yIndex=(int)(c.yPos()/cellSize);
		int xIndex=(int)((c.xPos()/cellSize));
		int index=xIndex+(int)(cellsInRow*yIndex);
		int counter=0;
		// 4 Directions
		int indexAbove=index-cellsInRow;
		int indexBelow=index+cellsInRow;
		int indexRight=index+1;
		int indexLeft=index-cellsInRow;
		//4 Sub -directions
		int indexAboveRight=index-cellsInRow+1;
		int indexAboveLeft=index-cellsInRow-1;
		int indexBelowRight=index+cellsInRow+1;
		int indexBelowLeft=index+cellsInRow-1;
		if(yIndex>0)
		{
			if(cells.get(indexAbove).isOccupied())
				counter++;
//			if((yIndex>=0&&xIndex>0)&&(yIndex<(cellsInColumn-1)&&xIndex<(cellsInRow-1)))
//			{
//				//Below
//				if(cells.get(indexBelowLeft).isOccupied())
//					counter++;
//				if(cells.get(indexBelow).isOccupied())
//					counter++;
//				if(cells.get(indexBelowRight).isOccupied())
//					counter++;
//				//Right and Left
//				if(cells.get(indexLeft).isOccupied())
//					counter++;
//				if(cells.get(indexRight).isOccupied())
//					counter++;
//				//Above
//				if(cells.get(indexAboveLeft).isOccupied())
//					counter++;
//				if(cells.get(indexAbove).isOccupied())
//					counter++;
//				if(cells.get(indexAboveRight).isOccupied())
//					counter++;
//			}
//			else
//			{
//				if(yIndex==0)
//				{
//					if(cells.get(indexBelow).isOccupied())
//						counter++;
//					if(xIndex==0)
//					{
//						if(cells.get(indexBelowRight).isOccupied())
//							counter++;
//						if(cells.get(indexRight).isOccupied())
//							counter++;
//					}
//					else if(xIndex==cellsInRow-1)
//					{
//						if(cells.get(indexBelowLeft).isOccupied())
//							counter++;
//						if(cells.get(indexLeft).isOccupied())
//							counter++;
//					}
//					else if(xIndex==cellsInRow-1)
//					{
//						if(cells.get(indexBelowLeft).isOccupied())
//							counter++;
//						if(cells.get(indexLeft).isOccupied())
//							counter++;
//						if(cells.get(indexBelowRight).isOccupied())
//							counter++;
//						if(cells.get(indexRight).isOccupied())
//							counter++;
//					}
//				}
//				else if(yIndex==cellsInColumn-1)
//				{
//					if(cells.get(indexAbove).isOccupied())
//						counter++;
//					if(xIndex==0)
//					{
//						if(cells.get(indexAboveRight).isOccupied())
//							counter++;
//						if(cells.get(indexRight).isOccupied())
//							counter++;
//					}
//					else if(xIndex==cellsInRow-1)
//					{
//						if(cells.get(indexAboveLeft).isOccupied())
//							counter++;
//						if(cells.get(indexLeft).isOccupied())
//							counter++;
//					}
//					else if(xIndex==cellsInRow-1)
//					{
//						if(cells.get(indexAboveLeft).isOccupied())
//							counter++;
//						if(cells.get(indexLeft).isOccupied())
//							counter++;
//						if(cells.get(indexAboveRight).isOccupied())
//							counter++;
//						if(cells.get(indexRight).isOccupied())
//							counter++;
//					}
//				}		
//			}
			//Conditions
			if(counter==0||(counter>3&&c.isOccupied()))
			{
				c.setOccupied(false);
			}
			else if(counter>0&&counter<=3&&c.isOccupied())
			{
				return;
			}
			else if(counter==3&&!c.isOccupied())
			{
				c.setOccupied(true);
			}
		}
		
	}
	public void update()
	{
		if(!paused)
		{
			for(int i=0;i<cells.size();i++)
			{
				advanceCells(cells.get(i));
			}
		}
	}
	void handleClick(float x, float y)
	{
		int cellsInRow=(int)(frame.getWidth()/cellSize);
		int cellsInColumn=(int)(frame.getHeight()/cellSize);
		int xIndex= (int)(x/cellSize);
		int yIndex= (int)(y/cellSize);
		int index=xIndex+(int)(cellsInRow*yIndex);
		if(index>=0&&index<cells.size())
		{
			boolean b=cells.get(index).isOccupied();
			cells.get(index).setOccupied(!b);
		}
	}
}
