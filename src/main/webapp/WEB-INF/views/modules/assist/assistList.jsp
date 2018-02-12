<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>帮扶状态管理</title>
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
		<li class="active"><a href="${ctx}/assist/assist/">微帮扶申请列表</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="assist" action="${ctx}/assist/assist/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		
		<ul class="ul-form">
			<li><label>申请人：</label>
				<form:input path="applyName" htmlEscape="false" maxlength="255" class="input-medium"/>
			</li>
			<li><label>帮扶状态：</label>
				<form:select path="assistState" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('assistState')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>申请时间：</label>
				<input name="beginApplydate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${assist.beginApplydate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/> - 
				<input name="endApplydate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${assist.endApplydate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>申请人</th>
				<th>帮扶状态</th>
				<th>申请时间</th>
				<th>社区</th>
				<shiro:hasPermission name="assist:assist:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="assist">
			<tr>
				<td>
					<a href="${ctx}/userinfo/userinfo/form?id=${assist.userinfoId}">
						${assist.applyName}
					</a>
				</td>
				<td>
					${fns:getDictLabel(assist.assistState, 'assistState', '')}
				</td>
				<td>
					<fmt:formatDate value="${assist.applyDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${fns:getDictLabel(assist.userInfo.communityKey,'ce_serial','')}
				</td>
				
					<td>
						<shiro:hasPermission name="assist:assist:view">
							<a href="${ctx}/assist/assist/formDetails?id=${assist.id}">查看</a>
 						</shiro:hasPermission>
						<shiro:hasPermission name="assist:assist:edit">
		    				<a href="${ctx}/assist/assist/form?id=${assist.id}">修改</a>
							<a href="${ctx}/assist/assist/delete?id=${assist.id}" onclick="return confirmx('确认要删除该帮扶状态吗？', this.href)">删除</a>
						</shiro:hasPermission>
						<shiro:hasPermission name="assist:assist:view">
 							<a href="${ctx}/assist/assist/sendMail?id=${assist.id}">发送至邮箱</a>
 						</shiro:hasPermission>
					</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>