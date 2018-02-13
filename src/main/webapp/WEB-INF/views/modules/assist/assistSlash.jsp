<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>帮扶状态管理</title>
	<meta name="decorator" content="default"/>
</head>
<body>
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
		<div class="control-group">
			<label class="control-label">帮扶列表：</label>
			<c:if test="${not empty urlList}">
				<div class="controls">
					<table id="contentTable" class="table table-striped table-bordered table-condensed">
						<thead>
							<tr>
								<th>附件链接</th>
							</tr>
						</thead>
						<tbody>
							 <c:forEach items="${urlList}"  var="item" varStatus="status"> 
								 <tr>
								 	<td>
								 		<a href = "${item}" target="_blank">附件${status.count}</a>
								 	</td>
								 </tr>
							 </c:forEach> 
						 </tbody>
					</table>
				</div>
			</c:if>
			<c:if test="${empty urlList}">
				<div class="controls">
					<form:input path="noSlashShow" htmlEscape="false" maxlength="255" class="input-xlarge "/>
				</div>
			</c:if>
		</div>
		<div class="form-actions">
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>