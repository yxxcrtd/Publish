package com.chinaedustar.publish.label;

import java.util.List;

import com.chinaedustar.publish.model.ItemQueryExecutor;
import com.chinaedustar.template.core.AttributeCollection;
import com.chinaedustar.template.core.LocalContext;

/**
 * 高级 Item 标签集合。Item 包括 Article, Soft, Photo 等
 * 
 * @author liujunxing
 */
@SuppressWarnings("rawtypes")
public final class AdvanceItemLabel extends GroupLabelBase {

	/**
	 * 不从外部初始化
	 */
	private AdvanceItemLabel() {
		//
	}

	/**
	 * 注册 LabelHandler
	 */
	public static final void registerHandler(LabelHandlerMap map) {
		map.put("ShowArticleList", new ShowArticleListHandler3());
		map.put("ShowPhotoList", new ShowPhotoListHandler3());
		map.put("ShowSoftList", new ShowSoftListHandler3());
		map.put("FlashNews", new FlashNewsHandler());
		map.put("ShowPicArticleList", new ShowPicArticleListHandler3());
		map.put("ShowPicSoftList", new ShowPicSoftListHandler3());
		map.put("ShowStudentList", new ShowStudentListHandler3());
		map.put("ShowRStudentList", new ShowRStudentListHandler3());
	}

	/**
	 * 显示文章列表
	 */
	static final class ShowArticleListHandler3 extends AbstractShowItemListHandler3 {
		private static final String DEFAULT_TEMPLATE = ".builtin.showarticlelist";

		@Override
		protected ShowArticleListHandler3 newInstance() {
			return new ShowArticleListHandler3();
		}

		@Override
		public int handleLabel() {
			return super.handleLabel();
		}

		@Override
		protected void setLocalContextVariable(LocalContext local_ctxt) {
			super.setLocalContextVariable(local_ctxt);
			local_ctxt.setVariable("article_list", super.item_list);
		}

		@Override
		protected Object getData() {
			ItemQueryExecutor ibo = new ItemQueryExecutor(pub_ctxt);
			List result = ibo.getAdvItemList(query_option, page_info);
			debugOutputQuery(ibo.getAdvItemQueryHelper());
			return result;
		}

		public String getBuiltinName() {
			return DEFAULT_TEMPLATE;
		}
	}
	
	/**
	 * 显示学生工作系统中的本科生列表
	 */
	private static final class ShowStudentListHandler3 extends AbstractShowItemListHandler3 {
		private static final String DEFAULT_TEMPLATE = ".builtin.showstudentlist";

		@Override
		protected ShowStudentListHandler3 newInstance() {
			return new ShowStudentListHandler3();
		}

		@Override
		public int handleLabel() {
			return super.handleLabel();
		}

		@Override
		protected void setLocalContextVariable(LocalContext local_ctxt) {
			super.setLocalContextVariable(local_ctxt);
			local_ctxt.setVariable("student_list", super.item_list);
		}

		@Override
		protected Object getData() {
			ItemQueryExecutor ibo = new ItemQueryExecutor(pub_ctxt);
			query_option.itemClass = "student";
			List result = ibo.getAdvItemList(query_option, page_info);
			
			// 显示调试信息
			debugOutputQuery(ibo.getAdvItemQueryHelper());
			return result;
		}

		public String getBuiltinName() {
			return DEFAULT_TEMPLATE;
		}		
	}
	
	/**
	 * 显示学生工作系统中的研究生列表
	 */
	private static final class ShowRStudentListHandler3 extends AbstractShowItemListHandler3 {
		private static final String DEFAULT_TEMPLATE = ".builtin.showrstudentlist";

		@Override
		protected ShowRStudentListHandler3 newInstance() {
			return new ShowRStudentListHandler3();
		}

		@Override
		public int handleLabel() {
			return super.handleLabel();
		}

		@Override
		protected void setLocalContextVariable(LocalContext local_ctxt) {
			super.setLocalContextVariable(local_ctxt);
			local_ctxt.setVariable("rstudent_list", super.item_list);
		}

		@Override
		protected Object getData() {
			ItemQueryExecutor ibo = new ItemQueryExecutor(pub_ctxt);
			query_option.itemClass = "rstudent";
			List result = ibo.getAdvItemList(query_option, page_info);
			
			// 显示调试信息
			debugOutputQuery(ibo.getAdvItemQueryHelper());
			return result;
		}

		public String getBuiltinName() {
			return DEFAULT_TEMPLATE;
		}		
	}

	/**
	 * 显示图片列表
	 */
	static final class ShowPhotoListHandler3 extends AbstractShowItemListHandler3 {
		private static final String DEFAULT_TEMPLATE = ".builtin.showphotolist";

		@Override
		public int handleLabel() {
			return super.handleLabel();
		}

		@Override
		protected void buildQueryOption() {
			super.buildQueryOption();
			
			// ShowPhotoList 限定了项目类型必须是 'photo' 这样可以优化查询。
			super.query_option.itemClass = "photo";
		}

		@Override
		protected Object getData() {
			ItemQueryExecutor ibo = new ItemQueryExecutor(pub_ctxt);
			List result = ibo.getAdvItemList(query_option, page_info);
			debugOutputQuery(ibo.getAdvItemQueryHelper());
			return result;
		}

		@Override
		protected void setLocalContextVariable(LocalContext local_ctxt) {
			super.setLocalContextVariable(local_ctxt);
			local_ctxt.setVariable("photo_list", super.item_list);
		}

