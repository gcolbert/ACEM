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
import eu.ueb.acem.services.BesoinsReponsesService;

/**
 * @author gcolbert @since 2013-11-27
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/domain-services-test-context.xml")
public class BesoinsReponsesTest {
	// TODO : trouver comment ne pas appeler BesoinNode (on est dans la couche "services")

	@Autowired
	private BesoinsReponsesService besoinsReponsesService;
	
	public BesoinsReponsesTest() {
	}

	@Before
	public void before() {
		besoinsReponsesService.getBesoinDAO().deleteAll();
		assertEquals(new Long(0), besoinsReponsesService.getBesoinDAO().count());

		besoinsReponsesService.getReponseDAO().deleteAll();
		assertEquals(new Long(0), besoinsReponsesService.getReponseDAO().count());
	}

	@After
	public void after() {
		besoinsReponsesService.getBesoinDAO().deleteAll();
		assertEquals(new Long(0), besoinsReponsesService.getBesoinDAO().count());

		besoinsReponsesService.getReponseDAO().deleteAll();
		assertEquals(new Long(0), besoinsReponsesService.getReponseDAO().count());
	}

	/**
	 * GetBesoinsRacines
	 */
	@Test
	public final void t01_TestGetBesoinsRacines() {
		assertEquals(new Long(0), besoinsReponsesService.getBesoinDAO().count());

		Besoin besoin1 = new BesoinNode("besoin de test t01_TestGetBesoinsRacines 1");
		Besoin besoin11 = new BesoinNode("besoin de test t01_TestGetBesoinsRacines 1.1");
		besoin1.addEnfant(besoin11);
		Besoin besoin111 = new BesoinNode("besoin de test t01_TestGetBesoinsRacines 1.1.1");
		besoin11.addEnfant(besoin111);
		Besoin besoin112 = new BesoinNode("besoin de test t01_TestGetBesoinsRacines 1.1.2");
		besoin11.addEnfant(besoin112);
		Besoin besoin12 = new BesoinNode("besoin de test t01_TestGetBesoinsRacines 1.2");
		besoin1.addEnfant(besoin12);
		Besoin besoin13 = new BesoinNode("besoin de test t01_TestGetBesoinsRacines 1.3");
		besoin1.addEnfant(besoin13);
		Besoin besoin2 = new BesoinNode("besoin de test t01_TestGetBesoinsRacines 2");
		Besoin besoin3 = new BesoinNode("besoin de test t01_TestGetBesoinsRacines 3");

		besoinsReponsesService.getBesoinDAO().create(besoin1);
		besoinsReponsesService.getBesoinDAO().create(besoin11);
		besoinsReponsesService.getBesoinDAO().create(besoin111);
		besoinsReponsesService.getBesoinDAO().create(besoin112);
		besoinsReponsesService.getBesoinDAO().create(besoin12);
		besoinsReponsesService.getBesoinDAO().create(besoin13);
		besoinsReponsesService.getBesoinDAO().create(besoin2);
		besoinsReponsesService.getBesoinDAO().create(besoin3);

		Set<Besoin> besoinsRacines = besoinsReponsesService.getBesoinsLies(null);
		assertEquals(3, besoinsRacines.size());
	}

	/**
	 * GetBesoinsWithParent
	 */
	@Test
	public final void t02_TestGetBesoinsLies() {
		Besoin besoin1 = new BesoinNode("besoin de test t02_TestGetBesoinsLies 1");
		besoinsReponsesService.getBesoinDAO().create(besoin1);

		Besoin besoin11 = new BesoinNode("besoin de test t02_TestGetBesoinsLies 1.1");
		besoinsReponsesService.getBesoinDAO().create(besoin11);
		besoin1.addEnfant(besoin11);
		besoinsReponsesService.getBesoinDAO().update(besoin1);

		Besoin besoin111 = new BesoinNode("besoin de test t02_TestGetBesoinsLies 1.1.1");
		besoinsReponsesService.getBesoinDAO().create(besoin111);
		besoin11.addEnfant(besoin111);
		besoinsReponsesService.getBesoinDAO().update(besoin11);

		// We check that the node with name "besoin de test t07_TestBesoinDAORetrieveChildren 1" has one child
		Set<Besoin> childrenOfBesoin1 = besoinsReponsesService.getBesoinsLies(besoin1);
		assertEquals(new Long(1), new Long(childrenOfBesoin1.size()));

		// We check that the node with name "besoin de test t07_TestBesoinDAORetrieveChildren 1.1" has one child
		Set<Besoin> childrenOfBesoin11 = besoinsReponsesService.getBesoinsLies(besoin11);
		assertEquals(new Long(1), new Long(childrenOfBesoin11.size()));

		Besoin besoin12 = new BesoinNode("besoin de test t02_TestGetBesoinsLies 1.2");
		besoinsReponsesService.getBesoinDAO().create(besoin12);
		besoin1.addEnfant(besoin12);
		besoinsReponsesService.getBesoinDAO().update(besoin1);

		// We add children to besoin12
		Besoin besoin121 = new BesoinNode("besoin de test t02_TestGetBesoinsLies 1.2.1");
		besoin12.addEnfant(besoin121);
		besoinsReponsesService.getBesoinDAO().create(besoin121);
		Besoin besoin122 = new BesoinNode("besoin de test t02_TestGetBesoinsLies 1.2.2");
		besoin12.addEnfant(besoin122);
		besoinsReponsesService.getBesoinDAO().create(besoin122);
		Besoin besoin123 = new BesoinNode("besoin de test t02_TestGetBesoinsLies 1.2.3");
		besoin12.addEnfant(besoin123);
		besoinsReponsesService.getBesoinDAO().create(besoin123);
	}

}
