package lemons.interfaces;

import java.util.List;

public interface ITanimotoCoefficientList<T> extends List<T> {
	
	public ITanimotoCoefficient getMatch(String name);
}
