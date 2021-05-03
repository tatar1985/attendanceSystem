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
        <!--[if lte IE 8]>
		  <link rel="stylesheet" href="assets/css/ace-ie.min.css" />
		<![endif]-->
		 <link rel="stylesheet" href="http://cache.amap.com/lbs/static/main1119.css"/>
          <script type="text/javascript"
            src="http://webapi.amap.com/maps?v=1.3&key=f1da219022183a93f81e9307a423fec2&plugin=AMap.Autocomplete"></script>
          <script type="text/javascript" src="http://cache.amap.com/lbs/static/addToolbar.js"></script>
		

		
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
       <span class="l_f">
        <a href="javascript:ovid()" id="member_add" class="btn btn-warning"><i class="icon-plus"></i>添加活动</a>
       </span>
    
     </div>
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
          <a title="编辑" onclick="member_edit('<%=bean.getSid() %>','<%=bean.getAreaName() %>','<%=bean.getStartTime() %>','<%=bean.getEndTime() %>')"  class="btn btn-xs btn-info" ><i class="icon-edit bigger-120"></i></a>         
          <a title="位置" onclick="member_mapedit('<%=bean.getSid() %>','<%=bean.getLngId() %>','<%=bean.getLatId() %>')" class="btn btn-xs btn-pink ads_link" ><i class="icon-map-marker  bigger-120"></i></a>
          <a title="删除" href="javascript:;"  onclick="member_del(this,'<%=bean.getSid() %>')" class="btn btn-xs btn-warning" ><i class="icon-trash  bigger-120"></i></a>
    </td>
	</tr>
	<%}} %>
	</tbody>    
    </table>     
    </div>
     
 </div>
</div>
<!--添加用户图层-->
<div class="add_menber" id="add_menber_style" style="display:none">
 <form class="form form-horizontal" action="<%=request.getContextPath()%>/tatar_/tatar.do" method="post"  id="tatarForm" name="tatarForm">
    <input type="hidden" name="method" value="areaCreate" id="method"/>  
    <input type="hidden" name="sid" value="" id="sid"/>  
    <ul class=" page-content">
  
     <li><label class="label_name">活动名称：</label><span class="add_name"><input value="" name="areaName" type="text"  class="text_add"/></span><div class="prompt r_f"></div></li>
     <li><label class="label_name"></label><span class="add_name"></span><div class="prompt r_f"></div></li>
     <li><label class="label_name">开始时间：</label><span class="add_name"><input type="text" name="startTime" onclick="laydate({istime: true, format: 'YYYY-MM-DD hh:mm:ss'})"/></span><div class="prompt r_f"></div></li>

     <li><label class="label_name">结束时间：</label><span class="add_name"><input type="text"  name="endTime" onclick="laydate({istime: true, format: 'YYYY-MM-DD hh:mm:ss'})"/></span><div class="prompt r_f"></div></li>
    </ul>
 

    </form>
 </div>
 
 <!--修改用户图层-->
<div class="edit_menber" id="edit_menber_style" style="display:none">
 <form class="form form-horizontal" action="<%=request.getContextPath()%>/tatar_/tatar.do" method="post"  id="tatarForm2" name="tatarForm2">
    <input type="hidden" name="method" value="areaEdit"/>  
    <input type="hidden" name="sid2" value="" id="sid2"/>  
    <ul class=" page-content">
    
     <li><label class="label_name">活动名称：</label><span class="add_name"><input value="" name="areaName" id="areaName" type="text"  class="text_add"/></span><div class="prompt r_f"></div></li>
     <li><label class="label_name"></label><span class="add_name"></span><div class="prompt r_f"></div></li>
     <li><label class="label_name">开始时间：</label><span class="add_name"><input type="text" name="startTime" id="startTime" onclick="laydate({istime: true, format: 'YYYY-MM-DD hh:mm:ss'})"/></span><div class="prompt r_f"></div></li>
    
     <li><label class="label_name">结束时间：</label><span class="add_name"><input type="text"  name="endTime" id="endTime" onclick="laydate({istime: true, format: 'YYYY-MM-DD hh:mm:ss'})"/></span><div class="prompt r_f"></div></li>
    </ul>
    </form>
    
 </div>
 
  <!--修改用户图层-->
