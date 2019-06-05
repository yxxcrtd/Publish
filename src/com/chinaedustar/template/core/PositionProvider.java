package com.chinaedustar.template.core;

/**
 * 为一段字符串提供行号、列号信息的辅助类。行号、列号都是从 1 开始算起的。
 * 
 * 此类是有状态的。不支持线程安全。
 * 
 * @author liujunxing
 *
 */
public class PositionProvider {
	/** 要解析的字符串。 */
	private final String source;
	private final int length;
	
	/** 最后一次调用的开始位置。 */
	private PosInfo lastPos = new PosInfo(0, 1);
	
	public PositionProvider(final String source) {
		this.source = source;
		this.length = source.length();
	}
	
	/**
	 * 获得指定位置的行、列信息，并放到对象 elem 中。
	 * @param start - 此标记的绝对开始位置。
	 * @param end - 此标记的绝对结束位置。
	 * @param elem - 结果放在这里。
	 */
	public void getPosition(int start, int end, AbstractLabelElement label) {
		if (start < lastPos.absPos)
			throw new IllegalStateException("start < lastEnd");
		calcPosition(start);
		label.setStartLineCol(lastPos.line, start - lastPos.absPos + 1);
		
		calcPosition(end);
		label.setEndLineCol(lastPos.line, end - lastPos.absPos + 1);
	}
	
	/**
	 * 计算指定位置的行号、列号。
	 * @param pos
	 */
	private void calcPosition(int pos) {
		int pos_iter = lastPos.absPos;
		int line_iter = lastPos.line;
		while (true) {
			int next_line = this.source.indexOf('\n', pos_iter);
			if (next_line < 0) {
				// 没有更多字符了，都在最后一行。
				lastPos.absPos = pos_iter;
				lastPos.line = line_iter;
				lastPos.nextPos = length;
				return;
			} else if (next_line < pos) {
				// 将将继续找下一行。
				++line_iter;
				pos_iter = next_line + 1;
			} else if (next_line == pos) {
				// 恰好在字符 '\n' 上面
				lastPos.absPos = pos_iter;
				lastPos.line = line_iter;
				lastPos.nextPos = next_line + 1;
				return;
			} else {
				// next_line > pos，越过了末尾，pos 就在这一行。
				lastPos.absPos = pos_iter;
				lastPos.line = line_iter;
				lastPos.nextPos = length;
				return;
			}
		}
	}
	
	@SuppressWarnings("unused")
	private static final class PosInfo {
		PosInfo(int absPos, int line) {
			this.absPos = absPos;
			this.line = line;
		}
		int absPos;			// 此行开始的绝对位置。
		int line;			// 行。
		int nextPos;		// 下一行的开始位置。
	}
}
