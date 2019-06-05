package com.chinaedustar.publish.label;

import java.util.*;
// import org.apache.log4j.Logger;
import com.chinaedustar.publish.model.*;
import com.chinaedustar.template.core.LocalContext;

/**
 * 来源对象相关标签。 
 *
 */
public final class GeneralSourceLabel {
	/** 日志记录器 */
	// private static Logger logger= Logger.getLogger(AbstractLabelHandler.class);
	
	/** 不需要实例化。 */
	private GeneralSourceLabel(){
	}
	
	/**
	 * 注册标签
	 * @param map
	 */
	public static final void registerHandler(LabelHandlerMap map){
		// logger.info("GeneralSourceLabel 注册了其包含的一组标签解释器");
		
		map.put("SourcePhoto", new SourcePhotoLabelHandler());	// 调试通过。
		map.put("SourceName", new SourceNameLabelHandler());	// 调试通过。
		map.put("SourceContacterName", new SourceContacterNameLbelHandler());	// 调试通过。
		map.put("SourceAddress", new SourceAddressLabelHandler());	// 调试通过。
		map.put("SourceTel", new SourceTelLaberHandler());	// 调试通过。
		map.put("SourceFax", new SourceFaxLabelHandler());	// 调试通过。
		map.put("SourceZipCode", new SourceZipCodeLabelHandler());	// 调试通过。
		map.put("SourceMail", new SourceMailLabelHandler());	// 调试通过。
		map.put("SourceHomePage", new SourceHomePageLabelHandler());	// 调试通过。
		map.put("SourceEmail", new SourceEmailLabelHandler());	// 调试通过。
		map.put("SourceQQ", new SourceQQLabelHandler());	// 调试通过。
		map.put("SourceType", new SourceTypeLabelHandler());	// 调试通过。
		map.put("SourceIntro", new SourceIntroLabelHandler());	// 调试通过。
		map.put("SourceList", new SourceListLabelHandler());	// 调试通过。
		
		// TODO:
		// map.put("SourceItemList", new SourceItemListLabelHandler());
	}
	
	/**
	 * Source 标签基类。
	 * @author liujunxing
	 *
	 */
	private static abstract class AbstractSourceLabel extends AbstractLabelHandler {
		@Override public int handleLabel() {
			Source source = getCurrentSource();
			if (source != null) {
				out(getSourceInfo(source));
			} else {
				out("#{" + label.getLabelName() + " 当前页面中不存在来源对象(source)}");
			}
			return PROCESS_DEFAULT;
		}
		protected abstract String getSourceInfo(Source source);
	}
	
	/** 
	 * 显示来源的图片。
	 * #{SourcePhoto} 
	 */
	static final class SourcePhotoLabelHandler extends AbstractSourceLabel{
		@Override protected String getSourceInfo(Source source) {
			return source.getPhoto();
		}
	}
	
	/**
	 * 显示来源名称
	 * #{SourceName} 
	 */
	static final class SourceNameLabelHandler extends AbstractSourceLabel{
		@Override protected String getSourceInfo(Source source) {
			return source.getName();
		}
	}
	
	/** 
	 * #{SourceContacterName} 
	 */
	static final class SourceContacterNameLbelHandler extends AbstractSourceLabel{
		@Override protected String getSourceInfo(Source source) {
			return source.getContacterName();
		}
	}
	
	/** 
	 * #{SourceAddress} 
	 */
	static final class SourceAddressLabelHandler extends AbstractSourceLabel{
		@Override protected String getSourceInfo(Source source) {
			return source.getAddress();
		}
	}
	
	/** 
	 * #{SourceTel} 
	 */
	static final class SourceTelLaberHandler extends AbstractSourceLabel{
		@Override protected String getSourceInfo(Source source) {
			return source.getTel();
		}
	}
	
	/** 
	 * #{SourceFax} 
	 */
	static final class SourceFaxLabelHandler extends AbstractSourceLabel{
		@Override protected String getSourceInfo(Source source) {
			return source.getFax();
		}
	}
	
