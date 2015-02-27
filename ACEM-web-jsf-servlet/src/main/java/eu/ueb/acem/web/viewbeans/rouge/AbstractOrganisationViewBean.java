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
package eu.ueb.acem.web.viewbeans.rouge;

import eu.ueb.acem.domain.beans.rouge.Organisation;
import eu.ueb.acem.web.viewbeans.AbstractViewBean;

/**
 * @author Grégoire Colbert
 * @since 2015-01-30
 * 
 */
public abstract class AbstractOrganisationViewBean<E extends Organisation> extends AbstractViewBean implements OrganisationViewBean {

	private E domainBean;

	private String name;

	private String shortname;

	private String iconFileName;

	private String contactMode;

	@Override
	public E getDomainBean() {
		return domainBean;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void setDomainBean(Organisation organisation) {
		this.domainBean = (E) organisation;
		setId(organisation.getId());
		setName(organisation.getName());
		setShortname(organisation.getShortname());
		setIconFileName(organisation.getIconFileName());
		setContactMode(organisation.getContactMode());
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String getShortname() {
		return shortname;
	}

	@Override
	public void setShortname(String shortname) {
		this.shortname = shortname;
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
	public String getContactMode() {
		return contactMode;
	}

	@Override
	public void setContactMode(String contactMode) {
		this.contactMode = contactMode;
	}

	@Override
	public int compareTo(OrganisationViewBean o) {
		return name.compareTo(o.getName());
	}

}
