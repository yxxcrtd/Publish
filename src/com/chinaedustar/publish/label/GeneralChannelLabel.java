package com.chinaedustar.publish.label;

import java.util.*;
import com.chinaedustar.publish.*;
import com.chinaedustar.publish.comp.OpenType;
import com.chinaedustar.publish.model.*;
import com.chinaedustar.template.core.AbstractLabelElement;
import com.chinaedustar.template.core.AttributeCollection;
import com.chinaedustar.template.core.InternalProcessEnvironment;
import com.chinaedustar.template.core.LocalContext;

/**
 * 频道标签。
 * 
 * @author liujunxing
 *
 */
public class GeneralChannelLabel extends GroupLabelBase {
	/** 不需要实例化。 */
	private GeneralChannelLabel() {
	}
	
	/**
	 * 注册 LabelHandler.
	 *
	 */
	public static final void registerHandler(LabelHandlerMap map) {
		// logger.debug("GeneralChannelLabel 注册了其包含的一组标签解释器。");
		
		map.put("Channel", new ChannelLabelHandler());
		map.put("ChannelName", new ChannelNameHandler());
		map.put("ChannelDir", new ChannelPropertyHandler());
		
		map.put("ChannelID", new ChannelPropertyHandler());
		map.put("ChannelUrl", new ChannelUrlHandler());
		map.put("UploadDir", new ChannelPropertyHandler());
		map.put("ChannelPicUrl", new ChannelPropertyHandler());
		map.put("ChannelItemName", new ChannelPropertyHandler());
		map.put("ChannelItemUnit", new ChannelPropertyHandler());
		
		map.put("Meta_Keywords_Channel", new MetaKeywordsChannelHandler());
		map.put("Meta_Description_Channel", new MetaDescriptionChannelHandler());
		
		map.put("ShowChannelCount", new ShowChannelCountLabelHandler());	// 调试通过。
//		map.put("ShowColumnList", new ShowColumnListHandler());
		map.put("ShowColumnMenu", new ShowColumnMenuHandler());
		map.put("ShowColumnGuide", new ShowColumnGuideHandler());
	}

	/**
	 * 提供频道通用属性的处理器。
	 * @label #{ChannelID} - 显示频道的标识。
	 * @label #{ChannelDir} - 显示当前频道的目录名称
	 * @label #{UploadDir} - 显示当前频道的上传目录名称
	 * @label #{ChannelPicUrl} - 显示频道图片URL
	 * 
	 * @label #{ChannelItemName} - 显示频道的项目名字。
	 * @author liujunxing
	 *
	 */
	static final class ChannelPropertyHandler extends AbstractSimpleLabelHandler {
		/*
		 * (non-Javadoc)
		 * @see com.chinaedustar.publish.itfc.LabelHandler#handleLabel(com.chinaedustar.publish.PublishContext, com.chinaedustar.template.core.InternalProcessEnvironment, com.chinaedustar.template.core.AbstractLabelElement)
		 */
		public int handleLabel(PublishContext pub_ctxt, InternalProcessEnvironment env, AbstractLabelElement label) {
			String label_name = label.getLabelName();
			Channel channel = AbstractLabelHandler.getCurrentChannel(env);
			if (channel == null) {
				env.getOut().write("#{?? " + label_name + " 标签没有频道对象}");
				return PROCESS_DEFAULT;
			}
			String result = "";
			if ("ChannelID".equals(label_name))
				result = String.valueOf(channel.getId());
			else if ("ChannelDir".equals(label_name))
				result = channel.getChannelDir();
			else if ("UploadDir".equals(label_name))
				result = channel.getUploadDir();
			else if ("ChannelPicUrl".equals(label_name))
				result = channel.getChannelPicUrl();
			else if ("ChannelItemName".equals(label_name))
				result = channel.getItemName();
			else if ("ChannelItemUnit".equals(label_name))
				result = channel.getItemUnit();
			
			env.getOut().write(result);
			return PROCESS_DEFAULT;
		}
	}
	
