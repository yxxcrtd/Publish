package com.chinaedustar.publish.label;

import java.util.*;
import com.chinaedustar.publish.*;
import com.chinaedustar.publish.comp.OpenType;
import com.chinaedustar.publish.itfc.ShowPathSupport;
import com.chinaedustar.publish.model.*;
import com.chinaedustar.template.core.AbstractLabelElement;
import com.chinaedustar.template.core.InternalProcessEnvironment;
import com.chinaedustar.template.core.LocalContext;

/**
 * 站点的标签集合。
 * 
 * @author liujunxing
 */
public final class GeneralSiteLabel extends GroupLabelBase {
    /** 不需要实例化。 */
    private GeneralSiteLabel() {
    }
    
	/**
	 * 注册 LabelHandler.
	 *
	 */
	public static final void registerHandler(LabelHandlerMap map) {
		// logger.info("registerHandler() batch set LabelHandler.");
		
		map.put("Site", new SitePropertyHandler());
		map.put("PageTitle", new PageTitleHandler());	// v
		map.put("ShowChannel", new ShowChannelHandler());
		map.put("ShowPath", new ShowPathHandler());
		map.put("ShowVote", new ShowVoteHandler());
		map.put("SiteName", new SitePropertyHandler());		// v
		map.put("SiteUrl", new SitePropertyHandler());		// v
		map.put("InstallDir", new SitePropertyHandler());	// v
		map.put("SitePageUrl", new SitePropertyHandler());

		map.put("Copyright", new CopyrightHandler());
		map.put("Meta_GB2312", new MetaGb2312Handler());
		map.put("Meta_Generator", new MetaGeneratorHandler());
		map.put("Meta_Keywords", new MetaKeywordsHandler());
		map.put("Meta_Description", new MetaDescriptionHandler());
		map.put("Webmaster", new SitePropertyHandler());
		map.put("WebmasterEmail", new SitePropertyHandler());
		map.put("Skin_CSS", new SkinCSSHandler());
		
		map.put("ShowLogo", new ShowLogoHandler());
		map.put("ShowBanner", new ShowBannerHandler());
		map.put("ShowBottom", new ShowBottomHandler());
		map.put("ShowAdminLogin", new ShowAdminLoginHandler());	// v
		map.put("ShowSiteCountAll", new ShowSiteCountAllHandler());
	}
	
	/**
	 * Site 属性标签的实现。
	 * @lable #{Site} - 返回网站名字，等同于 #{SiteName} 标签。
	 * @label #{SiteName} - 显示网站名称。 
	 * @label #{SiteUrl} - 显示网站主链接地址。 
	 * @label #{SitePageUrl} - 显示网站首页的页面地址。 
	 * @label #{InstallDir} - 显示当前网站的安装目录。
	 * @label #{Webmaster} - 显示管理员名字。  
	 * @label #{WebmasterEmail} - 显示管理员的邮件地址。 

	 * @author liujunxing
	 *
	 */
	static final class SitePropertyHandler extends AbstractSimpleLabelHandler {
		/*
		 * (non-Javadoc)
		 * @see com.chinaedustar.publish.itfc.LabelHandler#handleLabel(com.chinaedustar.publish.PublishContext, com.chinaedustar.template.core.InternalProcessEnvironment, com.chinaedustar.template.core.AbstractLabelElement)
		 */
		public int handleLabel(PublishContext pub_ctxt, InternalProcessEnvironment env, AbstractLabelElement label) {
			String label_name = label.getLabelName();
			Site site = pub_ctxt.getSite();		// site 对象总是存在。
			String result = "";
			if ("Site".equals(label_name) || "SiteName".equals(label_name))
				result = site.getName();
			else if ("SiteUrl".equals(label_name))
				result = site.getUrl();
			else if ("SitePageUrl".equals(label_name))
				result = site.getPageUrl();
			else if ("InstallDir".equals(label_name))
				result = site.getInstallDir();
			else if ("Webmaster".equals(label_name))
				result = site.getWebmaster();
			else if ("WebmasterEmail".equals(label_name))
				result = site.getWebmasterEmail();
			env.getOut().write(result);
			return PROCESS_DEFAULT;
		}
		
	}
	
