package com.chinaedustar.template.core;

import com.chinaedustar.rtda.oper.LogicCalculator;
import com.chinaedustar.template.expr.Expression;

/**
 * if 指令节点。
 * 
 * @author liujunxing
 */
public class IfInstructionElement extends InstructionElement {
	/**
	 * 构造一个 if 指令节点。
	 *
	 */
	public IfInstructionElement(Expression expr) {
		super("if");
		// 构造第一个 if 条件判定块，然后做为本节点的第一个子节点插入。
		ConditionElement cond_elem = new ConditionElement("if", expr);
		this.appendChild(cond_elem);
	}
	
	/** 转换为字符串表示。 */
	@Override public String toString() {
		return "IfInstruction{#if}";
	}
	
	/**
	 * 使用指定的模板变量对此节点进行访问。
	 * @param env
	 * @return - 返回执行结果要求，执行引擎根据结果进行不同的处理。
	 */
	public int accept(InternalProcessEnvironment env) {
		// 没有任何子块，就不用操作了。
		if (this.getFirstChild() == null) return PROCESS_SIBLING;
		
		// 查找条件为真的 if 或 elseif 块。
		ConditionElement cond_elem = findTrueCondition(env, false);
		if (cond_elem == null) {
			cond_elem = findTrueCondition(env, true);
		}
		
		if (cond_elem != null) {
			env.visit(cond_elem.getFirstChild(), true, true);
		}
		
		// 让继续执行其子节点。
		return PROCESS_SIBLING;
	}
	
	/**
	 * 查找符合条件的条件块(if, elseif。
	 * @param env
	 */
	private ConditionElement findTrueCondition(InternalProcessEnvironment env, boolean find_else) {
		AbstractTemplateElement elem_iter = this.getFirstChild();
		while (elem_iter != null) {
			if (elem_iter instanceof ConditionElement) {
				ConditionElement cond_elem = (ConditionElement)elem_iter;
				if (cond_elem.getInstruction().equals("else")) {
					if (find_else) 
						return cond_elem;
				} else {
					Expression cond_expr = cond_elem.getConditionExpression();
					if (cond_expr == null) return cond_elem;
					Object cond_value = env.calc(cond_expr);
					if (LogicCalculator.getBooleanValue(cond_value)) 
						return cond_elem;
				}
			}
			elem_iter = elem_iter.getNextElement();
		}
		return null;
	}
}
