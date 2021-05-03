<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ page import="com.tataren.tatar.domain.TInout"%>
<%
TInout bean=(TInout)request.getAttribute("bean");
String signature = (String)request.getAttribute("signature");
request.setAttribute("signature", signature);

String impKind=(String)request.getAttribute("impKind");

 %>
<html lang="zh-CN">
<head>
<meta charset="utf-8">

<script src="<%=request.getContextPath()%>/suld/js/jquery.js"></script>
<script src="<%=request.getContextPath()%>/suld/js/swiper.min.js"></script>

<link href="<%=request.getContextPath()%>/suld/css/css.css" rel="stylesheet" type="text/css">
<link href="<%=request.getContextPath()%>/suld/css/swiper.min.css" rel="stylesheet" type="text/css">
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
<meta name="format-detection" content="telephone=no" />

<meta charset="utf-8">
<title>首页</title>
<style>
.header_left img{ width:18px; height:auto; position:absolute; left:4%; top:2px; margin-left:36px;}
.header_left{ font-size:16px;}

.banner_box{ width:320px; height:114px; margin:auto;}
.banner_box #banner img{ width:320px; height:100%}
.swiper-pagination-bullet{ background:#fff; opacity:0.6;}
.swiper-pagination-bullet-active{ background:#fff; opacity:1;}
.swiper-container-horizontal>.swiper-pagination-bullets{ bottom:3px; left:auto; right:4%; width:55px;}

@media screen and (max-width: 768px) {
.banner_box{ width:100%; }
.banner_box #banner img{ width:100%;}
	}
/*常用服务*/
.login{ height:150px; background-color:#01aff0; position:relative}
.login > img{ width:9px; height:auto; position:absolute; left:5%; top:15px;}
.login_photo{ width:100px; height:100px; background-color:#fff; float:left; margin-left:2%; margin-top:20px; border-radius:50%; overflow:hidden;}
.login_photo img{ width:100px; height:100px;}
.login p{ float:left; line-height:48px; margin-top:55px; font-size:16px;color:#fff; margin-left:13px;}
.regularly_title{ width:22%; height:41px; line-height:41px; position:relative; margin-left:auto; margin-right:auto; text-align:center; color:#b2bab8;}
.regularly_title .border_top{ width:160%; top:20px;}
/*日常保洁*/
.evaluate_btn{ background-color:#FF7F00; color:white; border-radius:3px; width:44%; height:33px; line-height:33px; text-align:center; font-size:16px; margin-left:4%;margin-top:14px; float:left}
.daily{ height:105px; background-color:#fff; position:relative;  background-size:40px auto; background-position:left top; background-repeat:no-repeat;}
.daily_img{ width:72px; height:auto; float:left; margin-left:7.5%; margin-top:17px;}
.daily h1{ line-height:30px; font-size:18px; color:#191919; margin-top:23px;}
.daily p{ line-height:28px; font-size:16px; color:#b2bab8;}
/*地板打蜡、窗户全面清洁、皮具打蜡*/
.other{ height:105px; position:relative; background-color:#fff; margin-top:12px;}
.floor,.window,.leather{ width:33.3%; float:left; position:relative;}
.other img{ width:48px; height:auto; position:relative; left:50%; margin-left:-24px; margin-top:14px;}
.other_title{ line-height:43px; text-align:center; color:#191919; font-size:14px;}
.other .border_right{ height:77px; top:14px;}
/*服务介绍、会员充值*/
.introduce_box{ height:46px; background-color:#fff; position:relative; margin-bottom:60px;}
.introduce_box .border_right{ height:33px; top:7px;}
.member,.introduce{ float:left; position:relative; width:50%;}
.introduce_box img{ width:25px; height:auto; float:left; margin-left:27%; margin-top:12px;}
.introduce_box h1{ line-height:46px; float:left; margin-left:6%; font-size:14px; color:#65646b;}
/*底部导航*/
.nav_box{ width:100%; height:51px; position:fixed; left:0; bottom:0; background-color:#fff;}
.nav_index,.nav_order,.nav_my{ width:33.3%; float:left;}
.nav_box img{ width:18px; height:auto; position:relative; left:50%; margin-left:-9px; margin-top:8px;}
.nav_box h1{ font-size:12px; text-align:center; color:#65646b; line-height:25px;}
.nav_box .current_nav{ color:#01aff0;}
</style>

</head>

<body style="background-color: #f9f9f9;">
 <form class="form form-horizontal" action="<%=request.getContextPath()%>/suld_/suld.do" method="post"  id="inOut" name="inOut">
    <input type="hidden" name="method" value="firstMobile"/>
    <input type="hidden" name="kindId" id="kindId" value="getLocation"/>
    <input type="hidden" name="signature" id="signature" value="<%=signature%>"/>
    <input type="hidden" name="impKind" id="impKind" value="<%=impKind==null?"1":impKind %>"/>
<!--header顶部标题-->

<!--banner广告-->
<div class="login">
	<div class="login_photo" ><img src="<%=bean.getHEADURL()==null?"":bean.getHEADURL() %>" ></div>
    <p><%=bean.getUSERNAME()==null?"":bean.getUSERNAME() %></p>
    <div class="border_bottom"></div>
</div>
<!--常用服务-->
<div class="regularly_title">签到服务
	<div class="border_top" style="left:-160%;"></div>
    <div class="border_top" style="left:inherit;right:-160%;"></div>
</div>
<!--日常保洁-->
<div class="daily" onclick="document.location='#';">
	<div class="border_top"></div>
        <img src="<%=request.getContextPath()%>/suld/images/address.png" class="daily_img">
        <div style="float:left; margin-left:13px;">
        	<h1><div class="evaluate_btn" onclick="" style="width:100px; " id="btnSubmit" name="btnSubmit" >我要签到</div></h1>
        	
            <p></p>
        </div>
    <div class="border_bottom"></div>
</div>

<div class="regularly_title">签到状态
	<div class="border_top" style="left:-160%;"></div>
    <div class="border_top" style="left:inherit;right:-160%;"></div>
</div>

<!--服务介绍、会员充值-->
<div class="introduce_box">
	<!--服务介绍-->
	<div class="introduce" >
		<%if(bean.getFIRSTCHECKSTATE().equals("已签到")){ %>
    	<img src="<%=request.getContextPath()%>/suld/images/yes.png">
    	 <input type="hidden" name="firstId" id="firstId" value="1"/>
        <h1>已签到</h1>
        <%}else{%>
        <img src="<%=request.getContextPath()%>/suld/images/check.jpg">
         <input type="hidden" name="firstId" id="firstId" value="0"/>
        <h1>未签退</h1>
        <%} %>
        <div class="border_right"></div>
    </div>
    <!--会员充值-->
    <div class="member" >
    	<%if(bean.getTWOCHECKSTATE().equals("已签到")){ %>
    	<img src="<%=request.getContextPath()%>/suld/images/yes.png">
    	 <input type="hidden" name="twoId" id="twoId" value="1"/>
        <h1>已签到</h1>
        <%}else{%>
        <img src="<%=request.getContextPath()%>/suld/images/check.jpg">
         <input type="hidden" name="twoId" id="twoId" value="0"/>
        <h1>未签退</h1>
        <% } %>
        <div class="border_right"></div>
    </div>
    <!--服务介绍-->
	<div class="introduce" >
    	<%if(bean.getTHREECHECKSTATE().equals("已签到")){ %>
    	<img src="<%=request.getContextPath()%>/suld/images/yes.png">
    	 <input type="hidden" name="threeId" id="threeId" value="1"/>
        <h1>已签到</h1>
        <%}else{%>
        <img src="<%=request.getContextPath()%>/suld/images/check.jpg">
        <input type="hidden" name="threeId" id="threeId" value="0"/>
        <h1>未签退</h1>
        <% } %>
        <div class="border_right"></div>
    </div>
    <!--会员充值-->
    <div class="member" >
    	<%if(bean.getFOURCHECKSTATE().equals("已签到") ){ %>
    	<img src="<%=request.getContextPath()%>/suld/images/yes.png">
    	<input type="hidden" name="fourId" id="fourId" value="1"/>
        <h1>已签到</h1>
        <%}else{%>
        <img src="<%=request.getContextPath()%>/suld/images/check.jpg">
        <input type="hidden" name="fourId" id="fourId" value="0"/>
        <h1>未签退</h1>
        <%} %>
        <div class="border_right"></div>
    </div>
    <div class="border_top"></div>
    <div class="border_bottom"></div>
</div>
<!--底部导航-->
<div class="nav_box">
	<div class="border_top"></div>
	<div class="nav_index chenge" onclick="document.location='<%=request.getContextPath()%>/suld/tatarIndex.jsp';">
    	<img src="<%=request.getContextPath()%>/suld/images/index_blue.png">
        <h1 style=" color:#01AFF0">首页</h1>
    </div>
    <div class="nav_order chenge" onclick="document.location='<%=request.getContextPath()%>/suld/tatarLeaveList.html';" >
    	<img src="<%=request.getContextPath()%>/suld/images/order_gray.png">
        <h1>审批</h1>
    </div>
    <div class="nav_my chenge" onclick="document.location='<%=request.getContextPath()%>/suld/tatarSelf.html';" >
    	<img src="<%=request.getContextPath() %>/suld/images/my_gray.png">
        <h1>我的</h1>
    </div>
</div>
<script type="text/javascript" src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
<script>
wx.config({
    debug: true, // 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。
    appId: 'wxf0112102bfde2449', // 必填，公众号的唯一标识
    timestamp:'2017171717' , // 必填，生成签名的时间戳
    nonceStr: 'tataren', // 必填，生成签名的随机串
    signature: '<%=signature%>',// 必填，签名，见附录1
    jsApiList: ['getLocation'] // 必填，需要使用的JS接口列表，所有JS接口列表见附录2
});
wx.ready(function(){
});

wx.error(function(res){
});

$("#btnSubmit").click(function(){
 	wx.getLocation({
        success: function (res) {
            alert(res.latitude + "，" + res.longitude);
        },
        fail: function(error) {
        }
    });
});

$("#btnSubmit2").click(function(){
	var myDate = new Date();
	var year=myDate.getFullYear();
	var month=myDate.getMonth()+1;
	var date=myDate.getDate(); 
	var h=myDate.getHours();       //获取当前小时数(0-23)
	var m=myDate.getMinutes();     //获取当前分钟数(0-59)
	var s=myDate.getSeconds();  
	if(5<h&&h<10){
		if($("#firstId").val()==1){
			alert("你已经签过了！");
		}else{
			$("#kindId").val("1");
			$("#inOut").submit();
		}
	}else if (h>=10&&h<13){
		if($("#twoId").val()==1){
			alert("你已经签过了！");
		}else{
			$("#kindId").val("2");
			$("#inOut").submit();
		}
	}else if (h>=13&&h<4){
		if($("#threeId").val()==1){
			alert("你已经签过了！");
		}else{
			$("#kindId").val("3");
			$("#inOut").submit();
		}
	}else{
		if($("#fourId").val()==1){
			alert("你已经签过了！");
		}else{
			$("#kindId").val("4");
			$("#inOut").submit();
		}
	}  
});

$(document).ready(function() { 
if($("#impKind").val()==1){

	$("#inOut").submit();
}
});

</script>
</form>
</body>
</html>

