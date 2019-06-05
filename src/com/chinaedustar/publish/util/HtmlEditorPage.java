package com.chinaedustar.publish.util;

import java.util.*;

/**
 * editor.jsp 页面使用的数据的初始化。
 * 
 * @author liujunxing
 *
 */
@SuppressWarnings("deprecation")
public class HtmlEditorPage {
	/** 网站的安装目录，从数据库中获取，如：http://localhost:8080/PubWeb/。 */
	public String InstallDir;
	
	/** 频道的标识。*/
	public int channelId;
	   
	/** FORM 中 textarea 的 name 属性。 */
	public String tContentID;
	
	/** 模板的标识。 */
	public int templateType;
	
	/** 显示特定类型的按钮。 */
	public int showType; 

	/** 特定的按钮集合的字符串。 */
	public StringBuilder strButtons = new StringBuilder();

	/**
	 * 构造函数。
	 */
	public HtmlEditorPage() {
	}
	
	private static final int ALL_BUTTONS_NUM = 104;
	/** 
	 * 全部的按钮。 (原类型和名字: String[] arrButtons ) 
	 */
	private static String[] all_buttons = new String[ALL_BUTTONS_NUM];

	/** 静态初始化。 */
	static {
		// <!-- 定义按钮 -->
		// 调入按钮数组，按钮类型$按钮的title内容$按钮的onclick事件$按钮的图片
		all_buttons[0] = "yToolbar$$$";    // 按钮的工具栏。
		all_buttons[1] = "/yToolbar$$$";   // 按钮的工具栏结束。
		all_buttons[2] = "TBHandle$$$";    // 工具栏最开始的那段表示可以拖动的区域
		all_buttons[3] = "TBSep$$$";       // 表格的分割竖线。
		
		all_buttons[101] = "TBGen$$$";     // 下拉列表框，标题。
		all_buttons[102] = "TBGen2$$$";    // 下拉列表框，字体。
		all_buttons[103] = "TBGen3$$$";    // 下拉列表框，字号。
		
		all_buttons[5] = "Btn$全部选择$format('selectall')$selectall.gif";   // 按钮
		all_buttons[6] = "Btn$删除$format('delete')$delete.gif";
		all_buttons[7] = "Btn$剪切$format('cut')$cut.gif";
		all_buttons[8] = "Btn$复制$format('copy')$copy.gif";
		all_buttons[9] = "Btn$粘贴$format('paste')$paste.gif";
		all_buttons[10] = "Btn$从word中粘贴$insert('word')$wordpaste.gif";
		all_buttons[11] = "Btn$撤消$format('undo')$undo.gif";
		all_buttons[12] = "Btn$恢复$format('redo')$redo.gif";
		all_buttons[13] = "Btn$查找 / 替换$findstr()$find.gif";
		all_buttons[14] = "Btn$计算器$insert('calculator')$calculator.gif";
		all_buttons[15] = "Btn$打印$format('Print')$print.gif";
		all_buttons[16] = "Btn$查看帮助$insert('help')$help.gif";
		all_buttons[17] = "Btn$左对齐$format('justifyleft')$aleft.gif";
		all_buttons[18] = "Btn$居中$format('justifycenter')$acenter.gif";
		all_buttons[19] = "Btn$右对齐$format('justifyright')$aright.gif";
		all_buttons[20] = "Btn$两端对齐$format('JustifyFull')$justifyFull.gif";
		all_buttons[21] = "Btn$绝对或相对位置$format('absolutePosition')$abspos.gif";
		all_buttons[22] = "Btn$删除文字格式$format('RemoveFormat')$clear.gif";
		all_buttons[23] = "Btn$插入段落$format('insertparagraph')$paragraph.gif";
		all_buttons[24] = "Btn$插入换行符号$insert('br')$chars.gif";
		all_buttons[25] = "Btn$字体颜色$insert('fgcolor')$fgcolor.gif";
		all_buttons[26] = "Btn$文字背景色$insert('fgbgcolor')$fgbgcolor.gif";
		all_buttons[27] = "Btn$加粗$format('bold')$bold.gif";
		all_buttons[28] = "Btn$斜体$format('italic')$italic.gif";
		all_buttons[29] = "Btn$下划线$format('underline')$underline.gif";
		all_buttons[30] = "Btn$删除线$format('StrikeThrough')$strikethrough.gif";
		all_buttons[31] = "BtnMenu$更多文字格式$showToolMenu('font')$arrow.gif";
		all_buttons[32] = "Btn$显示或隐藏表格虚线、按钮等显示样式$showBorders()$showBorders.gif";
		all_buttons[33] = "Btn$图片左环绕$imgalign('left')$imgleft.gif";
		all_buttons[34] = "Btn$图片右环绕$imgalign('right')$imgright.gif";
		all_buttons[35] = "Btn$插入超级链接$insert('CreateLink')$url.gif";
		all_buttons[36] = "Btn$取消超级链接$format('unLink')$nourl.gif";
		all_buttons[37] = "Btn$插入普通水平线$format('InsertHorizontalRule')$line.gif";
		all_buttons[38] = "Btn$插入特殊水平线$insert('hr')$sline.gif";
		all_buttons[39] = "Btn$插入手动分页符$insert('page')$page.gif";
		all_buttons[40] = "Btn$插入当前日期$insert('nowdate')$date.gif";
		all_buttons[41] = "Btn$插入当前时间$insert('nowtime')$time.gif";
		all_buttons[42] = "Btn$插入栏目框$insert('FIELDSET')$fieldset.gif";
		all_buttons[43] = "Btn$插入网页$insert('iframe')$htm.gif";
		all_buttons[44] = "Btn$插入Excel表格$insert('excel')$excel.gif";
		all_buttons[45] = "Btn$插入表格$TableInsert()$table.gif";
		all_buttons[46] = "BtnMenu$表格操作$showToolMenu('table')$arrow.gif";  // 更多操作，下拉的菜单。

		// 计算时间。
		Calendar calendar = Calendar.getInstance();
		Date date = calendar.getTime();
		Date date2 = new Date(date.getYear(), date.getMonth(), date.getDate());
		all_buttons[47] = "Btn$插入下拉菜单$Insermenu('" + date.toLocaleString() + "')$menu.gif";
		all_buttons[48] = "BtnMenu$更多表单控件$showToolMenu('form')$arrow.gif";
		all_buttons[49] = "Btn$插入滚动文本$insert('insermarquee')$marquee.gif";
		all_buttons[50] = "BtnMenu$插入更多样式$showToolMenu('object')$arrow.gif";
		all_buttons[51] = "Btn$插入表情符号$insert('inseremot')$emot.gif";
		
		// 继续按钮。
		all_buttons[52] = "Btn$插入特殊符号$Insertlr('editor_tsfh.html',300,190," + (date.getTime() - date2.getTime()) + ")$symbol.gif";
		all_buttons[53] = "Btn$插入公式$insert('InsertEQ')$eq.gif";
		all_buttons[54] = "BtnMenu$公式操作$showToolMenu('gongshi')$arrow.gif";
		all_buttons[55] = "Btn$插入图片，支持格式为：jpg、gif、bmp、png等$insert('pic')$img.gif";
		all_buttons[56] = "Btn$批量上传图片，支持格式为：jpg、gif、bmp、png等$insert('batchpic')$pimg.gif";
		all_buttons[57] = "Btn$插入flash多媒体文件$insert('swf')$flash.gif";
		all_buttons[58] = "Btn$插入视频文件，支持格式为：avi、wmv、asf等$insert('wmv')$wmv.gif";
		all_buttons[59] = "Btn$插入RealPlay文件，支持格式为：rm、ra、ram$insert('rm')$rm.gif";
		all_buttons[60] = "Btn$上传附件$insert('fujian')$fujian.gif";
		all_buttons[61] = "Btn$从上传文件中选择$insert('SelectUpFile')$selectUpFile.gif";
		all_buttons[62] = "Btn$插入标签$insert('Label')$label.gif";
		all_buttons[63] = "Btn$图片单行居中$imgalign('center')$imgcenter.gif";
		all_buttons[64] = "Btn$插入带标题的分页$insert('pagetitle')$pagetitle.gif";

		all_buttons[65] = "Btn$显示文章标题等信息$SuperFunctionLabel('label/super_ShowArticleList.jsp','GetArticleList','文章列表函数标签',1,'GetList',800,700)$labelIco\\getArticleList.gif";
		all_buttons[66] = "Btn$显示图片文章$SuperFunctionLabel('label/super_ShowPicArticle.jsp','GetPicArticle','显示图片文章标签',1,'GetPic',700,500)$labelIco\\getPicArticle.gif";
		all_buttons[67] = "Btn$显示幻灯片文章$SuperFunctionLabel('label/super_ShowSlidePicArticle.jsp','GetSlidePicArticle','显示幻灯片文章标签',1,'GetSlide',700,500)$labelIco\\getSlidePicArticle.gif";
		all_buttons[68] = "Btn$文章自定义列表$SuperFunctionLabel('label/super_CustomArticleList.jsp','CustomListLable','文章自定义列表标签',1,'GetArticleCustom',720,700)$labelIco\\getArticleCustom.gif";
		all_buttons[69] = "Btn$显示软件标题$SuperFunctionLabel('label/super_ShowSoftList.jsp','GetSoftList','下载列表函数标签',2,'GetList',800,700)$labelIco\\getSoftList.gif";
		all_buttons[70] = "Btn$显示图片下载$SuperFunctionLabel('label/super_ShowPicSoft.jsp','GetPicSoft','显示图片下载标签',2,'GetPic',700,500)$labelIco\\getPicSoft.gif";
		all_buttons[71] = "Btn$显示幻灯片下载$SuperFunctionLabel('label/super_ShowSlidePicSoft.jsp','GetSlidePicSoft','显示幻灯片下载标签',2,'GetSlide',700,500)$labelIco\\getSlidePicSoft.gif";
		all_buttons[72] = "Btn$下载自定义列表$SuperFunctionLabel('label/super_CustomSoftList.jsp','CustomListLable','下载自定义列表标签',2,'GetSoftCustom',720,700)$labelIco\\getSoftCustom.gif";
		all_buttons[73] = "Btn$显示图片标题$SuperFunctionLabel('label/super_ShowPhotoList.jsp','GetPhotoList','图片列表函数标签',3,'GetList',800,700)$labelIco\\getPhotoList.gif";
		all_buttons[74] = "Btn$显示图片$SuperFunctionLabel('label/super_ShowPicPhoto.jsp','GetPicPhoto','显示图片图文标签',3,'GetPic',700,550)$labelIco\\getPicPhoto.gif";
		all_buttons[75] = "Btn$显示图片幻灯片$SuperFunctionLabel('label/super_ShowSlidePicPhoto.jsp','GetSlidePicPhoto','显示幻灯片图片标签',3,'GetSlide',700,550)$labelIco\\getSlidePicPhoto.gif";
		all_buttons[81] = "Btn$网站logo$FunctionLabel('label/label_ShowBanner.jsp', 520, 200)$labelIco\\chinaedustar_logo.gif";
		all_buttons[82] = "Btn$网站banner$FunctionLabel('label/label_ShowBanner.jsp', 520, 200)$labelIco\\chinaedustar_banner.gif";
		all_buttons[83] = "Btn$弹出公告$FunctionLabel('label/label_annWin.htm',240,140)$labelIco\\chinaedustar_popAnnouce.gif";
		all_buttons[84] = "Btn$公告$FunctionLabel('label/label_ShowAnnounce2.jsp', 240, 263)$labelIco\\chinaedustar_annouce.gif";
		all_buttons[85] = "Btn$友情$FunctionLabel('label/label_ShowFriendSite2.jsp', 460, 415)$labelIco\\chinaedustar_friendSite.gif";
		all_buttons[86] = "Btn$调查$InsertLabel('ShowVote')$labelIco\\chinaedustar_vote.gif";
		all_buttons[87] = "Btn$作者列表$FunctionLabel('label/label_author_showList.htm',400,345)$labelIco\\chinaedustar_author.gif";
		all_buttons[89] = "Btn$显示作品集排行$FunctionLabel('label/label_showBlogList.htm',400,400)$labelIco\\chinaedustar_blog.gif";
		all_buttons[90] = "Btn$显示专题列表$FunctionLabel('label/label_showSpecialList.htm',320,300)$labelIco\\chinaedustar_special.gif";
		all_buttons[91] = "Btn$显示注册用户$InsertLabel('ShowTopUser')$labelIco\\chinaedustar_user.gif";
		all_buttons[92] = "Btn$登录$InsertLabel('ShowAdminLogin')$labelIco\\chinaedustar_adminLogin.gif";
		all_buttons[93] = "Btn$导航$InsertLabel('ShowPath')$labelIco\\chinaedustar_path.gif";
		all_buttons[94] = "Btn$版权$InsertLabel('Copyright')$labelIco\\chinaedustar_copyright.gif";
	}
	
