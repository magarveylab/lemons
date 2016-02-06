package lemons.scaffold.reaction;

import java.util.ArrayList;
import java.util.List;

import org.openscience.cdk.exception.CDKException;
import org.openscience.cdk.interfaces.IAtom;
import org.openscience.cdk.interfaces.IAtomContainer;

import lemons.data.Reaction;
import lemons.data.ReactionList;
import lemons.data.Tag;
import lemons.enums.Reactions;
import lemons.enums.tags.ReactionTags;
import lemons.interfaces.IMonomer;
import lemons.interfaces.IReaction;
import lemons.interfaces.IReactionList;
import lemons.interfaces.IReactionPlanner;
import lemons.interfaces.IScaffold;
import lemons.interfaces.ITag;
import lemons.interfaces.ITagList;
import lemons.util.RandomUtil;
import lemons.util.ReactionsUtil;
import lemons.util.exception.BadTagException;
import lemons.util.exception.PolymerGenerationException;

/**
 * Addition of a single bond between any two random atoms within a molecule. 
 *
 * @author michaelskinnider
 *
 */
public class RandomBondReaction implements IReactionPlanner {
	
//	private static final Logger logger = Logger.getLogger(RandomBondReaction.class.getName());

	public void perceive(double numReactions, IScaffold scaffold)
			throws BadTagException, CDKException {
		IAtomContainer molecule = scaffold.molecule();
		
		List<IAtom> atoms = new ArrayList<IAtom>();
		for (IMonomer monomer : scaffold.monomers())
			for (IAtom atom : monomer.structure().atoms())
				atoms.add(atom);
		int atomCount = atoms.size();
		boolean[] used = new boolean[atomCount];
		
		IReactionList<IReaction> reactions = new ReactionList();
		for (int i = 0; i < numReactions + 1; i++) {		
			int r1 = -1;
			IAtom atom1 = null, atom2 = null;
			while (r1 == -1 || used[r1]) {
				r1 = RandomUtil.randomInt(0, atomCount - 1);
				atom1 = atoms.get(r1);
				String symbol = atom1.getSymbol();				
				if (symbol.equals("N")) {
					if (molecule.getConnectedBondsCount(atom1) >= 3
							&& molecule.getBondOrderSum(atom1) >= 3) {
						r1 = -1;
						continue;
					}
				} else if (symbol.equals("C")) {
					if (molecule.getConnectedBondsCount(atom1) >= 4
							&& molecule.getBondOrderSum(atom1) >= 4) {
						r1 = -1;
						continue;
					}
				} else if (symbol.equals("O") || symbol.equals("S")) {
					if (molecule.getConnectedBondsCount(atom1) >= 2
							&& molecule.getBondOrderSum(atom1) >= 2) {
						r1 = -1;
						continue;
					}
				} else {
					r1 = -1;
					continue;
				}
			}
			int r2 = -1;
			while (r2 == -1 || r2 == r1 || used[r2]) { 
				r2 = RandomUtil.randomInt(0, atomCount - 1);
				atom2 = atoms.get(r2);
				if (molecule.getBond(atom1, atom2) != null) {
					r2 = -1; 
					continue;
				}
				
				String symbol = atom2.getSymbol();
				if (symbol.equals("N")) {
					if (molecule.getConnectedBondsCount(atom2) >= 3
							&& molecule.getBondOrderSum(atom2) >= 3) {
						r2 = -1;
						continue;
					}
				} else if (symbol.equals("C")) {
					if (molecule.getConnectedBondsCount(atom2) >= 4
							&& molecule.getBondOrderSum(atom2) >= 4) {
						r2 = -1;
						continue;
					}
				} else if (symbol.equals("O") || symbol.equals("S")) {
					if (molecule.getConnectedBondsCount(atom2) >= 2
							&& molecule.getBondOrderSum(atom2) >= 2) {
						r2 = -1;
						continue;
					}
				} else { 
					r2 = -1;
					continue;
				}
			}
			
			ITag tag1 = new Tag(ReactionTags.RANDOM_BOND_ATOM, atom1);
			IMonomer monomer1 = getParentMonomer(atom1, scaffold);
			if (!monomer1.containsTag(ReactionTags.RANDOM_BOND_ATOM, atom1))
				monomer1.addTag(tag1);
			ITag tag2 = new Tag(ReactionTags.RANDOM_BOND_ATOM, atom2);
			IMonomer monomer2 = getParentMonomer(atom2, scaffold);
			if (!monomer2.containsTag(ReactionTags.RANDOM_BOND_ATOM, atom2))
				monomer2.addTag(tag2);
			
			IReaction reaction = new Reaction(Reactions.RANDOM);
			reaction.addTag(tag1);
			reaction.addTag(tag2);
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
		ITag tag1 = tags.get(0);
		ITag tag2 = tags.get(1);
		IAtom atom1 = tag1.atom();
		IAtom atom2 = tag2.atom();
		ReactionsUtil.addBond(atom1, atom2, molecule);
		ReactionsUtil.decrementHydrogenCount(atom1);
		ReactionsUtil.decrementHydrogenCount(atom2);
	}

	private IMonomer getParentMonomer(IAtom atom, IScaffold scaffold) {
		IMonomer monomer = null;
		for (IMonomer m : scaffold.monomers()) {
			IAtomContainer structure = m.structure();
			for (IAtom a : structure.atoms())
				if (atom.equals(a))
					monomer = m;
		}
		return monomer;
	}

}
