<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>社区邮箱管理</title>
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
		<li class="active"><a href="${ctx}/ce/communityemail/">社区邮箱列表</a></li>
		<shiro:hasPermission name="ce:communityemail:edit"><li><a href="${ctx}/ce/communityemail/form">社区邮箱添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="communityemail" action="${ctx}/ce/communityemail/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>社区：</label>
				<form:select path="communityName" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('ce_serial')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>序列号</th>
				<th>社区</th>
				<th>邮箱</th>
				<shiro:hasPermission name="ce:communityemail:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="communityemail">
			<tr>
				<td><a href="${ctx}/ce/communityemail/formShow?id=${communityemail.id}">
					${communityemail.serial}
				</a></td>
				<td>
					${fns:getDictLabel(communityemail.serial, 'ce_serial', '无')}
				</td>
				<td>
					${communityemail.email}
				</td>
				<shiro:hasPermission name="ce:communityemail:edit"><td>
    				<a href="${ctx}/ce/communityemail/form?id=${communityemail.id}">修改</a>
					<a href="${ctx}/ce/communityemail/delete?id=${communityemail.id}" onclick="return confirmx('确认要删除该社区邮箱吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>