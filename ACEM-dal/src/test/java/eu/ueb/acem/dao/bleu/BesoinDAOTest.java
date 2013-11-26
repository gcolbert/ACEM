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
package eu.ueb.acem.dao.bleu;

import junit.framework.TestCase;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import eu.ueb.acem.dal.DAO;
import eu.ueb.acem.domain.beans.bleu.Besoin;

/**
 * @author gcolbert @since 2013-11-22
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/test-context.xml")
public class BesoinDAOTest extends TestCase {

	@Autowired
	private DAO<Besoin> besoinDAO;

	public BesoinDAOTest() {
	}
	
	/**
	 * Create
	 */
	@Test
	public final void t0TestBesoinDAOCreate() {
		// Setting up
		besoinDAO.deleteAll();
		assertEquals(new Long(0), besoinDAO.count());
		
		// We create a new object in the datastore
		besoinDAO.create("besoin de test t0TestBesoinDAOCreate");

		// There must be 1 object in the datastore
		assertEquals(new Long(1), besoinDAO.count());
	}

	/**
	 * Retrieve
	 */
	@Test
	public final void t1TestBesoinDAORetrieve() {
		// Setting up
		besoinDAO.deleteAll();
		assertEquals(new Long(0), besoinDAO.count());
		Besoin besoin1 = besoinDAO.create("besoin de test t1TestBesoinDAORetrieve");
		assertEquals(new Long(1), besoinDAO.count());

		// We retrieve the object from the datastore with its name
		Besoin besoin2 = besoinDAO.retrieve(besoin1.getNom());
		assertEquals(besoin1.getNom(), besoin2.getNom());
	}

	/**
	 * Update
	 */
	@Test
	public final void t2TestBesoinDAOUpdate() {
		// Setting up
		besoinDAO.deleteAll();
		assertEquals(new Long(0), besoinDAO.count());
		Besoin besoin1 = besoinDAO.create("besoin de test t2TestBesoinDAOUpdate");
		assertEquals(new Long(1), besoinDAO.count());

		// We update the object
		besoin1.setNom("besoin de test t2TestBesoinDAOUpdate mis à jour");
		besoinDAO.update(besoin1);

		// We check that the object cannot be retrieved anymore with its original name
		assertNull(besoinDAO.retrieve("besoin de test t2TestBesoinDAOUpdate"));

		// We check that the object can be retrieved with its new name
		assertNotNull(besoinDAO.retrieve("besoin de test t2TestBesoinDAOUpdate mis à jour"));
		
		// We check that there is still only 1 object in the datastore
		assertEquals(new Long(1), besoinDAO.count());
	}

	/**
	 * Delete
	 */
	@Test
	public final void t3TestBesoinDAODelete() {
		// Setting up
		besoinDAO.deleteAll();
		assertEquals(new Long(0), besoinDAO.count());
		Besoin besoin1 = besoinDAO.create("besoin de test t3TestBesoinDAODelete");
		assertEquals(new Long(1), besoinDAO.count());

		// We delete the object
		besoinDAO.delete(besoin1);
		
		// We check that there is 0 object in the datastore
		assertEquals(new Long(0), besoinDAO.count());
	}
	
}
