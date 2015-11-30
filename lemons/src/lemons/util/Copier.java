package lemons.util;

import java.util.List;

import javax.vecmath.Point2d;
import javax.vecmath.Point3d;

import lemons.data.Monomer;
import lemons.data.Tag;
import lemons.data.TagList;
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

public class Copier {
	
	public Monomer deepCopy(Monomer monomer) {
		Monomer copy = new Monomer(monomer.type());
		deepCopy(monomer, copy);
		return monomer;
	}
	
	public void deepCopy(Monomer monomer, Monomer copy) {
		IAtomContainer original = monomer.structure();
		
		IAtomContainer structureCopy = original.getBuilder().newInstance(IAtomContainer.class);
		copyAtoms(original, structureCopy);
		copyBonds(original, structureCopy);
		copy.setStructure(structureCopy);

		ITagList<ITag> tagsCopy = copyTags(monomer, original, structureCopy);
		copy.setTags(tagsCopy);
	}
	
	public void copyAtoms(IAtomContainer original, IAtomContainer copy) {
		int atomCount = original.getAtomCount();
		IAtom[] atoms = new IAtom[atomCount];
		for (int i = 0; i < atomCount; i++) {
			IAtom originalAtom = original.getAtom(i);
			IAtom copyAtom = null;
			
			if (originalAtom instanceof IPseudoAtom) {
                copyAtom = original.getBuilder().newInstance(IPseudoAtom.class, originalAtom);
			} else {
				copyAtom = original.getBuilder().newInstance(IAtom.class, originalAtom);
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
	
	public void copyBonds(IAtomContainer original, IAtomContainer copy) {
		int bondCount = original.getBondCount();
		IBond[] bonds = new IBond[bondCount];
		for (int i = 0; i < bondCount; i++) {
			bonds[i] = new Bond();
			
			int atomIdx1 = -1;
			for (int j = 0; j < original.getAtomCount(); j++) {
				if (original.getBond(j).getAtom(0) == original.getAtom(i)) {
					atomIdx1 = j;
					break;
				}
			}
			
			int atomIdx2 = -1;
			for (int j = 0; j < original.getAtomCount(); j++) {
				if (original.getBond(j).getAtom(1) == original.getAtom(j)) {
					atomIdx2 = j;
					break;
				}
			}
			
			IAtom atom1 = copy.getAtom(atomIdx1);
			IAtom atom2 = copy.getAtom(atomIdx2);
			
			Order order = original.getBond(i).getOrder();
            IBond.Stereo stereo = original.getBond(i).getStereo();
            bonds[i] = new Bond(atom1, atom2, order, stereo);
            if (original.getBond(i).getID() != null) {
                bonds[i].setID(new String(original.getBond(i).getID()));
            }
            copy.addBond(bonds[i]);
		}
	}
	
	public ITagList<ITag> copyTags(Monomer monomer, IAtomContainer original, IAtomContainer copy) {
		ITagList<ITag> tagsCopy = new TagList();
		
		for (int i = 0; i < original.getAtomCount(); i++) {
			IAtom originalAtom = original.getAtom(i);
			IAtom copyAtom = copy.getAtom(i);
			
			List<ITag> atomTags = monomer.getTags(originalAtom);
			for (ITag atomTag : atomTags) {
				ITagType type = atomTag.type();
				ITag tagCopy = new Tag(type, copyAtom);
				tagsCopy.add(tagCopy);
			}
		}
		
		return tagsCopy;
	}

    private static void set2D(IAtomContainer container, int index, IAtom[] atoms) {
        if ((container.getAtom(index)).getPoint2d() != null) {
            atoms[index].setPoint2d(new Point2d(container.getAtom(index).getPoint2d()));
        }
    }

    private static void set3D(IAtomContainer container, int index, IAtom[] atoms) {
        if ((container.getAtom(index)).getPoint3d() != null) {
            atoms[index].setPoint3d(new Point3d(container.getAtom(index).getPoint3d()));
        }
    }

    private static void setFractionalPoint3d(IAtomContainer container, int index, IAtom[] atoms) {
        if ((container.getAtom(index)).getFractionalPoint3d() != null) {
            atoms[index].setFractionalPoint3d(new Point3d(container.getAtom(index).getFractionalPoint3d()));
        }
    }

    private static void setID(IAtomContainer container, int index, IAtom[] atoms) {
        if (container.getAtom(index).getID() != null) {
            atoms[index].setID(container.getAtom(index).getID());
        }
    }

    private static void setHydrogenCount(IAtomContainer container, int index, IAtom[] atoms) {
        if (container.getAtom(index).getImplicitHydrogenCount() != null) {
            atoms[index].setImplicitHydrogenCount(Integer.valueOf(container.getAtom(index).getImplicitHydrogenCount()));
        }
    }

    private static void setCharge(IAtomContainer container, int index, IAtom[] atoms) {
        if (container.getAtom(index).getCharge() != null) {
            atoms[index].setCharge(new Double(container.getAtom(index).getCharge()));
        }
    }
	
}
