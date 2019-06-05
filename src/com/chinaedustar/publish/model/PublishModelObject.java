package com.chinaedustar.publish.model;

import com.chinaedustar.publish.PublishContext;

/**
 * 定义发布系统模型数据的接口。这是内部使用的接口，所以方法都加上了 _ 开始，不希望其被外部模板访问到
 */
public interface PublishModelObject {
	
	/**
	 * 获得当前此模型对象绑定的环境对象
	 * 
	 * @return - 返回此对象所绑定的发布系统环境对象，返回必须不为空。
	 */
	public PublishContext _getPublishContext();
	
	/**
	 * 获得此对象的拥有者对象。
	 * @return - 返回此对象的拥有者对象，如果没有则返回 null。
	 */
	public PublishModelObject _getOwnerObject();
	
	/**
	 * 初始化此对象，并绑定到发布系统环境和指定的父对象。
	 *  对象调用 _init() 之后我们称之为进行了模型化(变成发布系统模型对象了)
	 *  容器要保证返回的对象已经调用了 _init()，_init()可能被调用多次，提供不同的拥有者或
	 *  进行别的处理。
	 * @param pub_ctxt - 发布系统环境对象。
	 * @param owner_obj - 拥有者对象，其可能为空。
	 */
	public void _init(PublishContext pub_ctxt, PublishModelObject owner_obj);
	
	/**
	 * 销毁此对象，取消其前面 _init() 方法调用中绑定的发布系统环境对象和父对象。
	 * 此调用之后，容器将不再保证之前 _init() 调用传递的 pub_ctxt, owner_obj 有效。
	 *   作为 _init() 的反操作, 调用 _destroy() 之后对象不是模型化对象了。
	 */
	public void _destroy();
	
}
