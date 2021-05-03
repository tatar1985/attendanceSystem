<!DOCTYPE HTML>
<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@page import="com.tataren.tatar.domain.TUsers"%>
<%

TUsers webUser = (TUsers)request.getSession().getAttribute("webUser");
String sid=request.getParameter("sid"); 
String level=request.getParameter("level"); 
String unitId=request.getParameter("unitId"); 
String userName=new String(request.getParameter("userName").getBytes("iso-8859-1"),"UTF-8");
String unitName=new String(request.getParameter("unitName").getBytes("iso-8859-1"),"UTF-8");
String leaveType=new String(request.getParameter("leaveType").getBytes("iso-8859-1"),"UTF-8");
String startTime=request.getParameter("startTime"); 
String endTime=request.getParameter("endTime"); 
String dayNum=request.getParameter("dayNum"); 
String remark=new String(request.getParameter("remark").getBytes("iso-8859-1"),"UTF-8");
String photo=request.getParameter("photo"); 
String mobile=request.getParameter("mobile"); 
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
<form  name="form-leave-change" action="<%=request.getContextPath()%>/tatar_/tatar.do" method="post" class="form form-horizontal" id="form-leave-change">
  <input type="hidden" name="method" value="leaveChange"/> 
  <input type="hidden" name="level" value="<%=level%>"/> 
  <input type="hidden" name="kind" id="kind" value="1"/>   
  <input type="hidden" name="sid"  value="<%=sid%>"/>  
  <table class="table">
    <tbody>
      <tr>
        <th class="text-r" width="20" >类别：</th>
        <td width="100"><%=leaveType %></td>
        <th class="text-r"  width="20">手机：</th>
        <td width="60"><%=mobile %></td>
      </tr>
      <tr>
        <th class="text-r"  width="20" >日期：</th>
        <td width="90"><%=startTime %>到<%=endTime %></td>
         <th class="text-r"  width="20" >天数：</th>
        <td><%=dayNum %></td>
      </tr>    
       <tr>
        <th class="text-r"  width="20" >理由：</th>
        <td colspan=3><%=remark %></td>
         
      </tr>   
 	  <tr>
        <th class="text-r"   >备注：</th>
        <td colspan=3>
        <input type="text" class="input-text" value="" placeholder="" id="remark" name="remark"  >
        </td>
         
      </tr>
    </tbody>
  </table>
  </form>
  <br><br>
  <%if(webUser.getUnitId().equals(unitId)&webUser.getRightt().equals("单位管理员")){ %>
   <div class="form-label col-12" align="center">
        <input class="btn btn-success radius" type="button"  value="&nbsp;&nbsp;通1&nbsp;&nbsp;&nbsp;过 &nbsp;&nbsp;" id="btnYesSubmit" name="btnYesSubmit">&nbsp;&nbsp;&nbsp;&nbsp;
        <input class="btn btn-danger radius" type="button"  value="&nbsp;&nbsp;不通过 &nbsp;&nbsp;" id="btnNoSubmit" name="btnNoSubmit">
   </div>
	<%} %>
</div>
<script src="<%=request.getContextPath()%>/main/assets/js/jquery.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/main/js/H-ui.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/main/js/H-ui.admin.js"></script>
<script type="text/javascript">
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
</script>
</body>
</html>