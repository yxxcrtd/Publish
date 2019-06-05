package com.chinaedustar.publish.itfc;

/**
 * 提供更多信息的 LabelHander 扩展接口
 */
public interface LabelHandler2 extends LabelHandler {

	/**
	 * 获得缺省内建模板的名字
	 * 
	 * @return 如 .builtin.showlogo
	 */
	public String getBuiltinName();

}
