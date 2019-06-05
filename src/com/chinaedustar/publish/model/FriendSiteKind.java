package com.chinaedustar.publish.model;

/**
 * 友情链接类别对象。
 * 
 * @author liujunxing
 *
 */
public class FriendSiteKind extends AbstractModelBase {
	public FriendSiteKind() {
		
	}
	
	@Override public String toString() {
		return "FriendSiteKind{id=" + super.getId() + ",name=" + getName() + "}";
	}
	
	@Override public boolean equals(Object another) {
		if (another == null) return false;
		if ((another instanceof FriendSiteKind) == false) return false;
		return ((FriendSiteKind)another).getId() == getId();
	}

	/** 类别的标识使用基类的 id. */
	// private int kindId;
	
	/** 类别的名字。 */
	private String kindName;
	
	public int getKindId() {
		return super.getId();
	}
	
	public void setKindId(int kindId) {
		super.setId(kindId);
	}
	
	public String getName() {
		return this.kindName;
	}
	
	public String getTitle() {
		return this.kindName;
	}
	
	public String getKindName() {
		return this.kindName;
	}
	
	public void setKindName(String kindName) {
		this.kindName = kindName;
	}
	
	private String kindDesc;
	
	public String getKindDesc() {
		return this.kindDesc;
	}
	
	public void setKindDesc(String kindDesc) {
		this.kindDesc = kindDesc;
	}
}
