<!DOCTYPE HTML>
<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.tataren.tatar.domain.TInout"%>
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
        <!--[if lte IE 8]>
		  <link rel="stylesheet" href="assets/css/ace-ie.min.css" />
		<![endif]-->
		<script type="text/javascript" src="<%=request.getContextPath()%>/lib/layer/1.9.3/layer.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/lib/laypage/1.2/laypage.js"></script> 
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/H-ui.admin.js"></script> 
		<script src="<%=request.getContextPath()%>/main/js/jquery-1.9.1.min.js"></script>
        <script src="<%=request.getContextPath()%>/main/assets/js/bootstrap.min.js"></script>
		<script src="<%=request.getContextPath()%>/main/assets/js/typeahead-bs2.min.js"></script>           	
		<script src="<%=request.getContextPath()%>/main/assets/js/jquery.dataTables.min.js"></script>
		<script src="<%=request.getContextPath()%>/main/assets/js/jquery.dataTables.bootstrap.js"></script>
        <script src="<%=request.getContextPath()%>/main/assets/layer/layer.js" type="text/javascript" ></script>          
        <script src="<%=request.getContextPath()%>/main/assets/laydate/laydate.js" type="text/javascript"></script>


<title>??????</title>
</head>

<body>
 <form class="form form-horizontal" action="<%=request.getContextPath()%>/tatar_/tatar.do" method="post"  id="form-member-query" name="form-member-login">
 <input type="hidden" name="method" value="InOutQuery"/>
