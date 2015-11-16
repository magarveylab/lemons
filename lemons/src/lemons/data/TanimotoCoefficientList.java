package lemons.data;

import java.util.ArrayList;
import java.util.Iterator;

import lemons.interfaces.ITanimotoCoefficient;
import lemons.interfaces.ITanimotoCoefficientList;

public class TanimotoCoefficientList extends ArrayList<ITanimotoCoefficient>
		implements ITanimotoCoefficientList<ITanimotoCoefficient> {
	
	private static final long serialVersionUID = 2790751306387018327L;

	@Override
	public ITanimotoCoefficient getMatch(String name) {
		ITanimotoCoefficient tc = null;
		Iterator<ITanimotoCoefficient> itr = this.iterator();
		while (itr.hasNext()) {
			ITanimotoCoefficient t = itr.next();
			if (t.name1().equals(name) && t.name2().equals(name))
				tc = t;
		}
		return tc;
	}
	
}
