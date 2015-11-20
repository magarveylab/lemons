package lemons.scaffold;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.openscience.cdk.exception.CDKException;

import lemons.Config;
import lemons.data.Scaffold;
import lemons.interfaces.IMonomer;
import lemons.interfaces.IMonomerType;
import lemons.interfaces.IPolymer;
import lemons.interfaces.IScaffold;
import lemons.util.MonomerGenerator;
import lemons.util.PolymerGenerator;
import lemons.util.RandomUtil;
import lemons.util.exception.PolymerGenerationException;

public class ScaffoldBuilder {

	public static List<IScaffold> buildProteinogenicPeptides(int number)
			throws CDKException, PolymerGenerationException, IOException {
		List<IScaffold> peptides = new ArrayList<IScaffold>();
		for (int i = 0; i < number; i++) {
			IScaffold peptide = ScaffoldBuilder.buildLinearPeptide();
			peptides.add(peptide);
		}
		return peptides;
	}

	public static IScaffold buildLinearPeptide() throws CDKException,
			PolymerGenerationException, IOException {
		IScaffold scaffold = new Scaffold();
		int size = RandomUtil.randomInt(Config.MIN_SIZE, Config.MAX_SIZE);
		List<IMonomer> monomers = new ArrayList<IMonomer>();
		for (int i = 0; i < size; i++) {
			IMonomerType type = RandomUtil.getRandomProteinogenicAminoAcid();
			IMonomer monomer = MonomerGenerator.buildMonomer(type); 
			monomers.add(monomer);
		}
		IPolymer polymer = PolymerGenerator.buildPolymer(monomers);
		scaffold.setPolymer(polymer);
		return scaffold;
	}

	public static IScaffold swap(IMonomerType monomer, int index,
			IPolymer polymer) throws IOException, CDKException, PolymerGenerationException {
		// swap a single amino acid
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
