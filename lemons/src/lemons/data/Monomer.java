package lemons.data;

import lemons.enums.tags.ScaffoldTags;
import lemons.interfaces.IMonomer;
import lemons.interfaces.IMonomerType;
import lemons.interfaces.ITag;
import lemons.interfaces.ITagList;
import lemons.interfaces.ITagType;
import lemons.util.TagManipulator;
import lemons.util.exception.BadTagException;

import org.openscience.cdk.interfaces.IAtom;
import org.openscience.cdk.interfaces.IAtomContainer;

/**
 * A biological monomer.
 * 
 * @author michaelskinnider
 *
 */
public class Monomer implements IMonomer {

	private IMonomerType type;
	private IAtomContainer structure;
	private ITagList<ITag> tags = new TagList();

	/**
	 * Instantiate a new biological monomer.
	 * 
	 * @param type
	 *            the type of the monomer (e.g., threonine or methylmalonate)
	 */
	public Monomer(IMonomerType type) {
		this.type = type;
	}

	public IMonomerType type() {
		return type;
	}

	public void setType(IMonomerType type) {
		this.type = type;
	}

	public IAtom begin() throws BadTagException {
		return TagManipulator.getSingleTag(this,
				ScaffoldTags.BACKBONE_BEGIN).atom();
	}

	public boolean hasExtend() throws BadTagException {
		return (TagManipulator.getSingleTag(this, ScaffoldTags.BACKBONE_EXTEND) != null);
	}
	
	public IAtom extend() throws BadTagException {
		return TagManipulator.getSingleTag(this, 
				ScaffoldTags.BACKBONE_EXTEND).atom();
	}
	
	public IAtomContainer structure() {
		return structure;
	}
	
	public void setStructure(IAtomContainer structure) {
		this.structure = structure;
	}
	
	public ITagList<ITag> getTags() {
		return tags;
	}
	
	public ITagList<ITag> getTags(IAtom atom) {
		return tags.getTags(atom);
	}
	
	public ITagList<ITag> getTags(ITagType type) {
		return tags.getTags(type);
	}
	
	public void addTag(ITag tag) {
		tags.add(tag);
	}
	
	public void addTags(ITagList<ITag> tags) {
		tags.addAll(tags);
	}
	
	public void setTags(ITagList<ITag> tags) {
		this.tags = tags;
	}
	
	public boolean containsTag(ITagType type, IAtom atom) {
		return tags.contains(type, atom);
	}

}
