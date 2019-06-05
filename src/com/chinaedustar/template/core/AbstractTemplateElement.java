package com.chinaedustar.template.core;

import com.chinaedustar.template.TemplateConstant;

/**
 * 表示一个编译过的模板元素节点。一个节点具有子节点，兄弟结点。缺省没有子节点。
 * 
 * <p>一个编译过的模板是一棵元素组成的树，元素种类如下所列：
 * <pre>
 * 元素类层次结构
 *  AbstractTemplateElement 		- 元素基类。
 *    TextElement					- 文本元素。典型的如 html
 *    ExpressionElement			    - 表达式元素。典型的如 ${user.name}
 *    AbstractContainerElement      - 可以有子元素的元素基类。
 *      AbstractLabelElement		- 标签元素基类。
 *        _StandaloneLabelElement	- 独立标签。 典型的如 ${SiteTitle}, ${my:Custom1}
 *        _NestedLabelElement		- 嵌套标签开始。
 *        _EndLabelElement			- 嵌套标签结束。
 *        LabelElement				- (考虑去掉前面三者，用这一个取代了)
 *    PlaceHolderElement			- 临时用的占位元素。
 * </pre></p>
 * 
 * @author liujunxing
 */
public abstract class AbstractTemplateElement implements TemplateConstant {
	/** 父节点。 */
	private AbstractContainerElement parent_elem;
	
	/** 后继结点。 */
	private AbstractTemplateElement next_elem;

	/** 前驱结点。 */
	private AbstractTemplateElement prev_elem;

	/**
	 * 获得父节点。
	 * @return
	 */
	public final AbstractContainerElement getParent() {
		return this.parent_elem;
	}

	/**
	 * 获得前驱节点。
	 * @return
	 */
	public final AbstractTemplateElement getPrevElement() {
		return this.prev_elem;
	}
	
	/**
	 * 获得后继结点。
	 * @return
	 */
	public final AbstractTemplateElement getNextElement() {
		return this.next_elem;
	}
	
	/**
	 * 获得第一个子节点。缺省元素节点没有子节点。
	 * @return
	 */
	public AbstractTemplateElement getFirstChild() {
		return null;
	}
	
	/**
	 * 获得最后一个子节点。缺省元素节点没有子节点。
	 * @return
	 */
	public AbstractTemplateElement getLastChild() {
		return null;
	}
	
	/**
	 * 判定是否是一个 LabelElement。
	 * @return
	 */
	public boolean isLabelElement() {
		return false;
	}
	
	/**
	 * 给此节点设置其父节点。只能从派生类中调用。
	 * @param parent
	 */
	protected final void setParent(AbstractContainerElement parent) {
		this.parent_elem = parent;
	}
	
	/**
	 * 设置这个节点的后继节点。只能从派生类中调用。
	 *
	 */
	protected final void setNextElement(AbstractTemplateElement new_next) {
		this.next_elem = new_next;
	}
	
	/**
	 * 设置这个节点的前驱节点。只能从派生类中调用。
	 *
	 */
	protected final void setPrevElement(AbstractTemplateElement new_prev) {
		this.prev_elem = new_prev;
	}
	
	/**
	 * 内部使用，将一个节点添加成为这个节点的子节点。容器节点会重载这个方法。
	 * @param elem
	 */
	void appendChild(AbstractTemplateElement elem) {
		throw new UnsupportedOperationException("不能给非容器节点添加一个子节点。");
	}
	
	/**
	 * 使用指定的模板变量对此节点进行访问。
	 * @param env
	 * @return - 返回执行结果要求，执行引擎根据结果进行不同的处理。
	 */
	public abstract int accept(InternalProcessEnvironment env);
}
