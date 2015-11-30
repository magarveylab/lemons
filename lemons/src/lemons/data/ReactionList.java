package lemons.data;

import java.util.ArrayList;
import java.util.Iterator;

import lemons.interfaces.IReaction;
import lemons.interfaces.IReactionList;
import lemons.interfaces.IReactionType;

public class ReactionList extends ArrayList<IReaction>
		implements IReactionList<IReaction> {
	
	private static final long serialVersionUID = 2830462289769010484L;

	public IReactionList<IReaction> getReactions(IReactionType type) {
		IReactionList<IReaction> reactions = new ReactionList();
		Iterator<IReaction> itr = this.iterator();
		while (itr.hasNext()) {
			IReaction reaction = itr.next();
			if (reaction.type().equals(type))
				reactions.add(reaction);
		}
		return reactions;
	}
	
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
