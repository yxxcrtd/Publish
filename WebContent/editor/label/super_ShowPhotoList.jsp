<%@ page language="java" contentType="text/html; charset=gb2312" pageEncoding="UTF-8"
%><%@page import="com.chinaedustar.publish.admin.SuperLabel"
%><%@taglib prefix="pub" uri="/WEB-INF/publish.tld"
%><%
  // 初始化页面数据。
  SuperLabel page_init = new SuperLabel(pageContext);
  page_init.initSuperItemPage();

%>
 
<%@ include file="../../admin/element_column.jsp" %> 
<%@ include file="element_label.jsp"%>

<!-- 主执行模板定义 -->
<pub:template name="main">
 #{call label_main_temp("ShowPhotoList","图片列表函数标签") }
 
<script Language="JavaScript">
function change_item(element){
  if(element.selectedIndex!=-1)
  var selectednumber = element.options[element.selectedIndex].value;

  if(selectednumber==1){
    objFiles.style.display="";
      
    $("common").src = "../../images/photo/photo_common.gif"
    $("elite").src = "../../images/photo/photo_elite.gif"
    $("ontop").src = "../../images/photo/photo_ontop.gif"
  } else if (selectednumber==0) {
    objFiles.style.display="none";
  } else if (selectednumber==2) {
    objFiles.style.display="none";
  } else if (selectednumber>=3) {
    selectednumber = selectednumber - 1
    objFiles.style.display="";
        
    $("common").src = "../../images/photo/photo_common" + selectednumber + ".gif"
    $("elite").src = "../../images/photo/photo_elite" + selectednumber + ".gif"
    $("ontop").src = "../../images/photo/photo_ontop" + selectednumber + ".gif"
  }
}
</script>
</pub:template>

<pub:template name="label_property_init">
  setLabelProp('channelId');
  setLabelProp('columnId');
  setLabelProp('specialId');
  setLabelProp('includeChild');
  setLabelProp('labelDesc');
  setLabelProp('showType');
  setLabelProp('itemNum');
  setLabelProp('isTop');
  setLabelProp('isCommend');
  setLabelProp('isElite');
  setLabelProp('isHot');
  setLabelProp('author');
  setLabelProp('picType');
  setLabelProp('dateScope');
  setLabelProp('orderType');
  setLabelProp('titleCharNum');
  setLabelProp('contentNum');
  setLabelProp('cols');
  setLabelProp('showColumn');
  setLabelProp('showAuthor');
  setLabelProp('lastModified');
  setLabelProp('showHits');
  setLabelProp('showHot');
  setLabelProp('showNew');
  setLabelProp('showTips');
  setLabelProp('usePage');
  setLabelProp('openType');
  setLabelProp('cssName');
  setLabelProp('cssName1');
  setLabelProp('cssName2');
</pub:template>


