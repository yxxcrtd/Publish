<%@page language="java" contentType="text/html; charset=gb2312" 
   pageEncoding="UTF-8" errorPage="admin_error.jsp"
%><%@taglib prefix="pub" uri="/WEB-INF/publish.tld" 
%><%@page import="com.chinaedustar.publish.admin.SiteManage"
%><%
  // 初始化页面数据.
  SiteManage site_manage = new SiteManage(pageContext);
  site_manage.initSitePage();
  
%><!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
  <title>网站配置</title>
  <link href='admin_style.css' rel='stylesheet' type='text/css' />
</head>
<body leftmargin='2' topmargin='0' marginwidth='0' marginheight='0'>

<pub:declare>
<!-- 定义系统内建站点信息的属性页 -->
<pub:tabs var="site_tabs" scope="page" purpose="定义 Site 所使用的 tab 页">
 <pub:tab name="siteInfo" text="网站信息" template="site_info_template" default="true" />
 <pub:tab name="siteOption" text="网站选项" template="site_option_template" />
 <pub:tab name="extendsInfo" text="扩展属性" template="object_extends_edit" />
</pub:tabs>

<%@ include file="tabs_tmpl2.jsp" %>
<%@ include file="extends_prop.jsp" %>

<!-- 主执行模板定义 -->
<pub:template name="main">
 #{call admin_help}
<form name="myform" id="myform" method="POST" action="admin_site_action.jsp">
 #{call tab_js }
 #{call tab_header(site_tabs) }
 #{call tab_content(site_tabs) }
 #{call form_button}
</form>
</pub:template> 
 
<!-- 网站信息配置页面头显示模板定义 -->
<pub:template name="admin_help">
<table width='100%' border='0' align='center' cellpadding='2' cellspacing='1' class='border'>
 <tr class='topbg'>
  <td align='center'><b>网 站 信 息 配 置</b></td>
 </tr>
</table>  
</pub:template>


<!-- FORM 提交按钮显示模板定义 -->
<pub:template name="form_button">
<table width='100%' border='0'>
 <tr>
  <td height='40' align='center'>
   <input type='hidden' name='command' value='save' />
   <input type='submit' name='cmdSave' id='cmdSave' value=' 保存设置 ' />
  </td>
 </tr>
</table>
</pub:template>

