package src.main.java.br.com.upe.fssii.algorithm;



public class AdaptativeParameter {
	private double coeficientValue;
	private int adaptativeDirection;
	private int currentSuccess;
	private int previousSuccess;
	
	
	
	public AdaptativeParameter(){
		this.coeficientValue = Parameters.INITIAL_C;
		this.adaptativeDirection = -1;
		this.currentSuccess = 0;
		this.previousSuccess = 0;
	}
	
	public void adapt(){
		if (currentSuccess > previousSuccess)
			adaptativeDirection = -1;
		else
			adaptativeDirection =1;
		
		coeficientValue += (adaptativeDirection * Parameters.ALPHA) * coeficientValue;
		
		if (coeficientValue < Parameters.MINIMUM_C)
			coeficientValue = Parameters.MINIMUM_C;
		if (coeficientValue > Parameters.MAXIMUM_C)
			coeficientValue = Parameters.MAXIMUM_C;
		
	}
		
	public double getCoeficientValue() {
		return coeficientValue;
	}

	public void setCoeficientValue(double coeficientValue) {
		this.coeficientValue = coeficientValue;
	}

	public int getCurrentSuccess() {
		return currentSuccess;
	}

	public void setCurrentSuccess(int currentSuccess) {
		this.currentSuccess = currentSuccess;
	}

	public int getPreviousSuccess() {
		return previousSuccess;
	}

	public void setPreviousSuccess(int previousSuccess) {
		this.previousSuccess = previousSuccess;
	}

	public void update(){
		
	}
	
	

}
