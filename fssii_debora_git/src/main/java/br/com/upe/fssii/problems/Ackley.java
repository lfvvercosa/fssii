package main.java.br.com.upe.fssii.problems;


public class Ackley extends Problem {

	public Ackley(int dimensions) {
		super(dimensions);
	}

	@Override
	public void init() {
		//Do nothing
	}

	@Override
	public double getLowerBound(int dimension) {
		return -32;
	}

	@Override
	public double getUpperBound(int dimension) {
		return 32;
	}

	@Override
	public double evaluateSolution(double[] solution) {
		double fitness = 0;
		double auxExp = 0;
		double auxCos = 0;

		for (int i = 0; i < this.dimensions; i++) {
			auxExp += solution[i] * solution[i];
			auxCos += Math.cos(2 * Math.PI * solution[i]);
		}

		auxExp = auxExp / this.dimensions;
		auxExp = Math.sqrt(auxExp);
		auxExp = auxExp * (-0.2);

		auxCos = auxCos / this.dimensions;

		fitness = (-1) * 20 * Math.exp(auxExp) - 1 * Math.exp(auxCos) + 20
				+ Math.E;

		return fitness;

	}

	@Override
	public boolean isFitnessBetterThan(double currentSolutionFitness,
			double bestSolutionFitness) {
		return currentSolutionFitness<bestSolutionFitness;
	}

}
