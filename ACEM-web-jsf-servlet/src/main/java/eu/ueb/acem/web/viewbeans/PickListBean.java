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
import org.primefaces.model.DualListModel;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * @author Grégoire Colbert @since 2014-01-10
 * @param <E>
 * 
 */
@Component("pickListBean")
@Scope("view")
public class PickListBean<E extends Comparable<E>> implements Serializable {

	private static final long serialVersionUID = 5863029035918765493L;

	private DualListModel<E> pickListEntities;

	public PickListBean() {
		pickListEntities = new DualListModel<E>();
		pickListEntities.setSource(new ArrayList<E>());
		pickListEntities.setTarget(new ArrayList<E>());
	}

	public DualListModel<E> getPickListEntities() {
		return pickListEntities;
	}

	public void setPickListEntities(DualListModel<E> pickListEntities) {
		this.pickListEntities = pickListEntities;
	}

	/*
	public void setLists(List<E> listSource, List<E> listTarget) {
		pickListEntities.setSource(listSource);
		pickListEntities.setTarget(listTarget);
	}
	*/

	public void addSourceEntity(E entity) {
		pickListEntities.getSource().add(entity);
	}

	public void addTargetEntity(E entity) {
		pickListEntities.getTarget().add(entity);
	}

}
