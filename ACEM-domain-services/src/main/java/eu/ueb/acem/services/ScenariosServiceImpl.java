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
package eu.ueb.acem.services;

import java.util.Collection;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import eu.ueb.acem.dal.bleu.PedagogicalActivityDAO;
import eu.ueb.acem.dal.bleu.ScenarioDAO;
import eu.ueb.acem.domain.beans.bleu.PedagogicalActivity;
import eu.ueb.acem.domain.beans.bleu.PedagogicalScenario;
import eu.ueb.acem.domain.beans.bleu.neo4j.PedagogicalActivityNode;
import eu.ueb.acem.domain.beans.bleu.neo4j.PedagogicalScenarioNode;
import eu.ueb.acem.domain.beans.gris.Teacher;
import eu.ueb.acem.domain.beans.gris.Person;

/**
 * @author Grégoire Colbert
 * @since 2013-11-20
 * 
 */
@Service("scenariosService")
public class ScenariosServiceImpl implements ScenariosService {

	@SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory.getLogger(ScenariosServiceImpl.class);

	@Inject
	private ScenarioDAO scenarioDAO;

	@Inject
	private PedagogicalActivityDAO pedagogicalActivityDAO;

	@Inject
	private UsersService usersService;
	
	public ScenariosServiceImpl() {
	}

	@Override
	public Long countScenarios() {
		return scenarioDAO.count();
	}

	@Override
	public PedagogicalScenario createScenario(Teacher author, String name, String objective) {
		PedagogicalScenario scenario = new PedagogicalScenarioNode(name, objective);
		scenario.addAuthor(author);
		return scenarioDAO.create(scenario);
	}

	@Override
	public PedagogicalScenario retrieveScenario(Long id) {
		return scenarioDAO.retrieveById(id);
	}

	@Override
	public Collection<PedagogicalScenario> retrieveScenariosWithAuthor(Person author) {
		return scenarioDAO.retrieveScenariosWithAuthor(author);
	}

	@Override
	public PedagogicalScenario updateScenario(PedagogicalScenario scenario) {
		return scenarioDAO.update(scenario);
	}

	@Override
	public Boolean dissociateAuthorOrDeleteScenarioIfLastAuthor(Long idScenario, Long idAuthor) {
		if (scenarioDAO.exists(idScenario)) {
			PedagogicalScenario scenario = scenarioDAO.retrieveById(idScenario);
			if (scenario.getAuthors().size() > 1) {
				// It's not the last author, we just want to dissociate the author from the scenario
				Teacher author = usersService.retrieveTeacher(idAuthor);
				scenario.removeAuthor(author);
				scenario = scenarioDAO.update(scenario);
				return true;
			}
			else {
				// It's the last author, we really delete the scenario
				return deleteScenario(idScenario);
			}
		}
		else {
			return false;
		}
	}
	
	@Override
	public Boolean deleteScenario(Long id) {
		if (scenarioDAO.exists(id)) {
			PedagogicalScenario scenario = scenarioDAO.retrieveById(id);
			for (PedagogicalActivity pedagogicalActivity : scenario.getPedagogicalActivities()) {
				pedagogicalActivityDAO.delete(pedagogicalActivity);
			}
			scenarioDAO.delete(scenario);
		}
		return (!scenarioDAO.exists(id));
	}

	@Override
	public void deleteAllScenarios() {
		scenarioDAO.deleteAll();
	}

	@Override
	public Long countPedagogicalActivities() {
		return pedagogicalActivityDAO.count();
	}

	@Override
	public PedagogicalActivity createPedagogicalActivity(String name) {
		return pedagogicalActivityDAO.create(new PedagogicalActivityNode(name));
	}

	@Override
	public PedagogicalActivity retrievePedagogicalActivity(Long id) {
		return pedagogicalActivityDAO.retrieveById(id);
	}

	@Override
	public PedagogicalActivity updatePedagogicalActivity(PedagogicalActivity pedagogicalActivity) {
		return pedagogicalActivityDAO.update(pedagogicalActivity);
	}

	@Override
	public Boolean deletePedagogicalActivity(Long id) {
		if (pedagogicalActivityDAO.exists(id)) {
			pedagogicalActivityDAO.delete(pedagogicalActivityDAO.retrieveById(id));
		}
		return (!pedagogicalActivityDAO.exists(id));
	}

	@Override
	public void deleteAllPedagogicalActivities() {
		pedagogicalActivityDAO.deleteAll();
	}
	
}
