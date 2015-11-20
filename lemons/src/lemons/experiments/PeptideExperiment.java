package lemons.experiments;

import java.io.IOException;
import java.util.List;

import lemons.data.TanimotoCoefficientList;
import lemons.fingerprint.Fingerprinters;
import lemons.interfaces.IFingerprint;
import lemons.interfaces.IFingerprintList;
import lemons.interfaces.IMonomerType;
import lemons.interfaces.IScaffold;
import lemons.interfaces.ITanimotoCoefficient;
import lemons.interfaces.ITanimotoCoefficientList;
import lemons.io.ExperimentWriter;
import lemons.scaffold.ReactionDetector;
import lemons.scaffold.ReactionExecutor;
import lemons.scaffold.ScaffoldBuilder;
import lemons.util.FingerprintUtil;
import lemons.util.RandomUtil;
import lemons.util.Sorter;
import lemons.util.TanimotoUtil;
import lemons.util.exception.FingerprintGenerationException;
import lemons.util.exception.PolymerGenerationException;

import org.openscience.cdk.exception.CDKException;

public class PeptideExperiment extends AbstractExperiment {
	
	public PeptideExperiment(int numSamples) {
		super(numSamples);
	}
	
	public void run() throws CDKException,
			PolymerGenerationException, IOException,
			FingerprintGenerationException {
		// generate a set of linear peptides 
		List<IScaffold> peptides = ScaffoldBuilder.buildProteinogenicPeptides(numSamples);
		
		// execute reactions if needed
		for (IScaffold peptide : peptides) {
			ReactionDetector.detectReactions(peptide);
			ReactionExecutor.executeReactions(peptide);
		}

		// generate fingerprints
		FingerprintUtil.setFingerprints(peptides);
		
		for (int i = 0; i < peptides.size(); i++) {
			IScaffold peptide = peptides.get(i);
			
			// swap a single amino acid 
			IMonomerType swap = RandomUtil.getRandomProteinogenicAminoAcid();
			int index = RandomUtil.randomInt(0, peptide.size() - 1);
			IScaffold newPeptide = ScaffoldBuilder.swap(swap, index, peptide.polymer());
						
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
