package com.chinaedustar.publish.label;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import com.chinaedustar.publish.model.*;
import com.chinaedustar.template.core.LocalContext;

/**
 * 留言类标签集合。
 *
 */
public final class GeneralFeedbackLabel extends GroupLabelBase {
	/** 私有的构造方法，对象不能实例化。 */
	private GeneralFeedbackLabel() {
	}

	/**
	 * 注册 LabelHandler.
	 *
	 */
	public static final void registerHandler(LabelHandlerMap map) {
		// logger.info("GeneralChannelLabel 注册了其包含的一组标签解释器。");
		
		map.put("FeedbackId", new FeedbackIdHandler());
		map.put("FeedbackTitle", new FeedbackTitleHandler());	// 调试通过。
		map.put("FeedbackUserName", new FeedbackUserNameHandler());	// 调试通过。
		map.put("FeedbackContent", new FeedbackContentHandler());	// 调试通过。
		map.put("FeedbackCreateTime", new FeedbackCreateTimeHandler());	// 调试通过。
		map.put("FeedbackHits", new FeedbackHitsHandler());		// 调试通过。
		map.put("FeedbackUserSex", new FeedbackUserSexHandler());
		map.put("FeedbackUserEmail", new FeedbackUserEmailHandler());
		map.put("FeedbackUserQq", new FeedbackUserUserQqHandler());
		map.put("FeedbackUserIcq", new FeedbackUserIcqHandler());
		map.put("FeedbackUserMsn", new FeedbackUserMsnHandler());
		map.put("FeedbackUserHompage", new FeedbackUserHompageHandler());
		map.put("FeedbackIsDisplay", new FeedbackIsDisplayHandler());
		map.put("FeedbackMainId", new FeedbackMainIdHandler());
		map.put("FeedbackMainFlag", new FeedbackMainFlagHandler());
		map.put("FeedbackTitleType", new FeedbackTitleTypeHandler());
		map.put("FeedbackReplyCount", new FeedbackReplyCountHandler());
		map.put("FeedbackInfoShow", new FeedbackInfoShowHandler());
		map.put("FeedbackLastReply", new FeedbackLastReplyHandler());
		map.put("FeedbackStatus", new FeedbackStatusHandler());	// 调试通过。
		map.put("FeedbackUrl", new FeedbackUrlHnalder());	// 调试通过。
		
		map.put("FeedbackList", new FeedbackListHandler());		// 调试通过。
	}
	
	/**
	 * 显示留言的ID。
	 * #{FeedbackId}
	 *
	 */
	static final class FeedbackIdHandler extends AbstractLabelHandler {
		/*
		 * (non-Javadoc)
		 * @see com.chinaedustar.publish.label.AbstractLabelHandler#handleLabel()
		 */
		@Override protected int handleLabel() {
			Feedback feedback = getCurrentFeedback();
			if(feedback == null){
				out("#{FeedbackId 当前页面不存在留言对象(feedback)}");
			}else{
				out(feedback.getId() +"");
			}
			return 	PROCESS_DEFAULT;
		}
	}
	
	/**
	 * 显示留言的标题。
	 * #{FeedbackTitle}
	 * @author wangyi
	 *
	 */
	static final class FeedbackTitleHandler extends AbstractLabelHandler {
		/*
		 * (non-Javadoc)
		 * @see com.chinaedustar.publish.label.AbstractLabelHandler#handleLabel()
		 */
		@Override protected int handleLabel() {
			Feedback feedback = getCurrentFeedback();
			if (feedback == null) {
				out("#{FeedbackTitle 当前页面中不存在留言对象(feedback)}");
			} else {
				String str="";
				if(feedback.getTitle() == null){
					str="";
				}else{
					str = feedback.getTitle();
				}
				out(str);
			}
			return PROCESS_DEFAULT;
		}
	}
	