<div class="margin clearfix">
 <div class="Guestbook_style">
 <div class="search_style">
      <div class="title_names">????????????</div>
      <ul class="search_content clearfix">
       <li><label class="l_f">??????</label><input class="inline laydate-icon" id="start" style=" margin-left:10px;" name="start"></li>
       <li style="width:90px;"><button type="button" id="btnSubmit" class="btn_search"><i class="icon-search"></i>??????</button></li>
      </ul>
    </div>
   
    <!--????????????-->
    <div class="Guestbook_list">
      <table class="table table-striped table-bordered table-hover" id="sample-table">
      <thead>
		 <tr>
          <th >??????</th>
          <th >??????</th>
          <th >??????</th>
          <th >??????</th>
          <th >??????</th>                
          <th >??????</th>
          <th >??????</th>                
          <th >??????</th>
          </tr>
      </thead>
	<tbody>
		<%
 ArrayList<TInout> list =( ArrayList<TInout>) request.getAttribute("list");
 if(list!=null){
for(int m=0 ;m < list.size();m++){	
	TInout bean = list.get(m);	

%>
		<tr class="text-c" >
				<td nowrap><%=bean.getUSERNAME()==null?"":bean.getUSERNAME() %></td>
				<td nowrap><%=bean.getUNITNAME()==null?"":bean.getUNITNAME() %></td>
				<td nowrap><%=bean.getDAYTYPE()==null?"":bean.getDAYTYPE() %></td>
				<td nowrap><%=bean.getDAYTIME()==null?"":bean.getDAYTIME() %></td>
				
				<%if(bean.getDAYTYPE().equals("??????")&&bean.getFIRSTCHECKSTATE().equals("?????????")){ %>
				<td nowrap><span class="label label-danger radius">??????</span></td>
				<%}else if(bean.getDAYTYPE().equals("??????")&&Integer.valueOf(bean.getFIRSTCHECKTIME().substring(12, 13))>=9){ %>
				<td onclick="member_show('QueryBySingel','<%=path%>/tatar_/tatar.do?method=InOutQuerySingel&usersid=<%=bean.getUSERSID() %>&daytime=<%=bean.getDAYTIME() %>','<%=bean.getSid() %>','900','630')" nowrap><span class="label label-alarm radius">??????</span></td>
				<%}else if(bean.getDAYTYPE().equals("??????")&&bean.getFIRSTCHECKLATIID().substring(0, 5).equals((bean.getLATID()==null?"11111111":bean.getLATID()).substring(0, 5))&&bean.getFIRSTCHECKLONGID().substring(0, 6).equals((bean.getLNGID()==null?"11111111":bean.getLNGID()).substring(0, 6))){ %>				
				<td onclick="member_show('QueryBySingel','<%=path%>/tatar_/tatar.do?method=InOutQuerySingel&usersid=<%=bean.getUSERSID() %>&daytime=<%=bean.getDAYTIME() %>','<%=bean.getSid() %>','900','630')" nowrap><span class="label label-success radius">??????</span></td>
				<%}else if(!bean.getDAYTYPE().equals("??????")&&bean.getFIRSTCHECKSTATE().equals("?????????")){ %>
				<td nowrap><span class="label label-success radius">??????</span></td>
				<%}else if(!bean.getDAYTYPE().equals("??????")&&!bean.getFIRSTCHECKSTATE().equals("?????????")){ %>
				<td onclick="member_show('QueryBySingel','<%=path%>/tatar_/tatar.do?method=InOutQuerySingel&usersid=<%=bean.getUSERSID() %>&daytime=<%=bean.getDAYTIME() %>','<%=bean.getSid() %>','900','630')" nowrap><span class="label label-success radius">??????</span></td>
				<%}else{ %>
				<td onclick="member_show('QueryBySingel','<%=path%>/tatar_/tatar.do?method=InOutQuerySingel&usersid=<%=bean.getUSERSID() %>&daytime=<%=bean.getDAYTIME() %>','<%=bean.getSid() %>','900','630')" nowrap><span class="label label-alarm radius">??????</span></td>
				<%} %>
				
				<%if(bean.getDAYTYPE().equals("??????")&&bean.getTWOCHECKSTATE().equals("?????????")){ %>
				<td nowrap><span class="label label-danger radius">??????</span></td>
				<%}else if(bean.getDAYTYPE().equals("??????")&&Integer.valueOf(bean.getTWOCHECKTIME().substring(12, 13))>=9){ %>
				<td  onclick="member_show('QueryBySingel','<%=path%>/tatar_/tatar.do?method=InOutQuerySingel&usersid=<%=bean.getUSERSID() %>&daytime=<%=bean.getDAYTIME() %>','<%=bean.getSid() %>','900','630')" nowrap><span class="label label-alarm radius">??????</span></td>
				<%}else if(bean.getDAYTYPE().equals("??????")&&bean.getTWOCHECKLATIID().substring(0, 5).equals((bean.getLATID()==null?"11111111":bean.getLATID()).substring(0, 5))&&bean.getTWOCHECKLONGID().substring(0, 6).equals((bean.getLNGID()==null?"11111111":bean.getLNGID()).substring(0, 6))){ %>	
				<td  onclick="member_show('QueryBySingel','<%=path%>/tatar_/tatar.do?method=InOutQuerySingel&usersid=<%=bean.getUSERSID() %>&daytime=<%=bean.getDAYTIME() %>','<%=bean.getSid() %>','900','630')" nowrap><span class="label label-success radius">??????</span></td>
				<%}else if(!bean.getDAYTYPE().equals("??????")&&bean.getTWOCHECKSTATE().equals("?????????")){ %>
				<td nowrap><span class="label label-success radius">??????</span></td>
				<%}else if(!bean.getDAYTYPE().equals("??????")&&!bean.getTWOCHECKSTATE().equals("?????????")){ %>
				<td onclick="member_show('QueryBySingel','<%=path%>/tatar_/tatar.do?method=InOutQuerySingel&usersid=<%=bean.getUSERSID() %>&daytime=<%=bean.getDAYTIME() %>','<%=bean.getSid() %>','900','630')" nowrap><span class="label label-success radius">??????</span></td>
				<%}else{ %>
				<td  onclick="member_show('QueryBySingel','<%=path%>/tatar_/tatar.do?method=InOutQuerySingel&usersid=<%=bean.getUSERSID() %>&daytime=<%=bean.getDAYTIME() %>','<%=bean.getSid() %>','900','630')" nowrap><span class="label label-alarm radius">??????</span></td>
				<%} %>
				

				<%if(bean.getDAYTYPE().equals("??????")&&bean.getTHREECHECKSTATE().equals("?????????")){ %>
				<td nowrap><span class="label label-danger radius">??????</span></td>
				<%}else if(bean.getDAYTYPE().equals("??????")&&Integer.valueOf(bean.getTHREECHECKTIME().substring(12, 13))>=9){ %>
				<td  onclick="member_show('QueryBySingel','<%=path%>/tatar_/tatar.do?method=InOutQuerySingel&usersid=<%=bean.getUSERSID() %>&daytime=<%=bean.getDAYTIME() %>','<%=bean.getSid() %>','900','630')" nowrap><span class="label label-alarm radius">??????</span></td>
				<%}else if(bean.getDAYTYPE().equals("??????")&&bean.getTHREECHECKLATIID().substring(0, 5).equals((bean.getLATID()==null?"11111111":bean.getLATID()).substring(0, 5))&&bean.getTHREECHECKLONGID().substring(0, 6).equals((bean.getLNGID()==null?"11111111":bean.getLNGID()).substring(0, 6))){ %>	
				<td  onclick="member_show('QueryBySingel','<%=path%>/tatar_/tatar.do?method=InOutQuerySingel&usersid=<%=bean.getUSERSID() %>&daytime=<%=bean.getDAYTIME() %>','<%=bean.getSid() %>','900','630')" nowrap><span class="label label-success radius">??????</span></td>
				<%}else if(!bean.getDAYTYPE().equals("??????")&&bean.getTHREECHECKSTATE().equals("?????????")){ %>
				<td nowrap><span class="label label-success radius">??????</span></td>
				<%}else if(!bean.getDAYTYPE().equals("??????")&&!bean.getTHREECHECKSTATE().equals("?????????")){ %>
				<td onclick="member_show('QueryBySingel','<%=path%>/tatar_/tatar.do?method=InOutQuerySingel&usersid=<%=bean.getUSERSID() %>&daytime=<%=bean.getDAYTIME() %>','<%=bean.getSid() %>','900','630')" nowrap><span class="label label-success radius">??????</span></td>
				<%}else{ %>
				<td  onclick="member_show('QueryBySingel','<%=path%>/tatar_/tatar.do?method=InOutQuerySingel&usersid=<%=bean.getUSERSID() %>&daytime=<%=bean.getDAYTIME() %>','<%=bean.getSid() %>','900','630')" nowrap><span class="label label-alarm radius">??????</span></td>
				<%} %>
				
				<%if(bean.getDAYTYPE().equals("??????")&&bean.getFOURCHECKSTATE().equals("?????????")){ %>
				<td nowrap><span class="label label-danger radius">??????</span></td>
				<%}else if(bean.getDAYTYPE().equals("??????")&&Integer.valueOf(bean.getFOURCHECKTIME().substring(12, 13))>=9){ %>
				<td onclick="member_show('QueryBySingel','<%=path%>/tatar_/tatar.do?method=InOutQuerySingel&usersid=<%=bean.getUSERSID() %>&daytime=<%=bean.getDAYTIME() %>','<%=bean.getSid() %>','900','630')" nowrap><span class="label label-alarm radius">??????</span></td>
				<%}else if(bean.getDAYTYPE().equals("??????")&&bean.getFOURCHECKLATIID().substring(0, 5).equals((bean.getLATID()==null?"11111111":bean.getLATID()).substring(0, 5))&&bean.getFOURCHECKLONGID().substring(0, 6).equals((bean.getLNGID()==null?"11111111":bean.getLNGID()).substring(0, 6))){ %>	
				<td onclick="member_show('QueryBySingel','<%=path%>/tatar_/tatar.do?method=InOutQuerySingel&usersid=<%=bean.getUSERSID() %>&daytime=<%=bean.getDAYTIME() %>','<%=bean.getSid() %>','900','630')" nowrap><span class="label label-success radius">??????</span></td>
				<%}else if(!bean.getDAYTYPE().equals("??????")&&bean.getFOURCHECKSTATE().equals("?????????")){ %>
				<td  nowrap><span class="label label-success radius">??????</span></td>
				<%}else if(!bean.getDAYTYPE().equals("??????")&&!bean.getFOURCHECKSTATE().equals("?????????")){ %>
				<td onclick="member_show('QueryBySingel','<%=path%>/tatar_/tatar.do?method=InOutQuerySingel&usersid=<%=bean.getUSERSID() %>&daytime=<%=bean.getDAYTIME() %>','<%=bean.getSid() %>','900','630')" nowrap><span class="label label-success radius">??????</span></td>
				<%}else{ %>
				<td onclick="member_show('QueryBySingel','<%=path%>/tatar_/tatar.do?method=InOutQuerySingel&usersid=<%=bean.getUSERSID() %>&daytime=<%=bean.getDAYTIME() %>','<%=bean.getSid() %>','900','630')" nowrap><span class="label label-alarm radius">??????</span></td>
				<%} %>	         
          </td>
		</tr>
	<%}} %>
	
        </tbody>
      </table>
    </div>
 </div>
