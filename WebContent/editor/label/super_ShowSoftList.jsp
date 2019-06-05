<%@page language="java" contentType="text/html; charset=gb2312" pageEncoding="utf-8"
%><%@taglib prefix="pub" uri="/WEB-INF/publish.tld"
%><%@page import="com.chinaedustar.publish.admin.SuperLabel"
%><%
  // 初始化页面数据。
  SuperLabel page_init = new SuperLabel(pageContext);
  page_init.initSuperItemPage();
  
%>
  
<%@ include file="../../admin/element_column.jsp" %> 
<%@ include file="element_label.jsp" %>
<%@ include file="super_label_common.jsp" %>

<!-- 主模板定义 -->
<pub:template name="main">
 #{call label_main_temp("ShowSoftList","下载列表函数标签") }
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
  setLabelProp('colNum');
  setLabelProp('showColumn');
  // setLabelProp('showPicArticle');
  setLabelProp('showAuthor');
  setLabelProp('lastModified');
  setLabelProp('showHits');
  setLabelProp('showHot');
  setLabelProp('showNew');
  // setLabelProp('showDescription');
  // setLabelProp('showComment');
  setLabelProp('usePage');
  setLabelProp('openType');
  setLabelProp('style');
  setLabelProp('class1');
  setLabelProp('class2');
</pub:template>

