package com.chinaedustar.publish.tag;

import javax.servlet.jsp.JspException;

import com.chinaedustar.publish.PublishContext;
import com.chinaedustar.publish.PublishUtil;
import com.chinaedustar.publish.comp.Menu;
import com.chinaedustar.publish.event.MenuShowEvent;
import com.chinaedustar.publish.model.Admin;

/**
 * 实现一个 pub:menu 的标签。
 * 
 * <p>
 * 这是一个数据定义标签，其在页面上面写为：
 *  <pub:menu name="mainMenu" 
 *    event="true/false">
 *    ... 菜单项和 子菜单的定义。
 *  </pub:menu>
 * </p>
 * 
 * @author liujunxing
 */
public class MenuTag extends ComponentTagSupport {
	/** serial version uid */
	private static final long serialVersionUID = -1222273472622412830L;

	/** 此菜单在定义好了之后放到 pageContext 中的名字，此属性必须给出。 */
	private String name = "";
	
	/** 此菜单在构建好之后是否激发 PublishContext 里面的 onMenuShow 事件。 */
	private boolean event = false;
	
	/** 菜单对象。 */
	private Menu menu = new Menu();
	
	/**
	 * 构造一个缺省的 MenuTag 的实例。
	 *
	 */
	public MenuTag() {
		
	}
	
	/**
	 * 获得菜单变量的名字。
	 * @return
	 */
	public String getName() {
		return this.name;
	}
	
	/**
	 * 设置菜单变量的名字。
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/** 获得要激发的事件的名字。 */
	public boolean getEvent() {
		return this.event;
	}
	
	/** 设置要激发的事件名字。 */
	public void setEvent(boolean event) {
		this.event = event;
	}
	
	/**
	 * 此菜单的文字。
	 * @return
	 */
	public String getText() {
		return this.menu.getText();
	}
	
	/**
	 * 此菜单的文字。
	 * @param text
	 */
	public void setText(String text) {
		this.menu.setText(text);
	}
	
	/**
	 * 获得创建的 Menu 对象，子标签调用此函数给里面添加菜单项。
	 * @return
	 */
	public Menu getMenu() {
		return this.menu;
	}
	
	/**
	 * 当开始执行此标签时被调用。因为我们是数据定义，其不需要输出任何数据，从而
	 *   我们将内容全部缓冲起来，并在 doEndTag() 的时候吃掉所有内容，不显示出来。
	 */
	public int doStartTag() throws JspException {
		return EVAL_BODY_BUFFERED;
	}
	
	/**
	 * 在标签结束的时候被调用。
	 */
	public int doEndTag() throws JspException {
		try {
			// 激发菜单事件。
			if (this.event) {
				fireMenuShowEvent();
			}
			
			// 将构造的初始菜单放置到 pageContext 里面。
			super.pageContext.setAttribute(this.name, this.menu);
			
			return EVAL_PAGE;
		} finally {
			reuse();
		}
	}
	
	// 激发 onMenuShow 事件。
	private final void fireMenuShowEvent() {
		// 构造 MenuShowEvent Object, 找到 onMenuShow 事件，并 fire 它。
		Admin admin = PublishUtil.getCurrentAdmin(this.pageContext.getSession());
		MenuShowEvent event = new MenuShowEvent(this, this.menu, admin);
		PublishContext pub_ctxt = super.getPublishContext();
		System.out.println("site = " + pub_ctxt.getSite());
		pub_ctxt.getEvents().onMenuShow.fireEvent(event);
	}

	private final void reuse() {
		this.menu = new Menu();
		this.name = "";
		this.event = false;
	}
}