</div>
<!--????????????-->
</form>
</body>
</html>
<script type="text/javascript">
 /*??????-??????*/
function member_show(title,url,id,w,h){
	layer_show(title,url+'#?='+id,w,h);
}
/*??????-??????*/
function member_del(obj,id){
	layer.confirm('?????????????????????',function(index){
		$(obj).parents("tr").remove();
		layer.msg('?????????!',{icon:1,time:1000});
	});
}
 laydate({
    elem: '#start',
    event: 'focus' 
});
/*checkbox????????????*/
$('#checkbox').on('click',function(){
	if($('input[name="checkbox"]').prop("checked")){
		 $('.Reply_style').css('display','block');
		}
	else{
		
		 $('.Reply_style').css('display','none');
		}	
	})
/*????????????*/
function Guestbook_iew(id){
	var index = layer.open({
        type: 1,
        title: '????????????',
		maxmin: true, 
		shadeClose:false,
        area : ['600px' , ''],
        content:$('#Guestbook'),
		btn:['??????','??????'],
		yes: function(index, layero){		 
		  if($('input[name="checkbox"]').prop("checked")){			 
			 if($('.form-control').val()==""){
				layer.alert('???????????????????????????',{
               title: '?????????',				
			  icon:0,		
			  }) 
			 }else{			
			      layer.alert('????????????????????????',{
				   title: '?????????',				
				   icon:0,	
				   btn:['??????','??????'],	
				   yes: function(index){					   
					     layer.closeAll();
					   }
				  }); 		  
		   }			
	      }else{			
			 layer.alert('????????????????????????',{
               title: '?????????',				
			icon:0,		
			  }); 
			  layer.close(index);      		  
		  }
	   }
	})	
};
	/*????????????*/
function checkLength(which) {
	var maxChars = 200; //
	if(which.value.length > maxChars){
	   layer.open({
	   icon:2,
	   title:'?????????',
	   content:'??????????????????????????????!',	
    });
		// ?????????????????????????????? ??????????????????????????????????????? ??????
		which.value = which.value.substring(0,maxChars);
		return false;
	}else{
		var curr = maxChars - which.value.length; //250 ?????? ???????????????
		document.getElementById("sy").innerHTML = curr.toString();
		return true;
	}
};
</script>
<script type="text/javascript">
jQuery(function($) {
		var oTable1 = $('#sample-table').dataTable( {
		"aaSorting": [[ 1, "desc" ]],//?????????????????????
		"bStateSave": true,//????????????
		"aoColumnDefs": [
		  //{"bVisible": false, "aTargets": [ 3 ]} //????????????????????????
		 // {"orderable":false,"aTargets":[0,2,3,5,6]}// ????????????????????????
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
$("#btnSubmit").click(function(){
	if($("#start").val()==""){
		alert("????????????");
		return false;
	}
   $("#form-member-query").submit();
});
function member_show(title,url,id,w,h){
	layer_show(title,url,w,h);
}
</script>
