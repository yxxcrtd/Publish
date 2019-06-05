package com.chinaedustar.publish.action;

import com.chinaedustar.publish.PublishUtil;
import com.chinaedustar.publish.model.*;

/**
 * 扩展属性操作。
 * 
 * @author liujunxing
 *
 */
public class ExtendsAction extends AbstractActionEx {
	// 支持的操作： save, delete, delete_all
	static {
		registerCommand(ExtendsAction.class, new ActionCommand("save"));
		registerCommand(ExtendsAction.class, new ActionCommand("delete"));
		registerCommand(ExtendsAction.class, new ActionCommand("delete_all"));
	}

	private ExtendPropertySupport host_obj;
	
	/**
	 * 新增/保存一个扩展属性。
	 * @return
	 */
	protected ActionResult save() {
		// 得到容器对象。
		ActionResult result;
		if ((result = getOperateObject()) != null) return result;
		
		// 获得参数。
		String propName = param_util.safeGetStringParam("propName");
		String propType = param_util.safeGetStringParam("propType");
		String propValue = param_util.safeGetStringParam("propValue");
		ExtendProperty prop = new ExtendProperty();
		prop.setPropName(propName);
		prop.setPropType(propType);
		prop.setPropValue(propValue);
		
		// 保存。
		ExtendPropertySet prop_set = new ExtendPropertySet(host_obj, pub_ctxt);
		tx_proxy.createExtendProperty(prop_set, prop);
		// prop_set.saveProperty(prop);
		
		return SUCCESS;
	}

	/**
	 * 删除一个扩展属性。
	 * @return
	 */
	protected ActionResult delete() {
		String prop_name = param_util.safeGetStringParam("propName");
		if (PublishUtil.isEmptyString(prop_name)) return INVALID_PARAM;
		
		// 得到容器对象。
		ActionResult result;
		if ((result = getOperateObject()) != null) return result;

		// 删除。
		ExtendPropertySet prop_set = new ExtendPropertySet(host_obj, pub_ctxt);
		tx_proxy.deleteExtendProperty(prop_set, prop_name);
		
		return SUCCESS;
	}
	
	/**
	 * 删除所有扩展属性。
	 * @return
	 */
	protected ActionResult delete_all() {
		// 得到容器对象。
		ActionResult result;
		if ((result = getOperateObject()) != null) return result;

		// 删除。
		ExtendPropertySet prop_set = new ExtendPropertySet(host_obj, pub_ctxt);
		tx_proxy.deleteAllExtendProperty(prop_set);

		return SUCCESS;
	}
	
	private ActionResult getOperateObject() {
		// 获得参数。
		int id = param_util.safeGetIntParam("id");
		String objectClass = param_util.safeGetStringParam("objectClass");
		if (id == 0 || PublishUtil.isEmptyString(objectClass))
			return INVALID_PARAM;
		
		// 加载对象。
		String hql = "FROM " + objectClass + " WHERE id = " + id;
		Object obj = PublishUtil.executeSingleObjectQuery(pub_ctxt.getDao(), hql);
		if (obj == null) {
			messages.add("未能找到指定标识的对象，可能参数不正确。");
			return INVALID_PARAM;
		}
		
		if (!(obj instanceof ExtendPropertySupport)) {
			messages.add("对象不支持扩展属性，请确定您是从有效链接提交的数据。");
			return INVALID_PARAM;
		}
		
		this.host_obj = (ExtendPropertySupport)obj;
		this.host_obj._init(pub_ctxt, site);
		
		return null;
	}
}
