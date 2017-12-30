<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>审批管理</title>
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
		<li class="active"><a href="${ctx}/assist/assistActivity/">审批列表</a></li>
		<shiro:hasPermission name="assist:assistActivity:start"><li><a href="${ctx}/assist/assistActivity/form">审批申请流程</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="assistActivity" action="${ctx}/assist/assistActivity/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead><tr><th>申请人身份证号</th><th>申请人</th><th>申请时间</th><th>申请理由</th><shiro:hasPermission name="oa:testAudit:edit"><th>操作</th></shiro:hasPermission></tr></thead>
		<tbody>
		<c:forEach items="${page.list}" var="assistActivity">
			<tr>
				<td><a href="${ctx}/assist/assistActivity/form?id=${assistActivity.id}">${assistActivity.userInfo.idCard}</a></td>
				<td>${assistActivity.applyName}</td>
				<td>${assistActivity.applyDate}</td>
				<td colspan="5">
						${assistActivity.applyReason}	
				</td>
				<shiro:hasPermission name="assist:assistActivity:edit"><td>
    				<a href="${ctx}/assist/assistActivity/form?id=${assistActivity.id}">详情</a>
					<a href="${ctx}/assist/assistActivity/delete?id=${assistActivity.id}" onclick="return confirmx('确认要删除该审批吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>