	/** 
	 * #{SourceZipCode} 
	 */
	static final class SourceZipCodeLabelHandler extends AbstractSourceLabel{
		@Override protected String getSourceInfo(Source source) {
			return source.getZipCode();
		}
	}
	
	/** #{SourceMail} */
	static final class SourceMailLabelHandler extends AbstractSourceLabel{
		@Override protected String getSourceInfo(Source source) {
			return source.getMail();
		}
	}
	
	/** #{SourceHomePage} */
	static final class SourceHomePageLabelHandler extends AbstractSourceLabel{
		@Override protected String getSourceInfo(Source source) {
			return source.getHomePage();
		}
	}
	
	/** #{SourceEmail} */
	static final class SourceEmailLabelHandler extends AbstractSourceLabel{
		@Override protected String getSourceInfo(Source source) {
			return source.getEmail();
		}
	}
	
	/** #{SourceQQ} */
	static final class SourceQQLabelHandler extends AbstractSourceLabel{
		@Override protected String getSourceInfo(Source source) {
			return String.valueOf(source.getQq());
		}
	}
	
	/** 
	 * 显示来源的类型，1：友情站点；2：中文站点；3：外文站点；4：其他站点。
	 * #{SourceType} 
	 */
	static final class SourceTypeLabelHandler extends AbstractSourceLabel{
		@Override protected String getSourceInfo(Source source) {
			int type = source.getSourceType();
			String sourceType = "未知站点";
			if (type == 1) {
				sourceType = "友情站点";
			} else if (type == 2) {
				sourceType = "中文站点";
			} else if (type == 3) {
				sourceType = "外文站点";
			} else if (type == 4) {
				sourceType = "其他站点";
			}
			return sourceType;
		}
	}
	
	/**
	 * 显示来源简介
	 * #{SourceIntro}
	 */
	static final class SourceIntroLabelHandler extends AbstractSourceLabel{
		@Override protected String getSourceInfo(Source source) {
			return source.getDescription();
		}
	}
	
	/**
	 * 显示来源列表。 
	 * #{SourceList channelId="0" type="0" colNum="5" style="" showType="true"}
	 * 
	 * @param channelId 频道的标识，-1：网站中所有的来源（包括所有频道的来源）；0：当前频道中的来源，如果频道对象不存在，则为全站来源；1：特定频道的来源。默认为 0 。
	 * @param type 来源分类，0：所有分类；1 友情站点； 2 中文站点； 3 外文站点； 4 其他站点。默认为 0 。
	 * @param colNum 显示的列数，默认为 1 。
	 * @param style 显示的样式表。
	 * @param showType 是否显示作者分类，默认为 false 。
	 */
	static final class SourceListLabelHandler extends AbstractLabelHandler {
		@Override public int handleLabel() {
			int channelId = label.getAttributes().safeGetIntAttribute("channelId", 0);
			int sourceType = label.getAttributes().safeGetIntAttribute("type", 0);
			int colNum = label.getAttributes().safeGetIntAttribute("colNum", 1);
			String style = label.getAttributes().getNamedAttribute("style");
			boolean showType = label.getAttributes().safeGetBoolAttribute("showType", false);
			List<Source> list = getSourceList(channelId, sourceType);
			if (label.hasChild()) {
				// 所有内容的 HTML 代码。
				StringBuilder strBuilder = new StringBuilder("<table width='100%' border='0' cellpadding='0' cellspacing='0'>");
				// 行数。
				int rows = list.size() / colNum;
				if (list.size() % colNum != 0) {
					rows++;
				}
				int sourceId = 0;
				for (int i = 0; i < rows; i++) {
					strBuilder.append("<tr>");
					for (int j = 0; j < colNum; j++) {
						strBuilder.append("<td>");
						sourceId = i * colNum + j;
						if (sourceId < list.size()) {
							Source source = list.get(sourceId);
							// 循环处理子模板，设置子模板的局部变量。
							LocalContext localContext = env.acquireLocalContext(label, PROCESS_DEFAULT);
							localContext.setVariable("this", source);
							localContext.setVariable("source", source);
							// 解析、执行子模板
							strBuilder.append(env.processChild(label));
						}
						strBuilder.append("</td>");
					}
					strBuilder.append("</tr>");
				}
				strBuilder.append("</table>");
				env.getOut().append(strBuilder.toString());
				return PROCESS_SIBLING;
			} else {
				if (list != null && !list.isEmpty()) {
					BuiltinLabel builtLabel = getBuiltinLabel(".builtin.showsourcelist");
					Object[] args = new Object[] {super.getTableList(list, colNum), style, showType};
					return builtLabel.process(env, args);
				} else {
					return PROCESS_DEFAULT;
				}
			}
		}
		
