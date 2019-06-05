package com.chinaedustar.publish.itfc;

import com.chinaedustar.publish.PublishContext;
import com.chinaedustar.template.core.AbstractLabelElement;
import com.chinaedustar.template.core.InternalProcessEnvironment;

/**
 * 定义发布系统的标签处理器的基本接口。
 * 
 * LabelHandler.getLabelHandler() 是一个工厂方法，其返回一个新的标签处理器，绑定给定的环境信息。
 * 新返回的标签解释器的 handleLabel() 实际进行标签的解释处理。
 * 
 * @author liujunxing
 */
public interface LabelHandler {
	/** 放入 Repeater 标签 LocalContext 的数据提供者的名称 */
	public final static String REPEATER_DATA_PROVIDER_NAME = ".REPEATE_DATA_PROVIDER_SOURCE";

	/** 放入 Repeater 标签 LocalContext 的循环变量的名称, 缺省的名字是 "object" */
	public final static String REPEATER_VAR_NAME = ".REPEATER_VAR_NAME";

	/** 
	 * 放入 PageNav 标签 LocalContext 的PaginationInfo对象的名称。
	 * <p>注意：在静态化的栏目、专题列表页的时候会在 vars 中放置带有分页信息的对象。
	 * </p> 
	 */
	public final static String PAGINATION_INFO_NAME = ".PAGINATION_INFO_NAME";

	/**
	 * 获得一个在指定环境下的标签处理器。
	 * @param pub_ctxt
	 * @param env
	 * @param label
	 * @return
	 */
	public LabelHandler getLabelHandler(PublishContext pub_ctxt, InternalProcessEnvironment env, AbstractLabelElement label);
	
	/**
	 * 解析一个标签并返回结果。
	 * @param pub_ctxt - 当前发布系统环境。
	 * @param env - 模板执行环境。
	 * @param label - 标签元素。
	 * @return 返回 LABEL_PROCESS 常量，一般为 PROCESS_DEFAULT.
	 */
	public int handleLabel(PublishContext pub_ctxt, InternalProcessEnvironment env, AbstractLabelElement label);
}
