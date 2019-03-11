$(function(){
	$.ajax({
		type:"post",
		url:"${pageContext.request.contextPath}/yjbj_queryAlarmAlert.action",
		success:function(data){
			var jsondata = eval(data);
			if(jsondata.length>0){
				var str="";
				for(var i=0;i<jsondata.length;i++){
					if(i>0){
						str+="；";
					}
					if(jsondata[i].YCBJ_NXJCLX!=null){
						str +="&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"+jsondata[i].YCBJ_SJ;
						var lx = jsondata[i].YCBJ_NXJCLX;
						var lxmc;
						if(lx=="1"){
							lxmc = "气耗比";
						}else if(lx=="2"){
							lxmc = "用水指标";
						}else if(lx=="3"){
							lxmc = "厂综合用电率";
						}else if(lx=="4"){
							lxmc = "单位面积耗热量";
						}else if(lx=="5"){
							lxmc = "单位面积耗电量";
						}else if(lx=="6"){
							lxmc = "单位面积耗水量";
						}
						str += "能效检测"+ lxmc +"中：";
						str += jsondata[i].YNSB_GLBH + "检测值为：" + jsondata[i].YCBJ_VAL +"&nbsp;"+ jsondata[i].YCBJ_BJLX;
						//str += jsondata[i].YNSB_GLBH + "已停运";
					}else{
						str +="&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"+jsondata[i].YCBJ_SJ;
						str += jsondata[i].YNSB_FIDNAME + "机组下设备：";
						var fidname = jsondata[i].YNSB_FIDNAME;
						if("环保监测" == fidname  ||  "恒东热电蒸汽+热水换热机组" == fidname ||  "动南燃气联合循环发电机组" == fidname ||  "动南燃气热水锅炉+换热机组" == fidname ||  "动力南厂燃气蒸汽锅炉" == fidname){
							str += jsondata[i].YNSB_GLBH + jsondata[i].YNSBSIS_MC + "检测值为：" + jsondata[i].YCBJ_VAL +"&nbsp;"+ jsondata[i].YCBJ_BJLX;
						}else{
							str += "&nbsp;" + jsondata[i].YNSB_GLBH + "   已停运";
						}
					}
				}
				$("#marquee0").html(str);
			}else{
				$("#marquee0").html("暂无报警信息");
			}
		}
	});
});

function extIntegralPointAfter(){
	loadYcbj();
	setInterval('loadYcbj()', 60*1000);
}

function loadYcbj(){
	$.ajax({
		type:"post",
		url:"${pageContext.request.contextPath}/yjbj_queryAlarmAlert.action",
		success:function(data){
			var jsondata = eval(data);
			if(jsondata.length>0){
				var str="";
				for(var i=0;i<jsondata.length;i++){
					if(i>0){
						str+="；";
					}
					
					if(jsondata[i].YCBJ_NXJCLX!=null){
						str +="&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"+jsondata[i].YCBJ_SJ;
						var lx = jsondata[i].YCBJ_NXJCLX;
						var lxmc;
						if(lx=="1"){
							lxmc = "气耗比";
						}else if(lx=="2"){
							lxmc = "用水指标";
						}else if(lx=="3"){
							lxmc = "厂综合用电率";
						}else if(lx=="4"){
							lxmc = "单位面积耗热量";
						}else if(lx=="5"){
							lxmc = "单位面积耗电量";
						}else if(lx=="6"){
							lxmc = "单位面积耗水量";
						}
						str += "能效检测"+ lxmc +"中：";
						str += jsondata[i].YNSB_GLBH + "检测值为：" + jsondata[i].YCBJ_VAL +"&nbsp;"+ jsondata[i].YCBJ_BJLX;
						//str += jsondata[i].YNSB_GLBH + "已停运";
					}else{
						str +="&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"+jsondata[i].YCBJ_SJ;
						str += jsondata[i].YNSB_FIDNAME + "机组下设备：";
						var fidname = jsondata[i].YNSB_FIDNAME;
						if("环保监测" == fidname ||  "恒东热电燃气蒸汽锅炉" == fidname ||  "恒东热电蒸汽+热水换热机组" == fidname ||  "动南燃气联合循环发电机组" == fidname ||  "动南燃气热水锅炉+换热机组" == fidname ||  "动力南厂燃气蒸汽锅炉" == fidname){
							str += jsondata[i].YNSB_GLBH + jsondata[i].YNSBSIS_MC + "检测值为：" + jsondata[i].YCBJ_VAL +"&nbsp;"+ jsondata[i].YCBJ_BJLX;
						}else{
							str += "&nbsp;" + jsondata[i].YNSB_GLBH + " 已停运";
						}
					}
				}
				$("#marquee0").html(str);
			}else{
				$("#marquee0").html("暂无报警信息");
			}
		}
	});
}

var newdate = new Date();
var sec = newdate.getSeconds(); //当前秒数
var surplusSec=60-sec;
setTimeout('extIntegralPointAfter()',surplusSec*1000);

function ycbjMarqueeDetail(){
	$("#ycbjMarquee").window({
		width:980,    
		height:300,
		title:"报警详情",
		href:"${pageContext.request.contextPath}/ycbjMarqueeInit.action"
	});
}