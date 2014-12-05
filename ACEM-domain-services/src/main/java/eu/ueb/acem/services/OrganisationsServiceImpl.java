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

import java.util.ArrayList;
import java.util.Collection;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import eu.ueb.acem.dal.rouge.AdministrativeDepartmentDAO;
import eu.ueb.acem.dal.rouge.CommunityDAO;
import eu.ueb.acem.dal.rouge.InstitutionDAO;
import eu.ueb.acem.dal.rouge.TeachingDepartmentDAO;
import eu.ueb.acem.domain.beans.rouge.Community;
import eu.ueb.acem.domain.beans.rouge.TeachingDepartment;
import eu.ueb.acem.domain.beans.rouge.Institution;
import eu.ueb.acem.domain.beans.rouge.Organisation;
import eu.ueb.acem.domain.beans.rouge.AdministrativeDepartment;
import eu.ueb.acem.domain.beans.rouge.neo4j.CommunityNode;
import eu.ueb.acem.domain.beans.rouge.neo4j.TeachingDepartmentNode;
import eu.ueb.acem.domain.beans.rouge.neo4j.InstitutionNode;
import eu.ueb.acem.domain.beans.rouge.neo4j.AdministrativeDepartmentNode;

/**
 * @author Grégoire Colbert
 * @since 2013-11-20
 * 
 */
@org.springframework.stereotype.Service("organisationsService")
public class OrganisationsServiceImpl implements OrganisationsService {

	private static final Logger logger = LoggerFactory.getLogger(OrganisationsServiceImpl.class);

	@Inject
	private CommunityDAO communityDAO;

	@Inject
	private InstitutionDAO institutionDAO;

	@Inject
	private TeachingDepartmentDAO teachingDepartmentDAO;

