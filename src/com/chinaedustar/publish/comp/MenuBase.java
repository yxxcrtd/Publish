package com.chinaedustar.publish.comp;

/**
 * 做为 Menu, MenuItem 的共同基类。
 * 
 * @author liujunxing
 */
public abstract class MenuBase {
	/** 这个菜单或菜单项的标识，可选。 */
	private String id = "";
	
	/** 这个菜单或菜单项的文字。 */
	private String text = "";
	
	/** 菜单项链接地址。 */
	private String url;
	
	public MenuBase() {
		
	}
	
	public MenuBase(String text) {
		this.text = text;
	}
	
	public MenuBase(String text, String url) {
		this.text = text;
		this.url = url;
	}
	
	/** 获得菜单项的标识。 */
	public String getId() {
		return this.id;
	}
	
	/** 获得菜单项的标识。 */
	public void setId(String id) {
		this.id = id;
	}
	
	/** 获取菜单项显示的文字。 */
	public String getText() {
		return this.text;
	}
	
	/** 设置菜单项显示的文字。 */
	public void setText(String text) {
		this.text = text;
	}
	/** 获取菜单项链接地址。 */
	public String getUrl() {
		return url;
	}
	/** 设置菜单项链接地址。 */
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * 判定这个项目是否是一个菜单或子菜单。不是菜单(Menu)就是菜单项(MenuItem)。
	 * @return
	 */
	public abstract boolean getIsMenu();
	
	/**
	 * 判定这个项目是否是一个菜单项。不是菜单项就是子菜单。
	 * @return
	 */
	public abstract boolean getIsMenuItem();

}
