package lemons.io;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import org.openscience.cdk.exception.CDKException;
import org.openscience.cdk.interfaces.IAtomContainer;

import lemons.Config;
import lemons.interfaces.IScaffold;

public class SmilesWriter {
	
	public static void write(String filename, List<IScaffold> scaffolds)
			throws CDKException, IOException {
		String filepath = Config.WORKING_DIRECTORY + File.separator + filename; 
		BufferedWriter bw = new BufferedWriter(new FileWriter(filepath));
		for (int i = 0; i < scaffolds.size(); i++) {
			IScaffold scaffold = scaffolds.get(i);
			IAtomContainer molecule = scaffold.molecule();
			String smiles = SmilesIO.smiles(molecule);
			int idx = i+1;
			bw.append("Scaffold_" + idx + "\t" + smiles + "\n");
		}
		bw.close();
	}

}