	@Inject
	private AdministrativeDepartmentDAO administrativeDepartmentDAO;

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
		return communityDAO.create(new CommunityNode(name, shortname, iconFileName));
	}

	@Override
	public Institution createInstitution(String name, String shortname, String iconFileName) {
		return institutionDAO.create(new InstitutionNode(name, shortname, iconFileName));
	}

	@Override
	public AdministrativeDepartment createAdministrativeDepartment(String name, String shortname, String iconFileName) {
		return administrativeDepartmentDAO.create(new AdministrativeDepartmentNode(name, shortname, iconFileName));
	}

	@Override
	public TeachingDepartment createTeachingDepartment(String name, String shortname, String iconFileName) {
		return teachingDepartmentDAO.create(new TeachingDepartmentNode(name, shortname, iconFileName));
	}

	@Override
	public Organisation retrieveOrganisation(Long idOrganisation) {
		logger.info("retrieveOrganisation({})", idOrganisation);
		Organisation organisation = null;
		if (communityDAO.exists(idOrganisation)) {
			logger.info("organisation found using communityDAO");
			organisation = communityDAO.retrieveById(idOrganisation);
		}
		else if (institutionDAO.exists(idOrganisation)) {
			logger.info("organisation found using institutionDAO");
			organisation = institutionDAO.retrieveById(idOrganisation);
		}
		else if (administrativeDepartmentDAO.exists(idOrganisation)) {
			logger.info("organisation found using administrativeDepartmentDAO");
			organisation = administrativeDepartmentDAO.retrieveById(idOrganisation);
		}
		else if (teachingDepartmentDAO.exists(idOrganisation)) {
			logger.info("organisation found using teachingDepartmentDAO");
			organisation = teachingDepartmentDAO.retrieveById(idOrganisation);
		}
		return organisation;
	}

	@Override
	public Community retrieveCommunity(Long id) {
		return communityDAO.retrieveById(id);
	}

	@Override
	public Institution retrieveInstitution(Long id) {
		return institutionDAO.retrieveById(id);
	}

	@Override
	public AdministrativeDepartment retrieveAdministrativeDepartment(Long id) {
		return administrativeDepartmentDAO.retrieveById(id);
	}

	@Override
	public TeachingDepartment retrieveTeachingDepartment(Long id) {
		return teachingDepartmentDAO.retrieveById(id);
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
		Organisation updatedOrganisation = null;
		if (organisation instanceof Community) {
			updatedOrganisation = updateCommunity((Community) organisation);
		}
		else if (organisation instanceof Institution) {
			updatedOrganisation = updateInstitution((Institution) organisation);
		}
		else if (organisation instanceof AdministrativeDepartment) {
			updatedOrganisation = updateAdministrativeDepartment((AdministrativeDepartment) organisation);
		}
		else if (organisation instanceof TeachingDepartment) {
			updatedOrganisation = updateTeachingDepartment((TeachingDepartment) organisation);
		}
		return updatedOrganisation;
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
		return (!communityDAO.exists(id));
	}

	@Override
	public Boolean deleteInstitution(Long id) {
		if (institutionDAO.exists(id)) {
			institutionDAO.delete(institutionDAO.retrieveById(id));
		}
		return (!institutionDAO.exists(id));
	}

	@Override
	public Boolean deleteAdministrativeDepartment(Long id) {
		if (administrativeDepartmentDAO.exists(id)) {
			administrativeDepartmentDAO.delete(administrativeDepartmentDAO.retrieveById(id));
		}
		return (!administrativeDepartmentDAO.exists(id));
	}

	@Override
	public Boolean deleteTeachingDepartment(Long id) {
		if (teachingDepartmentDAO.exists(id)) {
			teachingDepartmentDAO.delete(teachingDepartmentDAO.retrieveById(id));
		}
		return (!teachingDepartmentDAO.exists(id));
	}

	@Override
	public void deleteAllCommunities() {
		communityDAO.deleteAll();
	}

	@Override
	public void deleteAllInstitutions() {
		institutionDAO.deleteAll();
	}

	@Override
	public void deleteAllAdministrativeDepartments() {
		administrativeDepartmentDAO.deleteAll();
	}

	@Override
	public void deleteAllTeachingDepartments() {
		teachingDepartmentDAO.deleteAll();
	}

	@Override
	public Boolean associateCommunityAndInstitution(Long idCommunity, Long idInstitution) {
		logger.info("in associateCommunityAndInstitution");
		Community community = communityDAO.retrieveById(idCommunity);
		Institution institution = institutionDAO.retrieveById(idInstitution);
		community.addInstitution(institution);
		community = communityDAO.update(community);
		institution = institutionDAO.update(institution);
		return (community.getInstitutions().contains(institution));
	}

	@Override
	public Boolean dissociateCommunityAndInstitution(Long idCommunity, Long idInstitution) {
		logger.info("in dissociateCommunityAndInstitution");
		Community community = communityDAO.retrieveById(idCommunity);
		Institution institution = institutionDAO.retrieveById(idInstitution);
		community.removeInstitution(institution);
		community = communityDAO.update(community);
		institution = institutionDAO.update(institution);
		return (!community.getInstitutions().contains(institution));
	}

	@Override
	public Boolean associateInstitutionAndAdministrativeDepartment(Long idInstitution, Long idAdministrativeDepartment) {
		logger.info("in associateInstitutionAndAdministrativeDepartment");
		Institution institution = institutionDAO.retrieveById(idInstitution);
		AdministrativeDepartment administrativeDepartment = administrativeDepartmentDAO.retrieveById(idAdministrativeDepartment);
		institution.addAdministrativeDepartment(administrativeDepartment);
		institution = institutionDAO.update(institution);
		administrativeDepartment = administrativeDepartmentDAO.update(administrativeDepartment);
		return (institution.getAdministrativeDepartments().contains(administrativeDepartment));
	}

	@Override
	public Boolean dissociateInstitutionAndAdministrativeDepartment(Long idInstitution, Long idAdministrativeDepartment) {
		logger.info("in dissociateInstitutionAndAdministrativeDepartment");
		Institution institution = institutionDAO.retrieveById(idInstitution);
		AdministrativeDepartment administrativeDepartment = administrativeDepartmentDAO.retrieveById(idAdministrativeDepartment);
		institution.removeAdministrativeDepartment(administrativeDepartment);
		institution = institutionDAO.update(institution);
		administrativeDepartment = administrativeDepartmentDAO.update(administrativeDepartment);
		return (!institution.getAdministrativeDepartments().contains(administrativeDepartment));
	}

	@Override
	public Boolean associateInstitutionAndTeachingDepartment(Long idInstitution, Long idTeachingDepartment) {
		logger.info("in associateInstitutionAndTeachingDepartment");
		Institution institution = institutionDAO.retrieveById(idInstitution);
		TeachingDepartment teachingDepartment = teachingDepartmentDAO.retrieveById(idTeachingDepartment);
		institution.addTeachingDepartment(teachingDepartment);
		institution = institutionDAO.update(institution);
		teachingDepartment = teachingDepartmentDAO.update(teachingDepartment);
		return (institution.getTeachingDepartments().contains(teachingDepartment));
	}

	@Override
	public Boolean dissociateInstitutionAndTeachingDepartment(Long idInstitution, Long idTeachingDepartment) {
		logger.info("in dissociateInstitutionAndTeachingDepartment");
		Institution institution = institutionDAO.retrieveById(idInstitution);
		TeachingDepartment teachingDepartment = teachingDepartmentDAO.retrieveById(idTeachingDepartment);
		institution.removeTeachingDepartment(teachingDepartment);
		institution = institutionDAO.update(institution);
		teachingDepartment = teachingDepartmentDAO.update(teachingDepartment);
		return (!institution.getTeachingDepartments().contains(teachingDepartment));
	}

}
