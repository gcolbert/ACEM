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
import eu.ueb.acem.dal.jaune.neo4j.ProfessionalTrainingRepository;
import eu.ueb.acem.domain.beans.jaune.ProfessionalTraining;
import eu.ueb.acem.domain.beans.jaune.ResourceCategory;
import eu.ueb.acem.domain.beans.jaune.neo4j.ProfessionalTrainingNode;
import eu.ueb.acem.domain.beans.jaune.neo4j.ResourceCategoryNode;

/**
 * @author Grégoire Colbert
 * @since 2013-12-11
 * 
 */
@Repository("professionalTrainingDAO")
public class ProfessionalTrainingDAO implements DAO<Long, ProfessionalTraining> {

	/**
	 * For serialization.
	 */
	private static final long serialVersionUID = -4037963363836909614L;

	@SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory.getLogger(ProfessionalTrainingDAO.class);

	@Inject
	private ProfessionalTrainingRepository repository;

	public ProfessionalTrainingDAO() {

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
	public ProfessionalTraining create(ProfessionalTraining entity) {
		return repository.save((ProfessionalTrainingNode) entity);
	}

	@Override
	public ProfessionalTraining retrieveById(Long id) {
		return (id != null) ? repository.findOne(id) : null;
	}

	@Override
	public Collection<ProfessionalTraining> retrieveByName(String name) {
		Iterable<ProfessionalTrainingNode> nodes = repository.findByName(name);
		Collection<ProfessionalTraining> entities = new HashSet<ProfessionalTraining>();
		for (ProfessionalTrainingNode node : nodes) {
			entities.add(node);
		}
		return entities;
	}

	@Override
	public Collection<ProfessionalTraining> retrieveAll() {
		Iterable<ProfessionalTrainingNode> endResults = repository.findAll();
		Collection<ProfessionalTraining> collection = new HashSet<ProfessionalTraining>();
		if (endResults.iterator() != null) {
			Iterator<ProfessionalTrainingNode> iterator = endResults.iterator();
			while (iterator.hasNext()) {
				collection.add(iterator.next());
			}
		}
		return collection;
	}

	@Override
	public ProfessionalTraining update(ProfessionalTraining entity) {
		return repository.save((ProfessionalTrainingNode) entity);
	}

	@Override
	public void delete(ProfessionalTraining entity) {
		repository.delete((ProfessionalTrainingNode) entity);
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
	
	public Collection<ProfessionalTraining> retrieveAllWithCategory(ResourceCategory category) {
		Iterable<ProfessionalTrainingNode> endResults = repository.getEntitiesWithCategory(category.getId());
		Collection<ProfessionalTraining> collection = new HashSet<ProfessionalTraining>();
		if (endResults.iterator() != null) {
			Iterator<ProfessionalTrainingNode> iterator = endResults.iterator();
			while (iterator.hasNext()) {
				collection.add(iterator.next());
			}
		}
		return collection;
	}
	
}
