package main.java.br.com.upe.fssii.problems;

public class Rastrigin extends Problem {

	public Rastrigin(int dimensions) {
		super(dimensions);
	}

	@Override
	public void init() {
		//Do nothing
	}

	@Override
	public double getLowerBound(int dimension) {
		return -5.12;
	}

	@Override
	public double getUpperBound(int dimension) {
		return 5.12;
	}

	@Override
	public double evaluateSolution(double[] solution) {
		double fitness = 0;

		for (int i = 0; i < this.dimensions; i++) {
			fitness += (solution[i] * solution[i])
					- (10 * Math.cos(2 * Math.PI * solution[i])) + 10;
		}

		return fitness;
	}

	@Override
	public boolean isFitnessBetterThan(double currentSolutionFitness,
			double bestSolutionFitness) {
		return currentSolutionFitness<bestSolutionFitness;
	}

}
