<!DOCTYPE HTML>
<%@ page language="java" contentType="text/html; charset=utf-8"%>
<html>
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
<link href="<%=request.getContextPath()%>/css/H-ui.min.css" rel="stylesheet" type="text/css" />
<link href="<%=request.getContextPath()%>/css/H-ui.login.css" rel="stylesheet" type="text/css" />
<link href="<%=request.getContextPath()%>/css/style2.css" rel="stylesheet" type="text/css" />
<link href="<%=request.getContextPath()%>/css/1.0.1/iconfont.css" rel="stylesheet" type="text/css" />

<title>人员信息管理系统</title>

</head>
<body>
<input type="hidden" id="TenantId" name="TenantId" value="" />
<div class="header"></div>
<div class="loginWraper">
  <div id="loginform" class="loginBox">
    <form class="form form-horizontal" action="<%=request.getContextPath()%>/tatar_/tatar.do" method="post"  id="form-member-login" name="form-member-login">
    <input type="hidden" name="method" value="webUserLogin"/>
      <div class="row cl">
        <label class="form-label col-3"><i class="Hui-iconfont">&#xe60d;</i></label>
        <div class="formControls col-4">
          <input id="userName" name="userName" type="text" placeholder="账户" class="input-text size-L" >
        </div>
        <div class="col-4"> </div>
      </div>
      <div class="row cl">
        <label class="form-label col-3"><i class="Hui-iconfont">&#xe60e;</i></label>
        <div class="formControls col-4">
          <input id="password" name="password" type="password" placeholder="密码" class="input-text size-L" >
        </div>
        <div class="col-4"> </div>
      </div>
      <!-- 
      <div class="row cl">
        <div class="formControls col-8 col-offset-3">
          <input class="input-text size-L" type="text" placeholder="验证码" onblur="if(this.value==''){this.value='验证码:'}" onclick="if(this.value=='验证码:'){this.value='';}" value="验证码:" style="width:150px;">
          <img src="images/VerifyCode.aspx.png"> <a id="kanbuq" href="javascript:;">看不清，换一张</a> </div>
      </div>
       -->
      <div class="row">
        <div class="formControls col-8 col-offset-3">
          <label for="online">
            <input type="checkbox" name="online" id="online" value="">
            使我保持登录状态</label>
        </div>
      </div>
      <div class="row">
        <div class="formControls col-8 col-offset-3">
          <input name="btnSubmit" id="btnSubmit" type="button" class="btn btn-success radius size-L" value="&nbsp;登&nbsp;&nbsp;&nbsp;&nbsp;录&nbsp;">
          <input name="" type="reset" class="btn btn-default radius size-L" value="&nbsp;取&nbsp;&nbsp;&nbsp;&nbsp;消&nbsp;">
        </div>
      </div>
    </form>
  </div>
</div>
<div class="footer">Copyright 塔塔儿部落 网络科技 by H-ui.admin.v2.3</div>
<script type="text/javascript" src="<%=request.getContextPath()%>/main/js/jquery-1.9.1.min.js"></script> 
<script type="text/javascript" src="<%=request.getContextPath()%>/css/H-ui.js"></script> 
<script>
$("#btnSubmit").click(function(){
	
	
	if($("#userName").val()==""){
		alert("姓名不能为空");
		return false;
		
	}else if($("#password").val()==""){
		alert("密码不能为空");
		return false;
	}
	
   $("#form-member-login").submit();
  
});
</script>
</body>
</html>