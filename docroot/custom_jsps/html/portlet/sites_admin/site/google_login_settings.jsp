<%--
/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 *
 */
--%>

<%@ include file="/html/portlet/sites_admin/init.jsp" %>

<liferay-ui:error-marker key="errorSection" value="google-login-settings" />

<h3><liferay-ui:message key="google-login-settings" /></h3>

<%
	Group liveGroup = (Group)request.getAttribute("site.liveGroup");
%>

<div class="alert alert-info">
		<liferay-ui:message key="set-login-credentials-for-current-site-or-go-to-portal-settings" />
	</div>

<liferay-ui:custom-attribute
	className="<%= Group.class.getName() %>"
	classPK="<%= liveGroup.getGroupId() %>"
	editable="true"
	label="true"
	name="googleSiteClientId"
/>

<liferay-ui:custom-attribute
	className="<%= Group.class.getName() %>"
	classPK="<%= liveGroup.getGroupId() %>"
	editable="true"
	label="true"
	name="googleSiteClientSecret"
/>