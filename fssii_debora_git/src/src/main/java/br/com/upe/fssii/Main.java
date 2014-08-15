package src.main.java.br.com.upe.fssii;

import java.util.Random;

import src.main.java.br.com.upe.fssii.algorithm.FishSchoolSearchII;
import src.main.java.br.com.upe.fssii.algorithm.Parameters;
import src.main.java.br.com.upe.fssii.problems.Sphere;


public class Main {

	public static void main(String[] args) {

		Random r = new Random();
		Sphere problem = new Sphere(Parameters.DIMENSION);
//		Rosenbrock problem = new Rosenbrock(Parameters.DIMENSION);
//		Schwefel12 problem = new Schwefel12(Parameters.DIMENSION);
//		Rastrigin problem = new Rastrigin(Parameters.DIMENSION);
//		Griewank problem = new Griewank(Parameters.DIMENSION);
//		Ackley problem = new Ackley(Parameters.DIMENSION);
		int fitnessEvaluations = 0;
		FishSchoolSearchII school = new FishSchoolSearchII(problem, r);
		school.init();
		fitnessEvaluations+=Parameters.SCHOOL_SIZE;
		System.out.println(school.getBestSolution().getFitness());
		while(fitnessEvaluations<300000){
			school.iterate();
			fitnessEvaluations+=Parameters.SCHOOL_SIZE;
			System.out.println(school.getBestSolution().getFitness());
		}
		System.out.println(school.getBestSolution().getFitness());
		
		
	}


}
