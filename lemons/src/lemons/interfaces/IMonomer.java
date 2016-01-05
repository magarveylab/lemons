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

	/**
	 * Get the type of this monomer.
	 * 
	 * @return the monomer type
	 */
	public IMonomerType type();

	/**
	 * Set the type of this monomer.
	 * 
	 * @param type
	 *            the monomer type
	 */
	public void setType(IMonomerType type);

	/**
	 * Get the structure of this monomer.
	 * 
	 * @return the structure of the monomer
	 */
	public IAtomContainer structure();

	/**
	 * Set the structure of this monomer.
	 * 
	 * @param structure
	 *            the structure of the monomer
	 */
	public void setStructure(IAtomContainer structure);

	/**
	 * Get the atom at which this monomer is extended.
	 * 
	 * @return the atom at which which this monomer is extended
	 * @throws BadTagException
	 *             if no such atom exists
	 */
	public IAtom begin() throws BadTagException;

	/**
	 * Determine whether this monomer can extend a previous monomer.
	 * 
	 * @return true if this monomer can extend a previous monomer; false if it
	 *         must begin the polymer (e.g., fatty acids)
	 * @throws BadTagException
	 *             if there are more than 1 points at which this monomer can
	 *             extend a previous monomer
	 */
	public boolean hasExtend() throws BadTagException;

	/**
	 * Get the atom at which this monomer extends the previous monomer.
	 * 
	 * @return the atom at which which this monomer extends the previous monomer
	 * @throws BadTagException
	 *             if no such atom exists
	 */
	public IAtom extend() throws BadTagException;

	/**
	 * Get all tags associated with this monomer
	 * 
	 * @return the tags associated with this monomer
	 */
	public ITagList<ITag> getTags();

	/**
	 * Set the list of tags associated with this monomer.
	 * 
	 * @param tags
	 *            the tags associated with this monomer
	 */
	public void setTags(ITagList<ITag> tags);

	/**
	 * Add a tag.
	 * 
	 * @param tag
	 *            the tag to add
	 */
	public void addTag(ITag tag);

	/**
	 * Add a list of tags.
	 * 
	 * @param tags
	 *            the tags to add
	 */
	public void addTags(ITagList<ITag> tags);

	/**
	 * Get all tags of a given type.
	 * 
	 * @param type
	 *            the query tag type
	 * @return all tags of that type
	 */
	public ITagList<ITag> getTags(ITagType type);

	/**
	 * Get all tags on a single atom.
	 * 
	 * @param atom
	 *            the atom in question
	 * @return all tags on that atom
	 */
	public ITagList<ITag> getTags(IAtom atom);

	/**
	 * Check whether this monomer contains a tag.
	 * 
	 * @param type
	 *            the tag type to check for
	 * @param atom
	 *            the atom to check for the tag
	 * @return true if this monomer contains a tag of the given type on the
	 *         given atom
	 */
	public boolean containsTag(ITagType type, IAtom atom);

}
