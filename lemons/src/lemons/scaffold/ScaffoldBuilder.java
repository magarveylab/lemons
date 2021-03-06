package lemons.scaffold;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.openscience.cdk.exception.CDKException;

import lemons.Config;
import lemons.data.Scaffold;
import lemons.enums.monomers.Starters;
import lemons.interfaces.IMonomer;
import lemons.interfaces.IMonomerType;
import lemons.interfaces.IPolymer;
import lemons.interfaces.IScaffold;
import lemons.util.MonomerGenerator;
import lemons.util.MonomerUtil;
import lemons.util.PolymerGenerator;
import lemons.util.RandomUtil;
import lemons.util.exception.PolymerGenerationException;

public class ScaffoldBuilder {

	private static final Logger logger = Logger
			.getLogger(ScaffoldBuilder.class.getName());

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
		List<IMonomerType> types = Config.INITIAL_MONOMERS;
		List<IMonomerType> extenderTypes = new ArrayList<IMonomerType>(types);
		extenderTypes.removeAll(Arrays.asList(Starters.values()));

		// get the list of monomers that can extend amino acids at the N
		List<IMonomerType> aaExtenderTypes = MonomerUtil.getAminoAcidExtenderTypes(
				extenderTypes);
		// get amino acid monomers
		List<IMonomerType> aa = MonomerUtil.getAminoAcidTypes();

		int size = RandomUtil.randomInt(Config.MIN_SCAFFOLD_SIZE,
				Config.MAX_SCAFFOLD_SIZE);
		List<IMonomer> monomers = new ArrayList<IMonomer>();
		for (int i = 0; i < size; i++) {
			IMonomerType type = null;
			if (i == 0) {
				// terminal residue must have ketone
				boolean hasKetone = false;
				while (!hasKetone) {
					type = RandomUtil.getRandomMonomer(extenderTypes);
					String smiles = type.smiles();
					if (smiles.contains("=O"))
						hasKetone = true;
				}
			} else if (i == (size - 1)
					&& types.contains(Starters.values()[0])) {
				// if starter units, last unit must be starter
				type = RandomUtil.getRandomMonomer(Starters.values());
			} else {
				// if the previous residue was a NRPS, next residue cannot be a
				// non-ketone PK
				int lastIdx = i - 1;
				IMonomer lastMonomer = monomers.get(lastIdx);
				IMonomerType lastType = lastMonomer.type();
				if (aa.contains(lastType)) {
					type = RandomUtil.getRandomMonomer(aaExtenderTypes);
				} else {
					type = RandomUtil.getRandomMonomer(extenderTypes);
				}
			}
			IMonomer monomer = MonomerGenerator.buildMonomer(type);
			monomers.add(monomer);
		}

		IPolymer polymer = PolymerGenerator.buildPolymer(monomers);

		scaffold.setPolymer(polymer);
		return scaffold;
	}
	
}
