package com.chinaedustar.publish.label;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import com.chinaedustar.common.util.StringHelper;
import com.chinaedustar.publish.PublishContext;
import com.chinaedustar.publish.model.Article;
import com.chinaedustar.publish.model.BuiltinLabel;
import com.chinaedustar.publish.model.Channel;
import com.chinaedustar.publish.model.Site;
import com.chinaedustar.template.core.AbstractLabelElement;
import com.chinaedustar.template.core.AttributeCollection;
import com.chinaedustar.template.core.InternalProcessEnvironment;
import com.chinaedustar.template.core.LocalContext;

/**
 * 文章标签
 */
public class GeneralArticleLabel extends GroupLabelBase {
	
	/**
	 * 不需要实例化
	 */
	private GeneralArticleLabel() {
		// 
	}
		
	/**
	 * 注册处理器
	 */
	public static final void registerHandler(LabelHandlerMap map) {
		
		map.put("ArticleID", new ArticlePropertyHandler());
		map.put("ArticleTitle", new ArticlePropertyHandler());
		map.put("ArticleAuthor", new ArticlePropertyHandler());
		map.put("ArticleUrl", new ArticlePropertyHandler());
		map.put("ArticleProperty", new ArticlePropertyHandler());
		map.put("ArticleShortTitle", new ArticlePropertyHandler());
		map.put("ArticleInfo", new ArticlePropertyHandler());
		map.put("ArticleSubheading", new ArticlePropertyHandler());
		map.put("ArticleContent", new ArticlePropertyHandler());
		map.put("ArticleAction", new ArticleActionLabelHandler());
		map.put("ArticleSummary", new ArticleSummaryLabelHandler());
		// map.put("Article", new ArticleLabelHandler());
		map.put("PrevArticle", new PrevArticleHandler());
		map.put("PrevArticleBMD", new PrevArticleHandler());
		map.put("NextArticle", new NextArticleHandler());
		map.put("PrevColumnArticle", new PrevColumnArticleHandler());
		map.put("NextColumnArticle", new NextColumnArticleHandler());
		map.put("CorrelativeArticle", new CorrelativeArticleLabelHandler());
	}

	/**
	 * article 的通用属性对象解释器
	 * @label #{ArticleID} - 显示文章标识。
	 * @label #{ArticleProperty} - 显示文章的推荐、等级、精华属性。 
	 * @label #{ArticleTitle} - 显示文章的标题。
	 * @label #{ArticleAuthor} - 文章的作者。
	 * @label #{ArticleUrl} - 显示文章链接地址。   
	 * @label #{ArticleShortTitle} - 显示文章简单标题。 
	 * @label #{ArticleInfo} - 显示文章信息，整体显示文章作者、文章来源、点击数、更新时间信息。 
	 * @label #{ArticleSubheading} - 显示文章的副标题。 
	 * @label #{ArticleContent} - 显示文章的内容
	 */
	private static final class ArticlePropertyHandler extends AbstractSimpleLabelHandler {
		public int handleLabel(PublishContext pub_ctxt, InternalProcessEnvironment env, AbstractLabelElement label) {
			String label_name = label.getLabelName();
			Article article = AbstractLabelHandler.getCurrentArticle(env);
			String result = "";
			
			if (article == null)
				result = "#{?? " + label_name + " 没有找到当前文章对象}";
			else if ("ArticleID".equals(label_name))
				result = String.valueOf(article.getId());
			else if ("ArticleTitle".equals(label_name))
				result = StringHelper.htmlEncode(article.getTitle());
			else if ("ArticleAuthor".equals(label_name))
				result = StringHelper.htmlEncode(article.getAuthor());
			else if ("ArticleUrl".equals(label_name))
				result = article.getPageUrl();
			else if ("ArticleShortTitle".equals(label_name))
				result = StringHelper.htmlEncode(article.getShortTitle());
			else if ("ArticleSubheading".equals(label_name))
				result = StringHelper.htmlEncode(article.getSubheading());
			else if ("ArticleProperty".equals(label_name))
				result = article_prop(article); 
			else if ("ArticleInfo".equals(label_name))
				result = article_info(article);
			else if ("ArticleContent".equals(label_name))
				result = article.getContent();
			
			if (result != null)
				env.getOut().write(result);

			return PROCESS_DEFAULT;
		}