	// showType == 0, 文章
	private void createArticleButtons() {
		  strButtons.append(all_buttons[0]).append("|").append(all_buttons[2]).append("|").append(all_buttons[5]).append("|").append(all_buttons[3]).append("|").append(all_buttons[6]).append("|");
		  strButtons.append(all_buttons[3]).append("|").append(all_buttons[7]).append("|").append(all_buttons[8]).append("|").append(all_buttons[9]).append("|");
		  strButtons.append(all_buttons[10]).append("|").append(all_buttons[3]).append("|").append(all_buttons[11]).append("|").append(all_buttons[12]).append("|");
		  strButtons.append(all_buttons[3]).append("|").append(all_buttons[13]).append("|").append(all_buttons[3]).append("|").append(all_buttons[14]).append("|");
		  strButtons.append(all_buttons[3]).append("|").append(all_buttons[15]).append("|").append(all_buttons[3]).append("|").append(all_buttons[16]).append("|");
		  strButtons.append(all_buttons[3]).append("|").append(all_buttons[17]).append("|").append(all_buttons[18]).append("|").append(all_buttons[19]).append("|");
		  strButtons.append(all_buttons[20]).append("|").append(all_buttons[21]).append("|").append(all_buttons[3]).append("|").append(all_buttons[22]).append("|");
		  strButtons.append(all_buttons[3]).append("|").append(all_buttons[35]).append("|").append(all_buttons[36]).append("|").append(all_buttons[44]).append("|").append(all_buttons[1]).append("|");
		  strButtons.append(all_buttons[0]).append("|").append(all_buttons[2]).append("|").append(all_buttons[101]).append("|").append(all_buttons[102]).append("|").append(all_buttons[103]).append("|").append(all_buttons[3]).append("|");
		  strButtons.append(all_buttons[25]).append("|").append(all_buttons[26]).append("|").append(all_buttons[3]).append("|").append(all_buttons[27]).append("|");
		  strButtons.append(all_buttons[28]).append("|").append(all_buttons[29]).append("|").append(all_buttons[30]).append("|").append(all_buttons[31]).append("|");
		  strButtons.append(all_buttons[3]).append("|").append(all_buttons[32]).append("|").append(all_buttons[3]).append("|").append(all_buttons[33]).append("|");
		  strButtons.append(all_buttons[63]).append("|").append(all_buttons[34]).append("|").append(all_buttons[3]).append("|").append(all_buttons[24]).append("|");
		  strButtons.append(all_buttons[1]).append("|").append(all_buttons[0]).append("|").append(all_buttons[2]).append("|").append(all_buttons[37]).append("|");
		  strButtons.append(all_buttons[38]).append("|").append(all_buttons[3]).append("|").append(all_buttons[3]).append("|");
		  strButtons.append(all_buttons[41]).append("|").append(all_buttons[40]).append("|").append(all_buttons[43]).append("|").append(all_buttons[42]).append("|");
		  strButtons.append(all_buttons[3]).append("|").append(all_buttons[45]).append("|").append(all_buttons[46]).append("|").append(all_buttons[3]).append("|");
		  strButtons.append(all_buttons[47]).append("|").append(all_buttons[48]).append("|").append(all_buttons[49]).append("|").append(all_buttons[50]).append("|");
		  strButtons.append(all_buttons[51]).append("|").append(all_buttons[3]).append("|").append(all_buttons[52]).append("|").append(all_buttons[53]).append("|");
		  strButtons.append(all_buttons[54]).append("|").append(all_buttons[3]).append("|").append(all_buttons[55]).append("|");
		  strButtons.append(all_buttons[57]).append("|").append(all_buttons[58]).append("|").append(all_buttons[59]).append("|").append(all_buttons[60]).append("|");
		  strButtons.append(all_buttons[1]);
	}

