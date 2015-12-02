package lemons.scaffold;

import lemons.data.Reaction;
import lemons.data.ReactionList;
import lemons.data.TagList;
import lemons.enums.ReactionTypes;
import lemons.enums.tags.ScaffoldTags;
import lemons.interfaces.IMonomer;
import lemons.interfaces.IReaction;
import lemons.interfaces.IReactionList;
import lemons.interfaces.IReactionType;
import lemons.interfaces.IScaffold;
import lemons.interfaces.ITag;
import lemons.interfaces.ITagList;
import lemons.util.RandomUtil;
import lemons.util.TagManipulator;
import lemons.util.exception.BadTagException;

public class ReactionPerceiver {
	
	public static void detectReactions(IReactionType type, double num, IScaffold scaffold)
			throws BadTagException {
		if	(type == ReactionTypes.CYCLIZATION)
			detectCyclizations(num, scaffold);
		if	(type == ReactionTypes.AZOLE)
			detectOxazolesOrThiazoles(num, scaffold);
	}
	
	public static void detectCyclizations(double numReactions, IScaffold scaffold)
			throws BadTagException {
		// length check
		if (scaffold.size() < 4)
			return;
		
		// get C-terminus
		IMonomer last = scaffold.getMonomer(0);
		ITag cTerminus = TagManipulator.getSingleTag(last,
				ScaffoldTags.BACKBONE_KETONE);

		// get N-terminus
		IMonomer first = scaffold.getMonomer(scaffold.size() - 1);
		ITag nitrogen = TagManipulator.getSingleTag(first,
				ScaffoldTags.BACKBONE_NITROGEN);
		
		// perceive -OH tags
		ITagList<ITag> hydroxyls = new TagList();
		for (int i = 3; i < scaffold.size(); i++) {
			IMonomer monomer = scaffold.getMonomer(i);
			hydroxyls.addAll(TagPeceiver.perceiveHydroxyls(monomer));
		}

		// create reactions
		IReactionList<IReaction> reactions = new ReactionList();
		for (ITag hydroxyl : hydroxyls) {
			IReaction reaction = new Reaction(ReactionTypes.CYCLIZATION);
			reaction.addTag(cTerminus);
			reaction.addTag(hydroxyl);
			reactions.add(reaction);
		}
		if (nitrogen != null) {
			IReaction reaction = new Reaction(ReactionTypes.CYCLIZATION);
			reaction.addTag(cTerminus);
			reaction.addTag(nitrogen);
			reactions.add(reaction);
		}
		
		// pick random reactions 
		IReactionList<IReaction> random = RandomUtil.pickRandomReactions(
				numReactions, reactions);
		scaffold.addReactions(random);
	}
		
	public static void detectOxazolesOrThiazoles(double numReactions, IScaffold scaffold) {
		// perceive O/S-ketone pairs
		for (int i = 1; i < scaffold.size(); i++) {
			
		}
	}
	
}
