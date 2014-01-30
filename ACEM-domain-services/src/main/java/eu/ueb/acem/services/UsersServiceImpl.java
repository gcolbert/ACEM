package eu.ueb.acem.services;

import java.util.Collection;
import java.util.HashSet;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import eu.ueb.acem.dal.gris.EnseignantDAO;
import eu.ueb.acem.dal.gris.GestionnaireDAO;
import eu.ueb.acem.domain.beans.gris.Personne;

@Service("usersService")
public class UsersServiceImpl implements UsersService {

	@Autowired
	EnseignantDAO enseignantDAO;

	@Autowired
	GestionnaireDAO gestionnaireDAO;

	Collection<Personne> users;

	public UsersServiceImpl() {
		users = new HashSet<Personne>();
	}

	@Override
	public Collection<Personne> getUsers() {
		users.addAll(enseignantDAO.retrieveAll());
		users.addAll(gestionnaireDAO.retrieveAll());
		return users;
	}

	/*
	 * @Override public Personne createUser(String uid, String name, Boolean
	 * isAdmin, Boolean isTeacher) { }
	 */

}