	/** showType == 1, Template */
	private void createTemplateButtons() {
		strButtons.append(all_buttons[0]).append("|").append(all_buttons[2]).append("|").append(all_buttons[65]).append("|").append(all_buttons[66]).append("|").append(all_buttons[67]).append("|");
		strButtons.append(all_buttons[68]).append("|").append(all_buttons[3]).append("|").append(all_buttons[69]).append("|").append(all_buttons[70]).append("|");
		strButtons.append(all_buttons[71]).append("|").append(all_buttons[72]).append("|").append(all_buttons[3]).append("|");
		strButtons.append(all_buttons[73]).append("|").append(all_buttons[74]).append("|").append(all_buttons[75]).append("|").append(all_buttons[3]).append("|");
		strButtons.append(all_buttons[3]).append("|");
		strButtons.append(all_buttons[81]).append("|").append(all_buttons[82]).append("|").append(all_buttons[3]).append("|").append(all_buttons[83]).append("|");
		strButtons.append(all_buttons[84]).append("|").append(all_buttons[85]).append("|").append(all_buttons[86]).append("|").append(all_buttons[3]).append("|");
//		  strButtons.append(arrButtons[86]).append("|");  // 调查
		strButtons.append(all_buttons[3]).append("|");
		strButtons.append(all_buttons[87]).append("|").append(all_buttons[90]).append("|");
		strButtons.append(all_buttons[3]).append("|").append(all_buttons[91]).append("|").append(all_buttons[92]).append("|").append(all_buttons[93]).append("|").append(all_buttons[94]).append("|");
		strButtons.append(all_buttons[1]).append("|").append(all_buttons[0]).append("|").append(all_buttons[2]).append("|").append(all_buttons[5]).append("|").append(all_buttons[3]).append("|").append(all_buttons[6]).append("|");
		strButtons.append(all_buttons[3]).append("|").append(all_buttons[7]).append("|").append(all_buttons[8]).append("|").append(all_buttons[9]).append("|");
		strButtons.append(all_buttons[3]).append("|").append(all_buttons[11]).append("|").append(all_buttons[12]).append("|").append(all_buttons[3]).append("|");
		strButtons.append(all_buttons[13]).append("|").append(all_buttons[3]).append("|").append(all_buttons[14]).append("|").append(all_buttons[3]).append("|");
		strButtons.append(all_buttons[15]).append("|").append(all_buttons[3]).append("|").append(all_buttons[16]).append("|").append(all_buttons[3]).append("|");
		strButtons.append(all_buttons[17]).append("|").append(all_buttons[18]).append("|").append(all_buttons[19]).append("|").append(all_buttons[20]).append("|");
		strButtons.append(all_buttons[21]).append("|").append(all_buttons[3]).append("|").append(all_buttons[33]).append("|").append(all_buttons[63]).append("|");
		strButtons.append(all_buttons[34]).append("|").append(all_buttons[3]).append("|").append(all_buttons[22]).append("|").append(all_buttons[3]).append("|");
		strButtons.append(all_buttons[37]).append("|").append(all_buttons[38]).append("|").append(all_buttons[40]).append("|").append(all_buttons[41]).append("|");
		strButtons.append(all_buttons[52]).append("|").append(all_buttons[35]).append("|").append(all_buttons[36]).append("|").append(all_buttons[24]).append("|");
		strButtons.append(all_buttons[1]).append("|").append(all_buttons[0]).append("|").append(all_buttons[2]).append("|").append(all_buttons[101]).append("|").append(all_buttons[102]).append("|").append(all_buttons[103]).append("|");
		strButtons.append(all_buttons[3]).append("|").append(all_buttons[25]).append("|").append(all_buttons[26]).append("|").append(all_buttons[3]).append("|");
		strButtons.append(all_buttons[27]).append("|").append(all_buttons[28]).append("|").append(all_buttons[29]).append("|").append(all_buttons[30]).append("|");
		strButtons.append(all_buttons[31]).append("|").append(all_buttons[3]).append("|").append(all_buttons[62]).append("|").append(all_buttons[3]).append("|");
		strButtons.append(all_buttons[43]).append("|").append(all_buttons[45]).append("|").append(all_buttons[46]).append("|").append(all_buttons[3]).append("|");
		strButtons.append(all_buttons[32]).append("|").append(all_buttons[47]).append("|").append(all_buttons[48]).append("|").append(all_buttons[3]).append("|");
		strButtons.append(all_buttons[49]).append("|").append(all_buttons[50]).append("|").append(all_buttons[55]).append("|").append(all_buttons[57]).append("|");
		strButtons.append(all_buttons[58]).append("|").append(all_buttons[59]).append("|").append(all_buttons[60]).append("|").append(all_buttons[1]);
	}
	
