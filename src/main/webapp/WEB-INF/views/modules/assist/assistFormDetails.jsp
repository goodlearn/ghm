<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>帮扶状态管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#btnImport").click(function(){
				$.jBox($("#importBox").html(), {title:"导入数据", buttons:{"关闭":true}, 
					bottomText:"导入文件不能超过5M"});
			});
			//$("#name").focus();
			$("#inputForm").validate({
				submitHandler: function(form){
					loading('正在提交，请稍等...');
					form.submit();
				},
				errorContainer: "#messageBox",
				errorPlacement: function(error, element) {
					$("#messageBox").text("输入有误，请先更正。");
					if (element.is(":checkbox")||element.is(":radio")||element.parent().is(".input-append")){
						error.appendTo(element.parent().parent());
					} else {
						error.insertAfter(element);
					}
				}
			});
		});
	</script>
</head>
<body>
	<div id="importBox" class="hide">
		<form id="importForm" action="${ctx}/assist/assist/uploadManyImages" method="post" enctype="multipart/form-data"
			class="form-search" style="padding-left:20px;text-align:center;" onsubmit="loading('正在导入，请稍等...');"><br/>
			<input id="idCard" name="idCard" type="hidden" value="${userinfo.idCard}"/>
			<input id="uploadFile" name="file" type="file" style="width:330px"/><br/><br/>　　
			<input id="btnImportSubmit" class="btn btn-primary" type="submit" value=" 上传   "/>
		</form>
	</div>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/assist/assist/">帮扶状态列表</a></li>
	</ul><br/>
	<form:form class="form-horizontal">
		<sys:message content="${message}"/>	
		<ul class="ul-form">
			<li>
				<c:if test="${not empty showButtolWord}">
					<a href="${ctx}/assist/assist/editState?id=${assist.id}">
							<input id="btnSubmitAssist" class="btn btn-primary" type="button" value="${showButtolWord}"/>
					</a>
				</c:if>
				<a href="${ctx}/assist/assist/downloadWord?id=${assist.id}">
					<input id="btnSubmitAssist" class="btn btn-primary" type="button" value="下载表格"/>
				</a>
				<a href="${ctx}/assist/assist/downloadPhoto?id=${assist.id}">
					<input id="btnSubmitAssist" class="btn btn-primary" type="button" value="下载附件"/>
				</a>
				<input id="btnImport" class="btn btn-primary" type="button" value="上传附件"/>
			</li>
		</ul>
		<fieldset>
			<legend>会员信息</legend>
			<table class="table-form">
				<tr>
					<td class="tit">姓名</td>
					<td colspan="2">${userinfo.name}</td>
					<td class="tit">性别</td>
					<td colspan="2">
						<c:choose>
							<c:when test="${userinfo.gender}">
								${fns:getDictLabel('1','gender','')}
							</c:when>
							 <c:otherwise> 
								${fns:getDictLabel('0','gender','')}
							 </c:otherwise>
						</c:choose>
					</td>							
					
					<td class="tit">民族</td>
					<td colspan="2">
						${fns:getDictLabel(userinfo.nationKey,'nationKey','')}
					</td>
					<td class="tit">健康</td>
					<td colspan="2">${userinfo.healthy}</td>
					<td class="tit">电话</td>
					<td colspan="2">${userinfo.phoneNumber}</td>
				</tr>
				<tr>
					<td class="tit">电话号码</td>
					<td colspan="2">${userinfo.phoneNumber}</td>
					<td class="tit">身份证号</td>
					<td colspan="2">${userinfo.idCard}</td>
					<td class="tit">出生日期</td>
					<td colspan="2">${userinfo.birth}</td>
					<td class="tit">是否参加医保</td>
					<td colspan="2">${fns:getDictLabel(userinfo.medicalInsurance,'true_false','')}</td>
					<td class="tit">是否享受低保</td>
					<td colspan="2">${fns:getDictLabel(userinfo.subsistenceAllowances,'true_false','')}</td>
				</tr>
				<tr>
					<td class="tit">工作单位</td>
					<td colspan="2">${userinfo.originalWorkAddress}</td>
					<td class="tit">劳模类型</td>
					<td colspan="2">${userinfo.modelWorker}</td>
					<td class="tit">婚姻状况</td>
					<td colspan="2">${userinfo.marriage}</td>
					<td class="tit">邮政编码</td>
					<td colspan="2">${userinfo.postCode}</td>
					<td class="tit">政治面貌</td>
					<td colspan="2">${fns:getDictValue(userinfo.politicalLandscape,'politicalLandscapeKey','')}</td>
				</tr>
				<tr>
					<td class="tit">家庭住址</td>
					<td colspan="2">${userinfo.famliyAddress}</td>
					<td class="tit">住房类型</td>
					<td colspan="2">${userinfo.housingKind}</td>
					<td class="tit">住房面积</td>
					<td colspan="2">${userinfo.housingArea}</td>
					<td class="tit">家庭人口</td>
					<td colspan="2">${userinfo.famliyNum}</td>
					<td class="tit">户口类型</td>
					<td colspan="2">${userinfo.registerKind}</td>
				</tr>
				<tr>
					<td class="tit">残疾类别</td>
					<td colspan="2">${userinfo.disability}</td>
					<td class="tit">本人月工资</td>
					<td colspan="2">${userinfo.salaryPerson}</td>
					<td class="tit">家庭月人均收入</td>
					<td colspan="2">${salaryFamliy}</td>
					<td class="tit">户口所在地行政区别</td>
					<td colspan="2">${userinfo.registerPlace}</td>
					<td class="tit">身份</td>
					<td colspan="2">${userinfo.identity}</td>
				</tr>
				<tr>
					<td class="tit">备注</td>
					<td colspan="5">
						${userinfo.remarks}
					</td>
				</tr>
			</table>
			<legend>家庭信息</legend>
			<c:if test="${frCount > 0}">
				<table class="table-form">
					<c:forEach items="${famliyrelationships}" var="frs">
		           		<tr>
							<td class="tit">姓名</td>
							<td colspan="2">${frs.name}</td>
							<td class="tit">关系</td>
							<td colspan="2">${fns:getDictLabel(frs.relationshipKey,'relationship','')}</td>
							<td class="tit">性别</td>
							<td colspan="2">
								<c:choose>
									<c:when test="${frs.gender}">
										${fns:getDictLabel('1','gender','')}
									</c:when>
									 <c:otherwise> 
										${fns:getDictLabel('0','gender','')}
									 </c:otherwise>
								</c:choose>
							</td>
							<td class="tit">出生日期</td>
							<td colspan="2">${frs.birth}</td>
							<td class="tit">政治面貌</td>
							<td colspan="2">${fns:getDictLabel(frs.politicalLandscapeKey,'politicalLandscapeKey','')}</td>
						</tr>
						<tr>
							<td class="tit">身份证号</td>
							<td colspan="2">${frs.idCard}</td>
							<td class="tit">健康状况</td>
							<td colspan="2">${frs.healthy}</td>
							<td class="tit">所在单位或学校</td>
							<td colspan="2">${frs.jobAddress}</td>
							<td class="tit">本人月工资</td>
							<td colspan="2">${frs.salaryPerson}</td>
							<td class="tit">保险情况</td>
							<td colspan="2">${fns:getDictLabel(frs.insuranceKey,'insuranceKey','')}</td>
						</tr>
		            </c:forEach>
				</table>
			</c:if>
			<legend>帮扶信息</legend>
			<table class="table-form">
				<tr>
					<td class="tit">申请救助理由</td>
					<td colspan="5">
						${assist.applyReason}
					</td>
				</tr>
			</table>
		</fieldset>
		
		<div class="form-actions">
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>