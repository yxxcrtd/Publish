package com.chinaedustar.publish.event;

import java.util.EventObject;
import com.chinaedustar.publish.comp.Menu;
import com.chinaedustar.publish.model.Admin;

/**
 * 管理菜单显示事件对象。
 * 
 * @author liujunxing
 *
 */
public class MenuShowEvent extends EventObject {
	/**	 */
	private static final long serialVersionUID = -3736700582740016146L;
	
	/** 要显示的菜单。 */
	private final transient Menu menu;
	
	/** 当前管理员对象。 */
	private final transient Admin admin;
	
	/**
	 * 构造函数。
	 * @param source - 发生此事件的对象，当前为 tag.MenuTag。
	 */
	public MenuShowEvent(Object source, Menu menu, Admin admin) {
		super(source);
		this.menu = menu;
		this.admin = admin;
	}
	
	/**
	 * 获得要显示的菜单。
	 * @return
	 */
	public Menu getMenu() {
		return this.menu;
	}
	
	/**
	 * 获得当前管理员对象。
	 * @return
	 */
	public Admin getAdmin() {
		return this.admin;
	}
}
