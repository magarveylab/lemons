package lemons.interfaces;

import java.util.List;

import org.openscience.cdk.interfaces.IAtom;

/**
 * A list of atom tags.
 * 
 * @author michaelskinnider
 *
 * @param <T>
 */
public interface ITagList<T> extends List<T> {

	/**
	 * Get all tags of a given type within this list of tags.
	 * 
	 * @param type
	 *            the query tag type
	 * @return all tags of the query tag type
	 */
	public ITagList<ITag> getTags(ITagType type);

	/**
	 * Get all tags on a given atom within this list of tags.
	 * 
	 * @param atom
	 *            the query atom
	 * @return all tags on the given atom
	 */
	public ITagList<ITag> getTags(IAtom atom);

	/**
	 * Check if this list of tags contains a tag of the given type on the given
	 * atom.
	 * 
	 * @param type
	 *            the query tag type
	 * @param atom
	 *            the query tag atom
	 * @return true if this list of tags contains a tag of the given type on the
	 *         given atom
	 */
	public boolean contains(ITagType type, IAtom atom);

	/**
	 * Check if this list of tags contains a tag of the given type.
	 * 
	 * @param type
	 *            the query tag type
	 * @return true if this list of tags contains a tag of the given type
	 */
	public boolean contains(ITagType type);

}
