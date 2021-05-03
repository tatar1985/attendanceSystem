<!DOCTYPE HTML>
<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@page import="com.tataren.tatar.domain.Texam"%>

<title></title>
 <link href="<%=request.getContextPath()%>/main/assets/css/bootstrap.min.css" rel="stylesheet" />
        <link rel="stylesheet" href="<%=request.getContextPath()%>/main/css/style.css"/>       
        <link href="<%=request.getContextPath()%>/main/assets/css/codemirror.css" rel="stylesheet">
        <link rel="stylesheet" href="<%=request.getContextPath()%>/main/assets/css/ace.min.css" />
        <link rel="stylesheet" href="<%=request.getContextPath()%>/main/font/css/font-awesome.min.css" />
        <link rel="stylesheet" href="<%=request.getContextPath()%>/main/assets/css/font-awesome.min.css" />   
	<script src="<%=request.getContextPath()%>/main/js/jquery-1.9.1.min.js"></script>
        <script src="<%=request.getContextPath()%>/main/assets/js/bootstrap.min.js"></script>
		<script src="<%=request.getContextPath()%>/main/assets/js/typeahead-bs2.min.js"></script>           	
		<script src="<%=request.getContextPath()%>/main/assets/js/jquery.dataTables.min.js"></script>
		<script src="<%=request.getContextPath()%>/main/assets/js/jquery.dataTables.bootstrap.js"></script>
        <script src="<%=request.getContextPath()%>/main/assets/layer/layer.js" type="text/javascript" ></script>          
        <script src="<%=request.getContextPath()%>/main/assets/laydate/laydate.js" type="text/javascript"></script>	
</script>
<style type="text/css">

/*布局*/
#box {
	margin: 0 auto;
	width: 500pt;
	padding: 20pt 0;
}
p {
 line-height:15px
}

#inner_examCard {
}

#inner_examCard .table {
	width: 100%;
	border: 0;
	border-collapse: collapse;
}

.print_submit {
	text-align: center;
}

.print_submit button {
	font-size: 14px;
	padding: 5px;
	margin: 20px;
	text-align: center;
}

@media print {
	.noprint {
		display: none;
	}
}



.examinee_box {
}

.examinee_box table {
	border: 2px solid #000;
	border-spacing: 0;
	border-collapse: collapse;
	width: 100%;
}

.examinee_box td {
	border: 1px solid #ccc;
	padding: 10pt 5pt;
	font-size: 11pt;
}

.examinee_box td p {
	margin: 0;
	line-height: 0.5em;
}


.examinee_box .big_title2 {
	text-align: right;
	font-size: 11pt;
	font-weight: bold;
	line-height: 0.5em;
}
</style>
</head>

<body>


<div class="add_menber" id="add_menber_style" >
 <form class="form form-horizontal" action="<%=request.getContextPath()%>/tatar_/tatar.do" method="post"  id="tatarForm" name="tatarForm">
    <input type="hidden" name="method" value="examQuery" id="method"/>  
   	<table align="center" valign="center" >
		<tbody  align="center">
		<tr>
		<td class="big_title2" width="20%">姓名：</td><td width="30%"><input type="text" id="姓名" name="username"/></td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		<td class="big_title2" width="20%">身份证：</td><td width="30%"><input type="text" id="身份证号" name="identification"/></td>
		</tr>
		</tbody>
	</table>
    </form>
 </div>
</body>
<script>
 $('#member_add').on('click', function(){
    layer.open({
        type: 1,
        title: '查询模块',
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
               
			   layer.alert(str+=""+$(this).attr("id")+"不能为空！\r\n",{
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
</script>

</html>