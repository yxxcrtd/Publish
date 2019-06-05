package com.chinaedustar.publish.model;

import java.util.List;

import com.chinaedustar.publish.PublishContext;
import com.chinaedustar.publish.PublishException;

/**
 * 表示一个 Site 下面的所有已经加载的模块的集合。
 * 
 * <p>这个类支持多线程并发访问。</p>
 * 
 * @author liujunxing
 */
@SuppressWarnings({ "unchecked", "rawtypes" })
public class ModuleCollection extends AbstractModelCollection<Module> 
		implements PublishModelObject, ModelCollection<Module> {

	/**
	 * 构造函数。
	 *
	 */
	public ModuleCollection() {
		
	}

	/** 获得字符串表示。 */
	@Override public String toString() {
		return "ModuleCollection{load=" + this.full_load + ",size=" + super.internal_size() + "}";
	}
	
	/**
	 * 使用具有指定标识的模块，如果没有找到则返回 null。
	 * @param moduleId
	 * @return
	 */
	public Module getModule(int moduleId) {
		// 1. 保证整个集合被装载进来了。
		this.ensureCollectionLoaded();
		
		// 2. 在现有集合中查找。
		java.util.Iterator<Module> iter = iterator();
		while (iter.hasNext()) {
			Module module = iter.next();
			if (module.getId() == moduleId) return module;
		}
		return null;
	}
	
	// === PublishModelObject 接口实现 =====================================

	/*
	 * (non-Javadoc)
	 * @see com.chinaedustar.publish.model.AbstractPublishModelBase#_init(com.chinaedustar.publish.PublishContext, com.chinaedustar.publish.model.PublishModelObject)
	 */
	@Override public void _init(PublishContext pub_ctxt, PublishModelObject owner_obj) {
		super._init(pub_ctxt, owner_obj);
	}

	/*
	 * (non-Javadoc)
	 * @see com.chinaedustar.publish.model.AbstractPublishModelBase#_destroy()
	 */
	@Override public void _destroy() {
		synchronized (this) {
			if (super.child_v != null) {
				for (int index = 0; index < super.child_v.size(); ++index) {
					Module module = super.child_v.get(index);
					this.destroyModule(module);
				}
				super.set_collection(null);
			}
			
			this.full_load = true;
		}
		super._destroy();
	}

    // === 底层实现 ===============================================================
    
    /** 是否将所有模块信息加载进来了。 */
    private transient boolean full_load = false;
    
    /** 
     * 保证所有信息已经被加载进来了。
     * 在多线程条件下此函数可能被调用多次，模块装载内部可能会再次调入此函数。 
     */
    @Override protected void ensureCollectionLoaded() {
    	if (full_load == false) {
    		synchronized (this) {
    			if (full_load == true) return;
    			
        		this.full_load = true;
        		
        		// 执行完整加载。
        		fullLoad();
    		}
    	}
    }
    
    /** 执行完整加载。 */
	private synchronized void fullLoad() throws PublishException {
    	try {
        	// 装载所有模块进入进入内存。
        	String hql = "FROM Module m ORDER BY m.id ASC";
        	List list = super._getPublishContext().getDao().list(hql);
        	
        	// 设置到模块集合中。
        	super.set_collection(list);
        	
    		// 初始化各个 module.
    		initModules(list);
    	} catch (Exception ex) {
    		throw new PublishException("在试图装载模块数据的时候发生数据库访问异常。", ex);
    	} finally {
    		
    	}
    }

    /** 初始化所有模块。 */
    private void initModules(java.util.List<Module> module_v) {
    	for (int i = 0; i < module_v.size(); ++i) {
    		initModule(module_v.get(i));
    	}
    }
    
    // 初始化内存的一个模块。
    private void initModule(Module module) {
		try {
    		module._init(super._getPublishContext(), this);
		} catch (Exception ex) {
		} finally {
			// 如果初始化状态异常，则卸载模块。
			if (module.getModuleStatus() == Module.MODULE_STATUS_MEM_EXCEPTION) {
				destroyModule(module);
			}
		}
    }
    
    // 从内存中销毁一个模块。
    private void destroyModule(Module module) {
    	if (module == null) return;
    	try {
    		module._destroy();
    	} catch (Exception ex) {
    	}
    }
}
