package com.chinaedustar.publish.impl;

import com.chinaedustar.publish.PublishContext;
import com.chinaedustar.publish.model.UrlResolver;
import com.chinaedustar.template.BuiltinFunction;
import com.chinaedustar.template.core.DefaultProcessEnvironment;
import com.chinaedustar.template.core.InternalProcessEnvironment;

/**
 * JsptagTemplateProcessEnvironment 和 UserPageProcessEnvironment 的共同基类。
 * @author liujunxing
 *
 */
abstract class TemplateProcessBase extends DefaultProcessEnvironment {
	protected PublishContext pub_ctxt;
	
	/**
	 * 缺省构造函数。
	 *
	 */
	protected TemplateProcessBase (){
		
	}
	
	/**
	 * 复制构造函数。
	 * @param parent
	 * @param para
	 */
	protected TemplateProcessBase(TemplateProcessBase parent) {
		super(parent);
	}

	/*
	 * (non-Javadoc)
	 * @see com.chinaedustar.template.core.DefaultProcessEnvironment#lookupBuiltin(java.lang.String)
	 */
	@Override protected BuiltinFunction lookupBuiltin(String method_name) {
		if ("uri".equals(method_name))
			return new UriBuiltinImplement();
		return super.lookupBuiltin(method_name);
	}
	
	/**
	 * 实现对 string@uri 扩展内建函数的解释，其使用当前站点地址做为基地址。
	 * @author liujunxing
	 */
	private final class UriBuiltinImplement implements BuiltinFunction {
		/*
		 * (non-Javadoc)
		 * @see com.chinaedustar.template.BuiltinFunction#exec(com.chinaedustar.template.core.InternalProcessEnvironment, java.lang.Object, java.lang.Object[])
		 */
		public Object exec(InternalProcessEnvironment env, Object target, Object[] args) {
			if (target == null) return "";
			if (!(target instanceof String)) return null;
			
			// 缺省使用 Site 做为 UrlResolver
			UrlResolver resolver = pub_ctxt.getSite();
			if (args != null && args.length > 0 && args[0] != null && args[0] instanceof UrlResolver) {
				resolver = (UrlResolver)args[0];
			}

			return resolver.resolveUrl((String)target);
		}
	}
}
