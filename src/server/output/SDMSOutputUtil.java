/*
Copyright (c) 2000-2013 "independIT Integrative Technologies GmbH",
Authors: Ronald Jeninga, Dieter Stubler

schedulix Enterprise Job Scheduling System

independIT Integrative Technologies GmbH [http://www.independit.de]
mailto:contact@independit.de

This file is part of schedulix

schedulix is free software:
you can redistribute it and/or modify it under the terms of the
GNU Affero General Public License as published by the
Free Software Foundation, either version 3 of the License,
or (at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License
along with this program. If not, see <http://www.gnu.org/licenses/>.
*/
package de.independit.scheduler.server.output;

import java.lang.*;
import java.util.*;

import de.independit.scheduler.server.output.*;

public class SDMSOutputUtil
{

	public final static String __version = "@(#) $Id: SDMSOutputUtil.java,v 2.2.12.1 2013/03/14 10:24:18 ronald Exp $";

	public static String getFromRecord(SDMSOutput o, String label)
	{
		Vector record = (Vector)(o.container.dataset.elementAt(0));
		int i = index(o.container, label);
		if (i == -1) return null;
		return (record.elementAt(i)).toString();
	}

	public static int getTableLength(SDMSOutput o)
	{
		return getTableLength(o, null);
	}

	public static int getTableLength(SDMSOutput o, String tableName)
	{
		SDMSOutputContainer tableContainer = getTable(o, tableName);
		if (tableContainer == null)
			return -1;
		return tableContainer.lines;
	}

	public static String getFromTable(SDMSOutput o, int line, String label)
	{
		return getFromTable(o, null, line, label);
	}

	public static String getFromTable(SDMSOutput o, String tableName, int line, String label)
	{
		SDMSOutputContainer tableContainer = getTable(o, tableName);
		if (tableContainer == null)
			return null;
		Vector record = (Vector)(tableContainer.dataset.elementAt(line));
		int i = index(tableContainer, label);
		if (i == -1) return null;
		return (record.elementAt(i)).toString();
	}

	public static SDMSOutputContainer getTable(SDMSOutput o, String tableName)
	{
		if (o.container.columns == 0) {
			Vector record = (Vector)(o.container.dataset.elementAt(0));
			int i = index(o.container, tableName);
			if (i == -1)
				return null;
			SDMSOutputContainer tableContainer = null;
			try {
				return (SDMSOutputContainer)(record.elementAt(i));
			} catch (Exception e) {
				return null;
			}
		} else {
			if (tableName != null)
				return null;
			return o.container;
		}
	}

	public static int index(SDMSOutputContainer c, String label)
	{
		SDMSOutputLabel l;
		Iterator i = c.desc.iterator();
		int idx = 0;
		while (i.hasNext()) {
			l = (SDMSOutputLabel)i.next();
			if (l.name.equals(label)) {
				return idx;
			}
			idx ++;
		}
		return -1;
	}
}
