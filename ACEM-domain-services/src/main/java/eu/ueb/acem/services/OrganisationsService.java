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
 * A service to manage the {@link Organisation}s.
 * 
 * @author Grégoire Colbert
 * @since 2013-11-20
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
	 * Creates a new persistent Community.
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
	 * Creates a new persistent Institution.
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
	 * Creates a new persistent AdministrativeDepartment.
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
	 * Creates a new persistent TeachingDepartment.
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
	 * Retrieves an existing Organisation.
	 * 
	 * @param idOrganisation
	 *            The id of the Organisation to load from the Data Access Layer
	 * @param initialize
	 *            Set to true if the collections of associated objects should be
	 *            loaded as well
	 * @return the {@link Organisation} with the given id, or null if it doesn't
	 *         exist
	 */
	Organisation retrieveOrganisation(Long idOrganisation, boolean initialize);

	/**
	 * Retrieves an existing Community.
	 * 
	 * @param id
	 *            The id of the Community to load from the Data Access Layer
	 * @param initialize
	 *            Set to true if the collections of associated objects should be
	 *            loaded as well
	 * @return the {@link Community} with the given id, or null if it doesn't
	 *         exist
	 */
	Community retrieveCommunity(Long id, boolean initialize);

	/**
	 * Retrieves the existing Community that have the property
	 * "supannEtablissement" with the given value.
	 * 
	 * @param supannEtablissement
	 *            The "supannEtablissement" property value of the Community to
	 *            load from the Data Access Layer
	 * @return the {@link Community} with the given "supannEtablissement" value,
	 *         or null if it doesn't exist
	 */
	Community retrieveCommunityBySupannEtablissement(String supannEtablissement);
	
	/**
	 * Retrieves an existing Institution.
	 * 
	 * @param id
	 *            The id of the Institution to load from the Data Access Layer
	 * @param initialize
	 *            Set to true if the collections of associated objects should be
	 *            loaded as well
	 * @return the {@link Institution} with the given id, or null if it doesn't
	 *         exist
	 */
	Institution retrieveInstitution(Long id, boolean initialize);

	/**
	 * Retrieves the existing Institution that have the property
	 * "supannEtablissement" with the given value.
	 * 
	 * @param supannEtablissement
	 *            The "supannEtablissement" property value of the Institution to
	 *            load from the Data Access Layer
	 * @return the {@link Institution} with the given "supannEtablissement" value,
	 *         or null if it doesn't exist
	 */
	Institution retrieveInstitutionBySupannEtablissement(String supannEtablissement);
	
	/**
	 * Retrieves an existing AdministrativeDepartment.
	 * 
	 * @param id
	 *            The id of the AdministrativeDepartment to load from the Data
	 *            Access Layer
	 * @param initialize
	 *            Set to true if the collections of associated objects should be
	 *            loaded as well
	 * @return the {@link AdministrativeDepartment} with the given id, or null
	 *         if it doesn't exist
	 */
	AdministrativeDepartment retrieveAdministrativeDepartment(Long id, boolean initialize);

	/**
	 * Retrieves an existing TeachingDepartment.
	 * 
	 * @param id
	 *            The id of the TeachingDepartment to load from the Data Access
	 *            Layer
	 * @param initialize
	 *            Set to true if the collections of associated objects should be
	 *            loaded as well
	 * @return the {@link TeachingDepartment} with the given id, or null if it
	 *         doesn't exist
	 */
	TeachingDepartment retrieveTeachingDepartment(Long id, boolean initialize);

	/**
	 * Retrieves all existing Organisation objects.
	 * 
	 * @return the collection of all existing organisations
	 */
	Collection<Organisation> retrieveAllOrganisations();

	/**
	 * Retrieves all existing Community objects.
	 * 
	 * @return the collection of all existing communities
	 */
	Collection<Community> retrieveAllCommunities();

	/**
	 * Retrieves all existing Institution objects.
	 * 
	 * @return the collection of all existing institutions
	 */
	Collection<Institution> retrieveAllInstitutions();

	/**
	 * Retrieves all existing AdministrativeDepartment objects.
	 * 
	 * @return the collection of all existing administrative departments
	 */
	Collection<AdministrativeDepartment> retrieveAllAdministrativeDepartments();

	/**
	 * Retrieves all existing TeachingDepartment objects.
	 * 
	 * @return the collection of all existing teaching departments
	 */
	Collection<TeachingDepartment> retrieveAllTeachingDepartments();

	/**
	 * Updates the given Organisation and returns the updated entity.
	 * 
	 * @param organisation
	 *            The Organisation to update
	 * @return the updated Organisation
	 */
	Organisation updateOrganisation(Organisation organisation);

	/**
	 * Updates the given Community and returns the updated entity.
	 * 
	 * @param community
	 *            The Community to update
	 * @return the updated Community
	 */
	Community updateCommunity(Community community);

	/**
	 * Updates the given Institution and returns the updated entity.
	 * 
	 * @param institution
	 *            The Institution to update
	 * @return the updated Institution
	 */
	Institution updateInstitution(Institution institution);

	/**
	 * Updates the given AdministrativeDepartment and returns the updated
	 * entity.
	 * 
	 * @param administrativeDepartment
	 *            The AdministrativeDepartment to update
	 * @return the updated AdministrativeDepartment
	 */
	AdministrativeDepartment updateAdministrativeDepartment(AdministrativeDepartment administrativeDepartment);

	/**
	 * Updates the given TeachingDepartment and returns the updated entity.
	 * 
	 * @param teachingDepartment
	 *            The TeachingDepartment to update
	 * @return the updated TeachingDepartment
	 */
	TeachingDepartment updateTeachingDepartment(TeachingDepartment teachingDepartment);

	/**
	 * Deletes the Organisation having the given id value.
	 * 
	 * @param id
	 *            The id property value of the Organisation to delete
	 * @return true if the Organisation doesn't exists after this call, false if
	 *         the Organisation still exists
	 */
	Boolean deleteOrganisation(Long id);

	/**
	 * Deletes the Community having the given id value.
	 * 
	 * @param id
	 *            The id property value of the Community to delete
	 * @return true if the Community doesn't exists after this call, false if
	 *         the Community still exists
	 */
	Boolean deleteCommunity(Long id);

	/**
	 * Deletes the Institution having the given id value.
	 * 
	 * @param id
	 *            The id property value of the Institution to delete
	 * @return true if the Institution doesn't exists after this call, false if
	 *         the Institution still exists
	 */
	Boolean deleteInstitution(Long id);

	/**
	 * Deletes the AdministrativeDepartment having the given id value.
	 * 
	 * @param id
	 *            The id property value of the AdministrativeDepartment to
	 *            delete
	 * @return true if the AdministrativeDepartment doesn't exists after this
	 *         call, false if the AdministrativeDepartment still exists
	 */
	Boolean deleteAdministrativeDepartment(Long id);

	/**
	 * Deletes the TeachingDepartment having the given id value.
	 * 
	 * @param id
	 *            The id property value of the TeachingDepartment to delete
	 * @return true if the TeachingDepartment doesn't exists after this call,
	 *         false if the TeachingDepartment still exists
	 */
	Boolean deleteTeachingDepartment(Long id);

	/**
	 * Associates a Community and an Institution.
	 * 
	 * @param idCommunity
	 *            The id of the Community
	 * @param idInstitution
	 *            The id of the Institution
	 * @return true if the Community and the Institution are associated after
	 *         this call, false otherwise
	 */
	Boolean associateCommunityAndInstitution(Long idCommunity, Long idInstitution);

	/**
	 * Dissociates a Community and an Institution.
	 * 
	 * @param idCommunity
	 *            The id of the Community
	 * @param idInstitution
	 *            The id of the Institution
	 * @return true if the Community and the Institution are not associated,
	 *         false if they are still associated
	 */
	Boolean dissociateCommunityAndInstitution(Long idCommunity, Long idInstitution);

	/**
	 * Associates a Institution and an AdministrativeDepartment.
	 * 
	 * @param idInstitution
	 *            The id of the Institution
	 * @param idAdministrativeDepartment
	 *            The id of the AdministrativeDepartment
	 * @return true if the Institution and the AdministrativeDepartment are
	 *         associated after this call, false otherwise
	 */
	Boolean associateInstitutionAndAdministrativeDepartment(Long idInstitution, Long idAdministrativeDepartment);

	/**
	 * Dissociates a Institution and an AdministrativeDepartment.
	 * 
	 * @param idInstitution
	 *            The id of the Institution
	 * @param idAdministrativeDepartment
	 *            The id of the AdministrativeDepartment
	 * @return true if the Institution and the AdministrativeDepartment are not
	 *         associated, false if they are still associated
	 */
	Boolean dissociateInstitutionAndAdministrativeDepartment(Long idInstitution, Long idAdministrativeDepartment);

	/**
	 * Associates a Institution and a TeachingDepartment.
	 * 
	 * @param idInstitution
	 *            The id of the Institution
	 * @param idTeachingDepartment
	 *            The id of the TeachingDepartment
	 * @return true if the Institution and the TeachingDepartment are associated
	 *         after this call, false otherwise
	 */
	Boolean associateInstitutionAndTeachingDepartment(Long idInstitution, Long idTeachingDepartment);

	/**
	 * Dissociates a Institution and a TeachingDepartment.
	 * 
	 * @param idInstitution
	 *            The id of the Institution
	 * @param idTeachingDepartment
	 *            The id of the TeachingDepartment
	 * @return true if the Institution and the TeachingDepartment are not
	 *         associated, false if they are still associated
	 */
	Boolean dissociateInstitutionAndTeachingDepartment(Long idInstitution, Long idTeachingDepartment);

	/**
	 * Tests if two Organisations implicitly share Resources because they are
	 * associated.
	 * 
	 * @param organisation1
	 *            The first Organisation
	 * @param organisation2
	 *            The second Organisation
	 * @return true if organisation1 and organisation2 implicitly share
	 *         Resources, false otherwise
	 */
	Boolean isImplicitlySharingResourcesWith(Organisation organisation1, Organisation organisation2);

	/**
	 * Returns the collection of all {@link Organisation}s that act as support
	 * services for the given {@link Person}, based on the resources that the
	 * person can use.
	 * 
	 * @param person
	 *            The Person
	 * @return the collection of Organisations that are support services for the
	 *         person
	 */
	Collection<Organisation> retrieveAllSupportServicesForPerson(Person person);

}
