package com.chinaedustar.publish.biz;

import java.util.List;

import com.chinaedustar.publish.DataAccessObject;
import com.chinaedustar.publish.PublishContext;
import com.chinaedustar.publish.PublishUtil;
import com.chinaedustar.publish.model.PaginationInfo;

/**
 * BizName 的业务集合类
 * 
 * @author liujunxing
 */
@SuppressWarnings("rawtypes")
public class BizNameCollection implements BizConst {
	private PublishContext pub_ctxt;
	
	/**
	 * 构造函数。
	 * @param pub_ctxt
	 */
	public BizNameCollection(PublishContext pub_ctxt) {
		this.pub_ctxt = pub_ctxt;
	}
	
	/** 获得 Biz 表格的访问对象。 */
	private DataAccessObject getBizDao() {
		return (DataAccessObject)pub_ctxt.getSpringContext().getBean(BIZ_DAO);
	}
	
	/**
	 * 获得所有 BizName 数据，用于管理。
	 * @return
	 */
	public List getBizNameList() {
		List result = getBizDao().list("FROM BizName");
		return result;
	}

	/**
	 * 获得指定标识的业务连接对象。
	 * @param biz_id
	 * @return
	 */
	public BizName getBizName(int biz_id) {
		BizName biz_name = (BizName)getBizDao().get(BizName.class, biz_id);
		return biz_name;
	}
	
	/**
	 * 获得指定业务连接下的用户列表。
	 * @param biz_name
	 * @return
	 */
	public List getBizUserList(BizName biz_name) {
		// 得到所有用户标识。
		List<Integer> ids = safeParseIds(biz_name.getUserList());
		
		// 从用户表中读取信息。 - List<Map>
		UserDao user_dao = (UserDao)pub_ctxt.getSpringContext().getBean(BizConst.BIZ_DAO_USER_BEAN);
		List result = user_dao.getUserInfo(ids);
		
		return result;
	}
	
	/**
	 * 获得指定业务连接下的群组列表。
	 * @param biz_name
	 * @return
	 */
	public List getBizGroupList(BizName biz_name) {
		// 得到所有群组标识。
		List<Integer> ids = safeParseIds(biz_name.getGroupList());
		
		// 从群组表里面读取信息 - List<Map>
		UserDao user_dao = (UserDao)pub_ctxt.getSpringContext().getBean(BizConst.BIZ_DAO_USER_BEAN);
		List result = user_dao.getGroupInfo(ids);
		
		return result;
	}

	/**
	 * 按照分页获取用户信息。
	 * @param page_info
	 * @return
	 */
	public Object getSelectUserList(PaginationInfo page_info) {
		SelectUserDao dao = (SelectUserDao)pub_ctxt.getSpringContext().getBean(BizConst.BIZ_SELECT_DAO_BEAN);
		Object result = dao.getSelectUserList(page_info);
		return result;
	}
	
	public Object getSelectGroupList(PaginationInfo page_info) {
		SelectUserDao dao = (SelectUserDao)pub_ctxt.getSpringContext().getBean(BizConst.BIZ_SELECT_DAO_BEAN);
		Object result = dao.getSelectGroupList(page_info);
		return result;
	}

	/**
	 * 保存/更新一个业务连接。
	 * @param biz_name
	 */
	public void saveBizName(BizName biz_name) {
		getBizDao().save(biz_name);
	}
	
	/**
	 * 删除一个业务连接。
	 * @param bizId
	 */
	public void deleteBizName(int bizId) {
		String hql = "DELETE FROM BizName WHERE id = " + bizId;
		getBizDao().bulkUpdate(hql);
	}
	
	/**
	 * 给一个业务中添加用户。
	 *
	 */
	public void linkUser(BizName biz_name, List<Integer> user_ids) {
		// 获得原来的用户列表。
		List<Integer> origin_user_ids = safeParseIds(biz_name.getUserList());
		// 执行加法。
		List<Integer> result = plusIds(origin_user_ids, user_ids);
		
		biz_name.setUserList(PublishUtil.toSqlInString(result));
		
		// 更新记录。
		getBizDao().update(biz_name);
	}
	
	/**
	 * 从一个业务连接中去掉指定的用户s。
	 * @param biz_name
	 * @param user_ids
	 */
	public void unlinkUser(BizName biz_name, List<Integer> user_ids) {
		// 获得原来的用户列表。
		List<Integer> origin_user_ids = safeParseIds(biz_name.getUserList());
		// 执行减法。
		minusIds(origin_user_ids, user_ids);
		
		biz_name.setUserList(PublishUtil.toSqlInString(origin_user_ids));
		
		// 更新记录。
		getBizDao().update(biz_name);
	}

	/**
	 * 给一个业务中添加群组。
	 *
	 */
	public void linkGroup(BizName biz_name, List<Integer> group_ids) {
		// 获得原来的群组列表。
		List<Integer> origin_group_ids = safeParseIds(biz_name.getGroupList());
		// 执行加法。
		List<Integer> result = plusIds(origin_group_ids, group_ids);
		
		biz_name.setGroupList(PublishUtil.toSqlInString(result));
		
		// 更新记录。
		getBizDao().update(biz_name);
	}

	/**
	 * 从一个业务连接中去掉指定的群组s。
	 * @param biz_name
	 * @param group_ids
	 */
	public void unlinkGroup(BizName biz_name, List<Integer> group_ids) {
		// 获得原来的群组列表。
		List<Integer> origin_group_ids = safeParseIds(biz_name.getUserList());
		// 执行减法。
		minusIds(origin_group_ids, group_ids);
		
		biz_name.setGroupList(PublishUtil.toSqlInString(origin_group_ids));
		
		// 更新记录。
		getBizDao().update(biz_name);
	}

	/** 将一个字符串用 ',' 分隔的标识处理为 List[Integer] */
	public static List<Integer> safeParseIds(String id_list) {
		List<Integer> ids = new java.util.ArrayList<Integer>();
		if (id_list == null || id_list.length() == 0) return ids;
		String[] val_split = id_list.split(",");
		for (int j = 0; j < val_split.length; ++j) {
			try {
				ids.add(Integer.parseInt(val_split[j]));
			} catch (NumberFormatException ex) {
				// ignore
			}
		}
		return ids;
	}

	/**
	 * 执行 a1 + a2 操作，返回一个新的集合。
	 * @param a1
	 * @param a2
	 */
	public static List<Integer> plusIds(List<Integer> a1, List<Integer> a2) {
		List<Integer> result = new java.util.ArrayList<Integer>();
		if (a1 != null)
			result.addAll(a1);
		if (a2 == null || a2.size() == 0) return result;
		for (int i = 0; i < a2.size(); ++i) {
			Integer o = a2.get(i);
			if (result.contains(o) == false)
				result.add(o);
		}
		return result;
	}
	
	/**
	 * 执行 a1 - a2 操作，结果仍然放在 a1 中。
	 * @param a1
	 * @param a2
	 */
	public static void minusIds(List<Integer> a1, List<Integer> a2) {
		if (a1 == null || a1.size() == 0) return;
		if (a2 == null || a2.size() == 0) return;
		a1.removeAll(a2);
	}
}

