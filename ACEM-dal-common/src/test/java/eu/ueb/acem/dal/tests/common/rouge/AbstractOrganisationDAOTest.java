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

	public AbstractOrganisationDAOTest() {

	}

	protected abstract OrganisationDAO<Long, Community> getCommunityDAO();

	protected abstract OrganisationDAO<Long, Institution> getInstitutionDAO();

	protected abstract OrganisationDAO<Long, AdministrativeDepartment> getAdministrativeDepartmentDAO();

	protected abstract OrganisationDAO<Long, TeachingDepartment> getTeachingDepartmentDAO();

	protected abstract PersonDAO<Long, Teacher> getTeacherDAO();
	
	protected abstract ResourceDAO<Long, Equipment> getEquipmentDAO();
	
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
		Community community = getCommunityDAO().create(name, shortname, null);

		// We check that "create" is idempotent (multiple calls must not
		// duplicate data)
		community = getCommunityDAO().create(community);

		// There must exactly 1 object in the datastore
		assertEquals("There should be exactly one object in the datastore", new Long(1), getCommunityDAO().count());

		Community communityReloaded = getCommunityDAO().retrieveById(community.getId());
		assertNotNull("The reloaded community is null", communityReloaded);

		assertEquals("RetrieveByName didn't return the correct number of Community", new Long(1),  new Long(getCommunityDAO()
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
		assertEquals(new Long(0), getInstitutionDAO().count());

		Institution institution = getInstitutionDAO().create("University of California, Los Angeles", "UCLA", null);

		AdministrativeDepartment administrativeDepartment = getAdministrativeDepartmentDAO().create("Department of Health Policy and Management", "HPM", null);

		administrativeDepartment.getInstitutions().add(institution);
		institution.getAdministrativeDepartments().add(administrativeDepartment);
		getAdministrativeDepartmentDAO().update(administrativeDepartment);

		// The administrative department must be associated with the institution
		Institution institution2 = getInstitutionDAO().retrieveById(institution.getId(), true);
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
		Institution institution = getInstitutionDAO().create("Université de Rennes 1", "UR1", null);

		// We create a new community
		Community community = getCommunityDAO().create("Université européenne de Bretagne", "UEB", null);

		// There must be exactly 1 object in the community repository
		assertEquals("There are more than one object in the community repository", new Long(1), getCommunityDAO().count());

		// There must be exactly 1 object in the institution repository
		assertEquals("There are more than one object in the institution repository", new Long(1),
				getInstitutionDAO().count());

		assertFalse("The institution was found through the Community repository",
				getCommunityDAO().exists(institution.getId()));

		assertTrue("The community was not found through the Community repository",
				getCommunityDAO().exists(community.getId()));
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

		Community community = getCommunityDAO().create("Comunity", "COM", null);

		// ***********************************************
		// We create a math professor
		// ***********************************************
		Teacher mathTeacher = getTeacherDAO().create("Prof. Euler", "euler", "euler");

		Institution mathUniversity = getInstitutionDAO().create("University of Math", "UM", null);

		TeachingDepartment mathDepartment = getTeachingDepartmentDAO().create("Math", "MD", null);

		Equipment equipment = getEquipmentDAO().create("A brand new interactive whiteboard", null);

		// We associate the math teacher and the math department
		mathTeacher.getWorksForOrganisations().add(mathDepartment);
		mathTeacher = getTeacherDAO().update(mathTeacher);

		// We associate the math department with the university
		mathDepartment.getInstitutions().add(mathUniversity);
		mathUniversity.getTeachingDepartments().add(mathDepartment);
		mathDepartment = getTeachingDepartmentDAO().update(mathDepartment);
		mathUniversity =  getInstitutionDAO().update(mathUniversity);

		// We associate the university with the community
		mathUniversity.getCommunities().add(community);
		community.getInstitutions().add(mathUniversity);
		mathUniversity = getInstitutionDAO().update(mathUniversity);
		community = getCommunityDAO().update(community);

		// We associate the community and the resource
		community.getPossessedResources().add(equipment);
		equipment.setOrganisationPossessingResource(community);
		community = getCommunityDAO().update(community);
		equipment = getEquipmentDAO().update(equipment);

		// ***********************************************
		// We create a support service for the equipment
		// ***********************************************
		AdministrativeDepartment supportService = getAdministrativeDepartmentDAO().create("Support service", "S", null);

		// We associate the equipment and the support service
		supportService.getSupportedResources().add(equipment);
		equipment.setOrganisationSupportingResource(supportService);
		supportService = getAdministrativeDepartmentDAO().update(supportService);
		equipment = getEquipmentDAO().update(equipment);

		Collection<AdministrativeDepartment> supportServicesOfMathTeacher = getAdministrativeDepartmentDAO().retrieveSupportServicesForPerson(mathTeacher);
		assertEquals(
				"The math teacher working for the math department should see exactly one support service from the getAdministrativeDepartmentDAO().",
				1,
				supportServicesOfMathTeacher.size());
		assertTrue(
				"The math teacher working for the math department should see the support service for the equipment.",
				supportServicesOfMathTeacher.contains(supportService));
	}
	
}