<pub:template name="label_param_setting">
<table width='100%' bgcolor='white' cellspacing='1' cellpadding='1'>
  <tr class='tdbg'>
   <td height='25' align='right' class='tdbg5'><strong>所属频道：</strong></td>
   <td height='25'>
  <form action='super_ShowSoftList.jsp' method='get' name='myform' id='myform' style='margin:0px;'>
   #{call channel_select(700, 600) }
  </form>
   </td>
  </tr>
  #{if (channelId > 0) }
  <tr class='tdbg'>
   <td height='25' align='right' class='tdbg5'><strong>所属栏目：</strong></td>
   <td height='25'>
   #{call column_select }
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
  <tr style="display: none;">
   <td>
    <input type="hidden" value="0" __def_value='0' name="ColumnId" id="columnId" />
    <input type="checkbox" name='IncludeChild' value='true' __def_value='0' id="includeChild" checked="checked" />
    <input type="hidden" name="SpecialId" value="0" __def_value='0' id="specialId" />
   </td>
  </tr>
  #{/if }
 <tr class='tdbg'>
  <td height='25' align='right' class='tdbg5'><strong>标签说明：</strong></td>
  <td height='25'>
    <input type='text' name='LabelDesc' value='' __def_value='' id='labelDesc' size='15' maxlength='20'>
    &nbsp;&nbsp;<font style='font-size:12px' color='blue'>请在这里填写标签的使用说明方便以后的查找</font>
  </td>
 </tr>
 <tr class='tdbg'>
  <td height='25' align='right' class='tdbg5'><strong>显示样式：</strong></td>
  <td height='25'>
   <select name='ShowType' id='showType' __def_value='0'>
    <option value='0'>普通列表</option> 
    <option value='1'>表格式</option>
   </select>
  </td>
 </tr>
 <tr class='tdbg'>
  <td height='25' align='right' class='tdbg5'><strong>软件数目：</strong></td>
  <td height='25'>
   <input name='ItemNum' type='text' value='10' __def_value='0' size='5' id='itemNum' 
    maxlength='3'>&nbsp;&nbsp;&nbsp;&nbsp;<font color='#FF0000'>如果为0，将显示所有软件。</font>
  </td>
 </tr>
 <tr class='tdbg'>
  <td height='25' align='right' class='tdbg5'><strong>软件属性：</strong></td>
  <td height='25'>
    <input name='IsTop' type='checkbox' id='isTop' value='true' __false_value='' __def_value='' /> 固顶下载&nbsp;&nbsp;
    <input name='IsCommend' type='checkbox' id='isCommend' value='true' __false_value='' __def_value='' />推荐下载&nbsp;&nbsp;
    <input name='IsElite' type='checkbox' id='isElite' value='true' __false_value='' __def_value='' />精华下载&nbsp;&nbsp;
    <input name='IsHot' type='checkbox' id='isHot' value='true' __false_value='' __def_value='' /> 热门下载&nbsp;&nbsp;
    <font color='#FF0000'>如果都不选，将显示所有下载。</font>
   </td>
 </tr>
 <tr class='tdbg'>
  <td height='25' align='right' class='tdbg5'><strong>作者姓名：</strong></td>
  <td height='25'>
   <input name='Author' id='author' type='text' value='' __def_value='' size='10' 
    maxlength='10' />&nbsp;&nbsp;&nbsp;&nbsp;<font color='#FF0000'>如果不为空，则只显示指定录入者的软件，用于个人文集。</font>
  </td>
 </tr>
 <tr class='tdbg'>
  <td height='25' align='right' class='tdbg5'><strong>软件属性图片：</strong></td>
  <td height='25'>
  <table border='0' cellpadding='0' cellspacing='0' width='100%' height='100%' valign='top'>
   <tr>
    <td width='100'>
     <select name='PicType' id='picType' __def_value='0' onchange="javascript:change_item(this)">
     <option value='0'>不显示</option>
     <option value='2'>符号</option>
     <option value='1'>小图片（样式 1）</option>
     <option value='3'>小图片（样式 2）</option>
     <option value='4'>小图片（样式 3）</option>
     <option value='5'>小图片（样式 4）</option>
     <option value='6'>小图片（样式 5）</option>
    </select></td>
    <td id='objFiles' style='display:none'>
      &nbsp;普通图片&nbsp;<img id='common' src='../../images/soft/soft_common.gif' border='0' alt='普通图片'>
      &nbsp;推荐图片&nbsp;<img id='elite' src='../../images/soft/soft_elite.gif' border='0' alt='推荐图片'>
      &nbsp;固顶图片&nbsp;<img id='ontop' src='../../images/soft/soft_ontop.gif' border='0' alt='固顶图片'>
    </td>
   </tr>
  </table>
  </td>
 </tr>
 <tr class='tdbg'>
  <td height='25' align='right' class='tdbg5'><strong>日期范围：</strong></td>
  <td height='25'>只显示最近 <input name='DateScope' type='text'
   id='dateScope' value='0' __def_value='0' size='5' maxlength='3'>
    天内更新的软件&nbsp;&nbsp;&nbsp;&nbsp;<font color='#FF0000'>&nbsp;&nbsp;如果为空或0，则显示所有天数的软件。</font>
  </td>
 </tr>
 <tr class='tdbg'>
  <td height='25' align='right' class='tdbg5'><strong>排序方法：</strong></td>
  <td height='25'>
   <select name='OrderType' id='orderType' __def_value='0'>
    <option value='0'>软件ID（降序）</option>
    <option value='1'>软件ID（升序）</option>
    <option value='2'>更新时间（降序）</option>
    <option value='3'>更新时间（升序）</option>
    <option value='4'>点击次数（降序）</option>
    <option value='5'>点击次数（升序）</option>
   </select>
  </td>
 </tr>
 <tr class='tdbg'>
  <td height='25' align='right' class='tdbg5'><strong>标题最多字符数：</strong></td>
  <td height='25'>
   <input name='TitleCharNum' type='text' id='titleCharNum' value='0' __def_value='0' 
     size='5' maxlength='3'> &nbsp;&nbsp;&nbsp;&nbsp;
     <font color='#FF0000'>如果为0，则显示完整标题。字母算一个字符，汉字算两个字符。</font>
  </td>
 </tr>
 <tr class='tdbg'>
  <td height='25' align='right' class='tdbg5'><strong>软件内容字符数：</strong></td>
  <td height='25'>
   <input name='ContentNum' type='text' id='contentNum' value='0' __def_value='0' size='5' 
    maxlength='3' /> &nbsp;&nbsp;&nbsp;&nbsp;
     <font color='#FF0000'>如果大于0，则在标题下方面显示指定字数的软件介绍</font>
  </td>
 </tr>
 <tr class='tdbg'>
  <td height='25' align='right' class='tdbg5'><strong>每行的列数：</strong></td>
  <td height='25'>
   <input TYPE='text' NAME='ColNum' value='1' __def_value='1' id='colNum'
     size='5' maxlength='3'> &nbsp;&nbsp;&nbsp;&nbsp;<font color='#FF0000'>超过此列数就换行</font>
  </td>
 </tr>
 <tr class='tdbg'>
  <td height='50' align='right' class='tdbg5'><strong>显示内容：</strong></td>
  <td height='50'>
  <table width='100%' border='0' cellpadding='1' cellspacing='2'>
   <tr>
    <td>
     <input name='showColumn' type='checkbox' id='ShowColumn' value='false' 
      __def_value='false' />所属栏目
    </td>
    <td>
     <input name='ShowAuthor' type='checkbox' id='showAuthor' value='false'
      __def_value='false' />作者
    </td>
    <td>
      更新时间 <select name='lastModified' id='lastModified' __def_value=''>
      <option value=''>不显示</option>
      <option value='MM-dd mm:ss'>月-日 小时:分</option>
      <option value='yyyyMMdd'>年月日</option>
      <option value='MMdd'>月日</option>
      <option value='MM-dd'>月-日</option>
      <option value='yyyy-MM-dd'>年-月-日</option>
      <option value='yyyy-MM-dd mm:ss'>年-月-日 小时:分</option>
     </select>
    </td>
    <td>
     <input name='ShowHits' type='checkbox' id='ShowHits' value='false' 
      __def_value='false' />点击次数
    </td>
   </tr>
   <tr>
    <td>
     <input name='ShowHot' type='checkbox' id='showHot' value='false' 
      __def_value='false' />热点软件标志
    </td>
    <td>
     <input name='ShowNew' type='checkbox' id='showNew' value='false'
      __def_value='false' />最新软件标志
    </td>
    <td>
     <input name='UsePage' type='checkbox' id='usePage' value='false'
      __def_value='false' />使用分页
    </td>
   </tr>
  </table>
  </td>
 </tr>
 <tr class='tdbg'>
  <td height='25' align='right' class='tdbg5'><strong>软件打开方式：</strong></td>
  <td height='25'>
   <select name='OpenType' id='openType' __def_value='0'>
    <option value='0'>在原窗口打开</option>
    <option value='1'>在新窗口打开</option>
   </select>
  </td>
 </tr>
 <tr class='tdbg'>
  <td height='25' align='right' class='tdbg5'><strong>CSS类名：</strong></td>
  <td height='25'>
   <input name='Style' id='style' type='text' value='' __def_value='' size='10' 
    maxlength='10' />&nbsp;&nbsp;&nbsp;&nbsp;
    <font color='#FF0000'>列表中文字链接调用的CSS类名，可选参数(仅在表格式有效)</font>
  </td>
 </tr>
 <tr class='tdbg'>
  <td height='25' align='right' class='tdbg5'><strong>风格样式1：</strong></td>
  <td height='25'>
   <input name='Class1' id='class1' type='text' value='' __def_value='' size='10' 
    maxlength='10'>&nbsp;&nbsp;&nbsp;&nbsp;
    <font color='#FF0000'>列表中奇数行的CSS效果的类名，可选参数(仅在表格式有效)</font>
  </td>
 </tr>
 <tr class='tdbg'>
  <td height='25' align='right' class='tdbg5'><strong>风格样式2：</strong></td>
  <td height='25'>
   <input name='Class2' id='class2' type='text' value='' __def_value='' size='10' 
    maxlength='10'>&nbsp;&nbsp;&nbsp;&nbsp;
    <font color='#FF0000'>列表中偶数行的CSS效果的类名，可选参数(仅在表格式有效)</font>
  </td>
 </tr>
</table>

<script Language="JavaScript">
function change_item(element){
  if(element.selectedIndex!=-1)
  var selectednumber = element.options[element.selectedIndex].value;

  if(selectednumber==1) {
    objFiles.style.display="";
    
    $("common").src = "../../images/soft/soft_common.gif"
    $("elite").src = "../../images/soft/soft_elite.gif"
    $("ontop").src = "../../images/soft/soft_ontop.gif"
  } else if (selectednumber==0) {
    objFiles.style.display="none";
  } else if (selectednumber==2) {
    objFiles.style.display="none";
  } else if (selectednumber>=3) {
    selectednumber = selectednumber - 1
    objFiles.style.display="";
      
    $("common").src = "../../images/soft/soft_common" + selectednumber + ".gif"
    $("elite").src = "../../images/soft/soft_elite" + selectednumber + ".gif"
    $("ontop").src = "../../images/soft/soft_ontop" + selectednumber + ".gif"
  }
}
</script>
</pub:template>

<pub:process_template name="main" />
