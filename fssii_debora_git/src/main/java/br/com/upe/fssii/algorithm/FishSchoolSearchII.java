package main.java.br.com.upe.fssii.algorithm;

import java.util.Random;

import main.java.br.com.upe.fssii.problems.Problem;

public class FishSchoolSearchII {

	private Fish[] school;
	private Problem problem;
	private AdaptativeParameter c;
	private Random r;
	private Solution bestSolution;

	public FishSchoolSearchII(Problem problem, Random r) {
		this.r = new Random();
		this.problem = problem;
		this.school = new Fish[Parameters.SCHOOL_SIZE];
		this.c = new AdaptativeParameter();
	}

	public void init() {


		for (int i = 0; i < Parameters.SCHOOL_SIZE; i++) {
			this.school[i] = new Fish(problem, r);
			school[i].init();
		}
		initializeBestSolution();

	}

	private void initializeBestSolution() {
		int bestFish = 0;
		double bestFit = this.school[0].getCurrentSolution().getFitness();

		for (int i = 1; i < Parameters.SCHOOL_SIZE; i++) {
			if (this.problem.isFitnessBetterThan(this.school[i]
					.getCurrentSolution().getFitness(), bestFit)) {
				bestFish = i;
				bestFit = this.school[i].getCurrentSolution().getFitness();
			}
		}
		this.setBestSolution(this.school[bestFish].getCurrentSolution().clone());

	}

	private void updateBestSolution() {
		int bestFish = -1;
		double bestFit = bestSolution.getFitness();

		for (int i = 0; i < Parameters.SCHOOL_SIZE; i++) {
			if (this.problem.isFitnessBetterThan(this.school[i]
					.getCurrentSolution().getFitness(), bestFit)) {
				bestFish = i;
				bestFit = this.school[i].getCurrentSolution().getFitness();
			}
		}

		if (bestFish != -1)
			this.setBestSolution(this.school[bestFish].getCurrentSolution()
					.clone());
	}

	public void iterate() {
		c.setPreviousSuccess(c.getCurrentSuccess());

		// feed all the fishes
		schoolFeeding();
		int success = 0;
		for (Fish fish : school) {
			if (fish.getCurrentWeight() > fish.getPreviousWeight()) {
				success++;
			}
		}
		c.setCurrentSuccess(success);
		c.adapt();

		// update fish displacement regarding to previous iterations
		for (Fish fish : school) {
			double variation[] = new double[problem.getDimensions()];
			for (int i = 0; i < problem.getDimensions(); i++) {
				variation[i] = fish.getCurrentSolution().getPosition()[i]
						- fish.getPreviousSolution().getPosition()[i];
			}
			fish.setDeltaX(variation.clone());
		}

		double[][] individualMovementContribution = evaluateIndividualMovementContribution();
		double[] instinctiveMovementContribution = evaluateInstinctiveMovementContribution();
		double[][] volitiveMovementContribution = evaluateVollitiveContribution();
		movement(individualMovementContribution,
				instinctiveMovementContribution, volitiveMovementContribution);

		updateBestSolution();
	}

	public double[][] evaluateIndividualMovementContribution() {
		double[][] individual = new double[Parameters.SCHOOL_SIZE][problem
				.getDimensions()];
		for (int i = 0; i < Parameters.SCHOOL_SIZE; i++) {
			individual[i] = school[i].getDeltaX().clone();
		}
		return individual;
	}

	public double[] evaluateInstinctiveMovementContribution() {
		double[] instinctive = new double[problem.getDimensions()];

		double[] sum_prod = new double[problem.getDimensions()];
		double sum_fitness_gain = 0;

		for (int i = 0; i < sum_prod.length; i++) {
			sum_prod[i] = 0;
			instinctive[i] = 0;
		}

		for (Fish fish : school) {
			for (int i = 0; i < problem.getDimensions(); i++) {
				sum_prod[i] += (school[i].getDeltaX()[i])
						* fish.getFitnessGainNormalized();
			}

			sum_fitness_gain += fish.getFitnessGainNormalized();
		}

		// calculate global direction of good fishes
		for (int i = 0; i < sum_prod.length; i++) {
			// take care about zero division
			if (sum_fitness_gain != 0) {
				instinctive[i] = sum_prod[i] / sum_fitness_gain;
			} else {
				instinctive[i] = 0;
			}
		}

		return instinctive;
	}

