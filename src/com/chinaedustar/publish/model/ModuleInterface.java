package com.chinaedustar.publish.model;


/**
 * 模块的接口。
 * 
 * @author liujunxing
 */
public interface ModuleInterface extends ModelObject {
	/**
	 * 获得此模块的版本，一般格式为 1.3.4 xxx。在模块安装的时候确定。
	 * @return
	 */
	public String getVersion();
	
	/**
	 * 获得模块的状态：0 – 正常可用；1 – 新装模块还未初始化；2 – 正在初始化；3 – 不使用；4 - 初始化发生错误；5 – 正在卸载。
	 * @return
	 */
	public int getStatus();

	/**
	 * 获得此模块启动类，一般为 com.chinaedustar.publish.XxxModule，发布系统在加载的时候会实例化这个类。
	 * @return
	 */
	public String getModuleClass();
	
	/**
	 * 获得此模块的提供商名字。
	 * @return
	 */
	public String getVender();
	
	/**
	 * 获得此模块的支持说明网址。
	 * @return
	 */
	public String getUrl();
}
