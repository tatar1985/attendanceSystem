<!DOCTYPE HTML>
<%@page language="java" contentType="text/html; charset=utf-8"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.tataren.tatar.domain.TLeave"%>
<%
String path=(String)request.getContextPath();

 %>
<html>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
        <link href="<%=request.getContextPath()%>/main/assets/css/bootstrap.min.css" rel="stylesheet" />
        <link rel="stylesheet" href="<%=request.getContextPath()%>/main/css/style.css"/>       
        <link href="<%=request.getContextPath()%>/main/assets/css/codemirror.css" rel="stylesheet">
        <link rel="stylesheet" href="<%=request.getContextPath()%>/main/assets/css/ace.min.css" />
        <link rel="stylesheet" href="<%=request.getContextPath()%>/main/assets/css/font-awesome.min.css" />
		<script src="<%=request.getContextPath()%>/main/assets/js/jquery.min.js"></script>
		<script type="text/javascript">
			window.jQuery || document.write("<script src='assets/js/jquery-2.0.3.min.js'>"+"<"+"/script>");
		</script>
		<script type="text/javascript">
			if("ontouchend" in document) document.write("<script src='assets/js/jquery.mobile.custom.min.js'>"+"<"+"/script>");
		</script>
		<script src="<%=request.getContextPath()%>/main/assets/js/bootstrap.min.js"></script>
		<script src="<%=request.getContextPath()%>/main/assets/js/typeahead-bs2.min.js"></script>
		<!-- page specific plugin scripts -->
		<script src="<%=request.getContextPath()%>/main/assets/js/jquery.dataTables.min.js"></script>
		<script src="<%=request.getContextPath()%>/main/assets/js/jquery.dataTables.bootstrap.js"></script>
        <script type="text/javascript" src="<%=request.getContextPath()%>/main/js/H-ui.js"></script> 
        <script type="text/javascript" src="<%=request.getContextPath()%>/main/js/H-ui.admin.js"></script> 
        <script src="<%=request.getContextPath()%>/main/assets/layer/layer.js" type="text/javascript" ></script>
        <script src="<%=request.getContextPath()%>/main/assets/laydate/laydate.js" type="text/javascript"></script>
<title>????????????</title>
</head>

<body>
<form class="form form-horizontal" action="<%=request.getContextPath()%>/tatar_/tatar.do" method="post"  id="form-member-query" name="form-member-query">
 <input type="hidden" name="method" value="leaveQuery"/>
