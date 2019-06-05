package com.chinaedustar.publish.tag;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.Tag;
import com.chinaedustar.publish.comp.Menu;

/**
 * 实现一个 pub:submenu 的标签。
 * 
 * <pre>
 * 这是一个数据定义标签，其在页面上面语法为：
 *  <pub:menu name="mainMenu" >
 *    <pub:submenu id="xxx" 
 *           text="xxx" 
 *           icon="xxx" >
 *      ... submenu or menuitem declaration
 *    </pub:submenu>
 *  </pub:menu>
 * </pre>
 * 
 * @author liujunxing
 */
public class SubMenuTag extends ComponentTagSupport {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4583008624825745100L;
	
	/** 子菜单对象。 */
	private Menu submenu = new Menu();
	
	public SubMenuTag() {
		
	}

	/** 此子菜单的文字，必须给出。 */
	public String getText() {
		return this.submenu.getText();
	}
	
	/** 此子菜单的文字，必须给出。 */
	public void setText(String text) {
		this.submenu.setText(text);
	}
	
	/** 此子菜单的图标或图片。 */
	public String getIcon() {
		return this.submenu.getIcon();
	}
	
	/** 此子菜单的图标或图片。 */
	public void setIcon(String icon) {
		this.submenu.setIcon(icon);
	}
	
	/** 此子菜单的链接地址。 */
	public String getUrl() {
		return this.submenu.getUrl();
	}
	
	/** 此子菜单的链接地址。 */
	public void setUrl(String url) {
		this.submenu.setUrl(url);
	}

	/**
	 * 获得创建的 Menu 对象，子标签调用此函数给里面添加菜单项。
	 * @return
	 */
	public Menu getMenu() {
		return this.submenu;
	}
	
	/**
	 * 当开始执行此标签时被调用。因为我们是数据定义，其不需要输出任何数据，从而
	 *   我们将内容全部缓冲起来，并在 doEndTag() 的时候吃掉所有内容，不显示出来。
	 */
	public int doStartTag() throws JspException {
		// 这个标签必须被嵌套到 MenuTag 里面或者 SubMenuTag 里面。
		Tag parent = this.getParent();
		if ((parent instanceof MenuTag) || (parent instanceof SubMenuTag)) {
			// 设置这个子菜单的属性
			this.submenu.setId(this.id);
			// 其它留待 doEndTag() 的时候处理。
		} else {
			throwExcept();
		}
		return EVAL_BODY_BUFFERED;
	}
	
	/**
	 * 在标签结束的时候被调用。
	 */
	public int doEndTag() throws JspException {
		// 设置这个子菜单的属性
		this.submenu.setId(this.id);
		
		// 加入到父 Menu 集合中。
		Tag parent = this.getParent();
		if (parent instanceof MenuTag) {
			((MenuTag)parent).getMenu().addSubMenu(-1, this.submenu);
		} else if (parent instanceof SubMenuTag) {
			((SubMenuTag)parent).getMenu().addSubMenu(-1, this.submenu);
		} else {
			throwExcept();
		}
		
		// 重新初始化。
		submenu = new Menu();
		return EVAL_PAGE;
	}
	
	private final void throwExcept() throws JspException {
		throw new JspException("submenu 标签必须被嵌套在 menu 或 submenu 标签之中。");
	}
}
