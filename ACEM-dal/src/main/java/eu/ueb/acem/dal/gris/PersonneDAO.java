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
import java.util.Iterator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import eu.ueb.acem.dal.DAO;
import eu.ueb.acem.dal.gris.neo4j.PersonneRepository;
import eu.ueb.acem.domain.beans.gris.Personne;
import eu.ueb.acem.domain.beans.gris.neo4j.PersonneNode;

/**
 * @author Grégoire Colbert @since 2013-12-11
 * 
 */
@Repository("personDAO")
public class PersonneDAO implements DAO<Long, Personne> {

	/**
	 * For Logging.
	 */
	@SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory.getLogger(PersonneDAO.class);

	@Autowired
	private PersonneRepository repository;

	public PersonneDAO() {

	}

	@Override
	public Boolean exists(Long id) {
		return (id != null) ? repository.exists(id) : false;
	}

	@Override
	public Personne create(Personne entity) {
		return repository.save((PersonneNode) entity);
	}

	@Override
	public Collection<Personne> retrieveByName(String name) {
		Iterable<PersonneNode> nodes = repository.findByName(name);
		Collection<Personne> entities = new HashSet<Personne>();
		for (PersonneNode node : nodes) {
			entities.add(node);
		}
		return entities;
	}

	@Override
	public Personne retrieveById(Long id) {
		return (id != null) ? repository.findOne(id) : null;
	}

	@Override
	public Collection<Personne> retrieveAll() {
		Iterable<PersonneNode> endResults = repository.findAll();
		Collection<Personne> collection = new HashSet<Personne>();
		if (endResults.iterator() != null) {
			Iterator<PersonneNode> iterator = endResults.iterator();
			while (iterator.hasNext()) {
				collection.add(iterator.next());
			}
		}
		return collection;
	}

	@Override
	public Personne update(Personne entity) {
		PersonneNode personNode = (PersonneNode) entity;
		return repository.save(personNode);
	}

	@Override
	public void delete(Personne entity) {
		PersonneNode personNode = (PersonneNode) entity;
		repository.delete(personNode);
	}

	@Override
	public void deleteAll() {
		repository.deleteAll();
	}

	@Override
	public Long count() {
		return repository.count();
	}

	public Personne retrieveByLogin(String id) {
		return repository.findByLogin(id);
	}

}
