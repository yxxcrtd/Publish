package com.chinaedustar.publish.model;

import java.util.Date;

import com.chinaedustar.publish.PublishContext;
import com.chinaedustar.publish.util.QueryHelper;

/**
 * 具有更多扩展搜索条件的 ItemQueryOption.
 * 
 * @author liujunxing
 *
 */
public class ItemQueryOptionEx extends ItemQueryOption {
	/** 要查找的项目标题, = null 表示不限定 */
	public String title;
	
	/** 项目描述。 */
	public String description;
	
	/** 录入者, 实现在基类。 */
	// public String inputer;
	
	/** 来源。 */
	public String source;
	
	/** 项目的关键字，注意，区别于keyword。 */
	public String keywords;
	
	/** 最大点数，当前不支持 */
	public int maxPoint;
	
	/** 最小点数，当前不支持 */
	public int minPoint;

	/** 开始时间 */
	public Date beginDate;
	
	/** 结束时间 */
	public Date endDate;

	// ************** 文章独有的搜索字段
	/** 文章的内容 */
	public String articleContent;

	// ************** 图片独有的搜索字段

	// ************** 软件独有的搜索字段
	/** 软件的语言 */
	public String softLanguage;
	
	/** 软件的类别 */
	public String softType;
	
	/** 软件的版本 */
	public String softVersion;
	
	/** 软件的授权 */
	public String softCopyright;

	/*
	 * (non-Javadoc)
	 * @see com.chinaedustar.publish.model.ItemQueryOption#initQueryHelper(com.chinaedustar.publish.util.QueryHelper, com.chinaedustar.publish.PublishContext)
	 */
	public QueryHelper initQueryHelper(QueryHelper query, PublishContext pub_ctxt) {
		// 先让基类初始化 QueryHelper. 
		super.initQueryHelper(query, pub_ctxt);
		
		// 设置我们支持的新条件。
		// title
		if (isEmpty(title) == false) {
			query.addAndWhere("title LIKE :title");
			query.setString("title", "%" + this.title + "%");
		}
		
		// description
		if (isEmpty(description) == false) {
			query.addAndWhere("description LIKE :description");
			query.setString("description", "%" + description + "%");
		}
		
		// author, inputer - support by parent class
		
		// source
		if (isEmpty(source) == false) {
			query.addAndWhere("source = :source");
			query.setString("source", source);
		}
		
		// keywords - 注意区分 keyword, keywords.
		if (isEmpty(keywords) == false) {
			query.addAndWhere("keywords LIKE :keywords");
			query.setString("keywords", keywords);
		}
		
		// maxPoint, minPoint - 当前不支持
		
		// beginDate, endDate
		if (null != beginDate && null != endDate
				&& (beginDate.equals(endDate) || beginDate.before(endDate))) {
			query.addAndWhere("(createTime >= :beginDate and createTime <= :endDate)");
			query.setTimestamp("beginDate", beginDate);
			query.setTimestamp("endDate", endDate);
		}
		
		// 某些项目特有的条件
		if ("article".equalsIgnoreCase(super.itemClass)) 
			buildArticleQuery(query);
		else if ("photo".equalsIgnoreCase(super.itemClass))
			buildPhotoQuery(query);
		else if ("soft".equalsIgnoreCase(super.itemClass))
			buildSoftQuery(query);
		
		return query;
	}
	
	// 文章特有的查询。
	private void buildArticleQuery(QueryHelper query) {
		if (isEmpty(articleContent) == false) {
			query.addAndWhere("content LIKE :content");
			query.setString("content", "%" + articleContent + "%");
		}
	}
	
	// 图片特有的查询。
	private void buildPhotoQuery(QueryHelper query) {
		// 当前没有
	}
	
	// 软件特有的查询。
	private void buildSoftQuery(QueryHelper query) {
		if (isEmpty(this.softCopyright) == false) {
			query.addAndWhere("copyrightType = :copyrightType");
			query.setString("copyrightType", softCopyright);
		}
		if (isEmpty(this.softLanguage) == false) {
			query.addAndWhere("language = :language");
			query.setString("language", this.softLanguage);
		}
		if (isEmpty(this.softType) == false) {
			query.addAndWhere("type = :type");
			query.setString("type", this.softType);
		}
		if (isEmpty(this.softVersion) == false) {
			query.addAndWhere("version LIKE :version");
			query.setString("version", this.softVersion);
		}
	}
	
	public static boolean isEmpty(String v) {
		if (v == null) return true;
		if (v.length() == 0) return true;
		if (v.trim().length() == 0) return true;
		return false;
	}
}