		private String article_prop(Article article) {
			return "推荐：" + (article.getCommend() ? "是" : "否") + " 等级：" + article.getStars() + " 精华：" + (article.getElite() ? "是" : "否");
		}
		
		// #{ArticleInfo}
		private static String article_info(Article article) {
			Channel channel = article.getChannel();
			
			// 文章信息，整体显示文章作者、文章来源、点击数、更新时间信息。
			DateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			return "作者：" + article.getAuthor()
					+ "　文章来源：" + article.getSource()
					+ "　点击数：<script type='text/javascript' src='"
					+ channel.resolveUrl("showHits.jsp?itemId=" + article.getId()) + "'></script>"	
					+ "　更新时间：" + dateformat.format(article.getLastModified());	
		}
	}
	
	/**
	 * 取得一个文章，在该标签内能够使用该文章数据的标签
	 * #{Article id="1"} 
	 *  TODO: 还没有实现
	 * 
	 * @author liujunxing
	 *
	 */
	static final class ArticleLabelHandler extends AbstractLabelHandler {
		@Override public int handleLabel() {
			// int id = label.getAttributes().safeGetIntAttribute("id", 0);
			
			return PROCESS_DEFAULT;
		}
	}
	
	/**
	 * 显示前一文章
	 *  
	 * #{PrevArticle } 
	 * */
	static final class PrevArticleHandler extends AbstractLabelHandler {
		@Override
		public int handleLabel() {
			Article article = getCurrentArticle();
			if (article == null) {
				out("#{?? PrevArticle 没有当前文章}");
				return PROCESS_DEFAULT;
				
			} 
			
			Article prevArticle = article.getPrevArticle();
			if (label.hasChild()) {
				LocalContext local_ctxt = env.acquireLocalContext(label, 0);
				local_ctxt.setVariable("article", prevArticle);
				local_ctxt.setVariable("item", prevArticle);
			} else {
				String outstring = "";
				if (prevArticle != null) {
					outstring = "上一篇文章：" + "<a href = \"" + prevArticle.calcPageUrl()
						+"\">"+ prevArticle.getTitle()+"</a>";
				} else {
					outstring = "上一篇文章：没有了！";
				}
				out(outstring);
			}
			return PROCESS_DEFAULT;
		}
	}
	
	
	/**
	 * 显示后一文章。 
	 * #{NextArticle } 
	 * */
	static final class NextArticleHandler extends AbstractLabelHandler {
		@Override public int handleLabel() {
			Article article = getCurrentArticle();
			if (article == null) {
				out("#{?? NextArticle 没有当前文章}");
				return PROCESS_DEFAULT;
			}
			
			Article nextArticle = article.getNextArticle();
			if (label.hasChild()) {
				LocalContext local_ctxt = env.acquireLocalContext(label, 0);
				local_ctxt.setVariable("article", nextArticle);
				local_ctxt.setVariable("item", nextArticle);
			} else {
				String outstring = "";
				if (nextArticle != null) {
					outstring = "下一篇文章：" +"<a href = \"" + nextArticle.calcPageUrl()
						+ "\">" + nextArticle.getTitle() + "</a>";	
				} else {
					outstring = "下一篇文章：没有了！";
				}
				out(outstring);
			}
			return PROCESS_DEFAULT;
		}
	}
	
