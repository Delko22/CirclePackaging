
public class CostFunction {
	
	public CostFunction(Configuration C, Int radius)
	{
		Circle outerCircle = C.getOuterCircle();
		List<Circle> innerCircles = C.getInnerCircles();
		costOuterOverlap(outerCircle, innerCircles);
		costInnerOverlap(innerCircles);
	}
	
	public int costOuterOverlap(Circle outer, List<Circle> inners)
	{
		int cost = 0, totalCost = 0;
		for ( Circle c : inners )
		{
			cost = Math.sqrt(Math.pow((c.x() + c.y()), 2)) + c.getRadius() - outer.getRadius();
			totalCost += cost;
		}
		
		return totalCost;
	}
	
	public int costInnerOverlap(List<Circle> inners)
	{
		int cost = 0, totalCost = 0;
		for ( int i = 0; i < inners.size(); i++ )
		{
			for ( int j = i; j < inners.size(); j++ )
			{
				cost = inners[j].getRadius() + inners[i].getRadius() 
						- Math.sqrt(Math.pow((inners[i].x() - inners[j].x()), 2) 
						+ Math.pow((inners[i].y() - inners[j].y()), 2));
				totalCost += cost;				 
			}
		}
		
		return totalCost;
	}

}
