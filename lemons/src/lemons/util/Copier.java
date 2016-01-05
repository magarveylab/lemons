package lemons.util;

import javax.vecmath.Point2d;
import javax.vecmath.Point3d;

import lemons.data.Monomer;
import lemons.data.Tag;
import lemons.data.TagList;
import lemons.interfaces.IMonomer;
import lemons.interfaces.ITag;
import lemons.interfaces.ITagList;
import lemons.interfaces.ITagType;
import org.openscience.cdk.Bond;
import org.openscience.cdk.interfaces.IAtom;
import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.interfaces.IBond;
import org.openscience.cdk.interfaces.IBond.Order;
import org.openscience.cdk.interfaces.IPseudoAtom;
import org.openscience.cdk.interfaces.IStereoElement;

/**
 * Create deep copies of LEMONS objects. 
 * @author michaelskinnider
 *
 */
public class Copier {

	/**
	 * Create a deep copy of a monomer. The type, structure, and atom tags are
	 * all deep copied.
	 * 
	 * @param monomer
	 *            the monomer to copy
	 * @return a deep copy of that monomer
	 */
	public static IMonomer deepCopy(IMonomer monomer) {
		IMonomer copy = new Monomer(monomer.type());
		deepCopy(monomer, copy);
		return copy;
	}

	
	private static void deepCopy(IMonomer monomer, IMonomer copy) {
		IAtomContainer original = monomer.structure();

		IAtomContainer structureCopy = original.getBuilder().newInstance(
				IAtomContainer.class);
		copyAtoms(original, structureCopy);
		copyBonds(original, structureCopy);
		copy.setStructure(structureCopy);

		ITagList<ITag> tagsCopy = copyTags(monomer, original, structureCopy);
		copy.setTags(tagsCopy);
	}

	private static void copyAtoms(IAtomContainer original, IAtomContainer copy) {
		int atomCount = original.getAtomCount();
		IAtom[] atoms = new IAtom[atomCount];
		for (int i = 0; i < atomCount; i++) {
			IAtom originalAtom = original.getAtom(i);
			IAtom copyAtom = null;

			if (originalAtom instanceof IPseudoAtom) {
				copyAtom = original.getBuilder().newInstance(IPseudoAtom.class,
						originalAtom);
			} else {
				copyAtom = original.getBuilder().newInstance(IAtom.class,
						originalAtom);
			}
			atoms[i] = copyAtom;

			set2D(original, i, atoms);
			set3D(original, i, atoms);
			setFractionalPoint3d(original, i, atoms);
			setID(original, i, atoms);
			setHydrogenCount(original, i, atoms);
			setCharge(original, i, atoms);
			copy.addAtom(atoms[i]);
		}

		for (IStereoElement stereoElement : original.stereoElements())
			copy.addStereoElement(stereoElement);
	}

	private static void copyBonds(IAtomContainer original, IAtomContainer copy) {
		int bondCount = original.getBondCount();
		IBond[] bonds = new IBond[bondCount];
		for (int index = 0; index < bondCount; index++) {
			bonds[index] = new Bond();

			int atomIdx1 = -1;
			for (int i = 0; i < original.getAtomCount(); i++) {
				if (original.getBond(index).getAtom(0) == original.getAtom(i)) {
					atomIdx1 = i;
					break;
				}
			}
			
			if (atomIdx1 == -1) //
				continue; // 

			int atomIdx2 = -1;
			for (int i = 0; i < original.getAtomCount(); i++) {
				if (original.getBond(index).getAtom(1) == original.getAtom(i)) {
					atomIdx2 = i;
					break;
				}
			}
			
			if (atomIdx2 == -1)
				continue;

			IAtom atom1 = copy.getAtom(atomIdx1);
			IAtom atom2 = copy.getAtom(atomIdx2);

			Order order = original.getBond(index).getOrder();
			IBond.Stereo stereo = original.getBond(index).getStereo();
			bonds[index] = new Bond(atom1, atom2, order, stereo);
			if (original.getBond(index).getID() != null) {
				bonds[index].setID(new String(original.getBond(index).getID()));
			}
			copy.addBond(bonds[index]);
		}
	}

	private static ITagList<ITag> copyTags(IMonomer monomer, IAtomContainer original,
			IAtomContainer copy) {
		ITagList<ITag> tagsCopy = new TagList();
		
			for (ITag tag : monomer.getTags()) {
				try {
					IAtom originalAtom = tag.atom();
					int originalAtomIdx = original.getAtomNumber(originalAtom);
					IAtom copyAtom = copy.getAtom(originalAtomIdx);
					ITagType type = tag.type();
					ITag tagCopy = new Tag(type, copyAtom);
					tagsCopy.add(tagCopy);
				} catch (Exception e ){
					throw new RuntimeException("Couldn't copy tag " + tag.type());
				}
			}
		return tagsCopy;
	}
	
	private static void set2D(IAtomContainer container, int index, IAtom[] atoms) {
		if ((container.getAtom(index)).getPoint2d() != null) {
			atoms[index].setPoint2d(new Point2d(container.getAtom(index)
					.getPoint2d()));
		}
	}

	private static void set3D(IAtomContainer container, int index, IAtom[] atoms) {
		if ((container.getAtom(index)).getPoint3d() != null) {
			atoms[index].setPoint3d(new Point3d(container.getAtom(index)
					.getPoint3d()));
		}
	}

	private static void setFractionalPoint3d(IAtomContainer container,
			int index, IAtom[] atoms) {
		if ((container.getAtom(index)).getFractionalPoint3d() != null) {
			atoms[index].setFractionalPoint3d(new Point3d(container.getAtom(
					index).getFractionalPoint3d()));
		}
	}

	private static void setID(IAtomContainer container, int index, IAtom[] atoms) {
		if (container.getAtom(index).getID() != null) {
			atoms[index].setID(container.getAtom(index).getID());
		}
	}

	private static void setHydrogenCount(IAtomContainer container, int index,
			IAtom[] atoms) {
		if (container.getAtom(index).getImplicitHydrogenCount() != null) {
			atoms[index].setImplicitHydrogenCount(Integer.valueOf(container
					.getAtom(index).getImplicitHydrogenCount()));
		}
	}

	private static void setCharge(IAtomContainer container, int index,
			IAtom[] atoms) {
		if (container.getAtom(index).getCharge() != null) {
			atoms[index].setCharge(new Double(container.getAtom(index)
					.getCharge()));
		}
	}

}