	// showType == 2, 留言 公告
	private void createAnnounceButtons() {
		strButtons.append(all_buttons[0]).append("|").append(all_buttons[2]).append("|").append(all_buttons[101]).append("|").append(all_buttons[102]).append("|").append(all_buttons[103]).append("|").append(all_buttons[3]).append("|");
		strButtons.append(all_buttons[25]).append("|").append(all_buttons[26]).append("|").append(all_buttons[3]).append("|").append(all_buttons[27]).append("|");
		strButtons.append(all_buttons[28]).append("|").append(all_buttons[29]).append("|").append(all_buttons[30]).append("|").append(all_buttons[31]).append("|");
		strButtons.append(all_buttons[16]).append("|").append(all_buttons[1]).append("|");
		strButtons.append(all_buttons[0]).append("|").append(all_buttons[2]).append("|").append(all_buttons[17]).append("|").append(all_buttons[18]).append("|");
		strButtons.append(all_buttons[19]).append("|").append(all_buttons[3]).append("|").append(all_buttons[22]).append("|").append(all_buttons[3]).append("|");
		strButtons.append(all_buttons[35]).append("|").append(all_buttons[36]).append("|").append(all_buttons[43]).append("|").append(all_buttons[3]).append("|");
		strButtons.append(all_buttons[44]).append("|").append(all_buttons[45]).append("|").append(all_buttons[46]).append("|").append(all_buttons[49]).append("|");
		strButtons.append(all_buttons[50]).append("|").append(all_buttons[3]).append("|").append(all_buttons[51]).append("|").append(all_buttons[52]).append("|");
		strButtons.append(all_buttons[3]).append("|").append(all_buttons[55]).append("|").append(all_buttons[57]).append("|").append(all_buttons[58]).append("|");
		strButtons.append(all_buttons[59]).append("|").append(all_buttons[60]).append("|").append(all_buttons[1]);
	}
	
