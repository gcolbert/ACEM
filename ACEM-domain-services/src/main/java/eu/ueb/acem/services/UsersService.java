package eu.ueb.acem.services;

import java.util.Collection;

import eu.ueb.acem.domain.beans.gris.Personne;
import eu.ueb.acem.domain.beans.rouge.Organisation;

public interface UsersService {

	public Long countTeachers();
	
	public Long countAdministrators();
	
	public Long countUsers();

	public Personne createPerson(String string, Organisation organisation);
	
	public Personne retrievePerson(Long id);
	
	public Personne updatePerson(Personne person);

	public Boolean deletePerson(Long id);

	public void deleteAllPersons();
	
	public Collection<Personne> getPersons();

}
