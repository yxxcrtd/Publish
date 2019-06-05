package com.chinaedustar.publish.tag;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.Tag;

import com.chinaedustar.common.util.StringHelper;
import com.chinaedustar.publish.PublishUtil;
import com.chinaedustar.publish.comp.MenuItem;
import com.chinaedustar.publish.model.Admin;

/**
 * 表示一个菜单项的标签。
 * 
 * <pre>
 * 这是一个数据定义标签，其在页面上面语法为：
 *  <pub:menu name="mainMenu" >
 *    <pub:menuitem id="xxx" 
 *           text="xxx" 
 *           url="xxx"
 *           target="xxx"
 *           lineBreak="xxx"
 *           itemBreak="xxx"
 *           icon="xxx" 
 *           >
 *      ... submenu or menuitem declaration
 *    </pub:submenu>
 *  </pub:menu>
 * </pre>
 * @author liujunxing
 *
 */
public class MenuItemTag extends ComponentTagSupport {
	/**
	 * 
	 */
	private static final long serialVersionUID = 9211218161602374619L;
	
	/** 实际菜单项。 */
	private MenuItem menu_item = new MenuItem();
	
	/**
	 * 构造。
	 *
	 */
	public MenuItemTag() {
		
	}
	
	/* === getter/setter ========================================================= */
	
	public String getText() {
		return menu_item.getText();
	}
	
	public void setText(String text) {
		menu_item.setText(text);
	}
	
	public String getUrl() {
		return menu_item.getUrl();
	}
	
	public void setUrl(String url) {
		menu_item.setUrl(url);
	}
	
	public String getTarget() {
		return menu_item.getTarget();
	}
	
	public void setTarget(String target) {
		menu_item.setTarget(target);
	}
	
	public boolean getLineBreak() {
		return menu_item.getLineBreak();
	}
	
	public void setLineBreak(boolean lineBreak) {
		menu_item.setLineBreak(lineBreak);
	}
	
	public boolean getItemBreak() {
		return menu_item.getItemBreak();
	}
	
	public void setItemBreak(boolean itemBreak) {
		menu_item.setItemBreak(itemBreak);
	}
	
	public String getRight() {
		return this.menu_item.getRight();
	}
	
	public void setRight(String right) {
		this.menu_item.setRight(right);
	}
	
	/**
	 * 当开始执行此标签时被调用。因为我们是数据定义，其不需要输出任何数据，从而
	 *   我们将内容全部缓冲起来，并在 doEndTag() 的时候吃掉所有内容，不显示出来。
	 */
	public int doStartTag() throws JspException {
		Tag parent = super.getParent();
		if (parent instanceof MenuTag || parent instanceof SubMenuTag) 
			return EVAL_BODY_BUFFERED;
		
		throw new JspException("menuitem 标签必须被使用在 menu 或 submenu 标签里面。");
	}
	
	/**
	 * 在标签结束的时候被调用。
	 */
	public int doEndTag() throws JspException {
		// 判定权限。
		if (checkRight() == false) return EVAL_PAGE;
		
		// 设置好菜单项，加入到父集合中。
		menu_item.setId(super.id);
		
		Tag parent = super.getParent();
		if (parent instanceof MenuTag) {
			((MenuTag)parent).getMenu().addMenuItem(-1, menu_item);
		} else if (parent instanceof SubMenuTag) {
			((SubMenuTag)parent).getMenu().addMenuItem(-1, menu_item);
		} else
			throw new JspException("menuitem 标签必须被使用在 menu 或 submenu 标签里面。");
		
		// 重新初始化。
		menu_item = new MenuItem();
		return EVAL_PAGE;
	}
	
	// 检查当前管理员是否具有指定的权限。
	private boolean checkRight() {
		Admin admin = PublishUtil.getCurrentAdmin(super.pageContext.getSession());
		if (admin == null) return false;
		String right = menu_item.getRight();
		// 不限定权限。
		if (right == null || right.trim().length() == 0) return true;
		
		String[] target_operation = StringHelper.split(right, ".", false);
		// format error
		if (target_operation == null || target_operation.length != 2) 
			return false;
		return admin.checkSiteRight(target_operation[0], target_operation[1]);
	}
}
