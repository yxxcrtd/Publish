package com.chinaedustar.template.comp;

import com.chinaedustar.rtda.ObjectWrapper;
import com.chinaedustar.template.VariableResolver;
import com.chinaedustar.template.expr.ExpressionCalculator;

/**
 * 标准的表达式计算器对象实现。只要给出变量解析环境和对象包装器就能计算值，可以用于
 *   非模板的执行环境。
 * 
 * @author liujunxing
 */
public class StandardExpressionCalculator extends AbstractExpressionCalculator implements ExpressionCalculator {
	/**
	 * 使用指定的变量解析器和对象包装器构造一个 StandardExpressionCalculator 的实例。 
	 *
	 */
	public StandardExpressionCalculator(VariableResolver variable_resolver, ObjectWrapper obj_wrapper) {
		super(variable_resolver, obj_wrapper);
	}
}
