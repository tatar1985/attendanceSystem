<!DOCTYPE HTML>
<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ page import="com.tataren.tatar.domain.TInout"%>
<%@ page import="java.util.Iterator"%>
<%@ page import="java.util.ArrayList"%>
<% 
String kindId="0";
kindId = (String)request.getAttribute("kindId");
String dayTime = (String)request.getAttribute("dayTime");
ArrayList<TInout> list =( ArrayList<TInout>) request.getAttribute("list");
String mapId="";
String labelId="";
if(list!=null){
	System.out.println("hhhhhh");
	for(int m=0 ;m < list.size();m++){	
		System.out.println("hh");
		TInout bean = list.get(m);
		if(kindId.equals("1")){
			mapId=mapId+"["+bean.getFIRSTCHECKLONGID()+","+bean.getFIRSTCHECKLATIID()+"],";
			labelId=labelId+"['"+bean.getUSERNAME()+","+bean.getFIRSTCHECKTIME().substring(11, 19)+"'],";
		}else if(kindId.equals("2")){
			mapId=mapId+"["+bean.getTWOCHECKLONGID()+","+bean.getTWOCHECKLATIID()+"],";
			labelId=labelId+"['"+bean.getUSERNAME()+","+bean.getTWOCHECKTIME().substring(11, 19)+"'],";
		}else if(kindId.equals("3")){
			mapId=mapId+"["+bean.getTHREECHECKLONGID()+","+bean.getTHREECHECKLATIID()+"],";
			labelId=labelId+"['"+bean.getUSERNAME()+","+bean.getTHREECHECKTIME().substring(11, 19)+"'],";
		}else if(kindId.equals("4")){
			mapId=mapId+"["+bean.getFOURCHECKLONGID()+","+bean.getFOURCHECKLATIID()+"],";
			labelId=labelId+"['"+bean.getUSERNAME()+","+bean.getFOURCHECKTIME().substring(11, 19)+"'],";
		}
	}
	if(!mapId.equals("")){
		mapId=mapId.substring(0, mapId.length()-1);
		labelId=labelId.substring(0, labelId.length()-1);
	}
} 

	
%>
<html>
<style>
.other{ width:100%;height:800px; position:relative; float:left; background-color:#fff;margin-top:0px; }
</style>
<head>
<script type="text/javascript" src="http://webapi.amap.com/maps?v=1.3&amp;key=f1da219022183a93f81e9307a423fec2"></script> 
<script src="http://webapi.amap.com/maps?v=1.3&amp;key=f1da219022183a93f81e9307a423fec2&amp;callback=init"></script>
<script src="<%=request.getContextPath()%>/suld/js/jquery.js"></script>
<link href="../css/H-ui.min.css" rel="stylesheet" type="text/css" />
<link href="../css/H-ui.admin.css" rel="stylesheet" type="text/css" />
<link href="../lib/icheck/icheck.css" rel="stylesheet" type="text/css" />
<link href="../lib/Hui-iconfont/1.0.1/iconfont.css" rel="stylesheet" type="text/css" />

<!--[if IE 6]>
<script type="text/javascript" src="http://lib.h-ui.net/DD_belatedPNG_0.0.8a-min.js" ></script>
<script>DD_belatedPNG.fix('*');</script>
<![endif]-->
<title>请假申请</title>
</head>
<body>
<div class="pd-20">
  <form action="<%=request.getContextPath()%>/tatar_/tatar.do" method="post" class="form form-horizontal" id="tatarForm" name="tatarForm">
  <input type="hidden" name="method" value="queryInOut"/> 
  <input type="hidden" name="kindId" id="kindId" value="0"/> 
  <input type="hidden" name="dayTime" id="dayTime" value="0"/> 
  

    <div class="row cl">
     <label class="form-label col-3"><span class="c-red">*</span>签到日期：</label>
       <div class="formControls col-9">
     <input type="text" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'datemin\')}'})" id="datemin" name="datemin" class="input-text Wdate" style="width:120px;" datatype="*2-16" nullmsg="请选择日期" value="<%=dayTime==null?"":dayTime %>">
	   <input class="btn btn-primary radius" onClick="member_inOut(1)" type="button" value="上午签到">
	   <input class="btn btn-primary radius" onClick="member_inOut(2)" type="button" value="上午签退">
	   <input class="btn btn-primary radius" onClick="member_inOut(3)" type="button" value="下午签到">
	    <input class="btn btn-primary radius" onClick="member_inOut(4)" type="button" value="下午签退"> 
    </div>
     
  </form>
</div>
</div>
<div class="other" id="container"></div>


<script type="text/javascript" src="../lib/jquery/1.9.1/jquery.min.js"></script> 
<script type="text/javascript" src="../lib/icheck/jquery.icheck.min.js"></script> 
<script type="text/javascript" src="../lib/My97DatePicker/WdatePicker.js"></script> 
<script type="text/javascript" src="../lib/Validform/5.3.2/Validform.min.js"></script>
<script type="text/javascript" src="../lib/layer/1.9.3/layer.js"></script>
<script type="text/javascript" src="../js/H-ui.js"></script> 
<script type="text/javascript" src="../js/H-ui.admin.js"></script>
<script type="text/javascript">
var map = new AMap.Map("container", {
        resizeEnable: true,
        center: [108.57325, 38.042225],
        zoom: 8
    });

   var markers = [], positions = [<%=mapId%>],labels=[<%=labelId%>];
    for (var i = 0, marker; i < positions.length; i++) {
        marker = new AMap.Marker({
            map: map,
            position: positions[i]
        });
        marker.setLabel({//label默认蓝框白底左上角显示，样式className为：amap-marker-label
        offset: new AMap.Pixel(20, 20),//修改label相对于maker的位置
        content: labels[i]
    	});
        markers.push(marker);
    }
function member_inOut(a){
	$("#kindId").val(a)
	$("#dayTime").val($("#datemin").val())
	$("#tatarForm").submit();
}

</script>
</body>
</html>