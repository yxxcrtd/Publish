<%@ page language="java" contentType="text/html; charset=gb2312" pageEncoding="UTF-8"
%><!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>发布系统网站通用标签</title>
<link href='help.css' rel='stylesheet' type='text/css' />
</head>
<body>

<h2>发布系统网站通用标签</h2>

<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td width="*" valign="top">
    <p>
    网站通用标签适用于整个网站，可在网站所有的版式模板页面中进行调用。以下将详细说明各标签的作用：
    </p>
    <!-- SiteName -->
    <table id='label_table' cellSpacing=1 cellPadding=1 width="100%" border=0>
      <tr>
        <th width='160'>标签名：</th>
        <th>#{SiteName}</th>
      </tr>
      <tr>
        <td>作用：</td>
        <td>显示网站名称</td>
      </tr>
      <tr>
        <td>等效写法：</td>
        <td>#{site.name}</td>
      </tr>
    </table>

    <!-- SiteUrl -->
    <table id='label_table' cellSpacing=1 cellPadding=1 width="100%" border=0>
      <tr>
        <th width='160'>标签名：</th>
        <th>#{SiteUrl}</th>
      </tr>
      <tr>
        <td>作用：</td>
        <td>得到网站地址，此地址是在网站配置中给出的网站完整地址，如 'http://www.domain.com/'</td>
      </tr>
      <tr>
        <td>等效写法：</td>
        <td>#{site.url}</td>
      </tr>
    </table>
    
    <!-- InstallDir -->
    <table id='label_table' cellSpacing=1 cellPadding=1 width="100%" border=0>
      <tr>
        <th width='160'>标签名：</th>
        <th>#{InstallDir}</th>
      </tr>
      <tr>
        <td>作用：</td>
        <td>得到系统安装目录，此目录是在网站配置中给出的，如 '/publish/'</td>
      </tr>
      <tr>
        <td>等效写法：</td>
        <th>#{site.installDir}</th>
      </tr>
    </table>
    
    <!-- Copyright -->
    <table id='label_table' cellSpacing=1 cellPadding=1 width="100%" border=0>
      <tr>
        <th width='160'>标签名：</th>
        <th>#{Copyright}</th>
      </tr>
      <tr>
        <td>作用：</td>
        <td>显示版权信息</td>
      </tr>
      <tr>
        <td>等效写法：</td>
        <th>#{site.copyright}</th>
      </tr>
    </table>
    
    <!-- Webmaster -->
    <table id='label_table' cellSpacing=1 cellPadding=1 width="100%" border=0>
      <tr>
        <th width='160'>标签名：</th>
        <th>#{Webmaster}</th>
      </tr>
      <tr>
        <td>作用：</td>
        <td>显示站长姓名</td>
      </tr>
      <tr>
        <td>等效写法：</td>
        <th>#{site.webmaster}</th>
      </tr>
    </table>
    
    <!-- WebmasterEmail -->
    <table id='label_table' cellSpacing=1 cellPadding=1 width="100%" border=0>
      <tr>
        <th width='160'>标签名：</th>
        <th>#{WebmasterEmail}</th>
      </tr>
      <tr>
        <td>作用：</td>
        <td>显示站长 Email 地址</td>
      </tr>
      <tr>
        <td>等效写法：</td>
        <th>#{site.webmasterEmail}</th>
      </tr>
    </table>
    
    <!-- Meta_Keywords -->
    <table id='label_table' cellSpacing=1 cellPadding=1 width="100%" border=0>
      <tr>
        <th width='160'>标签名：</th>
        <th>#{Meta_Keywords}</th>
      </tr>
      <tr>
        <td>作用：</td>
        <td>网站META关键词，针对搜索引擎设置的关键词</td>
      </tr>
      <tr>
        <td>等效写法：</td>
        <td>&lt;meta name='Keywords' content='#{site.metaKey@html}' /&gt;</td>
      </tr>
    </table>
    
    <!-- Meta_Description -->
    <table id='label_table' cellSpacing=1 cellPadding=1 width="100%" border=0>
      <tr>
        <th width='160'>标签名：</th>
        <th>#{Meta_Description}</th>
      </tr>
      <tr>
        <td>作用：</td>
        <td>网站META网页描述，针对搜索引擎设置的网页描述</td>
      </tr>
      <tr>
        <td>等效写法：</td>
        <td>&lt;meta name='Description' content='#{site.metaDesc@html}' /&gt;</td>
      </tr>
    </table>
    
    <!-- Skin_CSS -->
    <table id='label_table' cellSpacing=1 cellPadding=1 width="100%" border=0>
      <tr>
        <th width='160'>标签名：</th>
        <th>#{Skin_CSS}</th>
      </tr>
      <tr>
        <td>作用：</td>
        <td>得到系统相关的风格CSS调用代码</td>
      </tr>
    </table>
    
    <!-- PageTitle -->    
    <table id='label_table' cellSpacing=1 cellPadding=1 width="100%" border=0>
      <tr>
        <th width='160'>标签名：</th>
        <th>#{PageTitle}</th>
      </tr>
      <tr>
        <td>作用：</td>
        <td>显示浏览器的标题栏显示页面的标题信息</td>
      </tr>
      <tr>
        <td>等效写法：</td>
        <td>#{this.title} - 显示当前站点、频道、栏目、专题、文章等当前对象的标题<br/>
         #{site.title} - 显示当前站点的标题。
        </td>
      </tr>
    </table>
    
    <!-- ShowPath -->
    <table id='label_table' cellSpacing=1 cellPadding=1 width="100%" border=0>
      <tr>
        <th width='160'>标签名：</th>
        <th>#{ShowPath}</th>
      </tr>
      <tr>
        <td>作用：</td>
        <td>显示“你现在所有位置”导航信息</td>
      </tr>
    </table>
    
    <!-- ShowAdminLogin -->
    <table id='label_table' cellSpacing=1 cellPadding=1 width="100%" border=0>
      <tr>
        <th width='160'>标签名：</th>
        <th>#{ShowAdminLogin}</th>
      </tr>
      <tr>
        <td>作用：</td>
        <td>显示管理登录及链接</td>
      </tr>
      <tr>
        <td>等效写法：</td>
        <td>&lt;a href='#{installDir}admin/admin_login.jsp'&gt;管理登录&lt;/a&gt;
         地址可以根据自己需要进行调整。
        </td>
      </tr>
    </table>
    
    <!-- ShowVote -->
    <table id='label_table' cellSpacing=1 cellPadding=1 width="100%" border=0>
      <tr>
        <th width='160'>标签名：</th>
        <th>#{ShowVote}</th>
      </tr>
      <tr>
        <td>作用：</td>
        <td>显示网站调查</td>
      </tr>
    </table>
    
    <!-- ShowSiteCountAll -->
    <table id='label_table' cellSpacing=1 cellPadding=1 width="100%" border=0>
      <tr>
        <th width='160'>标签名：</th>
        <th>#{ShowSiteCountAll}</th>
      </tr>
      <tr>
        <td>作用：</td>
        <td>显示网站统计信息</td>
      </tr>
    </table>
    
    <!-- ShowLogo -->
    <table id='label_table' cellSpacing=1 cellPadding=1 width="100%" border=0>
      <tr>
        <th width='160'>标签名：</th>
        <th>#{ShowLogo}</th>
      </tr>
      <tr>
        <td>作用：</td>
        <td>显示网站LOGO图片</td>
      </tr>
      <tr>
        <td>参数：</td>
        <td>
         width -- 显示LOGO宽度<br/>
         height -- 显示LOGO高度<br/>
        </td>
      </tr>
      <tr>
        <td>例子</td>
        <td>
          #{ShowLogo width=180 height=60 }
        </td>
      </tr>
    </table>
    
    <!-- ShowBanner -->
    <table id='label_table' cellSpacing=1 cellPadding=1 width="100%" border=0>
      <tr>
        <th width='160'>标签名：</th>
        <th>#{ShowBanner}</th>
      </tr>
      <tr>
        <td>作用：</td>
        <td>显示网站 Banner 图片</td>
      </tr>
      <tr>
        <td>参数：</td>
        <td>
         width -- 显示 Banner 宽度，缺省 500<br/>
         height -- 显示 Banner 高度，缺省 60<br/>
        </td>
      </tr>
      <tr>
        <td>例子</td>
        <td>
          #{ShowBanner width=500 height=60 }， #{ShowBanner } 效果和前面相同。
        </td>
      </tr>
    </table>
    
    <!-- ShowChannel -->
    <table id='label_table' cellSpacing=1 cellPadding=1 width="100%" border=0>
      <tr>
        <th width='160'>标签名：</th>
        <th>#{ShowChannel}</th>
      </tr>
      <tr>
        <td>作用：</td>
        <td>显示页面顶部的频道导航。</td>
      </tr>
      <tr>
        <td>例子</td>
        <td>
          #{ShowChannel }
        </td>
      </tr>
    </table>
    
    <!-- ShowBottom -->
    <table id='label_table' cellSpacing=1 cellPadding=1 width="100%" border=0>
      <tr>
        <th width='160'>标签名：</th>
        <th>#{ShowBottom}</th>
      </tr>
      <tr>
        <td>作用：</td>
        <td>显示并输出网页底部的 HTML。</td>
      </tr>
      <tr>
        <td>参数：</td>
        <td>
        color -- 底部 html 表格的前景色，缺省为 #000000 。<br/>
        bgcolor -- 底部 html 表格的背景色，缺省为 #ffffff 。<br/>
        </td>
      </tr>
      <tr>
        <td>例子</td>
        <td>
          #{ShowBottom}, 等效于 #{ShowBottom color='#000000' bgcolor='#ffffff' }
        </td>
      </tr>
    </table>

    <!-- ShowAnnounce -->    
    <table id='label_table' cellSpacing=1 cellPadding=1 width="100%" border=0>
      <tr>
        <th width='160'>标签名：</th>
        <th>#{ShowAnnounce}</th>
      </tr>
      <tr>
        <td>作用：</td>
        <td>显示本站公告信息。</td>
      </tr>
      <tr>
        <td>参数：</td>
        <td>
	 	   channelId -- 缺省为当前频道。公告所在频道的标识。-1：频道公用公告；0：网站首页公告；&gt; 0：该频道的公告<br/>
	      style -- 显示方式 1：横向；2：纵向。默认值为1。<br/>
	      num -- 最多显示多少条公告 默认为5。<br/>
	 	   showAuthor -- 是否显示公告的作者，默认为true。<br/>
	 	   showDate -- 是否显示公告发布的日期。默认为true。<br/>
	 	   contentLen -- 公告内容最多字符数。默认为50。<br/>
        </td>
      </tr>
      <tr>
        <td>例子</td>
        <td>
          #{ShowAnnounce channelId='0' style='1' num='5' showAuthor='true' contentLen='56' }
        </td>
      </tr>
    </table>
    
    <!-- ShowFriendSite -->
    <table id='label_table' cellSpacing=1 cellPadding=1 width="100%" border=0>
      <tr>
        <th width='160'>标签名：</th>
        <th>#{ShowFriendSite}</th>
      </tr>
      <tr>
        <td>作用：</td>
        <td>显示友情链接信息。</td>
      </tr>
      <tr>
        <td>参数：</td>
        <td>
          linkType -- 链接类型。文字链接 = 1；图片链接 = 2。默认为1。<br/>
          kindId -- 链接类别的标识。默认为0，表示所有的类别。<br/>
          specialId -- 链接专题的标识。默认为0，表示所有的专题。<br/>
          command -- true 表示显示推荐的站点，false 表示显示非推荐的站点，null 表示任何站点。默认为null。<br/>
          maxNum -- 最多显示站点个数。 默认为14。<br/>
          orderBy -- 按什么排序。默认为 0。即按排序ID降序。<br/>
          *  (以上参数影响获取数据的方式)<br/>
          cols -- 每行显示多少个站点。 默认为7。<br/>
          tableWidth -- 显示宽度。 默认为100%。<br/>
          showStyle -- 显示方式。1：向上滚动；2：横向列表；3：下拉列表。默认为2。<br/>
          * 
          * 以下只有在showStyle为1时才有用<br/>
          delay -- 停留时间，默认为20毫秒，时间越长，向上滚动所需的时间越长<br/>
          width -- 向上滚动的内容的宽度，默认为100%。<br/>
          height -- 向上滚动的内容的高度，默认为40。<br/>
        </td>
      </tr>
      <tr>
        <td>例子</td>
        <td>
          #{ShowFriendSite linkType='1' maxNum='8' cols='4' showStyle='2'  }
        </td>
      </tr>
    </table>
    
    <!-- ShowAuthorList -->
    <table id='label_table' cellSpacing=1 cellPadding=1 width="100%" border=0>
      <tr>
        <th width='160'>标签名：</th>
        <th>#{ShowAuthorList}</th>
      </tr>
      <tr>
        <td>作用：</td>
        <td>显示作者列表。</td>
      </tr>
      <tr>
        <td>参数：</td>
        <td>
          channelId - 频道的标识，-1：网站中所有的作者（包括所有频道的作者）；0：当前频道中的作者，如果频道对象不存在，则为全站作者；1：特定频道的作者。默认为 0 。<br/>
          type - 作者分类，0：所有分类；1 大陆作者； 2 港台作者； 3 海外作者； 4 本站特约； 5其他作者。默认为 0 。<br/>
          num - 显示多少个作者。缺省取前 5 个。<br/>
          dispType  - 显示方式。0：普通；1：显示图像及说明；2：显示图像不显示说明。默认为1。<br/>
          openType  - 打开方式。0：_self；1：_blank。默认为0。<br/>
          cols - 每行显示的列数，默认为 1 。<br/>
          style - 显示的样式表。<br/>
          imgWidth  - 头像宽度。默认为50px。<br/>
          imgHeight - 头像高度。默认为60px。<br/>
          picPos - 图片与文字位置。0：图左；1：图上；2：图右；3：图下。默认为左。<br/>
          moreText - ”更多“处显示的文字。如"more";<br/>
        </td>
      </tr>
      <tr>
        <td>例子</td>
        <td>
          #{ShowAuthorList num='8' openType='_blank' }
        </td>
      </tr>
    </table>
  
    <!-- ShowSpecialList -->
    <table id='label_table' cellSpacing=1 cellPadding=1 width="100%" border=0>
      <tr>
        <th width='160'>标签名：</th>
        <th>#{ShowSpecialList}</th>
      </tr>
      <tr>
        <td>作用：</td>
        <td>显示作者列表。</td>
      </tr>
      <tr>
        <td>参数：</td>
        <td>
          channel -- 频道的标识.<br/>
&nbsp; &nbsp;   -3,all: 所有专题。<br/>
&nbsp; &nbsp;   -2: 全站和本频道的专题。<br/>
&nbsp; &nbsp;   -1,site：全站专题。<br/>
&nbsp; &nbsp;   0,current：(默认)当前频道的专题（如果当前频道不存在，则表示为网站）<br/>
&nbsp; &nbsp;   &gt; 0：指定的某个频道中的专题。<br/>
          isElite -- 是否推荐：不指定或为空表示全部；true：推荐的专题；false：未推荐的专题。<br/>
          itemNum -- 获取的专题数目，= 0 表示获取全部, 缺省 = 8。<br/>
        </td>
      </tr>
      <tr>
        <td>例子</td>
        <td>
          #{ShowSpecialList channel='current' itemNum='10' }
        </td>
      </tr>
    </table>
    
    </td>
  </tr>
</table>

</body>
</html>

      