package lemons.experiments.impl;

import java.io.IOException;
import java.util.List;

import lemons.Config;
import lemons.data.TanimotoCoefficientList;
import lemons.enums.monomers.ProteinogenicAminoAcids;
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

public class PeptideExperiment implements IExperiment {
	
	private int numSwaps = 0;
	
	public PeptideExperiment(int numSwaps) {
		this.numSwaps = numSwaps;
	}
	
	public void run() throws CDKException,
			PolymerGenerationException, IOException,
			FingerprintGenerationException {
		// generate a set of linear peptides 
		List<IScaffold> peptides = ScaffoldBuilder.buildScaffolds(Config.LIBRARY_SIZE);
		
		// execute reactions if needed
		for (IScaffold peptide : peptides) {
			ReactionPerceiver.detectReactions(peptide);
			ReactionExecutor.executeReactions(peptide);
		}

		// generate fingerprints
		FingerprintUtil.setFingerprints(peptides);
		
		for (int i = 0; i < peptides.size(); i++) {
			IScaffold peptide = peptides.get(i);
			
			IMonomerType[] types = Config.SWAP_MONOMERS;
			boolean[] used = new boolean[types.length];
			
			// swap amino acids
			IScaffold newPeptide = null;
			for (int j = 0; j < numSwaps; j++) {
				int r = -1;
				while (r == -1 || used[r] == true) 
					r = RandomUtil.randomInt(0, aas.length - 1);
				used[r] = true;
				IMonomerType swap = aas[r];

				int index = RandomUtil.randomInt(0, peptide.size() - 1);
				if (newPeptide == null) {
					newPeptide = ScaffoldBuilder.swap(swap, index, peptide.polymer());
				} else {
					newPeptide = ScaffoldBuilder.swap(swap, index, newPeptide.polymer());
				}
			}
			
			// execute reactions if needed
			//XXX How does this work? How do you compare two molecules when the reactions have already been executed?? 
			//XXX Create a new function to copy the reactions, but also to detect what subset of reactions are possible...
			//XXX This is going to be more complicated than I thought 
			ReactionPerceiver.detectReactions(newPeptide);
			ReactionExecutor.executeReactions(newPeptide);
						
			// generate fingerprints 
			String name = "Scaffold_" + (i+1);
			FingerprintUtil.setFingerprints(newPeptide, name);
			
			// calculate all Tanimoto coefficients  
			IFingerprintList<IFingerprint> newFingerprints = newPeptide.fingerprints();
			for (Fingerprinters fp : Fingerprinters.values()) {
				ITanimotoCoefficientList<ITanimotoCoefficient> tcList = new TanimotoCoefficientList();
				IFingerprint newFingerprint = newFingerprints.getFingerprint(fp);

				for (IScaffold oldPeptide : peptides) {
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