<pub:template name="label_param_setting">
<table width='100%' bgcolor='white' cellspacing='1' cellpadding='1'>
  <tr class='tdbg'>
   <td height='25' align='right' class='tdbg5'><strong>所属频道：</strong></td>
   <td height='25'>
  <form action='super_ShowPhotoList.jsp' method='get' name='myform' id='myform' style='margin:0px'>
   <select id='channelId' name='channelId' __def_value='0' onchange='document.myform.submit();'>
    <option value='0' #{iif (channelId == 0, "selected", "") }>当前频道(缺省)</option>
   #{foreach channel in channel_list }
    <option value='#{channel.id }' #{iif (channelId == channel.id, "selected", "") }>#{channel.name}</option>
   #{/foreach }
    <option value='-1' #{iif (channelId == -1, "selected", "") }>当前频道的所有同类频道</option>
    <option value='-2' #{iif (channelId == -2, "selected", "") }>任何频道</option>
   </select>
  </form>
   </td>
  </tr>
  #{if (channelId > 0) }
  <tr class='tdbg'>
   <td height='25' align='right' class='tdbg5'><strong>所属栏目：</strong></td>
   <td height='25'>
   <select name='columnId' id="columnId" __def_value='0' size='1'>
    <option value='0' style=''>任何栏目</option>
    <option value='-1' style=''>当前栏目</option>
    #{call dropDownColumns(channelId, dropdown_columns) }
   </select> 
   <input type='checkbox' id='includeChild' name='includeChild' __def_value='true' 
     value='true' checked='checked' />包含子栏目
  </td>
  </tr>
  <tr class='tdbg'>
   <td height='25' align='right' class='tdbg5'><strong>所属专题：</strong></td>
   <td height='25'>
   <select name='specialId' id='specialId' __def_value='0'>
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
  <input type='hidden' value='0' __def_value='0' name="columnId" id="columnId" />
  <input type='checkbox' name='includeChild' value='true' __def_value='true' 
    id='includeChild' checked="checked" />
  <input type="hidden" name="specialId" value="0" __def_value='0' id="specialId" />
  </td>
  </tr>
  #{/if }
 <tr class='tdbg'>
  <td height='25' align='right' class='tdbg5'><strong>标签说明：</strong></td>
  <td height='25'><input TYPE='text' id='labelDesc' name='LabelDesc' value=''
   id='id' size='15' maxlength='20'>&nbsp;&nbsp;<FONT
   style='font-size:12px' color='blue'>请在这里填写标签的使用说明方便以后的查找</FONT></td>
 </tr>
 <tr class='tdbg'>
  <td height='25' align='right' class='tdbg5'><strong>显示样式：</strong></td>
  <td height='25'><select name='showType' id='showType' __def_value='0'>
   <option value='0'>普通列表</option>
   <option value='1'>表格式</option>
   <!-- <option value='3'>各项独立式</option>
   <option value='4'>输出DIV格式</option>
   <option value='5'>输出RSS格式</option>-->
  </select></td>
 </tr>
 <tr class='tdbg'>
  <td height='25' align='right' class='tdbg5'><strong>图片数目：</strong></td>
  <td height='25'><input id='itemNum' name='itemNum' type='text' value='10' size='5'
   __def_value='10' maxlength='3'>&nbsp;&nbsp;&nbsp;&nbsp;<font color='#FF0000'>如果为0，将显示所有图片。</font></td>
 </tr>
 <tr class='tdbg'>
  <td height='25' align='right' class='tdbg5'><strong>图片属性：</strong></td>
  <td height='25'>
    <input name='IsTop' type='checkbox' id='isTop' value='true' __false_value='' __def_value='' /> 固顶图片&nbsp;&nbsp;
    <input name='IsCommend' type='checkbox' id='isCommend' value='true' __false_value='' __def_value='' />推荐图片&nbsp;&nbsp;
    <input name='IsElite' type='checkbox' id='isElite' value='true' __false_value='' __def_value='' />精华图片&nbsp;&nbsp;
    <input name='IsHot' type='checkbox' id='isHot' value='true' __false_value='' __def_value='' /> 热门图片&nbsp;&nbsp;
    <font color='#FF0000'>如果都不选，将显示所有图片。</font>
  </td>
 </tr>
 <tr class='tdbg'>
  <td height='25' align='right' class='tdbg5'><strong>作者姓名：</strong></td>
  <td height='25'><input id='author' name='Author' type='text' value='' __def_value=''
   size='10' maxlength='20' />&nbsp;&nbsp;&nbsp;&nbsp;<font
   color='#FF0000'>如果不为空，则只显示指定录入者的图片，用于个人文集。</font></td>
 </tr>
 <tr class='tdbg'>
  <td height='25' align='right' class='tdbg5'><strong>图片属性图片：</strong></td>
  <td height='25'>
  <table border='0' cellpadding='0' cellspacing='0' width='100%'
   height='100%' valign='top'>
   <tr>
    <td width='100'>
    <select name='picType' id='picType' __def_value='0' onchange="javascript:change_item(this)">
     <option value='0'>不显示</option>
     <option value='2'>符号</option>
     <option value='1'>小图片（样式 1）</option>
     <option value='3'>小图片（样式 2）</option>
     <option value='4'>小图片（样式 3）</option>
     <option value='5'>小图片（样式 4）</option>
     <option value='6'>小图片（样式 5）</option>
    </select></td>
    <td id='objFiles' style='display:none'>
     &nbsp;普通图片&nbsp;<img src='../../images/photo/photo_common.gif' id="common" BORDER='0' alt='普通图片' />
     &nbsp;推荐图片&nbsp;<img src='../images/photo/photo_elite.gif' id="elite" BORDER='0' alt='推荐图片' />
     &nbsp;固定图片&nbsp;<img src='../../images/photo/photo_ontop.gif' id="ontop" BORDER='0' alt='固定图片' />
    </td>
   </tr>
  </table>
  </td>
 </tr>
 <tr class='tdbg'>
  <td height='25' align='right' class='tdbg5'><strong>日期范围：</strong></td>
  <td height='25'>只显示最近 <input name='DateScope' type='text'
   id='dateScope' value='0' __def_value='0' size='5' maxlength='3' />
  天内更新的图片&nbsp;&nbsp;&nbsp;&nbsp;<font color='#FF0000'>&nbsp;&nbsp;如果为空或0，则显示所有天数的图片。</font></td>
 </tr>
 <tr class='tdbg'>
  <td height='25' align='right' class='tdbg5'><strong>排序方法：</strong></td>
  <td height='25'>
  <select name='OrderType' id='orderType' __def_value='0'>
   <option value='0'>图片ID（降序）</option>
   <option value='1'>图片ID（升序）</option>
   <option value='2'>更新时间（降序）</option>
   <option value='3'>更新时间（升序）</option>
   <option value='4'>点击次数（降序）</option>
   <option value='5'>点击次数（升序）</option>
  </select></td>
 </tr>
 <tr class='tdbg'>
  <td height='25' align='right' class='tdbg5'><strong>标题最多字符数：</strong></td>
  <td height='25'><input name='TitleCharNum' type='text' id='titleCharNum'
   value='0' __def_value='0' size='5' maxlength='3' /> &nbsp;&nbsp;&nbsp;&nbsp;<font
   color='#FF0000'>如果为0，则显示完整标题。字母算一个字符，汉字算两个字符。</font></td>
 </tr>
 <tr class='tdbg'>
  <td height='25' align='right' class='tdbg5'><strong>图片内容字符数：</strong></td>
  <td height='25'><input name='ContentNum' type='text'
   id='contentNum' value='0' __def_value='0' size='5' maxlength='3'>
  &nbsp;&nbsp;&nbsp;&nbsp;<font color='#FF0000'>如果大于0，则在标题下方面显示指定字数的图片内容</font></td>
 </tr>
 <tr class='tdbg'>
  <td height='25' align='right' class='tdbg5'><strong>每行的列数：</strong></td>
  <td height='25'><input type='text' name='Cols' value='1' __def_value='1' id='cols'
   size='5' maxlength='3'> &nbsp;&nbsp;&nbsp;&nbsp;<font
   color='#FF0000'>超过此列数就换行</font> </td>
 </tr>
 <tr class='tdbg'>
  <td height='50' align='right' class='tdbg5'><strong>显示内容：</strong></td>
  <td height='50'>
  <table width='100%' border='0' cellpadding='1' cellspacing='2'>
   <tr>
    <td><input name='showColumn' type='checkbox'
     id='showColumn' value='true' __def_value='false' />所属栏目</td>
    <td><input name='ShowAuthor' type='checkbox' id='showAuthor'
     value='true' __def_value='false' />作者</td>
    <td>更新时间 <select name='lastModified' id='lastModified' __def_value='0'>
     <option value='0'>不显示</option>
     <option value='1'>年月日</option>
     <option value='2'>月日</option>
     <option value='3'>月-日</option>
    </select></td>
    <td><input name='ShowHits' type='checkbox' id='ShowHits'
     value='true' __def_value='false' />点击次数</td>
   </tr>
   <tr>
    <td><input name='ShowHot' type='checkbox' id='showHot'
     value='true' __def_value='false' />热点图片标志</td>
    <td><input name='ShowNew' type='checkbox' id='showNew'
     value='true' __def_value='false' />最新图片标志</td>
    <td><input name='ShowTips' type='checkbox' id='showTips'
     value='true' __def_value='false' />显示提示信息</td>
    <td><input name='usePage' type='checkbox' id='usePage'
     value='0'>是否分页显示</td>
   </tr>
  </table>
  </td>
 </tr>
 <tr class='tdbg'>
  <td height='25' align='right' class='tdbg5'><strong>图片打开方式：</strong></td>
  <td height='25'><select name='OpenType' id='openType' __def_value='0'>
   <option value='0'>在原窗口打开</option>
   <option value='1'>在新窗口打开</option>
  </select></td>
 </tr>
 <tr class='tdbg'>
  <td height='25' align='right' class='tdbg5'><strong>CSS类名：</strong></td>
  <td height='25'><input id='cssName' name='CssName' type='text' value='' __def_value=''
   size='10' maxlength='20' />&nbsp;&nbsp;&nbsp;&nbsp;<font
   color='#FF0000'>列表中文字链接调用的CSS类名，可选参数(仅在表格式有效)</font></td>
 </tr>
 <tr class='tdbg'>
  <td height='25' align='right' class='tdbg5'><strong>风格样式1：</strong></td>
  <td height='25'><input id='CssName1' name='CssName1' type='text' value='' __def_value=''
   size='10' maxlength='20'>&nbsp;&nbsp;&nbsp;&nbsp;<font
   color='#FF0000'>列表中奇数行的CSS效果的类名，可选参数(仅在表格式有效)</font></td>
 </tr>
 <tr class='tdbg'>
  <td height='25' align='right' class='tdbg5'><strong>风格样式2：</strong></td>
  <td height='25'><input id='CssName2' name='CssName2' type='text' value='' __def_value=''
   size='10' maxlength='20'>&nbsp;&nbsp;&nbsp;&nbsp;<font
   color='#FF0000'>列表中偶数行的CSS效果的类名，可选参数(仅在表格式有效)</font></td>
 </tr>
</table>
</pub:template>

<jsp:forward page="label_process.jsp" />
