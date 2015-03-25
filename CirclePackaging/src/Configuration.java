import java.util.ArrayList;
import java.util.List;


public class Configuration {

	private Circle outerCircle;
	private List<Circle> innerCircles;
	
	public Configuration(Circle outerCircle, List<Circle> innerCircles) {
		super();
		this.outerCircle = outerCircle;
		this.innerCircles = innerCircles;
	}
	
	public Configuration(Circle outerCircle)
	{
		this.outerCircle = outerCircle;
		this.innerCircles = new ArrayList<Circle>();
	}

	public Circle getOuterCircle() {
		return outerCircle;
	}

	public List<Circle> getInnerCircles() {
		return innerCircles;
	}
	
	public void setInnerCircles(List<Circle> innerCircles)
	{
		this.innerCircles = innerCircles;
	}
	
	public void setOuterCircle(Circle outerCircle)
	{
		this.outerCircle = outerCircle;
	}
	
	public void addInner(Circle innerCircle)
	{
		this.innerCircles.add(innerCircle);
	}
	
	public String toString() {
		String s = "Configuration\n";
		for(Circle c : innerCircles) {
			s +=  c;
		}
		return s;
	}

}
