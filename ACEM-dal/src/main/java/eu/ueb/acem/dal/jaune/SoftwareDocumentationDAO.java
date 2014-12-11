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
import eu.ueb.acem.dal.jaune.neo4j.SoftwareDocumentationRepository;
import eu.ueb.acem.domain.beans.jaune.ResourceCategory;
import eu.ueb.acem.domain.beans.jaune.SoftwareDocumentation;
import eu.ueb.acem.domain.beans.jaune.neo4j.ResourceCategoryNode;
import eu.ueb.acem.domain.beans.jaune.neo4j.SoftwareDocumentationNode;

/**
 * @author Grégoire Colbert
 * @since 2014-03-11
 * 
 */
@Repository("softwareDocumentationDAO")
public class SoftwareDocumentationDAO implements DAO<Long, SoftwareDocumentation> {

	/**
	 * For serialization.
	 */
	private static final long serialVersionUID = 9174057115460081629L;

	@SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory.getLogger(SoftwareDocumentationDAO.class);

	@Inject
	private Neo4jOperations neo4jOperations;

	@Inject
	private SoftwareDocumentationRepository repository;

	public SoftwareDocumentationDAO() {

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
	public SoftwareDocumentation create(SoftwareDocumentation entity) {
		return repository.save((SoftwareDocumentationNode) entity);
	}

	@Override
	public SoftwareDocumentation retrieveById(Long id) {
		return (id != null) ? repository.findOne(id) : null;
	}

	@Override
	public SoftwareDocumentation retrieveById(Long id, boolean initialize) {
		SoftwareDocumentation entity = retrieveById(id);
		if (initialize) {
			neo4jOperations.fetch(entity.getCategories());
			neo4jOperations.fetch(entity.getOrganisationsHavingAccessToResource());
			neo4jOperations.fetch(entity.getOrganisationPossessingResource());
			neo4jOperations.fetch(entity.getUseModes());
			neo4jOperations.fetch(entity.getSoftwares());
		}
		return entity;
	}

	@Override
	public Collection<SoftwareDocumentation> retrieveByName(String name) {
		Iterable<SoftwareDocumentationNode> softwareNodes = repository.findByName(name);
		Collection<SoftwareDocumentation> softwares = new HashSet<SoftwareDocumentation>();
		for (SoftwareDocumentation software : softwareNodes) {
			softwares.add(software);
		}
		return softwares;
	}

	@Override
	public Collection<SoftwareDocumentation> retrieveAll() {
		Iterable<SoftwareDocumentationNode> endResults = repository.findAll();
		Collection<SoftwareDocumentation> collection = new HashSet<SoftwareDocumentation>();
		if (endResults.iterator() != null) {
			Iterator<SoftwareDocumentationNode> iterator = endResults.iterator();
			while (iterator.hasNext()) {
				collection.add(iterator.next());
			}
		}
		return collection;
	}

	@Override
	public SoftwareDocumentation update(SoftwareDocumentation entity) {
		SoftwareDocumentation updatedEntity = repository.save((SoftwareDocumentationNode) entity);
		return retrieveById(updatedEntity.getId(), true);
	}

	@Override
	public void delete(SoftwareDocumentation entity) {
		repository.delete((SoftwareDocumentationNode) entity);
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

	public Collection<SoftwareDocumentation> retrieveAllWithCategory(ResourceCategory category) {
		Iterable<SoftwareDocumentationNode> endResults = repository.getEntitiesWithCategory(category.getId());
		Collection<SoftwareDocumentation> collection = new HashSet<SoftwareDocumentation>();
		if (endResults.iterator() != null) {
			Iterator<SoftwareDocumentationNode> iterator = endResults.iterator();
			while (iterator.hasNext()) {
				collection.add(iterator.next());
			}
		}
		return collection;
	}

}
