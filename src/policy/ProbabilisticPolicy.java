package policy;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.stream.DoubleStream;
import java.util.Arrays;

import main.Action;
import main.State;
import player.Player;

public class ProbabilisticPolicy extends HashMap<State, double[]> implements Policy {
	/** generated id */
	private static final long serialVersionUID = -7901905496277036579L;
	
	private Boolean p;
	
	private Random random;
	
	private final double EPSILON = 0.1; //Do something random 1 every 20 times

	public ProbabilisticPolicy(Map<State, double[]> policy, Boolean p, Random random) {
		super(policy);
		this.p=p;
		this.random = random;
	}

	@Override
	public Action getAction(State state) {
		Action[] allActions = Action.values();
		double[] qVals = super.get(state);

		double sum = 0.0;
		double r = this.random.nextDouble();
		//Epsilon greedy
		if(r<EPSILON) {
			Action a = null;
			do {
				a = allActions[this.random.nextInt(Action.values().length)];
			} while (!state.isActionPossible(this.p, a));
			return a;
		}
		else {
			double maxVal = -Double.MAX_VALUE;
			Action bestAction = Action.STAND;
			int i = 0;
			for(Action a : Action.values()) {
				if(state.isActionPossible(this.p, a) && qVals[i] > maxVal) {
					maxVal=qVals[i];
					bestAction=a;
				}
			}
			return bestAction;
		}
//		//Original
//		for (int i = 0; i < qVals.length; i++) {
//			sum = sum + qVals[i];
//			if (r <= sum) {
//				return allActions[i];
//			}
//		}
//		
//		return allActions[allActions.length - 1];
	}

}