<!-- 站点信息属性页(PropertyPage)的显示模板定义 -->
<pub:template name="site_info_template">
<table width='100%' border='0' align='center' cellpadding='2' cellspacing='1'>
 <tr class='tdbg'>
  <td width='40%' class='tdbg5'><strong>网站名称：</strong></td>
  <td>
   <input name='SiteName' type='text' id='SiteName' value='#{site.name }' size='40' maxlength='50' />
  </td>
 </tr>
 <tr class='tdbg'>
  <td width='40%' class='tdbg5'><strong>网站标题：</strong></td>
  <td>
   <input name='SiteTitle' type='text' id='SiteTitle' value='#{site.title }' size='40' maxlength='50' />
  </td>
 </tr>
 <tr class='tdbg'>
  <td width='40%' class='tdbg5'><strong>网站地址：</strong><br />请填写完整URL地址</td>
  <td>
 <input name='SiteUrl' type='text' id='SiteUrl' value='#{site.url}' size='40' maxlength='255' />
  </td>
 </tr>
 <tr class='tdbg'>
  <td width='40%' class='tdbg5'><font color='red'><strong>安装目录：</strong>
    <br />系统安装目录（相对于根目录的位置）</font>
  </td>
  <td>
 <input name='InstallDir' type='text' id='InstallDir' value='#{site.installDir}' size='40'
    maxlength='30' />
  </td>
 </tr>
 <tr class='tdbg'>
  <td width='40%' class='tdbg5'><strong>LOGO地址：</strong>
   <br />请填写完整URL地址
  </td>
  <td>
 <input name='LogoUrl' type='text' id='LogoUrl' value='#{site.logo}' size='40' maxlength='255' />
  </td>
 </tr>
 <tr class='tdbg'>
  <td width='40%' class='tdbg5'><strong>Banner地址：</strong>
   <br />请填写完整URL地址
  </td>
  <td>
 <input name='BannerUrl' type='text' id='BannerUrl' value='#{site.banner}'
    size='40' maxlength='255' />
  </td>
 </tr>
 <tr class='tdbg'>
  <td width='40%' class='tdbg5'><strong>站长姓名：</strong></td>
  <td>
 <input name='WebmasterName' type='text' id='WebmasterName' value='#{site.webmaster}' size='40' maxlength='20' />
  </td>
 </tr>
 <tr class='tdbg'>
  <td width='40%' class='tdbg5'><strong>站长信箱：</strong></td>
  <td>
   <input name='WebmasterEmail' type='text' id='WebmasterEmail'
    value='#{site.webmasterEmail}' size='40' maxlength='100' />
  </td>
 </tr>
 <tr class='tdbg'>
  <td width='40%' class='tdbg5'><strong>版权信息：</strong>
   <br />支持HTML标记
  </td>
  <td>
 <textarea name='Copyright' cols='60' rows='4' id='Copyright' type="_moz">#{site.copyright@html}</textarea>
  </td>
 </tr>
 <tr class='tdbg'>
  <td width='40%' class='tdbg5'><strong>网站META关键词：</strong> 
    <br />针对搜索引擎设置的关键词
  <br />例如：在文本框填写

  <br />&lt;meta name="Keywords" content="网站,门户,新闻,快讯"&gt;
  <br />多个关键词请用,号分隔

  </td>
  <td>
 <textarea name='Meta_Keywords' cols='60' rows='4' id='Meta_Keywords' type="_moz">#{site.metaKey}</textarea>
  </td>
 </tr>
 <tr class='tdbg'>
  <td width='40%' class='tdbg5'><strong>网站META网页描述：</strong>
  <br />针对搜索引擎设置的网页描述

  <br />例如：在文本框填写

  <br />&lt;meta name="Description" content="网站,门户,新闻,快讯"&gt;
  <br />多个描述请用,号分隔

  </td>
  <td>
    <textarea name='Meta_Description' cols='60' rows='4' id='Meta_Description' type="_moz">#{site.metaDesc}</textarea>
  </td>
 </tr>
</table>
</pub:template>

<!-- 网站生成页（）显示模板定义 -->
<pub:template name="generate_option_template">
 <tr><td>
 <h2>TODO </h2>
 </td></tr>
</pub:template>

