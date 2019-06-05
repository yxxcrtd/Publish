package com.chinaedustar.publish.biz;

import java.util.List;

import com.chinaedustar.publish.model.PaginationInfo;

/**
 * 管理时候用到的用户、群组信息获取接口定义。
 * @author liujunxing
 *
 */
public interface SelectUserDao {
	/**
	 * 按照指定分页参数获取用户信息。
	 * @param page_info
	 * @return
	 */
	public Object getSelectUserList(PaginationInfo page_info);
	
	/**
	 * 按照指定分页参数获取群组信息。
	 * @param page_info
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public List getSelectGroupList(PaginationInfo page_info);
}
