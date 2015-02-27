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
import eu.ueb.acem.web.viewbeans.Pickable;

/**
 * @author Grégoire Colbert
 * @since 2014-02-25
 * 
 */
public interface OrganisationViewBean extends Pickable, Comparable<OrganisationViewBean> {

	@Override
	Long getId();

	String getName();

	void setName(String name);

	String getShortname();

	void setShortname(String name);

	String getIconFileName();

	void setIconFileName(String iconFileName);

	String getContactMode();

	void setContactMode(String contactMode);

	Organisation getDomainBean();

	void setDomainBean(Organisation organisation);

}
