<!DOCTYPE HTML>
<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%
String sid=request.getParameter("sid"); 
String level=request.getParameter("level"); 
String userName=request.getParameter("userName"); 
String unitName=request.getParameter("unitName"); 
String leaveType=request.getParameter("leaveType"); 
String startTime=request.getParameter("startTime"); 
String endTime=request.getParameter("endTime"); 
String dayNum=request.getParameter("dayNum"); 
String remark=request.getParameter("remark"); 
String photo=request.getParameter("photo"); 
String mobile=request.getParameter("mobile"); 
String firstPerson=request.getParameter("firstPerson"); 
String firstDate=request.getParameter("firstDate"); 
String firstRemark=request.getParameter("firstRemark"); 
 %>
<html>
<head>
<link href="../css/H-ui.min.css" rel="stylesheet" type="text/css" />
<link href="../css/H-ui.admin.css" rel="stylesheet" type="text/css" />
<link href="../lib/Hui-iconfont/1.0.1/iconfont.css" rel="stylesheet" type="text/css" />
<title>请假审批</title>
</head>
<body>
<div class="cl pd-20" style=" background-color:#5bacb6">
  <img class="avatar size-XL l" src="<%=photo%>">
  <dl style="margin-left:80px; color:#fff">
    <dt><span class="f-18"><%=userName %></span> <span class="pl-10 f-12"></span></dt>
    <dd class="pt-10 f-12" style="margin-left:0"><%=unitName %></dd>
  </dl>
</div>
<div class="pd-20">
<form  name="form-leave-change" action="<%=request.getContextPath()%>/common_/login.do" method="post" class="form form-horizontal" id="form-leave-change">
  <input type="hidden" name="method" value="leaveChange"/> 
  <input type="hidden" name="level" value="<%=level%>"/> 
  <input type="hidden" name="kind" id="kind" value="1"/>   
  <input type="hidden" name="sid"  value="<%=sid%>"/>  
  <table class="table">
    <tbody>
      <tr>
        <th class="text-r" width="20" >请假类别：</th>
        <td width="100"><%=leaveType %></td>
        <th class="text-r"  width="40">联系电话：</th>
        <td width="60"><%=mobile %></td>
      </tr>
      <tr>
        <th class="text-r"  width="20" >请假日期：</th>
        <td width="90"><%=startTime %>到<%=endTime %></td>
         <th class="text-r"  width="20" >请假天数：</th>
        <td><%=dayNum %></td>
      </tr>     
       <tr>
        <th class="text-r"  width="20" >请假理由：</th>
        <td colspan=3><%=(remark.equals("null")?"":remark) %></td>        
      </tr>  
      <tr>
        <th class="text-r"  width="20" >审核人员：</th>
        <td width="90"><%=firstPerson %></td>
         <th class="text-r"  width="20" >审核日期：</th>
        <td ><%=firstDate %></td>
      </tr>   
 	  <tr>
        <th class="text-r"   >审核备注：</th>
        <td colspan=3>
        <%=(firstRemark.equals("null")?"":firstRemark) %>
        </td>
         
      </tr>
    </tbody>
  </table>
  </form>
  <!-- 
  <br><br>
   <div class="form-label col-12" align="center">
        <input class="btn btn-success radius" type="button"  value="&nbsp;&nbsp;通&nbsp;&nbsp;&nbsp;过 &nbsp;&nbsp;" id="btnYesSubmit" name="btnYesSubmit">&nbsp;&nbsp;&nbsp;&nbsp;
        <input class="btn btn-danger radius" type="button"  value="&nbsp;&nbsp;不通过 &nbsp;&nbsp;" id="btnNoSubmit" name="btnNoSubmit">
   </div>
 -->
</div>
<script type="text/javascript" src="../lib/jquery/1.9.1/jquery.min.js"></script> 
<script type="text/javascript" src="../js/H-ui.js"></script>
<script type="text/javascript" src="../js/H-ui.admin.js"></script>
<script type="text/javascript">
$(function(){
	$("#btnYesSubmit").click(function(){
		$("#kind").val("已通过");
   		$("#form-leave-change").submit(); 
	});

	$("#btnNoSubmit").click(function(){
		if($("#remark").val()==""){
			alert("请在备注写明不通过理由！");
			return false;		
		}
		$("#kind").val("不通过");
   		$("#form-leave-change").submit(); 
	});
});
</script>
</body>
</html>