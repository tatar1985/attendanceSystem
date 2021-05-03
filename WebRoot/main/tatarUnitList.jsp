<!DOCTYPE HTML>
<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.tataren.tatar.domain.TUnit"%>
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
        <a href="javascript:ovid()" id="member_add" class="btn btn-warning"><i class="icon-plus"></i>添加单位</a>
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
				<th >电话</th>				
				<th >联系人</th>
				<th >联系方式</th>
				<th >分配积分</th>               
				<th >备注信息</th>
				<th >位置信息</th>
				<th >状态</th>             
				<th >操作</th>
			</tr>
		</thead>
	<tbody>
		<%
 ArrayList<TUnit> list =( ArrayList<TUnit>) request.getAttribute("list");
 if(list!=null){
for(int m=0 ;m < list.size();m++){	
	TUnit bean = list.get(m);	

%>
	<tr class="text-c">
	<td><%=bean.getSid() %></td>
	<td><%=bean.getRegionName() %></td>
	<td><%=bean.getUnitName() %></td>
	<td><%=bean.getTel()==null?"":bean.getTel() %></td>
	<td><%=bean.getLinkMan()==null?"":bean.getLinkMan() %></td>
	<td><%=bean.getLinkPhone()==null?"":bean.getLinkPhone() %></td>
	<td><%=bean.getScoreOriginal()==null?"":bean.getScoreOriginal() %></td>
	<td><%=bean.getRemark()==null?"":bean.getRemark() %></td>
	<td><%=bean.getLngId()==null?"":bean.getLngId() %></td>
	<%if(bean.getState().equals("生效")){ %>
	<td><span class="label label-success radius"><%=bean.getState() %></span></td>
	<%}else { %>
	<td><span class="label label-defaunt radius"><%=bean.getState() %></span></td>
	<%} %>
	<td class="td-manage">
		  <!--
          <a onClick="member_stop(this,'10001')"  href="javascript:;" title="停用"  class="btn btn-xs btn-success"><i class="icon-ok bigger-120"></i></a> 
            -->
          <a title="编辑" onclick="member_edit('<%=bean.getSid() %>','<%=bean.getUnitName() %>','<%=bean.getOrganizationCode() %>','<%=bean.getUnitProperties() %>','<%=bean.getTel() %>','<%=bean.getLinkMan() %>','<%=bean.getLinkPhone() %>','<%=bean.getScoreOriginal() %>','<%=bean.getRemark() %>','<%=bean.getState() %>')"  class="btn btn-xs btn-info" ><i class="icon-edit bigger-120"></i></a>         
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
    <input type="hidden" name="method" value="unitCreate" id="method"/>  
    <input type="hidden" name="sid" value="" id="sid"/>  
    <ul class=" page-content">
     <li><label class="label_name">单位名称：</label><span class="add_name"><input value="" name="unitName" type="text"  class="text_add"/></span><div class="prompt r_f"></div></li>
     <li><label class="label_name">组织机构：</label><span class="add_name"><input value="" name="organizationCode" type="text"  class="text_add"/></span><div class="prompt r_f"></div></li>
     <li><label class="label_name">签到类别：</label><span class="add_name">
     <label><input name="unitProperties" type="radio" checked="checked" value="两签" class="ace"><span class="lbl">两签</span></label>&nbsp;&nbsp;&nbsp;
     <label><input name="unitProperties" type="radio" value="四签" class="ace"><span class="lbl">四签</span></label>&nbsp;&nbsp;&nbsp;
     <label><input name="unitProperties" type="radio" value="六签" class="ace"><span class="lbl">六签</span></label>
     </span>
     <div class="prompt r_f"></div>
     </li>
     <li><label class="label_name">固定电话：</label><span class="add_name"><input name="tel" type="text"  class="text_add"/></span><div class="prompt r_f"></div></li>
     <li><label class="label_name">联系人员：</label><span class="add_name"><input name="linkMan" type="text"  class="text_add"/></span><div class="prompt r_f"></div></li>
     <li><label class="label_name">联系方式：</label><span class="add_name"><input name="linkPhone" type="text"  class="text_add"/></span><div class="prompt r_f"></div></li>
     <li><label class="label_name">分配积分：</label><span class="add_name"><input name="scoreOriginal" type="text"  class="text_add" onkeyup="this.value=this.value.replace(/[^\d]/ig,'')"/></span><div class="prompt r_f"></div></li>
     <li><label class="label_name">备注信息：</label><span class="add_name"><input name="remark" type="text"  class="text_add"/></span><div class="prompt r_f"></div></li>
     <li><label class="label_name">状&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;态：</label><span class="add_name">
     <label><input name="state" type="radio" checked="checked" value="生效" class="ace"><span class="lbl">生效</span></label>&nbsp;&nbsp;&nbsp;
     <label><input name="state"type="radio" value="失效" class="ace"><span class="lbl">失效</span></label></span><div class="prompt r_f"></div></li>
    </ul>
    </form>
 </div>
 
 <!--修改用户图层-->
<div class="edit_menber" id="edit_menber_style" style="display:none">
 <form class="form form-horizontal" action="<%=request.getContextPath()%>/tatar_/tatar.do" method="post"  id="tatarForm2" name="tatarForm2">
    <input type="hidden" name="method" value="unitEdit"/>  
    <input type="hidden" name="sid" value="" id="sid2"/>  
    <ul class=" page-content">
     <li><label class="label_name">单位名称：</label><span class="edit_name"><input value="" name="unitName" id="unitName" type="text"  class="text_add"/></span><div class="prompt r_f"></div></li>
     <li><label class="label_name">组织机构：</label><span class="edit_name"><input value="" name="organizationCode" id="organizationCode" type="text"  class="text_add"/></span><div class="prompt r_f"></div></li>
     <li><label class="label_name">签到类别：</label><span class="edit_name">
     <label><input name="unitProperties"  value="两签" type="radio" class="ace"><span class="lbl">两签</span></label>&nbsp;&nbsp;&nbsp;
     <label><input name="unitProperties"  value="四签" type="radio" class="ace"><span class="lbl">四签</span></label>&nbsp;&nbsp;&nbsp;
     <label><input name="unitProperties"  value="六签" type="radio" class="ace"><span class="lbl">六签</span></label>
     </span>
     <div class="prompt r_f"></div>
     </li>
     <li><label class="label_name">固定电话：</label><span class="edit_name"><input name="tel" id="tel" type="text"  class="text_add"/></span><div class="prompt r_f"></div></li>
     <li><label class="label_name">联系人员：</label><span class="edit_name"><input name="linkMan" id="linkMan" type="text"  class="text_add"/></span><div class="prompt r_f"></div></li>
     <li><label class="label_name">联系方式：</label><span class="edit_name"><input name="linkPhone" id="linkPhone" type="text"  class="text_add"/></span><div class="prompt r_f"></div></li>
     <li><label class="label_name">分配积分：</label><span class="edit_name"><input name="scoreOriginal" id="scoreOriginal" type="text"  class="text_add"/></span><div class="prompt r_f"></div></li>
     <li><label class="label_name">备注信息：</label><span class="edit_name"><input name="remark" id="remark" type="text"  class="text_add"/></span><div class="prompt r_f"></div></li>
     <li><label class="label_name">状&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;态：</label><span class="add_name">
     <label><input name="state"  type="radio" value="生效" class="ace"><span class="lbl">生效</span></label>&nbsp;&nbsp;&nbsp;
     <label><input name="state"  type="radio" value="失效" class="ace"><span class="lbl">失效</span></label></span><div class="prompt r_f"></div></li>
    </ul>
    </form>
    
 </div>
 
  <!--修改用户图层-->
<div class="map_menber" id="map_menber_style" style="display:none">
 <form class="form form-horizontal" action="<%=request.getContextPath()%>/tatar_/tatar.do" method="post"  id="tatarForm3" name="tatarForm3">
  <input type="hidden" name="sid3" value="" id="sid3"/>  
  <input type="hidden" name="method" value="unitMap"/>  
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
        area : ['800px' , ''],
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
function member_edit(sid,unitName,organizationCode,unitProperties,tel,linkMan,linkPhone,scoreOriginal,remark,state){
	  
	  $("#sid2").val(sid);
	  $("#unitName").val(unitName);
	  $("#organizationCode").val(organizationCode);
	  $("#unitProperties1").val(unitProperties);
	  $("input[name='unitProperties'][value="+unitProperties+"]").attr("checked",true); 
	  
	  $("#tel").val(tel);
	  $("#linkMan").val(linkMan);
	  $("#linkPhone").val(linkPhone);
	  $("#scoreOriginal").val(scoreOriginal);  
	  $("#remark").val(remark); 
	  $("input[name='state'][value="+state+"]").attr("checked",true);
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
	     $("#method").val("unitDelete");
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
