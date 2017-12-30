<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>家庭信息管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			
		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/famliyship/famliyrelationship/">家庭信息列表</a></li>
		<li class="active"><a href="${ctx}/famliyship/famliyrelationship/form">家庭成员信息添加</a></li>	</ul>
	<form:form id="searchForm" modelAttribute="famliyrelationship" action="${ctx}/famliyship/famliyrelationship/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>姓名：</label>
				<form:input path="name" htmlEscape="false" maxlength="255" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>姓名</th>
				<th>身份证</th>
				<th>关系人</th>
				<th>关系</th>
				<shiro:hasPermission name="famliyship:famliyrelationship:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="famliyrelationship">
			<tr>
				<td><a href="${ctx}/famliyship/famliyrelationship/form?id=${famliyrelationship.id}">
					${famliyrelationship.name}
				</a></td>
				<td>
					${famliyrelationship.idCard}
				</td>
				<td>
					${famliyrelationship.userInfo.name}
				</td>
				<td>
					${famliyrelationship.relationship}
				</td>
				<shiro:hasPermission name="famliyship:famliyrelationship:edit"><td>
    				<a href="${ctx}/famliyship/famliyrelationship/form?id=${famliyrelationship.id}">修改</a>
					<a href="${ctx}/famliyship/famliyrelationship/delete?id=${famliyrelationship.id}" onclick="return confirmx('确认要删除该家庭信息吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>