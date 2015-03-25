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
		Circle outerCircle = C.getOuterCircle();
		List<Circle> innerCircles = C.getInnerCircles();
		double costOuter = costOuterOverlap(outerCircle, innerCircles);
		double costInner = costInnerOverlap(innerCircles);
		return costInner + costOuter; //Hoe werden die twee nu weer gecombineerd?
	}
	
	public double costOuterOverlap(Circle outer, List<Circle> inners)
	{
		double cost = 0, totalCost = 0;
		for ( Circle c : inners )
		{
			cost = Math.sqrt(Math.pow(c.getX(),2) + Math.pow(c.getY(), 2)) + c.getRadius() - outer.getRadius();
			if( cost > 0 )
				totalCost += cost;
		}
		
		return totalCost;
	}
	
	public double costInnerOverlap(List<Circle> inners)
	{
		double cost = 0, totalCost = 0;
		for ( int i = 0; i < inners.size(); i++ )
		{
			for ( int j = i; j < inners.size(); j++ )
			{
				cost = inners.get(j).getRadius() + inners.get(i).getRadius() 
						- Math.sqrt(Math.pow((inners.get(i).getX() - inners.get(j).getX()), 2) 
						+ Math.pow((inners.get(i).getY() - inners.get(j).getY()), 2));
				if(cost > 0 )
					totalCost += cost;				 
			}
		}
		
		return totalCost;
	}

}
