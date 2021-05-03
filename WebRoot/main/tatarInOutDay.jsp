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


<title>留言</title>
</head>

<body>
 <form class="form form-horizontal" action="<%=request.getContextPath()%>/tatar_/tatar.do" method="post"  id="form-member-query" name="form-member-login">
 <input type="hidden" name="method" value="InOutQuery"/>
<div class="margin clearfix">
 <div class="Guestbook_style">
 <div class="search_style">
      <div class="title_names">搜索查询</div>
      <ul class="search_content clearfix">
       <li><label class="l_f">时间</label><input class="inline laydate-icon" id="start" style=" margin-left:10px;" name="start"></li>
       <li style="width:90px;"><button type="button" id="btnSubmit" class="btn_search"><i class="icon-search"></i>查询</button></li>
      </ul>
    </div>
   
    <!--留言列表-->
    <div class="Guestbook_list">
      <table class="table table-striped table-bordered table-hover" id="sample-table">
      <thead>
		 <tr>
          <th >姓名</th>
          <th >单位</th>
          <th >当天</th>
          <th >时间</th>
          <th >签到</th>                
          <th >签退</th>
          <th >签到</th>                
          <th >签退</th>
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
				
				<%if(bean.getDAYTYPE().equals("上班")&&bean.getFIRSTCHECKSTATE().equals("未签到")){ %>
				<td nowrap><span class="label label-danger radius">无签</span></td>
				<%}else if(bean.getDAYTYPE().equals("上班")&&Integer.valueOf(bean.getFIRSTCHECKTIME().substring(12, 13))>=9){ %>
				<td onclick="member_show('QueryBySingel','<%=path%>/tatar_/tatar.do?method=InOutQuerySingel&usersid=<%=bean.getUSERSID() %>&daytime=<%=bean.getDAYTIME() %>','<%=bean.getSid() %>','900','630')" nowrap><span class="label label-alarm radius">迟到</span></td>
				<%}else if(bean.getDAYTYPE().equals("上班")&&bean.getFIRSTCHECKLATIID().substring(0, 5).equals((bean.getLATID()==null?"11111111":bean.getLATID()).substring(0, 5))&&bean.getFIRSTCHECKLONGID().substring(0, 6).equals((bean.getLNGID()==null?"11111111":bean.getLNGID()).substring(0, 6))){ %>				
				<td onclick="member_show('QueryBySingel','<%=path%>/tatar_/tatar.do?method=InOutQuerySingel&usersid=<%=bean.getUSERSID() %>&daytime=<%=bean.getDAYTIME() %>','<%=bean.getSid() %>','900','630')" nowrap><span class="label label-success radius">正常</span></td>
				<%}else if(!bean.getDAYTYPE().equals("上班")&&bean.getFIRSTCHECKSTATE().equals("未签到")){ %>
				<td nowrap><span class="label label-success radius">无签</span></td>
				<%}else if(!bean.getDAYTYPE().equals("上班")&&!bean.getFIRSTCHECKSTATE().equals("未签到")){ %>
				<td onclick="member_show('QueryBySingel','<%=path%>/tatar_/tatar.do?method=InOutQuerySingel&usersid=<%=bean.getUSERSID() %>&daytime=<%=bean.getDAYTIME() %>','<%=bean.getSid() %>','900','630')" nowrap><span class="label label-success radius">已签</span></td>
				<%}else{ %>
				<td onclick="member_show('QueryBySingel','<%=path%>/tatar_/tatar.do?method=InOutQuerySingel&usersid=<%=bean.getUSERSID() %>&daytime=<%=bean.getDAYTIME() %>','<%=bean.getSid() %>','900','630')" nowrap><span class="label label-alarm radius">偏离</span></td>
				<%} %>
				
				<%if(bean.getDAYTYPE().equals("上班")&&bean.getTWOCHECKSTATE().equals("未签到")){ %>
				<td nowrap><span class="label label-danger radius">无签</span></td>
				<%}else if(bean.getDAYTYPE().equals("上班")&&Integer.valueOf(bean.getTWOCHECKTIME().substring(12, 13))>=9){ %>
				<td  onclick="member_show('QueryBySingel','<%=path%>/tatar_/tatar.do?method=InOutQuerySingel&usersid=<%=bean.getUSERSID() %>&daytime=<%=bean.getDAYTIME() %>','<%=bean.getSid() %>','900','630')" nowrap><span class="label label-alarm radius">迟到</span></td>
				<%}else if(bean.getDAYTYPE().equals("上班")&&bean.getTWOCHECKLATIID().substring(0, 5).equals((bean.getLATID()==null?"11111111":bean.getLATID()).substring(0, 5))&&bean.getTWOCHECKLONGID().substring(0, 6).equals((bean.getLNGID()==null?"11111111":bean.getLNGID()).substring(0, 6))){ %>	
				<td  onclick="member_show('QueryBySingel','<%=path%>/tatar_/tatar.do?method=InOutQuerySingel&usersid=<%=bean.getUSERSID() %>&daytime=<%=bean.getDAYTIME() %>','<%=bean.getSid() %>','900','630')" nowrap><span class="label label-success radius">正常</span></td>
				<%}else if(!bean.getDAYTYPE().equals("上班")&&bean.getTWOCHECKSTATE().equals("未签到")){ %>
				<td nowrap><span class="label label-success radius">无签</span></td>
				<%}else if(!bean.getDAYTYPE().equals("上班")&&!bean.getTWOCHECKSTATE().equals("未签到")){ %>
				<td onclick="member_show('QueryBySingel','<%=path%>/tatar_/tatar.do?method=InOutQuerySingel&usersid=<%=bean.getUSERSID() %>&daytime=<%=bean.getDAYTIME() %>','<%=bean.getSid() %>','900','630')" nowrap><span class="label label-success radius">已签</span></td>
				<%}else{ %>
				<td  onclick="member_show('QueryBySingel','<%=path%>/tatar_/tatar.do?method=InOutQuerySingel&usersid=<%=bean.getUSERSID() %>&daytime=<%=bean.getDAYTIME() %>','<%=bean.getSid() %>','900','630')" nowrap><span class="label label-alarm radius">偏离</span></td>
				<%} %>
				

				<%if(bean.getDAYTYPE().equals("上班")&&bean.getTHREECHECKSTATE().equals("未签到")){ %>
				<td nowrap><span class="label label-danger radius">无签</span></td>
				<%}else if(bean.getDAYTYPE().equals("上班")&&Integer.valueOf(bean.getTHREECHECKTIME().substring(12, 13))>=9){ %>
				<td  onclick="member_show('QueryBySingel','<%=path%>/tatar_/tatar.do?method=InOutQuerySingel&usersid=<%=bean.getUSERSID() %>&daytime=<%=bean.getDAYTIME() %>','<%=bean.getSid() %>','900','630')" nowrap><span class="label label-alarm radius">迟到</span></td>
				<%}else if(bean.getDAYTYPE().equals("上班")&&bean.getTHREECHECKLATIID().substring(0, 5).equals((bean.getLATID()==null?"11111111":bean.getLATID()).substring(0, 5))&&bean.getTHREECHECKLONGID().substring(0, 6).equals((bean.getLNGID()==null?"11111111":bean.getLNGID()).substring(0, 6))){ %>	
				<td  onclick="member_show('QueryBySingel','<%=path%>/tatar_/tatar.do?method=InOutQuerySingel&usersid=<%=bean.getUSERSID() %>&daytime=<%=bean.getDAYTIME() %>','<%=bean.getSid() %>','900','630')" nowrap><span class="label label-success radius">正常</span></td>
				<%}else if(!bean.getDAYTYPE().equals("上班")&&bean.getTHREECHECKSTATE().equals("未签到")){ %>
				<td nowrap><span class="label label-success radius">无签</span></td>
				<%}else if(!bean.getDAYTYPE().equals("上班")&&!bean.getTHREECHECKSTATE().equals("未签到")){ %>
				<td onclick="member_show('QueryBySingel','<%=path%>/tatar_/tatar.do?method=InOutQuerySingel&usersid=<%=bean.getUSERSID() %>&daytime=<%=bean.getDAYTIME() %>','<%=bean.getSid() %>','900','630')" nowrap><span class="label label-success radius">已签</span></td>
				<%}else{ %>
				<td  onclick="member_show('QueryBySingel','<%=path%>/tatar_/tatar.do?method=InOutQuerySingel&usersid=<%=bean.getUSERSID() %>&daytime=<%=bean.getDAYTIME() %>','<%=bean.getSid() %>','900','630')" nowrap><span class="label label-alarm radius">偏离</span></td>
				<%} %>
				
				<%if(bean.getDAYTYPE().equals("上班")&&bean.getFOURCHECKSTATE().equals("未签到")){ %>
				<td nowrap><span class="label label-danger radius">无签</span></td>
				<%}else if(bean.getDAYTYPE().equals("上班")&&Integer.valueOf(bean.getFOURCHECKTIME().substring(12, 13))>=9){ %>
				<td onclick="member_show('QueryBySingel','<%=path%>/tatar_/tatar.do?method=InOutQuerySingel&usersid=<%=bean.getUSERSID() %>&daytime=<%=bean.getDAYTIME() %>','<%=bean.getSid() %>','900','630')" nowrap><span class="label label-alarm radius">迟到</span></td>
				<%}else if(bean.getDAYTYPE().equals("上班")&&bean.getFOURCHECKLATIID().substring(0, 5).equals((bean.getLATID()==null?"11111111":bean.getLATID()).substring(0, 5))&&bean.getFOURCHECKLONGID().substring(0, 6).equals((bean.getLNGID()==null?"11111111":bean.getLNGID()).substring(0, 6))){ %>	
				<td onclick="member_show('QueryBySingel','<%=path%>/tatar_/tatar.do?method=InOutQuerySingel&usersid=<%=bean.getUSERSID() %>&daytime=<%=bean.getDAYTIME() %>','<%=bean.getSid() %>','900','630')" nowrap><span class="label label-success radius">正常</span></td>
				<%}else if(!bean.getDAYTYPE().equals("上班")&&bean.getFOURCHECKSTATE().equals("未签到")){ %>
				<td  nowrap><span class="label label-success radius">无签</span></td>
				<%}else if(!bean.getDAYTYPE().equals("上班")&&!bean.getFOURCHECKSTATE().equals("未签到")){ %>
				<td onclick="member_show('QueryBySingel','<%=path%>/tatar_/tatar.do?method=InOutQuerySingel&usersid=<%=bean.getUSERSID() %>&daytime=<%=bean.getDAYTIME() %>','<%=bean.getSid() %>','900','630')" nowrap><span class="label label-success radius">已签</span></td>
				<%}else{ %>
				<td onclick="member_show('QueryBySingel','<%=path%>/tatar_/tatar.do?method=InOutQuerySingel&usersid=<%=bean.getUSERSID() %>&daytime=<%=bean.getDAYTIME() %>','<%=bean.getSid() %>','900','630')" nowrap><span class="label label-alarm radius">偏离</span></td>
				<%} %>	         
          </td>
		</tr>
	<%}} %>
	
        </tbody>
      </table>
    </div>
 </div>