<div class="page-content clearfix">
    <div id="Member_Ratings">
      <div class="d_Confirm_Order_style">
	 <br>
     <!---->
     <div class="border clearfix">
      
       <span class="l_f">
        <select name="yearId">
        <option value='2019'>2019??????</option>
        <option value='2018'>2018??????</option>
       </select>
        <button type="button" id="btnSubmit" class="btn_search">??????</button>
       </span>
    
     </div>
     <!---->
     <div class="table_menu_list">
       <table class="table table-striped table-bordered table-hover" id="sample-table">
		<thead>
		 <tr>
				
				<th >ID</th>
				<th>??????</th>
				<th >??????</th>
				<th >???</th>
				<th >????????????</th>
				<th >????????????</th>
				<th >????????????</th>
				<th >???</th>
				<th >?????????</th>
				<th >?????????</th>
				<th >?????????</th>
				<th >??????</th>
			</tr>
		</thead>
	<tbody>
	<%
 ArrayList<TLeave> list =( ArrayList<TLeave>) request.getAttribute("list");
 if(list!=null){
for(int m=0 ;m < list.size();m++){	
	TLeave bean = list.get(m);	
%>
		<tr class="text-c">
				
				<td nowrap><%=bean.getSid() %></td>
				<td nowrap ><%=bean.getUSERNAME()%></td>
				<td nowrap><%=bean.getDepartment()==null?"":bean.getDepartment()%></td>
				<td nowrap><%=bean.getTLEAVETYPE() %></td>
				<td><%=bean.getREMARK()==null?"":bean.getREMARK() %></td>
				<td nowrap><%=bean.getSTARTTIME() %><%=bean.getDAYTYPE()==null?"":bean.getDAYTYPE() %></td>
				<td nowrap><%=bean.getENDTIME() %><%=bean.getDAYTYPEID()==null?"":bean.getDAYTYPEID() %></td>
				<td nowrap><%=bean.getDAYNUM() %></td>
				<%if(bean.getFIRSTAPPROVALSTATE().equals("?????????")){ %>
					<td ><span class="label label-defaunt radius"><u style="cursor:pointer" onclick="change_state('????????????','../main/tatarLeaveChange.jsp?sid=<%=bean.getSid() %>&userName=<%=bean.getUSERNAME() %>&unitId=<%=bean.getTUNITID() %>&unitName=<%=bean.getUNITNAME() %>&leaveType=<%=bean.getTLEAVETYPE() %>&startTime=<%=bean.getSTARTTIME() %>&endTime=<%=bean.getENDTIME() %>&dayNum=<%=bean.getDAYNUM() %>&remark=<%=bean.getREMARK() %>&photo=<%=bean.getPHOTO() %>&mobile=<%=bean.getMOBILE() %>&level=1','10001','450','450')"><%=bean.getFIRSTAPPROVALSTATE()%>4</u></span></td>
					<td></td>
					<td></td>
	
				<%}else if(bean.getFIRSTAPPROVALSTATE().equals("?????????")) {%>
					<td class="td-status"><span class="label label-success radius"><u style="cursor:pointer"  onclick="change_state('????????????','/main/tatarLeaveDetail.jsp?sid=<%=bean.getSid() %>&userName=<%=bean.getUSERNAME() %>&unitName=<%=bean.getDepartment() %>&leaveType=<%=bean.getTLEAVETYPE() %>&startTime=<%=bean.getSTARTTIME() %>&endTime=<%=bean.getENDTIME() %>&dayNum=<%=bean.getDAYNUM() %>&remark=<%=bean.getREMARK() %>&photo=<%=bean.getPHOTO() %>&mobile=<%=bean.getMOBILE() %>&firstPerson=<%=bean.getFIRSTAPPROVALPERSON() %>&firstDate=<%=bean.getFIRSTAPPROVALDATE() %>&firstRemark=<%=bean.getFIRSTAPPROVALREMARK() %>&level=1','10001','600','470')"><%=bean.getFIRSTAPPROVALSTATE()%></u></span></td>
					<%if(bean.getSECONDAPPROVALSTATE().equals("?????????")) {%>
						<td><span class="label label-defaunt radius"><u style="cursor:pointer"  onclick="change_state('????????????','tatarLeaveChange.jsp?sid=<%=bean.getSid() %>&userName=<%=bean.getUSERNAME() %>&unitId=<%=bean.getTUNITID() %>&unitName=<%=bean.getDepartment() %>&leaveType=<%=bean.getTLEAVETYPE() %>&startTime=<%=bean.getSTARTTIME() %>&endTime=<%=bean.getENDTIME() %>&dayNum=<%=bean.getDAYNUM() %>&remark=<%=bean.getREMARK() %>&photo=<%=bean.getPHOTO() %>&mobile=<%=bean.getMOBILE() %>&level=2','10001','450','450')"><%=bean.getSECONDAPPROVALSTATE() %></u></span></td>
						<td></td>
					<%}else if(bean.getSECONDAPPROVALSTATE().equals("?????????")) {%>
						<td class="td-status"><span class="label label-success radius"><u style="cursor:pointer"  onclick="change_state('????????????','/main/tatarLeaveDetail.jsp?sid=<%=bean.getSid() %>&userName=<%=bean.getUSERNAME() %>&unitName=<%=bean.getDepartment() %>&leaveType=<%=bean.getTLEAVETYPE() %>&startTime=<%=bean.getSTARTTIME() %>&endTime=<%=bean.getENDTIME() %>&dayNum=<%=bean.getDAYNUM() %>&remark=<%=bean.getREMARK() %>&photo=<%=bean.getPHOTO() %>&mobile=<%=bean.getMOBILE() %>&firstPerson=<%=bean.getSECONDAPPROVALPERSON() %>&firstDate=<%=bean.getSECONDAPPROVALDATE() %>&firstRemark=<%=bean.getSECONDAPPROVALREMARK() %>&level=3','10001','600','470')"><%=bean.getSECONDAPPROVALSTATE() %></u></span></td>
						<%if(bean.getTHREEAPPROVALSTATE().equals("?????????")){ %>
						<td><span class="label label-defaunt radius"><u style="cursor:pointer"  onclick="change_state('????????????','/main/tatarLeaveChange.jsp?sid=<%=bean.getSid() %>&userName=<%=bean.getUSERNAME() %>&unitId=<%=bean.getTUNITID() %>&unitName=<%=bean.getDepartment() %>&leaveType=<%=bean.getTLEAVETYPE() %>&startTime=<%=bean.getSTARTTIME() %>&endTime=<%=bean.getENDTIME() %>&dayNum=<%=bean.getDAYNUM() %>&remark=<%=bean.getREMARK() %>&photo=<%=bean.getPHOTO() %>&mobile=<%=bean.getMOBILE() %>&level=3','10001','450','450')"><%=bean.getTHREEAPPROVALSTATE() %></u></span></td>
						<%}else if(bean.getTHREEAPPROVALSTATE().equals("????????????")){ %>
						<td class="td-status"><span class="label label-success radius"><u style="cursor:pointer"  onclick="change_state('????????????','/main/tatarLeaveDetail.jsp?sid=<%=bean.getSid() %>&userName=<%=bean.getUSERNAME() %>&unitName=<%=bean.getDepartment() %>&leaveType=<%=bean.getTLEAVETYPE() %>&startTime=<%=bean.getSTARTTIME() %>&endTime=<%=bean.getENDTIME() %>&dayNum=<%=bean.getDAYNUM() %>&remark=<%=bean.getREMARK() %>&photo=<%=bean.getPHOTO() %>&mobile=<%=bean.getMOBILE() %>&firstPerson=<%=bean.getSECONDAPPROVALPERSON() %>&firstDate=<%=bean.getSECONDAPPROVALDATE() %>&firstRemark=<%=bean.getSECONDAPPROVALREMARK() %>&level=3','10001','600','470')"><%=bean.getTHREEAPPROVALSTATE() %></u></span></td>
						<%}else if(bean.getTHREEAPPROVALSTATE().equals("?????????")){ %>
						<td class="td-status"><span class="label label-success radius"><u style="cursor:pointer"  onclick="change_state('????????????','/main/tatarLeaveDetail.jsp?sid=<%=bean.getSid() %>&userName=<%=bean.getDepartment() %>&unitName=<%=bean.getUNITNAME() %>&leaveType=<%=bean.getTLEAVETYPE() %>&startTime=<%=bean.getSTARTTIME() %>&endTime=<%=bean.getENDTIME() %>&dayNum=<%=bean.getDAYNUM() %>&remark=<%=bean.getREMARK() %>&photo=<%=bean.getPHOTO() %>&mobile=<%=bean.getMOBILE() %>&firstPerson=<%=bean.getSECONDAPPROVALPERSON() %>&firstDate=<%=bean.getSECONDAPPROVALDATE() %>&firstRemark=<%=bean.getSECONDAPPROVALREMARK() %>&level=3','10001','600','470')"><%=bean.getTHREEAPPROVALSTATE() %></u></span></td>
						<%}else{ %>
						<td><span class="label label-danger radius"><u style="cursor:pointer"  onclick="change_state('????????????','/main/tatarLeaveDetail.jsp?sid=<%=bean.getSid() %>&userName=<%=bean.getUSERNAME() %>&unitName=<%=bean.getDepartment() %>&leaveType=<%=bean.getTLEAVETYPE() %>&startTime=<%=bean.getSTARTTIME() %>&endTime=<%=bean.getENDTIME() %>&dayNum=<%=bean.getDAYNUM() %>&remark=<%=bean.getREMARK() %>&photo=<%=bean.getPHOTO() %>&mobile=<%=bean.getMOBILE() %>&firstPerson=<%=bean.getSECONDAPPROVALPERSON() %>&firstDate=<%=bean.getSECONDAPPROVALDATE() %>&firstRemark=<%=bean.getSECONDAPPROVALREMARK() %>&level=3','10001','600','470')"><%=bean.getSECONDAPPROVALSTATE() %></u></span></td>
						<%} %>
					<%}else{ %>
						<td><span class="label label-danger radius"><u style="cursor:pointer" onclick="change_state('????????????','/main/tatarLeaveDetail.jsp?sid=<%=bean.getSid() %>&userName=<%=bean.getUSERNAME() %>&unitName=<%=bean.getDepartment() %>&leaveType=<%=bean.getTLEAVETYPE() %>&startTime=<%=bean.getSTARTTIME() %>&endTime=<%=bean.getENDTIME() %>&dayNum=<%=bean.getDAYNUM() %>&remark=<%=bean.getREMARK() %>&photo=<%=bean.getPHOTO() %>&mobile=<%=bean.getMOBILE() %>&firstPerson=<%=bean.getSECONDAPPROVALPERSON() %>&firstDate=<%=bean.getSECONDAPPROVALDATE() %>&firstRemark=<%=bean.getSECONDAPPROVALREMARK() %>&level=1','10001','600','470')"><%=bean.getSECONDAPPROVALSTATE() %></u></span></td>
						<td></td>
					<%} %>
				<%}else { %>
					<td><span class="label label-danger radius"><u style="cursor:pointer" onclick="change_state('????????????','/main/tatarLeaveDetail.jsp?sid=<%=bean.getSid() %>&userName=<%=bean.getUSERNAME() %>&unitName=<%=bean.getUNITNAME() %>&leaveType=<%=bean.getTLEAVETYPE() %>&startTime=<%=bean.getSTARTTIME() %>&endTime=<%=bean.getENDTIME() %>&dayNum=<%=bean.getDAYNUM() %>&remark=<%=bean.getREMARK() %>&photo=<%=bean.getPHOTO() %>&mobile=<%=bean.getMOBILE() %>&firstPerson=<%=bean.getFIRSTAPPROVALPERSON() %>&firstDate=<%=bean.getFIRSTAPPROVALDATE() %>&firstRemark=<%=bean.getFIRSTAPPROVALREMARK() %>&level=1','10001','600','470')"><%=bean.getFIRSTAPPROVALSTATE()%></u></span></td>
					<td></td>
					<td></td>
				<%} %>
				
				
				<!--  
				<td class="td-status"><span class="label label-success radius">?????????</span></td>
				-->
				<td nowrap> 
				<a title="??????" href="javascript:;" onclick="member_del(this,'<%=bean.getSid()  %>')" class="btn btn-xs btn-warning" >
				<i class="icon-trash  bigger-120"></i></a>
				
				</td>
			</tr>
			<%}} %>
      </tbody>
	</table>
   </div>
  </div>
 </div>
