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
package eu.ueb.acem.web.utils.spring;

import java.util.Map;

import javax.faces.context.FacesContext;

import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.config.Scope;

/**
 * This class enables the Scope("view") annotation for Spring.
 * 
 * @author Grégoire Colbert
 * @since 2014-02-25
 */
public class ViewScope implements Scope {

	private Map<String, Object> getViewMap() {
		FacesContext currentInstance = FacesContext.getCurrentInstance();
		if (currentInstance != null && currentInstance.getViewRoot() != null)
			return currentInstance.getViewRoot().getViewMap();
		return null;
	}

	@Override
	public Object get(String name, ObjectFactory<?> objectFactory) {
		Map<String, Object> viewMap = getViewMap();

		if (viewMap == null)
			return objectFactory.getObject();
		if (viewMap.containsKey(name)) {
			return viewMap.get(name);
		} else {
			Object object = objectFactory.getObject();
			viewMap.put(name, object);
			return object;
		}
	}

	public Object remove(String name) {
		Map<String, Object> viewMap = getViewMap();
		if (viewMap == null)
			return null;
		return FacesContext.getCurrentInstance().getViewRoot().getViewMap().remove(name);
	}

	public String getConversationId() {
		return null;
	}

	public void registerDestructionCallback(String name, Runnable callback) {
	}

	public Object resolveContextualObject(String key) {
		return null;
	}

}