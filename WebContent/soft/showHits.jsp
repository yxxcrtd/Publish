<%@ page language="java" contentType="text/html; charset=gb2312" pageEncoding="UTF-8"
%><%@taglib prefix="pub" uri="/WEB-INF/publish.tld"
%>

<pub:data provider="com.chinaedustar.publish.admin.SoftHitsDataProvider" purpose="提供有关软件的点击次数的数据"/>

<pub:template name="main">
document.write('&nbsp;本日：#{dayHits }　　&nbsp;本周：#{weekHits }<br>&nbsp;&nbsp;本月：#{monthHits }　&nbsp;　总计：#{hits }');
</pub:template>
<pub:process_template name="main"/>