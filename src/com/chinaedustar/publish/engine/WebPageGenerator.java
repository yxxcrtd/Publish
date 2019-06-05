package com.chinaedustar.publish.engine;

import com.chinaedustar.publish.PublishContext;
import com.chinaedustar.publish.model.WebPage;

/**
 * 自定义页面生成器。
 * @author liujunxing
 *
 */
public class WebPageGenerator extends AbstractGenerator {
	private final WebPage webpage;
	/**
	 * 构造函数。
	 * @param pub_ctxt
	 * @param webpage
	 */
	public WebPageGenerator(PublishContext pub_ctxt, WebPage webpage) {
		super(pub_ctxt);
		this.webpage = webpage;
	}

	/*
	 * (non-Javadoc)
	 * @see com.chinaedustar.publish.engine.AbstractGenerator#generate()
	 */
	@Override protected boolean generate() {
		// 计算静态地址。
		if (webpage.rebuildStaticPageUrl())
			webpage.updateGenerateStatus();
			
		// 生成。
		PageGenerator page_gen = new PageGenerator(pub_ctxt);
		String page_content = page_gen.generateWebPage(null, webpage);

		// 写入文件。
		super.writePagedObjectPage(webpage, page_content);
		
		return false;
	}

}
