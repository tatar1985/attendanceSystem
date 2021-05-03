<!DOCTYPE HTML>
<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%
	String userName = (String)request.getParameter("userName");
	String unitLongId = (String)request.getParameter("unitLoId");
	String unitLaId = (String)request.getParameter("unitLaId");
	String beanLoId = (String)request.getParameter("beanLoId");
	String beanLaId = (String)request.getParameter("beanLaId");
	String checkTime = (String)request.getParameter("checkTime");

%>

<html>
<style>
.other{ width:100%;height:800px; position:relative; float:left; background-color:#fff;margin-top:0px; }
</style>
<head>
<script type="text/javascript" src="http://webapi.amap.com/maps?v=1.4.6&key=f1da219022183a93f81e9307a423fec2"></script> 
<script src="http://webapi.amap.com/maps?v=1.4.6&key=f1da219022183a93f81e9307a423fec2&callback=init"></script>
<title>用户查看</title>
</head>
<body>
<div class="pd-20">
  <form action="<%=request.getContextPath()%>/tatar_/tatar.do" method="post" class="form form-horizontal" id="tatarForm" name="tatarForm">
  <input type="hidden" name="method" value="queryInOutLeave"/> 
     
  </form>
</div>

<div id="container" style="width:550px; height:410px" align="center"></div>
 

<script>
var map = new AMap.Map('container',{
   zoom: 15,
   center: [<%=unitLongId %>,<%=unitLaId%>]
});
 map.plugin(["AMap.ToolBar"], function() {
            map.addControl(new AMap.ToolBar());
        });
 var circle = new AMap.Circle({
    center: [<%=unitLongId%>,<%=unitLaId%>],
    radius: 1000,
    fillOpacity:0.2,
    strokeWeight:1
})
circle.setMap(map);
var marker = new AMap.Marker({
    position: [<%=beanLoId %>, <%=beanLaId %>]
});
marker.setMap(map);
map.setFitView()
var info = new AMap.InfoWindow({
    content:"<%=userName%>：<%=checkTime %>",
    offset:new AMap.Pixel(0,-28)
})
info.open(map,marker.getPosition())
</script>
</body>
</html>