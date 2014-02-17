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
import eu.ueb.acem.dal.bleu.neo4j.ReponseRepository;
import eu.ueb.acem.domain.beans.bleu.Reponse;
import eu.ueb.acem.domain.beans.bleu.Scenario;
import eu.ueb.acem.domain.beans.bleu.neo4j.ReponseNode;
import eu.ueb.acem.domain.beans.bleu.neo4j.ScenarioNode;

/**
 * @author Grégoire Colbert @since 2013-11-26
 * 
 */
@Repository("answerDAO")
public class ReponseDAO implements DAO<Long, Reponse> {

	/**
	 * For Logging.
	 */
	@SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory.getLogger(ReponseDAO.class);

	@Autowired
	private ReponseRepository repository;

	public ReponseDAO() {

	}

	@Override
	public Boolean exists(Long id) {
		return (id != null) ? repository.exists(id) : false;
	}

	@Override
	public Reponse create(Reponse reponse) {
		return repository.save((ReponseNode) reponse);
	}

	@Override
	public Reponse retrieveById(Long id) {
		return (id != null) ? repository.findOne(id) : null;
	}

	@Override
	public Reponse retrieveByName(String name) {
		return repository.findByPropertyValue("name", name);
	}

	@Override
	public Set<Reponse> retrieveAll() {
		Iterable<ReponseNode> endResults = repository.findAll();
		Set<Reponse> collection = new HashSet<Reponse>();
		if (endResults.iterator() != null) {
			Iterator<ReponseNode> iterator = endResults.iterator();
			while (iterator.hasNext()) {
				collection.add(iterator.next());
			}
		}
		return collection;
	}

	@Override
	public Reponse update(Reponse reponse) {
		return repository.save((ReponseNode) reponse);
	}

	@Override
	public void delete(Reponse reponse) {
		repository.delete((ReponseNode) reponse);
	}

	@Override
	public void deleteAll() {
		repository.deleteAll();
	}

	@Override
	public Long count() {
		return repository.count();
	}

	public Set<Scenario> retrieveScenariosRelatedToAnswer(Long id) {
		Set<ScenarioNode> nodes = repository.findScenariosRelatedToAnswer(id);
		Set<Scenario> scenarios = new HashSet<Scenario>();
		for (ScenarioNode node : nodes) {
			scenarios.add(node);
		}
		return scenarios;
	}

}
