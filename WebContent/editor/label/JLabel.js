/**
 * @author 		dengxiaolong
 */

/**
 * 标签类.
 * @param {Object} name
 */
var JLabel = function(name){
	if (name) {
		this.name = name;
		this.content = null;
		this.template = null;
		this.property = {};
		// this.init(name);
	} else {
		throw new Error("必须指定标签的名称");
	}
};

/**
 * 标签的方法和属性.
 */
JLabel.prototype = {
	/**
	 * 初始化标签,赋予其标签的名称.
	 * @param {Object} name.
	 */
	init:function(name){
		this.name = name;
	},
	/**
	 * 设置标签的属性.
	 * @param {String} name 属性名称.
	 * @param {Object} value 属性值.
	 */
	set:function(name, value){
		if (name) {
			this.property[name] = value;
		}
	},
	/**
	 * 生成标签的字符串,如#{ShowBanner width='468'}
	 */
	toString:function() {
		if (!this.name) {
			throw new Error("还没有指定标签的名称");
		}
		var str = "#{" + this.name;
		for (name in this.property) {
			str += " " + name + "='" + this.property[name] + "'";
		}
		str += "}";
		if (this.template != null) {
			str += "\r\n" + this.template + "\r\n" + "#{/" + this.name + "}";
		}
		this.content = str;
		return str;
	}
}