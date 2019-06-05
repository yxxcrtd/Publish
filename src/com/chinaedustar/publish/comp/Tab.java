package com.chinaedustar.publish.comp;

/**
 * 代表一个属性页对象。
 * 
 * @author liujunxing
 */
public class Tab {
	
	/** 标签页的父对象（标签页集合对象Tabs）。 */
	private Tabs parent;
	
	/** 标签页的名称，内存中唯一标识对象。 */
	private String name;
	
	/** 标签页的显示文本。 */
	private String text;
	
	/** 标签页所使用的内容模板名字。 */
	private String template;
	
	/** 标签页的数据提供者。 */
	private String provider;
	
	/** 是否是默认的当前标签页，true：可用，显示出来；false：不可用，不显示出来。 */
	private boolean _default = false;
	
	/** 是否可用，true：可用，显示出来；false：不可用，不显示出来。 */
	private boolean _disabled = true;
	
	/** 标签页的扩展数据。 */
	private Object extend;
	
	/**
	 * 使用缺省参数构造一个 Tab 的实例。
	 *
	 */
	public Tab() {
	}
	
	/** 标签页的父对象（标签页集合对象Tabs）。 */
	public Tabs getParent() {
		return this.parent;
	}
	
	/** 标签页的父对象（标签页集合对象Tabs）。 */
	public void setParent(Tabs tabs) {
		this.parent = tabs;
	}

	/** 标签页的数据提供者。 */
	public String getProvider() {
		return provider;
	}

	/** 标签页的数据提供者。 */
	public void setProvider(String provider) {
		this.provider = provider;
	}

	/** 标签页所使用的内容模板名字。 */
	public String getTemplate() {
		return template;
	}

	/** 标签页所使用的内容模板名字。 */
	public void setTemplate(String template) {
		this.template = template;
	}

	/** 标签页的扩展数据。 */
	public Object getExtend() {
		return extend;
	}

	/** 标签页的扩展数据。 */
	public void setExtend(Object extend) {
		this.extend = extend;
	}

	/** 标签页的名称，内存中唯一标识对象。 */
	public String getName() {
		return name;
	}

	/** 标签页的名称，内存中唯一标识对象。 */
	public void setName(String name) {
		this.name = name;
	}

	/** 标签页的显示文本。 */
	public String getText() {
		return text;
	}

	/** 标签页的显示文本。 */
	public void setText(String text) {
		this.text = text;
	}

	/** 是否是默认的当前标签页，true：可用，显示出来；false：不可用，不显示出来。 */
	public boolean getDefault() {
		return _default;
	}

	/** 是否是默认的当前标签页，true：可用，显示出来；false：不可用，不显示出来。 */
	public void setDefault(boolean _default) {
		this._default = _default;
	}

	/** 是否可用，true：可用，显示出来；false：不可用，不显示出来。 */
	public boolean getDisabled() {
		return _disabled;
	}

	/** 是否可用，true：可用，显示出来；false：不可用，不显示出来。 */
	public void setDisabled(boolean _disabled) {
		this._disabled = _disabled;
	}
}
