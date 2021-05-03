<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ page import="com.tataren.tatar.domain.TUsers"%>
<%
TUsers webUser=(TUsers)request.getSession().getAttribute("webUser");
 %>
<html lang="zh-CN">
<script src="js/jquery.js"></script>
<link href="css/css.css" rel="stylesheet" type="text/css">
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
<meta name="format-detection" content="telephone=no" />
<meta charset="utf-8">
<title>意见建议</title>
<style>
/*服务态度、服务质量*/
.evaluate{ height:72px; background-color:#fff; position:relative; margin-top:14px; padding-left:4%; padding-top:8px; box-sizing:border-box}
.evaluate p{ line-height:27px; color:#65646b; font-size:14px; float:left}
/*文字评价*/
.say_more{ height:160px; position:relative; margin-top:14px; background-color:#fff;}
textarea{ height:160px; width:92%; margin-left:4%; line-height:22px; color:#191919;}
/*评价成功*/
.success{ margin-top:46px;}
.success h1{ text-align:center; font-size:16px; line-height:28px; color:#191919;}
.success h2{ text-align:center; font-size:12px; line-height:27px; color:#65646b;}
/*分享框*/
.black{ z-index:3}
.share_box{ width:100%; height:126px; background-color:#fff; position:fixed; left:0; bottom:-140px; z-index:4}
.share_title{ position:relative; height:41px; line-height:41px; color:#65646b; text-align:center; font-size:14px;}
.share_title img{ width:9px; height:9px; position:absolute; left:4%; top:14px;}
.share,.share1{ width:50%; float:left}
.share img,.share1 img{margin-top:15px; width:33px; height:auto}
.share img{float:right; margin-right:22%;} 
.share1 img{float:left; margin-left:22%;}
.share p,.share1 p{ position:relative; bottom:0; font-size:12px; color:#65646b; line-height:36px;}
.share p{ clear:right; text-align:right; right:22%; margin-right:-5px;}
.share1 p{ clear:left; text-align:left; left:22%; margin-left:-2px;}
</style>
</head>

<body style="background-color: #f9f9f9;">
 <form class="form form-horizontal" action="<%=request.getContextPath()%>/suld_/suld.do" method="post"  id="tatarForm" name="tatarForm">
    <input type="hidden" name="method" id="method" value="selfRemarkMobile"/>
     <input type="hidden" name="sid" id="sid" value="<%=webUser.getSid() %>"/>

<!--header顶部标题-->
<div class="header">建议
	<div class="header_left" onclick="window.history.go(-1)"><img src="<%=request.getContextPath()%>/suld/images/return.png"></div>
</div>
<!--阿姨信息-->
<div class="waiter_box">
	<div class="border_top"></div>
	<div class="waiter_img"><img src="<%=webUser.getHeadUrl() %>"></div>
    <div class="waiter">
    	<h1><%=webUser.getUserName()==null?webUser.getChatName():webUser.getUserName() %></h1>
        <h2>等级：
        	<div class="star_box" style=" position:absolute; top:0; right:-85px; margin-top:0">
                <img src="images/star.png">
                <img src="images/star.png">
                <img src="images/star.png">
                <img src="images/star.png">
                <img src="images/star_gray.png">
            </div>
        </h2>
        <p>  </p>
    </div>
    <p class="waiter_id">手机：<%=webUser.getMobile() %></p> 
    <div class="border_bottom"></div>
</div>

<!--文字评价-->
<div class="say_more">
	<div class="border_top"></div>
	<textarea placeholder="请写入意见建议" maxlength=225 id="remark" name="remark"></textarea>
    <div class="border_bottom"></div>
</div>
<!--提交-->
	<div class="confirm" style=" background:#01AEF0" id="buttonSub" name="buttonSub">提交</div>
</div>

<script>
//五星评价:服务态度
$("#buttonSub").click(function(){
	
 	if($("#remark").val()==""){
		alert("姓名不能为空");
		return ;	
		}
	$("#tatarForm").submit();
	alert("成功提交");
});
</script>
</form>
</body>
</html>

