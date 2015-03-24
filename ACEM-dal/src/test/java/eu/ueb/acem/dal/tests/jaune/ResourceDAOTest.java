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
package eu.ueb.acem.dal.tests.jaune;

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

import eu.ueb.acem.dal.DAO;
import eu.ueb.acem.dal.jaune.ResourceDAO;
import eu.ueb.acem.domain.beans.gris.Person;
import eu.ueb.acem.domain.beans.gris.Teacher;
import eu.ueb.acem.domain.beans.gris.neo4j.PersonNode;
import eu.ueb.acem.domain.beans.gris.neo4j.TeacherNode;
import eu.ueb.acem.domain.beans.jaune.Equipment;
import eu.ueb.acem.domain.beans.jaune.PedagogicalAndDocumentaryResource;
import eu.ueb.acem.domain.beans.jaune.ProfessionalTraining;
import eu.ueb.acem.domain.beans.jaune.ResourceCategory;
import eu.ueb.acem.domain.beans.jaune.Software;
import eu.ueb.acem.domain.beans.jaune.Documentation;
import eu.ueb.acem.domain.beans.jaune.UseMode;
import eu.ueb.acem.domain.beans.jaune.neo4j.EquipmentNode;
import eu.ueb.acem.domain.beans.jaune.neo4j.ProfessionalTrainingNode;
import eu.ueb.acem.domain.beans.jaune.neo4j.ResourceCategoryNode;
import eu.ueb.acem.domain.beans.jaune.neo4j.DocumentationNode;
import eu.ueb.acem.domain.beans.jaune.neo4j.SoftwareNode;
import eu.ueb.acem.domain.beans.rouge.AdministrativeDepartment;
import eu.ueb.acem.domain.beans.rouge.Community;
import eu.ueb.acem.domain.beans.rouge.Institution;
import eu.ueb.acem.domain.beans.rouge.TeachingDepartment;
import eu.ueb.acem.domain.beans.rouge.neo4j.AdministrativeDepartmentNode;
import eu.ueb.acem.domain.beans.rouge.neo4j.CommunityNode;
import eu.ueb.acem.domain.beans.rouge.neo4j.InstitutionNode;
import eu.ueb.acem.domain.beans.rouge.neo4j.TeachingDepartmentNode;

