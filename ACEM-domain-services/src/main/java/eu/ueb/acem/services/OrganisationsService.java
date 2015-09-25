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
package eu.ueb.acem.services;

import java.io.Serializable;
import java.util.Collection;

import eu.ueb.acem.domain.beans.gris.Person;
import eu.ueb.acem.domain.beans.rouge.Community;
import eu.ueb.acem.domain.beans.rouge.TeachingDepartment;
import eu.ueb.acem.domain.beans.rouge.Institution;
import eu.ueb.acem.domain.beans.rouge.Organisation;
import eu.ueb.acem.domain.beans.rouge.AdministrativeDepartment;

/**
 * @author Grégoire Colbert
 * @since 2013-11-20
 * 
 */
public interface OrganisationsService extends Serializable {

	/**
	 * Counts the number of persistent Community objects
	 * 
	 * @return number of communities
	 */
	Long countCommunities();

	/**
	 * Counts the number of persistent Institution objects
	 * 
	 * @return number of institutions
	 */
	Long countInstitutions();

	/**
	 * Counts the number of persistent AdministrativeDepartment objects
	 * 
	 * @return number of administrative departments
	 */
	Long countAdministrativeDepartments();

	/**
	 * Counts the number of persistent TeachingDepartment objects
	 * 
	 * @return number of teaching departments
	 */
	Long countTeachingDepartments();

	/**
	 * Creates a new persistent Community object.
	 * 
	 * @param name
	 *            A name describing the Community (e.g.
	 *            "Community of Somewhere")
	 * @param shortname
	 *            A shortname for the Community (e.g. "CoS")
	 * @param iconFileName
	 *            (optional) Icon path (relative to the images.path property)
	 * @return The newly created Community, or null.
	 */
	Community createCommunity(String name, String shortname, String iconFileName);

	/**
	 * Creates a new persistent Institution object.
	 * 
	 * @param name
	 *            A name describing the Institution (e.g.
	 *            "Institution of Somewhere")
	 * @param shortname
	 *            A shortname for the Institution (e.g. "IoS")
	 * @param iconFileName
	 *            (optional) Icon path (relative to the images.path property)
	 * @return The newly created Institution, or null.
	 */
	Institution createInstitution(String name, String shortname, String iconFileName);

	/**
	 * Creates a new persistent AdministrativeDepartment object.
	 * 
	 * @param name
	 *            A name describing the AdministrativeDepartment (e.g.
	 *            "Some administrative department")
	 * @param shortname
	 *            A shortname for the AdministrativeDepartment (e.g. "AD")
	 * @param iconFileName
	 *            (optional) Icon path (relative to the images.path property)
	 * @return The newly created AdministrativeDepartment, or null.
	 */
	AdministrativeDepartment createAdministrativeDepartment(String name, String shortname, String iconFileName);

	/**
	 * Creates a new persistent TeachingDepartment object.
	 * 
	 * @param name
	 *            A name describing the TeachingDepartment (e.g.
	 *            "Math department")
	 * @param shortname
	 *            A shortname for the TeachingDepartment (e.g. "MATH")
	 * @param iconFileName
	 *            (optional) Icon path (relative to the images.path property)
	 * @return The newly created TeachingDepartment, or null.
	 */
	TeachingDepartment createTeachingDepartment(String name, String shortname, String iconFileName);

	/**
	 * Retrieves an existing Organisation object.
	 * 
	 * @param idOrganisation
	 *            The id of the Organisation to load from the Data Access Layer
	 * @param initialize
	 *            Set to true if the collections of associated objects should be
	 *            loaded as well
	 * @return the retrieved {@link Organisation}
	 */
	Organisation retrieveOrganisation(Long idOrganisation, boolean initialize);

	Community retrieveCommunity(Long id, boolean initialize);

	Institution retrieveInstitution(Long id, boolean initialize);

	AdministrativeDepartment retrieveAdministrativeDepartment(Long id, boolean initialize);

	TeachingDepartment retrieveTeachingDepartment(Long id, boolean initialize);

	Collection<Organisation> retrieveAllOrganisations();
	
	Collection<Community> retrieveAllCommunities();
	
	Collection<Institution> retrieveAllInstitutions();
	
	Collection<AdministrativeDepartment> retrieveAllAdministrativeDepartments();
	
	Collection<TeachingDepartment> retrieveAllTeachingDepartments();
	
	Organisation updateOrganisation(Organisation organisation);

	Community updateCommunity(Community community);

	Institution updateInstitution(Institution institution);

	AdministrativeDepartment updateAdministrativeDepartment(AdministrativeDepartment administrativeDepartment);

	TeachingDepartment updateTeachingDepartment(TeachingDepartment teachingDepartment);

	Boolean deleteOrganisation(Long id);

	Boolean deleteCommunity(Long id);

	Boolean deleteInstitution(Long id);

	Boolean deleteAdministrativeDepartment(Long id);

	Boolean deleteTeachingDepartment(Long id);

	Boolean associateCommunityAndInstitution(Long idCommunity, Long idInstitution);

	Boolean dissociateCommunityAndInstitution(Long idCommunity, Long idInstitution);

	Boolean associateInstitutionAndAdministrativeDepartment(Long idInstitution, Long idAdministrativeDepartment);

	Boolean dissociateInstitutionAndAdministrativeDepartment(Long idInstitution, Long idAdministrativeDepartment);

	Boolean associateInstitutionAndTeachingDepartment(Long idInstitution, Long idTeachingDepartment);

	Boolean dissociateInstitutionAndTeachingDepartment(Long idInstitution, Long idTeachingDepartment);

	Boolean isImplicitlySharingResourcesWith(Organisation organisation1, Organisation organisation2);

	Collection<Organisation> retrieveAllSupportServicesForPerson(Person person);

}
