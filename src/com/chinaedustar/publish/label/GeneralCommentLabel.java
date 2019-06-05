package com.chinaedustar.publish.label;

import java.util.List;

import com.chinaedustar.publish.comp.OpenType;
import com.chinaedustar.publish.itfc.LabelHandler2;
import com.chinaedustar.publish.model.*;
import com.chinaedustar.template.core.AttributeCollection;
import com.chinaedustar.template.core.LocalContext;

/**
 * 注释类的标签。
 * 
 * @author liujunxing
 *
 */
public final class GeneralCommentLabel extends GroupLabelBase {
	/** 不需要实例化。 */
	private GeneralCommentLabel() {
	}
	
	/**
	 * 注册 LabelHandler.
	 *
	 */
	public static final void registerHandler(LabelHandlerMap map) {
		// logger.info("GeneralCommentLabel 注册了其包含的一组标签解释器。");
		
		map.put("ShowNewCommentList", new ShowNewCommentListHandler());
		map.put("ShowCommentAddPanel", new ShowCommentAddPanelHandler());
		map.put("ShowCommentList", new ShowCommentListHandler());
	}
	
	/**
	 * 显示评论添加面板。
	 * #{ShowCommentAddPanel }
	 * param channelId：频道标识；
	 * param columnId：栏目标识；
	 * param itemId：项目标识。
	 */
	static final class ShowCommentAddPanelHandler extends AbstractLabelHandler implements LabelHandler2 {
		public static final String DEFAULT_TEMPLATE = ".builtin.showcommentaddpanel";
		/*
		 * (non-Javadoc)
		 * @see com.chinaedustar.publish.label.AbstractLabelHandler#handleLabel()
		 */
		@Override protected int handleLabel() {
			String template_name = super.getTemplateName(DEFAULT_TEMPLATE);
			BuiltinLabel builtin_label = getBuiltinLabel(template_name);
			if (builtin_label == null) return super.unexistBuiltinLabel(template_name);
			return builtin_label.process(env, null);
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
	 * 显示评论列表
	 * #{ShowCommentList maxPerPage='10'}
	 * @param maxPerPage 每页显示多少条评论。默认为20。
	 * <br>可自定义。
	 * <br>示例：
	 * <pre>
	 * #{ShowCommentList maxPerPage='4'}
	 *   #{Repeater}
	 *     <div>#{comment.content}</div>
	 *   #{/Repeater}
	 *   #{PageNav}
	 * #{/ShowCommentList}
	 * </pre>
	 * @author dengxiaolong
	 */
	static final class ShowCommentListHandler extends AbstractLabelHandler implements LabelHandler2 {
		public static final String DEFAULT_TEMPLATE = ".builtin.showcommentlist";
		/*
		 * (non-Javadoc)
		 * @see com.chinaedustar.publish.label.AbstractLabelHandler#handleLabel()
		 */
		@Override protected int handleLabel() {
			int pageSize = label.getAttributes().safeGetIntAttribute("pageSize", 20);
			if (pageSize <= 0) pageSize = 20;
			java.util.Map<String, Object> options = label.getAttributes().attrToOptions();
			options.put("pageSize", pageSize);
			
			Item item = getCurrentItem(env);
			if (item == null) {
				out("#{?? ShowCommentList 没有当前项目}");
				return PROCESS_DEFAULT;
			}
			
			PaginationInfo page_info = new PaginationInfo();
			page_info.setItemName("评论");
			page_info.setItemUnit("条");
			page_info.setPageSize(pageSize);
			
			CommentCollection comm_coll = item.getCommentCollection();
			List<Comment> comment_list = comm_coll.getCommentList(page_info);
			
			if (label.hasChild()) {
				LocalContext local_ctxt = env.acquireLocalContext(label, 0);
				local_ctxt.setVariable("comment_list", comment_list);
				local_ctxt.setVariable("options", options);
				local_ctxt.setVariable("page_info", page_info);
				super.addRepeaterSupport(local_ctxt, "comment", comment_list);
				super.addPageNavSupport(local_ctxt, page_info);
				return PROCESS_DEFAULT;
			} else {
				String template_name = super.getTemplateName(DEFAULT_TEMPLATE);
				BuiltinLabel builtinLabel = getBuiltinLabel(template_name);
				if (builtinLabel == null) return super.unexistBuiltinLabel(template_name);
				
				return builtinLabel.process(env, new Object[]{comment_list, options, page_info});
			}
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
	 * 显示最新评论列表
	 * @param length 评论内容最多字数。默认为0，0表示不予限制。
	 * @param num 每页显示的评论最多条数。
	 * @param openType 全部评论页面的打开方式：0：在当前窗口打开；1：在新窗口中打开。
	 * @author dengxiaolong
	 *
	 */
	static final class ShowNewCommentListHandler extends AbstractLabelHandler implements LabelHandler2 {
		public static final String DEFAULT_TEMPLATE = ".builtin.shownewcommentlist";
		/*
		 * (non-Javadoc)
		 * @see com.chinaedustar.publish.label.AbstractLabelHandler#handleLabel()
		 */
		@Override protected int handleLabel() {
			AttributeCollection coll = label.getAttributes();
			
			java.util.Map<String, Object> options = coll.attrToOptions();
			int length = coll.safeGetIntAttribute("length", 0);
			options.put("length", length);
			int num = coll.safeGetIntAttribute("num", 10);
			options.put("num", num);
			OpenType openType = OpenType.fromString(coll.safeGetStringAttribute("openType", null));
			options.put("openType", openType);
			
			Item item = getCurrentItem(env);
			if (item == null) {
				out("#{?? ShowNewCommentList 没有当前项目}");
				return PROCESS_DEFAULT;
			}

			PaginationInfo page_info = new PaginationInfo(1, num);
			java.util.List<Comment> comment_list = item.getCommentCollection().getCommentList(page_info);
			if (label.hasChild()) {
				LocalContext local_ctxt = env.acquireLocalContext(label, 0);
				local_ctxt.setVariable("comment_list", comment_list);
				local_ctxt.setVariable("options", options);
				local_ctxt.setVariable("page_info", page_info);
				super.addRepeaterSupport(local_ctxt, "comment", comment_list);
				super.addPageNavSupport(local_ctxt, page_info);
				return PROCESS_DEFAULT;
			} else {
				String template_name = super.getTemplateName(DEFAULT_TEMPLATE);
				BuiltinLabel builtinLabel = getBuiltinLabel(template_name);
				if (builtinLabel == null) return super.unexistBuiltinLabel(template_name);
				
				return builtinLabel.process(env, new Object[]{comment_list, options, page_info});
			}
		}
		/*
		 * (non-Javadoc)
		 * @see com.chinaedustar.publish.itfc.LabelHandler2#getBuiltinName()
		 */
		public String getBuiltinName() {
			return DEFAULT_TEMPLATE;
		}
	}
}
