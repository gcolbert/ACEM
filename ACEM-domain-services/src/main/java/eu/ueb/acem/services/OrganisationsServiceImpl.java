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
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import eu.ueb.acem.dal.common.rouge.OrganisationDAO;
import eu.ueb.acem.domain.beans.gris.Person;
import eu.ueb.acem.domain.beans.rouge.AdministrativeDepartment;
import eu.ueb.acem.domain.beans.rouge.Community;
import eu.ueb.acem.domain.beans.rouge.Institution;
import eu.ueb.acem.domain.beans.rouge.Organisation;
import eu.ueb.acem.domain.beans.rouge.TeachingDepartment;

/**
 * @author Grégoire Colbert
 * @since 2013-11-20
 * 
 */
@Service("organisationsService")
public class OrganisationsServiceImpl implements OrganisationsService, Serializable {

	/**
	 * For serialization.
	 */
	private static final long serialVersionUID = -4629646991379337991L;

	@Inject
	private OrganisationDAO<Long, Community> communityDAO;

	@Inject
	private OrganisationDAO<Long, Institution> institutionDAO;

	@Inject
	private OrganisationDAO<Long, TeachingDepartment> teachingDepartmentDAO;

	@Inject
	private OrganisationDAO<Long, AdministrativeDepartment> administrativeDepartmentDAO;
	
	@Override
	public Long countCommunities() {
		return communityDAO.count();
	}

	@Override
	public Long countInstitutions() {
		return institutionDAO.count();
	}

	@Override
	public Long countAdministrativeDepartments() {
		return administrativeDepartmentDAO.count();
	}

	@Override
	public Long countTeachingDepartments() {
		return teachingDepartmentDAO.count();
	}

	@Override
	public Community createCommunity(String name, String shortname, String iconFileName) {
		return communityDAO.create(name, shortname, iconFileName);
	}

	@Override
	public Institution createInstitution(String name, String shortname, String iconFileName) {
		return institutionDAO.create(name, shortname, iconFileName);
	}

	@Override
	public AdministrativeDepartment createAdministrativeDepartment(String name, String shortname, String iconFileName) {
		return administrativeDepartmentDAO.create(name, shortname, iconFileName);
	}

	@Override
	public TeachingDepartment createTeachingDepartment(String name, String shortname, String iconFileName) {
		return teachingDepartmentDAO.create(name, shortname, iconFileName);
	}

	@Override
	public Organisation retrieveOrganisation(Long idOrganisation, boolean initialize) {
		Organisation organisation = null;
		if (communityDAO.exists(idOrganisation)) {
			organisation = communityDAO.retrieveById(idOrganisation, initialize);
		}
		else if (institutionDAO.exists(idOrganisation)) {
			organisation = institutionDAO.retrieveById(idOrganisation, initialize);
		}
		else if (administrativeDepartmentDAO.exists(idOrganisation)) {
			organisation = administrativeDepartmentDAO.retrieveById(idOrganisation, initialize);
		}
		else if (teachingDepartmentDAO.exists(idOrganisation)) {
			organisation = teachingDepartmentDAO.retrieveById(idOrganisation, initialize);
		}
		return organisation;
	}

	@Override
	public Community retrieveCommunity(Long id, boolean initialize) {
		return communityDAO.retrieveById(id, initialize);
	}

	@Override
	public Institution retrieveInstitution(Long id, boolean initialize) {
		return institutionDAO.retrieveById(id, initialize);
	}

	@Override
	public AdministrativeDepartment retrieveAdministrativeDepartment(Long id, boolean initialize) {
		return administrativeDepartmentDAO.retrieveById(id, initialize);
	}

	@Override
	public TeachingDepartment retrieveTeachingDepartment(Long id, boolean initialize) {
		return teachingDepartmentDAO.retrieveById(id, initialize);
	}

	@Override
	public Collection<Organisation> retrieveAllOrganisations() {
		Collection<Organisation> organisations = new ArrayList<Organisation>();
		organisations.addAll(communityDAO.retrieveAll());
		organisations.addAll(institutionDAO.retrieveAll());
		organisations.addAll(administrativeDepartmentDAO.retrieveAll());
		organisations.addAll(teachingDepartmentDAO.retrieveAll());
		return organisations;
	}

	@Override
	public Collection<Community> retrieveAllCommunities() {
		return communityDAO.retrieveAll();
	}

	@Override
	public Collection<Institution> retrieveAllInstitutions() {
		return institutionDAO.retrieveAll();
	}

	@Override
	public Collection<AdministrativeDepartment> retrieveAllAdministrativeDepartments() {
		return administrativeDepartmentDAO.retrieveAll();
	}

	@Override
	public Collection<TeachingDepartment> retrieveAllTeachingDepartments() {
		return teachingDepartmentDAO.retrieveAll();
	}

