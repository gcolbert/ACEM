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

import javax.inject.Inject;

import junit.framework.TestCase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import eu.ueb.acem.dal.gris.PersonDAO;
import eu.ueb.acem.dal.gris.TeacherDAO;
import eu.ueb.acem.domain.beans.gris.Enseignant;
import eu.ueb.acem.domain.beans.gris.Personne;
import eu.ueb.acem.domain.beans.gris.neo4j.EnseignantNode;
import eu.ueb.acem.domain.beans.gris.neo4j.PersonneNode;

/**
 * @author Grégoire Colbert
 * @since 2013-11-22
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/dal-test-context.xml")
public class PersonDAOTest extends TestCase {

	/**
	 * For Logging.
	 */
	@SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory.getLogger(PersonDAOTest.class);

	@Inject
	private TeacherDAO teacherDAO;

	@Inject
	private PersonDAO personDAO;

	public PersonDAOTest() {
		
	}

	@Before
	public void before() {
		personDAO.deleteAll();
		assertEquals(new Long(0), personDAO.count());
		
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
		Enseignant teacher1 = new EnseignantNode("Pr. John Doe", "jdoe");

		// We create our object
		teacher1 = teacherDAO.create(teacher1);

		// We check that "create" is idempotent (multiple calls must not
		// duplicate data)
		teacher1 = teacherDAO.create(teacher1);

		// There must exactly 1 object in the datastore
		assertEquals("There are more than one object in the datastore", new Long(1), teacherDAO.count());
	}

	/**
	 * RetrieveByLogin
	 */
	@Test
	public final void t02_TestRetrieveByLogin() {
		// We create a new object in the datastore
		Personne person1 = new PersonneNode("Grégoire Colbert", "gcolbert");
		person1.setAdministrator(true);

		// We save our object
		person1 = personDAO.update(person1);

		Personne person1bis = personDAO.retrieveByLogin(person1.getLogin());

		assertNotNull(person1bis);
		assertEquals(person1bis, person1);
		assertEquals(person1bis.getName(), person1.getName());
	}
	
}
