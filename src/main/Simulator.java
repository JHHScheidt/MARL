package main;

import java.awt.Point;
import java.util.Random;

import player.Player;
import player.QLearningPlayer;

public class Simulator {
	private Player p1, p2;
	private int goals1, goals2;
	
	private Random random;

	public Simulator(Player p1, Player p2, Random random) {
		this.p1 = p1;
		this.p2 = p2;
		this.random = random;
		resetCounters();
	}

	public int getGoalsP1() {
		return this.goals1;
	}

	public int getGoalsP2() {
		return this.goals2;
	}

	public void setP1(Player p1) {
		this.p1 = p1;
	}

	public void setP2(Player p2) {
		this.p2 = p2;
	}

	private void resetCounters() {
		this.goals1 = 0;
		this.goals2 = 0;
	}

	public void simulate(int steps, double drawProbability) {
		resetCounters();

		State state = initializeRandomly();

		for (int i = 0; i < steps; i++) {
			Action a1 = this.p1.chooseAction(state);
			Action a2 = this.p2.chooseAction(state);
			if (this.random.nextDouble() < 0.5) {
				state = performAction(state, a1, State.FIRST_PLAYER);
				state = performAction(state, a2, State.SECOND_PLAYER);
			} else {
				state = performAction(state, a2, State.SECOND_PLAYER);
				state = performAction(state, a1, State.FIRST_PLAYER);
			}

			Point ballPosition = state.getPossession() == State.FIRST_PLAYER ? state.getP1() : state.getP2();
			boolean resetBoard = false;
			double rewardP1 = 0.0, rewardP2 = 0.0;
			if (ballPosition.x == State.MIN_X - 1) { // goal for first player
				this.goals1++;
				resetBoard = true;
				rewardP1 = 1.0;
				rewardP2 = -1.0;
			} else if (ballPosition.x == State.MAX_X + 1) { // goal for second
															// player
				this.goals2++;
				resetBoard = true;
				rewardP1 = -1.0;
				rewardP2 = 1.0;
			} else if (this.random.nextDouble() < drawProbability) { // draw
				resetBoard = true;
			}
			if (resetBoard) {
				state = initializeRandomly();
			}
			this.p1.receiveReward(rewardP1, state, a2);
			this.p2.receiveReward(rewardP2, state, a1);
		}

	}
	
public void simulate(int steps, double drawProbability, boolean showGame, QLearningPlayer qp1, QLearningPlayer qp2) {
	resetCounters();
	random = new Random(1024);

	State state = initializeRandomly();

	for (int i = 0; i < steps; i++) {
		Action a1 = this.p1.chooseAction(state);
		Action a2 = this.p2.chooseAction(state);
		if (this.random.nextDouble() < 0.5) {
			state = performAction(state, a1, State.FIRST_PLAYER);
			state = performAction(state, a2, State.SECOND_PLAYER);
		} else {
			state = performAction(state, a2, State.SECOND_PLAYER);
			state = performAction(state, a1, State.FIRST_PLAYER);
		}

		Point ballPosition = state.getPossession() == State.FIRST_PLAYER ? state.getP1() : state.getP2();
		boolean resetBoard = false;
		double rewardP1 = 0.0, rewardP2 = 0.0;
		if (ballPosition.x == State.MIN_X - 1) { // goal for first player
			this.goals1++;
			resetBoard = true;
			rewardP1 = 1.0;
			rewardP2 = -1.0;
		} else if (ballPosition.x == State.MAX_X + 1) { // goal for second
														// player
			this.goals2++;
			resetBoard = true;
			rewardP1 = -1.0;
			rewardP2 = 1.0;
		} 
//		else if (this.random.nextDouble() < drawProbability) { // draw
//			resetBoard = true;
//		}
		if (resetBoard) {
			state = initializeRandomly();
		}
		this.p1.receiveReward(rewardP1, state, a2);
		this.p2.receiveReward(rewardP2, state, a1);
		

		if(showGame) {
			System.out.println("P1:" + a1);
			System.out.println("P2:" + a2);
			for(Action a : Action.values()) {
				System.out.println("P1 Action "+a+": "+qp1.getQVals(state, a));
//				System.out.println("P2 Action "+a+": "+qp2.getQVals(state, a));
			}
			for(int k = 0; k<=State.MAX_Y; k++) {
				for(int j =0; j<=State.MAX_X; j++) {
					Point current = new Point(j,k);
					if(state.getP1().distance(current)==0) {
						if(state.getPossession()==State.FIRST_PLAYER) {
							System.out.print("A ");
						}
						else System.out.print("a ");
					}
					else if(state.getP2().distance(current)==0) {
						if(state.getPossession()==State.SECOND_PLAYER) {
							System.out.print("O ");
						}
						else System.out.print("o ");
					}
					else System.out.print(". ");
				}
				System.out.println();
			}
			System.out.println();
			System.out.println();
		}
	}
}

public void simulate(int steps, double drawProbability, boolean showGame) {
resetCounters();
random = new Random(1024);

State state = initializeRandomly();

for (int i = 0; i < steps; i++) {
	Action a1 = this.p1.chooseAction(state);
	Action a2 = this.p2.chooseAction(state);
	if (this.random.nextDouble() < 0.5) {
		state = performAction(state, a1, State.FIRST_PLAYER);
		state = performAction(state, a2, State.SECOND_PLAYER);
	} else {
		state = performAction(state, a2, State.SECOND_PLAYER);
		state = performAction(state, a1, State.FIRST_PLAYER);
	}

	Point ballPosition = state.getPossession() == State.FIRST_PLAYER ? state.getP1() : state.getP2();
	boolean resetBoard = false;
	double rewardP1 = 0.0, rewardP2 = 0.0;
	if (ballPosition.x == State.MIN_X - 1) { // goal for first player
		this.goals1++;
		resetBoard = true;
		rewardP1 = 1.0;
		rewardP2 = -1.0;
	} else if (ballPosition.x == State.MAX_X + 1) { // goal for second
													// player
		this.goals2++;
		resetBoard = true;
		rewardP1 = -1.0;
		rewardP2 = 1.0;
	} 
//	else if (this.random.nextDouble() < drawProbability) { // draw
//		resetBoard = true;
//	}
	if (resetBoard) {
		state = initializeRandomly();
	}
	this.p1.receiveReward(rewardP1, state, a2);
	this.p2.receiveReward(rewardP2, state, a1);
	

	if(showGame) {
		System.out.println("P1:" + a1);
		System.out.println("P2:" + a2);
		for(Action a : Action.values()) {
//			System.out.println("P1 Action "+a+": "+qp1.getQVals(state, a));
//			System.out.println("P2 Action "+a+": "+qp2.getQVals(state, a));
		}
		for(int k = 0; k<=State.MAX_Y; k++) {
			for(int j =0; j<=State.MAX_X; j++) {
				Point current = new Point(j,k);
				if(state.getP1().distance(current)==0) {
					if(state.getPossession()==State.FIRST_PLAYER) {
						System.out.print("A ");
					}
					else System.out.print("a ");
				}
				else if(state.getP2().distance(current)==0) {
					if(state.getPossession()==State.SECOND_PLAYER) {
						System.out.print("O ");
					}
					else System.out.print("o ");
				}
				else System.out.print(". ");
			}
			System.out.println();
		}
		System.out.println();
		System.out.println();
	}
}
}

