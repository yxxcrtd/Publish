package com.chinaedustar.template.comp;

import com.chinaedustar.rtda.ObjectWrapper;
import com.chinaedustar.rtda.model.*;
import com.chinaedustar.rtda.oper.*;
import com.chinaedustar.template.TemplateException;
import com.chinaedustar.template.VariableResolver;
import com.chinaedustar.template.expr.ExpressionCalculator;
import com.chinaedustar.template.expr.Operator;
import com.chinaedustar.template.expr.Parameter;
import com.chinaedustar.template.expr.ParameterList;

/**
 * 提供基本的表达式计算器，其能够支持运算符和属性查找。
 * 
 * @author liujunxing
 */
public abstract class AbstractExpressionCalculator implements ExpressionCalculator {
	/** 变量查找器。 */
	protected final VariableResolver variable_resolver;
	
	/** 对象包装器。 */
	protected final ObjectWrapper obj_wrapper;
	
	/**
	 * 使用指定的参数构造一个 AbstractExpressionCalculator 的实例。
	 * @param variable_resolver
	 */
	protected AbstractExpressionCalculator(VariableResolver variable_resolver, ObjectWrapper obj_wrapper) {
		if (obj_wrapper == null)
			throw new IllegalArgumentException("obj_wrapper == null");
		this.variable_resolver = variable_resolver;
		this.obj_wrapper = obj_wrapper;
	}

	/**
	 * 计算一个常量值，通常原样返回就可以了。
	 * @param const_value
	 * @return
	 */
	public Object evalConst(Object const_value) {
		return const_value;
	}

	/**
	 * 计算一个变量的值，通常在环境中查找此变量然后返回其值。
	 * @param var_name
	 * @return
	 */
	public Object evalVariable(String var_name) {
		// 在变量容器链里面查找。
		VariableResolver iter_vc = this.variable_resolver;
		while (iter_vc != null) {
			// 在当前容器里面找。
			Object var = iter_vc.resolveVariable(var_name);
			if (var != null) 
				return var;
			
			// 如果没有找到，则在其父容器里面找。
			iter_vc = iter_vc.getParentResolver();
		}

		return null;
	}

	/**
	 * 计算一元操作符 oper oper_obj 的值。如 !true, -user_age。
	 * @param oper
	 * @param oper_obj
	 * @return
	 */
	public Object evalOperator(Operator oper, Object oper_obj) {
		if (oper == Operator.OPERATOR_NOT)
			return LogicCalculator.not(oper_obj);
		if (oper == Operator.OPERATOR_PLUS)
			return MathCalculator.plus(oper_obj);
		if (oper == Operator.OPERATOR_SUBSTRACT)
			return MathCalculator.substract(oper_obj);
		
		throw new UnsupportedOperationException("不支持的一元运算符：" + oper);
	}

	/**
	 * 计算二元操作符 left_value oper right_value 的值。
	 * @param oper
	 * @param left_value
	 * @param right_value
	 * @return
	 */
	public Object evalOperator(Operator oper, Object left_value, Object right_value) {
		return innerEvalOperator(oper, obj_wrapper.unwrap(left_value), obj_wrapper.unwrap(right_value));
	}
	
	/**
	 * 调用一个全局函数。
	 * @param method_name - 函数名字。
	 * @param param_list - 参数列表。
	 * @return - 返回值。
	 */
	public Object invokeGlobalMethod(String method_name, ParameterList param_list) {
		throw new UnsupportedOperationException();
	}
	
	/**
	 * 访问一个对象的指定属性。
	 * @param acc_obj
	 * @param prop_name
	 * @return
	 */
	public Object invokeProperty(Object acc_obj, String prop_name) {
		// 使用包装器包装此对象，然后访问其属性。
		DataModel wrap_obj = this.obj_wrapper.wrap(acc_obj);
		if (wrap_obj instanceof HashDataModel) {
			// 如果是 hash 对象模型，则调用其 get 方面获得该属性值。
			Object result = ((HashDataModel)wrap_obj).get(prop_name);
			return result;
		} else {
			// ?? 其它数据访问模型能够获得什么呢 ?
			throw new TemplateException("无法获得对象 " + acc_obj + " 的属性 '" + prop_name + "'。");
		}
	}
	
