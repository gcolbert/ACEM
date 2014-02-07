package eu.ueb.acem.services;

import java.util.Collection;
import java.util.HashSet;

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

	@Override
	public Collection<Personne> getPersons() {
		persons.addAll(teacherDAO.retrieveAll());
		persons.addAll(administratorDAO.retrieveAll());
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
