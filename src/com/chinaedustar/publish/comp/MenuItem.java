package com.chinaedustar.publish.comp;

/**
 * 表示一个菜单项目。
 * 
 * 每个菜单项目可以在页面被当作一个 HashDataModel 来访问。
 * 
 * @author liujunxing
 */
public class MenuItem extends MenuBase {
	/** 菜单项点击之后打开的浏览器窗口名字，缺省为 ""，在当前窗口打开。 */
	private String target = "";
	
	/** 表示这是一个行分隔符，不是一个可显示的菜单项。 */
	private boolean lineBreak = false;
	
	/** 表示这个项目是否换独立一行，缺省为 true。 */
	private boolean itemBreak = true;
	
	/** 权限验证项名字，格式为 'target.operation' */
	private String right = null;
	
	/**
	 * 使用缺省参数构造一个 MenuItem，各个字段都是缺省值（一般为空字符串）。
	 *
	 */
	public MenuItem() {
		
	}

	/**
	 * 使用指定的菜单文字构造一个 MenuItem 的项目。
	 * @param text
	 */
	public MenuItem(String text) {
		super(text);
	}
	
	/**
	 * 使用指定的菜单文字、链接地址、目标窗口构造一个 MenuItem 的项目。
	 * @param text
	 * @param url
	 * @param target - 目标窗口，缺省为 'main'
	 */
	public MenuItem(String text, String url, String target) {
		super(text, url);		
		this.target = target;
		if (target == null || target.length() == 0)
			this.target = "main";
	}
	
	/**
	 * 判定这个项目是否是一个菜单或子菜单。不是菜单(Menu)就是菜单项(MenuItem)。
	 * @return
	 */
	public boolean getIsMenu() {
		return false;
	}
	
	/**
	 * 判定这个项目是否是一个菜单项。不是菜单项就是子菜单。
	 * @return
	 */
	public boolean getIsMenuItem() {
		return true;
	}
	
	/** 获取菜单项点击之后打开的浏览器窗口名字，缺省为 ""，在当前窗口打开。 */
	public String getTarget() {
		return this.target;
	}
	
	/** 设置菜单项点击之后打开的浏览器窗口名字，缺省为 ""，在当前窗口打开。 */
	public void setTarget(String target) {
		this.target = target;
	}

	/** 获取表示这是一个行分隔符，不是一个可显示的菜单项。 */
	public boolean getLineBreak() {
		return this.lineBreak;
	}

	/** 设置表示这是一个行分隔符，不是一个可显示的菜单项。 */
	public void setLineBreak(boolean lineBreak) {
		this.lineBreak = lineBreak;
	}

	/** 获取表示这个项目是否换独立一行，缺省为 true。 */
	public boolean getItemBreak() {
		return this.itemBreak;
	}
	
	/** 设置这个项目是否换独立一行，缺省为 true。 */
	public void setItemBreak(boolean itemBreak) {
		this.itemBreak = itemBreak;
	}

	/** 权限验证项名字，格式为 'target.operation' */
	public String getRight() {
		return this.right;
	}
	
	/** 权限验证项名字，格式为 'target.operation' */
	public void setRight(String right) {
		this.right = right;
	}
}
