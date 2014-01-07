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
public class BesoinsReponsesServiceTest {
	// TODO : trouver comment ne pas appeler BesoinNode (on est dans la couche "services")

	@Autowired
	private BesoinsReponsesService besoinsReponsesService;
	
	public BesoinsReponsesServiceTest() {
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
		Besoin besoin1 = new BesoinNode("t01 besoin 1");
		Besoin besoin11 = new BesoinNode("t01 besoin 1.1");
		besoin1.addChild(besoin11);
		Besoin besoin111 = new BesoinNode("t01 besoin 1.1.1");
		besoin11.addChild(besoin111);
		Besoin besoin112 = new BesoinNode("t01 besoin 1.1.2");
		besoin11.addChild(besoin112);
		Besoin besoin12 = new BesoinNode("t01 besoin 1.2");
		besoin1.addChild(besoin12);
		Besoin besoin13 = new BesoinNode("t01 besoin 1.3");
		besoin1.addChild(besoin13);
		Besoin besoin2 = new BesoinNode("t01 besoin 2");
		Besoin besoin3 = new BesoinNode("t01 besoin 3");

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
		Besoin besoin1 = new BesoinNode("t02 besoin 1");
		besoinsReponsesService.getBesoinDAO().create(besoin1);

		Besoin besoin11 = new BesoinNode("t02 besoin 1.1");
		besoinsReponsesService.getBesoinDAO().create(besoin11);
		besoin1.addChild(besoin11);
		besoinsReponsesService.getBesoinDAO().update(besoin1);
		besoinsReponsesService.getBesoinDAO().update(besoin11);

		Besoin besoin111 = new BesoinNode("t02 besoin 1.1.1");
		besoinsReponsesService.getBesoinDAO().create(besoin111);
		besoin11.addChild(besoin111);
		besoinsReponsesService.getBesoinDAO().update(besoin11);
		besoinsReponsesService.getBesoinDAO().update(besoin111);

		Set<Besoin> childrenOfBesoin1 = besoinsReponsesService.getBesoinsLies(besoin1);
		assertEquals(new Long(1), new Long(childrenOfBesoin1.size()));

		Set<Besoin> childrenOfBesoin11 = besoinsReponsesService.getBesoinsLies(besoin11);
		assertEquals(new Long(1), new Long(childrenOfBesoin11.size()));

		Besoin besoin12 = new BesoinNode("t02 besoin 1.2");
		besoinsReponsesService.getBesoinDAO().create(besoin12);
		besoin1.addChild(besoin12);
		besoinsReponsesService.getBesoinDAO().update(besoin1);
		besoinsReponsesService.getBesoinDAO().update(besoin12);

		// We add children to besoin12
		Besoin besoin121 = new BesoinNode("t02 besoin 1.2.1");
		besoin12.addChild(besoin121);
		besoinsReponsesService.getBesoinDAO().create(besoin121);
		Besoin besoin122 = new BesoinNode("t02 besoin 1.2.2");
		besoin12.addChild(besoin122);
		besoinsReponsesService.getBesoinDAO().create(besoin122);
		Besoin besoin123 = new BesoinNode("t02 besoin 1.2.3");
		besoin12.addChild(besoin123);
		besoinsReponsesService.getBesoinDAO().create(besoin123);
	}

}
