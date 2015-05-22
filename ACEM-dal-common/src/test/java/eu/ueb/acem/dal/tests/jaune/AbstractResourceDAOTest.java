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
package eu.ueb.acem.dal.tests.jaune;

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

import eu.ueb.acem.dal.gris.PersonDAO;
import eu.ueb.acem.dal.jaune.ResourceCategoryDAO;
import eu.ueb.acem.dal.jaune.ResourceDAO;
import eu.ueb.acem.dal.jaune.UseModeDAO;
import eu.ueb.acem.dal.rouge.OrganisationDAO;
import eu.ueb.acem.domain.beans.gris.Teacher;
import eu.ueb.acem.domain.beans.jaune.Documentation;
import eu.ueb.acem.domain.beans.jaune.Equipment;
import eu.ueb.acem.domain.beans.jaune.PedagogicalAndDocumentaryResource;
import eu.ueb.acem.domain.beans.jaune.ProfessionalTraining;
import eu.ueb.acem.domain.beans.jaune.ResourceCategory;
import eu.ueb.acem.domain.beans.jaune.Software;
import eu.ueb.acem.domain.beans.rouge.AdministrativeDepartment;
import eu.ueb.acem.domain.beans.rouge.Community;
import eu.ueb.acem.domain.beans.rouge.Institution;
import eu.ueb.acem.domain.beans.rouge.TeachingDepartment;

