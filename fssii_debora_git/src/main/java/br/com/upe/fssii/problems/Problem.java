package main.java.br.com.upe.fssii.problems;

public abstract class Problem {

	protected int dimensions;
	
	public Problem(int dimensions){
		this.dimensions = dimensions;
	}

	/**
	 * Makes the initial setup setting the pre informed parameters where are
	 * required and initializes what is required.
	 */
	public abstract void init();

	/**
	 * Returns the lower bound of the search space for this problem in the given
	 * dimension.
	 * 
	 * @param dimension
	 *            The dimension for which we want to know the lower limit.
	 * @return The lower bound of the search space for this problem in the given
	 *         dimension.
	 */
	public abstract double getLowerBound(int dimension);

	/**
	 * Returns the upper bound of the search space for this problem in the given
	 * dimension.
	 * 
	 * @param dimension
	 *            The dimension for which we want to know the upper limit.
	 * @return The upper bound of the search space for this problem in the given
	 *         dimension.
	 */
	public abstract double getUpperBound(int dimension);

	/**
	 * Calculates the fitness of the given solution.
	 * 
	 * @param solution
	 *            The values of the solution in each dimension of the problem.
	 * @return The fitness of the given solution.
	 */
	public abstract double evaluateSolution(double[] solution);

	/**
	 * Compares the bestSolutionFitness with the currentSolutionFitness and
	 * returns <code>true</code> if currentSolutionFitness has a better fitness
	 * than bestSolutionFitness, otherwise it returns <code>false</code>.
	 * 
	 * @param bestSolutionFitness
	 *            The fitness of the best solution.
	 * @param currentSolutionFitness
	 *            The fitness of the current solution.
	 * @return <code>true</code> if currentSolutionFitness has a better fitness
	 *         than bestSolutionFitness, otherwise it returns <code>false</code>
	 *         .
	 */
	public abstract boolean isFitnessBetterThan(double currentSolutionFitness,double bestSolutionFitness);

	public int getDimensions() {
		return dimensions;
	}

	public void setDimensions(int dimensions) {
		this.dimensions = dimensions;
	}

	public double[] validatePosition(double[] position) {

		double[] newPosition = position.clone();
		for (int i = 0; i < dimensions; i++) {
			if (position[i] >= getUpperBound(i)) {
				newPosition[i] = getUpperBound(i);
			}
			if (position[i] <= getLowerBound(i)) {
				newPosition[i] = getLowerBound(i);
			}
		}
		return newPosition;
	}

}
