package com.chinaedustar.publish.model;

import java.util.List;

import com.chinaedustar.common.util.StringHelper;
import com.chinaedustar.publish.DataAccessObject;
import com.chinaedustar.publish.PublishException;
import com.chinaedustar.publish.PublishUtil;
import com.chinaedustar.publish.util.QueryHelper;
import com.chinaedustar.publish.util.UpdateHelper;

/**
 * 关键字对象的集合对象，可以属于频道和网站。
 * @author wangyi
 *
 */
public class KeywordCollection extends AbstractCollWithContainer {
	/**
	 * 得到关键字的集合， List<Keyword> 。
	 * @param field 搜索的字段。
	 * @param keyword 搜索的关键字。
	 * @param page 当前页码。
	 * @param maxPerPage 每页最大记录数。
	 * @return
	 */
	public List<Keyword> getKeywordList(String field, String keyword, PaginationInfo page_info) {
		// 构造查询。
		QueryHelper qh = new QueryHelper();
		qh.fromClause = " FROM Keyword ";
		qh.whereClause = " WHERE channelId = " + container.getChannelId();
		if (field != null && field.trim().length() > 0) {
			qh.whereClause += " and " + field + " like :keyword";
			qh.setString("keyword", "%" + keyword + "%");
		}
		qh.orderClause += " ORDER BY id desc ";

		// 查询。
		page_info.setTotalCount(qh.queryTotalCount(pub_ctxt.getDao()));
		@SuppressWarnings("unchecked")
		List<Keyword> keyword_list = (List<Keyword>)qh.queryData(pub_ctxt.getDao(), page_info);
		
		PublishUtil.initModelList(keyword_list, pub_ctxt, this);
		
		return keyword_list;
	}
	
	/**
	 * 获取最近使用的关键字,List<String>
	 * @param count 需要的关键字个数
	 * @return List&lt;String&gt;, 其中每一项是一个关键字。
	 */
	public List<String> getLatestUsedKeywords(int count) {
		int channelId = container.getChannelId();
		
		// 使用 QueryHelper 帮助查询。 
		// hql = "SELECT name FROM Keyword WHERE channelId = :channelId ORDER BY lastUseTime DESC ";
		QueryHelper qh = new QueryHelper();
		qh.selectClause = "SELECT name ";
		qh.fromClause = " FROM Keyword ";
		qh.whereClause = " WHERE channelId = :channelId ";
		qh.setInteger("channelId", channelId);
		qh.orderClause = " ORDER BY lastUseTime DESC ";
		
		@SuppressWarnings("unchecked")
		List<String> result = qh.queryData(pub_ctxt.getDao(), -1, count);
		return result;
	}
	
	
	/**
	 * 得到指定的关键字对象。
	 * @param keywordId 关键字的标识。
	 * @return
	 */
	public Keyword getKeyword(int keywordId) {
		Keyword keyword = (Keyword)pub_ctxt.getDao().get(Keyword.class, keywordId);
		if (keyword != null)
			keyword._init(pub_ctxt, this);

		return keyword;
	}
	
	/**
	 * 获取相同的关键字对象。
	 * @param keyword 现有关键字对象。
	 * @return
	 */
	@Deprecated
	public Keyword getSameKeyword(Keyword keyword) {
		throw new UnsupportedOperationException();
	}
	
	/**
	 * 创建关键字对象。
	 * 注意：需要事务支持。
	 * @param keyword
	 */
	public void insertKeyword(Keyword keyword) {
		if (keyword.getId() > 0) {
			updateKeyword(keyword);
		} else {
			// TODO: 检查是否有相同的关键字
			Keyword oKeyword = getSameKeyword(keyword);
			
			if (oKeyword != null) {
				updateKeyword(oKeyword);
			} else {								
				_getPublishContext().getDao().save(keyword);
				keyword._init(_getPublishContext(), this);
			}
		}
	}
	
