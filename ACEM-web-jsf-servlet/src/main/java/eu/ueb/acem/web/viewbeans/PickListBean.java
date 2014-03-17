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
 * A view bean for <a
 * href="http://www.primefaces.org/showcase/ui/picklist.jsf">PrimeFaces PickList
 * component</a>.
 * 
 * @author Grégoire Colbert
 * @since 2014-01-10
 * @see <a href="http://www.primefaces.org/showcase/ui/picklist.jsf">PrimeFaces
 *      PickList component</a>
 */
@Component("pickListBean")
@Scope("view")
public class PickListBean implements Serializable {

	private static final long serialVersionUID = 5863029035918765493L;

	private DualListModel<Pickable> pickListEntities;

	public PickListBean() {
		pickListEntities = new DualListModel<Pickable>();
		pickListEntities.setSource(new ArrayList<Pickable>());
		pickListEntities.setTarget(new ArrayList<Pickable>());
	}

	public DualListModel<Pickable> getPickListEntities() {
		return pickListEntities;
	}

	public void setPickListEntities(DualListModel<Pickable> pickListEntities) {
		this.pickListEntities = pickListEntities;
	}

	public void addSourceEntity(Pickable entity) {
		pickListEntities.getSource().add(entity);
	}

	public void addTargetEntity(Pickable entity) {
		pickListEntities.getTarget().add(entity);
	}

}
