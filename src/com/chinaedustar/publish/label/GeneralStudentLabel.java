package com.chinaedustar.publish.label;

import com.chinaedustar.common.util.StringHelper;
import com.chinaedustar.publish.PublishContext;
import com.chinaedustar.publish.model.Student;
import com.chinaedustar.template.core.AbstractLabelElement;
import com.chinaedustar.template.core.InternalProcessEnvironment;

/**
 * 学生（包括：本科生、工程硕士、工学硕士和博士）标签
 */
public class GeneralStudentLabel extends GroupLabelBase {
	
	/**
	 * 不需要实例化
	 */
	private GeneralStudentLabel() {
		// 
	}
		
	/**
	 * 注册处理器
	 */
	public static final void registerHandler(LabelHandlerMap map) {
		
		map.put("sid", new StudentPropertyHandler());
		map.put("snumber", new StudentPropertyHandler());
		map.put("sname", new StudentPropertyHandler());
		map.put("gender", new StudentPropertyHandler());
		map.put("classs", new StudentPropertyHandler());
		map.put("grade", new StudentPropertyHandler());
		map.put("institute", new StudentPropertyHandler());
		map.put("teacher", new StudentPropertyHandler());
	}

	/**
	 * 学生的通用属性对象解释器
	 */
	private static final class StudentPropertyHandler extends AbstractSimpleLabelHandler {
		
		/* (non-Javadoc)
		 * 
		 * @see com.chinaedustar.publish.itfc.LabelHandler#handleLabel(com.chinaedustar.publish.PublishContext, com.chinaedustar.template.core.InternalProcessEnvironment, com.chinaedustar.template.core.AbstractLabelElement)
		 */
		public int handleLabel(PublishContext pub_ctxt, InternalProcessEnvironment env, AbstractLabelElement label) {
			String label_name = label.getLabelName();
			Student student = AbstractLabelHandler.getCurrentStudent(env);
			String result = "";
			
			if (student == null) {
				result = "标签中没有找到学生对象：" + label_name;
			} else if ("sid".equals(label_name)) {
				result = String.valueOf(student.getSid());
			} else if ("snumber".equals(label_name)) {
				result = StringHelper.htmlEncode(student.getSnumber());
			} else if ("sname".equals(label_name)) {
				result = StringHelper.htmlEncode(student.getSname());
			} else if ("gender".equals(label_name)) {
				result = StringHelper.htmlEncode(student.getGender());
			} else if ("classs".equals(label_name)) {
				result = StringHelper.htmlEncode(student.getClasss().getCname());
			} else if ("grade".equals(label_name)) {
				result = StringHelper.htmlEncode(student.getGrade().getGname());
			} else if ("institute".equals(label_name)) {
				result = StringHelper.htmlEncode(student.getInstitute().getTitle());
			} else if ("teacher".equals(label_name)) {
				if (null == student.getTeacher()) {
					result = "";
				} else {
					result = StringHelper.htmlEncode(student.getTeacher().getTeacher());
				}
			}
			
			if (null != result)
				env.getOut().write(result);

			return PROCESS_DEFAULT;
		}
	}
	
}
