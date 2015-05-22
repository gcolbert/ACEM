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
package eu.ueb.acem.dal.jaune.neo4j;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;

import javax.inject.Inject;

import org.springframework.stereotype.Repository;

import eu.ueb.acem.dal.jaune.ResourceDAO;
import eu.ueb.acem.dal.neo4j.AbstractDAO;
import eu.ueb.acem.dal.neo4j.GenericRepository;
import eu.ueb.acem.domain.beans.gris.Person;
import eu.ueb.acem.domain.beans.jaune.Documentation;
import eu.ueb.acem.domain.beans.jaune.ResourceCategory;
import eu.ueb.acem.domain.beans.jaune.neo4j.DocumentationNode;
import eu.ueb.acem.domain.beans.jaune.neo4j.ResourceCategoryNode;

/**
 * @author Grégoire Colbert
 * @since 2014-03-11
 * 
 */
@Repository("documentationDAO")
public class DocumentationDAO extends AbstractDAO<Documentation, DocumentationNode> implements
		ResourceDAO<Long, Documentation> {

	/**
	 * For serialization.
	 */
	private static final long serialVersionUID = 9174057115460081629L;

	@Inject
	private DocumentationRepository repository;

	@Override
	protected final GenericRepository<DocumentationNode> getRepository() {
		return repository;
	}

	@Override
	protected final void initializeCollections(Documentation entity) {
		if (entity != null) {
			neo4jOperations.fetch(entity.getCategories());
			neo4jOperations.fetch(entity.getOrganisationsHavingAccessToResource());
			neo4jOperations.fetch(entity.getOrganisationPossessingResource());
			neo4jOperations.fetch(entity.getOrganisationSupportingResource());
			neo4jOperations.fetch(entity.getUseModes());
			// Resources that are documented by the Documentation entity
			neo4jOperations.fetch(entity.getResources());
            // Documentations about this Documentation entity should be empty
			neo4jOperations.fetch(entity.getDocumentations());
		}
	}

	/**
	 * Returns the categories containing at least one "Documentation"
	 * entity.
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
	 * Returns the categories containing at least one "Documentation" entity
	 * that the given person can see.
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
	public Collection<Documentation> retrieveAllWithCategory(ResourceCategory category) {
		Iterable<DocumentationNode> endResults = repository.getEntitiesWithCategory(category.getId());
		Collection<Documentation> collection = new HashSet<Documentation>();
		if (endResults.iterator() != null) {
			Iterator<DocumentationNode> iterator = endResults.iterator();
			while (iterator.hasNext()) {
				Documentation entity = iterator.next();
				initializeCollections(entity);
				collection.add(entity);
			}
		}
		return collection;
	}

	@Override
	public Collection<Documentation> retrieveResourcesInCategoryForPerson(ResourceCategory category, Person person) {
		Iterable<DocumentationNode> endResults = repository.getResourcesInCategoryForPerson(category.getId(), person.getId());
		Collection<Documentation> collection = new HashSet<Documentation>();
		if (endResults.iterator() != null) {
			Iterator<DocumentationNode> iterator = endResults.iterator();
			while (iterator.hasNext()) {
				Documentation entity = iterator.next();
				initializeCollections(entity);
				collection.add(entity);
			}
		}
		return collection;
	}

	@Override
	public Documentation create(String name, String iconFileName) {
		return super.create(new DocumentationNode(name, iconFileName));
	}

}
