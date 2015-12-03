package lemons.util;

import java.util.List;

import javax.vecmath.Point2d;
import javax.vecmath.Point3d;

import lemons.data.Monomer;
import lemons.data.Tag;
import lemons.data.TagList;
import lemons.interfaces.IMonomer;
import lemons.interfaces.ITag;
import lemons.interfaces.ITagList;
import lemons.interfaces.ITagType;
import lemons.io.SmilesIO;

import org.openscience.cdk.Bond;
import org.openscience.cdk.exception.CDKException;
import org.openscience.cdk.interfaces.IAtom;
import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.interfaces.IBond;
import org.openscience.cdk.interfaces.IBond.Order;
import org.openscience.cdk.interfaces.IPseudoAtom;
import org.openscience.cdk.interfaces.IStereoElement;

public class Copier {

	public static IMonomer deepCopy(IMonomer monomer) {
		IMonomer copy = new Monomer(monomer.type());
		deepCopy(monomer, copy);
		return copy;
	}

	public static void deepCopy(IMonomer monomer, IMonomer copy) {
		IAtomContainer original = monomer.structure();

		IAtomContainer structureCopy = original.getBuilder().newInstance(
				IAtomContainer.class);
		copyAtoms(original, structureCopy);
		copyBonds(original, structureCopy);
		copy.setStructure(structureCopy);

		ITagList<ITag> tagsCopy = copyTags(monomer, original, structureCopy);
		copy.setTags(tagsCopy);
	}

	public static void copyAtoms(IAtomContainer original, IAtomContainer copy) {
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

	public static void copyBonds(IAtomContainer original, IAtomContainer copy) {
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
	
    private static void copyBonds(IAtom[] atoms, IAtomContainer container, IAtomContainer newAtomContainer) {
        int bondCount = container.getBondCount();
        IBond[] bonds = new IBond[bondCount];
        for (int index = 0; index < container.getBondCount(); index++) {
            bonds[index] = new Bond();
            int IndexI = 999;
            for (int i = 0; i < container.getAtomCount(); i++) {
                if (container.getBond(index).getAtom(0) == container.getAtom(i)) {
                    IndexI = i;
                    break;
                }
            }
            int IndexJ = 999;
            for (int j = 0; j < container.getAtomCount(); j++) {
                if (container.getBond(index).getAtom(1) == container.getAtom(j)) {
                    IndexJ = j;
                    break;
                }
            }

            IAtom atom1 = atoms[IndexI];
            IAtom atom2 = atoms[IndexJ];

            Order order = container.getBond(index).getOrder();
            IBond.Stereo stereo = container.getBond(index).getStereo();
            bonds[index] = new Bond(atom1, atom2, order, stereo);
            if (container.getBond(index).getID() != null) {
                bonds[index].setID(new String(container.getBond(index).getID()));
            }
            newAtomContainer.addBond(bonds[index]);

        }
    }


	public static ITagList<ITag> copyTags(IMonomer monomer, IAtomContainer original,
			IAtomContainer copy) {
		ITagList<ITag> tagsCopy = new TagList();
		
		for (ITag tag : monomer.getTags()) {
			IAtom originalAtom = tag.atom();
			int originalAtomIdx = original.getAtomNumber(originalAtom);
			IAtom copyAtom = copy.getAtom(originalAtomIdx);
			ITagType type = tag.type();
			ITag tagCopy = new Tag(type, copyAtom);
			tagsCopy.add(tagCopy);
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
