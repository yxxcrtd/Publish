<%@ page language="java" contentType="text/html; charset=gb2312"
    pageEncoding="UTF-8"%>
<%@taglib prefix="pub" uri="/WEB-INF/publish.tld" %>

<!-- 会员控制面板的顶部 -->
<!--频道显示代码-->
<!--网站Logo和banner显示代码-->
<pub:template name="tmpl_top">
<table class='top_tdbgall' style='word-break: break-all' cellSpacing='0' cellPadding='0' width='760' align='center' border='0'>
  <tr>
    <td><table width='100%' border='0' cellpadding='0' cellspacing='0' background='images/contmenu_bg.gif'>
      <tr>
        <td width='8'><img src='images/contmenu1.gif' width='8' height='45'></td>
        <td width='160' align='right'><img src='images/contmenu.gif' width='151' height='45'></td>
        <td style="padding-top: 15px;">
        #{call tmpl_main_menu }
  </td>
          </tr>
        </table>
        </td>
        <td width='6'><img src='images/contmenu2.gif' width='6' height='45'></td>
      </tr>
    </table></td>
  </tr>
</table>
</pub:template>

<!-- 会员控制面板的底部 -->
<pub:template name="tmpl_bottom">
<table cellSpacing=0 cellPadding=0 width=778 align=center border=0>
  <tr>
    <td class=menu_bottombg align=middle>
      | <a href="#" onClick="this.style.behavior='url(#default#homepage)';this.setHomePage('#{site.url }');">设为首页</a>
      | <a href="javascript:window.external.addFavorite('#{site.url }','#{site.name }');">加入收藏</a>
      | <a href="mailto:#{site.webmasterEmail }">联系站长</a>
      | <a href="friendSite.jsp" target="_blank">友情链接</a>
      | <a href='../admin/admin_index.jsp' target='_blank'>管理登录</a>
      |
    </td>
  </tr>
  <tr>
    <td class=bottom_bg height=80>
      <table cellSpacing=0 cellPadding=0 width="90%" align=center border=0>
        <tr>
          <td><IMG height=80 src="images/bottom_left.gif" width=9></td>
          <td align=middle width="80%"> 版权所有 &copy; 2003-2006</td>
          <td align=right><IMG height=80 src="images/bottom_r.gif" width=9></td>
        </tr>
    </table></td>
  </tr>
</table>
</pub:template>

<!-- 生成主菜单的 JS 。 -->
<pub:template name="tmpl_main_menu">
<script type="text/javascript">
<!--
 stm_bm(['uueoehr',400,'','#{InstallDir}images/blank.gif',0,'','',0,0,0,0,0,1,0,0]);
 stm_bp('p0',[0,4,0,0,2,2,0,0,100,'filter:Glow(Color=#000000, Strength=3)',4,'',23,50,0,0,'#000000','transparent','',3,0,0,'#000000']);
 stm_ai('p0i0',[0,'','','',-1,-1,0,'','_self','','','','',0,0,0,'','',0,0,0,0,1,'#F1F2EE',1,'#CCCCCC',1,'','',3,3,0,0,'#FFFFF7','#FF0000','#ffffff','#ffff00','9pt 宋体','9pt 宋体','9pt 宋体','9pt 宋体']);
 stm_aix('p0i1','p0i0',[0,'会员中心首页','','',-1,-1,0,'index.jsp','_self','index.jsp','','','',0,0,0,'','',0,0,0,0,1,'#F1F2EE',1,'#CCCCCC',1,'','',3,3,0,0,'#FFFFF7','#FF0000','#ffffff','#ffff00','9pt 宋体','9pt 宋体']);
 stm_aix('p0i2','p0i0',[0,'|','','',-1,-1,0,'','_self','','','','',0,0,0,'','',0,0,0,0,1,'#F1F2EE',1,'#CCCCCC',1,'','',3,3,0,0,'#FFFFF7','#FF0000','#ffffff','#ffff00','9pt 宋体','9pt 宋体']);
 stm_aix('p0i3','p0i0',[0,'信息管理','','',-1,-1,0,'','_self','','','','',0,0,0,'','',0,0,0,0,1,'#F1F2EE',1,'#CCCCCC',1,'','',3,3,0,0,'#FFFFF7','#FF0000','#ffffff','#ffff00','9pt 宋体','9pt 宋体']);
 stm_bp('p1',[1,4,0,6,2,3,6,7,100,'filter:Glow(Color=#000000, Strength=3)',4,'',23,50,2,4,'#999999','#0089F7','',3,1,1,'#ACA899']);
