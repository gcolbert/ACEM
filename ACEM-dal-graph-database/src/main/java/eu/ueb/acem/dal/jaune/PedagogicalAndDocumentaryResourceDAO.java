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
package eu.ueb.acem.dal.jaune;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;

import javax.inject.Inject;

import org.springframework.stereotype.Repository;

import eu.ueb.acem.dal.AbstractDAO;
import eu.ueb.acem.dal.GenericRepository;
import eu.ueb.acem.dal.jaune.neo4j.PedagogicalAndDocumentaryResourcesRepository;
import eu.ueb.acem.domain.beans.gris.Person;
import eu.ueb.acem.domain.beans.jaune.PedagogicalAndDocumentaryResource;
import eu.ueb.acem.domain.beans.jaune.ResourceCategory;
import eu.ueb.acem.domain.beans.jaune.neo4j.PedagogicalAndDocumentaryResourceNode;
import eu.ueb.acem.domain.beans.jaune.neo4j.ResourceCategoryNode;

/**
 * @author Grégoire Colbert
 * @since 2014-03-11
 * 
 */
@Repository("pedagogicalAndDocumentaryResourceDAO")
public class PedagogicalAndDocumentaryResourceDAO extends
		AbstractDAO<PedagogicalAndDocumentaryResource, PedagogicalAndDocumentaryResourceNode> implements
		ResourceDAO<Long, PedagogicalAndDocumentaryResource> {

	/**
	 * For serialization.
	 */
	private static final long serialVersionUID = 3560652375213346842L;

	@Inject
	private PedagogicalAndDocumentaryResourcesRepository repository;

	@Override
	protected final GenericRepository<PedagogicalAndDocumentaryResourceNode> getRepository() {
		return repository;
	}
	
	@Override
	protected final void initializeCollections(PedagogicalAndDocumentaryResource entity) {
		if (entity != null) {
			neo4jOperations.fetch(entity.getCategories());
			neo4jOperations.fetch(entity.getOrganisationsHavingAccessToResource());
			neo4jOperations.fetch(entity.getOrganisationPossessingResource());
			neo4jOperations.fetch(entity.getOrganisationSupportingResource());
			neo4jOperations.fetch(entity.getUseModes());
			neo4jOperations.fetch(entity.getDocumentations());
		}
	}

	/**
	 * Returns the categories containing at least one
	 * "PedagogicalAndDocumentaryResource" entity.
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
	 * Returns the categories containing at least one
	 * "PedagogicalAndDocumentaryResource" entity that the given person can see.
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

	@Override
	public Collection<PedagogicalAndDocumentaryResource> retrieveAllWithCategory(ResourceCategory category) {
		Iterable<PedagogicalAndDocumentaryResourceNode> endResults = repository.getEntitiesWithCategory(category.getId());
		Collection<PedagogicalAndDocumentaryResource> collection = new HashSet<PedagogicalAndDocumentaryResource>();
		if (endResults.iterator() != null) {
			Iterator<PedagogicalAndDocumentaryResourceNode> iterator = endResults.iterator();
			while (iterator.hasNext()) {
				PedagogicalAndDocumentaryResource entity = iterator.next();
				initializeCollections(entity);
				collection.add(entity);
			}
		}
		return collection;
	}

	@Override
	public Collection<PedagogicalAndDocumentaryResource> retrieveResourcesInCategoryForPerson(ResourceCategory category, Person person) {
		Iterable<PedagogicalAndDocumentaryResourceNode> endResults = repository.getResourcesInCategoryForPerson(category.getId(), person.getId());
		Collection<PedagogicalAndDocumentaryResource> collection = new HashSet<PedagogicalAndDocumentaryResource>();
		if (endResults.iterator() != null) {
			Iterator<PedagogicalAndDocumentaryResourceNode> iterator = endResults.iterator();
			while (iterator.hasNext()) {
				PedagogicalAndDocumentaryResource entity = iterator.next();
				initializeCollections(entity);
				collection.add(entity);
			}
		}
		return collection;
	}

}
