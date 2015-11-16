package lemons.data;

import lemons.interfaces.ITanimotoCoefficient;

public class TanimotoCoefficient implements ITanimotoCoefficient {
	
	private String name1;
	private String name2;
	private double value;
	
	public TanimotoCoefficient(String name1, String name2, double value) {
		this.name1 = name1;
		this.name2 = name2;
		this.value = value;
	}
	
	public String name1() {
		return name1;
	}
	
	public String name2() {
		return name2;
	}
	
	public double value() {
		return value;
	}

}
