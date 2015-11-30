package lemons.scaffold;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.openscience.cdk.exception.CDKException;

import lemons.Config;
import lemons.data.Scaffold;
import lemons.enums.ReactionTypes;
import lemons.interfaces.IMonomer;
import lemons.interfaces.IMonomerType;
import lemons.interfaces.IPolymer;
import lemons.interfaces.IScaffold;
import lemons.util.MonomerGenerator;
import lemons.util.PolymerGenerator;
import lemons.util.RandomUtil;
import lemons.util.exception.PolymerGenerationException;

public class ScaffoldBuilder {

	public static List<IScaffold> buildScaffolds(int number)
			throws IOException, CDKException, PolymerGenerationException {
		List<IScaffold> scaffolds = new ArrayList<IScaffold>();
		for (int i = 0; i < number; i++) {
			IScaffold scaffold = buildLinearPolymer();
			scaffolds.add(scaffold);
		}
		return scaffolds;
	}
	
	public static IScaffold buildLinearPolymer()
			throws IOException, CDKException, PolymerGenerationException {
		IScaffold scaffold = new Scaffold();
		IMonomerType[] types = Config.INITIAL_MONOMERS;
		int size = RandomUtil.randomInt(Config.MIN_SCAFFOLD_SIZE, Config.MAX_SCAFFOLD_SIZE);
		List<IMonomer> monomers = new ArrayList<IMonomer>();
		for (int i = 0; i < size; i++) {
			IMonomerType type = null;
			if (Config.INITIAL_REACTIONS.get(ReactionTypes.CYCLIZATION) > 0 || 
					Config.ADD_REACTIONS.get(ReactionTypes.CYCLIZATION) > 0 || 
					Config.SWAP_REACTIONS.get(ReactionTypes.CYCLIZATION) > 0 ||
					Config.REMOVE_REACTIONS.get(ReactionTypes.CYCLIZATION) > 0) { 
				// terminal residue must have ketone if cyclizing 
				String smiles = null;
				while (!smiles.contains("=0") || smiles == null) {
					type = RandomUtil.getRandomMonomer(types);
					smiles = type.smiles();
				}
			} else {
				type = RandomUtil.getRandomMonomer(types);
			}
			IMonomer monomer = MonomerGenerator.buildMonomer(type); 
			monomers.add(monomer);
		}
		
		IPolymer polymer = PolymerGenerator.buildPolymer(monomers);
		scaffold.setPolymer(polymer);
		return scaffold;
	}

	public static IScaffold swap(IMonomerType monomer, int index,
			IPolymer polymer) throws IOException, CDKException, PolymerGenerationException {
		// swap a single monomer 
		List<IMonomer> newMonomers = new ArrayList<IMonomer>();
		for (int j = 0; j < polymer.size(); j++) {
			if (j == index) {
				IMonomer newMonomer = MonomerGenerator.buildMonomer(monomer);
				newMonomers.add(newMonomer);
			} else {
				IMonomer oldMonomer = polymer.getMonomer(j);
				IMonomer newMonomer = MonomerGenerator.buildMonomer(
						oldMonomer.type());
				newMonomers.add(newMonomer);
			}
		}
		IScaffold newScaffold = new Scaffold();
		IPolymer newPolymer = PolymerGenerator.buildPolymer(newMonomers);
		newScaffold.setPolymer(newPolymer);
		return newScaffold;
	}
	
}