<!-- 网站选项属性页(PropertyPage)显示模板定义 -->
<pub:template name="site_option_template">
<table width='100%' border='0' align='center' cellpadding='2' cellspacing='1'>
  <tr class='tdbg'>
    <td width='40%' class='tdbg5'><strong>是否显示网站频道：</strong></td>
    <td>
      <input type='radio' name='ShowSiteChannel' value='1' #{iif(site.showSiteChannel == 1, "checked", " ") }> 是 &nbsp;&nbsp;&nbsp;&nbsp;
      <input type='radio' name='ShowSiteChannel' value='0' #{iif(site.showSiteChannel == 0, "checked", " ") }> 否
    </td>
  </tr>
  <tr class='tdbg'>
    <td width='40%' class='tdbg5'><strong>是否显示管理登录链接：</strong></td>
    <td>
      <input type='radio' name='ShowAdminLogin' value='1' #{iif(site.showAdminLogin == 1, "checked", " ") }> 是 &nbsp;&nbsp;&nbsp;&nbsp;
      <input type='radio' name='ShowAdminLogin' value='0' #{iif(site.showAdminLogin == 0, "checked", " ") }> 否
    </td>
  </tr>
  <tr class='tdbg'>
    <td width='40%' class='tdbg5'><strong>是否保存远程图片到本地：</strong><br>如果从其它网站上复制的内容中包含图片，则将图片复制到本站服务器上</td>
    <td>
      <input type='radio' name='EnableSaveRemote' value='1' #{iif(site.enableSaveRemote == 1, "checked"," ") }> 是 &nbsp;&nbsp;&nbsp;&nbsp;
      <input type='radio' name='EnableSaveRemote' value='0' #{iif(site.enableSaveRemote == 0, "checked"," ") }> 否
    </td>
  </tr>
  <tr class='tdbg'>
    <td width='40%' class='tdbg5'><strong>是否开放友情链接申请：</strong></td>
    <td>
      <input type='radio' name='EnableLinkReg' value='1' #{iif(site.enableLinkReg == 1, "checked"," ") }> 是 &nbsp;&nbsp;&nbsp;&nbsp;
      <input type='radio' name='EnableLinkReg' value='0' #{iif(site.enableLinkReg == 0, "checked"," ") }> 否
    </td>
  </tr>
  <tr class='tdbg'>
    <td width='40%' class='tdbg5'><strong>是否统计友情链接点击数：</strong></td>
    <td>
      <input type='radio' name='EnableCountFriendSiteHits' value='1' #{iif(site.enableCountFriendSiteHits == 1, "checked"," ") }> 是 &nbsp;&nbsp;&nbsp;&nbsp;
      <input type='radio' name='EnableCountFriendSiteHits' value='0' #{iif(site.enableCountFriendSiteHits == 0, "checked"," ") }> 否
    </td>
  </tr>
  <tr class='tdbg'>
    <td width='40%' class='tdbg5'><strong>是否使用频道、栏目、专题自设内容：</strong><br>若选择是，频道、栏目、专题管理会增加自设内容选项。</td>
    <td>
      <input type='radio' name='EnableSoftKey' value='1' #{iif(site.enableSoftKey == 1, "checked", " ") }> 是 &nbsp;&nbsp;&nbsp;&nbsp;
      <input type='radio' name='EnableSoftKey' value='0' #{iif(site.enableSoftKey == 0, "checked", " ") }> 否
    </td>
  </tr>
  <tr class='tdbg'>
    <td width='40%' class='tdbg5'><strong>弹出公告窗口的间隔时间：</strong><br>以小时为单位，为0时每次刷新页面时都弹出公告。</td>
    <td><input name='AnnounceCookieTime' type='text' id='AnnounceCookieTime' value='#{site.announceCookieTime}' size='10' maxlength='10'> 小时</td>
  </tr>
  <tr class='tdbg'>
    <td width='40%' class='tdbg5'><strong>网站热点的点击数最小值：</strong><br>只有点击数达到此数值，才会作为网站的热点内容显示。</td>
    <td><input name='HitsOfHot' type='text' id='HitsOfHot' value='#{site.hitsOfHot}' size='10' maxlength='10'></td>
  </tr>
  <tr class='tdbg'>
    <td width='40%' class='tdbg5'><strong><font color=red>网站首页的扩展名：</font></strong><br>若选择前四项，即启用了网站首页的生成HTML功能。</td>
    <td>
      <input name='FileExt_SiteIndex' type='radio' value='0' #{iif(site.fileExt_SiteIndex == 0, "checked", " ") }>.html &nbsp;&nbsp;&nbsp;&nbsp;
      <input name='FileExt_SiteIndex' type='radio' value='1' #{iif(site.fileExt_SiteIndex == 1, "checked", " ") }>.htm &nbsp;&nbsp;&nbsp;&nbsp;
      <input name='FileExt_SiteIndex' type='radio' value='2' #{iif(site.fileExt_SiteIndex == 2, "checked", " ") }>.shtml &nbsp;&nbsp;&nbsp;&nbsp;
      <input name='FileExt_SiteIndex' type='radio' value='3' #{iif(site.fileExt_SiteIndex == 3, "checked", " ") }>.shtm &nbsp;&nbsp;&nbsp;&nbsp;
      <input name='FileExt_SiteIndex' type='radio' value='4' #{iif(site.fileExt_SiteIndex == 4, "checked", " ") }>.jsp 
    </td>
  </tr>
  <tr class='tdbg'>
    <td width='40%' class='tdbg5'><strong><font color=red>全站专题的扩展名：</font></strong><br>若选择前四项，即启用了全站专题的生成HTML功能。</td>
    <td>
      <input name='FileExt_SiteSpecial' type='radio' value='0' #{iif(site.fileExt_SiteSpecial == 0, "checked", " ") }>.html &nbsp;&nbsp;&nbsp;&nbsp;
      <input name='FileExt_SiteSpecial' type='radio' value='1' #{iif(site.fileExt_SiteSpecial == 1, "checked", " ") }>.htm &nbsp;&nbsp;&nbsp;&nbsp;
      <input name='FileExt_SiteSpecial' type='radio' value='2' #{iif(site.fileExt_SiteSpecial == 2, "checked", " ") }>.shtml &nbsp;&nbsp;&nbsp;&nbsp;
      <input name='FileExt_SiteSpecial' type='radio' value='3' #{iif(site.fileExt_SiteSpecial == 3, "checked", " ") }>.shtm &nbsp;&nbsp;&nbsp;&nbsp;
      <input name='FileExt_SiteSpecial' type='radio' value='4' #{iif(site.fileExt_SiteSpecial == 4, "checked", " ") }>.jsp 
    </td>
  </tr>
  <tr class='tdbg'>
    <td width='40%' class='tdbg5'><strong>链接地址方式：</strong></td>
    <td>
      <input name='SiteUrlType' type='radio' value='0' #{iif(site.siteUrlType == 0, "checked", " ") }> 相对路径（形如：&lt;a href='/news/200509/1358.html'&gt;标题&lt;/a&gt;）<br>&nbsp;&nbsp;&nbsp;&nbsp;当一个网站有多个域名时，一般采用此方式<br>&nbsp;&nbsp;&nbsp;&nbsp;当一个网站有多个镜像网站时，必须采用此方式<br>
      <input name='SiteUrlType' type='radio' value='1' #{iif(site.siteUrlType == 1, "checked", " ") }> 绝对路径（形如：&lt;a href='http://www.chinaedustar.com/news/200509/1358.html'&gt;标题&lt;/a&gt;）<br>&nbsp;&nbsp;&nbsp;&nbsp;当要把频道做为子站点来访问时，必须使用此方式<br>&nbsp;&nbsp;&nbsp;&nbsp;要使用此方式，必须把网站URL设置正确。
    </td>
  </tr>
