package lemons.interfaces;

import lemons.util.exception.BadTagException;

import org.openscience.cdk.interfaces.IAtom;
import org.openscience.cdk.interfaces.IAtomContainer;

/**
 * A peptide or polyketide monomer.
 * 
 * @author michaelskinnider
 *
 */
public interface IMonomer {

	public IMonomerType type();
	
	public void setType(IMonomerType type);
	
	public IAtomContainer structure();
	
	public void setStructure(IAtomContainer structure);
	
	public IAtom begin() throws BadTagException;
		
	public boolean hasExtend() throws BadTagException;
	
	public IAtom extend() throws BadTagException;
	
	public ITagList<ITag> getTags();

	public void setTags(ITagList<ITag> tags);

	public void addTag(ITag tag);

	public void addTags(ITagList<ITag> tags);

	public ITagList<ITag> getTags(ITagType type);

	public ITagList<ITag> getTags(IAtom atom);
	
	public boolean containsTag(ITagType type, IAtom atom);

}
