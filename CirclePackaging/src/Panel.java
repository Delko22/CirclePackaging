import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

import java.awt.Graphics;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;


public class Panel extends JPanel implements ActionListener {
	
	private List<Configuration> configurations = new ArrayList<Configuration>();
	private int frameSize = 960;
	private Timer tm = new Timer(10,this);
	private int multiplier = 200;
	
	public Panel() {
	}
	
	public void setConfiguration(Configuration config) {
		configurations.add(new Configuration(config));
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Configuration configuration = configurations.remove(0);
		g.setColor(Color.BLACK);
		int r = (int) Math.round(multiplier * configuration.getOuterCircle().getRadius());
		drawCenteredCircle(g, frameSize/2 ,frameSize/2, r);
		g.setColor(Color.RED);
		for (Circle circle : configuration.getInnerCircles()) {
			int radius = (int) Math.round(multiplier*circle.getRadius());
			int x = (int) Math.round(frameSize/2 + multiplier*circle.getX());
			int y = (int) Math.round(frameSize/2 + multiplier*circle.getY());
			drawCenteredCircle(g, x ,y, radius);
		}
		tm.start();
	}
	
	public void drawCenteredCircle(Graphics g, int x, int y, int r) {
		x = x-r;
		y = y-r;
		g.drawOval(x,y,2*r,2*r);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(configurations.size()>0)
			repaint();
	}
	
//	public static void main(String[] args) {
//		Circle outerCircle = new Circle(100, 0, 0);
//		List<Circle> innerCircles = new ArrayList<Circle>();
//		innerCircles.add(new Circle(50,0,0));
//		innerCircles.add(new Circle(20, 10, 10));
//		Configuration configuration = new Configuration(outerCircle, innerCircles);
//		Panel p = new Panel();
//		p.setConfiguration(configuration);
//		JFrame jf = new JFrame();
//		jf.setTitle("Circle Packing Problem");
//		jf.setSize(960,960);
//		jf.setVisible(true);
//		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		jf.add(p);
//	}

}
