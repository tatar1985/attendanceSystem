<!DOCTYPE HTML>
<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ page import="com.tataren.tatar.domain.TInout"%>
<%@ page import="java.util.Iterator"%>
<%@ page import="java.util.ArrayList"%>
<% 

ArrayList<TInout> list =( ArrayList<TInout>) request.getAttribute("list");
String mapId="";
String labelId="";
if(list!=null){
	for(int m=0 ;m < list.size();m++){	
		TInout bean = list.get(m);
		
			mapId=mapId+"["+bean.getFIRSTCHECKLONGID()+","+bean.getFIRSTCHECKLATIID()+"],";
			labelId=labelId+"['"+bean.getUSERNAME()+","+bean.getFIRSTCHECKTIME().substring(11, 19)+"'],";
	
	}
	System.out.println("777777777777777777777777777"+mapId);
	System.out.println("777777777777777777777777777"+labelId);
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

        <link href="<%=request.getContextPath()%>/main/assets/css/bootstrap.min.css" rel="stylesheet" />
        <link rel="stylesheet" href="<%=request.getContextPath()%>/main/css/style.css"/>       
        <link rel="stylesheet" href="<%=request.getContextPath()%>/main/assets/css/ace.min.css" />
        <link rel="stylesheet" href="<%=request.getContextPath()%>/main/assets/css/font-awesome.min.css" />
        <link rel="stylesheet" href="<%=request.getContextPath()%>/main/Widget/zTree/css/zTreeStyle/zTreeStyle.css" type="text/css">
        <link href="<%=request.getContextPath()%>/main/Widget/icheck/icheck.css" rel="stylesheet" type="text/css" />   

<!--[if IE 6]>
<script type="text/javascript" src="http://lib.h-ui.net/DD_belatedPNG_0.0.8a-min.js" ></script>
<script>DD_belatedPNG.fix('*');</script>
<![endif]-->
<title>????????????</title>
</head>
<body>
<div class="pd-20">
  <form action="<%=request.getContextPath()%>/tatar_/tatar.do" method="post" class="form form-horizontal" id="tatarForm" name="tatarForm">
  <input type="hidden" name="method" value="queryInOutLeave"/> 

  


     
  </form>
</div>
</div>
<div class="other" id="container"></div>


	    <script src="<%=request.getContextPath()%>/main/js/jquery-1.9.1.min.js"></script>   
        <script src="<%=request.getContextPath()%>/main/assets/js/bootstrap.min.js"></script>
        <script src="<%=request.getContextPath()%>/main/assets/js/typeahead-bs2.min.js"></script>
		<!-- page specific plugin scripts -->
		<script src="<%=request.getContextPath()%>/main/assets/js/jquery.dataTables.min.js"></script>
		<script src="<%=request.getContextPath()%>/main/assets/js/jquery.dataTables.bootstrap.js"></script>

        <script src="<%=request.getContextPath()%>/main/assets/layer/layer.js" type="text/javascript" ></script>
        <script src="<%=request.getContextPath()%>/main/assets/laydate/laydate.js" type="text/javascript"></script>
        <script src="<%=request.getContextPath()%>/main/js/lrtk.js" type="text/javascript" ></script>
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
        marker.setLabel({//label??????????????????????????????????????????className??????amap-marker-label
        offset: new AMap.Pixel(20, 20),//??????label?????????maker?????????
        content: labels[i]
    	});
        markers.push(marker);
    }

</script>
</body>
</html>