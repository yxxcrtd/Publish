package com.chinaedustar.publish.model;

import java.util.Date;
import java.util.List;
import com.chinaedustar.publish.*;
import com.chinaedustar.publish.pjo.Vote;

/**
 * 
 * @author 
 *
 */
public class VoteWrapper extends AbstractModelBase {
	/** 内部实际的投票对象。 */
	private final Vote vote;
	
	/**
	 * 构造函数。
	 * @param vote - 实际的底层 vote 对象。
	 */
	public VoteWrapper(PublishContext pub_ctxt, PublishModelObject owner_obj, Vote vote) {
		this.vote = vote;
		super._init(pub_ctxt, owner_obj);
	}
	
	/**
	 * 获得被包装的目标对象。
	 * @return
	 */
	public Vote getTargetObject() {
		return vote;
	}
	
    // === getter/setter =========================================================

	/** 获得对象标识。 */
	public int getId() {
		return vote.getId();
	}
	
    /** 投票标题。 */    
    public String getTitle() {
        return vote.getTitle();
    }
    
    /** 投票开始时间。 */
    public Date getBeginTime() {
        return vote.getBeginTime();
    }
    
    /** 投票结束时间。 */
    public Date getEndTime() {
        return vote.getEndTime();
    }
    
    /** 0 - 单选，1 - 多选 */
    public int getVoteType() {
        return vote.getVoteType();
    }
    
    /** 是否启用。 */
    public boolean getIsSelected() {
        return vote.getIsSelected();
    }
    
    /** 是否是一个项目的投票。 */
    public boolean getIsItem() {
        return vote.getIsItem();
    }
    
    /** 所属频道标识。 */
    public int getChannelId() {
    	return vote.getChannelId();
    }

    /** 获得总投票数。 */
    public long getTotal() {
    	return vote._getTotalAnswer();
    }
    
    /**
     * 获得所有投票选项。
     * @return
     */
    public List<VoteOption> getSelects() {
    	return getVoteOptions();
    }
    
    /**
     * 获得所有投票选项。
     * @return
     */
    public List<VoteOption> getVoteOptions() {
    	List<VoteOption> options = new java.util.ArrayList<VoteOption>();
    	// 把下面这些字段转换为数组，看起来挺烦，其实应该比较快。
    	if (PublishUtil.isEmptyString(vote.getSelect1()) == false)
    		options.add(new VoteOption(1, vote.getSelect1(), vote.getAnswer1()));
    	if (PublishUtil.isEmptyString(vote.getSelect2()) == false)
    		options.add(new VoteOption(2, vote.getSelect2(), vote.getAnswer2()));
    	if (PublishUtil.isEmptyString(vote.getSelect3()) == false)
    		options.add(new VoteOption(3, vote.getSelect3(), vote.getAnswer3()));
    	if (PublishUtil.isEmptyString(vote.getSelect4()) == false)
    		options.add(new VoteOption(4, vote.getSelect4(), vote.getAnswer4()));
    	if (PublishUtil.isEmptyString(vote.getSelect5()) == false)
    		options.add(new VoteOption(5, vote.getSelect5(), vote.getAnswer5()));
    	if (PublishUtil.isEmptyString(vote.getSelect6()) == false)
    		options.add(new VoteOption(6, vote.getSelect6(), vote.getAnswer6()));
    	if (PublishUtil.isEmptyString(vote.getSelect7()) == false)
    		options.add(new VoteOption(7, vote.getSelect7(), vote.getAnswer7()));
    	if (PublishUtil.isEmptyString(vote.getSelect8()) == false)
    		options.add(new VoteOption(8, vote.getSelect8(), vote.getAnswer8()));
    	return options;
    }
    
    /**
     * 代表一个投票选项。
     * @author liujunxing
     *
     */
    public class VoteOption {
    	private final int order;
    	private final String select;
    	private final int answer;
    	public VoteOption(int order, String select, int answer) {
    		this.order = order;
    		this.select = select;
    		this.answer = answer;
    	}
    	
    	/**
    	 * 获得问题序号，从1开始，最多8个(当前)。
    	 * @return
    	 */
    	public int getOrder() {
    		return this.order;
    	}
    	
    	/**
    	 * 获得问题。
    	 * @return
    	 */
    	public String getSelect() {
    		return this.select;
    	}
    	
    	/**
    	 * 获得票数。
    	 * @return
    	 */
    	public int getAnswer() {
    		return this.answer;
    	}
    	
    	/**
    	 * 获得这个选项的投票百分比，显然自己需要格式化一下。
    	 * @return
    	 */
    	public double getPercent() {
    		long total = getTotal();
    		if (total <= 0) return 0;
    		return (double)this.answer*100.0/(double)total;
    	}
    }
}
