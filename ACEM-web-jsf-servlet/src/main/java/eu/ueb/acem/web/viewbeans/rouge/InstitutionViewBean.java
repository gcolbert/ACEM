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

import java.io.Serializable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import eu.ueb.acem.domain.beans.rouge.Institution;
import eu.ueb.acem.domain.beans.rouge.Organisation;
import eu.ueb.acem.web.viewbeans.AbstractViewBean;

/**
 * @author Grégoire Colbert
 * @since 2014-02-25
 * 
 */
public class InstitutionViewBean extends AbstractViewBean implements OrganisationViewBean, Serializable,
		Comparable<InstitutionViewBean> {

	/**
	 * For serialization.
	 */
	private static final long serialVersionUID = 6170498010377898612L;

	@SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory.getLogger(InstitutionViewBean.class);

	private Institution institution;

	private String name;

	private String shortname;

	private String iconFileName;

	public InstitutionViewBean() {
	}

	public InstitutionViewBean(Institution institution) {
		this();
		setInstitution(institution);
	}

	public Institution getInstitution() {
		return institution;
	}

	public void setInstitution(Institution institution) {
		this.institution = institution;
		setId(institution.getId());
		setName(institution.getName());
		setShortname(institution.getShortname());
		setIconFileName(institution.getIconFileName());
	}

	@Override
	public Organisation getDomainBean() {
		return getInstitution();
	}

	@Override
	public void setDomainBean(Organisation organisation) {
		setInstitution((Institution) organisation);
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
	public int compareTo(InstitutionViewBean o) {
		return name.compareTo(o.getName());
	}

}
