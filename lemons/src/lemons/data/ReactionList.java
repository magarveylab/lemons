package lemons.data;

import java.util.ArrayList;
import java.util.Iterator;

import lemons.interfaces.IReaction;
import lemons.interfaces.IReactionList;

public class ReactionList extends ArrayList<IReaction>
		implements IReactionList<IReaction> {
	
	private static final long serialVersionUID = 2830462289769010484L;

	public IReactionList<IReaction> getReactions(Class<?> clazz) {
		IReactionList<IReaction> reactions = new ReactionList();
		Iterator<IReaction> itr = this.iterator();
		while (itr.hasNext()) {
			IReaction reaction = itr.next();
			if (clazz.isInstance(reaction)) 
				reactions.add(reaction);
		}
		return reactions;

	}

}
