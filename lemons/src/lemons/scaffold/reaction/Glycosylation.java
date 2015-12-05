package lemons.scaffold.reaction;

import org.openscience.cdk.exception.CDKException;
import org.openscience.cdk.interfaces.IAtomContainer;

import lemons.data.Reaction;
import lemons.data.ReactionList;
import lemons.data.TagList;
import lemons.enums.Reactions;
import lemons.enums.monomers.Sugars;
import lemons.enums.tags.ReactionTags;
import lemons.interfaces.IMonomer;
import lemons.interfaces.IReaction;
import lemons.interfaces.IReactionList;
import lemons.interfaces.IReactionPlanner;
import lemons.interfaces.IScaffold;
import lemons.interfaces.ITag;
import lemons.interfaces.ITagList;
import lemons.scaffold.TagPerceiver;
import lemons.util.RandomUtil;
import lemons.util.ReactionsUtil;
import lemons.util.exception.BadTagException;
import lemons.util.exception.PolymerGenerationException;

/**
 * Glycosylation of a free, aliphatic, SP3 carbon-bound hydroxyl with a hexose
 * or deoxy sugar.
 * 
 * @author michaelskinnider
 *
 */
public class Glycosylation implements IReactionPlanner {

	public void perceive(double numReactions, IScaffold scaffold)
			throws BadTagException, CDKException {
		// perceive -OH tags
		ITagList<ITag> hydroxyls = new TagList();
		for (IMonomer monomer : scaffold.monomers()) {
			TagPerceiver.perceiveHydroxyls(monomer,
					scaffold.molecule());
			hydroxyls.addAll(monomer.getTags(ReactionTags.SP2_CARBON_HYDROXYL));
			hydroxyls.addAll(monomer.getTags(ReactionTags.SP3_CARBON_HYDROXYL));
		}

		// create reactions
		IReactionList<IReaction> reactions = new ReactionList();
		for (ITag hydroxyl : hydroxyls) {
			IReaction reaction = new Reaction(Reactions.GLYCOSYLATION);
			
			// set smiles 
			int idx = RandomUtil.randomInt(0, Sugars.values().length - 1);
			Sugars sugar = Sugars.values()[idx];
			String smiles = sugar.smiles();
			reaction.setSmiles(smiles);
			
			reaction.addTag(hydroxyl);
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
		ITag hydroxyl = tags.get(0);

		ReactionsUtil.functionalize(reaction.smiles(), hydroxyl.atom(), molecule);
	}

}
