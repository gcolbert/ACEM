package eu.ueb.acem.services.tests;

import static org.junit.Assert.*;

import java.util.Collection;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import eu.ueb.acem.domain.beans.bleu.Besoin;
import eu.ueb.acem.services.NeedsAndAnswersService;

/**
 * @author Grégoire Colbert @since 2013-11-27
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/domain-services-test-context.xml")
public class NeedsAndAnswersServiceTest {

	@Autowired
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
		needsAndAnswersService.deleteAllNeeds();
		assertEquals(new Long(0), needsAndAnswersService.countNeeds());

		needsAndAnswersService.deleteAllAnswers();
		assertEquals(new Long(0), needsAndAnswersService.countAnswers());
	}

	/**
	 * GetBesoinsRacines
	 */
	@SuppressWarnings("unused")
	@Transactional
	@Test
	public final void t01_TestGetBesoinsRacines() {
		Besoin need1 = needsAndAnswersService.createNeed("t01 need 1");
		Besoin need11 = needsAndAnswersService.createNeed("t01 need 1.1");
		need1.addChild(need11);
		
		Besoin need111 = needsAndAnswersService.createNeed("t01 need 1.1.1");
		need11.addChild(need111);
		Besoin need112 = needsAndAnswersService.createNeed("t01 need 1.1.2");
		need11.addChild(need112);
		
		Besoin need12 = needsAndAnswersService.createNeed("t01 need 1.2");
		need1.addChild(need12);
		Besoin need13 = needsAndAnswersService.createNeed("t01 need 1.3");
		need1.addChild(need13);
		Besoin need2 = needsAndAnswersService.createNeed("t01 need 2");
		Besoin need3 = needsAndAnswersService.createNeed("t01 need 3");

		Collection<Besoin> rootNeeds = needsAndAnswersService.getAssociatedNeedsOf(null);
		assertEquals(3, rootNeeds.size());
	}

	/**
	 * GetBesoinsWithParent
	 */
	@Test
	@Transactional
	public final void t02_TestGetBesoinsLies() {
		Besoin need1 = needsAndAnswersService.createNeed("t02 need 1");

		Besoin need11 = needsAndAnswersService.createNeed("t02 need 1.1");
		need1.addChild(need11);
		need1 = needsAndAnswersService.updateNeed(need1);
		need11 = needsAndAnswersService.updateNeed(need11);

		Besoin need111 = needsAndAnswersService.createNeed("t02 need 1.1.1");
		need11.addChild(need111);
		need11 = needsAndAnswersService.updateNeed(need11);
		need111 = needsAndAnswersService.updateNeed(need111);

		Collection<Besoin> childrenOfNeed1 = needsAndAnswersService.getAssociatedNeedsOf(need1);
		assertEquals(new Long(1), new Long(childrenOfNeed1.size()));

		Collection<Besoin> childrenOfNeed11 = needsAndAnswersService.getAssociatedNeedsOf(need11);
		assertEquals(new Long(1), new Long(childrenOfNeed11.size()));

		Besoin need12 = needsAndAnswersService.createNeed("t02 need 1.2");
		need1.addChild(need12);
		need1 = needsAndAnswersService.updateNeed(need1);
		need12 = needsAndAnswersService.updateNeed(need12);

		Besoin need121 = needsAndAnswersService.createNeed("t02 need 1.2.1");
		need12.addChild(need121);
		need121 = needsAndAnswersService.updateNeed(need121);
		Besoin need122 = needsAndAnswersService.createNeed("t02 need 1.2.2");
		need12.addChild(need122);
		need122 = needsAndAnswersService.updateNeed(need122);
		Besoin need123 = needsAndAnswersService.createNeed("t02 need 1.2.3");
		need12.addChild(need123);
		need123 = needsAndAnswersService.updateNeed(need123);
		need12 = needsAndAnswersService.updateNeed(need12);

		Collection<Besoin> childrenOfNeed12 = needsAndAnswersService.getAssociatedNeedsOf(need12);
		assertEquals(new Long(3), new Long(childrenOfNeed12.size()));		
	}

}