/**
 * @author Grégoire Colbert
 * @since 2014-03-11
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/dal-test-context.xml")
public class ResourceDAOTest extends TestCase {

	@SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory.getLogger(ResourceDAOTest.class);

	@Inject
	private DAO<Long, UseMode> useModeDAO;

	@Inject
	private DAO<Long, ResourceCategory> resourceCategoryDAO;

	@Inject
	private DAO<Long, Person> personDAO;

	@Inject
	private DAO<Long, Teacher> teacherDAO;
	
	@Inject
	private DAO<Long, Community> communityDAO;

	@Inject
	private DAO<Long, Institution> institutionDAO;

	@Inject
	private DAO<Long, TeachingDepartment> teachingDepartmentDAO;

	@Inject
	private DAO<Long, AdministrativeDepartment> administrativeDepartmentDAO;

	@Inject
	private ResourceDAO<Long, Software> softwareDAO;

	@Inject
	private ResourceDAO<Long, Documentation> documentationDAO;

	@Inject
	private ResourceDAO<Long, Equipment> equipmentDAO;

	@Inject
	private ResourceDAO<Long, PedagogicalAndDocumentaryResource> pedagogicalAndDocumentaryResourcesDAO;

	@Inject
	private ResourceDAO<Long, ProfessionalTraining> professionalTrainingDAO;

	public ResourceDAOTest() {
	}

	/**
	 * Test Resource creation and ResourceCategory association
	 */
	@Test
	@Transactional
	@Rollback(true)
	public final void t01_TestDAOResourceAndResourceCategoryAssociation() {
		ResourceCategory learningManagementSystem = new ResourceCategoryNode("Learning Management System", "A superb tool", null);
		learningManagementSystem = resourceCategoryDAO.create(learningManagementSystem);
		
		Software moodle = new SoftwareNode("Moodle", null);
		moodle = softwareDAO.create(moodle);

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
		Software software = new SoftwareNode("Moodle", null);
		software = softwareDAO.create(software);

		Documentation documentation = new DocumentationNode("Tutorial for Moodle", null);
		documentation = documentationDAO.create(documentation);

		software.getDocumentations().add(documentation);
		documentation.getResources().add(software);

		software = softwareDAO.update(software);
		documentation = documentationDAO.update(documentation);

		Software softwareBis = softwareDAO.retrieveById(software.getId(), true);
		assertEquals(new Long(1), new Long(softwareBis.getDocumentations().size()));

		Documentation documentationBis = documentationDAO.retrieveById(documentation.getId(), true);
		logger.info("documentation={}", documentation);
		logger.info("documentationBis={}", documentationBis);
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
		Person personWorkingForOrganisation = new PersonNode("Some user", "loginuser", "somepassword");
		personWorkingForOrganisation = personDAO.create(personWorkingForOrganisation);

		TeachingDepartment teachingDepartment = new TeachingDepartmentNode("Math", "M", null);
		teachingDepartment = teachingDepartmentDAO.create(teachingDepartment);

		ResourceCategory category = new ResourceCategoryNode("LMS", "Learning Management System", null);
		category = resourceCategoryDAO.create(category);

		Software software = new SoftwareNode("Moodle", null);
		software = softwareDAO.create(software);

		// We associate the person and the organisation
		personWorkingForOrganisation.getWorksForOrganisations().add(teachingDepartment);
		personWorkingForOrganisation = personDAO.update(personWorkingForOrganisation);

		// We associate the organisation and the resource
		teachingDepartment.getPossessedResources().add(software);
		teachingDepartment = teachingDepartmentDAO.update(teachingDepartment);

		// We associate the resource and the category
		category.getResources().add(software);
		category = resourceCategoryDAO.update(category);

		Collection<Software> usableSoftwaresOfPersonWorking = softwareDAO.retrieveResourcesInCategoryForPerson(category,
				personWorkingForOrganisation);
		assertTrue("The person working for an organisation that possesses a resource must be able to retrieve it.",
				usableSoftwaresOfPersonWorking.contains(software));

		// A person who don't work for ANY organisation should not be
		// able to use the resource, hence not be able to retrieve it
		Person personNotWorkingForOrganisation = new PersonNode("Another user", "anotherlogin", "anotherpassword");
		personNotWorkingForOrganisation = personDAO.create(personNotWorkingForOrganisation);

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
		Person personWorkingForOrganisation = new PersonNode("Some user", "loginuser", "somepassword");
		personWorkingForOrganisation = personDAO.create(personWorkingForOrganisation);

		AdministrativeDepartment administrativeDepartment = new AdministrativeDepartmentNode("The support service", "TSS", null);
		administrativeDepartment = administrativeDepartmentDAO.create(administrativeDepartment);

		ResourceCategory category = new ResourceCategoryNode("LMS", "Learning Management System", null);
		category = resourceCategoryDAO.create(category);

		Documentation documentation = new DocumentationNode("Moodle tutorial", null);
		documentation = documentationDAO.create(documentation);

		// We associate the person and the organisation
		personWorkingForOrganisation.getWorksForOrganisations().add(administrativeDepartment);
		personWorkingForOrganisation = personDAO.update(personWorkingForOrganisation);

		// We associate the organisation and the resource
		administrativeDepartment.getSupportedResources().add(documentation);
		administrativeDepartment = administrativeDepartmentDAO.update(administrativeDepartment);

		// We associate the resource and the category
		category.getResources().add(documentation);
		category = resourceCategoryDAO.update(category);

		Collection<Documentation> usableDocumentationsOfPersonWorking = documentationDAO.retrieveResourcesInCategoryForPerson(category,
				personWorkingForOrganisation);
		assertTrue("The person working for an organisation that supports a resource must be able to retrieve it.",
				usableDocumentationsOfPersonWorking.contains(documentation));

		// A person who don't work for ANY organisation should not be
		// able to use the resource, hence not be able to retrieve it
		Person personNotWorkingForOrganisation = new PersonNode("Another user", "anotherlogin", "anotherpassword");
		personNotWorkingForOrganisation = personDAO.create(personNotWorkingForOrganisation);

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
		Person personWorkingForOrganisation = new PersonNode("Some user", "loginuser", "somepassword");
		personWorkingForOrganisation = personDAO.create(personWorkingForOrganisation);

		Institution institution = new InstitutionNode("University", "U", null);
		institution = institutionDAO.create(institution);

		ResourceCategory category = new ResourceCategoryNode("Interactive whiteboards", "Interactive whiteboards", null);
		category = resourceCategoryDAO.create(category);

		Equipment equipment = new EquipmentNode("A brand new interactive whiteboard", null);
		equipment = equipmentDAO.create(equipment);

		// We associate the person and the organisation
		personWorkingForOrganisation.getWorksForOrganisations().add(institution);
		personWorkingForOrganisation = personDAO.update(personWorkingForOrganisation);

		// We associate the organisation and the resource
		institution.getSupportedResources().add(equipment);
		institution = institutionDAO.update(institution);

		// We associate the resource and the category
		category.getResources().add(equipment);
		category = resourceCategoryDAO.update(category);

		Collection<Equipment> usableEquipmentsOfPersonWorking = equipmentDAO.retrieveResourcesInCategoryForPerson(category,
				personWorkingForOrganisation);
		assertTrue("The person working for an organisation that has access to a resource must be able to retrieve it.",
				usableEquipmentsOfPersonWorking.contains(equipment));

		// A person who don't work for ANY organisation should not be
		// able to use the resource, hence not be able to retrieve it
		Person personNotWorkingForOrganisation = new PersonNode("Another user", "anotherlogin", "anotherpassword");
		personNotWorkingForOrganisation = personDAO.create(personNotWorkingForOrganisation);

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

		Community community = new CommunityNode("Comunity", "COM", null);
		community = communityDAO.create(community);

		// ***********************************************
		// We create a math professor
		// ***********************************************
		Teacher mathTeacher = new TeacherNode("Prof. Euler", "euler", "euler");
		mathTeacher = teacherDAO.create(mathTeacher);

		Institution mathUniversity = new InstitutionNode("University of Math", "UM", null);
		mathUniversity = institutionDAO.create(mathUniversity);

		TeachingDepartment mathDepartment = new TeachingDepartmentNode("Math", "MD", null);
		mathDepartment = teachingDepartmentDAO.create(mathDepartment);

		ResourceCategory interactiveWhiteboards = new ResourceCategoryNode("Interactive whiteboards", "Interactive whiteboards", null);
		interactiveWhiteboards = resourceCategoryDAO.create(interactiveWhiteboards);

		Equipment equipment = new EquipmentNode("A brand new interactive whiteboard", null);
		equipment = equipmentDAO.create(equipment);

		// We associate the math teacher and the math department
		mathTeacher.getWorksForOrganisations().add(mathDepartment);
		mathTeacher = teacherDAO.update(mathTeacher);

		// We associate the math department with the university
		mathDepartment.getInstitutions().add(mathUniversity);
		mathUniversity.getTeachingDepartments().add(mathDepartment);
		mathDepartment = teachingDepartmentDAO.update(mathDepartment);

		// We associate the university with the community
		mathUniversity.getCommunities().add(community);
		community.getInstitutions().add(mathUniversity);
		mathUniversity = institutionDAO.update(mathUniversity);

		// We associate the community and the resource
		community.getSupportedResources().add(equipment);
		community = communityDAO.update(community);

		// We associate the resource and the category
		interactiveWhiteboards.getResources().add(equipment);
		interactiveWhiteboards = resourceCategoryDAO.update(interactiveWhiteboards);

		Collection<Equipment> usableEquipmentsOfMathTeacher = equipmentDAO.retrieveResourcesInCategoryForPerson(interactiveWhiteboards,
				mathTeacher);
		assertTrue(
				"The math teacher working for the math department should be able to retrieve the interactive whiteboard possessed by the community, given the organisations are associated.",
				usableEquipmentsOfMathTeacher.contains(equipment));

		// *************************************************
		// We create a biology professor
		// *************************************************
		Teacher biologyTeacher = new TeacherNode("Prof. Darwin", "darwin", "darwin");
		biologyTeacher = teacherDAO.create(biologyTeacher);

		Institution biologyUniversity = new InstitutionNode("University of Biology", "UB", null);
		biologyUniversity = institutionDAO.create(biologyUniversity);

		// We associate the biology teacher and the biology university
		biologyTeacher.getWorksForOrganisations().add(biologyUniversity);
		biologyTeacher = teacherDAO.update(biologyTeacher);

		Collection<Equipment> usableResourcesOfBiologyTeacher = equipmentDAO.retrieveResourcesInCategoryForPerson(interactiveWhiteboards,
				biologyTeacher);
		assertFalse(
				"The biology teacher working for the biology department should not be able to retrieve the interactive whiteboard possessed by the community, given the math university and the community are not yet associated.",
				usableResourcesOfBiologyTeacher.contains(equipment));

		// We associate the biology university with the community
		biologyUniversity.getCommunities().add(community);
		community.getInstitutions().add(biologyUniversity);
		biologyUniversity = institutionDAO.update(biologyUniversity);

		usableResourcesOfBiologyTeacher = equipmentDAO.retrieveResourcesInCategoryForPerson(interactiveWhiteboards, biologyTeacher);
		assertTrue(
				"The biology teacher working for the biology department should be able to retrieve the interactive whiteboard possessed by the community, given the math university and the community are now associated.",
				usableResourcesOfBiologyTeacher.contains(equipment));

		// ************************************************************************
		// The biology university creates an Internet training for every person
		// that works for the biology university (or for its departments)
		// ************************************************************************
		ProfessionalTraining internetTraining = new ProfessionalTrainingNode("How to use Google Images to mix fungi species", null);
		internetTraining = professionalTrainingDAO.create(internetTraining);

		ResourceCategory internetTrainings = new ResourceCategoryNode("Internet trainings", "Internet trainings", null);
		internetTrainings.getResources().add(internetTraining);
		internetTrainings = resourceCategoryDAO.create(internetTrainings);

		biologyUniversity.getPossessedResources().add(internetTraining);
		biologyUniversity = institutionDAO.update(biologyUniversity);

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
		mathUniversity = institutionDAO.update(mathUniversity);

		usableProfessionalTrainingsOfMathTeacher = professionalTrainingDAO.retrieveResourcesInCategoryForPerson(internetTrainings, mathTeacher);
		assertTrue(
				"The math teacher working for the math department should be able to retrieve the Internet training possessed by the biology university, because the biology university decided to share this resource with the math university.",
				usableProfessionalTrainingsOfMathTeacher.contains(internetTraining));
	}

}