	/**
	 * 访问一个对象的指定方法。
	 * @param target - 要访问的对象。
	 * @param method_name - 方法名字。
	 * @param param_list - 参数列表。
	 * @return
	 */
	public Object invokeMethod(Object target, String method_name, ParameterList param_list) {
		// 使用包装器包装此对象，然后访问其 method_name 属性。
		DataModel wrap_obj = this.obj_wrapper.wrap(target);
		if (wrap_obj instanceof HashDataModel) {
			Object method = ((HashDataModel)wrap_obj).get(method_name);
			if (method == null || method == this.obj_wrapper.wrap(null))
				return handleMethodAccessFail(target, method_name, param_list);
			
			// 如果 method_name 属性返回的是一个函数数据访问模型对象，则调用其函数子 functor.
			if (method instanceof MethodDataModel) {
				return invokeFunctor((MethodDataModel)method, param_list);
			} else {
				return handleMethodAccessFail(target, method_name, param_list);
			}
		} else {
			// 其它数据访问模型不支持 () 运算子，我们抛出异常。
			return handleMethodAccessFail(target, method_name, param_list);
		}
	}

	/**
	 * 访问一个对象的指定索引的值。
	 * @param acc_obj - 要访问的对象。
	 * @param acc_index - 访问的索引。
	 * @return
	 */
	public Object invokeIndexProperty(Object acc_obj, Object acc_index) {
		// 使用包装器包装此对象，然后访问其属性。
		DataModel wrap_obj = this.obj_wrapper.wrap(acc_obj);
		if (wrap_obj instanceof SequenceDataModel) {
			// 顺序型的 可以使用 target[index] 方式访问。
			return ((SequenceDataModel)wrap_obj).indexor(acc_index);
		} else if (wrap_obj instanceof HashDataModel) {
			// Hash 型的，[] 访问等同于 '.' 操作。
			return ((HashDataModel)wrap_obj).get(String.valueOf(acc_index));
		} else {
			// 其它数据访问模型不支持 [] 运算子的。
			throw new TemplateException("对象 " + acc_obj + " 不支持索引访问。");
		}
	}
	
	/**
	 * 访问一个对象的内建方法, 派生类负责实现。
	 * @param acc_obj
	 * @param method_name
	 * @param param_list
	 * @return 返回 DataModel.NULL 表示对象不支持执行内建函数。
	 */
	public Object invokeBuiltin(Object acc_obj, String method_name, Object[] param_list) {
		// 使用包装器包装此对象，然后访问其 method_name builtin。
		DataModel wrap_obj = this.obj_wrapper.wrap(acc_obj);
		if (wrap_obj instanceof BuiltinDataModel) {
			return ((BuiltinDataModel)wrap_obj).builtin(method_name, param_list);
		}
		return DataModel.NULL;
	}

	/**
	 * 计算参数，派生类应该实现的。
	 */
	public Object[] calcParameter(ParameterList param_list) {
		if (param_list == null) return new Object[0];
		
		// 计算所有参数值。
		Object[] args = new Object[param_list.size()];
		for (int index = 0; index < param_list.size(); ++index) {
			Parameter val = param_list.get(index);
			args[index] = val == null ? null : val.eval(this);
		}
		return args;
	}
	
	/**
	 * 获得指定对象的类型名字。
	 * @param target
	 * @return
	 */
	public static final String getObjectClassName(Object target) {
		return (target == null) ? "(null)" : target.getClass().getName();
	}

	// === 实现 ====================================================================
	