</div>
<!--??????????????????-->
<div class="add_menber" id="add_menber_style" style="display:none">
  
    <ul class=" page-content">
     <li><label class="label_name">???????????????</label><span class="add_name"><input value="" name="????????????" type="text"  class="text_add"/></span><div class="prompt r_f"></div></li>
     <li><label class="label_name">???????????????</label><span class="add_name"><input name="????????????" type="text"  class="text_add"/></span><div class="prompt r_f"></div></li>
     <li><label class="label_name">???????????????</label><span class="add_name">
     <label><input name="form-field-radio" type="radio" checked="checked" class="ace"><span class="lbl">??????</span></label>&nbsp;&nbsp;&nbsp;
     <label><input name="form-field-radio" type="radio" class="ace"><span class="lbl">??????</span></label>&nbsp;&nbsp;&nbsp;
     <label><input name="form-field-radio" type="radio" class="ace"><span class="lbl">??????</span></label>
     </span>
     <div class="prompt r_f"></div>
     </li>
     <li><label class="label_name">???????????????</label><span class="add_name"><input name="????????????" type="text"  class="text_add"/></span><div class="prompt r_f"></div></li>
     <li><label class="label_name">???????????????</label><span class="add_name"><input name="????????????" type="text"  class="text_add"/></span><div class="prompt r_f"></div></li>
     <li><label class="label_name">???????????????</label><span class="add_name"><input name="????????????" type="text"  class="text_add"/></span><div class="prompt r_f"></div></li>
     <li class="adderss"><label class="label_name">???????????????</label><span class="add_name"><input name="????????????" type="text"  class="text_add" style=" width:350px"/></span><div class="prompt r_f"></div></li>
     <li><label class="label_name">???&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;??????</label><span class="add_name">
     <label><input name="form-field-radio1" type="radio" checked="checked" class="ace"><span class="lbl">??????</span></label>&nbsp;&nbsp;&nbsp;
     <label><input name="form-field-radio1"type="radio" class="ace"><span class="lbl">??????</span></label></span><div class="prompt r_f"></div></li>
    </ul>
 </div>