	/**
	 * 留言的用户名
	 * #{FeedbackUserName}
	 * @author wangyi
	 *
	 */
	static final class FeedbackUserNameHandler extends AbstractLabelHandler {
		@Override public int handleLabel() {
			Feedback feedback = getCurrentFeedback();
			if (feedback == null) {
				out("#{FeedbackUserName 当前页面中不存在留言对象(feedback)}");
			} else {
				out(feedback.getUserName());
			}
			return PROCESS_DEFAULT;
		}
	}
	
	/**
	 * 显示留言的ID。
	 * #{FeedbackUserSex}
	 *
	 */
	static final class FeedbackUserSexHandler extends AbstractLabelHandler{
		@Override public int handleLabel(){
			Feedback feedback = getCurrentFeedback();
			if(feedback == null){
				out("#{FeedbackId 当前页面不存在留言对象(feedback)}");
			}else{
				out(feedback.getUserSex() +"");
			}
			return 	PROCESS_DEFAULT;
		}
	}
	
	/**
	 * 显示留言的Email。
	 * #{FeedbackUserEmail}
	 *
	 */
	static final class FeedbackUserEmailHandler extends AbstractLabelHandler{
		@Override public int handleLabel() {
			Feedback feedback = getCurrentFeedback();
			if(feedback == null) {
				out("#{FeedbackId 当前页面不存在留言对象(feedback)}");
			} else {
				String str="";
				if (feedback.getEmail() == null) {
					str="";
				} else {
					str = feedback.getEmail();
				}
				out(str);
			}
			return 	PROCESS_DEFAULT;
		}
	}
	
	/**
	 * 显示留言的主页。
	 * #{FeedbackUserHompage}
	 *
	 */
	static final class FeedbackUserHompageHandler extends AbstractLabelHandler{
		@Override public int handleLabel(){
			Feedback feedback = getCurrentFeedback();
			if(feedback == null){
				out("#{FeedbackId 当前页面不存在留言对象(feedback)}");
			}else{
				String str="";
				if(feedback.getHomepage() == null){
					str="";
				}else{
					str = feedback.getHomepage();
				}
				out(str);
			}
			return 	PROCESS_DEFAULT;
		}
	}
	
	/**
	 * 显示留言的QQ。
	 * #{FeedbackUserQq}
	 *
	 */
	static final class FeedbackUserUserQqHandler extends AbstractLabelHandler{
		@Override public int handleLabel(){
			Feedback feedback = getCurrentFeedback();
			if(feedback == null){
				out("#{FeedbackId 当前页面不存在留言对象(feedback)}");
			}else{
				String str="";
				if(feedback.getUserQq() == null){
					str="";
				}else{
					str = feedback.getUserQq();
				}
				out(str);
			}
			return 	PROCESS_DEFAULT;
		}
	}
	
	/**
	 * 显示留言的Icq。
	 * #{FeedbackUserIcq}
	 *
	 */
	static final class FeedbackUserIcqHandler extends AbstractLabelHandler{
		@Override public int handleLabel(){
			Feedback feedback = getCurrentFeedback();
			if(feedback == null){
				out("#{FeedbackId 当前页面不存在留言对象(feedback)}");
			}else{
				String str="";
				if(feedback.getUserIcq() == null){
					str="";
				}else{
					str = feedback.getUserIcq();
				}
				out(str);
			}
			return 	PROCESS_DEFAULT;
		}
	}
	
	/**
	 * 显示留言的Msn。
	 * #{FeedbackUserMsn}
	 *
	 */
	static final class FeedbackUserMsnHandler extends AbstractLabelHandler{
		@Override public int handleLabel(){
			Feedback feedback = getCurrentFeedback();
			if(feedback == null){
				out("#{FeedbackId 当前页面不存在留言对象(feedback)}");
			}else{
				String str="";
				if(feedback.getUserMsn() == null){
					str="";
				}else{
					str = feedback.getUserMsn();
				}
				out(str);
			}
			return 	PROCESS_DEFAULT;
		}
	}
	
