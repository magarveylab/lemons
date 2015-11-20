package lemons.interfaces;

import org.openscience.cdk.interfaces.IAtom;

public interface ITag {

	public ITagType type();
	
	public void setType(ITagType type);
	
	public IAtom atom();
	
	public void setAtom(IAtom atom);
	
}
