package com.chinaedustar.publish.label;

import java.util.*;

import com.chinaedustar.publish.comp.OpenType;
import com.chinaedustar.publish.itfc.LabelHandler2;
import com.chinaedustar.publish.model.*;
import com.chinaedustar.template.core.AttributeCollection;
import com.chinaedustar.template.core.LocalContext;

/**
 * 友情站点有关的标签。
 * 
 * @author liujunxing
 *
 */
public final class GeneralFriendSiteLabel extends GroupLabelBase {
	/** 不需要实例化。 */
	private GeneralFriendSiteLabel() {
	}

	/**
	 * 注册 LabelHandler.
	 *
	 */
	public static final void registerHandler(LabelHandlerMap map) {
		// logger.info("GeneralFriendSiteLabel 注册了其包含的一组标签解释器。");
		
		map.put("ShowFriendSite", new ShowFriendSiteHandler());
		map.put("ShowFriendSiteList", new ShowFriendSiteListHandler());
		map.put("ShowFriendSiteViewList", new ShowFriendSiteViewListHandler());
	}

	/** 
	 * 显示友情链接.
	 * #{ShowFriendSite linkType, kindId, specialId, maxNum, cols, showStyle, delay, height} 
	 * @param linkType - 链接类型。文字链接 = 1；图片链接 = 2。默认为1。
	 * @param kindId - 链接类别的标识。默认为0，表示所有的类别。
	 * @param specialId - 链接专题的标识。默认为0，表示所有的专题。
	 * @param command - true 表示显示推荐的站点，false 表示显示非推荐的站点，null 表示任何站点。默认为null。
	 * @param maxNum - 最多显示站点个数。 默认为14。
	 * @param orderBy - 按什么排序。默认为 0 。即按排序ID降序。
	 *  (以上参数影响获取数据的方式)
	 * @param cols - 每行显示多少个站点。 默认为7。
	 * @param tableWidth - 显示宽度。 默认为100%。
	 * @param showStyle - 显示方式。1：向上滚动；2：横向列表；3：下拉列表。默认为2。
	 * 
	 * 以下只有在showStyle为1时才有用
	 * @param delay  停留时间，默认为20毫秒，时间越长，向上滚动所需的时间越长
	 * @param width 向上滚动的内容的宽度，默认为100%。
	 * @param height 向上滚动的内容的高度，默认为40。
	 */
	static final class ShowFriendSiteHandler extends AbstractLabelHandler {
		/*
		 * (non-Javadoc)
		 * @see com.chinaedustar.publish.label.AbstractLabelHandler#handleLabel()
		 */
		@Override protected int handleLabel(){
			//获得参数
			AttributeCollection coll = label.getAttributes();
			int linkType = coll.safeGetIntAttribute("linkType", 0);
			int kindId = coll.safeGetIntAttribute("kindId", 0);
			int specialId = coll.safeGetIntAttribute("specialId", 0);
			int maxNum = coll.safeGetIntAttribute("maxNum", 14);
			int cols = coll.safeGetIntAttribute("cols", 7);
			String tableWidth = coll.safeGetStringAttribute("tableWidth", "100%");
			int showStyle = coll.safeGetIntAttribute("showStyle", 2);
			Boolean commend = coll.safeGetBoolAttribute("commend", null);
			int orderBy = coll.safeGetIntAttribute("orderBy", 0);
			
			int delay = coll.safeGetIntAttribute("delay", 20);
			String width = coll.safeGetStringAttribute("width", "100%");
			String height = coll.safeGetStringAttribute("height", "40");
			
			PaginationInfo page_info = new PaginationInfo();
			page_info.setCurrPage(1);
			page_info.setPageSize(maxNum);
			List<FriendSite> list = pub_ctxt.getSite().getFriendSiteCollection()
				.getFriendSiteList(true, commend, linkType, kindId, specialId, orderBy, page_info);
			                                                              
			// 如果索取list没有达到maxNum个链接，则补充剩余的
			if (list.size() < maxNum) {
				for (int i = list.size(); i < maxNum; i++) {
					FriendSite fs = new FriendSite();
					fs.setLinkType(-1);
					list.add(fs);
				}
			}
			
			Map<String, Object> options = coll.attrToOptions();
			options.put("linkType", linkType);
			options.put("kindId", kindId);
			options.put("specialId", specialId);
			options.put("maxNum", maxNum);
			options.put("cols", cols);
			options.put("tableWidth", tableWidth);
			options.put("showStyle", showStyle);
			options.put("commend", commend);
			options.put("orderBy", orderBy);
			options.put("delay", delay);
			options.put("width", width);
			options.put("height", height);
			
			//取得内建 Label。
			BuiltinLabel builtin_label = getBuiltinLabel(".builtin.showfriendsite");
			//执行这个内建标签。
			if (showStyle != 3) {
				return builtin_label.process(env, new Object[]{
						super.getTableList(list, cols), options
				});
			} else {
				return builtin_label.process(env, new Object[]{list, options});
			}
		}
	}
	