	// showType == 3, 说明框
	private void createDescriptionButtons() {
		strButtons.append(all_buttons[0]).append("|").append(all_buttons[2]).append("|").append(all_buttons[101]).append("|").append(all_buttons[102]).append("|").append(all_buttons[103]).append("|").append(all_buttons[3]).append("|");
		strButtons.append(all_buttons[25]).append("|").append(all_buttons[26]).append("|").append(all_buttons[3]).append("|").append(all_buttons[27]).append("|");
		strButtons.append(all_buttons[28]).append("|").append(all_buttons[29]).append("|").append(all_buttons[30]).append("|").append(all_buttons[31]).append("|");
		strButtons.append(all_buttons[22]).append("|").append(all_buttons[35]).append("|").append(all_buttons[36]).append("|").append(all_buttons[52]).append("|");
		strButtons.append(all_buttons[3]).append("|").append(all_buttons[55]).append("|").append(all_buttons[57]).append("|").append(all_buttons[58]).append("|");
		strButtons.append(all_buttons[59]).append("|").append(all_buttons[60]).append("|").append(all_buttons[1]);
	}
	
	// showType == 4, 商城
	private void createShangchengButtons() {
		strButtons.append(all_buttons[0]).append("|").append(all_buttons[2]).append("|").append(all_buttons[5]).append("|").append(all_buttons[3]).append("|").append(all_buttons[6]).append("|");
		strButtons.append(all_buttons[3]).append("|").append(all_buttons[7]).append("|").append(all_buttons[8]).append("|").append(all_buttons[9]).append("|");
		strButtons.append(all_buttons[3]).append("|").append(all_buttons[11]).append("|").append(all_buttons[12]).append("|").append(all_buttons[3]).append("|");
		strButtons.append(all_buttons[13]).append("|").append(all_buttons[14]).append("|").append(all_buttons[3]).append("|");
		strButtons.append(all_buttons[17]).append("|").append(all_buttons[18]).append("|").append(all_buttons[19]).append("|").append(all_buttons[20]).append("|");
		strButtons.append(all_buttons[21]).append("|").append(all_buttons[3]).append("|").append(all_buttons[33]).append("|").append(all_buttons[63]).append("|");
		strButtons.append(all_buttons[34]).append("|").append(all_buttons[3]).append("|").append(all_buttons[22]).append("|").append(all_buttons[3]).append("|");
		strButtons.append(all_buttons[38]).append("|").append(all_buttons[40]).append("|").append(all_buttons[41]).append("|");
		strButtons.append(all_buttons[52]).append("|").append(all_buttons[35]).append("|").append(all_buttons[36]).append("|").append(all_buttons[24]).append("|");
		strButtons.append(all_buttons[1]).append("|").append(all_buttons[0]).append("|").append(all_buttons[2]).append("|").append(all_buttons[101]).append("|").append(all_buttons[102]).append("|").append(all_buttons[103]).append("|");
		strButtons.append(all_buttons[3]).append("|").append(all_buttons[25]).append("|").append(all_buttons[26]).append("|").append(all_buttons[3]).append("|");
		strButtons.append(all_buttons[27]).append("|").append(all_buttons[28]).append("|").append(all_buttons[29]).append("|").append(all_buttons[30]).append("|");
		strButtons.append(all_buttons[31]).append("|").append(all_buttons[3]).append("|");
		strButtons.append(all_buttons[45]).append("|").append(all_buttons[46]).append("|").append(all_buttons[3]).append("|");
		strButtons.append(all_buttons[47]).append("|").append(all_buttons[48]).append("|").append(all_buttons[3]).append("|");
		strButtons.append(all_buttons[55]).append("|").append(all_buttons[57]).append("|");
		strButtons.append(all_buttons[58]).append("|").append(all_buttons[59]).append("|").append(all_buttons[60]).append("|").append(all_buttons[1]);
	}
	
