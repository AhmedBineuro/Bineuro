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
	ArrayList<Material> m;
	public CellHandler(Dimension d, int s,int fps)
	{
		super();
		frame=d;
		cellSize=s;
		FPS=fps;
		timer=new Timer((int)(1000/FPS),this);
		int cellsInRow=(int)((d.getWidth()/cellSize));
		int cellsInColumn=(int)((d.getHeight()/cellSize));
		m=new ArrayList<Material>();
		addMouseListener(new Mouse());
		addMouseMotionListener(new mouseMotion());
		for (int i=0;i<cellsInColumn;i++)
		{
			for(int j=0;j<cellsInRow;j++)
			{
				m.add(new Material(cellSize/2+(j*cellSize),cellSize/2+(i*cellSize),cellSize,d));
			}
		}
		timer.start();
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		for(int i=m.size()-1;i>=0;i--)
				m.get(i).update(m,i);
		repaint();	
	}
	@Override
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		Graphics2D g2=(Graphics2D)g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
		for(int i=m.size()-1;i>=0;i--)
			m.get(i).draw(g2);
	}
	private class Mouse extends MouseAdapter 
	{
		public void mouseClicked(MouseEvent e) {
			int index=(int)((e.getX()/cellSize)+((frame.getWidth()/cellSize)*(e.getY()/cellSize)));
			boolean inPanel=((e.getX()>=0&&e.getX()<frame.getWidth())&&(e.getY()>=0&&e.getY()<frame.getHeight()));
			if(inPanel && index<m.size())
			{
				if(e.isShiftDown())
					m.get(index).setMaterial("Water");
				else if(e.isAltDown())
				{
					m.get(index).setMaterial("Smoke");
				}
				else if(e.isControlDown())
				{
					m.get(index).setMaterial("Stone");
				}
				else
					m.get(index).setMaterial("Sand");
			}
		}
	}
	private class mouseMotion extends MouseMotionAdapter
	{


		public void mouseDragged(MouseEvent e) {
			boolean inPanel=((e.getX()>=0&&e.getX()<frame.getWidth())&&(e.getY()>=0&&e.getY()<frame.getHeight()));
			int index=(int)((e.getX()/cellSize)+((frame.getWidth()/cellSize)*(e.getY()/cellSize)));
			if(inPanel && index<m.size())
			{
				if(e.isShiftDown())
					m.get(index).setMaterial("Water");
				else if(e.isAltDown())
				{
					m.get(index).setMaterial("Smoke");
				}
				else if(e.isControlDown())
				{
					m.get(index).setMaterial("Stone");
				}
				else
					m.get(index).setMaterial("Sand");
			}
		}
	}
	
}
