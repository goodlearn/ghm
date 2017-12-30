<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>帮扶状态管理</title>
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
		<li><a href="${ctx}/assist/assist/">帮扶状态列表</a></li>
		<li class="active"><a href="${ctx}/assist/assist/form?id=${assist.id}">帮扶状态<shiro:hasPermission name="assist:assist:edit">${not empty assist.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="assist:assist:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="assist" action="${ctx}/assist/assist/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<form:hidden path="userinfoId"/>
		<sys:message content="${message}"/>	
		<div class="control-group">
			<label class="control-label">申请人：</label>
			<div class="controls">
				<form:input path="applyName" htmlEscape="false" maxlength="255" class="input-xlarge "/>
			</div>
		</div>	
		<div class="control-group">
			<label class="control-label">申请理由：</label>
			<div class="controls">
				<form:textarea path="applyReason" htmlEscape="false" rows="4" maxlength="255" class="input-xxlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">帮扶状态：</label>
			<div class="controls">
				<form:select path="assistState" class="input-xlarge ">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('assistState')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="assist:assist:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>