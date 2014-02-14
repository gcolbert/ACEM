package eu.ueb.acem.services.tests;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import eu.ueb.acem.domain.beans.bleu.ActivitePedagogique;
import eu.ueb.acem.domain.beans.bleu.Scenario;
import eu.ueb.acem.domain.beans.gris.Personne;
import eu.ueb.acem.domain.beans.rouge.Etablissement;
import eu.ueb.acem.services.OrganisationsService;
import eu.ueb.acem.services.ScenariosService;
import eu.ueb.acem.services.UsersService;

/**
 * @author Grégoire Colbert @since 2014-02-07
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/domain-services-test-context.xml")
public class ScenariosServiceTest {

	@Autowired
	private ScenariosService scenariosService;

	@Autowired
	private UsersService usersService;

	@Autowired
	private OrganisationsService organisationsService;

	public ScenariosServiceTest() {
	}

	@Before
	public void before() {
		scenariosService.deleteAllScenarios();
		assertEquals(new Long(0), scenariosService.countScenarios());

		scenariosService.deleteAllPedagogicalActivities();
		assertEquals(new Long(0), scenariosService.countPedagogicalActivities());

		usersService.deleteAllPersons();
		assertEquals(new Long(0), usersService.countUsers());

		organisationsService.deleteAllInstitutions();
		assertEquals(new Long(0), organisationsService.countInstitutions());
	}

	@After
	public void after() {
		scenariosService.deleteAllScenarios();
		assertEquals(new Long(0), scenariosService.countScenarios());

		scenariosService.deleteAllPedagogicalActivities();
		assertEquals(new Long(0), scenariosService.countPedagogicalActivities());

		usersService.deleteAllPersons();
		assertEquals(new Long(0), usersService.countUsers());

		organisationsService.deleteAllInstitutions();
		assertEquals(new Long(0), organisationsService.countInstitutions());
	}

	/**
	 * GetScenariosWithAuthor
	 */
	@Transactional
	@Test
	public final void t01_TestGetScenariosWithAuthor() {
		Etablissement institution = organisationsService.createInstitution("University of Music");
		
		Personne user = usersService.createPerson("Grégoire COLBERT", institution);

		Scenario scenario1 = scenariosService.createScenario(user, "Study of the G-clef",
				"Make the learners able to read out loud the notes written in the G-clef");

		ActivitePedagogique pedagogicalActivity1 = scenariosService
				.createPedagogicalActivity("Introduction to the western musical notation");
		scenario1.addPedagogicalActivity(pedagogicalActivity1);

		ActivitePedagogique pedagogicalActivity2 = scenariosService
				.createPedagogicalActivity("Lecture d'une suite de ré et de mi");
		scenario1.addPedagogicalActivity(pedagogicalActivity2);

		ActivitePedagogique pedagogicalActivity3 = scenariosService
				.createPedagogicalActivity("Lecture d'une suite de mi et de fa");
		scenario1.addPedagogicalActivity(pedagogicalActivity3);

		ActivitePedagogique pedagogicalActivity4 = scenariosService
				.createPedagogicalActivity("Lecture d'une suite de fa et de sol");
		scenario1.addPedagogicalActivity(pedagogicalActivity4);

		scenario1 = scenariosService.updateScenario(scenario1);

		Scenario scenario1bis = scenariosService.retrieveScenario(scenario1.getId());
		
		assertEquals(4, scenario1bis.getPedagogicalActivities().size());
	}

}
