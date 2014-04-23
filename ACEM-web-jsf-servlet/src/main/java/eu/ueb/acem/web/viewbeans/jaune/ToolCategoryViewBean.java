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

	public ToolCategoryViewBean() {
		resourceViewBeans = new ArrayList<ResourceViewBean>();
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
		resourceViewBeans.clear();
		for (Ressource resource : resourceCategory.getResources()) {
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
	}

	@Override
	public int compareTo(ToolCategoryViewBean o) {
		return name.compareTo(o.getName());
	}

}
