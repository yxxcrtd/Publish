package com.chinaedustar.rtda.bean;

import com.chinaedustar.rtda.ObjectWrapper;
import com.chinaedustar.rtda.model.BooleanDataModel;

/**
 * 布尔型数据访问模型。其支持 BooleanDataModel 接口。
 * 
 * @author liujunxing
 *
 */
public class BooleanModelImpl extends BeanModelImpl<Boolean> 
		implements BooleanDataModel {
	/**
	 * 使用指定的 target 对象和 wrapper 对象构造一个 BooleanModelImpl 的实例。
	 * @param target
	 * @param wrapper
	 */
	public BooleanModelImpl(Boolean target, ObjectWrapper wrapper) {
		super(target, wrapper);
	}
	
	/**
	 * 转换此标量为一个字符串表示。一般可以直接用 Object.toString() 来实现。
	 * @param optional_format - (可选的) 使用此格式，可能为 null.
	 * @return
	 */
	public String to_string(String optional_format) {
		return target.toString();
	}

	// === BooleanDataModel 接口实现 =========================================
	
    /**
     * @return - 返回按照 boolean 量来解释此数据的值。
     */
    public boolean getAsBoolean() {
    	return target.booleanValue();
    }
    
    /*
     * (non-Javadoc)
     * @see com.chinaedustar.rtda.bean.BeanModelImpl#builtin(java.lang.String, java.lang.Object[])
     */
	@Override public Object builtin(String method_name, Object[] param_list) {
		if ("is_boolean".equals(method_name))
			return true;
		return super.builtin(method_name, param_list);
	}

}
