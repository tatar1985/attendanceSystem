<!DOCTYPE HTML>
<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.tataren.tatar.domain.TView"%>
<%
String path=(String)request.getContextPath();

 %>
<html>
<head>
<meta charset="utf-8">
<meta name="renderer" content="webkit|ie-comp|ie-stand">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1.0,maximum-scale=1.0,member-scalable=no" />
<meta http-equiv="Cache-Control" content="no-siteapp" />
<!--[if lt IE 9]>
<script type="text/javascript" src="lib/html5.js"></script>
<script type="text/javascript" src="lib/respond.min.js"></script>
<script type="text/javascript" src="lib/PIE_IE678.js"></script>
<![endif]-->
<link href="../css/H-ui.min.css" rel="stylesheet" type="text/css" />
<link href="../css/H-ui.admin.css" rel="stylesheet" type="text/css" />
<link href="../lib/Hui-iconfont/1.0.1/iconfont.css" rel="stylesheet" type="text/css" />
<!--[if IE 6]>
<script type="text/javascript" src="http://lib.h-ui.net/DD_belatedPNG_0.0.8a-min.js" ></script>
<script>DD_belatedPNG.fix('*');</script>
<![endif]-->
<title>请假管理</title>
</head>
<body>
<nav class="breadcrumb"><i class="Hui-iconfont">&#xe67f;</i> 首页 <span class="c-gray en">&gt;</span> 用户中心 <span class="c-gray en">&gt;</span> 请假管理 <a class="btn btn-success radius r mr-20" style="line-height:1.6em;margin-top:3px" href="javascript:location.replace(location.href);" title="刷新" ><i class="Hui-iconfont">&#xe68f;</i></a></nav>
<div class="pd-20">
	 <div class="row cl">
     	<div class="formControls col-12">
    		<input type="text" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'startTime\')}'})" id="startTime" name="startTime" class="input-text Wdate" style="width:120px;" datatype="*2-16" >
	 		<input type="text" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'endTime\')}'})" id="endTime" name="endTime" class="input-text Wdate" style="width:120px;" datatype="*2-16"  >	 
	 		<input class="btn btn-primary radius" onClick="member_inOut()" type="button" value="查询"> （提示：按周统计数据，请选择周一和周五）
    	</div>
      </div>
	<div class="mt-20">
	<form  name="tatarForm" action="<%=request.getContextPath()%>/tatar_/tatar.do" method="post" class="form form-horizontal" id="tatarForm">
  	<input type="hidden" name="method" value="queryInOutCount"/> 
  	<input type="hidden" name="startTimeH" id="startTimeH" /> 
  	<input type="hidden" name="endTimeH" id="endTimeH" /> 
	<table class="table table-border table-bordered table-hover table-bg table-sort">
		<thead>
			<tr class="text-c">
				
				<th >ID</th>
				<th>微信名称</th>
				<th >真实名称</th>
				<th >单位名称</th>
				<th >科室名称</th>
				<th >开始日期</th>
				<th >结束日期</th>
				<th >公派</th>
				<th >请假</th>
				<th >上午签到</th>
				<th >上午签退</th>
				<th >下午签到</th>
				<th >下午签退</th>
				<th >正常天数</th>
				
			</tr>
		</thead>
		<tbody>
			<%
 ArrayList<TView> list =( ArrayList<TView>) request.getAttribute("list");
 if(list!=null){
for(int m=0 ;m < list.size();m++){	
	TView bean = list.get(m);	
%>
			<tr class="text-c" onclick="member_show('QueryBySingel','<%=path%>/find-first.jsp?kind=51&userId=<%=bean.getUserId() %>&startTime=<%=bean.getStartTime() %>','<%=bean.getSid() %>','900','750')">
			
				<td><%=bean.getSid() %></td>
				<td><%=bean.getUserName() %></td>
				<td><%=bean.getTrueName()==null?"":bean.getTrueName() %></td>
				<td><%=bean.getUnitName()==null?"":bean.getUnitName() %></td>
				<td><%=bean.getDepartmentName()==null?"":bean.getDepartmentName() %></td>
				<td><%=bean.getStartTime()==null?"":bean.getStartTime() %></td>
				<td><%=bean.getEndTime()==null?"":bean.getEndTime() %></td>
				<td><%=bean.getBizCount()==null?"":bean.getBizCount() %>
				<td><%=bean.getLeaveCount()==null?"":bean.getLeaveCount() %>
				<td><%=bean.getFirstCount()==null?"":bean.getFirstCount() %></td>
				<td><%=bean.getTwoCount()==null?"":bean.getTwoCount() %></td>
				<td><%=bean.getThreeCount()==null?"":bean.getThreeCount() %></td>
				<td><%=bean.getFourCount()==null?"":bean.getFourCount() %></td>
				<td><%=bean.getTotallyCount()==null?"":bean.getTotallyCount() %></td>
				
			</tr>
			<%}} %>
		</tbody>
	</table>
	</form>
	</div>
