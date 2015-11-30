package lemons.enums.monomers;

import lemons.interfaces.IMonomerType;

public enum PolyketideMonomers implements IMonomerType {

	_2_METHYLBUTYRATE("IC(C(CC)(C)F)=O"),
	_2_METHYLBUTYRATE_1("IC(O)C(C)(F)CC"),
	_2_METHYLBUTYRATE_3("ICC(C)(F)CC"),
	ETHYLMALONATE("IC(C(CC)F)=O"),
	ETHYLMALONATE_1("IC(O)C(F)CC"),
	ETHYLMALONATE_2("I/C=C(F)/CC"),
	ETHYLMALONATE_3("ICC(F)CC"),
	ISOBUTYRATE("IC(C(C)(C)F)=O"),
	ISOBUTYRATE_1("IC(O)C(C)(F)C"),
	ISOBUTYRATE_3("ICC(C)(F)C"),
	MALONATE("IC(CF)=O"),
	MALONATE_1("IC(O)CF"), 
	MALONATE_2("I/C=C/F"),
	MALONATE_3("ICCF"),
	METHYLMALONATE("IC(C(C)F)=O"),
	METHYLMALONATE_1("IC(O)C(F)C"),
	METHYLMALONATE_2("I/C=C(F)/C"),
	METHYLMALONATE_3("ICC(F)C"),
	METHOXYMALONATE("IC(C(OC)F)=O"),
	METHOXYMALONATE_1("IC(O)C(F)OC"),
	METHOXYMALONATE_2("I/C=C(F)/OC"),
	METHOXYMALONATE_3("ICC(F)OC"),
	PROPIONATE("IC(C(CC)F)=O"),
	PROPIONATE_1("IC(O)C(F)CC"),
	PROPIONATE_2("I/C=C(F)/CC"),
	PROPIONATE_3("ICC(F)CC"),
	;
	
	private String smiles;
	
	private PolyketideMonomers(String smiles) {
		this.smiles = smiles;
	}
	
	public String smiles() {
		return smiles;
	}

}
