import java.util.Map;
import java.util.Map.Entry;


public class MoveStrategy {
	
	private Map<Integer,Double> possibleIndices ;
	
	public MoveStrategy (Map<Integer,Double> indices)
	{
		possibleIndices = indices;
	}
	
	public Integer selectIndex()
	{
		int finalIndex = 0;
		double maxCost = 0;
		
		for ( Entry<Integer,Double> entry : possibleIndices.entrySet() )
		{
			if ( entry.getValue() > maxCost )
			{
				finalIndex = entry.getKey();
				maxCost = entry.getValue();
			}
		}
		
		return finalIndex;
	}

}
