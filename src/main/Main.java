package main;

import exploration.RandomExploration;
import player.MinimaxQPlayer;
import player.Player;
import player.PolicyPlayer;
import player.QLearningPlayer;
import player.RandomPlayer;
import policy.Policy;

public class Main {

	public static void main(String[] args) {

//		//Deterministic/Probabilistic vs. Random
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
//		System.out.println();
		

//		//Deterministic vs. Probabilistic
//		QLearningPlayer p1 = new QLearningPlayer(State.FIRST_PLAYER, 0.9, new RandomExploration(1024));
//		QLearningPlayer p2 = new QLearningPlayer(State.SECOND_PLAYER, 0.9, new RandomExploration(1024));
//
//		Simulator sim = new Simulator(p1, p2);
//		sim.simulate(1_000_000, 0.1);
//		System.out.println("Training QR finished");
//
//		Policy QR1 = p1.getDeterministicPolicy();
//		Policy QR2 = p2.getProbabilisticPolicy();
//		sim.setP1(new PolicyPlayer(QR1));
//		sim.setP2(new PolicyPlayer(QR2));
//		sim.simulate(1_000_000, 0.1);
//		System.out.println("Evaluation QR finished");
//		System.out.println("Player 1 scored "+sim.getGoalsP1()+" goals.");
//		System.out.println("Player 2 scored "+sim.getGoalsP2()+" goals.");
//		System.out.println();
			
		//MinimaxQ vs. 
		MinimaxQPlayer qPlayer = new MinimaxQPlayer(State.FIRST_PLAYER, 0.9, 1, new RandomExploration(1024));
		Player p2 = new RandomPlayer(State.SECOND_PLAYER);

		Simulator sim = new Simulator(qPlayer, p2);
		sim.simulate(1_000_000, 0.1);
		System.out.println("Training QR finished");

//		Policy QR = qPlayer.getDeterministicPolicy();
		Policy QR = qPlayer.getPolicy();
		sim.setP1(new PolicyPlayer(QR));
		sim.simulate(1_000_000, 0.1);
		System.out.println("Evaluation QR finished");
		System.out.println("Player 1 scored "+sim.getGoalsP1()+" goals.");
		System.out.println("Player 2 scored "+sim.getGoalsP2()+" goals.");
			System.out.println();
		
	}
}
