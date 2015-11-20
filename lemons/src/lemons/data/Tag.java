package lemons.data;

import lemons.interfaces.ITag;
import lemons.interfaces.ITagType;

import org.openscience.cdk.interfaces.IAtom;

public class Tag implements ITag {

	private ITagType type;
	private IAtom atom;
	
	public Tag(ITagType type, IAtom atom) {
		this.type = type;
		this.atom = atom;
	}

	public ITagType type() {
		return type;
	}
	
	public void setType(ITagType type) {
		this.type = type;
	}
	
	public IAtom atom() {
		return atom;
	}
	
	public void setAtom(IAtom atom) {
		this.atom = atom;
	}
	
}