	protected Object innerEvalOperator(Operator oper, Object left_value, Object right_value) {
		try {
			switch (oper.getOperator()) {
			case Operator.OPERATOR_OR_CODE: 		return or(left_value, right_value);
			case Operator.OPERATOR_AND_CODE: 		return and(left_value, right_value);
			case Operator.OPERATOR_XOR_CODE: 		return xor(left_value, right_value);
			case Operator.OPERATOR_EQUAL_CODE: 		return equal(left_value, right_value);
			case Operator.OPERATOR_NOEQUAL_CODE: 	return notEqual(left_value, right_value);
			case Operator.OPERATOR_GREATOREQUAL_CODE: return greatEqual(left_value, right_value);
			case Operator.OPERATOR_LESSOREQUAL_CODE: return lessEqual(left_value, right_value);
			case Operator.OPERATOR_GREAT_CODE: 		return great(left_value, right_value);
			case Operator.OPERATOR_LESS_CODE: 		return less(left_value, right_value);
			case Operator.OPERATOR_PLUS_CODE: 		return plus(left_value, right_value);
			case Operator.OPERATOR_SUBSTRACT_CODE: 	return substract(left_value, right_value);
			case Operator.OPERATOR_MULTIPLY_CODE: 	return multiply(left_value, right_value);
			case Operator.OPERATOR_DIVIDE_CODE: 	return divide(left_value, right_value);
			case Operator.OPERATOR_MODULUS_CODE: 	return modulus(left_value, right_value);
			default: throw new TemplateException("未知或不支持的运算符 '" + oper + "'");
			}
		} catch (CalculationException ex) {
			throw new TemplateException("无法计算表达式 " + left_value + " " + oper + " " + right_value, ex);
		}
	}

	// 处理当访问一个对象属性失败的情况。
	protected Object handleMethodAccessFail(Object target, String method_name, ParameterList param_list) {
		throw new TemplateException(getObjectClassName(target) + " 类型的对象名为 '" + method_name + "' 的子对象不是一个可访问的方法。");
	}
	
	/**
	 * 使用指定参数调用指定函数。
	 * @param method_model
	 * @param param_list
	 * @return
	 */
	private Object invokeFunctor(MethodDataModel method_model, ParameterList param_list) {
		Object[] args = parameterToArgs(param_list);
		
		// 实际产生调用。
		return ((MethodDataModel)method_model).functor(args);
	}
	
	/**
	 * 计算所有的参数值。
	 * @param param_list
	 * @return
	 */
	private Object[] parameterToArgs(ParameterList param_list) {
		// 计算所有参数值。
		Object[] args = new Object[param_list.size()];
		for (int index = 0; index < param_list.size(); ++index) {
			args[index] = param_list.get(index).eval(this);
		}
		return args;
	}
	
	// === 运算符部分 ===============================================================

	public static final Object or(Object left_value, Object right_value) {
		return LogicCalculator.or(left_value, right_value);
	}
	
	public static final Object and(Object left_value, Object right_value) {
		return LogicCalculator.and(left_value, right_value);
	}
	
	public static final Object xor(Object left_value, Object right_value) {
		return LogicCalculator.xor(left_value, right_value);
	}
	
	public static final Object equal(Object left_value, Object right_value) {
		return LogicCalculator.equal(left_value, right_value);
	}
	
	public static final Object notEqual(Object left_value, Object right_value) {
		return LogicCalculator.notEqual(left_value, right_value);
	}
	
	public static final Object greatEqual(Object left_value, Object right_value) {
		return LogicCalculator.greatEqual(left_value, right_value);
	}
	
	public static final Object lessEqual(Object left_value, Object right_value) {
		return LogicCalculator.lessEqual(left_value, right_value);
	}
	
	public static final Object great(Object left_value, Object right_value) {
		return LogicCalculator.great(left_value, right_value);
	}
	
	public static final Object less(Object left_value, Object right_value) {
		return LogicCalculator.less(left_value, right_value);
	}
	
	public static final Object plus(Object left_value, Object right_value) {
		return MathCalculator.plus(left_value, right_value);
	}
	
	public static final Object substract(Object left_value, Object right_value) {
		return MathCalculator.substract(left_value, right_value);
	}
	
	public static final Object multiply(Object left_value, Object right_value) {
		return MathCalculator.multiply(left_value, right_value);
	}
	
	public static final Object divide(Object left_value, Object right_value) {
		return MathCalculator.divide(left_value, right_value);
	}
	
	public static final Object modulus(Object left_value, Object right_value) {
		return MathCalculator.modulus(left_value, right_value);
	}
	
	public static final Object not(Object union_value) {
		return LogicCalculator.not(union_value);
	}
}