	/**
	 * 显示留言的MainID。
	 * #{FeedbackMainId}
	 *
	 */
	static final class FeedbackMainIdHandler extends AbstractLabelHandler{
		@Override public int handleLabel(){
			Feedback feedback = getCurrentFeedback();
			if(feedback == null){
				out("#{FeedbackId 当前页面不存在留言对象(feedback)}");
			}else{
				out(feedback.getMainId()+"");
			}
			return 	PROCESS_DEFAULT;
		}
	}
	
	/**
	 * 显示留言的MainFlag。
	 * #{FeedbackMainFlag}
	 *
	 */
	static final class FeedbackMainFlagHandler extends AbstractLabelHandler{
		@Override public int handleLabel(){
			Feedback feedback = getCurrentFeedback();
			if(feedback == null){
				out("#{FeedbackId 当前页面不存在留言对象(feedback)}");
			}else{
				out(feedback.getMainFlag()+"");
			}
			return 	PROCESS_DEFAULT;
		}
	}
	
	/**
	 * 显示留言是主题还是回复。
	 * #{FeedbackTitleType}
	 *
	 */
	static final class FeedbackTitleTypeHandler extends AbstractLabelHandler{
		@Override public int handleLabel(){
			Feedback feedback = getCurrentFeedback();
			if(feedback == null){
				out("#{FeedbackId 当前页面不存在留言对象(feedback)}");
			}else{
				String titleType = "";
				if(feedback.getMainFlag() == 1){
					titleType = "主题";
				}else{
					titleType = "回复";
				}
				out(titleType+"");
			}
			return 	PROCESS_DEFAULT;
		}
	}
	
	/**
	 * 留言的内容
	 * #{FeedbackContent}
	 *
	 */
	static final class FeedbackContentHandler extends AbstractLabelHandler {
		@Override public int handleLabel() {
			Feedback feedback = getCurrentFeedback();
			if (feedback == null) {
				out("#{FeedbackContent 当前页面中不存在留言对象(feedback)}");
			} else {
				out(feedback.getContent() == null? "" : feedback.getContent());
			}
			return PROCESS_DEFAULT;
		}
	}
	
	/**
	 * 显示留言的创建时间。
	 * #{FeedbackCreateTime format=""}
	 * @format 时间的格式化模板，默认为 yyyy-MM-dd HH:mm:ss。
		SimpleDateFormat中的方式格式化时间。
		G  Era 标志符  Text  AD  
		y  年  Year  1996; 96  
		M  年中的月份  Month  July; Jul; 07  
		w  年中的周数  Number  27  
		W  月份中的周数  Number  2  
		D  年中的天数  Number  189  
		d  月份中的天数  Number  10  
		F  月份中的星期  Number  2  
		E  星期中的天数  Text  Tuesday; Tue  
		a  Am/pm 标记  Text  PM  
		H  一天中的小时数（0-23）  Number  0  
		k  一天中的小时数（1-24）  Number  24  
		K  am/pm 中的小时数（0-11）  Number  0  
		h  am/pm 中的小时数（1-12）  Number  12  
		m  小时中的分钟数  Number  30  
		s  分钟中的秒数  Number  55  
		S  毫秒数  Number  978  
		z  时区  General time zone  Pacific Standard Time; PST; GMT-08:00  
		Z  时区  RFC 822 time zone  -0800  
	 * @author wangyi
	 *
	 */
	static final class FeedbackCreateTimeHandler extends AbstractLabelHandler {
		@Override public int handleLabel() {
			Feedback feedback = getCurrentFeedback();
			if (feedback == null) {
				out("");
			} else {
				String format = label.getAttributes().safeGetStringAttribute("format", "yyyy-MM-dd HH:mm:ss");
				Date createTime = feedback.getCreateTime();
				if (createTime == null) {
					out("");
				} else {
					SimpleDateFormat dateFormat = new SimpleDateFormat(format);
					String time = dateFormat.format(createTime);
					System.out.println(time.toString());
					out(time);
				}
			}
			return PROCESS_DEFAULT;
		}
	}