	/**
	 * 获取一组频道的数据。
	 * @param id 频道的标识。0：表示当前的频道；>0：对应标识的频道。默认为""，表示取当前的频道。
	 * 示例：#{Channel id='1,11'}<div><a href=\"#{channel.pageUrl}\" target=\"#{iif(channel.openType==1,'_blank','_self')}\">#{channel.name}</a></div>#{/Channel}
	 * 输出结果：<div><a href="/PubWeb/news/index.html" target="_self">新闻中心</a></div><div><a href="/PubWeb/photo/index.jsp" target="_self">图片中心</a></div>
	 * 如果只用#{Channel}，将默认输出对应频道的名称。如果当前没有频道，则输出
	 */
	static final class ChannelLabelHandler extends AbstractLabelHandler {
		/*
		 * (non-Javadoc)
		 * @see com.chinaedustar.publish.label.AbstractLabelHandler#handleLabel()
		 */
		@Override protected int handleLabel() {
			if (label.hasChild() == false) return PROCESS_SIBLING;
			// 获取数据
			List<Channel> list = getData();
			
			// 显示。
			if (list.isEmpty()) return PROCESS_SIBLING;
			env.foreach(label, "channel", list, RepeatNameSetter.Instance);
			return PROCESS_SIBLING;
		}
		private final List<Channel> getData() {
			String str_id = label.getAttributes().safeGetStringAttribute("id", "");
			List<Channel> list = new ArrayList<Channel>();
			if (str_id.equals("") || str_id.trim().length() < 1) {
				Channel channel = getCurrentChannel();	// 取得当前的频道
				if (channel != null) {
					list.add(channel);
				}
			} else {
				String[] str_ids = str_id.split(",");  
				ChannelCollection cc = pub_ctxt.getSite().getChannels();
				for (int i = 0; i < str_ids.length; i++) {
					try {
						int channelId = Integer.parseInt(str_ids[i].trim()); // 过滤掉多于的空格
						Channel channel = cc.getChannel(channelId);
						if (channel != null) {
							list.add(channel);
						}
					} catch (Exception e) {
						//
					}
				}
			}
			return list;
		}
	}
	
	/**
	 * 显示当前频道名称.
	 * #{ChannelName default="" id=""}
	 * @param default - 如果当前频道为空，所显示的默认频道名称
	 * @param id - 频道标识；缺省为 0 表示当前频道。
	 */
	static final class ChannelNameHandler extends AbstractLabelHandler {
		/*
		 * (non-Javadoc)
		 * @see com.chinaedustar.publish.label.AbstractLabelHandler#handleLabel()
		 */
		@Override protected int handleLabel() {
			// 如果没有给出 channel_id ，则获得当前 channel；否则获得指定频道。
			Channel channel = null;
			int channel_id = label.getAttributes().safeGetIntAttribute("id", 0);
			if (channel_id == 0)
				channel = getCurrentChannel();
			else
				channel = pub_ctxt.getSite().getChannels().getChannel(channel_id);
			
			if (channel == null) {
				String no_channel_str = label.getAttributes().getNamedAttribute("default");
				if (no_channel_str == null) 
					no_channel_str = "{?? #ChannelName 没有当前频道}";
				out(no_channel_str);
			} else {
				out(channel.getName());
			}
			
			return PROCESS_DEFAULT;
		}
	}
	
	/**
	 * 显示当前频道或指定标识频道的链接地址。
	 * #{ChannelUrl id='1'}, id 缺省 = 0 表示当前频道。 
	 */
	static final class ChannelUrlHandler extends AbstractLabelHandler {
		/*
		 * (non-Javadoc)
		 * @see com.chinaedustar.publish.label.AbstractLabelHandler#handleLabel()
		 */
		@Override protected int handleLabel() {
			// 如果没有给出 channel_id ，则获得当前 channel；否则获得指定频道。
			Channel channel = null;
			int channel_id = label.getAttributes().safeGetIntAttribute("id", 0);
			if (channel_id == 0)
				channel = getCurrentChannel();
			else
				channel = pub_ctxt.getSite().getChannel(channel_id);

			String output = "";
			if (channel == null) {
				output = "#{?? ChannelUrl 没有频道}";
			} else {
				output = channel.getPageUrl();
			}
			out(output);
			return PROCESS_DEFAULT;
		}
	}

