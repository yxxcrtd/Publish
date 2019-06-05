package com.chinaedustar.publish.comp;

import com.chinaedustar.common.util.StringHelper;
import com.chinaedustar.publish.PublishUtil;

/**
 * 表示一个字符串资源集合，其从指定文件加载。
 * 
 * @author liujunxing
 *
 */
public class StringResources extends java.util.HashMap<String, String> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8243358693492117395L;

	/**
	 * 构造函数，从指定文件使用指定字符集加载。
	 * @param file_name
	 * @param charset
	 */
	public StringResources(String file_name, String charset) throws java.io.IOException {
		internalInit(file_name, charset);
	}
	
	/**
	 * 构造。
	 */
	public StringResources() {
		
	}
	
	/**
	 * 初始化。
	 * @param file_name
	 * @param charset
	 */
	public void init(String file_name, String charset) throws java.io.IOException {
		internalInit(file_name, charset);
	}

	/** 加载之后成为只读集合，不能修改。 */
	@Override public void clear() {
		throw new UnsupportedOperationException();
	}
	
	/** 加载之后成为只读集合，不能修改。 */
	@Override public String put(String key, String value) {
		throw new UnsupportedOperationException();
	}

	/** 加载之后成为只读集合，不能修改。 */
	@Override public String remove(Object key) {
		throw new UnsupportedOperationException();
	}

	/**
	 * 某些情况下还是要添加，只能走后门？
	 * @param key
	 * @param value
	 */
	public void backdoorPut(String key, String value) {
		super.put(key, value);
	}
	
	private void internalInit(String file_name, String charset) throws java.io.IOException {
		String content = PublishUtil.readTextFile(file_name, charset);
		String[] lines = StringHelper.split(content, "\n", false);
		parseLines(lines);
	}
	
	private void parseLines(String[] lines) {
		for (int i = 0; i < lines.length; ++i) {
			String line = StringHelper.replace(lines[i].trim(), "\r", "");
			// 忽略空行和注释行。
			if (line.length() == 0) continue;
			if (line.startsWith("#") || line.startsWith("!")) continue;
			
			// 分解 key: value, 或 key = value.
			String[] kv = parseKeyValue(line);
			if (kv[0].length() > 0)
				super.put(kv[0], kv[1]);
		}
	}
	
	private String[] parseKeyValue(String line) {
		int sep_pos1 = line.indexOf(':');
		int sep_pos2 = line.indexOf('=');
		if (sep_pos1 < 0 && sep_pos2 < 0)
			return new String[] { line.trim(), "" };
		
		if (sep_pos1 < 0 || sep_pos1 > sep_pos2) 
			sep_pos1 = sep_pos2;
		String key = line.substring(0, sep_pos1).trim();
		String value = line.substring(sep_pos1 + 1).trim();
		return new String[] { key, value };
	}
}
