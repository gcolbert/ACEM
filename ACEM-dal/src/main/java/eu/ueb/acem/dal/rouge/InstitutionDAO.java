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
package eu.ueb.acem.dal.rouge;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import eu.ueb.acem.dal.DAO;
import eu.ueb.acem.dal.rouge.neo4j.InstitutionRepository;
import eu.ueb.acem.domain.beans.rouge.Institution;
import eu.ueb.acem.domain.beans.rouge.neo4j.InstitutionNode;

/**
 * @author Grégoire Colbert
 * @since 2014-02-07
 * 
 */
@Repository("institutionDAO")
public class InstitutionDAO implements DAO<Long, Institution> {

	/**
	 * FOr serialization.
	 */
	private static final long serialVersionUID = -1248475351876837707L;

	@SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory.getLogger(InstitutionDAO.class);

	@Inject
	private InstitutionRepository repository;

	public InstitutionDAO() {

	}

	@Override
	public Boolean exists(Long id) {
		//return (id != null) ? repository.exists(id) : false;
		if (id == null) {
			return false;
		}
		else {
			return repository.count(id) > 0 ? true : false;
		}
	}

	@Override
	public Institution create(Institution entity) {
		return repository.save((InstitutionNode) entity);
	}

	@Override
	public Institution retrieveById(Long id) {
		return (id != null) ? repository.findOne(id) : null;
	}

	@Override
	public Collection<Institution> retrieveByName(String name) {
		Iterable<InstitutionNode> nodes = repository.findByName(name);
		Collection<Institution> entities = new HashSet<Institution>();
		for (InstitutionNode node : nodes) {
			entities.add(node);
		}
		return entities;
	}

	@Override
	public Collection<Institution> retrieveAll() {
		Iterable<InstitutionNode> endResults = repository.findAll();
		Collection<Institution> collection = new HashSet<Institution>();
		if (endResults.iterator() != null) {
			Iterator<InstitutionNode> iterator = endResults.iterator();
			while (iterator.hasNext()) {
				collection.add(iterator.next());
			}
		}
		return collection;
	}

	@Override
	public Institution update(Institution entity) {
		return repository.save((InstitutionNode) entity);
	}

	@Override
	public void delete(Institution entity) {
		repository.delete((InstitutionNode) entity);
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
