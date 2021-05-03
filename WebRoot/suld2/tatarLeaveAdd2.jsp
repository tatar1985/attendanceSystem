<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ page import="com.tataren.tatar.domain.TMember"%>
<%
TMember webUser=(TMember)request.getSession().getAttribute("webUser");
webUser=new TMember();
webUser.setSid(1);
webUser.setUsername("1");
webUser.setPhoto("1");
webUser.setUnitId("1");
webUser.setFinalMajor("1");

 %>
<html lang="zh-CN">
<head>
<link href="<%=request.getContextPath()%>/suld/css/css2.css" rel="stylesheet" type="text/css">
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
<meta name="format-detection" content="telephone=no" />
<script type="text/javascript" src="<%=request.getContextPath()%>/Scripts/jquery-1.7.2.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/Scripts/jquery-ui-1.8.22.custom.min.js"></script>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/Styles/ui-lightness/jquery-ui-1.8.22.custom.css" />
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/Styles/ui-lightness/jquery-ui-1.8.22.custom.css" />

<title>请假页面</title>
<style>
/*地址输入框*/
.column_box input{ line-height:46px; color:#191919; font-size:14px; float:left; margin-left:12px;}
.s_center option{
    text-align: right;
}
/*底部导航*/
.nav_box{ width:100%; height:51px; position:fixed; left:0; bottom:0; background-color:#fff;}
.nav_index,.nav_order,.nav_my{ width:33.3%; float:left;}
.nav_box img{ width:18px; height:auto; position:relative; left:50%; margin-left:-9px; margin-top:8px;}
.nav_box h1{ font-size:12px; text-align:center; color:#65646b; line-height:25px;}
.nav_box .current_nav{ color:#01aff0;}
</style>
</head>

<body style="background-color: #f9f9f9;">
 <form class="form form-horizontal" action="<%=request.getContextPath()%>/suld_/suld.do" method="post"  id="tatarForm" name="tatarForm">
    <input type="hidden" name="method" id="method" value="leaveAdd"/>
    <input type="hidden" name="headImgUrl" id="headImgUrl" value="<%=webUser.getPhoto() %>"/>
    <input type="hidden" name="sid" id="sid" value="<%=webUser.getSid() %>"/>
    <input type="hidden" name="opendId" id="opendId" value="<%=webUser.getFinalMajor() %>"/>
 
<!--header顶部标题-->
<div class="header">请假页面
	<div class="header_left" onclick="window.history.go(-1)"><img src="<%=request.getContextPath()%>/suld/images/return.png"></div>
</div>
<!--column搜索小区-->
<!--column联系人-->
<div class="column_box">
	<div class="column_img"><img src="<%=request.getContextPath()%>/suld/images/members.png"></div>
    <input placeholder="请假人" id="trueName" name="trueName" value="<%=webUser.getTrueName()==null?"":webUser.getTrueName() %>"></input>
    <div class="border_bottom"></div>
</div>
<!--column电话-->
<div class="column_box">
	<div class="column_img"><img src="<%=request.getContextPath()%>/suld/images/phone.png"></div>
    <input placeholder="联系电话" id="mobile" name="mobile" value="<%=webUser.getMobile()==null?"":webUser.getMobile() %>"></input>
    <div class="border_bottom"></div>
</div>
<!-- 
<div class="column_box">
	<div class="column_img"><img src="<%=request.getContextPath()%>/suld/images/adrress_2.png"></div>
     <input placeholder="单位名称" id="unitName" name="unitName" value="<%=webUser.getUnit()==null?"":webUser.getUnit() %>"/>
    <div class="border_bottom"></div>
</div>
 -->
<div class="column_box">
	<div class="column_img"><img src="<%=request.getContextPath()%>/suld/images/adrress_2.png"></div>
     <input placeholder="科室名称" id="department" name="department" value="<%=webUser.getDepartment() ==null?"":webUser.getDepartment() %>"></input>
    <div class="border_bottom"></div>
</div>


<!--  
<div class="column_box_tatar">
	<div class="column_img_tatar"><img src="<%=request.getContextPath()%>/suld/images/adrress_2.png"></div>
    <input placeholder="单位名称" id="unitName" name="unitName" value="<%=webUser.getUnit()==null?"":webUser.getUnit() %>"/>&nbsp;&nbsp;
   
    <input placeholder="科室名称" id="department" name="department" value="<%=webUser.getDepartment() ==null?"":webUser.getDepartment() %>" style="height: 46px; width: 80px; "/>
    <div class="border_bottom"></div>
</div>
-->




<!--column请假类型-->
<div class="column_box">
	<div class="column_img"><img src="<%=request.getContextPath()%>/suld/images/set.png"></div>
	<select id="leaveKind" name="leaveKind" class=s_center>
		<option value="">申请类别</option>
		<option value="事假">事假</option>
		<option value="病假">病假</option>
		<option value="婚假">婚假</option>
		<option value="产假">产假</option>
		<option value="公派">公派</option>
	</select> 
	
    <div class="border_bottom"></div>
</div>
<!--column请假类型  --徐康祥 276   --王宗明 401   奇乌拉  259   额日琴   319  道格通巴雅尔 247 阿拉腾乌都 402-->
<div class="column_box">
	<div class="column_img"><img src="<%=request.getContextPath()%>/suld/images/people.png"></div>
	<select id="adminLeavel1" name="adminLeavel1" id="adminLeavel1">
		<option value=""  disabled selected>分管领导</option>
		<option value="541-特木尔">特木尔</option>
		<option value="402-阿拉腾乌都">阿拉腾乌都</option>
		<option value="259-奇乌拉">奇乌拉</option>
		<option value="401-王宗明">王宗明</option>
		<option value="319-额日琴">额日琴</option>
		<option value="6-满达">满达</option>
		<option value="581-额邓达来">额邓达来</option>
		<option value="247-道格通巴雅尔">道格通巴雅尔</option>
		<option value="276-徐康祥">徐康祥</option>
		<option value="284-阿腾毕力格">阿腾毕力格</option>
	
	</select> 
	<select  id="adminLeavel2" name="adminLeavel2" id="adminLeavel2">
		<option value="" disabled selected>常务领导</option>
		<option value="319-额日琴">额日琴</option>
		
		
	</select> 
	<select  id="adminLeavel3" name="adminLeavel3" id="adminLeavel3">
		<option value="" disabled selected>主要领导</option>
		<option value="8-呼日亚图">呼日亚图</option>
		<option value="9-吉仁巴雅尔">吉仁巴雅尔</option>
		
	</select> 
    <div class="border_bottom"></div>
</div>

<!--column电话-->
<div class="column_box_tatar">
	<div class="column_img_tatar"><img src="<%=request.getContextPath()%>/suld/images/time.png"></div>
    <input name="startTime" class="ipt datepicker" size="16" type="text" id="starTime"  placeholder="开始时间" style="height: 46px; width: 88px; "/> &nbsp;&nbsp;
    
    <input name="endTime" class="ipt datepicker" size="16" type="text" id="endTime"  placeholder="结束时间" style="height: 46px; width: 88px; "/>
    <div class="border_bottom"></div>
</div>
<div class="column_box_tatar2">
	<div class="column_img_tatar"><img src="<%=request.getContextPath()%>/suld/images/time.png"></div>
    <input name="startTime" class="ipt datepicker" size="16" type="text" id="starTime"  placeholder="开始时间" style="height: 46px; width: 88px; "/> &nbsp;&nbsp;
    
   <label><input name="dayType" id="dayType" checked="true" type="radio" value="全天" style="width: 17px;height: 17px"  >全天 </label> 
	<label><input name="dayType" id="dayType2" type="radio" value="上午" style="width: 17px;height: 17px" >上午 </label> 
	<label><input name="dayType" id="dayType3" type="radio" value="下午" style="width: 17px;height: 17px" />下午 </label>
    <div class="border_bottom"></div>
</div>
<div class="column_box_tatar2">
	<div class="column_img_tatar"><img src="<%=request.getContextPath()%>/suld/images/adrress_2.png"></div>
  	<label><input name="dayType" id="dayType" checked="true" type="radio" value="全天" style="width: 17px;height: 17px"  >全天 </label> 
	<label><input name="dayType" id="dayType2" type="radio" value="上午" style="width: 17px;height: 17px" >上午 </label> 
	<label><input name="dayType" id="dayType3" type="radio" value="下午" style="width: 17px;height: 17px" />下午 </label>
    <div class="border_bottom"></div>
</div>
<!--column详细地址-->
<div class="column_box">
	<div class="column_img"><img src="<%=request.getContextPath()%>/suld/images/order_3.png"></div>
    <input placeholder="理由说明" id="leaveRemark" name="leaveRemark" value=""></input>
    <div class="border_bottom"></div>
</div>
<!--acreage面积-->


<!--confirm确认添加-->
	
	<div class="confirm" style=" background:#01AEF0" id="buttonSub" name="buttonSub">申请请假</div>
</div>

<div class="nav_box">
	<div class="border_top"></div>
	<div class="nav_index chenge" >
    	<img src="<%=request.getContextPath()%>/suld/images/index_blue.png">
        <h1 >请假</h1>
    </div>
    <div class="nav_order chenge"  onclick="document.location='<%=request.getContextPath()%>/suld/tatarLeaveList.jsp';">
    	<img src="<%=request.getContextPath()%>/suld/images/order_gray.png">
         <h1 style=" color:#01AFF0">查看</h1>
    </div>
    <div class="nav_my chenge" onclick="document.location='<%=request.getContextPath()%>/suld/tatarSelf.jsp';" >
    	<img src="<%=request.getContextPath() %>/suld/images/my_gray.png">
        <h1>我的</h1>
    </div>
</div>
<script>
 $(function () {
            $(".datepicker").datepicker();
            $.datepicker.regional['zh-CN'] = {clearText: '清除', clearStatus: '清除已选日期',  

  closeText: '关闭', closeStatus: '不改变当前选择',  

  prevText: '<上月', prevStatus: '显示上月',  

  nextText: '下月>', nextStatus: '显示下月',  

  currentText: '今天', currentStatus: '显示本月',  

  monthNames: ['一月','二月','三月','四月','五月','六月',  

  '七月','八月','九月','十月','十一月','十二月'],  

  monthNamesShort: ['一','二','三','四','五','六',  

  '七','八','九','十','十一','十二'],  

  monthStatus: '选择月份', yearStatus: '选择年份',  

  weekHeader: '周', weekStatus: '年内周次',  

  dayNames: ['星期日','星期一','星期二','星期三','星期四','星期五','星期六'],  

  dayNamesShort: ['周日','周一','周二','周三','周四','周五','周六'],  

  dayNamesMin: ['日','一','二','三','四','五','六'],  

  dayStatus: '设置 DD 为一周起始', dateStatus: '选择 m月 d日, DD',  

  dateFormat: 'yy-mm-dd', firstDay: 1,   

  initStatus: '请选择日期', isRTL: false};  

 $.datepicker.setDefaults($.datepicker.regional['zh-CN']);  
            
 });
$("#buttonSub").click(function(){
		
	
 	if($("#trueName").val()==""){
		alert("姓名不能为空");
		return false;		
	}else if($("#mobile").val()==""){
		alert("手机号不能为空");
		return false;
	}else if($("#unitName").val()==""){
		alert("单位名称不能为空");
		return false;
	}else if($("#leaveKind").val()==""){
		alert("请假类型不能为空");
		return false;
	}else if($("#adminLeavel1").val()==""){
		alert("分管领导不能为空");
		return false;
	}else if($("#adminLeavel2").val()==""){
		alert("常务领导不能为空");
		return false;
	}else if($("#starTime").val()==""){
		alert("开始时间不能为空");
		return false;
	}else if($("#endTime").val()==""){
		alert("结束时间不能为空");
		return false;
	}
	var startTime=$("#starTime").val();
	var endTime=$("#endTime").val();
	var m1=startTime.substring(5,7);
	var m2=endTime.substring(5,7);
	var d1=startTime.substring(8,10);
	var d2=endTime.substring(8,10);
	if(m1!=m2){
		if($("#adminLeavel3").val()==""){
			alert("超过三天主管领导不能为空");
			return false;
		}
	}else if(d2-d1>=2){
		if($("#adminLeavel3").val()==""){
			alert("超过三天主管领导不能为空");
			return false;
		}
	}
	$("#tatarForm").submit();
	
});
$("#dayType2").click(function(){
	var s1=$("#starTime").val();
	var s2=$("#endTime").val()
 	
 	
	if($("#starTime").val()==""){
		alert("开始时间不能为空");
		return false;
	}else if($("#endTime").val()==""){
		alert("结束时间不能为空");
		return false;
	}else if(s1!=s2){
		alert("请假当天才能选半天");
		return false;
	}

		
	
});
$("#dayType3").click(function(){
		
 	var s1=$("#starTime").val();
	var s2=$("#endTime").val()
 	
	if($("#starTime").val()==""){
		alert("开始时间不能为空");
		return false;
	}else if($("#endTime").val()==""){
		alert("结束时间不能为空");
		return false;
	}else if(s1!=s2){
		alert("请假当天才能选半天");
		return false;
	}

		
	
});
</script>
</form>
</body>
</html>