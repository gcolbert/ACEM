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
@ContextConfiguration(locations = {"/properties/applicationContext.xml", "/web-test-context.xml"})
//@ContextConfiguration(locations = {"/web-test-context.xml"})
public class TestBeansLoaded extends TestCase {

	@After
	public void after() {
	}

	@Before
	public void before() {
		if (ApplicationContextHolder.getContext() == null) {
			throw new ConfigException("vous devez ajouter le bean ApplicationContextHolder dans votre fichier applicationContext.xml");
		}
	}

	@Test
	public void testEditableTreeBean() {
		ApplicationContext context = ApplicationContextHolder.getContext();
		assertTrue("Le composant editableTreeBean n'est pas chargé", context.containsBean("editableTreeBean"));
	}

	@Test
	public void testBeanInexistant() {
		ApplicationContext context = ApplicationContextHolder.getContext();
		assertFalse(context.containsBean("inexistantBean"));
	}

	@Test
	public void testBesoinsReponsesController() {
		ApplicationContext context = ApplicationContextHolder.getContext();
		assertTrue("Le composant besoinsReponsesController n'est pas chargé", context.containsBean("besoinsReponsesController"));
	}
	
	@Test
	public void testBesoinsReponsesService() {
		ApplicationContext context = ApplicationContextHolder.getContext();
		assertTrue("Le composant besoinsReponsesService n'est pas chargé", context.containsBean("besoinsReponsesService"));
	}

	
/*
    @Test
    public void testSpringBeans() {
        ClassPathXmlApplicationContext unused = new ClassPathXmlApplicationContext("properties/applicationContext.xml");
        ApplicationContext context = ApplicationContextHolder.getContext();
		assertTrue(context.containsBean("treeBean"));
    }
*/
}