	private double[][] evaluateVollitiveContribution() {


		double school_volitive[][] = new double[Parameters.SCHOOL_SIZE][problem
				.getDimensions()];
		for (int t = 0; t < school.length; t++)
			school_volitive[t] = new double[problem.getDimensions()];

		double[] school_barycentre = new double[problem.getDimensions()];
		double[] sum_prod = new double[problem.getDimensions()];
		double sum_weight_now = 0;
		double sum_weight_past = 0;

		// clear
		for (int i = 0; i < sum_prod.length; i++) {
			sum_prod[i] = 0;
			school_barycentre[i] = 0;
		}

		// each fish contribute with your position and weight
		for (Fish fish : school) {
			for (int i = 0; i < problem.getDimensions(); i++) {
				sum_prod[i] += fish.getCurrentSolution().getPosition()[i] * fish.getCurrentWeight();
			}
			// sum weight
			sum_weight_now += fish.getCurrentWeight();
			sum_weight_past += fish.getPreviousWeight();
		}
		// calculate barycentre
		for (int i = 0; i < sum_prod.length; i++) {
			school_barycentre[i] = sum_prod[i] / sum_weight_now;
		}

		double direction = 0;
		if (sum_weight_now > sum_weight_past) {
			direction = 1;
		} else {
			direction = -1;
		}

		int p = 0;
		for (Fish fish : school) {
			for (int i = 0; i < problem.getDimensions(); i++)
				school_volitive[p][i] += direction
						* (fish.getCurrentSolution().getPosition()[i] - school_barycentre[i]);
			p++;
		}

		return school_volitive;
	}

	void schoolFeeding() {

		double maxAbsFitGain = maxAbsFitnessGain();
		for (int i = 0; i < Parameters.SCHOOL_SIZE; i++) {
			this.school[i].setPreviousWeight(this.school[i].getCurrentWeight());
			if (maxAbsFitGain != 0.0) {
				// calculate normalized gain
				double temp = this.school[i].getFitnessGain() / maxAbsFitGain;
				this.school[i].setFitnessGainNormalized(temp);

				temp += this.school[i].getCurrentWeight();

				if (temp < Parameters.MINIMUM_WEIGHT)
					temp = Parameters.MINIMUM_WEIGHT;
				if (temp > Parameters.MAXIMUM_WEIGHT)
					temp = Parameters.MAXIMUM_WEIGHT;

				this.school[i].setCurrentWeight(temp);
			}
		}

	}

	double maxAbsFitnessGain() {

		double max = Math.abs(this.school[0].getFitnessGain());

		for (int i = 1; i < Parameters.SCHOOL_SIZE; i++) {
			double temp = Math.abs(this.school[i].getFitnessGain());
			if (temp > max)
				max = temp;
		}
		return max;
	}

	public void movement(double individual[][], double instinctive[],
			double volitive[][]) {

		for (int i = 0; i < Parameters.SCHOOL_SIZE; i++) {
			this.school[i].setPreviousSolution(this.school[i]
					.getCurrentSolution().clone());
			double temporaryPosition[] = new double[this.problem
					.getDimensions()];
			for (int j = 0; j < this.problem.getDimensions(); j++) {
				temporaryPosition[i] = this.school[i].getCurrentSolution()
						.getPosition()[j]
						+ (Parameters.BETA * c.getCoeficientValue() * individual[i][j])
						+ (c.getCoeficientValue() * r.nextDouble() * instinctive[j])
						+ (c.getCoeficientValue() * r.nextDouble() * volitive[i][j]);

			}
			this.school[i].updateSolution(problem
					.validatePosition(temporaryPosition));
		}

	}

	public Solution getBestSolution() {
		return bestSolution;
	}

	public void setBestSolution(Solution bestSolution) {
		this.bestSolution = bestSolution;
	}
}
