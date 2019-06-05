package com.chinaedustar.publish.admin;

import javax.servlet.jsp.PageContext;

import com.chinaedustar.publish.PublishUtil;
import com.chinaedustar.publish.model.*;

/**
 * 评论管理。
 * 
 * @author liujunxing
 * @jsppage
 *  <li>admin_comment_list.jsp 
 */
public class CommentManage extends AbstractBaseManage {
	/**
	 * 构造。
	 * @param pageContext
	 */
	public CommentManage(PageContext pageContext) {
		super(pageContext);
	}
	
	/**
	 * admin_comment_list.jsp 页面数据初始化。
	 *
	 */
	public void initListPage() {
		// 根据 Request 的 channelId 获取 Channel. 
		Channel channel = super.getChannelData();
		if (channel == null) throw super.exChannelUnexist();
		setTemplateVariable("channel", channel);
		
		// 得到评论列表的数据，返回 DataTable。
		PaginationInfo page_info = getPaginationInfo();
		Object comment_list = channel.getCommentCollection().getCommentDataTable(channel.getId(), false, page_info);
		setTemplateVariable("comment_list", comment_list);
		page_info.setItemName("个评论");
		setTemplateVariable("page_info", page_info);
	}

	/**
	 * admin_comment_reply.jsp 页面初始化。
	 *
	 */
	public void initReplyPage() {
		// 根据 Request 的 channelId 获取 Channel. 
		Channel channel = super.getChannelData();
		if (channel == null) throw super.exChannelUnexist();
		setTemplateVariable("channel", channel);

		// 根据 commentId 得到 Comment
		int commentId = param_util.safeGetIntParam("commentId");
		Comment comment = channel.getCommentCollection().getComment(commentId);
		setTemplateVariable("comment", comment);
	}
	
	/**
	 * 用户添加一个评论。(也许放到 action 或别的地方更合适??)
	 *
	 */
	public void userAddComment() {
		java.util.List<String> error_list = new java.util.ArrayList<String>();
		setTemplateVariable("error_list", error_list);
		
		// 验证表单码。
		String formId = param_util.safeGetStringParam("formId", null);
		if (PublishUtil.isFormValidId(page_ctxt.getSession(), formId) == false) {
			error_list.add("请勿重复提交表单，请刷新添加评论页面添加新评论！");
			return;
		}

		// 验证频道。
		int channelId = param_util.safeGetIntParam("channelId");
		Channel channel = site.getChannel(channelId);
		if (channel == null) {
			error_list.add("无法获得频道数据，请确定您是从有效的链接提交的表单。");
			return;
		}

		// 验证项目。
		int itemId = param_util.safeGetIntParam("itemId", 0);
		Item item = channel.loadItem(itemId);
		// TODO: allowComment ??
		if (item == null || item.getChannelId() != channelId || item.getDeleted() 
				|| item.getStatus() != Item.STATUS_APPR ) {
			error_list.add("发表评论的目标项目不存在, 或者项目未审核");
		}

		// 获得表单输入，准备创建评论。
		Comment comment = new Comment();
		User user = PublishUtil.getCurrentUser(page_ctxt.getSession());
		if (user == null) {
			if ("".equals(param_util.safeGetStringParam("userName").trim())) {
				error_list.add("输入姓名不能为空！");
			}
		}
		if ("".equals(param_util.safeGetStringParam("content").trim())) {
			error_list.add("输入内容不能为空！");
		}

		// 如果上面操作有错误，则返回。
		if (!error_list.isEmpty()) 
			return;
		
		CommentCollection comm_coll = item.getCommentCollection();
		if (user != null) {
			//先假定一个用户。
			comment.setUserName(user.getUserName());
			comment.setUserType(1);
			comment.setQq("");
			comment.setMsn("");
			comment.setEmail("");
			comment.setHomepage("");
			comment.setSex(-1);
		} else {
			comment.setUserName(param_util.safeGetStringParam("userName"));
			comment.setUserType(0);
			comment.setQq(param_util.safeGetStringParam("qq"));
			comment.setMsn(param_util.safeGetStringParam("msn"));
			comment.setEmail(param_util.safeGetStringParam("email"));
			comment.setHomepage(param_util.safeGetStringParam("homepage"));
			comment.setSex(param_util.safeGetIntParam("sex", 1));
		}
		comment.setScore(param_util.safeGetIntParam("score", 3));
		comment.setContent(param_util.safeGetStringParam("content"));
		comment.setItemId(itemId);
		comment.setWriteTime(new java.util.Date());
		comment.setIp(page_ctxt.getRequest().getRemoteAddr());
		comment.setPassed(false);

		param_util.getPublishContext().getTransactionProxy()
				.saveComment(comm_coll, comment);
		setTemplateVariable("comment", comment);
		setTemplateVariable("site", site);
		setTemplateVariable("channel", channel);
		setTemplateVariable("item", item);
	}
}
