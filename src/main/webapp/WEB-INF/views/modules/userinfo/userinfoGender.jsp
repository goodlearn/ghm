<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>

<html>·
<head>
	<title>会员信息管理</title>
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
   	 	function loadData() {
   	 		var pageContextVal = $("#PageContext").val();
   	 	 	$.ajax({
	   	 	 	 type : "get",
	             async : false, //同步执行
	             url : pageContextVal+'/userinfo/userinfo/findGenderCount',
	             dataType : "json", //返回数据形式为json
	             success : function(result) {
	                    if (result) {
	                    	  var myChart = echarts.init(document
	                                  .getElementById('main'));
	                    	  var option = {
                    			  title : {
                                      text : '会员档案数:'+result.totalP,
                                      x:'center',
                                      y: 'top',
                                      textStyle: {//主标题文本样式{"fontSize": 18,"fontWeight": "bolder","color": "#333"}
                                          fontFamily: 'font_family',
                                          fontSize: 20,
                                          fontStyle: 'normal',
                                          fontWeight: 'normal',
                                      },
                    			  }, 
                                  series : [ {
                                      name : '人数比例',
                                      type : 'pie',
                                      radius : '65%',
                                      center: ['50%', '65%'],
                                      minAngle:'15',
                                      label: {
                                          normal: {
                                              show: true,
                                              formatter: '{b}: {c}({d}%)'
                                          }
                                      },
                                      data : [{
                                          value : result.manP,
                                          name : '男性人数'
                                      }, {
                                          value : result.womanP,
                                          name : '女性人数'
                                      },{
                                          value : result.undefinedP,
                                          name : '未知性别人数'
                                      }]
                                  } ]
	                    	  };
	                    	  myChart.setOption(option);
	                    }
	                },
	                error : function(errorMsg) {
	                    alert("图表请求数据失败啦!");
	                }
   	 	 	});
   	 	}
   	 	loadData();
    </script>
</body>
</html>