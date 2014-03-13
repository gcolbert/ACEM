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

import java.util.Collection;
import java.util.Collections;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * @author Grégoire Colbert @since 2014-03-06
 * @param <E>
 * 
 */
@Component("sortableTableBean")
@Scope("view")
public class SortableTableBean<E extends Comparable<E>> extends TableBean<E> {

	private static final long serialVersionUID = -5189856159830582038L;

	public void sort() {
		Collections.sort(tableEntries);
	}

	public void sortReverseOrder() {
		Collections.sort(tableEntries, Collections.reverseOrder());
	}

	@Override
	public void setTableEntries(Collection<E> collectionEntities) {
		super.setTableEntries(collectionEntities);
		sortReverseOrder();
	}
	
}
