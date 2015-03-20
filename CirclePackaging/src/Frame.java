import javax.swing.JFrame;
import java.awt.Graphics;
import java.awt.Color;


public class Frame extends JFrame {
	
	private Configuration configuration;
	private int frameSize = 960;
	public Frame() {
		setTitle("Circle packing problem");
		setSize(frameSize,frameSize);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
	public void setConfiguration(Configuration config) {
		this.configuration = config;
	}
	
	public void paint(Graphics g) {
		g.setColor(Color.BLACK);
		int r = (int) Math.round(configuration.getOuterCircle().getRadius());
		drawCenteredCircle(g, frameSize/2 ,frameSize/2, r);	
		//System.out.println(r);
		for (Circle circle : configuration.getInnerCircles()) {
			g.setColor(Color.RED);
			int radius = (int) Math.round(circle.getRadius());
			int x = (int) Math.round(frameSize/2 + circle.getX());
			int y = (int) Math.round(frameSize/2 + circle.getY());
			//System.out.println(radius + " " + x + " " + y);
			drawCenteredCircle(g, x ,y, radius);		
		}
	}
	
	public void drawCenteredCircle(Graphics g, int x, int y, int r) {
		x = x-(r/2);
		y = y-(r/2);
		g.drawOval(x,y,r,r);
	}
}
