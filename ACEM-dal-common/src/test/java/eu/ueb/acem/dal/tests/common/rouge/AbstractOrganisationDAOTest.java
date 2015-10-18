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
package eu.ueb.acem.dal.tests.common.rouge;

import java.util.Collection;

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

import eu.ueb.acem.dal.common.gris.PersonDAO;
import eu.ueb.acem.dal.common.jaune.ResourceDAO;
import eu.ueb.acem.dal.common.rouge.OrganisationDAO;
import eu.ueb.acem.domain.beans.gris.Teacher;
import eu.ueb.acem.domain.beans.jaune.Equipment;
import eu.ueb.acem.domain.beans.rouge.AdministrativeDepartment;
import eu.ueb.acem.domain.beans.rouge.Community;
import eu.ueb.acem.domain.beans.rouge.Institution;
import eu.ueb.acem.domain.beans.rouge.TeachingDepartment;

/**
 * @author Grégoire Colbert
 * @since 2014-03-05
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/dal-common-test-context.xml")
public abstract class AbstractOrganisationDAOTest extends TestCase {

	/**
	 * For Logging.
	 */
	@SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory.getLogger(AbstractOrganisationDAOTest.class);

	@Inject
	private OrganisationDAO<Long, Community> communityDAO;

	@Inject
	private OrganisationDAO<Long, Institution> institutionDAO;

	@Inject
	private OrganisationDAO<Long, AdministrativeDepartment> administrativeDepartmentDAO;

	@Inject
	private OrganisationDAO<Long, TeachingDepartment> teachingDepartmentDAO;

	@Inject
	private PersonDAO<Long, Teacher> teacherDAO;

	@Inject
	private ResourceDAO<Long, Equipment> equipmentDAO;

	public AbstractOrganisationDAOTest() {

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
		// We create our object
		Community community = communityDAO.create(name, shortname, null);

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

		Institution institution = institutionDAO.create("University of California, Los Angeles", "UCLA", null);

		AdministrativeDepartment administrativeDepartment = administrativeDepartmentDAO.create("Department of Health Policy and Management", "HPM", null);

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
		Institution institution = institutionDAO.create("Université de Rennes 1", "UR1", null);

		// We create a new community
		Community community = communityDAO.create("Université européenne de Bretagne", "UEB", null);

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

	/**
	 * Test support services retrieval.
	 * The math teacher works for the math department, in a university associated to a community.
	 * The community owns an equipment.
	 * The equipment is supported by an AdministrativeDepartment.
	 * Do the math teacher can "see" the service support?
	 */
	@Test
	@Transactional
	@Rollback(true)
	public final void t04_TestSupportServiceRetrievalWithImplicitAccessThroughOrganisationsAssociations() {

		Community community = communityDAO.create("Comunity", "COM", null);

		// ***********************************************
		// We create a math professor
		// ***********************************************
		Teacher mathTeacher = teacherDAO.create("Prof. Euler", "euler", "euler");

		Institution mathUniversity = institutionDAO.create("University of Math", "UM", null);

		TeachingDepartment mathDepartment = teachingDepartmentDAO.create("Math", "MD", null);

		Equipment equipment = equipmentDAO.create("A brand new interactive whiteboard", null);

		// We associate the math teacher and the math department
		mathTeacher.getWorksForOrganisations().add(mathDepartment);
		mathTeacher = teacherDAO.update(mathTeacher);

		// We associate the math department with the university
		mathDepartment.getInstitutions().add(mathUniversity);
		mathUniversity.getTeachingDepartments().add(mathDepartment);
		mathDepartment = teachingDepartmentDAO.update(mathDepartment);
		mathUniversity =  institutionDAO.update(mathUniversity);

		// We associate the university with the community
		mathUniversity.getCommunities().add(community);
		community.getInstitutions().add(mathUniversity);
		mathUniversity = institutionDAO.update(mathUniversity);
		community = communityDAO.update(community);

		// We associate the community and the resource
		community.getPossessedResources().add(equipment);
		equipment.setOrganisationPossessingResource(community);
		community = communityDAO.update(community);
		equipment = equipmentDAO.update(equipment);

		// ***********************************************
		// We create a support service for the equipment
		// ***********************************************
		AdministrativeDepartment supportService = administrativeDepartmentDAO.create("Support service", "S", null);

		// We associate the equipment and the support service
		supportService.getSupportedResources().add(equipment);
		equipment.setOrganisationSupportingResource(supportService);
		supportService = administrativeDepartmentDAO.update(supportService);
		equipment = equipmentDAO.update(equipment);

		Collection<AdministrativeDepartment> supportServicesOfMathTeacher = administrativeDepartmentDAO.retrieveSupportServicesForPerson(mathTeacher);
		assertEquals(
				"The math teacher working for the math department should see exactly one support service from the administrativeDepartmentDAO.",
				1,
				supportServicesOfMathTeacher.size());
		assertTrue(
				"The math teacher working for the math department should see the support service for the equipment.",
				supportServicesOfMathTeacher.contains(supportService));
	}

}
