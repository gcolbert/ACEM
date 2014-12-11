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
package eu.ueb.acem.dal.bleu;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.neo4j.template.Neo4jOperations;
import org.springframework.stereotype.Repository;

import eu.ueb.acem.dal.DAO;
import eu.ueb.acem.dal.TimeTicker;
import eu.ueb.acem.dal.bleu.neo4j.PedagogicalScenarioRepository;
import eu.ueb.acem.domain.beans.bleu.PedagogicalScenario;
import eu.ueb.acem.domain.beans.bleu.neo4j.PedagogicalScenarioNode;
import eu.ueb.acem.domain.beans.gris.Person;

/**
 * @author Grégoire Colbert
 * @since 2013-12-11
 * 
 */
@Repository("scenarioDAO")
public class PedagogicalScenarioDAO implements DAO<Long, PedagogicalScenario> {

	/**
	 * For serialization.
	 */
	private static final long serialVersionUID = -8699683589186956566L;

	/**
	 * For Logging.
	 */
	@SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory.getLogger(PedagogicalScenarioDAO.class);

	@Inject
	private Neo4jOperations neo4jOperations;

	@Inject
	private PedagogicalScenarioRepository repository;

	public PedagogicalScenarioDAO() {
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
	public PedagogicalScenario create(PedagogicalScenario entity) {
		entity.setCreationDate(TimeTicker.tick());
		return repository.save((PedagogicalScenarioNode) entity);
	}

	@Override
	public PedagogicalScenario retrieveById(Long id) {
		return (id != null) ? repository.findOne(id) : null;
	}

	@Override
	public PedagogicalScenario retrieveById(Long id, boolean initialize) {
		PedagogicalScenario entity = retrieveById(id);
		if (initialize) {
			neo4jOperations.fetch(entity.getPedagogicalActivities());
			neo4jOperations.fetch(entity.getAuthors());
			neo4jOperations.fetch(entity.getTeachingClasses());
		}
		return entity;
	}

	@Override
	public Collection<PedagogicalScenario> retrieveByName(String name) {
		Iterable<PedagogicalScenarioNode> nodes = repository.findByName(name);
		Collection<PedagogicalScenario> entities = new HashSet<PedagogicalScenario>();
		for (PedagogicalScenarioNode node : nodes) {
			entities.add(node);
		}
		return entities;
	}

	@Override
	public Collection<PedagogicalScenario> retrieveAll() {
		Iterable<PedagogicalScenarioNode> endResults = repository.findAll();
		Collection<PedagogicalScenario> collection = new HashSet<PedagogicalScenario>();
		if (endResults.iterator() != null) {
			Iterator<PedagogicalScenarioNode> iterator = endResults.iterator();
			while (iterator.hasNext()) {
				collection.add(iterator.next());
			}
		}
		return collection;
	}

	@Override
	public PedagogicalScenario update(PedagogicalScenario entity) {
		entity.setModificationDate(TimeTicker.tick());
		PedagogicalScenario updatedEntity = repository.save((PedagogicalScenarioNode) entity);
		return retrieveById(updatedEntity.getId(),true);
	}

	@Override
	public void delete(PedagogicalScenario entity) {
		repository.delete((PedagogicalScenarioNode) entity);
	}

	@Override
	public void deleteAll() {
		repository.deleteAll();
	}

	@Override
	public Long count() {
		return repository.count();
	}

	public Collection<PedagogicalScenario> retrieveScenariosWithAuthor(Person author) {
		Iterable<PedagogicalScenarioNode> endResults = repository.findScenariosWithAuthor(author.getId());
		Collection<PedagogicalScenario> collection = new HashSet<PedagogicalScenario>();
		if (endResults.iterator() != null) {
			Iterator<PedagogicalScenarioNode> iterator = endResults.iterator();
			while (iterator.hasNext()) {
				collection.add(retrieveById(iterator.next().getId(),true));
			}
		}
		return collection;
	}

}