#{foreach channel in channels}
 stm_aix('p1i0','p0i0',[0,'#{channel.name}','','',-1,-1,0,'user_article_list.jsp?channelId=#{channel.id}','_self','user_article_list.jsp?channelId=#{channel.id}','','','',6,0,0,'#{InstallDir}images/arrow_r.gif','#{InstallDir}arrow_w.gif',7,7,0,0,1,'#F1F2EE',1,'#CCCCCC',1,'','',3,3,0,0,'#FFFFF7','#FF0000','#ffffff','#ffff00','9pt 宋体','9pt 宋体']);
 stm_bpx('p2','p1',[1,2,-2,-3,2,3,0,7,100,'filter:Glow(Color=#000000, Strength=3)',4,'',23,50,2,4,'#999999','#0089F7','',3,1,1,'#ACA899']);
 #{if (user.inputer) }
 stm_aix('p2i0','p1i0',[0,'添加#{channel.itemName}','','',-1,-1,0,'user_article_add.jsp?channelId=#{channel.id}','_self','user_article_add.jsp?channelId=#{channel.id}','','','',0,0,0,'','',0,0,0,0,1,'#F1F2EE',1,'#CCCCCC',1,'','',3,3,0,0,'#FFFFF7','#FF0000','#ffffff','#ffff00','9pt 宋体','9pt 宋体']);
 #{/if }
 stm_aix('p2i0','p1i0',[0,'我添加的#{channel.itemName}','','',-1,-1,0,'user_article_list.jsp?channelId=#{channel.id}','_self','user_article_list.jsp?channelId=#{channel.id}','','','',0,0,0,'','',0,0,0,0,1,'#F1F2EE',1,'#CCCCCC',1,'','',3,3,0,0,'#FFFFF7','#FF0000','#ffffff','#ffff00','9pt 宋体','9pt 宋体']);
// stm_aix('p2i0','p1i0',[0,'我评论的#{channel.itemName}','','',-1,-1,0,'user_comment.jsp?channelId=#{channel.id}','_self','user_comment.jsp?channelId=#{channel.id}','','','',0,0,0,'','',0,0,0,0,1,'#F1F2EE',1,'#CCCCCC',1,'','',3,3,0,0,'#FFFFF7','#FF0000','#ffffff','#ffff00','9pt 宋体','9pt 宋体']);
 stm_ep();
#{/foreach}
 stm_ep();
 stm_ep();
 stm_aix('p0i2','p0i0',[0,'|','','',-1,-1,0,'','_self','','','','',0,0,0,'','',0,0,0,0,1,'#F1F2EE',1,'#CCCCCC',1,'','',3,3,0,0,'#FFFFF7','#FF0000','#ffffff','#ffff00','9pt 宋体','9pt 宋体']);
 stm_aix('p0i0','p0i0',[0,'修改密码','','',-1,-1,0,'user_password_change.jsp','_self','user_password_change.jsp','','','',0,0,0,'','',0,0,0,0,1,'#F1F2EE',1,'#CCCCCC',1,'','',3,3,0,0,'#FFFFF7','#FF0000','#ffffff','#ffff00','9pt 宋体','9pt 宋体']);
 stm_ep();
 stm_aix('p0i2','p0i0',[0,'|','','',-1,-1,0,'','_self','','','','',0,0,0,'','',0,0,0,0,1,'#F1F2EE',1,'#CCCCCC',1,'','',3,3,0,0,'#FFFFF7','#FF0000','#ffffff','#ffff00','9pt 宋体','9pt 宋体']);
 stm_aix('p0i0','p0i0',[0,'退出登录','','',-1,-1,0,'user_logout.jsp','_self','user_logout.jsp','','','',0,0,0,'','',0,0,0,0,1,'#F1F2EE',1,'#CCCCCC',1,'','',3,3,0,0,'#FFFFF7','#FF0000','#ffffff','#ffff00','9pt 宋体','9pt 宋体']);
 stm_ep();
 stm_ep();
 stm_em();
 
// -->
</script>
</pub:template>