	/**
	 * 显示频道的 MetaKeywords。
	 * #{Meta_Keywords_Channel }
	 */
	static final class MetaKeywordsChannelHandler extends AbstractLabelHandler {
		/*
		 * (non-Javadoc)
		 * @see com.chinaedustar.publish.label.AbstractLabelHandler#handleLabel()
		 */
		@Override protected int handleLabel() {
			Channel channel = getCurrentChannel();
			String output = getOutput(channel);
			out( output);
			return PROCESS_DEFAULT;
		}
		
		private static String getOutput(Channel channel) {
			if (channel == null) return "";
			StringBuffer strbuf = new StringBuffer();
			// 输出为: <meta name="keywords" content="the keywords..." />
			strbuf.append("<meta name=\"keywords\" content=\"")
				.append(PublishUtil.HtmlEncode(channel.getMetaKey()))
				.append("\" />");
			return strbuf.toString();
		}
	}

	/**
	 * 显示频道的 Meta_Description。
	 * #{Meta_Description_Channel }
	 */
	static final class MetaDescriptionChannelHandler extends AbstractLabelHandler {
		/*
		 * (non-Javadoc)
		 * @see com.chinaedustar.publish.label.AbstractLabelHandler#handleLabel()
		 */
		@Override protected int handleLabel() {
			Channel channel = getCurrentChannel();
			String output = getOutput(channel);
			out( output);
			return PROCESS_DEFAULT;
		}
		
		private static String getOutput(Channel channel) {
			if (channel == null) return "";
			StringBuffer strbuf = new StringBuffer();
			// 输出为: <meta name="description" content="the descriptions..." />
			strbuf.append("<meta name=\"description\" content=\"")
				.append(PublishUtil.HtmlEncode(channel.getMetaDesc()))
				.append("\" />");
			return strbuf.toString();
		}
	}

	/**
	 * 显示频道的栏目导航条
	 * #{ShowColumnMenu }
	 * 引入一个自动生成的JS，达到显示层级菜单的效果.
	 */
	static final class ShowColumnMenuHandler extends AbstractLabelHandler {
		/*
		 * (non-Javadoc)
		 * @see com.chinaedustar.publish.label.AbstractLabelHandler#handleLabel()
		 */
		@Override protected int handleLabel() {
			Channel channel = getCurrentChannel();
			if (channel == null) {
				out("{?? #ShowColumnMenu 没有当前频道}");
			} else {
				String jsFilePath = channel.getColumnTree().getColumnMenuJsUrl();
				out("<script src='" + pub_ctxt.getSite().resolveUrl("js/stm31.js") + "' type='text/javascript'></script>\r\n"
						+ "<script src=\"" + jsFilePath + "\" type='text/javascript'></script>\r\n");
			}
			return PROCESS_DEFAULT;
		}
	}
	
	/**
	 * 显示此频道的栏目地图。
	 * #{ShowColumnGuide(channelId,openType,cols) }
	 * @param channelId - 频道标识，默认为当前栏目；
	 * @param openType - 打开方式。0：当前窗口，1：新窗口，默认为当前窗口；
	 * @param cols - 每行显示多少个子栏目。
	 * @param template - 所使用的模板名字。
	 * 
	 * 返回 column_list，为 mainColumn 和 subColumns 两个键值的Map所构成的List。
	 * 其中mainColumn为第一级栏目，subColumns为其对应的第二级栏目构成的List。
	 * 返回数据描述为：
	 * data = List<Map>
	 *   Map =
	 *     mainColumn = Column
	 *     subColumns = List<Column>
	 */
	static final class ShowColumnGuideHandler extends AbstractLabelHandler2 {
		private static final String DEFAULT_TEMPLATE = ".builtin.showcolumnguide";
		
