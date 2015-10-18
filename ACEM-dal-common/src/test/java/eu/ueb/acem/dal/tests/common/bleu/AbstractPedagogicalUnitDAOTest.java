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
package eu.ueb.acem.dal.tests.common.bleu;

import java.util.Collection;

import javax.inject.Inject;

import junit.framework.TestCase;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import eu.ueb.acem.dal.common.bleu.PedagogicalActivityDAO;
import eu.ueb.acem.dal.common.bleu.PedagogicalScenarioDAO;
import eu.ueb.acem.dal.common.bleu.PedagogicalSequenceDAO;
import eu.ueb.acem.dal.common.bleu.PedagogicalSessionDAO;
import eu.ueb.acem.domain.beans.bleu.PedagogicalActivity;
import eu.ueb.acem.domain.beans.bleu.PedagogicalScenario;
import eu.ueb.acem.domain.beans.bleu.PedagogicalSequence;
import eu.ueb.acem.domain.beans.bleu.PedagogicalSession;

/**
 * @author Grégoire Colbert
 * @since 2015-09-02
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/dal-common-test-context.xml")
public abstract class AbstractPedagogicalUnitDAOTest extends TestCase {

	/**
	 * For Logging.
	 */
	@SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory.getLogger(AbstractPedagogicalUnitDAOTest.class);

	@Inject
	private PedagogicalScenarioDAO<Long> pedagogicalScenarioDAO;

	@Inject
	private PedagogicalSequenceDAO<Long> pedagogicalSequenceDAO;

	@Inject
	private PedagogicalSessionDAO<Long> pedagogicalSessionDAO;

	@Inject
	private PedagogicalActivityDAO<Long> pedagogicalActivityDAO;

	public AbstractPedagogicalUnitDAOTest() {
	}

	/**
	 * Create a PedagogicalUnit (here we use a PedagogicalScenario, but since
	 * PedagogicalUnitDAO is abstract, all instances of PedagogicalUnitDAO
	 * inherit the "create" method, we don't need to test with every instance)
	 */
	@Test
	@Transactional
	@Rollback(true)
	public final void t01_TestPedagogicalUnitDAOCreate() {
		// We create our object
		PedagogicalScenario scenario1 = pedagogicalScenarioDAO.create("t01 scenario", "objective of the scenario");

		// We check that "create" is idempotent (multiple calls must not
		// duplicate data)
		scenario1 = pedagogicalScenarioDAO.create(scenario1);

		// There must exactly 1 object in the datastore
		assertEquals("There are more than one object in the datastore", new Long(1), pedagogicalScenarioDAO.count());
	}

	/**
	 * Retrieve
	 */
	@Test
	@Transactional
	@Rollback(true)
	public final void t02_TestPedagogicalUnitDAORetrieve() {
		// We create a new object in the datastore
		PedagogicalScenario scenario1 = pedagogicalScenarioDAO.create("t02 scenario");
		assertEquals(new Long(1), pedagogicalScenarioDAO.count());

		// We retrieve the object from the datastore using its id
		PedagogicalScenario scenario1bis = pedagogicalScenarioDAO.retrieveById(scenario1.getId());
		assertEquals(scenario1, scenario1bis);
		assertEquals(scenario1.getId(), scenario1bis.getId());
		assertEquals(scenario1.getName(), scenario1bis.getName());

		// We retrieve the object from the datastore using its name
		Collection<PedagogicalScenario> scenarios = pedagogicalScenarioDAO.retrieveByName(scenario1.getName());
		assertTrue(scenarios.contains(scenario1));

		assertTrue(pedagogicalScenarioDAO.retrieveByName(scenario1.getName()).contains(scenario1));
	}

	/**
	 * RetrieveFirstSequencesOfScenario
	 */
	@Test
	@Transactional
	@Rollback(true)
	public final void t03_TestRetrieveFirstSequencesOfScenario() {
		// We create a new object in the datastore
		PedagogicalScenario scenario1 = pedagogicalScenarioDAO.create("t03 scenario");
		assertEquals(new Long(1), pedagogicalScenarioDAO.count());

		// We create four sequences
		PedagogicalSequence sequence1a = pedagogicalSequenceDAO.create("t03 sequence 1a");
		sequence1a.setPedagogicalScenario(scenario1);
		scenario1.getPedagogicalSequences().add(sequence1a);

		PedagogicalSequence sequence1b = pedagogicalSequenceDAO.create("t03 sequence 1b");
		sequence1b.setPedagogicalScenario(scenario1);
		scenario1.getPedagogicalSequences().add(sequence1b);

		sequence1a.setNextPedagogicalSequence(sequence1b);

		sequence1a = pedagogicalSequenceDAO.update(sequence1a);
		sequence1b = pedagogicalSequenceDAO.update(sequence1b);

		PedagogicalSequence sequence2a = pedagogicalSequenceDAO.create("t03 sequence 2a");
		sequence2a.setPedagogicalScenario(scenario1);
		scenario1.getPedagogicalSequences().add(sequence2a);

		PedagogicalSequence sequence2b = pedagogicalSequenceDAO.create("t03 sequence 2b");
		sequence2b.setPedagogicalScenario(scenario1);
		scenario1.getPedagogicalSequences().add(sequence2b);

		sequence2a.setNextPedagogicalSequence(sequence2b);

		sequence2a = pedagogicalSequenceDAO.update(sequence2a);
		sequence2b = pedagogicalSequenceDAO.update(sequence2b);

		// We update the scenario
		scenario1 = pedagogicalScenarioDAO.update(scenario1);

		assertEquals("There should be 4 sequences in total in the scenario", new Long(4), pedagogicalSequenceDAO.count());

		Collection<PedagogicalSequence> firstSequences = pedagogicalSequenceDAO.retrieveFirstSequencesOfScenario(scenario1);
		assertEquals("There should be 2 'first sequences' (sequences running in parallel) in the scenario", 2L, firstSequences.size());
		assertTrue("Sequence 1a should be one of the 'first sequences' of scenario 1", firstSequences.contains(sequence1a));
		assertFalse("Sequence 1b should not be one of the 'first sequences' of scenario 1", firstSequences.contains(sequence1b));
		assertTrue("Sequence 2a should be one of the 'first sequences' of scenario 1", firstSequences.contains(sequence2a));
		assertFalse("Sequence 2b should not be one of the 'first sequences' of scenario 1", firstSequences.contains(sequence2b));
	}

	/**
	 * RetrieveFirstSessionsOfSequence
	 */
	@Test
	@Transactional
	@Rollback(true)
	public final void t04_TestRetrieveFirstSessionsOfSequence() {
		// We create a new object in the datastore
		PedagogicalSequence sequence1 = pedagogicalSequenceDAO.create("t04 sequence 1");
		assertEquals(new Long(1), pedagogicalSequenceDAO.count());

		// We create four sessions
		PedagogicalSession session1a = pedagogicalSessionDAO.create("t04 session 1a");
		session1a.setPedagogicalSequence(sequence1);
		sequence1.getPedagogicalSessions().add(session1a);

		PedagogicalSession session1b = pedagogicalSessionDAO.create("t04 session 1b");
		session1b.setPedagogicalSequence(sequence1);
		sequence1.getPedagogicalSessions().add(session1b);

		session1a.setNextPedagogicalSession(session1b);

		session1a = pedagogicalSessionDAO.update(session1a);
		session1b = pedagogicalSessionDAO.update(session1b);

		PedagogicalSession session2a = pedagogicalSessionDAO.create("t04 session 2a");
		session2a.setPedagogicalSequence(sequence1);
		sequence1.getPedagogicalSessions().add(session2a);

		PedagogicalSession session2b = pedagogicalSessionDAO.create("t04 session 2b");
		session2b.setPedagogicalSequence(sequence1);
		sequence1.getPedagogicalSessions().add(session2b);

		session2a.setNextPedagogicalSession(session2b);

		session2a = pedagogicalSessionDAO.update(session2a);
		session2b = pedagogicalSessionDAO.update(session2b);

		// We update the sequence
		sequence1 = pedagogicalSequenceDAO.update(sequence1);

		assertEquals("There should be 4 sessions in total", new Long(4), pedagogicalSessionDAO.count());

		Collection<PedagogicalSession> firstSessionsOfSequence1 = pedagogicalSessionDAO.retrieveFirstSessionsOfSequence(sequence1);
		assertEquals("There should be 2 'first sessions' (sessions running in parallel) in sequence 1", 2L, firstSessionsOfSequence1.size());
		assertTrue("Session 1a should be one of the 'first sequences' of sequence 1", firstSessionsOfSequence1.contains(session1a));
		assertFalse("Session 1b should not be one of the 'first sequences' of sequence 1", firstSessionsOfSequence1.contains(session1b));
		assertTrue("Session 2a should be one of the 'first sequences' of sequence 1", firstSessionsOfSequence1.contains(session2a));
		assertFalse("Session 2b should not be one of the 'first sequences' of sequence 1", firstSessionsOfSequence1.contains(session2b));

		// -------------------------------------------------------

		// We create a new object in the datastore
		PedagogicalSequence sequence2 = pedagogicalSequenceDAO.create("t04 sequence 2");
		assertEquals(new Long(2), pedagogicalSequenceDAO.count());

		// We use a session, that was not a first session of sequence1, as first session of sequence2
		session1b.setPedagogicalSequence(sequence2);
		sequence2.getPedagogicalSessions().add(session1b);

		session1b = pedagogicalSessionDAO.update(session1b);

		// We update the sequence
		sequence2 = pedagogicalSequenceDAO.update(sequence2);

		assertEquals("There should still be 4 sessions in total", new Long(4), pedagogicalSessionDAO.count());

		Collection<PedagogicalSession> firstSessionsOfSequence2 = pedagogicalSessionDAO.retrieveFirstSessionsOfSequence(sequence2);
		assertEquals("There should be 1 'first sessions' (sessions running in parallel) in sequence 2", 1L, firstSessionsOfSequence2.size());
		assertFalse("Session 1a should not be one of the 'first sequences' of sequence 2", firstSessionsOfSequence2.contains(session1a));
		assertTrue("Session 1b should be one of the 'first sequences' of sequence 2", firstSessionsOfSequence2.contains(session1b));
		assertFalse("Session 2a should not be one of the 'first sequences' of sequence 2", firstSessionsOfSequence2.contains(session2a));
		assertFalse("Session 2b should not be one of the 'first sequences' of sequence 2", firstSessionsOfSequence2.contains(session2b));
	}

	/**
	 * RetrieveFirstActivitiesOfSession
	 */
	@Test
	@Transactional
	@Rollback(true)
	public final void t05_TestRetrieveFirstActivitiesOfSession() {
		// We create a new object in the datastore
		PedagogicalSession session1 = pedagogicalSessionDAO.create("t05 session 1");
		assertEquals(new Long(1), pedagogicalSessionDAO.count());

		// We create four activities
		PedagogicalActivity activity1a = pedagogicalActivityDAO.create("t05 activity 1a");
		activity1a.setPedagogicalSession(session1);
		session1.getPedagogicalActivities().add(activity1a);

		PedagogicalActivity activity1b = pedagogicalActivityDAO.create("t05 activity 1b");
		activity1b.setPedagogicalSession(session1);
		session1.getPedagogicalActivities().add(activity1b);

		activity1a.setNextPedagogicalActivity(activity1b);

		activity1a = pedagogicalActivityDAO.update(activity1a);
		activity1b = pedagogicalActivityDAO.update(activity1b);

		PedagogicalActivity activity2a = pedagogicalActivityDAO.create("t05 activity 2a");
		activity2a.setPedagogicalSession(session1);
		session1.getPedagogicalActivities().add(activity2a);

		PedagogicalActivity activity2b = pedagogicalActivityDAO.create("t05 activity 2b");
		activity2b.setPedagogicalSession(session1);
		session1.getPedagogicalActivities().add(activity2b);

		activity2a.setNextPedagogicalActivity(activity2b);

		activity2a = pedagogicalActivityDAO.update(activity2a);
		activity2b = pedagogicalActivityDAO.update(activity2b);

		// We update the session
		session1 = pedagogicalSessionDAO.update(session1);

		assertEquals("There should be 4 activities in total", new Long(4), pedagogicalActivityDAO.count());

		Collection<PedagogicalActivity> firstActivitiesOfSession1 = pedagogicalActivityDAO.retrieveFirstActivitiesOfSession(session1);
		assertEquals("There should be 2 'first activities' (activities running in parallel) in session 1", 2L, firstActivitiesOfSession1.size());
		assertTrue("Activity 1a should be one of the 'first sessions' of session 1", firstActivitiesOfSession1.contains(activity1a));
		assertFalse("Activity 1b should not be one of the 'first sessions' of session 1", firstActivitiesOfSession1.contains(activity1b));
		assertTrue("Activity 2a should be one of the 'first sessions' of session 1", firstActivitiesOfSession1.contains(activity2a));
		assertFalse("Activity 2b should not be one of the 'first sessions' of session 1", firstActivitiesOfSession1.contains(activity2b));

		// -------------------------------------------------------

		// We create a new object in the datastore
		PedagogicalSession session2 = pedagogicalSessionDAO.create("t05 session 2");
		assertEquals(new Long(2), pedagogicalSessionDAO.count());

		// We use a activity, that was not a first activity of session1, as first activity of session2
		activity1b.setPedagogicalSession(session2);
		session2.getPedagogicalActivities().add(activity1b);

		activity1b = pedagogicalActivityDAO.update(activity1b);

		// We update the session
		session2 = pedagogicalSessionDAO.update(session2);

		assertEquals("There should still be 4 activities in total", new Long(4), pedagogicalActivityDAO.count());

		Collection<PedagogicalActivity> firstActivitiesOfSession2 = pedagogicalActivityDAO.retrieveFirstActivitiesOfSession(session2);
		assertEquals("There should be 1 'first activities' (activities running in parallel) in session 2", 1L, firstActivitiesOfSession2.size());
		assertFalse("Activity 1a should not be one of the 'first sessions' of session 2", firstActivitiesOfSession2.contains(activity1a));
		assertTrue("Activity 1b should be one of the 'first sessions' of session 2", firstActivitiesOfSession2.contains(activity1b));
		assertFalse("Activity 2a should not be one of the 'first sessions' of session 2", firstActivitiesOfSession2.contains(activity2a));
		assertFalse("Activity 2b should not be one of the 'first sessions' of session 2", firstActivitiesOfSession2.contains(activity2b));
	}

	/**
	 * Session chain
	 */
	@Test
	@Transactional
	@Rollback(true)
	public final void t06_TestSessionChain() {
		// We create a new object in the datastore
		PedagogicalSession session1 = pedagogicalSessionDAO.create("t06 session 1");
		PedagogicalSession session2 = pedagogicalSessionDAO.create("t06 session 2");
		PedagogicalSession session3 = pedagogicalSessionDAO.create("t06 session 3");
		assertEquals(new Long(3), pedagogicalSessionDAO.count());

		session1.setNextPedagogicalSession(session2);
		session2.setNextPedagogicalSession(session3);
		session1 = pedagogicalSessionDAO.update(session1);
		session2 = pedagogicalSessionDAO.update(session2);
		session3 = pedagogicalSessionDAO.update(session3);

		// We reload the sessions
		PedagogicalSession session1bis = pedagogicalSessionDAO.retrieveById(session1.getId());
		PedagogicalSession session2bis = pedagogicalSessionDAO.retrieveById(session2.getId());
		PedagogicalSession session3bis = pedagogicalSessionDAO.retrieveById(session3.getId());

		assertNull("session1bis.getPreviousPedagogicalSession() should be null", session1bis.getPreviousPedagogicalSession());
		assertTrue("session1bis.getNextPedagogicalSession() should be session2bis", session1bis.getNextPedagogicalSession().equals(session2bis));

		assertTrue("session2bis.getPreviousPedagogicalSession() should be session1bis", session2bis.getPreviousPedagogicalSession().equals(session1bis));
		assertTrue("session2bis.getNextPedagogicalSession() should be session3bis", session2bis.getNextPedagogicalSession().equals(session3bis));

		assertTrue("session3bis.getPreviousPedagogicalSession() should be session2bis", session3bis.getPreviousPedagogicalSession().equals(session2bis));
		assertNull("session3bis.getNextPedagogicalSession() should be null", session3bis.getNextPedagogicalSession());
	}

}
