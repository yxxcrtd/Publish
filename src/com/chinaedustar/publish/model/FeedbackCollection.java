package com.chinaedustar.publish.model;

import java.sql.SQLException;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.chinaedustar.publish.PublishUtil;

/**
 * 网站留言的集合类
 */
@SuppressWarnings("rawtypes")
public class FeedbackCollection extends AbstractPublishModelBase implements PublishModelObject {
	/**
	 * 得到指定的反馈对象。
	 * @param id 反馈的标识。
	 * @return
	 */
	public Feedback getFeedback(int id) {
		Feedback feedback = null;
		if(id != 0){
			feedback = (Feedback)pub_ctxt.getDao().get(Feedback.class, id);
		}else{
			feedback = new Feedback();
		}
		feedback._init(pub_ctxt, this);
		return feedback;
	}
	
	/**
	 * 统计回复留言的总数（根据状态来统计）
	 * @param status 留言的状态，-1（或者不为0、1）：全部留言；0：未审核的留言；1：审核通过的留言。
	 * @param feedbackId 主文档Id。
	 * @return
	 */
	public long getReplyFeedbackAllCount(int feedbackId, int status) {
		String hql = "select count(id) from Feedback";
		if (status == -1) {
			hql += " where mainFlag=0 and mainId="+feedbackId;
		} else if (status == 0 || status == 1) {
			hql += " where mainFlag=0 and status=" + status +"and mainId="+feedbackId;
		} else {
			// 得到全部的留言总数。
		}
		List list = pub_ctxt.getDao().list(hql);
		if (list == null || list.size() == 0) return 0;
		return PublishUtil.safeGetLongVal(list.get(0));
	}
	
	/**
	 * 最后一条回复留言
	 * @param feedbackId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Feedback getReplyFeedback(int feedbackId) {
		String hql = "from Feedback";
		hql += " where mainId="+feedbackId +" order by id desc";
		List<Feedback> fdlist = pub_ctxt.getDao().list(hql);
		Feedback feedback = null;
		if(!fdlist.isEmpty()){
			int maxId = (Integer)fdlist.get(0).getId();
			feedback = getFeedback(maxId);
		}else{
			hql = "from Feedback where id ="+feedbackId;
			fdlist = pub_ctxt.getDao().list(hql);
			feedback = (Feedback)fdlist.get(0);
		}
		
		
		return feedback;
	}
	
	
	/**
	 * 统计留言的总数（根据状态来统计）
	 * @param status 留言的状态，-1（或者不为0、1）：全部留言；0：未审核的留言；1：审核通过的留言。
	 * @return
	 */
	public long getFeedbackAllCount(int status) {
		String hql = "select count(*) from Feedback";
		if (status == -1) {
			// 得到全部的留言总数。
		} else if (status == 0 || status == 1) {
			hql += " where status=" + status;
		} else {
			// 得到全部的留言总数。
		}
		List list = pub_ctxt.getDao().list(hql);
		if (list == null || list.size() == 0) return 0;
		return PublishUtil.safeGetLongVal(list.get(0));
	}
	
	/**
	 * 显示出指定选项的留言信息，有分页显示。
	 * @param status 留言的状态，-1(或者不为0、1)：显示全部；0：未审核；1：审核通过。
	 * @param mainFalg 是否主文档，1为主文档，0为回复。
	 * @param obId,某条留言和它的回复。
	 * @param page 当前页码。
	 * @param maxPerPage 每页做大记录数。
	 * @param field 搜索字段。
	 * @param keyword 关键字。
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Feedback> getFeedbackList(final int status, final Integer mainFlag, final Integer obId,  final String orderBy, final Integer page, final Integer maxPerPage, final String field, final String keyword) {
		List<Feedback> list = (List<Feedback>)pub_ctxt.getDao().getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				String hql = "from Feedback where 1=1";
				if (status == 0 || status == 1) {
					hql += " and status=" + status;
				}
				if (mainFlag != -1) {
					hql += " and mainFlag=" + mainFlag;
				}
				if (obId != null) {
					hql += " and (id=" + obId +" or mainId ="+ obId +")";
				}
				if (field != null && field.trim().length() > 0 && keyword != null) {
					hql += " and " + field + " like :keyword";
				}
				if (orderBy == null || orderBy.trim().length() < 1) {
					hql += " order by id desc";
				} else {
					hql += " order by " + orderBy;
				}
				Query query = session.createQuery(hql);
				if (field != null && field.trim().length() > 0 && keyword != null) {
					query.setString("keyword", "%" + keyword + "%");
				}
				query.setFirstResult((page - 1) * maxPerPage);
				query.setMaxResults(maxPerPage);
				return query.list();
			}
		});
		for (int i = 0; i < list.size(); i++) {
			list.get(i)._init(pub_ctxt, this);
		}
		return list;
	}
	
	/**
	 * 得到指定选项的留言总数，参数设置与上面的方法 getFeedbackList 一致。
	 * @param status 留言的状态，-1(或者不为0、1)：显示全部；0：未审核；1：审核通过。
	 * @param page 当前页码。
	 * @param maxPerPage 每页做大记录数。
	 * @param field 搜索字段。
	 * @param keyword 关键字。
	 * @return
	 */
	public long getFeedbackListCount(final int status, final Integer mainFlag, final Integer obId, final int page, final Integer maxPerPage, final String field, final String keyword) {
		Object val = pub_ctxt.getDao().getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				String hql = "select count(*) from Feedback where 1=1";
				if (status == 0 || status == 1) {
					hql += " and status=" + status;
				}
				if (mainFlag != -1) {
					hql += " and mainFlag=" + mainFlag;
				}
				if (obId != null) {
					hql += " and (id=" + obId +" or mainId ="+ obId +")";
				}
				if (field != null && field.trim().length() > 0 && keyword != null) {
					hql += " and " + field + " like :keyword";
				}
				Query query = session.createQuery(hql);
				if (field != null && field.trim().length() > 0 && keyword != null) {
					query.setString("keyword", "%" + keyword + "%");
				}
				return query.list().get(0);
			}
		});
		
		return PublishUtil.safeGetLongVal(val);
	}
	
	/**
	 * 创建、保存反馈信息。
	 * @param feedback 发聩对象。
	 */
	public void insertFeedback(Feedback feedback) {
		pub_ctxt.getDao().save(feedback);
	}
	
	/**
	 * 更新反馈信息。
	 * @param feedback 反馈对象。
	 */
	public void updateFeedback(Feedback feedback) {
		pub_ctxt.getDao().update(feedback);
	}
	
	/**
	 * 更新指定留言的状态，0：未审核；1：审核。
	 * @param feedbackIds
	 * @param status
	 */
	public void updateFeedbackStatus(final int[] feedbackIds, final int status) {
		pub_ctxt.getDao().getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				for (int i = 0; i < feedbackIds.length; i++) {
					final int feedbackId = feedbackIds[i];
					String hql = "update Feedback set status=" + status + " where id=" + feedbackId;
					session.createQuery(hql).executeUpdate();
				}
				return null;
			}
		});
	}

	/**
	 * 删除指定的留言。
	 * @param id 留言的标识。
	 */
	public int deleteFeedback(final int id) {
		return (Integer)pub_ctxt.getDao().getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				String hql = "delete from Feedback where id=" + id;
				return session.createQuery(hql).executeUpdate();
			}
		});
	}
}
