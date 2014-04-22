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
package eu.ueb.acem.web.viewbeans.jaune;

import java.io.Serializable;

import eu.ueb.acem.domain.beans.jaune.ResourceCategory;
import eu.ueb.acem.web.viewbeans.Pickable;

/**
 * @author Grégoire Colbert
 * @since 2014-04-22
 * 
 */
public class ToolCategoryViewBean implements Serializable, Pickable, Comparable<ToolCategoryViewBean> {

	private static final long serialVersionUID = -116654020465612191L;

	private ResourceCategory resourceCategory;

	private Long id;

	private String name;

	public ToolCategoryViewBean() {
	}

	public ToolCategoryViewBean(ResourceCategory resourceCategory) {
		this();
		setResourceCategory(resourceCategory);
	}

	public ResourceCategory getDomainBean() {
		return resourceCategory;
	}
	
	public void setDomainBean(ResourceCategory resourceCategory) {
		setResourceCategory((ResourceCategory) resourceCategory);
	}

	public ResourceCategory getResourceCategory() {
		return resourceCategory;
	}

	public void setResourceCategory(ResourceCategory resourceCategory) {
		this.resourceCategory = resourceCategory;
		setId(resourceCategory.getId());
		setName(resourceCategory.getName());
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public int compareTo(ToolCategoryViewBean o) {
		return name.compareTo(o.getName());
	}

}
