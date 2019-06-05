package com.chinaedustar.publish.label;

import com.chinaedustar.publish.model.AbstractModelBase;

/**
 * Model 对象模型中一些通用数据的标签实现。
 * 
 * @author liujunxing
 *
 */
public final class CommonModelLabel extends GroupLabelBase {
	/** 不需要实例化。 */
	private CommonModelLabel() {
	}
		
	/**
	 * 注册 LabelHandler.
	 *
	 */
	public static final void registerHandler(LabelHandlerMap map) {
		// logger.debug("CommonModelLabel 注册了其包含的一组标签解释器。");
		
		map.put("Parent", new ModelPropertyLabelHandler());
		map.put("ObjectClass", new ModelPropertyLabelHandler());
		map.put("Class", new ModelPropertyLabelHandler());
	}
	
	/** #{ModelProperty } */
	static class ModelPropertyLabelHandler extends AbstractLabelHandler {
		@Override public int handleLabel() {
			String sLabelName = label.getLabelName();
			
			AbstractModelBase model = getCurrentItem(env);
			
			if (model != null) {
				// 得到对象中的属性
				Object oGetObj = model.get(sLabelName.substring(0, 1).toLowerCase() + sLabelName.substring(1));
				
				if (oGetObj == null) {
					out("Null");
				} else {
					out(oGetObj.toString());
				}
			} else {
				out("#{" + sLabelName + " 未找到适当对象}");
			}
			return PROCESS_DEFAULT;
		}
	}
}