	/**
	 * 更新关键字对象。
	 * 注意：需要事务支持。
	 * @param keyword
	 */
	public void updateKeyword(Keyword keyword) {
		if (keyword.getId() > 0) {
			_getPublishContext().getDao().update(keyword);
			keyword._init(_getPublishContext(), this);
		} else {
			insertKeyword(keyword);
		}
	}
	
	/**
	 * 删除在指定的关键字对象。
	 * 注意：需要事务支持。
	 * @param keywordId
	 */
	public void deleteKeyword(int keywordId) {
		Keyword keyword = this.getKeyword(keywordId);
		if (keyword == null) {
			throw new PublishException("指定标识为 " + keywordId + " 的专题不存在。");
		}
		// 删除专题。
		super._getPublishContext().getDao().delete(keyword);
		// 销毁专题。
		if (keyword != null) {
			keyword._destroy();
		}
	}

	/**
	 * 批量删除关键字。
	 * @param keywordIds
	 * @return 返回删除的关键字数量。
	 */
	public int batchDeleteKeywords(List<Integer> keywordIds) {
		if (keywordIds == null || keywordIds.size() == 0) return 0;
		String ids_sql = PublishUtil.toSqlInString(keywordIds);
		String hql = "DELETE FROM Keyword WHERE id IN (" + ids_sql + ")";
		return pub_ctxt.getDao().bulkUpdate(hql);
	}
	
	/**
	 * 删除当前频道下面的所有关键字。
	 * 注意：需要事务支持。
	 * @return 更新的记录数。
	 */
	public int clearKeywords() {
		int channelId = 0;
		if (super._getOwnerObject() instanceof Channel) {
			channelId = ((Channel)super._getOwnerObject()).getId();
		}
		
		// LIUJUNXING: 这里需要做下测试。
		String hql = "delete from Keyword where channelId = " + channelId;
		int num = pub_ctxt.getDao().bulkUpdate(hql);

		return num;
	}

	/**
	 * 创建/更新项目 (Item) 的关键字列表，其通过 | 符号隔开。
	 * @param keywords
	 */
	public void createItemKeywords(String keywords) {
		if (keywords == null || keywords.length() == 0) return;
		
		StringBuilder strbuf = new StringBuilder();
		// 使用 "|" 切分关键字列表。
		String[] keyword_array = StringHelper.split(keywords, "|", true);
		if (keyword_array == null || keyword_array.length == 0) return;
		for (int i = 0; i < keyword_array.length; ++i) {
			String keyword = keyword_array[i];
			if (keyword == null) continue;
			keyword = keyword.trim();
			if (keyword.length() == 0) continue;
			
			// 首先尝试更新现有的。
			UpdateHelper updator = new UpdateHelper();
			updator.updateClause = "UPDATE Keyword SET lastUseTime = :lastUseTime " +
				" WHERE channelId = " + this.container.getChannelId() + " AND name = :name "; 
			updator.setDate("lastUseTime", new java.util.Date());
			updator.setString("name", keyword);
			int update_num = updator.executeUpdate(pub_ctxt.getDao());
			
			// 如果没有记录被更新则表示要插入一个新的。
			if (update_num == 0) {
				Keyword new_keyword = new Keyword();
				new_keyword.setName(keyword);
				new_keyword.setChannelId(this.container.getChannelId());
				new_keyword.setLastUseTime(new java.util.Date());
				pub_ctxt.getDao().save(new_keyword);
				strbuf.append("[创建关键字: ").append(keyword).append("]");
			} else {
				strbuf.append("[更新关键字: ").append(keyword).append("]");
			}
		}
		
	}
	
	/**
	 * 删除指定频道标识下的所有关键字。
	 * @param dao
	 * @param channelId
	 */
	public static final void deleteChannelKeywords(DataAccessObject dao, int channelId) {
		String hql = "DELETE FROM Keyword WHERE channelId = " + channelId;
		dao.bulkUpdate(hql);
	}
}
