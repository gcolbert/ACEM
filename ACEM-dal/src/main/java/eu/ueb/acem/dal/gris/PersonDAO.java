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
package eu.ueb.acem.dal.gris;

import java.util.Collection;
import java.util.HashSet;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import eu.ueb.acem.dal.AbstractDAO;
import eu.ueb.acem.dal.DAO;
import eu.ueb.acem.dal.GenericRepository;
import eu.ueb.acem.dal.gris.neo4j.PersonRepository;
import eu.ueb.acem.domain.beans.gris.Person;
import eu.ueb.acem.domain.beans.gris.neo4j.PersonNode;

/**
 * @author Grégoire Colbert
 * @since 2013-12-11
 * 
 */
@Repository("personDAO")
public class PersonDAO extends AbstractDAO<Person, PersonNode> implements DAO<Long, Person> {

	/**
	 * For serialization.
	 */
	private static final long serialVersionUID = 1069286673672742458L;

	/**
	 * For Logging.
	 */
	@SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory.getLogger(PersonDAO.class);

	@Inject
	private PersonRepository repository;

	@Override
	public GenericRepository<PersonNode> getRepository() {
		return repository;
	}

	@Override
	public Boolean exists(Long id) {
		// This line should be sufficient but https://jira.spring.io/browse/DATAGRAPH-438
		//return (id != null) ? repository.exists(id) : false;
		if (id == null) {
			return false;
		}
		else {
			return repository.count(id) > 0 ? true : false;
		}
	}

	@Override
	public Collection<Person> retrieveByName(String name) {
		Iterable<PersonNode> nodes = repository.findByName(name);
		Collection<Person> entities = new HashSet<Person>();
		for (PersonNode node : nodes) {
			entities.add(node);
		}
		return entities;
	}

	@Override
	public void initializeCollections(Person entity) {
		neo4jOperations.fetch(entity.getWorksForOrganisations());
	}

	public Person retrieveByLogin(String id, boolean initialize) {
		Person entity = repository.findByLogin(id);
		if (initialize) {
			initializeCollections(entity);
		}
		return entity;
	}

}
