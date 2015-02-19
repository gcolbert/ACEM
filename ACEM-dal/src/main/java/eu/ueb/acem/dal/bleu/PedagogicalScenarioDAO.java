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
import org.springframework.stereotype.Repository;

import eu.ueb.acem.dal.AbstractDAO;
import eu.ueb.acem.dal.GenericRepository;
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
public class PedagogicalScenarioDAO extends AbstractDAO<PedagogicalScenario, PedagogicalScenarioNode> {

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
	private PedagogicalScenarioRepository repository;

	@Override
	protected final GenericRepository<PedagogicalScenarioNode> getRepository() {
		return repository;
	}

	@Override
	protected final void initializeCollections(PedagogicalScenario entity) {
		neo4jOperations.fetch(entity.getPedagogicalActivities());
		neo4jOperations.fetch(entity.getAuthors());
		neo4jOperations.fetch(entity.getClasses());
	}

	public Collection<PedagogicalScenario> retrieveScenariosWithAuthor(Person author) {
		Iterable<PedagogicalScenarioNode> endResults = repository.findScenariosWithAuthor(author.getId());
		Collection<PedagogicalScenario> collection = new HashSet<PedagogicalScenario>();
		if (endResults.iterator() != null) {
			Iterator<PedagogicalScenarioNode> iterator = endResults.iterator();
			while (iterator.hasNext()) {
				PedagogicalScenario entity = iterator.next();
				initializeCollections(entity);
				collection.add(entity);
			}
		}
		return collection;
	}

	@Override
	public PedagogicalScenario create(PedagogicalScenario entity) {
		entity.setCreationDate(TimeTicker.tick());
		return super.create(entity);
	}

	@Override
	public PedagogicalScenario update(PedagogicalScenario entity) {
		entity.setModificationDate(TimeTicker.tick());
		return super.update(entity);
	}

}
