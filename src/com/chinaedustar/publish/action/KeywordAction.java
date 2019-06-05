package com.chinaedustar.publish.action;

import java.util.List;

import com.chinaedustar.publish.model.Keyword;
import com.chinaedustar.publish.model.KeywordCollection;

/**
 * 关键字操作的集合。
 * 
 * @author liujunxing
 *
 */
public class KeywordAction extends AbstractAction {
	/** 所属频道标识。 */
	private int channelId = 0;
	
	/**
	 * 业务的执行方法。
	 *
	 */
	@Override public void execute() throws Exception {
		this.channelId = param_util.safeGetIntParam("channelId", 0);
		String command = super.param_util.safeGetStringParam("command");
		if ("save".equalsIgnoreCase(command))
			save();
		else if ("delete".equalsIgnoreCase(command))
			delete();
		else if ("clear".equalsIgnoreCase(command))
			clear();
		else
			unknownCommand(command);
	}
	
	/** 新建或更新关键字。 */
	private void save() {
		// 收集 keyword 对象数据。
		Keyword keyword = new Keyword();
		keyword.setId(param_util.safeGetIntParam("id", 0));
		keyword.setName(param_util.safeGetStringParam("name"));
		keyword.setChannelId(param_util.safeGetIntParam("channelId"));
		
		// 执行操作。
		KeywordCollection keyword_coll = getKeywordColl();
		if (keyword.getId() > 0) {
			// collection.updateKeyword(keyword);
			pub_ctxt.getTransactionProxy().updateKeyword(keyword_coll, keyword);
			messages.add("关键字更新成功。");
		} else {
			// collection.insertKeyword(keyword);
			pub_ctxt.getTransactionProxy().insertKeyword(keyword_coll, keyword);
			messages.add("关键字创建成功。");
		}

		links.add(getBackActionLink());
	}
	
	/** 删除关键字。 */
	private void delete() {
		// 获得参数。
		List<Integer> keyword_ids = param_util.safeGetIntValues("keywordIds");
		if (keyword_ids == null || keyword_ids.size() == 0) {
			noIdsParam();
			return;
		}
		
		// 执行操作。
		KeywordCollection keyword_coll = getKeywordColl();
		pub_ctxt.getTransactionProxy().deleteKeywords(keyword_coll, keyword_ids);
		
		// 设置返回信息。
		messages.add("关键字删除成功。");
		links.add(getBackActionLink());
	}
	
	/** 清除指定频道的所有关键字。 */
	private void clear() {
		// 执行操作。
		KeywordCollection keyword_coll = getKeywordColl();
		pub_ctxt.getTransactionProxy().clearKeywords(keyword_coll);
		
		messages.add("关键字清空成功。");
		links.add(getBackActionLink());
	}
	
	// 根据频道标识获得 KeywordCollection 对象。
	private KeywordCollection getKeywordColl() {
		KeywordCollection keyword_coll = null;
		if (channelId == 0) {
			keyword_coll = pub_ctxt.getSite().getKeywordCollection();
		} else {
			keyword_coll = pub_ctxt.getSite().getChannel(channelId).getKeywordCollection();
		}
		return keyword_coll;
	}
	
	// 获得返回的 ActionLink 对象。
	@Override protected ActionLink getBackActionLink() {
		return new ActionLink("返回", "admin_keyword_list.jsp?channelId=" + param_util.safeGetIntParam("channelId"));
	}
}
