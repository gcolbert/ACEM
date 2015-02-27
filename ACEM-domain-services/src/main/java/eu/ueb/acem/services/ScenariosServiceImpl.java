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

import java.io.Serializable;
import java.util.Collection;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import eu.ueb.acem.dal.DAO;
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
public class ScenariosServiceImpl implements ScenariosService, Serializable {

	/**
	 * For serialization.
	 */
	private static final long serialVersionUID = 7105659785242719915L;

	@Inject
	private PedagogicalScenarioDAO<Long, PedagogicalScenario> pedagogicalScenarioDAOImpl;

	@Inject
	private DAO<Long, PedagogicalActivity> pedagogicalActivityDAO;

	@Inject
	private UsersService usersService;

	public ScenariosServiceImpl() {
	}

	@Override
	public Long countScenarios() {
		return pedagogicalScenarioDAOImpl.count();
	}

	@Override
	public PedagogicalScenario createScenario(Teacher author, String name, String objective) {
		PedagogicalScenario scenario = new PedagogicalScenarioNode(name, objective);
		scenario.getAuthors().add(author);
		scenario = pedagogicalScenarioDAOImpl.create(scenario);
		author.getScenarios().add(scenario);
		author = usersService.updateTeacher(author);
		return scenario;
	}

	@Override
	public PedagogicalScenario retrievePedagogicalScenario(Long id, boolean initialize) {
		return pedagogicalScenarioDAOImpl.retrieveById(id, initialize);
	}

	@Override
	public Collection<PedagogicalScenario> retrieveScenariosWithAuthor(Person author) {
		return pedagogicalScenarioDAOImpl.retrieveScenariosWithAuthor(author);
	}

	@Override
	public PedagogicalScenario updateScenario(PedagogicalScenario pedagogicalScenario) {
		return pedagogicalScenarioDAOImpl.update(pedagogicalScenario);
	}

	@Override
	public Boolean dissociateAuthorOrDeleteScenarioIfLastAuthor(Long idScenario, Long idAuthor) {
		if (pedagogicalScenarioDAOImpl.exists(idScenario)) {
			PedagogicalScenario scenario = pedagogicalScenarioDAOImpl.retrieveById(idScenario);
			if (scenario.getAuthors().size() > 1) {
				// It's not the last author, we just want to dissociate the author from the scenario
				Teacher author = usersService.retrieveTeacher(idAuthor);
				scenario.getAuthors().remove(author);
				author.getScenarios().remove(scenario);
				scenario = pedagogicalScenarioDAOImpl.update(scenario);
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
		if (pedagogicalScenarioDAOImpl.exists(id)) {
			PedagogicalScenario scenario = pedagogicalScenarioDAOImpl.retrieveById(id);
			for (PedagogicalActivity pedagogicalActivity : scenario.getPedagogicalActivities()) {
				pedagogicalActivityDAO.delete(pedagogicalActivity);
			}
			pedagogicalScenarioDAOImpl.delete(scenario);
		}
		return !pedagogicalScenarioDAOImpl.exists(id);
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
		return pedagogicalActivityDAO.update(pedagogicalActivity);
	}

	@Override
	public Boolean deletePedagogicalActivity(Long id) {
		if (pedagogicalActivityDAO.exists(id)) {
			pedagogicalActivityDAO.delete(pedagogicalActivityDAO.retrieveById(id));
		}
		return !pedagogicalActivityDAO.exists(id);
	}

}
