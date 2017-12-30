<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>资源表管理</title>
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
		<li class="active"><a href="${ctx}/resourceType/resourceType/">资源表列表</a></li>
		<shiro:hasPermission name="resourceType:resourceType:edit"><li><a href="${ctx}/resourceType/resourceType/form">资源表添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="resourceType" action="${ctx}/resourceType/resourceType/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>类型</label>
				<form:input path="type" htmlEscape="false" maxlength="11" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>编号</th>
				<th>类型</th>
				<th>图片路径</th>
				<shiro:hasPermission name="resourceType:resourceType:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="resourceType">
			<tr>
				<td><a href="${ctx}/resourceType/resourceType/form?id=${resourceType.id}">
					${resourceType.id}
				</a></td>
				<td>
					${resourceType.type}
				</td>
				<td>
					${resourceType.sourcePath}
				</td>
				<shiro:hasPermission name="resourceType:resourceType:edit"><td>
    				<a href="${ctx}/resourceType/resourceType/form?id=${resourceType.id}">修改</a>
					<a href="${ctx}/resourceType/resourceType/delete?id=${resourceType.id}" onclick="return confirmx('确认要删除该资源表吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>