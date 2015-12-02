package lemons.scaffold;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.openscience.cdk.exception.CDKException;
import org.openscience.cdk.interfaces.IAtom;
import org.openscience.cdk.interfaces.IAtomContainer;

import lemons.enums.ReactionTypes;
import lemons.enums.tags.AtomTags;
import lemons.enums.tags.ScaffoldTags;
import lemons.interfaces.IReaction;
import lemons.interfaces.IScaffold;
import lemons.interfaces.ITag;
import lemons.interfaces.ITagList;
import lemons.io.SmilesIO;
import lemons.util.ReactionsUtil;
import lemons.util.TagManipulator;
import lemons.util.exception.PolymerGenerationException;

public class ReactionExecutor {
	
	private static final Logger logger = Logger.getLogger(ReactionExecutor.class.getName());
	
	public static void executeReactions(IScaffold scaffold)
			throws PolymerGenerationException, CDKException {
		for (IReaction reaction : scaffold.reactions()) {
			if (reaction.type() == ReactionTypes.CYCLIZATION) 
				executeCyclization(reaction, scaffold);
		}
		logger.log(Level.INFO, "Executed reactions, new scaffold: " + SmilesIO.smiles(scaffold.molecule()));
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
		IAtom ketoneAtom = ketone.atom();
		IAtom cyclizationAtom = cyclization.atom();
		cyclizationAtom.setImplicitHydrogenCount(cyclizationAtom.getImplicitHydrogenCount() - 1);
		ReactionsUtil.removeAlcohol(ketoneAtom, molecule);
		ReactionsUtil.addBond(cyclization.atom(), ketone.atom(), molecule);
	}
	
}
