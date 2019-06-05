package com.chinaedustar.publish.pjo;

import java.util.Date;

/**
 * 投票的基准类。
 * 
 * @author liujunxing
 *
 */
public class Vote {
	/** 对象唯一标识。 */
	private int id;
	
    /** 投票标题。 */    
    private String title;
    
    /** 选项1-8，票数1-8 */
    private String select1;
    private int answer1;
    
    private String select2;
    private int answer2;
    
    private String select3;
    private int answer3;
    
    private String select4;
    private int answer4;
    
    private String select5;
    private int answer5;
    
    private String select6;
    private int answer6;
    
    private String select7;
    private int answer7;
    
    private String select8;
    private int answer8;
    
    /** 投票开始时间。 */
    private Date beginTime;
    
    /** 投票结束时间。 */
    private Date endTime;
    
    /** 0 - 单选，1 - 多选 */
    private int voteType;
    
    /** 是否启用。 */
    private boolean isSelected;
    
    /** 是否是一个项目的投票。 */
    private boolean isItem;
    
    /** 所属频道标识。 */
    private int channelId;
    
    /** 所属项目标识。 */
    private int itemId;
    
    // === getter/setter =========================================================

	/** 对象唯一标识。 */
    public int getId() {
    	return this.id;
    }
    
	/** 对象唯一标识。 */
    public void setId(int id) {
    	this.id = id;
    }
    
    /** 投票标题。 */    
    public String getTitle() {
        return this.title;
    }
    
    /** 投票标题。 */    
    public void setTitle(String title) {
        this.title = title;
    }

    public String getSelect1() {
        return this.select1;
    }
    
    public void setSelect1(String select1) {
        this.select1 = select1;
    }

    public int getAnswer1() {
        return this.answer1;
    }
    
    public void setAnswer1(int answer1) {
        this.answer1 = answer1;
    }

    public String getSelect2() {
        return this.select2;
    }
    
    public void setSelect2(String select2) {
        this.select2 = select2;
    }

    public int getAnswer2() {
        return this.answer2;
    }
    
    public void setAnswer2(int answer2) {
        this.answer2 = answer2;
    }

    public String getSelect3() {
        return this.select3;
    }
    
    public void setSelect3(String select3) {
        this.select3 = select3;
    }

    public int getAnswer3() {
        return this.answer3;
    }
    
    public void setAnswer3(int answer3) {
        this.answer3 = answer3;
    }

    public String getSelect4() {
        return this.select4;
    }
    
    public void setSelect4(String select4) {
        this.select4 = select4;
    }

    public int getAnswer4() {
        return this.answer4;
    }
    
    public void setAnswer4(int answer4) {
        this.answer4 = answer4;
    }

    public String getSelect5() {
        return this.select5;
    }
    
    public void setSelect5(String select5) {
        this.select5 = select5;
    }

    public int getAnswer5() {
        return this.answer5;
    }
    
    public void setAnswer5(int answer5) {
        this.answer5 = answer5;
    }

    public String getSelect6() {
        return this.select6;
    }
    
    public void setSelect6(String select6) {
        this.select6 = select6;
    }

    public int getAnswer6() {
        return this.answer6;
    }
    
    public void setAnswer6(int answer6) {
        this.answer6 = answer6;
    }

    public String getSelect7() {
        return this.select7;
    }
    
    public void setSelect7(String select7) {
        this.select7 = select7;
    }

    public int getAnswer7() {
        return this.answer7;
    }
    
    public void setAnswer7(int answer7) {
        this.answer7 = answer7;
    }

    public String getSelect8() {
        return this.select8;
    }
    
    public void setSelect8(String select8) {
        this.select8 = select8;
    }

    public int getAnswer8() {
        return this.answer8;
    }
    
    public void setAnswer8(int answer8) {
        this.answer8 = answer8;
    }

    /** 投票开始时间。 */
    public Date getBeginTime() {
        return this.beginTime;
    }
    
    /** 投票开始时间。 */
    public void setBeginTime(Date voteTime) {
        this.beginTime = voteTime;
    }

    /** 投票结束时间。 */
    public Date getEndTime() {
        return this.endTime;
    }
    
    /** 投票结束时间。 */
    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    /** 0 - 单选，1 - 多选 */
    public int getVoteType() {
        return this.voteType;
    }
    
    /** 0 - 单选，1 - 多选 */
    public void setVoteType(int voteType) {
        this.voteType = voteType;
    }

    /** 是否启用。 */
    public boolean getIsSelected() {
        return this.isSelected;
    }
    
    /** 是否启用。 */
    public void setIsSelected(boolean isSelected) {
        this.isSelected = isSelected;
    }

    /** 是否是一个项目的投票。 */
    public boolean getIsItem() {
        return this.isItem;
    }
    
    /** 是否是一个项目的投票。 */
    public void setIsItem(boolean isItem) {
        this.isItem = isItem;
    }

    /** 所属频道标识。 */
    public int getChannelId() {
    	return this.channelId;
    }
    
    /** 所属频道标识。 */
    public void setChannelId(int channelId) {
    	this.channelId = channelId;
    }
    
    /** 所属项目标识。 */
    public int getItemId() {
    	return this.itemId;
    }
    
    /** 所属项目标识。 */
    public void setItemId(int itemId) {
    	this.itemId = itemId;
    }
    
    // === 轻量函数 =================================================================
    
    /**
     * 获得总投票数。
     */
    public long _getTotalAnswer() {
    	return this.answer1 + this.answer2 + this.answer3 + this.answer4 +
    		this.answer5 + this.answer6 + this.answer7 + this.answer8;
    }
}