</table>
</pub:template>

<!-- 会员选项属性页(PropertyPage)显示模板定义 -->
<pub:template name="member_option_template">
<table width='100%' border='0' align='center' cellpadding='2' cellspacing='1'>
    <tr class='tdbg'>
      <td width='40%' class='tdbg5'><strong>是否允许新会员注册：</strong></td>
      <td>
        <input type='radio' name='EnableUserReg' value='1'  checked> 是 &nbsp;&nbsp;&nbsp;&nbsp;
        <input type='radio' name='EnableUserReg' value='0' > 否
      </td>
    </tr>
    <tr class='tdbg'>
      <td width='40%' class='tdbg5'><strong>新会员注册是否需要邮件验证：</strong><br>若选择“是”，则会员注册后系统会发一封带有验证码的邮件给此会员，会员必须在通过邮件验证后才能真正成为正式注册会员</td>
      <td>
        <input type='radio' name='EmailCheckReg' value='1' > 是 &nbsp;&nbsp;&nbsp;&nbsp;
  <input type='radio' name='EmailCheckReg' value='0'  checked> 否
      </td>
    </tr>
    <tr class='tdbg'>
      <td width='40%' class='tdbg5'><strong>新会员注册是否需要管理员认证：</strong><br>若选择是，则会员必须在通过管理员认证后才能真正成功正式注册会员。</td>
      <td>
        <input type='radio' name='AdminCheckReg' value='1' > 是 &nbsp;&nbsp;&nbsp;&nbsp;
        <input type='radio' name='AdminCheckReg' value='0'  checked> 否
      </td>
    </tr>
    <tr class='tdbg'>
      <td width='40%' class='tdbg5'><strong>每个Email是否允许注册多次：</strong><br>若选择是，则利用同一个Email可以注册多个会员。</td>
      <td>
        <input type='radio' name='EnableMultiRegPerEmail' value='1' > 是 &nbsp;&nbsp;&nbsp;&nbsp;
  <input type='radio' name='EnableMultiRegPerEmail' value='0'  checked> 否
      </td>
    </tr>
    <tr class='tdbg'>
      <td width='40%' class='tdbg5'><strong>会员注册时是否启用验证码功能：</strong><br>启用验证码功能可以在一定程度上防止暴力营销软件或注册机自动注册</td>
      <td>
        <input type='radio' name='EnableCheckCodeOfReg' value='1' > 是 &nbsp;&nbsp;&nbsp;&nbsp;
  <input type='radio' name='EnableCheckCodeOfReg' value='0'  checked> 否
      </td>
    </tr>
    <tr class='tdbg'>
      <td width='40%' class='tdbg5'><strong>会员注册时是否启用回答问题验证功能：</strong><br>启用此功能，可以最大程度上防止暴力营销软件或注册机自动注册，也可以用于某些特殊场合，防止无关人员注册会员。</td>
      <td>
        <input type='radio' name='EnableQAofReg' value='1' > 是 &nbsp;&nbsp;&nbsp;&nbsp;
  <input type='radio' name='EnableQAofReg' value='0'  checked> 否
      </td>
    </tr>
    <tr class='tdbg'>
      <td width='40%' class='tdbg5'><strong>问题一：</strong><br>如果启用验证功能，则问题一和答案必须填写。</td>
      <td>问题： <input type='text' name='RegQuestion1' value='问题一' size='50'><br>答案：
         <input type='text' name='RegAnswer1' value='答案一' size='50'></td>
    </tr>
    <tr class='tdbg'>
      <td width='40%' class='tdbg5'><strong>问题二：</strong><br>问题二可以选填</td>
      <td>问题： <input type='text' name='RegQuestion2' value='问题二' size='50'><br>答案：
         <input type='text' name='RegAnswer2' value='答案二' size='50'></td>
    </tr>
    <tr class='tdbg'>
      <td width='40%' class='tdbg5'><strong>问题三：</strong><br>问题三可以选填</td>
      <td>问题： <input type='text' name='RegQuestion3' value='问题三' size='50'><br>答案：
         <input type='text' name='RegAnswer3' value='答案三' size='50'></td>
    </tr>
    <tr class='tdbg'>
      <td width='40%' class='tdbg5'><strong>新会员注册时用户名最少字符数：</strong></td>
      <td><input name='UserNameLimit' type='text' id='UserNameLimit' value='4' size='6' maxlength='5'> 个字符</td>
    </tr>
    <tr class='tdbg'>
      <td width='40%' class='tdbg5'><strong>新会员注册时用户名最多字符数：</strong></td>
      <td><input name='UserNameMax' type='text' id='UserNameMax' value='20' size='6' maxlength='5'> 个字符</td>
    </tr>
    <tr class='tdbg'>
      <td width='40%' class='tdbg5'><strong>新会员注册时赠送的积分：</strong></td>
      <td><input name='PresentExp' type='text' id='PresentExp' value='0' size='6' maxlength='5'> 分积分</td>
    </tr>
    <tr class='tdbg'>
      <td width='40%' class='tdbg5'><strong>新会员注册时赠送的金钱：</strong></td>
      <td><input name='PresentMoney' type='text' id='PresentMoney' value='0' size='6' maxlength='5'> 元人民币（为0时不赠送）</td>
    </tr>
    <tr class='tdbg'>
      <td width='40%' class='tdbg5'><strong>新会员注册时赠送的点数：</strong></td>
      <td><input name='PresentPoint' type='text' id='PresentPoint' value='10' size='6' maxlength='5'> 点点券（为0时不赠送）</td>
    </tr>
    <tr class='tdbg'>
      <td width='40%' class='tdbg5'><strong>新会员注册时赠送的有效期：</strong></td>
      <td><input name='PresentValidNum' type='text' id='PresentValidNum' value='0' size='6' maxlength='5'>      <select name='PresentValidUnit' id='PresentValidUnit'><option value='1'  selected>天</option><option value='2' >月</option><option value='3' >年</option></select>（为0时不赠送，为－1表示无限期）</td>
    </tr>
    <tr class='tdbg'>
      <td width='40%' class='tdbg5'><strong>会员登录时是否启用验证码功能：</strong><br>启用验证码功能可以在一定程度上防止会员密码被暴力破解</td>
      <td>
        <input type='radio' name='EnableCheckCodeOfLogin' value='1'  checked> 是 &nbsp;&nbsp;&nbsp;&nbsp; 
  <input type='radio' name='EnableCheckCodeOfLogin' value='0' > 否
      </td>
    </tr>
    <tr class='tdbg'>
      <td width='40%' class='tdbg5'><strong>会员每登录一次奖励的积分：</strong><br>一天只计算一次</td>
      <td><input name='PresentExpPerLogin' type='text' id='PresentExpPerLogin' value='0' size='6' maxlength='5'> 分积分</td>
    </tr>
    <tr class='tdbg'>
      <td width='40%' class='tdbg5'><strong>会员的资金与点券的兑换比率：</strong></td>
      <td>每 <input name='MoneyExchangePoint' type='text' id='MoneyExchangePoint' value='1' size='6' maxlength='5'> 元钱可兑换 <strong><font color='#FF0000'>1</font></strong> 点点券</td>
    </tr>
    <tr class='tdbg'>
      <td width='40%' class='tdbg5'><strong>会员的资金与有效期的兑换比率：</strong></td>
      <td>每 <input name='MoneyExchangeValidDay' type='text' id='MoneyExchangeValidDay' value='1' size='6' maxlength='5'> 元钱可兑换 <strong><font color='#FF0000'>1</font></strong> 天有效期</td>
    </tr>
    <tr class='tdbg'>
      <td width='40%' class='tdbg5'><strong>会员的积分与点券的兑换比率：</strong></td>
      <td>每 <input name='UserExpExchangePoint' type='text' id='UserExpExchangePoint' value='1' size='6' maxlength='5'> 分积分可兑换 <strong><font color='#FF0000'>1</font></strong> 点点券</td>
    </tr>
    <tr class='tdbg'>
      <td width='40%' class='tdbg5'><strong>会员的积分与有效期的兑换比率：</strong></td>
      <td>每 <input name='UserExpExchangeValidDay' type='text' id='UserExpExchangeValidDay' value='1' size='6' maxlength='5'> 分积分可兑换 <strong><font color='#FF0000'>1</font></strong> 天有效期</td>
    </tr>
    <tr class='tdbg'>
      <td width='40%' class='tdbg5'><strong>点券的名称：</strong><br>例如：动易币、点券、金币</td>
      <td><input name='PointName' type='text' id='PointName' value='点券' size='6' maxlength='5'></td>
    </tr>
    <tr class='tdbg'>
      <td width='40%' class='tdbg5'><strong>点券的单位：</strong>例如：点、个</td>
      <td><input name='PointUnit' type='text' id='PointUnit' value='点' size='6' maxlength='5'></td>
    </tr>
</table>
</pub:template>


</pub:declare>
<%-- 执行模板，产生输出。 --%>
<pub:process_template name="main" />

</body>
</html>
