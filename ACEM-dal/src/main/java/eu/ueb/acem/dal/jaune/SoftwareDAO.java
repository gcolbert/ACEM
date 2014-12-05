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
import org.springframework.stereotype.Repository;

import eu.ueb.acem.dal.DAO;
import eu.ueb.acem.dal.jaune.neo4j.SoftwareRepository;
import eu.ueb.acem.domain.beans.jaune.Software;
import eu.ueb.acem.domain.beans.jaune.ResourceCategory;
import eu.ueb.acem.domain.beans.jaune.neo4j.SoftwareNode;
import eu.ueb.acem.domain.beans.jaune.neo4j.ResourceCategoryNode;

/**
 * @author Grégoire Colbert
 * @since 2014-03-11
 * 
 */
@Repository("softwareDAO")
public class SoftwareDAO implements DAO<Long, Software> {

	/**
	 * For serialization.
	 */
	private static final long serialVersionUID = 9036527207136169412L;

	@SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory.getLogger(SoftwareDAO.class);

	@Inject
	private SoftwareRepository repository;

	public SoftwareDAO() {

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
	public Software create(Software entity) {
		return repository.save((SoftwareNode) entity);
	}

	@Override
	public Software retrieveById(Long id) {
		return (id != null) ? repository.findOne(id) : null;
	}

	@Override
	public Collection<Software> retrieveByName(String name) {
		Iterable<SoftwareNode> softwareNodes = repository.findByName(name);
		Collection<Software> softwares = new HashSet<Software>();
		for (Software software : softwareNodes) {
			softwares.add(software);
		}
		return softwares;
	}

	@Override
	public Collection<Software> retrieveAll() {
		Iterable<SoftwareNode> endResults = repository.findAll();
		Collection<Software> collection = new HashSet<Software>();
		if (endResults.iterator() != null) {
			Iterator<SoftwareNode> iterator = endResults.iterator();
			while (iterator.hasNext()) {
				collection.add(iterator.next());
			}
		}
		return collection;
	}

	@Override
	public Software update(Software entity) {
		return repository.save((SoftwareNode) entity);
	}

	@Override
	public void delete(Software entity) {
		repository.delete((SoftwareNode) entity);
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

	public Collection<Software> retrieveAllWithCategory(ResourceCategory category) {
		Iterable<SoftwareNode> endResults = repository.getEntitiesWithCategory(category.getId());
		Collection<Software> collection = new HashSet<Software>();
		if (endResults.iterator() != null) {
			Iterator<SoftwareNode> iterator = endResults.iterator();
			while (iterator.hasNext()) {
				collection.add(iterator.next());
			}
		}
		return collection;
	}

}
