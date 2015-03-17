
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
		Configuration initialConfiguration = null; //TODO: initialize configuration
		double initialCost = costFunction.calculateCostFunction(initialConfiguration);
		double[] costArray = new double[costArrayLength];
		for(int i = 0; i < costArrayLength; i++){
			costArray[i] = initialCost;
		}
		int steps = 0;
		while(true) { //TODO: insert stopping condition!
			Configuration candidate = null; //TODO: construct condidate
			double candidateCost = costFunction.calculateCostFunction(candidate);
			int v = steps % costArrayLength;
			if(candidateCost <= costArray[v]) {
				//accept candidate configuration
			}
			costArray[v] = candidateCost; //Ik denk dat dit hier moet maar ben niet zeker of het niet in de if moet.
			steps ++;
		}
	}
	
}
