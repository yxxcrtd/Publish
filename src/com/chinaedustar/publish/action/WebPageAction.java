package com.chinaedustar.publish.action;

import com.chinaedustar.publish.PublishUtil;
import com.chinaedustar.publish.model.WebPage;
import com.chinaedustar.publish.model.WebPageCollection;

/**
 * 网站自定义页面操作。
 * 
 * @author liujunxing
 *
 */
public class WebPageAction extends AbstractActionEx {
	// 支持的命令 'save', 'generate'
	static {
		registerCommand(WebPageAction.class, new ActionCommand("save"));
		registerCommand(WebPageAction.class, new ActionCommand("generate"));
	}
	
	/** 正在操作的对象， 'save', 'generate' 等命令中使用 */
	private WebPage webpage;
	
	/** 
	 * 更新或保存一个自定义网页。
	 */
	protected ActionResult save() {
		// 1. 收集数据。
		ActionResult result;
		if ((result = collectData()) != null) return result;
		
		// 2. 进行初步校验
		if ((result = checkData()) != null) return result;
		
		// 3. 保存。
		WebPageCollection wp_coll = pub_ctxt.getSite().getWebPageCollection();
		tx_proxy.saveWebPage(wp_coll, webpage);
		
		return SUCCESS;
	}
	
	/**
	 * 生成所选择的一个页面。
	 * @return
	 */
	protected ActionResult generate() {
		// 获得参数。
		ActionResult result;
		if ((result = getOperateWebPage()) != null) return result;
		
		// 立刻生成这个页面。
		pub_ctxt.getGenerateEngine().genWebPage(null, webpage);
		
		return SUCCESS;
	}
	
	// === 辅助函数 ================================================================
	
	// 从页面提交的数据中收集出 WebPage 对象。
	private final ActionResult collectData() {
		this.webpage = new WebPage();
		int id = param_util.safeGetIntParam("id");
		if (id != 0) {
			this.webpage = site.getWebPageCollection().getWebPage(id);
			if (this.webpage == null)
				return INVALID_PARAM;
		}
		
		// 重要标识。
		webpage.setId(id);
		webpage.setChannelId(param_util.safeGetIntParam("channelId"));
		webpage.setParentId(param_util.safeGetIntParam("parentId"));
		
		// 基类属性
		webpage.setName(param_util.safeGetStringParam("name"));
		webpage.setTitle(param_util.safeGetStringParam("title"));
		webpage.setLogo(param_util.safeGetStringParam("logo"));
		webpage.setBanner(param_util.safeGetStringParam("banner"));
		webpage.setCopyright(param_util.safeGetStringParam("copyright"));
		webpage.setMetaKey(param_util.safeGetStringParam("metaKey"));
		webpage.setMetaDesc(param_util.safeGetStringParam("metaDesc"));
		webpage.setTemplateId(param_util.safeGetIntParam("templateId"));
		webpage.setSkinId(param_util.safeGetIntParam("skinId"));
		
		// WebPage 属性
		webpage.setTips(param_util.safeGetStringParam("tips"));
		webpage.setDescription(param_util.safeGetStringParam("description"));
		webpage.setOpenType(param_util.safeGetIntParam("openType"));
		
		// 收集扩展属性。
		if (webpage.getId() != 0)
			collectExtendsProp(webpage);
		
		return null;
	}
	
	// 检查 webpage 对象的数据是否合法。
	private ActionResult checkData() {
		String name = webpage.getName();
		if (PublishUtil.isEmptyString(name)) {
			messages.add("必须给出页面名字。");
			return INVALID_PARAM;
		}
		
		if (PublishUtil.isValidDir(name) == false || PublishUtil.isSystemDir(name)) {
			messages.add("给出的名字非法，或者是一个系统使用的名字。");
			return INVALID_PARAM;
		}
		
		return null;
	}

	// 获得当前要操作的页面对象，放在 this.webpage 中。
	private ActionResult getOperateWebPage() {
		int webpageId = param_util.safeGetIntParam("webpageId");
		if (webpageId == 0) return INVALID_PARAM;
		
		this.webpage = site.getWebPageCollection().getWebPage(webpageId);
		if (this.webpage == null) return INVALID_PARAM;
		
		return null;
	}
}
