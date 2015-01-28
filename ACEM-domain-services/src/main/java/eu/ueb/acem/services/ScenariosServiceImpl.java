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
import eu.ueb.acem.dal.bleu.PedagogicalScenarioDAO;
import eu.ueb.acem.domain.beans.bleu.PedagogicalActivity;
import eu.ueb.acem.domain.beans.bleu.PedagogicalScenario;
import eu.ueb.acem.domain.beans.bleu.neo4j.PedagogicalActivityNode;
import eu.ueb.acem.domain.beans.bleu.neo4j.PedagogicalScenarioNode;
import eu.ueb.acem.domain.beans.gris.Person;
import eu.ueb.acem.domain.beans.gris.Teacher;

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
	private PedagogicalScenarioDAO pedagogicalScenarioDAO;

	@Inject
	private PedagogicalActivityDAO pedagogicalActivityDAO;

	@Inject
	private UsersService usersService;

	public ScenariosServiceImpl() {
	}

	@Override
	public Long countScenarios() {
		return pedagogicalScenarioDAO.count();
	}

	@Override
	public PedagogicalScenario createScenario(Teacher author, String name, String objective) {
		PedagogicalScenario scenario = new PedagogicalScenarioNode(name, objective);
		scenario.getAuthors().add(author);
		author.getScenarios().add(scenario);
		scenario = pedagogicalScenarioDAO.create(scenario);
		scenario = pedagogicalScenarioDAO.retrieveById(scenario.getId(), true);
		author = usersService.updateTeacher(author);
		return scenario;
	}

	@Override
	public PedagogicalScenario retrievePedagogicalScenario(Long id, boolean initialize) {
		return pedagogicalScenarioDAO.retrieveById(id, initialize);
	}

	@Override
	public Collection<PedagogicalScenario> retrieveScenariosWithAuthor(Person author) {
		return pedagogicalScenarioDAO.retrieveScenariosWithAuthor(author);
	}

	@Override
	public PedagogicalScenario updateScenario(PedagogicalScenario pedagogicalScenario) {
		PedagogicalScenario updatedEntity = pedagogicalScenarioDAO.update(pedagogicalScenario);
		return updatedEntity;
	}

	@Override
	public Boolean dissociateAuthorOrDeleteScenarioIfLastAuthor(Long idScenario, Long idAuthor) {
		if (pedagogicalScenarioDAO.exists(idScenario)) {
			PedagogicalScenario scenario = pedagogicalScenarioDAO.retrieveById(idScenario);
			if (scenario.getAuthors().size() > 1) {
				// It's not the last author, we just want to dissociate the author from the scenario
				Teacher author = usersService.retrieveTeacher(idAuthor);
				scenario.getAuthors().remove(author);
				author.getScenarios().remove(scenario);
				scenario = pedagogicalScenarioDAO.update(scenario);
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
		if (pedagogicalScenarioDAO.exists(id)) {
			PedagogicalScenario scenario = pedagogicalScenarioDAO.retrieveById(id);
			for (PedagogicalActivity pedagogicalActivity : scenario.getPedagogicalActivities()) {
				pedagogicalActivityDAO.delete(pedagogicalActivity);
			}
			pedagogicalScenarioDAO.delete(scenario);
		}
		return (!pedagogicalScenarioDAO.exists(id));
	}

	@Override
	public void deleteAllScenarios() {
		pedagogicalScenarioDAO.deleteAll();
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
	public PedagogicalActivity retrievePedagogicalActivity(Long id, boolean initialize) {
		return pedagogicalActivityDAO.retrieveById(id, initialize);
	}

	@Override
	public PedagogicalActivity updatePedagogicalActivity(PedagogicalActivity pedagogicalActivity) {
		PedagogicalActivity updatedEntity = pedagogicalActivityDAO.update(pedagogicalActivity);
		return updatedEntity;
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
