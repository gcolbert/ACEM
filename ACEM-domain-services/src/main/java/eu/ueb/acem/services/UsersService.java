package eu.ueb.acem.services;

import java.util.Collection;

import eu.ueb.acem.domain.beans.gris.Personne;

public interface UsersService {
	
	public Collection<Personne> getUsers();

}
