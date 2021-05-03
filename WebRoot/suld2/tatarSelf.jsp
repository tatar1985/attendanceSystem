<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ page import="com.tataren.tatar.domain.TUsers"%>
<%
TUsers webUser=(TUsers)request.getSession().getAttribute("webUser");
 %>
<html lang="zh-CN">
<head>
<meta charset="utf-8">
<script src="<%=request.getContextPath()%>/suld/js/jquery.js"></script>
<link href="<%=request.getContextPath()%>/suld/css/css.css" rel="stylesheet" type="text/css">
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
<meta name="format-detection" content="telephone=no" />
<title>个人中心</title>
<style>
.right{ height:100%; width:100%; background-color:#f9f9f9; position:absolute;top:0; right:0;}
/*头像、点击登录*/
.login{ height:110px; background-color:#01aff0; position:relative}
.login > img{ width:12px; height:auto; position:absolute; left:5%; top:10px;}
.login_photo{ width:48px; height:48px; background-color:#fff; float:left; margin-left:5%; margin-top:50px; border-radius:50%; overflow:hidden;}
.login_photo img{ width:48px; height:48px;}
.login p{ float:left; line-height:48px; margin-top:55px; font-size:16px;color:#fff; margin-left:13px;}
/*菜单栏*/
.column{ height:46px; position:relative; background:#fff;}
.col_img{ width:18px; height:18px; float:left; margin-left:5%; margin-top:14px;}
.col_img img{ width:18px; height:18px;}
.column p{ line-height:46px; float:left; color:#191919; font-size:16px; margin-left:14px;}
.amount{ width:8px; height:8px; position:absolute; left:5%; top:11px; background-color:#c40000; border-radius:50%; margin-left:15px;}
/*选择头像*/
.photo_box{ width:100%; height:138px; background-color:#fff; position:fixed; left:0; bottom:0;}
.photo{ height:46px; line-height:46px; text-align:center; position:relative}
/*底部导航*/
.nav_box{ width:100%; height:51px; position:fixed; left:0; bottom:0; background-color:#fff;}
.nav_index,.nav_order,.nav_my{ width:33.3%; float:left;}
.nav_box img{ width:18px; height:auto; position:relative; left:50%; margin-left:-9px; margin-top:8px;}
.nav_box h1{ font-size:12px; text-align:center; color:#65646b; line-height:25px;}
.nav_box .current_nav{ color:#01aff0;}

</style>
</head>

<body style="background-color: #f9f9f9;">
 <form class="form form-horizontal" action="<%=request.getContextPath()%>/suld_/suld.do" method="post"  id="tatarSelf" name="tatarSelf">
    <input type="hidden" name="method" id="method" value="mobileInoutQuery"/>
    <input type="hidden" name="sid" id="sid" value="<%=webUser.getSid() %>"/>
  	<input type="hidden" name="opendId" id="opendId" value="<%=webUser.getOpenId() %>"/>
    
  
<div class="right">
	<!--头像、点击登录-->
	<div class="login">
    	<img src="<%=request.getContextPath()%>/suld/images/return1.png" onClick="window.history.go(-1)">
        <div class="login_photo" ><img src="<%=webUser.getHeadUrl() %>"></div>
        <p><%=webUser.getUserName()==null?webUser.getChatName():webUser.getUserName() %></p>
        <div class="border_bottom"></div>
    </div>
    <!--菜单栏-->
     <div class="column" onclick="document.location='<%=request.getContextPath()%>/suld/tatarEditSelf.jsp';">
    	<div class="col_img"><img src="<%=request.getContextPath()%>/suld/images/people.png"></div>
        <p>基本信息</p>
        <div class="border_bottom"></div>
    </div>
    <div class="column" id="secondQueryLeave">
    	<div class="col_img"><img src="<%=request.getContextPath()%>/suld/images/wallet.png"></div>
        <p>请假记录</p>
        <div class="border_bottom"></div>
    </div>
   
    <div class="column" id="mobileInoutQuery">
    	<div class="col_img"><img src="<%=request.getContextPath()%>/suld/images/order_3.png"></div>
        <p>签到记录</p>
        <div class="border_bottom"></div>
    </div>
    
     <div class="column" onclick="document.location='<%=request.getContextPath()%>/hurd_/hurd.do?method=taskQuery';">
    	<div class="col_img"><img src="<%=request.getContextPath()%>/suld/images/time.png"></div>
        <p>任务记录</p>
        <div class="border_bottom"></div>
    </div>
    
    <div class="column" onclick="document.location='<%=request.getContextPath()%>/suld/tatarEditCode.jsp';">
    	<div class="col_img"><img src="<%=request.getContextPath()%>/suld/images/set.png"></div>
        <p>后台信息</p>
        <div class="border_bottom"></div>
    </div>
    
    <div class="margin"></div>    
        <div class="column" id="adminSecondList">
    	<div class="col_img"><img src="<%=request.getContextPath()%>/suld/images/message.png"></div>
        <p>待审批单</p>
        <div class="amount"></div>
        <div class="border_bottom"></div>
        <div class="border_top"></div>
    </div>  
   
    <div class="margin"></div>
    <div class="column" onclick="document.location='<%=request.getContextPath()%>/suld/tatarRemark.jsp';">
    	<div class="col_img"><img src="<%=request.getContextPath()%>/suld/images/share.png"></div>
        <p>意见建议</p>
        <div class="border_bottom"></div>
        <div class="border_top"></div>
    </div>
     <div class="margin"></div>
   
</div>
<!--底部导航-->
<div class="nav_box">
	<div class="border_top"></div>
	<div class="nav_index chenge" onclick="document.location='<%=request.getContextPath()%>/hurd_/hurd.do?method=contactQuery'">
    	<img src="<%=request.getContextPath()%>/suld/images/phone_index.png".png">
        <h1 style="">通讯</h1>
    </div>
    <!--
  <div class="nav_order chenge" id="linkAddLeave" name="linkAddLeave">
    -->
     <div class="nav_order chenge" onclick="document.location='<%=request.getContextPath()%>/hurd_/hurd.do?method=locationQuery'">
    	<img src="<%=request.getContextPath()%>/suld/images/adrress.png">
        <h1>位置</h1>
    </div>
    <div class="nav_my chenge"  >
    	<img src="<%=request.getContextPath()%>/suld/images/my_blue.png">
        <h1 style=" color:#01AFF0">我的</h1>
    </div>
</div>
</form>
<script>
$("#linkAddLeave").click(function(){
		
 		$("#method").val("secondEnterLeaveAdd");	
		$("#tatarSelf").submit();
	
});

$("#mobileInoutQuery").click(function(){
		
 		$("#method").val("mobileInoutQuery");	
		$("#tatarSelf").submit();secondLeaveSubmitAdmin
	
});
$("#adminSecondList").click(function(){
		
 		$("#method").val("secondLeaveSubmitAdmin");	
		$("#tatarSelf").submit();
	
});
$("#secondQueryLeave").click(function(){
		
 		$("#method").val("secondQueryLeaveList");	
		$("#tatarSelf").submit();
	
});
</script>
</body>
</html>
