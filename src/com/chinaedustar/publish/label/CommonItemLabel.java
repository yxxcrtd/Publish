package com.chinaedustar.publish.label;

import com.chinaedustar.common.util.StringHelper;
import com.chinaedustar.publish.PublishContext;
import com.chinaedustar.publish.model.*;
import com.chinaedustar.template.core.AbstractLabelElement;
import com.chinaedustar.template.core.InternalProcessEnvironment;

/**
 * Item(Article, Soft, Photo) 的一些基本属性标签的解释器集合。
 * 
 * @author liujunxing
 * 测试页面： label_common_item.jsp
 */
public final class CommonItemLabel extends GroupLabelBase {
	/** 不需要实例化。 */
	private CommonItemLabel() {
	}

	/**
	 * 注册 LabelHandler.
	 *
	 */
	public static final void registerHandler(LabelHandlerMap map) {
		// logger.debug("CommonItemLabel 注册了其包含的一组标签解释器。");
		
		/** 通过js调用jsp页面完成hits的递增 */
		map.put("ShowHits", new ItemHitsLabelHandler());
		
		map.put("ItemClass", new ItemPropertyLabelHandler());
		map.put("Usn", new ItemPropertyLabelHandler());
		map.put("CreateTime", new ItemPropertyLabelHandler());
		map.put("LastModified", new ItemPropertyLabelHandler());
		map.put("Status", new ItemPropertyLabelHandler());
		map.put("Stars", new ItemPropertyLabelHandler());
		map.put("Top", new ItemPropertyLabelHandler());
		map.put("Commend", new ItemPropertyLabelHandler());
		map.put("Elite", new ItemPropertyLabelHandler());
		map.put("Hot", new ItemPropertyLabelHandler());
		map.put("Deleted", new ItemPropertyLabelHandler());
		map.put("Hits", new ItemPropertyLabelHandler());
		map.put("TemplateId", new ItemPropertyLabelHandler());
		map.put("SkinId", new ItemPropertyLabelHandler());
		//map.put("Privilege", new ItemPropertyLabelHandler());
		//map.put("Charge", new ItemPropertyLabelHandler());
		//map.put("Custom", new ItemPropertyLabelHandler());
		//map.put("VoteFlag", new ItemPropertyLabelHandler());
		//map.put("BlogFlag", new ItemPropertyLabelHandler());
		//map.put("BbsFlag", new ItemPropertyLabelHandler());
		//map.put("CommentFlag", new ItemPropertyLabelHandler());
		//map.put("CommentCount", new ItemPropertyLabelHandler());
		
		map.put("Title", new ItemPropertyLabelHandler());
		map.put("ShortTitle", new ItemPropertyLabelHandler());
		map.put("Author", new ItemPropertyLabelHandler());
		map.put("Source", new ItemPropertyLabelHandler());
		map.put("Inputer", new ItemPropertyLabelHandler());
		map.put("Editor", new ItemPropertyLabelHandler());
		map.put("Keywords", new ItemPropertyLabelHandler());
		map.put("Description", new ItemPropertyLabelHandler());
	}
	
	/** #{ItemProperty } - 项目的一般属性的获取标签。 */
	private static final class ItemPropertyLabelHandler extends AbstractSimpleLabelHandler {
		public int handleLabel(PublishContext pub_ctxt, InternalProcessEnvironment env, AbstractLabelElement label) {
			String label_name = label.getLabelName();
			Item item = AbstractLabelHandler.getCurrentItem(env);
			String result = "";
			if (item == null) {
				result = "#{?? " + label_name + " 没有当前项目。";
				env.getOut().write(result);
				return PROCESS_DEFAULT;
			}
			
			// 得到对象中的属性
			
			Object prop_value = item.get(StringHelper.uncapFirst(label_name));
			if (prop_value == null)
				result = "";
			else if ("Top".equals(label_name))
				result = (Boolean)prop_value ? "顶" : "";
			else if ("Commend".equals(label_name))
				result = (Boolean)prop_value ? "荐" : "";
			else if ("Elite".equals(label_name))
				result = (Boolean)prop_value ? "精" : "";
			else if ("Hot".equals(label_name))
				result = (Boolean)prop_value ? "热" : "";
			else if ("Stars".equals(label_name))
				result = getStarsValue((Integer)prop_value, label);
			else if (prop_value instanceof java.util.Date) {
				result = formatDateValue((java.util.Date)prop_value, label);
			} else if ("Title".equals(label_name))
				result = StringHelper.htmlEncode((String)prop_value);
			else if (prop_value != null) {
				result = String.valueOf(prop_value);
			}
			
			env.getOut().write(result);
			return PROCESS_DEFAULT;
		}
		

		
		private String getStarsValue(Integer stars, AbstractLabelElement label) {
			String ch = label.getAttributes().safeGetStringAttribute("char", "★");
			if (ch == null || ch.length() == 0) ch = "★";
			return StringHelper.repeat(ch, stars == null ? 0 : stars.intValue());
		}
	}
	
	/**
	 * 项目的点击数标签
	 * @author dengxiaolong
	 *
	 */
	static final class ItemHitsLabelHandler extends AbstractLabelHandler {
		/*
		 * (non-Javadoc)
		 * @see com.chinaedustar.publish.label.AbstractLabelHandler#handleLabel()
		 */
		@Override protected int handleLabel() {
			Item item = getCurrentItem(env);
			
			if (item != null) {
				Channel channel = item.getChannel();
				if (channel != null) {
					out("<script type='text/javascript' src='" 
						+ channel.resolveUrl("showHits.jsp?itemId=" + item.getId()) + "'></script>");
					return PROCESS_DEFAULT;
				}
			}
			out(String.valueOf(item.getHits()));
			return PROCESS_DEFAULT;
		}
	}
}
