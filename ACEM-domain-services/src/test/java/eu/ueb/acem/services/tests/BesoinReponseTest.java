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
	 * t01_TestBesoinReponseTestAddBesoin
	 */
	@Test
	public final void t01_TestBesoinReponseTestAddBesoin() {
		/*
		assertEquals("besoinDAO is not empty", new Long(0), besoinDAO.count());
		assertEquals("reponseDAO is not empty", new Long(0), reponseDAO.count());
		
		Besoin besoin1 = new BesoinNode("besoin 1");
		besoinDAO.create(besoin1);

			Besoin besoin11 = besoinDAO.create("besoin 1.1");
			besoin1.addBesoin(besoin11);
		
			Besoin besoin12 = besoinDAO.create("besoin 1.2");
			besoin1.addBesoin(besoin12);

		besoinDAO.update(besoin1);
			
		Besoin besoin1FromDAO = besoinDAO.retrieve(besoin1.getNom());
		assertEquals("Bad number of Besoin", new Long(2), new Long(besoin1FromDAO.getBesoins().size()));

		Besoin besoin2 = besoinDAO.create("besoin 2");
		
			Besoin besoin21 = besoinDAO.create("besoin 2.1");
			besoin2.addBesoin(besoin21);
			
				Besoin besoin211 = besoinDAO.create("besoin 2.1.1");
				besoin21.addBesoin(besoin211);
				
				Besoin besoin212 = besoinDAO.create("besoin 2.1.2");
				besoin21.addBesoin(besoin212);
		
			Besoin besoin22 = besoinDAO.create("besoin 2.2");
			besoin2.addBesoin(besoin22);

		besoinDAO.update(besoin2);
		
		Besoin besoin2FromDAO = besoinDAO.retrieve(besoin2.getNom());
		assertEquals("Bad number of Besoin", new Long(2), new Long(besoin2FromDAO.getBesoins().size()));

		assertEquals("Bad number of Besoin", new Long(8), besoinDAO.count());
		
		Reponse reponse11 = reponseDAO.create("réponse 1.1");
		besoin11.addReponse(reponse11);
		besoinDAO.update(besoin11);

		Reponse reponse12 = reponseDAO.create("réponse 1.2");
		besoin12.addReponse(reponse12);
		besoinDAO.update(besoin12);

		Besoin besoin11FromDAO = besoinDAO.retrieve(besoin11.getNom());
		assertEquals("Bad number of Reponse", new Long(1), new Long(besoin11FromDAO.getReponses().size()));
		
		assertEquals("Bad number of Reponse", new Long(2), reponseDAO.count());
		*/
	}

	/**
	 * GetBesoinsRacines
	 */
	@Test
	public final void t05_TestGetBesoinsRacines() {
		assertEquals(new Long(0), besoinReponseService.getBesoinDAO().count());

		Besoin besoin1 = new BesoinNode("besoin de test t05_TestGetBesoinsRacines 1");
		Besoin besoin11 = new BesoinNode("besoin de test t05_TestGetBesoinsRacines 1.1", besoin1);
		Besoin besoin111 = new BesoinNode("besoin de test t05_TestGetBesoinsRacines 1.1.1", besoin11);
		Besoin besoin112 = new BesoinNode("besoin de test t05_TestGetBesoinsRacines 1.1.2", besoin11);
		Besoin besoin12 = new BesoinNode("besoin de test t05_TestGetBesoinsRacines 1.2", besoin1);
		Besoin besoin13 = new BesoinNode("besoin de test t05_TestGetBesoinsRacines 1.3", besoin1);
		Besoin besoin2 = new BesoinNode("besoin de test t05_TestGetBesoinsRacines 2");
		Besoin besoin3 = new BesoinNode("besoin de test t05_TestGetBesoinsRacines 3");
		
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
