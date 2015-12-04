package lemons.enums.monomers;

public enum Sugars {
	
	// hexose
	GLUCOSE("Glucose", "OCC1OC(I)C(O)C(O)C1O"),
	GLCNAC("N-acetylglucosamine", "O=C(NC1C(O)C(O)C(CO)OC1I)C"),
	MANNOSE("Mannose", "OCC1OC(I)C(O)C(O)C1O"),
	GULOSE("Gulose", "OCC1OC(I)C(O)C(O)C1O"),
	
	// deoxy
	L_ACULOSE("L-aculose", "CC1OC(I)C=CC1=O"),
	L_CINERULOSE_A("L-cinerulose A", "CC1OC(I)CCC1=O"),
	L_RHODINOSE("L-rhodinose", "OC1CCC(I)OC1C"),
	REDNOSE("Rednose", "CC1OC(C(N)=CC1=O)I"),
	L_CINERULOSE_B("L-cinerulose B", "CC1OC(I)C(O)CC1=O"),
	O_METHYL_L_AMICETOSE("O-methyl-L-amicetose", "COC1CCC(I)OC1C"),
	_4_O_METHYL_L_RHODINOSE("4-O-methyl-L-rhodinose", "COC1CCC(I)OC1C"),
	L_DAUNOSAMINE("L-daunosamine", "OC1C(N)CC(I)OC1C"), 
	L_RISTOSAMINE("L-ristosamine", "CC1OC(CC(C1O)N)I"),
	D_DIGITOXOSE("D-digitoxose", "CC1OC(CC(C1O)O)I"),
	L_DIGITOXOSE("L-digitoxose", "CC1OC(CC(C1O)O)I"),
	_2_DEOXY_L_FUCOSE("2-deoxy-L-fucose", "CC1OC(CC(C1O)O)I"),
	D_OLIVOSE("D-olivose", "CC1OC(I)CC(O)C1O"),
	D_OLIOSE("D-oliose", "CC1OC(I)CC(O)C1O"),
	_4_OXO_L_VANCOSAMINE("4-oxo-L-vancosamine", "NC1(C)CC(I)OC(C)C1=O"),
	D_FOROSAMINE("D-forosamine", "CC1OC(I)CCC1N(C)C"), 
	L_ACTINOSAMINE("L-actinosamine", "COC1C(OC(CC1N)I)C"),
	L_VANCOSAMINE("L-vancosamine", "IC1OC(C)C(O)C(C)(N)C1"),
	L_VICENISAMINE("L-vicenisamine", "CNC1C(O)CC(I)OC1C"),
	D_CHALCOSE("D-chalcose", "COC1CC(C)OC(I)C1O"),
	D_MYCAROSE("D-mycarose", "CC1OC(I)CC(O)(C)C1O"),
	L_OLEANDROSE("L-oleandrose", "COC1CC(I)OC(C)[C@@H]1O"),
	OLIVOMOSE("Olivomose", "IC1CC(O)C(OC)C(C)O1"),
	D_MYCOSAMINE("D-mycosamine", "OC1C(N)C(O)C(I)OC1C"),
	_4_DEOXY_4_THIO_D_DIGITOXOSE("4-deoxy-4-thio-D-digitoxose ", "CC1OC(I)CC(O)C1S"),
	D_FUCOFURANOSE("D-fucofuranose", "CC(O)C1C(O)C(O)C(I)O1"),
	D_FUCOSE("D-fucose", "CC1OC(I)C(O)C(O)C1O"),		
	L_RHAMNOSE("L-rhamnose", "OC1C(O)C(O)C(I)OC1C"),
	_4_N_ETHYL_4_AMINO_3_O_METHOXY_2_4_5_TRIDEOXYPENTOSE("4-N-ethyl-4-amino-3-O-methoxy-2,4,5-trideoxypentose", 
			"CCNC1COC(I)CC1OC"),
	D_3_N_METHYL_4_O_METHYL_L_RISTOSAMINE("D-3-N-methyl-4-O-methyl-L-ristosamine", "CNC1CC(I)OC(C)C1OC"),
	N_N_DIMETHYL_L_PYRROLOSAMINE("N,N-dimethyl-L-pyrrolosamine", "CC1OC(CC(C1N(C)C)O)I"),	
	D_DESOSAMINE("D-desosamine", "CN(C1CC(C)OC(I)C1O)C"),
	L_MEGOSAMINE("L-megosamine", "CN(C1CC(I)OC(C)C1O)C"),
	NOGALAMINE("Nogalamine", "IC1OC(C)C(O)C(N(C)C)C1O"), 
	L_RHODOSAMINE("L-rhodosamine", "CC1OC(I)CC(C1O)N(C)C"),	
	D_ANGOLOSAMINE("D-angolosamine", "CN(C)C1CC(I)OC(C)C1O"),
	KEDAROSAMINE("Kedarosamine", "IC1OC(C)C(N(C)C)C(O)C1"),
	L_NOVIOSE("L-noviose", "CC1(C(O)C(O)C(O)C(O1)I)C"),
	L_CLADINOSE("L-cladinose", "OC1C(C)OC(I)CC1(C)OC"),
	_2_N_METHYL_D_FUCOSAMINE("2'-N-methyl-D-fucosamine", "CNC1C(I)OC(C)C(O)C1O"),
	D_DIGITALOSE("D-digitalose", "COC1C(O)C(C)OC(I)C1O"),
	_3_O_METHYL_RHAMNOSE("3-O-methyl-rhamnose", "COC1C(O)C(C)OC(I)C1O"),
	_2_O_METHYL_RHAMNOSE("2-O-methyl-rhamnose", "COC1C(I)OC(C)C(O)C1O"),
	_6_DEOXY_3_C_METHYL_L_MANNOSE("6-deoxy-3-C-methyl-L-mannose", "OC1(C)C(O)C(I)OC(C)C1O"),
	_4_6_DIDEOXY_4_HYDROXYLAMINO_D_GLUCOSE("4,6-dideoxy-4-hydroxylamino-D-glucose", "CC1OC(C(C(C1NO)O)O)I"),
	_3_N_N_DIMETHYL_L_EREMOSAMINE("3-N,N-dimethyl-L-eremosamine", "IC1CC(C(O)C(C)O1)(C)N(C)C"),
	CHROMOSE("Chromose (4-O-acetyl-beta-D-oliose)", "CC1OC(CC(C1OC(C)=O)O)I"), 
	D_RAVIDOSAMINE("D-ravidosamine", "CN(C1C(O)C(I)OC(C)C1O)C"),
	MYCAMINOSE("3-N,N-dimethyl-D-mycosamine", "CN(C1C(O)C(I)OC(C)C1O)C"),
	_2_3_O_DIMETHYL_L_RHAMNOSE("2,3-O-dimethyl-L-rhamnose", "COC1C(I)OC(C)C(O)C1OC"),
	_2_4_O_DIMETHYL_L_RHAMNOSE("2,4-O-dimethyl-L-rhamnose", "COC1C(C)OC(I)C(OC)C1O"),
	_3_4_O_DIMETHYL_L_RHAMNOSE("3,4-O-dimethyl-L-rhamnose", "COC1C(C)OC(I)C(O)C1OC"),
	_2_THIOGLUCOSE("2-thioglucose", "IC1C(S)C(O)C(O)C(CO)O1"),
	OLIVOMYCOSE("Olivomycose", "OC1(C)CC(I)OC(C)C1OC(C)=O"),
	_4_N_N_DIMETHYLAMINO_4_DEOXY_5_C_METHYL_L_RHAMNOSE("4-N,N-dimethylamino-4-deoxy-5-C-methyl-l-rhamnose", 
			"CN(C1C(C(C(OC1(C)C)I)O)O)C"),
	_2_3_4_TRI_O_METHYLRHAMNOSE("2,3,4-tri-O-methylrhamnose", "COC1C(C)OC(I)C(OC)C1OC"),
	_4_O_ACETYL_L_ARCANOSE("4-O-acetyl-L-arcanose", "IC1CC(OC)(C)C(OC(C)=O)C(C)O1"),
	_3_N_ACETYL_D_RAVIDOSAMINE("3-N-acetyl-D-ravidosamine", "CC(N(C1C(O)C(I)OC(C)C1OC(C)=O)C)=O"),
	L_NOGALOSE("L-nogalose", "COC1C(OC(C(C1(OC)C)OC)I)C"),
	_4_O_ACETYL_D_RAVIDOSAMINE("4-O-acetyl-D-ravidosamine", "CN(C1[C@@H](O)[C@@H](I)OC(C)C1OC(C)=O)C"),
	_4_O_METHYL_L_NOVIOSE("4-O-methyl-L-noviose", "CC1(C)[C@H](OC)C(O)C(O)C(I)O1"),
	_3_N_ACETYL_4_O_ACETYL_D_RAVIDOSAMINE("3-N-acetyl-4-O-acetyl-D-ravidosamine", 
			"CC(N(C1[C@@H](O)[C@@H](I)OC(C)C1OC(C)=O)C)=O"),
	_3_5_METHYL_2_PYRROLYLCARBONYL_4_O_METHYL_L_NOVIOSE("3-(5'-methyl-2'-pyrrolylcarbonyl-)4-O-methyl-L-noviose", 
			"COC1C(OC(C2=CC=C(C)N2)=O)C(O)C(I)OC1(C)C"),
	MADUROSE("Madurose", "IC1C(O)C(O)(C)C(N)CO1"),
	_4_N_METHYL_4_AMINO_3_O_METHOXY_2_4_5_TRIDEOXYPENTOSE("4-N-methyl-4-amino-3-O-methoxy-2,4,5-trideoxypentose", 
			"IC1CC(OC)[C@H](CO1)NC"),
	;
	
	private String name;
	private String smiles;
	
	private Sugars(String name, String smiles) {
		this.name = name;
		this.smiles = smiles;	
	}
	
	public String sugarNames() {
		return name;
	}

	public String smiles() {
		return smiles; 
	}
}
