<!DOCTYPE HTML>
<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.tataren.tatar.domain.TInout"%>
<%@page import="com.tataren.main.service.SessionInfo"%>
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
		       <script type="text/javascript" src="<%=request.getContextPath()%>/main/js/H-ui.js"></script> 
        <script type="text/javascript" src="<%=request.getContextPath()%>/main/js/H-ui.admin.js"></script>  
		 <script src="<%=request.getContextPath()%>/main/assets/layer/layer.js" type="text/javascript" ></script>
        <script src="<%=request.getContextPath()%>/main/assets/laydate/laydate.js" type="text/javascript"></script>
		
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
				<%}else if(bean.getDAYTYPE().equals("??????")&&(Integer.valueOf(bean.getFIRSTCHECKTIME().substring(11, 13))>=9)){ %>
				<td nowrap><span class="label label-alarm radius" ><u style="cursor:pointer" onclick="change_state('????????????','/main/tatarInOutSingleMap.jsp?unitLoId=<%=bean.getLNGID() %>&unitLaId=<%=bean.getLATID() %>&beanLoId=<%=bean.getFIRSTCHECKLONGID() %>&beanLaId=<%=bean.getFIRSTCHECKLATIID() %>&checkTime=<%=bean.getFIRSTCHECKTIME() %>&userName=<%=bean.getUSERNAME() %>','10001','600','470')">??????</u></span></td>
				<%}else if(bean.getDAYTYPE().equals("??????")&&SessionInfo.Distance(Double.valueOf(bean.getFIRSTCHECKLONGID()), Double.valueOf(bean.getFIRSTCHECKLATIID()), Double.valueOf(bean.getLNGID()), Double.valueOf(bean.getLATID()))<2000){ %>				
				<td onclick="member_show('QueryBySingel','<%=path%>/tatar_/tatar.do?method=InOutQuerySingel&usersid=<%=bean.getUSERSID() %>&daytime=<%=bean.getDAYTIME() %>','<%=bean.getSid() %>','900','630')" nowrap><span class="label label-success radius" onclick="change_state('????????????','/main/tatarInOutSingleMap.jsp?unitLoId=<%=bean.getLNGID() %>&unitLaId=<%=bean.getLATID() %>&beanLoId=<%=bean.getFIRSTCHECKLONGID() %>&beanLaId=<%=bean.getFIRSTCHECKLATIID() %>&checkTime=<%=bean.getFIRSTCHECKTIME() %>&userName=<%=bean.getUSERNAME() %>','10001','600','470')">??????</span></td>
				<%}else if(!bean.getDAYTYPE().equals("??????")&&bean.getFIRSTCHECKSTATE().equals("?????????")){ %>
				<td nowrap><span class="label label-success radius">??????</span></td>
				<%}else if(!bean.getDAYTYPE().equals("??????")&&!bean.getFIRSTCHECKSTATE().equals("?????????")){ %>
				<td onclick="change_state('????????????','/main/tatarInOutSingleMap.jsp?unitLoId=<%=bean.getLNGID() %>&unitLaId=<%=bean.getLATID() %>&beanLoId=<%=bean.getFIRSTCHECKLONGID() %>&beanLaId=<%=bean.getFIRSTCHECKLATIID() %>&checkTime=<%=bean.getFIRSTCHECKTIME() %>&userName=<%=bean.getUSERNAME() %>','10001','600','470')" nowrap><span class="label label-success radius">??????</span></td>
				<%}else{ %>
				<td onclick="change_state('????????????','/main/tatarInOutSingleMap.jsp?unitLoId=<%=bean.getLNGID() %>&unitLaId=<%=bean.getLATID() %>&beanLoId=<%=bean.getFIRSTCHECKLONGID() %>&beanLaId=<%=bean.getFIRSTCHECKLATIID() %>&checkTime=<%=bean.getFIRSTCHECKTIME() %>&userName=<%=bean.getUSERNAME() %>','10001','600','470')" nowrap><span class="label label-alarm radius">??????</span></td>
				<%} %>
			
				
				<%if(bean.getDAYTYPE().equals("??????")&&bean.getFOURCHECKSTATE().equals("?????????")){ %>
				<td nowrap><span class="label label-danger radius">??????</span></td>
				<%}else if(bean.getDAYTYPE().equals("??????")&&(Integer.valueOf(bean.getFOURCHECKTIME().substring(11, 13))<17||(Integer.valueOf(bean.getFOURCHECKTIME().substring(11, 13))==17&&Integer.valueOf(bean.getFOURCHECKTIME().substring(14, 16))<30))){ %>
				<td onclick="change_state('????????????','/main/tatarInOutSingleMap.jsp?unitLoId=<%=bean.getLNGID() %>&unitLaId=<%=bean.getLATID() %>&beanLoId=<%=bean.getFOURCHECKLONGID() %>&beanLaId=<%=bean.getFOURCHECKLATIID() %>&checkTime=<%=bean.getFOURCHECKTIME() %>&userName=<%=bean.getUSERNAME() %>','10001','600','470')" nowrap><span class="label label-alarm radius">??????</span></td>
				<%}else if(bean.getDAYTYPE().equals("??????")&&SessionInfo.Distance(Double.valueOf(bean.getFOURCHECKLONGID()), Double.valueOf(bean.getFOURCHECKLATIID()), Double.valueOf(bean.getLNGID()), Double.valueOf(bean.getLATID()))<2000){ %>	
				<td onclick="change_state('????????????','/main/tatarInOutSingleMap.jsp?unitLoId=<%=bean.getLNGID() %>&unitLaId=<%=bean.getLATID() %>&beanLoId=<%=bean.getFOURCHECKLONGID() %>&beanLaId=<%=bean.getFOURCHECKLATIID() %>&checkTime=<%=bean.getFOURCHECKTIME() %>&userName=<%=bean.getUSERNAME() %>','10001','600','470')" nowrap><span class="label label-success radius">??????</span></td>
				<%}else if(!bean.getDAYTYPE().equals("??????")&&bean.getFOURCHECKSTATE().equals("?????????")){ %>
				<td  nowrap><span class="label label-success radius">??????</span></td>
				<%}else if(!bean.getDAYTYPE().equals("??????")&&!bean.getFOURCHECKSTATE().equals("?????????")){ %>
				<td onclick="change_state('????????????','/main/tatarInOutSingleMap.jsp?unitLoId=<%=bean.getLNGID() %>&unitLaId=<%=bean.getLATID() %>&beanLoId=<%=bean.getFOURCHECKLONGID() %>&beanLaId=<%=bean.getFOURCHECKLATIID() %>&checkTime=<%=bean.getFOURCHECKTIME() %>&userName=<%=bean.getUSERNAME() %>','10001','600','470')" nowrap><span class="label label-success radius">??????</span></td>
				<%}else{ %>
				<td onclick="change_state('????????????','/main/tatarInOutSingleMap.jsp?unitLoId=<%=bean.getLNGID() %>&unitLaId=<%=bean.getLATID() %>&beanLoId=<%=bean.getFOURCHECKLONGID() %>&beanLaId=<%=bean.getFOURCHECKLATIID() %>&checkTime=<%=bean.getFOURCHECKTIME() %>&userName=<%=bean.getUSERNAME() %>','10001','600','470')" nowrap><span class="label label-alarm radius">??????</span></td>
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
function change_state(title,url,id,w,h){

	layer_show(title,url,w,h);	
}
 laydate({
    elem: '#start',
    event: 'focus' 
});
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

</script>
