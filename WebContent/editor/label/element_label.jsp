<%@page language="java" contentType="text/html; charset=gb2312" pageEncoding="UTF-8"%>
<%@taglib prefix="pub" uri="/WEB-INF/publish.tld"%>
<%@page import="com.chinaedustar.publish.*"%>

<!-- 定义标签页集合。 -->
<pub:tabs var="contentTabs" scope="request" purpose="">
 <pub:tab name="paramSet" text="参数设置" template="temp_paramSet" default="true" />
 <pub:tab name="labelPreview" text="标签预览" template="temp_labelPreview" default="false" />
</pub:tabs>

<%@ include file="../../admin/tabs_tmpl2.jsp" %>

<%
  ParamUtil putil = new ParamUtil(pageContext);
  int width = putil.safeGetIntParam("width", 540);
  int height = putil.safeGetIntParam("height", 360);
 
  width -= 40;
  height -= 120;
  if (width < 220) {
    width = 220;
  }
  if (height < 60) {
    height = 60;
  }
 
  request.setAttribute("width", width);
  request.setAttribute("height", height);
%>

<!-- 
 param labelName 标签名称
 param labelTitle 标签标题
 -->
<pub:template name="label_main_temp">#{param labelName, labelTitle}
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
#{call html_head(labelName, labelTitle)}
</head>
<body onkeydown='body_onkeydown();'>
<!-- 为了节省空间, 不显示这个了
 <div class="title" style="margin:1px;" align="center"><strong>#{labelTitle }</strong></div>
-->
 #{call tab_js}
 #{call tab_header(contentTabs)}
 #{call tab_content(contentTabs)}
</body>
</html>
</pub:template>


<pub:template name="html_head">
#{param labelName, labelTitle}
 <meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
 <title>#{labelTitle}</title>
 <base target='_self' /><%-- 保证 model dialog 的时候链接在自己的窗口 --%>
 <link href='../../admin/admin_style.css' rel='stylesheet' type='text/css' />
 <script type="text/javascript" src="JLabel.js"></script> 
<script type="text/javascript">
var label = null; // new JLabel("#{labelName}");
var gen_full_attr = false;  // 生成完整的标签属性??
// 通过id找到控件.
function $(id) {
 var obj = document.getElementById(id);
 if (!obj) throw new Error("Id为" + id + "的控件不存在");
 return obj;
}

function body_onkeydown() {
  if (event.keyCode == 27) // ESC
    window.close();
}

// 初始化标签的属性.
function initLabelProperty() {
  label = new JLabel("#{labelName}");
  #{call label_property_init}
}

// 显示预览内容.
function showPreviewContent(){
  initLabelProperty();    
  $("LABEL_CONTENT").value = label.toString();
  $("formParamSet").submit();
  
  showTab("labelPreview");    
  showPreviewPanel();
}

// 显示预览界面面板.
function showPreviewPanel() {
  $("htmlCode").style.display = "none";
  $("labelContent").style.display = "none";
  $("frmLabelPreview").style.display = "";
}  

// 显示HTML代码面板.
function showHTMLPanel() {
  $("frmLabelPreview").style.display = "none";
  $("labelContent").style.display = "none";
  $("htmlCode").style.display = "";   
 
  // var obj = eval("frmLabelPreview");
  // $("htmlCode").value = obj.window.document.body.innerHTML;
  $("htmlCode").value = frmLabelPreview.window.document.body.innerHTML;
}

// 显示标签内容面板.
function showLabelContentPanel() {
  $("frmLabelPreview").style.display = "none";
  $("htmlCode").style.display = "none";
  $("labelContent").style.display = "";
  $("labelContent").value = (label == null || label.content == null) ? "" : label.content;
}

// 返回结果给调用页.
function back2Caller(){
  initLabelProperty();
  window.returnValue=label.toString();
  window.close();
}
  
// 提供精简的标签产生，如果值为缺省则不产生该属性。对于大部分属性为缺省的时候，比较简短.
function setLabelProp(elem_name) {
  var element = $(elem_name);
  
  var def_value = element.__def_value;
  if (def_value == null) def_value = '';
  
  var value = element.value;
  if (element.tagName.toUpperCase() == 'INPUT' ) {
    if (element.type.toLowerCase() == 'checkbox') {
      value = element.checked ? element.value : element.__false_value;
    } else if (element.type.toLowerCase() == 'radio') {
      // check for which radio is checked?
      var radios = document.getElementsByName(elem_name);
      for(var i = 0; i < radios.length; ++i) {
        var radio = radios[i];
        if (radio.checked) {
          value = radio.value;
          def_value = radio.__def_value;
          break;
        }
      } // end of for radios
    }
  }
  if (value == null) value = def_value;
  
  // assume element have a proprty named __def_value
  if (value.toString() != def_value || gen_full_attr)
    label.set(elem_name, value);
}
</script>
</pub:template>



<pub:template name="temp_paramSet">
 #{call label_param_setting }
 <div align='center'>
   <input type="button" value="    确   定    " onclick="back2Caller();" />
   <input type="button" value="生成预览" onclick="showPreviewContent();" />
   <input type="button" value=" 帮 助 " onclick="javascript:window.open('../../admin/help/index.jsp');" />
 </div>
</pub:template>


<pub:template name="temp_labelPreview">
<table width='100%'>
 <tr>
  <td>
   <iframe id="frmLabelPreview" name="frmLabelPreview" style="width:#{width }px;height:#{height }px;"></iframe>
   <textarea rows="10" cols="40" id="htmlCode" name="htmlCode" style="width:#{width }px;height:#{height }px;display:none"></textarea>
   <textarea rows="10" cols="40" id="labelContent" name="labelContent" style="width:#{width }px;height:#{height }px;display:none"></textarea>
  </td>
 </tr>
 <tr>
  <td>
   <input type="button" value=" 更新 "  onclick="showPreviewContent();"/>
   <input type="button" value="界面预览" onclick="showPreviewPanel();"/>
   <input type="button" value="查看HTML代码" onclick="showHTMLPanel();"/>
   <input type="button" value="标签内容" onclick="showLabelContentPanel();"/>
   <input type='checkbox' name='fullAttribute' onclick='gen_full_attr=this.checked;' />
    生成标签的全部属性
  </td>
 </tr>
</table>
<form action="super_label_preview.jsp" target="frmLabelPreview" name="formParamSet" method="post" style='margin:0px;'>
 <input type="hidden" name="LABEL_CONTENT" id="LABEL_CONTENT" />
</form>
</pub:template>
