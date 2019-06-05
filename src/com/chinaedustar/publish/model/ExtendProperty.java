package com.chinaedustar.publish.model;

import java.math.BigDecimal;
import java.text.ParseException;
import com.chinaedustar.common.util.StringHelper;
import com.chinaedustar.publish.*;

/**
 * 表示一个对象的扩展属性。
 * 
 * @author liujunxing
 */
public class ExtendProperty {
	/** 此对象的唯一标识。 */
	private int id;
	
	/** 目标对象唯一标识。 */
	private String targetUuid;
	
	/** 目标对象类别，如 'site', 'channel', 'article' 等 。 */
	private String targetClass; 
	
	/** 属性的名字。 */
	private String propName;
	
	/** 属性的类型。 */
	private String propType;
	
	/** 属性的值。 */
	private String propValue;
	
	/** 原始目标对象。 */
	private Object targetObject;
	
	/** 根据类型转化过的值。 */
	private Object typedValue;

	/** 是否发生了改变。 */
	private boolean changed = false;
	
	/** 发布系统环境对象。 */
	@SuppressWarnings("unused")
	private PublishContext pub_ctxt;
	
	/** 内存状态。 = 0 表示正常状态(刚装载进入)，= -1 表示临时属性不持久化 */
	private int status;
	
	/** 扩展属性集合对象。 */
	private ExtendPropertySet prop_set;
	
	/**
	 * 构造一个缺省的 ExtendProperty 的新实例。
	 *
	 */
	public ExtendProperty() {
		
	}
	
	/** 获得这个值。 */
	@Override public String toString() {
		return String.valueOf(this.typedValue);
	}

	void _init(PublishContext pub_ctxt, PublishModelObject owner_obj) {
		this.pub_ctxt = pub_ctxt;
		this.prop_set = (ExtendPropertySet)owner_obj;
		this.targetObject = prop_set.getTargetObject();
		this.typedValue = makeTypeValue();
	}
	
	// === getter, setter ======================================================
	
	/** 此对象的唯一标识。 */
	public int getId() {
		return this.id;
	}
	
	/** 此对象的唯一标识。 */
	public void setId(int id) {
		this.id = id;
	}

	/** 目标对象唯一标识。 */
	public String getTargetUuid() {
		return this.targetUuid;
	}

	/** 目标对象唯一标识。 */
	public void setTargetUuid(String targetUuid) {
		this.targetUuid = targetUuid;
	}
	
	/** 目标对象类别，如 'site', 'channel', 'article' 等 。 */
	public String getTargetClass() {
		return this.targetClass;
	}
	
	/** 目标对象类别，如 'site', 'channel', 'article' 等 。 */
	public void setTargetClass(String targetClass) {
		this.targetClass = targetClass;
	}
	
	/** 属性的名字。 */
	public String getPropName() {
		return this.propName;
	}
	
	/** 属性的名字。 */
	public void setPropName(String propName) {
		this.propName = propName;
	}
	
	/** 属性的值。 */
	public String getPropType() {
		return this.propType;
	}
	
	/** 属性的值。 */
	public void setPropType(String propType) {
		this.propType = propType;
	}

	/** 属性的值。 */
	public String getPropValue() {
		return this.propValue;
	}
	
	/** 属性的值。 */
	public void setPropValue(String propValue) {
		this.propValue = propValue;
	}

	/** 设置属性的值，并设置/返回是否发生了变化。 */
	public boolean _setPropValue(String propValue) {
		if (propValue == null && this.propValue == null) return false;
		if (propValue == null) {
			this.changed = true;
			this.propValue = null;
			return true;
		}
		
		if (propValue.equals(this.propValue)) return false;
		
		this.changed = true;
		this.propValue = propValue;
		return true;
	}
	
	/** 内存状态。 = 0 表示正常状态(刚装载进入)， = -1 表示临时属性不持久化 */
	public int getStatus() {
		return this.status;
	}
	
