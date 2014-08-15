package src.main.java.br.com.upe.fssii.problems;

public class Griewank extends Problem {

	public Griewank(int dimensions) {
		super(dimensions);
	}

	@Override
	public void init() {
		//Do nothing
	}

	@Override
	public double getLowerBound(int dimension) {
		return -600;
	}

	@Override
	public double getUpperBound(int dimension) {
		return 600;
	}

	@Override
	public double evaluateSolution(double[] solution) {
		double fitness = 0;
        double auxSum = 0;
        double auxMul = 1;
        
        for (int i = 0; i < this.dimensions; i++) {
            auxSum += ((solution[i] * solution[i]) / 4000);
            auxMul *= Math.cos(solution[i] / Math.sqrt(i + 1));
        }
        
        fitness = 1 + auxSum - auxMul;
        
        return fitness;
	}

	@Override
	public boolean isFitnessBetterThan(double currentSolutionFitness,
			double bestSolutionFitness) {
		return currentSolutionFitness<bestSolutionFitness;
	}

}
