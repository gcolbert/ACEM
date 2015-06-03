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
import eu.ueb.acem.domain.beans.jaune.ResourceCategory;
import eu.ueb.acem.domain.beans.jaune.Software;
import eu.ueb.acem.domain.beans.jaune.jpa.ResourceCategoryEntity;
import eu.ueb.acem.domain.beans.jaune.jpa.SoftwareEntity;

/**
 * @author Grégoire Colbert
 * @since 2015-05-29
 */
@Repository("softwareDAO")
public class SoftwareDAO extends AbstractDAO<Software, SoftwareEntity> implements ResourceDAO<Long, Software> {

	/**
	 * For serialization.
	 */
	private static final long serialVersionUID = -9210124706910105531L;

	@Inject
	private SoftwareRepository repository;

	@Override
	protected final GenericRepository<SoftwareEntity> getRepository() {
		return repository;
	}

	@Override
	protected final void initializeCollections(Software entity) {
		if (entity != null) {
			entity.getCategories().size();
			entity.getOrganisationsHavingAccessToResource().size();
			entity.getUseModes().size();
			entity.getDocumentations().size();
		}
	}

	/**
	 * Returns the categories containing at least one "Software" entity.
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
	 * Returns the categories containing at least one "Software" entity.
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

	/**
	 * Returns the categories containing at least one "Software" entity that the given person can see.
	 */
	@Override
	public Collection<Software> retrieveAllWithCategory(ResourceCategory category) {
		Iterable<SoftwareEntity> endResults = repository.getEntitiesWithCategory(category.getId());
		Collection<Software> collection = new HashSet<Software>();
		if (endResults.iterator() != null) {
			Iterator<SoftwareEntity> iterator = endResults.iterator();
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
		Iterable<SoftwareEntity> endResults = repository.getResourcesInCategoryForPerson(category.getId(), person.getId());
		Collection<Software> collection = new HashSet<Software>();
		if (endResults.iterator() != null) {
			Iterator<SoftwareEntity> iterator = endResults.iterator();
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
		return super.create(new SoftwareEntity(name, iconFileName));
	}
}
