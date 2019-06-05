package com.chinaedustar.template.core;

/**
 * 可以带有子元素的容器元素抽象类。
 * 
 * @author liujunxing
 *
 */
public abstract class AbstractContainerElement extends AbstractTemplateElement {
	/** 第一个子节点，可能为 null. */
	private AbstractTemplateElement first_child;
	
	/** 最后一个子节点，可能为 null，也可能和 first_child 相同。 */
	private AbstractTemplateElement last_child;
	
	/** 子节点数量。 */
	private int child_count;
	
	/**
	 * 获得第一个子节点。缺省元素节点没有子节点。
	 * @return
	 */
	@Override public final AbstractTemplateElement getFirstChild() {
		return this.first_child;
	}
	
	/**
	 * 获得最后一个子节点。缺省元素节点没有子节点。
	 * @return
	 */
	@Override public final AbstractTemplateElement getLastChild() {
		return this.last_child;
	}
	
	/**
	 * 判断是否有子节点。
	 * @return
	 */
	public final boolean hasChild() {
		return (first_child != null);
	}
	
	/**
	 * 获得子节点的数量。
	 * @return
	 */
	public final int getChildCount() {
		return this.child_count;
	}

	/**
	 * 将指定节点做为此节点的最后一个子节点。
	 * @param child
	 * @exception IllegalArgumentException child==null 时发生。
	 * @exception IllegalStateException child 已经有了父节点时发生。
	 */
	@Override final void appendChild(AbstractTemplateElement child) {
		if (child == null) throw new IllegalArgumentException("child == null");
		if (child.getParent() != null) throw new IllegalStateException("child already has a parent element");
		
		child.setPrevElement(this.last_child);
		child.setNextElement(null);
		child.setParent(this);
		
		if (this.last_child == null) {
			assert (this.first_child == null);
			this.first_child = child;
		} else {
			this.last_child.setNextElement(child);
		}
		
		this.last_child = child;
		++this.child_count;
	}

	protected final void _internalXferSiblingToChild() {
		if (this.first_child != null)
			throw new IllegalStateException("子节点已经存在了，不能再次转换。");
		
		// 没有兄弟元素，所以不用转。
		if (this.getNextElement() == null)
			return;
		
		// 开始转换。
		AbstractTemplateElement iter_elem = this.getNextElement();
		this.first_child = iter_elem; 
		this.first_child.setPrevElement(null);
		
		while (iter_elem != null) {
			// iter_elem.parent = this.parent, 其子节点数量-1
			--this.getParent().child_count;
			iter_elem.setParent(this);
			++this.child_count;
			iter_elem = iter_elem.getNextElement();
		}
		
		this.last_child = iter_elem;
		this.setNextElement(null);
		this.getParent().last_child = this;
	}
}