	/** 
	 * 显示当前页面的标题。
	 * #{PageTitle}  
	 */
	static final class PageTitleHandler extends AbstractLabelHandler {
		@Override public int handleLabel() {
			String title = getTitle();
			out(title);
			return PROCESS_DEFAULT;
		}

		// 返回要显示的字符串。
		public String getTitle() {
			/*
			具体算法：
			  1、如果存在当前模型对象，则使用当前模型对象的标题。
			  2、使用网站Site的标题。
			*/
			// 使用当前模型对象的标题或名字。
			PublishModelObject this_ = getThis();
			while (this_ != null) {
				// TODO: 建立一个 TitleObject 接口，使得能够获得 Title.
				if (this_ instanceof Item) return ((Item)this_).getTitle();
				if (this_ instanceof Column) return ((Column)this_).getName();
				if (this_ instanceof SpecialWrapper) return ((SpecialWrapper)this_).getName();
				if (this_ instanceof Channel) return ((Channel)this_).getName();
				if (this_ instanceof Site) return ((Site)this_).getTitle();
				this_ = this_._getOwnerObject();
			}
			
			// 使用网站的名字。
			return pub_ctxt.getSite().getTitle();
		}
	}

	/**
	 * 显示页面顶部的频道导航。  
	 * #{ShowChannel}
	 * @param template - 显示使用的模板，缺省为 ".builtin.showchannel"。
	 *  
	 * 其提供给模板的数据是一个 DataTable，其 schema 为：
	 *   id - 频道的标识，= 0 表示是主页，也就是站点对象
	 *   name - 频道的名字。
	 *   openType - 打开方式，0 在原窗口打开， 1 在新窗口打开。
	 *   linkUrl - 频道的链接地址。
	 *   isCurrChannel - 是否是当前频道。
	 *   object - 频道或站点对象
	 */
	static final class ShowChannelHandler extends AbstractLabelHandler2 {
		/** 显示使用的缺省内建模板名字。 */
		private static final String DEFAULT_TEMPLATE = ".builtin.showchannel";
		@Override public int handleLabel() {
			// 准备所使用的数据。
			DataTable dt = getData();
			String template_name = getTemplateName(DEFAULT_TEMPLATE);
			
			// 取得内建 Label。
			BuiltinLabel builtin_label = getBuiltinLabel(template_name);
			if (builtin_label == null) return unexistBuiltinLabel(template_name); 
				
			// 执行这个内建标签。
			builtin_label.process(env, new Object[] {dt});
			return PROCESS_DEFAULT;
		}
		public String getBuiltinName() {
			return DEFAULT_TEMPLATE;
		}
		
		private final DataTable getData() {
			DataTable dt = new DataTable(new DataSchema(new String[]{
					"id", "name", "openType", "pageUrl", "isCurrChannel", "object"
			}));
			
			// 放置站点
			Site site = pub_ctxt.getSite();
			DataRow dr = dt.newRow();
			dr.set("id", 0);
			dr.set("name", "网站首页");
			dr.set("openType", OpenType.SELF);
			dr.set("pageUrl", site.getPageUrl());
			dr.set("isCurrChannel", (site == getThis()));
			dr.set("object", site);
			dt.add(dr);
			
			Iterator<Channel> iter = site.getChannels().iterator();
			Channel curr_channel = getCurrentChannel();
			// 放置频道
			while (iter.hasNext()) {
				Channel channel = iter.next();
				if (channel.getStatus() == 0) {
					dr = dt.newRow();
					
					dr.set("id", channel.getId());
					dr.set("openType", OpenType.fromInteger(channel.getOpenType()));
					dr.set("name", channel.getName());
					dr.set("pageUrl", channel.getPageUrl());					
					dr.set("isCurrChannel", curr_channel == null ? false : curr_channel.getId() == channel.getId());
					dr.set("object", channel);
					
					dt.add(dr);
				}
			}
			
			return dt;
		}
		/* 输出示例
		 * &nbsp;|&nbsp;
		 *   <a class='Channel' href='/PubWeb/index.jsp'>网站首页</a>&nbsp;|&nbsp;
		 *   <a class='Channel2' href='/PubWeb/Article/Index.html' target='_blank'>新闻中心</a>&nbsp;|&nbsp;
		 *   <a class='Channel'  href='/PubWeb/Soft/Index.asp' target='_blank'>下载中心</a>&nbsp;|&nbsp;
		 *   <a class='Channel'  href='/PubWeb/Photo/Index.html' target='_blank'>图片中心</a>&nbsp;|&nbsp;
		 */		
	}