	private State initializeRandomly() {
		return this.random.nextDouble() < 0.5 ? State.getInitialState(State.FIRST_PLAYER)
				: State.getInitialState(State.SECOND_PLAYER);
	}

	private State performAction(State state, Action a, boolean player) {
		if (!state.isActionPossible(player, a)) {
			return state;
		}
		Point p1 = state.getP1();
		Point p2 = state.getP2();
		boolean possession = state.getPossession();
		if (player == State.FIRST_PLAYER) {
			Point newPos = targetPosition(p1, a);
			if (newPos.equals(p2)) {
				// possession goes to the stationary player
				// and move does not take place
				possession = State.SECOND_PLAYER;
			} else {
				p1 = newPos;
			}
		} else {
			Point newPos = targetPosition(p2, a);
			if (newPos.equals(p1)) {
				// possession goes to the stationary player
				// and move does not take place
				possession = State.FIRST_PLAYER;
			} else {
				p2 = newPos;
			}
		}
		return new State(p1, p2, possession);
	}

	private Point targetPosition(Point pos, Action a) {
		Point newPos = new Point(pos);
		switch (a) {
		case EAST:
			newPos.x += 1;
			break;
		case NORTH:
			newPos.y -= 1;
			break;
		case SOUTH:
			newPos.y += 1;
			break;
		case WEST:
			newPos.x -= 1;
			break;
		case STAND:
			break;
		default:
			break;
		}
		return newPos;
	}
}
