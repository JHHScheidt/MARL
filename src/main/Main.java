package main;

import java.util.Random;

import exploration.RandomExploration;
import player.MinimaxQPlayer;
import player.Player;
import player.PolicyPlayer;
import player.QLearningPlayer;
import player.RandomPlayer;
import policy.Policy;

public class Main {

	public static void main(String[] args) {
		Random mainRandom = new Random(13);

		//Deterministic/Probabilistic vs. Random
//		QLearningPlayer qPlayer = new QLearningPlayer(State.FIRST_PLAYER, 0.9, new RandomExploration(1024));
//		Player p2 = new RandomPlayer(State.SECOND_PLAYER);
//
//		Simulator sim = new Simulator(qPlayer, p2);
//		sim.simulate(1_000_000, 0.1);
//		System.out.println("Training QR finished");
//
////		Policy QR = qPlayer.getDeterministicPolicy();
//		Policy QR = qPlayer.getProbabilisticPolicy();
//		sim.setP1(new PolicyPlayer(QR));
//		sim.simulate(1_000_000, 0.1);
//		System.out.println("Evaluation QR finished");
//		System.out.println("Player 1 scored "+sim.getGoalsP1()+" goals.");
//		System.out.println("Player 2 scored "+sim.getGoalsP2()+" goals.");
//		sim.simulate(100, 0.1, true);
//		System.out.println();
		

//		//Deterministic vs. Probabilistic
//		QLearningPlayer p1 = new QLearningPlayer(State.FIRST_PLAYER, 0.9, new RandomExploration(mainRandom), mainRandom);
//		QLearningPlayer p2 = new QLearningPlayer(State.SECOND_PLAYER, 0.9, new RandomExploration(mainRandom), mainRandom);
//
//		Simulator sim = new Simulator(p1, p2, mainRandom);
//		sim.simulate(1_000_000, 0.1);
//		System.out.println("Training QR finished");
//
//		Policy QR1 = p1.getProbabilisticPolicy();
//		Policy QR2 = p2.getDeterministicPolicy();
//		sim.setP1(new PolicyPlayer(QR1));
//		sim.setP2(new PolicyPlayer(QR2));
//		sim.simulate(1_000_000, 0.1);
//		System.out.println("Evaluation QR finished");
//		System.out.println("Player 1 scored "+sim.getGoalsP1()+" goals.");
//		System.out.println("Player 2 scored "+sim.getGoalsP2()+" goals.");
//		sim.simulate(100, 0.1, true, p1, p2);
//		System.out.println();
			
//		//MinimaxQ vs. 
		MinimaxQPlayer qPlayer = new MinimaxQPlayer(State.FIRST_PLAYER, 0.9, 1, new RandomExploration(mainRandom), mainRandom);
//		Player p2 = new RandomPlayer(State.SECOND_PLAYER, mainRandom);
		QLearningPlayer p2 = new QLearningPlayer(State.SECOND_PLAYER, 0.9, new RandomExploration(mainRandom), mainRandom);

		Simulator sim = new Simulator(qPlayer, p2, mainRandom);
		sim.simulate(10_000, 0.1);
		System.out.println("Training QR finished");

		Policy QR = qPlayer.getPolicy();
		sim.setP1(new PolicyPlayer(QR));
		Policy QR2 = p2.getDeterministicPolicy();
		sim.setP2(new PolicyPlayer(QR2));

		sim.simulate(1_000_000, 0.1);
		System.out.println("Evaluation QR finished");
		System.out.println("Player 1 scored "+sim.getGoalsP1()+" goals.");
		System.out.println("Player 2 scored "+sim.getGoalsP2()+" goals.");
			System.out.println();
		sim.simulate(100, 0.1, true);
	}
}
