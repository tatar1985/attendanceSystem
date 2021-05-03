<!DOCTYPE HTML>
<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.tataren.tatar.domain.TSign"%>
<%
	String headUrl = (String)request.getAttribute("headUrl");
	String userName = (String)request.getAttribute("userName");
	String trueName = (String)request.getAttribute("trueName");
	String unitName = (String)request.getAttribute("unitName");
	String departMent = (String)request.getAttribute("departMent");
	String mapId = (String)request.getAttribute("mapId");
%>

<html>
<style>
.other{ width:100%;height:350px; position:relative; float:left; background-color:#fff;margin-top:0px; }
</style>
<head>
<meta charset="utf-8">
<meta name="renderer" content="webkit|ie-comp|ie-stand">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
<meta http-equiv="Cache-Control" content="no-siteapp" />
<!--[if lt IE 9]>
<script type="text/javascript" src="lib/html5.js"></script>
<script type="text/javascript" src="lib/respond.min.js"></script>
<script type="text/javascript" src="lib/PIE_IE678.js"></script>
<![endif]-->
<link href="../css/H-ui.min.css" rel="stylesheet" type="text/css" />
<link href="../css/H-ui.admin.css" rel="stylesheet" type="text/css" />
<link href="../lib/Hui-iconfont/1.0.1/iconfont.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="http://webapi.amap.com/maps?v=1.3&amp;key=f1da219022183a93f81e9307a423fec2"></script> 
<script src="http://webapi.amap.com/maps?v=1.3&amp;key=f1da219022183a93f81e9307a423fec2&amp;callback=init"></script>
<!--[if IE 6]>
<script type="text/javascript" src="http://lib.h-ui.net/DD_belatedPNG_0.0.8a-min.js" ></script>
<script>DD_belatedPNG.fix('*');</script>
<![endif]--><title>用户查看</title>
</head>
<body>
<div class="cl pd-20" style=" background-color:#5bacb6">
  <img class="avatar size-XL l" src="<%=headUrl %>">
  <dl style="margin-left:80px; color:#fff">
    <dt><span class="f-18"><%=trueName==null?userName:trueName %></span>  <span class="pl-10 f-12"><%=unitName==null?"":unitName %></span></dt>
    <dd class="pt-10 f-12" style="margin-left:0"><%=departMent==null?"":departMent %></dd>
  </dl>
</div>
<div class="pd-20">
  <table class="table">
  	<thead>
		<tr class="text-c">
			<th>日期</th>
			<th>当日状态</th>
			<th>上午签到</th>
			<!-- <th>签到地址</th> -->
			<th>上午签退</th>
			<!--<th>签退地址</th> -->
			<th>下午签到</th>
			<!--<th>签到地址</th> -->	
			<th>下午签退</th>
			<!--<th>签退地址</th> -->
			</tr>
		</thead>
    <tbody>
      <%
 	ArrayList<TSign> list =( ArrayList<TSign>) request.getAttribute("list");
 	if(list!=null){
		for(int m=0 ;m < list.size();m++){	
			TSign bean = list.get(m);	
%>
			<tr class="text-c">
				<td><%=bean.getWeekName() %></td>
				<td><%=bean.getWorkState() %></td>
				<td><%=bean.getFirstState() %></td>
				<!-- <td><%=bean.getFirstAddress() %></td> -->
				<td><%=bean.getTwoState() %></td>
				<!--<td><%=bean.getTwoAddress() %></td> -->
				<td><%=bean.getThreeState() %></td>
				<!--<td><%=bean.getThreeAddress() %></td> -->
				<td><%=bean.getFourState() %></td>
				<!--<td><%=bean.getFourAddress() %></td> -->
	<%}} %>
    </tbody>
  </table>
</div>
<div class="other" id="container"></div>
<script type="text/javascript" src="../js/jquery.min.js"></script> 
<script type="text/javascript" src="../js/H-ui.js"></script>
<script type="text/javascript" src="../js/H-ui.admin.js"></script>
</body>
<script>
    var map = new AMap.Map("container", {
        resizeEnable: true,
        center: [108.57325, 38.042225],
        zoom: 8
    });

   var markers = [], positions = [<%=mapId %>];
    for (var i = 0, marker; i < positions.length; i++) {
        marker = new AMap.Marker({
            map: map,
            position: positions[i]
        });
        markers.push(marker);
    }
</script>
</html>