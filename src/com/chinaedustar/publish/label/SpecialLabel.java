package com.chinaedustar.publish.label;

import java.util.List;

import com.chinaedustar.publish.PublishContext;
import com.chinaedustar.publish.comp.OpenType;
import com.chinaedustar.publish.model.*;
import com.chinaedustar.template.core.AbstractLabelElement;
import com.chinaedustar.template.core.AttributeCollection;
import com.chinaedustar.template.core.InternalProcessEnvironment;
import com.chinaedustar.template.core.LocalContext;

/**
 * 专题相关的标签。
 * 
 * @author liujunxing
 */
public final class SpecialLabel extends GroupLabelBase {
	/** 不需要实例化。 */
	private SpecialLabel() {
	}
	
	/** 注册标签处理器。 */
	public static final void registerHandler(LabelHandlerMap map) {
		// logger.info("registerHandler() batch set LabelHandler.");
		
		map.put("SpecialID", new SpecialPropertyHandler());
		map.put("SpecialName", new SpecialPropertyHandler());
		map.put("SpecialPicUrl", new SpecialPropertyHandler());
		// special的其他属性
		map.put("ShowSpecialList", new ShowSpecialListHandler());
	}
	
	/**
	 * 专题属性处理器
	 * 
	 * @label #{SpeicalName} - 显示当前专题的名称
	 * @label #{SpecialPicUrl} - 显示专题的图片地址
	 */
	static final class SpecialPropertyHandler extends AbstractSimpleLabelHandler {
		public int handleLabel(PublishContext pub_ctxt, InternalProcessEnvironment env, AbstractLabelElement label) {
			String label_name = label.getLabelName();
			SpecialWrapper special = AbstractLabelHandler.getCurrentSpecial(env);
			String result = "";
			if (special == null) {
				result = "#{?? " + label_name + " 没有当前专题}";
			} else if ("SpecialID".equals(label_name))
				result = String.valueOf(special.getId());
			else if ("SpecialName".equals(label_name))
				result = special.getName();
			else if ("SpecialPicUrl".equals(label_name))
				result = special.getSpecialPicUrl();
			
			env.getOut().write(result);
			return PROCESS_DEFAULT;
		}
	}
	
