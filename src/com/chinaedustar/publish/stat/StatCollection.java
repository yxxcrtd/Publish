package com.chinaedustar.publish.stat;

import java.util.List;

import com.chinaedustar.publish.DataAccessObject;
import com.chinaedustar.publish.PublishUtil;
import com.chinaedustar.publish.model.PaginationInfo;
import com.chinaedustar.publish.util.QueryHelper;


/**
 * 统计数据获取(使用Hibernate方式)的包装类
 * 
 * @author liujunxing
 */
@SuppressWarnings("rawtypes")
public class StatCollection {
	/**
	 * 得到指定页的访问者记录。
	 * @param page_info
	 * @return
	 */
	public List getVisitorList(PaginationInfo page_info) {
		DataAccessObject dao = PublishUtil.getPublishContext().getDao();
		QueryHelper qh = new QueryHelper();
		qh.fromClause = " FROM StatVisitor ";
		qh.orderClause = " ORDER BY visitTime DESC ";
		return qh.queryDataAndCount(dao, page_info);
	}
}
