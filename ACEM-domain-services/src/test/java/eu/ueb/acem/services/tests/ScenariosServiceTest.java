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
	}

	@After
	public void after() {
		scenariosService.deleteAllScenarios();
		assertEquals(new Long(0), scenariosService.countScenarios());

		scenariosService.deleteAllPedagogicalActivities();
		assertEquals(new Long(0), scenariosService.countPedagogicalActivities());
	}

	/**
	 * GetScenariosWithAuthor
	 */
	@SuppressWarnings("unused")
	@Transactional
	@Test
	public final void t01_TestGetScenariosWithAuthor() {
		
	}

}
