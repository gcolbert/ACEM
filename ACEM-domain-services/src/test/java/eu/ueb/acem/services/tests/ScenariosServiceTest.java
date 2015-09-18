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
package eu.ueb.acem.services.tests;

import javax.inject.Inject;

import junit.framework.TestCase;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import eu.ueb.acem.domain.beans.bleu.PedagogicalActivity;
import eu.ueb.acem.domain.beans.bleu.PedagogicalScenario;
import eu.ueb.acem.domain.beans.bleu.PedagogicalSequence;
import eu.ueb.acem.domain.beans.bleu.PedagogicalSession;
import eu.ueb.acem.domain.beans.gris.Teacher;
import eu.ueb.acem.domain.beans.rouge.Institution;
import eu.ueb.acem.services.OrganisationsService;
import eu.ueb.acem.services.ScenariosService;
import eu.ueb.acem.services.UsersService;

/**
 * @author Grégoire Colbert
 * @since 2014-02-07
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/domain-services-test-context.xml")
public class ScenariosServiceTest extends TestCase {

	@Inject
	private ScenariosService scenariosService;

	@Inject
	private UsersService usersService;

	@Inject
	private OrganisationsService organisationsService;

	public ScenariosServiceTest() {
	}

	/**
	 * GetScenariosWithAuthor
	 */
	@Test
	@Transactional
	@Rollback(true)
	public final void t01_TestGetScenariosWithAuthor() {
		Institution institution = organisationsService.createInstitution("University of Music", "UoM", null);
		
		Teacher teacher = usersService.createTeacher("Grégoire COLBERT", "gcolbert", "pass");
		teacher.getWorksForOrganisations().add(institution);
		teacher = usersService.updateTeacher(teacher);

		PedagogicalScenario scenario1 = scenariosService.createScenario(teacher, "Study of the G-clef",
				"Make the learners able to read out loud the notes written in the G-clef");

		PedagogicalScenario scenario1bis = scenariosService.retrievePedagogicalScenario(scenario1.getId(), false);

		assertTrue("The scenario should be associated with the teacher", scenario1bis.getAuthors().contains(teacher));
	}

	/**
	 * Count sequences, sessions and activities
	 */
	@Test
	@Transactional
	@Rollback(true)
	public final void t02_TestGetScenariosSequencesSessionsActivities() {
		Institution institution = organisationsService.createInstitution("University of Music", "UoM", null);

		Teacher teacher = usersService.createTeacher("Grégoire COLBERT", "gcolbert", "pass");
		teacher.getWorksForOrganisations().add(institution);
		teacher = usersService.updateTeacher(teacher);

		PedagogicalScenario scenario1 = scenariosService.createScenario(teacher, "Study of the G-clef",
				"Make the learners able to read out loud the notes written in the G-clef");

		PedagogicalSequence sequence1 = scenariosService.createPedagogicalSequence("Introduction to the western musical notation");
		scenario1.getPedagogicalSequences().add(sequence1);
		sequence1.setPedagogicalScenario(scenario1);
		scenariosService.updatePedagogicalSequence(sequence1);

		PedagogicalSession session1 = scenariosService.createPedagogicalSession("Session 1");
		sequence1.getPedagogicalSessions().add(session1);
		session1.setPedagogicalSequence(sequence1);
		scenariosService.updatePedagogicalSession(session1);

		PedagogicalActivity pedagogicalActivity1 = scenariosService
				.createPedagogicalActivity("Reading a sequence of C and D");
		session1.getPedagogicalActivities().add(pedagogicalActivity1);
		pedagogicalActivity1.setPedagogicalSession(session1);
		scenariosService.updatePedagogicalSession(session1);
		pedagogicalActivity1 = scenariosService.updatePedagogicalActivity(pedagogicalActivity1);

		PedagogicalActivity pedagogicalActivity2 = scenariosService
				.createPedagogicalActivity("Reading a sequence of D and E");
		pedagogicalActivity1.setNextPedagogicalActivity(pedagogicalActivity2);
		pedagogicalActivity2.setPedagogicalSession(session1);
		session1.getPedagogicalActivities().add(pedagogicalActivity2);
		pedagogicalActivity2 = scenariosService.updatePedagogicalActivity(pedagogicalActivity2);

		PedagogicalActivity pedagogicalActivity3 = scenariosService
				.createPedagogicalActivity("Reading a sequence of E and F");
		pedagogicalActivity2.setNextPedagogicalActivity(pedagogicalActivity3);
		pedagogicalActivity3.setPedagogicalSession(session1);
		session1.getPedagogicalActivities().add(pedagogicalActivity3);
		pedagogicalActivity3 = scenariosService.updatePedagogicalActivity(pedagogicalActivity3);

		PedagogicalActivity pedagogicalActivity4 = scenariosService
				.createPedagogicalActivity("Reading a sequence of F and G");
		pedagogicalActivity3.setNextPedagogicalActivity(pedagogicalActivity4);
		pedagogicalActivity4.setPedagogicalSession(session1);
		session1.getPedagogicalActivities().add(pedagogicalActivity4);
		pedagogicalActivity4 = scenariosService.updatePedagogicalActivity(pedagogicalActivity4);

		scenario1 = scenariosService.updateScenario(scenario1);

		PedagogicalScenario scenario1bis = scenariosService.retrievePedagogicalScenario(scenario1.getId(), false);
		assertEquals("There should be 1 sequence in the scenario", 1, scenario1bis.getPedagogicalSequences().size());

		PedagogicalSequence sequence1bis = scenariosService.retrievePedagogicalSequence(sequence1.getId(), false);
		assertEquals("There should be 1 sequence in the scenario", 1, sequence1bis.getPedagogicalSessions().size());

		PedagogicalSession session1bis = scenariosService.retrievePedagogicalSession(session1.getId(), false);
		assertEquals("There should be 4 sequences in the scenario", 4, session1bis.getPedagogicalActivities().size());
	}

	/**
	 * DissociateScenarioFromUniqueAuthor
	 */
	@Test
	@Transactional
	@Rollback(true)
	public final void t03_TestDissociateScenarioFromUniqueAuthor() {
		Teacher teacher1 = usersService.createTeacher("Grégoire COLBERT", "gcolbert", "pass");

		PedagogicalScenario scenario1 = scenariosService.createScenario(teacher1, "Study of the G-clef",
				"Make the learners able to read out loud the notes written in the G-clef");
		assertEquals("The scenario should exist because we created it.", new Long(1L), scenariosService.countScenarios());

		Teacher teacher2 = usersService.createTeacher("JS Bach", "bach", "welltempered");
		teacher2.getPedagogicalScenarios().add(scenario1);
		scenario1.getAuthors().add(teacher2);
		scenario1 = scenariosService.updateScenario(scenario1);
		teacher2 = usersService.updateTeacher(teacher2);

		scenariosService.dissociateAuthorOrDeleteScenarioIfLastAuthor(scenario1.getId(), teacher2.getId());

		assertEquals("The scenario should still exist because teacher1 is still associated with it.", new Long(1L), scenariosService.countScenarios());

		scenariosService.dissociateAuthorOrDeleteScenarioIfLastAuthor(scenario1.getId(), teacher1.getId());

		assertEquals("The scenario shouldn't exist because we dissociated it from its unique author.", new Long(0L), scenariosService.countScenarios());

		assertEquals("The teachers should still exists.", new Long(2L), usersService.countTeachers());
	}

	/**
	 * DeleteScenarioWithParallelSequences
	 */
	@Test
	@Transactional
	@Rollback(true)
	public final void t04_TestDeleteScenarioWithParallelSequences() {
		Teacher teacher = usersService.createTeacher("Grégoire COLBERT", "gcolbert", "pass");

		PedagogicalScenario scenario1 = scenariosService.createScenario(teacher, "Study of the C-clef",
				"Make the learners able to read out loud the notes written in the C-clef");

		assertEquals("The scenario should exist because we created it.", new Long(1L), scenariosService.countScenarios());

		PedagogicalSequence sequenceA1 = scenariosService.createPedagogicalSequence("Sequence A1");
		scenario1.getPedagogicalSequences().add(sequenceA1);
		sequenceA1.setPedagogicalScenario(scenario1);
		scenariosService.updatePedagogicalSequence(sequenceA1);
		scenariosService.updateScenario(scenario1);

		PedagogicalSequence sequenceA2 = scenariosService.createPedagogicalSequence("Sequence A2");
		scenario1.getPedagogicalSequences().add(sequenceA2);
		sequenceA2.setPedagogicalScenario(scenario1);
		sequenceA1.setNextPedagogicalSequence(sequenceA2);
		scenariosService.updatePedagogicalSequence(sequenceA1);
		scenariosService.updatePedagogicalSequence(sequenceA2);
		scenariosService.updateScenario(scenario1);

		PedagogicalSequence sequenceA3 = scenariosService.createPedagogicalSequence("Sequence A3");
		scenario1.getPedagogicalSequences().add(sequenceA3);
		sequenceA3.setPedagogicalScenario(scenario1);
		sequenceA2.setNextPedagogicalSequence(sequenceA3);
		scenariosService.updatePedagogicalSequence(sequenceA2);
		scenariosService.updatePedagogicalSequence(sequenceA3);
		scenariosService.updateScenario(scenario1);

		PedagogicalSequence sequenceB1 = scenariosService.createPedagogicalSequence("Sequence B1");
		scenario1.getPedagogicalSequences().add(sequenceB1);
		sequenceB1.setPedagogicalScenario(scenario1);
		scenariosService.updatePedagogicalSequence(sequenceB1);
		scenariosService.updateScenario(scenario1);

		PedagogicalSequence sequenceB2 = scenariosService.createPedagogicalSequence("Sequence B2");
		scenario1.getPedagogicalSequences().add(sequenceB2);
		sequenceB2.setPedagogicalScenario(scenario1);
		sequenceB1.setNextPedagogicalSequence(sequenceB2);
		scenariosService.updatePedagogicalSequence(sequenceB1);
		scenariosService.updatePedagogicalSequence(sequenceB2);
		scenariosService.updateScenario(scenario1);

		assertEquals("Before deletion of the scenario, we should count 1 scenario", new Long(1), scenariosService.countScenarios());
		assertEquals("Before deletion of the scenario, we should count 5 sequences", new Long(5), scenariosService.countPedagogicalSequences());

		scenariosService.deleteScenario(scenario1.getId());
		
		assertEquals("After deletion of the scenario, we should count 0 scenario", new Long(0), scenariosService.countScenarios());
		assertEquals("After deletion of the scenario, we should count 0 sequence", new Long(0), scenariosService.countPedagogicalSequences());
	}

	/**
	 * addActivityToSession
	 */
	@Test
	@Transactional
	@Rollback(true)
	public final void t05_TestAddActivityToSession() {
		PedagogicalSession session1 = scenariosService.createPedagogicalSession("Session 1");
		session1.setDuration(3600L);
		assertEquals("We should count 1 session from scenarios service", new Long(1), scenariosService.countPedagogicalSessions());

		PedagogicalActivity activityF = scenariosService.createPedagogicalActivity("Activity F is quite in the middle of the session");
		activityF.setDuration(1200L);
		assertTrue("addActivityToSession(middleActivity, session1, null, null) returned false", scenariosService.addActivityToSession(activityF, session1, null, null));
		assertEquals("We should count 1 activity in session1", 1, session1.getPedagogicalActivities().size());
		assertEquals("We should count 1 activity from scenarios service", new Long(1), scenariosService.countPedagogicalActivities());

		PedagogicalActivity activityB = scenariosService.createPedagogicalActivity("Activity B is near the start of the session");
		activityB.setDuration(600L);
		assertTrue("addActivityToSession(activityB, session1, null, activityF) returned false", scenariosService.addActivityToSession(activityB, session1, null, activityF));
		assertEquals("We should count 2 activities in session1", 2, session1.getPedagogicalActivities().size());
		assertEquals("We should count 2 activities", new Long(2), scenariosService.countPedagogicalActivities());

		PedagogicalActivity activityX = scenariosService.createPedagogicalActivity("Activity X is near the end of the session");
		activityX.setDuration(600L);
		assertTrue("addActivityToSession(activityX, session1, activityF, null) returned false", scenariosService.addActivityToSession(activityX, session1, activityF, null));
		assertEquals("We should count 3 activities in session1", 3, session1.getPedagogicalActivities().size());
		assertEquals("We should count 3 activities", new Long(3), scenariosService.countPedagogicalActivities());

		PedagogicalActivity activityP = scenariosService.createPedagogicalActivity("Activity P is somewhere between F and X");
		activityP.setDuration(1200L);
		assertTrue("addActivityToSession(activityP, session1, activityF, activityX) returned false", scenariosService.addActivityToSession(activityP, session1, activityF, activityX));
		assertEquals("We should count 4 activities in session1", 4, session1.getPedagogicalActivities().size());
		assertEquals("We should count 4 activities", new Long(4), scenariosService.countPedagogicalActivities());
	}

}
