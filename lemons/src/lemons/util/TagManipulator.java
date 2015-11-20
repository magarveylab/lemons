package lemons.util;

import lemons.interfaces.IMonomer;
import lemons.interfaces.ITag;
import lemons.interfaces.ITagList;
import lemons.interfaces.ITagType;
import lemons.util.exception.BadTagException;

public class TagManipulator {

	public static ITag getSingleTag(IMonomer monomer, ITagType type)
			throws BadTagException {
		ITagList<ITag> allTags = monomer.getTags();
		ITagList<ITag> tags = allTags.getTags(type);
		if (tags.size() != 1)
			throw new BadTagException("Error: " + "number of " + type
					+ " tags not equal to 1!");
		ITag tag = tags.get(0);
		return tag;
	}

	public static ITag getSingleTag(ITagList<ITag> allTags, ITagType type)
			throws BadTagException {
		ITagList<ITag> tags = allTags.getTags(type);
		if (tags.size() != 1)
			throw new BadTagException("Error: " + "number of " + type
					+ " tags not equal to 1!");
		ITag tag = tags.get(0);
		return tag;
	}

}
