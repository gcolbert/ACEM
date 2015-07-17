/**
 *     Copyright Université Européenne de Bretagne 2012-2015
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
import java.util.Set;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import eu.ueb.acem.dal.common.bleu.PedagogicalActivityDAO;
import eu.ueb.acem.dal.common.bleu.PedagogicalScenarioDAO;
import eu.ueb.acem.dal.common.bleu.PedagogicalSequenceDAO;
import eu.ueb.acem.dal.common.bleu.PedagogicalSessionDAO;
import eu.ueb.acem.domain.beans.bleu.PedagogicalActivity;
import eu.ueb.acem.domain.beans.bleu.PedagogicalScenario;
import eu.ueb.acem.domain.beans.bleu.PedagogicalSequence;
import eu.ueb.acem.domain.beans.bleu.PedagogicalSession;
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
	private PedagogicalScenarioDAO<Long> pedagogicalScenarioDAO;

	@Inject
	private PedagogicalSequenceDAO<Long> pedagogicalSequenceDAO;

	@Inject
	private PedagogicalSessionDAO<Long> pedagogicalSessionDAO;

	@Inject
	private PedagogicalActivityDAO<Long> pedagogicalActivityDAO;

	@Inject
	private UsersService usersService;

	public ScenariosServiceImpl() {
	}

	/* ***********
        SCENARIOS
       *********** */
	@Override
	public Long countScenarios() {
		return pedagogicalScenarioDAO.count();
	}

	@Override
	public PedagogicalScenario createScenario(Teacher author, String name, String objective) {
		PedagogicalScenario scenario = pedagogicalScenarioDAO.create(name, objective);
		scenario.getAuthors().add(author);
		author.getPedagogicalScenarios().add(scenario);
		scenario = pedagogicalScenarioDAO.update(scenario);
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
		return pedagogicalScenarioDAO.update(pedagogicalScenario);
	}

	@Override
	public Boolean dissociateAuthorOrDeleteScenarioIfLastAuthor(Long idScenario, Long idAuthor) {
		if (pedagogicalScenarioDAO.exists(idScenario)) {
			PedagogicalScenario scenario = pedagogicalScenarioDAO.retrieveById(idScenario);
			if (scenario.getAuthors().size() > 1) {
				// It's not the last author, we just want to dissociate the author from the scenario
				Teacher author = usersService.retrieveTeacher(idAuthor);
				scenario.getAuthors().remove(author);
				author.getPedagogicalScenarios().remove(scenario);
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
			Set<PedagogicalSequence> firstPedagogicalSequences = scenario.getFirstPedagogicalSequences();
			for (PedagogicalSequence firstSequence : firstPedagogicalSequences) {
				PedagogicalSequence pedagogicalSequence = firstSequence;
				while (pedagogicalSequence != null) {
					deletePedagogicalSequence(pedagogicalSequence.getId());
					pedagogicalSequence = pedagogicalSequence.getNextPedagogicalSequence();
				}
			}
			pedagogicalScenarioDAO.delete(scenario);
		}
		return !pedagogicalScenarioDAO.exists(id);
	}

	/* ***********
        SEQUENCES
       *********** */
	@Override
	public Long countPedagogicalSequences() {
		return pedagogicalSequenceDAO.count();
	}

	@Override
	public PedagogicalSequence createPedagogicalSequence(String name) {
		return pedagogicalSequenceDAO.create(name);
	}

	@Override
	public PedagogicalSequence retrievePedagogicalSequence(Long id, boolean initialize) {
		return pedagogicalSequenceDAO.retrieveById(id, initialize);
	}

	@Override
	public PedagogicalSequence updatePedagogicalSequence(PedagogicalSequence pedagogicalSequence) {
		return pedagogicalSequenceDAO.update(pedagogicalSequence);
	}

	@Override
	public Boolean deletePedagogicalSequence(Long id) {
		if (pedagogicalSequenceDAO.exists(id)) {
			PedagogicalSequence pedagogicalSequence = pedagogicalSequenceDAO.retrieveById(id);
			PedagogicalSession pedagogicalSession = pedagogicalSequence.getFirstPedagogicalSession();
			while (pedagogicalSession != null) {
				// We delete a session only if it is associated with exactly 1 sequence
				// (because sequences are running in parallel, we allow the deletion of
				// a sequence but have to keep the sessions associated with another sequence)
				if (pedagogicalSession.getPedagogicalSequences().size() == 1) {
					deletePedagogicalSession(pedagogicalSession.getId());
				}
				pedagogicalSession = pedagogicalSession.getNextPedagogicalSession();
			}
			pedagogicalSequenceDAO.delete(pedagogicalSequence);
		}
		return !pedagogicalSequenceDAO.exists(id);
	}

	/* **********
        SESSIONS
	   ********** */
	@Override
	public Long countPedagogicalSessions() {
		return pedagogicalSessionDAO.count();
	}

	@Override
	public PedagogicalSession createPedagogicalSession(String name) {
		return pedagogicalSessionDAO.create(name);
	}

	@Override
	public PedagogicalSession retrievePedagogicalSession(Long id, boolean initialize) {
		return pedagogicalSessionDAO.retrieveById(id, initialize);
	}

	@Override
	public PedagogicalSession updatePedagogicalSession(PedagogicalSession pedagogicalSession) {
		return pedagogicalSessionDAO.update(pedagogicalSession);
	}

	@Override
	public Boolean deletePedagogicalSession(Long id) {
		if (pedagogicalSessionDAO.exists(id)) {
			PedagogicalSession pedagogicalSession = pedagogicalSessionDAO.retrieveById(id);
			PedagogicalActivity pedagogicalActivity = pedagogicalSession.getFirstPedagogicalActivity();
			while (pedagogicalActivity != null) {
				deletePedagogicalActivity(pedagogicalActivity.getId());
				pedagogicalActivity = pedagogicalActivity.getNextPedagogicalActivity();
			}
			pedagogicalSessionDAO.delete(pedagogicalSession);
		}
		return !pedagogicalSessionDAO.exists(id);
	}

	/* ************
        ACTIVITIES
       ************ */
	@Override
	public Long countPedagogicalActivities() {
		return pedagogicalActivityDAO.count();
	}

	@Override
	public PedagogicalActivity createPedagogicalActivity(String name) {
		return pedagogicalActivityDAO.create(name);
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
