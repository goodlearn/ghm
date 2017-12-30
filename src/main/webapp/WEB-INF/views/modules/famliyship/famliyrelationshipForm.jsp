<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>家庭信息管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
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
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/famliyship/famliyrelationship/">家庭信息列表</a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="famliyrelationship" action="${ctx}/famliyship/famliyrelationship/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<form:hidden path="userinfoId"/>
		<sys:message content="${message}"/>	
		<fieldset>
			<legend>添加成员</legend>
			<table class="table-form">
				<tr>
					<td class="tit">会员</td>
					<td>
						<form:input path="userInfo.name" readonly="true" htmlEscape="false" maxlength="255" class="input-xlarge "/>
					</td>
					<td class="tit">名字</td>
					<td>
						<form:input path="name" htmlEscape="false" maxlength="255" class="input-xlarge "/>
					</td>
					<td class="tit">身份证号</td>
					<td>
						<form:input path="idCard" htmlEscape="false" maxlength="255" class="input-xlarge "/>
					</td>
				</tr>
				<tr>
					<td class="tit">性别</td>
					<td>
						<form:radiobuttons path="gender" items="${fns:getDictList('gender')}" itemLabel="label" itemValue="value" htmlEscape="false" class=""/>
					</td>
					<td class="tit">年龄</td>
					<td>
						<form:input path="age" htmlEscape="false" maxlength="11" class="input-xlarge "/>
					</td>
					<td class="tit">生日</td>
					<td>
						<input name="birth" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
							onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
					</td>
				</tr>
				<tr>
					<td class="tit">家庭地址</td>
					<td>
						<form:input path="famliyAddress" htmlEscape="false" maxlength="255" class="input-xlarge "/>
					</td>
					<td class="tit">健康</td>
					<td>
						<form:select path="healthyKey" class="input-xlarge">
							<form:option value="" label="请选择"/>
							<form:options items="${fns:getDictList('healthy')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
						</form:select>					
					</td>
					<td class="tit">保险情况</td>
					<td>
						<form:select path="insuranceKey" class="input-xlarge">
							<form:option value="" label="请选择"/>
							<form:options items="${fns:getDictList('insuranceKey')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
						</form:select>
					</td>
				</tr>
				<tr>
					<td class="tit">工作地址</td>
					<td>
						<form:input path="jobAddress" htmlEscape="false" maxlength="255" class="input-xlarge "/>
					</td>
					<td class="tit">关系</td>
					<td>
						<form:select path="relationshipKey" class="input-xlarge">
							<form:option value="" label="请选择"/>
							<form:options items="${fns:getDictList('relationship')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
						</form:select>					
					</td>
				</tr>
				<tr>
					<td class="tit">政治面貌</td>
					<td>
						<form:select path="politicalLandscapeKey" class="input-xlarge">
							<form:option value="" label="请选择"/>
							<form:options items="${fns:getDictList('politicalLandscapeKey')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
						</form:select>	
					</td>
					<td class="tit">邮编</td>
					<td>
						<form:input path="postCode" htmlEscape="false" maxlength="255" class="input-xlarge "/>
					</td>
					<td class="tit">薪水</td>
					<td>
						<form:input path="salaryPerson" htmlEscape="false" class="input-xlarge "/>
					</td>
				</tr>
			</table>
		</fieldset>
		<div class="form-actions">
			<shiro:hasPermission name="famliyship:famliyrelationship:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>