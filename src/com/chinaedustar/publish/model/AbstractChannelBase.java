package com.chinaedustar.publish.model;

import com.chinaedustar.publish.itfc.ChannelContainer;


/**
 * 实现 Site, Channel 的共同属性的抽象基类。
 * 
 * <p>
 * Site, Channel 共同之处在于都能做为 Special, WebPage 的容器对象。
 * 实现的 SpecialContainer, WebPageContainer 缺省返回一个新对象。
 * </p>
 * @author liujunxing
 */
public abstract class AbstractChannelBase extends AbstractPageModelBase 
		implements ChannelContainer {
	/** 缺省构造函数。 */
	protected AbstractChannelBase() {
	}
	
	/** 复制构造函数。 */
	protected AbstractChannelBase(AbstractChannelBase src) {
		super(src);
	}

	// === GeneralContainer 接口实现 ============================================

	/*
	 * (non-Javadoc)
	 * @see com.chinaedustar.publish.itfc.GeneralContainer#getContainerId()
	 */
	public abstract int getChannelId();
	
	// === ChannelContainer 接口实现 ============================================
	
	/*
	 * (non-Javadoc)
	 * @see com.chinaedustar.publish.itfc.SpecialContainer#getSpecialCollection()
	 */
	public SpecialCollection getSpecialCollection() {
		SpecialCollection spec_coll = new SpecialCollection();
		spec_coll._init(pub_ctxt, this);
		return spec_coll;
	}

	/*
	 * (non-Javadoc)
	 * @see com.chinaedustar.publish.itfc.WebPageContainer#getWebPageCollection()
	 */
	public WebPageCollection getWebPageCollection() {
		WebPageCollection webpage_coll = new WebPageCollection();
		webpage_coll._init(pub_ctxt, this);
		return webpage_coll;
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.chinaedustar.publish.model.ChannelContainer#getAuthorCollection()
	 */
	public AuthorCollection getAuthorCollection() {
		AuthorCollection author_coll = new AuthorCollection();
		author_coll._init(pub_ctxt, this);
		return author_coll;
	}

	/*
	 * (non-Javadoc)
	 * @see com.chinaedustar.publish.model.ChannelContainer#getSourceCollection()
	 */
	public SourceCollection getSourceCollection() {
		SourceCollection source_coll = new SourceCollection();
		source_coll._init(pub_ctxt, this);
		return source_coll;
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.chinaedustar.publish.model.ChannelContainer#getKeywordCollection()
	 */
	public KeywordCollection getKeywordCollection() {
		KeywordCollection keyword_coll = new KeywordCollection();
		keyword_coll._init(pub_ctxt, this);
		return keyword_coll;
	}

}