</body>
</html>
<script>
jQuery(function($) {
				var oTable1 = $('#sample-table').dataTable( {
				"aaSorting": [[ 0, "desc" ]],//?????????????????????
		"bStateSave": true,//????????????
		"aoColumnDefs": [
		  {"orderable":false,"aTargets":[7,8,9,10,11]}// ????????????????????????
		] } );
				
				
				$('table th input:checkbox').on('click' , function(){
					var that = this;
					$(this).closest('table').find('tr > td:first-child input:checkbox')
					.each(function(){
						this.checked = that.checked;
						$(this).closest('tr').toggleClass('selected');
					});
						
				});
			
			
				$('[data-rel="tooltip"]').tooltip({placement: tooltip_placement});
				function tooltip_placement(context, source) {
					var $source = $(source);
					var $parent = $source.closest('table')
					var off1 = $parent.offset();
					var w1 = $parent.width();
			
					var off2 = $source.offset();
					var w2 = $source.width();
			
					if( parseInt(off2.left) < parseInt(off1.left) + parseInt(w1 / 2) ) return 'right';
					return 'left';
				}
			})
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
function member_show(title,url,id,w,h){
	layer_show(title,url+'#?='+id,w,h);
}
/*??????-??????*/
function member_stop(obj,id){
	layer.confirm('?????????????????????',function(index){
		$(obj).parents("tr").find(".td-manage").prepend('<a style="text-decoration:none" class="btn btn-xs " onClick="member_start(this,id)" href="javascript:;" title="??????"><i class="icon-ok bigger-120"></i></a>');
		$(obj).parents("tr").find(".td-status").html('<span class="label label-defaunt radius">?????????</span>');
		$(obj).remove();
		layer.msg('?????????!',{icon: 5,time:1000});
	});
}

