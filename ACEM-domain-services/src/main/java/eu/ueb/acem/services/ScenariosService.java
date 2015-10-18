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

import eu.ueb.acem.domain.beans.bleu.PedagogicalActivity;
import eu.ueb.acem.domain.beans.bleu.PedagogicalScenario;
import eu.ueb.acem.domain.beans.bleu.PedagogicalSequence;
import eu.ueb.acem.domain.beans.bleu.PedagogicalSession;
import eu.ueb.acem.domain.beans.bleu.TeachingMode;
import eu.ueb.acem.domain.beans.gris.Person;
import eu.ueb.acem.domain.beans.gris.Teacher;

/**
 * A service to manage services related to {@link PedagogicalScenario},
 * {@link PedagogicalSequence}, {@link PedagogicalSession},
 * {@link PedagogicalActivity} and {@link TeachingMode} objects.
 * 
 * @author Grégoire Colbert
 * @since 2013-11-20
 */
public interface ScenariosService extends Serializable {

	/**
	 * Counts the number of persistent PedagogicalScenario objects
	 * 
	 * @return number of scenarios
	 */
	Long countPedagogicalScenarios();

	/**
	 * Creates a new persistent PedagogicalScenario.
	 * 
	 * @param author
	 *            A Teacher
	 * @param name
	 *            A name for the scenario
	 * @param objective
	 *            An objective for the scenario
	 * @return The newly created PedagogicalScenario, or null.
	 */
	PedagogicalScenario createPedagogicalScenario(Teacher author, String name, String objective);

	/**
	 * Retrieves an existing PedagogicalScenario.
	 * 
	 * @param id
	 *            The id of the PedagogicalScenario to load from the Data Access
	 *            Layer
	 * @param initialize
	 *            Set to true if the collections of associated objects should be
	 *            loaded as well
	 * @return the {@link PedagogicalScenario} with the given id, or null if it
	 *         doesn't exist
	 */
	PedagogicalScenario retrievePedagogicalScenario(Long id, boolean initialize);

	/**
	 * Updates the given PedagogicalScenario and returns the updated entity.
	 * 
	 * @param pedagogicalScenario
	 *            The PedagogicalScenario to update
	 * @return the updated PedagogicalScenario
	 */
	PedagogicalScenario updatePedagogicalScenario(PedagogicalScenario pedagogicalScenario);

	/**
	 * Deletes the PedagogicalScenario with the given id, including all
	 * associated PedagogicalSequence objects.
	 * 
	 * @param id
	 *            The id property value of the PedagogicalScenario to delete
	 * @return true if the PedagogicalScenario doesn't exists after this call,
	 *         false if the PedagogicalScenario still exists
	 */
	Boolean deletePedagogicalScenario(Long id);

	/**
	 * Returns the {@link PedagogicalScenario}s associated with the given
	 * author.
	 * 
	 * @param author
	 *            A Person
	 * @return the collection of the given author's scenarios
	 */
	Collection<PedagogicalScenario> retrieveScenariosWithAuthor(Person author);

	/**
	 * Dissociates the scenario with the given id from the author with the given
	 * id. If the given author is the last Teacher associated with the scenario,
	 * then the scenario is deleted using
	 * {@link #deletePedagogicalScenario(Long)}.
	 * 
	 * @param idScenario
	 *            An PedagogicalScenario's identifier
	 * @param idAuthor
	 *            A Teacher's identifier
	 * @return true if the author and scenario are not associated at the end of
	 *         the method, false otherwise
	 */
	Boolean dissociateAuthorOrDeleteScenarioIfLastAuthor(Long idScenario, Long idAuthor);

	/**
	 * Counts the number of persistent PedagogicalSequence objects
	 * 
	 * @return number of sequences
	 */
	Long countPedagogicalSequences();

	/**
	 * Creates a new persistent PedagogicalSequence.
	 * 
	 * @param name
	 *            A name for the sequence
	 * @return The newly created PedagogicalSequence, or null.
	 */
	PedagogicalSequence createPedagogicalSequence(String name);

	/**
	 * Retrieves an existing PedagogicalSequence.
	 * 
	 * @param id
	 *            The id of the PedagogicalSequence to load from the Data Access
	 *            Layer
	 * @param initialize
	 *            Set to true if the collections of associated objects should be
	 *            loaded as well
	 * @return the {@link PedagogicalSequence} with the given id, or null if it
	 *         doesn't exist
	 */
	PedagogicalSequence retrievePedagogicalSequence(Long id, boolean initialize);

	/**
	 * Updates the given PedagogicalSequence and returns the updated entity.
	 * 
	 * @param pedagogicalSequence
	 *            The PedagogicalSequence to update
	 * @return the updated PedagogicalSequence
	 */
	PedagogicalSequence updatePedagogicalSequence(PedagogicalSequence pedagogicalSequence);

