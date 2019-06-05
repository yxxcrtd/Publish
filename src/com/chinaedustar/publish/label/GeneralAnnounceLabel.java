package com.chinaedustar.publish.label;

import java.util.HashMap;
import java.util.List;
import com.chinaedustar.publish.PublishContext;
import com.chinaedustar.publish.itfc.LabelHandler2;
import com.chinaedustar.publish.model.*;
import com.chinaedustar.template.core.AbstractLabelElement;
import com.chinaedustar.template.core.AttributeCollection;
import com.chinaedustar.template.core.InternalProcessEnvironment;
import com.chinaedustar.template.core.LocalContext;

/**
 * 实现公告有关的标签。
 * 
 * @author liujunxing
 *
 */
public class GeneralAnnounceLabel extends GroupLabelBase {
	/** 不需要实例化。 */
	private GeneralAnnounceLabel() {
	}

	/**
	 * 注册 LabelHandler.
	 *
	 */
	public static final void registerHandler(LabelHandlerMap map) {
		// logger.debug("AnnounceLabel 注册了其包含的一组标签解释器。");
		
		map.put("AnnounceTitle", new AnnouncePropertyHandler());
		map.put("AnnounceContent", new AnnouncePropertyHandler());
		map.put("AnnounceAuthor", new AnnouncePropertyHandler());
		map.put("AnnounceCreateDate", new AnnouncePropertyHandler());
		map.put("AnnounceOffDate", new AnnouncePropertyHandler());
		map.put("AnnounceIsSelected", new AnnouncePropertyHandler());
		map.put("AnnounceChannelId", new AnnouncePropertyHandler());
		map.put("AnnounceShowType", new AnnouncePropertyHandler());
		map.put("AnnounceOutTime", new AnnouncePropertyHandler());
		
		map.put("ShowAnnounce", new ShowAnnounceHandler());
		map.put("ShowAnnounceList", new ShowAnnounceListHandler());
	}

	/** 
	 * 显示公告指定属性的标签处理器。
	 * #{AnnounceXxxProperty } 
	 */
	static final class AnnouncePropertyHandler extends AbstractSimpleLabelHandler {
		/*
		 * (non-Javadoc)
		 * @see com.chinaedustar.publish.itfc.LabelHandler#handleLabel(com.chinaedustar.publish.PublishContext, com.chinaedustar.template.core.InternalProcessEnvironment, com.chinaedustar.template.core.AbstractLabelElement)
		 */
		public int handleLabel(PublishContext pub_ctxt, InternalProcessEnvironment env, AbstractLabelElement label) {
			String label_name = label.getLabelName();
			Announcement announce = AbstractLabelHandler.getCurrentAnnounce(env);
			String result = "";
			if (announce == null)
				result = "#{?? " + label_name + " 没有找到当前公告}";
			else if ("AnnounceTitle".equals(label_name))
				result = announce.getTitle();
			else if ("AnnounceContent".equals(label_name))
				result = announce.getContent();
			else if ("AnnounceAuthor".equals(label_name))
				result = announce.getAuthor();
			else if ("AnnounceCreateDate".equals(label_name))
				result = formatDateValue(announce.getCreateDate(), label);
			else if ("AnnounceOffDate".equals(label_name))
				result = formatDateValue(announce.getOffDate(), label);
			else if ("AnnounceIsSelected".equals(label_name))
				result = String.valueOf(announce.getIsSelected());
			else if ("AnnounceChannelId".equals(label_name))
				result = String.valueOf(announce.getChannelId());
			else if ("AnnounceShowType".equals(label_name))
				result = String.valueOf(announce.getShowType());
			else if ("AnnounceOutTime".equals(label_name))
				result = String.valueOf(announce.getOutTime());

			if (result == null) result = "";
			env.getOut().write(result);
			return PROCESS_DEFAULT;
		}
	}

