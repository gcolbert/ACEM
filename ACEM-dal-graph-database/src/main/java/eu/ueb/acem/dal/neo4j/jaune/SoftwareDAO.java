/**
 *     Copyright Université Européenne de Bretagne 2012-2015
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
package eu.ueb.acem.dal.neo4j.jaune;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;

import javax.inject.Inject;

import org.springframework.stereotype.Repository;

import eu.ueb.acem.dal.common.jaune.ResourceDAO;
import eu.ueb.acem.dal.neo4j.AbstractDAO;
import eu.ueb.acem.dal.neo4j.GenericRepository;
import eu.ueb.acem.domain.beans.gris.Person;
import eu.ueb.acem.domain.beans.jaune.ResourceCategory;
import eu.ueb.acem.domain.beans.jaune.Software;
import eu.ueb.acem.domain.beans.neo4j.jaune.ResourceCategoryNode;
import eu.ueb.acem.domain.beans.neo4j.jaune.SoftwareNode;

/**
 * The Spring Data Neo4j implementation of ResourceDAO for Software domain
 * beans.
 * 
 * @author Grégoire Colbert
 * @since 2014-03-11
 */
@Repository("softwareDAO")
public class SoftwareDAO extends AbstractDAO<Software, SoftwareNode> implements ResourceDAO<Long, Software> {

	/**
	 * For serialization.
	 */
	private static final long serialVersionUID = 9036527207136169412L;

	@Inject
	private SoftwareRepository repository;

	@Override
	protected final GenericRepository<SoftwareNode> getRepository() {
		return repository;
	}

	@Override
	protected final void initializeCollections(Software entity) {
		if (entity != null) {
			neo4jOperations.fetch(entity.getCategories());
			neo4jOperations.fetch(entity.getOrganisationsHavingAccessToResource());
			neo4jOperations.fetch(entity.getUseModes());
			neo4jOperations.fetch(entity.getDocumentations());
		}
	}

	/**
	 * Returns the categories containing at least one "Software" entity.
	 */
	@Override
	public Collection<ResourceCategory> retrieveCategories() {
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

	/**
	 * Returns the categories containing at least one "Software" entity.
	 */
	@Override
	public Collection<ResourceCategory> retrieveCategoriesForPerson(Person person) {
		Iterable<ResourceCategoryNode> endResults = repository.getCategoriesForPerson(person.getId());
		Collection<ResourceCategory> collection = new HashSet<ResourceCategory>();
		if (endResults.iterator() != null) {
			Iterator<ResourceCategoryNode> iterator = endResults.iterator();
			while (iterator.hasNext()) {
				collection.add(iterator.next());
			}
		}
		return collection;
	}

	/**
	 * Returns the categories containing at least one "Software" entity that the
	 * given person can see.
	 */
	@Override
	public Collection<Software> retrieveAllWithCategory(ResourceCategory category) {
		Iterable<SoftwareNode> endResults = repository.getEntitiesWithCategory(category.getId());
		Collection<Software> collection = new HashSet<Software>();
		if (endResults.iterator() != null) {
			Iterator<SoftwareNode> iterator = endResults.iterator();
			while (iterator.hasNext()) {
				Software entity = iterator.next();
				initializeCollections(entity);
				collection.add(entity);
			}
		}
		return collection;
	}

	@Override
	public Collection<Software> retrieveResourcesInCategoryForPerson(ResourceCategory category, Person person) {
		Iterable<SoftwareNode> endResults = repository
				.getResourcesInCategoryForPerson(category.getId(), person.getId());
		Collection<Software> collection = new HashSet<Software>();
		if (endResults.iterator() != null) {
			Iterator<SoftwareNode> iterator = endResults.iterator();
			while (iterator.hasNext()) {
				Software entity = iterator.next();
				initializeCollections(entity);
				collection.add(entity);
			}
		}
		return collection;
	}

	@Override
	public Software create(String name, String iconFileName) {
		return super.create(new SoftwareNode(name, iconFileName));
	}
}