/**
 * @author Grégoire Colbert
 * @since 2014-03-11
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/dal-common-test-context.xml")
public abstract class AbstractResourceDAOTest extends TestCase {

	@SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory.getLogger(AbstractResourceDAOTest.class);

	public AbstractResourceDAOTest() {
	}

	protected abstract UseModeDAO<Long> getUseModeDAO();

	protected abstract ResourceCategoryDAO<Long> getResourceCategoryDAO();

	protected abstract PersonDAO<Long, Teacher> getTeacherDAO();

	protected abstract OrganisationDAO<Long, Community> getCommunityDAO();

	protected abstract OrganisationDAO<Long, Institution> getInstitutionDAO();

	protected abstract OrganisationDAO<Long, TeachingDepartment> getTeachingDepartmentDAO();

	protected abstract OrganisationDAO<Long, AdministrativeDepartment> getAdministrativeDepartmentDAO();

	protected abstract ResourceDAO<Long, Software> getSoftwareDAO();

	protected abstract ResourceDAO<Long, Documentation> getDocumentationDAO();	

	protected abstract ResourceDAO<Long, Equipment> getEquipmentDAO();	

	protected abstract ResourceDAO<Long, PedagogicalAndDocumentaryResource> getPedagogicalAndDocumentaryResourceDAO();	

	protected abstract ResourceDAO<Long, ProfessionalTraining> getProfessionalTrainingDAO();

	/**
	 * Test Resource creation and ResourceCategory association
	 */
	@Test
	@Transactional
	@Rollback(true)
	public final void t01_TestDAOResourceAndResourceCategoryAssociation() {
		ResourceCategory learningManagementSystem = getResourceCategoryDAO().create("Learning Management System", "A superb tool", null);
		
		Software moodle = getSoftwareDAO().create("Moodle", null);

		moodle.getCategories().add(learningManagementSystem);
		learningManagementSystem.getResources().add(moodle);
		moodle = getSoftwareDAO().update(moodle);
		learningManagementSystem = getResourceCategoryDAO().update(learningManagementSystem);
		
		Software moodleBis = getSoftwareDAO().retrieveById(moodle.getId(), true);
		ResourceCategory learningManagementSystemBis = getResourceCategoryDAO().retrieveById(learningManagementSystem.getId(), true);

		assertTrue(learningManagementSystemBis.getResources().contains(moodleBis));
		assertTrue(moodleBis.getCategories().contains(learningManagementSystemBis));
		
		Collection<Software> softwares = getSoftwareDAO().retrieveAllWithCategory(learningManagementSystemBis);
		assertTrue(softwares.contains(moodleBis));
	}

	/**
	 * Test Resource creation and ResourceCategory association
	 */
	@Test
	@Transactional
	@Rollback(true)
	public final void t02_TestAssociateSoftwareAndDocumentation() {
		Software software = getSoftwareDAO().create("Moodle", null);

		Documentation documentation = getDocumentationDAO().create("Tutorial for Moodle", null);

		software.getDocumentations().add(documentation);
		documentation.getResources().add(software);

		software = getSoftwareDAO().update(software);
		documentation = getDocumentationDAO().update(documentation);

		Software softwareBis = getSoftwareDAO().retrieveById(software.getId(), true);
		assertEquals(new Long(1), new Long(softwareBis.getDocumentations().size()));

		Documentation documentationBis = getDocumentationDAO().retrieveById(documentation.getId(), true);
		assertTrue(softwareBis.getDocumentations().contains(documentationBis));

		softwareBis.getDocumentations().remove(documentationBis);
		documentationBis.getResources().remove(softwareBis);
		assertEquals(new Long(0), new Long(softwareBis.getDocumentations().size()));

		softwareBis = getSoftwareDAO().update(softwareBis);
		assertFalse(softwareBis.getDocumentations().contains(documentationBis));
	}
	
	/**
	 * Test resource retrieval by a person working (or not) for the organisation possessing the given resource
	 */
	@Test
	@Transactional
	@Rollback(true)
	public final void t03_TestResourceRetrievalByPersonWorkingForAnOrganisationThatIsPossessingTheResource() {
		Teacher personWorkingForOrganisation = getTeacherDAO().create("Some user", "loginuser", "somepassword");

		TeachingDepartment teachingDepartment = getTeachingDepartmentDAO().create("Math", "M", null);

		ResourceCategory category = getResourceCategoryDAO().create("LMS", "Learning Management System", null);

		Software software = getSoftwareDAO().create("Moodle", null);

		// We associate the person and the organisation
		personWorkingForOrganisation.getWorksForOrganisations().add(teachingDepartment);
		personWorkingForOrganisation = getTeacherDAO().update(personWorkingForOrganisation);

		// We associate the organisation and the resource
		teachingDepartment.getPossessedResources().add(software);
		teachingDepartment = getTeachingDepartmentDAO().update(teachingDepartment);

		// We associate the resource and the category
		category.getResources().add(software);
		category = getResourceCategoryDAO().update(category);

		Collection<Software> usableSoftwaresOfPersonWorking = getSoftwareDAO().retrieveResourcesInCategoryForPerson(category,
				personWorkingForOrganisation);
		assertTrue("The person working for an organisation that possesses a resource must be able to retrieve it.",
				usableSoftwaresOfPersonWorking.contains(software));

		// A person who doesn't work for ANY organisation should not be
		// able to use the resource, hence not be able to retrieve it
		Teacher personNotWorkingForOrganisation = getTeacherDAO().create("Another user", "anotherlogin", "anotherpassword");

		Collection<Software> usableSoftwaresOfPersonNotWorking = getSoftwareDAO().retrieveResourcesInCategoryForPerson(
				category, personNotWorkingForOrganisation);
		assertFalse(
				"The person, who do not work for an organisation that possesses a resource, must not be able to retrieve it.",
				usableSoftwaresOfPersonNotWorking.contains(software));
	}

	/**
	 * Test resource retrieval by a person working (or not) for the organisation supporting the given resource
	 */
	@Test
	@Transactional
	@Rollback(true)
	public final void t04_TestResourceRetrievalByPersonWorkingForAnOrganisationThatIsSupportingTheResource() {
		Teacher personWorkingForOrganisation = getTeacherDAO().create("Some user", "loginuser", "somepassword");

		AdministrativeDepartment administrativeDepartment = getAdministrativeDepartmentDAO().create("The support service", "TSS", null);

		ResourceCategory category = getResourceCategoryDAO().create("LMS", "Learning Management System", null);

		Documentation documentation = getDocumentationDAO().create("Moodle tutorial", null);

		// We associate the person and the organisation
		personWorkingForOrganisation.getWorksForOrganisations().add(administrativeDepartment);
		personWorkingForOrganisation = getTeacherDAO().update(personWorkingForOrganisation);

		// We associate the organisation and the resource
		administrativeDepartment.getSupportedResources().add(documentation);
		administrativeDepartment = getAdministrativeDepartmentDAO().update(administrativeDepartment);

		// We associate the resource and the category
		category.getResources().add(documentation);
		category = getResourceCategoryDAO().update(category);

		Collection<Documentation> usableDocumentationsOfPersonWorking = getDocumentationDAO().retrieveResourcesInCategoryForPerson(category,
				personWorkingForOrganisation);
		assertTrue("The person working for an organisation that supports a resource must be able to retrieve it.",
				usableDocumentationsOfPersonWorking.contains(documentation));

		// A person who don't work for ANY organisation should not be
		// able to use the resource, hence not be able to retrieve it
		Teacher personNotWorkingForOrganisation = getTeacherDAO().create("Another user", "anotherlogin", "anotherpassword");

		Collection<Documentation> usableDocumentationsPersonNotWorking = getDocumentationDAO().retrieveResourcesInCategoryForPerson(
				category, personNotWorkingForOrganisation);
		assertFalse(
				"The person, who do not work for an organisation that supports a resource, must not be able to retrieve it.",
				usableDocumentationsPersonNotWorking.contains(documentation));
	}

	/**
	 * Test resource retrieval by a person working (or not) for the organisation having access to the given resource
	 */
	@Test
	@Transactional
	@Rollback(true)
	public final void t05_TestResourceRetrievalByPersonWorkingForAnOrganisationThatIsHavingAccessToTheResource() {
		Teacher personWorkingForOrganisation = getTeacherDAO().create("Some user", "loginuser", "somepassword");

		Institution institution = getInstitutionDAO().create("University", "U", null);

		ResourceCategory category = getResourceCategoryDAO().create("Interactive whiteboards", "Interactive whiteboards", null);

		Equipment equipment = getEquipmentDAO().create("A brand new interactive whiteboard", null);

		// We associate the person and the organisation
		personWorkingForOrganisation.getWorksForOrganisations().add(institution);
		personWorkingForOrganisation = getTeacherDAO().update(personWorkingForOrganisation);

		// We associate the organisation and the resource
		institution.getSupportedResources().add(equipment);
		institution = getInstitutionDAO().update(institution);

		// We associate the resource and the category
		category.getResources().add(equipment);
		category = getResourceCategoryDAO().update(category);

		Collection<Equipment> usableEquipmentsOfPersonWorking = getEquipmentDAO().retrieveResourcesInCategoryForPerson(category,
				personWorkingForOrganisation);
		assertTrue("The person working for an organisation that has access to a resource must be able to retrieve it.",
				usableEquipmentsOfPersonWorking.contains(equipment));

		// A person who don't work for ANY organisation should not be
		// able to use the resource, hence not be able to retrieve it
		Teacher personNotWorkingForOrganisation = getTeacherDAO().create("Another user", "anotherlogin", "anotherpassword");

		Collection<Equipment> usableEquipmentsOfPersonNotWorking = getEquipmentDAO().retrieveResourcesInCategoryForPerson(
				category, personNotWorkingForOrganisation);
		assertFalse(
				"The person, who do not work for an organisation that has access to a resource, must not be able to retrieve it.",
				usableEquipmentsOfPersonNotWorking.contains(equipment));
	}

	/**
	 * Test resource retrieval by a person working for an organisation which is
	 * associated with the organisation possessing/supporting/having access to a
	 * resource.
	 */
	@Test
	@Transactional
	@Rollback(true)
	public final void t06_TestResourceRetrievalWithImplicitAccessThroughOrganisationsAssociations() {

		Community community = getCommunityDAO().create("Comunity", "COM", null);

		// ***********************************************
		// We create a math professor
		// ***********************************************
		Teacher mathTeacher = getTeacherDAO().create("Prof. Euler", "euler", "euler");

		Institution mathUniversity = getInstitutionDAO().create("University of Math", "UM", null);

		TeachingDepartment mathDepartment = getTeachingDepartmentDAO().create("Math", "MD", null);

		ResourceCategory interactiveWhiteboards = getResourceCategoryDAO().create("Interactive whiteboards", "Interactive whiteboards", null);

		Equipment equipment = getEquipmentDAO().create("A brand new interactive whiteboard", null);

		// We associate the math teacher and the math department
		mathTeacher.getWorksForOrganisations().add(mathDepartment);
		mathTeacher = getTeacherDAO().update(mathTeacher);

		// We associate the math department with the university
		mathDepartment.getInstitutions().add(mathUniversity);
		mathUniversity.getTeachingDepartments().add(mathDepartment);
		mathDepartment = getTeachingDepartmentDAO().update(mathDepartment);

		// We associate the university with the community
		mathUniversity.getCommunities().add(community);
		community.getInstitutions().add(mathUniversity);
		mathUniversity = getInstitutionDAO().update(mathUniversity);

		// We associate the community and the resource
		community.getSupportedResources().add(equipment);
		community = getCommunityDAO().update(community);

		// We associate the resource and the category
		interactiveWhiteboards.getResources().add(equipment);
		interactiveWhiteboards = getResourceCategoryDAO().update(interactiveWhiteboards);

		Collection<Equipment> usableEquipmentsOfMathTeacher = getEquipmentDAO().retrieveResourcesInCategoryForPerson(interactiveWhiteboards,
				mathTeacher);
		assertTrue(
				"The math teacher working for the math department should be able to retrieve the interactive whiteboard possessed by the community, given the organisations are associated.",
				usableEquipmentsOfMathTeacher.contains(equipment));

		// *************************************************
		// We create a biology professor
		// *************************************************
		Teacher biologyTeacher = getTeacherDAO().create("Prof. Darwin", "darwin", "darwin");

		Institution biologyUniversity = getInstitutionDAO().create("University of Biology", "UB", null);

		// We associate the biology teacher and the biology university
		biologyTeacher.getWorksForOrganisations().add(biologyUniversity);
		biologyTeacher = getTeacherDAO().update(biologyTeacher);

		Collection<Equipment> usableResourcesOfBiologyTeacher = getEquipmentDAO().retrieveResourcesInCategoryForPerson(interactiveWhiteboards,
				biologyTeacher);
		assertFalse(
				"The biology teacher working for the biology department should not be able to retrieve the interactive whiteboard possessed by the community, given the math university and the community are not yet associated.",
				usableResourcesOfBiologyTeacher.contains(equipment));

		// We associate the biology university with the community
		biologyUniversity.getCommunities().add(community);
		community.getInstitutions().add(biologyUniversity);
		biologyUniversity = getInstitutionDAO().update(biologyUniversity);

		usableResourcesOfBiologyTeacher = getEquipmentDAO().retrieveResourcesInCategoryForPerson(interactiveWhiteboards, biologyTeacher);
		assertTrue(
				"The biology teacher working for the biology department should be able to retrieve the interactive whiteboard possessed by the community, given the math university and the community are now associated.",
				usableResourcesOfBiologyTeacher.contains(equipment));

		// ************************************************************************
		// The biology university creates an Internet training for every person
		// that works for the biology university (or for its departments)
		// ************************************************************************
		ProfessionalTraining internetTraining = getProfessionalTrainingDAO().create("How to use Google Images to mix fungi species", null);

		ResourceCategory internetTrainings = getResourceCategoryDAO().create("Internet trainings", "Internet trainings", null);
		internetTrainings.getResources().add(internetTraining);
		internetTrainings = getResourceCategoryDAO().update(internetTrainings);

		biologyUniversity.getPossessedResources().add(internetTraining);
		biologyUniversity = getInstitutionDAO().update(biologyUniversity);

		Collection<ProfessionalTraining> usableProfessionalTrainingsOfBiologyTeacher = getProfessionalTrainingDAO().retrieveResourcesInCategoryForPerson(internetTrainings, biologyTeacher);
		assertTrue(
				"The biology teacher working for the biology university should be able to retrieve the Internet training possessed by the biology university.",
				usableProfessionalTrainingsOfBiologyTeacher.contains(internetTraining));

		Collection<ProfessionalTraining> usableProfessionalTrainingsOfMathTeacher = getProfessionalTrainingDAO().retrieveResourcesInCategoryForPerson(internetTrainings, mathTeacher);
		assertFalse(
				"The math teacher working for the math department should not be able to retrieve the Internet training possessed by the biology university (even though they belong to the same community).",
				usableProfessionalTrainingsOfMathTeacher.contains(internetTraining));

		// ****************************************************************************
		// The biology university shares its Internet training with the math university
		// ****************************************************************************
		mathUniversity.getViewedResources().add(internetTraining);
		mathUniversity = getInstitutionDAO().update(mathUniversity);

		usableProfessionalTrainingsOfMathTeacher = getProfessionalTrainingDAO().retrieveResourcesInCategoryForPerson(internetTrainings, mathTeacher);
		assertTrue(
				"The math teacher working for the math department should be able to retrieve the Internet training possessed by the biology university, because the biology university decided to share this resource with the math university.",
				usableProfessionalTrainingsOfMathTeacher.contains(internetTraining));
	}

}
