package lemons.enums.monomers;

import lemons.interfaces.IMonomerType;

/**
 * A template for the addition of new monomer types to LEMONS. Simply modify
 * this file, replacing sample entries in the enum with desired monomers.
 * <p>
 * 
 * In LEMONS, the atom at which a monomer is extended (e.g. the carboxylic acid,
 * in amino acids) of a given monomer is marked with an iodine (I) symbol, while
 * the atom which extends the previous monomer (e.g. the nitrogen, in amino
 * acids) is marked with a fluorine (F) symbol.
 * 
 * @author michaelskinnider
 *
 */
public enum MonomerSetTemplate implements IMonomerType {

	SAMPLE_1_METHANE("FCI"),
	SAMPLE_2_ETHANE("FCCI"),
	SAMPLE_3_PROPANE("FCCCI"),
	; 
	
	private final String smiles;
	
	private MonomerSetTemplate(final String smiles) {
		this.smiles = smiles;
	}
	
	public String smiles() {
		return smiles;
	}

	
}
