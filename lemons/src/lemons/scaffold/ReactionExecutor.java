package lemons.scaffold;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.openscience.cdk.exception.CDKException;
import org.openscience.cdk.interfaces.IAtom;
import org.openscience.cdk.interfaces.IAtomContainer;

import lemons.enums.Reactions;
import lemons.enums.tags.ReactionTags;
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
	
	public static void executeReactions(List<IScaffold> scaffolds)
			throws PolymerGenerationException, CDKException {
		for (IScaffold s : scaffolds)
			executeReactions(s);
	}

	public static void executeReactions(IScaffold scaffold)
			throws PolymerGenerationException, CDKException {
		for (IReaction reaction : scaffold.reactions()) {
			if (reaction.type() == Reactions.CYCLIZATION) 
				executeCyclization(reaction, scaffold);
			if (reaction.type() == Reactions.N_METHYLATION)
				executeNMethylation(reaction, scaffold);
		}
		logger.log(Level.INFO, "Executed reactions, new scaffold: " + SmilesIO.smiles(scaffold.molecule()));
	}
	
	public static void executeCyclization(IReaction reaction, IScaffold scaffold)
			throws PolymerGenerationException, CDKException {
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
					ReactionTags.SP3_CARBON_HYDROXYL);
		}
		IAtom ketoneAtom = ketone.atom();
		IAtom cyclizationAtom = cyclization.atom();
		
		// correct valency of cyclization atom 
		int valency = cyclizationAtom.getValency();
		int bondOrderSum = (int) molecule.getBondOrderSum(cyclizationAtom)
				+ cyclizationAtom.getImplicitHydrogenCount() + 1; // (a bond is about to be added) 		
		if (valency != bondOrderSum) {
			int remainder = valency - bondOrderSum;
			cyclizationAtom.setImplicitHydrogenCount(cyclizationAtom.getImplicitHydrogenCount() + remainder);
		}

		ReactionsUtil.removeAlcohol(ketoneAtom, molecule);
		ReactionsUtil.addBond(cyclizationAtom, ketoneAtom, molecule);
	}
	
	public static void executeNMethylation(IReaction reaction, IScaffold scaffold)
			throws PolymerGenerationException, CDKException {
		IAtomContainer molecule = scaffold.molecule();
		ITagList<ITag> tags = reaction.getTags();
		ITag nitrogen = TagManipulator.getSingleTag(tags, 
				ScaffoldTags.BACKBONE_NITROGEN);
		
		String smiles = "IC";
		ReactionsUtil.functionalize(smiles, nitrogen.atom(), molecule);
	}
	
}
