package lemons.data;

import java.util.ArrayList;
import java.util.Iterator;

import org.openscience.cdk.interfaces.IAtom;

import lemons.interfaces.ITag;
import lemons.interfaces.ITagList;
import lemons.interfaces.ITagType;

public class TagList extends ArrayList<ITag>
		implements ITagList<ITag> {

	private static final long serialVersionUID = -3186014948695014609L;

	@Override
	public ITagList<ITag> getTags(ITagType type) {
		ITagList<ITag> tags = new TagList();
		Iterator<ITag> itr = this.iterator();
		while (itr.hasNext()) {
			ITag next = itr.next();
			if (next.type() == type)
				tags.add(next);
		}
		return tags;
	}
	
	@Override
	public ITagList<ITag> getTags(IAtom atom) {
		ITagList<ITag> tags = new TagList();
		Iterator<ITag> itr = this.iterator();
		while (itr.hasNext()) {
			ITag next = itr.next();
			if (next.atom().equals(atom))
				tags.add(next);
		}
		return tags;
	}
	
	@Override
	public boolean contains(ITagType type) {
		boolean flag = false;
		Iterator<ITag> itr = this.iterator();
		while (itr.hasNext()) {
			ITag next = itr.next();
			if (next.type().equals(type))
				flag = true;
		}
		return flag;
	}
	
	@Override
	public boolean contains(ITagType type, IAtom atom) {
		boolean flag = false;
		Iterator<ITag> itr = this.iterator();
		while (itr.hasNext()) {
			ITag next = itr.next();
			if (next.atom().equals(atom) && next.type().equals(type))
				flag = true;
		}
		return flag;
	}
	
}
