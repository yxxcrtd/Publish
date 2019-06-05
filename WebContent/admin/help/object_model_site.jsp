<%@ page language="java" contentType="text/html; charset=gb2312" pageEncoding="UTF-8"
%><!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>网站站点 site 对象介绍</title>
<link href='help.css' rel='stylesheet' type='text/css' />
</head>
<body>

<h2>网站站点 site 对象介绍</h2>

<p>网站对象是在任何页面都可以访问的对象，它提供了整个网站的名称、地址等配置信息。
</p>

<p>下表详细列出这些属性及其说明(属性名标记为蓝色的为常用属性)：
</p>

<table id='label_table' width="100%" border="0" cellspacing="1" cellpadding="2" align='center'>
  <thead>
  <tr>
    <th width="180">属性名</th>
    <th width='*'>详细说明</th>
  </tr>
  </thead>
  <tbody>
  <!-- id -->
  <tr>
    <td><font color='blue'>id</font></td>
    <td>
    <p>表示这个对象的标识，标识是一个整数，范围从 1 - (2^31-1)，由于站点对象只有一个，其标识也就只有一个。
    </p>
    <p>使用示例： #{site.id}。<br/>
    另外 id 属性的全名是 objectId, 使用 objectId 访问效果完全相同，如 #{site.objectId}
    </p>
    </td>
  </tr>
  
  <!-- objectClass -->
  <tr>
    <td>objectClass</td>
    <td>
    <p>表示这个对象的类型，对于 site 对象，类型返回为 'Site'。
    </p>
    <p>使用示例： #{site.objectClass}。</p>
    </td>
  </tr>
  
  <!-- parent -->
  <tr>
    <td>parent</td>
    <td>
    <p>得到项目对象的父对象，在当前发布系统中 site 对象没有父对象，所以其返回 null.
    </p>
    <p>使用示例： #{site.parent}
    </p>
    </td>
  </tr>
  
  <!-- objectUuid -->
  <tr>
    <td>uuid</td>
    <td>
    <p>得到站点对象的全局唯一标识，该标识是一个 40 位的标准 GUID 格式字符串。
    此标识在所有对象，包括网站、频道、栏目等中都是唯一的。系统使用这种标识来在任何对象中唯一区分一个对象。
    </p>
    <p>使用示例： #{site.uuid}，全名：objectUuid, 如 #{site.objectUuid}
    </p>
    </td>
  </tr>
  
  <!-- name -->
  <tr>
    <td><font color='blue'>name</font></td>
    <td>
    <p>返回此站点的名字，如 'XXX网站'，该信息配置的地方在后台管理的网站信息配置中。</p>
    <p>使用示例，#{site.name }。标签 #{SiteName } 内部使用 site.name 来实现。</p>
    </td>
  </tr>
  
  <!-- logo -->
  <tr>
    <td>logo</td>
    <td>
    <p>返回此站点的 LOGO 图片，如 'images/logo.jpg'，该信息配置的地方在后台管理的网站信息配置中。</p>
    <p>使用示例，#{site.logo }。系统标签 #{ShowLogo } 内部使用 site.logo 做为网站的 Logo 图片。</p>
    </td>
  </tr>
  
  <!-- banner -->
  <tr>
    <td>banner</td>
    <td>
    <p>返回此站点的 BANNER 图片，如 'images/banner.jpg'，该信息配置的地方在后台管理的网站信息配置中。</p>
    <p>使用示例，#{site.banner }。系统标签 #{ShowBanner } 内部使用 site.banner 做为网站的 Banner 图片。</p>
    </td>
  </tr>
  
  <!-- copyright -->
  <tr>
    <td>copyright</td>
    <td>
    <p>返回此站点的版权声明，如 'Copyright by xxx'，该信息配置的地方在后台管理的网站信息配置中。
      版权声明中允许直接使用 html 代码。
    </p>
    <p>使用示例，#{site.copyright }。系统标签 #{ShowBottom } 内部使用 site.copyright 
       做为网站的版权声明内容部分。</p>
    </td>
  </tr>
  
  <!-- metaKey -->
  <tr>
    <td>metaKey</td>
    <td>
    <p>获得此站点的针对搜索引擎的关键字 keywords 描述。</p>
    <p>
     使用示例： #{site.metaKey}, 系统标签 #{Meta_Keywords} 内部实现为:<br/>
      &lt;meta name="keywords" content="#{site.metaKey@html }" /&gt;
    </p>
    </td>
  </tr>
  
  <!-- metaDesc -->
  <tr>
    <td>metaDesc</td>
    <td>
    <p>获得站点的针对搜索引擎的 description 描述。</p>
    <p>使用示例： #{site.metaDesc}, 系统标签 #{Meta_Description} 内部实现为:<br/>
      &lt;meta name="description" content="#{site.metaDesc@html }" /&gt;
    </p>
    </td>
  </tr>
  
  <!-- templateId -->
  <tr>
    <td>templateId</td>
    <td>
    <p>获得站点使用的显示模板标识，如果 = 0 则表示使用当前选择的缺省模板方案中网站的缺省模板。</p>
    <p>使用示例： #{site.templateId} </p>
    </td>
  </tr>

  <!-- skinId -->
  <tr>
    <td>skinId</td>
    <td>
    <p>获得站点使用的样式标识，如果 = 0 则表示使用当前选择的缺省模板方案中该类型的缺省样式。</p>
    <p>使用示例： #{site.skinId} </p>
    <p>标签 #{Skin_CSS } 内部使用 site.skinId 属性实现。</p>
    </td>
  </tr>

  <!-- pageUrl -->
  <tr>
    <td>pageUrl</td>
    <td>
    <p>获得站点页面地址，该地址已经经过了绝对化处理，根据站点配置项目'链接地址方式'，
      如果设置为相对路径，则返回形式为 '/installDir/index.xxx'；如果设置为绝对路径，
      则返回形式为 'http://your-domain/installDir/index.xxx'。<br/>
     如果项目所在频道设置项目要生成静态页面，则返回为静态页面地址，即使该页面还未生成；否则返回为动态 'index.jsp' 地址。
      </p>
    <p>使用示例： #{site.pageUrl} </p>
    <p>标签 #{SitePageUrl } 内部使用 site.pageUrl 属性实现。
      对于站点对象，一般不是用 pageUrl 属性，而是使用网站配置的 site.url 属性。参见 site.url 属性的说明。
    </p>
    </td>
  </tr>
  
    <!-- isGenerated -->
  <tr>
    <td>isGenerated</td>
    <td>
    <p>站点是否已经生成了静态页面的标志，= true 表示已经生成了静态页面，= false 表示还未生成静态页面。</p>
    <p>
     使用示例： #{site.isGenerated}
    </p>
    </td>
  </tr>
  
  <!-- staticPageUrl -->
  <tr>
    <td>staticPageUrl</td>
    <td>
      <p>站点静态页面的 url 地址，这个地址是相对于站点安装目录的 site.installDir。
      如果页面还未生成静态化的页面，依然返回一个静态化地址，表示其如果未来静态化了的地址所在。</p>
      <p>该地址也是没有经过绝对化 (relativize) 处理的，直接使用在任何页面可能导致链接地址不正确。 使用属性
      site.url, site.pageUrl 能够保证在任何页面获得一个正确的项目链接地址，一般推荐使用 #{site.url}。
      请参见 url, pageUrl 属性的详细说明。</p>
      <p>使用示例：
      #{site.staticPageUrl}，将该地址绝对化：#{site.staticPageUrl@uri }</p>
    </td>
  </tr>

  <!-- title -->
  <tr>
    <td><font color='blue'>title</font></td>
    <td>
      <p>获得网站的标题，如 'XXX网'，该信息配置的地方在后台管理的网站信息配置中。</p>
      <p>使用示例：
      #{site.title}，系统标签 #{PageTitle } 在内部使用 site.title，如果在主页上使用 
      #{PageTitle } 标签，则得到的标题就是 site.title。</p>
    </td>
  </tr>
  
  <!-- url -->
  <tr>
    <td><font color='blue'>url</font></td>
    <td>
      <p>获得网站的URL地址，如 'http://www.yourdomain.com/publish/'，该信息配置的地方在后台管理的网站信息配置中。
       网站的 url 地址必须完整配置，即包含完整的 'http://domain/dir/'，如果安装在根目录下面，
       则配置为 'http://domain/'，注意最后必须以 '/' 结尾。在网站内部地址绝对化的时候，内部使用此地址进行正确的计算。
      </p>
      <p>一般，site.url 是网站的首页地址，在任何地方使用 site.url 都要保证正确的链接到主页，
        并且不考虑主页是否被静态化了，为此 url 后面不需要添加 'index.jsp' 或 'index.html' 。
        通过在网站上配置合适的 welcome 文件列表(可以配置为 index.html, index.jsp) 系统会在目录中自动找到首页文件。
      </p>
      <p>做为网站最重要的参数 url 地址，您必须保证其配置正确。我们推荐您使用域名做为此地址，而不是难于记忆的 IP 地址形式。
      </p>
      <p>使用示例：
      #{site.url}，系统标签 #{SiteUrl } 在内部使用 site.url 实现。</p>
    </td>
  </tr>
  
  <!-- installDir -->
  <tr>
    <td><font color='blue'>installDir</font></td>
    <td>
      <p>获得网站安装的地址，如 '/publish/'，该信息配置的地方在后台管理的网站信息配置中。
       注意其前后都必须添加 '/' 分界符。如果安装在根目录下面，则设置 installDir 为 '/'。
       在网站内部地址绝对化的时候，内部使用此地址进行正确的计算。
      </p>
      <p>使用示例：
      #{site.installDir}，系统标签 #{InstallDir } 在内部使用 site.installDir 实现。</p>
    </td>
  </tr>
  
  <!-- webmaster -->
  <tr>
    <td>webmaster</td>
    <td>
      <p>获得网站安装的管理员名字。 如 '张三'，该信息配置的地方在后台管理的网站信息配置中。
      </p>
      <p>使用示例：
      #{site.webmaster}，系统标签 #{Webmaster } 在内部使用 site.webmaster 实现。</p>
    </td>
  </tr>

  <!-- webmasterEmail -->
  <tr>
    <td>webmasterEmail</td>
    <td>
      <p>获得网站安装的管理员邮箱地址。 如 'webmaster@your-domain.com'，该信息配置的地方在后台管理的网站信息配置中。
      </p>
      <p>使用示例：
      #{site.webmasterEmail}，系统标签 #{WebmasterEmail } 在内部使用 site.webmasterEmail 实现。</p>
    </td>
  </tr>
  
  <!-- 还有一些配置方面的内容, 我们也许应该以后组装之后介绍 -->
  
  </tbody>
</table>

</body>
</html>
