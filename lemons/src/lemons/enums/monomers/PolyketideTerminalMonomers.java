package lemons.enums.monomers;

import lemons.interfaces.IMonomerType;

public enum PolyketideTerminalMonomers implements IMonomerType {

	_2_METHYLBUTYRATE("IC(C(CC)(C)F)=O"),
	ETHYLMALONATE("IC(C(CC)F)=O"),
	ISOBUTYRATE("IC(C(C)(C)F)=O"),
	MALONATE("IC(CF)=O"),
	METHYLMALONATE("IC(C(C)F)=O"),
	METHOXYMALONATE("IC(C(OC)F)=O"),
	PROPIONATE("IC(C(CC)F)=O"),
	;
	
	private String smiles;
	
	private PolyketideTerminalMonomers(String smiles) {
		this.smiles = smiles;
	}
	
	public String smiles() {
		return smiles;
	}

}
