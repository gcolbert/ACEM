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
package eu.ueb.acem.dal.tests.common.jaune;

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
import eu.ueb.acem.dal.common.jaune.ResourceCategoryDAO;
import eu.ueb.acem.dal.common.jaune.ResourceDAO;
import eu.ueb.acem.dal.common.jaune.UseModeDAO;
import eu.ueb.acem.dal.common.rouge.OrganisationDAO;
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

	@Inject
	private UseModeDAO<Long> useModeDAO;

	@Inject
	private ResourceCategoryDAO<Long> resourceCategoryDAO;

	@Inject
	private PersonDAO<Long, Teacher> teacherDAO;
	
	@Inject
	private OrganisationDAO<Long, Community> communityDAO;

	@Inject
	private OrganisationDAO<Long, Institution> institutionDAO;

	@Inject
	private OrganisationDAO<Long, TeachingDepartment> teachingDepartmentDAO;

	@Inject
	private OrganisationDAO<Long, AdministrativeDepartment> administrativeDepartmentDAO;

	@Inject
	private ResourceDAO<Long, Software> softwareDAO;

	@Inject
	private ResourceDAO<Long, Documentation> documentationDAO;

	@Inject
	private ResourceDAO<Long, Equipment> equipmentDAO;

	@Inject
	private ResourceDAO<Long, PedagogicalAndDocumentaryResource> pedagogicalAndDocumentaryResourceDAO;

	@Inject
	private ResourceDAO<Long, ProfessionalTraining> professionalTrainingDAO;
	
	public AbstractResourceDAOTest() {
	}

	/**
	 * Test Resource creation and ResourceCategory association
	 */
	@Test
	@Transactional
	@Rollback(true)
	public final void t01_TestDAOResourceAndResourceCategoryAssociation() {
		ResourceCategory learningManagementSystem = resourceCategoryDAO.create("Learning Management System", "A superb tool", null);
		
		Software moodle = softwareDAO.create("Moodle", null);

		moodle.getCategories().add(learningManagementSystem);
		learningManagementSystem.getResources().add(moodle);
		moodle = softwareDAO.update(moodle);
		learningManagementSystem = resourceCategoryDAO.update(learningManagementSystem);
		
		Software moodleBis = softwareDAO.retrieveById(moodle.getId(), true);
		ResourceCategory learningManagementSystemBis = resourceCategoryDAO.retrieveById(learningManagementSystem.getId(), true);

		assertTrue(learningManagementSystemBis.getResources().contains(moodleBis));
		assertTrue(moodleBis.getCategories().contains(learningManagementSystemBis));
		
		Collection<Software> softwares = softwareDAO.retrieveAllWithCategory(learningManagementSystemBis);
		assertTrue(softwares.contains(moodleBis));
	}

	/**
	 * Test Resource creation and ResourceCategory association
	 */
	@Test
	@Transactional
	@Rollback(true)
	public final void t02_TestAssociateSoftwareAndDocumentation() {
		Software software = softwareDAO.create("Moodle", null);

		Documentation documentation = documentationDAO.create("Tutorial for Moodle", null);

		software.getDocumentations().add(documentation);
		documentation.getResources().add(software);

		software = softwareDAO.update(software);
		documentation = documentationDAO.update(documentation);

		Software softwareBis = softwareDAO.retrieveById(software.getId(), true);
		assertEquals(new Long(1), new Long(softwareBis.getDocumentations().size()));

		Documentation documentationBis = documentationDAO.retrieveById(documentation.getId(), true);
		assertTrue(softwareBis.getDocumentations().contains(documentationBis));

		softwareBis.getDocumentations().remove(documentationBis);
		documentationBis.getResources().remove(softwareBis);
		assertEquals(new Long(0), new Long(softwareBis.getDocumentations().size()));

		softwareBis = softwareDAO.update(softwareBis);
		assertFalse(softwareBis.getDocumentations().contains(documentationBis));
	}
	
	/**
	 * Test resource retrieval by a person working (or not) for the organisation possessing the given resource
	 */
	@Test
	@Transactional
	@Rollback(true)
	public final void t03_TestResourceRetrievalByPersonWorkingForAnOrganisationThatIsPossessingTheResource() {
		Teacher personWorkingForOrganisation = teacherDAO.create("Some user", "loginuser", "somepassword");

		TeachingDepartment teachingDepartment = teachingDepartmentDAO.create("Math", "M", null);

		ResourceCategory category = resourceCategoryDAO.create("LMS", "Learning Management System", null);

		Software software = softwareDAO.create("Moodle", null);

		// We associate the person and the organisation
		personWorkingForOrganisation.getWorksForOrganisations().add(teachingDepartment);
		personWorkingForOrganisation = teacherDAO.update(personWorkingForOrganisation);

		// We associate the organisation and the resource
		teachingDepartment.getPossessedResources().add(software);
		software.setOrganisationPossessingResource(teachingDepartment);
		teachingDepartment = teachingDepartmentDAO.update(teachingDepartment);
		software = softwareDAO.update(software);

		// We associate the resource and the category
		category.getResources().add(software);
		software.getCategories().add(category);
		category = resourceCategoryDAO.update(category);
		software = softwareDAO.update(software);

		Collection<Software> usableSoftwaresOfPersonWorking = softwareDAO.retrieveResourcesInCategoryForPerson(category,
				personWorkingForOrganisation);
		assertTrue("The person working for an organisation that possesses a resource must be able to retrieve it.",
				usableSoftwaresOfPersonWorking.contains(software));

		// A person who doesn't work for ANY organisation should not be
		// able to use the resource, hence not be able to retrieve it
		Teacher personNotWorkingForOrganisation = teacherDAO.create("Another user", "anotherlogin", "anotherpassword");

		Collection<Software> usableSoftwaresOfPersonNotWorking = softwareDAO.retrieveResourcesInCategoryForPerson(
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
		Teacher personWorkingForOrganisation = teacherDAO.create("Some user", "loginuser", "somepassword");

		AdministrativeDepartment administrativeDepartment = administrativeDepartmentDAO.create("The support service", "TSS", null);

		ResourceCategory category = resourceCategoryDAO.create("LMS", "Learning Management System", null);

		Documentation documentation = documentationDAO.create("Moodle tutorial", null);

		// We associate the person and the organisation
		personWorkingForOrganisation.getWorksForOrganisations().add(administrativeDepartment);
		personWorkingForOrganisation = teacherDAO.update(personWorkingForOrganisation);

		// We associate the organisation and the resource
		administrativeDepartment.getSupportedResources().add(documentation);
		documentation.setOrganisationSupportingResource(administrativeDepartment);
		administrativeDepartment = administrativeDepartmentDAO.update(administrativeDepartment);
		documentation = documentationDAO.update(documentation);

		// We associate the resource and the category
		category.getResources().add(documentation);
		documentation.getCategories().add(category);
		category = resourceCategoryDAO.update(category);
		documentation = documentationDAO.update(documentation);

		Collection<Documentation> usableDocumentationsOfPersonWorking = documentationDAO.retrieveResourcesInCategoryForPerson(category,
				personWorkingForOrganisation);
		assertTrue("The person working for an organisation that supports a resource must be able to retrieve it.",
				usableDocumentationsOfPersonWorking.contains(documentation));

		// A person who don't work for ANY organisation should not be
		// able to use the resource, hence not be able to retrieve it
		Teacher personNotWorkingForOrganisation = teacherDAO.create("Another user", "anotherlogin", "anotherpassword");

		Collection<Documentation> usableDocumentationsPersonNotWorking = documentationDAO.retrieveResourcesInCategoryForPerson(
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
		Teacher personWorkingForOrganisation = teacherDAO.create("Some user", "loginuser", "somepassword");

		Institution institution = institutionDAO.create("University", "U", null);

		ResourceCategory category = resourceCategoryDAO.create("Interactive whiteboards", "Interactive whiteboards", null);

		Equipment equipment = equipmentDAO.create("A brand new interactive whiteboard", null);

		// We associate the person and the organisation
		personWorkingForOrganisation.getWorksForOrganisations().add(institution);
		personWorkingForOrganisation = teacherDAO.update(personWorkingForOrganisation);

		// We associate the organisation and the resource
		equipment.setOrganisationSupportingResource(institution);
		equipment = equipmentDAO.update(equipment);

		// We associate the resource and the category
		category.getResources().add(equipment);
		category = resourceCategoryDAO.update(category);

		Collection<Equipment> usableEquipmentsOfPersonWorking = equipmentDAO.retrieveResourcesInCategoryForPerson(category,
				personWorkingForOrganisation);
		assertTrue("The person working for an organisation that has access to a resource must be able to retrieve it.",
				usableEquipmentsOfPersonWorking.contains(equipment));

		// A person who don't work for ANY organisation should not be
		// able to use the resource, hence not be able to retrieve it
		Teacher personNotWorkingForOrganisation = teacherDAO.create("Another user", "anotherlogin", "anotherpassword");

		Collection<Equipment> usableEquipmentsOfPersonNotWorking = equipmentDAO.retrieveResourcesInCategoryForPerson(
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

		Community community = communityDAO.create("Community", "COM", null);

		// ***********************************************
		// We create a math professor
		// ***********************************************
		Teacher mathTeacher = teacherDAO.create("Prof. Euler", "euler", "euler");

		Institution mathUniversity = institutionDAO.create("University of Math", "UM", null);

		TeachingDepartment mathDepartment = teachingDepartmentDAO.create("Math", "MD", null);

		ResourceCategory interactiveWhiteboards = resourceCategoryDAO.create("Interactive whiteboards", "Interactive whiteboards", null);

		Equipment equipmentSupportedByCommunity = equipmentDAO.create("A brand new interactive whiteboard", null);

		Equipment equipmentPossessedByMathUniversity = equipmentDAO.create("A shiny math electronic whiteboard", null);
		
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

		// We associate the community and the resource it supports
		community.getSupportedResources().add(equipmentSupportedByCommunity);
		equipmentSupportedByCommunity.setOrganisationSupportingResource(community);
		community = communityDAO.update(community);
		equipmentSupportedByCommunity = equipmentDAO.update(equipmentSupportedByCommunity);

		// We associate the university and the resource it possesses
		mathUniversity.getPossessedResources().add(equipmentPossessedByMathUniversity);
		equipmentPossessedByMathUniversity.setOrganisationPossessingResource(mathUniversity);
		mathUniversity = institutionDAO.update(mathUniversity);
		equipmentPossessedByMathUniversity = equipmentDAO.update(equipmentPossessedByMathUniversity);

		// We associate the resource and the category
		interactiveWhiteboards.getResources().add(equipmentSupportedByCommunity);
		equipmentSupportedByCommunity = equipmentDAO.update(equipmentSupportedByCommunity);
		interactiveWhiteboards.getResources().add(equipmentPossessedByMathUniversity);
		equipmentPossessedByMathUniversity = equipmentDAO.update(equipmentPossessedByMathUniversity);
		interactiveWhiteboards = resourceCategoryDAO.update(interactiveWhiteboards);
		
		Collection<Equipment> usableEquipmentsOfMathTeacher = equipmentDAO.retrieveResourcesInCategoryForPerson(interactiveWhiteboards,
				mathTeacher);
		//assertEquals("There should be two equipments available for the math teacher", 2L, usableEquipmentsOfMathTeacher.size());
		assertTrue(
				"The math teacher working for the math department should be able to retrieve the interactive whiteboard possessed by the math university, given the organisations are associated.",
				usableEquipmentsOfMathTeacher.contains(equipmentPossessedByMathUniversity));
		assertTrue(
				"The math teacher working for the math department should be able to retrieve the interactive whiteboard supported by the community, given the organisations are associated.",
				usableEquipmentsOfMathTeacher.contains(equipmentSupportedByCommunity));

		// *************************************************
		// We create a biology professor
		// *************************************************
		Teacher biologyTeacher = teacherDAO.create("Prof. Darwin", "darwin", "darwin");

		Institution biologyUniversity = institutionDAO.create("University of Biology", "UB", null);

		// We associate the biology teacher and the biology university
		biologyTeacher.getWorksForOrganisations().add(biologyUniversity);
		biologyTeacher = teacherDAO.update(biologyTeacher);

		Collection<Equipment> usableResourcesOfBiologyTeacher = equipmentDAO.retrieveResourcesInCategoryForPerson(interactiveWhiteboards,
				biologyTeacher);
		assertFalse(
				"The biology teacher working for the biology university should not be able to retrieve the interactive whiteboard supported by the community, given the biology university and the community are not yet associated.",
				usableResourcesOfBiologyTeacher.contains(equipmentSupportedByCommunity));

		// We associate the biology university with the community
		biologyUniversity.getCommunities().add(community);
		community.getInstitutions().add(biologyUniversity);
		biologyUniversity = institutionDAO.update(biologyUniversity);
		community = communityDAO.update(community);

		usableResourcesOfBiologyTeacher = equipmentDAO.retrieveResourcesInCategoryForPerson(interactiveWhiteboards, biologyTeacher);
		assertTrue(
				"The biology teacher working for the biology university should be able to retrieve the interactive whiteboard supported by the community, given the biology university and the community are now associated.",
				usableResourcesOfBiologyTeacher.contains(equipmentSupportedByCommunity));

		// ************************************************************************
		// The biology university creates an Internet training for every person
		// that works for the biology university (or for its departments)
		// ************************************************************************
		ProfessionalTraining internetTraining = professionalTrainingDAO.create("How to use Google Images to mix fungi species", null);

		ResourceCategory internetTrainings = resourceCategoryDAO.create("Internet trainings", "Internet trainings", null);
		internetTrainings.getResources().add(internetTraining);
		internetTraining.getCategories().add(internetTrainings);
		internetTrainings = resourceCategoryDAO.update(internetTrainings);
		internetTraining = professionalTrainingDAO.update(internetTraining);

		biologyUniversity.getPossessedResources().add(internetTraining);
		internetTraining.setOrganisationPossessingResource(biologyUniversity);
		biologyUniversity = institutionDAO.update(biologyUniversity);
		internetTraining = professionalTrainingDAO.update(internetTraining);

		Collection<ProfessionalTraining> usableProfessionalTrainingsOfBiologyTeacher = professionalTrainingDAO.retrieveResourcesInCategoryForPerson(internetTrainings, biologyTeacher);
		assertTrue(
				"The biology teacher working for the biology university should be able to retrieve the Internet training possessed by the biology university.",
				usableProfessionalTrainingsOfBiologyTeacher.contains(internetTraining));

		Collection<ProfessionalTraining> usableProfessionalTrainingsOfMathTeacher = professionalTrainingDAO.retrieveResourcesInCategoryForPerson(internetTrainings, mathTeacher);
		assertFalse(
				"The math teacher working for the math department should not be able to retrieve the Internet training possessed by the biology university (even though they belong to the same community).",
				usableProfessionalTrainingsOfMathTeacher.contains(internetTraining));

		// ****************************************************************************
		// The biology university shares its Internet training with the math university
		// ****************************************************************************
		mathUniversity.getViewedResources().add(internetTraining);
		internetTraining.getOrganisationsHavingAccessToResource().add(mathUniversity);
		mathUniversity = institutionDAO.update(mathUniversity);
		internetTraining = professionalTrainingDAO.update(internetTraining);

		usableProfessionalTrainingsOfMathTeacher = professionalTrainingDAO.retrieveResourcesInCategoryForPerson(internetTrainings, mathTeacher);
		assertTrue(
				"The math teacher working for the math department should be able to retrieve the Internet training possessed by the biology university, because the biology university decided to share this resource with the math university.",
				usableProfessionalTrainingsOfMathTeacher.contains(internetTraining));
	}

}
