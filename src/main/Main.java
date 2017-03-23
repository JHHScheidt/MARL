package main;

import exploration.RandomExploration;
import player.Player;
import player.PolicyPlayer;
import player.QLearningPlayer;
import player.RandomPlayer;
import policy.Policy;

public class Main {

	public static void main(String[] args) {

		QLearningPlayer qPlayer = new QLearningPlayer(State.FIRST_PLAYER, 0.9, new RandomExploration(1024));
		Player p2 = new RandomPlayer(State.SECOND_PLAYER);

		Simulator sim = new Simulator(qPlayer, p2);
		sim.simulate(1_000_000, 0.1);
		System.out.println("Training QR finished");

		Policy QR = qPlayer.getPolicy();
		sim.setP1(new PolicyPlayer(QR));
		sim.simulate(1_000_000, 0.1);
		System.out.println("Evaluation QR finished");
		System.out.println("Player 1 scored "+sim.getGoalsP1()+" goals.");
		System.out.println("Player 2 scored "+sim.getGoalsP2()+" goals.");
//			System.out.println();
	}
}
