<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>会员信息查询</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/userinfo/userinfo/">会员信息列表</a></li>
	</ul><br/>
	<form:form class="form-horizontal">
		<sys:message content="${message}"/>	
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
								${fns:getDictLabel('true','gender','')}
							</c:when>
							 <c:otherwise> 
								${fns:getDictLabel('false','gender','')}
							 </c:otherwise>
						</c:choose>
					</td>							
					
					<td class="tit">民族</td>
					<td>
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
					<td class="tit">证件类型</td>
					<td colspan="2">${userinfo.certificate}</td>
					<td class="tit">身份证号</td>
					<td colspan="2">${userinfo.idCard}</td>
					<td class="tit">出生日期</td>
					<td colspan="2">${userinfo.birth}</td>
					<td class="tit">是否参加医保</td>
					<td colspan="2">${fns:getDictLabel(userinfo.medicalInsurance,'true_false','')}</td>
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
					<td colspan="2">${userinfo.politicalLandscape}</td>
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
					<td class="tit">是否享受低保</td>
					<td colspan="2">${fns:getDictLabel(userinfo.subsistenceAllowances,'true_false','')}</td>
					<td class="tit">劳模先进</td>
					<td colspan="2">${fns:getDictLabel(userinfo.advanced,'yes_no','')}</td>
					<td class="tit">五一劳动</td>
					<td colspan="2">${fns:getDictLabel(userinfo.labor,'yes_no','')}</td>
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
		</fieldset>
		
		<div class="form-actions">
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>