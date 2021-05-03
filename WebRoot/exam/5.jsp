<!DOCTYPE HTML>
<%@ page language="java" contentType="text/html; charset=utf-8"%>
<% String error = (String)request.getAttribute("error"); %>
<head>
    <!-- UNIFIED -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,Chrome=1" />
    <meta  name="keywords" content="毕业报到系统前台"/>
    <meta name="description" content=""/>
    <meta name="author" content="f2e_hhf[ai]"/>

    <title> </title>
    <!-- BS CSS -->
    <link href="<%=request.getContextPath()%>/exam/dist/css/bootstrap.min.css" rel="stylesheet" media="screen">
    <!--main content +footer-->
    <link href="<%=request.getContextPath()%>/exam/dist/css/xg_bybd.css" rel="stylesheet" media="screen">
    <!--[if lt IE 9]>
    <script src="dist/js/respond.min.js"></script>
    <script src="dist/js/html5.js"></script>
    <![endif]-->
</head>
<body>
 <form class="form form-horizontal" action="<%=request.getContextPath()%>/tatar_/tatar.do" method="post"  id="tatarForm" name="tatarForm">
    <input type="hidden" name="method" value="examQuery" id="method"/>  
<!-- 内容区 -->
<div class="container xg-bybd aiui">
    <div class="sys-name"><img  src="<%=request.getContextPath()%>/exam/images/system-name.png"/> </div>
    <div class="row">
        <div class="col-md-2 col-sm-3 step">
            <h3>报到步骤</h3>
            <ul>
                <li onClick="javascript:window.location.href='index.html' " ><div class="clearfix"><span class="pull-left nav-adorn"><em>1</em></span><span class="pull-left words">考试通告阅读</span></div></li>
                <li ><div class="clearfix"><span class="pull-left nav-adorn"><em>2</em></span><span class="pull-left words">报名情况登记</span></div></li>
                <li ><div class="clearfix"><span class="pull-left nav-adorn"><em>3</em></span><span class="pull-left words">基本信息确认</span></div></li>
                <li class="choice"  onclick="javascript:window.location.href='5.jsp'"><div class="clearfix"><span class="pull-left nav-adorn"><em>4</em></span><span class="pull-left words last-w">打印准考证</span></div></li>
            </ul>
            <a class="refer-btn" href="5.jsp">打印准考证</a>

        </div>
        <div class="col-md-10 col-sm-9">
            <div class="r-content">
                <div class="refer-cont">
                    <div class="row form-s1 checkid">
                        <div class="col-sm-4 col-md-3 col-md-offset-2"><label>身份证号码：</label></div>
                        <div class="col-sm-8 col-md-5 "><input class="form-control" id="identification" name="identification" placeholder="请输身份证号码" type="text"><%=error==null?"":error %></div>
                    </div>
                    <p class="text-center"><button type="button" class="btn btn-default blue30 btn-blue mr10" id="submitBtn" name="submitBtn">查询数据</button></p>
                </div>
            </div>
        </div>
    </div>
</div>
<div class="footer">
  <p>技术支持：塔塔尔部落网络科技</p>
</div>


<div class="modal fade popdemo_1"  tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="false">
    <div class="modal-dialog win-small">
        <div class="modal-content  aiui">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                <h4 class="modal-title" id="myModalLabel">系统提示</h4>
            </div>
            <div class="modal-body ">
                <div class="bot-box">
                    <h4>您还没进行报到登记，</h4>
                    <h4>请先进行报到并录入相关信息!</h4>
                </div>
            </div>
            <div class="modal-footer">
                <p class="text-center">
                    <button type="button" class="btn  btn-default  save-btn" data-dismiss="modal" onClick="javascript:window.location.href='index.html' ">确认前往报到</button>
                </p>
            </div>
        </div>
    </div>
</div>
<script src="<%=request.getContextPath()%>/exam/dist/js/jquery-1.7.1.min.js"></script>
<script src="<%=request.getContextPath()%>/exam/dist/js/bootstrap.min.js"></script>
<script src="<%=request.getContextPath()%>/exam/dist/js/datepicker/laydate.js"></script>

</form>
</body>
<script>
$('#submitBtn').on('click', function(){
	 if($.trim($('#identification').val()).length == 0) {
		alert("请输入身份证号码");
		return;
		 }
	if(isCardNo($('#identification').val())){
		 $("#tatarForm").submit();
	}else{
		alert("不规则的身份证号码，请重新输入！");
		return ;		
	};
	
	
	
});
function isCardNo(card) { 
	 var pattern = /(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/; 
	 
	 return pattern.test(card); 
	
	} 
</script>
</html>