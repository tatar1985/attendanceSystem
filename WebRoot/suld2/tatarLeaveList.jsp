<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ page import="com.tataren.tatar.domain.TLeave"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.Iterator"%>

<html lang="zh-CN">
<head>
<meta charset="utf-8">
<script src="<%=request.getContextPath()%>/suld/js/jquery.js"></script>
<link href="<%=request.getContextPath()%>/suld/css/css.css" rel="stylesheet" type="text/css">
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
<meta name="format-detection" content="telephone=no" />
<title>申请订单</title>
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
   <input type="hidden" name="method" id="method" value="moibleLeaveDeatail"/>
    <input type="hidden" name="sid" id="sid" value=""/>

<!--header顶部标题-->
<div class="header">我的申请
	<div class="header_left" onclick="window.history.go(-1)"><img src="<%=request.getContextPath()%>/suld/images/return.png"></div>
</div>
<!--订单状态-->
<div class="state_box">
    <div class="state">审批中</div>
    <div class="state">已审批</div>
    <div class="blue_block"></div>
</div>
<!--订单信息-->
<div class="move_box">
<div class="move">

    <!--未付款-->
    <div class="order_box">
    <%
    	List proTLeaves = (List) request.getSession().getAttribute("proTLeaves");
		if(proTLeaves!=null&&proTLeaves.size()!=0){
		
		for (Iterator iter = proTLeaves.iterator(); iter.hasNext();) {
		TLeave bean = (TLeave) iter.next();
		
	
     %>
        <div class="order" >
            <div class="border_top"></div>
            <div class="order_title"><%=bean.getUSERNAME() %></div>
            <div class="order_text">
                <div class="border_top"></div>
                <h1><%=bean.getSTARTTIME() %>&nbsp;&nbsp;&nbsp;&nbsp;<%=bean.getENDTIME() %></h1>
                <h2><%=bean.getTLEAVETYPE() %></h2>
                <p><%=bean.getDAYNUM() %>天</p>
                <div class="border_bottom"></div>
            </div>
          <div class="cancel" style="border:#ff7f00 1px solid; color:#ff7f00;" onClick="init(<%=bean.getSid() %>)">查看</div>
          <%if(bean.getFIRSTAPPROVALSTATE().equals("待审批")){ %>
             <div class="cancel" align="left" style="border:#ff7f00 1px solid; color:#ff0000;" onClick="linkDel(<%=bean.getSid() %>)">撤销</div>
           <%} %> 
            <div class="border_bottom"></div>
        </div>
        <%}} %>
       <br><br>
    </div>
 
    <!--待评价-->
    <div class="order_box">
    <%
    	List succTLeaves = (List) request.getSession().getAttribute("succTLeaves");
		if(succTLeaves!=null&&succTLeaves.size()!=0){
		for (Iterator iter = succTLeaves.iterator(); iter.hasNext();) {
		TLeave bean = (TLeave) iter.next();
		
	
     %>
        <div class="order" >
            <div class="border_top"></div>
            <div class="order_title"><%=bean.getUSERNAME() %></div>
            <div class="order_text">
                <div class="border_top"></div>
                <h1><%=bean.getSTARTTIME() %>&nbsp;&nbsp;<%=bean.getENDTIME() %></h1>
                <h2><%=bean.getTLEAVETYPE() %></h2>
                <p><%=bean.getDAYNUM() %>天</p>
                <div class="border_bottom"></div>
            </div>
            <div class="cancel" style="border:1px solid #01aff0; color:#01aff0;" onClick="init(<%=bean.getSid() %>)">查看</div>
            <div class="border_bottom"></div>
        </div>
        <%}} %>
		 
       
    </div> 
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
    	<img src="<%=request.getContextPath()%>/suld/images/my_gray.png">
        <h1>我的</h1>
    </div>
</div>
<script>
function init(a){
	$("#sid").val(a);	
	$("#tatarForm").submit();
 }
 function linkDel(a){
	$("#method").val("linkDelLeave");
	$("#sid").val(a);			
	$("#tatarForm").submit();
 }
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
</script>
</form>
</body>
</html>

