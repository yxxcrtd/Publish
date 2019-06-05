package com.chinaedustar.publish.model;

/**
 * 一般容器接口。
 * 
 * @author liujunxing
 */
public interface GeneralContainer extends ModelObject {
	/**
	 * 获得专题容器标识，是频道标识。
	 * @return 返回 0 表示 site; 返回 > 0 数字表示 channel 标识。
	 *  更多数字含义可以由使用者决定(如 -1，-2)
	 */
	public int getChannelId();
}
