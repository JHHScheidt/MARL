package policy;

import java.awt.Point;
import java.util.HashMap;
import java.util.Map;

import main.Action;
import main.State;

public class DeterministicPolicy extends HashMap<State, Action> implements Policy {
	/** generated id for serialization */
	private static final long serialVersionUID = 291698286088712191L;

	boolean p;
	
	public DeterministicPolicy(Map<State, Action> policy, boolean p) {
		super(policy);
		this.p = p;
	}

	@Override
	public Action getAction(State state) {
		Point p1 = state.getP1();
		Point p2 = state.getP2();
//		if(State.FIRST_PLAYER==this.p) {
//			if(state.getPossession()==State.SECOND_PLAYER) {
//				if(p1.getY()<p2.getY() && state.isActionPossible(p, Action.SOUTH)) {
//					return Action.SOUTH;
//				}
//				if(p1.getY()>p2.getY() && state.isActionPossible(p, Action.NORTH)) {
//					return Action.NORTH;
//				}
//				if(p1.getX()<p2.getX() && state.isActionPossible(p, Action.EAST)) {
//					return Action.EAST;
//				}
//				if(p1.getX()>p2.getX() && state.isActionPossible(p, Action.WEST)) {
//					return Action.WEST;
//				}
//				return Action.STAND;
//			}
//			else {
//				if(p1.getY() == State.MIN_Y && state.isActionPossible(p, Action.SOUTH)) {
//					return Action.SOUTH;
//				}
//				if(p1.getY() == State.MAX_Y && state.isActionPossible(p, Action.NORTH)) {
//					return Action.NORTH;
//				}
//				return Action.WEST;
//			}
//		}
//		else {
//			if(state.getPossession()==State.FIRST_PLAYER) {
//				if(p1.getY()>p2.getY() && state.isActionPossible(p, Action.SOUTH)) {
//					return Action.SOUTH;
//				}
//				if(p1.getY()<p2.getY() && state.isActionPossible(p, Action.NORTH)) {
//					return Action.NORTH;
//				}
//				if(p1.getX()>p2.getX() && state.isActionPossible(p, Action.EAST)) {
//					return Action.EAST;
//				}
//				if(p1.getX()<p2.getX() && state.isActionPossible(p, Action.WEST)) {
//					return Action.WEST;
//				}
//				return Action.STAND;
//			}
//			else {
//				if(p2.getY() == State.MIN_Y && state.isActionPossible(p, Action.SOUTH)) {
//					return Action.SOUTH;
//				}
//				if(p2.getY() == State.MAX_Y && state.isActionPossible(p, Action.NORTH)) {
//					return Action.NORTH;
//				}
//				return Action.EAST;
//			}
//		}
		
		if(p2.getY()>State.MIN_Y && state.isActionPossible(p, Action.NORTH))
			return Action.NORTH;
		else if(state.isActionPossible(p, Action.EAST))
			return Action.EAST;
		else return Action.STAND;
	}
}
