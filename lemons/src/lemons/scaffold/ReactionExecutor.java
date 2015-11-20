package lemons.scaffold;

import org.openscience.cdk.interfaces.IAtomContainer;

import lemons.enums.ReactionTypes;
import lemons.enums.tags.AtomTags;
import lemons.enums.tags.ScaffoldTags;
import lemons.interfaces.IReaction;
import lemons.interfaces.IScaffold;
import lemons.interfaces.ITag;
import lemons.interfaces.ITagList;
import lemons.util.ReactionsUtil;
import lemons.util.TagManipulator;
import lemons.util.exception.PolymerGenerationException;

public class ReactionExecutor {
	
	public static void executeReactions(IScaffold scaffold)
			throws PolymerGenerationException {
		for (IReaction reaction : scaffold.reactions()) {
			if (reaction.type() == ReactionTypes.CYCLIZATION) 
				executeCyclization(reaction, scaffold);
		}
	}
	
	public static void executeCyclization(IReaction reaction, IScaffold scaffold)
			throws PolymerGenerationException {
		IAtomContainer molecule = scaffold.molecule();
		ITagList<ITag> tags = reaction.getTags();
		ITag ketone = TagManipulator.getSingleTag(tags, 
				ScaffoldTags.BACKBONE_KETONE);
		ITag cyclization = null;
		if (tags.contains(ScaffoldTags.BACKBONE_NITROGEN)) {
			cyclization = TagManipulator.getSingleTag(tags, 
					ScaffoldTags.BACKBONE_NITROGEN);
		} else {
			cyclization = TagManipulator.getSingleTag(tags, 
					AtomTags.HYDROXYL);
		}
		ReactionsUtil.removeAlcohol(ketone.atom(), molecule);
		ReactionsUtil.addBond(cyclization.atom(), ketone.atom(), molecule);
	}
	
}
