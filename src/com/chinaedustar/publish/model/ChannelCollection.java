package com.chinaedustar.publish.model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import com.chinaedustar.publish.DataAccessObject;
import com.chinaedustar.publish.PublishContext;
import com.chinaedustar.publish.PublishException;
import com.chinaedustar.publish.PublishUtil;
import com.chinaedustar.publish.comp.Menu;
import com.chinaedustar.publish.comp.MenuItem;
import com.chinaedustar.publish.event.MenuShowEvent;
import com.chinaedustar.publish.event.MenuShowListener;
import com.chinaedustar.publish.module.ArticleModule;
import com.chinaedustar.publish.module.ManageMenuModule;
import com.chinaedustar.publish.module.PhotoModule;
import com.chinaedustar.publish.module.SoftModule;

/**
 * ChannelModel 对象的集合。
 * 
 * <p>ChannelModel 的初始化依赖于 Site, Module 的初始化。</p>
 * 
 * @author liujunxing
 */
@SuppressWarnings("rawtypes")
public class ChannelCollection extends AbstractModelCollection<Channel> 
		implements PublishModelObject, ModelCollection<Channel> {
	/**
	 * 构造函数。
	 *
	 */
	public ChannelCollection() {
		
	}

	/** 获得字符串表示。 */
	@Override public String toString() {
		return "ChannelCollection{load=" + this.full_load + ",size=" + super.internal_size() + "}";
	}
	
	// === PublishModelObject 接口实现 ========================================

	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jpub.model.AbstractPublishModelBase#_init(cn.edustar.jpub.PublishContext, cn.edustar.jpub.model.PublishModelObject)
	 */
	@Override public synchronized void _init(PublishContext pub_ctxt, PublishModelObject owner_obj) {
		// 在调用此函数的时候，site(==owner_obj) 已经加载进来了。
		super._init(pub_ctxt, owner_obj);
		
		// 挂接自己成为 onMenuShow 的事件侦听者。
		refreshMenuShowEventListener();
	}

	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jpub.model.AbstractPublishModelBase#_destroy()
	 */
	@Override public synchronized void _destroy() {
		// 取消对 onMenuShow 的事件侦听。
		if (this.onmenushow_listener != null && pub_ctxt != null) {
			pub_ctxt.getEvents().onMenuShow.removeListener(this.onmenushow_listener);
		}
		this.onmenushow_listener = null;
		
		// 关闭所有频道。
		if (super.child_v != null) {
			for (int index = 0; index < super.child_v.size(); ++index) {
				Channel channel = super.child_v.get(index);
				freeChannel(channel);
			}
			
			super.set_collection(null);
		}
		this.full_load = true;
		
		super._destroy();
	}

	/** 当前注册的 MenuShowListener 对象。 */
	private onMenuShowEventListener onmenushow_listener;

	/** PublishContext.onMenuShow 事件侦听者。 */
	private final class onMenuShowEventListener implements MenuShowListener {
		/**
		 * 当菜单将要显示之前，在菜单构造阶段被调用。
		 * @param event
		 */
		public void onMenuShow(MenuShowEvent event) {
			Menu menu = event.getMenu();
			
			// 生成每个频道的管理菜单。
			addChannelManageMenu(menu, event.getAdmin());
			
			// 在网站生成管理菜单下面为每个带有生成配置的频道产生菜单。
			addGenerateMenu(menu, event.getAdmin());
		}
		
		private void addChannelManageMenu(Menu menu, Admin admin) {
			Iterator<Channel> iter = iterator();
			int index = 0;
			while (iter.hasNext()) {
				Channel channel = iter.next();
				// 如果频道不是可管理频道，则继续下一个。
				if (channel.getManagable() == false) continue;
				Menu channel_menu = createChannelMenu(channel, admin);
				if (channel_menu != null) {
					menu.addSubMenu(index++, channel_menu);
				}
			}
		}
		
		private void addGenerateMenu(Menu menu, Admin admin) {
			Menu gen_menu = menu.getSubMenuById("site_generate");
			if (gen_menu == null) return;
			
			Iterator<Channel> iter = iterator();
			while (iter.hasNext()) {
				Channel channel = iter.next();
				if (channel.getNeedGenerate() == false) continue;
				
				MenuItem menu_item = new MenuItem(channel.getName() + "生成管理", 
						"admin_generate_channel.jsp?channelId=" + channel.getId(), "main");
				gen_menu.addMenuItem(menu_item);
			}
		}
		
		// 为指定频道生成菜单。
		private Menu createChannelMenu(Channel channel, Admin admin) {
			// 如果此频道加载有问题，则不创建菜单。
			if (channel.getChannelStatus() != Channel.CHANNEL_STATUS_MEM_OK) {
				return null;
			}
			
			PublishModule module = channel.getChannelModule();
			if (module == null) { // 这是不应该的，因为 Channel.init() 里面一定会设置。
				return null;
			}
			
			// 如果此频道的支持模块支持为频道创建菜单则
			if (module instanceof ManageMenuModule) {
				return ((ManageMenuModule)module).getChannelMenu(channel, admin);
			}
			
			return null;
		}
	}
	
	/** 刷新当前的 MenuShowEventListener 对象，使其按照新的频道对象列表进行重新排布。 */
	private void refreshMenuShowEventListener(){		
		// 如果已经注册过相应的事件，则应该先删除掉。
		if(this.onmenushow_listener != null)	
			pub_ctxt.getEvents().onMenuShow.removeListener(this.onmenushow_listener);

		this.onmenushow_listener = new onMenuShowEventListener();
		pub_ctxt.getEvents().onMenuShow.addListener(this.onmenushow_listener);
	}
	
	// === ChannelCollection 核心业务 =========================================
	
	public Site getSite() {
		if (this.owner_obj != null && this.owner_obj instanceof Site)
			return (Site)this.owner_obj;
		return pub_ctxt.getSite();
	}
	
	/**
	 * 获得一个测试用的频道，一般是第一个频道。
	 */
	public Channel getTestChannel() {
		java.util.Iterator<Channel> iter = iterator();
		if (iter.hasNext())
			return iter.next();
		return null;
	}
	
	/**
	 * 获得内存缓存的指定频道标识的频道模型数据。如果没有找到则返回 null.
	 * @param channelId
	 * @return
	 */
	public Channel getChannel(int channelId) {
		// 获得枚举器，并遍历所有 ChannelModel 查找 id 相同的。
		java.util.Iterator<Channel> iter = iterator();
		while (iter.hasNext()) {
			Channel channel = iter.next();
			if (channel.getId() == channelId) return channel;
		}
		return null;
	}

	/**
	 * 通过指定的网站目录名查找频道对象。
	 * @param channelDir
	 * @return 返回 channel.channelDir == channelDir 的频道，如果不存在则返回 null.
	 * 注意： channel.getChannelDir() 可能为空。
	 */
	public Channel getChannelByDir(String channelDir) {
		if (channelDir == null || channelDir.length() == 0) return null;
		
		java.util.Iterator<Channel> iter = iterator();
		while (iter.hasNext()) {
			Channel channel = iter.next();
			if (channelDir.equalsIgnoreCase(channel.getChannelDir())) return channel;
		}
		return null;
	}
	
	/**
	 * 从数据库中立刻加载指定频道对象。
	 * @param channelId
	 * @return
	 */
	public Channel loadChannel(int channelId) {
		Channel channel = (Channel)pub_ctxt.getDao().get(Channel.class, channelId);
		if (channel != null) {
			channel._init(pub_ctxt, this);
		}
		return channel;
	}
	
	/**
	 * 新建一个频道。
	 */
	public void createChannel(Channel channel) {
		// 构建模型化对象。
		channel._init(pub_ctxt, this);
		
		synchronized (this) {
			// 1. 创建 Channel 对象。
			channel.setChannelOrder(this.getMaxChannelOrder() + 1);

			// 插入到 DB 中。
			pub_ctxt.getDao().save(channel);
			pub_ctxt.getDao().flush();

			if (channel.getChannelType() == Channel.CHANNEL_TYPE_INTERNAL) {
				// 如果是内部频道则创建根目录。
				channel._getCreateRootColumn();
				
				// 以及复制模板
				copyTemplates(channel);
				
				// 创建频道目录、上传目录
				createChannelDir(channel);
				
				// 创建所需文件 (index.jsp 等)
				createChannelRequiredFiles(channel);	
			}
			pub_ctxt.getDao().flush();

			// 更新内存。
			reloadLoad();
		}
	}
	
	/**
	 * 生成频道对象的根目录、上载文件目录。
	 * @param channel 频道对象。
	 */
	private void createChannelDir(Channel channel) {
		String channel_dir = pub_ctxt.getRootDir() + "\\" + channel.getChannelDir();
		new File(channel_dir).mkdirs();
		String channel_upload_dir = channel_dir + "\\" + channel.getUploadDir();
		new File(channel_upload_dir).mkdirs();
	} 
	
	/**
	 * 创建频道对象所需要的文件。
	 */
	private void createChannelRequiredFiles(Channel channel) {	
		String root_path = pub_ctxt.getRootDir();
		//得到zip源文件名称
		String zipSourceName = root_path + "\\WEB-INF\\resource";		
		ChannelModule module = channel.getChannelModule();
		if (module == null) return;		
		zipSourceName += "\\" + module.getModuleName() + ".zip";
		
		//得到要复制到的目标路径
		String targetDirPath = root_path + "\\" + channel.getChannelDir();
		
		//开始复制
		copyZip2Target(zipSourceName, targetDirPath);
	}

	/**
	 * 将zip源文件解压到目标路径。
	 * @param zipSourceName zip源文件名称。
	 * @param targetDirPath 目标路径，后面不能带\。
	 */
	private void copyZip2Target(String zipSourceName, String targetDirPath) {
		try {		
			ZipInputStream zipInputStream = new ZipInputStream(new FileInputStream(zipSourceName));
			//得到下一个zip包里边的文件实体
			ZipEntry zipEntry = zipInputStream.getNextEntry();	

			//数据缓存
			byte[] buffer = new byte[1024];
			//实际读出的字节数
			int readNumber;		

			while (zipEntry != null) {
				String fileName = zipEntry.getName().replace("/", "\\");

				if (fileName.lastIndexOf("\\") == (fileName.length() - 1)) {
					new File(targetDirPath + "\\" + fileName.substring(0, (fileName.length() - 1))).mkdir();
				} else {
					FileOutputStream fileOutStream = new FileOutputStream(targetDirPath + "\\" + fileName);
					
					while ((readNumber = zipInputStream.read(buffer, 0, buffer.length)) != -1) {
						fileOutStream.write(buffer, 0, readNumber);
					}
					fileOutStream.close();
				}
				zipEntry = zipInputStream.getNextEntry();
			}

		} catch (FileNotFoundException e) {
			throw new PublishException("没有找到对应的文件。");
		} catch (IOException e) {
			throw new PublishException("无法加载程序包：" + zipSourceName );
		}
		
	}

	/**
	 * 更改一个频道的各种属性。
	 * @param channel
	 */
	public void updateChannel(Channel channel) {
		if (channel.getId() == 0) {
			createChannel(channel);
			return;
		}
		
		// 更新静态化地址
		channel.rebuildStaticPageUrl();
		synchronized (this) {
			
			// 1. 找到当前集合中的 Channel.
			Channel orig_channel = this.getChannel(channel.getId());
			if (orig_channel == null)
				throw new PublishException("要更新的频道 " + channel.getName() + " 不存在。");
			
			// 更新到 DB 中。
			pub_ctxt.getDao().save(channel);
			channel.getExtends().update();
			pub_ctxt.getDao().flush();
			
			// 更新内存数据。
			reloadLoad();
			
			//更新菜单的显示
			refreshMenuShowEventListener();			
		}
	}
	
	/**
	 * 更新相关对象的“是否生成”选项。 
	 * @param oriChannel 内存中的频道。
	 * @param curChannel 当前的频道。
	 *//*
	private void updateRelationIsGenerate(Channel oriChannel, Channel curChannel) {
		if (curChannel.getUseCreateHTML() != oriChannel.getUseCreateHTML()) {
			switch (curChannel.getUseCreateHTML()) {
			case 0:	
				// 频道首页、所有属于该频道的栏目、专题、内容(及其他)的isGenerated都更新为false。
				break;
			case 1:
				// 不做任何处理。
				break;
			case 2:
				// 除首页、内容页外，所有属于该频道的栏目、专题（及其他）的isGenerated都更新为false
				break;
			case 3:
				// 除首页、所有属于该频道的栏目、专题、内容外，其他的isGenerated都更新为false。
			}
		}
	}*/
	
	/**
	 * 禁用一个频道
	 * @param channelId
	 *//*
	public void disableChannel(int channelId){
		synchronized (this) {
			//找到当前集合中的Channel
			Channel orig_channel=this.getChannel(channelId);
			
			if(orig_channel==null){
				throw new PublishException("要禁用的频道（ID=" + channelId + "）不存在。");
			}
			//更新到 DB 中
			orig_channel.setStatus(1);
			pub_ctxt.getDao().save(orig_channel);
			
			//更新内存数据
			orig_channel.copy(orig_channel);
		}
	}*/
	/**
	 * 启用一个频道
	 * @param channelId
	 *//*
	public void unDisableChannel(int channelId){
		synchronized (this) {
			//找到当前集合中的Channel
			Channel orig_channel=this.getChannel(channelId);
			
			if(orig_channel==null){
				throw new PublishException("要启用的频道（ID=" + channelId + "）不存在。");
			}
			//更新到 DB 中
			orig_channel.setStatus(0);
			pub_ctxt.getDao().save(orig_channel);
			
			//更新内存数据
			orig_channel.copy(orig_channel);
		}
	}*/
	
	/**
	 * 删除一个频道，并放回到回收站。
	 * @param channel - 频道对象。
	 */
	public void deleteChannel(Channel channel) {
		synchronized (this) {
			if (channel.getChannelType() == Channel.CHANNEL_TYPE_SYSTEM)
				throw new PublishException("试图删除系统频道 '" + channel.getName() + "' 被拒绝。");
			
			// TODO: raise ChannelDeleting events.
			
			String hql = "UPDATE Channel SET status = 2 WHERE id = " + channel.getId();
			pub_ctxt.getDao().bulkUpdate(hql);
			pub_ctxt.getDao().clear();
			
			// 重新加载所有 Channel.
			reloadLoad();
			
			//更新菜单的显示
			refreshMenuShowEventListener();
			
			// TODO: raise ChannelDeleted events.
		}
	}
	
	/**
	 * 从回收站中恢复一个频道。
	 *
	 */
	public void recoverChannel(Channel channel) {
		synchronized (this) {
			String hql = "UPDATE Channel SET status = 0 WHERE id = " + channel.getId();
			
			pub_ctxt.getDao().bulkUpdate(hql);
			pub_ctxt.getDao().clear();
			
			// 重新加载所有 Channel.
			reloadLoad();
			
			//更新菜单的显示
			refreshMenuShowEventListener();
		}
	}
	
	/**
	 * 彻底销毁一个频道。
	 * 此销毁频道仅仅是从数据库删除，不涉及物理目录。
	 */
	public void destroyChannel(Channel channel) {
		DataAccessObject dao = pub_ctxt.getDao();
		// 1. 从 db 中删除所有频道相关内容。
		//  权限项。
		AdminRight.deleteChannelRights(dao, channel.getId());
		//  Author, Keyword, Source, Comments
		AuthorCollection.deleteChannelAuthors(dao, channel.getId());
		KeywordCollection.deleteChannelKeywords(dao, channel.getId());
		SourceCollection.deleteChannelSources(dao, channel.getId());
		CommentCollection.deleteChannelComments(dao, channel.getId());
		//  Item_Special, Item, Special, Column
		deleteChannelItems(dao, channel.getId());
		deleteChannelSpecials(dao, channel.getId());
		deleteChannelColumns(dao, channel.getId());
		// Template
		TemplateThemeCollection.deleteChannelTemplates(dao, channel.getId());
		// Channel
		dao.bulkUpdate("DELETE FROM Channel WHERE id = " + channel.getId());
	}
	
	/**
	 * 安全的深度删除一个目录，包含其所有子文件、子目录。
	 * @param dir
	 */
	public static void deepDeleteDirectory(String dir) {
		java.io.File dir_obj = new java.io.File(dir);
		if (dir_obj.exists() == false) return;
		if (dir_obj.isDirectory() == false) return;
		deepDeleteDirectory(dir_obj);
	}
	
	private static void deepDeleteDirectory(File dir) {
		File[] children = dir.listFiles();
		if (children != null && children.length > 0) {
			for (int i = 0; i < children.length; ++i) {
				if (children[i].isDirectory())
					deepDeleteDirectory(children[i]);	// 递归删除子目录。
				else {
					children[i].delete();
				}
			}
		}
		
		// 删除自己。
		dir.delete();
	}
	
	/** 删除一个频道下所有项目。 */
	private static void deleteChannelItems(DataAccessObject dao, int channelId) {
		// 删除所有 RefSpecialItem 关联。
		String hql = "DELETE FROM RefSpecialItem WHERE itemId IN (SELECT id FROM Item WHERE channelId = " + channelId + ")";
		dao.bulkUpdate(hql);
		
		// 由于 item 映射方法不同，也许这样以后删除有问题，虽然现在没有问题。
		hql = "DELETE FROM Item WHERE channelId = " + channelId;
		dao.bulkUpdate(hql);
	}
	
	/** 删除一个频道下所有专题。 */
	private static void deleteChannelSpecials(DataAccessObject dao, int channelId) {
		String hql = "DELETE FROM Special WHERE channelId = " + channelId;
		dao.bulkUpdate(hql);
	}
	
	/** 删除一个频道下所有栏目。 */
	private static void deleteChannelColumns(DataAccessObject dao, int channelId) {
		String hql = "DELETE FROM Column WHERE channelId = " + channelId;
		dao.bulkUpdate(hql);
	}
	
	/**
	 * 使能或禁用一个频道。
	 * @param channel
	 * @param enable_status
	 */
	public void enableChannel(Channel channel, boolean enable_status) {
		synchronized (this) {
			if (channel.getStatus() == Channel.CHANNEL_STATUS_DELETING.getCode())
				throw new PublishException("频道 '" + channel.getName() + "' 已经被删除，不能进行禁用/启用操作。");
			
			String hql = "UPDATE Channel SET status = " + 
				(enable_status ? Channel.CHANNEL_STATUS_NORMAL.getCode() : Channel.CHANNEL_STATUS_DISABLED.getCode()) +
				" WHERE id = " + channel.getId();
			pub_ctxt.getDao().bulkUpdate(hql);
			
			// 重新加载所有 Channel.
			reloadLoad();
			
			//更新菜单的显示
			refreshMenuShowEventListener();
		}
	}
	
	/**
	 * 实际地删除一个频道。
	 * @param channelId 频道标识。
	 */
	public void realDeleteChannel(int channelId) {
		synchronized (this) {
			// 试图找到这个频道。
			Channel channel = getChannel(channelId);
			if (channel == null)
				throw new PublishException("指定标识为 " + channelId + " 的频道不存在。");
			
			// TODO: raise ChannelDeleting events.
			
			internalDeleteChannel(channel);
			
			// TODO: raise ChannelDeleted events.
		}
	}
	
	/**
	 * 对指定的频道对象进行排序。
	 * @param channelIds 指定的频道对象标识的数组。
	 */
	public void reorderChannel(List<Integer> new_order_ids) {
		if (new_order_ids == null) return;
		
		// 1. 按照新顺序一个一个更新 channelOrder
		for (int i = 0; i < new_order_ids.size(); ++i) {
			String hql = "UPDATE Channel SET channelOrder = " + (i + 1) + 
			   " WHERE id = " + new_order_ids.get(i);
			pub_ctxt.getDao().bulkUpdate(hql);
		}

		synchronized (this) {
			// 重新加载 Channel 集合。
			reloadLoad();
			
			//更新菜单的显示
			refreshMenuShowEventListener();
		}
	}
		
	// 实际删除一个频道。
	private void internalDeleteChannel(Channel channel) {
		// 从数据库中删除。
		pub_ctxt.getDao().delete(channel);
		
		//删除频道的目录
		if (channel.getChannelType() != 2) {
			deleteDir(pub_ctxt.getRootDir() + channel.getChannelDir());
		}
		
		// 释放此 Channel 对象。
		freeChannel(channel);
		
		// 从内存中删除。
		List<Channel> copy_v = new java.util.ArrayList<Channel>();
		copy_v.addAll(super.child_v);
		copy_v.remove(channel);
		super.set_collection(copy_v);
		
		
		//刷新频道的菜单显示。
		refreshMenuShowEventListener();
	}
	
	/**
	 * 删除文件夹。
	 * @param dirPath 文件夹路径。要求最后不带\。
	 */
	@SuppressWarnings("unused")
	private void deleteDir(String dirPath) {
		File dir = new File(dirPath);
		if (dir == null) return;
		
		String[] files = dir.list();
		if (files != null) {
			for (int i = 0; i< files.length; i++) {
				File file = new File(dirPath + "\\" + files[i]);
				if (file.isDirectory()) {
					deleteDir(dirPath + "\\" + files[i]);
				} else if (file.isFile()) {
					new File(dirPath + "\\" + files[i]).delete();
				}
			}
		}
		
		dir.delete();
	}

	
	/**
	 * 得到最大的排序数字，用来给新加的频道
	 * @return 频道对象最大的排序数。
	 */
	public int getMaxChannelOrder() {
		int maxOrder = 0;
		String hql = "SELECT Max(channelOrder) from Channel";
		List list = super._getPublishContext().getDao().list(hql);
		if(list != null && list.size() > 0) {
			maxOrder = (Integer)list.get(0);
		}
		return maxOrder;
	}
	
	// === 实现 ================================================================
	
    /** 是否将所有模块信息加载进来了。 */
    private boolean full_load = false;
    
	/**
	 * 派生类必须实现的，保证集合被完整的装载了，其在将要完整访问集合的时候调用。
	 * 派生类必须保证多线程的安全性。
	 */
	@Override protected void ensureCollectionLoaded() {
		if (full_load == true) return;
		synchronized (this) {
			if (full_load == true) return;
			
    		this.full_load = true;
    		
    		// 执行完整加载。
    		reloadLoad();
		}
    }
    
    /** 执行完整加载。 */
	private synchronized void reloadLoad() throws PublishException {
    	try {
			// 1. 从数据库加载所有状态正常的 Channel 信息到内存。
	    	String hql = "FROM Channel WHERE status = 0 ORDER BY channelOrder ASC, id ASC";
	    	List list = pub_ctxt.getDao().list(hql);
	    	
			super.set_collection(list);
			
	    	// 初始化内存 ChannelModel, 在完成的完整的初始化之后, ChannelModel 对象才可用。
	    	for (int index = 0; index < list.size(); ++ index) {
	    		Channel channel = (Channel)list.get(index);
	    		initChannel(channel);
	    	}
    	} catch (Exception ex) {
    		throw new PublishException(ex);
    	}
    }
    
    /** 初始化频道。 */
    private void initChannel(Channel channel) {
		try {
			channel._init(super._getPublishContext(), this);
		} catch (Exception ex) {
		} finally {
			// 如果初始化状态异常，则卸载模块。
			if (channel.getChannelStatus() == Channel.CHANNEL_STATUS_MEM_EXCEPTION) {
				freeChannel(channel);
			}
		}
    }
    
    /** 内存中释放一个频道。 */
    private void freeChannel(Channel channel) {
    	if (channel == null) return;
    	try {
    		channel._destroy();
    	} catch (Exception ex) {
    	}
    }
    
    // === 业务方法 ====================================================================

	/**
	 * 从数据库立刻加载所有频道列表，用于管理使用。
	 *  包括禁用的，不含被删除的。
	 * @return
	 */
	public List<Channel> loadChannelList() {
		String hql = " FROM Channel WHERE status <> 2 ORDER BY channelOrder, id ";
		@SuppressWarnings("unchecked")
		List<Channel> list = pub_ctxt.getDao().list(hql);
		PublishUtil.initModelList(list, pub_ctxt, this);
		return list;
	}
	
	/**
	 * 从数据库立刻加载所有被删除的频道，用于管理。
	 * @return
	 */
	public List<Channel> getRecycleChannelList() {
		String hql = " FROM Channel WHERE status = 2 ORDER BY channelOrder, id ";
		@SuppressWarnings("unchecked")
		List<Channel> list = pub_ctxt.getDao().list(hql);
		PublishUtil.initModelList(list, pub_ctxt, this);
		return list;
	}
	
	/**
	 * 从数据库立刻加载当前可用频道列表，不包括禁用和删除的，用于管理使用。
	 * @return
	 */
	public List<Channel> loadActiveChannelList() {
		String hql = " FROM Channel WHERE status = 0 ORDER BY channelOrder, id ";
		@SuppressWarnings("unchecked")
		List<Channel> list = pub_ctxt.getDao().list(hql);
		PublishUtil.initModelList(list, pub_ctxt, this);
		return list;
	}

    /**
     * 获得频道列表，列表是当前内存频道列表的一个备份。
     * @return - List&lt;Channel&gt; 频道集合。 
     */
    public java.util.List<Channel> getChannelList() {
    	java.util.ArrayList<Channel> list = new java.util.ArrayList<Channel>();
    	java.util.Iterator<Channel> iter = this.iterator();
    	while (iter.hasNext()) {
    		list.add(iter.next());
    	}
    	return list;
    }
    
    /**
     * 获得设置有生成属性的频道列表。
     * @return List&lt;Channel&gt;
     */
    public java.util.List<Channel> getGenereteChannelList() {
    	java.util.List<Channel> list = new java.util.ArrayList<Channel>();
    	java.util.Iterator<Channel> iter = this.iterator();
    	while (iter.hasNext()) {
    		Channel channel = iter.next();
    		if (channel.getNeedGenerate())
    			list.add(channel);
    	}
    	return list;
    }
    
    /**
     * 复制指定频道的模板。
     * @param channel - 目标频道。
     */
	public void copyTemplates(Channel channel) {
		int moduleId = channel.getModuleId();
		if (moduleId == 0) return;
		
		// TODO: 这里有硬代码， src_channel_id = xxx, 我们有什么好办法没有？
		int src_channel_id = 0;
    	Module module = pub_ctxt.getSite().getModules().getModule(channel.getModuleId());
		if (module.getPublishModule() instanceof ArticleModule) {
			src_channel_id = 1;
		} else if (module.getPublishModule() instanceof PhotoModule) {
			src_channel_id = 2;
		} else if (module.getPublishModule() instanceof SoftModule) {
			src_channel_id = 3;
		} else
			return;
		if (src_channel_id == channel.getId()) return;
		int themeId = pub_ctxt.getSite().getDefaultTheme().getId();
		
		// 找到缺省方案下源频道所有缺省模板。
		String hql = " FROM PageTemplate WHERE themeId = " + themeId + "" +
				" AND channelId = " + src_channel_id + 
				" AND isDefault = true";
		@SuppressWarnings("unchecked")
		List<PageTemplate> list = pub_ctxt.getDao().list(hql);
		if (list == null || list.size() == 0) return;
		
		// 复制这些模板到新频道中。
		for (int i = 0; i < list.size(); ++i) {
			PageTemplate template = list.get(i);
			PageTemplate new_template = new PageTemplate();
			new_template.setName(template.getName());
			new_template.setThemeId(template.getThemeId());
			new_template.setChannelId(channel.getId());
			new_template.setTypeId(template.getTypeId());
			new_template.setContent(template.getContent());
			new_template.setThemeDefault(false);
			new_template.setIsDefault(template.getIsDefault());
			new_template.setDeleted(false);
			pub_ctxt.getDao().insert(new_template);
		}
		pub_ctxt.getDao().flush();
		
		// 清除缓存。
		pub_ctxt.getSite().getTemplateThemeCollection().clearPageTemplateCache();
    }
    
    /**
     * 删除频道的模板
     * @param channel
     */
    public void deleteTemplates(int channelId) {
    	String hql = "DELETE FROM PageTemplate WHERE channelId=" + channelId;
		super._getPublishContext().getDao().bulkUpdate(hql);
    }
}

