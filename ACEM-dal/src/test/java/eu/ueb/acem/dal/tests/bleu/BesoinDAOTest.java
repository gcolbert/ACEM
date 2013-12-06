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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import eu.ueb.acem.dal.DAO;
import eu.ueb.acem.domain.beans.bleu.Besoin;
import eu.ueb.acem.domain.beans.bleu.neo4j.BesoinNode;

/**
 * @author gcolbert @since 2013-11-22
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/dal-test-context.xml")
public class BesoinDAOTest extends TestCase {

	@Autowired
	private DAO<Besoin> besoinDAO;

	public BesoinDAOTest() {
	}

	@Before
	public void before() {
		besoinDAO.deleteAll();
	}

	@After
	public void after() {
		besoinDAO.deleteAll();
	}

	/**
	 * Create
	 */
	@Test
	public final void t01_TestBesoinDAOCreate() {
		assertEquals(new Long(0), besoinDAO.count());
		
		// We create a new object in the datastore
		Besoin besoin1 = new BesoinNode("besoin de test t01_TestBesoinDAOCreate");
		besoinDAO.create(besoin1);

		// There must be 1 object in the datastore
		assertEquals(new Long(1), besoinDAO.count());
	}

	/**
	 * Retrieve
	 */
	@Test
	public final void t02_TestBesoinDAORetrieve() {
		// Setting up
		assertEquals(new Long(0), besoinDAO.count());
		Besoin besoin1 = new BesoinNode("besoin de test t02_TestBesoinDAORetrieve");
		besoinDAO.create(besoin1);
		assertEquals(new Long(1), besoinDAO.count());

		// We retrieve the object from the datastore with its name
		Besoin besoin2 = besoinDAO.retrieve(besoin1.getNom());
		
		assertEquals(besoin1.getNom(), besoin2.getNom());
	}

	/**
	 * Update
	 */
	@Test
	public final void t03_TestBesoinDAOUpdate() {
		// Setting up
		assertEquals(new Long(0), besoinDAO.count());
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
		assertEquals(new Long(0), besoinDAO.count());
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
		assertEquals(new Long(0), besoinDAO.count());
		
		Besoin besoin1 = new BesoinNode("besoin de test t05_TestBesoinDAORetrieveAll 1");
		besoinDAO.create(besoin1);
		
		Besoin besoin11 = new BesoinNode("besoin de test t05_TestBesoinDAORetrieveAll 1.1");
		besoinDAO.create(besoin11);
		
		besoin1.addBesoin(besoin11);
		besoinDAO.update(besoin1);

		Besoin besoin12 = new BesoinNode("besoin de test t05_TestBesoinDAORetrieveAll 1.2");
		besoinDAO.create(besoin12);
		
		besoin1.addBesoin(besoin12);
		besoinDAO.update(besoin1);
		
		Besoin besoin2 = new BesoinNode("besoin de test t05_TestBesoinDAORetrieveAll 2");
		besoinDAO.create(besoin2);
		
		// We retrieve all objects
		Set<Besoin> allBesoins = besoinDAO.retrieveAll();
		
		// We check that there are 4 objects in the Set
		assertEquals(new Long(4), new Long(allBesoins.size()));
	}

	/**
	 * Check AddBesoin
	 */
	@Test
	public final void t06_TestBesoinDAOCheckParentChildRelationship() {
		assertEquals(new Long(0), besoinDAO.count());

		Besoin besoin1 = new BesoinNode("besoin de test t06_TestBesoinDAOCheckParentChildRelationship 1");
		besoinDAO.create(besoin1);

		Besoin besoin11 = new BesoinNode("besoin de test t06_TestBesoinDAOCheckParentChildRelationship 1.1");
		besoinDAO.create(besoin11);

		assertEquals(new Long(2), besoinDAO.count());

		// We add besoin11 as a child of besoin1
		besoin1.addBesoin(besoin11);
		besoinDAO.update(besoin1);
		besoinDAO.update(besoin11);

		Besoin besoin1bis = besoinDAO.retrieve(besoin1.getNom());
		Besoin besoin11bis = besoinDAO.retrieve(besoin11.getNom());
		
		// We check that entities know about each other
		assertFalse(besoin1bis.getBesoins().isEmpty());
		assertNotNull(besoin11bis.getParent());
	}
	
}
