package eu.ueb.acem.services;

import java.util.Collection;

import eu.ueb.acem.domain.beans.gris.Personne;
import eu.ueb.acem.domain.beans.rouge.Organisation;

public interface UsersService {

	Long countTeachers();
	
	Long countAdministrators();
	
	Long countUsers();

	Personne createPerson(String string, Organisation organisation);
	
	Personne retrievePerson(Long id);
	
	Personne updatePerson(Personne person);

	Boolean deletePerson(Long id);

	void deleteAllPersons();
	
	Collection<Personne> getPersons();

}
