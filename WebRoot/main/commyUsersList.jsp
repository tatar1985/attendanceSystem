<!DOCTYPE HTML>
<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.tataren.tatar.domain.TCommy"%>
<%
String path=(String)request.getContextPath();

 %>
<html>
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
        <!--[if lte IE 8]>
		  <link rel="stylesheet" href="assets/css/ace-ie.min.css" />
		<![endif]-->
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
<div class="margin clearfix">
 <div class="" id="Other_Management">
 <div class="search_style">
      
    
     <div class="list_style">
     <table class="table table-striped table-bordered table-hover" id="sample-table">
     <thead>
		 <tr>
				<th >??????</th>
				<th >?????????</th>
				<th >??????</th>
				<th >??????</th>
				<th >??????</th>
				<th >??????</th>   
                <th >??????</th>
                <th >????????????</th>   
                <th >??????</th>            
				<th >??????????????????</th>
				<th >??????????????????</th>
				<th >??????</th>
				<th >????????????</th>
				<th >????????????</th>
				<th >????????????</th>
				<th >??????????????????</th>
				<th >????????????</th>
				<th >???????????????</th>
				<th >????????????</th>
				<th >????????????</th>
				<th >????????????</th>
				<th >????????????</th>
				<th >?????????</th>
				<th >????????????</th>
				<th >????????????</th>
				<th >????????????</th>
				<th >??????????????????</th>
				<th >????????????</th>
				<th >????????????</th>
				<th >??????</th>
				<th >??????</th>
			</tr>
		</thead>
	<tbody>
		<%
 ArrayList<TCommy> list =( ArrayList<TCommy>) request.getAttribute("list");
 if(list!=null){
for(int m=0 ;m < list.size();m++){	
	TCommy bean = list.get(m);	

%>
		<tr class="text-c">
				
				<td nowrap><%=bean.getUnitName()==null?"":bean.getUnitName() %></td>
				<td nowrap><%=bean.getPartyOrganization()==null?"":bean.getPartyOrganization() %></td>
				<td nowrap><%=bean.getUnitKind()==null?"":bean.getUnitKind() %></td>
				<td nowrap><%=bean.getUserName()==null?"":bean.getUserName() %></td>
				<td nowrap><%=bean.getSex()==null?"":bean.getSex() %></td>
				<td nowrap><%=bean.getNation()==null?"":bean.getNation() %></td>
				<td nowrap><%=bean.getNativePlace()==null?"":bean.getNativePlace() %></td>
				<td nowrap><%=bean.getBirthday()==null?"":bean.getBirthday() %></td>
				<td nowrap><%=bean.getAge()==null?"":bean.getAge() %></td>
				<td nowrap><%=bean.getProbationTime()==null?"":bean.getProbationTime() %></td>
				<td nowrap><%=bean.getFormalTime()==null?"":bean.getFormalTime() %></td>
				<td nowrap><%=bean.getPartyStanding()==null?"":bean.getPartyStanding() %></td>
				<td nowrap><%=bean.getPartyPosition()==null?"":bean.getPartyPosition() %></td>
				<td nowrap><%=bean.getUnitNameNow()==null?"":bean.getUnitNameNow() %></td>
				<td nowrap><%=bean.getUnitPositionNow()==null?"":bean.getUnitPositionNow() %></td>
				<td nowrap><%=bean.getSkillPosition()==null?"":bean.getSkillPosition() %></td>
				<td nowrap><%=bean.getSkillPositionLevel()==null?"":bean.getSkillPositionLevel() %></td>
				<td nowrap><%=bean.getBachelor()==null?"":bean.getBachelor() %></td>
				<td nowrap><%=bean.getLastBachelor()==null?"":bean.getLastBachelor() %></td>
				<td nowrap><%=bean.getMobile()==null?"":bean.getMobile() %></td>
				<td nowrap><%=bean.getCurrentAddress()==null?"":bean.getCurrentAddress() %></td>
				<td nowrap><%=bean.getFloatingMember()==null?"":bean.getFloatingMember() %></td>
				<td nowrap><%=bean.getFlowAddress()==null?"":bean.getFlowAddress() %></td>
				<td nowrap><%=bean.getRetirement()==null?"":bean.getRetirement() %></td>
				<td nowrap><%=bean.getJobLevel()==null?"":bean.getJobLevel() %></td>
				<td nowrap><%=bean.getJobKind()==null?"":bean.getJobKind() %></td>
				<td nowrap><%=bean.getCadreManagement()==null?"":bean.getCadreManagement() %></td>
				<td nowrap><%=bean.getCommend()==null?"":bean.getCommend() %></td>
				<td nowrap><%=bean.getCommendLevel()==null?"":bean.getCommendLevel() %></td>
				<td nowrap><%=bean.getRemark()==null?"":bean.getRemark() %></td>
				
				
				<td nowrap>
          		<a title="??????"  class="btn btn-xs btn-success"><i class="icon-ok bigger-120"></i></a> 
          		<a title="??????" onclick="member_edit('310')" href="javascript:;"  class="btn btn-xs btn-info" ><i class="icon-edit bigger-120"></i></a>      
          		 <a title="??????" href="javascript:;"  onclick="member_del(this,'1')" class="btn btn-xs btn-warning" ><i class="icon-trash  bigger-120"></i></a>
          
         
          </td>
		</tr>
	<%}} %>
	

        </tbody>    
     </table>     
     </div>
     
 </div>
</div>

</body>
</html>
<script>
jQuery(function($) {
				var oTable1 = $('#sample-table').dataTable( {
				"aaSorting": [[ 0, "desc" ]],//?????????????????????
		"bStateSave": true,//????????????
		"aoColumnDefs": [
		  //{"bVisible": false, "aTargets": [ 3 ]} //????????????????????????
		  {"orderable":false,"aTargets":[1,2,3,8]}// ????????????????????????
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
			


/*??????-??????*/
function member_del(obj,id){
	layer.confirm('?????????????????????',function(index){
		$(obj).parents("tr").remove();
		layer.msg('?????????!',{icon:1,time:1000});
	});
}
//????????????
function integration_history(id){
	layer.open({
    type: 1,
	title:'??????????????????',
	area: ['800px','400px'],
	shadeClose: true,
	content: $('#integration_history'),
	})	
		$('#integration_history_list').dataTable({
		"aaSorting": [[ 1, "desc" ]],//?????????????????????
    "bStateSave": true,//????????????
    "aoColumnDefs": [{
	  "bSortable": false, 
	  "orderable":false, 
	  "aTargets": [0,1]
	 }]
});
	
	};
//????????????

function Browse_history(id){
	layer.open({
    type: 1,
	title:'????????????',
	area: ['800px','400px'],
	shadeClose: true,
	content: $('#Browse_history'),
	})	
$('#Browse_history_list').dataTable({
	"aaSorting": [[ 1, "desc" ]],//?????????????????????
    "bStateSave": true,//????????????
    "aoColumnDefs": [{
	  "bSortable": false, 
	  "orderable":false, 
	  "aTargets": [0,1]
	}]
});	
}


//????????????
function Order_history(id){
	layer.open({
    type: 1,
	title:'????????????',
	area: ['800px','400px'],
	shadeClose: true,
	content: $('#Order_history'),
	});	
		$('#Order_history_list').dataTable({
		"aaSorting": [[ 1, "desc" ]],//?????????????????????
    "bStateSave": true,//????????????
    "aoColumnDefs": [{
	  "bSortable": false, 
	  "orderable":false, 
	  "aTargets": [0,1]
	 }]
});		
}


</script>