	// showType == 5, 供求模块 
	private void createGongqiuButtons() {
		strButtons.append(all_buttons[0]).append("|").append(all_buttons[2]).append("|").append(all_buttons[101]).append("|").append(all_buttons[102]).append("|").append(all_buttons[103]).append("|").append(all_buttons[3]).append("|");
		strButtons.append(all_buttons[25]).append("|").append(all_buttons[26]).append("|").append(all_buttons[3]).append("|").append(all_buttons[27]).append("|");
		strButtons.append(all_buttons[28]).append("|").append(all_buttons[29]).append("|").append(all_buttons[30]).append("|").append(all_buttons[31]).append("|");
		strButtons.append(all_buttons[16]).append("|").append(all_buttons[1]).append("|");
		strButtons.append(all_buttons[0]).append("|").append(all_buttons[2]).append("|").append(all_buttons[17]).append("|").append(all_buttons[18]).append("|");
		strButtons.append(all_buttons[19]).append("|").append(all_buttons[3]).append("|").append(all_buttons[22]).append("|").append(all_buttons[3]).append("|");
		strButtons.append(all_buttons[35]).append("|").append(all_buttons[36]).append("|").append(all_buttons[43]).append("|").append(all_buttons[3]).append("|");
		strButtons.append(all_buttons[44]).append("|").append(all_buttons[45]).append("|").append(all_buttons[46]).append("|").append(all_buttons[49]).append("|");
		strButtons.append(all_buttons[50]).append("|").append(all_buttons[3]).append("|").append(all_buttons[51]).append("|").append(all_buttons[52]).append("|");
		strButtons.append(all_buttons[3]).append("|").append(all_buttons[55]).append("|").append(all_buttons[57]).append("|").append(all_buttons[58]).append("|");
		strButtons.append(all_buttons[59]).append("|").append(all_buttons[60]).append("|").append(all_buttons[1]);
	}
	
