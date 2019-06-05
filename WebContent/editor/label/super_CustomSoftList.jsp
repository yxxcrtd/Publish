<%@ page language="java" contentType="text/html; charset=gb2312" pageEncoding="UTF-8"%>
<%@page import="com.chinaedustar.publish.admin.SuperLabel"%>
<%@taglib prefix="pub" uri="/WEB-INF/publish.tld"%>
<%
  // 初始化页面数据。
  SuperLabel page_init = new SuperLabel(pageContext);
  page_init.initSuperItemPage();

%>
 
<%@ include file="../../admin/element_column.jsp" %> 
<%@ include file="element_label.jsp"%>


<!-- 主执行模板定义 -->
<pub:template name="main">
 #{call label_main_temp("ShowSoftList","下载自定义列表标签") }
</pub:template>

<script language="javascript">
function insertLabel(strLabel) {
 $("Content").focus();
 var str = document.selection.createRange();
 str.text = strLabel;
}
</script>
<pub:template name="label_property_init">
  setLabelProp("channelId");
  setLabelProp("columnId");
  setLabelProp("specialId");
  setLabelProp("includeChild");
  setLabelProp("labelDesc");
  setLabelProp("itemNum");
  setLabelProp("isHot");
  setLabelProp("isElite");
  setLabelProp("author");
  setLabelProp("dateScope");
  setLabelProp("orderType");
  setLabelProp("titleCharNum");
  setLabelProp("contentNum");
  setLabelProp("usePage");
  
  label.template = $("Content").value;
</pub:template>

<pub:template name="label_param_setting">
<table width='100%' bgcolor='white' cellspacing='1' cellpadding='1'>
 <tr class='tdbg'>
  <td height='25' width='130' class='tdbg5' align='right'><strong>所属频道：</strong></td>
  <td height='25' class='tdbg5'>
  <form action='super_CustomSoftList.jsp' method='get' name='myform' id='myform' style='margin: 0px;'>
   <select id="channelId" name='channelId' __def_value='0' onchange='document.myform.submit();'>
    <option value='0' #{iif (channelId == 0, "selected", "") }>当前频道(缺省)</option>
   #{foreach channel in channel_list }
    <option value='#{channel.id }' #{iif (channelId == channel.id, "selected", "") }>#{channel.name }</option>
   #{/foreach }
    <option value='-1' #{iif (channelId == -1, "selected", "") }>所有同类频道</option>
    <option value='-2' #{iif (channelId == -2, "selected", "") }>任何频道</option>
   </select>
  </form>
  </td>
 </tr>
