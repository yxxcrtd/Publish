package com.chinaedustar.publish.label;

import com.chinaedustar.publish.itfc.LabelHandler2;

/**
 * 支持 LabelHandler2 接口的抽象基类。
 * @author liujunxing
 *
 */
public abstract class AbstractLabelHandler2 extends AbstractLabelHandler implements LabelHandler2 {
	
	/*
	 * (non-Javadoc)
	 * @see com.chinaedustar.publish.itfc.LabelHandler2#getBuiltinName()
	 */
	public String getBuiltinName() {
		throw new UnsupportedOperationException("派生类必须实现 getBuiltinName() 函数。");
	}
}