	private void createShowType6() {
		strButtons.append(all_buttons[0]).append("|").append(all_buttons[2]).append("|").append(all_buttons[102]).append("|").append(all_buttons[103]).append("|").append(all_buttons[3]).append("|");
	    strButtons.append(all_buttons[25]).append("|").append(all_buttons[26]).append("|").append(all_buttons[3]).append("|").append(all_buttons[27]).append("|");
	    strButtons.append(all_buttons[28]).append("|").append(all_buttons[29]).append("|").append(all_buttons[30]).append("|").append(all_buttons[31]).append("|");
	    strButtons.append(all_buttons[22]).append("|").append(all_buttons[35]).append("|").append(all_buttons[36]).append("|").append(all_buttons[52]).append("|");
	    strButtons.append(all_buttons[3]).append("|").append(all_buttons[55]).append("|").append(all_buttons[1]);
	}
	
	/**
	 * 初始化 editor.jsp 页面所需数据。
	 *
	 */
	public void init() {
		// <!-- 根据 showType 得到特定按钮集合 -->
		switch (showType) {
		case 0: // 文章
			createArticleButtons();
			break;
		case 1: // 模板
			createTemplateButtons();
			break;
		case 2: // 留言 公告
			createAnnounceButtons();
			break;
		case 3: // 说明框
			createDescriptionButtons();
			break;
		case 4: // 商城
			createShangchengButtons();
			break;
		case 5: // 供求模块 
			createGongqiuButtons();
		  break;
		case 6: //说明框
			createShowType6();
			break;
		default:
			createDescriptionButtons();
			break;
		}
	}
}