	/**
	 * 显示本栏目内的前一文章。
	 * #{PrevColumnArticle }
	 */
	static final class PrevColumnArticleHandler extends AbstractLabelHandler {
		@Override public int handleLabel() {
			Article article = getCurrentArticle();
			if (article == null) {
				out("#{?? PrevColumnArticle 没有当前文章}");
				return PROCESS_DEFAULT;
			} 
			
			Article prevArticle = article.getPrevColumnArticle();
			if (label.hasChild()) {
				LocalContext local_ctxt = env.acquireLocalContext(label, 0);
				local_ctxt.setVariable("article", prevArticle);
				local_ctxt.setVariable("item", prevArticle);
			} else {
				String outstring = "";
				if (prevArticle != null) {
					outstring = "上一篇文章：" + "<a href = \"" + prevArticle.calcPageUrl()
						+ "\">" + prevArticle.getTitle() + "</a>";
				} else {
					outstring = "上一篇文章：没有了！";
				}
				out(outstring);
			}
			return PROCESS_DEFAULT;
		}
	}

	/**
	 * 显示本栏目后一文章。 
	 * #{NextColumnArticle } 
	 * */
	static final class NextColumnArticleHandler extends AbstractLabelHandler {
		@Override public int handleLabel() {
			Article article = getCurrentArticle();
			if (article == null) {
				out("#{?? NextColumnArticle 没有当前文章}");
				return PROCESS_DEFAULT;
			}
			
			Article nextArticle = article.getNextArticle();
			if (label.hasChild()) {
				LocalContext local_ctxt = env.acquireLocalContext(label, 0);
				local_ctxt.setVariable("article", nextArticle);
				local_ctxt.setVariable("item", nextArticle);
			} else {
				String outstring = "";
				if (nextArticle != null) {
					outstring = "下一篇文章：" +"<a href = \"" + nextArticle.calcPageUrl()
						+ "\">" + nextArticle.getTitle() + "</a>";	
				} else {
					outstring = "下一篇文章：没有了！";
				}
				out(outstring);
			}
			return PROCESS_DEFAULT;
		}
	}

	/**
	 * 显示文章的功能：告诉好友，打印，关闭，发表评论等。 
	 * #{ArticleAction  if_comment if_send if_print if_close } 
	 * */
	static final class ArticleActionLabelHandler extends AbstractLabelHandler {
		@Override public int handleLabel() {
			// 获得参数
			AttributeCollection coll = label.getAttributes();
			Boolean if_comment = coll.safeGetBoolAttribute("if_comment", true);
			Boolean if_send	= coll.safeGetBoolAttribute("if_send", true);
			Boolean if_print = coll.safeGetBoolAttribute("if_print", true);
			Boolean if_close = coll.safeGetBoolAttribute("if_close", true);
			
			Article article = getCurrentArticle();
			String outstring="";
			if(if_send){
				outstring = "<a href=''>【告诉好友】";
			}
			if(if_print){
				outstring += "<a href='javascript:print();'>【打印此文】";
			}
			if(if_close){
				outstring += "<a href='javascript:close();'>【关闭窗口】</a>";
			}
			
			if (article != null) {
				String siteUrl = "";
				Site site = (Site)env.findVariable("site");
				if (site != null) {
					siteUrl = site.getUrl();
				}
				String channelDir = "";	
				Channel channel = pub_ctxt.getSite().getChannels().getChannel(article.getChannelId());
				if (channel != null) {
					channelDir = channel.getChannelDir();
				}
				if (if_comment && article.getCommentFlag()== 1){
					outstring = "<a href='" + siteUrl + channelDir + "/showComments.jsp?columnId=" + article.getColumnId()
						+ "&itemId=" + article.getId() + "' target='_blank'" + ">【发表评论】</a>" + outstring;
				}
				out(outstring);	// TODO: 显示【发表评论】【告诉好友】【打印此文】【关闭窗口】
			} else {
				out("#{?? ArticleAction 没有当前文章对象}");
			}
			return PROCESS_DEFAULT; 
		}
	}
	
