package lemons.io;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.openscience.cdk.exception.CDKException;
import org.openscience.cdk.interfaces.IAtomContainer;

import lemons.data.Scaffold;
import lemons.interfaces.IScaffold;

public class SmilesReader {

	public static List<IScaffold> read(String filepath) throws IOException, CDKException {
		File file = new File(filepath);
		if (!file.exists())
			throw new FileNotFoundException("Could not find SMILES file " + filepath);
		
		BufferedReader br = new BufferedReader(new FileReader(file));
		String line;
		List<IScaffold> scaffolds = new ArrayList<IScaffold>();
		while ((line = br.readLine()) != null) {
			String[] split = line.split("\t");
			if (split.length < 2)
				continue;
			String smiles = split[1];
			
			IScaffold scaffold = new Scaffold();
			IAtomContainer molecule = SmilesIO.molecule(smiles);
			scaffold.setMolecule(molecule);
			scaffolds.add(scaffold);
		}
		
		br.close();
		return scaffolds;
	}
	
}
