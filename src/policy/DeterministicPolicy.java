package policy;

import java.awt.Point;
import java.util.HashMap;
import java.util.Map;

import main.Action;
import main.State;

public class DeterministicPolicy extends HashMap<State, Action> implements Policy {
	/** generated id for serialization */
	private static final long serialVersionUID = 291698286088712191L;

	public DeterministicPolicy(Map<State, Action> policy) {
		super(policy);
	}

	@Override
	public Action getAction(State state) {
		Point p1 = state.getP1();
		Point p2 = state.getP2();
		if(!state.getPossession()) {
			if(p1.getY()<p2.getY() && state.isActionPossible(State.FIRST_PLAYER, Action.SOUTH)) {
				return Action.SOUTH;
			}
			if(p1.getY()>p2.getY() && state.isActionPossible(State.FIRST_PLAYER, Action.NORTH)) {
				return Action.NORTH;
			}
			if(p1.getX()<p2.getX() && state.isActionPossible(State.FIRST_PLAYER, Action.EAST)) {
				return Action.EAST;
			}
			if(p1.getX()>p2.getX() && state.isActionPossible(State.FIRST_PLAYER, Action.WEST)) {
				return Action.WEST;
			}
			return Action.STAND;
		}
		else {
			if(p1.getY() == State.MIN_Y && state.isActionPossible(State.FIRST_PLAYER, Action.SOUTH)) {
				return Action.SOUTH;
			}
			if(p1.getY() == State.MAX_Y && state.isActionPossible(State.FIRST_PLAYER, Action.NORTH)) {
				return Action.NORTH;
			}
			return Action.WEST;
		}
//		return super.get(state);
	}
}
