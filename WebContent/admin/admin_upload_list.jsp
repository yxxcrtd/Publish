<%@ page language="java" contentType="text/html; charset=gb2312"
   pageEncoding="UTF-8" errorPage="admin_error.jsp"
%><%@page import="com.chinaedustar.publish.admin.UploadManage"  
%><%@taglib prefix="pub" uri="/WEB-INF/publish.tld"
%><%
  UploadManage manager = new UploadManage(pageContext);
  manager.initListPage();
%><html>
 <head>
  <meta http-equiv="Content-Type" content="text/html; charset=gb2312">
  <title>文件列表</title>
  <link href="admin_style.css" rel="stylesheet" type="text/css" />
  <script type="text/javascript">
   function unselectall(){
       if(document.myform.chkAll.checked){
      document.myform.chkAll.checked = document.myform.chkAll.checked&0;
       }
   }
   function CheckAll(form){
     for (var i=0;i<form.elements.length;i++)
       {
       var e = form.elements[i];
       if (e.Name != 'chkAll')
          e.checked = form.chkAll.checked;
       }
   }
   function preloadImg(src) {
     var img=new Image();
     img.src=src
   }
   preloadImg('images/admin_upload_open.gif');
   
   var displayBar=false;
   function switchBar(obj) {
     if (displayBar) {
       parent.frame.cols='0,*';
       displayBar=false;
       obj.src='images/admin_upload_open.gif';
       obj.title='打开左边文件目录树型导航';
     } else {
       parent.frame.cols='160,*';
       displayBar=true;
       obj.src='images/admin_upload_close.gif';
       obj.title='关闭左边文件目录树型导航';
     }
   }
   function reSort(which)
   {
    document.myform.sortby.value = which;
    document.myform.action=document.myform.action.replace("admin_upload_operate","admin_upload_list");
    document.myform.submit();
    document.myform.action=document.myform.action.replace("admin_upload_list","admin_upload_operate");
   } 
   
   function returnInfo(id, name, path, size){
    window.returnValue=id + "$$$" + name + "$$$" + path + "$$$" + size;
    window.close();
   }  
  </script>
  <base target="_self"/>
 </head>
 <body>
 
 <pub:template name="temp_main">   
  #{call temp_share_top }
  #{if(showFileStyle==1) }
   #{call temp_detail_style }
  #{elseif(showFileStyle==2) }
   #{call temp_thumb_style }
  #{endif }
 </pub:template>
 
 <pub:template name="temp_share_top">
  <table width='100%' border='0' align='center' cellpadding='2' cellspacing='1'>
   <tr class='tdbg'>
    <td width='0' height='30'>
     <table width='100%' border='0' align='center' cellpadding='1'
      cellspacing='1'>
      <tr>
       #{if(!selectFile) }
       <td height='22'>
        &nbsp;
        <img onclick='switchBar(this)'
         src='images/admin_upload_open.gif' title='打开左边文件目录树型导航'
         style='cursor:hand'>
       </td>
       #{endif}
       <td height='22'>
       </td>
       <td>
       #{if(showFileStyle==1) }
        <a href='admin_upload_list.jsp?ShowFileStyle=2&channelId=#{channelId }&selectFile=#{selectFile }&curPath=#{curDirPath }'>切换到缩略图方式 </a>
       #{elseif(showFileStyle== 2)}
        <a href='admin_upload_list.jsp?ShowFileStyle=1&channelId=#{channelId }&selectFile=#{selectFile }&curPath=#{curDirPath }'>切换到详细信息方式</a> 
       #{endif }
       </td>
      </tr>
     </table>
    </td>
    <td height='30' width='50%'>
    </td>
   </tr>
  </table>
  <br>
  <table width='100%' border='0' cellspacing='0' cellpadding='0'>
   <tr>
    <td>
     当前目录：#{webPath }
    </td>
    #{if(!isRootDir) }
    <td align='right'>
     <a href='admin_upload_list.jsp?channelId=#{channelId }&selectFile=#{selectFile }&curPath=#{parentDirPath }'>↑返回上级目录</a>
    </td>
    #{endif }
   </tr>
  </table>
 </pub:template>
 
 <pub:template name="temp_detail_style">
  #{call temp_form_begin }  
  
  #{call temp_detail_list }
  
  #{call temp_form_hidden }

  #{if(hasFile && !selectFile) }
   #{call temp_file_operate }
  #{endif }

  #{call temp_preview_js}

  #{call temp_form_end }
  
  #{if(hasFile) }
   #{call temp_show_order_js }
  #{endif }
 </pub:template>
 
 <pub:template name="temp_thumb_style">
  #{call temp_form_begin }
  
  #{call temp_thumb_dir_list }
  
  #{if(hasFile) }
   
   #{call temp_thumb_file_list }
   
   #{if(!selectFile) }
    #{call temp_file_operate }
   #{endif }
   
   #{call temp_form_hidden }
   #{call temp_form_end }
   
   #{call temp_thumb_guide }
   #{call temp_show_order_js }
    
  #{else }
   #{call temp_thumb_nofile }
   #{call temp_form_hidden }
   #{call temp_form_end }    
  #{endif }
  
 </pub:template>
 
 <pub:template name="temp_detail_list">
  <table width='100%' border='0' cellspacing='0' cellpadding='0'>
   <tr>
    <td height='18' class='title0' onmouseout="this.className='title0'"
     onmouseover="this.className='tdbgmouseover1'">
     &nbsp;&nbsp;
     <a href="javascript:reSort(#{SORT_BY_NAME });">文件名&nbsp;&nbsp;<img
       src='images/Calendar_Down.gif' border='0' style='display:none'
       id='Sort#{SORT_BY_NAME }'> </a>
    </td>
    <td width='55' align="right" class='title0'
     onmouseout="this.className='title0'"
     onmouseover="this.className='tdbgmouseover1'">
     <a href="javascript:reSort(#{SORT_BY_SIZE });">大小&nbsp;<img
       src='images/Calendar_Down.gif' border='0' style='display:none'
       id='Sort#{SORT_BY_SIZE }'> </a>&nbsp;
    </td>
    <td width='180' class='title0' onmouseout="this.className='title0'"
     onmouseover="this.className='tdbgmouseover1'">
     &nbsp;
     <a href="javascript:reSort(#{SORT_BY_TYPE });">类型&nbsp;&nbsp;<img
       src='images/Calendar_Down.gif' border='0' style='display:none'
       id='Sort#{SORT_BY_TYPE }'> </a>
    </td>
    <td width='140' class='title0' onmouseout="this.className='title0'"
     onmouseover="this.className='tdbgmouseover1'">
     <a href="javascript:reSort(#{SORT_BY_LASTMODIFYDATE });">上次修改时间&nbsp;&nbsp;<img
       src='images/Calendar_Down.gif' border='0' style='display:none'
       id='Sort#{SORT_BY_LASTMODIFYDATE }'> </a>
    </td>
    <td width='30' align='center' class='title0'>
     操作&nbsp;
    </td>
   </tr>
  #{foreach dir in Dirs }  
   <tr>
    <td height='18'>
     &nbsp;&nbsp;
     <img src='images/folder/folder.gif'>
     <a
      href='admin_upload_list.jsp?channelId=#{channelId }&selectFile=#{selectFile }&curPath=#{dir.path }'>#{dir.name }
     </a>
    </td>
    <td width='50' align="right">
     &nbsp;
    </td>
    <td width='180'>
     &nbsp;文件夹
    </td>
    <td width='140'>
     #{dir.lastModifyDate }
    </td>
    <td width='30' align='center'>
     <a
      href='admin_upload_operate.jsp?channelId=#{channelId }&selectFile=#{selectFile }&curPath=#{curDirPath }&DirName=#{dir.name }&Action=DelThisFolder'
      onclick="return confirm('你真的要删除此文件夹及里面的文件吗!');">删除</a>&nbsp;
    </td>
   </tr>
  #{/foreach }
  #{foreach file in Files }
   <tr onmouseout="this.className='tdbgmouseout1'" onmouseover="this.className='tdbg1'">
    <td align='left'>
     #{if(!selectFile) }
     <input name='FileName' type='checkbox' id='FileName'
      value='#{file.name }' onclick='unselectall()' #{iif(file.isUserful,"","checked") }>
     #{endif }
     <img src='images/folder/#{file.format }.gif'>
     #{if(!selectFile)}
      <a href='#{webPath}/#{file.name}' target='_blank'>
     #{else}
      <a href='#' onclick='returnInfo(#{file.id },"#{file.name }","#{webPath }/#{file.name }", "#{file.KSize }")'>
     #{endif}
      <span #{if(file.canPreview) }onmouseover="ShowADPreview('<img src=\&#39;#{webPath }/#{file.name }\&#39; width=\&#39;200\&#39; height=\&#39;120\&#39; border=\&#39;0\&#39;>')"
      onmouseout="hideTooltip('dHTMLADPreview')"#{else } title="非图片文件不能预览" #{endif}> #{file.name }</span>
     </a>
    </td>
    <td width='55' align='right'>
     #{file.stringSize }&nbsp;
    </td>
    <td width='180'>
     &nbsp;
     #{file.type }
    </td>
    <td width='140'>
     #{file.lastModifyDate }
    </td>
    <td width='30' align='center'>
     <a
      href='admin_upload_operate.jsp?channelId=#{channelId }&selectFile=#{selectFile }&curPath=#{curDirPath }&Action=Del&FileName=#{file.name }'
      onclick="return confirm('你真的要删除此文件吗!');">删除</a>&nbsp;
    </td>
   </tr>
  #{/foreach } 
   <tr>
    <td height=1><br><br></td>
   </tr>
  </table>
 </pub:template>
 
 <pub:template name="temp_thumb_dir_list">
  <table width='100%' cellpadding='2' cellspacing='1' class='border'>
   <tr class='title' height='22'>
    <td colspan='20'>
     <b>子目录导航</b>
    </td>
   </tr>
   <tr class='tdbg'> 
   #{foreach dir in Dirs }
    <td>
     <a href='admin_upload_list.jsp?channelId=#{channelId }&selectFile=#{selectFile }&curPath=#{dir.path }'>#{dir.name }</a>
    </td>
   #{/foreach } 
   </tr>
  </table>
  <br>
 </pub:template>
 
