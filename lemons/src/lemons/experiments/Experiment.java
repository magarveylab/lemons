package lemons.experiments;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.openscience.cdk.exception.CDKException;

import lemons.Config;
import lemons.data.TanimotoCoefficientList;
import lemons.fingerprint.Fingerprinters;
import lemons.interfaces.IExperiment;
import lemons.interfaces.IFingerprint;
import lemons.interfaces.IFingerprintList;
import lemons.interfaces.IScaffold;
import lemons.interfaces.ITanimotoCoefficient;
import lemons.interfaces.ITanimotoCoefficientList;
import lemons.io.ExperimentWriter;
import lemons.io.SmilesWriter;
import lemons.scaffold.MonomerManipulator;
import lemons.scaffold.ReactionExecutor;
import lemons.scaffold.ReactionManipulator;
import lemons.scaffold.ScaffoldBuilder;
import lemons.util.FingerprintUtil;
import lemons.util.Sorter;
import lemons.util.TanimotoUtil;
import lemons.util.exception.FingerprintGenerationException;
import lemons.util.exception.PolymerGenerationException;

public class Experiment implements IExperiment {

	private static final Logger logger = Logger
			.getLogger(Experiment.class.getName());

	public void run() throws CDKException, PolymerGenerationException,
			IOException, FingerprintGenerationException {
		// generate a set of linear scaffolds
		List<IScaffold> scaffolds = ScaffoldBuilder
				.buildScaffolds(Config.LIBRARY_SIZE);

		// detect reactions
		for (IScaffold scaffold : scaffolds)
			ReactionManipulator.detectReactions(scaffold);

		// swap monomers and add, remove, or swap reactions
		List<IScaffold> swapScaffolds = swapScaffolds(scaffolds);

		// execute reactions
		ReactionExecutor.executeReactions(scaffolds);
		ReactionExecutor.executeReactions(swapScaffolds);

		// write structures
		if (Config.WRITE_STRUCTURES) {
			SmilesWriter.write("scaffolds.tsv", scaffolds);
			SmilesWriter.write("swap_scaffolds.tsv", swapScaffolds);
		}

		// generate fingerprints
		if (Config.GET_FINGERPRINTS) {
			FingerprintUtil.setFingerprints(scaffolds);
			FingerprintUtil.setFingerprints(swapScaffolds);

			// calculate all Tanimoto coefficients
			calculateRanks(scaffolds, swapScaffolds);
		}
	}

	private List<IScaffold> swapScaffolds(List<IScaffold> scaffolds)
			throws IOException, CDKException, PolymerGenerationException {
		List<IScaffold> newScaffolds = new ArrayList<IScaffold>();

		// create swapped scaffolds
		for (int i = 0; i < scaffolds.size(); i++) {
			logger.log(Level.INFO, "Swapping scaffold " + (i + 1));

			IScaffold scaffold = scaffolds.get(i);

			// swap monomers and copy reactions
			IScaffold newScaffold = MonomerManipulator.swapMonomers(scaffold);

			// remove reactions
			ReactionManipulator.removeReactions(newScaffold);

			// swap reactions
			ReactionManipulator.swapReactions(newScaffold);

			// add new reactions
			ReactionManipulator.addReactions(newScaffold);

			newScaffolds.add(newScaffold);
		}

		return newScaffolds;
	}

	public static void calculateRanks(List<IScaffold> scaffolds,
			List<IScaffold> swapScaffolds)
			throws FingerprintGenerationException, CDKException, IOException {
		for (int i = 0; i < scaffolds.size(); i++) {
			IScaffold newScaffold = swapScaffolds.get(i);
			IFingerprintList<IFingerprint> newFingerprints = newScaffold
					.fingerprints();
			for (Fingerprinters fp : Fingerprinters.values()) {
				ITanimotoCoefficientList<ITanimotoCoefficient> tcList = new TanimotoCoefficientList();
				IFingerprint newFingerprint = newFingerprints
						.getFingerprint(fp);

				for (IScaffold oldScaffold : scaffolds) {
					IFingerprintList<IFingerprint> oldFingerprints = oldScaffold
							.fingerprints();
					IFingerprint oldFingerprint = oldFingerprints
							.getFingerprint(fp);

					// calculate TC
					ITanimotoCoefficient tc = TanimotoUtil.get(newFingerprint,
							oldFingerprint);
					tcList.add(tc);
				}

				// sort Tanimoto coefficients
				Sorter.sort(tcList);

				// write
				String name = "Scaffold_" + (i + 1);
				ITanimotoCoefficient match = tcList.getMatch(name);
				ExperimentWriter.writeRow(match, tcList, fp);
			}
		}
	}

}
