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

import eu.ueb.acem.domain.beans.rouge.Institution;
import eu.ueb.acem.domain.beans.rouge.Organisation;

/**
 * @author Grégoire Colbert
 * @since 2014-02-25
 * 
 */
public class InstitutionViewBean extends AbstractOrganisationViewBean implements Serializable {

	/**
	 * For serialization.
	 */
	private static final long serialVersionUID = 6170498010377898612L;

	private Institution domainBean;

	public InstitutionViewBean() {
	}

	public InstitutionViewBean(Institution domainBean) {
		this();
		setDomainBean(domainBean);
	}

	@Override
	public Institution getDomainBean() {
		return domainBean;
	}

	@Override
	public void setDomainBean(Organisation domainBean) {
		setDomainBean((Institution) domainBean);
	}

	public void setDomainBean(Institution domainBean) {
		this.domainBean = domainBean;
		super.setDomainBean(domainBean);
	}

}
