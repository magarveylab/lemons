package lemons.util;

import java.util.Random;

import lemons.Config;
import lemons.data.Scaffold;
import lemons.enums.ProteinogenicAminoAcids;
import lemons.interfaces.IMonomerType;
import lemons.interfaces.IScaffold;

public class ScaffoldGenerator {

	public static IScaffold generateLinearPeptide() {
		IScaffold scaffold = new Scaffold();
		int size = randomInt(Config.MIN_SIZE, Config.MAX_SIZE);
		for (int i = 0; i < size; i++) {
			IMonomerType monomer = randomProteinogenicAminoAcid();
			scaffold.add(monomer);
		}
		return scaffold;
	}
	
	public static IMonomerType randomProteinogenicAminoAcid() {
		ProteinogenicAminoAcids[] aas = ProteinogenicAminoAcids.values();
		int index = randomInt(0, aas.length - 1);
		IMonomerType monomer = aas[index];
		return monomer;
	}
	
	public static int randomInt(int min, int max) {
		Random rand = new Random();
		int size = rand.nextInt((max - min) + 1) + min;
		return size;
	}
	
}
