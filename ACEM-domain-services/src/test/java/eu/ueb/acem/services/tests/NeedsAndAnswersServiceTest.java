/**
 *     Copyright Grégoire COLBERT 2013
 * 
 *     This file is part of Atelier de Création d'Enseignement Multimodal (ACEM).
 * 
 *     ACEM is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 * 
 *     ACEM is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 * 
 *     You should have received a copy of the GNU General Public License
 *     along with ACEM.  If not, see <http://www.gnu.org/licenses/>
 */
package eu.ueb.acem.services.tests;

import java.util.Collection;

import javax.inject.Inject;

import junit.framework.TestCase;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.annotation.Rollback;
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

	/**
	 * GetBesoinsRacines
	 */
	@Test
	@Transactional
	@Rollback(true)
	@SuppressWarnings("unused")
	public final void t01_TestGetBesoinsRacines() {
		PedagogicalNeed need1 = needsAndAnswersService.createPedagogicalNeed("t01 need 1");
		PedagogicalNeed need11 = needsAndAnswersService.createPedagogicalNeed("t01 need 1.1");
		need1.getChildren().add(need11);

		PedagogicalNeed need111 = needsAndAnswersService.createPedagogicalNeed("t01 need 1.1.1");
		need11.getChildren().add(need111);
		PedagogicalNeed need112 = needsAndAnswersService.createPedagogicalNeed("t01 need 1.1.2");
		need11.getChildren().add(need112);
		need11 = needsAndAnswersService.updatePedagogicalNeed(need11);

		PedagogicalNeed need12 = needsAndAnswersService.createPedagogicalNeed("t01 need 1.2");
		need1.getChildren().add(need12);
		PedagogicalNeed need13 = needsAndAnswersService.createPedagogicalNeed("t01 need 1.3");
		need1.getChildren().add(need13);
		PedagogicalNeed need2 = needsAndAnswersService.createPedagogicalNeed("t01 need 2");
		PedagogicalNeed need3 = needsAndAnswersService.createPedagogicalNeed("t01 need 3");

		need1 = needsAndAnswersService.updatePedagogicalNeed(need1);

		Collection<PedagogicalNeed> rootNeeds = needsAndAnswersService.retrieveNeedsAtRoot();
		assertEquals(3, rootNeeds.size());
	}

	/**
	 * GetBesoinsWithParent
	 */
	@Test
	@Transactional
	@Rollback(true)
	public final void t02_TestGetBesoinsLies() {
		PedagogicalNeed need1 = needsAndAnswersService.createPedagogicalNeed("t02 need 1");

		PedagogicalNeed need11 = needsAndAnswersService.createPedagogicalNeed("t02 need 1.1");
		need1.getChildren().add(need11);
		need1 = needsAndAnswersService.updatePedagogicalNeed(need1);
		need11 = needsAndAnswersService.updatePedagogicalNeed(need11);

		PedagogicalNeed need111 = needsAndAnswersService.createPedagogicalNeed("t02 need 1.1.1");
		need11.getChildren().add(need111);
		need11 = needsAndAnswersService.updatePedagogicalNeed(need11);
		need111 = needsAndAnswersService.updatePedagogicalNeed(need111);

		assertEquals(new Long(1), new Long(need1.getChildren().size()));

		assertEquals(new Long(1), new Long(need11.getChildren().size()));

		PedagogicalNeed need12 = needsAndAnswersService.createPedagogicalNeed("t02 need 1.2");
		need1.getChildren().add(need12);
		need1 = needsAndAnswersService.updatePedagogicalNeed(need1);
		need12 = needsAndAnswersService.updatePedagogicalNeed(need12);

		PedagogicalNeed need121 = needsAndAnswersService.createPedagogicalNeed("t02 need 1.2.1");
		need12.getChildren().add(need121);
		need121 = needsAndAnswersService.updatePedagogicalNeed(need121);
		PedagogicalNeed need122 = needsAndAnswersService.createPedagogicalNeed("t02 need 1.2.2");
		need12.getChildren().add(need122);
		need122 = needsAndAnswersService.updatePedagogicalNeed(need122);
		PedagogicalNeed need123 = needsAndAnswersService.createPedagogicalNeed("t02 need 1.2.3");
		need12.getChildren().add(need123);
		need123 = needsAndAnswersService.updatePedagogicalNeed(need123);
		need12 = needsAndAnswersService.updatePedagogicalNeed(need12);

		PedagogicalNeed need12bis = needsAndAnswersService.retrievePedagogicalNeed(need12.getId(), true);
		assertEquals(3, need12bis.getChildren().size());
		assertTrue(need12bis.getChildren().contains(need123));
	}

}