#{if (channelId > 0) }
 <tr class='tdbg'>
   <td height='25' align='right' class='tdbg5'><strong>所属栏目：</strong></td>
   <td height='25'>
   <select  name='ColumnId' id="columnId" size='1' __def_value='0'>
    <option value='0' style=''>任何栏目</option>
    <option value='-1' style=''>当前栏目</option>
    #{call dropDownColumns(channelId, dropdown_columns) }
   </select> 
   <input type='checkbox' id="includeChild" name='includeChild' value='true' 
     __def_value='true' checked='checked' >包含子栏目
  </td>
  </tr>
  <tr class='tdbg'>
   <td height='25' align='right' class='tdbg5'><strong>所属专题：</strong></td>
   <td height='25'>
   <select name='SpecialId' id='specialId' __def_value='0'>
    <option value='0'>当前专题</option>
    <option value='-1'>当前频道的所有专题</option>
    <option value='-2'>不对专题考虑</option>
   #{foreach special in special_list }
    <option value='#{special.id }'>#{special.name }(#{iif (special.channelId == 0, "全站", "本频道") })</option>
   #{/foreach }
   </select>
   </td>
  </tr>
  #{else }
  <tr style="display: none;"><td></td><td>
  <input type="hidden" value="0" name="ColumnId" id="columnId" __def_value='0' />
  <input type="checkbox" name='includeChild' value='true' __def_value='true' id="includeChild" checked="checked" />
  <input type="hidden" name="SpecialId" value="0" id="specialId" __def_value='0' />
  </td>
  </tr>
  #{/if }
 <tr class='tdbg'>
   <td height='25' align='right' class='tdbg5'><strong>标签说明：</strong></td>
   <td height='25'><INPUT TYPE='text' NAME='LabelDesc' value=''
    id='labelDesc' size='15' maxlength='20'>&nbsp;&nbsp;<FONT
    style='font-size:12px' color='blue'>请在这里填写标签的使用说明方便以后的查找</FONT></td>
 </tr>
 <tr class=tdbg>
  <td width='130' class='tdbg5' align='right' height=25><STRONG>下载数：</STRONG></td>
  <td height=25><input type='text' id='itemNum' name='itemNum' value='6' __def_value='0' size="8">&nbsp;&nbsp;&nbsp;<font color='#FF0000'>如果为0，将显示所有下载。</font></td>
 </tr>
 <tr class=tdbg>
  <td width="130" class='tdbg5' align=right height=25><STRONG>下载属性：</STRONG></td>
  <td height=25 >
    <Input id=IsHot type=checkbox value=0 name=IsHot> 热门下载&nbsp;&nbsp;&nbsp;&nbsp;
    <Input id=IsElite type=checkbox value=0 name=IsElite> 推荐下载&nbsp;&nbsp;&nbsp;&nbsp;<FONT color=#ff0000>如果都不选，将显示所有文章。</FONT></td>
 </tr>
 <tr class=tdbg>
  <td width="130" class='tdbg5' align=right height=25><STRONG>显示指定作者的下载：</STRONG></td>
  <td height=25 > 
     <Input id='author'  maxLength=10 size=10 value='' name='author'>&nbsp;&nbsp;&nbsp;&nbsp;<FONT color=#ff0000>如果都不添，将不指定。</FONT>
 </tr>
 <tr class=tdbg>
  <td width="130" class='tdbg5' align=right height=25><STRONG>日期范围：</STRONG></td>
  <td height=25>只显示最近 
    <Input id='dateScope' maxLength=3 size=5 value='0' __def_value='0' name='dateScope'> 天内更新的下载&nbsp;&nbsp;&nbsp;&nbsp;<FONT color=#ff0000>&nbsp;&nbsp;如果为空或0，则显示所有天数的文章。</FONT></td>
 </tr>
 <tr class=tdbg>
  <td width="130" class='tdbg5' align=right height=25><STRONG>排序方法：</STRONG></td>
  <td height=25 >
    <Select id='orderType' name='orderType' __def_value=''> 
      <Option value='' selected>下载ID（降序）</Option> 
      <Option value='1'>下载ID（升序）</Option> 
      <Option value='2'>更新时间（降序）</Option> 
      <Option value='3'>更新时间（升序）</Option> 
      <Option value='4'>点击次数（降序）</Option> 
      <Option value='5'>点击次数（升序）</Option>
    </Select>
  </td>
 </tr>
 <tr class=tdbg>
  <td width="130"  class='tdbg5' align=right height=25><STRONG>是否分页显示：</STRONG></td>
  <td height=25 >
   <input type="radio" value="true" __def_value='false' name="usePage" id="usePage" />显示
   <input type="radio" value="false" __def_value='false' checked="checked" name="usePage" />不显示
  </td>
 </tr>
  <tr class='tdbg' style='display:none'>
   <td height='25' align='right' class='tdbg5'><strong>标题最多字符数：</strong></td>
   <td height='25'><input name='TitleCharNum' type='text' id='titleCharNum'
    value='' __def_value='' size='5' maxlength='3'> &nbsp;&nbsp;&nbsp;&nbsp;<font
    color='#FF0000'>如果为0，则显示完整标题。字母算一个字符，汉字算两个字符。</font></td>
  </tr>
  <tr class='tdbg' style='display:none'>
   <td height='25' align='right' class='tdbg5'><strong>文章内容字符数：</strong></td>
   <td height='25'><input name='ContentNum' type='text'
    id='contentNum' value='' __def_value='' size='5' maxlength='3'>
   &nbsp;&nbsp;&nbsp;&nbsp;<font color='#FF0000'>如果大于0，则在标题下方面显示指定字数的文章内容</font></td>
  </tr>
 <tr class=tdbg>
  <td width="130" class='tdbg5' align=right height=25><STRONG>循环标签支持标签：</STRONG></td>
  <td>
    <table border='0' cellpadding='0' cellspacing='0' width='100%' height='100%' align='center'>
      <tr>
       <td valign='top'><a href="javascript:insertLabel('\#{SoftUrl}')" title="软件地址">\#{SoftUrl}</a></td>
       <td valign='top'><a href="javascript:insertLabel('\#{SoftId}')" title="软件ID">\#{SoftId}</a></td>
       <td valign='top'><a href="javascript:insertLabel('\#{SoftName}')" title="软件名称">\#{SoftName}</a></td>
       <td valign='top'><a href="javascript:insertLabel('\#{SoftVersion}')" title="软件版本">\#{SoftVersion}</a></td>
       <td valign='top'><a href="javascript:insertLabel('\#{SoftProperty}')" title="软件属性（固顶、推荐等）">\#{SoftProperty}</a></td>
       <td valign='top'><a href="javascript:insertLabel('\#{SoftSize}')" title="软件大小">\#{SoftSize}</a></td>
      </tr>
      <tr>
       <td valign='top'><a href="javascript:insertLabel('\#{UpdateTime}')" title="更新时间">\#{UpdateTime}</a></td>
       <td valign='top'><a href="javascript:insertLabel('\#{CopyrightType}')" title="版权类型">\#{CopyrightType}</a></td>
       <td valign='top'><a href="javascript:insertLabel('\#{Stars}')" title="评分等级">\#{Stars}</a></td>
       <td valign='top'><a href="javascript:insertLabel('\#{SoftIntro}')" title="软件简介">\#{SoftIntro}</a></td>
       <td valign='top'><a href="javascript:insertLabel('\#{OperatingSystem}')" title="运行平台">\#{OperatingSystem}</a></td>
       <td valign='top'><a href="javascript:insertLabel('\#{SoftType}')" title="软件类型">\#{SoftType}</a></td>
      </tr>
      <tr>
       <td valign='top'><a href="javascript:insertLabel('\#{Hits}')" title="点击数">\#{Hits}</a></td>
       <td valign='top'><a href="javascript:insertLabel('\#{DayHits}')" title="显示每日点击数">\#{DayHits}</a></td>
       <td valign='top'><a href="javascript:insertLabel('\#{WeekHits}')" title="显示每周点击数">\#{WeekHits}</a></td>
       <td valign='top'><a href="javascript:insertLabel('\#{MonthHits}')" title="显示每月点击数">\#{MonthHits}</a></td>
       <td valign='top'><a href="javascript:insertLabel('\#{SoftLanguage}')" title="语言种类">\#{SoftLanguage}</a></td>
       <td valign='top'><a href="javascript:insertLabel('\#{Author}')" title="软件作者">\#{Author}</a></td>
      </tr>
      <tr>
       <td valign='top'><a href="javascript:insertLabel('\#{DemoUrl}')" title="软件演示地址">\#{DemoUrl}</a></td>
       <td valign='top'><a href="javascript:insertLabel('\#{RegUrl}')" title="软件注册地址">\#{RegUrl}</a></td>
     <td valign='top'><a href="javascript:insertLabel('\#{SoftPic}')" title="显示图片软件，width为图片宽度，height为图片高度">\#{SoftPic}</a></td>
         <td valign='top'></td>
        </tr>
        <tr>
        </tr>
      </table>
  </td>
 </tr>
 <tr class='tdbg'>
    <td width="130"  class='tdbg5' align=right height=25><STRONG>请输入循环标签Html代码：</STRONG>
    <br>
      <select name='TemplateID' onChange='document.myform.submit();'>
      <option value="0" selected>还没有列表模板！</option> 
      </select>
  </td>
  <td>
    <textarea id='Content' NAME='Content'  style='width:550px;height:200px'>&lt;!-- 在下面的循环标签里面填写图片标签及 html 代码 --&gt;
\#{Repeater}
  &lt;li&gt;&lt;a href='\#{SoftUrl}'&gt;\#{SoftTitle}&lt;/a&gt;&lt;/li&gt;
  
\#{/Repeater}
    </TEXTAREA>
  </td>
 </tr>
 <tr class='tdbg'>
  <td height='10' colspan='2' align='center'>
    <input name='Title' type='hidden' id='Title' value='下载自定义列表标签'>
    <input name='Action' type='hidden' id='Action' value='CustomListLable'>
    <input name='editLabel' type='hidden' id='editLabel' value=''>
    <input name='dChannelID' type='hidden' id='dChannelID' value='0'> 
    <input name='ModuleType' type='hidden' id='ModuleType' value='2'>
    <input name='ChannelShowType' type='hidden' id='ChannelShowType' value='" & ChannelShowType & "'> 
  </td>
 </tr>
</table>
</pub:template>

<pub:process_template name="main"/>