	/** 
	 * 显示导航信息。
	 * #{ShowPath template=""}  
	 * <br>支持自定义循环功能。放入循环中的对象为path，包括name和url两个属性。
	 * <br>示例：#{ShowPath}>><a href="#{path.url}">#{path.name}</a>#{/ShowPath}；
	 * <br>结果可能为：>><a href='/PubWeb/index.jsp'>中教育星发布系统</a>>><a href='/PubWeb/news/index.html'>新闻中心</a>
	 * 提供的数据模型为 DataTable, schema 为：
	 *   name - 对象的名字。
	 *   url - 链接地址。
	 *   object - 对象，可能是 site, channel, special, column 等数种。
	 */
	static final class ShowPathHandler extends AbstractLabelHandler2 {
		private static final String DEFAULT_TEMPLATE = ".builtin.showpath";
		@Override public int handleLabel() {
			// 获得导航数据。
			DataTable dt = getData();
			
			if (label.hasChild()) {		// 增加自定义循环功能。
				env.foreach(label, "path", dt, RepeatNameSetter.Instance);
				return PROCESS_SIBLING;
			}
			
			// 取得内建 Label。
			String template_name = super.getTemplateName(DEFAULT_TEMPLATE);
			BuiltinLabel builtin_label = getBuiltinLabel(template_name);
			if (builtin_label == null) return unexistBuiltinLabel(template_name);
			
			// 执行这个内建标签。
			return builtin_label.process(env, new Object[]{dt});
		}
		public String getBuiltinName() {
			return DEFAULT_TEMPLATE;
		}
		/**
		 * 构造导航所需的 DataTable 数据。
		 * @return
		 */
		private final DataTable getData() {
			// 将现有对象压入栈内，并在此过程中得到频道和网站。
			Stack<ShowPathSupport> stack = new Stack<ShowPathSupport>();
			ModelObject model_obj = getThis();
			
			if (model_obj != null) {
				while (model_obj != null) {
					if (model_obj instanceof ShowPathSupport)
						stack.push((ShowPathSupport)model_obj);
					model_obj = model_obj.getParent();
				}
			}
			
			//然后再将对象出栈，逐步构建路径对象。
			DataTable dt = new DataTable(PublishUtil.columnsToSchema("name, pageUrl, target, object"));
			
			while (!stack.isEmpty()) {
				ShowPathSupport pa_obj = stack.pop();
				if (pa_obj.isShowInPath()) {
					DataRow row = dt.newRow();
					row.add(0, pa_obj.getPathTitle());
					row.add(1, pa_obj.getPathPageUrl());
					row.add(2, pa_obj.getPathTarget());
					row.add(3, pa_obj);
					dt.add(row);
				}
			}
			
			return dt;
		}
		/* 例子：
		 * 您现在的位置：&nbsp;<a class='LinkPath' href='http://www.chinaedustar.comt'>中教育星</a>&nbsp;
		 *   >>&nbsp;<a class='LinkPath' href='/PubWeb/Article/Index.html'>新闻中心</a>&nbsp;
		 *   >>&nbsp;首页 。。。
		 */
	}

