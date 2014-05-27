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

import java.util.List;

import eu.ueb.acem.domain.beans.jaune.Ressource;
import eu.ueb.acem.web.viewbeans.Pickable;
import eu.ueb.acem.web.viewbeans.rouge.OrganisationViewBean;

/**
 * @author Grégoire Colbert
 * @since 2014-02-25
 * 
 */
public interface ResourceViewBean extends Pickable {

	Long getId();

	String getName();

	Ressource getDomainBean();

	void setDomainBean(Ressource resource);

	String getDescription();

	void setDescription(String description);

	String getIconFileName();

	void setIconFileName(String iconFileName);
	
	OrganisationViewBean getOrganisationPossessingResourceViewBean();
	
	void setOrganisationPossessingResourceViewBean(OrganisationViewBean organisationViewBean);

	List<OrganisationViewBean> getOrganisationViewingResourceViewBeans();

	void addOrganisationViewingResourceViewBean(OrganisationViewBean organisationViewBean);

	void removeOrganisationViewingResourceViewBean(OrganisationViewBean organisationViewBean);
	
	UseModeViewBean getUseModeViewBean();
	
	void setUseModeViewBean(UseModeViewBean useModeViewBean);
	
	List<UseModeViewBean> getUseModeViewBeans();
	
	void setUseModeViewBeans(List<UseModeViewBean> useModeViewBeans);
}