	/** 内存状态。 = 0 表示正常状态(刚装载进入)， = -1 表示临时属性不持久化 */
	public void setStatus(int status) {
		this.status = status;
	}
	
	// === 一些常用变名 getter ====================================================
	
	/** 获得属性的名字。 */
	public String getName() {
		return this.getPropName();
	}
	
	/** 获得此属性的实际 java 类型的值。 */
	public Object getValue() {
		return this.typedValue;
	}

	/** 设置属性值。 */
	public void setValue(Object val) {
		this.typedValue = val;
	}
	
	/** 获得此属性所述的目标对象。 */
	public Object getObject() {
		return this.targetObject;
	}

	/** 值是否发生了变化。 */
	public boolean _getChanged() {
		return this.changed;
	}
	
	// === impl
	
	/** 根据类型将 string 转换为实际值。 */
	private Object makeTypeValue() {
		// 'string', 'html' 就是字符串，因而无须转换。
		if ("string".equalsIgnoreCase(this.propType) || "html".equalsIgnoreCase(this.propType))
			return this.propValue;
		else if ("int".equalsIgnoreCase(this.propType) || "integer".equalsIgnoreCase(this.propType))
			return PublishUtil.safeParseInt(this.propValue, 0);
		else if ("number".equalsIgnoreCase(this.propType))
			return safeParseNumber(this.propValue);
		else if ("date".equalsIgnoreCase(this.propType))
			return safeParseDate(this.propValue);
		else if ("multi.string".equalsIgnoreCase(this.propType))
			return new MultiString(this.propValue);
		else if ("multi.int".equalsIgnoreCase(this.propType))
			return new MultiInt(this.propValue);
		
		return this.propValue;
	}
	
	// 安全转换数字。
	private BigDecimal safeParseNumber(String str) {
		try {
			return new BigDecimal(str);
		} catch (NumberFormatException ex) {
			return BigDecimal.ZERO;
		}
	}
	
	private java.util.Date safeParseDate(String str) {
		try {
			java.text.DateFormat formatter = java.text.DateFormat.getDateInstance();
			return formatter.parse(str);
		} catch (ParseException ex) {
			return new java.util.Date();
		}
	}
	
	/**
	 * 表示一个多值字符串。
	 * @author liujunxing
	 *
	 */
	public static final class MultiString extends java.util.ArrayList<String> {
		/**
		 * 
		 */
		private static final long serialVersionUID = 6791804680986770714L;

		/**
		 * 构造。
		 * @param str
		 */
		public MultiString(String str) {
			String[] vals = StringHelper.split(str, "\r\n", false);
			if (vals == null) return;
			for (int i = 0; i < vals.length; ++i) {
				if (vals[i] == null || vals[i].length() == 0) continue;
				super.add(vals[i]);
			}
		}
		
		/*
		 * (non-Javadoc)
		 * @see java.util.AbstractCollection#toString()
		 */
		@Override public String toString() {
			StringBuilder strbuf = new StringBuilder();
			for (int i = 0; i < size(); ++i) {
				if (i > 0) strbuf.append("\r\n");
				strbuf.append(get(i));
			}
			return strbuf.toString();
		}
	}

	/**
	 * 表示一个多值整数集合。
	 * @author liujunxing
	 *
	 */
	public static final class MultiInt extends java.util.ArrayList<Integer> {
		/**
		 * 
		 */
		private static final long serialVersionUID = 5649936543997685710L;

		/**
		 * 构造。
		 * @param str
		 */
		public MultiInt(String str) {
			String[] vals = StringHelper.split(str, "\r\n", false);
			if (vals == null) return;
			for (int i = 0; i < vals.length; ++i) {
				if (vals[i] == null || vals[i].length() == 0) continue;
				try {
					super.add(Integer.parseInt(vals[i]));
				} catch (NumberFormatException ex) {
					// ignore
				}
			}
		}
	}
}
