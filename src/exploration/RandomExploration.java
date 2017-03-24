package exploration;

import java.util.Random;

import main.Action;
import main.State;

public class RandomExploration implements ExplorationStrategy {

	private Random random;

//	public RandomExploration() {
//		this.random = new Random();
//	}

	public RandomExploration(Random random) {
		this.random = random;
	}

	@Override
	public Action selectAction(State state, boolean player) {
		Action[] allActions = Action.values();
		Action a = null;
		do {
			a = allActions[this.random.nextInt(Action.values().length)];
		} while (!state.isActionPossible(player, a));
		return a;
	}

}
