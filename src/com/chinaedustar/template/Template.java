package com.chinaedustar.template;

import com.chinaedustar.template.core.AbstractTemplateElement;

/**
 * 表示一个编译过的模板。
 * 
 * <p>一般而言，通过 TemplateFactory 来获得一个模板。</p>
 * 
 * <p>一个编译过的模板是不可改变的(immutable)，从而可以被多线程同时访问和使用。</p>
 * 
 * @author liujunxing
 */
public interface Template extends TemplateConstant {
	/**
	 * 获得此模板的名字。
	 * @return
	 */
	public String getName();
		
	/**
	 * 获得此模板的根元素。
	 * @return
	 */
	public AbstractTemplateElement getRootElement();
}
