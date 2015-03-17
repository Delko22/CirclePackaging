import java.util.List;


public class Configuration {

	private Circle outerCircle;
	private List<Circle> innerCircles;
	
	public Configuration(Circle outerCircle, List<Circle> innerCircles) {
		super();
		this.outerCircle = outerCircle;
		this.innerCircles = innerCircles;
	}

	public Circle getOuterCircle() {
		// TODO Auto-generated method stub
		return outerCircle;
	}

	public List<Circle> getInnerCircles() {
		// TODO Auto-generated method stub
		return innerCircles;
	}

}
