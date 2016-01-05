package lemons.interfaces;

/**
 * A Tanimoto coefficient between two scaffolds.
 * 
 * @author michaelskinnider
 *
 */
public interface ITanimotoCoefficient {

	/**
	 * Get the name of the first scaffold.
	 * 
	 * @return the first scaffold name
	 */
	public String name1();

	/**
	 * Get the name of the second scaffold.
	 * 
	 * @return the second scaffold name
	 */
	public String name2();

	/**
	 * Get the value of the Tanimoto coefficient.
	 * 
	 * @return the value of the Tanimoto coefficient
	 */
	public double value();

}
