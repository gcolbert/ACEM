package eu.ueb.acem.web.tests;

import javax.inject.Inject;

import junit.framework.TestCase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:web-test-context.xml"})
public class TestBeansLoaded extends TestCase {

	@Inject
	ApplicationContext applicationContext;
	
	@After
	public void after() {
	}

	@Before
	public void before() {
	}

	@Test
	public void testEditableTreeBean() {
		assertTrue("Component editableTreeBean is not loaded", applicationContext.containsBean("editableTreeBean"));
	}

	@Test
	public void testTableBean() {
		assertTrue("Component tableBean is not loaded", applicationContext.containsBean("tableBean"));
	}
	
	@Test
	public void testNonexistentBean() {
		assertFalse(applicationContext.containsBean("testNonexistentBean"));
	}

	@Test
	public void testNeedsAndAnswersController() {
		assertTrue("Controller needsAndAnswersController is not loaded", applicationContext.containsBean("needsAndAnswersController"));
	}

	@Test
	public void testNeedsAndAnswersService() {
		assertTrue("Service needsAndAnswersService is not loaded", applicationContext.containsBean("needsAndAnswersService"));
	}

	@Test
	public void testMainMenuController() {
		assertTrue("Controller mainMenuController is not loaded", applicationContext.containsBean("mainMenuController"));
	}

	@Test
	public void testMyScenariosController() {
		assertTrue("Controller myScenariosController is not loaded", applicationContext.containsBean("myScenariosController"));
	}
	
	@Test
	public void testScenariosService() {
		assertTrue("Controller scenariosService is not loaded", applicationContext.containsBean("scenariosService"));
	}
	
}
