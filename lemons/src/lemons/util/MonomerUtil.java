package lemons.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import lemons.enums.monomers.NonProteinogenicAminoAcids;
import lemons.enums.monomers.PolyketideMonomers;
import lemons.enums.monomers.ProteinogenicAminoAcids;
import lemons.interfaces.IMonomerType;

public class MonomerUtil {

	public static List<IMonomerType> getAminoAcidTypes() {
		List<IMonomerType> aa = new ArrayList<IMonomerType>();
		aa.addAll(Arrays.asList(ProteinogenicAminoAcids.values()));
		aa.addAll(Arrays.asList(NonProteinogenicAminoAcids.values()));
		return aa;
	}

	public static List<IMonomerType> getAminoAcidExtenderTypes(
			List<IMonomerType> extenderTypes) {
		List<IMonomerType> aaExtenderTypes = new ArrayList<IMonomerType>(
				extenderTypes);
		Iterator<IMonomerType> itr = aaExtenderTypes.iterator();
		while (itr.hasNext()) {
			IMonomerType next = itr.next();
			if (isAminoAcidNonExtender(next)) 
				itr.remove();
		}
		return aaExtenderTypes;
	}
	
	public static boolean isAminoAcidNonExtender(IMonomerType type) {
		List<IMonomerType> pkTypes = new ArrayList<IMonomerType>();
		pkTypes.addAll(Arrays.asList(PolyketideMonomers.values()));
		return pkTypes.contains(type) && !type.smiles().contains("=O");
	}

}