	/** 
	 * 显示相关文章列表。
	 * #{CorrelativeArticle } 
	 */
	static final class CorrelativeArticleLabelHandler extends AbstractLabelHandler {
		/*
		 * (non-Javadoc)
		 * @see com.chinaedustar.publish.label.AbstractLabelHandler#handleLabel()
		 */
		@Override public int handleLabel() {
			out("#{?? CorrelativeArticle 未实现}");
			return PROCESS_DEFAULT;
			/*
			//获取参数
			String temp="";
			temp = label.getAttributes().getNamedAttribute("articleCount");
			final int articleCount = getIntAttribute(temp,5);
			temp = label.getAttributes().getNamedAttribute("titleNum");
			int titleNum = getIntAttribute(temp,0);//0为完整标题。
			// TODO: 显示相关文章。
			final Article article = getCurrentArticle();
			int id= article.getId();
			@SuppressWarnings("unchecked")
			List <Article> list = (List)pub_ctxt.getDao().getHibernateTemplate().execute(new HibernateCallback(){
				public Object doInHibernate(Session session)throws HibernateException, SQLException{
					String articlekey = article.getKeywords();
					String hql="from Article as a where a.status=1 and a.deleted=false and a.keywords like '%" +articlekey+"%' order by a.createTime desc";
					Query query = session.createQuery(hql);
					query.setFirstResult(0);
					query.setMaxResults(articleCount);
					return query.list();
				}
			});
			String outstring = "";
			if(list.size() > 0){
				for(int i =0;i<list.size();i++){
					if(id != list.get(i).getId()){
						String title="";
						if(titleNum >0 && titleNum < list.get(i).getTitle().length()){
							title = list.get(i).getTitle().substring(0, titleNum);
						}else {
							title = list.get(i).getTitle();
						}
						
						outstring = outstring +"<a href=\"" + list.get(i).calcPageUrl() + "\"><li>"+ title +"</li></a><br/>";
					}
				}
			}else{
				outstring = "没有相关文章！";
			}
			out(outstring);
			return PROCESS_DEFAULT;
			*/
		}
	}

	/**
	 * 显示文章综合信息。（类型、精华、置项、推荐、热门、星级） 
	 * #{ArticleSummary} 
	 */
	static final class ArticleSummaryLabelHandler extends AbstractLabelHandler {
		@Override public int handleLabel(){
			Article article = getCurrentArticle();
			if (article == null) {
				out("#{?? ArticleSummary 没有当前文章}");
				return PROCESS_DEFAULT;
			}
			/*
			List<String> list = new ArrayList<String>();
			String outstring = "";
			switch (article.getIncludePic()){
			case 1:
				outstring = "【图文】";
				break;
			case 2:
				outstring = "【组图】";
				break;
			case 3:
				outstring = "【推荐】";
				break;
			case 4:
				outstring = "【注意】";
				break;
			}
			list.add(0,outstring);
			//
			outstring = "";
			outstring = article.getTitle();
			list.add(1,outstring);
			//
			outstring = "";
			if(article.getElite()){
				outstring ="精";
			}
			list.add(2,outstring);
			//
			outstring ="";
			if(article.getOnTop()){
				outstring ="顶";
			}
			list.add(3,outstring);
			//
			outstring ="";
			if(article.getRecommend()){
				outstring ="荐";
			}
			list.add(4,outstring);
			//
			outstring ="";

			if(article.getIsHot()) {
				outstring ="热";
			}
			list.add(5,outstring);

			outstring = "";
			if (article.getStars()>0) {
				for(int i=0;i<article.getStars();i++){
					outstring +="★";
				}
			}
			list.add(6,outstring);
			*/
			
			// 获得内建标签.
			String template_name = super.getTemplateName(".builtin.showarticlesummary");
			BuiltinLabel builtin_label = getBuiltinLabel(template_name);
			if (builtin_label == null) return super.unexistBuiltinLabel(template_name);
			
			// 执行内建标签.
			return builtin_label.process(env, new Object[] {article} );
		}
	}
}
