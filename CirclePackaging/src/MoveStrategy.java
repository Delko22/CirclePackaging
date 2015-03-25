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
		for ( Entry<Integer,Double> entry : possibleIndices )
	}

}