/*??????-??????*/
function member_start(obj,id){
	layer.confirm('?????????????????????',function(index){
		$(obj).parents("tr").find(".td-manage").prepend('<a style="text-decoration:none" class="btn btn-xs btn-success" onClick="member_stop(this,id)" href="javascript:;" title="??????"><i class="icon-ok bigger-120"></i></a>');
		$(obj).parents("tr").find(".td-status").html('<span class="label label-success radius">?????????</span>');
		$(obj).remove();
		layer.msg('?????????!',{icon: 6,time:1000});
	});
}
/*??????-??????*/
function member_edit(id){
	  layer.open({
        type: 1,
        title: '??????????????????',
		maxmin: true, 
		shadeClose:false, //?????????????????????
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
			  layer.alert('???????????????',{
               title: '?????????',				
			icon:1,		
			  });
			   layer.close(index);	
		  }		  		     				
		}
    });
}
/*??????-??????*/
function member_del(obj,id){
	layer.confirm('?????????????????????',function(index){
		$(obj).parents("tr").remove();
		layer.msg('?????????!',{icon:1,time:1000});
	});
}


function change_state(title,url,id,w,h){
	layer_show(title,url,w,h);	
}
$("#btnSubmit").click(function(){
	$("#form-member-query").submit();
  
});
</script>