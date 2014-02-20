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
import java.util.HashSet;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import eu.ueb.acem.dal.gris.EnseignantDAO;
import eu.ueb.acem.dal.gris.GestionnaireDAO;
import eu.ueb.acem.domain.beans.gris.Personne;
import eu.ueb.acem.domain.beans.rouge.Organisation;

@Service("usersService")
public class UsersServiceImpl implements UsersService {

	@Autowired
	EnseignantDAO teacherDAO;

	@Autowired
	GestionnaireDAO administratorDAO;

	@Autowired
	OrganisationsService organisationsService;

	Collection<Personne> persons;

	public UsersServiceImpl() {
		persons = new HashSet<Personne>();
	}
	
	@PostConstruct
	public void initUsersService() {
		persons.clear();
		persons.addAll(teacherDAO.retrieveAll());
		persons.addAll(administratorDAO.retrieveAll());
	}

	@Override
	public Collection<Personne> getPersons() {
		return persons;
	}

	@Override
	public Long countTeachers() {
		return teacherDAO.count();
	}

	@Override
	public Long countAdministrators() {
		return administratorDAO.count();
	}

	@Override
	public Long countUsers() {
		return countAdministrators() + countTeachers();
	}

	@Override
	public Personne createPerson(String name, Organisation organisation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Personne retrievePerson(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Personne updatePerson(Personne person) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean deletePerson(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteAllPersons() {
		// TODO Auto-generated method stub

	}

}
