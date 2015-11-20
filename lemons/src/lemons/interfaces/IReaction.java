package lemons.interfaces;

import org.openscience.cdk.interfaces.IAtom;

public interface IReaction {
	
	public IReactionType type();
	
	public ITagList<ITag> getTags();

	public void setTags(ITagList<ITag> tags);

	public void addTag(ITag tag);

	public void addTags(ITagList<ITag> tags);

	public ITagList<ITag> getTags(ITagType type);

	public ITagList<ITag> getTags(IAtom atom);
	
}
