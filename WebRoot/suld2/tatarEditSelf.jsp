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
<title>基本信息</title>
<style>
.right{ height:100%; width:100%; background-color:#f9f9f9; position:absolute;top:0; right:0;}
/*头像、点击登录*/
.login{ height:128px; background-color:#01aff0; position:relative}
.login > img{ width:9px; height:auto; position:absolute; left:5%; top:15px;}
.login_photo{ width:48px; height:48px; background-color:#fff; float:left; margin-left:5%; margin-top:55px; border-radius:50%; overflow:hidden;}
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
.column_box h1{ line-height:46px; float:left; color:#191919;}
.column_box input{ width:130px; margin-left:27px;}
/*评价成功*/
.success{ margin-top:46px;}
.success h1{ text-align:center; font-size:16px; line-height:28px; color:#191919;}
.success h2{ text-align:center; font-size:12px; line-height:27px; color:#65646b;}
</style>
</head>

<body style="background-color: #f9f9f9;">
 <form class="form form-horizontal" action="<%=request.getContextPath()%>/suld_/suld.do" method="post"  id="tatarForm" name="tatarForm">
    <input type="hidden" name="method" id="method" value="selfEditMobile"/>
    <input type="hidden" name="sid" id="sid" value="<%=webUser.getSid() %>"/>
  
    
  
<div class="right">
	<!--头像、点击登录-->
	<div class="login">
    	<img src="images/return1.png" onClick="window.history.go(-1)">
        <div class="login_photo" ><img src="<%=webUser.getHeadUrl()==null?"":webUser.getHeadUrl() %>"></div>
        <p><%=webUser.getChatName()==null?"":webUser.getChatName() %></p>
        <div class="border_bottom"></div>
    </div>
    <!--菜单栏-->
     <div class="column" >
    	<div class="column_box" style="margin-top:14px; margin-bottom:100px;">
			<div class="border_top"></div>
			<h1>真实姓名</h1>
			<input placeholder="真实姓名" name="trueName" id="trueName" value="<%=webUser.getUserName()==null?"":webUser.getUserName() %>">
    		<div class="border_bottom"></div>
		</div>
    </div>
     <div class="column" >
    	<div class="column_box" style="margin-top:14px; margin-bottom:100px;">
			<div class="border_top"></div>
			<h1>手机号码</h1>
			<input placeholder="手机号码" name="mobile" id="mobile" value="<%=webUser.getMobile()==null?"":webUser.getMobile() %>">
    		<div class="border_bottom"></div>
		</div>
    </div>
   
    <div class="column" >
    	<div class="column_box" style="margin-top:14px; margin-bottom:100px;">
			<div class="border_top"></div>
			<h1>单位名称</h1>
			<input placeholder="单位名称" name="unitName" id="unitName" value="<%=webUser.getUnitName()==null?"":webUser.getUnitName() %>" readonly style="width: 162px; ">
    		<div class="border_bottom"></div>
		</div>
    </div>
     <div class="column" >
    	<div class="column_box" style="margin-top:14px; margin-bottom:100px;">
			<div class="border_top"></div>
			<h1>科室名称</h1>
			<input placeholder="科室名称" name="department" id="department" value="<%=webUser.getDepartmentName()==null?"":webUser.getDepartmentName() %>">
    		<div class="border_bottom"></div>
		</div>
    </div>
    <div class="confirm" style=" background:#01AEF0" id="buttonSub" name="buttonSub">保存</div>
</div>


</div>
<!--底部导航-->
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
    	<img src="<%=request.getContextPath()%>/suld/images/my_blue.png">
        <h1  >我的</h1>
    </div>
</div>
</form>
<script>

$("#buttonSub").click(function(){

		if($("#trueName").val()==""){
		alert("姓名不能为空");
		return false;
		
	}else if($("#mobile").val()==""){
		alert("手机不能为空");
		return false;
	}else if($("#department").val()==""){
		alert("科室不能为空");
		return false;
	}
		$("#tatarForm").submit();
		alert("成功提交");
	
});


</script>
</body>
</html>
