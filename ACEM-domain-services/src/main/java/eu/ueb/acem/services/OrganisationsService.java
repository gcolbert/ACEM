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
package eu.ueb.acem.services;

import java.util.Collection;

import eu.ueb.acem.domain.beans.rouge.Communaute;
import eu.ueb.acem.domain.beans.rouge.Composante;
import eu.ueb.acem.domain.beans.rouge.Etablissement;
import eu.ueb.acem.domain.beans.rouge.Service;

public interface OrganisationsService {

	Long countCommunities();

	Long countInstitutions();

	Long countAdministrativeDepartments();

	Long countTeachingDepartments();

	Communaute createCommunity(String name, String shortname);

	Etablissement createInstitution(String name, String shortname);

	Service createAdministrativeDepartment(String name, String shortname);

	Composante createTeachingDepartment(String name, String shortname);

	Communaute retrieveCommunity(Long id);

	Etablissement retrieveInstitution(Long id);

	Service retrieveAdministrativeDepartment(Long id);

	Composante retrieveTeachingDepartment(Long id);

	Collection<Communaute> retrieveAllCommunities();
	
	Collection<Etablissement> retrieveAllInstitutions();
	
	Collection<Service> retrieveAllAdministrativeDepartments();
	
	Collection<Composante> retrieveAllTeachingDepartments();
	
	Communaute updateCommunity(Communaute community);

	Etablissement updateInstitution(Etablissement institution);

	Service updateAdministrativeDepartment(Service administrativeDepartment);

	Composante updateTeachingDepartment(Composante teachingDepartment);

	Boolean deleteCommunity(Long id);

	Boolean deleteInstitution(Long id);

	Boolean deleteAdministrativeDepartment(Long id);

	Boolean deleteTeachingDepartment(Long id);

	void deleteAllCommunities();

	void deleteAllInstitutions();

	void deleteAllAdministrativeDepartments();

	void deleteAllTeachingDepartments();

	Boolean associateCommunityAndInstitution(Long idCommunity, Long idInstitution);
	
	Boolean dissociateCommunityAndInstitution(Long idCommunity, Long idInstitution);

}
