<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>会员信息管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#btnImport").click(function(){
				$.jBox($("#importBox").html(), {title:"导入数据", buttons:{"关闭":true}, 
					bottomText:"导入文件不能超过5M，仅允许导入“xls”或“xlsx”格式文件！"});
			});
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
	<div id="importBox" class="hide">
		<form id="importForm" action="${ctx}/userinfo/userinfo/importNew" method="post" enctype="multipart/form-data"
			class="form-search" style="padding-left:20px;text-align:center;" onsubmit="loading('正在导入，请稍等...');"><br/>
			<input id="uploadFile" name="file" type="file" style="width:330px"/><br/><br/>　　
			<input id="btnImportSubmit" class="btn btn-primary" type="submit" value="   导    入   "/>
		</form>
	</div>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/userinfo/userinfo/">会员信息列表</a></li>
		<shiro:hasPermission name="userinfo:userinfo:edit"><li><a href="${ctx}/userinfo/userinfo/form">新增会员</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="userinfo" action="${ctx}/userinfo/userinfo/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>姓名：</label>
				<form:input path="name" htmlEscape="false" maxlength="255" class="input-medium"/>
			</li>
			<li><label>身份证号：</label>
				<form:input path="idCard" htmlEscape="false" maxlength="255" class="input-medium"/>
			</li>
			<li class="btns">
				<input id="btnSubmit" class="btn btn-primary" type="submit" value="查询" onclick="return page();"/>
				<shiro:hasPermission name="userinfo:userinfo:batchedit"><input id="btnImport" class="btn btn-primary" type="button" value="导入"/></shiro:hasPermission>
			</li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>姓名</th>
				<th>身份证号</th>
				<th>性别</th>
				<th>手机</th>
				<th>社区</th>
				<shiro:hasPermission name="userinfo:userinfo:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="userinfo">
			<tr>
				<td><a href="${ctx}/userinfo/userinfo/form?id=${userinfo.id}">
					${userinfo.name}</a>
				</td>
				<td>
					${userinfo.idCard}
				</td>
				<td>
					${fns:getDictLabel(userinfo.gender,'gender','')}
				</td>
				<td>
					${userinfo.phoneNumber}
				</td>
				<td>
					${fns:getDictLabel(userinfo.communityKey,'ce_serial','')}
				</td>
				<td>
					<a href="${ctx}/userinfo/userinfo/formDetails?id=${userinfo.id}">查看</a>
					<shiro:hasPermission name="userinfo:userinfo:edit">
	    				<a href="${ctx}/userinfo/userinfo/form?id=${userinfo.id}">修改</a>
						<a href="${ctx}/userinfo/userinfo/delete?id=${userinfo.id}" onclick="return confirmx('确认要删除该会员信息吗？', this.href)">删除</a>
 						<%--
 							添加帮扶信息关闭
 							<a href="${ctx}/assist/assist/form?userInfoIdCard=${userinfo.id}">添加帮扶信息</a>
 						--%>
 					</shiro:hasPermission>
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>