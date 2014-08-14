package main.java.br.com.upe.fssii.algorithm;

import java.util.Random;

import main.java.br.com.upe.fssii.problems.Problem;

public class Fish {

	private Solution currentSolution;
	private Solution previousSolution;

	private double currentWeight;
	private double previousWeight;
	private double[] deltaX;
	
	private Problem problem;
	private double deltaFitness;
	private double deltaWeight;
	private Random r;
	
	
	
	private double fitnessGain;
	private double fitnessGainNormalized;
	
	

	public Fish(Problem problem, Random r) {
		this.r = r;
		this.problem = problem;
		this.currentSolution = new Solution(problem.getDimensions());
		this.previousSolution = new Solution(problem.getDimensions());
		this.deltaX = new double[problem.getDimensions()];
	}

	public void init() {

		// initializes initial weight and positions.
		currentWeight = Parameters.INITIAL_WEIGHT;
		previousWeight = currentWeight;
		double[] position = new double[problem.getDimensions()];
		
		for (int i = 0; i < this.problem.getDimensions(); i++) {
			position[i] = getRandomNumber(problem.getUpperBound(i) / 2,
					problem.getUpperBound(i), r);
		}
		this.currentSolution.setPosition(position);
		this.currentSolution.setFitness(problem
				.evaluateSolution(currentSolution.getPosition()));

		// apllies initial local search to trigger the main algorithm loop
		double[] newPosition = new double[problem.getDimensions()];
		for (int i = 0; i < problem.getDimensions(); i++) {
			newPosition[i] = currentSolution.getPosition()[i] += getRandomNumber(
					-1.0, 1.0, r);
		}
		updateSolution(problem.validatePosition(newPosition));

	}

	public void updateSolution(double[] position) {
		this.setPreviousSolution(this.currentSolution.clone());
		this.currentSolution.setPosition(position);
		this.currentSolution.setFitness(problem
				.evaluateSolution(currentSolution.getPosition()));
		this.deltaFitness = currentSolution.getFitness()-currentSolution.getFitness(); 
		
		boolean a = this.problem.isFitnessBetterThan(currentSolution.getFitness(), previousSolution.getFitness());
		boolean b = this.deltaFitness >=0;
		if(a == b){
			this.fitnessGain = this.deltaFitness;	
		}
		else{
			this.fitnessGain = -this.deltaFitness;
		}
	}

	private double getRandomNumber(double min, double max, Random r) {
		return min + (max - min) * r.nextDouble();
	}

	public double getCurrentWeight() {
		return currentWeight;
	}

	public void setCurrentWeight(double currentWeight) {
		this.currentWeight = currentWeight;
	}

	public double getPreviousWeight() {
		return previousWeight;
	}

	public void setPreviousWeight(double previousWeight) {
		this.previousWeight = previousWeight;
	}

	public Problem getProblem() {
		return problem;
	}

	public void setProblem(Problem problem) {
		this.problem = problem;
	}

	public double getDeltaFitness() {
		return deltaFitness;
	}

	public void setDeltaFitness(double deltaFitness) {
		this.deltaFitness = deltaFitness;
	}

	public double getDeltaWeight() {
		return deltaWeight;
	}

	public void setDeltaWeight(double deltaWeight) {
		this.deltaWeight = deltaWeight;
	}

	public Solution getPreviousSolution() {
		return previousSolution;
	}

	public void setPreviousSolution(Solution previousSolution) {
		this.previousSolution = previousSolution;
	}

	public Solution getCurrentSolution() {
		return currentSolution;
	}

	public void setCurrentSolution(Solution currentSolution) {
		this.currentSolution = currentSolution;
	}

	public double getFitnessGain() {
		return fitnessGain;
	}

	public void setFitnessGain(double fitnessGain) {
		this.fitnessGain = fitnessGain;
	}

	public double getFitnessGainNormalized() {
		return fitnessGainNormalized;
	}

	public void setFitnessGainNormalized(double fitnessGainNormalized) {
		this.fitnessGainNormalized = fitnessGainNormalized;
	}

	public double[] getDeltaX() {
		return deltaX;
	}

	public void setDeltaX(double[] deltaX) {
		this.deltaX = deltaX;
	}

}
