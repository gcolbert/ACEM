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

import eu.ueb.acem.domain.beans.jaune.Applicatif;
import eu.ueb.acem.domain.beans.jaune.Ressource;

/**
 * @author Grégoire Colbert
 * @since 2014-02-25
 * 
 */
public class SoftwareViewBean implements ResourceViewBean, Serializable, Comparable<SoftwareViewBean> {

	private static final long serialVersionUID = -116654020465612191L;

	private Applicatif software;

	private Long id;

	private String name;

	//private Boolean favoriteResource;
	
	private String iconFileName;
	
	private String description;
	
	public SoftwareViewBean() {
	}

	public SoftwareViewBean(Applicatif software) {
		this();
		setSoftware(software);
	}

	@Override
	public Applicatif getDomainBean() {
		return software;
	}
	
	@Override
	public void setDomainBean(Ressource resource) {
		setSoftware((Applicatif) resource);
	}

	public Applicatif getSoftware() {
		return software;
	}

	public void setSoftware(Applicatif software) {
		this.software = software;
		setId(software.getId());
		setName(software.getName());
		setIconFileName(software.getIconFileName());
		setDescription(software.getDescription());
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

	/*-
	@Override
	public Boolean getFavoriteResource() {
		return favoriteResource;
	}
	
	@Override
	public void setFavoriteResource(Boolean favoriteResource) {
		this.favoriteResource = favoriteResource;
	}
	*/
	
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
	public int compareTo(SoftwareViewBean o) {
		return name.compareTo(o.getName());
	}

}
