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
 #{call label_main_temp("ShowPhotoList", "图片自定义列表标签") }
</pub:template>

<script language="javascript">
function insertLabel(strLabel) {
 $("Content").focus();
 var str = document.selection.createRange();
 str.text = strLabel;
}
</script>

<pub:template name="label_property_init">
  setLabelProp('channelId');
  setLabelProp('columnId');
  setLabelProp('specialId');
  setLabelProp('includeChild');
  setLabelProp('labelDesc');
  setLabelProp('itemNum');
  setLabelProp('isHot');
  setLabelProp('isElite');
  setLabelProp('author');
  setLabelProp('dateScope');
  setLabelProp('orderType');
  setLabelProp('usePage');
  setLabelProp('titleCharNum');
  setLabelProp('contentNum');
  setLabelProp('colNum');
  label.template = $('Content').value;
</pub:template>


<pub:template name="label_param_setting">
<table width='100%' bgcolor='white' cellspacing='1' cellpadding='1'>
  <tr class='tdbg'>
   <td height='25' width='130' class='tdbg5' align='right'><strong>所属频道：</strong></td>
   <td height='25' class='tdbg5'>
  <form action='super_CustomPhotoList.jsp' method='get' name='myform' id='myform' style='margin: 0px;'>
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
    <td width='130' class='tdbg5' align='right' height=25><STRONG>图片数：</STRONG></td>
    <td height=25><input type='text' id='itemNum' name='ItemNum' value='0' __def_value='0' size="5" />
     &nbsp;&nbsp;&nbsp;
     每行 <input type='text' id='colNum' name='colNum' value='1' __def_value='1' size='5' /> 个图片。
    </td>
  </tr>
  <tr class=tdbg>
    <td width="130" class='tdbg5' align=right height=25><STRONG>图片属性：</STRONG></td>
    <td height=25 >
    <input name='IsHot' type='checkbox' id='isHot' value='true' __false_value='' __def_value='' />
      热门图片&nbsp;&nbsp;&nbsp;&nbsp;
    <input name='IsElite' type='checkbox' id='isElite' value='0' __false_value='' __def_value='' />
      推荐图片&nbsp;&nbsp;&nbsp;&nbsp;<font color='#FF0000'>如果都不选，将显示所有图片。</font>
  </tr>
  <tr class=tdbg>
    <td width="130" class='tdbg5' align=right height=25><STRONG>显示指定作者的图片：</STRONG></td>
    <td height=25 > 
     <input id='author' maxLength=10 size=10 value='' __def_value='' name='Author' />&nbsp;&nbsp;&nbsp;&nbsp;
      <font color=#ff0000>如果填写作者名字，将查找该作者的图片，用于做个人图片集。</font>
  </tr>
  <tr class=tdbg>
    <td width="130" class='tdbg5' align=right height=25><STRONG>日期范围：</STRONG></td>
    <td height=25>只显示最近 
      <input id='dateScope' maxLength='3' size='5' value='0' __def_value='0' name='DateScope' /> 
        天内更新的图片&nbsp;&nbsp;&nbsp;&nbsp;<font color='#ff0000'>&nbsp;&nbsp;如果为空或0，则显示所有天数的图片。</FONT>
    </td>
  </tr>
  <tr class=tdbg>
    <td width="130" class='tdbg5' align=right height=25><STRONG>排序方法：</STRONG></td>
    <td height=25 >
      <Select id='orderType' name='OrderType' __def_value=''> 
        <Option value=''>图片ID（降序）</Option> 
        <Option value='1'>图片ID（升序）</Option> 
        <Option value='2'>更新时间（降序）</Option> 
        <Option value='3'>更新时间（升序）</Option> 
        <Option value='4'>点击次数（降序）</Option> 
        <Option value='5'>点击次数（升序）</Option>
      </Select>
    </td>
  </tr>
  <tr class=tdbg>
    <td width="130" class='tdbg5' align=right height=25><STRONG>是否分页显示：</STRONG></td>
    <td height=25 >
      <input type="radio" name="usePage" checked value="true" __def_value='true' />是
      <input type="radio" name="usePage" value="false" __def_value='true' />否
     </td> 
  </tr>
  <tr class=tdbg>
    <td width="130"  class='tdbg5' align=right height=25><STRONG>图片标题最多字符：</STRONG></td>
    <td height=25 >
      <input maxLength='3' size='5' value='' __def_value='' id='titleCharNum' name='TitleCharNum' />&nbsp;&nbsp;&nbsp;&nbsp;
      <font color='#ff0000'>一个汉字=两个英文字符，为0时全部显示</font>
     </td>
  </tr>
  <tr class=tdbg>
    <td width="130" class='tdbg5' align=right height=25><STRONG>图片内容最多字符：</STRONG></td>
    <td height=25>
      <input maxLength='3' size='5' value='' __def_value='' id='contentNum' name='ContentNum' />&nbsp;&nbsp;&nbsp;&nbsp;
      <font color='red'>图片内容介绍显示的文字数量，缺省为全部显示</font>
    </td>
  </tr>
  <tr class=tdbg>
    <td width="130" class='tdbg5' align=right height=25><STRONG>循环标签支持标签：</STRONG></td>
    <td>
      <table border='0' cellpadding='0' cellspacing='0' width='100%' height='100%' align='center'>
        <tr>
         <td valign='top'><a href="javascript:insertLabel('\#{PhotoID}')" title="图片ID, 也可以使用 \#{photo.id}">\#{PhotoID}</a></td>
         <td valign='top'><a href="javascript:insertLabel('\#{PhotoUrl}')" title="图片地址">\#{PhotoUrl}</a></td>
         <td valign='top'><a href="javascript:insertLabel('\#{LastModified}')" title="更新时间">\#{LastModified}</a></td>
         <td valign='top'><a href="javascript:insertLabel('\#{Stars}')" title="评分等级">\#{Stars}</a></td>
         <td valign='top'><a href="javascript:insertLabel('\#{Author}')" title="图片作者, = \#{photo.author}">\#{Author}</a></td>
         <td valign='top'><a href="javascript:insertLabel('\#{Source}')" title="图片来源, = \#{photo.source}">\#{Source}</a></td>
        </tr>
        <tr>
         <td valign='top'><a href="javascript:insertLabel('\#{Hits}')" title="图片的点击数">\#{Hits}</a></td>
         <td valign='top'><a href="javascript:insertLabel('\#{Inputer}')" title="图片的录入作者">\#{Inputer}</a></td>
         <td valign='top'><a href="javascript:insertLabel('\#{Editor}')" title="图片的编辑者">\#{Editor}</a></td>
         <td valign='top'><a href="javascript:insertLabel('\#{Keywords}')" title="图片关键字">\#{Keywords}</a></td>
         <td valign='top'><a href="javascript:insertLabel('\#{photo.top}')" title="显示固顶">\#{photo.top}</a></td>
         <td valign='top'><a href="javascript:insertLabel('\#{photo.commend}')" title="显示推荐">\#{photo.commend}</a></td>
        </tr>
        <tr>
         <td valign='top'><a href="javascript:insertLabel('\#{photo.hot}')" title="显示热门">\#{photo.hot}</a></td>
         <td valign='top'><a href="javascript:insertLabel('\#{PhotoThumb}')" title="显示图片的缩略图">\#{PhotoThumb}</a></td>
         <td valign='top'><a href="javascript:insertLabel('\#{PhotoDayHits}')" title="显示当日点击数">\#{DayHits}</a></td>
         <td valign='top'><a href="javascript:insertLabel('\#{PhotoWeekHits}')" title="显示本周点击数">\#{WeekHits}</a></td>
         <td valign='top'><a href="javascript:insertLabel('\#{PhotoMonthHits}')" title="显示本月点击数">\#{MonthHits}</a></td>
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
    <!-- 
    <select name='TemplateID' onChange='document.myform.submit();'>
      <option value="0" selected>还没有列表模板！</option> 
    </select>
     -->
    </td>
    <td>
      <textarea id='Content' name='Content' style='width:550px;height:200px'>&lt;!-- 在下面的循环标签里面填写图片标签及 html 代码 --&gt;
\#{Repeater}
  &lt;li&gt;&lt;a href='\#{PhotoUrl}'&gt;\#{PhotoTitle}&lt;/a&gt;&lt;/li&gt;
  
\#{/Repeater}
</textarea>
    </td>
  </tr>
</table>
</pub:template>

<pub:process_template name="main"/>
