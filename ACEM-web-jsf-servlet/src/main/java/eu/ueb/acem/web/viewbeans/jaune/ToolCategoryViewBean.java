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
import java.util.ArrayList;
import java.util.List;

import eu.ueb.acem.domain.beans.jaune.Applicatif;
import eu.ueb.acem.domain.beans.jaune.DocumentationApplicatif;
import eu.ueb.acem.domain.beans.jaune.Equipement;
import eu.ueb.acem.domain.beans.jaune.FormationProfessionnelle;
import eu.ueb.acem.domain.beans.jaune.ResourceCategory;
import eu.ueb.acem.domain.beans.jaune.Ressource;
import eu.ueb.acem.domain.beans.jaune.RessourcePedagogiqueEtDocumentaire;
import eu.ueb.acem.web.viewbeans.Pickable;

/**
 * @author Grégoire Colbert
 * @since 2014-04-22
 * 
 */
public class ToolCategoryViewBean implements Serializable, Pickable, Comparable<ToolCategoryViewBean> {

	private static final long serialVersionUID = -116654020465612191L;

	private ResourceCategory resourceCategory;

	private List<ResourceViewBean> resourceViewBeans;
	
	private Long id;

	private String name;
	
	private Boolean favoriteToolCategory;
	
	private String iconFileName;
	
	private String description;

	public ToolCategoryViewBean() {
		resourceViewBeans = new ArrayList<ResourceViewBean>();
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
		resourceViewBeans.clear();
		for (Ressource resource : toolCategory.getResources()) {
			if (resource instanceof Applicatif) {
				addResourceViewBean(new SoftwareViewBean((Applicatif)resource));
			}
			else if (resource instanceof Equipement) {
				addResourceViewBean(new EquipmentViewBean((Equipement)resource));
			}
			else if (resource instanceof FormationProfessionnelle) {
				addResourceViewBean(new ProfessionalTrainingViewBean((FormationProfessionnelle)resource));
			}
			else if (resource instanceof DocumentationApplicatif) {
				addResourceViewBean(new SoftwareDocumentationViewBean((DocumentationApplicatif)resource));
			}
			else if (resource instanceof RessourcePedagogiqueEtDocumentaire) {
				addResourceViewBean(new DocumentaryAndPedagogicalResourceViewBean((RessourcePedagogiqueEtDocumentaire)resource));
			}
		}
		setIconFileName(toolCategory.getIconFileName());
		setDescription(toolCategory.getDescription());
	}
	
	public List<ResourceViewBean> getResourceViewBeans() {
		return resourceViewBeans;
	}

	public void addResourceViewBean(ResourceViewBean resourceViewBean) {
		resourceViewBeans.add(resourceViewBean);
	}

	public void removeResourceViewBean(ResourceViewBean resourceViewBean) {
		resourceViewBeans.remove(resourceViewBean);
	}
	
	@Override
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ToolCategoryViewBean other = (ToolCategoryViewBean) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		}
		else
			if (!id.equals(other.id))
				return false;
		if (name == null) {
			if (other.name != null)
				return false;
		}
		else
			if (!name.equals(other.name))
				return false;
		return true;
	}
	
}
