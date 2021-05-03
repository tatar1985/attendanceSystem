<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ page import="com.tataren.tatar.domain.TInout"%>
<%@ page import="com.tataren.tatar.domain.TUsers"%>
<%
TInout bean=(TInout)request.getSession().getAttribute("bean");
TUsers webUser=(TUsers)request.getSession().getAttribute("webUser");
String signature = (String)request.getSession().getAttribute("signature");
//TInout bean=(TInout)request.getAttribute("bean");
//String signature = (String)request.getAttribute("signature");
System.out.println(webUser.getUserName()+"11"+webUser.getUnitName()+"22"+webUser.getDepartmentName());
if(webUser.getUserName()==null||webUser.getUnitName()==null||webUser.getDepartmentName()==null){
%>
<script>
alert("请先完善自己的个人信息");
window.location.href='<%=request.getContextPath() %>/suld/tatarEditSelf.jsp';
</script>

<% 
}
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
.introduce_box img{ width:20px; height:auto; float:left; margin-left:15%; margin-top:12px;}
.introduce_box h1{ line-height:46px; float:left; margin-left:6%; font-size:14px; color:#65646b;}
.introduce_box h2{ line-height:46px; float:left; margin-left:6%; font-size:9px; color:#65646b;}
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
    <input type="hidden" name="method" id="method" value="getMobileInoutBa"/>
    <input type="hidden" name="kindId" id="kindId" value="5"/>
    <input type="hidden" name="kindCheck" id="kindCheck" value="4"/>
    <input type="hidden" name="latitudeId" id="latitudeId" value=""/>
    <input type="hidden" name="longitudeId" id="longitudeId" value=""/>
    <input type="hidden" name="mapname" id="mapname" value=""/>
    <input type="hidden" name="opendId" id="opendId" value="<%=bean==null?"":bean.getUSERSID() %>"/>
    <input type="hidden" name="signature" id="signature" value="<%=signature==null?"":signature%>"/>
<!--header顶部标题-->

<!--banner广告-->
<div class="login">
	<div class="login_photo" ><img src="<%=bean==null?"":bean.getHEADURL() %>" ></div>
    <p><%=bean==null?"":bean.getUSERNAME() %></p>
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
	<%if(bean!=null){ %>
	<div class="introduce" >
		<%if(bean.getFIRSTCHECKSTATE().equals("已签到")){ %>
    	<img src="<%=request.getContextPath()%>/suld/images/yes.png">
    	 <input type="hidden" name="firstId" id="firstId" value="1"/>
        <h1>已签到</h1><h2>(7:00-9:00)</h2>
        <%}else{%>
        <img src="<%=request.getContextPath()%>/suld/images/check.jpg">
         <input type="hidden" name="firstId" id="firstId" value="0"/>
        <h1>未签到</h1><h2>(7:00-9:00)</h2>
        <%} %>
        <div class="border_right"></div>
    </div>
    <!--会员充值-->
    <div class="member" >
    	<%if(bean.getTWOCHECKSTATE().equals("已签退")){ %>
    	<img src="<%=request.getContextPath()%>/suld/images/yes.png">
    	 <input type="hidden" name="twoId" id="twoId" value="1"/>
        <h1>已签退</h1><h2>(12:00-13:00)</h2>
        <%}else{%>
        <img src="<%=request.getContextPath()%>/suld/images/check.jpg">
         <input type="hidden" name="twoId" id="twoId" value="0"/>
        <h1>未签退</h1><h2>(12:00-13:00)</h2>
        <% } %>
        <div class="border_right"></div>
    </div>
    <!--服务介绍-->
	<div class="introduce" >
    	<%if(bean.getTHREECHECKSTATE().equals("已签到")){ %>
    	<img src="<%=request.getContextPath()%>/suld/images/yes.png">
    	 <input type="hidden" name="threeId" id="threeId" value="1"/>
        <h1>已签到</h1><h2>(14:00-15:00)</h2>
        <%}else{%>
        <img src="<%=request.getContextPath()%>/suld/images/check.jpg">
        <input type="hidden" name="threeId" id="threeId" value="0"/>
        <h1>未签到</h1><h2>(14:00-15:00)</h2>
        <% } %>
        <div class="border_right"></div>
    </div>
    <!--会员充值-->
    <div class="member" >
    	<%if(bean.getFOURCHECKSTATE().equals("已签退") ){ %>
    	<img src="<%=request.getContextPath()%>/suld/images/yes.png">
    	<input type="hidden" name="fourId" id="fourId" value="1"/>
        <h1>已签退</h1><h2>(17:30-19:00)</h2>
        <%}else{%>
        <img src="<%=request.getContextPath()%>/suld/images/check.jpg">
        <input type="hidden" name="fourId" id="fourId" value="0"/>
        <h1>未签退</h1><h2>(17:30-19:00)</h2>
        <%} %>
        <div class="border_right"></div>
    </div>
    <%} %>
    <div class="border_top"></div>
    <div class="border_bottom"></div>
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
    	<img src="<%=request.getContextPath() %>/suld/images/my_gray.png">
        <h1>我的</h1>
    </div>
</div>
<script type="text/javascript" src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
<script>
var repeat = false;
wx.config({
    debug: false, // 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。
    appId: 'wxa7d55f77a133135f', // 必填，公众号的唯一标识 
    timestamp:'2017171717' , // 必填，生成签名的时间戳
    nonceStr: 'tataren', // 必填，生成签名的随机串
    signature: '<%=signature%>',// 必填，签名，见附录1
    jsApiList: ['getLocation'] // 必填，需要使用的JS接口列表，所有JS接口列表见附录2
});
wx.ready(function(){
});

wx.error(function(res){
});

$("#mobileInoutQuery").click(function(){
		
 		$("#method").val("mobileInoutQuery");	
		$("#inOut").submit();
	
});

$("#btnSubmit").click(function(){
	if(repeat){
		alert("不能重复提交！");
		return;
	}
	repeat = true;
	
	var myDate = new Date();
	var year=myDate.getFullYear();
	var month=myDate.getMonth()+1;
	var date=myDate.getDate(); 
	var h=myDate.getHours();       //获取当前小时数(0-23)
	var m=myDate.getMinutes();     //获取当前分钟数(0-59)
	var s=myDate.getSeconds();  
	
	if(7<=h&&h<10){
		if($("#firstId").val()==1){
			alert("你已经签过了！");
		}else{
			$("#kindId").val("1");
			wx.getLocation({
        		success: function (res) {    		
            		$("#latitudeId").val(res.latitude);
					$("#longitudeId").val(res.longitude); 	
if($("#longitudeId").val()==""){ alert("签到失败,位置获取失败"); return; }
						$("#inOut").submit();
						alert("签到成功");			       		
        		},
        		fail: function(error) {
        			alert("签退失败");  
       			 }
    		});
    	
		}
	}else if (h>=12&&h<14){
		if($("#twoId").val()==1){
			alert("你已经签过了！");
		}else{
			$("#kindId").val("2");
			wx.getLocation({
        		success: function (res) {
        		
            		$("#latitudeId").val(res.latitude);
					$("#longitudeId").val(res.longitude); 
if($("#longitudeId").val()==""){ alert("签退失败,位置获取失败"); return; }
					alert("签退成功");  
					$("#inOut").submit();
					       		
        		},
        		fail: function(error) {
        			alert("签退失败");  
       			 }
    		});
		}
	}else if (h>=14&&h<16){
		if($("#threeId").val()==1){
			alert("你已经签过了！");
		}else{
			$("#kindId").val("3");
			wx.getLocation({
        		success: function (res) {
        		
            		$("#latitudeId").val(res.latitude);
					$("#longitudeId").val(res.longitude); 
if($("#longitudeId").val()==""){ alert("签到失败,位置获取失败"); return; }
					alert("签到成功");  
					$("#inOut").submit();
					       		
        		},
        		fail: function(error) {
        			alert("签到失败");  
       			 }
    		});
		}
	}else if(h>=17&&h<24){
		if(h==17&&m<30){
			alert("请在规定时间签退");
			return;
		}
		if($("#fourId").val()==1){
			alert("你已经签过了！");
		}else{
			$("#kindId").val("4");			
			wx.getLocation({
        		success: function (res) {
        		
            		$("#latitudeId").val(res.latitude);
					$("#longitudeId").val(res.longitude); 
if($("#longitudeId").val()==""){ alert("签退失败,位置获取失败"); return; }
					alert("签退成功");  
					$("#inOut").submit();
					       		
        		},
        		fail: function(error) {
        			alert("签退失败");  
       			 }
    		});
			
		}
	}else{
		alert("请在规定时间签到!");
	}
});


</script>
</form> 
</body>
</html>

