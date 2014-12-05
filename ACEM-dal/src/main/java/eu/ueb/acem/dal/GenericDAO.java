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
package eu.ueb.acem.dal;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

/**
 * TODO This class is not used for now. While Spring 4 supports autowiring generics :
 * <ul>
 * <li>http://www.jayway.com/2013/11/03/spring-and-autowiring-of-generic-types/</li>
 * <li>https://spring.io/blog/2013/12/03/spring-framework-4-0-and-java-generics</li>
 * </ul>
 * I don't succeed in making this work. I get the following error :
 * "Could not resolve id type of interface eu.ueb.acem.dal.neo4j.GenericRepository!"
 * It could mean that Spring Data Neo4j doesn't allow autowiring generics.
 * 
 * @author Grégoire Colbert
 * @since 2013-11-20
 */
//@Repository("genericDAO") // Uncomment to use
public class GenericDAO<ID extends Serializable, E, N extends E> implements DAO<Long, E> {

	/**
	 * For serizalization.
	 */
	private static final long serialVersionUID = -2603752470990407282L;

	//@Inject // Uncomment to use
	protected GenericRepository<N> repository;

	@Override
	public Boolean exists(Long id) {
		return (id != null) ? repository.exists(id) : false;
	}

	@SuppressWarnings("unchecked")
	@Override
	public E create(E entity) {
		return (E) repository.save((N) entity);
	}

	@Override
	public E retrieveById(Long id) {
		return (id != null) ? (E) repository.findOne(id) : null;
	}

	@Override
	public Collection<E> retrieveByName(String name) {
		Iterable<N> nodes = repository.findByName(name);
		Collection<E> entities = new HashSet<E>();
		for (N node : nodes) {
			entities.add(node);
		}
		return entities;
	}

	@Override
	public Collection<E> retrieveAll() {
		Iterable<N> endResults = repository.findAll();
		Collection<E> collection = new HashSet<E>();
		if (endResults.iterator() != null) {
			Iterator<N> iterator = endResults.iterator();
			while (iterator.hasNext()) {
				collection.add(iterator.next());
			}
		}
		return collection;
	}

	@SuppressWarnings("unchecked")
	@Override
	public E update(E entity) {
		return (E) repository.save((N) entity);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void delete(E entity) {
		repository.delete((N) entity);
	}

	@Override
	public void deleteAll() {
		repository.deleteAll();
	}

	@Override
	public Long count() {
		return repository.count();
	}

}
