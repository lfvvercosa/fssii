package src.main.java.br.com.upe.fssii.problems;

public class Rosenbrock extends Problem {

	public Rosenbrock(int dimensions) {
		super(dimensions);
	}
	
	@Override
	public void init() {
		//do nothing
	}

	@Override
	public double getLowerBound(int dimension) {
		return -30;
	}

	@Override
	public double getUpperBound(int dimension) {
		return 30;
	}

	@Override
	public double evaluateSolution(double[] solution) {
		double fitness = 0;

		for (int i = 0; i < this.dimensions - 1; i++) {
			fitness += 100
					* Math.pow(solution[i + 1] - Math.pow(solution[i], 2.0),
							2.0) + Math.pow(solution[i] - 1.0, 2.0);
		}

		return fitness;
	}

	@Override
	public boolean isFitnessBetterThan(double currentSolutionFitness,
			double bestSolutionFitness) {
		return currentSolutionFitness<bestSolutionFitness;
	}

}