		public String getBuiltinName() {
			return DEFAULT_TEMPLATE;
		}
	}

	/**
	 * 显示下载列表。(ShowSoftListHandler 的改进版本3)
	 */
	static final class ShowSoftListHandler3 extends AbstractShowItemListHandler3 {
		private static final String DEFAULT_TEMPLATE = ".builtin.showsoftlist";

		@Override
		public int handleLabel() {
			return super.handleLabel();
		}

		@Override
		protected void buildQueryOption() {
			super.buildQueryOption();
			
			// ShowSoftList 限定了项目类型必须是 'soft' 这样可以优化查询。
			super.query_option.itemClass = "soft";
		}

		@Override
		protected Object getData() {
			ItemQueryExecutor ibo = new ItemQueryExecutor(pub_ctxt);
			List result = ibo.getAdvItemList(query_option, page_info);
			debugOutputQuery(ibo.getAdvItemQueryHelper());

			return result;
		}

		@Override
		protected void setLocalContextVariable(LocalContext local_ctxt) {
			super.setLocalContextVariable(local_ctxt);
			local_ctxt.setVariable("soft_list", super.item_list);
		}

		@Override
		public String getBuiltinName() {
			return DEFAULT_TEMPLATE;
		}
	}

	/**
	 * 提供给 ShowPicArticleList, ShowPicSoftList 做为基类，其提供公共的部分服务。公共部分包括：buildLabelOption() 中提供缺省 picWidth, picHeight
	 */
	private static abstract class AbstractShowPicItemHandler extends AbstractShowItemListHandler3 {
		@Override
		protected void buildLabelOption() {
			super.buildLabelOption();

			AttributeCollection coll = label.getAttributes();
			// 额外添加 本标签使用的 picWidth(图形宽度，缺省为 240), picHeight(图形高度，缺省为 180) 参数。
			Integer picWidth = coll.safeGetIntAttribute("picWidth", 0);
			Integer picHeight = coll.safeGetIntAttribute("picHeight", 0);
			if (picWidth == 0 && picHeight == 0) {
				picWidth = 240;
				picHeight = 180;
			} else if (picWidth == 0 && picHeight > 0)
				picWidth = null; // 等高。
			else if (picWidth > 0 && picHeight == 0)
				picHeight = null; // 等宽。
			else if (picWidth < 0 && picHeight < 0)
				picWidth = picHeight = null; // 不设置高度宽度，导致使用图片原始大小。
			// else 使用用户指定的大小

			options.put("picWidth", picWidth);
			options.put("picHeight", picHeight);
		}
	}

	/**
	 * 显示图片新闻列表。(ShowPicArticleListHandler 的改进版本3) 
	 */
	static class ShowPicArticleListHandler3 extends AbstractShowPicItemHandler {
		private static final String DEFAULT_TEMPLATE = ".builtin.showpicarticlelist";

		@Override
		public int handleLabel() {
			return super.handleLabel();
		}

		@Override
		protected void buildQueryOption() {
			super.buildQueryOption();
			// ShowPicArticleList 限定了项目类型必须是 'article' 这样可以优化查询。
			super.query_option.itemClass = "article";
		}

		@Override
		protected Object getData() {
			ItemQueryExecutor ibo = new ItemQueryExecutor(pub_ctxt);
			List result = ibo.getPicArticleList(query_option, page_info);
			debugOutputQuery(ibo.getAdvItemQueryHelper());

			return result;
		}

		public String getBuiltinName() {
			return DEFAULT_TEMPLATE;
		}
	}

	/**
	 * 使用 Flash 显示带有图片的新闻, 其是 ShowPicArticleListHandler3 的一个特定版本
	 */
	static final class FlashNewsHandler extends ShowPicArticleListHandler3 {
		private static final String DEFAULT_TEMPLATE = ".builtin.flashnews";

		/** 重载选项部分，加入 Pic 所需选项 */
		@Override
		public void buildLabelOption() {
			super.buildLabelOption();

			AttributeCollection coll = label.getAttributes();
			// itemNum - 缺省 3 个.
			int itemNum = coll.safeGetIntAttribute("itemNum", 3);
			if (itemNum <= 0)
				itemNum = 3;
			super.query_option.itemNum = itemNum;
			options.put("itemNum", itemNum);

			// FlashNews 不支持分页, 去掉分页属性
			page_info.setCurrPage(1);
			page_info.setPageSize(itemNum);
			options.put("usePage", false);
		}

		@Override
		public String getBuiltinName() {
			return DEFAULT_TEMPLATE;
		}
	}

	/**
	 * 显示图片下载列表. (ShowPicSoftListHandler 的改进版本3)
	 */
	static final class ShowPicSoftListHandler3 extends AbstractShowPicItemHandler {
		private static final String DEFAULT_TEMPLATE = ".builtin.showpicsoftlist";

		@Override
		public int handleLabel() {
			return super.handleLabel();
		}

		@Override
		protected void buildQueryOption() {
			super.buildQueryOption();
			// #ShowPicSoftList 限定了项目类型必须是 'soft' 这样可以优化查询。
			super.query_option.itemClass = "soft";
		}

		@Override
		protected Object getData() {
			ItemQueryExecutor ibo = new ItemQueryExecutor(pub_ctxt);
			List result = ibo.getPicSoftList(query_option, page_info);
			debugOutputQuery(ibo.getAdvItemQueryHelper());
			return result;
		}

		public String getBuiltinName() {
			return DEFAULT_TEMPLATE;
		}
	}
	
}
