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
 */

package com.liferay.google.action;

import com.liferay.google.GoogleOAuth;
import com.liferay.portal.kernel.events.ActionException;
import com.liferay.portal.kernel.events.SimpleAction;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.User;
import com.liferay.portlet.expando.DuplicateTableNameException;
import com.liferay.portlet.expando.model.ExpandoColumn;
import com.liferay.portlet.expando.model.ExpandoColumnConstants;
import com.liferay.portlet.expando.model.ExpandoTable;
import com.liferay.portlet.expando.model.ExpandoTableConstants;
import com.liferay.portlet.expando.service.ExpandoColumnLocalServiceUtil;
import com.liferay.portlet.expando.service.ExpandoTableLocalServiceUtil;

/**
 * @author Sergio González
 */
public class AddGoogleExpandoFieldsAction extends SimpleAction {

	@Override
	public void run(String[] ids) throws ActionException {
		try {
			doRun(GetterUtil.getLong(ids[0]));
		}
		catch (Exception e) {
			throw new ActionException(e);
		}
	}

	protected void addColumn(
			long tableId, String name, UnicodeProperties properties)
		throws PortalException, SystemException {

		ExpandoColumn column = ExpandoColumnLocalServiceUtil.getColumn(
			tableId, name);

		if (column != null) {
			return;
		}

		ExpandoColumn expandoColumn =
			ExpandoColumnLocalServiceUtil.addColumn(
				tableId, name, ExpandoColumnConstants.STRING);

		ExpandoColumnLocalServiceUtil.updateTypeSettings(
			expandoColumn.getColumnId(), properties.toString());
	}

	protected void doRun(long companyId) throws Exception {
		ExpandoTable expandoTable = null;

		// Add expando fields for storing User personal tokens
		
		try {
			expandoTable = ExpandoTableLocalServiceUtil.addTable(
				companyId, User.class.getName(),
				ExpandoTableConstants.DEFAULT_TABLE_NAME);
		}
		catch (Exception e) {
			expandoTable = ExpandoTableLocalServiceUtil.getTable(
				companyId, User.class.getName(),
				ExpandoTableConstants.DEFAULT_TABLE_NAME);
		}

		UnicodeProperties properties = new UnicodeProperties();

		properties.setProperty("hidden", "true");
		properties.setProperty("visible-with-update-permission", "false");

		addColumn(
			expandoTable.getTableId(), GoogleOAuth.GOOGLE_ACCESS_TOKEN,
			properties);
		addColumn(
			expandoTable.getTableId(), GoogleOAuth.GOOGLE_REFRESH_TOKEN,
			properties);
		addColumn(
			expandoTable.getTableId(), GoogleOAuth.GOOGLE_USER_ID, properties);
		
		// Add expando field for storing site-specific google login OAuth json token
		
		properties = new UnicodeProperties();

		properties.setProperty("visible-with-update-permission", "false");
		properties.setProperty("height", "105");
		properties.setProperty("width", "450");
		
		expandoTable = null;
		
		try {
			expandoTable = ExpandoTableLocalServiceUtil.addTable(
				companyId, Group.class.getName(),
				ExpandoTableConstants.DEFAULT_TABLE_NAME);
		} catch (DuplicateTableNameException ex) {
			expandoTable = ExpandoTableLocalServiceUtil.getTable(
				companyId, Group.class.getName(),
				ExpandoTableConstants.DEFAULT_TABLE_NAME);
		}
		
		addColumn(
			expandoTable.getTableId(), GoogleOAuth.GOOGLE_SITE_CLIENT_ID,
			properties);
		addColumn(
			expandoTable.getTableId(), GoogleOAuth.GOOGLE_SITE_CLIENT_SECRET,
			properties);
	}

}