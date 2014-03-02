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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import eu.ueb.acem.dal.DAO;
import eu.ueb.acem.dal.bleu.neo4j.ScenarioRepository;
import eu.ueb.acem.domain.beans.bleu.Scenario;
import eu.ueb.acem.domain.beans.bleu.neo4j.ScenarioNode;
import eu.ueb.acem.domain.beans.gris.Personne;

/**
 * @author Grégoire Colbert @since 2013-12-11
 * 
 */
@Repository("scenarioDAO")
public class ScenarioDAO implements DAO<Long, Scenario> {

	/**
	 * For Logging.
	 */
	@SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory.getLogger(ScenarioDAO.class);

	@Autowired
	private ScenarioRepository repository;

	private Long tick() {
		return System.currentTimeMillis() / 1000;
	}

	public ScenarioDAO() {

	}

	@Override
	public Boolean exists(Long id) {
		return (id != null) ? repository.exists(id) : false;
	}

	@Override
	public Scenario create(Scenario scenario) {
		scenario.setCreationDate(tick());
		return repository.save((ScenarioNode) scenario);
	}

	@Override
	public Scenario retrieveById(Long id) {
		return (id != null) ? repository.findOne(id) : null;
	}

	@Override
	public Collection<Scenario> retrieveByName(String name) {
		Iterable<ScenarioNode> nodes = repository.findByName(name);
		Collection<Scenario> entities = new HashSet<Scenario>();
		for (ScenarioNode node : nodes) {
			entities.add(node);
		}
		return entities;
		//return repository.findByPropertyValue("name", name);
	}

	@Override
	public Collection<Scenario> retrieveAll() {
		Iterable<ScenarioNode> endResults = repository.findAll();
		Collection<Scenario> collection = new HashSet<Scenario>();
		if (endResults.iterator() != null) {
			Iterator<ScenarioNode> iterator = endResults.iterator();
			while (iterator.hasNext()) {
				collection.add(iterator.next());
			}
		}
		return collection;
	}

	@Override
	public Scenario update(Scenario scenario) {
		scenario.setModificationDate(tick());
		return repository.save((ScenarioNode) scenario);
	}

	@Override
	public void delete(Scenario scenario) {
		repository.delete((ScenarioNode) scenario);
	}

	@Override
	public void deleteAll() {
		repository.deleteAll();
	}

	@Override
	public Long count() {
		return repository.count();
	}

	public Collection<Scenario> retrieveScenariosWithAuthor(Personne author) {
		Iterable<ScenarioNode> endResults = repository.findScenariosWithAuthor(author.getId());
		Collection<Scenario> collection = new HashSet<Scenario>();
		if (endResults.iterator() != null) {
			Iterator<ScenarioNode> iterator = endResults.iterator();
			while (iterator.hasNext()) {
				collection.add(iterator.next());
			}
		}
		return collection;
	}

}
