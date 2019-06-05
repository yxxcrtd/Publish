<%@ page language="java" contentType="text/html; charset=gb2312" pageEncoding="UTF-8"
%><!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>频道 channel 对象介绍</title>
<link href='help.css' rel='stylesheet' type='text/css' />
</head>
<body>

<h2>频道 channel 对象介绍</h2>

<p>频道对象提供了频道名称、地址等配置信息。
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
        频道的标识是重要的内部参数，一旦创建频道之后该值就不能更改了。
    </p>
    <p>使用示例： #{channel.id}。系统内部标签 #{ChannelID } 内部就是用 channel.id 实现的。 <br/>
    另外 id 属性的全名是 objectId, 使用 objectId 访问效果完全相同，如 #{channel.objectId}。<br/>
    系统标签 #{ChannelID } 内部使用此属性实现。
    </p>
    </td>
  </tr>
  
  <!-- objectClass -->
  <tr>
    <td>objectClass</td>
    <td>
    <p>表示这个对象的类型，对于 channel 对象，类型返回为 'Channel'。
    </p>
    <p>使用示例： #{channel.objectClass}。</p>
    </td>
  </tr>
  
  <!-- parent -->
  <tr>
    <td>parent</td>
    <td>
    <p>得到项目对象的父对象，在当前发布系统中 channel 对象父对象是站点对象，因此 channel.parent 总是返回 site 对象。
    </p>
    <p>使用示例： #{channel.parent}
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
    <p>使用示例： #{channel.uuid}，全名：objectUuid, 如 #{channel.objectUuid}
    </p>
    </td>
  </tr>
  
  <!-- name -->
  <tr>
    <td><font color='blue'>name</font></td>
    <td>
    <p>返回此频道的名字，如 '新闻中心'，该信息配置的地方在后台管理的频道信息配置中。</p>
    <p>使用示例，#{channel.name }。标签 #{ChannelName } 内部使用 channel.name 来实现。</p>
    </td>
  </tr>
  
  <!-- logo -->
  <tr>
    <td>logo</td>
    <td>
    <p>返回此频道的 LOGO 图片，如 'images/news_logo.jpg'，该信息配置的地方在后台管理的频道信息配置中。</p>
    <p>使用示例，#{channel.logo }。 当前系统也许没有使用这个属性，但不影响您自由的使用此属性。</p>
    </td>
  </tr>
  
  <!-- banner -->
  <tr>
    <td>banner</td>
    <td>
    <p>返回此频道的 BANNER 图片，如 'images/news_banner.jpg'，该信息配置的地方在后台管理的频道信息配置中。</p>
    <p>使用示例，#{channel.banner }。</p>
    </td>
  </tr>
  
  <!-- copyright -->
  <tr>
    <td>copyright</td>
    <td>
    <p>返回此频道的版权声明，如 'Copyright by xxx'，该信息配置的地方在后台管理的频道信息配置中。
      版权声明中允许直接使用 html 代码。
    </p>
    <p>使用示例，#{channel.copyright }。</p>
    </td>
  </tr>
  
  <!-- metaKey -->
  <tr>
    <td>metaKey</td>
    <td>
    <p>获得此频道的针对搜索引擎的关键字 keywords 描述。</p>
    <p>
     使用示例： #{channel.metaKey}, 系统标签 #{Meta_Keywords_Channel} 内部实现为:<br/>
      &lt;meta name="keywords" content="#{channel.metaKey@html }" /&gt;
    </p>
    </td>
  </tr>
  
  <!-- metaDesc -->
  <tr>
    <td>metaDesc</td>
    <td>
    <p>获得频道的针对搜索引擎的 description 描述。</p>
    <p>使用示例： #{channel.metaDesc}, 系统标签 #{Meta_Description_Channel} 内部实现为:<br/>
      &lt;meta name="description" content="#{channel.metaDesc@html }" /&gt;
    </p>
    </td>
  </tr>
  
  <!-- templateId -->
  <tr>
    <td>templateId</td>
    <td>
    <p>获得频道使用的显示模板标识，如果 = 0 则表示使用当前选择的缺省模板方案中的缺省模板。</p>
    <p>使用示例： #{channel.templateId} </p>
    </td>
  </tr>

  <!-- skinId -->
  <tr>
    <td>skinId</td>
    <td>
    <p>获得频道使用的样式标识，如果 = 0 则表示使用当前选择的缺省模板方案中该类型的缺省样式。</p>
    <p>使用示例： #{channel.skinId} </p>
    </td>
  </tr>

  <!-- pageUrl -->
  <tr>
    <td><font color='blue'>pageUrl</font></td>
    <td>
    <p>获得频道首页地址，该地址已经经过了绝对化处理，根据站点配置项目'链接地址方式'，
      如果设置为相对路径，则返回形式为 '/installDir/channelDir/index.xxx'；如果设置为绝对路径，
      则返回形式为 'http://your-domain/installDir/channelDir/index.xxx'。<br/>
     如果项目所在频道设置项目要生成静态页面，则返回为静态页面地址，即使该页面还未生成；否则返回为动态 'channelDir/index.jsp' 地址。
      </p>
    <p>使用示例： #{channel.pageUrl} </p>
    <p>标签 #{ChannelUrl } 内部使用 channel.pageUrl 属性实现。</p>
    </td>
  </tr>
  
  <!-- isGenerated -->
  <tr>
    <td>isGenerated</td>
    <td>
    <p>频道是否已经生成了静态页面的标志，= true 表示已经生成了静态页面，= false 表示还未生成静态页面。</p>
    <p>
     使用示例： #{channel.isGenerated}
    </p>
    </td>
  </tr>
  
  <!-- staticPageUrl -->
  <tr>
    <td>staticPageUrl</td>
    <td>
      <p>频道静态页面的 url 地址，这个地址是相对于站点安装目录的 site.installDir。
      如果页面还未生成静态化的页面，依然返回一个静态化地址，表示其如果未来静态化了的地址所在。</p>
      <p>该地址也是没有经过绝对化 (relativize) 处理的，直接使用在任何页面可能导致链接地址不正确。 使用属性
      channel.pageUrl 能够保证在任何页面获得一个正确的项目链接地址，一般推荐使用 #{channel.pageUrl}。
      请参见 pageUrl 属性的详细说明。</p>
      <p>使用示例：
      #{channel.staticPageUrl}，将该地址绝对化：#{channel.staticPageUrl@uri }</p>
    </td>
  </tr>

  <!-- itemName -->
  <tr>
    <td><font color='blue'>itemName</font></td>
    <td>
      <p>获得频道内项目的名字，如 '新闻', '软件' 等。该信息配置的地方在后台管理的频道信息配置中。</p>
      <p>使用示例： #{channel.itemName }, 系统标签 #{ChannelItemName } 内部使用 channel.itemName 实现。</p>
    </td>
  </tr>

  <!-- itemUnit -->
  <tr>
    <td>itemUnit</td>
    <td>
      <p>获得频道内项目的单位，如 '篇','个','幅' 等。该信息配置的地方在后台管理的频道信息配置中。</p>
      <p>使用示例： #{channel.itemUnit }, 系统标签 #{ChannelItemUnit } 内部使用 channel.itemUnit 实现。</p>
    </td>
  </tr>

  <!-- tips -->
  <tr>
    <td>tips</td>
    <td>
      <p>获得频道的简要提示，一般用于链接的 title 属性上，不支持 html 语法。
         该信息配置的地方在后台管理的频道信息配置中。</p>
      <p>使用示例： #{channel.tips }。</p>
    </td>
  </tr>
  
  <!-- description -->
  <tr>
    <td>description</td>
    <td>
      <p>获得频道的详细说明，支持 html 语法。该信息配置的地方在后台管理的频道信息配置中。</p>
      <p>使用示例： #{channel.description }。</p>
    </td>
  </tr>
  
  <!-- channelDir -->
  <tr>
    <td><font color='blue'>channelDir</font></td>
    <td>
      <p>获得频道的目录，频道的目录是频道内部核心参数之一。频道的所有内容，包括项目、栏目、专题等都放在此目录下面。
      频道的目录一旦创建之后就不能更改了(虽然您可以手工修改数据库和目录，但可能文章中含有大量链接地址不容易修改)，
      频道中项目等的地址也都是相对于频道目录地址进行计算的。该信息配置的地方在后台管理的频道信息配置中。
      </p>
      <p>使用示例： #{channel.channelDir}。系统标签 #{ChannelDir } 内部使用 channel.channelDir 实现。</p>
    </td>
  </tr>
  
  <!-- channelUrl -->
  <tr>
    <td>channelUrl</td>
    <td>
      <p>如果频道是一个外部频道，则此地址表示外部频道的链接地址。该信息配置的地方在后台管理的频道信息配置中。</p>
      <p>一般不直接使用此属性，channel.pageUrl 会根据频道是否是外部频道，返回正确的链接地址，并且能够对地址进行绝对化处理。
      </p>
      <p>使用示例： #{channel.channelUrl}。</p>
    </td>
  </tr>
  
  <!-- channelPicUrl -->
  <tr>
    <td>channelPicUrl</td>
    <td>
      <p>获得频道的图片地址，您可以为每个频道配置一个不同的图片并在页面中使用。该信息配置的地方在后台管理的频道信息配置中。</p>
      <p>使用示例： #{channel.channelPicUrl}。系统标签 #{ChannelPicUrl } 内部使用此属性实现。 </p>
    </td>
  </tr>
  
  <!-- channelOrder -->
  <tr>
    <td>channelOrder</td>
    <td>
      <p>获得频道的序列值，频道按照此顺序显示在导航条上面。该信息配置的地方在后台管理的频道信息配置，频道排序中。</p>
      <p>使用示例： #{channel.channelOrder}。</p>
    </td>
  </tr>
  
  <!-- status -->
  <tr>
    <td>status</td>
    <td>
      <p>获得频道的状态值，0 表示正常；1 表示频道被暂时关闭，禁用。其它值为内部使用的。
      正常在页面中获得的频道，这个值都是 0。除非某些标签特殊说明。
      </p>
      <p>使用示例： #{channel.status}。</p>
    </td>
  </tr>
  
  <!-- channelType -->
  <tr>
    <td>channelType</td>
    <td>
      <p>获得频道的类型，0 – 系统频道；1 – 内部频道；2 – 外部频道。
      </p>
      <p>使用示例： #{channel.channelType}。</p>
    </td>
  </tr>
  
  <!-- hitsOfHot -->
  <tr>
    <td>hitsOfHot</td>
    <td>
      <p>获得频道的设置，点击多少次算热门项目。
      </p>
      <p>使用示例： #{channel.hitsOfHot}。</p>
    </td>
  </tr>
  
  <!-- enableUploadFile -->
  <tr>
    <td>enableUploadFile</td>
    <td>
      <p>获得频道的设置，是否允许上传文件。
      </p>
      <p>使用示例： #{channel.enableUploadFile}。</p>
    </td>
  </tr>
  
  <!-- uploadDir -->
  <tr>
    <td>uploadDir</td>
    <td>
      <p>获得频道的设置，上传文件存放的路径。此路径在频道配制中可以设置，一般设置之后不要更改。
      </p>
      <p>使用示例： #{channel.uploadDir}。系统标签 #{UploadDir } 内部使用此属性实现。</p>
    </td>
  </tr>
  
  <!-- maxFileSize -->
  <tr>
    <td>maxFileSize</td>
    <td>
      <p>获得频道的设置，允许上传文件的最大大小。
      </p>
      <p>使用示例： #{channel.maxFileSize}。</p>
    </td>
  </tr>
  
  <!-- upFileType -->
  <tr>
    <td>upFileType</td>
    <td>
      <p>获得频道的设置，允许上传文件的文件类型，以 '|' 分隔的文件后缀字符串。
      </p>
      <p>使用示例： #{channel.upFileType}。</p>
    </td>
  </tr>
  
  <!-- 还有更多设置，也许没有必要说明?? -->
  
  </tbody>
</table>
  
</body>
</html>