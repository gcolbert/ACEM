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
package eu.ueb.acem.web.viewbeans.gris;

import java.util.List;

import eu.ueb.acem.domain.beans.gris.Person;
import eu.ueb.acem.web.viewbeans.Pickable;
import eu.ueb.acem.web.viewbeans.jaune.ToolCategoryViewBean;
import eu.ueb.acem.web.viewbeans.rouge.OrganisationViewBean;

/**
 * @author Grégoire Colbert
 * @since 2014-02-25
 * 
 */
public interface PersonViewBean extends Pickable, Comparable<PersonViewBean> {

	Person getDomainBean();

	void setDomainBean(Person domainBean);

	List<OrganisationViewBean> getOrganisationViewBeans();

	String getName();

	void setName(String name);

	String getLogin();

	void setLogin(String login);

	String getPassword();

	void setPassword(String password);

	String getEmail();

	void setEmail(String email);
	
	String getLanguage();

	void setLanguage(String language);

	Boolean getAdministrator();

	void setAdministrator(Boolean administrator);

	Boolean getTeacher();

	void setTeacher(Boolean teacher);

	List<ToolCategoryViewBean> getFavoriteToolCategoryViewBeans();

}
