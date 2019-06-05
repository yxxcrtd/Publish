package com.chinaedustar.publish.comp;

import java.util.ArrayList;

/**
 * 表示页面上面的 属性页对象(Tab) 集合。
 * 
 * @author liujunxing
 *
 */
public class Tabs extends ArrayList<Tab> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6626722653995424037L;

	/**
	 * 获得缺省的属性页。
	 * @return
	 */
	public Tab getDefault() {
		if (size() == 0) return null;
		for (int i = 0; i < size(); ++i) {
			Tab tab = get(i);
			if (tab.getDefault()) return tab;
		}
		// 使用第一个做为缺省的属性页。
		return get(0);
	}
}
