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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import eu.ueb.acem.dal.bleu.PedagogicalActivityDAO;
import eu.ueb.acem.dal.bleu.ScenarioDAO;
import eu.ueb.acem.domain.beans.bleu.ActivitePedagogique;
import eu.ueb.acem.domain.beans.bleu.Scenario;
import eu.ueb.acem.domain.beans.bleu.neo4j.ActivitePedagogiqueNode;
import eu.ueb.acem.domain.beans.bleu.neo4j.ScenarioNode;
import eu.ueb.acem.domain.beans.gris.Enseignant;
import eu.ueb.acem.domain.beans.gris.Personne;

@Service("scenariosService")
public class ScenariosServiceImpl implements ScenariosService {

	@SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory.getLogger(ScenariosServiceImpl.class);

	@Autowired
	private ScenarioDAO scenarioDAO;

	@Autowired
	private PedagogicalActivityDAO pedagogicalActivityDAO;

	public ScenariosServiceImpl() {
	}

	@Override
	public Long countScenarios() {
		return scenarioDAO.count();
	}

	@Override
	public Scenario createScenario(Enseignant author, String name, String objective) {
		Scenario scenario = new ScenarioNode(name, objective);
		scenario.addAuthor(author);
		return scenarioDAO.create(scenario);
	}

	@Override
	public Scenario retrieveScenario(Long id) {
		return scenarioDAO.retrieveById(id);
	}

	@Override
	public Collection<Scenario> retrieveScenariosWithAuthor(Personne author) {
		return scenarioDAO.retrieveScenariosWithAuthor(author);
	}

	@Override
	public Scenario updateScenario(Scenario scenario) {
		return scenarioDAO.update(scenario);
	}

	@Override
	public Boolean deleteScenario(Long id) {
		if (scenarioDAO.exists(id)) {
			Scenario scenario = scenarioDAO.retrieveById(id);
			for (ActivitePedagogique pedagogicalActivity : scenario.getPedagogicalActivities()) {
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
	public ActivitePedagogique createPedagogicalActivity(String name) {
		return pedagogicalActivityDAO.create(new ActivitePedagogiqueNode(name));
	}

	@Override
	public ActivitePedagogique retrievePedagogicalActivity(Long id) {
		return pedagogicalActivityDAO.retrieveById(id);
	}

	@Override
	public ActivitePedagogique updatePedagogicalActivity(ActivitePedagogique pedagogicalActivity) {
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