	/**
	 * 显示网站调查。 
	 * #{ShowVote}  
	 */
	static final class ShowVoteHandler extends AbstractLabelHandler {
		/*
		 * (non-Javadoc)
		 * @see com.chinaedustar.publish.label.AbstractLabelHandler#handleLabel()
		 */
		@Override public int handleLabel() {
			// 获得当前站点的投票。
			VoteCollection vote_coll = pub_ctxt.getSite().getVoteCollection();
			VoteWrapper vote = vote_coll.getSiteVote();
			if (vote == null) {
				String novote = label.getAttributes().getNamedAttribute("novote");
				if (novote == null || novote.length() == 0) novote = "没有投票";
				out(novote);
				return PROCESS_SIBLING;
			}
			
			// 产生输出。
			if (label.hasChild()) {
				LocalContext local_ctxt = env.acquireLocalContext(label, 0);
				local_ctxt.setVariable("vote", vote);
				return PROCESS_DEFAULT;
			} else {
				String template_name = super.getTemplateName(".builtin.showvote");
				BuiltinLabel builtin_label = super.getBuiltinLabel(template_name);
				if (builtin_label == null) return super.unexistBuiltinLabel(template_name);
				
				builtin_label.process(env, new Object[]{ vote });
				return PROCESS_DEFAULT;
			}
		}
	}
	
	
	/**
	 * 是否显示当前网站管理登陆信息。选项从网站配置信息中得到 
	 * #{ShowAdminLogin show="true"} 
	 */
	static final class ShowAdminLoginHandler extends AbstractLabelHandler {
		@Override public int handleLabel() {
			Site site = pub_ctxt.getSite();
			if (site.getShowAdminLogin() == 1) {
				out("<a class=Bottom href=\"" + site.getUrl() + "admin/admin_login.jsp\" target=_blank>管理登陆</a>");
			} else {
				out("");
			}
			
			return PROCESS_DEFAULT;
		}		
	}

	/**
	 * 显示站点的版权声明。
	 * （原来实现的是显示当前对象的版权声明，为了和动易兼容，改为网站的）  
	 * #{Copyright}
	 */
	static final class CopyrightHandler extends AbstractLabelHandler {
		@Override public int handleLabel() {
			String html = getString();
			out(html);
			return PROCESS_DEFAULT;
		}
		
		private String getString() {
			return pub_ctxt.getSite().getCopyright();
			/*
			// 使用当前对象的版权信息。
			ModelObject this_ = getThis();
			while (this_ != null) {
				if (this_ instanceof PageAttrObject) {
					String copyright = ((PageAttrObject)this_).getCopyright();
					if (StringHelper.isEmpty(copyright) == false) 
						return copyright; 
				}
				this_ = this_.getParent();
			}
			
			// 使用网站的版权信息。
			return pub_ctxt.getSite().getCopyright();
			*/
		}
	}
	
	/**
	 * 页面上面产生 meta content-type 的标签。
	 * @author liujunxing
	 *
	 */
	static final class MetaGb2312Handler extends AbstractLabelHandler {
		// <meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
		/*
		 * (non-Javadoc)
		 * @see com.chinaedustar.publish.label.AbstractLabelHandler#handleLabel()
		 */
		@Override public int handleLabel() {
			// 也许以后可以根据参数或系统配置生成更好的 编码值。
			String meta_content_type = "<meta http-equiv=\"Content-Type\" content=\"text/html; charset=gb2312\" />\r\n";
			out(meta_content_type);
			return PROCESS_DEFAULT;
		}
	}
	
	/**
	 * 页面上面产生 meta generator 的处理器。
	 * @author liujunxing
	 *
	 */
	static final class MetaGeneratorHandler extends AbstractLabelHandler {
		/*
		 * (non-Javadoc)
		 * @see com.chinaedustar.publish.label.AbstractLabelHandler#handleLabel()
		 */
		@Override public int handleLabel() {
			String meta_generator = "<meta name=\"generator\" content=\"Publish 2.0 by www.chinaedustar.com\" />\r\n";
			out(meta_generator);
			return PROCESS_DEFAULT;
		}
	}
	
	/**
	 * 显示当前页面的针对搜索引擎的meta_keywords。  
	 * #{Meta_Keywords}
	 */
	static final class MetaKeywordsHandler extends AbstractLabelHandler {
		@Override public int handleLabel() {
			String metaKey = getMetaKey();
			if (metaKey == null) metaKey = "";
			StringBuilder strbuf = new StringBuilder();
			strbuf.append("<meta name=\"keywords\" content=\"")
				.append(PublishUtil.HtmlEncode(metaKey))
				.append("\" />\r\n");
			out(strbuf.toString());
			return PROCESS_DEFAULT;
		}
		
