import java.util.List;


public class CostFunction {
	
	/*	Kost omlaag indien er contact is met een andere cirkel
	 * 	waarbij de kost meer omlaag gaat indien het contact met de
	 * 	buitenste cirkel is. Dus dit wordt geprefereerd.
	 * 
	 * 	Kost omhoog indien er overlap is met een andere cirkel
	 */
	
	public CostFunction() {}
	
	public double calculateCostFunction(Configuration C)
	{
		List<Circle> innerCircles = C.getInnerCircles();
		double costOuter = costOuterOverlap(innerCircles);
		double costInner = costInnerOverlap(innerCircles);
		return costInner + costOuter; //Hoe werden die twee nu weer gecombineerd?
	}
	
	public double costOuterOverlap(List<Circle> inners)
	{
		double cost = 0, totalCost = 0;
		for ( Circle c : inners )
		{
			cost = Math.sqrt(Math.pow(c.getX(),2) + Math.pow(c.getY(), 2)) + c.getRadius() - 1;
			if( cost > 0 )
				totalCost += cost;
		}
		
		return totalCost;
	}
	
	public double costInnerOverlap(List<Circle> inners)
	{
		double cost = 0, totalCost = 0;
		Circle circleA, circleB;
		
		for ( int i = 0; i < inners.size(); i++ )
		{
			for ( int j = i+1; j < inners.size(); j++ )
			{
				circleA = inners.get(i);
				circleB = inners.get(j);
				cost = circleB.getRadius() + circleA.getRadius() 
						- Math.sqrt(Math.pow((circleA.getX() - circleB.getX()), 2) 
									+ Math.pow((circleA.getY() - circleB.getY()), 2));
				if(cost > 0 )
					totalCost += cost;				 
			}
		}
		
		return totalCost;
	}

}
