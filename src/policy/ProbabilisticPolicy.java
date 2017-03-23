package policy;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Arrays;

import main.Action;
import main.State;

public class ProbabilisticPolicy extends HashMap<State, double[]> implements Policy {
	/** generated id */
	private static final long serialVersionUID = -7901905496277036579L;
	
	private Random random;
	
	private final double EPSILON = 0.10;

	public ProbabilisticPolicy(Map<State, double[]> policy) {
		super(policy);
		random = new Random(1024);
	}

	@Override
	public Action getAction(State state) {
		Action[] allActions = Action.values();
		double[] prob = super.get(state);

		double sum = 0.0;
		double r = this.random.nextDouble();

		//Epsilon greedy
		if(r<EPSILON) {
			return allActions[(int) (this.random.nextDouble()*allActions.length)];
		}
		else {
			double max = Arrays.stream(prob).max().getAsDouble();
			for(int i = 0; i<prob.length; i++) {
				if(prob[i]==max) {
					return allActions[i];
				}
			}
		}
//		//Original
//		for (int i = 0; i < prob.length; i++) {
//			sum = sum + prob[i];
//			if (r <= sum) {
//				return allActions[i];
//			}
//		}
		
		return allActions[allActions.length - 1];
	}

}
