package main.java.br.com.upe.fssii.problems;

public class Sphere extends Problem {

	public Sphere(int dimensions) {
		super(dimensions);
	}

	@Override
	public void init() {
		// Do nothing
	}

	@Override
	public double getLowerBound(int dimension) {
		return -100;
	}

	@Override
	public double getUpperBound(int dimension) {
		return 100;
	}

	@Override
	public double evaluateSolution(double[] solution) {
		double sumSquare = 0.0;
		for (int i = 0; i < solution.length; i++) {
			sumSquare += (Math.pow(solution[i], 2.0));
		}
		return sumSquare;
	}

	@Override
	public boolean isFitnessBetterThan(double currentSolutionFitness,double bestSolutionFitness) {

		return currentSolutionFitness < bestSolutionFitness;
	}

}
