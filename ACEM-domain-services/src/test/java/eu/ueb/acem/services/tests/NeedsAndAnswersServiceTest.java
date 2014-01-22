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
import eu.ueb.acem.services.NeedsAndAnswersService;

/**
 * @author Grégoire Colbert @since 2013-11-27
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/domain-services-test-context.xml")
public class NeedsAndAnswersServiceTest {
	// TODO : trouver comment ne pas appeler BesoinNode (on est dans la couche "services")

	@Autowired
	private NeedsAndAnswersService needsAndAnswersService;
	
	public NeedsAndAnswersServiceTest() {
	}

	@Before
	public void before() {
		needsAndAnswersService.getBesoinDAO().deleteAll();
		assertEquals(new Long(0), needsAndAnswersService.getBesoinDAO().count());

		needsAndAnswersService.getReponseDAO().deleteAll();
		assertEquals(new Long(0), needsAndAnswersService.getReponseDAO().count());
	}

	@After
	public void after() {
		needsAndAnswersService.getBesoinDAO().deleteAll();
		assertEquals(new Long(0), needsAndAnswersService.getBesoinDAO().count());

		needsAndAnswersService.getReponseDAO().deleteAll();
		assertEquals(new Long(0), needsAndAnswersService.getReponseDAO().count());
	}

	/**
	 * GetBesoinsRacines
	 */
	@Test
	public final void t01_TestGetBesoinsRacines() {
		Besoin need1 = new BesoinNode("t01 need 1");
		Besoin need11 = new BesoinNode("t01 need 1.1");
		need1.addChild(need11);
		Besoin need111 = new BesoinNode("t01 need 1.1.1");
		need11.addChild(need111);
		Besoin need112 = new BesoinNode("t01 need 1.1.2");
		need11.addChild(need112);
		Besoin need12 = new BesoinNode("t01 need 1.2");
		need1.addChild(need12);
		Besoin need13 = new BesoinNode("t01 need 1.3");
		need1.addChild(need13);
		Besoin need2 = new BesoinNode("t01 need 2");
		Besoin need3 = new BesoinNode("t01 need 3");

		needsAndAnswersService.getBesoinDAO().create(need1);
		needsAndAnswersService.getBesoinDAO().create(need11);
		needsAndAnswersService.getBesoinDAO().create(need111);
		needsAndAnswersService.getBesoinDAO().create(need112);
		needsAndAnswersService.getBesoinDAO().create(need12);
		needsAndAnswersService.getBesoinDAO().create(need13);
		needsAndAnswersService.getBesoinDAO().create(need2);
		needsAndAnswersService.getBesoinDAO().create(need3);

		Set<Besoin> rootNeeds = needsAndAnswersService.getAssociatedNeedsOf(null);
		assertEquals(3, rootNeeds.size());
	}

	/**
	 * GetBesoinsWithParent
	 */
	@Test
	public final void t02_TestGetBesoinsLies() {
		Besoin need1 = new BesoinNode("t02 need 1");
		needsAndAnswersService.getBesoinDAO().create(need1);

		Besoin need11 = new BesoinNode("t02 need 1.1");
		needsAndAnswersService.getBesoinDAO().create(need11);
		need1.addChild(need11);
		needsAndAnswersService.getBesoinDAO().update(need1);
		needsAndAnswersService.getBesoinDAO().update(need11);

		Besoin need111 = new BesoinNode("t02 need 1.1.1");
		needsAndAnswersService.getBesoinDAO().create(need111);
		need11.addChild(need111);
		needsAndAnswersService.getBesoinDAO().update(need11);
		needsAndAnswersService.getBesoinDAO().update(need111);

		Set<Besoin> childrenOfNeed1 = needsAndAnswersService.getAssociatedNeedsOf(need1);
		assertEquals(new Long(1), new Long(childrenOfNeed1.size()));

		Set<Besoin> childrenOfNeed11 = needsAndAnswersService.getAssociatedNeedsOf(need11);
		assertEquals(new Long(1), new Long(childrenOfNeed11.size()));

		Besoin need12 = new BesoinNode("t02 need 1.2");
		needsAndAnswersService.getBesoinDAO().create(need12);
		need1.addChild(need12);
		needsAndAnswersService.getBesoinDAO().update(need1);
		needsAndAnswersService.getBesoinDAO().update(need12);

		// We add children to need12
		Besoin need121 = new BesoinNode("t02 need 1.2.1");
		need12.addChild(need121);
		needsAndAnswersService.getBesoinDAO().create(need121);
		Besoin need122 = new BesoinNode("t02 need 1.2.2");
		need12.addChild(need122);
		needsAndAnswersService.getBesoinDAO().create(need122);
		Besoin need123 = new BesoinNode("t02 need 1.2.3");
		need12.addChild(need123);
		needsAndAnswersService.getBesoinDAO().create(need123);
	}

}
