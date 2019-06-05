package com.chinaedustar.publish.model;

/**
 * 学生对象
 * 
 * @author Yang XinXin
 */
public class Student extends AbstractModelBase {
	
	/**
	 * 学生Id
	 */
	private int sid;

	/**
	 * 学号
	 */
	private String snumber;
	
	/**
	 * 姓名
	 */
	private String sname;
	
	/**
	 * 性别
	 */
	private String gender;
	
	/**
	 * 班级对象
	 */
	private Classs classs;
	
	/**
	 * 年级对象
	 */
	private Grade grade;
	
	/**
	 * 研究所对象
	 */
	private Institute institute;
	
	/**
	 * 导师对象
	 */
	private Teacher teacher;

	public int getSid() {
		return sid;
	}

	public void setSid(int sid) {
		this.sid = sid;
	}

	public String getSnumber() {
		return snumber;
	}

	public void setSnumber(String snumber) {
		this.snumber = snumber;
	}

	public String getSname() {
		return sname;
	}

	public void setSname(String sname) {
		this.sname = sname;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}
	
	public Classs getClasss() {
		return classs;
	}

	public void setClasss(Classs classs) {
		this.classs = classs;
	}

	public Grade getGrade() {
		return grade;
	}

	public void setGrade(Grade grade) {
		this.grade = grade;
	}

	public Institute getInstitute() {
		return institute;
	}

	public void setInstitute(Institute institute) {
		this.institute = institute;
	}

	public Teacher getTeacher() {
		return teacher;
	}

	public void setTeacher(Teacher teacher) {
		this.teacher = teacher;
	}
	
}