<div class="map_menber" id="map_menber_style" style="display:none">
 <form class="form form-horizontal" action="<%=request.getContextPath()%>/tatar_/tatar.do" method="post"  id="tatarForm3" name="tatarForm3">
  <input type="hidden" name="sid3" value="" id="sid3"/>  
  <input type="hidden" name="method" value="areaMap"/>  
 <div id="container" ></div>
<div id="myPageTop" >
    <table>
        <tr>
            <td>
                <label>按关键字搜索：</label>
            </td>
            <td class="column2">
                <label>左击获取经纬度：</label>
            </td>
        </tr>
        <tr>
            <td>
                <input type="text" placeholder="请输入关键字进行搜索" id="tipinput">
            </td>
            <td class="column2">
                <input type="text" readonly="true" id="lnglat" name="lnglat">
            </td>
        </tr>
    </table>
  </div>
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
			
			
			
/*用户-添加*/
 $('#member_add').on('click', function(){
    layer.open({
        type: 1,
        title: '添加模块',
		maxmin: true, 
		shadeClose: true, //点击遮罩关闭层
        area : ['600px' , '250px'],
        content:$('#add_menber_style'),
		btn:['提交','取消'],
		yes:function(index,layero){	
		 var num=0;
		 var str="";
     $(".add_menber input[type$='text']").each(function(n){
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
          $("#tatarForm").submit();
          
			  layer.alert('添加成功！',{
               title: '提示框',				
			icon:1,		
			  });
			   layer.close(index);	
		  }		  		     				
		}
    });
});
/*用户-删除*/
function member_edit(sid,areaName,startTime,endTime){
	  
	  $("#sid2").val(sid);
	  $("#areaName").val(areaName);
	  $("#startTime").val(startTime);
	  $("#endTime").val(endTime);
	 layer.open({
        type: 1,
        title: '修改模块',
		maxmin: true, 
		shadeClose: true, //点击遮罩关闭层
         area : ['600px' , '250px'],
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
/*用户-删除*/
function member_mapedit(sid,lngId,latId){
	
	  $("#sid3").val(sid);
	  $("#lnglat").val(lngId+","+latId);
	 layer.open({
        type: 1,
        title: 'Map',
		maxmin: true, 
		shadeClose: true, //点击遮罩关闭层
        area : ['800px' , '400px'],
        content:$('#map_menber_style'),
		btn:['提交','取消'],
		yes:function(index,layero){	
		 var num=0;
		 var str="";    
      	
      	if($("#lnglat").val()==""){
      		alert("经纬度不能为空");
      		return ;
      	}
          $("#tatarForm3").submit();
			 layer.msg('已修改!',{icon:1,time:1000});
		  }		  		     				
		
		 });
}
/*用户-删除*/
function member_del(obj,id){
	layer.confirm('确认要删除吗？',function(index){
	     $("#sid").val(id);
	     $("#method").val("areaDelete");
		 $("#tatarForm").submit();
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

var map = new AMap.Map("container", {
        resizeEnable: true
    });
    //为地图注册click事件获取鼠标点击出的经纬度坐标
    var clickEventListener = map.on('click', function(e) {
        document.getElementById("lnglat").value = e.lnglat.getLng() + ',' + e.lnglat.getLat();
    });
    var auto = new AMap.Autocomplete({
        input: "tipinput"
    });
    AMap.event.addListener(auto, "select", select);//注册监听，当选中某条记录时会触发
    function select(e) {
        if (e.poi && e.poi.location) {
            map.setZoom(10);
            map.setCenter(e.poi.location);
        }
    }

</script>