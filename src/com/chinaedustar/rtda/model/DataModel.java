package com.chinaedustar.rtda.model;

import com.chinaedustar.rtda.simple.NullData;

/**
 * 定义一个标记接口，其代表该内存数据能够被方便和统一的方法的访问到。
 * 
 * 接口派生的层次：
 * <pre>
 *  DataModel - 数据模型根接口。
 *    ScalarDataModel - 简单标量属性的数据访问模型。
 *      StringDataModel - 表示一个字符串量的数据访问模型。
 *      BooleanDataModel - 表示一个布尔量的数据访问模型。
 *      NumberDataModel - 表示一个数字量的数据访问模型。
 *      DatetimeDataModel - 表示一个日期量的数据访问模型。
 *    HashDataModel - 使用指定 key 访问属性的数据访问模型。
 *      * HashExDataModel - (optional) 提供访问 size(), keys(), values() 的能力的数据访问模型。
 *    SequenceDataModel - 提供按索引访问数据能力的数据访问模型。 ([] oper)
 *    CollectionDataModel - 提供按照集合枚举方式访问数据的数据访问模型。 (iterator)
 *    * TreeDataModel - (optional) 按照树结构来访问数据的数据访问模型。
 *      * XmlDomDataModel - (optional) XML DOM 数据访问模型。
 *    MethodDataModel - 能够对 '()' 函数运算子进行运算的数据访问模型。 (() oper)
 *      * MethodExDataModel - (optional)具有扩展调用能力的数据访问模型。
 *    * TransformDataModel - (not impl) 调用，当前我们不是这种实现方式。
 * </pre>
 * 注： * 当前没有定义完整的接口。
 * 
 * @author liujunxing
 */
public interface DataModel {
	/** 一个 null 的替代。 */
	public static final DataModel NULL = NullData.NULL;
	
	/**
	 * 返回被包装起来的对象本身。可以返回自己。
	 * @return
	 */
	public Object unwrap();
}
