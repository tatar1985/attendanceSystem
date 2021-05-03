<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ page import="com.tataren.tatar.domain.TUsers"%>
<%
TUsers webUser=(TUsers)request.getSession().getAttribute("webUser");
 %>
<html lang="zh-CN">
<head>
<script src="<%=request.getContextPath()%>/suld/js/jquery.js"></script>
<link href="<%=request.getContextPath()%>/suld/css/css.css" rel="stylesheet" type="text/css">
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
<meta name="format-detection" content="telephone=no" />
<meta charset="utf-8">
<title>无标题文档</title>
<style>
/*充值账户*/
.column_box p{ position:absolute;right:4%;top:0; color:#191919;}
/*充值金额*/
.money_box{ height:127px; position:relative; background-color:#fff;}
.money_box h1{ line-height:39px; font-size:14px; color:#65646b; margin-left:4%;}
.money_box h1 span{font-size:12px; color:#ff7f00;}
.money{ float:left; width:40%; height:33px; line-height:33px; text-align:center; color:#191919; margin-left:4%; border:1px solid #ccc; border-radius:3px; box-sizing:border-box; background-position:left top; background-size:19px 19px; background-repeat:no-repeat;}
.money_box input{ width:40%; height:33px; text-align:center; color:#01aff0; border:1px solid #ccc; border-radius:3px; box-sizing:border-box; position:absolute;left:60%;top:39px;}
input:placeholder{color:#191919;}
.choose{ border:1px solid #01aff0; color:#01aff0;}
.first{ height:23px; line-height:23px; width:20%; margin-top:7px; margin-left:auto; margin-right:auto; text-align:center; font-size:12px; color:#65646b; position:relative}
.first .border_top{ top:11px; width:180%;}
.money_box p{ text-align:center; font-size:12px; color:#ff7f00; line-height:12px;}

/* all */
.money_box input::-webkit-input-placeholder { color:#191919; }
.money_box input::-moz-placeholder { color:#191919; } /* firefox 19+ */
.money_box input:-ms-input-placeholder { color:#191919; } /* ie */
.money_box input input:-moz-placeholder { color:#191919; }

/*选择套餐*/
.setmeal_box{ background-color:#fff; position:relative; padding-top:14px;}
.setmeal_title{ line-height:13px; font-size:14px; color:#65646b; margin-left:4%;}
.setmeal{ width:92%; margin-left:4%; position:relative}
.setmeal > h1,.setmeal > h2{ line-height:42px; float:left; font-size:14px; color:#191919;}
.setmeal > h2{ margin-left:10%; color:#c40000;}
.setmeal img{ width:11px; height:auto; position:absolute; left:50%; top:20px;}
.setmeal input{ float:right; width:16px; height:16px; margin-top:13px;}
.text h1{ line-height:13px; font-size:14px; color:#191919; margin-bottom:13px;}
.text h2{ line-height:11px; font-size:12px; color:#191919; margin-bottom:7px;}
.text p{ line-height:17px; font-size:12px; color:#65646b;}
/*推荐人手机号*/
.column_box h1{ line-height:46px; float:left; color:#191919;}
.column_box input{ width:130px; margin-left:27px;}
</style>
</head>

<body style="background-color: #f9f9f9;">
 <form class="form form-horizontal" action="<%=request.getContextPath()%>/suld_/suld.do" method="post"  id="suldForm" name="suldForm">
    <input type="hidden" name="method" id="method" value="tatarRegister"/>
<!--header顶部标题-->
<div class="header">部落考勤 邀请页面
	<div class="header_left" onclick="window.history.go(-1)"><img src="<%=request.getContextPath()%>/suld/images/return.png"></div>
</div>
<!--充值账户-->
<div class="column_box" style="margin-top:14px;">被邀请方
	<div class="border_top"></div>
    <p><%=webUser.getChatName() %></p>
    <div class="border_bottom"></div>
</div>
<!--充值金额-->
<div class="money_box">
	<h1>&nbsp;&nbsp;&nbsp;&nbsp;<span></span></h1>
    <div>
    <div class="money choose" style="background-image:url(images/recommend.png)">有邀请码</div>
    
    <div class="money " id="noCode" >先去逛逛</div>
    </div>    
    <div class="border_bottom"></div>
</div>
<!--选择套餐-->
<div class="setmeal_box">

    <div class="setmeal">
    	<h1>单位名称</h1>
        <h2>宇宙俱乐部</h2>
        <div class="clearfix"></div>
       
        <div class="border_bottom"></div>
    </div>
    <div class="setmeal">
    	<h1>科室名称</h1>
        <h2>地球俱乐部</h2>
        <div class="clearfix"></div>
        <div class="border_bottom"></div>
    </div>
    <div class="setmeal">
    	<h1>当前积分</h1>
        <h2>100分</h2>
        
        <div class="clearfix"></div>
        <div class="text">
        	
        </div>
    </div>
    <div class="border_bottom"></div>
</div>
<!--推荐人手机号-->
<div class="column_box" id="divCode" style="margin-top:14px; margin-bottom:100px;">
	<div class="border_top"></div>
	<h1>输入邀请码</h1>
	<input placeholder="输入邀请码" id="code" name="code">
    <div class="border_bottom"></div>
</div>
<!--立即充值-->
<div class="confirm_box" id="submit1">
	<div class="confirm" style="background-color:#01aff0" id="btnSubmit1">提交</div>
</div>
<div class="confirm_box" id="submit2">
	<div class="confirm" style="background-color:#01aff0;" id="btnSubmit2">先去逛逛</div>
</div>
<script>
//隐藏赠送明细

//隐藏套餐列表
$(".setmeal_box").hide();
//点击下拉按钮、显示赠送明细
$("#submit2").hide();
//点击收起按钮，隐藏赠送明细

//选择金额，改变所选金额样式
$(".money").click(
	function(){
		$(".money_box input").css("border","1px solid #ccc");
		$(this).addClass("choose").siblings().removeClass("choose");
		$(".setmeal_box").hide();
		$("#divCode").show();
		$("#submit1").show();
		$("#submit2").hide();
		}
)
//选择其他金额，显示金额输入框
$("#noCode").click(
	function(){
		$("#divCode").hide();
		$(this).css("border","1px solid #01aff0");
		$(this).siblings().removeClass("choose");
		$(".setmeal_box").show();
		$(".money_box input").show();
		$("#submit2").show();
		$("#submit1").hide();
		}
)
$("#btnSubmit1").click(function(){		
 		if($("#code").val()==null){
 			alert("输入码不能为空!");
 		}
		$("#suldForm").submit();
	
});
$("#btnSubmit2").click(function(){
		
 		$("#code").val("111111");	
		$("#suldForm").submit();
	
});
</script>
</form>
</body>
</html>

