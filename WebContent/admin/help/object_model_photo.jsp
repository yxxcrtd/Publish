<%@ page language="java" contentType="text/html; charset=gb2312" pageEncoding="UTF-8"
%><!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>图片对象介绍</title>
<link href='help.css' rel='stylesheet' type='text/css' />
</head>
<body>

<h2>图片对象介绍</h2>

<p>图片对象 photo 以 item 对象为基类，item 对象所具有的属性和支持的标签 photo 也都全部支持。
</p>
<p>下表列出的是 photo 除了具有 item 对象的属性之外还具有的属性，<a 
    href='object_model_item.jsp'>点击这里查看 item 对象具有的属性</a>，
    仍要记得 item 具有的所有属性 photo 都全部具有。
</p>

<table id='label_table' width="100%" border="0" cellspacing="1" cellpadding="2" align='center'>
  <thead>
  <tr>
    <th width="240">属性名</th>
    <th width='*'>详细说明</th>
  </tr>
  </thead>
  <tbody>
  <!-- thumbPic -->
  <tr>
    <td width="240">thumbPic, thumbPicAbs</td>
    <td>
    <p>thumbPic 获得该图片的缩略图地址。该地址是相对于频道目录的，一般不直接使用。<br/>
      thumbPicAbs 获得该图片缩略图的绝对化地址。</p>
    <p>使用示例： #{photo.thumbPic}, #{item.thumbPic@uri(channel) }, #{photo.thumbPicAbs }。</p>
    </td>
  </tr>
  
  <!-- pictureList -->
  <tr>
    <td width="240">pictureList</td>
    <td>
    <p>获得这个图片的所有图片列表，其数据模型为返回一个图片项的数组，其中每个图片项包括 name 和 url 两个属性。<br/>
       ArrayList[UrlEntry]<br/>
       &nbsp;&nbsp;&nbsp; - name<br/>
       &nbsp;&nbsp;&nbsp; - url<br/>
       其中地址已经经过了计算，指向合适的图片地址。
      </p>
    <p>使用示例： </p>
    <pre>
    #{foreach pic in photo.pictureList }    // 循环每个图片项
      &lt;li&gt; #{pic.name }, #{pic.url }        // 输出每个图片项名字和 URL 地址。
    #{/foreach }
    </pre>
    <pre>
     #{photo.pictureList@size }             // 获得图片项的数目。
    </pre>
    </td>
  </tr>
  
  <!-- dayHits -->
  <tr>
    <td width="240">dayHits</td>
    <td>
    <p>获得该图片的日点击数。</p>
    <p>使用示例： #{photo.dayHits }。</p>
    </td>
  </tr>
  
  <!-- weekHits -->
  <tr>
    <td width="240">weekHits</td>
    <td>
    <p>获得该图片的周点击数。</p>
    <p>使用示例： #{photo.weekHits }。</p>
    </td>
  </tr>

  <!-- monthHits -->
  <tr>
    <td width="240">monthHits</td>
    <td>
    <p>获得该图片的月点击数。</p>
    <p>使用示例： #{photo.monthHits }。</p>
    </td>
  </tr>
  
  <!-- lastHitTime -->
  <tr>
    <td width="240">lastHitTime</td>
    <td>
    <p>获得该图片最后点击时间。</p>
    <p>使用示例： #{photo.lastHitTime }, #{photo.lastHitTime@format('MM-dd hh:mm')}。</p>
    </td>
  </tr>
  
  <!-- prevPhoto -->
  <tr>
    <td width="240">prevPhoto</td>
    <td>
    <p>获得该图片前一张图片。</p>
      <p>使用示例： #{photo.prevPhoto}</p>
  <pre>
    #{PrevPhoto } 
       // 注意：在 PrevPhoto 标签对之内，photo 对象被替换为当前图片对象的前一幅图片，在
       // #{/PrevPhoto } 之后，photo 重新恢复为原来的图片对象。
      #{photo.pageUrl }, #{photo.title }, #{photo.thumbPicAbs }
    #{/PrevPhoto }
  </pre>
    </td>
  </tr>

  <!-- nextPhoto -->
  <tr>
    <td width="240">nextPhoto</td>
    <td>
    <p>获得该图片后一张图片。</p>
      <p>使用示例： #{photo.nextPhoto}，另参考 prevPhoto 的示例。</p>
    </td>
  </tr>

  <!-- prevColumnPhoto -->
  <tr>
    <td width="240">prevColumnPhoto</td>
    <td>
    <p>获得该图片同栏目的前一张图片。</p>
      <p>使用示例： #{photo.prevColumnPhoto}，另参考 prevPhoto 的示例。</p>
    </td>
  </tr>

  <!-- nextColumnPhoto -->
  <tr>
    <td width="240">nextColumnPhoto</td>
    <td>
    <p>获得该图片同栏目的后一张图片。</p>
      <p>使用示例： #{photo.nextColumnPhoto}，另参考 prevPhoto 的示例。</p>
    </td>
  </tr>
  
  </tbody>
</table>

</body>
</html>
