package com.chinaedustar.publish.model;

/**
 * 能够把相对 url, path 解析为绝对 url, path 的接口。
 * @author liujunxing
 *
 */
public interface UrlResolver {
	/**
	 * 为子对象计算其相对目录地址为绝对目录地址。
	 * @param child_obj_path - 子对象目录地址。
	 * @return 返回绝对化的目录，根据容器可能有所不同。
	 */
	public String resolvePath(String child_obj_path);
	
	/**
	 * 为子对象计算其相对 url 地址的绝对地址。
	 * @param rel_url - 子对象的相对地址。
	 * @return 返回绝对化过的 url 地址，可能根据配置有所不同。
	 * 当前有 Site, Channel 实现此方法了。
	 */
	public String resolveUrl(String rel_url);
	
	/**
	 * 为子对象绝对的链接地址计算其相对 url 地址，该操作是 resolveUrl 的反操作。
	 * @param abs_url - 子对象的绝对地址。
	 * @return 返回相对化过的 url 地址，可能根据配置有所不同。
	 */
	public String relativizeUrl(String abs_url);
}
