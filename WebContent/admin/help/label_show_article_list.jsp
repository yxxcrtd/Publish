<%@ page language="java" contentType="text/html; charset=gb2312" pageEncoding="UTF-8"
%><!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>ShowArticleList 标签说明</title>
<link href='help.css' rel='stylesheet' type='text/css' />
</head>
<body>

<h2>ShowArticleList 标签说明</h2>

<p>ShowArticleList 及其系列标签是发布系统中最重要的一组标签，其包括：</p>
<table width="100%" border=1 cellspacing=1 cellpadding=1>
 <thead>
  <tr>
   <th width=100>标签名</th>
   <th>简述</th>
  </tr>
 </thead>
 <tbody>
  <tr>
   <td>#{ShowArticleList}</td>
   <td>显示文章列表，一般用于显示新闻、文章的列表，在主页、文章类频道中使用较多。</td>
  </tr>
  <tr>
   <td>#{ShowSoftList}</td>
   <td>显示软件列表，一般用于显示软件、下载列表，在下载频道中使用较多。</td>
  </tr>
  <tr>
   <td>#{ShowPhotoList}</td>
   <td>显示图片列表，一般用于显示图片列表，在图片频道中使用较多。</td>
  </tr>
  <tr>
   <td>#{ShowPicArticleList}</td>
   <td>类似于 #{ShowArticleList }, 只选择带有缩略图的文章，用于显示带有缩略图的文章列表。</td>
  </tr>
  <tr>
   <td>#{FlashNews }</td>
   <td>#{ShowPicArticleList }的增强, 以特定的 flash 显示一组带有缩略图的文章。</td>
  </tr>
  <tr>
   <td>#{ShowPicSoftList }</td>
   <td>显示带有缩略图的软件列表。</td>
  </tr>
 </tbody>
</table>
<br/>

<p>  
该系列一般使用格式如下：<br/>
#{ShowArticleList 参数列表 }<br />
其中参数列表写法为 x='y' ...(多个属性以空格、或者回车换行隔开), 写法类似于 html 标记(tag)的写法。<br/>
例如： #{ShowArticleList channelId='1' columnId='0' itemNum=6 isHot=true }, 
  此标签的语义是从频道1获取6个热门项目，缺省按照id排序。以缺省形式显示出来(单列列表)。<br/><br/>
<font color='red'>说明</font>：这些属性如果取默认值，则可以不用给出，大部分标签都被设计为有合适的缺省值，
  从而可以简化写出来的标签。另外，通过标签编辑对话框编辑出来的标签，缺省也是以较短的形式给出。
</p>

