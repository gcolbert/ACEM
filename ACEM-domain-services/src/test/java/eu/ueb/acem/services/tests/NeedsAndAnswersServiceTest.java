package eu.ueb.acem.services.tests;

import java.util.Collection;

import javax.inject.Inject;

import junit.framework.TestCase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import eu.ueb.acem.domain.beans.bleu.PedagogicalNeed;
import eu.ueb.acem.services.NeedsAndAnswersService;

/**
 * @author Grégoire Colbert
 * @since 2013-11-27
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/domain-services-test-context.xml")
public class NeedsAndAnswersServiceTest extends TestCase {

	@Inject
	private NeedsAndAnswersService needsAndAnswersService;
	
	public NeedsAndAnswersServiceTest() {
	}

	@Before
	public void before() {
		needsAndAnswersService.deleteAllNeeds();
		assertEquals(new Long(0), needsAndAnswersService.countNeeds());

		needsAndAnswersService.deleteAllAnswers();
		assertEquals(new Long(0), needsAndAnswersService.countAnswers());
	}

	@After
	public void after() {
		before();
	}

	/**
	 * GetBesoinsRacines
	 */
	@SuppressWarnings("unused")
	@Transactional
	@Test
	public final void t01_TestGetBesoinsRacines() {
		PedagogicalNeed need1 = needsAndAnswersService.createNeed("t01 need 1");
		PedagogicalNeed need11 = needsAndAnswersService.createNeed("t01 need 1.1");
		need1.addChild(need11);

		PedagogicalNeed need111 = needsAndAnswersService.createNeed("t01 need 1.1.1");
		need11.addChild(need111);
		PedagogicalNeed need112 = needsAndAnswersService.createNeed("t01 need 1.1.2");
		need11.addChild(need112);
		need11 = needsAndAnswersService.updateNeed(need11);

		PedagogicalNeed need12 = needsAndAnswersService.createNeed("t01 need 1.2");
		need1.addChild(need12);
		PedagogicalNeed need13 = needsAndAnswersService.createNeed("t01 need 1.3");
		need1.addChild(need13);
		PedagogicalNeed need2 = needsAndAnswersService.createNeed("t01 need 2");
		PedagogicalNeed need3 = needsAndAnswersService.createNeed("t01 need 3");

		need1 = needsAndAnswersService.updateNeed(need1);

		Collection<PedagogicalNeed> rootNeeds = needsAndAnswersService.retrieveNeedsAtRoot();
		assertEquals(3, rootNeeds.size());
	}

	/**
	 * GetBesoinsWithParent
	 */
	@Test
	@Transactional
	public final void t02_TestGetBesoinsLies() {
		PedagogicalNeed need1 = needsAndAnswersService.createNeed("t02 need 1");

		PedagogicalNeed need11 = needsAndAnswersService.createNeed("t02 need 1.1");
		need1.addChild(need11);
		need1 = needsAndAnswersService.updateNeed(need1);
		need11 = needsAndAnswersService.updateNeed(need11);

		PedagogicalNeed need111 = needsAndAnswersService.createNeed("t02 need 1.1.1");
		need11.addChild(need111);
		need11 = needsAndAnswersService.updateNeed(need11);
		need111 = needsAndAnswersService.updateNeed(need111);

		assertEquals(new Long(1), new Long(need1.getChildren().size()));

		assertEquals(new Long(1), new Long(need11.getChildren().size()));

		PedagogicalNeed need12 = needsAndAnswersService.createNeed("t02 need 1.2");
		need1.addChild(need12);
		need1 = needsAndAnswersService.updateNeed(need1);
		need12 = needsAndAnswersService.updateNeed(need12);

		PedagogicalNeed need121 = needsAndAnswersService.createNeed("t02 need 1.2.1");
		need12.addChild(need121);
		need121 = needsAndAnswersService.updateNeed(need121);
		PedagogicalNeed need122 = needsAndAnswersService.createNeed("t02 need 1.2.2");
		need12.addChild(need122);
		need122 = needsAndAnswersService.updateNeed(need122);
		PedagogicalNeed need123 = needsAndAnswersService.createNeed("t02 need 1.2.3");
		need12.addChild(need123);
		need123 = needsAndAnswersService.updateNeed(need123);
		need12 = needsAndAnswersService.updateNeed(need12);

		PedagogicalNeed need12bis = needsAndAnswersService.retrieveNeed(need12.getId());
		assertEquals(3, need12bis.getChildren().size());
		assertTrue(need12bis.getChildren().contains(need123));
	}

}