	/**
	 * Deletes the PedagogicalSequence with the given id, including all
	 * associated PedagogicalSession objects.
	 * 
	 * @param id
	 *            The id property value of the PedagogicalSequence to delete
	 * @return true if the PedagogicalSequence doesn't exists after this call,
	 *         false if the PedagogicalSequence still exists
	 */
	Boolean deletePedagogicalSequence(Long id);

	/**
	 * Returns the collection of {@link PedagogicalSequence} that have no
	 * predecessors in the given PedagogicalScenario.
	 * 
	 * @param pedagogicalScenario
	 *            A PedagogicalScenario
	 * @return the collection of first sequences for the given
	 *         PedagogicalScenario
	 */
	Collection<PedagogicalSequence> getFirstPedagogicalSequencesOfScenario(PedagogicalScenario pedagogicalScenario);

	/**
	 * Counts the number of persistent PedagogicalSession objects
	 * 
	 * @return number of sessions
	 */
	Long countPedagogicalSessions();

	/**
	 * Creates a new persistent PedagogicalSession.
	 * 
	 * @param name
	 *            A name for the session
	 * @return The newly created PedagogicalSession, or null.
	 */
	PedagogicalSession createPedagogicalSession(String name);

	/**
	 * Retrieves an existing PedagogicalSession.
	 * 
	 * @param id
	 *            The id of the PedagogicalSession to load from the Data Access
	 *            Layer
	 * @param initialize
	 *            Set to true if the collections of associated objects should be
	 *            loaded as well
	 * @return the {@link PedagogicalSession} with the given id, or null if it
	 *         doesn't exist
	 */
	PedagogicalSession retrievePedagogicalSession(Long id, boolean initialize);

	/**
	 * Updates the given PedagogicalSession and returns the updated entity.
	 * 
	 * @param pedagogicalSession
	 *            The PedagogicalSession to update
	 * @return the updated PedagogicalSession
	 */
	PedagogicalSession updatePedagogicalSession(PedagogicalSession pedagogicalSession);

	/**
	 * Deletes the PedagogicalSession with the given id, including all
	 * associated PedagogicalActivity objects.
	 * 
	 * @param id
	 *            The id property value of the PedagogicalSession to delete
	 * @return true if the PedagogicalSession doesn't exists after this call,
	 *         false if the PedagogicalSession still exists
	 */
	Boolean deletePedagogicalSession(Long id);

	/**
	 * Returns the collection of {@link PedagogicalSession} that have no
	 * predecessors in the given pedagogicalSequence.
	 * 
	 * @param pedagogicalSequence
	 *            A PedagogicalSequence
	 * @return the collection of first sessions for the given
	 *         PedagogicalSequence
	 */
	Collection<PedagogicalSession> getFirstPedagogicalSessionsOfSequence(PedagogicalSequence pedagogicalSequence);

	/**
	 * Counts the number of persistent PedagogicalActivities objects
	 * 
	 * @return number of activities
	 */
	Long countPedagogicalActivities();

	/**
	 * Creates a new persistent PedagogicalActivity.
	 * 
	 * @param name
	 *            A name for the activity
	 * @return The newly created PedagogicalActivity, or null.
	 */
	PedagogicalActivity createPedagogicalActivity(String name);

	/**
	 * Retrieves an existing PedagogicalActivity.
	 * 
	 * @param id
	 *            The id of the PedagogicalActivity to load from the Data Access
	 *            Layer
	 * @param initialize
	 *            Set to true if the collections of associated objects should be
	 *            loaded as well
	 * @return the {@link PedagogicalActivity} with the given id, or null if it
	 *         doesn't exist
	 */
	PedagogicalActivity retrievePedagogicalActivity(Long id, boolean initialize);

	/**
	 * Updates the given PedagogicalActivity and returns the updated entity.
	 * 
	 * @param pedagogicalActivity
	 *            The PedagogicalActivity to update
	 * @return the updated PedagogicalActivity
	 */
	PedagogicalActivity updatePedagogicalActivity(PedagogicalActivity pedagogicalActivity);

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
	Boolean deletePedagogicalActivity(Long id);

	/**
	 * Returns the collection of {@link PedagogicalActivity} that have no
	 * predecessors in the given PedagogicalSession.
	 * 
	 * @param pedagogicalSession
	 *            A PedagogicalSession
	 * @return the collection of first activities for the given
	 *         PedagogicalSession
	 */
	Collection<PedagogicalActivity> getFirstPedagogicalActivitiesOfSession(PedagogicalSession pedagogicalSession);

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
	 * @return true if everything is right, false otherwise
	 * @throws eu.ueb.acem.services.exceptions.ServiceException
	 *             if the given next and previous activities are not associated
	 *             with pedagogicalSession, or are not chained together as
	 *             previous/next in the sequence, or if the duration of
	 *             pedagogicalSession is shorter than the duration of the
	 *             inserted activity
	 */
	Boolean addActivityToSession(PedagogicalActivity pedagogicalActivityToAdd, PedagogicalSession pedagogicalSession,
			PedagogicalActivity previousPedagogicalActivity, PedagogicalActivity nextPedagogicalActivity);

}
