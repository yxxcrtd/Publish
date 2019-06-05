<%@ page language="java" contentType="text/html; charset=gb2312" pageEncoding="UTF-8"
%><!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>下载对象介绍</title>
<link href='help.css' rel='stylesheet' type='text/css' />
</head>
<body>

<h2>下载对象介绍</h2>

<p>下载对象 soft 以 item 对象为基类，item 对象所具有的属性和支持的标签 soft 也都全部支持。
</p>
<p>下表列出的是 soft 除了具有 item 对象的属性之外还具有的属性，<a 
    href='object_model_item.jsp'>点击这里查看 item 对象具有的属性</a>，
    仍要记得 item 具有的所有属性 soft 都全部具有。
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
    <p>thumbPic 获得该下载的缩略图地址。该地址是相对于频道目录的，一般不直接使用。<br/>
      thumbPicAbs 获得该下载缩略图的绝对化地址。</p>
    <p>使用示例： #{soft.thumbPic}, #{item.thumbPic@uri(channel) }, #{soft.thumbPicAbs }。</p>
    </td>
  </tr>
  
  <!-- copyrightType -->
  <tr>
    <td width="240">copyrightType</td>
    <td>
    <p>获取版权类型。比如“免费版”。</p>
    <p>使用示例： #{soft.copyrightType}。</p>
    </td>
  </tr>
  
  <!-- decompPwd -->
  <tr>
    <td width="240">decompPwd</td>
    <td>
    <p>获取解压密码。</p>
    <p>使用示例： #{soft.decompPwd}。</p>
    </td>
  </tr>

  <!-- demoUrl -->
  <tr>
    <td width="240">demoUrl</td>
    <td>
    <p>获取用来演示软件的URL地址。</p>
    <p>使用示例： #{soft.demoUrl}。</p>
    </td>
  </tr>
  
  <!-- registUrl -->
  <tr>
    <td width="240">registUrl</td>
    <td>
    <p>获取软件注册地址。</p>
    <p>使用示例： #{soft.registUrl}。</p>
    </td>
  </tr>
  
  <!-- language -->
  <tr>
    <td width="240">language</td>
    <td>
    <p>设置软件使用的语言。比如“简体中文”。</p>
    <p>使用示例： #{soft.language}。</p>
    </td>
  </tr>
  
  <!-- OS -->
  <tr>
    <td width="240">OS</td>
    <td>
    <p>获取适用的操作系统。</p>
    <p>使用示例： #{soft.OS}。</p>
    </td>
  </tr>

  <!-- size -->
  <tr>
    <td width="240">size</td>
    <td>
    <p>获取软件大小。以K为单位。</p>
    <p>使用示例： #{soft.size}。</p>
    </td>
  </tr>

  <!-- type -->
  <tr>
    <td width="240">type</td>
    <td>
    <p>获取软件类型。比如“国产软件”。</p>
    <p>使用示例： #{soft.type}。</p>
    </td>
  </tr>

  <!-- version -->
  <tr>
    <td width="240">version</td>
    <td>
    <p>获取软件的版本。比如“2.0”。</p>
    <p>使用示例： #{soft.version}。</p>
    </td>
  </tr>


  
  <!-- downloadUrlList -->
  <tr>
    <td width="240">downloadUrlList</td>
    <td>
    <p>获得这个下载的所有下载列表，其数据模型为返回一个下载项的数组，其中每个下载项包括 name 和 url 两个属性。<br/>
       ArrayList[UrlEntry]<br/>
       &nbsp;&nbsp;&nbsp; - name<br/>
       &nbsp;&nbsp;&nbsp; - url<br/>
       其中地址已经经过了计算，指向合适的下载地址。
      </p>
    <p>使用示例： </p>
    <pre>
    #{foreach addr in soft.downloadUrlList }    // 循环每个下载项
      &lt;li&gt; #{addr.name }, #{addr.url }        // 输出每个下载项名字和 URL 地址。
    #{/foreach }
    </pre>
    <pre>
     #{soft.downloadUrlList@size }             // 获得下载项的数目。
    </pre>
    </td>
  </tr>
  
  <!-- dayHits -->
  <tr>
    <td width="240">dayHits</td>
    <td>
    <p>获得该下载的日点击数。</p>
    <p>使用示例： #{soft.dayHits }。</p>
    </td>
  </tr>
  
  <!-- weekHits -->
  <tr>
    <td width="240">weekHits</td>
    <td>
    <p>获得该下载的周点击数。</p>
    <p>使用示例： #{soft.weekHits }。</p>
    </td>
  </tr>

  <!-- monthHits -->
  <tr>
    <td width="240">monthHits</td>
    <td>
    <p>获得该下载的月点击数。</p>
    <p>使用示例： #{soft.monthHits }。</p>
    </td>
  </tr>
  
  <!-- lastHitTime -->
  <tr>
    <td width="240">lastHitTime</td>
    <td>
    <p>获得该下载最后点击时间。</p>
    <p>使用示例： #{soft.lastHitTime }, #{soft.lastHitTime@format('MM-dd hh:mm')}。</p>
    </td>
  </tr>
  
  <!-- prevSoft -->
  <tr>
    <td width="240">prevSoft</td>
    <td>
    <p>获得该下载前一个下载。</p>
      <p>使用示例： #{soft.prevSoft}</p>
  <pre>
    #{PrevSoft } 
       // 注意：在 PrevSoft 标签对之内，soft 对象被替换为当前下载对象的前一个下载，在
       // #{/PrevSoft } 之后，soft 重新恢复为原来的下载对象。
      #{soft.pageUrl }, #{soft.title }, #{soft.version }
    #{/PrevSoft }
  </pre>
    </td>
  </tr>

  <!-- nextSoft -->
  <tr>
    <td width="240">nextSoft</td>
    <td>
    <p>获得该下载后一个下载。</p>
      <p>使用示例： #{soft.nextSoft}，另参考 prevSoft 的示例。</p>
    </td>
  </tr>

  <!-- prevColumnSoft -->
  <tr>
    <td width="240">prevColumnSoft</td>
    <td>
    <p>获得该下载同栏目的前一个下载。</p>
      <p>使用示例： #{soft.prevColumnSoft}，另参考 prevSoft 的示例。</p>
    </td>
  </tr>

  <!-- nextColumnSoft -->
  <tr>
    <td width="240">nextColumnSoft</td>
    <td>
    <p>获得该下载同栏目的后一个下载。</p>
      <p>使用示例： #{soft.nextColumnSoft}，另参考 prevSoft 的示例。</p>
    </td>
  </tr>
  
  </tbody>
</table>

</body>
</html>
