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
import eu.ueb.acem.domain.beans.rouge.AdministrativeDepartment;
import eu.ueb.acem.domain.beans.rouge.Community;
import eu.ueb.acem.domain.beans.rouge.Institution;
import eu.ueb.acem.domain.beans.rouge.TeachingDepartment;
import eu.ueb.acem.domain.beans.rouge.neo4j.AdministrativeDepartmentNode;
import eu.ueb.acem.domain.beans.rouge.neo4j.CommunityNode;
import eu.ueb.acem.domain.beans.rouge.neo4j.InstitutionNode;

/**
 * @author Grégoire Colbert
 * @since 2014-03-05
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

	@Inject
	private DAO<Long, Community> communityDAO;

	@Inject
	private DAO<Long, Institution> institutionDAO;

	@Inject
	private DAO<Long, AdministrativeDepartment> administrativeDepartmentDAO;

	@Inject
	@SuppressWarnings("unused")
	private DAO<Long, TeachingDepartment> teachingDepartmentDAO;

	public OrganisationDAOTest() {

	}

	/**
	 * Create
	 */
	@Test
	@Transactional
	@Rollback(true)
	public final void t01_TestCreateOrganisation() {
		// We create a new object in the datastore
		String name = "University of California, Los Angeles";
		String shortname = "UCLA";
		Community community = new CommunityNode(name, shortname, null);

		// We create our object
		community = communityDAO.create(community);

		// We check that "create" is idempotent (multiple calls must not
		// duplicate data)
		community = communityDAO.create(community);

		// There must exactly 1 object in the datastore
		assertEquals("There should be exactly one object in the datastore", new Long(1), communityDAO.count());

		Community communityReloaded = communityDAO.retrieveById(community.getId());
		assertNotNull("The reloaded community is null", communityReloaded);

		assertEquals("RetrieveByName didn't return the correct number of Community", new Long(1),  new Long(communityDAO
				.retrieveByName(community.getName()).size()));

		// The name and shortname must be good
		assertEquals("The reloaded name is not good", name, communityReloaded.getName());
		assertEquals("The reloaded shortname is not good", shortname, communityReloaded.getShortname());
	}

	/**
	 * AssociateInstitutionWithAdministrativeDepartment
	 */
	@Test
	@Transactional
	@Rollback(true)
	public final void t02_TestAssociateInstitutionWithAdministrativeDepartment() {
		assertEquals(new Long(0), institutionDAO.count());

		Institution institution = new InstitutionNode("University of California, Los Angeles", "UCLA", null);
		institution = institutionDAO.create(institution);

		AdministrativeDepartment administrativeDepartment = new AdministrativeDepartmentNode(
				"Department of Health Policy and Management", "HPM", null);
		administrativeDepartment = administrativeDepartmentDAO.create(administrativeDepartment);

		administrativeDepartment.getInstitutions().add(institution);
		institution.getAdministrativeDepartments().add(administrativeDepartment);
		administrativeDepartmentDAO.update(administrativeDepartment);

		// The administrative department must be associated with the institution
		Institution institution2 = institutionDAO.retrieveById(institution.getId(), true);
		assertTrue("The administrative department is not associated with the institution", institution2
				.getAdministrativeDepartments().contains(administrativeDepartment));
	}

	/**
	 * Exists
	 */
	@Test
	@Transactional
	@Rollback(true)
	public final void t03_TestExists() {
		// We create a new institution
		Institution institution = new InstitutionNode("Université de Rennes 1", "UR1", null);
		institution = institutionDAO.create(institution);

		// We create a new community
		Community community = new CommunityNode("Université européenne de Bretagne", "UEB", null);
		community = communityDAO.create(community);

		// There must be exactly 1 object in the community repository
		assertEquals("There are more than one object in the community repository", new Long(1), communityDAO.count());

		// There must be exactly 1 object in the institution repository
		assertEquals("There are more than one object in the institution repository", new Long(1),
				institutionDAO.count());

		assertFalse("The institution was found through the Community repository",
				communityDAO.exists(institution.getId()));

		assertTrue("The community was not found through the Community repository",
				communityDAO.exists(community.getId()));
	}
}
