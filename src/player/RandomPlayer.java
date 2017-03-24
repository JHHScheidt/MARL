package player;

import java.util.Random;

import main.Action;
import main.State;

public class RandomPlayer implements Player {
	private Random random;
	private boolean player;

	public RandomPlayer(boolean player, Random random) {
		this.player = player;
		this.random = random;
	}
	
	@Override
	public Action chooseAction(State state) {
		Action[] allActions = Action.values();
		Action a = null;
		do {
			a = allActions[this.random.nextInt(Action.values().length)];
		} while (!state.isActionPossible(this.player, a));
		return a;
	}

	@Override
	public void receiveReward(double reward, State newState, Action opponentAction) {
		// ignore any rewards
	}

}