	/**
	 * 留言的点击次数。
	 * #{FeedbackHits}
	 * @author wangyi
	 *
	 */
	static final class FeedbackHitsHandler extends AbstractLabelHandler {
		@Override public int handleLabel() {
			Feedback feedback = getCurrentFeedback();
			if (feedback == null) {
				out("#{FeedbackHits 当前页面中不存在留言对象(feedback)}");
			} else {
				out(feedback.getHits() + "");
			}
			return PROCESS_DEFAULT;
		}
	}
	
	/**
	 * 显示留言的状态。
	 * #{FeedbackStatus}
	 * @author wangyi
	 *
	 */
	static final class FeedbackStatusHandler extends AbstractLabelHandler {
		@Override public int handleLabel() {
			Feedback feedback = getCurrentFeedback();
			if (feedback == null) {
				out("#{FeedbackStatus 当前页面中不存在留言对象(feedback)}");
			} else {
				out(feedback.getStatus() + "");
			}
			return PROCESS_DEFAULT;
		}
	}

	/**
	 * 留言是否公开显示。
	 * #{FeedbackIsDisplay}
	 * @author wangyi
	 *
	 */
	static final class FeedbackIsDisplayHandler extends AbstractLabelHandler {
		@Override public int handleLabel() {
			Feedback feedback = getCurrentFeedback();
			if (feedback == null) {
				out("#{FeedbackIsDisplay 当前页面中不存在留言对象(feedback)}");
			} else {
				out(feedback.getIsDisplay() + "");
			}
			return PROCESS_DEFAULT;
		}
	}
	
	/**
	 * 显示留言的链接。
	 * #{FeedbackUrl}
	 * @author wangyi
	 *
	 */
	static final class FeedbackUrlHnalder extends AbstractLabelHandler {
		@Override public int handleLabel() {
			Feedback feedback = getCurrentFeedback();
			if (feedback == null) {
				out("#{FeedbackUrl 当前页面中不存在留言对象(feedback)}");
			} else {
				out(feedback.getPageUrl());
			}
			return PROCESS_DEFAULT;
		}
	}
	
	/**
	 * 显示留言的回复条数。
	 * #{FeedbackReplyCount}
	 * @author wangyi
	 *
	 */
	static final class FeedbackReplyCountHandler extends AbstractLabelHandler {
		@Override public int handleLabel() {
			Feedback feedback = getCurrentFeedback();
			if (feedback == null) {
				out("");
			} else {
				long replyCount = pub_ctxt.getSite().getFeedbackCollection().getReplyFeedbackAllCount(feedback.getId(), -1);
				out(replyCount + "");
			}
			return PROCESS_DEFAULT;
		}
	}
	
	/**
	 * 留言的回复条数。
	 * #{FeedbackInfoShow}
	 *
	 */
	static final class FeedbackInfoShowHandler extends AbstractLabelHandler {
		@Override public int handleLabel() {
			Feedback feedback = getCurrentFeedback();
			if (feedback == null) {
				out("");
			} else {
				long replyCount = pub_ctxt.getSite().getFeedbackCollection().getReplyFeedbackAllCount(feedback.getId(), -1);
				String str = "";
				if(replyCount > 0){
					str = "共有"+replyCount+"条回复信息";
				}else{
					str = "<a href=\"feedbackList.jsp\">返回列表</a>";
				}
				out(str + "");
			}
			return PROCESS_DEFAULT;
		}
	}
	
