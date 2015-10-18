/**
 *     Copyright Université Européenne de Bretagne 2012-2015
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
package eu.ueb.acem.dal.common.jaune;

import eu.ueb.acem.dal.common.DAO;
import eu.ueb.acem.domain.beans.jaune.ResourceCategory;

/**
 * This interface describes the methods expected from DAO instances that take
 * care of beans that implement the ResourceCategory interface.
 * 
 * @param <ID>
 *            The type of the id property of objects stored in the database
 *            (typically java.lang.Long)
 * 
 * @author Grégoire Colbert
 * @since 2015-05-21
 * 
 */
public interface ResourceCategoryDAO<ID> extends DAO<ID, ResourceCategory> {

	/**
	 * Creates a new ResourceCategory with the given name, description and icon.
	 * 
	 * @param name
	 *            A name for the ResourceCategory
	 * @param description
	 *            A description for the ResourceCategory
	 * @param iconFileName
	 *            A icon file name for the ResourceCategory, including a
	 *            relative path starting after the base directory defined in the
	 *            "images.path" configuration property
	 * @return the newly created ResourceCategory
	 */
	ResourceCategory create(String name, String description, String iconFileName);

}