</div>
<script type="text/javascript" src="<%=request.getContextPath()%>/lib/jquery/1.9.1/jquery.min.js"></script> 
<script type="text/javascript" src="<%=request.getContextPath()%>/lib/layer/1.9.3/layer.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/lib/laypage/1.2/laypage.js"></script> 
<script type="text/javascript" src="<%=request.getContextPath()%>/lib/My97DatePicker/WdatePicker.js"></script> 
<script type="text/javascript" src="<%=request.getContextPath()%>/lib/datatables/1.10.0/jquery.dataTables.min.js"></script> 
<script type="text/javascript" src="<%=request.getContextPath()%>/js/H-ui.js"></script> 
<script type="text/javascript" src="<%=request.getContextPath()%>/js/H-ui.admin.js"></script> 
<script type="text/javascript">
$(function(){
	$('.table-sort').dataTable({
		"aaSorting": [[ 1, "desc" ]],//默认第几个排序
		"bStateSave": true,//状态保存
		"aoColumnDefs": [
		  //{"bVisible": false, "aTargets": [ 3 ]} //控制列的隐藏显示
		  {"orderable":false,"aTargets":[0,8,9]}// 制定列不参与排序
		]
	});
	$('.table-sort tbody').on( 'click', 'tr', function () {
		if ( $(this).hasClass('selected') ) {
			$(this).removeClass('selected');
		}
		else {
			table.$('tr.selected').removeClass('selected');
			$(this).addClass('selected');
		}
	});
});
/*用户-添加*/
function member_add(title,url,w,h){
	layer_show(title,url,w,h);
}
/*用户-查看*/
function member_show(title,url,id,w,h){
	layer_show(title,url,w,h);
}
/*用户-停用*/
function member_stop(obj,id){
	layer.confirm('确认要停用吗？',function(index){
		$(obj).parents("tr").find(".td-manage").prepend('<a style="text-decoration:none" onClick="member_start(this,id)" href="javascript:;" title="启用"><i class="Hui-iconfont">&#xe6e1;</i></a>');
		$(obj).parents("tr").find(".td-status").html('<span class="label label-defaunt radius">已停用</span>');
		$(obj).remove();
		layer.msg('已停用!',{icon: 5,time:1000});
	});
}

/*用户-启用*/
function member_start(obj,id){
	layer.confirm('确认要启用吗？',function(index){
		$(obj).parents("tr").find(".td-manage").prepend('<a style="text-decoration:none" onClick="member_stop(this,id)" href="javascript:;" title="停用"><i class="Hui-iconfont">&#xe631;</i></a>');
		$(obj).parents("tr").find(".td-status").html('<span class="label label-success radius">已启用</span>');
		$(obj).remove();
		layer.msg('已启用!',{icon: 6,time:1000});
	});
}
/*用户-编辑*/
function member_edit(title,url,id,w,h){
	layer_show(title,url,w,h);
}
/*密码-修改*/
function change_state(title,url,id,w,h){
	layer_show(title,url,w,h);	
}
/*用户-删除*/
function member_del(obj,id){
	layer.confirm('确认要删除吗？',function(index){	
		 $("#sid").val(id);
		 $("#form-leave-delete").submit();		
	});
	
}
function member_inOut(){
	
	if($("#startTime").val()==''){
		alert("开始时间不能为空");
		return ;
	}else if($("#endTime").val()==''){
		alert("结束时间不能为空");
		return ;
	}
	$("#startTimeH").val($("#startTime").val());
	$("#endTimeH").val($("#endTime").val());
	$("#method").val("queryInOutCount");
	$("#tatarForm").submit();
}
</script> 
</body>
</html>