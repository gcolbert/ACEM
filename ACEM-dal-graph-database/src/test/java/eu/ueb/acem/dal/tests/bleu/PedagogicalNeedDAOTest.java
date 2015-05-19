/**
 *     Copyright Université Européenne de Bretagne 2012-2015
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
package eu.ueb.acem.dal.tests.bleu;

import java.util.Collection;
import java.util.Set;

import javax.inject.Inject;

import junit.framework.TestCase;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import eu.ueb.acem.dal.DAO;
import eu.ueb.acem.dal.bleu.PedagogicalNeedDAO;
import eu.ueb.acem.domain.beans.bleu.PedagogicalAnswer;
import eu.ueb.acem.domain.beans.bleu.PedagogicalNeed;
import eu.ueb.acem.domain.beans.bleu.neo4j.PedagogicalNeedNode;

/**
 * @author Grégoire Colbert
 * @since 2013-11-22
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/dal-test-context.xml")
public class PedagogicalNeedDAOTest extends TestCase {

	/**
	 * For Logging.
	 */
	@SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory.getLogger(PedagogicalNeedDAOTest.class);

	@Inject
	private PedagogicalNeedDAO<Long, PedagogicalNeed> pedagogicalNeedDAO;

	@Inject
	private DAO<Long, PedagogicalAnswer> wrongDAO;

	public PedagogicalNeedDAOTest() {
	}

	/**
	 * Create
	 */
	@Test
	@Transactional
	@Rollback(true)
	public final void t01_TestPedagogicalNeedDAOCreate() {
		// We create a new object in the datastore
		PedagogicalNeed need1 = new PedagogicalNeedNode("t01 need");

		// We create our object
		need1 = pedagogicalNeedDAO.create(need1);

		// We check that "create" is idempotent (multiple calls must not
		// duplicate data)
		need1 = pedagogicalNeedDAO.create(need1);

		// There must exactly 1 object in the datastore
		assertEquals("There are more than one object in the datastore", new Long(1), pedagogicalNeedDAO.count());
	}

	/**
	 * Retrieve
	 */
	@Test
	@Transactional
	@Rollback(true)
	public final void t02a_TestPedagogicalNeedDAORetrieve() {
		// We create a new object in the datastore
		PedagogicalNeed need1 = pedagogicalNeedDAO.create(new PedagogicalNeedNode("t02 need"));
		assertEquals(new Long(1), pedagogicalNeedDAO.count());

		// We retrieve the object from the datastore using its id
		PedagogicalNeed need1bis = pedagogicalNeedDAO.retrieveById(need1.getId());
		assertEquals(need1, need1bis);
		assertEquals(need1.getId(), need1bis.getId());
		assertEquals(need1.getName(), need1bis.getName());

		// We retrieve the object from the datastore using its name
		Collection<PedagogicalNeed> needs = pedagogicalNeedDAO.retrieveByName(need1.getName());
		assertTrue(needs.contains(need1));

		assertTrue(pedagogicalNeedDAO.retrieveByName(need1.getName()).contains(need1));
	}

	/**
	 * Retrieve an A entity from DAO<B>
	 */
	@Test
	@Transactional
	@Rollback(true)
	public final void t02b_TestRetrieveUsingWrongDAO() {
		// We create a new object in the datastore
		PedagogicalNeed need1 = pedagogicalNeedDAO.create(new PedagogicalNeedNode("a need"));
		assertEquals("There must be exactly one object in the datastore", new Long(1), pedagogicalNeedDAO.count());

		// We retrieve our PedagogicalNeed from a wrong DAO
		assertEquals("There must be exactly zero object dealt by the wrongDAO", new Long(0), wrongDAO.count());
		PedagogicalAnswer nodeThatShouldBeNull = wrongDAO.retrieveById(need1.getId());
		assertNull("An entity retrieved through the wrong DAO should be null.", nodeThatShouldBeNull);
	}

	/**
	 * Update
	 */
	@Test
	@Transactional
	@Rollback(true)
	public final void t03_TestPedagogicalNeedDAOUpdate() {
		// Setting up
		PedagogicalNeed need1 = new PedagogicalNeedNode("t03 need");
		need1 = pedagogicalNeedDAO.create(need1);
		assertEquals(new Long(1), pedagogicalNeedDAO.count());

		// We update the object
		need1.setName("t03 need including modification");
		need1 = pedagogicalNeedDAO.update(need1);

		// We check that the object cannot be retrieved anymore with its
		// original name
		Collection<PedagogicalNeed> needs = pedagogicalNeedDAO.retrieveByName("t03 need");
		assertTrue(needs.isEmpty());

		// We check that the object can be retrieved with its new name
		assertTrue(pedagogicalNeedDAO.retrieveByName("t03 need including modification").contains(need1));

		// We check that there is still only 1 object in the datastore
		assertEquals(new Long(1), pedagogicalNeedDAO.count());
	}

	/**
	 * Delete
	 */
	@Test
	@Transactional
	@Rollback(true)
	public final void t04_TestPedagogicalNeedDAODelete() {
		PedagogicalNeed need1 = new PedagogicalNeedNode("t04 need");
		need1 = pedagogicalNeedDAO.create(need1);
		assertEquals(new Long(1), pedagogicalNeedDAO.count());

		// We delete the object
		pedagogicalNeedDAO.delete(need1);

		// We check that there is 0 object in the datastore
		assertEquals(new Long(0), pedagogicalNeedDAO.count());
	}

	/**
	 * Retrieve all
	 */
	@Test
	@Transactional
	@Rollback(true)
	@SuppressWarnings("unused")
	public final void t05_TestPedagogicalNeedDAORetrieveAll() {
		PedagogicalNeed need1 = pedagogicalNeedDAO.create(new PedagogicalNeedNode("t05 need 1"));
		PedagogicalNeed need11 = pedagogicalNeedDAO.create(new PedagogicalNeedNode("t05 need 1.1"));
		need1.getChildren().add(need11);
		need1 = pedagogicalNeedDAO.update(need1);
		need11 = pedagogicalNeedDAO.update(need11);

		PedagogicalNeed need12 = pedagogicalNeedDAO.create(new PedagogicalNeedNode("t05 need 1.2"));
		need1.getChildren().add(need12);
		need1 = pedagogicalNeedDAO.update(need1);
		need12 = pedagogicalNeedDAO.update(need12);

		PedagogicalNeed need2 = pedagogicalNeedDAO.create(new PedagogicalNeedNode("t05 need 2"));

		// We retrieve all objects
		Collection<PedagogicalNeed> allNeeds = pedagogicalNeedDAO.retrieveAll();

		// We check that there are 4 objects in the Set
		assertEquals(new Long(4), new Long(allNeeds.size()));
	}

	/**
	 * Check that the relationships are saved
	 */
	@Test
	@Transactional
	@Rollback(true)
	public final void t06_TestPedagogicalNeedDAOCheckParentChildRelationship() {
		// We check that addChild is sufficient to create the parent/child
		// relationship
		PedagogicalNeed need1 = pedagogicalNeedDAO.create(new PedagogicalNeedNode("t06 need 1"));
		PedagogicalNeed need11 = pedagogicalNeedDAO.create(new PedagogicalNeedNode("t06 need 1.1"));
		need1.getChildren().add(need11);
		need11.getParents().add(need1);
		need1 = pedagogicalNeedDAO.update(need1);
		need11 = pedagogicalNeedDAO.update(need11);

		// We retrieve the entities and we check that entities have parents and
		// children
		PedagogicalNeed need1bis = pedagogicalNeedDAO.retrieveById(need1.getId(), true);
		PedagogicalNeed need11bis = pedagogicalNeedDAO.retrieveById(need11.getId(), true);
		assertEquals(new Long(0), new Long(need1bis.getParents().size()));
		assertEquals(new Long(1), new Long(need1bis.getChildren().size()));
		assertEquals(new Long(0), new Long(need11bis.getChildren().size()));
		assertEquals(new Long(1), new Long(need11bis.getParents().size()));

		// We check that addParent is sufficient to create the parent/child
		// relationship
		PedagogicalNeed need12 = pedagogicalNeedDAO.create(new PedagogicalNeedNode("t06 need 1.2"));
		need12.getParents().add(need1);
		need1 = pedagogicalNeedDAO.update(need1);
		need12 = pedagogicalNeedDAO.update(need12);

		// We retrieve the entities and we check that entities have parents and
		// children
		PedagogicalNeed need1ter = pedagogicalNeedDAO.retrieveById(need1.getId(), true);
		Set<PedagogicalNeed> children = need1ter.getChildren();
		need1ter.setChildren(children);
		PedagogicalNeed need12bis = pedagogicalNeedDAO.retrieveById(need12.getId(), true);
		assertEquals(new Long(0), new Long(need1ter.getParents().size()));
		assertEquals(new Long(2), new Long(need1ter.getChildren().size()));
		assertEquals(new Long(0), new Long(need11bis.getChildren().size()));
		assertEquals(new Long(1), new Long(need11bis.getParents().size()));
		assertEquals(new Long(0), new Long(need12bis.getChildren().size()));
		assertEquals(new Long(1), new Long(need12bis.getParents().size()));
	}

	/**
	 * Check that we can retrieve the PedagogicalNeedNodes that don't have any
	 * parent (roots of the tree)
	 */
	@Test
	@Transactional
	@Rollback(true)
	@SuppressWarnings("unused")
	public final void t07_TestPedagogicalNeedDAORetrieveRootNeeds() {
		PedagogicalNeed need1 = pedagogicalNeedDAO.create(new PedagogicalNeedNode("t07 need 1"));

		PedagogicalNeed need11 = pedagogicalNeedDAO.create(new PedagogicalNeedNode("t07 need 1.1"));
		need1.getChildren().add(need11);
		need11.getParents().add(need1);
		need1 = pedagogicalNeedDAO.update(need1);
		need11 = pedagogicalNeedDAO.update(need11);

		PedagogicalNeed need2 = pedagogicalNeedDAO.create(new PedagogicalNeedNode("t07 need 2"));

		PedagogicalNeed need3 = pedagogicalNeedDAO.create(new PedagogicalNeedNode("t07 need 3"));

		PedagogicalNeed need31 = pedagogicalNeedDAO.create(new PedagogicalNeedNode("t07 need 3.1"));
		need3.getChildren().add(need31);
		need31.getParents().add(need3);
		need3 = pedagogicalNeedDAO.update(need3);
		need31 = pedagogicalNeedDAO.update(need31);

		PedagogicalNeed need32 = pedagogicalNeedDAO.create(new PedagogicalNeedNode("t07 need 3.2"));
		need3.getChildren().add(need32);
		need32.getParents().add(need3);
		need3 = pedagogicalNeedDAO.update(need3);
		need32 = pedagogicalNeedDAO.update(need32);

		Set<PedagogicalNeed> rootNeeds = pedagogicalNeedDAO.retrieveNeedsAtRoot();
		assertEquals(new Long(3), new Long(rootNeeds.size()));
	}

	/**
	 * Check that we can retrieve some children
	 */
	@Test
	@Transactional
	@Rollback(true)
	public final void t08_TestPedagogicalNeedDAORetrieveChildren() {
		PedagogicalNeed need1 = pedagogicalNeedDAO.create(new PedagogicalNeedNode("t08 need 1"));
		PedagogicalNeed need11 = pedagogicalNeedDAO.create(new PedagogicalNeedNode("t08 need 1.1"));
		need1.getChildren().add(need11);
		pedagogicalNeedDAO.update(need1);
		pedagogicalNeedDAO.update(need11);
		// We check that the node with name "t08 need 1" has one child
		assertEquals(new Long(1), new Long(need1.getChildren().size()));

		PedagogicalNeed need111 = pedagogicalNeedDAO.create(new PedagogicalNeedNode("t08 need 1.1.1"));
		need11.getChildren().add(need111);
		pedagogicalNeedDAO.update(need11);
		pedagogicalNeedDAO.update(need111);
		// We check that the node with name "t08 need 1.1" has one child
		assertEquals(new Long(1), new Long(need11.getChildren().size()));

		PedagogicalNeed need12 = pedagogicalNeedDAO.create(new PedagogicalNeedNode("t08 need 1.2"));
		need1.getChildren().add(need12);
		pedagogicalNeedDAO.update(need1);
		pedagogicalNeedDAO.update(need12);
		// We add children to need12
		PedagogicalNeed need121 = pedagogicalNeedDAO.create(new PedagogicalNeedNode("t08 need 1.2.1"));
		need12.getChildren().add(need121);
		PedagogicalNeed need122 = pedagogicalNeedDAO.create(new PedagogicalNeedNode("t08 need 1.2.2"));
		need12.getChildren().add(need122);
		PedagogicalNeed need123 = pedagogicalNeedDAO.create(new PedagogicalNeedNode("t08 need 1.2.3"));
		need12.getChildren().add(need123);
		pedagogicalNeedDAO.update(need12);
		// We check that the node with name "t08 need 1.2" has 3 children
		assertEquals(new Long(3), new Long(need12.getChildren().size()));
	}

	/**
	 * Check that we can retrieve some children using getChildren
	 */
	@Test
	@Transactional
	@Rollback(true)
	public final void t09_TestPedagogicalNeedDAORetrieveChildrenAutomatically() {
		PedagogicalNeed need1 = pedagogicalNeedDAO.create(new PedagogicalNeedNode("t09 need 1"));
		PedagogicalNeed need11 = pedagogicalNeedDAO.create(new PedagogicalNeedNode("t09 need 1.1"));
		need1.getChildren().add(need11);
		pedagogicalNeedDAO.update(need1);

		// Here we want to test the children of the reloaded PedagogicalNeed,
		// that's why the second parameter of retrieveById is set to 'true'
		PedagogicalNeed need1bis = pedagogicalNeedDAO.retrieveById(need1.getId(), true);
		for (PedagogicalNeed need1child : need1bis.getChildren()) {
			// If it was false, getName() would return null here
			assertEquals("t09 need 1.1", need1child.getName());
		}

		// Let's test if setting the second parameter to 'false' works as
		// expected
		PedagogicalNeed need1ter = pedagogicalNeedDAO.retrieveById(need1.getId(), false);
		for (PedagogicalNeed need1child : need1ter.getChildren()) {
			// If it was false, getName() would return null here
			assertNull("The child's name should be null because we called retrieveById with initialize=false",
					need1child.getName());
		}
	}

}