		private List<Source> getSourceList(int channelId, int sourceType) {
			// 根据频道属性添加来源。
			Site site = pub_ctxt.getSite();
			SourceCollection source_coll = null;
			if (channelId == -1) {
				source_coll = site.getSourceCollection();
				/*
				for (Iterator<Source> iterator = site.getSourceCollection().iterator(); iterator.hasNext(); ) {
					list.add(iterator.next());
				}
				for (Iterator<Channel> channels = site.getChannels().iterator(); channels.hasNext(); ) {
					Channel channel = channels.next();
					if (channel.getStatus() == 0 && channel.getChannelType() != 2) {
						for (Iterator<Source> iterator = channel.getSourceCollection().iterator(); iterator.hasNext(); ) {
							list.add(iterator.next());
						}
					}
				}
				*/
			} else if (channelId > 0) {
				source_coll = site.getChannel(channelId).getSourceCollection();
				/*
				Channel channel = site.getChannels().getChannel(channelId);
				if (channel.getStatus() == 0 && channel.getChannelType() != 2) {
					for (Iterator<Source> iterator = channel.getSourceCollection().iterator(); iterator.hasNext(); ) {
						list.add(iterator.next());
					}
				}
				*/
			} else if (channelId == 0) {
				Channel channel = getCurrentChannel();
				source_coll = channel.getSourceCollection();
				/*
				if (channel == null) {
					for (Iterator<Source> iterator = site.getSourceCollection().iterator(); iterator.hasNext(); ) {
						list.add(iterator.next());
					}
				} else {
					for (Iterator<Source> iterator = channel.getSourceCollection().iterator(); iterator.hasNext(); ) {
						list.add(iterator.next());
					}
				}
				*/
			} else {
				out("#{SourceList 频道属性(" + channelId + ")设置不正确}");
				return null;
			}

			PaginationInfo page_info = new PaginationInfo();
			page_info.setCurrPage(1);
			page_info.setPageSize(20);
			List<Source> result = source_coll.getSourceList(sourceType, null, null, page_info);
			page_info.init();
			
			return result;
		}
	}

	/**
	 * 显示特定来源的项目（包括文章、图片、软件等）列表。
	 * #{SourceItemList } 
	 *  <br>可自定义。能嵌套使用Repeater和PageNav标签。Repeater中能使用的变量是item。
	 *  
	 * @param moduleId 模块的标识。必须提供。（可通过在标签中给出，也可以通过设置环境变量给出）。
	 * @param contentLength 内容长度。默认为100。
	 * @param usePage 是否显示分页，默认为true。
	 * @param pageSize 每页显示搜索结果的个数。默认为10。
	 * @author dengxiaolong
	 *
	 */
	/*
	static final class SourceItemListLabelHandler extends ModuleItemListHandler2 {
		private String sourceName;
		@Override public int handleLabel() {
			Source source = getCurrentSource();
			if (source == null) {
				out("#{SourceItemlList 当前页面中不存在来源对象(source)}");
				return PROCESS_DEFAULT;
			}
			
			sourceName = source.getName();
			return super.handleLabel();
		}
		
		@Override public String getSystemLabelName() {
			return ".builtin.source" + super.getItemName().toLowerCase() + "list";
		}
		
		@Override public String getWhereCause() {
			return "source=:source";
		}
		
		@Override public void initPagination(PaginationInfo pInfo) {
			//		
		}
		
		@Override public void initQuery(Query query) {
			query.setString("source", sourceName);
		}
	} */
}
