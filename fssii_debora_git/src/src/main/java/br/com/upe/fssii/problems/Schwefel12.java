package src.main.java.br.com.upe.fssii.problems;

public class Schwefel12 extends Problem {

	public Schwefel12(int dimensions) {
		super(dimensions);
	}

	@Override
	public void init() {
		//Do nothing
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
		double fitness = 0;
		double auxFit = 0;

		for (int i = 0; i < this.dimensions; i++) {
			for (int j = 0; j <= i; j++) {
				auxFit += solution[j];
			}
			fitness += (auxFit * auxFit);
			auxFit = 0;
		}
		return fitness;
	}

	@Override
	public boolean isFitnessBetterThan(double currentSolutionFitness,
			double bestSolutionFitness) {
		return currentSolutionFitness<bestSolutionFitness;
	}

}