	@Override
	public Organisation updateOrganisation(Organisation organisation) {
		Organisation updatedEntity = null;
		if (organisation instanceof Community) {
			updatedEntity = updateCommunity((Community) organisation);
		}
		else if (organisation instanceof Institution) {
			updatedEntity = updateInstitution((Institution) organisation);
		}
		else if (organisation instanceof AdministrativeDepartment) {
			updatedEntity = updateAdministrativeDepartment((AdministrativeDepartment) organisation);
		}
		else if (organisation instanceof TeachingDepartment) {
			updatedEntity = updateTeachingDepartment((TeachingDepartment) organisation);
		}
		return updatedEntity;
	}

	@Override
	public Community updateCommunity(Community community) {
		return communityDAO.update(community);
	}

	@Override
	public Institution updateInstitution(Institution institution) {
		return institutionDAO.update(institution);
	}

	@Override
	public AdministrativeDepartment updateAdministrativeDepartment(AdministrativeDepartment administrativeDepartment) {
		return administrativeDepartmentDAO.update(administrativeDepartment);
	}

	@Override
	public TeachingDepartment updateTeachingDepartment(TeachingDepartment teachingDepartment) {
		return teachingDepartmentDAO.update(teachingDepartment);
	}

	@Override
	public Boolean deleteOrganisation(Long id) {
		boolean success = false;
		if (communityDAO.exists(id)) {
			success = deleteCommunity(id);
		}
		else if (institutionDAO.exists(id)) {
			success = deleteInstitution(id);
		}
		else if (administrativeDepartmentDAO.exists(id)) {
			success = deleteAdministrativeDepartment(id);
		}
		else if (teachingDepartmentDAO.exists(id)) {
			success = deleteTeachingDepartment(id);
		}
		return success;
	}

	@Override
	public Boolean deleteCommunity(Long id) {
		if (communityDAO.exists(id)) {
			communityDAO.delete(communityDAO.retrieveById(id));
		}
		return !communityDAO.exists(id);
	}

	@Override
	public Boolean deleteInstitution(Long id) {
		if (institutionDAO.exists(id)) {
			institutionDAO.delete(institutionDAO.retrieveById(id));
		}
		return !institutionDAO.exists(id);
	}

	@Override
	public Boolean deleteAdministrativeDepartment(Long id) {
		if (administrativeDepartmentDAO.exists(id)) {
			administrativeDepartmentDAO.delete(administrativeDepartmentDAO.retrieveById(id));
		}
		return !administrativeDepartmentDAO.exists(id);
	}

	@Override
	public Boolean deleteTeachingDepartment(Long id) {
		if (teachingDepartmentDAO.exists(id)) {
			teachingDepartmentDAO.delete(teachingDepartmentDAO.retrieveById(id));
		}
		return !teachingDepartmentDAO.exists(id);
	}

	@Override
	public Boolean associateCommunityAndInstitution(Long idCommunity, Long idInstitution) {
		Community community = retrieveCommunity(idCommunity, true);
		Institution institution = retrieveInstitution(idInstitution, true);

		community.getInstitutions().add(institution);
		institution.getCommunities().add(community);

		institution = updateInstitution(institution);
		community = updateCommunity(community);

		return community.getInstitutions().contains(institution) && institution.getCommunities().contains(community);
	}

	@Override
	public Boolean dissociateCommunityAndInstitution(Long idCommunity, Long idInstitution) {
		Community community = retrieveCommunity(idCommunity, true);
		Institution institution = retrieveInstitution(idInstitution, true);

		institution.getCommunities().remove(community);
		community.getInstitutions().remove(institution);

		community = updateCommunity(community);
		institution = updateInstitution(institution);

		return !community.getInstitutions().contains(institution) && !institution.getCommunities().contains(community);
	}

	@Override
	public Boolean associateInstitutionAndAdministrativeDepartment(Long idInstitution, Long idAdministrativeDepartment) {
		Institution institution = retrieveInstitution(idInstitution, true);
		AdministrativeDepartment administrativeDepartment = retrieveAdministrativeDepartment(idAdministrativeDepartment, true);

		institution.getAdministrativeDepartments().add(administrativeDepartment);
		administrativeDepartment.getInstitutions().add(institution);
		
		institution = updateInstitution(institution);
		administrativeDepartment = updateAdministrativeDepartment(administrativeDepartment);

		return institution.getAdministrativeDepartments().contains(administrativeDepartment) && administrativeDepartment.getInstitutions().contains(institution);
	}

