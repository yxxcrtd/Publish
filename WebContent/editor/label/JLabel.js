/**
 * @author 		dengxiaolong
 */

/**
 * ��ǩ��.
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
		throw new Error("����ָ����ǩ������");
	}
};

/**
 * ��ǩ�ķ���������.
 */
JLabel.prototype = {
	/**
	 * ��ʼ����ǩ,�������ǩ������.
	 * @param {Object} name.
	 */
	init:function(name){
		this.name = name;
	},
	/**
	 * ���ñ�ǩ������.
	 * @param {String} name ��������.
	 * @param {Object} value ����ֵ.
	 */
	set:function(name, value){
		if (name) {
			this.property[name] = value;
		}
	},
	/**
	 * ���ɱ�ǩ���ַ���,��#{ShowBanner width='468'}
	 */
	toString:function() {
		if (!this.name) {
			throw new Error("��û��ָ����ǩ������");
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