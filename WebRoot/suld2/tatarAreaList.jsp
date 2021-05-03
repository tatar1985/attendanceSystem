<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ page import="com.tataren.tatar.domain.TArea"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.Iterator"%>
<%
String signature = (String)request.getSession().getAttribute("signature");
%>
<html lang="zh-CN">
<head>
<meta charset="utf-8">
<script src="<%=request.getContextPath()%>/suld/js/jquery.js"></script>
<script src="<%=request.getContextPath()%>/suld/js/swiper.min.js"></script>
<link href="<%=request.getContextPath()%>/suld/css/swiper.min.css" rel="stylesheet" type="text/css">
<link href="<%=request.getContextPath()%>/suld/css/css.css" rel="stylesheet" type="text/css">
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
<meta name="format-detection" content="telephone=no" />
<title>活动列表</title>
<style>
/*订单状态*/
.state_box{ height:43px; position:relative}
.state{ float:left; width:50%; line-height:43px; color:#65646b; font-size:14px; text-align:center}
.blue_block{ width:50%; position:absolute; left:0; bottom:0; height:2px; background-color:#01aff0;}
/*订单信息*/
.move_box{ width:100%; overflow:hidden}
.move{ width:500%; position:relative; clear:both; left:0}
.order_box{ float:left; width:20%;}
.order{ height:133px; position:relative; background-color:#fff; margin-bottom:14px;}
.order_title{ line-height:40px; color:#191919; padding-left:4%; font-size:16px;}
.order_text{ width:92%; margin-left:4%; position:relative; height:53px; padding-top:5px; box-sizing:border-box}
.order_text h1,.order_text h2{ line-height:20px; color:#191919; font-size:14px;}
.order_text p{ position:absolute;top:0;right:0; line-height:53px; color:#65646b; font-size:16px;}
.cancel{ width:65px; height:25px; border:1px solid #ccc; text-align:center; line-height:25px; border-radius:3px; float:right; margin-top:6px; margin-right:4%; color:#65646b;}
/*底部导航*/
.nav_box{ width:100%; height:51px; position:fixed; left:0; bottom:0; background-color:#fff;}
.nav_index,.nav_order,.nav_my{ width:33.3%; float:left;}
.nav_box img{ width:18px; height:auto; position:relative; left:50%; margin-left:-9px; margin-top:8px;}
.nav_box h1{ font-size:12px; text-align:center; color:#65646b; line-height:25px;}
.nav_box .current_nav{ color:#01aff0;}

</style>
</head>

<body style="background-color: #f9f9f9;">
 <form class="form form-horizontal" action="<%=request.getContextPath()%>/suld_/suld.do" method="post"  id="tatarForm" name="tatarForm">
   <input type="hidden" name="method" id="method" value="getAreaAddress"/>
   <input type="hidden" name="areaId" id="areaId" value=""/>
   <input type="hidden" name="latitudeId" id="latitudeId" value=""/>
   <input type="hidden" name="longitudeId" id="longitudeId" value=""/>
<!--header顶部标题-->
<div class="header">活动列表
	<div class="header_left" onclick="window.history.go(-1)"><img src="<%=request.getContextPath()%>/suld/images/return.png"></div>
</div>
<!--订单状态-->
<div class="state_box">
    <div class="state">未签活动</div>
    <div class="state">已签活动</div>
    <div class="blue_block"></div>
</div>
<!--订单信息-->
<div class="move_box">
<div class="move">

    <!--未付款-->
    <div class="order_box">
    
    <%
    	List listAreaNo = (List) request.getAttribute("listAreaNo");
		if(listAreaNo!=null&&listAreaNo.size()!=0){
		
		for (Iterator iter = listAreaNo.iterator(); iter.hasNext();) {
		TArea bean = (TArea) iter.next();
		
	
     %>
        <div class="order" >
            <div class="border_top"></div>
            <div class="order_title"><%=bean.getAreaName() %></div>
            <div class="order_text">
                <div class="border_top"></div>
                <h2><%=bean.getStartTime() %></h2>
                <h2><%=bean.getEndTime() %></h2>
                <div class="border_bottom"></div>
            </div>
            <div class="cancel" style="border:#ff7f00 1px solid; color:#ff7f00;" onclick="getAreaAddress(<%=bean.getSid() %>)" >签到</div>
            <div class="border_bottom"></div>
             </div> 
        <%}} %>

       <br><br> 
    </div>
 
    <!--待评价-->
    <div class="order_box">
    <%
    	List listAreaYes = (List) request.getSession().getAttribute("listAreaYes");
		if(listAreaYes!=null&&listAreaYes.size()!=0){
		for (Iterator iter = listAreaYes.iterator(); iter.hasNext();) {
		TArea bean = (TArea) iter.next();
	
     %>
        <div class="order" >
            <div class="border_top"></div>
             <div class="order_title"><%=bean.getAreaName() %></div>
            <div class="order_text">
                <div class="border_top"></div>
                <h2><%=bean.getStartTime() %></h2>
                <h2><%=bean.getEndTime() %></h2>
                <div class="border_bottom"></div>
            </div>
            <div class="cancel" style="border:1px solid #01aff0; color:#01aff0;" >已签</div>
            <div class="border_bottom"></div>
        </div>
        <%}} %>    
         <br><br>   
    </div> 
    
</div>
</div>
<br>
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
$(".state:eq(0)").click(
	function(){
		$(".blue_block").animate({left:'0%'})
		$(".move").css("left","0%")
		}
)
$(".state:eq(1)").click(
	function(){
		$(".blue_block").animate({left:'50%'})
		$(".move").css("left","-100%")
		}
)
function getAreaAddress(areaId){

$("#areaId").val(areaId);
	wx.getLocation({
		type: 'gcj02',
        		success: function (res) {
        		
            		$("#latitudeId").val(res.latitude);
					$("#longitudeId").val(res.longitude); 
if($("#longitudeId").val()==""){ alert("签退失败,位置获取失败"); return; }
					alert("签到成功");  
					$("#tatarForm").submit();
					       		
        		},
        		fail: function(error) {
        			alert("签到失败");  
       			 }
       			 });
}
</script>
</form>
</body>
</html>

