<%@ page language="java" contentType="text/html; charset=gb2312" pageEncoding="UTF-8"
%><!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>栏目 column 对象介绍</title>
<link href='help.css' rel='stylesheet' type='text/css' />
</head>
<body>

<h2>栏目 column 对象介绍</h2>

<p>栏目对象提供了栏目名称、地址等配置信息。
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
    <p>表示这个对象的标识，标识是一个整数，范围从 1 - (2^31-1)，
        栏目的标识是重要的内部参数，一旦创建栏目之后该值就不能更改了。
    </p>
    <p>使用示例： #{column.id}。系统内部标签 #{ColumnID } 内部就是用 column.id 实现的。 <br/>
    另外 id 属性的全名是 objectId, 使用 objectId 访问效果完全相同，如 #{column.objectId}。<br/>
    </p>
    </td>
  </tr>
  
  <!-- objectClass -->
  <tr>
    <td>objectClass</td>
    <td>
    <p>表示这个对象的类型，对于 column 对象，类型返回为 'Column'。
    </p>
    <p>使用示例： #{column.objectClass}。</p>
    </td>
  </tr>
  
  <!-- parent -->
  <tr>
    <td>parent</td>
    <td>
    <p>得到项目对象的父对象。
    </p>
    <p>使用示例： #{column.parent}
    </p>
    </td>
  </tr>
  
  <!-- objectUuid -->
  <tr>
    <td>uuid</td>
    <td>
    <p>得到站点对象的全局唯一标识，该标识是一个 40 位的标准 GUID 格式字符串。
    此标识在所有对象，包括网站、栏目、栏目等中都是唯一的。系统使用这种标识来在任何对象中唯一区分一个对象。
    </p>
    <p>使用示例： #{column.uuid}，全名：objectUuid, 如 #{column.objectUuid}
    </p>
    </td>
  </tr>
  
  <!-- name -->
  <tr>
    <td><font color='blue'>name</font></td>
    <td>
    <p>返回此栏目的名字，如 '国内新闻'，该信息配置的地方在后台管理的栏目信息配置中。</p>
    <p>使用示例，#{column.name }。标签 #{ColumnName } 内部使用 column.name 来实现。</p>
    </td>
  </tr>
  
  <!-- logo -->
  <tr>
    <td>logo</td>
    <td>
    <p>返回此栏目的 LOGO 图片，如 'images/news_logo.jpg'，该信息配置的地方在后台管理的栏目信息配置中。</p>
    <p>使用示例，#{column.logo }。 当前系统也许没有使用这个属性，但不影响您自由的使用此属性。</p>
    </td>
  </tr>
  
  <!-- banner -->
  <tr>
    <td>banner</td>
    <td>
    <p>返回此栏目的 BANNER 图片，如 'images/news_banner.jpg'，该信息配置的地方在后台管理的栏目信息配置中。</p>
    <p>使用示例，#{column.banner }。</p>
    </td>
  </tr>
  
  <!-- copyright -->
  <tr>
    <td>copyright</td>
    <td>
    <p>返回此栏目的版权声明，如 'Copyright by xxx'，该信息配置的地方在后台管理的栏目信息配置中。
      版权声明中允许直接使用 html 代码。
    </p>
    <p>使用示例，#{column.copyright }。</p>
    </td>
  </tr>
  
  <!-- metaKey -->
  <tr>
    <td>metaKey</td>
    <td>
    <p>获得此栏目的针对搜索引擎的关键字 keywords 描述。</p>
    <p>
     使用示例： #{column.metaKey}, 系统标签 #{Meta_Keywords_Column} 内部实现为:<br/>
      &lt;meta name="keywords" content="#{column.metaKey@html }" /&gt;
    </p>
    </td>
  </tr>
  
  <!-- metaDesc -->
  <tr>
    <td>metaDesc</td>
    <td>
    <p>获得栏目的针对搜索引擎的 description 描述。</p>
    <p>使用示例： #{column.metaDesc}, 系统标签 #{Meta_Description_Column} 内部实现为:<br/>
      &lt;meta name="description" content="#{column.metaDesc@html }" /&gt;
    </p>
    </td>
  </tr>
  
  <!-- templateId -->
  <tr>
    <td>templateId</td>
    <td>
    <p>获得栏目使用的显示模板标识，如果 = 0 则表示使用当前选择的缺省模板方案中的缺省模板。</p>
    <p>使用示例： #{column.templateId} </p>
    </td>
  </tr>

  <!-- skinId -->
  <tr>
    <td>skinId</td>
    <td>
    <p>获得栏目使用的样式标识，如果 = 0 则表示使用当前选择的缺省模板方案中该类型的缺省样式。</p>
    <p>使用示例： #{column.skinId} </p>
    </td>
  </tr>

  <!-- pageUrl -->
  <tr>
    <td><font color='blue'>pageUrl</font></td>
    <td>
    <p>获得栏目首页地址，该地址已经经过了绝对化处理，根据站点配置项目'链接地址方式'，
      如果设置为相对路径，则返回形式为 '/installDir/channelDir/animal/index.html'；如果设置为绝对路径，
      则返回形式为 'http://your-domain/installDir/channelDir/showColumn.jsp?columnId=xxx'。<br/>
     如果项目所在栏目设置项目要生成静态页面，则返回为静态页面地址，即使该页面还未生成；否则返回为动态 'channelDir/showColumn.jsp?columnId=xxx' 地址。
      </p>
    <p>使用示例： #{column.pageUrl} </p>
    <p>标签 #{ColumnUrl } 内部使用 column.pageUrl 属性实现。</p>
    </td>
  </tr>
  
  <!-- isGenerated -->
  <tr>
    <td>isGenerated</td>
    <td>
    <p>栏目是否已经生成了静态页面的标志，= true 表示已经生成了静态页面，= false 表示还未生成静态页面。</p>
    <p>
     使用示例： #{column.isGenerated}
    </p>
    </td>
  </tr>
  
  <!-- staticPageUrl -->
  <tr>
    <td>staticPageUrl</td>
    <td>
      <p>栏目静态页面的 url 地址，这个地址是相对于站点安装目录的 site.installDir。
      如果页面还未生成静态化的页面，依然返回一个静态化地址，表示其如果未来静态化了的地址所在。</p>
      <p>该地址也是没有经过绝对化 (relativize) 处理的，直接使用在任何页面可能导致链接地址不正确。 使用属性
      column.pageUrl 能够保证在任何页面获得一个正确的项目链接地址，一般推荐使用 #{column.pageUrl}。
      请参见 pageUrl 属性的详细说明。</p>
      <p>使用示例：
      #{column.staticPageUrl}，将该地址绝对化：#{column.staticPageUrl@uri(channel) }</p>
    </td>
  </tr>

  
  </tbody>
</table>

</body>
</html>
