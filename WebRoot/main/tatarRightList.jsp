<!DOCTYPE HTML>
<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.tataren.tatar.domain.TUsers"%>
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
				<th >用户ID</th>
				<th >头像</th>
				<th >微信名</th>
				<th >姓名</th>
				<th >手机</th>
				<th >单位名称</th>   
                <th >科室名称</th>
                <th >积分</th>  
                <th >权限</th>  
                <th >状态</th>            
				<th >操作</th>
			</tr>
		</thead>
	<tbody>
		<%
 ArrayList<TUsers> list =( ArrayList<TUsers>) request.getAttribute("list");
 if(list!=null){
for(int m=0 ;m < list.size();m++){	
	TUsers bean = list.get(m);	

%>
		<tr class="text-c">
				
				<td nowrap><%=bean.getSid() %></td>
				<td nowrap><img width="40" height="40" alt="" src="<%=bean.getHeadUrl()==null?"":bean.getHeadUrl().substring(0, bean.getHeadUrl().length()-1) %>46"></td>
				<td nowrap><%=bean.getChatName()==null?"":bean.getChatName() %></td>
				<td nowrap><%=bean.getUserName()==null?"":bean.getUserName() %></td>
				<td nowrap><%=bean.getMobile()==null?"":bean.getMobile() %></td>
				<td nowrap><%=bean.getUnitName()==null?"":bean.getUnitName() %></td>
				<td nowrap><%=bean.getDepartmentName()==null?"":bean.getDepartmentName() %></td>
				<td nowrap><%=bean.getAccount() %></td>
				<td nowrap><%=bean.getRightt() %></td>
				<%if(bean.getState().equals("正常")){ %>
	<td><span class="label label-success radius"><%=bean.getState() %></span></td>
	<%}else { %>
	<td><span class="label label-defaunt radius"><%=bean.getState() %></span></td>
	<%} %>
				<td nowrap>
      		<a title="编辑" onclick="member_edit('<%=bean.getSid() %>','<%=bean.getUserName() %>','<%=bean.getRightt() %>','<%=bean.getLoginName() %>','<%=bean.getPwd() %>','<%=bean.getAccount() %>','<%=bean.getLeave1() %>','<%=bean.getLeave2() %>','<%=bean.getLeave3() %>')" href="javascript:;"  class="btn btn-xs btn-info" ><i class="icon-edit bigger-100"></i></a>          
          </td>
		</tr>
	<%}} %>
	

        </tbody>    
     </table>     
     </div>
     
 </div>
</div>
<!--修改用户图层-->
<div class="edit_menber" id="edit_menber_style" style="display:none">
 <form class="form form-horizontal" action="<%=request.getContextPath()%>/tatar_/tatar.do" method="post"  id="tatarForm2" name="tatarForm2">
    <input type="hidden" name="method" value="rightEdit"/>  
    <input type="hidden" name="sid" value="" id="sid2"/>  
    <ul class=" page-content">
     <li><label class="label_name">真实姓名：</label><span class="edit_name"><input value="" name="userName" id="userName" type="text"  class="text_add"/></span></li>
     <li><label class="label_name">权限范围：</label><span class="edit_name"><select id="rightt" name="rightt" class="text_add" style="width: 160px; "><option value="工作人员">工作人员</option><option value="科室管理员">科室管理员</option><option value="单位管理员">单位管理员</option></select></span></li>
     <li><label class="label_name">登录用户：</label><span class="edit_name"><input value="" name="loginName" id="loginName" type="text"  class="text_add"/></span></li>
     <li><label class="label_name">请假一级：</label><span class="edit_name"><select id="leave1" name="leave1" class="text_add" style="width: 160px; "><option value=""></option><option value="一级">一级</option></select></span></li>
     <li><label class="label_name">登录密码：</label><span class="edit_name"><input value="" name="pwd" id="pwd" type="text"  class="text_add"/></span></li>
     <li><label class="label_name">请假二级：</label><span class="edit_name"><select id="leave2" name="leave2" class="text_add" style="width: 160px; "><option value=""></option><option value="二级">二级</option></select></span></li>
     <li><label class="label_name">分配积分：</label><span class="edit_name"><input name="account" id="account" type="text"  class="text_add"/></span><div class="prompt r_f"></div></li>
     <li><label class="label_name">请假三级：</label><span class="edit_name"><select id="leave3" name="leave3" class="text_add" style="width: 160px; "><option value=""></option><option value="三级">三级</option></select></span></li>
    </ul>
    </form>
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

function member_edit(sid,userName,rightt,loginName,pwd,account,leave1,leave2,leave3){
	  
	  $("#sid2").val(sid);
	  $("#userName").val(userName);
	  $("#rightt").val(rightt);	
	  $("#loginName").val(loginName);		
	  $("#pwd").val(pwd);		
	  $("#account").val(account);	
	  $("#leave1").val(leave1);
	  $("#leave2").val(leave2);	
	  $("#leave3").val(leave3);				 
	 layer.open({
        type: 1,
        title: '修改模块',
		maxmin: true,  
		shadeClose: true, //点击遮罩关闭层
        area : ['800px' , ''],
        content:$('#edit_menber_style'),
		btn:['提交','取消'],
		yes:function(index,layero){	
		 var num=0;
		 var str="";
     $(".edit_menber input[type$='text']").each(function(n){
          if($(this).val()=="")
          {
               
			   layer.alert(str+=""+$(this).attr("name")+"不能为空！\r\n",{
                title: '提示框',				
				icon:0,								
          }); 
		    num++;
            return false;            
          } 
		 });
		  if(num>0){  return false;}	 	
          else{
          $("#tatarForm2").submit();
			 layer.msg('已修改!',{icon:1,time:1000});
		  }		  		     				
		}
		 });
}
</script>
