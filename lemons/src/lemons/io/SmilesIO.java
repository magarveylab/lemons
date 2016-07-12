package lemons.io;

import java.io.IOException;

import org.openscience.cdk.DefaultChemObjectBuilder;
import org.openscience.cdk.exception.CDKException;
import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.interfaces.IChemObjectBuilder;
import org.openscience.cdk.smiles.SmilesGenerator;
import org.openscience.cdk.smiles.SmilesParser;

public class SmilesIO {

	/**
	 * Generate a CDK molecule from a SMILES string.
	 * 
	 * @param smiles
	 *            The structure to generate an IAtomContainer instance from, as
	 *            a SMILES string
	 * @return An IAtomContainer object corresponding to the input SMILES
	 * @throws IOException
	 * @throws CDKException
	 */
	@SuppressWarnings("deprecation")
	public static IAtomContainer molecule(String smiles) throws IOException,
			CDKException {
		IAtomContainer mol = null;
		synchronized (SmilesIO.class) {
			IChemObjectBuilder builder = DefaultChemObjectBuilder.getInstance();
			SmilesParser parser = new SmilesParser(builder);
			parser.setPreservingAromaticity(true);
			mol = parser.parseSmiles(smiles);
		}
		return mol;
	}

	/**
	 * Generate a SMILES string from an IAtomContainer
	 * 
	 * @param mol
	 *            The molecule to be parsed
	 * @return The molecule's structures as a SMILES string
	 * @throws CDKException
	 */
	@SuppressWarnings("deprecation")
	public static String smiles(IAtomContainer mol) throws CDKException {
		SmilesGenerator sg = new SmilesGenerator();
		sg.setUseAromaticityFlag(true);
		String smiles = sg.createSMILES(mol);
		return smiles;
	}

}
