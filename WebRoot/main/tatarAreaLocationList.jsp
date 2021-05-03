<!DOCTYPE HTML>
<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.tataren.tatar.domain.TArea"%>
<%
String path=(String)request.getContextPath();

 %>
<html>
	<style media="screen">
		body{padding:100px;font-size: 14px;}
		h1{font-size: 26px;}
		p{font-size: 14px; margin-top: 10px;}
		pre{background:#eee;border:1px solid #ddd;border-left:4px solid #f60;padding:15px;margin-top: 15px;}
		h2{font-size: 20px;margin-top: 20px;}
		.case{margin-top: 15px}
		.ECalendar{width:200px;height:30px;}
	</style>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="renderer" content="webkit|ie-comp|ie-stand">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta http-equiv="Cache-Control" content="no-siteapp" />
<link href="<%=request.getContextPath()%>/main/assets/css/bootstrap.min.css" rel="stylesheet" />
        <link rel="stylesheet" href="<%=request.getContextPath()%>/main/css/style.css"/>       
        <link href="<%=request.getContextPath()%>/main/assets/css/codemirror.css" rel="stylesheet">
        <link rel="stylesheet" href="<%=request.getContextPath()%>/main/assets/css/ace.min.css" />
        <link rel="stylesheet" href="<%=request.getContextPath()%>/main/font/css/font-awesome.min.css" />
        <link rel="stylesheet" href="<%=request.getContextPath()%>/main/assets/css/font-awesome.min.css" />                                                    

		

		
		<script src="<%=request.getContextPath()%>/main/js/jquery-1.9.1.min.js"></script>
        <script src="<%=request.getContextPath()%>/main/assets/js/bootstrap.min.js"></script>
		<script src="<%=request.getContextPath()%>/main/assets/js/typeahead-bs2.min.js"></script>           	
		<script src="<%=request.getContextPath()%>/main/assets/js/jquery.dataTables.min.js"></script>
		<script src="<%=request.getContextPath()%>/main/assets/js/jquery.dataTables.bootstrap.js"></script>
        <script src="<%=request.getContextPath()%>/main/assets/layer/layer.js" type="text/javascript" ></script>          
        <script src="<%=request.getContextPath()%>/main/assets/laydate/laydate.js" type="text/javascript"></script>
<title></title>
</head>

<body>
<form class="form form-horizontal" action="<%=request.getContextPath()%>/tatar_/tatar.do" method="post"  id="form-member-query" name="form-member-login">
  <input type="hidden" name="method" value="areaInOutMap"/>
  <input type="hidden" name="areaId" id="areaId" value=""/>
<div class="margin clearfix">
 <div class="" id="Other_Management">

 <div class="search_style">
     <div class="list_style">
     <table class="table table-striped table-bordered table-hover" id="sample-table">
     <thead>
		 <tr>				
				<th >ID</th>
				<th >区域名称</th>
				<th >单位名称</th>
				<th >活动名称</th>				
				<th >开始时间</th>
				<th >结束时间</th>
				<th >活动经度</th>               
				<th >活动维度</th>  
				<th >状态</th>   
				<th >操作</th>
			</tr>
		</thead>
	<tbody>
		<%
 ArrayList<TArea> list =( ArrayList<TArea>) request.getAttribute("list");
 if(list!=null){
for(int m=0 ;m < list.size();m++){	
	TArea bean = list.get(m);	

%>
	<tr class="text-c">
	<td><%=bean.getSid() %></td>
	<td><%=bean.getRegionName() %></td>
	<td><%=bean.getUnitName() %></td>
	<td><%=bean.getAreaName() %></td>
	<td><%=bean.getStartTime() %></td>
	<td><%=bean.getEndTime() %></td>
	<td><%=bean.getLngId()==null?"":bean.getLngId() %></td>
	<td><%=bean.getLatId()==null?"":bean.getLatId() %></td>
	<%if(bean.getFlagState().equals("正常")){ %>
	<td><span class="label label-success radius"><%=bean.getFlagState() %></span></td>
	<%}else { %>
	<td><span class="label label-defaunt radius"><%=bean.getFlagState() %></span></td>
	<%} %>
	<td class="td-manage">
		  <!--
          <a onClick="member_stop(this,'10001')"  href="javascript:;" title="停用"  class="btn btn-xs btn-success"><i class="icon-ok bigger-120"></i></a> 
            -->
      
          <a title="位置" onclick="member_show('<%=bean.getSid() %>')" class="btn btn-xs btn-pink ads_link" ><i class="icon-map-marker  bigger-120"></i></a>
      
    </td>
	</tr>
	<%}} %>
	</tbody>    
    </table>     
    </div>
     
 </div>
</div>



</div>
</form>
</body>
</html>
<script>
jQuery(function($) {
				var oTable1 = $('#sample-table').dataTable( {
				"aaSorting": [[ 0, "desc" ]],//默认第几个排序
		"bStateSave": true,//状态保存
		"aoColumnDefs": [
		  //{"bVisible": false, "aTargets": [ 3 ]} //控制列的隐藏显示
		  {"orderable":false,"aTargets":[4,5,8,9]}// 制定列不参与排序
		] } );
				
				
				$('table th input:checkbox').on('click' , function(){
					var that = this;
					$(this).closest('table').find('tr > td:first-child input:checkbox')
					.each(function(){
						this.checked = that.checked;
						$(this).closest('tr').toggleClass('selected');
					});
						
				});
			});
			
function member_show(id){
	
	$("#areaId").val(id);
	$("#form-member-query").submit();
}
  
  
		
			


</script>
