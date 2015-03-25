
public class Circle {

	private double radius;
	private double x;
	private double y;
	
	public Circle(double radius, double x, double y) {
		super();
		this.radius = radius;
		this.x = x;
		this.y = y;
	}

	public double getRadius() {
		return radius;
	}

	public void setRadius(double radius) {
		this.radius = radius;
	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}
	
	public String toString() {
		return "x: " + this.x + " y: " + this.y + " r: " + this.radius +  "\n";
	}
	
}
