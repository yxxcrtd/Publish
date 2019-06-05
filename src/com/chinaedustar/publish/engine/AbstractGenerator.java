package com.chinaedustar.publish.engine;

import java.io.*;
import com.chinaedustar.publish.PublishContext;
import com.chinaedustar.publish.PublishUtil;
import com.chinaedustar.publish.model.Channel;
import com.chinaedustar.publish.model.PageAttrObject;

/**
 * 发布系统中实现可生成接口的对象。
 * 
 * @author liujunxing
 *
 */
public abstract class AbstractGenerator implements Generator{
	/** 发布系统环境。 */
	protected PublishContext pub_ctxt;
	
	/** 回调接口。 */
	protected GenerateCallback callback;
 
	/**
	 * 构造。
	 * @param pub_ctxt
	 */
	public AbstractGenerator(PublishContext pub_ctxt) {
		this.pub_ctxt = pub_ctxt;
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.chinaedustar.publish.engine.Generatable#genObjectPages(com.chinaedustar.publish.engine.GenerateCallback)
	 */
	public boolean genObjectPages(GenerateCallback callback) {
		this.callback = callback;
		return generate();
	}

	/**
	 * 实际产生所需页面。
	 * @return 返回 true 表示还有更多项目需要生成, 返回 false 表示没有更多项目需要生成了。
	 */
	protected abstract boolean generate();

	/**
	 * 根据基准目录计算相对目录文件的绝对位置。
	 * @param base_dir
	 * @param rel_file_name
	 * @return
	 */
	protected String combineFileName(String base_dir, String rel_file_name) {
		if (base_dir.endsWith("/") || base_dir.endsWith("\\"))
			return base_dir + rel_file_name;
		else
			return base_dir + "/" + rel_file_name;
	}
	
	/**
	 * 根据频道基准物理目录计算指定的相对文件的绝对位置。
	 * @param channel
	 * @param rel_file_name
	 * @return
	 */
	protected String combineChannelFileName(Channel channel, String rel_file_name) {
		return channel.resolvePath(rel_file_name);
	}
	
	/**
	 * 将指定的文件内容写入到指定文件中，缺省使用 site.charset(GB2312) 编码。
	 * @param file_path - 文件地址。
	 * @param page_content
	 */
	protected void writePage(String file_path, String page_content) {
		// 1. 保证文件父目录存在。
		File file = new File(file_path);
		File parent_dir = new File(file.getParent());
		if (parent_dir.exists() == false) {
			if (parent_dir.mkdirs() == false)
				throw new GenerateException("不能创建文件 " + file_path + " 的父级目录，可能站点或频道等对象目录配置不正确。");
		}
		
		// 如果原来文件存在，则删除掉
		if (file.exists())
			file.delete();
		
		// 2. 创建文件并写入内容。
		try {
			PublishUtil.writeTextFile(file.getAbsolutePath(), page_content, 
					pub_ctxt.getSite().getCharset());
		} catch (java.io.IOException ex) {
			throw new GenerateException("试图写入文件 " + file_path + " 的时候发生输出异常: " + ex.getMessage(), ex); 
		}
	}

	/**
	 * 写入具有 PageAttrObject 实现的静态页面，并更新对象的生成状态。
	 * @param page_obj
	 * @param page_content
	 */
	protected void writePagedObjectPage(PageAttrObject page_obj, String page_content) {
		// 得到项目静态页面的路径。	
		String file_path = page_obj.getStaticPageUrl();
		// 如果没有给出静态化地址，则现在立刻计算。
		if (file_path == null || file_path.length() == 0) {
			page_obj.rebuildStaticPageUrl();
			file_path = page_obj.getStaticPageUrl();
		}
		if (file_path == null || file_path.length() == 0)
			throw new GenerateException("无法获得对象 " + page_obj + " 的静态化页面地址。");
		
		// 合成路径，写入文件。
		file_path = page_obj.getUrlResolver().resolvePath(file_path);
		writePage(file_path, page_content);
		
		// 更新对象的页面生成状态。
		page_obj.setIsGenerated(true);
		page_obj.updateGenerateStatus();
	}
}