<pub:template name="temp_thumb_file_list">
  <table width='100%' border='0' cellspacing='0' cellpadding='0'>
   <tr>
    <td height='18'>
     排序方式：&nbsp;&nbsp;
     <a href="javascript:reSort(#{SORT_BY_NAME });">文件名&nbsp;<img
       src='Images/Calendar_Down.gif' border='0' style='display:none'
       id='Sort#{SORT_BY_NAME }'>
     </a> &nbsp;&nbsp;
     <a href="javascript:reSort(#{SORT_BY_SIZE });">大小&nbsp;<img
       src='Images/Calendar_Down.gif' border='0' style='display:none'
       id='Sort#{SORT_BY_SIZE }'>
     </a> &nbsp;&nbsp;
     <a href="javascript:reSort(#{SORT_BY_TYPE });">类型&nbsp;<img
       src='Images/Calendar_Down.gif' border='0' style='display:none'
       id='Sort#{SORT_BY_TYPE }'>
     </a> &nbsp;&nbsp;
     <a href="javascript:reSort(#{SORT_BY_LASTMODIFYDATE });">上次修改时间&nbsp;<img
       src='Images/Calendar_Down.gif' border='0' style='display:none'
       id='Sort#{SORT_BY_LASTMODIFYDATE }'>
     </a>
    </td>
    <td align='right'>
    </td>
   </tr>
  </table>
  <table width='100%' border='0' cellpadding='0' cellspacing='0'>
   <tr>
    <td colspan='2'>
 
  <table width='100%' border='0' align='center' cellpadding='0' cellspacing='3' class='border'>
   #{foreach row in fileTable }
   <tr class='tdbg'>     
    #{foreach file in row }
    <td width='25%'>
     <table width='100%' height='100%' border='0' cellpadding='0'
      cellspacing='2'>
      <tr>
       <td colspan='2' align='center'>
        #{if(!selectFile)}
         <a href='#{webPath}/#{file.name}' target='_blank'>
        #{else}
         <a href='#' onclick='returnInfo(#{file.id },"#{file.name }","#{webPath }/#{file.name }")'>
        #{endif}        
         <img src='#{iif(file.canPreview,webPath + "/" + file.name,"images/filetype_" + file.thumbPic + ".gif") }' width='130' height='90' border='2'
          Title='#{iif(file.isUseful,"","") }
