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
public class TestBeansLoaded extends TestCase {

	@After
	public void after() {
	}

	@Before
	public void before() {
	}
	
	@Test
	public void testTreeBean() {
		if (ApplicationContextHolder.getContext() == null) {
			throw new ConfigException("vous devez ajouter le bean ApplicationContextHolder dans votre fichier application.xml");
		}
		ApplicationContext context = ApplicationContextHolder.getContext();
		assertTrue(context.containsBean("treeBean"));
	}

	@Test
	public void testBeanInexistant() {
		if (ApplicationContextHolder.getContext() == null) {
			throw new ConfigException("vous devez ajouter le bean ApplicationContextHolder dans votre fichier application.xml");
		}
		ApplicationContext context = ApplicationContextHolder.getContext();
		assertFalse(context.containsBean("inexistantBean"));
	}

	@Test
	public void testBesoinsReponsesController() {
		if (ApplicationContextHolder.getContext() == null) {
			throw new ConfigException("vous devez ajouter le bean ApplicationContextHolder dans votre fichier application.xml");
		}
		ApplicationContext context = ApplicationContextHolder.getContext();
		assertTrue(context.containsBean("besoinsReponsesController"));
	}
	
	@Test
	public void testBesoinsReponsesService() {
		if (ApplicationContextHolder.getContext() == null) {
			throw new ConfigException("vous devez ajouter le bean ApplicationContextHolder dans votre fichier application.xml");
		}
		ApplicationContext context = ApplicationContextHolder.getContext();
		assertTrue(context.containsBean("besoinsReponsesService"));
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
