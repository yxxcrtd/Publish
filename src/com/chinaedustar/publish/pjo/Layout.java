package com.chinaedustar.publish.pjo;

/**
 * 表示一个布局对象。
 * @author liujunxing
 *
 */
public class Layout extends NamedModelBase {
	/** 布局的描述。 */
	private String description;
	
	/** 布局的详细内容。 */
	private String content;
	
	// === getter/setter ===============================================
	
	/** 布局的描述。 */
	public String getDescription() {
		return this.description; 
	}
	
	/** 布局的描述。 */
	public void setDescription(String description) {
		this.description = description; 
	}
	
	/** 布局的详细内容。 */
	public String getContent() {
		return this.content;
	}
	
	/** 布局的详细内容。 */
	public void setContent(String content) {
		this.content = content;
	}
}
