package eu.ueb.acem.web.tests;

import junit.framework.TestCase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.esupportail.commons.context.ApplicationContextHolder;
import org.esupportail.commons.exceptions.ConfigException;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:web-test-context.xml"})
public class TestBeansLoaded extends TestCase {

	@After
	public void after() {
	}

	@Before
	public void before() {
		if (ApplicationContextHolder.getContext() == null) {
			throw new ConfigException("You must add the ApplicationContextHolder bean in your applicationContext.xml");
		}
	}

	@Test
	public void testEditableTreeBean() {
		ApplicationContext context = ApplicationContextHolder.getContext();
		assertTrue("Component editableTreeBean is not loaded", context.containsBean("editableTreeBean"));
	}

	@Test
	public void testTableBean() {
		ApplicationContext context = ApplicationContextHolder.getContext();
		assertTrue("Component tableBean is not loaded", context.containsBean("tableBean"));
	}
	
	@Test
	public void testNonexistentBean() {
		ApplicationContext context = ApplicationContextHolder.getContext();
		assertFalse(context.containsBean("testNonexistentBean"));
	}

	@Test
	public void testNeedsAndAnswersTreeController() {
		ApplicationContext context = ApplicationContextHolder.getContext();
		assertTrue("Controller needsAndAnswersTreeController is not loaded", context.containsBean("needsAndAnswersTreeController"));
	}

	@Test
	public void testNeedsAndAnswersTableController() {
		ApplicationContext context = ApplicationContextHolder.getContext();
		assertTrue("Controller needsAndAnswersTableController is not loaded", context.containsBean("needsAndAnswersTableController"));
	}

	@Test
	public void testNeedsAndAnswersService() {
		ApplicationContext context = ApplicationContextHolder.getContext();
		assertTrue("Service needsAndAnswersService is not loaded", context.containsBean("needsAndAnswersService"));
	}

	@Test
	public void testMainMenuController() {
		ApplicationContext context = ApplicationContextHolder.getContext();
		assertTrue("Controller mainMenuController is not loaded", context.containsBean("mainMenuController"));
	}

	@Test
	public void testMyScenariosController() {
		ApplicationContext context = ApplicationContextHolder.getContext();
		assertTrue("Controller myScenariosController is not loaded", context.containsBean("myScenariosController"));
	}
	
	@Test
	public void testScenariosService() {
		ApplicationContext context = ApplicationContextHolder.getContext();
		assertTrue("Controller scenariosService is not loaded", context.containsBean("scenariosService"));
	}
	
}
