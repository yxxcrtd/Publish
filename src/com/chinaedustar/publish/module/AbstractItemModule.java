package com.chinaedustar.publish.module;

import com.chinaedustar.publish.comp.ChannelRightWrapper;
import com.chinaedustar.publish.comp.Menu;
import com.chinaedustar.publish.comp.MenuItem;
import com.chinaedustar.publish.itfc.ChannelRightModule;
import com.chinaedustar.publish.model.Admin;
import com.chinaedustar.publish.model.Channel;

/**
 * 项目类模块的基类。
 * 
 * @author liujunxing
 *
 */
public abstract class AbstractItemModule extends BaseChannelModule implements ChannelRightModule {
	public Object createRightData(Channel channel, Admin admin) {
		// 构造一个 wrapper, 其用于模板访问。
		ChannelRightWrapper wrapper = new ChannelRightWrapper(channel, admin);
		
		return wrapper;
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.chinaedustar.publish.itfc.ChannelRightModule#getTemplateName(com.chinaedustar.publish.model.Channel)
	 */
	public String getTemplateName(Channel channel) {
		return "channel_right_template";
	}

	// === ChannelModule 接口实现 ======================================================

	/*
	 * (non-Javadoc)
	 * @see com.chinaedustar.publish.model.ChannelModule#getDefaultItemName()
	 */
	public String getDefaultItemName() {
		return "项目";
	}

	/*
	 * (non-Javadoc)
	 * @see com.chinaedustar.publish.model.ChannelModule#getDefaultItemUnit()
	 */
	public String getDefaultItemUnit() {
		return "个";
	}

	// === 创建菜单用的辅助方法 ==========================================================

	/**
	 * 菜单创建器。
	 * @author liujunxing
	 *
	 */
	protected class MenuBuilder {
		private final Channel channel;
		private final Admin admin;
		private Menu menu;
		
		private int cid;
		private int channel_role;
		private String item_name;
		private String channel_name;
		
		public MenuBuilder(Channel channel, Admin admin) {
			this.channel = channel;
			this.admin = admin;
			this.menu = new Menu();
			this.cid = channel.getId();
			this.channel_role = admin.getChannelRoleValue(cid);
			this.item_name = channel.getItemName();
			this.channel_name = channel.getName();
		}
	
		public void initSetMenu(Menu menu) {
			this.menu = menu;
		}
		
		public Menu getMenu() {
			return this.menu;
		}
		
		// 给菜单中添加一个 '添加项目', '我添加的项目' 菜单项。
		public void addItemMenu(String itemClass) {
			menu.addMenuItem(new MenuItem("添加" + item_name, 
						"admin_" + itemClass + "_add.jsp?channelId=" + cid, ""));
			menu.addMenuItem(new MenuItem("我添加的" + item_name, 
					"admin_" + itemClass + "_my.jsp?channelId=" + cid, ""));
		}
		
		// '文章|图片|下载管理', '审核', '生成'
		private void addItemManageMenu(String itemClass) {
			String item_name = channel.getItemName();

			Menu child_menu = new Menu();
			// '文章管理'
			child_menu.addMenuItem(new MenuItem(item_name + "管理", 
					"admin_" + itemClass + "_list.jsp?channelId=" + cid, ""));

			// '审核'
			if (canApprove()) {
				child_menu.addMenuItem(new MenuItem("审核", 
						"admin_" + itemClass + "_approv.jsp?channelId=" + cid, ""));
			}
			
			// '生成'
			if (canGenerate()) {
				child_menu.addMenuItem(new MenuItem("生成", 
						"admin_" + itemClass + "_generate.jsp?channelId=" + cid, ""));
			}
			menu.addSubMenu(-1, child_menu);
		}
		
		// '文章管理', '审核', '生成'
		protected void addAritcleItemManageMenu() {
			addItemManageMenu("article");
		}

		// '图片管理', '审核', '生成'
		protected void addPhotoItemManageMenu() {
			addItemManageMenu("photo");
		}

		// '软件管理', '审核', '生成'
		protected void addSoftItemManageMenu() {
			addItemManageMenu("soft");
		}

		// '专题文章管理' 
		protected void addSpecialItemMenu(String itemClass) {
			if (canSpecial()) {
				menu.addMenuItem(new MenuItem("专题" + item_name + "管理", 
						"admin_special_" + itemClass + "_list.jsp?channelId=" + cid, ""));
			}
		}

		// '评论管理'
		protected void addCommentMenu() {
			if (canApprove()) {
				menu.addMenuItem(new MenuItem(item_name + "评论管理", 
						"admin_comment_list.jsp?channelId=" + cid, ""));
			}
		}

		// '回收站管理'
		protected void addRecycleMenu(String itemClass) {
			if (canGenerate()) {
				menu.addMenuItem(new MenuItem(item_name + "回收站管理", 
						"admin_recycle_" + itemClass + "_list.jsp?channelId=" + cid, ""));
			}
		}

		// 加入一条分隔线。
		protected void addSeperator() {
			// 添加一个分隔线。
			MenuItem menu_break = new MenuItem("=================");
			menu_break.setLineBreak(true);
			menu.addMenuItem(menu_break);
		}
		
		// '新闻中心设置'
		protected void addChannelSetting() {
			if (admin.checkChannelRight(cid, Admin.TARGET_CHANNEL, Admin.OPERATION_CHANNEL_MANAGE)) {
				menu.addMenuItem(new MenuItem(channel_name + "设置", 
						"admin_channel_add.jsp?channelId=" + cid, ""));
			}
		}

		// '栏目管理', '专题管理'
		protected void addColumnMenu() {
			Menu child_menu = new Menu();
			child_menu.addMenuItem(new MenuItem("栏目管理", 
					"admin_column_list.jsp?channelId=" + cid, ""));
			if (canSpecial()) {
				child_menu.addMenuItem(new MenuItem("专题管理", 
						"admin_special_list.jsp?channelId=" + cid, ""));
			}
			menu.addSubMenu(-1, child_menu);
		}

		// '上传文件管理', '清理', '顶部菜单设置', '生成'
		protected void addUploadMenu() {
			/*
			Menu child_menu = new Menu();
			child_menu.addMenuItem(new MenuItem("上传文件管理", 
					"admin_upload_manage.jsp?channelId=" + cid, ""));
			child_menu.addMenuItem(new MenuItem("清理", 
					"admin_upload_clear.jsp?channelId=" + cid, ""));
			menu.addSubMenu(-1, child_menu);
			*/
			
			/*
			child_menu = new Menu();
			child_menu.addMenuItem(new MenuItem("顶部菜单设置", "admin_unimpl.jsp", ""));
			child_menu.addMenuItem(new MenuItem("生成", "admin_unimpl.jsp", ""));
			menu.addSubMenu(-1, child_menu);
			*/

		}
		
		// '模板管理', '关键字管理', '作者管理', '来源管理'
		protected void addOtherChannelMenu() {
			boolean right_template = admin.checkChannelRight(cid, 
					Admin.TARGET_CHANNEL, Admin.OPERATION_CHANNEL_TEMPLATE);
			if (right_template) {
				menu.addMenuItem(new MenuItem(item_name + "模板页管理", 
						"admin_template_list.jsp?channelId=" + cid, ""));
			}
			boolean right_keyword = admin.checkChannelRight(cid, 
					Admin.TARGET_CHANNEL, Admin.OPERATION_CHANNEL_KEYWORD);
			// menu.addMenuItem(new MenuItem(item + "JS文件管理", "admin_js.jsp?channelId=" + sid, ""));
			if (right_keyword) {
				menu.addMenuItem(new MenuItem(item_name + "关键字管理", 
						"admin_keyword_list.jsp?channelId=" + cid, ""));
			}
			
			boolean right_author = admin.checkChannelRight(cid, 
					Admin.TARGET_CHANNEL, Admin.OPERATION_CHANNEL_AUTHOR);
			boolean right_source = admin.checkChannelRight(cid, 
					Admin.TARGET_CHANNEL, Admin.OPERATION_CHANNEL_SOURCE);
			if (right_author || right_source) {
				Menu child_menu = new Menu();
				if (right_author) {
					child_menu.addMenuItem(new MenuItem("作者管理", 
							"admin_author_list.jsp?channelId=" + cid, ""));
				}
				if (right_source) {
					child_menu.addMenuItem(new MenuItem("来源管理", 
							"admin_source_list.jsp?channelId=" + cid, ""));
				}
				menu.addSubMenu(-1, child_menu);
			}
			
			// menu.addMenuItem(new MenuItem("更新栏目XML数据", "admin_unimpl.jsp", ""));
			// menu.addMenuItem(new MenuItem("自定义字段管理", "admin_custom.jsp?channelId=" + sid, ""));
		}
		
		private boolean canApprove() {
			if (channel_role == Admin.CHANNEL_ROLE_MANAGER) return true;
			if (channel_role == Admin.CHANNEL_ROLE_EDITOR) return true;
			if (channel_role == Admin.CHANNEL_ROLE_COLUMN) return true;
			return false;
		}
		private boolean canGenerate() {
			if (channel_role == Admin.CHANNEL_ROLE_MANAGER) return true;
			if (channel_role == Admin.CHANNEL_ROLE_EDITOR) return true;
			return false;
		}
		private boolean canSpecial() {
			if (channel_role == Admin.CHANNEL_ROLE_MANAGER) return true;
			if (channel_role == Admin.CHANNEL_ROLE_EDITOR) return true;
			return false;
		}
	}

}
