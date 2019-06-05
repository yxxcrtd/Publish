package com.chinaedustar.publish.model;

import java.util.List;

import com.chinaedustar.publish.DataAccessObject;
import com.chinaedustar.publish.PublishException;
import com.chinaedustar.publish.PublishUtil;
import com.chinaedustar.publish.util.QueryHelper;

/**
 * 作者集合对象。
 * 
 * @author 
 */
public class AuthorCollection extends AbstractCollWithContainer {
	
	/**
	 * 得到指定标识的的作者。
	 * @param authorId - 作者的标识。
	 * @return
	 */
	public Author getAuthor(int authorId) {
		Author author = (Author)pub_ctxt.getDao().get(Author.class, authorId);
		if (author != null)
			author._init(pub_ctxt, this);
		return author;
	}
	
	/**
	 * 得到作者对象的集合 List<Author> 。
	 * @param authorType 作者的类型，0：不处理作者类型；1：大陆作者；2：港台作者；3：海外作者；4：本站特约；5：其它作者。
	 * @param field 搜索的字段。
	 * @param keyword 搜索的关键字。
	 * @param page_info - 分页属性。
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public List getAuthorList(int authorType, String field, String keyword, PaginationInfo page_info) {
		// 构造查询帮助器。
		int channelId = this.container.getChannelId();
		QueryHelper qh = new QueryHelper();
		qh.fromClause = " FROM Author ";
		qh.whereClause = " WHERE channelId = " + channelId;
		if (authorType > 0)
			qh.whereClause += " AND authorType = " + authorType;
		if (field != null && field.length() > 0 && keyword != null && keyword.length() > 0) {
			// TODO: 验证 field 存在。
			qh.whereClause += " AND " + field + " LIKE :keyword ";
			qh.setString("keyword", "%" + keyword + "%");
		}

		// 查询总数，查询结果。
		page_info.setTotalCount( qh.queryTotalCount(pub_ctxt.getDao()) );
		List result = qh.queryData(pub_ctxt.getDao(), page_info);
		
		PublishUtil.initModelList(result, pub_ctxt, this);
		return result;
	}

	/**
	 * 得到指定频道的作者对象集合，用于支持 ShowAuthorList 标签。
	 * @param channelId - 频道标识，-1 表示所有作者，0 表示全站作者，> 0 表示指定频道。
	 * @param num - 要获取的数量。
	 * @param author_type - 作者类型。
	 * @return 返回 List&lt;Author&gt;, 所有返回的作者对象都是审核通过的。
	 * TODO: 也许为了支持更多选项，我们需要更复杂的查询。
	 */
	public List<Author> getAuthorList(int channelId, int num, int author_type) {
		// 构造查询。
		QueryHelper query = new QueryHelper();
		query.fromClause = " FROM Author ";
		query.whereClause = " WHERE (passed = true)";
		if (channelId >= 0)
			query.whereClause += " AND (channelId = " + channelId + ") ";
		if (author_type > 0)
			query.whereClause += " AND (authorType = " + author_type + ") ";
		query.orderClause = " ORDER BY id DESC ";
		
		// 查询
		@SuppressWarnings("unchecked")
		List<Author> result = query.queryData(pub_ctxt.getDao(), -1, num);
		
		PublishUtil.initModelList(result, pub_ctxt, this);
		return result;
	}
	
	/**
	 * 获得全部或某频道的作者集合。
	 * @param channelId 0为全部频道。
	 * @param author_num为查找作者个数,-1为全部作者。
	 * @param author_type作者的类型，0：不处理作者类型；1：大陆作者；2：港台作者；3：海外作者；4：本站特约；5：其它作者。
	 * @return
	 */
	@Deprecated
	public List<Author> getAuthors(int channelId, int author_num, int author_type){
		throw new UnsupportedOperationException("Deprecated");
		/*
		List<Author> list = (List<Author>)super._getPublishContext().getDao().getHibernateTemplate().execute(new HibernateCallback(){
			public Object doInHibernate(Session session) throws HibernateException, SQLException{
				String hql = "from Author where ";
				if(channelId < 0){
					hql += " channelId > -1";
				}else {
					hql += " channelId=:channelId";
				}
				if(author_type > 0 ){
					hql += " and authorType=:authorType";
				}
				hql += " order by id desc";
				Query query = session.createQuery(hql);
				if(channelId >= 0){
					query.setInteger("channelId", channelId);
				}
				if(author_type > 0){
					query.setInteger("authorType", author_type);
				}
				if(author_num > 0){
					query.setMaxResults(author_num);
				}
				return query.list();
			}
		});
		
		if (list != null && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				list.get(i)._init(_getPublishContext(), this);
			}
		}
		
		return list;
		*/
	}
	/**
	 * 创建一个新的作者。
	 * 注意：需要事务支持：
	 * @param author
	 */
	public void insertAuthor(Author author) {
		if (author.getId() == 0) {
			_getPublishContext().getDao().save(author);
			author._init(_getPublishContext(), this);
		} else {
			updateAuthor(author);
		}
	}
	/**
	 * 更新一个作者。
	 * 注意：需要事务支持。
	 * @param author
	 */
	public void updateAuthor(Author author) {
		if (author.getId() < 1) {
			insertAuthor(author);
		} else {
			_getPublishContext().getDao().update(author);
			author._init(_getPublishContext(), this);
		}
	}
	
	/**
	 * 删除一个作者。
	 * 注意：需要事务支持。
	 * @param authorId 作者的标识。
	 */
	public void deleteAuthor(int authorId) {
		Author author = getAuthor(authorId);
		if (author == null) {
			throw new PublishException("指定标识为 " + authorId + " 的作者不存在。");
		}
		// 删除。
		super._getPublishContext().getDao().delete(author);
		// 销毁。
		if (author != null) {
			author._destroy();
		}
	}

	/**
	 * 删除指定频道标识的所有作者。
	 * @param dao
	 * @param channelId
	 */
	public static final void deleteChannelAuthors(DataAccessObject dao, int channelId) {
		String hql = "DELETE FROM Author WHERE channelId = " + channelId;
		dao.bulkUpdate(hql);
	}
}