		private Channel channel;
		private Map<String, Object> options;
		/*
		 * (non-Javadoc)
		 * @see com.chinaedustar.publish.label.AbstractLabelHandler#handleLabel()
		 */
		@SuppressWarnings("rawtypes")
		@Override protected int handleLabel() {
			// 获取选项参数。
			makeOptions();
			
			// 获取数据。
			List column_list = getData();
			if (column_list == null) return PROCESS_DEFAULT;
			
			// 合成显示。
			if (label.hasChild()) {
				LocalContext local_ctxt = env.acquireLocalContext(label, 0);
				local_ctxt.setVariable("column_list", column_list);
				local_ctxt.setVariable("options", options);
				super.addRepeaterSupport(local_ctxt, "column", column_list);
				return PROCESS_DEFAULT;
			} else {
				String template_name = super.getTemplateName(DEFAULT_TEMPLATE);
				BuiltinLabel builtin_label = getBuiltinLabel(template_name);
				if (builtin_label == null) return super.unexistBuiltinLabel(template_name);
					
				// 显示有关的选项。
				return builtin_label.process(env, new Object[] {column_list, options} );
			}
		}
		@Override public String getBuiltinName() {
			return DEFAULT_TEMPLATE;
		}
		// 获取数据的选项。
		private void makeOptions() {
			AttributeCollection coll = label.getAttributes();
			int channelId = coll.safeGetIntAttribute("channelId", 0);
			
			// 得到频道。
			if (channelId > 0)
				this.channel = pub_ctxt.getSite().getChannel(channelId);
			else
				this.channel = getCurrentChannel();
			if (channel == null) channelId = 0;
			options = coll.attrToOptions();
			
			options.put("channelId", channelId);
			OpenType openType = OpenType.fromString(coll.safeGetStringAttribute("openType", null));
			options.put("openType", openType);
			
			int cols = coll.safeGetIntAttribute("cols", 5);
			options.put("cols", cols);
		}
		// 获取数据。
		private List<Column> getData() {
			if (channel == null) return null;
			// java.util.List<Map> list = new java.util.ArrayList<Map>();
			ColumnTree tree = channel.getColumnTree();
			int rootColumnId = channel.getRootColumnId();
			// 第一级 column_list
			List<Column> first_level_cl = tree.getChildColumnList(rootColumnId);
			if (first_level_cl == null || first_level_cl.size() == 0)
				return first_level_cl;
			
			for (int i = 0; i < first_level_cl.size(); i++) {
				Column column = first_level_cl.get(i);
				List<Column> child_columns = tree.getChildColumnList(column.getId());
				column.setChildColumns(child_columns);
			}
			
			return first_level_cl;
		}
	}
	
	/** 
	 * 统计频道信息：内容项总数，待审核内容项。
	 * #{ShowChannelCount} 
	 */
	static final class ShowChannelCountLabelHandler extends AbstractLabelHandler {
		/*
		 * (non-Javadoc)
		 * @see com.chinaedustar.publish.label.AbstractLabelHandler#handleLabel()
		 */
		@Override protected int handleLabel() {
			Channel channel = getCurrentChannel();
			if (channel == null) {
				out("#{?? ShowChannelCount 当前页面中不存在频道对象(channel) } ");
				return PROCESS_DEFAULT;
			}
			
			// 获得统计数据。
			ChannelStatistics stat = channel.getStatisticsData();

			if (label.hasChild()) {
				LocalContext local_ctxt = env.acquireLocalContext(label, 0);
				local_ctxt.setVariable("stat", stat);
				local_ctxt.setVariable("channel_stat", stat);
				
				// -- 当前 template 执行有一个 BUG，返回 PROCESS_DEFAULT 会消除掉上面设置的局部
				//  变量，所以暂时先主动执行子节点。以后修正了 template 包之后改过来这里。
				// out(env.processChild(label));
				// return PROCESS_SIBLING;
				return PROCESS_DEFAULT;
			} else {
				// 获得数据,文章总数（审核+未审核）、 待审核文章数目
				int article_count = stat.getItemNum();
				int unappr_count = stat.getUnapprNum();
				
				String outString = channel.getItemName() + "总数：" + article_count + channel.getItemUnit() 
					+ "<br/>待审" + channel.getItemName() + "：" + unappr_count + channel.getItemUnit();
				out(outString);
			}
			
			return PROCESS_DEFAULT;
		}
	}
}
