package lemons.experiments;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.Date;
import java.util.List;

import lemons.Config;
import lemons.data.Fingerprint;
import lemons.data.Scaffold;
import lemons.data.TanimotoCoefficientList;
import lemons.fingerprint.Fingerprinters;
import lemons.interfaces.IFingerprint;
import lemons.interfaces.IFingerprintList;
import lemons.interfaces.IMonomerType;
import lemons.interfaces.IScaffold;
import lemons.interfaces.ITanimotoCoefficient;
import lemons.interfaces.ITanimotoCoefficientList;
import lemons.util.IOUtil;
import lemons.util.ScaffoldGenerator;
import lemons.util.SmilesUtil;
import lemons.util.Sorter;
import lemons.util.TanimotoUtil;
import lemons.util.exception.FingerprintGenerationException;
import lemons.util.exception.PolymerGenerationException;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.openscience.cdk.exception.CDKException;
import org.openscience.cdk.fingerprint.IFingerprinter;
import org.openscience.cdk.interfaces.IAtomContainer;

public class LinearPeptideTest {
	
	private Date initDate;
	
	public LinearPeptideTest() {
		initDate = new Date();
	}
	
	public void testProteinogenicPeptides(int swaps, int samples) throws CDKException,
			PolymerGenerationException, IOException,
			FingerprintGenerationException {
		// generate a set of linear peptides 
		List<IScaffold> peptides = generateProteinogenicPeptides(samples);
		
		// generate fingerprints
		for (int i = 0; i < peptides.size(); i++) {
			IScaffold peptide = peptides.get(i);
			String name = "Scaffold_" + (i+1);
			setFingerprints(peptide, name);
		}

		for (int i = 0; i < peptides.size(); i++) {
			IScaffold peptide = peptides.get(i);
			
			// swap a single amino acid 
			IScaffold newPeptide = new Scaffold();
			newPeptide.add(peptide.monomers());
			IMonomerType monomer = ScaffoldGenerator.randomProteinogenicAminoAcid();
			int index = ScaffoldGenerator.randomInt(0, newPeptide.size() - 1);
			newPeptide.set(index, monomer);
			
			// generate fingerprints 
			String name = "Scaffold_" + (i+1);
			setFingerprints(newPeptide, name);
			
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
				write(match, tcList, fp);
			}
		}
	}
	
	public void write(ITanimotoCoefficient tc,
			ITanimotoCoefficientList<ITanimotoCoefficient> tcList,
			Fingerprinters fp) throws IOException {
		// get file 
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-HHmm");
		String date = sdf.format(initDate);
		String directory = Config.WORKING_DIRECTORY;
		IOUtil.checkDir(directory);
		String subdir = directory + File.separator + date;
		IOUtil.checkDir(subdir);
		File file = new File(subdir + File.separator + fp.toString() + ".csv");

		// check file exists
		boolean exists = file.exists();
		
		// create file writer
		CSVFormat csvFileFormat = CSVFormat.EXCEL;
		CSVPrinter cw = new CSVPrinter(new FileWriter(file, true), csvFileFormat);
		
		// write header, if not already written 
		if (!exists) {
			List<String> header = new ArrayList<String>();
			header.add("Name");
			header.add("Value");
			header.add("Rank");
			cw.printRecord(header);
		}
		
		// append row
		List<String> row = new ArrayList<String>();
		row.add(tc.name1());
		row.add(tc.value() + "");
		row.add((tcList.indexOf(tc) + 1) + "");
		cw.printRecord(row);
		
		cw.close();
	}
		
	public void setFingerprints(IScaffold scaffold, String name)
			throws CDKException, PolymerGenerationException, IOException {
		// construct molecule
		IAtomContainer molecule = SmilesUtil.toMolecule(scaffold);

		// calculate fingerprints
		for (Fingerprinters fingerprinter : Fingerprinters.values()) {
			IFingerprinter fp = fingerprinter.getFingerprinter();
			BitSet bitset = fp.getBitFingerprint(molecule).asBitSet();
			IFingerprint fingerprint = new Fingerprint(name, bitset, fingerprinter);
			scaffold.add(fingerprint);
		}
	}
	
	public List<IScaffold> generateProteinogenicPeptides(int number) {
		List<IScaffold> peptides = new ArrayList<IScaffold>();
		for (int i = 0; i < number; i++) {
			IScaffold peptide = ScaffoldGenerator.generateLinearPeptide();
			peptides.add(peptide);
		}
		return peptides;
	}
	
}