<table width="100%" border=1 cellspacing=1 cellpadding=1>
 <thead>
 <tr>
  <th width='100'>属性名</th>
  <th>说明</th>
 </tr>
 </thead>
 <tbody>
 <!-- channelId -->
 <tr>
  <td>channelId</td>
  <td>
    频道标识，默认值 = 0。<br/>
   取值 = '0', 'current' 表示当前频道，当前频道指当显示某个频道的主页，或者某个栏目、文章、专题(属于频道的)，
     都具有当前频道。如果没有当前频道，则获取所有文章类型频道的。<br/>
   取值 = '-1' 表示和当前频道相同的同类频道，如果没有当前频道，作用等同于 0。<br/>
   取值 = '-2' 不属于任何频道（全站专题时）<br/>
   取值 &gt; 0 表示一个频道的标识，将从该频道获取数据。 <br/>
   例子： #{ShowArticleList channelId=1 }, #{ShowArticleList channelId=current }
  </td>
 </tr>
 
 <!-- columnId -->
 <tr>
  <td>columnId</td>
  <td>
    columnId - 栏目标识, 默认为 0。<br/>
    取值 = '0', 'current': 当前栏目，如果当前没有栏目不考虑栏目(等于取值 -1)。<br/>
    取值 = '-1', 'ignore': 不考虑栏目（获取的文章等项目可以在任何栏目）。<br/>
    取值 &gt; 0：栏目标识为该栏目。<br/>
    例子：#{ShowArticleList columnId=current }, #{ShowArticleList columnId=7 }
  </td>
 </tr>
 
 <!-- includeChild -->
 <tr>
  <td>includeChild</td>
  <td>
   includeChild - 是否包含子栏目，默认为 true。<br/>
   取值 1, true: 包含所选栏目的所有子栏目。如果没有指定栏目，则该参数不生效。<br/>
   取值 0, false：不包含。<br/>
   (此参数通常和 columnId 配合使用)     <br/>
   例子： #{ShowArticleList columnId=7 includeChild=true }
  </td>
 </tr>
 
 <!-- specialId -->
 <tr>
  <td>specialId</td>
  <td>
   specialId - 所属专题, 默认为 0。<br/>
   取值 = 0, 'current': 当前专题，如果没有当前专题则不考虑专题。<br/>
   取值 = -1：当前频道的所有专题，如果没有当前频道则不考虑专题。<br/>
   取值 = -2, 'ignore': 不对专题考虑(获取的文章等项目可以在任何专题，或不在任何专题)。<br/>
   取值 &gt; 0：专题标识为该值。 <br/>
   例子： #{ShowArticleList specialId='ignore' }, #{ShowArticleList specialId='3' }
  </td>
 </tr>
 
 <!-- itemNum -->
 <tr>
  <td>itemNum</td>
  <td>
    itemNum - 获取的项目数量。<br/>
    默认如果有当前栏目，则使用当前栏目设置的‘每页显示项目数’(一般是20)；如果没有当前栏目则使用 20。
    在主页或频道主页使用 #{ShowArticleList } 等标签的时候，为了限定获取的项目数量，一般要给出
    此参数。
  </td>
 </tr>
 
 <!-- isTop -->
 <tr>
  <td>isTop</td>
  <td>
    isTop - 是否是固顶项目，缺省为 null。<br/>
    取值 '', 'null': 不考虑是否固顶。<br/>
    取值 'true', '1': 选择固顶的项目。<br/>
    取值 'false', '0': 选择不固顶的项目。<br/>
    例子: #{ShowArticleList isTop='true' }, #{ShowArticleList isTop='false' }
  </td>
 </tr>
 
 <!-- isCommend -->
 <tr>
  <td>isCommend</td>
  <td>
   isCommend - 是否是推荐项目，缺省为 null。<br/>
    取值 '', 'null': 不考虑是否推荐。<br/>
    取值 'true', '1': 选择推荐的项目。<br/>
    取值 'false', '0': 选择不推荐的项目。<br/>
    例子: #{ShowArticleList isCommend='true' }, #{ShowArticleList isCommend='false' }
  </td>
 </tr>
 
 <!-- isElite -->
 <tr>
  <td>isElite</td>
  <td>
   isElite - 是否是精华项目，缺省为 null。<br/>
    取值 '', 'null': 不考虑是否精华。<br/>
    取值 'true', '1': 选择精华的项目。<br/>
    取值 'false', '0': 选择非精华的项目。<br/>
    例子: #{ShowArticleList isElite='true' }, #{ShowArticleList isElite='false' }
  </td>
 </tr>

 <!-- isHot -->
 <tr>
  <td>isHot</td>
  <td>
   isHot - 是否是精华项目，缺省为 null。<br/>
    取值 '', 'null': 不考虑是否热门。<br/>
    取值 'true', '1': 选择热门的项目。<br/>
    取值 'false', '0': 选择非热门的项目。<br/>
    例子: #{ShowArticleList isHot='true' }, #{ShowArticleList isHot='false' }
  </td>
 </tr>
 
 <!-- author -->
 <tr>
  <td>author</td>
  <td>
  author - 作者姓名（如果为空，则获取任何作者的；否则只获取指定作者的。默认为空。）<br/>
  例子： #{ShowArticleList author='' }, #{ShowArticleList author='李白' }
  </td>
 </tr>
 
 <!-- inputer -->
 <tr>
  <td>inputer</td>
  <td>
   inputer - 录入者名字，默认为空。<br/>
   取值为 '' 空，表示获取任何录入者的。这是默认行为。<br/>
   取值为某个值的，表示获取该录入者的项目。<br/>
   例子：#{ShowArticleList inputer='admin' }
  </td>
 </tr>
 
 <!-- orderBy -->
 <tr>
  <td>orderBy</td>
  <td>
   orderBy - 排序方式，默认为空。<br/>
   取值为 '', '0': 表示使用缺省排序，项目 id 逆序排列，id 是首次创建项目时产生的，每次+1。<br/>
   取值为 '1': 表示使用 id 顺序排列。此顺序和 '0' 正好相反。<br/>
   取值为 '2': 表示使用更新时间 (lastModified) 降序排列。最后更新时间指项目每次更新所在的时间。<br/>
   取值为 '3': 表示使用更新时间 (lastModified) 升序排列。<br/>
   取值为 '4': 表示使用点击次数 (hits) 降序排列。点击次数指项目用户总的点击数。<br/>
   取值为 '5': 表示使用点击次数 (hits) 升序排列。<br/>
   例子： #{ShowArticleList orderBy='2' }   
  </td>
 </tr>
 
 <!-- template -->
 <tr>
  <td>template</td>
  <td>
   template - 使用哪一个模板显示项目。每一个标签都有一个通用的系统内建标签 (.builtin.showxxxlist) 
     来最终显示项目，您可以自己修改、新建内建标签来显示自定义的样式。<br/>
   默认为 '', 表示使用该标签缺省的内建模板显示。<br/>
   例子： #{ShowArticleList template='.user.div_article_list' }
  </td>
 </tr>
 
 <!-- labelDesc -->
 <tr>
  <td>labelDesc</td>
  <td>
   labelDesc - 标签的说明，一般用于说明标签的用途。（系统以后可能使用此属性实现布局的功能）<br/>
   例子： #{ShowArticleList labelDesc='获取最新的10篇热门文章' isHot='true' itemNum=10}
  </td>
 </tr>
 
 <!-- colNum -->
 <tr>
  <td>colNum</td>
  <td>
   colNum - 每行列数。默认为 1 。<br/>
   取值 &gt; 1: 表示多列，每行可以显示多列项目。<br/>
   例子： #{ShowArticleList colNum=3 }, 每行显示 3 个项目。<br/>
   注：此属性是否支持取决于选用的模板，缺省模板支持此属性。
  </td>
 </tr>
 
 <!-- lastModified -->
 <tr>
  <td>lastModified</td>
  <td>
   lastModified - 显示最后更新日期的格式，缺省为 ''。<br/>
   取值 '': 表示不显示最后更新日期。<br/>
   取值为某个日期格式则表示按该格式显示最后更新时间，日期格式简列如下：<br/>
    'yyyy' - 显示4位年，'yy' - 显示2位年， 'yyyy年' - 显示数字+中文字年。<br/>
    'MM' - 显示2位月，如果月份 &lt; 10 则前面填充 0， 'M' - 显示1-2位月。<br/>
    'dd' - 显示2位日，如果日 &lt; 10 则前面填充 0， 'd' - 显示1-2位日。<br/>
    'hh' - 显示2位时，如果时 &lt; 10 则前面填充 0， 'h' - 显示1-2位时。<br/>
    'mm' - 显示2位分，如果时 &lt; 10 则前面填充 0， 'm' - 显示1-2位分。<br/>
    'ss' - 显示2位秒，如果时 &lt; 10 则前面填充 0， 's' - 显示1-2位秒。<br/>
  例子：#{ShowArticleList lastModified='yyyy-MM-dd hh:mm:ss' }, <br/>
    #{ShowArticleList lastModified='yyyy年M月d日' }
  </td>
 </tr>
 
 <!-- showColumn -->
 <tr>
  <td>showColumn</td>
  <td>
   showColumn - 是否显示所属栏目，缺省为 false。<br/>
   取值 'true', '1': 显示项目所属栏目。注意：如果选择显示，由于要加载更多栏目数据，可能导致速度较慢。<br/>
   取值 'false', '0': 不显示项目所属栏目。
  </td>
 </tr>
 
 <!-- showAuthor -->
 <tr>
  <td>showAuthor</td>
  <td>
   showAuthor - 是否显示文章作者，缺省为 false。<br/>
   取值 'true', '1': 显示文章作者。<br/>
   取值 'false', '0': 不显示文章作者。
  </td>
 </tr>
 
 <!-- showHits -->
 <tr>
  <td>showHits</td>
  <td>
   showHits - 是否显示项目点击数，缺省为 false。<br/>
   取值 'true', '1': 显示项目点击数。<br/>
   取值 'false', '0': 不显示项目点击数。<br/>
  </td>
 </tr>

 <!-- showHot -->
 <tr>
  <td>showHot</td>
  <td>
   showHot - 是否显示项目热点标志，缺省为 false。<br/>
   取值 'true', '1': 显示热点标志。<br/>
   取值 'false', '0': 不显示热点标志。<br/>
  </td>
 </tr>
 
 <!-- showNew -->
 <tr>
  <td>showNew</td>
  <td>
   showNew - 是否显示最新文章标志，缺省为 false。<br/>
   取值 'true', '1': 显示最新标志。<br/>
   取值 'false', '0': 不显示最新标志。<br/>
  </td>
 </tr>
 
 <!-- showDescription -->
 <tr>
  <td>showDescription</td>
  <td>
   showDescription - 是否显示项目简介，缺省为 false。<br/>
   取值 'true', '1': 显示项目简介。<br/>
   取值 'false', '0': 不显示项目简介。<br/>
  </td>
 </tr>
 
 <!-- showComment -->
 <tr>
  <td>showComment</td>
  <td>
   showComment - 是否显示评论链接，缺省为 false。<br/>
   取值 'true', '1': 显示评论链接。<br/>
   取值 'false', '0': 不显示评论链接。<br/>
  </td>
 </tr>
 
 <!-- openType -->
 <tr>
  <td>openType</td>
  <td>
   openType - 链接打开方式，缺省 = 0。<br/>
   取值 '', '0', '_self': 在原窗口打开。<br/>
   取值 '1', '_blank': 在新窗口打开。<br/>
   取值 'name': 在指定名字的窗口中打开。<br/>
  </td>
 </tr>
 
 <!-- picType -->
 <tr>
  <td>picType</td>
  <td>
   picType - 文章属性图片样式，普通，推荐，固顶三种图片样式<br/>
   0：不显示；1：符号；2：小图片1；3：小图片2；4：小图片3；5：小图片4；6：小图片5；7：小图片6；8：小图片7；9：小图片8；10：小图片9。
  </td>
 </tr>
 
 <!-- style -->
 <tr>
  <td>style</td>
  <td>
   style - 样式表（显示的更多风格、样式。）
  </td>
 </tr>
 
 <!-- class1 -->
 <tr>
  <td>class1</td>
  <td>
   class1 - 奇数行的样式。
  </td>
 </tr>
 
 <!-- class2 -->
 <tr>
  <td>class2</td>
  <td>
   class2 - 偶数行的样式。
  </td>
 </tr>
 
 <!-- usePage -->
 <tr>
  <td>usePage</td>
  <td>
   usePage - 是否使用分页，缺省为 false。<br/>
   使用分页将影响到获取数据的方式和显示数据的方式。如果设置 = 'true'，则获取的数据按照分页数量获取，
     而忽略掉 itemNum 属性。 同时在项目列表之后显示分页链接。<br/>
   使用分页还影响到栏目、专题在生成列表页。列表页中带有分页属性的标记能够按照生成的列表页页次正确的获取出该页的数据。
   未设置分页属性的仍然获取的是最前面的 itemNum 个项目。
  </td>
 </tr>
 
 <!-- ShowPicArticleList, FlashNews,   -->
 <tr>
  <td colspan='2'>
   #{ShowPicArticleList }, #{FlashNews }, #{ShowPicSoftList } 支持的属性。
  </td>
 </tr>
 
 <!-- picWidth -->
 <tr>
  <td>picWidth</td>
  <td>
   picWidth - 图形宽度，缺省为 240。<br/>
   如果未给出则使用缺省值，和 picHeight 共同使用时，如果单独给出 picWidth 则可以固定图片宽度，高度不限。
  </td>
 </tr>

 <!-- picHeight -->
 <tr>
  <td>picHeight</td>
  <td>
   picHeight - 图形高度，缺省为 180。<br/>
   如果未给出则使用缺省值，和 picWidth 共同使用时，如果单独给出 picHeight 则可以固定图片高度，而宽度不限。
  </td>
 </tr>
 
 </tbody>
</table>

<p>
注：<br/>
1) 显示的属性如 'picType', 'class1' 等受支持的模板影响，可能不完全支持。<br/>
2) 
</p>

</body>
</html>
