package com.chinaedustar.template.builtin;

import com.chinaedustar.rtda.bean.StringModelImpl;
import com.chinaedustar.template.BuiltinFunction;
import com.chinaedustar.template.core.InternalProcessEnvironment;
import com.chinaedustar.template.core.LoopVarDecorator;

/**
 * IteratorDecorator 的内建函数容器。 
 * 
 * @author liujunxing
 *
 */
public class IterDecoBuiltins {
	private IterDecoBuiltins() {
		
	}
	
	/**
	 * 将所有对象内建函数注册到指定集合中。
	 *
	 */
	public static final void registerTo(java.util.Map<String, BuiltinFunction> map) {
		map.put("is_iterator", new iter_builtin(0));
		map.put("index", new iter_builtin(1));
		map.put("is_first", new iter_builtin(2));
		map.put("is_last", new iter_builtin(3));
		map.put("row_begin", new iter_builtin(4));
		map.put("row_end", new iter_builtin(5));
		map.put("odd_row", new iter_builtin(6));
		map.put("even_row", new iter_builtin(7));
		map.put("row_index", new iter_builtin(8));
	}

	/**
	 * 支持循环变量 @is_iterator, @index, @is_first, @is_last 内建方法的函数。
	 * 
	 * @author liujunxing
	 *
	 */
	private static final class iter_builtin implements BuiltinFunction {
		/** @is_iterator = 0, @index = 1, @is_first = 2, @is_last = 3*/
		private final int method_index;
		public iter_builtin(int method_index) {
			this.method_index = method_index;
		}
		/*
		 * (non-Javadoc)
		 * @see com.chinaedustar.template.BuiltinFunctionEx#exec(com.chinaedustar.template.core.InternalProcessEnvironment, java.lang.Object, com.chinaedustar.template.expr.ParameterList)
		 */
		public Object exec(InternalProcessEnvironment env, Object target, Object[] args) {
			LoopVarDecorator decorator = env.findLoopVarDecorator(target);
			if (decorator == null) return null;
			
			switch (this.method_index) {
			case 0: return true;
			case 1: return decorator.index;
			case 2: return decorator.is_first;
			case 3: return decorator.is_last;
			case 4: return row_begin(decorator, args);
			case 5: return row_end(decorator, args);
			case 6: return odd_row(decorator, args);
			case 7: return even_row(decorator, args);
			case 8: return row_index(decorator, args);
			}
			return null;
		}
		
		// 判断是否是一行的开始，用于生成多列表格。
		private static final boolean row_begin(LoopVarDecorator decorator, Object[] args) {
			int colNum = getIntParam(args, 1);
			if (colNum <= 0) colNum = 1;
			if (colNum == 1) return true;		// always begin
			return (decorator.index % colNum) == 0;
		}
		
		// 判断是否是一行的结束，用于生成多列表格。
		private static final boolean row_end(LoopVarDecorator decorator, Object[] args) {
			int colNum = getIntParam(args, 1);
			if (colNum <= 0) colNum = 1;
			if (colNum == 1) return true;		// always end
			if (decorator.is_last) return true;
			return ((decorator.index+1) % colNum) == 0;
		}
		
		// 判断是否是奇数行
		private static final boolean odd_row(LoopVarDecorator decorator, Object[] args) {
			int row_index = row_index(decorator, args);
			return (row_index % 2) == 0 ? true : false;
		}

		// 判断是否是偶数行
		private static final boolean even_row(LoopVarDecorator decorator, Object[] args) {
			int row_index = row_index(decorator, args);
			return (row_index % 2) == 0 ? false : true;
		}

		// 多列时候的行索引。
		private static final int row_index(LoopVarDecorator decorator, Object[] args) {
			int colNum = getIntParam(args, 1);
			if (colNum <= 0) colNum = 1;
			int row_index = (int)(decorator.index / colNum);
			return row_index;
		}

		private static final int getIntParam(Object[] args, int def_val) {
			if (args == null || args.length == 0 || args[0] == null) return def_val;
			
			return StringModelImpl.getIntParam(args[0]);
		}
	}
}
