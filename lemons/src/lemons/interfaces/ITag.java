package lemons.interfaces;

import org.openscience.cdk.interfaces.IAtom;

/**
 * An atom tag.
 * 
 * @author michaelskinnider
 *
 */
public interface ITag {

	/**
	 * Get the type of this tag.
	 * 
	 * @return the tag type
	 */
	public ITagType type();

	/**
	 * Set the type of this tag.
	 * 
	 * @param type
	 *            the tag type
	 */
	public void setType(ITagType type);

	/**
	 * Get the atom tagged by this tag.
	 * 
	 * @return the tagged atom
	 */
	public IAtom atom();

	/**
	 * Set the atom tagged by this tag
	 * 
	 * @param atom
	 *            the tagged atom
	 */
	public void setAtom(IAtom atom);

}
