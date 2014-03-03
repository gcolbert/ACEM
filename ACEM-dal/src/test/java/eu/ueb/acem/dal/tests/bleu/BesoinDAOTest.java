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
package eu.ueb.acem.dal.tests.bleu;

import java.util.Collection;
import java.util.Set;

import junit.framework.TestCase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import eu.ueb.acem.dal.bleu.BesoinDAO;
import eu.ueb.acem.domain.beans.bleu.Besoin;
import eu.ueb.acem.domain.beans.bleu.neo4j.BesoinNode;

/**
 * @author Grégoire Colbert @since 2013-11-22
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/dal-test-context.xml")
public class BesoinDAOTest extends TestCase {

	/**
	 * For Logging.
	 */
	private static final Logger logger = LoggerFactory.getLogger(BesoinDAOTest.class);

	@Autowired
	private BesoinDAO needDAO;

	public BesoinDAOTest() {
	}

	@Before
	public void before() {
		needDAO.deleteAll();
		assertEquals(new Long(0), needDAO.count());
	}

	@After
	public void after() {
		before();
	}

	/**
	 * Create
	 */
	@Test
	public final void t01_TestBesoinDAOCreate() {
		// We create a new object in the datastore
		Besoin need1 = new BesoinNode("t01 need");

		// We create our object
		need1 = needDAO.create(need1);

		// We check that "create" is idempotent (multiple calls must not
		// duplicate data)
		need1 = needDAO.create(need1);

		// There must exactly 1 object in the datastore
		assertEquals("There are more than one object in the datastore", new Long(1), needDAO.count());
	}

	/**
	 * Retrieve
	 */
	@Test
	@Transactional
	public final void t02_TestBesoinDAORetrieve() {
		// We create a new object in the datastore
		Besoin need1 = needDAO.create(new BesoinNode("t02 need"));
		assertEquals(new Long(1), needDAO.count());

		// We retrieve the object from the datastore using its id
		Besoin need1bis = needDAO.retrieveById(need1.getId());
		assertEquals(need1, need1bis);
		assertEquals(need1.getId(), need1bis.getId());
		assertEquals(need1.getName(), need1bis.getName());

		// We retrieve the object from the datastore using its name
		Collection<Besoin> needs = needDAO.retrieveByName(need1.getName());
		assertTrue(needs.contains(need1));

		assertTrue(needDAO.retrieveByName(need1.getName()).contains(need1));
	}

	/**
	 * Update
	 */
	@Test
	@Transactional
	public final void t03_TestBesoinDAOUpdate() {
		// Setting up
		Besoin need1 = new BesoinNode("t03 need");
		need1 = needDAO.create(need1);
		assertEquals(new Long(1), needDAO.count());

		// We update the object
		need1.setName("t03 need including modification");
		need1 = needDAO.update(need1);

		// We check that the object cannot be retrieved anymore with its
		// original name
		Collection<Besoin> needs = needDAO.retrieveByName("t03 need");
		assertTrue(needs.isEmpty());

		// We check that the object can be retrieved with its new name
		assertTrue(needDAO.retrieveByName("t03 need including modification").contains(need1));

		// We check that there is still only 1 object in the datastore
		assertEquals(new Long(1), needDAO.count());
	}

	/**
	 * Delete
	 */
	@Test
	public final void t04_TestBesoinDAODelete() {
		Besoin need1 = new BesoinNode("t04 need");
		need1 = needDAO.create(need1);
		assertEquals(new Long(1), needDAO.count());

		// We delete the object
		needDAO.delete(need1);

		// We check that there is 0 object in the datastore
		assertEquals(new Long(0), needDAO.count());
	}

	/**
	 * Retrieve all
	 */
	@Test
	@Transactional
	@SuppressWarnings("unused")
	public final void t05_TestBesoinDAORetrieveAll() {
		Besoin need1 = needDAO.create(new BesoinNode("t05 need 1"));
		Besoin need11 = needDAO.create(new BesoinNode("t05 need 1.1"));
		need1.addChild(need11);
		need1 = needDAO.update(need1);
		need11 = needDAO.update(need11);

		Besoin need12 = needDAO.create(new BesoinNode("t05 need 1.2"));
		need1.addChild(need12);
		need1 = needDAO.update(need1);
		need12 = needDAO.update(need12);

		Besoin need2 = needDAO.create(new BesoinNode("t05 need 2"));

		// We retrieve all objects
		Collection<Besoin> allNeeds = needDAO.retrieveAll();

		// We check that there are 4 objects in the Set
		assertEquals(new Long(4), new Long(allNeeds.size()));
	}

	/**
	 * Check that the relationships are saved
	 */
	@Test
	@Transactional
	public final void t06_TestBesoinDAOCheckParentChildRelationship() {
		// We check that addChild is sufficient to create the parent/child
		// relationship
		Besoin need1 = needDAO.create(new BesoinNode("t06 need 1"));
		Besoin need11 = needDAO.create(new BesoinNode("t06 need 1.1"));
		need1.addChild(need11);
		need1 = needDAO.update(need1);
		need11 = needDAO.update(need11);

		// We retrieve the entities and we check that entities have parents and
		// children
		Besoin need1bis = needDAO.retrieveById(need1.getId());
		Besoin need11bis = needDAO.retrieveById(need11.getId());
		assertEquals(new Long(0), new Long(need1bis.getParents().size()));
		assertEquals(new Long(1), new Long(need1bis.getChildren().size()));
		assertEquals(new Long(0), new Long(need11bis.getChildren().size()));
		assertEquals(new Long(1), new Long(need11bis.getParents().size()));

		// We check that addParent is sufficient to create the parent/child
		// relationship
		Besoin need12 = needDAO.create(new BesoinNode("t06 need 1.2"));
		need12.addParent(need1);
		need1 = needDAO.update(need1);
		need12 = needDAO.update(need12);

		// We retrieve the entities and we check that entities have parents and
		// children
		Besoin need1ter = needDAO.retrieveById(need1.getId());
		@SuppressWarnings("unchecked")
		Set<Besoin> children = (Set<Besoin>) need1ter.getChildren();
		need1ter.setChildren(children);
		Besoin need12bis = needDAO.retrieveById(need12.getId());
		assertEquals(new Long(0), new Long(need1ter.getParents().size()));
		assertEquals(new Long(2), new Long(need1ter.getChildren().size()));
		assertEquals(new Long(0), new Long(need11bis.getChildren().size()));
		assertEquals(new Long(1), new Long(need11bis.getParents().size()));
		assertEquals(new Long(0), new Long(need12bis.getChildren().size()));
		assertEquals(new Long(1), new Long(need12bis.getParents().size()));
	}

	/**
	 * Check that we can retrieve the BesoinNodes that don't have any parent
	 * (roots of the tree)
	 */
	@Test
	@Transactional
	@SuppressWarnings("unused")
	public final void t07_TestBesoinDAORetrieveRootNeeds() {
		Besoin need1 = needDAO.create(new BesoinNode("t07 need 1"));

		Besoin need11 = needDAO.create(new BesoinNode("t07 need 1.1"));
		need1.addChild(need11);
		need1 = needDAO.update(need1);
		need11 = needDAO.update(need11);

		Besoin need2 = needDAO.create(new BesoinNode("t07 need 2"));

		Besoin need3 = needDAO.create(new BesoinNode("t07 need 3"));

		Besoin need31 = needDAO.create(new BesoinNode("t07 need 3.1"));
		need3.addChild(need31);
		need3 = needDAO.update(need3);
		need31 = needDAO.update(need31);

		Besoin need32 = needDAO.create(new BesoinNode("t07 need 3.2"));
		need3.addChild(need32);
		need3 = needDAO.update(need3);
		need32 = needDAO.update(need32);

		logger.info("t07_TestBesoinDAORetrieveRootNeeds calling besoinDAO.retrieveNeedsAtRoot()");
		Set<Besoin> rootNeeds = needDAO.retrieveNeedsAtRoot();
		assertEquals(new Long(3), new Long(rootNeeds.size()));
	}

	/**
	 * Check that we can retrieve some children
	 */
	@Test
	@Transactional
	public final void t08_TestBesoinDAORetrieveChildren() {
		Besoin need1 = needDAO.create(new BesoinNode("t08 need 1"));
		Besoin need11 = needDAO.create(new BesoinNode("t08 need 1.1"));
		need1.addChild(need11);
		needDAO.update(need1);
		needDAO.update(need11);
		// We check that the node with name "t08 need 1" has one child
		assertEquals(new Long(1), new Long(need1.getChildren().size()));

		Besoin need111 = needDAO.create(new BesoinNode("t08 need 1.1.1"));
		need11.addChild(need111);
		needDAO.update(need11);
		needDAO.update(need111);
		// We check that the node with name "t08 need 1.1" has one child
		assertEquals(new Long(1), new Long(need11.getChildren().size()));

		Besoin need12 = needDAO.create(new BesoinNode("t08 need 1.2"));
		need1.addChild(need12);
		needDAO.update(need1);
		needDAO.update(need12);
		// We add children to need12
		Besoin need121 = needDAO.create(new BesoinNode("t08 need 1.2.1"));
		need12.addChild(need121);
		Besoin need122 = needDAO.create(new BesoinNode("t08 need 1.2.2"));
		need12.addChild(need122);
		Besoin need123 = needDAO.create(new BesoinNode("t08 need 1.2.3"));
		need12.addChild(need123);
		needDAO.update(need12);
		// We check that the node with name "t08 need 1.2" has 3 children
		assertEquals(new Long(3), new Long(need12.getChildren().size()));
	}

	/**
	 * Check that we can retrieve some children using getChildren
	 */
	@Test
	@Transactional
	public final void t09_TestBesoinDAORetrieveChildrenAutomatically() {
		Besoin need1 = needDAO.create(new BesoinNode("t09 need 1"));
		Besoin need11 = needDAO.create(new BesoinNode("t09 need 1.1"));
		need1.addChild(need11);
		needDAO.update(need1);

		Besoin need1bis = needDAO.retrieveById(need1.getId());
		for (Besoin need1child : need1bis.getChildren()) {
			assertEquals(need1child.getName(), "t09 need 1.1");
		}
	}

}
