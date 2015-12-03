package lemons.scaffold;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.openscience.cdk.exception.CDKException;

import lemons.Config;
import lemons.data.Scaffold;
import lemons.enums.Reactions;
import lemons.interfaces.IMonomer;
import lemons.interfaces.IMonomerType;
import lemons.interfaces.IPolymer;
import lemons.interfaces.IScaffold;
import lemons.util.MonomerGenerator;
import lemons.util.PolymerGenerator;
import lemons.util.RandomUtil;
import lemons.util.exception.PolymerGenerationException;

public class ScaffoldBuilder {
	
	private static final Logger logger = Logger.getLogger(ScaffoldBuilder.class.getName());

	public static List<IScaffold> buildScaffolds(int number)
			throws IOException, CDKException, PolymerGenerationException {
		logger.log(Level.INFO, "Building " + number + " scaffolds");
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
			if (i == 0
					&& (Config.INITIAL_REACTIONS.get(Reactions.CYCLIZATION) > 0
							|| Config.ADD_REACTIONS.get(Reactions.CYCLIZATION) > 0
							|| Config.SWAP_REACTIONS.get(Reactions.CYCLIZATION) > 0
							|| Config.REMOVE_REACTIONS.get(Reactions.CYCLIZATION) > 0)) {
				// terminal residue must have ketone if cyclizing 
				boolean hasKetone = false;
				while (!hasKetone) {
					type = RandomUtil.getRandomMonomer(types);
					String smiles = type.smiles();
					if (smiles.contains("=O"))
						hasKetone = true;
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
