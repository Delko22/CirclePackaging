import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JFrame;


public class LAHC {
	
	private double moveAmount = 0.1;
	private List<Integer> previousCircles = new ArrayList<Integer>();

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
		List<Double> radii = new Reader().readRadii("C:\\Bestanden\\School\\Capita Selecta\\IN9_1-9.txt");///home/katrijne/git/CirclePackaging/CirclePackaging/src/testInstances/NR10_1-10.txt
		configuration = createInitialConfig( radii );
		
		Panel panel = createPanel();
		panel.setConfiguration(configuration);
		
		double initialCost = costFunction.calculateCostFunction(configuration);
		double[] costArray = new double[costArrayLength];
		for(int i = 0; i < costArrayLength; i++){
			costArray[i] = initialCost;
		}
		int steps = 0;
		while(true) { 
			Configuration candidate = constructCandidate(new Configuration(configuration));
			double candidateCost = costFunction.calculateCostFunction(candidate);
			int v = steps % costArrayLength;
			System.out.println("De nieuwe kost is: " + candidateCost);
			if( candidateCost <= costArray[v] ) {
				System.out.println("Configuration accepted");
				configuration = candidate;
				panel.setConfiguration(configuration);	
				
				int prev = previousCircles.get(previousCircles.size()-1);
				previousCircles.clear();
				previousCircles.add(prev);
				costArray[v] = candidateCost;
//				System.out.println(configuration);				
			}
			else
			{
				System.out.println("Configuration not accepted");
				if ( previousCircles.size() == radii.size() )
				{
					List<Circle> circles = configuration.getInnerCircles();	
					swapPerturbation(circles);
					shiftPerturbation(circles);
					previousCircles.clear();
				}
			}
			if ( candidateCost == 0 )
				break;						
			
			
			
			steps++;
		}
	}
	
	public Panel createPanel() {
		Panel p = new Panel();
		JFrame jf = new JFrame();
		jf.setTitle("Circle Packing Problem");
		jf.setSize(960,960);
		jf.setVisible(true);
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jf.add(p);
		return p;
	}
	
	public Configuration createInitialConfig(List<Double> radii)
	{
		Circle outerCircle = new Circle(1,0,0);
		Configuration config = new Configuration(outerCircle);
		
		for ( double radius : radii )
		{
			double x = 0;
			double y = 0;
			do {
				x = Math.random()*2 - 1;
				y = Math.random()*2 - 1;
			} while (Math.sqrt( Math.pow(x, 2)+Math.pow(y, 2)) > outerCircle.getRadius());
			Circle circle = new Circle(radius,x,y);
			config.addInner(circle);
		}
		
		return config;
	}
	
	public Configuration constructCandidate(Configuration config)
	{
		List<Circle> circles = config.getInnerCircles();
		
		int indexCircle = selectCircleToMove(circles);
//		System.out.println(previousCircle);
		
//		System.out.println("Coord voor de move: " + circles.get(indexCircle).toString());
		
		Circle circle = circles.get(indexCircle);
		List<Circle> copyCircles = new ArrayList<Circle>(circles);
		copyCircles.remove(indexCircle);
		
		double moveX = 0;
		double moveY = 0;
		double changeY;
		double changeX;
		double tan;
		double angle;
		
		int index = selectCircleWithMostOverlap(circle, copyCircles);
		
		if ( index == -1 ) 
		{
			System.out.println("Er is geen overlap gevonden voor: " + indexCircle);
			int result = testLAHCFinished(copyCircles);
			
			if ( result == 1 )
				return config;
			
//			if ( previousCircles.size() == config.getInnerCircles().size() )
//			{
//				swapPerturbation(circles);
//				shiftPerturbation(circles);				
//				previousCircles.clear();
//			}
			
			return config;
		}


		Circle otherCircle = null;
		if (index == 0  ) 
		{
			otherCircle = new Circle(1,0,0);	
			System.out.println("Er is overlap gevonden met de buitenste cirkel voor: " + indexCircle);
		}
		else 
		{
			index = index - 1;
			otherCircle = copyCircles.get(index);
			System.out.println("Er is overlap gevonden met de een binnenste cirkel voor: " + indexCircle);
		}			
		
		changeY = circle.getY() - otherCircle.getY();
		changeX = circle.getX() - otherCircle.getX();
		if ( changeX == 0 )
			changeX = 0.000001;
		tan = changeY/changeX; 
		angle = Math.atan(tan);
		
		double localMoveAmount = moveAmount;
		double dist = moveAmount;
		if(index == 0) 
			dist = calculateDistanceCenters(circle, otherCircle) + circle.getRadius() - otherCircle.getRadius();		
		else
			dist = circle.getRadius() + otherCircle.getRadius() - calculateDistanceCenters(circle, otherCircle);
				
		if(dist < moveAmount ) 
			localMoveAmount = dist;
		
		
		moveX = localMoveAmount * Math.cos(angle);
		moveY = localMoveAmount * Math.sin(angle);
		
		System.out.println("In x-richting: " + moveX + " en in y-richting: " + moveY);
		
		if ( circle.getX() < otherCircle.getX() )
		{
			if(index == 0) {
				circle.setX(circle.getX() + moveX);
				circle.setY(circle.getY() + moveY);
			}
			else {
				circle.setX(circle.getX() - moveX);
				circle.setY(circle.getY() - moveY);
			}
		}
		else
		{
			if(index == 0) {
				circle.setX(circle.getX() - moveX);
				circle.setY(circle.getY() - moveY);
			}
			else {
				circle.setX(circle.getX() + moveX);
				circle.setY(circle.getY() + moveY);
			}
			
		}
			
//		System.out.println("Coord na de move: " + circles.get(indexCircle).toString() + "\n");
		
//		System.out.println("Move amount x: " + moveX);
//		System.out.println("Move amount Y: " + moveY + "\n");

//		copyCircles.add(circle);
//		Configuration newConfig = new Configuration(config.getOuterCircle(), copyCircles);
		return config;
	}
	
	public int selectCircleToMove(List<Circle> circles)
	{
		System.out.println("\n");
		
		double cost = 0, finalCost = 0, maxCost = 0;
		int index = 0, finalIndex = 0;
		List<Circle> copyCircles;
		
		double chance = Math.random();
		
		if ( chance < 0.1 )
		{
			do			
				finalIndex = (int) Math.round(Math.random()*(circles.size()-1));			
			while ( previousCircles.contains( finalIndex) );//	
			
			previousCircles.add(finalIndex);
			System.out.println(previousCircles.toString());
			return finalIndex;
		}
		
		for ( Circle c : circles )
		{
			copyCircles = new ArrayList<Circle>(circles);
			copyCircles.remove(c);
			for ( int i = 0; i < copyCircles.size(); i++ )
			{
				cost = copyCircles.get(i).getRadius() + c.getRadius() 
						- Math.sqrt(Math.pow((c.getX() - copyCircles.get(i).getX()), 2) 
									+ Math.pow((c.getY() - copyCircles.get(i).getY()), 2));
				if ( cost > 0 )
					finalCost += cost;
			}
		
		
			cost = Math.sqrt(Math.pow(c.getX(),2) + Math.pow(c.getY(), 2)) + c.getRadius() - 1;
			if ( cost > 0 )
				finalCost += cost;
			
			
			
			if ( finalCost > maxCost && previousCircles.contains(index) == false )
			{
				finalIndex = index;
				maxCost = finalCost;
			}
			
//			System.out.println("Kost voor " + index + " : " + finalCost);
			
			index++;
			finalCost = 0;
			
		}
		
		previousCircles.add(finalIndex);
		System.out.println(previousCircles.toString());
		return finalIndex;		
	}
	
	public int selectCircleWithMostOverlap(Circle testCircle, List<Circle> restCircles)
	{
		double cost = 0;
		Map<Integer,Double> indices = new HashMap<Integer,Double>();
		
		for ( int i = 0; i < restCircles.size(); i++ )
		{
			cost = restCircles.get(i).getRadius() + testCircle.getRadius() 
					- Math.sqrt(Math.pow((testCircle.getX() - restCircles.get(i).getX()), 2) 
								+ Math.pow((testCircle.getY() - restCircles.get(i).getY()), 2));
			if ( cost > 0 )
				indices.put(i+1, cost);
		}
		
		
		cost = Math.sqrt(Math.pow(testCircle.getX(),2) + Math.pow(testCircle.getY(), 2)) + testCircle.getRadius() - 1;
		if ( cost > 0 )
			indices.put(0, cost);
				
		MoveStrategy ms = new MoveStrategy(indices);
		int index = ms.selectIndex();
		
		return index;
	}
	
	public double calculateDistanceCenters(Circle c1, Circle c2) {
		return  Math.sqrt(Math.pow(( c1.getX() - c2.getX()), 2) 
						+ Math.pow(( c1.getY() - c2.getY()), 2));
	}
	
	public int testLAHCFinished(List<Circle> circles)
	{
		List<Circle> copyCircles;
		int tempResult, finalResult = 0;
		
		for ( Circle c : circles )
		{
			copyCircles = new ArrayList<Circle>(circles);
			copyCircles.remove(c);
			tempResult = selectCircleWithMostOverlap(c,copyCircles);
			if ( tempResult != -1 )
				finalResult++;
		}
		
		if ( finalResult != 0 )
			return 0;
		else
			return 1;
	}
	
	/*	This method tries out 4 different moves (left, right, up, down) for every circle 
	 * 	and performs the shift with the best result (most decrease in cost) on the actual
	 * 	configuration.
	 */
	public void shiftPerturbation(List<Circle> allCircles)
	{
		double angle, moveX, moveY, cost = 0, minCost = 10000;
		List<Circle> copyCircles = new ArrayList<Circle>(allCircles);
		Circle c;	
		int circleIndex = 0, angleIndex = 0;
		CostFunction costFunction = new CostFunction();
		Configuration candidate;
		
		for ( int i = 0; i < copyCircles.size(); i++ )
		{		
			c = copyCircles.get(i);
			
			for ( int j = 1; j < 5; j++ )
			{
				angle = Math.PI*2/4*j;
				moveX = moveAmount * Math.cos(angle);
				moveY = moveAmount * Math.sin(angle);
				
				c.setX(c.getX() + moveX);
				c.setY(c.getY() + moveY);
				
				candidate = new Configuration(new Circle(1,0,0), copyCircles);
				cost = costFunction.calculateCostFunction(candidate); 
				
				if ( cost < minCost )
				{
					circleIndex = i;
					angleIndex = j;
					minCost = cost;
				}
			}			     

			copyCircles = new ArrayList<Circle>(allCircles);
		}
		
		c = allCircles.get(circleIndex);
		angle = Math.PI*2/4*angleIndex;
		moveX = moveAmount * Math.cos(angle);
		moveY = moveAmount * Math.sin(angle);
		
		c.setX(c.getX() + moveX);
		c.setY(c.getY() + moveY);
	}
	
	/*	Finds the best two circles to swap and swaps them.
	 * 	Only circles with sequential radii can be swapped, so first
	 * 	they are ordered among radius size and then the checks are performed.
	 */
	public void swapPerturbation(List<Circle> allCircles)
	{
		int index = 0;
		Circle circleA, circleB;
		List<Circle> copyCircles;
		CostFunction costFunction = new CostFunction();
		Configuration candidate;
		double cost = 0, minCost = 100000;
		
		for ( int i = 0; i < allCircles.size() - 2; i++ )
		{
			copyCircles = new ArrayList<Circle>(allCircles);
			circleA = copyCircles.get(i);
			circleB = copyCircles.get(i+1);
			
			double tempX = circleA.getX();
			double tempY = circleA.getY();
			
			circleA.setX(circleB.getX());
			circleA.setY(circleB.getY());
			
			circleB.setX(tempX);
			circleB.setY(tempY);
			
			candidate = new Configuration(new Circle(1,0,0), copyCircles);
			
			cost = costFunction.calculateCostFunction(candidate);      
			
			if ( cost < minCost )
			{
				index = i;
				minCost = cost;
			}
		}
			
		circleA = allCircles.get(index);
		circleB = allCircles.get(index+1);
		
		double tempX = circleA.getX();
		double tempY = circleA.getY();
		
		circleA.setX(circleB.getX());
		circleA.setY(circleB.getY());
		
		circleB.setX(tempX);
		circleB.setY(tempY);
		
		
	}
}
