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
package eu.ueb.acem.dal.jaune.jpa;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;

import javax.inject.Inject;

import org.springframework.stereotype.Repository;

import eu.ueb.acem.dal.jaune.ResourceDAO;
import eu.ueb.acem.dal.jpa.AbstractDAO;
import eu.ueb.acem.dal.jpa.GenericRepository;
import eu.ueb.acem.domain.beans.gris.Person;
import eu.ueb.acem.domain.beans.jaune.PedagogicalAndDocumentaryResource;
import eu.ueb.acem.domain.beans.jaune.ResourceCategory;
import eu.ueb.acem.domain.beans.jaune.jpa.PedagogicalAndDocumentaryResourceEntity;
import eu.ueb.acem.domain.beans.jaune.jpa.ResourceCategoryEntity;

/**
 * @author Grégoire Colbert
 * @since 2015-05-29
 * 
 */
@Repository("pedagogicalAndDocumentaryResourceDAO")
public class PedagogicalAndDocumentaryResourceDAO extends
		AbstractDAO<PedagogicalAndDocumentaryResource, PedagogicalAndDocumentaryResourceEntity> implements
		ResourceDAO<Long, PedagogicalAndDocumentaryResource> {

	/**
	 * For serialization.
	 */
	private static final long serialVersionUID = -5750921886981813580L;

	@Inject
	private PedagogicalAndDocumentaryResourcesRepository repository;

	@Override
	protected final GenericRepository<PedagogicalAndDocumentaryResourceEntity> getRepository() {
		return repository;
	}
	
	@Override
	protected final void initializeCollections(PedagogicalAndDocumentaryResource entity) {
		if (entity != null) {
			entity.getCategories().size();
			entity.getOrganisationsHavingAccessToResource().size();
			entity.getUseModes().size();
			entity.getDocumentations().size();
		}
	}

	/**
	 * Returns the categories containing at least one
	 * "PedagogicalAndDocumentaryResource" entity.
	 */
	@Override
	public Collection<ResourceCategory> retrieveCategories() {
		Iterable<ResourceCategoryEntity> endResults = repository.getCategories();
		Collection<ResourceCategory> collection = new HashSet<ResourceCategory>();
		if (endResults.iterator() != null) {
			Iterator<ResourceCategoryEntity> iterator = endResults.iterator();
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
		Iterable<ResourceCategoryEntity> endResults = repository.getCategoriesForPerson(person.getId());
		Collection<ResourceCategory> collection = new HashSet<ResourceCategory>();
		if (endResults.iterator() != null) {
			Iterator<ResourceCategoryEntity> iterator = endResults.iterator();
			while (iterator.hasNext()) {
				collection.add(iterator.next());
			}
		}
		return collection;
	}

	@Override
	public Collection<PedagogicalAndDocumentaryResource> retrieveAllWithCategory(ResourceCategory category) {
		Iterable<PedagogicalAndDocumentaryResourceEntity> endResults = repository.getEntitiesWithCategory(category.getId());
		Collection<PedagogicalAndDocumentaryResource> collection = new HashSet<PedagogicalAndDocumentaryResource>();
		if (endResults.iterator() != null) {
			Iterator<PedagogicalAndDocumentaryResourceEntity> iterator = endResults.iterator();
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
		Iterable<PedagogicalAndDocumentaryResourceEntity> endResults = repository.getResourcesInCategoryForPerson(category.getId(), person.getId());
		Collection<PedagogicalAndDocumentaryResource> collection = new HashSet<PedagogicalAndDocumentaryResource>();
		if (endResults.iterator() != null) {
			Iterator<PedagogicalAndDocumentaryResourceEntity> iterator = endResults.iterator();
			while (iterator.hasNext()) {
				PedagogicalAndDocumentaryResource entity = iterator.next();
				initializeCollections(entity);
				collection.add(entity);
			}
		}
		return collection;
	}

	@Override
	public PedagogicalAndDocumentaryResource create(String name, String iconFileName) {
		return super.create(new PedagogicalAndDocumentaryResourceEntity(name, iconFileName));
	}

}
