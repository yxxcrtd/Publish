<%@ page language="java" contentType="text/html; charset=gb2312" pageEncoding="UTF-8"
%><!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>公告相关标签</title>
<link href='help.css' rel='stylesheet' type='text/css' />
</head>
<body>

<h2>公告相关标签</h2>

<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td width="*" valign="top">
    <p>
    公告标签分为两种，一种是公告属性标签，一种是显示公告列表标签。
    其中公告属性标签只能用在显示公告内容的页面，显示公告列表标签可以用在任何页面。
    下面详细说明各个标签。
    </p>
    
    <!-- AnnounceTitle -->
    <table id='label_table' cellSpacing=1 cellPadding=1 width="100%" border=0>
      <tr>
        <th width='160'>标签名：</th>
        <th>#{AnnounceTitle}</th>
      </tr>
      <tr>
        <td>作用：</td>
        <td>显示当前公告的标题。</td>
      </tr>
      <tr>
        <td>等效写法：</td>
        <td>#{announce.title }</td>
      </tr>
    </table>

    <!-- AnnounceContent -->
    <table id='label_table' cellSpacing=1 cellPadding=1 width="100%" border=0>
      <tr>
        <th width='160'>标签名：</th>
        <th>#{AnnounceContent}</th>
      </tr>
      <tr>
        <td>作用：</td>
        <td>显示当前公告的内容。</td>
      </tr>
      <tr>
        <td>等效写法：</td>
        <td>#{announce.content }</td>
      </tr>
    </table>

    <!-- AnnounceAuthor -->
    <table id='label_table' cellSpacing=1 cellPadding=1 width="100%" border=0>
      <tr>
        <th width='160'>标签名：</th>
        <th>#{AnnounceAuthor}</th>
      </tr>
      <tr>
        <td>作用：</td>
        <td>显示当前公告的作者。</td>
      </tr>
      <tr>
        <td>等效写法：</td>
        <td>#{announce.author }</td>
      </tr>
    </table>

    <!-- AnnounceCreateDate -->
    <table id='label_table' cellSpacing=1 cellPadding=1 width="100%" border=0>
      <tr>
        <th width='160'>标签名：</th>
        <th>#{AnnounceCreateDate}</th>
      </tr>
      <tr>
        <td>作用：</td>
        <td>显示当前公告的创建日期。 支持带有一个属性 format。</td>
      </tr>
      <tr>
        <td>等效写法：</td>
        <td>#{announce.createDate }</td>
      </tr>
      <tr>
        <td>示例：</td>
        <td>#{AnnounceCreateDate format='yyyy-MM-dd'}, #{announce.createDate@format('yy年MM月dd日') }</td>
      </tr>
    </table>

    <!-- AnnounceOffDate -->
    <table id='label_table' cellSpacing=1 cellPadding=1 width="100%" border=0>
      <tr>
        <th width='160'>标签名：</th>
        <th>#{AnnounceOffDate}</th>
      </tr>
      <tr>
        <td>作用：</td>
        <td>显示当前公告的过期日期。 支持带有一个属性 format。</td>
      </tr>
      <tr>
        <td>等效写法：</td>
        <td>#{announce.offDate }</td>
      </tr>
      <tr>
        <td>示例：</td>
        <td>#{AnnounceOffDate format='yyyy-MM-dd'}, #{announce.offDate@format('yy年MM月dd日') }</td>
      </tr>
    </table>
    
    <!-- AnnounceIsSelected -->
    <table id='label_table' cellSpacing=1 cellPadding=1 width="100%" border=0>
      <tr>
        <th width='160'>标签名：</th>
        <th>#{AnnounceIsSelected}</th>
      </tr>
      <tr>
        <td>作用：</td>
        <td>显示当前公告是否被选中。</td>
      </tr>
      <tr>
        <td>等效写法：</td>
        <td>#{announce.isSelected }</td>
      </tr>
    </table>

    <!-- AnnounceChannelId -->
    <table id='label_table' cellSpacing=1 cellPadding=1 width="100%" border=0>
      <tr>
        <th width='160'>标签名：</th>
        <th>#{AnnounceChannelId}</th>
      </tr>
      <tr>
        <td>作用：</td>
        <td>显示当前公告所属的频道标识。</td>
      </tr>
      <tr>
        <td>等效写法：</td>
        <td>#{announce.channelId }</td>
      </tr>
    </table>
    
    <!-- AnnounceShowType -->
    <table id='label_table' cellSpacing=1 cellPadding=1 width="100%" border=0>
      <tr>
        <th width='160'>标签名：</th>
        <th>#{AnnounceShowType}</th>
      </tr>
      <tr>
        <td>作用：</td>
        <td>显示当前公告的显示方式，1 - 滚动式，2 - 弹出式。</td>
      </tr>
      <tr>
        <td>等效写法：</td>
        <td>#{announce.showType }</td>
      </tr>
    </table>

    <!-- AnnounceOutTime -->
    <table id='label_table' cellSpacing=1 cellPadding=1 width="100%" border=0>
      <tr>
        <th width='160'>标签名：</th>
        <th>#{AnnounceOutTime}</th>
      </tr>
      <tr>
        <td>作用：</td>
        <td>显示当前公告的过期天数，单位为天。</td>
      </tr>
      <tr>
        <td>等效写法：</td>
        <td>#{announce.outTime }</td>
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
        <td>显示公告栏。</td>
      </tr>
      <tr>
        <td>参数：</td>
        <td>
         channelId - 获取该频道的公告，缺省为 0 表示是当前频道，如果没有当前频道，则获取的是网站的公告。
    <br/>num - 获取的公告数量，缺省为 5 个。
    <br/>showAuthor - 是否显示公告作者。
    <br/>showDate - 是否显示公告发布日期。
    <br/>contentLen - 显示内容的最大长度，缺省为 50。
        </td>
      </tr>
      <tr>
        <td>示例：</td>
        <td>#{ShowAnnounce channelId='0' num='6' showDate='true' }</td>
      </tr>
      <tr>
        <td>定制：</td>
        <td>
          <p>#{ShowAnnounce} 标签内部使用 '.builtin.showannounce' 来显示公告内容，
           可以通过设计自己的公告显示模板并在 #{ShowAnnounce template='your_template_name' } 
            中指定所使用模板。</p>
          <p>#{ShowAnnounce } 也支持使用 #{ShowAnnounce } ... #{/ShowAnnounce } 标签对，
            其中可以访问的变量为 'announce_list', 'options'，分别表示公告列表和标签选项。
            如果在内部使用 #{Repeater } 标签，则循环变量对象为 'announce'，表示一个公告。
            示例如下：
          </p>
      <pre>
       #{ShowAnnounce channelId='1' num='6' }
        #{Repeater }
         li. announce = #{announce.title }, #{announce.id }
        #{/Repeater }
        #{foreach announce in announce_list }
         li. announce = #{announce.title }, #{announce.createDate@format }
        #{/foreach }
       #{/ShowAnnounce}
      </pre>
        </td>
      </tr>
    </table>
    
    <!-- ShowAnnounceList -->
    <table id='label_table' cellSpacing=1 cellPadding=1 width="100%" border=0>
      <tr>
        <th width='160'>标签名：</th>
        <th>#{ShowAnnounceList}</th>
      </tr>
      <tr>
        <td>作用：</td>
        <td>显示公告列表。(和 ShowAnnounce 相比区别不大，也许以后改进 ShowAnnounce)</td>
      </tr>
      <tr>
        <td>参数：</td>
        <td>
         channelId - 频道ID， 0 表示全站公告，-1表示所有公告。默认：-1
         <br/>num - 获取的公告数量。
         <br/>titleCharNum - 标题的字符数。
         <br/>createDate - 显示发布日期的格式，缺省为 'yyyy-MM-dd hh:mm:ss'
        </td>
      </tr>
      <tr>
        <td>定制</td>
        <td>
          <p>#{ShowAnnounceList} 标签内部使用 '.builtin.showannouncelist' 来显示公告内容，
           可以通过设计自己的公告显示模板并在 #{ShowAnnounceList template='your_template_name' } 
            中指定所使用模板。</p>
          <p>#{ShowAnnounceList } 也支持使用 #{ShowAnnounceList } ... #{/ShowAnnounceList } 标签对，
            其中可以访问的变量为 'announce_list', 'options'，分别表示公告列表和标签选项。
            如果在内部使用 #{Repeater } 标签，则循环变量对象为 'announce'，表示一个公告。
            示例请参考 #{ShowAnnounce } 的说明。
          </p>
        </td>
      </tr>
    </table>
    

    </td>
  </tr>
</table>

</body>
</html>
