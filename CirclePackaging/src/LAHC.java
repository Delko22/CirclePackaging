import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class LAHC {

	/*
	 * Produce an initial solution s
	 * Calculate initial cost function C(s)
	 * Let the initial number of steps I = 0
	 * Fora all k in ( 0 .. L-1 ) Ck = C(s)
	 * Do until a stopping condition
	 * 		Construct a candidate solution s*
	 * 		Calculate its cost function C(s*)
	 * 		v = I mod L
	 * 		If C(s*) <= Cv
	 * 		Then accept s*
	 * 		Insert cost value into the list Cv = C(s)
	 * 		Increment a number of steps I = I+1
	 * End do
	 */
	public void doLAHC(int costArrayLength) {
		CostFunction costFunction = new CostFunction();
		Configuration configuration = null; 
		
		configuration = createInitialConfig( xxxxxxxxxxxxxxxxx );
		
		double initialCost = costFunction.calculateCostFunction(configuration);
		double[] costArray = new double[costArrayLength];
		for(int i = 0; i < costArrayLength; i++){
			costArray[i] = initialCost;
		}
		int steps = 0;
		while(true) { 
			Configuration candidate = constructCandidate(configuration);
			double candidateCost = costFunction.calculateCostFunction(candidate);
			int v = steps % costArrayLength;
			if( candidateCost <= costArray[v] ) 
				configuration = candidate;
			
			if ( candidateCost == 0 )
				break;
			
			costArray[v] = candidateCost; //Ik denk dat dit hier moet maar ben niet zeker of het niet in de if moet.
			steps++;
		}
	}
	
	public static void main(String[] args) {
		Circle outerCircle = new Circle(100, 0, 0);
		List<Circle> innerCircles = new ArrayList<Circle>();
		innerCircles.add(new Circle(50,0,0));
		innerCircles.add(new Circle(20, 10, 10));
		Configuration configuration = new Configuration(outerCircle, innerCircles);
		Frame frame = new Frame();
		frame.setConfiguration(configuration);
		frame.paint(null);
		configuration.getOuterCircle().setRadius(200);
		frame.repaint();
	}
	
	public Configuration createInitialConfig(List<Double> radii)
	{
		Circle outerCircle = new Circle(1,0,0);
		Configuration config = new Configuration(outerCircle);
		
		for ( double radius : radii )
		{
			double x = Math.random()*2 - 1;
			double y = Math.random()*2 - 1;
			Circle circle = new Circle(radius,x,y);
			config.addInner(circle);
		}
		
		return config;
	}
	
	public Configuration constructCandidate(Configuration config)
	{
		List<Circle> circles = config.getInnerCircles();

		int indexCircle = (int) Math.round(Math.random()*circles.size());
		
		Circle circle = circles.get(indexCircle);
		circles.remove(indexCircle);
		
		int overlap = selectMove(circle, circles);
		
		
			
	}
	
	public double selectMove(Circle testCircle, List<Circle> restCircles)
	{
		double cost = 0;
		Map<Integer,Double> indices = new HashMap<Integer,Double>();
		
		for ( int i = 0; i < restCircles.size(); i++ )
		{
			cost = restCircles.get(i).getRadius() + testCircle.getRadius() 
					- Math.sqrt(Math.pow((testCircle.getX() - restCircles.get(i).getX()), 2) 
					+ Math.pow((testCircle.getY() - restCircles.get(i).getY()), 2));
			if ( cost > 0 )
				indices.put(i, cost);
		}
		
		int index = selectIndex(indices);
		
		return index;
	}
}
