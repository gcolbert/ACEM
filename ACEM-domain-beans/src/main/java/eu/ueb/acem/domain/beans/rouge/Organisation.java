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
package eu.ueb.acem.domain.beans.rouge;

import java.io.Serializable;
import java.util.Set;

import eu.ueb.acem.domain.beans.jaune.Resource;
import eu.ueb.acem.domain.beans.jaune.UseMode;
import eu.ueb.acem.domain.beans.vert.PhysicalSpace;

/**
 * @author Grégoire Colbert
 * @since 2013-11-20
 * 
 */
public interface Organisation extends Serializable, Comparable<Organisation> {

	Long getId();

	/**
	 * The id of the original object in the information system, if any.
	 * @return the id of the original object, or null.
	 */
	Long getIdSource();

	void setIdSource(Long idSource);

	String getName();

	void setName(String name);

	String getShortname();

	void setShortname(String shortname);

	String getDescription();

	void setDescription(String description);

	String getIconFileName();

	void setIconFileName(String iconFileName);

	String getContactMode();

	void setContactMode(String contactMode);

	Set<Resource> getPossessedResources();

	void setPossessedResources(Set<Resource> possessedResources);

	Set<Resource> getSupportedResources();

	void setSupportedResources(Set<Resource> supportedResources);

	Set<Resource> getViewedResources();

	void setViewedResources(Set<Resource> viewedResources);

	Set<UseMode> getUseModes();

	void setUseModes(Set<UseMode> useModes);

	Set<PhysicalSpace> getOccupiedPhysicalSpaces();

	void setOccupiedPhysicalSpaces(Set<PhysicalSpace> occupiedPhysicalSpaces);

}
