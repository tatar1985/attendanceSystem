<!DOCTYPE HTML>
<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.tataren.tatar.domain.TDepartment"%>
<%@page import="com.tataren.tatar.domain.TUsers"%>
<%
String path=(String)request.getContextPath();
TUsers webUser = (TUsers)request.getSession().getAttribute("webUser");

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
   <div class="border clearfix">
   <%if(!webUser.getRightt().equals("????????????")) {%>
       <span class="l_f">
        <a href="javascript:ovid()" id="member_add" class="btn btn-warning"><i class="icon-plus"></i>????????????</a>
       </span>
    <%}%>
     </div>
 <div class="search_style">
     <div class="list_style">
     <table class="table table-striped table-bordered table-hover" id="sample-table">
     <thead>
		 <tr>				
				<th >ID</th>
				<th >????????????</th>
				<th >????????????</th>
				<th >??????</th>				
				<th >?????????</th>
				<th >????????????</th>
				<th >????????????</th>               
				<th >????????????</th>
				<th >??????</th>             
				<th >??????</th>
			</tr>
		</thead>
	<tbody>
		<%
 ArrayList<TDepartment> list =( ArrayList<TDepartment>) request.getAttribute("list");
 if(list!=null){
for(int m=0 ;m < list.size();m++){	
	TDepartment bean = list.get(m);	

%>
	<tr class="text-c">
	<td><%=bean.getSid() %></td>
	<td><%=bean.getUnitName() %></td>
	<td><%=bean.getDepartmentName() %></td>
	<td><%=bean.getTel()==null?"":bean.getTel() %></td>
	<td><%=bean.getLinkMan()==null?"":bean.getLinkMan() %></td>
	<td><%=bean.getLinkPhone()==null?"":bean.getLinkPhone() %></td>
	<td><%=bean.getScoreOriginal()==null?"":bean.getScoreOriginal() %></td>
	<td><%=bean.getRemark()==null?"":bean.getRemark() %></td>
	<%if(bean.getState().equals("??????")){ %>
	<td><span class="label label-success radius"><%=bean.getState() %></span></td>
	<%}else { %>
	<td><span class="label label-defaunt radius"><%=bean.getState() %></span></td>
	<%} %>
	<td class="td-manage">
		  <!--
          <a onClick="member_stop(this,'10001')"  href="javascript:;" title="??????"  class="btn btn-xs btn-success"><i class="icon-ok bigger-120"></i></a> 
            -->
          <a title="??????" onclick="member_edit('<%=bean.getSid() %>','<%=bean.getUnitName() %>','<%=bean.getDepartmentName() %>','<%=bean.getTel() %>','<%=bean.getLinkMan() %>','<%=bean.getLinkPhone() %>','<%=bean.getScoreOriginal() %>','<%=bean.getRemark() %>','<%=bean.getState() %>')" href="javascript:;"  class="btn btn-xs btn-info" ><i class="icon-edit bigger-120"></i></a>         
          <a title="??????" href="javascript:;"  onclick="member_del(this,'<%=bean.getSid() %>')" class="btn btn-xs btn-warning" ><i class="icon-trash  bigger-120"></i></a>
    </td>
	</tr>
	<%}} %>
	</tbody>    
    </table>     
    </div>
     
 </div>
</div>
<!--??????????????????-->
<div class="add_menber" id="add_menber_style" style="display:none">
 <form class="form form-horizontal" action="<%=request.getContextPath()%>/tatar_/tatar.do" method="post"  id="tatarForm" name="tatarForm">
    <input type="hidden" name="method" value="departmentCreate" id="method"/>  
    <input type="hidden" name="sid" value="" id="sid"/>  
    <ul class=" page-content">
     <li><label class="label_name">???????????????</label><span class="add_name"><input value="<%=webUser.getUnitName() %>" name="unitName" type="text"  class="text_add" readonly/></span><div class="prompt r_f"></div></li>
     <li><label class="label_name">???????????????</label><span class="add_name"><input value="" name="departmentName" type="text"  class="text_add"/></span><div class="prompt r_f"></div></li>
     <li><label class="label_name">???????????????</label><span class="add_name"><input name="tel" type="text"  class="text_add"/></span><div class="prompt r_f"></div></li>
     <li><label class="label_name">???????????????</label><span class="add_name"><input name="linkMan" type="text"  class="text_add"/></span><div class="prompt r_f"></div></li>
     <li><label class="label_name">???????????????</label><span class="add_name"><input name="linkPhone" type="text"  class="text_add"/></span><div class="prompt r_f"></div></li>
     <li><label class="label_name">???????????????</label><span class="add_name"><input name="scoreOriginal" type="text"  class="text_add" onkeyup="this.value=this.value.replace(/[^\d]/ig,'')"/></span><div class="prompt r_f"></div></li>
     <li><label class="label_name">???????????????</label><span class="add_name"><input name="remark" type="text"  class="text_add"/></span><div class="prompt r_f"></div></li>
     <li><label class="label_name">???&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;??????</label><span class="add_name">
     <label><input name="state" type="radio" checked="checked" value="??????" class="ace"><span class="lbl">??????</span></label>&nbsp;&nbsp;&nbsp;
     <label><input name="state"type="radio" value="??????" class="ace"><span class="lbl">??????</span></label></span><div class="prompt r_f"></div></li>
    </ul>
    </form>
 </div>
 
 <!--??????????????????-->
