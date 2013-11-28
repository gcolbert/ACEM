package eu.ueb.acem.services.tests;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import eu.ueb.acem.dal.bleu.BesoinDAO;
import eu.ueb.acem.dal.bleu.ReponseDAO;
import eu.ueb.acem.domain.beans.bleu.Besoin;
import eu.ueb.acem.domain.beans.bleu.Reponse;

/**
 * @author gcolbert @since 2013-11-27
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/domain-services-test-context.xml")
public class BesoinReponseTest {

	@Autowired
	private BesoinDAO besoinDAO;
	
	@Autowired
	private ReponseDAO reponseDAO;

	public BesoinReponseTest() {
	}

	@Before
	public void before() {
		besoinDAO.deleteAll();
		reponseDAO.deleteAll();
	}

	@After
	public void after() {
		besoinDAO.deleteAll();
		reponseDAO.deleteAll();
	}
	
	/**
	 * t01_TestBesoinReponseTestAddBesoin
	 */
	@Test
	public final void t01_TestBesoinReponseTestAddBesoin() {
		assertEquals("besoinDAO is not empty", new Long(0), besoinDAO.count());
		assertEquals("reponseDAO is not empty", new Long(0), reponseDAO.count());
		
		Besoin besoin1 = besoinDAO.create("besoin 1");

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

	}

}