		// 返回要显示的字符串。
		private String getMetaKey() {
			// 使用当前模型对象。
			ModelObject this_ = getThis();
			while (this_ != null) {
				if (this_ instanceof PageAttrObject) 
					return ((PageAttrObject)this_).getMetaKey();
				this_ = this_.getParent();
			}
			
			// 使用网站的。
			Site site = pub_ctxt.getSite();
			return site.getMetaKey();
		}		
	}

	/**
	 * 显示当前页面针对搜索引擎设置的 meta_desc. 
	 * #{Meta_Description} 
	 */
	static final class MetaDescriptionHandler extends AbstractLabelHandler {
		@Override public int handleLabel() {
			String metaDesc = getMetaDesc();
			if (metaDesc == null) metaDesc = "";
			StringBuffer strbuf = new StringBuffer();
			// 输出为: <meta name="description" content="the descriptions..." />
			strbuf.append("<meta name=\"description\" content=\"")
				.append(PublishUtil.HtmlEncode(metaDesc))
				.append("\" />\r\n");

			out(strbuf.toString());
			return PROCESS_DEFAULT;
		}
		
		// 返回要显示的字符串。
		private String getMetaDesc() {
			// 使用当前模型对象。
			ModelObject this_ = getThis();
			while (this_ != null) {
				if (this_ instanceof PageAttrObject) 
					return ((PageAttrObject)this_).getMetaDesc();
				this_ = this_.getParent();
			}
			
			// 使用网站的。
			Site site = pub_ctxt.getSite();
			return site.getMetaDesc();
		}		
	}

	/**
	 * 显示网站统计 
	 * #{ShowSiteCountAll template="使用的模板名字"} 
	 */
	static final class ShowSiteCountAllHandler extends AbstractLabelHandler {
		@Override public int handleLabel() {
			// 获得数据
			SiteStatistics stat = pub_ctxt.getSite().getSiteStatistics();
			
			// 获得模板，缺省没有模板，而是直接产生输出。
			String template_name = getTemplateName(null);
			if (template_name == null || template_name.length() == 0)
				return defaultOutput(stat);
			
			BuiltinLabel builtin_label = getBuiltinLabel(template_name);
			if (builtin_label == null) return super.unexistBuiltinLabel(template_name);
			
			builtin_label.process(env, new Object[] { stat } );
			return PROCESS_DEFAULT;
		}
		
		// 产生统计标签的缺省输出。
		private int defaultOutput(SiteStatistics stat) {
			// 产生简单 '频道名: 项目数量' 输出。
			StringBuilder strbuf = new StringBuilder();
			ArrayList<ChannelStatistics> coll = stat.getChannelStats();
			for (int i = 0; i < coll.size(); ++i) {
				ChannelStatistics channel_stat = coll.get(i);
				strbuf.append("<br/>").append(channel_stat.getChannelName())
					.append(":").append(channel_stat.getItemNum())
					.append(channel_stat.getItemUnit());
			}
			
			out(strbuf.toString().substring(5));
			return PROCESS_DEFAULT;
		}
	}
	
	/** 
	 * 为页面引入模板样式
	 * #{Skin_CSS} 
	 */
	static final class SkinCSSHandler extends AbstractLabelHandler {
		@Override public int handleLabel() {
			String html = getString();
			out(html);
			return PROCESS_DEFAULT;
		}
		
		/* 动易的示例。
		 * <link href='/PowerEasy/Skin/DefaultSkin.css' rel='stylesheet' type='text/css'>
		 */
		private String getString() {
			Site site = pub_ctxt.getSite();
			
			// 加上路径，css 都放在 Skin 目录下面。
			return "<link href='" + site.getInstallDir()
				+ "Skin/DefaultSkin.css' rel='stylesheet' type='text/css' />";
		}
	}
	
