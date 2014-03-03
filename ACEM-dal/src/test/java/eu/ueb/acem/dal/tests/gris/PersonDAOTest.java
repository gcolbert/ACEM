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
package eu.ueb.acem.dal.tests.gris;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import eu.ueb.acem.dal.gris.EnseignantDAO;
import eu.ueb.acem.dal.gris.GestionnaireDAO;
import eu.ueb.acem.domain.beans.gris.Enseignant;
import eu.ueb.acem.domain.beans.gris.Gestionnaire;
import eu.ueb.acem.domain.beans.gris.neo4j.EnseignantNode;
import eu.ueb.acem.domain.beans.gris.neo4j.GestionnaireNode;

/**
 * @author Grégoire Colbert @since 2013-11-22
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/dal-test-context.xml")
public class PersonDAOTest {

	/**
	 * For Logging.
	 */
	@SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory.getLogger(PersonDAOTest.class);

	@Autowired
	private GestionnaireDAO administratorDAO;
	
	@Autowired
	private EnseignantDAO teacherDAO;

	public PersonDAOTest() {
		
	}

	@Before
	public void before() {
		administratorDAO.deleteAll();
		assertEquals(new Long(0), administratorDAO.count());

		teacherDAO.deleteAll();
		assertEquals(new Long(0), teacherDAO.count());
	}

	@After
	public void after() {
		before();
	}

	/**
	 * Create
	 */
	@Test
	public final void t01_TestCreateTeacher() {
		// We create a new object in the datastore
		Enseignant teacher1 = new EnseignantNode("Pr. John Doe");

		// We create our object
		teacher1 = teacherDAO.create(teacher1);

		// We check that "create" is idempotent (multiple calls must not
		// duplicate data)
		teacher1 = teacherDAO.create(teacher1);

		// There must exactly 1 object in the datastore
		assertEquals("There are more than one object in the datastore", new Long(1), teacherDAO.count());
	}

	/**
	 * Create
	 */
	@Test
	public final void t02_TestCreateAdministrator() {
		// We create a new object in the datastore
		Gestionnaire administrator1 = new GestionnaireNode("Grégoire Colbert");

		// We create our object
		administrator1 = administratorDAO.create(administrator1);

		// We check that "create" is idempotent (multiple calls must not
		// duplicate data)
		administrator1 = administratorDAO.create(administrator1);

		// There must exactly 1 object in the datastore
		assertEquals("There are more than one object in the datastore", new Long(1), administratorDAO.count());
	}

	/**
	 * RetrieveByLogin
	 */
	@Test
	public final void t03_TestRetrieveByLogin() {
		// We create a new object in the datastore
		Gestionnaire administrator1 = new GestionnaireNode("Grégoire Colbert");
		administrator1.setLogin("gcolbert");

		// We save our object
		administrator1 = administratorDAO.create(administrator1);

		Gestionnaire administrator1bis = administratorDAO.retrieveByLogin(administrator1.getLogin());

		assertNotNull(administrator1bis);
		assertEquals(administrator1bis, administrator1);
		assertEquals(administrator1bis.getName(), administrator1.getName());
	}
	
}
