<!DOCTYPE HTML>
<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@page import="com.tataren.tatar.domain.Texam"%>
<% Texam bean = (Texam)request.getAttribute("bean"); %>
<title></title>
 
	<script src="<%=request.getContextPath()%>/main/js/jquery-1.9.1.min.js"></script>
        <script src="<%=request.getContextPath()%>/main/assets/js/bootstrap.min.js"></script>
		<script src="<%=request.getContextPath()%>/main/assets/js/typeahead-bs2.min.js"></script>           	
		<script src="<%=request.getContextPath()%>/main/assets/js/jquery.dataTables.min.js"></script>
		<script src="<%=request.getContextPath()%>/main/assets/js/jquery.dataTables.bootstrap.js"></script>
        <script src="<%=request.getContextPath()%>/main/assets/layer/layer.js" type="text/javascript" ></script>          
        <script src="<%=request.getContextPath()%>/main/assets/laydate/laydate.js" type="text/javascript"></script>
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
<script type="text/javascript">
	
</script>
</head>
<body>
<%if(bean!=null){ %>
	<div id="box">
		<div class="print_submit">
			<button id="print_btn" class="noprint" onclick="javascript:window.print();">打印准考证</button>
			<button class="noprint"  onClick="javascript:window.location.href='<%=request.getContextPath()%>/exam/5.jsp' " >打印其他人员</button>
		</div>
		<div id="inner_examCard">
			<div>
				<p style="margin:0;padding: 20pt 0 10pt;font-size: 18pt;font-weight: bold;text-align: center;">
					<span style="border-bottom: 3px double #000;padding: 5pt 20pt;">2017年康巴什新区警务辅助人员考试准考证</span>
				</p>
				<p>
				</p>
				<div class="examinee_box">
				
					<table>
						<tbody>
						<tr>
							<td class="big_title2">准考证号：</td>
							<td colspan="3"><%=bean.getExamNo() %></td>
							<td rowspan="4">
									<img width="120" height="180" src="<%=request.getContextPath()%>/exam/img/<%=bean.getIdentification() %>.jpg"/>
							</td>
						</tr>
						<tr>
							<td class="big_title2" width="15%">考员姓名：</td>
							<td width="25%"><%=bean.getUsername() %></td>
							<td class="big_title2" width="15%">身份证号：</td>
							<td width="25%"><%=bean.getIdentification() %></td>
							
						</tr>
						
						<tr>
							<td class="big_title2">考试日期：</td>
							<td>2017年7月20日</td>
							<td class="big_title2">考试时间：</td>
							<td>上午9:00—11:00</td>
						</tr>
						<tr>
							<td class="big_title2">考&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;点：</td>
							<td >康巴什新区第一中学</td>
							<td class="big_title2">考试地址：</td>
							<td>康巴什新区市府北街与民和路交界处</td>
							
						</tr>			
						
						<tr>
							<td class="big_title2">考&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;场：</td>
							<td ><%=bean.getRoom() %></td>
							<td class="big_title2">座&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;位：</td>
							<td colspan="2"><%=bean.getChair() %></td>
						
							
							
						</tr>
						
						
						
					</tbody></table>
				</div>
				<p style="font-weight: bold;text-align: left;font-size: 11pt;">考生须知：</p>
					
<p>考生必须认真阅读以下信息，认真遵守本次考试的相关法律、规则和要求，否则，将承担由此引起的相应的后果和责任。</p>
<p>1.领到《准考证》，考生应认真核对姓名、身份证号等相关信息，如不正确请及时联系考试报名处更正。考生不得修改《准考证》的任何信息，考试时如发现考生所持《准考证》与《考场底册》信息不一致，将被取消考试资格，并按违纪处理。
</p><p>2. 考生应于考试前15分钟凭准考证、身份证进入考场，持其他证件将禁止进入考场。考生应在“考场座次表”上签到后对号入座，并将两证放在课桌右上角以便核查。
</p><p>3.本次考试规定：开考15分钟后一律禁止入场（以进入考场时间为准），考试中途不得退场。
</p><p>4. 考生应携带黑色墨水笔或黑色碳素笔、2B铅笔、橡皮。黑色墨水笔或黑色碳素笔用于填写姓名、准考证号、作答试卷。2B铅笔用于填涂所有信息点。使用其它型号的铅笔或其它颜色的墨水笔可能影响考试成绩。
</p><p>5.答题前，请认真阅读答题卡和试卷的答题注意事项，按规定要求作答。考生必须在答题卡上在指定位置准确填写个人信息。凡未按相关要求填写或作答对阅卷造成影响的，后果自负。
</p><p>6. 考生不得随身携带手机、智能手表、手环、无线电接收器等通讯工具。若携带上述通讯工具，考试前必须关机并放置于考场指定的存包处，禁止随身携带。所有参考人员须经金属探测仪检查，请考生认真配合检查工作 ，否则将被视为违纪。一经发现随身携带电子通讯工具，也将被视为违纪。
</p><p>7.考试结束后，考生必须将所答试卷、答题卡、草稿纸全部交回，严禁带离考场。否则，将被视为违纪。
</p><p>8. 考生必须全面了解并严格遵守考试纪律、考场规则和相关要求，违反者将按相关规定及法律严肃处理。
</p><p>9.刑法修正案（九）已于2015年11月1日起实施。根据相关法律规定，考试过程中销售、使用手机、无线电通讯工具接收传递答案，以及代替他人或让他人代替自己参加考试等行为将处以管制、拘役或并处罚金，严重者将获七年以下有期徒刑。
</p><p>提示：考生应在考试前一天熟悉考场地址和交通路线。考试当天应提早出发，以免迟到。
</p>
				
			</div>
		</div>
	</div>


<div class="add_menber" id="add_menber_style" style="display:none">
 <form class="form form-horizontal" action="<%=request.getContextPath()%>/tatar_/tatar.do" method="post"  id="tatarForm" name="tatarForm">
    <input type="hidden" name="method" value="examQuery" id="method"/>  
   	<table>
		<tbody>
		<tr>
		<td class="big_title2" width="20%">姓名：</td><td width="30%"><input type="text" id="姓名" name="username"/></td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		<td class="big_title2" width="20%">身份证：</td><td width="30%"><input type="text" id="身份证号" name="identification"/></td>
		</tr>
		</tbody>
	</table>
    </form>
 </div>
<%}else{ %>
姓名或身份证号码不对！请重新输入
<%} %>
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