package eu.ueb.acem.services.tests;

import static org.junit.Assert.*;

import java.util.Set;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import eu.ueb.acem.domain.beans.bleu.Besoin;
import eu.ueb.acem.domain.beans.bleu.neo4j.BesoinNode;
import eu.ueb.acem.services.BesoinReponseService;

/**
 * @author gcolbert @since 2013-11-27
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/domain-services-test-context.xml")
public class BesoinReponseTest {

	@Autowired
	private BesoinReponseService besoinReponseService;
	
	public BesoinReponseTest() {
	}

	@Before
	public void before() {
		besoinReponseService.getBesoinDAO().deleteAll();
		besoinReponseService.getReponseDAO().deleteAll();
	}

	@After
	public void after() {
		besoinReponseService.getBesoinDAO().deleteAll();
		besoinReponseService.getReponseDAO().deleteAll();
	}

	/**
	 * GetBesoinsRacines
	 */
	@Test
	public final void t01_TestGetBesoinsRacines() {
		assertEquals(new Long(0), besoinReponseService.getBesoinDAO().count());

		// TODO : trouver comment ne pas appeler BesoinNode (on est dans la couche "services")
		Besoin besoin1 = new BesoinNode("besoin de test t01_TestGetBesoinsRacines 1");
		Besoin besoin11 = new BesoinNode("besoin de test t01_TestGetBesoinsRacines 1.1", besoin1);
		Besoin besoin111 = new BesoinNode("besoin de test t01_TestGetBesoinsRacines 1.1.1", besoin11);
		Besoin besoin112 = new BesoinNode("besoin de test t01_TestGetBesoinsRacines 1.1.2", besoin11);
		Besoin besoin12 = new BesoinNode("besoin de test t01_TestGetBesoinsRacines 1.2", besoin1);
		Besoin besoin13 = new BesoinNode("besoin de test t01_TestGetBesoinsRacines 1.3", besoin1);
		Besoin besoin2 = new BesoinNode("besoin de test t01_TestGetBesoinsRacines 2");
		Besoin besoin3 = new BesoinNode("besoin de test t01_TestGetBesoinsRacines 3");
		
		besoinReponseService.getBesoinDAO().create(besoin1);
		besoinReponseService.getBesoinDAO().create(besoin11);
		besoinReponseService.getBesoinDAO().create(besoin111);
		besoinReponseService.getBesoinDAO().create(besoin112);
		besoinReponseService.getBesoinDAO().create(besoin12);
		besoinReponseService.getBesoinDAO().create(besoin13);
		besoinReponseService.getBesoinDAO().create(besoin2);
		besoinReponseService.getBesoinDAO().create(besoin3);

		Set<Besoin> besoinsRacines = besoinReponseService.getBesoinsRacines();
		assertEquals(3, besoinsRacines.size());
	}
	
}
