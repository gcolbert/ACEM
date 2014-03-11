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
package eu.ueb.acem.dal.tests.rouge;

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

import eu.ueb.acem.dal.rouge.CommunityDAO;
import eu.ueb.acem.dal.rouge.TeachingDepartmentDAO;
import eu.ueb.acem.dal.rouge.InstitutionDAO;
import eu.ueb.acem.dal.rouge.AdministrativeDepartmentDAO;
import eu.ueb.acem.domain.beans.rouge.Communaute;
import eu.ueb.acem.domain.beans.rouge.neo4j.CommunauteNode;

/**
 * @author Grégoire Colbert @since 2014-03-05
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/dal-test-context.xml")
public class OrganisationDAOTest extends TestCase {

	/**
	 * For Logging.
	 */
	@SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory.getLogger(OrganisationDAOTest.class);

	@Autowired
	private CommunityDAO communityDAO;

	@Autowired
	private InstitutionDAO institutionDAO;

	@Autowired
	private AdministrativeDepartmentDAO administrativeDepartmentDAO;

	@Autowired
	private TeachingDepartmentDAO teachingDepartmentDAO;

	public OrganisationDAOTest() {

	}

	@Before
	public void before() {
		communityDAO.deleteAll();
		assertEquals(new Long(0), communityDAO.count());

		institutionDAO.deleteAll();
		assertEquals(new Long(0), institutionDAO.count());

		administrativeDepartmentDAO.deleteAll();
		assertEquals(new Long(0), administrativeDepartmentDAO.count());

		teachingDepartmentDAO.deleteAll();
		assertEquals(new Long(0), teachingDepartmentDAO.count());
	}

	@After
	public void after() {
		before();
	}

	/**
	 * Create
	 */
	@Test
	public final void t01_TestCreateOrganisation() {
		// We create a new object in the datastore
		Communaute community = new CommunauteNode("University of California, Los Angeles", "UCLA");

		// We create our object
		community = communityDAO.create(community);

		// We check that "create" is idempotent (multiple calls must not
		// duplicate data)
		community = communityDAO.create(community);

		// There must exactly 1 object in the datastore
		assertEquals("There are more than one object in the datastore", new Long(1), communityDAO.count());
	}

}
