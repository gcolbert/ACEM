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
 * @author gcolbert @since 2013-11-22
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/dal-test-context.xml")
public class BesoinDAOTest extends TestCase {

	/**
	 * For Logging.
	 */
	private final static Logger logger = LoggerFactory.getLogger(BesoinDAOTest.class);
	
	@Autowired
	private BesoinDAO besoinDAO;

	public BesoinDAOTest() {
	}

	@Before
	public void before() {
		besoinDAO.deleteAll();
		assertEquals(new Long(0), besoinDAO.count());
	}

	@After
	public void after() {
		besoinDAO.deleteAll();
		assertEquals(new Long(0), besoinDAO.count());
	}

	/**
	 * Create
	 */
	@Test
	public final void t01_TestBesoinDAOCreate() {
		// We create a new object in the datastore
		Besoin besoin1 = new BesoinNode("t01 besoin");
		
		// We create our object
		besoin1 = besoinDAO.create(besoin1);
		
		// We check that "create" is idempotent (multiple calls must not duplicate data)
		besoin1 = besoinDAO.create(besoin1);
		
		// There must exactly 1 object in the datastore
		assertEquals("There are more than one object in the datastore", new Long(1), besoinDAO.count());
	}

	/**
	 * Retrieve
	 */
	@Test
	public final void t02_TestBesoinDAORetrieve() {
		// We create a new object in the datastore
		Besoin besoin1 = besoinDAO.create(new BesoinNode("t02 besoin"));
		assertEquals(new Long(1), besoinDAO.count());

		// We retrieve the object from the datastore using its id
		Besoin besoin1bis = besoinDAO.retrieveById(besoin1.getId());
		assertEquals(besoin1, besoin1bis);
		assertEquals(besoin1.getId(), besoin1bis.getId());
		assertEquals(besoin1.getName(), besoin1bis.getName());
		
		// We retrieve the object from the datastore using its name
		Besoin besoin1ter = besoinDAO.retrieveByName(besoin1.getName());
		assertEquals(besoin1, besoin1ter);
		assertEquals(besoin1.getId(), besoin1ter.getId());
		assertEquals(besoin1.getName(), besoin1ter.getName());
	}

	/**
	 * Update
	 */
	@Test
	public final void t03_TestBesoinDAOUpdate() {
		// Setting up
		Besoin besoin1 = new BesoinNode("t03 besoin");
		besoin1 = besoinDAO.create(besoin1);
		assertEquals(new Long(1), besoinDAO.count());

		// We update the object
		besoin1.setName("t03 besoin mis à jour");
		besoinDAO.update(besoin1);

		// We check that the object cannot be retrieved anymore with its original name
		assertNull(besoinDAO.retrieveByName("t03 besoin"));

		// We check that the object can be retrieved with its new name
		assertNotNull(besoinDAO.retrieveByName("t03 besoin mis à jour"));
		
		// We check that there is still only 1 object in the datastore
		assertEquals(new Long(1), besoinDAO.count());
	}

	/**
	 * Delete
	 */
	@Test
	public final void t04_TestBesoinDAODelete() {
		Besoin besoin1 = new BesoinNode("t04 besoin");
		besoin1 = besoinDAO.create(besoin1);
		assertEquals(new Long(1), besoinDAO.count());

		// We delete the object
		besoinDAO.delete(besoin1);
		
		// We check that there is 0 object in the datastore
		assertEquals(new Long(0), besoinDAO.count());
	}

	/**
	 * Retrieve all
	 */
	@Test
	@Transactional
	public final void t05_TestBesoinDAORetrieveAll() {
		Besoin besoin1 = besoinDAO.create(new BesoinNode("t05 besoin 1"));
		Besoin besoin11 = besoinDAO.create(new BesoinNode("t05 besoin 1.1"));
		besoin1.addChild(besoin11);
		besoin1 = besoinDAO.update(besoin1);
		besoin11 = besoinDAO.update(besoin11);
		
		Besoin besoin12 = besoinDAO.create(new BesoinNode("t05 besoin 1.2"));
		besoin1.addChild(besoin12);
		besoin1 = besoinDAO.update(besoin1);
		besoin12 = besoinDAO.update(besoin12);
		
		Besoin besoin2 = besoinDAO.create(new BesoinNode("t05 besoin 2"));
		
		// We retrieve all objects
		Set<Besoin> allBesoins = besoinDAO.retrieveAll();
		
		// We check that there are 4 objects in the Set
		assertEquals(new Long(4), new Long(allBesoins.size()));
	}

