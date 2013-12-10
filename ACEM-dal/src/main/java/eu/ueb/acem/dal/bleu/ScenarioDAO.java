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

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import eu.ueb.acem.dal.DAO;
import eu.ueb.acem.dal.bleu.neo4j.ScenarioRepository;
import eu.ueb.acem.domain.beans.bleu.Scenario;
import eu.ueb.acem.domain.beans.bleu.neo4j.ScenarioNode;

/**
 * @author gcolbert @since 2013-12-11
 *
 */
@Repository("scenarioDAO")
public class ScenarioDAO implements DAO<Scenario>{

	/**
	 * For Logging.
	 */
	@SuppressWarnings("unused")
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private ScenarioRepository repository;
	
	public ScenarioDAO() {
		
	}
	
	@Override
	public void create(Scenario scenario) {
		repository.save((ScenarioNode) scenario);
	}
	
	@Override
	public Scenario retrieve(String nom) {
		return repository.findByPropertyValue("nom", nom);
	}
	
	@Override
	public Set<Scenario> retrieveAll() {
		Iterable<ScenarioNode> endResults = repository.findAll();
		Set<Scenario> set = new HashSet<Scenario>();
		if (endResults.iterator() != null) {
			Iterator<ScenarioNode> iterator = endResults.iterator();
			while (iterator.hasNext()) {
				set.add(iterator.next());
			}
		}
		return set;
	}
	
	@Override
	public Scenario update(Scenario scenario) {
		ScenarioNode scenarioNode = (ScenarioNode) scenario;
		return repository.save(scenarioNode);
	}
	
	@Override
	public void delete(Scenario scenario) {
		ScenarioNode scenarioNode = (ScenarioNode) scenario;
		repository.delete(scenarioNode);
	}
	
	@Override
	public void deleteAll() {
		repository.deleteAll();
	}

	@Override
	public Long count() {
		return repository.count();
	}
	
}
