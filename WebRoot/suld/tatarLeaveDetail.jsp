<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ page import="com.tataren.tatar.domain.TLeave"%>
<%@ page import="com.tataren.tatar.domain.TTemp"%>
<%
TLeave bean=(TLeave)request.getAttribute("bean");
TTemp tempBean=(TTemp)request.getAttribute("tempBean");
System.out.println(tempBean.getChanjia()+"66666666666666666666666666666");

 %>
<html lang="zh-CN">
<head>
<meta charset="utf-8">
<script src="<%=request.getContextPath()%>/suld/js/jquery.js"></script>
<link href="<%=request.getContextPath()%>/suld/css/css.css" rel="stylesheet" type="text/css">
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
<meta name="format-detection" content="telephone=no" />
<title>请假申请</title>
<style>
.move_box{ width:100%; clear:both; position:relative; left:0; overflow:hidden}
.move{ width:200%; clear:both; position:relative; left:0; }
/*服务流程or订单详情*/
.information,.evaluate{ height:41px; width:50%; line-height:41px; color:#65646b; text-align:center; float:left; font-size:14px;}
.blue_block{ width:50%; height:2px; background-color:#01aff0; position:absolute; left:0; bottom:0;}
/*服务流程*/
.service_box{  background-color:#fff; position:relative; width:50%; float:left}
.blue{ width:14px; height:14px; position:absolute; z-index:2; left:4%; top:20px; background-color:#a5dcfa; border-radius:50%; padding-top:2px; padding-left:2px; box-sizing:border-box}
.blue div{ height:10px; width:10px; background-color:#01aff0; border-radius:50%;}
.blue p{ position:absolute; width:2px; height:59px; background-color:#01aff0; left:50%; margin-left:-1px; top:-50px;}
.gray{ width:14px; height:14px; position:absolute; z-index:0; left:4%; top:20px; padding-top:1px; padding-left:1px; box-sizing:border-box}
.gray div{ height:10px; width:12px; background-color:#ccc; border-radius:50%;}
.gray p{ position:absolute; width:2px; height:59px; background-color:#ccc; left:50%; margin-left:-1px; top:-50px;}
.red{ width:14px; height:14px; position:absolute; z-index:0; left:4%; top:20px; padding-top:1px; padding-left:1px; box-sizing:border-box}
.red div{ height:10px; width:12px; background-color:#FF0000; border-radius:50%;}
.red p{ position:absolute; width:2px; height:59px; background-color:#FF0000; left:50%; margin-left:-1px; top:-50px;}

.service{ height:60px; padding-top:11px; box-sizing:border-box; position:relative; box-sizing:border-box; background:#fff;}
.service h1{ line-height:18px; color:#191919; font-size:14px; margin-left:11%;}
.service h2{ line-height:16px; color:#65646b; font-size:12px; margin-left:11%}
.service > p{ line-height:42px; color:#65646b; font-size:12px; position:absolute; top:0; right:4%;}
/*订单详情*/
.order_box{ width:50%; float:left}
.order_title{ height:39px; line-height:39px; font-size:14px; color:#65646b; padding-left:4%; box-sizing:border-box}
.order{ height:42px; background-color:#fff; position:relative; padding-left:4%; padding-right:4%; padding-top:9px; box-sizing:border-box}
.order_line{ height:26px;}
.order_line h1{ line-height:26px; font-size:14px; color:#191919; float:left}
.order_line p{ line-height:26px; font-size:14px; color:#EA5858; float:right}
/*订单信息*/
.order_msg{ background-color:#fff; position:relative; padding-left:4%; padding-right:4%; padding-top:9px; padding-bottom:9px; box-sizing:border-box}
.msg_line h1{ line-height:26px; font-size:14px; color:#65646b; float:left; width:30%;}
.msg_line p{ line-height:26px; font-size:14px; color:#191919; float:left; width:70%;}
/*底部按钮*/
.evaluate_btn,.share_btn{ background-color:#FF7F00; color:white; border-radius:3px; width:44%; height:33px; line-height:33px; text-align:center; font-size:16px; margin-left:4%;margin-top:14px; float:left}
.share_btn{ background-color:#fff; border:1px solid #ccc; color:#65646b;}
/*分享框*/
.black{ z-index:3}
.share_box{ width:100%; height:126px; background-color:#fff; position:fixed; left:0; bottom:0; z-index:4}
.share_title{ position:relative; height:41px; line-height:41px; color:#65646b; text-align:center; font-size:14px;}
.share_title img{ width:9px; height:9px; position:absolute; left:4%; top:14px;}
.share,.share1{ width:50%; float:left}
.share img,.share1 img{margin-top:15px; width:33px; height:auto}
.share img{float:right; margin-right:22%;} 
.share1 img{float:left; margin-left:22%;}
.share p,.share1 p{ position:relative; bottom:0; font-size:12px; color:#65646b; line-height:36px;}
.share p{ clear:right; text-align:right; right:22%; margin-right:-5px;}
.share1 p{ clear:left; text-align:left; left:22%; margin-left:-2px;}
/*取消规则*/
.cancel_box{ z-index:4}
.cancel_title{ line-height:33px; height:33px; color:#01aff0; border-bottom:1px solid #01aff0; padding-left:14px;}
.cancel_text{ padding-left:14px; padding-right:14px; margin-top:4px; height:224px;}
.cancel_text h1{ font-size:14px; line-height:18px; color:#01aff0;}
.cancel_text p{ font-size:12px; line-height:15px; color:#65646b;}
.cancel{ height:24px;}
.cancel p{ float:left; line-height:24px; color:#191919; font-size:12px; margin-left:14px;}
.cancel input{ float:right; margin-right:14px; margin-top:5px;}
/*底部导航*/
.nav_box{ width:100%; height:51px; position:fixed; left:0; bottom:0; background-color:#fff;}
.nav_index,.nav_order,.nav_my{ width:33.3%; float:left;}
.nav_box img{ width:18px; height:auto; position:relative; left:50%; margin-left:-9px; margin-top:8px;}
.nav_box h1{ font-size:12px; text-align:center; color:#65646b; line-height:25px;}
.nav_box .current_nav{ color:#01aff0;}

</style>
</head>

<body style="background-color: #f9f9f9;">
<!--header顶部标题-->
<div class="header">申请请假
	<div class="header_left" onclick="window.history.go(-1)"><img src="<%=request.getContextPath()%>/suld/images/return.png"></div>
</div>
<!--阿姨信息-->
<div class="waiter_box">
	<div class="border_top"></div>
	<div class="waiter_img"><img src="<%=bean.getPHOTO() %>"></div>
    <div class="waiter">
    	<h1><%=bean.getUSERNAME() %></h1>
       <h2><%=bean.getDepartment()==null?"":bean.getDepartment() %>
        <!-- 
        	<div class="star_box" style=" position:absolute; top:0; right:-85px; margin-top:0">
                <img src="<%=request.getContextPath()%>/suld/images/star.png">
                <img src="<%=request.getContextPath()%>/suld/images/star.png">
                <img src="<%=request.getContextPath()%>/suld/images/star.png">
                <img src="<%=request.getContextPath()%>/suld/images/star.png">
                <img src="<%=request.getContextPath()%>/suld/images/star_gray.png">
            </div>
             -->
        </h2>
        <p>已请&nbsp;事假：<%=tempBean.getShijia()==null?"0":tempBean.getShijia()%>天  &nbsp;&nbsp;&nbsp;病假：<%=tempBean.getBingjia()==null?"0":tempBean.getBingjia()%>天&nbsp;&nbsp;&nbsp;婚假：<%=tempBean.getHunjia()==null?"0":tempBean.getHunjia() %>天 </p>
    </div>
    <p class="waiter_id">手机：<%=bean.getMOBILE() %></p>
    <div class="border_bottom"></div>
</div>
<!--服务流程or订单详情-->
<div style="position:relative; height:42px;">
    <div class="information" style="color:#01aff0">订单详情</div>
    <div class="evaluate">审批详情</div>
    <div class="blue_block"></div>
    <div class="border_bottom"></div>
</div>
<!--服务流程-->
<div class="move_box">
<div class="move">

<!--订单详情-->
<div class="order_box">
    <!--订单信息-->
    <div class="order_title">详细信息</div>
    <div class="order_msg">
        <div class="border_top"></div>
        <div class="msg_line">
            <h1>申请类型</h1>
            <p><%=bean.getTLEAVETYPE() %>&nbsp;(<%=bean.getDAYNUM() %>天 )</p>
            <div class="clearfix"></div>
        </div>
        <div class="msg_line">
            <h1>开始时间</h1>
            <p><%=bean.getSTARTTIME() %>&nbsp;&nbsp;<%=bean.getDAYTYPE()==null?"":bean.getDAYTYPE() %></p>
            <div class="clearfix"></div>
        </div>
		 <div class="msg_line">
            <h1>结束时间</h1>
            <p><%=bean.getENDTIME() %>&nbsp;&nbsp;<%=bean.getDAYTYPEID()==null?"":bean.getDAYTYPEID() %></p>
            <div class="clearfix"></div>
        </div>
        <div class="msg_line">
            <h1>备注理由</h1>
            <p><%=bean.getREMARK()==null?"":bean.getREMARK() %></p>
            <div class="clearfix"></div>
        </div>
        <div class="msg_line">
            <h1>当前状态</h1>
            <p><%=bean.getFLAGSTATE() %></p>
            <div class="clearfix"></div>
        </div>
        
        <div class="clearfix"></div>
        <div class="border_bottom"></div>
    </div>
    <div class="order_title">审批备注</div>
    <div class="order">
        <div class="border_top"></div>
        <div class="order_line">
            <textarea placeholder="审批备注" ></textarea>
        </div>
        <div class="border_bottom"></div>
    </div>    
</div>

<div class="service_box">
    <!--服务流程（上）-->
    <%if(bean.getFLAGSTATE().equals("未通过")){ %>
      <div class="service">
    	<div class="red">
    		<div></div>
    	</div>
        <h1>申请时间</h1>
        <h2></h2>
        <p><%=bean.getAPPLICATIONSTIME() %></p>
    </div>
    <div class="service">
    	<div class="red">
       	  	<div></div>
            <p></p>
      	</div>
        <h1>分管领导</h1>
        <h2><%=bean.getFIRSTAPPROVALSTATE()==null?"":bean.getFIRSTAPPROVALSTATE() %></h2>
        <p><%=bean.getFIRSTAPPROVALPERSON()==null?"":bean.getFIRSTAPPROVALPERSON() %></p>
    </div>
   <div class="service">
    	<div class="red">
       	  	<div></div>
            <p></p>
      	</div>
        <h1>常务领导</h1>
        <h2><%=bean.getSECONDAPPROVALSTATE() %></h2>
        <p><%=bean.getSECONDAPPROVALPERSON() %>-<%=bean.getSECONDAPPROVALDATE() %></p>
    </div>   
    <div class="service">
    		<div class="red">
       	  		<div></div>
            	<p></p>
      		</div>
        	<h1>主要领导</h1>
        	<h2><%=bean.getTHREEAPPROVALSTATE() %></h2>
        	<p><%=bean.getTHREEAPPROVALPERSON() %>-<%=bean.getSECONDAPPROVALDATE() %></p>
    </div> 
     <div class="service">
    	<div class="red">
       	  	<div></div>
            <p></p>
      	</div>
        <h1>存档</h1>
        <h2>未通过</h2>
        <p></p>
    </div>
    <%}else{ %>
    <div class="service">
    	<div class="blue">
    		<div></div>
    	</div>
        <h1>申请时间</h1>
        <h2></h2>
        <p><%=bean.getAPPLICATIONSTIME() %></p>
    </div>
 	<%if(bean.getFIRSTAPPROVALSTATE().equals("待审批")){ %>
 	 <div class="service">
    	<div class="gray">
       	  	<div></div>
            <p></p>
      	</div>
        <h1>分管领导</h1>
        <h2><%=bean.getFIRSTAPPROVALSTATE()==null?"":bean.getFIRSTAPPROVALSTATE() %></h2>
        <p><%=bean.getFIRSTAPPROVALPERSON()==null?"":bean.getFIRSTAPPROVALPERSON() %></p>
    </div>
 	<%}else{ %>
 	<div class="service">
    	<div class="blue">
       	  	<div></div>
            <p></p>
      	</div>
        <h1>分管领导</h1>
        <h2><%=bean.getFIRSTAPPROVALSTATE() %></h2>
        <p><%=bean.getFIRSTAPPROVALPERSON() %>-<%=bean.getFIRSTAPPROVALDATE() %></p>
    </div>   
 	<%} %>
     
    <%if(bean.getSECONDAPPROVALSTATE().equals("待审批")){ %>
 	 <div class="service">
    	<div class="gray">
       	  	<div></div>
            <p></p>
      	</div>
        <h1>常务领导</h1>
        <h2>待审批</h2>
        <p><%=bean.getSECONDAPPROVALPERSON() %></p>
    </div>
 	<%}else{ %>
 	<div class="service">
    	<div class="blue">
       	  	<div></div>
            <p></p>
      	</div>
        <h1>常务领导</h1>
        <h2><%=bean.getSECONDAPPROVALSTATE() %></h2>
        <p><%=bean.getSECONDAPPROVALPERSON() %>-<%=bean.getSECONDAPPROVALDATE() %></p>
    </div>   
 	<%} %>
    <%if(bean.getTHREEAPPROVALSTATE().equals("待审批")){ %>
 	 <div class="service">
    	<div class="gray">
       	  	<div></div>
            <p></p>
      	</div>
        <h1>主要领导</h1>
        <h2>待审批</h2>
        <p><%=bean.getTHREEAPPROVALPERSON() %></p>
    </div>
 	<%}else{ 
 	
 		if(bean.getSECONDAPPROVALSTATE().equals("已通过")&&(bean.getTHREEAPPROVALSTATE().equals("已通过")||bean.getTHREEAPPROVALSTATE().equals("无需审批"))){%>
 	      
 		<div class="service">
    		<div class="blue">
       	  		<div></div>
            	<p></p>
      		</div>
        	<h1>主要领导</h1>
        	<h2><%=bean.getTHREEAPPROVALSTATE() %></h2>
        	<p><%=bean.getTHREEAPPROVALPERSON() %>-<%=bean.getSECONDAPPROVALDATE() %></p>
    	</div> 
    	<%}else{ %>
    		<div class="service">
    			<div class="gray">
       	  		<div></div>
            	<p></p>
      		</div>
        	<h1>主要领导</h1>
        	<h2><%=bean.getTHREEAPPROVALSTATE() %></h2>
        	<p><%=bean.getTHREEAPPROVALPERSON()==null?"":bean.getTHREEAPPROVALPERSON()%></p>
    		</div> 
    	<%} %>  
 	<%} %>
	<%if(bean.getFLAGSTATE().equals("审批中")){ %>
 	 <div class="service">
    	<div class="gray">
       	  	<div></div>
            <p></p>
      	</div>
        <h1>存档</h1>
        <h2>审批中</h2>
        <p></p>
    </div>
 	<%}else{ %>
 	<div class="service">
    	<div class="blue">
       	  	<div></div>
            <p></p>
      	</div>
        <h1>存档</h1>
        <h2>已完成</h2>
        <p></p>
    </div>   
 	<%} %>
    <%} %>
    
    <div class="border_bottom"></div>
</div>

</div>
</div>
<br/><br/><br/><br/>
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
//点击服务流程，蓝色滑块向左移动，切换到服务流程页
$(".information").click(
	function(){
		$(".blue_block").animate({left:'0%'});
		$(".move").animate({left:'0%'});
		}
)
//点击订单详情，蓝色滑块向右移动，切换到订单详情页
$(".evaluate").click(
	function(){
		$(".blue_block").animate({left:'50%'});
		$(".move").animate({left:'-100%'});
		}
)
//隐藏黑色半透明背景和分享页
$(".black").hide();
$(".share_box").hide();
//点击分享按钮，弹出分享页
$(".share_btn").click(
	function(){
		$(".black").show();
		$(".share_box").show();
		}
)
//点击分享页左上角的关闭按钮
$(".close_share").click(
	function(){
		$(".black").hide();
		$(".share_box").hide();
		}
)
//隐藏取消规则
$(".cancel_box").hide();
</script>
</body>
</html>

