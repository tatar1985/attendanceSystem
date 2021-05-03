<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ page import="com.tataren.tatar.domain.TInout"%>
<%@ page import="java.util.Iterator"%>
<%@ page import="java.util.List"%>
<%
List list = (List) request.getAttribute("list");
 %>
<html lang="zh-CN">
<head>
<meta charset="utf-8">
<script type="text/javascript" src="http://webapi.amap.com/maps?v=1.3&amp;key=f1da219022183a93f81e9307a423fec2"></script> 
<script src="http://webapi.amap.com/maps?v=1.3&amp;key=f1da219022183a93f81e9307a423fec2&amp;callback=init"></script>
<script src="<%=request.getContextPath()%>/suld/js/jquery.js"></script>
<link href="<%=request.getContextPath()%>/suld/css/css.css" rel="stylesheet" type="text/css">
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
<meta name="format-detection" content="telephone=no" />
<title>签到记录查询</title>
<style>

/***********选择服务时间:弹出层**********/
#option_time{ width:100%; height:100%; top:0; right:0; position:absolute; background:#fff;}
.week_box{ height:62px; background-color:#fff; position:relative; margin-bottom:8px;}
.week{ height:62px; width:13.9%; float:left}
.tatar{ float:left; width:13.9%;}
.week h1{ line-height:22px; font-size:14px; text-align:center; margin-top:11px;}
.week h2{ line-height:12px; font-size:12px; color:#65646b; text-align:center;}
.blue{ width:14.3%; position:absolute; left:0; bottom:0; height:2px; background-color:#01aff0;}
.time_box{ margin-bottom:80px; }
.time{ width:80%; float:left; margin-left:4%; margin-top:6px; border:1px solid #ccc; border-radius:3px; text-align:center; line-height:40px; height:40px; color:#b2bab8; box-sizing:border-box;}
.option {
    border: 1px solid #01aff0;
    color:#191919;
    background-image:url(images/checkbox.png);
    background-size: 12px 12px;
    background-position: right bottom;
    background-repeat: no-repeat;
}
.other{ width:100%;height:150px; position:relative; float:left; background-color:#fff;margin-top:320px; }
/*底部导航*/
.nav_box{ width:100%; height:51px; position:fixed; left:0; bottom:0; background-color:#fff;}
.nav_index,.nav_order,.nav_my{ width:33.3%; float:left;}
.nav_box img{ width:18px; height:auto; position:relative; left:50%; margin-left:-9px; margin-top:8px;}
.nav_box h1{ font-size:12px; text-align:center; color:#65646b; line-height:25px;}
.nav_box .current_nav{ color:#01aff0;}
</style>
</head>
<body>
<!--header顶部标题-->
<div id="option_time">
<div class="header">签到记录
	<div class="header_left"  onclick="window.history.go(-1)"><img src="<%=request.getContextPath()%>/suld/images/return.png"></div>
    <div class="header_right" id="open_sta" ></div>
</div>
	<!--header顶部标题-->
   
    <div class="week_box">
    <%
	for (Iterator iter = list.iterator(); iter.hasNext();) {
		TInout bean = (TInout) iter.next();
	%>
		<div class="week" >
            <h1><%=bean.getWEEK() %></h1>
            <h2><%=bean.getDAYTIME() %></h2>
        </div>			
		<%
	}
		%>
        <div class="border_bottom"></div>
    </div>
    <div class="time_box" id="option_box">
     <%
	for (Iterator iter = list.iterator(); iter.hasNext();) {
		TInout bean = (TInout) iter.next();
		%>
		<div class="tatar">
		<% 
		if(bean.getFIRSTCHECKSTATE().equals("已签到")){
		%>
			<div class="time" onClick="init(<%=bean.getFIRSTCHECKLATIID() %>,<%=bean.getFIRSTCHECKLONGID() %>)"><img src="<%=request.getContextPath()%>/suld/images/in4.png"></div>
		<%}else{
		%>
			<div class="time" ></div>
		<% 
		}
		
		if(bean.getTWOCHECKSTATE().equals("已签退")){
		%>
			<div class="time" onClick="init(<%=bean.getTWOCHECKLATIID() %>,<%=bean.getTWOCHECKLONGID() %>)"><img src="<%=request.getContextPath()%>/suld/images/in4.png"></div>
		<%}else{
		%>
			<div class="time" ></div>
		<% }
		
		if(bean.getTHREECHECKSTATE().equals("已签到")){
		%>
			<div class="time" onClick="init(<%=bean.getTHREECHECKLATIID() %>,<%=bean.getTHREECHECKLONGID() %>)"><img src="<%=request.getContextPath()%>/suld/images/in4.png"></div>
		<%}else{
		%>
			<div class="time" ></div>
		<% 
		}
		
		if(bean.getFOURCHECKSTATE().equals("已签退")){
		%>
			<div class="time" onClick="init(<%=bean.getFOURCHECKLATIID() %>,<%=bean.getFOURCHECKLONGID() %>)"><img src="<%=request.getContextPath()%>/suld/images/in4.png"></div>
		<%}else{
		%>
			<div class="time" ></div>
		<% 
		}		
		%>
		</div>
		<%
		}
	

	 %> 
    </div>
</div>
<div class="other" id="container"></div>

<div class="nav_box">
	<div class="border_top"></div>
	<div class="nav_index chenge" onclick="document.location='<%=request.getContextPath()%>/hurd_/hurd.do?method=contactQuery'">
    	<img src="<%=request.getContextPath()%>/suld/images/phone_index.png".png">
        <h1 style="">通讯</h1>
    </div>
     <div class="nav_order chenge" onclick="document.location='<%=request.getContextPath()%>/hurd_/hurd.do?method=locationQuery'">
    	<img src="<%=request.getContextPath()%>/suld/images/adrress.png">
        <h1>位置</h1>
    </div>
    <div class="nav_my chenge" onclick="document.location='<%=request.getContextPath()%>/suld/tatarSelf.jsp';" >
    	<img src="<%=request.getContextPath() %>/suld/images/my_gray.png">
        <h1>我的</h1>
    </div>
</div>
<script>
function init(a,b){
	  var map = new AMap.Map('container', {
    	center: [b, a]  ,
        zoom: 16
    });
	marker = new AMap.Marker({
         icon: "http://webapi.amap.com/theme/v1.3/markers/n/mark_b.png",
         position: [b, a]
    });
        marker.setMap(map);
  
 }


</script>
</body>
</html>