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
package eu.ueb.acem.web.viewbeans.jaune;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import eu.ueb.acem.domain.beans.jaune.ResourceCategory;
import eu.ueb.acem.web.viewbeans.AbstractViewBean;
import eu.ueb.acem.web.viewbeans.Pickable;
import eu.ueb.acem.web.viewbeans.bleu.PedagogicalScenarioViewBean;

/**
 * @author Grégoire Colbert
 * @since 2014-04-22
 * 
 */
public class ToolCategoryViewBean extends AbstractViewBean implements Serializable, Pickable,
		Comparable<ToolCategoryViewBean> {

	/**
	 * For serialization.
	 */
	private static final long serialVersionUID = -116654020465612191L;

	@SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory.getLogger(ToolCategoryViewBean.class);

	private ResourceCategory resourceCategory;

	private List<ResourceViewBean> resourceViewBeans;

	private List<PedagogicalScenarioViewBean> pedagogicalScenarioViewBeans;

	private String name;

	private Boolean favoriteToolCategory;

	private String iconFileName;

	private String description;

	public ToolCategoryViewBean() {
		resourceViewBeans = new ArrayList<ResourceViewBean>();
		pedagogicalScenarioViewBeans = new ArrayList<PedagogicalScenarioViewBean>();
	}

	public ToolCategoryViewBean(ResourceCategory toolCategory) {
		this();
		setResourceCategory(toolCategory);
	}

	public ResourceCategory getDomainBean() {
		return resourceCategory;
	}

	public void setDomainBean(ResourceCategory toolCategory) {
		setResourceCategory((ResourceCategory) toolCategory);
	}

	public ResourceCategory getResourceCategory() {
		return resourceCategory;
	}

	public void setResourceCategory(ResourceCategory toolCategory) {
		this.resourceCategory = toolCategory;
		setId(toolCategory.getId());
		setName(toolCategory.getName());
		setIconFileName(toolCategory.getIconFileName());
		setDescription(toolCategory.getDescription());
	}

	public List<ResourceViewBean> getResourceViewBeans() {
		return resourceViewBeans;
	}

	public List<PedagogicalScenarioViewBean> getScenarioViewBeans() {
		return pedagogicalScenarioViewBeans;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
		getDomainBean().setName(name);
	}

	public Boolean getFavoriteToolCategory() {
		return favoriteToolCategory;
	}

	public void setFavoriteToolCategory(Boolean favoriteToolCategory) {
		this.favoriteToolCategory = favoriteToolCategory;
	}

	public String getIconFileName() {
		return iconFileName;
	}

	public void setIconFileName(String iconFileName) {
		this.iconFileName = iconFileName;
		getDomainBean().setIconFileName(iconFileName);
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
		getDomainBean().setDescription(description);
	}

	@Override
	public int compareTo(ToolCategoryViewBean o) {
		return name.compareTo(o.getName());
	}

}