文 件 名：#{file.name }
&#13;文件大小：#{file.KSize } K
文件类型：#{file.type }
&#13;修改时间：#{file.lastModifyDate}' style="border-color:yellow">
        </a>
       </td>
      </tr>
      #{if(!selectFile) }
      <tr>
       <td align='center'>
        #{if (!selectFile) }
         <a href='#{webPath}/#{file.name}' target='_blank'
        #{else }
         <a href='#' onclick='returnInfo(#{file.id },"#{file.name }","#{webPath }/#{file.name }")'
        #{/if } title='#{iif(file.isUseful,"","") }' >
         <font color=red>#{file.name }</font>
        </a>
       </td>
      </tr>      
      <tr>
       <td align='center'>
        <input name='FileName' type='checkbox' id='FileName'
         value='#{file.name }' onclick='unselectall()'
         #{iif(file.isUseful,"","checked") }>
        选中&nbsp;&nbsp;
        <a
         href='admin_upload_operate.jsp?channelId=#{channelId }&selectFile=#{selectFile }&curPath=#{curDirPath }&Action=Del&FileName=#{file.name }'
         onclick="return confirm('你真的要删除此文件吗? 注意：该文件可能被用在某个文章或页面中，删除之后该文章不会被自动更改。');">删除</a>
       </td>
      </tr>
      #{else }
      <tr>
       <td align='right'>
        文 件 名：
       </td>
       <td>
        <a href='#{webPath }/#{file.name }' target='_blank'>#{file.name }</a>
       </td>
      </tr>
      <tr>
       <td align='right'>
        文件大小：
       </td>
       <td>
        #{file.KSize } K
       </td>
      </tr>
      <tr>
       <td align='right'>
        文件类型：
       </td>
       <td>
        #{file.type }
       </td>
      </tr>
      <tr>
       <td align='right'>
        修改时间：
       </td>
       <td>
        #{file.lastModifyDate }
       </td>
      </tr>
      #{endif }
    </table>
    </td>
    #{/foreach }
   </tr> 
  #{/foreach }
  </table>
     
    </td>
   </tr>
  </table>
