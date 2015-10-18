/**
 *     Copyright Grégoire COLBERT 2015
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
import java.util.Collection;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * A simple view bean that allows to display domain view beans in a table.
 * 
 * @author Grégoire Colbert
 * @since 2014-01-10
 * @param <E>
 *            A domain view bean, chosen from the eu.ueb.acem.web.viewbeans.*
 *            packages
 * @see SortableTableBean An extended TableBean that allows sorting.
 * 
 */
@Component("tableBean")
@Scope("view")
public class TableBean<E> implements Serializable {

	@SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory.getLogger(TableBean.class);

	private static final long serialVersionUID = -3164178023755035995L;

	private List<E> tableEntries;

	public TableBean() {
		tableEntries = new ArrayList<E>();
	}

	public List<E> getTableEntries() {
		return tableEntries;
	}

	public void setTableEntries(Collection<E> collectionEntities) {
		tableEntries.clear();
		if (collectionEntities instanceof List<?>) {
			tableEntries = (List<E>) collectionEntities;
		}
		else {
			for (E entity : collectionEntities) {
				tableEntries.add(entity);
			}
		}
	}

}
