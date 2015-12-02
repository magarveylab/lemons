package lemons.scaffold;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.openscience.cdk.exception.CDKException;
import org.openscience.cdk.interfaces.IAtom;
import org.openscience.cdk.interfaces.IAtomContainer;

import lemons.Config;
import lemons.data.Scaffold;
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
		IMonomerType[] swapTypes = Config.SWAP_MONOMERS;
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
			int r = RandomUtil.randomInt(0, swapTypes.length - 1);
			IMonomerType swapType = swapTypes[r];

			int s = -1;
			while (s == -1 || usedSwaps[s] == true
					|| newMonomers[s] != null) //XXX
				s = RandomUtil.randomInt(0, original.size() - 1);
			usedSwaps[s] = true;
			
			newMonomers[s] = MonomerGenerator.buildMonomer(swapType);
		}
		
		// copy unset monomers 
		for (int k = 0; k < original.size(); k++)
			if (newMonomers[k] == null)
				newMonomers[k] = Copier.deepCopy(original.getMonomer(k));
		
		// construct polymer 
		IPolymer polymer = PolymerGenerator.buildPolymer(Arrays.asList(newMonomers));
		
		IScaffold newScaffold = new Scaffold();
		newScaffold.setPolymer(polymer);
		return newScaffold;
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
