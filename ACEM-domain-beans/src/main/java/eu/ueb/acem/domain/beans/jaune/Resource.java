/**
 *     Copyright Université Européenne de Bretagne 2012-2015
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
package eu.ueb.acem.domain.beans.jaune;

import java.io.Serializable;
import java.util.Set;

import eu.ueb.acem.domain.beans.bleu.TeachingMode;
import eu.ueb.acem.domain.beans.rouge.Organisation;

/**
 * @author Grégoire Colbert
 * @since 2013-11-20
 * 
 */
public interface Resource extends Serializable, Comparable<Resource> {

	Long getId();

	/**
	 * The id of the original object in the information system, if any.
	 * @return the id of the original object, or null.
	 */
	Long getIdSource();

	void setIdSource(Long idSource);

	/**
	 * Some resources can be used for a PedagogicalActivity (a mobile equipment,
	 * for example), whereas some other resources can be used for a
	 * PedagogicalSession (a special room, for example). This field is used to
	 * filter the various resources available when associating a resource with a
	 * PedagogicalActivity or with a PedagogicalSession.
	 * 
	 * @return true if the Resource can be used during an activity.
	 */
	boolean isForPedagogicalActivity();

	void setForPedagogicalActivity(boolean isForActivity);

	/**
	 * Some resources can be used for a PedagogicalActivity (a mobile equipment,
	 * for example), whereas some other resources can be used for a
	 * PedagogicalSession (a special room, for example). This field is used to
	 * filter the various resources available when associating a resource with a
	 * PedagogicalActivity or with a PedagogicalSession.
	 * 
	 * @return true if the Resource can be used during an activity.
	 */
	boolean isForPedagogicalSession();

	void setForPedagogicalSession(boolean isForSession);

	String getName();

	void setName(String name);

	String getDescription();

	void setDescription(String description);

	String getIconFileName();

	void setIconFileName(String iconFileName);

	Set<ResourceCategory> getCategories();

	void setCategories(Set<ResourceCategory> categories);

	Set<UseMode> getUseModes();

	void setUseModes(Set<UseMode> useModes);

	Organisation getOrganisationPossessingResource();

	void setOrganisationPossessingResource(Organisation organisation);

	Organisation getOrganisationSupportingResource();

	void setOrganisationSupportingResource(Organisation organisation);

	Set<Organisation> getOrganisationsHavingAccessToResource();

	void setOrganisationsHavingAccessToResource(Set<Organisation> organisations);

	Set<Documentation> getDocumentations();

	void setDocumentations(Set<Documentation> documentations);

	TeachingMode getTeachingMode();

	void setTeachingMode(TeachingMode teachingMode);

}