<div class="edit_menber" id="edit_menber_style" style="display:none">
 <form class="form form-horizontal" action="<%=request.getContextPath()%>/tatar_/tatar.do" method="post"  id="tatarForm2" name="tatarForm2">
    <input type="hidden" name="method" value="unitEdit"/>  
    <input type="hidden" name="sid" value="" id="sid2"/>  
    <ul class=" page-content">
     <li><label class="label_name">???????????????</label><span class="edit_name"><input value="" name="unitName" id="unitName" type="text"  class="text_add" readonly/></span><div class="prompt r_f"></div></li>
     <li><label class="label_name">???????????????</label><span class="edit_name"><input value="" name="departmentName" id="departmentName" type="text"  class="text_add"/></span><div class="prompt r_f"></div></li>
     <li><label class="label_name">???????????????</label><span class="edit_name"><input name="tel" id="tel" type="text"  class="text_add"/></span><div class="prompt r_f"></div></li>
     <li><label class="label_name">???????????????</label><span class="edit_name"><input name="linkMan" id="linkMan" type="text"  class="text_add"/></span><div class="prompt r_f"></div></li>
     <li><label class="label_name">???????????????</label><span class="edit_name"><input name="linkPhone" id="linkPhone" type="text"  class="text_add"/></span><div class="prompt r_f"></div></li>
     <li><label class="label_name">???????????????</label><span class="edit_name"><input name="scoreOriginal" id="scoreOriginal" type="text"  class="text_add"/></span><div class="prompt r_f"></div></li>
     <li><label class="label_name">???????????????</label><span class="edit_name"><input name="remark" id="remark" type="text"  class="text_add"/></span><div class="prompt r_f"></div></li>
     <li><label class="label_name">???&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;??????</label><span class="add_name">
     <label><input name="state"  type="radio" value="??????" class="ace"><span class="lbl">??????</span></label>&nbsp;&nbsp;&nbsp;
     <label><input name="state"  type="radio" value="??????" class="ace"><span class="lbl">??????</span></label></span><div class="prompt r_f"></div></li>
    </ul>
    </form>
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
		  {"orderable":false,"aTargets":[4,5,8,9]}// ????????????????????????
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
 $('#member_add').on('click', function(){
    layer.open({
        type: 1,
        title: '????????????',
		maxmin: true, 
		shadeClose: true, //?????????????????????
        area : ['800px' , ''],
        content:$('#add_menber_style'),
		btn:['??????','??????'],
		yes:function(index,layero){	
		 var num=0;
		 var str="";
     $(".add_menber input[type$='text']").each(function(n){
          if($(this).val()=="")
          {
               
			   layer.alert(str+=""+$(this).attr("name")+"???????????????\r\n",{
                title: '?????????',				
				icon:0,								
          }); 
		    num++;
            return false;            
          } 
		 });
		  if(num>0){  return false;}	 	
          else{       
          $("#tatarForm").submit();
          
			  layer.alert('???????????????',{
               title: '?????????',				
			icon:1,		
			  });
			   layer.close(index);	
		  }		  		     				
		}
    });
});
/*??????-??????*/
function member_edit(sid,unitName,departmentName,tel,linkMan,linkPhone,scoreOriginal,remark,state){
	  
	  $("#sid2").val(sid);
	  $("#unitName").val(unitName);
	  $("#departmentName").val(departmentName);	  
	  $("#tel").val(tel);
	  $("#linkMan").val(linkMan);
	  $("#linkPhone").val(linkPhone);
	  $("#scoreOriginal").val(scoreOriginal);  
	  $("#remark").val(remark); 
	  $("input[name='state'][value="+state+"]").attr("checked",true);
	 layer.open({
        type: 1,
        title: '????????????',
		maxmin: true, 
		shadeClose: true, //?????????????????????
        area : ['800px' , ''],
        content:$('#edit_menber_style'),
		btn:['??????','??????'],
		yes:function(index,layero){	
		 var num=0;
		 var str="";
     $(".edit_menber input[type$='text']").each(function(n){
          if($(this).val()=="")
          {
               
			   layer.alert(str+=""+$(this).attr("name")+"???????????????\r\n",{
                title: '?????????',				
				icon:0,								
          }); 
		    num++;
            return false;            
          } 
		 });
		  if(num>0){  return false;}	 	
          else{
          $("#tatarForm2").submit();
			 layer.msg('?????????!',{icon:1,time:1000});
		  }		  		     				
		}
		 });
}
/*??????-??????*/
function member_del(obj,id){
	layer.confirm('?????????????????????',function(index){
	     $("#sid").val(id);
	     $("#method").val("departmentDelete");
		 $("#tatarForm").submit();
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