	@Override
	public Boolean dissociateInstitutionAndAdministrativeDepartment(Long idInstitution, Long idAdministrativeDepartment) {
		Institution institution = retrieveInstitution(idInstitution, true);
		AdministrativeDepartment administrativeDepartment = retrieveAdministrativeDepartment(idAdministrativeDepartment, true);

		institution.getAdministrativeDepartments().remove(administrativeDepartment);
		administrativeDepartment.getInstitutions().remove(institution);

		institution = updateInstitution(institution);
		administrativeDepartment = updateAdministrativeDepartment(administrativeDepartment);

		return !institution.getAdministrativeDepartments().contains(administrativeDepartment) && !administrativeDepartment.getInstitutions().contains(institution);
	}

	@Override
	public Boolean associateInstitutionAndTeachingDepartment(Long idInstitution, Long idTeachingDepartment) {
		Institution institution = retrieveInstitution(idInstitution, true);
		TeachingDepartment teachingDepartment = retrieveTeachingDepartment(idTeachingDepartment, true);

		teachingDepartment.getInstitutions().add(institution);
		institution.getTeachingDepartments().add(teachingDepartment);

		institution = updateInstitution(institution);
		teachingDepartment = updateTeachingDepartment(teachingDepartment);

		return institution.getTeachingDepartments().contains(teachingDepartment) && teachingDepartment.getInstitutions().contains(institution);
	}

	@Override
	public Boolean dissociateInstitutionAndTeachingDepartment(Long idInstitution, Long idTeachingDepartment) {
		Institution institution = retrieveInstitution(idInstitution, true);
		TeachingDepartment teachingDepartment = retrieveTeachingDepartment(idTeachingDepartment, true);

		teachingDepartment.getInstitutions().remove(institution);
		institution.getTeachingDepartments().remove(teachingDepartment);

		institution = updateInstitution(institution);
		teachingDepartment = updateTeachingDepartment(teachingDepartment);

		return !institution.getTeachingDepartments().contains(teachingDepartment) && !teachingDepartment.getInstitutions().contains(institution);
	}

	@Override
	// TODO : should be replaced by a request in the DAL (if using Neo4j)
	public Boolean isImplicitlySharingResourcesWith(Organisation organisation1, Organisation organisation2) {
		Boolean implicitShare = false;
		if (organisation1 instanceof Community) {
			if (organisation2 instanceof Institution) {
				implicitShare = isImplicitlySharingResourcesWith((Community) organisation1, (Institution) organisation2);
			}
			else if (organisation2 instanceof AdministrativeDepartment) {
				implicitShare = isImplicitlySharingResourcesWith((Community) organisation1,
						(AdministrativeDepartment) organisation2);
			}
			else if (organisation2 instanceof TeachingDepartment) {
				implicitShare = isImplicitlySharingResourcesWith((Community) organisation1,
						(TeachingDepartment) organisation2);
			}
		}
		else if (organisation1 instanceof Institution) {
			if (organisation2 instanceof AdministrativeDepartment) {
				implicitShare = isImplicitlySharingResourcesWith((Institution) organisation1,
						(AdministrativeDepartment) organisation2);
			}
			else if (organisation2 instanceof TeachingDepartment) {
				implicitShare = isImplicitlySharingResourcesWith((Institution) organisation1,
						(TeachingDepartment) organisation2);
			}
		}
		return implicitShare;
	}

	private Boolean isImplicitlySharingResourcesWith(Community c, Institution i) {
		return i.getCommunities().contains(c);
	}

	private Boolean isImplicitlySharingResourcesWith(Community c, AdministrativeDepartment ad) {
		Set<Institution> intersection = new HashSet<Institution>(ad.getInstitutions());
		intersection.retainAll(c.getInstitutions());
		return !intersection.isEmpty();
	}

	private Boolean isImplicitlySharingResourcesWith(Community c, TeachingDepartment td) {
		Set<Institution> intersection = new HashSet<Institution>(td.getInstitutions());
		intersection.retainAll(c.getInstitutions());
		return !intersection.isEmpty();
	}

	private Boolean isImplicitlySharingResourcesWith(Institution i, AdministrativeDepartment ad) {
		return ad.getInstitutions().contains(i);
	}

	private Boolean isImplicitlySharingResourcesWith(Institution i, TeachingDepartment td) {
		return td.getInstitutions().contains(i);
	}
	
	@Override
	public Collection<Organisation> retrieveAllSupportServicesForPerson(Person person) {
		Collection<Organisation> supportServices = new HashSet<Organisation>();
		supportServices.addAll(communityDAO.retrieveSupportServicesForPerson(person));
		supportServices.addAll(institutionDAO.retrieveSupportServicesForPerson(person));
		supportServices.addAll(administrativeDepartmentDAO.retrieveSupportServicesForPerson(person));
		supportServices.addAll(teachingDepartmentDAO.retrieveSupportServicesForPerson(person));
		return supportServices;
	}
}
