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
import java.util.Set;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.neo4j.template.Neo4jOperations;
import org.springframework.stereotype.Repository;

import eu.ueb.acem.dal.DAO;
import eu.ueb.acem.dal.jaune.neo4j.EquipmentRepository;
import eu.ueb.acem.domain.beans.jaune.Equipment;
import eu.ueb.acem.domain.beans.jaune.ResourceCategory;
import eu.ueb.acem.domain.beans.jaune.neo4j.EquipmentNode;
import eu.ueb.acem.domain.beans.jaune.neo4j.ResourceCategoryNode;

/**
 * @author Grégoire Colbert
 * @since 2014-03-11
 * 
 */
@Repository("equipmentDAO")
public class EquipmentDAO implements DAO<Long, Equipment> {

	/**
	 * For serialization.
	 */
	private static final long serialVersionUID = -8561396431760674336L;

	@SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory.getLogger(EquipmentDAO.class);

	@Inject
	private Neo4jOperations neo4jOperations;

	@Inject
	private EquipmentRepository repository;

	public EquipmentDAO() {

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
	public Equipment create(Equipment entity) {
		return repository.save((EquipmentNode) entity);
	}

	@Override
	public Equipment retrieveById(Long id) {
		return (id != null) ? repository.findOne(id) : null;
	}

	@Override
	public Equipment retrieveById(Long id, boolean initialize) {
		Equipment entity = retrieveById(id);
		if (initialize) {
			initializeCollections(entity);
		}
		return entity;
	}

	@Override
	public Set<Equipment> retrieveByName(String name) {
		Iterable<EquipmentNode> equipmentNodes = repository.findByName(name);
		Set<Equipment> equipments = new HashSet<Equipment>();
		for (Equipment entity : equipmentNodes) {
			equipments.add(entity);
		}
		return equipments;
	}

	@Override
	public Set<Equipment> retrieveByName(String name, boolean initialize) {
		Set<Equipment> entities = retrieveByName(name);
		if (initialize) {
			for (Equipment entity : entities) {
				initializeCollections(entity);
			}
		}
		return entities;
	}

	@Override
	public void initializeCollections(Equipment entity) {
		neo4jOperations.fetch(entity.getCategories());
		neo4jOperations.fetch(entity.getOrganisationsHavingAccessToResource());
		neo4jOperations.fetch(entity.getOrganisationPossessingResource());
		neo4jOperations.fetch(entity.getUseModes());
		neo4jOperations.fetch(entity.getStorageLocations());
	}

	@Override
	public Collection<Equipment> retrieveAll() {
		Iterable<EquipmentNode> endResults = repository.findAll();
		Collection<Equipment> collection = new HashSet<Equipment>();
		if (endResults.iterator() != null) {
			Iterator<EquipmentNode> iterator = endResults.iterator();
			while (iterator.hasNext()) {
				Equipment entity = iterator.next();
				initializeCollections(entity);
				collection.add(entity);
			}
		}
		return collection;
	}

	@Override
	public Equipment update(Equipment entity) {
		Equipment updatedEntity = repository.save((EquipmentNode) entity);
		initializeCollections(updatedEntity);
		return updatedEntity;
	}

	@Override
	public void delete(Equipment entity) {
		repository.delete((EquipmentNode) entity);
	}

	@Override
	public void deleteAll() {
		repository.deleteAll();
	}

	@Override
	public Long count() {
		return repository.count();
	}

	public Collection<ResourceCategory> getCategories() {
		Iterable<ResourceCategoryNode> endResults = repository.getCategories();
		Collection<ResourceCategory> collection = new HashSet<ResourceCategory>();
		if (endResults.iterator() != null) {
			Iterator<ResourceCategoryNode> iterator = endResults.iterator();
			while (iterator.hasNext()) {
				collection.add(iterator.next());
			}
		}
		return collection;
	}

	public Collection<Equipment> retrieveAllWithCategory(ResourceCategory category) {
		Iterable<EquipmentNode> endResults = repository.getEntitiesWithCategory(category.getId());
		Collection<Equipment> collection = new HashSet<Equipment>();
		if (endResults.iterator() != null) {
			Iterator<EquipmentNode> iterator = endResults.iterator();
			while (iterator.hasNext()) {
				Equipment entity = iterator.next();
				initializeCollections(entity);
				collection.add(entity);
			}
		}
		return collection;
	}

}
