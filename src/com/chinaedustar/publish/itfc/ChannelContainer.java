package com.chinaedustar.publish.itfc;

import com.chinaedustar.publish.model.AuthorCollection;
import com.chinaedustar.publish.model.GeneralContainer;
import com.chinaedustar.publish.model.KeywordCollection;
import com.chinaedustar.publish.model.SourceCollection;
import com.chinaedustar.publish.model.SpecialCollection;
import com.chinaedustar.publish.model.WebPageCollection;

/**
 * 提供做为专题、自定义页面、作者、来源容器的接口。
 * 
 * @author liujunxing
 * 当前 频道、站点实现此接口。
 */
public interface ChannelContainer extends GeneralContainer {
	/**
	 * 获得此容器中的专题集合对象。
	 * @return 返回专题集合对象，注意：每次调用返回的对象可能是新建的，从而不保证相同。
	 */
	public SpecialCollection getSpecialCollection(); 
	
	/**
	 * 获得此容器中的自定义页面集合对象。
	 * @return 返回自定义页面集合对象，注意：每次调用返回的对象可能是新建的，从而不保证相同。
	 */
	public WebPageCollection getWebPageCollection(); 
	
	/**
	 * 返回此容器中的作者集合对象。
	 * @return 返回作者集合对象，注意：每次调用返回的对象可能是新建的，从而不保证相同。
	 */
	public AuthorCollection getAuthorCollection();
	
	/**
	 * 返回此容器中的来源集合对象。
	 * @return 返回来源集合对象，注意：每次调用返回的对象可能是新建的，从而不保证相同。
	 */
	public SourceCollection getSourceCollection();

	/**
	 * 返回此容器中的关键字集合对象。
	 * @return 返回关键字集合对象，注意：每次调用返回的对象可能是新建的，从而不保证相同。
	 */
	public KeywordCollection getKeywordCollection();
}
