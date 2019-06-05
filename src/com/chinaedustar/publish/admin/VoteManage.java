package com.chinaedustar.publish.admin;

import javax.servlet.jsp.PageContext;
import com.chinaedustar.publish.PublishException;
import com.chinaedustar.publish.model.*;
import com.chinaedustar.publish.pjo.Vote;

/**
 * 投票管理。
 * @author liujunxing
 *
 */
public class VoteManage extends AbstractBaseManage {
	/**
	 * 构造函数。
	 * @param page_ctxt
	 */
	public VoteManage(PageContext page_ctxt) {
		super(page_ctxt);
	}
	
	/**
	 * 初始化投票列表页 admin_vote_list.jsp。
	 *
	 */
	public void initListPage() {
		// 获得指定频道下的投票列表。
		int channelId = param_util.safeGetIntParam("channelId");
		PaginationInfo page_info = super.getPaginationInfo();
		Object vote_list = site.getVoteCollection().getVoteDataTable(channelId, page_info);
		setTemplateVariable("vote_list", vote_list);
		page_info.setItemName("个调查");
		setTemplateVariable("page_info", page_info);
		
		// 初始化频道列表。
		Object channel_list = super.getChannelListData();
		setTemplateVariable("channel_list", channel_list);
	}

	/**
	 * admin_vote_add.jsp 页面数据初始化。
	 * @remark
	 *   vote - pjo.Vote 类型，未包装的。
	 *   vote_wrapper - model.VoteWrapper 类型，包装过的。
	 */
	public void initEditPage() {
		// 初始化投票对象自身。
		VoteWrapper vote = getOperateVote();
		setTemplateVariable("vote", vote.getTargetObject());
		setTemplateVariable("vote_wrapper", vote);
		
		// 初始化频道列表。
		Object channel_list = super.getChannelListData();
		setTemplateVariable("channel_list", channel_list);
	}
	
	// === 辅助函数 ===============================================================
	
	// 获得要操作的投票对象。
	private VoteWrapper getOperateVote() {
		int voteId = param_util.safeGetIntParam("voteId");
		if (voteId == 0)
			return new VoteWrapper(pub_ctxt, site.getVoteCollection(), new Vote());
		
		VoteWrapper vote_wrapper = site.getVoteCollection().getVote(voteId);
		if (vote_wrapper == null) throw new PublishException("非法参数。");
		return vote_wrapper;
	}
}
