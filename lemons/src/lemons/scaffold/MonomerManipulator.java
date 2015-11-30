package lemons.scaffold;

import java.io.IOException;

import org.openscience.cdk.exception.CDKException;

import lemons.Config;
import lemons.interfaces.IMonomerType;
import lemons.interfaces.IScaffold;
import lemons.util.RandomUtil;
import lemons.util.exception.PolymerGenerationException;

public class MonomerManipulator {

	public static IScaffold swapMonomers(IScaffold original)
			throws IOException, CDKException, PolymerGenerationException {
		IScaffold newScaffold = null;
		IMonomerType[] types = Config.SWAP_MONOMERS;
		boolean[] usedTypes = new boolean[types.length];
		boolean[] usedSwaps = new boolean[original.size()];
		for (int j = 0; j < Config.NUM_MONOMER_SWAPS; j++) {
			int r = -1;
			while (r == -1 || usedTypes[r] == true)
				r = RandomUtil.randomInt(0, types.length - 1);
			usedTypes[r] = true;
			IMonomerType swap = types[r];

			int s = -1;
			while (s == -1 || usedSwaps[s] == true)
				s = RandomUtil.randomInt(0, original.size() - 1);
			if (newScaffold == null) {
				newScaffold = ScaffoldBuilder.swap(swap, s, original.polymer());
			} else {
				newScaffold = ScaffoldBuilder.swap(swap, s,
						newScaffold.polymer());
			}
		}
		return newScaffold;
	}

}
