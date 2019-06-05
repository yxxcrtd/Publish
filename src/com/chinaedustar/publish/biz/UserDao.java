package com.chinaedustar.publish.biz;

import java.util.List;

/**
 * 获得群组用户信息的 DAO 接口。
 * @author liujunxing
 *
 */
@SuppressWarnings("rawtypes")
public interface UserDao {
	/**
	 * 得到指定标识的用户信息。
	 * @param ids
	 * @return 返回 List&lt;Map&gt; ，其中每个 Map 代表一个用户，字段有 
	 * 		id - 用户标识；
	 * 		name - 用户登录名，英文的；
	 * 		title - 用户姓名，一般为中文；
	 * 		email - 电子邮件地址；
	 * 		description - 简介；
	 */
	public List getUserInfo(List<Integer> ids);
	
	/**
	 * 得到指定标识的群组信息。
	 * @param ids
	 * @return 返回 List&lt;Map&gt;
	 *   	id - 群组标识
	 *   	name - 群组名字
	 *   	creator - 创建者
	 *   	description - 描述
	 */
	public List getGroupInfo(List<Integer> ids);
}
