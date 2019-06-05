package com.chinaedustar.publish.model;

/**
 * 导师对象
 * 
 * @author Yang XinXin
 */
public class Teacher extends AbstractModelBase {
	
	/**
	 * 导师Id
	 */
	private int tid;
	
	/**
	 * 导师姓名
	 */
	private String teacher;

	public int getTid() {
		return tid;
	}

	public void setTid(int tid) {
		this.tid = tid;
	}

	public String getTeacher() {
		return teacher;
	}

	public void setTeacher(String teacher) {
		this.teacher = teacher;
	}
	
}
