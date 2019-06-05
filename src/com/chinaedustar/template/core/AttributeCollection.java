package com.chinaedustar.template.core;

import java.util.*;

/**
 * 定义一个属性的集合类。
 *  
 * @author liujunxing
 */
public class AttributeCollection {
	/** 空的属性集合。 */
	public static final AttributeCollection EMPTY_ATTRIBUTE_COLL = new AttributeCollection(true);
	
	/** 属性集合。 */
	private final java.util.ArrayList<Attribute> attr_v = new java.util.ArrayList<Attribute>();

	/** 集合是否是只读的，其不允许外部更改。 */
	private boolean readonly = false;
	
	public AttributeCollection() {
		
	}
	
	public AttributeCollection(boolean readonly) {
		this.readonly = readonly;
	}
	
	/** 优化表示这个 Collection。 */
	public static final AttributeCollection optimizedCollection(AttributeCollection attr_list) {
		if (attr_list == null) return EMPTY_ATTRIBUTE_COLL;
		if (attr_list == EMPTY_ATTRIBUTE_COLL) return EMPTY_ATTRIBUTE_COLL;
		if (attr_list.size() == 0) return EMPTY_ATTRIBUTE_COLL;
		return attr_list;
	}
	
	/** 转为字符串表示。 */
	@Override public String toString() {
		StringBuilder strbuf = new StringBuilder();
		
		strbuf.append("AttributeCollection{");
		for (int i = 0; i < attr_v.size(); ++i) {
			Attribute attr = attr_v.get(i);
			if (i > 0) strbuf.append(",");
			strbuf.append(attr.getKey()).append("=").append(attr.getValue());
		}
		strbuf.append("}");
		
		return strbuf.toString();
	}

	/**
	 * 获得集合内元素的数量。
	 * @return
	 */
	public int size() {
		return attr_v.size();
	}
	
	/**
	 * 集合是否为空。
	 * @return
	 */
	public boolean isEmpty() {
		return attr_v.isEmpty();
	}
	
	/**
	 * 获得指定名字的属性值，如果该名字的属性不存在，则返回 null。
	 * @param name - 属性的名字。不区分大小写。
	 * @return
	 */
	public String getNamedAttribute(String name) {
		if (name == null) name = Attribute.EMPTY_STRING;
		for (int i = 0; i < attr_v.size(); ++i) {
			Attribute attr = attr_v.get(i);
			if (attr.getKey().equalsIgnoreCase(name))
				return attr.getValue();
		}
		return null;
	}

	/**
	 * 获得指定索引处的属性。
	 * @param index
	 * @return
	 */
	public Attribute getIndexedAttribute(int index) {
		return this.attr_v.get(index);
	}
	
	/**
	 * 添加一个新的 Attribute。如果有重名的，则用新的取代前面的。
	 *   一个例外是 key == "" 则不取代，直接添加。
	 */
	void addAttribute(Attribute attr_new) {
		if (readonly)
			throw new IllegalStateException("此集合是只读的，不能在此集合上面执行写入操作。");
		String key = attr_new.getKey();
		if ("".equals(key) == false) {
			// 如果有重名的，用新的取代前面的。
			for (int i = 0; i < attr_v.size(); ++i) {
				Attribute attr = attr_v.get(i);
				if (attr.getKey().equalsIgnoreCase(key)) {
					attr_v.set(i, attr_new);
					return;
				}
			}
		}
		attr_v.add(attr_new);
	}
	
	/**
	 * 添加一个新的 key,value 对。如果有重名的，则用新的取代前面的。
	 *   一个例外是 key == "" 则不取代，直接添加。
	 * @param key
	 * @param value
	 */
	void addKeyValue(String key, String value) {
		if (readonly)
			throw new IllegalStateException("此集合是只读的，不能在此集合上面执行写入操作。");
		if (key == null) key = Attribute.EMPTY_STRING;
		if (value == null) value = Attribute.EMPTY_STRING;
		addAttribute(new Attribute(key, value));
	}
	
	/**
	 * 得到属性的字符串值。
	 * @param name 属性名称。
	 * @param defVar 缺省值。
	 * @return
	 */
	public String safeGetStringAttribute(String name, String defvar) {
		String str = getNamedAttribute(name);
		if (str == null || str.trim().length() < 1) return defvar;
		else return str;
	}
	
	/**
	 * 得到属性的整型值。
	 * @param name 属性名称。
	 * @param defVar 缺省值。
	 * @return
	 */
	public int safeGetIntAttribute(String name, int defvar) {
		String str = getNamedAttribute(name);
		if (str == null || str.trim().length() < 1) {
			return defvar;
		} else {
			try {
				return Integer.parseInt(str);
			} catch (NumberFormatException ex) {
				return defvar;
			}
		}
	}
	
	/**
	 * 得到属性的 id 标识数组，如果不存在则返回 null。
	 * @param name
	 * @return
	 */
	public List<Integer> safeGetIntArray(String name) {
		String str = getNamedAttribute(name);
		if (str == null || "null".equals(str)) return null;
		List<Integer> result = new ArrayList<Integer>();
		if (str.length() == 0) return result;
		
		// 每个单项都可以是 '3, 4, 5' 的 标识数组。
		String[] val_split = str.split(",");
		for (int j = 0; j < val_split.length; ++j) {
			try {
				result.add(Integer.parseInt(val_split[j]));
			} catch (NumberFormatException ex) {
				// ignore
			}
		}
		return result;
	}
	
	/**
	 * 得到属性的Bool型值
	 * @param name 属性名称。
	 * @param defVar 缺省值。
	 * @return
	 */
	public Boolean safeGetBoolAttribute(String name, Boolean defvar) {
		String str = getNamedAttribute(name);
		if (str == null || str.trim().length() < 1) {
			return defvar;
		} else if (str.trim().equals("0")) {
			return false;
		} else if (str.trim().equals("1")) {
			return true;
		} else if (str.trim().equalsIgnoreCase("null")) {
			return null;
		} else if (str.trim().equalsIgnoreCase("yes")) {
			return true;
		} else if (str.trim().equalsIgnoreCase("no")) {
			return false;
		} else {
			return Boolean.parseBoolean(str);
		}
	}

	/**
	 * 得到属性的Bool型值
	 * @param name 属性名称。
	 * @param defVar 缺省值。
	 * @return
	 */
	public boolean safeGetBoolAttribute(String name, boolean defvar) {
		Boolean v = safeGetBoolAttribute(name, defvar ? Boolean.TRUE : Boolean.FALSE);
		if (v == null) return false;
		return v.booleanValue();
	}

	/**
	 * 将 label 的属性转换为一个 HashMap<String, Object>，用于子模板的选项参数。
	 * @return
	 */
	public final Map<String, Object> attrToOptions() {
		HashMap<String, Object> options = new HashMap<String, Object>();
		if (attr_v == null || attr_v.size() == 0) return options;
		
		for (int i = 0; i < attr_v.size(); ++i) {
			Attribute attr = attr_v.get(i);
			options.put(attr.getKey(), attr.getValue());
		}
		return options;
	}

}