	/**
	 * 显示网站 Logo. 
	 * #{ShowLogo width='xxx' height='yyy'}
	 * 生成示例。
	 * #{param width = 180}#{param height = 60}
	 * <a href='http://www.powereasy.net' title='刘氏小网' target='_blank'>
	 *  <img src='/PowerEasy/images/logo.gif' width='#{width}180' height='60' border='0'>
	 * </a>
	 */
	static final class ShowLogoHandler extends AbstractLabelHandler2 {
		/** 缺省模板方案的名字。 */
		private static final String DEFAULT_TEMPLATE = ".builtin.showlogo"; 
		@Override public int handleLabel() {
			// 获得参数。
			String width = getNamedAttribute(label.getAttributes(), "width", "180");
			String height = getNamedAttribute(label.getAttributes(), "height", "60");
			String logo = pub_ctxt.getSite().getLogo();
			Object[] args = new Object[] { width, height, logo };
			
			// 取得内建 Label。
			String template_name = super.getTemplateName(DEFAULT_TEMPLATE);
			BuiltinLabel builtin_label = getBuiltinLabel(template_name);
			if (builtin_label == null) return super.unexistBuiltinLabel(template_name);
			
			// 执行这个内建标签。
			return builtin_label.process(env, args);
		}
		public String getBuiltinName() {
			return DEFAULT_TEMPLATE;
		}
	}
	
	/**
	 * 显示网站 Banner. 
	 * #{ShowBanner width="500" height="60" }
		<a href='#{SiteUrl }' title='#{SiteName }'>
			<img src='#{SiteUrl }images/banner.jpg' width='#{width }' height='#{#{height } }' border='0'>
		</a>
	 *  
	 */
	static final class ShowBannerHandler extends AbstractLabelHandler2 {
		private static final String DEFAULT_TEMPLATE = ".builtin.showbanner";
		@Override public int handleLabel() {
			// 获得参数。
			String width = getNamedAttribute(label.getAttributes(), "width", "500");
			String height = getNamedAttribute(label.getAttributes(), "height", "60");
			String banner = pub_ctxt.getSite().getBanner();
			Object[] args = new Object[] { width, height, banner };
			
			// 找到模板
			String template_name = super.getTemplateName(DEFAULT_TEMPLATE);
			BuiltinLabel builtin_label = getBuiltinLabel(template_name);
			if (builtin_label == null) return super.unexistBuiltinLabel(template_name);

			// 执行这个内建标签。
			return builtin_label.process(env, args);
		}
		public String getBuiltinName() {
			return DEFAULT_TEMPLATE;
		}
	}

	/**
	 * 显示并输出网页底部的 HTML 。
	 * @author wangyi
	 * 使用例子：
	 * #{ShowBottom color="#000000" bgcolor="#ffffff" }
	 */
	static final class ShowBottomHandler extends AbstractLabelHandler {
		/*
		 * 模板内容：
#{param color } #{param bgcolor}
<div align="center">
	<table border="0" width="100%" align="center" style="color: #{color }; " bgcolor="#{bgcolor }">
		<tr>
			<td width="200">
			#{ShowLogo width="200" height="50" }
			<br />
			<a href="##{ShowBeiAn }">京ICP备NNNN号</a>
			</td>
			<td align="center">
			#{Copyright }<br />EMAIL：<a href="mailto:#{WebmasterEmail }">#{WebmasterEmail }</a>
			</td>
			<td width="150">
			<a href="#{ShowGongShang }"><img border="0" src="#{SiteUrl }images/gongshang.gif" /></a>
			</td>

	</table>
</div>
		 */
		@Override public int handleLabel() {
			// 获得参数。
			String color = getNamedAttribute(label.getAttributes(), "color", "#000000");
			String bgcolor = getNamedAttribute(label.getAttributes(), "bgcolor", "#ffffff");
			Object[] args = new Object[] { color, bgcolor};
					
			BuiltinLabel builtin_label = getBuiltinLabel(".builtin.showbottom");
			System.out.println(builtin_label.getName());
			// 执行这个内建标签。
			return builtin_label.process(env, args);
		}
	}
}