</div>
<!--留言详细-->
</form>
</body>
</html>
<script type="text/javascript">
 /*用户-查看*/
function member_show(title,url,id,w,h){
	layer_show(title,url+'#?='+id,w,h);
}
/*留言-删除*/
function member_del(obj,id){
	layer.confirm('确认要删除吗？',function(index){
		$(obj).parents("tr").remove();
		layer.msg('已删除!',{icon:1,time:1000});
	});
}
 laydate({
    elem: '#start',
    event: 'focus' 
});
/*checkbox激发事件*/
$('#checkbox').on('click',function(){
	if($('input[name="checkbox"]').prop("checked")){
		 $('.Reply_style').css('display','block');
		}
	else{
		
		 $('.Reply_style').css('display','none');
		}	
	})
/*留言查看*/
function Guestbook_iew(id){
	var index = layer.open({
        type: 1,
        title: '留言信息',
		maxmin: true, 
		shadeClose:false,
        area : ['600px' , ''],
        content:$('#Guestbook'),
		btn:['确定','取消'],
		yes: function(index, layero){		 
		  if($('input[name="checkbox"]').prop("checked")){			 
			 if($('.form-control').val()==""){
				layer.alert('回复内容不能为空！',{
               title: '提示框',				
			  icon:0,		
			  }) 
			 }else{			
			      layer.alert('确定回复该内容？',{
				   title: '提示框',				
				   icon:0,	
				   btn:['确定','取消'],	
				   yes: function(index){					   
					     layer.closeAll();
					   }
				  }); 		  
		   }			
	      }else{			
			 layer.alert('是否要取消回复！',{
               title: '提示框',				
			icon:0,		
			  }); 
			  layer.close(index);      		  
		  }
	   }
	})	
};
	/*字数限制*/
function checkLength(which) {
	var maxChars = 200; //
	if(which.value.length > maxChars){
	   layer.open({
	   icon:2,
	   title:'提示框',
	   content:'您输入的字数超过限制!',	
    });
		// 超过限制的字数了就将 文本框中的内容按规定的字数 截取
		which.value = which.value.substring(0,maxChars);
		return false;
	}else{
		var curr = maxChars - which.value.length; //250 减去 当前输入的
		document.getElementById("sy").innerHTML = curr.toString();
		return true;
	}
};
</script>
<script type="text/javascript">
jQuery(function($) {
		var oTable1 = $('#sample-table').dataTable( {
		"aaSorting": [[ 1, "desc" ]],//默认第几个排序
		"bStateSave": true,//状态保存
		"aoColumnDefs": [
		  //{"bVisible": false, "aTargets": [ 3 ]} //控制列的隐藏显示
		 // {"orderable":false,"aTargets":[0,2,3,5,6]}// 制定列不参与排序
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
		alert("不能为空");
		return false;
	}
   $("#form-member-query").submit();
});
function member_show(title,url,id,w,h){
	layer_show(title,url,w,h);
}
</script>
