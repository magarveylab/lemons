package lemons.scaffold.reaction;

import org.openscience.cdk.exception.CDKException;
import org.openscience.cdk.interfaces.IAtom;
import org.openscience.cdk.interfaces.IAtomContainer;

import lemons.data.Reaction;
import lemons.data.ReactionList;
import lemons.data.TagList;
import lemons.enums.Reactions;
import lemons.enums.tags.ScaffoldTags;
import lemons.interfaces.IAminoAcidType;
import lemons.interfaces.IMonomer;
import lemons.interfaces.IReaction;
import lemons.interfaces.IReactionList;
import lemons.interfaces.IReactionPlanner;
import lemons.interfaces.IScaffold;
import lemons.interfaces.ITag;
import lemons.interfaces.ITagList;
import lemons.util.RandomUtil;
import lemons.util.ReactionsUtil;
import lemons.util.TagManipulator;
import lemons.util.exception.BadTagException;
import lemons.util.exception.PolymerGenerationException;

/**
 * N-methylation of backbone nitrogen (amino acid) residues.
 * 
 * @author michaelskinnider
 *
 */
public class NMethylation implements IReactionPlanner {

	public void perceive(double numReactions, IScaffold scaffold)
			throws BadTagException {
		// perceive nitrogens
		ITagList<ITag> nitrogens = new TagList();
		IAtomContainer molecule = scaffold.molecule();
		for (IMonomer monomer : scaffold.monomers()) {
			if (monomer.type() instanceof IAminoAcidType) {
				ITag nitrogen = TagManipulator.getSingleTag(monomer,
						ScaffoldTags.BACKBONE_NITROGEN);

				// check ability to form new bond
				IAtom atom = nitrogen.atom();
				if (molecule.getBondOrderSum(atom) >= 3) 
					continue;

				nitrogens.add(nitrogen);
			}
		}
		
		// create reactions
		IReactionList<IReaction> reactions = new ReactionList();
		for (ITag nitrogen : nitrogens) {
			IReaction reaction = new Reaction(Reactions.N_METHYLATION);
			reaction.addTag(nitrogen);
			reactions.add(reaction);
		}
		
		// pick random reactions
		IReactionList<IReaction> random = RandomUtil.pickRandomReactions(
				numReactions, reactions);
		scaffold.addReactions(random);
	}

	public void execute(IReaction reaction, IScaffold scaffold)
			throws PolymerGenerationException, CDKException {
		IAtomContainer molecule = scaffold.molecule();
		ITagList<ITag> tags = reaction.getTags();
		ITag nitrogen = TagManipulator.getSingleTag(tags,
				ScaffoldTags.BACKBONE_NITROGEN);

		String smiles = "IC";
		ReactionsUtil.functionalize(smiles, nitrogen.atom(), molecule);
	}

}
