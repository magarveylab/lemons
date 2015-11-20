package lemons.interfaces;

import java.util.List;

import org.openscience.cdk.interfaces.IAtom;

public interface ITagList<T> extends List<T> {
	
	public ITagList<ITag> getTags(ITagType type);

	public ITagList<ITag> getTags(IAtom atom);
	
	public boolean contains(ITagType type, IAtom atom);

	public boolean contains(ITagType type);
	
}
