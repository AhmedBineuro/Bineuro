package cells.Sandbox;
import java.awt.Dimension;
import javax.swing.JFrame;
public class Cell_Based_Physics extends JFrame {
	private Dimension d;
	private int FPS;
	Cell_Based_Physics(String t,int cellSize,int fps)
	{
		super();
		d=this.getSize();
		this.setSize(1900,1000);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		FPS=fps;
		CellHandler panel= new CellHandler(this.getSize(),cellSize,FPS);
		this.add(panel);
		this.setVisible(true);
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Cell_Based_Physics cbp= new Cell_Based_Physics("Cell Based Physics Simulator",2,1000);
	}

}
