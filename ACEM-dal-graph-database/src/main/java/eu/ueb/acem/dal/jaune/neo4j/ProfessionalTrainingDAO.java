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
import eu.ueb.acem.domain.beans.jaune.ProfessionalTraining;
import eu.ueb.acem.domain.beans.jaune.Resource;
import eu.ueb.acem.domain.beans.jaune.ResourceCategory;
import eu.ueb.acem.domain.beans.jaune.neo4j.ProfessionalTrainingNode;
import eu.ueb.acem.domain.beans.jaune.neo4j.ResourceCategoryNode;

/**
 * @author Grégoire Colbert
 * @since 2013-12-11
 * 
 */
@Repository("professionalTrainingDAO")
public class ProfessionalTrainingDAO extends AbstractDAO<ProfessionalTraining, ProfessionalTrainingNode> implements
		ResourceDAO<Long, ProfessionalTraining> {

	/**
	 * For serialization.
	 */
	private static final long serialVersionUID = -4037963363836909614L;

	@Inject
	private ProfessionalTrainingRepository repository;

	@Override
	protected final GenericRepository<ProfessionalTrainingNode> getRepository() {
		return repository;
	}

	@Override
	protected final void initializeCollections(ProfessionalTraining entity) {
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
	 * Returns the categories containing at least one "ProfessionalTraining"
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
	 * Returns the categories containing at least one "ProfessionalTraining"
	 * entity that the given person can see.
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
	public Collection<ProfessionalTraining> retrieveAllWithCategory(ResourceCategory category) {
		Iterable<ProfessionalTrainingNode> endResults = repository.getEntitiesWithCategory(category.getId());
		Collection<ProfessionalTraining> collection = new HashSet<ProfessionalTraining>();
		if (endResults.iterator() != null) {
			Iterator<ProfessionalTrainingNode> iterator = endResults.iterator();
			while (iterator.hasNext()) {
				ProfessionalTraining entity = iterator.next();
				initializeCollections(entity);
				collection.add(entity);
			}
		}
		return collection;
	}

	@Override
	public Collection<ProfessionalTraining> retrieveResourcesInCategoryForPerson(ResourceCategory category, Person person) {
		Iterable<ProfessionalTrainingNode> endResults = repository.getResourcesInCategoryForPerson(category.getId(), person.getId());
		Collection<ProfessionalTraining> collection = new HashSet<ProfessionalTraining>();
		if (endResults.iterator() != null) {
			Iterator<ProfessionalTrainingNode> iterator = endResults.iterator();
			while (iterator.hasNext()) {
				ProfessionalTraining entity = iterator.next();
				initializeCollections(entity);
				collection.add(entity);
			}
		}
		return collection;
	}

	@Override
	public Resource create(String name, String iconFileName) {
		return super.create(new ProfessionalTrainingNode(name, iconFileName));
	}

}
