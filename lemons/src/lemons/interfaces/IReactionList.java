package lemons.interfaces;

import java.util.List;

public interface IReactionList<T> extends List<T> {
	
	public IReactionList<T> getReactions(Class<?> clazz);

}
