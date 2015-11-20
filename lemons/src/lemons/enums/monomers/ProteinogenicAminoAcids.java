package lemons.enums.monomers;

import lemons.interfaces.IAminoAcidType;

public enum ProteinogenicAminoAcids implements IAminoAcidType {

	ALANINE("IC(C(C)NF)=O", "Alanine", "A"),
	ASPARAGINE("FNC(C(I)=O)CC(N)=O", "Asparagine", "N"),
	ASPARTATE("FNC(C(I)=O)CC(O)=O", "Aspartate", "D"),
	ARGININE("FNC(C(I)=O)CCCNC(N)=N", "Arginine", "R"),
	CYSTEINE("IC(C(CS)NF)=O", "Cysteine", "C"),
	GLYCINE("IC(CNF)=O", "Glycine", "G"),
	GLUTAMINE("FNC(C(I)=O)CCC(N)=O", "Glutamine", "Q"),
	GLUTAMATE("FNC(C(I)=O)CCC(O)=O", "Glutamate", "E"),
	HISTIDINE("FNC(CC1=CN=CN1)C(I)=O", "Histidine", "H"),
	ISOLEUCINE("IC(C(C(C)CC)NF)=O", "Isoleucine", "I"),
	LEUCINE("IC(C(CC(C)C)NF)=O", "Leucine", "L"),
	LYSINE("FNC(C(I)=O)CCCCN", "Lysine", "K"),
	METHIONINE("FNC(CCSC)C(I)=O", "Methionine", "M"),
	PHENYLALANINE("FNC(CC1=CC=CC=C1)C(I)=O", "Phenylalanine", "F"),
	PROLINE("FN1CCCC1C(I)=O", "Proline", "P"),
	SERINE("IC(C(CO)NF)=O", "Serine", "S"),
	THREONINE("IC(C(C(C)O)NF)=O", "Threonine", "T"),
	TRYPTOPHAN("FNC(CC1=CNC2=C1C=CC=C2)C(I)=O", "Tryptophan", "W"),
	TYROSINE("FNC(CC1=CC=C(O)C=C1)C(I)=O", "Tyrosine", "Y"),
	VALINE("IC(C(C(C)C)NF)=O", "Valine", "V"),
	;
	
	private String smiles;
	private String fullName;
	private String abbreviation;
	
	private ProteinogenicAminoAcids(String smiles, String fullName, String abbreviation) {
		this.smiles = smiles;
		this.fullName = fullName;
		this.abbreviation = abbreviation;
	}
	
	public String smiles() {
		return smiles;
	}
	
	public String fullName() {
		return fullName;
	}
	
	public String abbreviation() {
		return abbreviation;
	}
	
}
