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

import java.util.Set;

/**
 * The Institution domain bean interface.
 * 
 * @author Grégoire Colbert
 * @since 2013-11-20
 */
public interface Institution extends Organisation {

	Set<Community> getCommunities();

	void setCommunities(Set<Community> communities);

	Set<TeachingDepartment> getTeachingDepartments();

	void setTeachingDepartments(Set<TeachingDepartment> teachingDepartments);

	Set<AdministrativeDepartment> getAdministrativeDepartments();

	void setAdministrativeDepartments(Set<AdministrativeDepartment> administrativeDepartments);

	String getSupannEtablissement();

	void setSupannEtablissement(String supannEtablissement);

}
