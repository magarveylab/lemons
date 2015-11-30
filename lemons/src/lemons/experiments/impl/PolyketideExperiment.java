package lemons.experiments.impl;

import java.io.IOException;
import java.util.List;

import lemons.Config;
import lemons.data.TanimotoCoefficientList;
import lemons.enums.monomers.PolyketideMonomers;
import lemons.fingerprint.Fingerprinters;
import lemons.interfaces.IExperiment;
import lemons.interfaces.IFingerprint;
import lemons.interfaces.IFingerprintList;
import lemons.interfaces.IMonomerType;
import lemons.interfaces.IScaffold;
import lemons.interfaces.ITanimotoCoefficient;
import lemons.interfaces.ITanimotoCoefficientList;
import lemons.io.ExperimentWriter;
import lemons.scaffold.ReactionPerceiver;
import lemons.scaffold.ReactionExecutor;
import lemons.scaffold.ScaffoldBuilder;
import lemons.util.FingerprintUtil;
import lemons.util.RandomUtil;
import lemons.util.Sorter;
import lemons.util.TanimotoUtil;
import lemons.util.exception.FingerprintGenerationException;
import lemons.util.exception.PolymerGenerationException;

import org.openscience.cdk.exception.CDKException;

public class PolyketideExperiment implements IExperiment {
	
	private int numSwaps = 0;
	
	public PolyketideExperiment(int numSwaps) {
		this.numSwaps = numSwaps;
	}
	
	public void run() throws CDKException,
			PolymerGenerationException, IOException,
			FingerprintGenerationException {
		// generate a set of linear peptides 
		List<IScaffold> polyketides = ScaffoldBuilder.buildPolyketides(Config.LIBRARY_SIZE);
		
		// execute reactions if needed
		for (IScaffold polyketide : polyketides) {
			ReactionPerceiver.detectReactions(polyketide);
			ReactionExecutor.executeReactions(polyketide);
		}

		// generate fingerprints
		FingerprintUtil.setFingerprints(polyketides);
		
		for (int i = 0; i < polyketides.size(); i++) {
			IScaffold polyketide = polyketides.get(i);
			
			// swap amino acids
			PolyketideMonomers[] pks = PolyketideMonomers.values();
			boolean[] used = new boolean[pks.length];
			IScaffold newPolyketide = null;
			for (int j = 0; j < numSwaps; j++) {
				int r = -1;
				while (r == -1 || used[r] == true) 
					r = RandomUtil.randomInt(0, pks.length - 1);
				used[r] = true;
				IMonomerType swap = pks[r];

				int index = RandomUtil.randomInt(0, polyketide.size() - 1);
				if (newPolyketide == null) {
					newPolyketide = ScaffoldBuilder.swap(swap, index, polyketide.polymer());
				} else {
					newPolyketide = ScaffoldBuilder.swap(swap, index, newPolyketide.polymer());
				}
			}
						
			// generate fingerprints 
			String name = "Scaffold_" + (i+1);
			FingerprintUtil.setFingerprints(newPolyketide, name);
			
			// calculate all Tanimoto coefficients  
			IFingerprintList<IFingerprint> newFingerprints = newPolyketide.fingerprints();
			for (Fingerprinters fp : Fingerprinters.values()) {
				ITanimotoCoefficientList<ITanimotoCoefficient> tcList = new TanimotoCoefficientList();
				IFingerprint newFingerprint = newFingerprints.getFingerprint(fp);

				for (IScaffold oldPeptide : polyketides) {
					IFingerprintList<IFingerprint> oldFingerprints = oldPeptide.fingerprints();
					IFingerprint oldFingerprint = oldFingerprints.getFingerprint(fp);
					
					// calculate TC 
					ITanimotoCoefficient tc = TanimotoUtil.get(newFingerprint, oldFingerprint);
					tcList.add(tc);
				}

				// sort Tanimoto coefficients 
				Sorter.sort(tcList);

				// write 
				ITanimotoCoefficient match = tcList.getMatch(name);
				ExperimentWriter.writeRow(match, tcList, fp);
			}
		}
	}
	
}
