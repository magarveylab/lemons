package lemons.scaffold;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.openscience.cdk.exception.CDKException;
import org.openscience.cdk.interfaces.IAtom;
import org.openscience.cdk.interfaces.IAtomContainer;

import lemons.Config;
import lemons.data.Reaction;
import lemons.data.Scaffold;
import lemons.enums.monomers.Starters;
import lemons.interfaces.IMonomer;
import lemons.interfaces.IMonomerType;
import lemons.interfaces.IPolymer;
import lemons.interfaces.IReaction;
import lemons.interfaces.IReactionList;
import lemons.interfaces.IScaffold;
import lemons.interfaces.ITag;
import lemons.interfaces.ITagList;
import lemons.util.Copier;
import lemons.util.MonomerGenerator;
import lemons.util.PolymerGenerator;
import lemons.util.RandomUtil;
import lemons.util.exception.PolymerGenerationException;

public class MonomerManipulator {
	
	public static IScaffold swapMonomers(IScaffold original)
			throws CDKException, PolymerGenerationException, IOException {
		// create empty lists
		IMonomer[] newMonomers = new IMonomer[original.size()];
		boolean[] usedSwaps = new boolean[original.size()];

		// get monomers which contain a reaction
		boolean[] reactions = getMonomersWithReactions(original);
		
		// copy in old monomers which have a reaction tag 
		for (int i = 0; i < original.size(); i++)
			if (reactions[i] == true) 
				newMonomers[i] = Copier.deepCopy(original.getMonomer(i));
		
		// set new monomers 
		for (int j = 0; j < Config.NUM_MONOMER_SWAPS; j++) {
			int s = -1;
			while (s == -1 || usedSwaps[s] == true
					|| newMonomers[s] != null)
				s = RandomUtil.randomInt(0, original.size() - 1);
			usedSwaps[s] = true;
			
			IMonomerType swapType = null;
			if (s == (original.size() - 1)) {
				int r = RandomUtil.randomInt(0, Starters.values().length - 1);
				swapType = Starters.values()[r];
			} else {
				List<IMonomerType> extenderSwapTypes = new ArrayList<IMonomerType>(Config.SWAP_MONOMERS);
				extenderSwapTypes.removeAll(Arrays.asList(Starters.values()));
				int r = RandomUtil.randomInt(0, extenderSwapTypes.size() - 1);
				swapType = extenderSwapTypes.get(r);
			}

			newMonomers[s] = MonomerGenerator.buildMonomer(swapType);
		}
		
		// copy unset monomers 
		for (int k = 0; k < original.size(); k++)
			if (newMonomers[k] == null)
				newMonomers[k] = Copier.deepCopy(original.getMonomer(k));
		
		// create new scaffold
		IScaffold newScaffold = new Scaffold();
		
		// construct polymer 
		IPolymer polymer = PolymerGenerator.buildPolymer(Arrays.asList(newMonomers));
		newScaffold.setPolymer(polymer);
		
		// copy old reactions 
		copyReactions(original, newScaffold);
		
		return newScaffold;
	}
	
	public static void copyReactions(IScaffold original, IScaffold newScaffold) {
		IReactionList<IReaction> reactions = original.reactions();
		List<IMonomer> monomers = original.monomers();
		for (IReaction reaction : reactions) {
			IReaction copy = new Reaction(reaction.type());

			ITagList<ITag> tags = reaction.getTags();
			for (ITag tag : tags) {
				// get the indices of the parent monomer & tag index in monomer 
				int parentIdx = -1;
				int tagIdx = -1;
				for (int i = 0; i < monomers.size(); i++) {
					IMonomer monomer = monomers.get(i);
					ITagList<ITag> monomerTags = monomer.getTags();
					if (monomerTags.contains(tag)) {
						parentIdx = i;
						tagIdx = monomerTags.indexOf(tag);
					}
				}
				
				// copy tag-reaction association 
				IMonomer monomerCopy = newScaffold.getMonomer(parentIdx);
				ITag tagCopy = monomerCopy.getTags().get(tagIdx);
				copy.addTag(tagCopy);
			}
			
			newScaffold.addReaction(copy);
		}
	}

	public static boolean[] getMonomersWithReactions(IScaffold scaffold) {
		boolean[] monomerHasReactions = new boolean[scaffold.size()];
		IReactionList<IReaction> reactions = scaffold.reactions();
		List<IMonomer> monomers = scaffold.monomers();
		for (IReaction r : reactions) {
			ITagList<ITag> tags = r.getTags();
			for (ITag t : tags) {
				for (int i = 0; i < monomers.size(); i++) {
					IMonomer m = monomers.get(i);
					IAtomContainer structure = m.structure();
					for (IAtom atom : structure.atoms())
						if (t.atom().equals(atom))
							monomerHasReactions[i] = true;
				}
			}
		}
		return monomerHasReactions;
	}
	
}
