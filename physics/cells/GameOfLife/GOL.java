package cells.GameOfLife;
import java.awt.Dimension;
import javax.swing.JFrame;

import cells.Sandbox.CellHandler;
import cells.Sandbox.Cell_Based_Physics;
public class GOL extends JFrame {
	private Dimension d;
	private int FPS;
	GOL(String t,int cellSize,int fps)
	{
		super();
		d=this.getSize();
		this.setSize(1000,1000);
		this.setTitle(t);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		FPS=fps;
		GOLHandler panel= new GOLHandler(this.getSize(),cellSize,FPS);
		this.add(panel);
		this.setVisible(true);
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		GOL cbp= new GOL("Game of life",100,1);
	}

}
