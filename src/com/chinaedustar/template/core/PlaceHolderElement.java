package com.chinaedustar.template.core;

/**
 * 占位元素，其一般用于做为根容器节点。
 * 
 * @author liujunxing
 */
final class PlaceHolderElement extends AbstractContainerElement {
	PlaceHolderElement() {
	}
	
	@Override public String toString() {
		return "PlaceHolderElement";
	}
	
	/**
	 * 使用指定的模板变量对此节点进行访问。
	 * @param env
	 * @return
	 */
	@Override public int accept(InternalProcessEnvironment env) {
		// 占位用的，不做任何事情。
		return PROCESS_DEFAULT;
	}
}