	/**
	 * 最后一条回复留言。
	 * #{FeedbackLastReply}
	 * @author wangyi
	 *
	 */
	static final class FeedbackLastReplyHandler extends AbstractLabelHandler {
		@Override public int handleLabel() {
			Feedback feedback = getCurrentFeedback();
			if (feedback == null) {
				out("");
			} else {
				Feedback replyfd = pub_ctxt.getSite().getFeedbackCollection().getReplyFeedback(feedback.getId());
				SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
				String str =sd.format(replyfd.getCreateTime())+"<font color=\"#FF0000\">|</font>"+ replyfd.getUserName();
				out(str +"");
			}
			return PROCESS_DEFAULT;
		}
	}
	
	
	/**
	 * 留言列表标签，支持循环自定义标签，提供默认的标签模板只是显示留言的链接。 
	 * #{FeedbackList titleCharNum="30" num="10" orderBy="id desc" mainFlag="1" }#{feedback.***}#{/FeedbackList}
	 * 
	 * @param titleCharNum 标题显示字符数 0：全部显示出来；默认为30。
	 * @param num 显示的留言记录数，默认为 20 。
	 * @param orderBy 记录的排序，默认为 id desc 。
	 * @param mainFlag 是否主文档。1主文档，0回复,-1全部。
	 * @author wangyi
	 *
	 */
	static final class FeedbackListHandler extends AbstractLabelHandler {
		@Override public int handleLabel() {
			Feedback fd = getCurrentFeedback();
			Integer obId = null; 
			if(fd != null){
				obId = fd.getId();
			}		
			int titleCharNum = label.getAttributes().safeGetIntAttribute("titleCharNum", 30);
			int num = label.getAttributes().safeGetIntAttribute("num", 20);
			String orderBy = label.getAttributes().safeGetStringAttribute("orderBy", "id desc");
			int mainFlag = label.getAttributes().safeGetIntAttribute("mainFlag", -1);
			List<Feedback> list = pub_ctxt.getSite().getFeedbackCollection().getFeedbackList(-1, mainFlag, obId, orderBy, 1, num, null, null);
			if (list == null || list.isEmpty()) {
				out("没有任何留言");
			} else {
				if (label.hasChild()) {
					StringBuffer strBuffer = new StringBuffer();
					for (int i = 0; i < list.size(); i++) {
						Feedback feedback = list.get(i);
						String title = feedback.getTitle();
						if (titleCharNum > 0) {
							byte[] titles;
							try {
								titles = title.getBytes("GBK");
								if (titles.length > titleCharNum) {
									title = new String(titles, 0, titleCharNum, "UTF-8");
								}
								feedback.setTitle(title);
							} catch (UnsupportedEncodingException ex) {
							}
							
						}
						LocalContext localContext = env.acquireLocalContext(label, PROCESS_DEFAULT);
						localContext.setVariable("feedback", feedback);
						strBuffer.append(env.processChild(label));
					}
					out(strBuffer.toString());
					return PROCESS_SIBLING;
				} else {
					StringBuffer strBuffer = new StringBuffer();
					for (int i = 0; i < list.size(); i++) {
						strBuffer.append("<div>");
						Feedback feedback = list.get(i);
						if("".equals(feedback.getStaticPageUrl()) || feedback.getStaticPageUrl() == null){
							strBuffer.append("<a href='").append("feedback.jsp?feedbackId="+feedback.getId()).append("' target='_blank'>");
						}else{
							strBuffer.append("<a href='").append(feedback.getStaticPageUrl()).append("' target='_blank'>");
						}
						String title = feedback.getTitle();
						if (titleCharNum > 0) {
							byte[] titles;
							try {
								titles = title.getBytes("GBK");
								if (titles.length > titleCharNum) {
									title = new String(titles, 0, titleCharNum, "UTF-8");
								}
							} catch (UnsupportedEncodingException ex) {
							}
							
						}
						strBuffer.append(title);
						strBuffer.append("</a>");
						strBuffer.append("</div>");
					}
					out(strBuffer.toString());
				}
			}
			return PROCESS_DEFAULT;
		}
	}

}
