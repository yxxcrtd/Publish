package com.chinaedustar.publish.admin;

import javax.servlet.jsp.PageContext;

import com.chinaedustar.publish.PublishException;
import com.chinaedustar.publish.PublishUtil;
import com.chinaedustar.publish.model.DynamicAttributeSupport;

/**
 * 扩展属性管理。
 * @author liujunxing
 *
 */
public class ExtendsManage extends AbstractBaseManage {
	/**
	 * 构造。
	 * @param page_ctxt
	 */
	public ExtendsManage(PageContext page_ctxt) {
		super(page_ctxt);
	}
	
	/**
	 * 初始化扩展属性添加页面。
	 *
	 */
	public void initAddPage() {
		// 获得参数。
		String objectClass = param_util.safeGetStringParam("objectClass");
		int id = param_util.safeGetIntParam("id");
		
		// 加载这个对象，并验证。
		String hql = "FROM " + objectClass + " WHERE id = " + id;
		Object obj = PublishUtil.executeSingleObjectQuery(pub_ctxt.getDao(), hql);
		if (obj == null) throw new PublishException("指定标识的对象不存在。");
		if (!(obj instanceof DynamicAttributeSupport))
			throw new PublishException("对象不支持扩展属性。");
		setTemplateVariable("object", obj);
		
		
	}
}