	/**
	 * 显示公告栏。
	 *  #{ShowAnnounce style num showAuthor showDate ?? maxChar} 
	 *  @param channelId - 缺省为当前频道。公告所在频道的标识。-1：频道公用公告；0：网站首页公告；>0：频道公告
	 *  @param style - 显示方式 1：横向；2：纵向。默认值为1。
	 *  @param num - 最多显示多少条公告 默认为5。
	 *  @param showAuthor - 是否显示公告的作者，默认为true。
	 *  @param showDate - 是否显示公告发布的日期。默认为true。
	 *  @param contentLen - 公告内容最多字符数。默认为50。
	 */
	static final class ShowAnnounceHandler extends AbstractLabelHandler implements LabelHandler2 {
		private static final String DEFAULT_TEMPLATE = ".builtin.showannounce";
		/*
		 * (non-Javadoc)
		 * @see com.chinaedustar.publish.label.AbstractLabelHandler#handleLabel()
		 */
		@Override public int handleLabel() {
			// 准备选项。
			AttributeCollection coll = label.getAttributes();
			int channelId = super.getChannelAttrib().channelId;
			int style = coll.safeGetIntAttribute("style", 1);
			int num = coll.safeGetIntAttribute("num", 5);
			if (num < 0) num = 0;
			boolean showAuthor = coll.safeGetBoolAttribute("showAuthor", true);
			boolean showDate = coll.safeGetBoolAttribute("showDate", true);
			int contentLen = coll.safeGetIntAttribute("contentLen", 50);
			
			java.util.Map<String, Object> options = coll.attrToOptions();
			options.put("style", style);
			options.put("num", num);
			options.put("showAuthor", showAuthor);
			options.put("showDate", showDate);
			options.put("contentLen", contentLen);

			// 获得数据。
			Object announce_list = getData(channelId, num);
			
			// 合并执行模板。
			if (label.hasChild()) {
				LocalContext local_ctxt = env.acquireLocalContext(label, 0);
				local_ctxt.setVariable("announce_list", announce_list);
				local_ctxt.setVariable("options", options);
				addRepeaterSupport(local_ctxt, "announce", announce_list);
				return PROCESS_DEFAULT;
			} else {
				String template_name = super.getTemplateName(DEFAULT_TEMPLATE);
				BuiltinLabel builtin_label = getBuiltinLabel(template_name);
				if (builtin_label == null) return super.unexistBuiltinLabel(template_name);
				
				Object[] args = new Object[] {announce_list, options};
				return builtin_label.process(env, args);
			}
		}
		
		private List<Announcement> getData(int channelId, int num) {
			AnnouncementCollection ann_coll = pub_ctxt.getSite().getAnnouncementCollection();
			List<Announcement> list = ann_coll.getAnnounceList(channelId, num);
			return list;
		}
		
		/*
		 * (non-Javadoc)
		 * @see com.chinaedustar.publish.itfc.LabelHandler2#getBuiltinName()
		 */
		public String getBuiltinName() {
			return DEFAULT_TEMPLATE;
		}
	}
	
	/**
	 * 显示公告列表。
	 * <pre>
	 * #{ShowAnnounceList titleCharNum='10' createDate='1' channelId='-1'}
	 *   &lt;a href='#{announcement.linkUrl}'&gt;#{announcement.title}&lt;/a&gt;
	 * #{/ShowAnnounceList}
	 * </pre>
	 * 
	 * @param channelId - 频道ID， 0 表示全站公告，-1表示所有公告。默认：-1
	 * @param titleCharNum - 显示文字字数，0表示不限制。默认：0
	 * @param createDate - 显示日期格式
	 */
	static final class ShowAnnounceListHandler extends AbstractLabelHandler implements LabelHandler2 {
		public static final String DEFAULT_TEMPLATE = ".builtin.showannouncelist";
		/*
		 * (non-Javadoc)
		 * @see com.chinaedustar.publish.label.AbstractLabelHandler#handleLabel()
		 */
		@Override protected int handleLabel() {
			// 获得参数
			AttributeCollection coll = label.getAttributes();
			java.util.Map<String, Object> options = new HashMap<String, Object>();
			options = coll.attrToOptions();
			options.put("titleCharNum", coll.safeGetIntAttribute("titleCharNum", 0));
			options.put("createDate", coll.safeGetStringAttribute("createDate", "yyyy-MM-dd hh:mm"));
			
			//获得数据
			int channelId = coll.safeGetIntAttribute("channelId", -1);
			int num = coll.safeGetIntAttribute("num", -1);
			options.put("num", num);

			AnnouncementCollection ann_coll = pub_ctxt.getSite().getAnnouncementCollection();
			List<Announcement> announce_list = ann_coll.getAnnounceList(channelId, num);
			
			if (label.hasChild()) {
				// 建立环境让子标签输出。
				LocalContext local_ctxt = env.acquireLocalContext(label, 0);
				local_ctxt.setVariable("announce_list", announce_list);
				local_ctxt.setVariable("options", options);
				super.addRepeaterSupport(local_ctxt, "announce", announce_list);
				return PROCESS_DEFAULT;
			} else {
				// 使用模板输出。
				String template_name = super.getTemplateName(DEFAULT_TEMPLATE);
				BuiltinLabel builtLabel = getBuiltinLabel(template_name);
				if (builtLabel == null) return super.unexistBuiltinLabel(template_name);
				
				return builtLabel.process(env, new Object[] {announce_list, options});
			}
		}

		public String getBuiltinName() {
			return DEFAULT_TEMPLATE;
		}
	}
}
