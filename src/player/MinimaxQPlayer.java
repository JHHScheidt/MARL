package player;

import java.awt.Point;
import java.util.HashMap;
import java.util.Map;

import exploration.ExplorationStrategy;
import main.Action;
import main.State;
import main.Triple;
import main.Tuple;
import policy.Policy;
import policy.ProbabilisticPolicy;
import scpsolver.constraints.LinearBiggerThanEqualsConstraint;
import scpsolver.constraints.LinearEqualsConstraint;
import scpsolver.constraints.LinearSmallerThanEqualsConstraint;
import scpsolver.lpsolver.LinearProgramSolver;
import scpsolver.lpsolver.SolverFactory;
import scpsolver.problems.LinearProgram;
import java.util.Random;

public class MinimaxQPlayer implements Player {
	private boolean player;
	private State s;
	private Action a;
	private Random random;

	private Map<Triple<State, Action, Action>, Double> qValues;
	private Map<State, double[]> pi;
	private Map<State,Double> vValues;
	
	private final double EXPLOR = 0.2;
	
	private double alpha;
	private double gamma;
	private double decay;

	public MinimaxQPlayer(boolean player, double discountFactor, double decay, ExplorationStrategy es) {
		this.player = player;
		random = new Random(1024);
		initMinimax();
		this.gamma = discountFactor;
		this.decay = decay;
	}
	
	public void initMinimax() {
		this.qValues = new HashMap<Triple<State, Action, Action>, Double>();
		this.pi = new HashMap<State, double[]>();
		this.vValues = new HashMap<State, Double>();
		
		for (int x1 = State.MIN_X - 1; x1 <= State.MAX_X + 1; x1++) {
			for (int y1 = State.MIN_Y - 1; y1 <= State.MAX_Y + 1; y1++) {
				for (int x2 = State.MIN_X - 1; x2 <= State.MAX_X + 1; x2++) {
					for (int y2 = State.MIN_Y - 1; y2 <= State.MAX_Y + 1; y2++) {
						Point p1 = new Point(x1, y1);
						Point p2 = new Point(x2, y2);
						State s1 = new State(p1, p2, true);
						State s2 = new State(p1, p2, false);
						for (Action a : Action.values()) {
							for(Action o:Action.values()){
								this.qValues.put(new Triple<State, Action, Action>(s1, a, o), 1.0);
								this.qValues.put(new Triple<State, Action, Action>(s2, a, o), 1.0);
							}
						}
						this.vValues.put(s1, 1.0);
						this.vValues.put(s2, 1.0);
						double[] piDoubles = new double[Action.values().length];
						for(int i = 0; i<Action.values().length; i++) {
							piDoubles[i] = 1.0/(Action.values().length);
						}
						this.pi.put(s1, piDoubles);
						this.pi.put(s2, piDoubles);
					}
				}
			}
		}
		this.alpha=1;
	}

	@Override
	public Action chooseAction(State state) {
		this.s = state;
		if(random.nextDouble()<EXPLOR) {
			this.a = new RandomPlayer(this.player).chooseAction(state);
		}
		else {
			double sum = 0;
			double[] prob = pi.get(state);
			double r = random.nextDouble();
			System.out.println(r);
			for (int i = 0; i <prob.length; i++) {
				sum = sum + prob[i];
				System.out.println(sum);
				if (r <= sum) {
					this.a = Action.values()[i];
				}
			}
		}
		return this.a;
	}

	@Override
	public void receiveReward(double reward, State newState, Action o) {
		//Update q value
		Triple<State, Action, Action> triple = new Triple<State,Action,Action>(this.s,this.a,o);
		double newQVal = (1-this.alpha) * this.qValues.get(triple) + this.alpha*(reward+this.gamma*vValues.get(newState));
		this.qValues.put(triple, newQVal);
		
		//Update linear program
		linearProgramming();
		
		//Update V values
		double v = Double.MAX_VALUE;
		for(Action nextO : Action.values()){
			double currentV = 0;
			int i = 0;
			for(Action nextA: Action.values()) {
				currentV += (pi.get(s)[i] * qValues.get(new Triple<State,Action,Action>(s, nextA, nextO)));
				i++;
			}
			if(currentV<v)
				v=currentV;
		}
		vValues.put(s, v);
		
		//Update alpha
		this.alpha=this.alpha*this.decay;
	}

	public void linearProgramming() {
		Action[] allActions = Action.values();

		// maximize slack variable (index 0), which represents the value of the
		// inner minimization
		double[] maxObjective = new double[allActions.length + 1];
		maxObjective[0] = 1.0;
		LinearProgram lp = new LinearProgram(maxObjective);

		// all probabilities must add up to 1
		double[] sum1 = new double[allActions.length + 1];
		for (int i = 1; i < sum1.length; i++) {
			sum1[i] = 1.0;
		}
		lp.addConstraint(new LinearEqualsConstraint(sum1, 1.0, "a"));

		for (int i = 0; i < allActions.length; i++) {
			// all probabilities must be positive
			double[] arr1 = new double[maxObjective.length];
			arr1[i + 1] = 1.0;
			lp.addConstraint(new LinearBiggerThanEqualsConstraint(arr1, 0, "b" + (i + 1)));

			// v <= sum( p(s, action_i) * Q(s,action_i, action_opponent) )
			// v - sum( p(s, action_i) * Q(s,action_i, action_opponent) ) <= 0
			double[] arr2 = new double[maxObjective.length];
			arr2[0] = 1.0;
			for (int j = 0; j < allActions.length; j++) {
				arr2[i + 1] = -this.qValues
						.get(new Triple<State, Action, Action>(this.s, allActions[i], allActions[j]));
			}
			lp.addConstraint(new LinearSmallerThanEqualsConstraint(arr2, 0, "c" + (i + 1)));
		}
		lp.setMinProblem(false);

		LinearProgramSolver solver = SolverFactory.newDefault();
		double[] sol = solver.solve(lp);
		double[] newPi = new double[allActions.length];
		System.arraycopy(sol, 1, newPi, 0, newPi.length);
		this.pi.put(this.s, newPi);
	}

	public Policy getPolicy() {
		return new ProbabilisticPolicy(this.pi);
	}

}
