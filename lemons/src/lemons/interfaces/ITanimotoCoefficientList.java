package lemons.interfaces;

import java.util.List;

/**
 * A list of Tanimoto coefficients.
 * 
 * @author michaelskinnider
 *
 * @param <T>
 */
public interface ITanimotoCoefficientList<T> extends List<T> {

	/**
	 * Get the Tanimoto coefficient of the match between two scaffolds with the
	 * same name.
	 * 
	 * @param name
	 *            the query name
	 * @return the Tanimoto coefficient of the original and swapped scaffold
	 */
	public ITanimotoCoefficient getMatch(String name);
}
