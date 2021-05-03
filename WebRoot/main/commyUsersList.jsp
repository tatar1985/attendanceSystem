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
				<th >单位</th>
				<th >党支部</th>
				<th >类别</th>
				<th >姓名</th>
				<th >性别</th>
				<th >民族</th>   
                <th >籍贯</th>
                <th >出生年月</th>   
                <th >年龄</th>            
				<th >预备党员时间</th>
				<th >正式党员时间</th>
				<th >党龄</th>
				<th >党内职务</th>
				<th >所在单位</th>
				<th >所在职务</th>
				<th >专业技术职务</th>
				<th >职称类别</th>
				<th >全日制学历</th>
				<th >最后学历</th>
				<th >联系电话</th>
				<th >现居住地</th>
				<th >流动党员</th>
				<th >流入地</th>
				<th >退休状态</th>
				<th >职务层次</th>
				<th >职务类别</th>
				<th >干部管理权限</th>
				<th >表彰情况</th>
				<th >表彰级别</th>
				<th >备注</th>
				<th >操作</th>
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
          		<a title="停用"  class="btn btn-xs btn-success"><i class="icon-ok bigger-120"></i></a> 
          		<a title="编辑" onclick="member_edit('310')" href="javascript:;"  class="btn btn-xs btn-info" ><i class="icon-edit bigger-120"></i></a>      
          		 <a title="删除" href="javascript:;"  onclick="member_del(this,'1')" class="btn btn-xs btn-warning" ><i class="icon-trash  bigger-120"></i></a>
          
         
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
				"aaSorting": [[ 0, "desc" ]],//默认第几个排序
		"bStateSave": true,//状态保存
		"aoColumnDefs": [
		  //{"bVisible": false, "aTargets": [ 3 ]} //控制列的隐藏显示
		  {"orderable":false,"aTargets":[1,2,3,8]}// 制定列不参与排序
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
			


/*用户-删除*/
function member_del(obj,id){
	layer.confirm('确认要删除吗？',function(index){
		$(obj).parents("tr").remove();
		layer.msg('已删除!',{icon:1,time:1000});
	});
}
//积分浏览
function integration_history(id){
	layer.open({
    type: 1,
	title:'积分获取记录',
	area: ['800px','400px'],
	shadeClose: true,
	content: $('#integration_history'),
	})	
		$('#integration_history_list').dataTable({
		"aaSorting": [[ 1, "desc" ]],//默认第几个排序
    "bStateSave": true,//状态保存
    "aoColumnDefs": [{
	  "bSortable": false, 
	  "orderable":false, 
	  "aTargets": [0,1]
	 }]
});
	
	};
//浏览记录

function Browse_history(id){
	layer.open({
    type: 1,
	title:'浏览记录',
	area: ['800px','400px'],
	shadeClose: true,
	content: $('#Browse_history'),
	})	
$('#Browse_history_list').dataTable({
	"aaSorting": [[ 1, "desc" ]],//默认第几个排序
    "bStateSave": true,//状态保存
    "aoColumnDefs": [{
	  "bSortable": false, 
	  "orderable":false, 
	  "aTargets": [0,1]
	}]
});	
}


//购物记录
function Order_history(id){
	layer.open({
    type: 1,
	title:'购物记录',
	area: ['800px','400px'],
	shadeClose: true,
	content: $('#Order_history'),
	});	
		$('#Order_history_list').dataTable({
		"aaSorting": [[ 1, "desc" ]],//默认第几个排序
    "bStateSave": true,//状态保存
    "aoColumnDefs": [{
	  "bSortable": false, 
	  "orderable":false, 
	  "aTargets": [0,1]
	 }]
});		
}


</script>
