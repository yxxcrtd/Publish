package com.chinaedustar.publish.admin;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.PageContext;

import com.chinaedustar.publish.model.Channel;
import com.chinaedustar.publish.model.UploadFile;
import com.chinaedustar.publish.model.UploadFile.PageNode;

/**
 * 上传管理使用的数据。
 * @author liujunxing
 *
 */
public class UploadManage extends AbstractBaseManage {
	/**
	 * 构造。
	 *
	 */
	public UploadManage(PageContext page_ctxt) {
		super(page_ctxt);
	}
	
	/**
	 * 提供给 admin_upload_top.jsp 页面，该页面需要根据 channelId 参数得到频道名字。
	 * @return
	 */
	public Channel getCurrentChannel() {
		int channelId = param_util.safeGetIntParam("channelId");
		return site.getChannel(channelId);
	}

	/**
	 * 提供给 admin_upload_nav.jsp 页面，该页面显示频道上载目录 js 代码。
	 * @return
	 */
	public String getNavJsString() {
		// 得到当前频道。
		Channel channel = getCurrentChannel();
		if (channel == null) return "";
		UploadFile uf = new UploadFile(page_ctxt);
		return uf.getJSString();
	}
	
	/**
	 * admin_upload_list.jsp 页面的数据初始化。
	 *
	 */
	public void initListPage() {
		//得到要显示文件的方式，1-详细方式 2-缩略图方式
		String strShowFileStyle = param_util.safeGetStringParam("ShowFileStyle","");
		if (strShowFileStyle.equals("")) {
			Cookie[] cookies = ((HttpServletRequest)page_ctxt.getRequest()).getCookies();
			for (int i = 0; i < cookies.length; i++) {
				if (cookies[i].getName().equals("ShowFileStyle")) {
					strShowFileStyle = cookies[i].getValue();
					break;
				}
			}
		} else {
			Cookie cookie = new Cookie("ShowFileStyle", strShowFileStyle);
			((HttpServletResponse)page_ctxt.getResponse()).addCookie(cookie);
		}
		if (strShowFileStyle.equals(""))
			strShowFileStyle = "1";
		int intShowFileStyle = Integer.parseInt(strShowFileStyle);
		//按什么排序
		int intSortBy = param_util.safeGetIntParam("sortby",0);
		//上一次排序按的什么
		int intPriorSort = param_util.safeGetIntParam("priorsort",-1);
		if (intPriorSort == intSortBy && intPriorSort != -1) {
			intPriorSort = -1;
		} else {
			intPriorSort = intSortBy;
		}
		
		UploadFile uf = new UploadFile(page_ctxt);		
		UploadFile.DirNode curDir= uf.getCurDir();		
		
		setTemplateVariable("showFileStyle", intShowFileStyle);
		setTemplateVariable("sortBy", intSortBy);
		setTemplateVariable("page", param_util.safeGetIntParam("page", 1));		
		setTemplateVariable("priorSort", intPriorSort);
		setTemplateVariable("channelId", uf.getChannelId());
		setTemplateVariable("webPath", uf.getWebPath());
		setTemplateVariable("parentDirPath", uf.getParentDir());
		setTemplateVariable("curDirPath", curDir.getPath());
		setTemplateVariable("isRootDir", curDir.getIsRoot());
		setTemplateVariable("SORT_BY_NAME", UploadFile.SORT_BY_NAME);
		setTemplateVariable("SORT_BY_SIZE", UploadFile.SORT_BY_SIZE);
		setTemplateVariable("SORT_BY_TYPE", UploadFile.SORT_BY_TYPE);
		setTemplateVariable("SORT_BY_LASTMODIFYDATE", UploadFile.SORT_BY_LASTMODIFYDATE);		
		setTemplateVariable("Dirs", curDir.getDirChildNodes());
		setTemplateVariable("selectFile", uf.getSelectFile());
		
		
		if (intShowFileStyle == 1) {				//详细视图
			setTemplateVariable("Files",uf.sortFile(intSortBy, intPriorSort>=0));
			setTemplateVariable("hasFile", curDir.getHasFile());
		} else if(intShowFileStyle == 2) {			//缩略视图			
			PageNode pn=uf.getPageFiles(param_util.safeGetIntParam("page", 1), 20);
			setTemplateVariable("Guide", pn);
			if (pn != null ) {	
				setTemplateVariable("hasFile", true);
				setTemplateVariable("fileTable", uf.getFileTable(uf.sortFile(intSortBy, intPriorSort>=0, pn.getFileList())));
			}else{
				setTemplateVariable("hasFile", false);
				setTemplateVariable("fileTable", null);
			}
		}
	}
}
