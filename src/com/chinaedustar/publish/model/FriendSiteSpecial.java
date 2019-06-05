package com.chinaedustar.publish.model;

/**
 * 友情链接的专题对象。
 * 
 * @author liujunxing
 *
 */
public class FriendSiteSpecial extends AbstractModelBase {
	public FriendSiteSpecial() {
		
	}
	
	@Override public String toString() {
		return "FriendSiteSpecial{id=" + super.getId() + ",name=" + getName() + "}";
	}
	
	@Override public boolean equals(Object another) {
		if (another == null) return false;
		if ((another instanceof FriendSiteSpecial) == false) return false;
		return ((FriendSiteSpecial)another).getId() == getId();
	}
	
	/** 友情链接专题标识使用基类的 id. */
	// private int specialId;
	
	/** 友情链接专题的名字。 */
	private String specialName;
	
	public int getSpecialId() {
		return super.getId();
	}
	
	public void setSpecialId(int specialId) {
		super.setId(specialId);
	}
	
	public String getName() {
		return this.specialName;
	}
	
	public String getTitle() {
		return this.specialName;
	}
	
	public String getSpecialName() {
		return this.specialName;
	}
	
	public void setSpecialName(String specialName) {
		this.specialName = specialName;
	}

	private String specialDesc;
	
	public String getSpecialDesc() {
		return this.specialDesc;
	}
	
	public void setSpecialDesc(String v) {
		this.specialDesc = v;
	}
	
	public String getDescription() {
		return this.specialDesc;
	}
}
