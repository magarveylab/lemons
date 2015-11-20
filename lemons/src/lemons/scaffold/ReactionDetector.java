package lemons.scaffold;

import java.util.Map;

import lemons.Config;
import lemons.data.Reaction;
import lemons.data.TagList;
import lemons.enums.ReactionTypes;
import lemons.enums.tags.ScaffoldTags;
import lemons.interfaces.IMonomer;
import lemons.interfaces.IReaction;
import lemons.interfaces.IReactionType;
import lemons.interfaces.IScaffold;
import lemons.interfaces.ITag;
import lemons.interfaces.ITagList;
import lemons.util.TagManipulator;
import lemons.util.exception.BadTagException;

public class ReactionDetector {
	
	public static void detectReactions(IScaffold scaffold)
			throws BadTagException {
		Map<IReactionType, Integer> reactions = Config.REACTIONS;
		if (reactions.containsKey(ReactionTypes.CYCLIZATION)
				&& reactions.get(ReactionTypes.CYCLIZATION) > 0)
			detectCyclizations(scaffold);
		if (reactions.containsKey(ReactionTypes.AZOLE)
				&& reactions.get(ReactionTypes.AZOLE) > 0)
			detectOxazolesOrThiazoles(scaffold);
	}
	
	public static void detectCyclizations(IScaffold scaffold) throws BadTagException {
		// length check 
		if (scaffold.size() < 4)
			return;
		
		// get C-terminus
		IMonomer last = scaffold.getMonomer(scaffold.size() - 1);
		ITag cTerminus = TagManipulator.getSingleTag(last, ScaffoldTags.BACKBONE_KETONE);
		
		// get N-terminus
		IMonomer first = scaffold.getMonomer(0);
		ITag nitrogen = TagManipulator.getSingleTag(first, ScaffoldTags.BACKBONE_NITROGEN);
		
		// perceive -OH tags
		ITagList<ITag> hydroxyls = new TagList();
		for (int i = 3; i < scaffold.size(); i++) {
			IMonomer monomer = scaffold.getMonomer(i);
			hydroxyls.addAll(TagPeceiver.perceiveHydroxyls(monomer));
		}

		// create reactions
		for (ITag hydroxyl : hydroxyls) {
			IReaction reaction = new Reaction(ReactionTypes.CYCLIZATION);
			reaction.addTag(cTerminus);
			reaction.addTag(hydroxyl);
			scaffold.addReaction(reaction);
		}
		IReaction reaction = new Reaction(ReactionTypes.CYCLIZATION);
		reaction.addTag(cTerminus);
		reaction.addTag(nitrogen);
		scaffold.addReaction(reaction);
	}
	
	public static void detectOxazolesOrThiazoles(IScaffold scaffold) {
		// perceive O/S-ketone pairs
		for (int i = 1; i < scaffold.size(); i++) {
			
		}
	}
	
}
