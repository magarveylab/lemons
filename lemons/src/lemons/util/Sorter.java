package lemons.util;

import java.util.Collections;
import java.util.Comparator;

import lemons.interfaces.ITanimotoCoefficient;
import lemons.interfaces.ITanimotoCoefficientList;

/**
 * Performs sorting operations. 
 * @author michaelskinnider
 *
 */
public class Sorter {
	
	/**
	 * Sort a list of Tanimoto coefficients by their value, largest first. 
	 * @param tcList	list of Tanimoto coefficients 
	 */
	public static void sort(ITanimotoCoefficientList<ITanimotoCoefficient> tcList) {
		Collections.sort(tcList, new Comparator<ITanimotoCoefficient>() {
			@Override
			public int compare(ITanimotoCoefficient tc1, ITanimotoCoefficient tc2) {
				return Double.compare(tc1.value(), tc2.value()) * -1;
			}
		});
	}
	
}
