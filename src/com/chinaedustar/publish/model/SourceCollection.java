package com.chinaedustar.publish.model;

import java.util.List;

import com.chinaedustar.publish.DataAccessObject;
import com.chinaedustar.publish.PublishException;
import com.chinaedustar.publish.PublishUtil;
import com.chinaedustar.publish.util.QueryHelper;

/**
 * 来源集合对象。
 * @author 
 */
public class SourceCollection extends AbstractCollWithContainer {
	/**
	 * 得到指定的来源对象。
	 * @param sourceId 来源的标识。
	 * @return
	 */
	public Source getSource(int sourceId) {
		Source source = (Source)pub_ctxt.getDao().get(Source.class, sourceId);
		if (source != null)
			source._init(pub_ctxt, this);
		return source;
	}

	/**
	 * 得到指定的来源信息。
	 * @param sourceType 来源的类型。
	 * @param field 搜索的字段。
	 * @param keyword 搜索的关键字。
	 * @param page_info 分页信息。
	 * @return List&lt;Source&gt;
	 */
	public List<Source> getSourceList(int sourceType, String field, String keyword, PaginationInfo page_info) {
		// 构造查询语句。
		int channelId = container.getChannelId();
		QueryHelper qh = new QueryHelper();
		qh.fromClause = " FROM Source ";
		qh.whereClause = " WHERE channelId = " + channelId;
		if (sourceType > 0)
			qh.whereClause += " AND sourceType = " + sourceType;
		if (field != null && field.trim().length() > 0) {
			qh.whereClause += " AND " + field + " LIKE :keyword ";
			qh.setString("keyword", "%" + keyword + "%");
		}
		qh.orderClause = " ORDER BY id DESC ";

		// 查询数量。
		page_info.setTotalCount(qh.queryTotalCount(pub_ctxt.getDao()));
		
		// 查询来源。
		@SuppressWarnings("unchecked")
		List<Source> result = qh.queryData(pub_ctxt.getDao(), page_info);
		PublishUtil.initModelList(result, pub_ctxt, this);
		
		return result;
	}
	
	/**
	 * 创建来源对象。
	 * 注意：需要事务支持。
	 * @param source
	 */
	public void insertSource(Source source) {
		if (source.getId() > 0) {
			updateSource(source);
		} else {
			_getPublishContext().getDao().save(source);
			source._init(_getPublishContext(), this);
		}
	}

	/**
	 * 更新来源对象。
	 * 注意：需要事务支持。
	 * @param source
	 */
	public void updateSource(Source source) {
		if (source.getId() > 0) {
			_getPublishContext().getDao().update(source);
			source._init(_getPublishContext(), this);
		} else {
			insertSource(source);
		}
	}

	/**
	 * 删除指定的来源。
	 * 注意：需要事务支持。
	 * @param sourceId
	 */
	public void deleteSource(int sourceId) {
		Source source = this.getSource(sourceId);
		if (source == null) {
			throw new PublishException("指定标识为 " + sourceId + " 的专题不存在。");
		}
		// 删除专题。
		super._getPublishContext().getDao().delete(source);
		// 销毁专题。
		if (source != null) {
			source._destroy();
		}
	}

	/**
	 * 删除指定频道下的所有来源对象。
	 * @param dao
	 * @param channelId
	 */
	public static final void deleteChannelSources(DataAccessObject dao, int channelId) {
		String hql = "DELETE FROM Source WHERE channelId = " + channelId;
		dao.bulkUpdate(hql);
	}
}