	/**
	 * 显示专题列表的循环自定义标签。
	 * #{ShowSpecialList channel="0" style="default_css" isHorizontal="" }
	 * 
	 * @label_param channel - 频道的标识.
	 *    -3,all: 所有专题。
	 *    -2: 全站和本频道的专题。
	 *    -1,site：全站专题。
	 *    0,current：(默认)当前频道的专题（如果当前频道不存在，则表示为网站）
	 *    > 0：指定的某个频道中的专题。
	 * @label_param isElite - 是否推荐：不指定或为空表示全部；true：推荐的专题；false：未推荐的专题。
	 * @label_param itemNum - 获取的专题数目，= 0 表示获取全部, 缺省 = 8。
	 * (如下四个选项当前不支持)
	 * @label_param imageNo 专题前显示的图片。0：不显示；1：显示字符；2：显示小图：/images/special*.gif。
	 * @label_param openType 打开方式。0：本窗口；1：新窗口。
	 * @label_param cols 每行显示的列数。默认为1。
	 * @label_param style 显示样式，CSS， class="default_css" 。 
	 * 
	 * @example
	 * <pre>
	 * #{ShowSpecialList channel="0"}
	 *   #{Repeater}
	 *   里面可以显示出专题对象中的属性。
	 *   #{/Repeater}
	 *   or #{foreach special in special_list}...#{/foreach}
	 * #{/ShowSpecialList}
	 * </pre>
	 * 
	 * @author wangyi
	 *
	 */
	static final class ShowSpecialListHandler extends AbstractLabelHandler2 {
		private static final String DEFAULT_TEMPLATE = ".builtin.showspeciallist";
		/** 专题查询选项 */
		private SpecialCollection.SpecialQueryOption query_option = new SpecialCollection.SpecialQueryOption();
		/** 提交给模板的选项 */
		private java.util.Map<String, Object> options;
		private Object special_list;	// 数据。 
		@Override public int handleLabel() {
			makeOptions();
			
			special_list = getData();
			
			return process();
		}
		// 获得频道标识参数。
		private void getChannelParam() {
			// 获得参数中指定的 channel 标识。
			String channel_string = label.getAttributes().getNamedAttribute("channel");
			int channelId = label.getAttributes().safeGetIntAttribute("channel", 0);
			if ("current".equals(channel_string))	// 当前专题
				channelId = 0;
			else if ("all".equals(channel_string))
				channelId = -3;
			else if ("site".equals(channel_string))	// 全站专题
				channelId = -1;

			Channel current_channel = getCurrentChannel();
			if (channelId == 0 && current_channel != null)
				channelId = current_channel.getId();
			options.put("channelId", channelId);
			
			// 设置到 query_option 中。
			if (channelId == -3)		// 所有专题。
				query_option.channel_ids = null;
			else if (channelId == -2) {	// 全站和本频道的专题。
				query_option.channel_ids.add(0);	// 全站。
				if (current_channel != null)
					query_option.channel_ids.add(current_channel.getId());
			} else if (channelId == -1) {	// 全站专题。
				query_option.channel_ids.add(0);
			} else if (channelId == 0) {	// 当前频道专题，如果没有当前频道，则为全站。
				query_option.channel_ids.add(current_channel == null ? 0 : current_channel.getId());
			} else {						// 指定频道专题。
				query_option.channel_ids.add(channelId);
			}
		}
		private final void makeOptions() {
			// 获得参数
			AttributeCollection attrs = label.getAttributes();
			options = attrs.attrToOptions();
			
			getChannelParam();
			
			query_option.isElite = attrs.safeGetBoolAttribute("isElite", null);
			options.put("isElite", query_option.isElite);
			
			query_option.itemNum = attrs.safeGetIntAttribute("itemNum", 8);
			options.put("itemNum", query_option.itemNum);
			
			int imageNo = attrs.safeGetIntAttribute("imageNo", 1);
			options.put("imageNo", imageNo);
			
			OpenType openType = OpenType.fromString(attrs.safeGetStringAttribute("openType", null));
			options.put("openType", openType);
			
			int cols = attrs.safeGetIntAttribute("cols", 1);	
			options.put("cols", cols);
			
			String style = attrs.safeGetStringAttribute("style", "");
			options.put("style", style);
			
			options.put("debug", attrs.safeGetBoolAttribute("debug", false));
		}
		
		// 获取数据。
		private final List<SpecialWrapper> getData() {
			SpecialCollection spec_coll = pub_ctxt.getSite().getSpecialCollection();
			List<SpecialWrapper> list = spec_coll.getSpecialList(this.query_option);
			return list;
		}
	
		private final int process() {
			if (label.hasChild()) {
				// 如果有子节点，则让子节点自己进行处理。
				LocalContext local_ctxt = env.acquireLocalContext(label, 0);
				local_ctxt.setVariable("special_list", special_list);
				super.addRepeaterSupport(local_ctxt, "special", special_list);
				return PROCESS_DEFAULT;
			} 

			// 否则使用指定模板进行处理。
			String template_name = super.getTemplateName(DEFAULT_TEMPLATE);
			BuiltinLabel builtin_label = getBuiltinLabel(template_name);
			if (builtin_label == null) return super.unexistBuiltinLabel(template_name);
			
			// 合成。
			Object[] args = new Object[] {special_list, options};
			return builtin_label.process(env, args);
		}
		/*
		 * (non-Javadoc)
		 * @see com.chinaedustar.publish.label.AbstractLabelHandler2#getBuiltinName()
		 */
		@Override public String getBuiltinName() {
			return DEFAULT_TEMPLATE;
		}
	}
	
}
