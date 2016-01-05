package lemons.util;

import lemons.interfaces.IMonomer;
import lemons.interfaces.ITag;
import lemons.interfaces.ITagList;
import lemons.interfaces.ITagType;
import lemons.util.exception.BadTagException;

/**
 * Utilities class for operations involving atom tags.
 * 
 * @author michaelskinnider
 *
 */
public class TagManipulator {

	/**
	 * Get a single tag of a given type from the tags within a monomer.
	 * 
	 * @param monomer
	 *            the monomer in question
	 * @param type
	 *            the type of tag to get
	 * @return the single tag of this type within the monomer
	 * @throws BadTagException
	 *             if there are zero or more the one tags of the given type
	 *             within the monomer
	 */
	public static ITag getSingleTag(IMonomer monomer, ITagType type)
			throws BadTagException {
		ITagList<ITag> allTags = monomer.getTags();
		ITagList<ITag> tags = allTags.getTags(type);
		if (tags.size() > 1)
			throw new BadTagException("Error: more than 1 " + type + " tags!");
		if (tags.size() == 0)
			return null;
		ITag tag = tags.get(0);
		return tag;
	}

	/**
	 * Get a single tag of a given type from the tags within a list of tags.
	 * 
	 * @param allTags
	 *            the list of tags
	 * @param type
	 *            the type of tag to get
	 * @return the single tag of this type within the list
	 * @throws BadTagException
	 *             if there are zero or more the one tags of the given type
	 *             within the list
	 */
	public static ITag getSingleTag(ITagList<ITag> allTags, ITagType type)
			throws BadTagException {
		ITagList<ITag> tags = allTags.getTags(type);
		if (tags.size() > 1)
			throw new BadTagException("Error: " + "number of " + type
					+ " tags >1!");
		if (tags.size() == 0)
			throw new BadTagException("Error: " + "number of " + type
					+ " tags = 0!");
		ITag tag = tags.get(0);
		return tag;
	}

}