</pub:template>
 
 <pub:template name="temp_thumb_guide">
 <form name='showpages' method='Post' action='admin_upload_list.jsp?channelId=#{channelId }&selectFile=#{selectFile }&curPath=#{curDirPath }'>
  <table align='center'>
   <tr>
    <td>
     共<b>#{Guide.fileTotalCount }</b> 个文件，占用<b>#{Guide.fileTotalSize }</b> bMB
     &nbsp;&nbsp;&nbsp;
     #{if(Guide.isFirstPage) }
      首页
     #{else }
      <a href='admin_upload_list.jsp?channelId=#{channelId }&selectFile=#{selectFile }&curPath=#{curDirPath }&page=1'>首页</a>
     #{endif }
     &nbsp;
     #{if(Guide.curPage==1) }
      上一页
     #{else }
      <a href='admin_upload_list.jsp?channelId=#{channelId }&selectFile=#{selectFile }&curPath=#{curDirPath }&page=#{Guide.curPage-1 }'>上一页</a>
     #{endif }
     &nbsp;
     #{if(Guide.curPage==Guide.pageCount) }
      下一页
     #{else }
      <a href='admin_upload_list.jsp?channelId=#{channelId }&selectFile=#{selectFile }&curPath=#{curDirPath }&page=#{Guide.curPage+1 }'>下一页</a>
     #{endif }
     &nbsp;
     #{if(Guide.isLastPage) }
      尾页
     #{else }
      <a href='admin_upload_list.jsp?channelId=#{channelId }&selectFile=#{selectFile }&curPath=#{curDirPath }&page=#{Guide.pageCount }'>尾页</a>
     #{endif }
     &nbsp;页次：
     <strong><font color=red>#{Guide.curPage }</font>/#{Guide.pageCount }</strong>页 &nbsp;
     <b>#{Guide.perPageCount }</b>个文件/页&nbsp;转到：
     <select name='page' size='1' onchange='javascript:submit()'>
      #{foreach i in range(1, Guide.pageCount+1) }
        <option value='#{i }' #{iif(Guide.curPage==i,"selected","") }>第#{i }页</option>
      #{/foreach }    
     </select>
    </td>
   </tr>
  </table>
 </form>
  <br>
  <div align='center'>本页共显示<b>#{Guide.curFileCount }</b> 个文件，占用<b>#{Guide.curFileSize }</b> MB</div>
 </pub:template>
 
 <pub:template name="temp_thumb_nofile">
  <table width='100%' border='0' cellspacing='0' cellpadding='0'>
   <tr>
    <td height='18'></td>
    <td align='right'>
    </td>
   </tr>
   <tr class='tdbg'>
    <td align='center' colspan='2'>
     <br>
     <br>
     当前目录下没有任何文件！
     <br>
     <br>
    </td>
   </tr>
  </table>
 </pub:template>
 
 <pub:template name="temp_file_operate">  
  <table width='100%' border='0' cellpadding='0' cellspacing='0'>
   <tr>
    <td width='200' height='30'>
     <input name='chkAll' type='checkbox' id='chkAll'
      onclick='CheckAll(this.form);' value='checkbox'>
     选中本页所有文件
    </td>
    <td>
     <input name='Action' type='hidden' id='Action' value=''>
     <input type='submit' name='Submit' value='删除选中的文件'
      onclick="document.myform.Action.value='Del';return confirm('确定要删除选中的文件吗？');">
     &nbsp;&nbsp;
     <input type='submit' name='Submit2' value='删除当前目录的所有文件'
      onClick="document.myform.Action.value='DelCurrentDir';return confirm('确定要删除当前目录下的所有文件吗？')">
    </td>
   </tr>
  </table>     
 </pub:template>

 <pub:template name="temp_preview_js">
  <div id='dHTMLADPreview' style='Z-INDEX: 1000; LEFT: 0px; VISIBILITY: hidden; WIDTH: 10px; POSITION: absolute; TOP: 0px; HEIGHT: 10px'></DIV>
  <script type="text/javascript">
   var tipTimer;
   function locateObject(n, d)
   {
      var p,i,x;
      if (!d) d=document;
      if ((p=n.indexOf('?')) > 0 && parent.frames.length)
      {d=parent.frames[n.substring(p+1)].document; n=n.substring(0,p);}
      if (!(x=d[n])&&d.all) x=d.all[n]; 
      for (i=0;!x&&i<d.forms.length;i++) x=d.forms[i][n];
      for (i=0;!x&&d.layers&&i<d.layers.length;i++) x=locateObject(n,d.layers[i].document); return x;
   }
   function ShowADPreview(ADContent)
   {
     showTooltip('dHTMLADPreview',event, ADContent, '#ffffff','#000000','#000000','6000')
   }
   function showTooltip(object, e, tipContent, backcolor, bordercolor, textcolor, displaytime)
   {
      window.clearTimeout(tipTimer)
      if (document.all) {
          locateObject(object).style.top=document.body.scrollTop+event.clientY+20
          locateObject(object).innerHTML='<table style="font-family:宋体; font-size: 9pt; border: '+bordercolor+'; border-style: solid; border-top-width: 1px; border-right-width: 1px; border-bottom-width: 1px; border-left-width: 1px; background-color: '+backcolor+'" width="10" border="0" cellspacing="0" cellpadding="0"><tr><td nowrap><font style="font-family:宋体; font-size: 9pt; color: '+textcolor+'">'+unescape(tipContent)+'</font></td></tr></table> '
          if ((e.x + locateObject(object).clientWidth) > (document.body.clientWidth + document.body.scrollLeft)) {
              locateObject(object).style.left = (document.body.clientWidth + document.body.scrollLeft) - locateObject(object).clientWidth-10;
          } else {
              locateObject(object).style.left=document.body.scrollLeft+event.clientX
          }
          locateObject(object).style.visibility='visible';
          tipTimer=window.setTimeout("hideTooltip('"+object+"')", displaytime);
          return true;
      } else if (document.layers) {
          locateObject(object).document.write('<table width="10" border="0" cellspacing="1" cellpadding="1"><tr bgcolor="'+bordercolor+'"><td><table width="10" border="0" cellspacing="0" cellpadding="0"><tr bgcolor="'+backcolor+'"><td nowrap><font style="font-family:宋体; font-size: 9pt; color: '+textcolor+'">'+unescape(tipContent)+'</font></td></tr></table></td></tr></table>')
          locateObject(object).document.close()
          locateObject(object).top=e.y+20
          if ((e.x + locateObject(object).clip.width) > (window.pageXOffset + window.innerWidth)) {
              locateObject(object).left = window.innerWidth - locateObject(object).clip.width-10;
          } else {
              locateObject(object).left=e.x;
          }
          locateObject(object).visibility='show';
          tipTimer=window.setTimeout("hideTooltip('"+object+"')", displaytime);
          return true;
      } else {
          return true;
      }
   }
   function hideTooltip(object) {
       if (document.all) {
           locateObject(object).style.visibility = 'hidden';
           locateObject(object).style.left = 1;
           locateObject(object).style.top = 1;
           return false;
       } else {
           if (document.layers) {
               locateObject(object).visibility = 'hide';
               locateObject(object).left = 1;
               locateObject(object).top = 1;
               return false;
           } else {
               return true;
           }
       }
   }   
  </script>  
 </pub:template>
 
 <pub:template name="temp_show_order_js">
  <script type="text/javascript">
   setTimeout('Change()',1000);
   function Change(){
    var Sort=document.getElementById("Sort#{sortBy }");
       Sort.src="images/Calendar_#{iif(priorSort<0,"Down","Up") }.gif";
       Sort.style.display="";  
   } 
  </script>
 </pub:template>
 
<pub:template name="temp_form_begin">
 <form name='myform' method='Post' action='admin_upload_operate.jsp?curPath=#{curDirPath }'>
</pub:template>
 
<pub:template name="temp_form_end">
 </form>  
</pub:template>
 
<pub:template name="temp_form_hidden">
  <input type='hidden' name='priorsort' value='#{priorSort }'/>
  <input type='hidden' name='sortby' value='-1'/>
  <input type='hidden' name='channelId' value='#{channelId }'/>
  <input type='hidden' name='selectFile' value='#{selectFile }'/>
  <input type='hidden' name='page' value='#{page }'/>
</pub:template>
 
 
<pub:process_template name="temp_main"/>
 
 </body>
</html>
