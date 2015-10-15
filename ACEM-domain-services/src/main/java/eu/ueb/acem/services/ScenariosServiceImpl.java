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

import java.util.Collection;

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
import eu.ueb.acem.services.exceptions.ServiceException;

/**
 * Implementation of ScenariosService.
 * 
 * @author Grégoire Colbert
 * @since 2013-11-20
 */
@Service("scenariosService")
public class ScenariosServiceImpl implements ScenariosService {

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
	public Long countPedagogicalScenarios() {
		return pedagogicalScenarioDAO.count();
	}

	@Override
	public PedagogicalScenario createPedagogicalScenario(Teacher author, String name, String objective) {
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
	public PedagogicalScenario updatePedagogicalScenario(PedagogicalScenario pedagogicalScenario) {
		return pedagogicalScenarioDAO.update(pedagogicalScenario);
	}

	/**
	 * This method dissociates the given scenario from the given author. The
	 * scenario is deleted if it doesn't have any author left.
	 * 
	 * @param idScenario
	 *            The id of the scenario
	 * @param idAuthor
	 *            The id of the author
	 * @return true if the scenario exists, false otherwise
	 */
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
				// We dissociated the scenario from the last author, we really delete the scenario
				return deletePedagogicalScenario(idScenario);
			}
		}
		else {
			return false;
		}
	}

	@Override
	public Boolean deletePedagogicalScenario(Long id) {
		if (pedagogicalScenarioDAO.exists(id)) {
			PedagogicalScenario scenario = pedagogicalScenarioDAO.retrieveById(id);
			Collection<PedagogicalSequence> firstPedagogicalSequences = pedagogicalSequenceDAO.retrieveFirstSequencesOfScenario(scenario);
			for (PedagogicalSequence sequence : firstPedagogicalSequences) {
				while (sequence != null) {
					deletePedagogicalSequence(sequence.getId());
					sequence = sequence.getNextPedagogicalSequence();
				}
			}
			pedagogicalScenarioDAO.delete(scenario);
		}
		return !pedagogicalScenarioDAO.exists(id);
	}

	@Override
	public Collection<PedagogicalSequence> getFirstPedagogicalSequencesOfScenario(PedagogicalScenario pedagogicalScenario) {
		return pedagogicalSequenceDAO.retrieveFirstSequencesOfScenario(pedagogicalScenario);
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
			PedagogicalSequence sequence = pedagogicalSequenceDAO.retrieveById(id);
			Collection<PedagogicalSession> firstPedagogicalSessions = pedagogicalSessionDAO.retrieveFirstSessionsOfSequence(sequence);
			for (PedagogicalSession session : firstPedagogicalSessions) {
				while (session != null) {
					deletePedagogicalSession(session.getId());
					session = session.getNextPedagogicalSession();
				}
			}
			pedagogicalSequenceDAO.delete(sequence);
		}
		return !pedagogicalSequenceDAO.exists(id);
	}

	@Override
	public Collection<PedagogicalSession> getFirstPedagogicalSessionsOfSequence(PedagogicalSequence pedagogicalSequence) {
		return pedagogicalSessionDAO.retrieveFirstSessionsOfSequence(pedagogicalSequence);
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
			Collection<PedagogicalActivity> firstPedagogicalActivities = pedagogicalActivityDAO.retrieveFirstActivitiesOfSession(pedagogicalSession);
			for (PedagogicalActivity activity : firstPedagogicalActivities) {
				while (activity != null) {
					deletePedagogicalActivity(activity.getId());
					activity = activity.getNextPedagogicalActivity();
				}
			}
			pedagogicalSessionDAO.delete(pedagogicalSession);
		}
		return !pedagogicalSessionDAO.exists(id);
	}

	@Override
	public Collection<PedagogicalActivity> getFirstPedagogicalActivitiesOfSession(PedagogicalSession pedagogicalSession) {
		return pedagogicalActivityDAO.retrieveFirstActivitiesOfSession(pedagogicalSession);
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

	/**
	 * This method deletes the PedagogicalActivity whose id is given. It takes
	 * care of maintaining the consistency of the existing chain of activities
	 * (that uses the PedagogicalUnit.getNext()) of the owning
	 * PedagogicalSessions and PedagogicalSequences.
	 * 
	 * @param id
	 *            The id of the PedagogicalActivity to delete.
	 * 
	 * @return true if the PedagogicalActivity doesn't exist after the call,
	 *         false otherwise.
	 */
	@Override
	public Boolean deletePedagogicalActivity(Long id) {
		if (pedagogicalActivityDAO.exists(id)) {
			PedagogicalActivity pedagogicalActivityToDelete = pedagogicalActivityDAO.retrieveById(id);

			// We search this activity from its PedagogicalSequence and dissociate the objects
			if (pedagogicalActivityToDelete.getPedagogicalSequence() != null) {
				PedagogicalSequence pedagogicalSequence = pedagogicalActivityToDelete.getPedagogicalSequence();
				pedagogicalSequence.getPedagogicalActivities().remove(pedagogicalActivityToDelete);
				pedagogicalActivityToDelete.setPedagogicalSequence(null);
				pedagogicalSequence = pedagogicalSequenceDAO.update(pedagogicalSequence);
			}

			// We search this activity in its PedagogicalSession and dissociate the objects
			if (pedagogicalActivityToDelete.getPedagogicalSession() != null) {
				PedagogicalSession pedagogicalSession = pedagogicalActivityToDelete.getPedagogicalSession();
				pedagogicalSession.getPedagogicalActivities().remove(pedagogicalActivityToDelete);
				pedagogicalActivityToDelete.setPedagogicalSession(null);
				pedagogicalSession = pedagogicalSessionDAO.update(pedagogicalSession);
			}

			// We maintain the chain of Activities
			if (pedagogicalActivityToDelete.getPreviousPedagogicalActivity()!=null) {
				PedagogicalActivity previousPedagogicalActivity = pedagogicalActivityToDelete.getPreviousPedagogicalActivity();
				previousPedagogicalActivity.setNextPedagogicalActivity(pedagogicalActivityToDelete.getNextPedagogicalActivity());
				previousPedagogicalActivity = pedagogicalActivityDAO.update(previousPedagogicalActivity);
			}
			if (pedagogicalActivityToDelete.getNextPedagogicalActivity()!=null) {
				PedagogicalActivity nextPedagogicalActivity = pedagogicalActivityToDelete.getNextPedagogicalActivity();
				nextPedagogicalActivity.setPreviousPedagogicalActivity(pedagogicalActivityToDelete.getPreviousPedagogicalActivity());
				nextPedagogicalActivity = pedagogicalActivityDAO.update(nextPedagogicalActivity);
			}

			// Now, we can safely delete the activity to delete
			pedagogicalActivityDAO.delete(pedagogicalActivityToDelete);
		}
		return !pedagogicalActivityDAO.exists(id);
	}

	/**
	 * This method inserts a PedagogicalActivity (pedagogicalActivityToAdd) in a
	 * Session (pedagogicalSession). If "previousPedagogicalActivity" is not
	 * null, then "pedagogicalActivityToAdd" is inserted after
	 * "previousPedagogicalActivity". If "nextPedagogicalActivity" is not null,
	 * then "pedagogicalActivityToAdd" is inserted before
	 * "nextPedagogicalActivity". If both "previous" and "next" are specified,
	 * then they must be chained together already, otherwise an exception will
	 * be thrown.
	 * 
	 * @param pedagogicalActivityToAdd
	 *            the PedagogicalActivity to add to the PedagogicalSession
	 * @param pedagogicalSession
	 *            the PedagogicalSession that will contain
	 *            pedagogicalActivityToAdd
	 * @param previousPedagogicalActivity
	 *            the PedagogicalActivity preceding pedagogicalActivityToAdd
	 *            (can be null, but if not null, it must be referenced by
	 *            "pedagogicalSession" otherwise an exception will be thrown)
	 * @param nextPedagogicalActivity
	 *            the PedagogicalActivity that comes after
	 *            pedagogicalActivityToAdd (can be null, but if not null, it
	 *            must be referenced by "pedagogicalSession" otherwise an
	 *            exception will be thrown)
	 */
	@Override
	public Boolean addActivityToSession(PedagogicalActivity pedagogicalActivityToAdd,
			PedagogicalSession pedagogicalSession, PedagogicalActivity previousPedagogicalActivity,
			PedagogicalActivity nextPedagogicalActivity) throws ServiceException {
		// Parameter validation
		if (previousPedagogicalActivity != null
				&& !previousPedagogicalActivity.getPedagogicalSession().equals(pedagogicalSession)) {
			throw new ServiceException("EXCEPTION.PREVIOUS_UNIT_BAD_OWNER.TITLE");
		}
		if (nextPedagogicalActivity != null
				&& !nextPedagogicalActivity.getPedagogicalSession().equals(pedagogicalSession)) {
			throw new ServiceException("EXCEPTION.NEXT_UNIT_BAD_OWNER.TITLE");
		}
		if ((nextPedagogicalActivity != null && nextPedagogicalActivity.getPreviousPedagogicalActivity() != null && !nextPedagogicalActivity
				.getPreviousPedagogicalActivity().equals(previousPedagogicalActivity))
				|| (previousPedagogicalActivity != null
						&& previousPedagogicalActivity.getNextPedagogicalActivity() != null && !previousPedagogicalActivity
						.getNextPedagogicalActivity().equals(nextPedagogicalActivity))) {
			throw new ServiceException("EXCEPTION.PREVIOUS_AND_NEXT_UNITS_ARE_NOT_CHAINED.TITLE");
		}
		if (pedagogicalSession.getDuration() != null && pedagogicalActivityToAdd.getDuration() != null
				&& (pedagogicalSession.getDuration() < pedagogicalActivityToAdd.getDuration())) {
			throw new ServiceException("EXCEPTION.PEDAGOGICAL_UNIT_DURATION.TITLE");
		}

		// We set the container
		pedagogicalActivityToAdd.setPedagogicalSession(pedagogicalSession);
		pedagogicalSession.getPedagogicalActivities().add(pedagogicalActivityToAdd);
		pedagogicalSession = pedagogicalSessionDAO.update(pedagogicalSession);

		// We maintain the chain of activities
		if ((previousPedagogicalActivity == null) && (nextPedagogicalActivity != null)) {
			pedagogicalActivityToAdd.setNextPedagogicalActivity(nextPedagogicalActivity);
			nextPedagogicalActivity = pedagogicalActivityDAO.update(nextPedagogicalActivity);
			pedagogicalActivityToAdd = pedagogicalActivityDAO.update(pedagogicalActivityToAdd);
		}
		else if ((previousPedagogicalActivity != null) && (nextPedagogicalActivity == null)) {
			pedagogicalActivityToAdd.setPreviousPedagogicalActivity(previousPedagogicalActivity);
			previousPedagogicalActivity = pedagogicalActivityDAO.update(previousPedagogicalActivity);
			pedagogicalActivityToAdd = pedagogicalActivityDAO.update(pedagogicalActivityToAdd);
		}
		else if ((previousPedagogicalActivity != null) && (nextPedagogicalActivity != null)) {
			pedagogicalActivityToAdd.setNextPedagogicalActivity(nextPedagogicalActivity);
			pedagogicalActivityToAdd.setPreviousPedagogicalActivity(previousPedagogicalActivity);
			nextPedagogicalActivity = pedagogicalActivityDAO.update(nextPedagogicalActivity);
			previousPedagogicalActivity = pedagogicalActivityDAO.update(previousPedagogicalActivity);
			pedagogicalActivityToAdd = pedagogicalActivityDAO.update(pedagogicalActivityToAdd);
		}

		return pedagogicalSession.getPedagogicalActivities().contains(pedagogicalActivityToAdd)
				&& pedagogicalActivityToAdd.getPedagogicalSession().equals(pedagogicalSession)
				&& (pedagogicalActivityToAdd.getPreviousPedagogicalActivity()==null || pedagogicalActivityToAdd.getPreviousPedagogicalActivity().equals(previousPedagogicalActivity))
				&& (pedagogicalActivityToAdd.getNextPedagogicalActivity()==null || pedagogicalActivityToAdd.getNextPedagogicalActivity().equals(nextPedagogicalActivity));
	}

}
