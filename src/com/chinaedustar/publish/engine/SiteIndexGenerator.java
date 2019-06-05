package com.chinaedustar.publish.engine;

import com.chinaedustar.publish.PublishContext;
import com.chinaedustar.publish.model.Site;

/**
 * 网站主页生成。
 * 
 * @author liujunxing
 */
public class SiteIndexGenerator extends AbstractGenerator {
	/**
	 * 构造函数。
	 * @param pub_ctxt
	 */
	public SiteIndexGenerator(PublishContext pub_ctxt) {
		super(pub_ctxt);
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.chinaedustar.publish.engine.AbstractPublishGeneratable#generate()
	 */
	@Override protected boolean generate() {
		Site site = pub_ctxt.getSite();
		if (site.getNeedGenerate() == false) {
			callback.info("网站主页设置为动态显示，其不需要生成。");
			return false;
		}
		// 如果地址变更了，立刻更新到数据库中，否则生成的时候会用到。
		if (site.rebuildStaticPageUrl())
			site.updateGenerateStatus();
		
		// 生成页面内容。
		PageGenerator page_gen = new PageGenerator(pub_ctxt);
		String page_content = page_gen.generateIndexPage();		// 生成主页
		
		// 写入其静态位置。
		writePagedObjectPage(site, page_content);
		callback.info("网站主页已经生成完成, 其 url 为: " + site.getStaticPageUrl());
		return false;
	}
}