	/**
	 * 显示友情链接分类浏览页面
	 * #{ShowFriendSiteViewList} 
	 * 此标签绑定了浏览页面中的所有数据输出，暂时先保留它
	 */
	static final class ShowFriendSiteViewListHandler extends AbstractLabelHandler {
		/*
		 * (non-Javadoc)
		 * @see com.chinaedustar.publish.label.AbstractLabelHandler#handleLabel()
		 */
		@Override protected int handleLabel() {
			// TODO:
			throw new UnsupportedOperationException();
			/*
			//获得参数
			int linkType = Integer.parseInt(env.findVariable("linkType").toString());
			int kindId =Integer.parseInt(env.findVariable("kindId").toString());
			int specialId = Integer.parseInt(env.findVariable("specialId").toString());
			int kindType = Integer.parseInt(env.findVariable("kindType").toString());
			//获得数据
			List ks_list = pub_ctxt.getSite().getFriendSiteCollection().getFsKindList();
			List sp_list = pub_ctxt.getSite().getFriendSiteCollection().getFsSpecialList();
			List fs_list = null;
			if(kindType == 1){
				fs_list =  pub_ctxt.getSite().getFriendSiteCollection().getFriendSiteListByKind(true, kindId);
			}else if(kindType == 2){
				fs_list =  pub_ctxt.getSite().getFriendSiteCollection().getFriendSiteListBySpecial(true, specialId);
			}else if(kindType == 0){
				fs_list =  pub_ctxt.getSite().getFriendSiteCollection().getFriendSiteList(true, null, linkType, kindId, specialId, "orderId", 0, 0);
			}
			//获得内建标签
			BuiltinLabel builtin_label = getBuiltinLabel(".builtin.showfriendsiteviewlist");
			//执行内建标签
			return builtin_label.process(env, new Object[]{ks_list, sp_list, fs_list, linkType, kindId, specialId, kindType});
			*/
		}
	}
	
	/**
	 * 显示友情链接列表。
	 * #{ShowFriendSiteList linkType='0' kindId='0' specialId='0' maxNum='10' showLogo='false' logoWidth='0' logoHeight='0' showTitle='true' charNum='10' openType='1' cols='1'}<a href='#{friendSite.linkUrl}'>#{friendSite.title}</a>#{/ShowFriendSiteList}
	 * 
	 * @param linkType 所选择的友情链接类型，0表示选择所有类型。文字链接 = 1；图片链接 = 2。默认为0。
	 * @param kindId 所选择的友情链接分类，0表示选择所有分类。默认：0|(kindId)。
	 * @param specialId 所选择的友情链接专题，0表示选择所有专题。默认：0|(specialId)。
	 * @param maxNum 显示的友情链接个数，0表示不限制。默认：0|(1......)。
	 * @param showLogo	是否显示链接Logo。默认：false
	 * @param logoWidth	显示Logo的宽度。
	 * @param logoHeight  显示Logo的高度。
	 * @param showTitle 显示文字标题。默认：false。
	 * @param charNum 显示文字字数，0表示不限制。默认：0
	 * @param openType  打开方式。0：_self；1：_blank。默认为1。
	 * @param cols 每行显示的列数，默认为 1 。
	 */
	static final class ShowFriendSiteListHandler extends AbstractLabelHandler implements LabelHandler2 {
		public static final String DEFAULT_TEMPLATE = ".builtin.showfriendsitelist";
		/*
		 * (non-Javadoc)
		 * @see com.chinaedustar.publish.label.AbstractLabelHandler#handleLabel()
		 */
		@Override protected int handleLabel() {
			// 获得参数
			AttributeCollection coll = label.getAttributes();
			int linkType = coll.safeGetIntAttribute("linkType", 0);
			int kindId = coll.safeGetIntAttribute("kindId", 0);
			int specialId = coll.safeGetIntAttribute("specialId", 0);
			int maxNum = coll.safeGetIntAttribute("maxNum", 0);
			
			java.util.Map<String, Object> options = coll.attrToOptions();
			int cols = coll.safeGetIntAttribute("cols", 1);
			if (cols < 1) cols = 1;
			options.put("cols", cols);
			options.put("showLogo", coll.safeGetBoolAttribute("showLogo", true));
			options.put("logoWidth", coll.safeGetIntAttribute("logoWidth", 88));
			options.put("logoHeight", coll.safeGetIntAttribute("logoHeight", 31));
			options.put("showTitle", coll.safeGetBoolAttribute("showTitle", true));
			options.put("charNum", coll.safeGetIntAttribute("charNum", 0));
			OpenType openType = OpenType.fromString(coll.safeGetStringAttribute("openType", "_blank"));
			options.put("openType", openType);
			
			// 获取数据。
			PaginationInfo page_info = new PaginationInfo(1, maxNum);
			List<FriendSite> fs_list = pub_ctxt.getSite().getFriendSiteCollection().getFriendSiteList(true, null, 
					linkType, kindId, specialId, 0, page_info);
			
			// 合成。
			if (label.hasChild()) {
				LocalContext local_ctxt = env.acquireLocalContext(label, 0);
				local_ctxt.setVariable("fs_list", fs_list);
				local_ctxt.setVariable("options", options);
				local_ctxt.setVariable("page_info", page_info);
				super.addRepeaterSupport(local_ctxt, "friendSite", fs_list);
				// 不支持分页，虽然有分页数据，但是是用于记录数据的。
				return PROCESS_DEFAULT;
			} else {
				String template_name = super.getTemplateName(DEFAULT_TEMPLATE);
				BuiltinLabel builtLabel = getBuiltinLabel(template_name);
				if (builtLabel == null) return super.unexistBuiltinLabel(template_name);
				
				Object[] args = new Object[] {fs_list, options, page_info};
				return builtLabel.process(env, args);
			}
		}
		/*
		 * (non-Javadoc)
		 * @see com.chinaedustar.publish.itfc.LabelHandler2#getBuiltinName()
		 */
		public String getBuiltinName() {
			return DEFAULT_TEMPLATE;
		}
	}
}
