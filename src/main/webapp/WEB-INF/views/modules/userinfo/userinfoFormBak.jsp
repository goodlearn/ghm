<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>会员信息管理</title>
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
		<li><a href="${ctx}/userinfo/userinfo/">会员信息列表</a></li>
		<li class="active"><a href="${ctx}/userinfo/userinfo/form?id=${userinfo.id}">会员信息<shiro:hasPermission name="userinfo:userinfo:edit">${not empty userinfo.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="userinfo:userinfo:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="userinfo" action="${ctx}/userinfo/userinfo/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<fieldset>
			<legend>会员信息</legend>
			<table class="table-form">
				<tr>
					<td class="tit">姓名</td>
					<td>
						<form:input path="name" htmlEscape="false" maxlength="255" class="input-xlarge required"/>
					</td>
					<td class="tit">生日</td>
					<td>
						<input name="birth" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
							onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>	
					</td>
				</tr>
				<tr>
					<td class="tit">性别</td>
					<td>
						<form:radiobuttons path="gender" items="${fns:getDictList('gender')}" itemLabel="label" itemValue="value" htmlEscape="false" class=""/>
					</td>
					<td class="tit">健康</td>
					<td>
						<form:input path="healthy" htmlEscape="false" class="input-xlarge "/>
					</td>
				</tr>
				<tr>
					<td class="tit">身份</td>
					<td>
						<form:input path="identity" htmlEscape="false" maxlength="255" class="input-xlarge "/>					
					</td>	
					<td class="tit">民族</td>
					<td>
						<form:select path="nationKey" class="input-xlarge">
							<form:option value="" label="请选择"/>
							<form:options items="${fns:getDictList('nationKey')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
						</form:select>						
					</td>
				</tr>
				<tr>	
					<td class="tit">邮编</td>
					<td>
						<form:input path="postCode" htmlEscape="false" maxlength="255" class="input-xlarge "/>
					</td>
					<td class="tit">社区</td>
					<td>
						<form:select path="communityKey" class="input-xlarge required">
							<form:option value="" label=""/>
							<form:options items="${fns:getDictList('ce_serial')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
						</form:select>	
					</td>	
				</tr>
				<tr>
					<td class="tit">电话号码</td>
					<td>
						<form:input path="phoneNumber" htmlEscape="false" maxlength="255" class="input-xlarge required"/>
					</td>
					<td class="tit">文化程度</td>
					<td>
						<form:input path="degreeOfEducation" htmlEscape="false" maxlength="255" class="input-xlarge "/>
					</td>
				</tr>
				<tr>
					<td class="tit">残疾类别</td>
					<td>
						<form:input path="disability" htmlEscape="false" maxlength="255" class="input-xlarge "/>
					</td>
					<td class="tit">家庭地址</td>
					<td>
						<form:input path="famliyAddress" htmlEscape="false" maxlength="255" class="input-xlarge "/>
					</td>
				</tr>
				<tr>
					<td class="tit">住房面积</td>
					<td>
						<form:input path="housingArea" htmlEscape="false" class="input-xlarge "/>
					</td>
					<td class="tit">住房类型</td>
					<td>
						<form:input path="housingKind" htmlEscape="false" class="input-xlarge "/>
					</td>
				</tr>
				<tr>
					<td class="tit">证件类型</td>
					<td>
						<form:input path="certificate" htmlEscape="false" class="input-xlarge "/>
					</td>
					<td class="tit">身份证号</td>
					<td>
						<form:input path="idCard" htmlEscape="false" maxlength="255" class="input-xlarge required"/>
						<span class="help-inline"><font color="red">*</font> </span>					
					</td>
				</tr>
				<tr>
					<td class="tit">保险情况</td>
					<td>
						<form:select path="insuranceKey" class="input-xlarge">
							<form:option value="" label="请选择"/>
							<form:options items="${fns:getDictList('insuranceKey')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
						</form:select>						</td>
					<td class="tit">婚姻状况</td>
					<td>
						<form:input path="marriage" htmlEscape="false" maxlength="255" class="input-xlarge required"/>
					</td>
				</tr>
				<tr>
					<td class="tit">劳模类型</td>
					<td>
						<form:input path="modelWorker" htmlEscape="false" maxlength="255" class="input-xlarge required"/>
					</td>
					<td class="tit">工作单位</td>
					<td>
						<form:input path="originalWorkAddress" htmlEscape="false" maxlength="255" class="input-xlarge "/>
					</td>
				</tr>
					<tr>
					<td class="tit">政治面貌</td>
					<td>
						<form:input path="politicalLandscape" htmlEscape="false" maxlength="255" class="input-xlarge "/>
					</td>
					<td class="tit">困难类别</td>
					<td>
						<form:input path="problemKind" htmlEscape="false" maxlength="255" class="input-xlarge "/>
					</td>
				</tr>
				<tr>
					<td class="tit">户口类型</td>
					<td>
						<form:input path="registerKind" htmlEscape="false" maxlength="255" class="input-xlarge "/>
					</td>
					<td class="tit">工会组织</td>
					<td>
						<form:input path="organization" htmlEscape="false" maxlength="255" class="input-xlarge "/>
					</td>
				</tr>
				<tr>
					<td class="tit">参加工作时间</td>
					<td>
						<input name="partInJobTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
							onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>	
					</td>
					<td class="tit">户口所在地行政区别</td>
					<td>
						<form:input path="registerPlace" htmlEscape="false" maxlength="255" class="input-xlarge "/>
					</td>
				</tr>
				<tr>
					<td class="tit">家庭月收入</td>
					<td>
						<form:input path="salaryFamliy" htmlEscape="false" class="input-xlarge "/>
					</td>
					<td class="tit">个人月收入</td>
					<td>
						<form:input path="salaryPerson" htmlEscape="false" class="input-xlarge "/>
					</td>
				</tr>
					<tr>
					<td class="tit">是否农民工</td>
					<td>
						<form:radiobuttons path="farmersAndHerdsmen" items="${fns:getDictList('yes_no')}" itemLabel="label" itemValue="value" htmlEscape="false" class=""/>
					</td>
					<td class="tit">是否参加医保</td>
					<td>
						<form:radiobuttons path="medicalInsurance" items="${fns:getDictList('yes_no')}" itemLabel="label" itemValue="value" htmlEscape="false" class=""/>
					</td>
				</tr>
				</tr>
					<tr>
					<td class="tit">是否劳模先进</td>
					<td>
						<form:radiobuttons path="advanced" items="${fns:getDictList('yes_no')}" itemLabel="label" itemValue="value" htmlEscape="false" class=""/>
					</td>
					<td class="tit">是否五一劳动</td>
					<td>
						<form:radiobuttons path="labor" items="${fns:getDictList('yes_no')}" itemLabel="label" itemValue="value" htmlEscape="false" class=""/>
					</td>
				</tr>
				<tr>
					<td class="tit">是否享受低保</td>
					<td>
						<form:radiobuttons path="subsistenceAllowances" items="${fns:getDictList('yes_no')}" itemLabel="label" itemValue="value" htmlEscape="false" class=""/>
					</td>
					<td class="tit">收入来源</td>
					<td>
						<form:select path="incomeSourceKey" class="input-xlarge">
							<form:option value="" label="请选择"/>
							<form:options items="${fns:getDictList('incomeSourceKey')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
						</form:select>						
					</td>
				</tr>
				<tr>
					<td class="tit">备注</td>
					<td colspan="5">
						<form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="255" class="input-xxlarge "/>
					</td>
				</tr>
			</table>
		</fieldset>
		<div class="form-actions">
			<shiro:hasPermission name="userinfo:userinfo:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>