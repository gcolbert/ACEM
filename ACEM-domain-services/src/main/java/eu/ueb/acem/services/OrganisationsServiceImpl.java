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

import org.springframework.beans.factory.annotation.Autowired;

import eu.ueb.acem.dal.rouge.CommunauteDAO;
import eu.ueb.acem.dal.rouge.ComposanteDAO;
import eu.ueb.acem.dal.rouge.EtablissementDAO;
import eu.ueb.acem.dal.rouge.ServiceDAO;
import eu.ueb.acem.domain.beans.rouge.Communaute;
import eu.ueb.acem.domain.beans.rouge.Composante;
import eu.ueb.acem.domain.beans.rouge.Etablissement;
import eu.ueb.acem.domain.beans.rouge.Service;
import eu.ueb.acem.domain.beans.rouge.neo4j.CommunauteNode;
import eu.ueb.acem.domain.beans.rouge.neo4j.ComposanteNode;
import eu.ueb.acem.domain.beans.rouge.neo4j.EtablissementNode;
import eu.ueb.acem.domain.beans.rouge.neo4j.ServiceNode;

@org.springframework.stereotype.Service("organisationsService")
public class OrganisationsServiceImpl implements OrganisationsService {

	@Autowired
	CommunauteDAO communityDAO;

	@Autowired
	EtablissementDAO institutionDAO;

	@Autowired
	ComposanteDAO teachingDepartmentDAO;

	@Autowired
	ServiceDAO administrativeDepartmentDAO;

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
	public Communaute createCommunity(String name) {
		return communityDAO.create(new CommunauteNode(name));
	}

	@Override
	public Etablissement createInstitution(String name) {
		return institutionDAO.create(new EtablissementNode(name));
	}

	@Override
	public Service createAdministrativeDepartment(String name) {
		return administrativeDepartmentDAO.create(new ServiceNode(name));
	}

	@Override
	public Composante createTeachingDepartment(String name) {
		return teachingDepartmentDAO.create(new ComposanteNode(name));
	}

	@Override
	public Communaute retrieveCommunity(String name) {
		return communityDAO.create(new CommunauteNode(name));
	}

	@Override
	public Etablissement retrieveInstitution(Long id) {
		return institutionDAO.retrieveById(id);
	}

	@Override
	public Service retrieveAdministrativeDepartment(Long id) {
		return administrativeDepartmentDAO.retrieveById(id);
	}

	@Override
	public Composante retrieveTeachingDepartment(Long id) {
		return teachingDepartmentDAO.retrieveById(id);
	}

	@Override
	public Communaute updateCommunity(Communaute community) {
		return communityDAO.update(community);
	}

	@Override
	public Etablissement updateInstitution(Etablissement institution) {
		return institutionDAO.update(institution);
	}

	@Override
	public Service updateAdministrativeDepartment(Service administrativeDepartment) {
		return administrativeDepartmentDAO.update(administrativeDepartment);
	}

	@Override
	public Composante updateTeachingDepartment(Composante teachingDepartment) {
		return teachingDepartmentDAO.update(teachingDepartment);
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

}