	/**
	 * Check that the relationships are saved
	 */
	@Test
	@Transactional
	public final void t06_TestBesoinDAOCheckParentChildRelationship() {
		// We check that addChild is sufficient to create the parent/child relationship
		Besoin besoin1 = besoinDAO.create(new BesoinNode("t06 besoin 1"));
		Besoin besoin11 = besoinDAO.create(new BesoinNode("t06 besoin 1.1"));
		besoin1.addChild(besoin11);
		besoin1 = besoinDAO.update(besoin1);
		besoin11 = besoinDAO.update(besoin11);

		// We retrieve the entities and we check that entities have parents and children
		Besoin besoin1bis = besoinDAO.retrieveById(besoin1.getId());
		Besoin besoin11bis = besoinDAO.retrieveById(besoin11.getId());
		assertEquals(new Long(0), new Long(besoin1bis.getParents().size()));
		assertEquals(new Long(1), new Long(besoin1bis.getChildren().size()));
		assertEquals(new Long(0), new Long(besoin11bis.getChildren().size()));
		assertEquals(new Long(1), new Long(besoin11bis.getParents().size()));

		// We check that addParent is sufficient to create the parent/child relationship
		Besoin besoin12 = besoinDAO.create(new BesoinNode("t06 besoin 1.2"));
		besoin12.addParent(besoin1);
		besoin1 = besoinDAO.update(besoin1);
		besoin12 = besoinDAO.update(besoin12);

		// We retrieve the entities and we check that entities have parents and children
		Besoin besoin1ter = besoinDAO.retrieveById(besoin1.getId());
		Set<Besoin> children = besoinDAO.retrieveChildrenOf(besoin1ter);
		besoin1ter.setChildren(children);
		Besoin besoin12bis = besoinDAO.retrieveById(besoin12.getId());
		assertEquals(new Long(0), new Long(besoin1ter.getParents().size()));
		assertEquals(new Long(2), new Long(besoin1ter.getChildren().size()));
		assertEquals(new Long(0), new Long(besoin11bis.getChildren().size()));
		assertEquals(new Long(1), new Long(besoin11bis.getParents().size()));
		assertEquals(new Long(0), new Long(besoin12bis.getChildren().size()));
		assertEquals(new Long(1), new Long(besoin12bis.getParents().size()));
	}

	/**
	 * Check that we can retrieve the BesoinNodes that don't have any parent (roots of the tree)
	 */
	@Test
	@Transactional
	public final void t07_TestBesoinDAORetrieveBesoinsRacines() {
		Besoin besoin1 = besoinDAO.create(new BesoinNode("t07 besoin 1"));

		Besoin besoin11 = besoinDAO.create(new BesoinNode("t07 besoin 1.1"));
		besoin1.addChild(besoin11);
		besoin1 = besoinDAO.update(besoin1);
		besoin11 = besoinDAO.update(besoin11);

		Besoin besoin2 = besoinDAO.create(new BesoinNode("t07 besoin 2"));
		
		Besoin besoin3 = besoinDAO.create(new BesoinNode("t07 besoin 3"));

		Besoin besoin31 = besoinDAO.create(new BesoinNode("t07 besoin 3.1"));
		besoin3.addChild(besoin31);
		besoin3 = besoinDAO.update(besoin3);
		besoin31 = besoinDAO.update(besoin31);
		
		Besoin besoin32 = besoinDAO.create(new BesoinNode("t07 besoin 3.2"));
		besoin3.addChild(besoin32);
		besoin3 = besoinDAO.update(besoin3);
		besoin32 = besoinDAO.update(besoin32);

		logger.info("t07_TestBesoinDAORetrieveBesoinsRacines calling besoinDAO.retrieveChildrenOf(null)");
		Set<Besoin> rootNodes = besoinDAO.retrieveChildrenOf(null);
		assertEquals(new Long(3), new Long(rootNodes.size()));
	}
	
	/**
	 * Check that we can retrieve some children
	 */
	@Test
	@Transactional
	public final void t08_TestBesoinDAORetrieveChildren() {
		Besoin besoin1 = besoinDAO.create(new BesoinNode("t08 besoin 1"));
		Besoin besoin11 = besoinDAO.create(new BesoinNode("t08 besoin 1.1"));
		besoin1.addChild(besoin11);
		besoinDAO.update(besoin1);
		besoinDAO.update(besoin11);
		// We check that the node with name "t08 besoin 1" has one child
		Set<Besoin> childrenOfBesoin1 = besoinDAO.retrieveChildrenOf(besoin1);
		assertEquals(new Long(1), new Long(childrenOfBesoin1.size()));
		
		Besoin besoin111 = besoinDAO.create(new BesoinNode("t08 besoin 1.1.1"));
		besoin11.addChild(besoin111);
		besoinDAO.update(besoin11);
		besoinDAO.update(besoin111);
		// We check that the node with name "t08 besoin 1.1" has one child
		Set<Besoin> childrenOfBesoin11 = besoinDAO.retrieveChildrenOf(besoin11);
		assertEquals(new Long(1), new Long(childrenOfBesoin11.size()));

		Besoin besoin12 = besoinDAO.create(new BesoinNode("t08 besoin 1.2"));
		besoin1.addChild(besoin12);
		besoinDAO.update(besoin1);
		besoinDAO.update(besoin12);
		// We add children to besoin12
		Besoin besoin121 = besoinDAO.create(new BesoinNode("t08 besoin 1.2.1"));
		besoin12.addChild(besoin121);
		Besoin besoin122 = besoinDAO.create(new BesoinNode("t08 besoin 1.2.2"));
		besoin12.addChild(besoin122);
		Besoin besoin123 = besoinDAO.create(new BesoinNode("t08 besoin 1.2.3"));
		besoin12.addChild(besoin123);
		besoinDAO.update(besoin12);
		// We check that the node with name "t08 besoin 1.2" has 3 children
		Set<Besoin> childrenOfBesoin12 = besoinDAO.retrieveChildrenOf(besoin12);
		assertEquals(new Long(3), new Long(childrenOfBesoin12.size()));
	}

}
