<%@ page language="java" contentType="text/html; charset=gb2312" pageEncoding="UTF-8"
%><!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>通用项目 item 对象介绍</title>
<link href='help.css' rel='stylesheet' type='text/css' />
</head>
<body>

<h2>通用项目 item 对象介绍</h2>

<p>通用项目 item 对象做为文章 artcile、图片 photo、下载 soft 对象的共同基对象，具有这几者的大量共同属性。
</p>
<p>在下表中列出的使用示例中对象名字都用的是 item, 如果是文章对象，则对象名字也可以使用 article，
  如果是图片对象则也可以使用 photo，如果是下载对象则也可以使用 soft，如 #{item.id }, #{article.id },
   #{photo.id }, #{soft.id }。要记得的是 item 具有的所有属性，article, photo, soft 都具有。
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
    <p>表示这个对象的标识，标识是一个整数，范围从 1 - (2^31-1)，对于一个项目类型的对象，如 article, photo, soft 
    其标识是唯一的。标识在新增项目的时候自动产生，每次递加 1，标识在产生之后不能也不会更改。
    </p>
    <p>使用示例： #{item.id}, #{article.id}。<br/>
    另外 id 属性的全名是 objectId, 使用 objectId 访问效果完全相同，如 #{item.objectId}
    </p>
    <p>标签 #{ArticleID }, #{PhotoID }, #{SoftID } 在项目页面实际就是使用 #{item.id } 来实现的。
    </td>
  </tr>
  
  <!-- objectClass -->
  <tr>
    <td>objectClass</td>
    <td>
    <p>表示一个对象的类型，对于文章 article 对象返回为 'Article'，对于图片 photo 对象返回为 'Photo'，
      对于下载 soft 对象返回为 'Soft'，使用此属性能够区分出 item 的具体类型是文章、图片或下载等。
    </p>
    <p>参见：itemClass 属性。
    </p>
    <p>使用示例： #{item.objectClass}
    </p>
    </td>
  </tr>
  
  <!-- parent -->
  <tr>
    <td>parent</td>
    <td>
    <p>得到项目对象的父对象，在当前发布系统中项目对象的父对象是栏目对象(column)。
      如果项目不属于任何栏目则返回为项目所在频道的隐含根栏目，效果等同于访问项目的 column 属性。
    </p>
    <p>使用示例： #{item.parent}<br/>
      注：父对象在某些标签中可能未从数据库中加载，访问父对象可能导致去立刻加载这些数据，从而影响性能。<br/>
      在项目的显示页面如 showArticle.jsp 等页面，父对象(所在栏目)总是预先加载的，也就是当前 column 对象，
      使用该属性不会引起性能问题。
    </p>
    </td>
  </tr>
  
  <!-- objectUuid -->
  <tr>
    <td>uuid</td>
    <td>
    <p>得到项目对象的全局唯一标识，该标识是一个 40 位的标准 GUID 格式字符串。
    此标识在所有对象，包括网站、频道、栏目等中都是唯一的。系统使用这种标识来在任何对象中唯一区分一个对象。
    </p>
    <p>使用示例： #{item.uuid}，全名：objectUuid, 如 #{item.objectUuid}
    </p>
    </td>
  </tr>
  
  <!-- name -->
  <tr>
    <td>name</td>
    <td>
    <p>项目对象当前未使用此属性。</p>
    </td>
  </tr>
  
  <!-- logo -->
  <tr>
    <td>logo</td>
    <td>
    <p>获得此项目的 Logo 图片地址。</p>
    <p>使用示例： #{item.logo }
    </p>
    </td>
  </tr>
  
  <!-- banner -->
  <tr>
    <td>banner</td>
    <td>
    <p>获得此项目的 Banner 图片地址。</p>
    <p>
     使用示例： #{item.banner }
    </p>
    </td>
  </tr>
  
  <!-- copyright -->
  <tr>
    <td>banner</td>
    <td>
    <p>获得此项目的版权声明。</p>
    <p>
     使用示例： #{item.copyright}
    </p>
    </td>
  </tr>
  
  <!-- metaKey -->
  <tr>
    <td>metaKey</td>
    <td>
    <p>获得此项目的针对搜索引擎的关键字 keywords 描述。</p>
    <p>
     使用示例： #{item.metaKey}
    </p>
    </td>
  </tr>
  
  <!-- metaDesc -->
  <tr>
    <td>metaDesc</td>
    <td>
    <p>获得此项目的针对搜索引擎的 description 描述。</p>
    <p>
     使用示例： #{item.metaDesc}
    </p>
    </td>
  </tr>
  
  <!-- templateId -->
  <tr>
    <td>templateId</td>
    <td>
    <p>获得此项目使用的显示模板标识，如果 = 0 则表示使用当前选择的缺省模板方案中该类型的缺省模板。</p>
    <p>使用示例： #{item.templateId} </p>
    <p>标签 #{TemplateId } 内部使用 item.templateId 属性实现。</p>
    </td>
  </tr>

  <!-- skinId -->
  <tr>
    <td>skinId</td>
    <td>
    <p>获得此项目使用的样式标识，如果 = 0 则表示使用当前选择的缺省模板方案中该类型的缺省样式。</p>
    <p>使用示例： #{item.skinId} </p>
    <p>标签 #{SkinId } 内部使用 item.skinId 属性实现。</p>
    </td>
  </tr>

  <!-- pageUrl -->
  <tr>
    <td><font color='blue'>pageUrl</font></td>
    <td>
    <p>获得此项目页面地址，该地址已经经过了绝对化处理，根据站点配置项目'链接地址方式'，
      如果设置为相对路径，则返回形式为 '/installDir/xxx'；如果设置为绝对路径，
      则返回形式为 'http://your-domain/installDir/xxx'。<br/>
     如果项目所在频道设置项目要生成静态页面，则返回为静态页面地址，即使该页面还未生成；否则返回为动态 'showXxx.jsp' 地址。
      </p>
    <p>使用示例： #{item.pageUrl} </p>
    <p>标签 #{ArticleUrl }, #{PhotoUrl }, #{SoftUrl } 内部使用 item.pageUrl 属性实现。</p>
    </td>
  </tr>
  
  <!-- isGenerated -->
  <tr>
    <td>isGenerated</td>
    <td>
    <p>该项目是否已经生成了静态页面的标志，= true 表示已经生成了静态页面，= false 表示还未生成静态页面。</p>
    <p>
     使用示例： #{item.isGenerated}
    </p>
    </td>
  </tr>
  
  <!-- staticPageUrl -->
  <tr>
    <td>staticPageUrl</td>
    <td>
      <p>该项目静态页面的 url 地址，这个地址是相对于频道的。
      如果页面还未生成静态化的页面，依然返回一个静态化地址，表示其如果未来静态化了的地址所在。</p>
      <p>该地址也是没有经过绝对化 (relativize) 处理的，直接使用在任何页面可能导致链接地址不正确。 使用属性
      item.pageUrl 能够保证在任何页面获得一个正确的项目链接地址，一般推荐使用 #{item.pageUrl}。
      请参见 pageUrl 属性的详细说明。</p>
      <p>使用示例：
      #{item.staticPageUrl}，将该地址在频道中绝对化：#{item.staticPageUrl@uri(channel)}</p>
    </td>
  </tr>

  <!-- channelId -->
  <tr>
    <td>channelId</td>
    <td>
      <p>获得该项目所属频道的标识。</p>
      <p>使用示例： #{item.channelId}</p>
    </td>
  </tr>
  
  <!-- columnId -->
  <tr>
    <td>columnId</td>
    <td>
      <p>获得该项目所属栏目的标识。如果该项目不属于任何栏目，则该值为 0。</p>
      <p>使用示例： #{item.columnId}</p>
    </td>
  </tr>

  <!-- itemClass -->
  <tr>
    <td>itemClass</td>
    <td>
      <p>得到项目类型。通过项目类型能够得知这个项目具体实现类，如 'article', 'photo', 'soft' 等。
      </p>
      <p>使用示例： #{item.itemClass}</p>
    </td>
  </tr>

  <!-- usn -->
  <tr>
    <td>usn</td>
    <td>
      <p>得到并发修改版本号数字，该数字每次修改项目 + 1。
      </p>
      <p>使用示例： #{item.usn}</p>
    </td>
  </tr>

  <!-- createTime -->
  <tr>
    <td>createTime</td>
    <td>
      <p>得到此项目的创建时间。（第一次插入时自动产生，以后不能修改）。
      </p>
      <p>使用示例： #{item.createTime}, #{item.createTime@format('yyyy年MM月dd日') }</p>
      <p>标签 #{CreateTime format='xxx' } 内部使用 #{item.createTime } 属性实现的。</p>
    </td>
  </tr>

  <!-- lastModified -->
  <tr>
    <td><font color='blue'>lastModified</font></td>
    <td>
      <p>得到最后修改时间。每次项目被修改的时候该值将自动更新为最后修改的时间。
      </p>
      <p>使用示例： #{item.lastModified}, #{item.lastModified@format('yyyy-MM-dd hh:mm:ss.fff') }</p>
      <p>标签 #{LastModified format='xxx' } 内部使用 #{item.lastModified } 属性实现的。</p>
    </td>
  </tr>

  <!-- status -->
  <tr>
    <td>status</td>
    <td>
      <p>得到项目状态，当前设定的状态有 -2：退稿，-1：草稿，0：未审核(缺省态)，1：审核通过。
      </p>
      <p>在发布系统大部分页面标签中所获取的项目都是审核通过的。除非某些特定标签有特定说明，否则只有审核通过的项目才会显示出来。
      </p>
      <p>使用示例： #{item.status } </p>
      <p>标签 #{Status } 内部使用 item.status 属性实现的。</p>
    </td>
  </tr>

  <!-- stars -->
  <tr>
    <td>stars</td>
    <td>
      <p>得到为此项目评定的星级。当前取值范围为 {0, 1, 2, 3, 4, 5}。
      </p>
      <p>使用示例： #{item.stars}, #{'★'@repeat(item.stars) }</p>
      <p>标签 #{Stars } 内部使用 item.stars 属性实现的。</p>
    </td>
  </tr>

  <!-- top -->
  <tr>
    <td>top</td>
    <td>
      <p>得到项目是否置顶。</p>
      <p>使用示例： #{item.top}, #{if item.top }[固顶]#{/if }</p>
      <p>标签 #{Top } 内部使用 item.top 属性实现。</p>
    </td>
  </tr>

  <!-- commend -->
  <tr>
    <td>commend</td>
    <td>
      <p>得到项目是否推荐。</p>
      <p>使用示例： #{item.commend}, #{if item.commend }<font color=red>[推荐]</font>#{/if }</p>
      <p>标签 #{Commend } 内部使用 item.commend 属性实现。 </p>
    </td>
  </tr>
  
  <!-- elite -->
  <tr>
    <td>elite</td>
    <td>
      <p>得到项目是否精华。</p>
      <p>使用示例： #{item.elite}, #{iif (item.elite, '精华', '非精华') }</p>
      <p>标签 #{Elite } 内部使用 item.elite 属性实现。</p>
    </td>
  </tr>

  <!-- hot -->
  <tr>
    <td>hot</td>
    <td>
      <p>得到项目是否热门。</p>
      <p>使用示例： #{item.hot }, #{iif (item.hot, '热', '') }</p>
      <p>标签 #{Hot } 内部使用 item.hot 属性实现。 </p>
      <p>系统提供了 top, commend, elite, hot 几种属性，实际使用时可以选择部分使用的。<br/>
      标签 #{ArticleSummary } 就是通过这几种属性实现的。</p>
    </td>
  </tr>

  <!-- deleted -->
  <tr>
    <td>deleted</td>
    <td>
      <p>得到项目是否被删除，在回收站中还未彻底销毁。</p>
      <p>使用示例： #{item.deleted } </p>
      <p>在发布系统大部分页面标签中所获取的项目都是未删除的。除非某些特定标签有特定说明，否则只有正常未删除的项目才会显示出来。</p>
      <p>标签 #{Deleted } 内部使用 item.deleted 属性实现。</p>
    </td>
  </tr>

  <!-- hits -->
  <tr>
    <td><font color='blue'>hits</font></td>
    <td>
      <p>得到项目点击总数。</p>
      <p>使用示例： #{item.hits }, #{item.hits@format('###,###,##0') }</p>
      <p>标签 #{Hits }, #{ShowHits } 内部使用 item.hits 属性实现，例如：<br/>
       &lt;script&gt;document.write('#{item.hits}');&lt;/script&gt;
    </td>
  </tr>

  <!-- title -->
  <tr>
    <td><font color='blue'>title</font></td>
    <td>
      <p>得到项目的标题，对于文章项目就是文章的标题。</p>
      <p>使用示例： #{item.title }, #{article.title@html }</p>
      <p>标签 #{Title }, #{ArticleTitle }, #{PhotoTitle }, #{SoftTitle } 内部使用 item.title 属性实现。 </p>
    </td>
  </tr>
  
  <!-- shortTitle -->
  <tr>
    <td>shortTitle</td>
    <td>
      <p>得到项目短的标题(可选使用)，对于文章项目就是文章的短标题。</p>
      <p>使用示例： #{item.shortTitle }, #{article.shortTitle@html }</p>
      <p>标签 #{ShortTitle }, #{ArticleShortTitle } 内部使用 item.shortTitle 属性实现。 </p>
    </td>
  </tr>
  
  <!-- author -->
  <tr>
    <td><font color='blue'>author</font></td>
    <td>
      <p>得到项目的原作者。</p>
      <p>使用示例： #{item.author }, #{article.author@html }</p>
      <p>标签 #{Author }, #{ArticleAuthor } 内部使用 item.author 属性实现。 </p>
    </td>
  </tr>

  <!-- source -->
  <tr>
    <td>source</td>
    <td>
      <p>得到项目的原始来源。</p>
      <p>使用示例： #{item.source }, #{article.source@html }</p>
      <p>标签 #{Source } 内部使用 item.source 属性实现。 </p>
    </td>
  </tr>

  <!-- inputer -->
  <tr>
    <td>inputer</td>
    <td>
      <p>得到项目的录入者名字。</p>
      <p>使用示例： #{item.inputer }, #{article.inputer@html }</p>
      <p>标签 #{Inputer } 内部使用 item.inputer 属性实现。 </p>
    </td>
  </tr>

  <!-- editor -->
  <tr>
    <td><font color='blue'>editor</font></td>
    <td>
      <p>得到项目的责任编辑(通常是审核者)。</p>
      <p>使用示例： #{item.editor }, #{article.editor@html }</p>
      <p>标签 #{Editor } 内部使用 item.editor 属性实现。 </p>
    </td>
  </tr>

  <!-- keywords -->
  <tr>
    <td>keywords</td>
    <td>
      <p>得到项目的关键字。</p>
      <p>使用示例： #{item.keywords }, #{article.keywords@split('|') }</p>
      <p>标签 #{Keywords } 内部使用 item.keywords 属性实现。 </p>
    </td>
  </tr>

  <!-- description -->
  <tr>
    <td>description</td>
    <td>
      <p>得到项目的简要描述，可以带有简单的 html 标记。</p>
      <p>使用示例： #{item.description }, #{article.description }</p>
      <p>标签 #{Description } 内部使用 item.description 属性实现。 </p>
    </td>
  </tr>
  
  <!-- channel -->
  <tr>
    <td>channel</td>
    <td>
      <p>得到项目的所属频道对象，和 channelId 属性不同之处是 channelId 获得的是数字标识，而 channel 属性获得的是频道对象。</p>
      <p>使用示例： #{item.channel.name }, #{article.channel.pageUrl }</p>
    </td>
  </tr>

  <!-- column -->
  <tr>
    <td>column</td>
    <td>
      <p>得到项目的所属栏目对象，和 columnId 属性不同之处是 columnId 获得的是数字标识，
        而 column 属性获得的是栏目对象，如果项目不属于任何栏目则返回为频道隐含的根栏目，其代表不属于任何栏目。</p>
      <p>使用示例： #{item.channel.name }, #{article.channel.pageUrl }</p>
    </td>
  </tr>


  
  </tbody>
</table>


</body>
</html>
