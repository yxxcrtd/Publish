package com.chinaedustar.rtda.bean;

import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.util.Iterator;
import java.util.StringTokenizer;

import com.chinaedustar.common.util.StringHelper;
import com.chinaedustar.rtda.ObjectWrapper;
import com.chinaedustar.rtda.model.CollectionDataModel;
import com.chinaedustar.rtda.model.NumberDataModel;
import com.chinaedustar.rtda.model.SequenceDataModel;
import com.chinaedustar.rtda.model.StringDataModel;
import com.chinaedustar.rtda.oper.MathCalculator;
import com.chinaedustar.rtda.simple.NullData;
import com.chinaedustar.rtda.simple.StringIterator;
import com.chinaedustar.template.TemplateException;

/**
 * 一般访问模型，其支持 hash, string 数据访问。
 * 
 * @author liujunxing
 */
public class StringModelImpl extends BeanModelImpl<String> 
		implements StringDataModel, SequenceDataModel, CollectionDataModel {
	/**
	 * 构造。
	 * @param target
	 * @param wrapper
	 */
	public StringModelImpl(String target, ObjectWrapper wrapper) {
		super(target, wrapper);
	}
	
	/**
	 * 转换此标量为一个字符串表示。一般可以直接用 Object.toString() 来实现。
	 * @param optional_format - (可选的) 使用此格式，可能为 null.
	 * @return
	 */
	public String to_string(String optional_format) {
		return target.toString();
	}
	
    /**
     * 返回此数据的字符串表示。一般而言不要返回 null。
     */
    public String getAsString() {
    	return target.toString();
    }
    
    /*
     * (non-Javadoc)
     * @see com.chinaedustar.rtda.bean.BeanModelImpl#builtin(java.lang.String, java.lang.Object[])
     */
	@Override public Object builtin(String method_name, Object[] param_list) {
		if ("is_string".equals(method_name))
			return true;
		else if ("size".equals(method_name) || "length".equals(method_name))
			return target.length();
		else if ("contains".equals(method_name))
			return contains(target, param_list);
		else if ("trim".equals(method_name))
			return target.trim();
		else if ("html".equals(method_name))
			// 执行 html 编码。
			return StringHelper.htmlEncode(target);
		else if ("xml".equals(method_name))
			// 执行 xml 编码
			return StringHelper.xmlEncode(target);
		else if ("rtf".equals(method_name))
			// 支持 string@rtf - 进行 rtf 编码处理。
			return StringHelper.rtfEncode(target);
		else if ("url".equals(method_name))
			return url(target, param_list);
		else if ("lower_case".equals(method_name))
			return lower_case(target, param_list);
		else if ("upper_case".equals(method_name))
			return upper_case(target, param_list);
		else if ("uri_resolve".equals(method_name))
			return uri_resolve(target, param_list);
		else if ("uri_relative".equals(method_name))
			return uri_relative(target, param_list);
		else if ("index_of".equals(method_name))
			return index_of(target, param_list);
		else if ("last_index_of".equals(method_name))
			return last_index_of(target, param_list);
		else if ("starts_with".equals(method_name))
			return starts_with(target, param_list);
		else if ("ends_with".equals(method_name))
			return ends_with(target, param_list);
		else if ("replace".equals(method_name))
			return replace(target, param_list);
		else if ("split".equals(method_name))
			return split(target, param_list);
		else if ("capitalize".equals(method_name))
			return capitalize(target, param_list);
		else if ("chop_linebreak".equals(method_name))
			return chop_linebreak(target, param_list);
		else if ("j_string".equals(method_name))
			return j_string(target, param_list);
		else if ("js_string".equals(method_name))
			return js_string(target, param_list);
		else if ("cap_first".equals(method_name))
			return cap_first(target, param_list);
		else if ("uncap_first".equals(method_name))
			return uncap_first(target, param_list);
		else if ("word_list".equals(method_name))
			return word_list(target, param_list);
		else if ("number".equals(method_name))
			return number(target, param_list);
		else if ("int".equals(method_name))
			return to_int(target, param_list);
		else if ("substring".equals(method_name))
			return substring(target, param_list);
		else if ("erase_html".equals(method_name))
			return erase_html(target, param_list);
		else if ("repeat".equals(method_name))
			return repeat(target, param_list);
		else if ("left".equals(method_name))
			return left(target, param_list);
		else if ("right".equals(method_name))
			return right(target, param_list);
		else if ("cleft".equals(method_name))
			return cleft(target, param_list);
		
		return super.builtin(method_name, param_list);
	}

	// === SequenceDataModel 接口实现 =================================================
	
	/*
	 * (non-Javadoc)
	 * @see com.chinaedustar.rtda.model.SequenceDataModel#indexor(java.lang.Object)
	 */
	public Object indexor(Object index) {
		int i = getIntParam(index);
		if (i < 0 || i >= target.length()) return null;
		return target.charAt(i);
	}

	/*
	 * (non-Javadoc)
	 * @see com.chinaedustar.rtda.model.SequenceDataModel#size()
	 */
	public int size() {
		return target.length();
	}
	
	// === CollectionDataModel 接口实现 ===============================================

	/*
	 * (non-Javadoc)
	 * @see com.chinaedustar.rtda.model.CollectionDataModel#iterator()
	 */
	@SuppressWarnings("rawtypes")
	public Iterator iterator() {
		return new StringIterator(this.target);
	}
	
	// === builtin 实现 ==============================================================
	
	// 字符串是否包含指定子串。
	private static boolean contains(String target, Object[] param_list) {
		if (param_list == null || param_list.length == 0) return false;
		
		return ((String)target).indexOf(param_list[0].toString()) >= 0;
	}
	
	private static String url(String target, Object[] args) {
		String charset = "UTF-8";	// default charset
		if (args.length > 0 && args[0] != null) {
			charset = args[0].toString(); 
		}
		
		try {
			return StringHelper.urlEncode(target, charset);
		} catch (UnsupportedEncodingException ex) {
			throw new TemplateException("@url 不支持的编码 '" + charset + 
					"' 参数，一般是所给参数错误，或者本机未安装对该编码的支持。通常可以不给出参数而使用缺省编码 UTF-8。", ex);
		}
	}
	
	/** 支持 string@uri_resolve(base_uri) - 根据此 base_uri 解析给定的 target （绝对化） */
	private static String uri_resolve(String target, Object[] args) {
		if (args == null ||args.length == 0) return target;
		try {
			if (target == null) target = "";
			java.net.URI base_uri = new java.net.URI(args[0].toString());
			return String.valueOf(base_uri.resolve(target));
		} catch (URISyntaxException e) {
			return target;
		}
	}

	/** 支持 target@uri_relative(base_uri) - 根据 base_uri 将 target 相对化。 */
	private static String uri_relative(String target, Object args[]) {
		if (args == null ||args.length == 0) return target;
		try {
			if (target == null) target = "";
			java.net.URI base_uri = new java.net.URI(args[0].toString());
			java.net.URI target_uri = new java.net.URI(target);
			return String.valueOf(base_uri.relativize(target_uri));
		} catch (URISyntaxException e) {
			return target;
		}
	}
	
	/** 支持 string@index_of(substr) - 查找子串位置。 */
	private static int index_of(String target, Object[] args) {
		if (args.length < 1)
			throw new TemplateException("@index_of 方法需要一个 String 类型的参数。");
		if (args[0] == null) return -1;
		return ((String)target).indexOf(args[0].toString());
	}

	/** 支持 string@last_index_of(substr) - 查找子串位置。 */
	private static int last_index_of(String target, Object[] args) {
		if (target == null) return -1;
		if (args.length < 1)
			throw new TemplateException("@last_index_of 方法需要一个 String 类型的参数。");
		if (args[0] == null) return -1;
		return target.lastIndexOf(args[0].toString());
	}
	
	/** 支持 string@starts_with(str) 。 */
	private static boolean starts_with(String target, Object[] args) {
		if (target == null) return false;
		if (args.length < 1)
			throw new TemplateException("@starts_with 方法需要一个 String 类型的参数。");
		if (args[0] == null) return false;
		return ((String)target).startsWith(args[0].toString());
	}
	
	/** 支持 string@ends_with(str) 。 */
	private static boolean ends_with(String target, Object[] args) {
		if (target == null) return false;
		if (args.length < 1)
			throw new TemplateException("@ends_with 方法需要一个 String 类型的参数。");
		if (args[0] == null) return false;
		return target.endsWith(args[0].toString());
	}
	
	/** 支持 string@replace(str, str) */
	private static String replace(String target, Object[] args) {
		if (target == null) return null;
		if (args.length < 2)
			throw new TemplateException("@replace 方法需要两个 String 类型的参数。");
		String oldsub = args[0] == null ? "" : args[0].toString();
		String newsub = args[1] == null ? "" : args[1].toString();
		return StringHelper.replace(target, oldsub, newsub, 
				false/*caseInsensitive*/, false/*firstOnly*/);
	}
	
	/** 支持 string@split(str) */
	private static String[] split(String target, Object[] args) {
		if (target == null) return null;
		if (args.length < 1)
			throw new TemplateException("@split 方法需要一个 String 类型的参数。");
		String sep = args[0] == null ? "," : args[0].toString();
		return StringHelper.split(target, sep, false/*caseInsensitive*/);
	}
	
	/** string@capitalize() */
	private static String capitalize(String target, Object[] args) {
		return StringHelper.capitalize(target);
	}
	
	/** string@chop_linebreak() */
	private static String chop_linebreak(String target, Object[] args) {
		return StringHelper.chomp(target);
	}
	
	/** string@j_string() */
	private static String j_string(String target, Object[] args) {
		if (target == null) return null;
		return StringHelper.javaStringEnc(target);
	}
	
	/** string@js_string() */
	private static String js_string(String target, Object[] args) {
		if (target == null) return null;
		return StringHelper.javaScriptStringEnc(target);
	}
	
	/** string@cap_first() */
	private static String cap_first(String target, Object[] args) {
		if (target == null) return null;
		return StringHelper.capFirst(target);
	}

	/** string@uncap_first() */
	private static String uncap_first(String target, Object[] args) {
		if (target == null) return null;
		return StringHelper.uncapFirst(target);
	}
	
	/** string@lower_case()*/
	private static String lower_case(String target, Object[] args) {
		if (target == null) return null;
		return target.toLowerCase();
	}
	
	/** string@upper_case()*/
	private static String upper_case(String target, Object[] args) {
		if (target == null) return null;
		return target.toUpperCase();
	}

	/** string@word_list() */
	private static String[] word_list(String target, Object[] args) {
		if (target == null) return new String[0];
        java.util.ArrayList<String> result = new java.util.ArrayList<String>();
        StringTokenizer st = new StringTokenizer((String)target);
        while (st.hasMoreTokens()) {
           result.add(st.nextToken());
        }
        return result.toArray(new String[0]);
	}

	/** string@number() */
	private static Number number(String target, Object[] args) {
		if (target == null || target.length() == 0) return NullData.ZERO;
		return MathCalculator.toNumber((String)target);
	}

	/** string@number() */
	private static Integer to_int(String target, Object[] args) {
		if (target == null || target.length() == 0) return NullData.ZERO;
		return MathCalculator.toNumber((String)target).intValue();
	}

	/** string@substring(begin, [last]) */
	private static String substring(String target, Object[] args) {
		if (target == null) return null;
		if (args.length < 1) 
			throw new TemplateException("@substring 方法需要 1 或 2 个数字参数。");
		
		Object trans = null;
		try {
			String str = (String)target;
			trans = args[0];
			int begin = getIntParam(args[0]);
			if (args.length == 1) {
				if (begin >= str.length()) return "";
				return str.substring(begin);
			}
			trans = args[1];
			int end = getIntParam(args[1]);
			if (begin >= str.length() || begin >= end) return "";
			if (end >= str.length()) return str.substring(begin); 
			return str.substring(begin, end);
		} catch (NumberFormatException ex) {
			throw new TemplateException("无法将 @substring 方法需要的参数值 '" + trans + "' 转换为整数。", ex);
		}
	}

	/** string@erase_html(size) */
	private static String erase_html(String target, Object[] args) {
		if (target == null) return null;
		try {
			int size = 100;
			if (args.length >= 1) {
				size = getIntParam(args[0]);
				if (size <= 0) size = 100;
			}
			
			String str = (String)target;
			str = StringHelper.eraseHtml(str);
			if (str.length() < size) return str;
			return str.substring(0, size);
		} catch (NumberFormatException ex) {
			throw new TemplateException("无法将 @erase_html 方法需要的参数值转换为整数。", ex);
		}
	}
	
	/**
	 * string@repeat(n) - 重复指定字符串 N 次
	 * @author liujunxing
	 *
	 */
	private static String repeat(String target, Object[] args) {
		if (target == null || target.length() == 0) return target;
		try {
			if (args.length < 1)
				return target;
			int counter = getIntParam(args[0]);
			return StringHelper.repeat(target, counter);
		} catch (NumberFormatException ex) {
			throw new TemplateException("无法将 @repeat 方法需要的参数值转换为整数。", ex);
		}
	}

	/** string@left(n) - 取左边 n 个字符。 */
	private static String left(String target, Object[] args) {
		if (target == null || target.length() == 0) return target;
		try {
			if (args.length < 1)
				return target;
			int counter = getIntParam(args[0]);
			if (counter >= target.length()) return target;
			if (counter <= 0) return target;
			return target.substring(0, counter);
		} catch (NumberFormatException ex) {
			throw new TemplateException("无法将 @left 方法需要的参数值转换为整数。", ex);
		}
	}

	/** string@right(n) - 取右边 n 个字符。 */
	private static String right(String target, Object[] args) {
		if (target == null || target.length() == 0) return target;
		try {
			if (args.length < 1)
				return target;
			int counter = getIntParam(args[0]);
			if (counter >= target.length()) return target;
			if (counter <= 0) return "";
			return target.substring(target.length() - counter, target.length());
		} catch (NumberFormatException ex) {
			throw new TemplateException("无法将 @right 方法需要的参数值转换为整数。", ex);
		}
	}
	
	/** string@cleft(n) - 取宽度为 n 个的子字符串，英文算1个宽度，中文算2个宽度。 */
	private static String cleft(String target, Object[] args) {
		if (target == null || target.length() == 0) return target;
		try {
			if (args.length < 1)
				return target;
			int counter = getIntParam(args[0]);
			if (counter <= 0) return target;
			int i = 0;
			for (; i < target.length(); ++i) {
				char ch = target.charAt(i);
				if (ch < 0xFF)
					--counter;
				else
					counter -= 2;
				if (counter < 0) break;
			}
			return target.substring(0, i);
		} catch (NumberFormatException ex) {
			throw new TemplateException("无法将 @cleft 方法需要的参数值转换为整数。", ex);
		}
	}

	/** 转为所给对象为一个整数。 */
	public static final int getIntParam(Object arg) {
		if (arg == null) return 0;
		if (arg instanceof Number)
			return ((Number)arg).intValue();
		if (arg instanceof NumberDataModel)
			return ((NumberDataModel)arg).getAsNumber().intValue();
		if (arg instanceof String)
			return MathCalculator.toNumber((String)arg).intValue();
		if (arg instanceof StringDataModel)
			return MathCalculator.toNumber(((StringDataModel)arg).getAsString()).intValue();
		
		return MathCalculator.toNumber(arg.toString()).intValue();
	}
}
