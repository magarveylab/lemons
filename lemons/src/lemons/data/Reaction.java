package lemons.data;

import org.openscience.cdk.interfaces.IAtom;

import lemons.interfaces.IReaction;
import lemons.interfaces.IReactionType;
import lemons.interfaces.ITag;
import lemons.interfaces.ITagList;
import lemons.interfaces.ITagType;

public class Reaction implements IReaction {

	private IReactionType type;
	private ITagList<ITag> tags = new TagList();
	
	public Reaction(IReactionType type) {
		this.type = type;
	}
	
	public IReactionType type() {
		return type;
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
	
}
