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

import eu.ueb.acem.domain.beans.jaune.DocumentationApplicatif;
import eu.ueb.acem.domain.beans.jaune.Ressource;

/**
 * @author Grégoire Colbert
 * @since 2014-03-19
 * 
 */
public class SoftwareDocumentationViewBean implements ResourceViewBean, Serializable, Comparable<SoftwareDocumentationViewBean> {

	private static final long serialVersionUID = 2189588973638154852L;

	private DocumentationApplicatif softwareDocumentation;

	private Long id;

	private String name;

	private Boolean favoriteResource;
	
	private String iconFileName;
	
	private String description;
	
	public SoftwareDocumentationViewBean() {
	}

	public SoftwareDocumentationViewBean(DocumentationApplicatif softwareDocumentation) {
		this();
		setSoftwareDocumentation(softwareDocumentation);
	}

	@Override
	public DocumentationApplicatif getDomainBean() {
		return softwareDocumentation;
	}
	
	@Override
	public void setDomainBean(Ressource resource) {
		setSoftwareDocumentation((DocumentationApplicatif) resource);
	}

	public DocumentationApplicatif getSoftwareDocumentation() {
		return softwareDocumentation;
	}

	public void setSoftwareDocumentation(DocumentationApplicatif softwareDocumentation) {
		this.softwareDocumentation = softwareDocumentation;
		setId(softwareDocumentation.getId());
		setName(softwareDocumentation.getName());
		setIconFileName(softwareDocumentation.getIconFileName());
		setDescription(softwareDocumentation.getDescription());
	}

	@Override
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public Boolean getFavoriteResource() {
		return favoriteResource;
	}
	
	@Override
	public void setFavoriteResource(Boolean favoriteResource) {
		this.favoriteResource = favoriteResource;
	}
	
	@Override
	public String getIconFileName() {
		return iconFileName;
	}

	@Override
	public void setIconFileName(String iconFileName) {
		this.iconFileName = iconFileName;
	}

	@Override
	public String getDescription() {
		return description;
	}

	@Override
	public void setDescription(String description) {
		this.description = description;
	}
	
	@Override
	public int compareTo(SoftwareDocumentationViewBean o) {
		return name.compareTo(o.getName());
	}
	
}
