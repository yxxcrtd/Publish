package com.chinaedustar.publish.model;

import com.chinaedustar.common.util.ClassHelper;
import com.chinaedustar.publish.PublishContext;
import com.chinaedustar.publish.PublishException;

/**
 * 模块的 Module 模型对象。
 * 
 * @author liujunxing
 */
@SuppressWarnings("rawtypes")
public class Module extends AbstractNamedModelBase 
		implements ModuleInterface, PublishModelObject {
    
	// === Module 持久状态和内存状态 ==============================================
	
	/** 正常状态，表示此模块安装正确完成。 */
	public static final ModuleStatus MODULE_STATUS_NORMAL = new ModuleStatus(0, "正常");
	
	/** 新安装的模块，还没有完成安装。 */
	public static final ModuleStatus MODULE_STATUS_NEW = new ModuleStatus(1, "新模块");
	
	/** 新安装的模块，正在进行安装初始化。 */
	public static final ModuleStatus MODULE_STATUS_SETUP = new ModuleStatus(2, "新模块正在安装");
	
	/** 此模块被禁用。 */
	public static final ModuleStatus MODULE_STATUS_DISABLE = new ModuleStatus(3, "禁用");
	
	/** 此模块在安装及初始化过程中发生错误。 */
	public static final ModuleStatus MODULE_STATUS_ERROR = new ModuleStatus(4, "模块安装错误");
	
	/** 此模块正在被卸载。 */
	public static final ModuleStatus MODULE_STATUS_UNSETUP = new ModuleStatus(5, "模块正在卸载");

	/** 此模块状态非法。 */
	public static final ModuleStatus MODULE_STATUS_INVALID = new ModuleStatus(6, "非法状态");

	/** 内存状态 - 模块加载正常完成，此模块在内存是正常可用的了。 */
	public static final ModuleStatus MODULE_STATUS_MEM_OK = new ModuleStatus(-1, "加载成功");
	
	/** 内存状态 - 此模块正在向内存中加载。 */
	public static final ModuleStatus MODULE_STATUS_MEM_INITING = new ModuleStatus(-2, "正在加载模块");
	
	/** 内存状态 - 此模块试图加载到内存中的时候发生异常。 */
	public static final ModuleStatus MODULE_STATUS_MEM_EXCEPTION = new ModuleStatus(-3, "加载模块异常");
	
	// === member ===============================================================
	/** 模块的英文名称 */
	private String title;
	
	/** 此模块的版本，一般格式为 1.3.4 xxx。在模块安装的时候确定。 */
	private String version;
	
	/** 模块的状态：0 – 正常可用；1 – 新装模块还未初始化；2 – 正在初始化；3 – 不使用；4 - 初始化发生错误；5 – 正在卸载。 */
	private int status;
	
	/** 此模块启动类，一般为 com.chinaedustar.publish.XxxModule，发布系统在加载的时候会实例化这个类。 */
	private String moduleClass;
	
	/** 此模块的提供商名字。 */
	private String vender;
	
	/** 此模块的支持说明网址。 */
	private String url;
	
	/** 此模块的实现类的实例。 */
	private PublishModule module_impl;
	
	/** 当前模块的状态。 */
	private ModuleStatus module_status;
	
	// === Module 实现 ============================================================
	
	public Module() {
	}
	
	/**
	 * 获得当前模块在内存中装载的状态。
	 * @return
	 */
	public ModuleStatus getModuleStatus() {
		return this.module_status;
	}
	
	/**
	 * 获得加载的模块接口实现。
	 * @return
	 */
	public PublishModule getPublishModule() {
		return module_impl;
	}
	/**
	 * 获得次模块的英文名称。
	 * @return
	 */
	public String getTitle() {
		return title;
	}
	/**
	 * 设置模块的英文名称。
	 * @param title
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * 获得此模块的版本，一般格式为 1.3.4 xxx。在模块安装的时候确定。
	 * @return
	 */
	public String getVersion() {
		return this.version;
	}
	
	/**
	 * 设置此模块的版本，一般格式为 1.3.4 xxx。在模块安装的时候确定。
	 * @param version
	 */
	public void setVersion(String version) {
		this.version = version;
	}
	
	/**
	 * 获得模块的状态：0 – 正常可用；1 – 新装模块还未初始化；2 – 正在初始化；3 – 不使用；4 - 初始化发生错误；5 – 正在卸载。
	 * @return
	 */
	public int getStatus() {
		return this.status;
	}

	/**
	 * 设置模块的状态：0 – 正常可用；1 – 新装模块还未初始化；2 – 正在初始化；3 – 不使用；4 - 初始化发生错误；5 – 正在卸载。
	 * @param status
	 */
	public void setStatus(int status) {
		this.status = status;
	}

	/**
	 * 获得此模块启动类，一般为 com.chinaedustar.publish.XxxModule，发布系统在加载的时候会实例化这个类。
	 * @return
	 */
	public String getModuleClass() {
		return this.moduleClass;
	}
	
	/**
	 * 设置此模块启动类，一般为 com.chinaedustar.publish.XxxModule，发布系统在加载的时候会实例化这个类。
	 * @param moduleClass
	 */
	public void setModuleClass(String moduleClass) {
		this.moduleClass = moduleClass;
	}
	
	/**
	 * 获得此模块的提供商名字。
	 * @return
	 */
	public String getVender() {
		return this.vender;
	}
	
	/**
	 * 设置此模块的提供商名字。
	 * @param vender
	 */
	public void setVender(String vender) {
		this.vender = vender;
	}
	
	/**
	 * 获得此模块的支持说明网址。
	 * @return
	 */
	public String getUrl() {
		return this.url;
	}
	
	/**
	 * 设置此模块的支持说明网址。
	 * @param url
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	// === PublishModelObject 接口实现 =============================================

	/*
	 * (non-Javadoc)
	 * @see com.chinaedustar.publish.model.AbstractPublishModelBase#_init(com.chinaedustar.publish.PublishContext, com.chinaedustar.publish.model.PublishModelObject)
	 */
	@Override public void _init(PublishContext pub_ctxt, PublishModelObject owner_obj) {
		super._init(pub_ctxt, owner_obj);
		
		// 根据持久状态设置模块内存状态。
		this.module_status = calcModuleStatus(getStatus());
		
		// 如果模块持久状态为正常，则加载模块进入内存并初始化。
		if (this.module_status == MODULE_STATUS_NORMAL) {
			// 假定模块装载发生了错误。
			ModuleStatus last_status = MODULE_STATUS_MEM_EXCEPTION;
			try {
				this.module_status = MODULE_STATUS_MEM_INITING;	// 正在加载。
				PublishModule module_impl = this.instanceModule();
				module_impl.initialize(super._getPublishContext());
				this.module_impl = module_impl;
				last_status = MODULE_STATUS_MEM_OK;		// 加载成功。
			} finally {
				this.module_status = last_status;	// ok or ex.
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.chinaedustar.publish.model.AbstractPublishModelBase#_destroy()
	 */
	@Override public void _destroy() {
		// 如果是内存状态，则设置回 NORMAL 状态。
		if (this.module_status == MODULE_STATUS_MEM_OK ||
				this.module_status == MODULE_STATUS_MEM_EXCEPTION ||
				this.module_status == MODULE_STATUS_MEM_INITING) {
			this.module_status = MODULE_STATUS_NORMAL;
		}
		
		// 卸载模块。
		if (this.module_impl != null) {
			this.module_impl.destroy();
			this.module_impl = null;
		}

		super._destroy();
	}

	// === 实现 ====================================================================
	
    // 实例化模块实现类。
	private PublishModule instanceModule() throws PublishException {
    	// 尝试使用装载本 tag 类的装载器来装载指定类。
    	String moduleClass = getModuleClass();
    	Class clazz = null;
    	try {
    		clazz = ClassHelper.classForName(moduleClass, this.getClass());
    	} catch (ClassNotFoundException ex) {
    		// 无法找到此类，则我们抛出异常。
    		throw new PublishException("无法获得类 '" + moduleClass + "' 的类信息，无法找到该类。请确定写的类名正确，大小写正确，及该类存在。", ex);
    	}
    	
    	if (clazz == null)		// ?? 可能发生吗??
    		throw new PublishException("Class.forName('" + moduleClass + "') 返回为 null。");
    	
    	// 检查这个类是否实现了 PublishModule 接口。
    	if (ClassHelper.clazzImplementInterface(clazz, PublishModule.class) == false)
    		throw new PublishException("指定的模块实现类 '" + moduleClass + "' 不是一个类，或者其未实现所需的 Module 接口。");
    	
    	Object instance = null;
    	try {
    		instance = clazz.newInstance();
    	} catch (IllegalAccessException ex) {
    		throw new PublishException("在试图创建类 '" + moduleClass + "' 的一个实例的时候发生访问异常。", ex);
    	} catch (InstantiationException ex) {
    		throw new PublishException("在试图创建类 '" + moduleClass + "' 的一个实例的时候发生实例化异常。", ex);
    	}
    	
    	if (instance == null) 	// ?? 可能发生吗??
    		throw new PublishException("'" + moduleClass + "' 的 clazz.newInstance() 返回为 null。");

    	if (instance instanceof PublishModule)
    		return (PublishModule)instance;
    	// 不可能的，前面检查过了。
    	throw new PublishException("'" + moduleClass + "' 未实现所需的 Module 接口。");
    }

    /**
     * 根据数据库中模块持久状态计算出内存状态。
     * @param db_status
     * @return
     */
    private static ModuleStatus calcModuleStatus(int db_status) {
    	switch (db_status) {
    	case 0: return MODULE_STATUS_NORMAL;
    	case 1: return MODULE_STATUS_NEW;
    	case 2: return MODULE_STATUS_SETUP;
    	case 3: return MODULE_STATUS_DISABLE;
    	case 4: return MODULE_STATUS_ERROR;
    	case 5: return MODULE_STATUS_UNSETUP;
    	// 其它状态都是非法状态。
    	default: return MODULE_STATUS_INVALID;
    	}
    }
    
    /**
     * 得到模块对应的模板组标识
     * @return
     */
    public int getTemplateGroupId(){
    	String hql="select id from TemplateType where type=1 AND name=?";
    	java.util.List list=this._getPublishContext().getDao().getHibernateTemplate().find(hql,this.getName());
    	if(list!=null && list.size()>0){
    		return Integer.parseInt(list.get(0).toString());
    	}
    	return 0;
    }
}
