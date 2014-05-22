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

import java.util.HashMap;
import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import eu.ueb.acem.domain.beans.jaune.ResourceCategory;

/**
 * @author Grégoire Colbert
 * @since 2014-04-10
 */
@FacesConverter("ResourceCategoryConverter")
public class ResourceCategoryConverter implements Converter {

	@SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory.getLogger(ResourceCategoryConverter.class);

    private static final String KEY = "eu.ueb.acem.web.utils.ResourceCategoryConverter";
	
    private Map<String, Object> getViewMap(FacesContext context) {
        Map<String, Object> viewMap = context.getViewRoot().getViewMap();
        @SuppressWarnings({ "unchecked", "rawtypes" })
        Map<String, Object> idMap = (Map) viewMap.get(KEY);
        if (idMap == null) {
            idMap = new HashMap<String, Object>();
            viewMap.put(KEY, idMap);
        }
        return idMap;
    }
	
	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if (value == null || value.isEmpty()) {
            return null;
        }

        if (!value.matches("\\d+")) {
            throw new ConverterException("The value is not a valid ID number: " + value);
        }

        return getViewMap(context).get(value);
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value == "") {
			return null;
		}
		else {
            getViewMap(context).put(((ResourceCategory) value).getId().toString(), value);
			return ((ResourceCategory) value).getId().toString();
		}
	}

}
