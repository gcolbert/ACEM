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
		Besoin besoin1 = new BesoinNode("besoin de test t01_TestBesoinDAOCreate");
		
		// We create our object
		besoinDAO.create(besoin1);
		
		// We check that "create" is idempotent (multiple calls must not duplicate data)
		besoinDAO.create(besoin1);

		// There must exactly 1 object in the datastore
		assertEquals("There are more than one object in the datastore", new Long(1), besoinDAO.count());
	}

	/**
	 * Retrieve
	 */
	@Test
	public final void t02_TestBesoinDAORetrieve() {
		// We create a new object in the datastore
		Besoin besoin1 = new BesoinNode("besoin de test t02_TestBesoinDAORetrieve");
		besoinDAO.create(besoin1);
		assertEquals(new Long(1), besoinDAO.count());

		// We retrieve the object from the datastore using its name
		Besoin besoin2 = besoinDAO.retrieve(besoin1.getNom());
		
		assertEquals(besoin1, besoin2);
		assertEquals(besoin1.getNom(), besoin2.getNom());
	}

	/**
	 * Update
	 */
	@Test
	public final void t03_TestBesoinDAOUpdate() {
		// Setting up
		Besoin besoin1 = new BesoinNode("besoin de test t03_TestBesoinDAOUpdate");
		besoinDAO.create(besoin1);
		assertEquals(new Long(1), besoinDAO.count());

		// We update the object
		besoin1.setNom("besoin de test t03_TestBesoinDAOUpdate mis à jour");
		besoinDAO.update(besoin1);

		// We check that the object cannot be retrieved anymore with its original name
		assertNull(besoinDAO.retrieve("besoin de test t03_TestBesoinDAOUpdate"));

		// We check that the object can be retrieved with its new name
		assertNotNull(besoinDAO.retrieve("besoin de test t03_TestBesoinDAOUpdate mis à jour"));
		
		// We check that there is still only 1 object in the datastore
		assertEquals(new Long(1), besoinDAO.count());
	}

	/**
	 * Delete
	 */
	@Test
	public final void t04_TestBesoinDAODelete() {
		Besoin besoin1 = new BesoinNode("besoin de test t04_TestBesoinDAODelete");
		besoinDAO.create(besoin1);
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
	public final void t05_TestBesoinDAORetrieveAll() {
		Besoin besoin1 = new BesoinNode("besoin de test t05_TestBesoinDAORetrieveAll 1");
		besoinDAO.create(besoin1);
		
		Besoin besoin11 = new BesoinNode("besoin de test t05_TestBesoinDAORetrieveAll 1.1");
		besoinDAO.create(besoin11);
		
		besoin1.addEnfant(besoin11);
		besoinDAO.update(besoin1);
		
		Besoin besoin12 = new BesoinNode("besoin de test t05_TestBesoinDAORetrieveAll 1.2");
		besoinDAO.create(besoin12);
		
		besoin1.addEnfant(besoin12);
		besoinDAO.update(besoin1);
		
		Besoin besoin2 = new BesoinNode("besoin de test t05_TestBesoinDAORetrieveAll 2");
		besoinDAO.create(besoin2);
		
		// We retrieve all objects
		Set<Besoin> allBesoins = besoinDAO.retrieveAll();
		
		// We check that there are 4 objects in the Set
		assertEquals(new Long(4), new Long(allBesoins.size()));
	}

	/**
	 * Check that the relationships are saved
	 */
	@Test
	public final void t06_TestBesoinDAOCheckParentChildRelationship() {
		Besoin besoin1 = new BesoinNode("besoin de test t06_TestBesoinDAOCheckParentChildRelationship 1");
		besoinDAO.create(besoin1);

		Besoin besoin11 = new BesoinNode("besoin de test t06_TestBesoinDAOCheckParentChildRelationship 1.1");
		besoinDAO.create(besoin11);

		assertEquals(new Long(2), besoinDAO.count());

		// We add besoin11 as a child of besoin1 and check that a call to addEnfant is sufficient to create the relationships
		besoin1.addEnfant(besoin11);
		besoinDAO.update(besoin1);
		besoinDAO.update(besoin11);

		// We retrieve the entities
		Besoin besoin1bis = besoinDAO.retrieve(besoin1.getNom());
		Besoin besoin11bis = besoinDAO.retrieve(besoin11.getNom());

		assertTrue(besoin11bis.getParents().contains(besoin1bis));
		
		// We check that entities know about each other
		assertEquals(new Long(0), new Long(besoin1bis.getParents().size()));
		assertEquals(new Long(1), new Long(besoin1bis.getEnfants().size()));
		assertEquals(new Long(0), new Long(besoin11bis.getEnfants().size()));
		assertEquals(new Long(1), new Long(besoin11bis.getParents().size()));

		/*
		// Now we check that addParent is sufficient to create the relationships, too
		Besoin besoin12 = new BesoinNode("besoin de test t06_TestBesoinDAOCheckParentChildRelationship 1.2");
		besoinDAO.create(besoin12);

		assertEquals(new Long(3), besoinDAO.count());

		besoin12.addParent(besoin1);
		besoinDAO.update(besoin1);
		besoinDAO.update(besoin12);

		// We retrieve the entities
		Besoin besoin1ter = besoinDAO.retrieve(besoin1.getNom());
		besoinDAO.retrieveChildrenOf(besoin1ter);
		Besoin besoin12bis = besoinDAO.retrieve(besoin12.getNom());

		// We check that entities know about each other
		assertEquals(new Long(0), new Long(besoin1ter.getParents().size()));
		assertEquals(new Long(2), new Long(besoin1ter.getEnfants().size()));
		assertEquals(new Long(0), new Long(besoin11bis.getEnfants().size()));
		assertEquals(new Long(1), new Long(besoin11bis.getParents().size()));
		assertEquals(new Long(0), new Long(besoin12bis.getEnfants().size()));
		assertEquals(new Long(1), new Long(besoin12bis.getParents().size()));
		*/
	}

	/**
	 * Check that we can retrieve the BesoinNodes that don't have any parent (roots of the tree)
	 */
	@Test
	public final void t07_TestBesoinDAORetrieveBesoinsRacines() {
		Besoin besoin1 = new BesoinNode("besoin de test t07_TestBesoinDAORetrieveBesoinsRacines 1");
		besoinDAO.create(besoin1);

		Besoin besoin11 = new BesoinNode("besoin de test t07_TestBesoinDAORetrieveBesoinsRacines 1.1");
		besoinDAO.create(besoin11);
		
		besoin1.addEnfant(besoin11);
		besoinDAO.update(besoin1);

		Besoin besoin2 = new BesoinNode("besoin de test t07_TestBesoinDAORetrieveBesoinsRacines 2");
		besoinDAO.create(besoin2);
		
		Besoin besoin3 = new BesoinNode("besoin de test t07_TestBesoinDAORetrieveBesoinsRacines 3");
		besoinDAO.create(besoin3);

		Besoin besoin31 = new BesoinNode("besoin de test t07_TestBesoinDAORetrieveBesoinsRacines 3.1");
		besoinDAO.create(besoin31);

		besoin3.addEnfant(besoin31);
		
		Besoin besoin32 = new BesoinNode("besoin de test t07_TestBesoinDAORetrieveBesoinsRacines 3.2");
		besoinDAO.create(besoin32);
		
		besoin3.addEnfant(besoin32);

		besoinDAO.update(besoin3);
		
		logger.info("t07_TestBesoinDAORetrieveBesoinsRacines calling besoinDAO.retrieveChildrenOf(null)");
		Set<Besoin> rootNodes = besoinDAO.retrieveChildrenOf(null);
		assertEquals(new Long(3), new Long(rootNodes.size()));
	}
	
	/**
	 * Check that we can retrieve some children
	 */
	@Test
	public final void t08_TestBesoinDAORetrieveChildren() {
		Besoin besoin1 = new BesoinNode("besoin de test t08_TestBesoinDAORetrieveChildren 1");
		besoinDAO.create(besoin1);

		Besoin besoin11 = new BesoinNode("besoin de test t08_TestBesoinDAORetrieveChildren 1.1");
		besoinDAO.create(besoin11);
		besoin1.addEnfant(besoin11);
		besoinDAO.update(besoin1);

		Besoin besoin111 = new BesoinNode("besoin de test t08_TestBesoinDAORetrieveChildren 1.1.1");
		besoinDAO.create(besoin111);
		besoin11.addEnfant(besoin111);
		besoinDAO.update(besoin11);

		// We check that the node with name "besoin de test t07_TestBesoinDAORetrieveChildren 1" has one child
		Set<Besoin> childrenOfBesoin1 = besoinDAO.retrieveChildrenOf(besoin1);
		assertEquals(new Long(1), new Long(childrenOfBesoin1.size()));

		// We check that the node with name "besoin de test t07_TestBesoinDAORetrieveChildren 1.1" has one child
		Set<Besoin> childrenOfBesoin11 = besoinDAO.retrieveChildrenOf(besoin11);
		assertEquals(new Long(1), new Long(childrenOfBesoin11.size()));

		Besoin besoin12 = new BesoinNode("besoin de test t08_TestBesoinDAORetrieveChildren 1.2");
		besoinDAO.create(besoin12);
		
		besoin1.addEnfant(besoin12);
		besoinDAO.update(besoin1);

		// We add children to besoin12
		Besoin besoin121 = new BesoinNode("besoin de test t08_TestBesoinDAORetrieveChildren 1.2.1");
		besoinDAO.create(besoin121);
		
		besoin12.addEnfant(besoin121);

		Besoin besoin122 = new BesoinNode("besoin de test t08_TestBesoinDAORetrieveChildren 1.2.2");
		besoinDAO.create(besoin122);

		besoin12.addEnfant(besoin122);
		
		Besoin besoin123 = new BesoinNode("besoin de test t08_TestBesoinDAORetrieveChildren 1.2.3");
		besoinDAO.create(besoin123);

		besoin12.addEnfant(besoin123);
		
		besoinDAO.update(besoin12);
		
		// We check that the node with name "besoin de test t07_TestBesoinDAORetrieveChildren 1.1" has one child
		Set<Besoin> childrenOfBesoin12 = besoinDAO.retrieveChildrenOf(besoin12);
		assertEquals(new Long(3), new Long(childrenOfBesoin12.size()));
	}

}
