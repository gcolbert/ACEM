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
package eu.ueb.acem.web.utils;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.primefaces.component.picklist.PickList;
import org.primefaces.model.DualListModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import eu.ueb.acem.web.viewbeans.Pickable;

/**
 * A converter implementation for <a
 * href="http://www.primefaces.org/showcase/ui/picklist.jsf">PrimeFaces PickList
 * component</a>. It must be referenced using the converter attribute of the
 * p:pickList element with a value equal to "PickListConverter" (in other words,
 * use &lt;p:pickList converter="PickListConverter" .../&gt;). The view beans
 * must implement the {@link Pickable} interface for this converter to work.
 * 
 * @author Grégoire Colbert
 * @since 2014-02-25
 * @see <a href="http://www.primefaces.org/showcase/ui/picklist.jsf">PrimeFaces
 *      PickList component</a>
 */
@FacesConverter("PickListConverter")
public class PickListConverter implements Converter {

	@SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory.getLogger(PickListConverter.class);

	public Object getAsObject(FacesContext facesContext, UIComponent component, String submittedValue) {
		Object ret = null;
		if (component instanceof PickList) {
			Object dualList = ((PickList) component).getValue();
			@SuppressWarnings("unchecked")
			DualListModel<Pickable> dl = (DualListModel<Pickable>) dualList;
			for (Object o : dl.getSource()) {
				String id = "";
				if (o instanceof Pickable) {
					id += ((Pickable) o).getId();
				}
				if (submittedValue.equals(id)) {
					ret = o;
					break;
				}
			}
			if (ret == null)
				for (Object o : dl.getTarget()) {
					String id = "";
					if (o instanceof Pickable) {
						id += ((Pickable) o).getId();
					}
					if (submittedValue.equals(id)) {
						ret = o;
						break;
					}
				}
		}
		return ret;
	}

	public String getAsString(FacesContext facesContext, UIComponent component, Object value) {
		return ((Pickable) value).getId().toString();
	}

}
