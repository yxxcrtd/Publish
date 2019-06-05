package com.chinaedustar.publish.engine;

/**
 * 表示一个可进行生成操作的接口。
 * 
 * @author liujunxing
 *
 */
public interface Generator {
	/**
	 * 请求生成此对象的页面。(可能多个)
	 * @param callback - 回调接口。
	 * @return 返回 true 表示还有更多项目需要生成, 返回 false 表示没有更多项目需要生成了。
	 */
	public boolean genObjectPages(GenerateCallback callback);
}
