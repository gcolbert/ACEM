/**
 *     Copyright Grégoire COLBERT 2013
 * 
 *     This file is part of Atelier de Création d'Enseignement Multimodal (ACEM).
 * 
 *     ACEM is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 * 
 *     ACEM is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 * 
 *     You should have received a copy of the GNU General Public License
 *     along with ACEM.  If not, see <http://www.gnu.org/licenses/>
 */
package eu.ueb.acem.web.viewbeans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * @author Grégoire Colbert @since 2014-01-10
 *
 */
@Component("tableBean")
@Scope("view")
public class TableBean  implements Serializable {

	/**
	 * For Logging.
	 */
	@SuppressWarnings("unused")
	private final static Logger logger = LoggerFactory.getLogger(TableBean.class);

	private static final long serialVersionUID = -3164178023755035995L;

	private List<TableEntry> tableEntries;

	public TableBean() {
		tableEntries = new ArrayList<TableEntry>();

		List<String> scenario1resources = new ArrayList<String>();
		scenario1resources.add("Tableau blanc interactif");
		TableEntry scenario1 = new TableEntry("Scénario de test n°1", "Grégoire Colbert", scenario1resources);
		tableEntries.add(scenario1);

		List<String> scenario2resources = new ArrayList<String>();
		scenario2resources.add("Tableau blanc interactif");
		scenario2resources.add("Boitiers de vote");
		TableEntry scenario2 = new TableEntry("Scénario de test n°2", "Grégoire Colbert", scenario2resources);
		tableEntries.add(scenario2);
	}

	public List<TableEntry> getTableEntries() {
		return tableEntries;
	}

	public void setTableEntries(List<TableEntry> tableEntries) {
		this.tableEntries = tableEntries;
	}
	
	public static class TableEntry implements Serializable {

		private static final long serialVersionUID = 5305081640505801043L;

		private String title;

		private String author;

		private List<String> resources;

		public TableEntry(String title, String author, List<String> resources) {
			this.title = title;
			this.author = author;
			this.resources = resources;
		}
		
		public String getTitle() {
			return title;
		}

		public void setTitle(String title) {
			this.title = title;
		}

		public String getAuthor() {
			return author;
		}

		public void setAuthor(String author) {
			this.author = author;
		}

		public List<String> getResources() {
			return resources;
		}

		public void setResources(List<String> resources) {
			this.resources = resources;
		}
	}

}
