<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>

<html>·
<head>
	<title>帮扶信息管理</title>
	<meta name="decorator" content="default"/>
	<script src="${ctxStatic}/jquery/jquery-1.8.3.min.js" type="text/javascript"></script>
	<script src="${ctxStatic}/echart/echarts.min.js" type="text/javascript"></script>
</head>
<body>
	 <!--定义页面图表容器-->
    <!-- 必须制定容器的大小（height、width） -->
    <div id="main" style="height: 400px; border: 1px solid #ccc; padding: 10px;"></div>
    <input id="PageContext" type="hidden" value="${ctx}" />
    <script type="text/javascript">
   	 	function loadData(option) {
   	 		var pageContextVal = $("#PageContext").val();
   	 	 	$.ajax({
	   	 	 	 type : "get",
	             async : false, //同步执行
	             url : pageContextVal+'/assist/assist/findAuc',
	             dataType : "json", //返回数据形式为json
	             success : function(result) {
	                    if (result) {
	                    	 //初始化xAxis[0]的data
	                        option.xAxis[0].data = [];
	                        for (var i=0; i<result.length; i++) {
	                            option.xAxis[0].data.push(result[i].type);
	                        }
	                        //初始化series[0]的data
	                        option.series[0].data = [];
	                        for (var i=0; i<result.length; i++) {
	                            option.series[0].data.push(result[i].num);
	                        }
	                    }
	                },
	                error : function(errorMsg) {
	                    alert("图表请求数据失败啦!");
	                }
   	 	 	});
   	 	}
   	 	 	
   	 	  var myChart = echarts.init(document
                  .getElementById('main'));
    	  var option = {
			  title : {
                  text : '帮扶数量',
                  x:'center',
                  y: 'top',
                  textStyle: {//主标题文本样式{"fontSize": 18,"fontWeight": "bolder","color": "#333"}
                      fontFamily: 'font_family',
                      fontSize: 20,
                      fontStyle: 'normal',
                      fontWeight: 'normal',
                  },
			  }, 
			  legend:{
		            data:['帮扶数量']
		      },
		      xAxis : [ {
	              type : 'category'
	          } ],
	          yAxis : [ {
	              type : 'value'
	          } ],
              series : [ {
                  name : '数量',
                  type : 'bar',
                  label:{ 
                	  normal:{ 
                	  show: true, 
                	  position: 'inside'
                	} 
                },
              } ]
    	  };
    	  loadData(option);
    	  myChart.setOption(option);
    </script>
</body>
</html>