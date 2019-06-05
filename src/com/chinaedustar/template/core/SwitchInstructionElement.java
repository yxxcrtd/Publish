package com.chinaedustar.template.core;

import com.chinaedustar.rtda.oper.LogicCalculator;
import com.chinaedustar.template.expr.Expression;

/**
 * switch 指令节点。
 *  
 * @author liujunxing
 */
public class SwitchInstructionElement extends InstructionElement {
	/** 值表达式。 */
	private final Expression value_expr;
	
	/**
	 * 使用指定的值表达式构造一个 SwitchInstructionElement 的实例。
	 * @param value_expr
	 */
	public SwitchInstructionElement(Expression value_expr) {
		super("switch");
		this.value_expr = value_expr;
	}
	
	/** 转为字符串表示。 */
	public String toString() {
		return "SwitchInstruction{value=" + value_expr + "}";
	}

	/**
	 * 使用指定的模板变量对此节点进行访问。
	 * @param env
	 * @return - 返回执行结果要求，执行引擎根据结果进行不同的处理。
	 */
	public int accept(InternalProcessEnvironment env) {
		// 如果没有任何子节点，则返回。
		if (this.getFirstChild() == null) return PROCESS_SIBLING;
		
		// 为 switch 请求一个局部环境，此局部环境将支持 break.
		env.acquireLocalContext(this, LocalContext.LOCAL_CONTEXT_BREAKABLE);
		
		// 求取表达式的值。
		Object switch_value = env.calc(value_expr);

		// 查找匹配这个值的 case 子元素。
		ConditionElement case_cond = findMatchedCaseElement(env, switch_value, false);
		if (case_cond != null && case_cond.getFirstChild() != null) {
			env.visit(case_cond.getFirstChild(), true, true);
			return PROCESS_SIBLING;
		}
		
		// 如果没有找到具有匹配的 case，则找 default
		ConditionElement def_cond = findMatchedCaseElement(env, null, true);
		if (def_cond != null && def_cond.getFirstChild() != null) {
			env.visit(def_cond.getFirstChild(), true, true);
			return PROCESS_SIBLING;
		}
		
		// 即没有找到 case 也没有 default 则继续下一个兄弟结点了。
		return PROCESS_SIBLING;
	}
	
	/** 查找 case 或 default 子节点。如果是找 case 子节点，其需要匹配值 switch_value. */
	private ConditionElement findMatchedCaseElement(InternalProcessEnvironment env, 
			Object switch_value, boolean find_default) {
		AbstractTemplateElement elem_iter = this.getFirstChild();
		while (elem_iter != null) {
			if (elem_iter instanceof ConditionElement) {
				// 必须是 ConditionElement
				ConditionElement cond_elem = (ConditionElement)elem_iter;
				// 如果只找 default 则验证是否是 default 节点。
				if (find_default) {
					if (cond_elem.getInstruction().equals("default"))
						return cond_elem;
				} else if (cond_elem.getInstruction().equals("case")) {
					Object cond_value = env.calc(cond_elem.getConditionExpression());
					if (LogicCalculator.equal(switch_value, cond_value))
						return cond_elem;
				}
			}
			elem_iter = elem_iter.getNextElement();
		}
		return null;
	}
}
