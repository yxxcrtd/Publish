<%@page language="java" contentType="text/html; charset=gb2312" 
   pageEncoding="UTF-8" errorPage="admin_error.jsp"
%><%@taglib prefix="pub" uri="/WEB-INF/publish.tld" %>

<pub:template name="object_extends_edit">
<script language='javascript'>
// 从指定节点向上找直到找到 TR 标记节点并返回该节点.
function findTrNode(node) {
  if (node == null) return null;
  while (node.tagName.toUpperCase() != 'TR') {
    node = node.parentElement;
  }
  return node;
}

// add an extends property
function addProperty() {
  if (confirm('您确定要添加一个自定义属性吗？添加将转到一个新页面，如果您修改了数据将不会保存。') == false) return;
  window.location = 'admin_extends_add.jsp?id=#{object.id}&objectClass=#{object.objectClass}';
}

var __prop_tr = null;
function getEditPropValue() {
  if (__prop_tr == null) return;
  var prop_value = __prop_tr.all._propValue.value;
  return prop_value;
}

function setEditPropValue(val) {
  if (__prop_tr == null) return;
  __prop_tr.all._propValue.value = val;
}

// edit html property
function editProperty(btn) {
  var tr = findTrNode(btn);
  // alert('tr = ' + tr.tagName + ', prop_name = ' + tr.prop_name + ', prop_type = ' + tr.prop_type);
  
  var prop_type = tr.prop_type;
  if (prop_type != 'html') return;

  var prop_value = tr.all._propValue.value;
  // alert('prop_value = ' + prop_value);
  __prop_tr = tr;
  
  window.open('admin_prop_edit.jsp?random=' + Math.random(), '_propValueEditor',
    'height:640px;width:800px;center:yes;status:no;');
}

// delete an extends property
function deleteProperty(btn) {
  if (confirm('您确定要删除这个属性吗？\r\n\r\n部分属性被系统使用，如果不正确的删除可能导致某些功能不正常。') == false) return;
  var tr = findTrNode(btn);
  // alert('tr = ' + tr.tagName + ', prop_name = ' + tr.prop_name + ', prop_type = ' + tr.prop_type);
  
  var prop_name = tr.prop_name;
  window.open('admin_extends_action.jsp?command=delete&id=#{object.id}&objectClass=#{object.objectClass}&propName=' + prop_name, '_self');
}

function deleteAllProperty() {
  if (confirm('您确定要删除对象的全部扩展属性吗？\r\n\r\n部分属性被系统使用，如果不正确的删除可能导致某些功能不正常。') == false) return;
  window.open('admin_extends_action.jsp?command=delete_all&id=#{object.id}&objectClass=#{object.objectClass}', '_self');
}
</script>
#{if object.id == 0 }
 <b>新建的对象保存之后才能管理其自定义属性。</b>
#{else }
<table>
 #{foreach prop in object.extends }
 <tr class='tdbg' prop_name='#{prop.name}' prop_type='#{prop.propType}'>
  <td width='300' class='tdbg5'><b>#{prop.name}</b>
  <input type='hidden' name='_propName' value='#{prop.name }' />
  <br />扩展属性 #{prop.name }, 类型为 #{prop.propType}</td>
  <td>
  #{if prop.propType@starts_with("multi.") || prop.propType == 'html' }
   <textarea name='_propValue' rows='6' cols='58'>#{prop.propValue@html }</textarea>
   #{if prop.propType == 'html' }
   <input type='button' value='编辑' onclick='editProperty(this)' />
   #{/if }
  #{else}
   <input name='_propValue' type='text' size='60' value="#{prop.propValue@html }">
  #{/if}
   <input type='button' value='删除' onclick="deleteProperty(this)" />
  </td>
 </tr>
 #{/foreach }
 <tr class='tdbg'>
  <td colspan='2'>说明：1. 多行的值请把每个值放在一行上面。<br />
   &nbsp;&nbsp;2. 对于 HTML 类型的属性，点击编辑之后可以在较大的窗口中编辑属性值。</td>
 </tr>
 <tr class='tdbg'>
  <td colspan='2' align='center'>
   <input type='button' value='添加一个自定义属性' onclick='addProperty();' />
   <input type='button' value='删除所有自定义属性' onclick='deleteAllProperty();'/>
  </td>
 </tr>
</table>
#{/if }

</pub:template>
