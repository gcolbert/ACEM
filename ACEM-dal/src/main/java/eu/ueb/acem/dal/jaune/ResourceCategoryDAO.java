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
package eu.ueb.acem.dal.jaune;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.neo4j.template.Neo4jOperations;
import org.springframework.stereotype.Repository;

import eu.ueb.acem.dal.DAO;
import eu.ueb.acem.dal.jaune.neo4j.ResourceCategoryRepository;
import eu.ueb.acem.domain.beans.jaune.ResourceCategory;
import eu.ueb.acem.domain.beans.jaune.neo4j.ResourceCategoryNode;

/**
 * @author Grégoire Colbert
 * @since 2014-04-09
 * 
 */
@Repository("resourceCategoryDAO")
public class ResourceCategoryDAO implements DAO<Long, ResourceCategory> {

	/**
	 * For serialization.
	 */
	private static final long serialVersionUID = -340108444569929110L;

	@SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory.getLogger(ResourceCategoryDAO.class);

	@Inject
	private Neo4jOperations neo4jOperations;

	@Inject
	private ResourceCategoryRepository repository;

	public ResourceCategoryDAO() {

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
	public ResourceCategory create(ResourceCategory entity) {
		return repository.save((ResourceCategoryNode) entity);
	}

	@Override
	public ResourceCategory retrieveById(Long id) {
		return (id != null) ? repository.findOne(id) : null;
	}

	@Override
	public ResourceCategory retrieveById(Long id, boolean initialize) {
		ResourceCategory entity = retrieveById(id);
		if (initialize) {
			neo4jOperations.fetch(entity.getAnswers());
			neo4jOperations.fetch(entity.getPedagogicalActivities());
			neo4jOperations.fetch(entity.getResources());
		}
		return entity;
	}

	@Override
	public Collection<ResourceCategory> retrieveByName(String name) {
		Iterable<ResourceCategoryNode> nodes = repository.findByName(name);
		Collection<ResourceCategory> categories = new HashSet<ResourceCategory>();
		for (ResourceCategory category : nodes) {
			categories.add(category);
		}
		return categories;
	}

	@Override
	public Collection<ResourceCategory> retrieveAll() {
		Iterable<ResourceCategoryNode> endResults = repository.findAll();
		Collection<ResourceCategory> collection = new HashSet<ResourceCategory>();
		if (endResults.iterator() != null) {
			Iterator<ResourceCategoryNode> iterator = endResults.iterator();
			while (iterator.hasNext()) {
				collection.add(iterator.next());
			}
		}
		return collection;
	}

	@Override
	public ResourceCategory update(ResourceCategory entity) {
		ResourceCategory updatedEntity = repository.save((ResourceCategoryNode) entity);
		return retrieveById(updatedEntity.getId(), true);
	}

	@Override
	public void delete(ResourceCategory entity) {
		repository.delete((ResourceCategoryNode) entity);
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
