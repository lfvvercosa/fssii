package main.java.br.com.upe.fssii.algorithm;

public class Solution {
	
	private double[] position;
	private double fitness;
	
	public Solution(int dimension){
		this.position = new double[dimension];
	}
	
	public double[] getPosition() {
		return position;
	}
	public void setPosition(double[] position) {
		this.position = position;
	}
	public double getFitness() {
		return fitness;
	}
	public void setFitness(double fitness) {
		this.fitness = fitness;
	}
	
	public Solution clone(){
		Solution clone = new Solution(this.position.length);
		clone.setPosition(this.position.clone());
		clone.setFitness(this.fitness);
		return clone;
	}
	
	
	


}
