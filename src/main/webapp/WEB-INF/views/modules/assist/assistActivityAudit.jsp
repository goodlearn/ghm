<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>审批管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#name").focus();
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
		<li><a href="${ctx}/assist/assistActivity/">审批列表</a></li>
		<li class="active"><a href="#"><shiro:hasPermission name="assist:assistActivity:edit">${assist.act.taskName}</shiro:hasPermission><shiro:lacksPermission name="assist:assistActivity:edit">查看</shiro:lacksPermission></a></li>
	</ul>
	<form:form id="inputForm" modelAttribute="assistActivity" action="${ctx}/assist/assistActivity/saveAudit" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<form:hidden path="userinfoId"/>
		<form:hidden path="act.taskId"/>
		<form:hidden path="act.taskName"/>
		<form:hidden path="act.taskDefKey"/>
		<form:hidden path="act.procInsId"/>
		<form:hidden path="act.procDefId"/>
		<form:hidden id="flag" path="act.flag"/>
		<sys:message content="${message}"/>
		<fieldset>
			<legend>${assistActivity.act.taskName}</legend>
			<table class="table-form">
				<tr>
					<td class="tit">申请人身份证号</td>
					<td><form:input path="userInfo.idCard" htmlEscape="false" maxlength="50"/></td>
					<td class="tit">申请时间</td>
					<td><form:input path="applyDate" htmlEscape="false" maxlength="50"/></td>
					<td class="tit">帮扶流程</td>
					<td>
					<form:select path="assistState" class="input-xlarge ">
						<form:option value="" label=""/>
						<form:options items="${fns:getDictList('assistState')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
					</form:select>
					</td>
				</tr>
				<tr>
					<td class="tit">申请原因</td>
					<td colspan="5">
						<form:textarea path="applyReason" class="required" rows="5" maxlength="300" cssStyle="width:500px"/>
					</td>
				</tr>
				<tr>
					<td class="tit">审批意见</td>
					<td colspan="5">
						<form:textarea path="act.comment" class="required" rows="5" maxlength="20" cssStyle="width:500px"/>
					</td>
				</tr>
			</table>
		</fieldset>
		<div class="form-actions">
			<shiro:hasPermission name="assist:assistActivity:edit">
				<c:if test="${assistActivity.act.taskDefKey eq 'stateUnionsFinish'}">
					<input id="btnSubmit" class="btn btn-primary" type="submit" value="发放" onclick="$('#flag').val('yes')"/>&nbsp;
				</c:if>
				<c:if test="${assistActivity.act.taskDefKey ne 'stateUnionsFinish'}">
					<input id="btnSubmit" class="btn btn-primary" type="submit" value="同 意" onclick="$('#flag').val('yes')"/>&nbsp;
					<input id="btnSubmit" class="btn btn-inverse" type="submit" value="驳 回" onclick="$('#flag').val('no')"/>&nbsp;
				</c:if>
			</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
		<act:histoicFlow procInsId="${assistActivity.act.procInsId}"/>
	</form:form>
</body>
</html>
