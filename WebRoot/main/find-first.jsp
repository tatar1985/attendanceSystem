<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%
String kind=request.getParameter("kind"); 
String kindName="";
String sid="";
String startTime="";
String userId="";
if(kind.equals("1")){
	kindName="queryUnit";
}else if(kind.equals("2")){
	kindName="memberQuery";
}else if(kind.equals("3")){
	kindName="memberShow";
	sid=request.getParameter("sid");		
}else if(kind.equals("4")){
	kindName="memberLinkEdit";
	sid=request.getParameter("sid");		
}else if(kind.equals("30")){
	kindName="leaveQuery";
}else if(kind.equals("31")){
	kindName="affairQuery";
}else if(kind.equals("41")){
	kindName="memberQueryRole";
}else if(kind.equals("50")){
	kindName="queryInOutCount";
}else if(kind.equals("51")){
	kindName="queryInOutSingle";
	userId=request.getParameter("userId");
	startTime=request.getParameter("startTime");
	System.out.println("startTime===="+startTime);
}

%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<body>

<form name="formname" action="<%=request.getContextPath()%>/tatar_/tatar.do" method="post">
<input type="hidden" name="method" value="queryMessage" />
<input type="hidden" name="sidInt" value="" />	
<input type="hidden" name="sid" value="<%=sid %>" />	
<input type="hidden" name="userId" value="<%=userId %>" />	
<input type="hidden" name="startTime" value="<%=startTime %>" />	
</form> 
</body>
</html>
<script >

	doGo();
	function doGo(){
		formname.method.value="<%=kindName%>";
		formname.submit(); 
	}
		
</script>