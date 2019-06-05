package com.chinaedustar.publish.event;

/**
 * 定义发布系统模型的根(PublishContext)事件集合。一个 PublishContext 里面
 *   有一个 EventCollection。一个 EventCollection 中包含了多个事件。
 * 
 * @author liujunxing
 *
 */
public class PublishContextEvents {
	// === SiteModel 的事件 =================================================
	
	/** Site 的 show 事件。 */
	//public final PublishEvent onSiteProperty = null;
	
	/** Site 的 change, update() 导致此事件发生。 */
	//public final PublishEvent onSiteChanged = null;
	
	/** Site 的所有事件。 */
	//public final PublishEvent siteEvent = null; 
	  // TODO: = new MultiEvent(onShowSiteProperty, onChangeSiteProperty) 
	
	// === Channel 的事件 ===================================================
	
	/** Channel 的 show 事件。 */
	//public final PublishEvent onChannelProperty = null;
	
	/** Channel 的 change, update() 导致此事件发生。 */
	//public final PublishEvent onChannelChanged = null;
	
	/** Channel 被创建的时候产生此事件。 */
	//public final PublishEvent onChannelCreated = null;
	
	/** Channel 被删除的时候产生此事件。 */
	//public final PublishEvent onChannelDeleted = null;
	
	// === Column 的事件 =====================================================
	
	//public final PublishEvent onColumnProperty = null;
	
	//public final PublishEvent onColumnChanged = null;
	
	//public final PublishEvent onColumnCreated = null;
	
	//public final PublishEvent onColumnDeleted = null;
	
	// === Item 的事件 =======================================================
	
	//public final PublishEvent onItemProperty = null;
	
	//public final PublishEvent onItemChanged = null;
	
	//public final PublishEvent onItemCreated = null;
	
	//public final PublishEvent onItemDeleted = null;
	
	// === Menu 事件 ========================================================
	
	/** 当显示 admin 的左边导航菜单的时候产生此事件。 */
	public final MenuShowDelegate onMenuShow = new MenuShowDelegate();
	
	// === 其它事件 ==========================================================
	
	/** 所有事件的集合对象。 */
	@SuppressWarnings("unused")
	private final java.util.HashMap<String, PublishEvent> event_m = 
		new java.util.HashMap<String, PublishEvent>();

	/**
	 * 构造函数。
	 *
	 */
	public PublishContextEvents() {
		
	}
}
