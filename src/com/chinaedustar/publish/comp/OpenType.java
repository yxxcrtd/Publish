package com.chinaedustar.publish.comp;

import com.chinaedustar.publish.PublishUtil;

/**
 * 在网页上面经常要生成链接目标属性如 &lt;a href='xxx' target='_blank, _self, ...'&gt;,
 *  此类用于辅助在模板中使用 OpenType 以简化模板中写法。
 *  
 * OpenType 在比较的时候有特定的算法。
 * 
 * @author liujunxing
 *
 */
public class OpenType {
	/** openType 内部数字表示值，0 = '_self' 自己， 1 = '_blank', -1 表示自定义。 */
	private final int value;
	
	/** 目标窗口名，如果 value == -1, 则使用此目标窗口名。 */
	private final String target;

	/** 链接到自己。 Default. Load the linked document into the window in which the link was clicked (the active window). */
	public static final OpenType SELF = new OpenType(0, "_self");
	
	/** 链接到新窗口。Load the linked document into a new blank window. This window is not named */
	public static final OpenType BLANK = new OpenType(1, "_blank");
	
	/** 链接到父窗口。 Load the linked document into the immediate parent of the document the link is in. */
	public static final OpenType PARENT = new OpenType(2, "_parent");
	
	/** 链接到顶级窗口。 Load the linked document into the topmost window.  */
	public static final OpenType TOP = new OpenType(3, "_top");
	
	/** Load the linked document into the browser search pane. Available in Internet Explorer 5 or later.  */
	public static final OpenType SEARCH = new OpenType(4, "_search");
	
	/** Load the linked document into the HTML content area of the Media Bar. Available in Internet Explorer 6 or later. */
	public static final OpenType MEDIA = new OpenType(5, "_media");
	
	/**
	 * 根据给定的数字获得一个合适的 OpenType 实例。
	 * @param openType
	 * @return
	 */
	public static final OpenType fromInteger(int v) {
		switch (v) {
		case 0: return SELF;
		case 1: return BLANK;
		case 2: return PARENT;
		case 3: return TOP;
		case 4: return SEARCH;
		case 5: return MEDIA;
		default: return SELF;		// 未知数字只好返回 SELF
		}
	}
	
	/**
	 * 根据给定的字符串获得一个合适的 OpenType 实例。
	 * @param openType
	 * @return
	 */
	public static final OpenType fromString(String openType) {
		// 为空使用缺省的。
		if (openType == null) return SELF;
		openType = openType.trim();
		if (openType.length() == 0) return SELF;
		
		// 如果是数字，则根据数字选择一个合适的。
		if (PublishUtil.isInteger(openType)) {
			int v = Integer.parseInt(openType);
			return fromInteger(v);
		}
		
		// 比较字符串。
		if ("_self".equalsIgnoreCase(openType))
			return SELF;
		else if ("_blank".equalsIgnoreCase(openType))
			return BLANK;
		else if ("_parent".equalsIgnoreCase(openType))
			return PARENT;
		else if ("_top".equalsIgnoreCase(openType))
			return TOP;
		else if ("_search".equalsIgnoreCase(openType))
			return SEARCH;
		else if ("_media".equalsIgnoreCase(openType))
			return MEDIA;
		
		// 可能是用于指定了某种窗口，则返回对其的封装。
		return new OpenType(-1, openType);
	}
	
	/**
	 * 通过指定的 value, target 构造 OpenType 类型。
	 * @param value
	 * @param target
	 */
	private OpenType(int value, String target) {
		this.value = value;
		this.target = target;
	}
	
	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override public String toString() {
		// 缺省我们返回数字值。
		if (value >= 0)
			return String.valueOf(value);
		else
			return this.target;
	}
	
	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override public boolean equals(Object o) {
		// 我们执行奇怪的 equals 比较，主要用于模板语法。
		if (o == null) 
			return compareNull();
		if (o instanceof Number)
			return compareNumber((Number)o);
		if (o instanceof OpenType)
			return compareOpenType((OpenType)o);
		return false;
	}
	
	private boolean compareNull() {
		// 0 == null
		if (this.value == 0) return true;
		return false;
	}
	
	// 数字 >= 0 且相同才认为相同。
	private boolean compareNumber(Number o) {
		int v = o.intValue();
		if (this.value >= 0 && this.value == v) return true;
		
		return false;
	}
	
	private boolean compareOpenType(OpenType o) {
		if (o.value == this.value && this.value >= 0) return true;
		return this.target.equalsIgnoreCase(o.target);
	}
	
	/**
	 * 获得 openType 内部数字表示值
	 * @return
	 */
	public int getValue() {
		return this.value;
	}
	
	/**
	 * 获得 openType 目标窗口名
	 * @return
	 */
	public String getTarget() {
		return this.target;
	}

	/**
	 * 获得组装好的 html target 标记，格式为 target='xx', 如果 target='_self' 则返回 '',
	 *   这是缺省情况。
	 * @return target='xxx', 注意前面没有空格，后面有空格。
	 *   如果 target='_self' 则返回 ' '(一个空格)
	 */
	public String getHtmlTarget() {
		if ("_self".equalsIgnoreCase(this.target))
			return " ";
		else
			return "target='" + this.target + "' ";
	}
	
	/**
	 * 获得组装好的 html target 标记，格式为 target='xx', 对 target='_self' 缺省也返回。
	 * @return target='xxx', 注意前面没有空格，后面有空格。
	 */
	public String getHtmlTarget2() {
		return "target='" + this.target + "' ";
	}
}
