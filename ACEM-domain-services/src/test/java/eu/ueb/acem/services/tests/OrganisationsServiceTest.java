package eu.ueb.acem.services.tests;

import javax.inject.Inject;

import junit.framework.TestCase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import eu.ueb.acem.domain.beans.bleu.PedagogicalAnswer;
import eu.ueb.acem.domain.beans.rouge.AdministrativeDepartment;
import eu.ueb.acem.domain.beans.rouge.Community;
import eu.ueb.acem.domain.beans.rouge.Institution;
import eu.ueb.acem.domain.beans.rouge.TeachingDepartment;
import eu.ueb.acem.services.NeedsAndAnswersService;
import eu.ueb.acem.services.OrganisationsService;

/**
 * @author Grégoire Colbert
 * @since 2014-02-26
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/domain-services-test-context.xml")
public class OrganisationsServiceTest extends TestCase {

	@Inject
	private OrganisationsService organisationsService;

	@Inject
	private NeedsAndAnswersService needsAndAnswersService;
	
	public OrganisationsServiceTest() {
	}
	
	@Before
	public void before() {
	}

	@After
	public void after() {
	}

	/**
	 * AssociateCommunityAndInstitution
	 */
	@Test
	@Transactional
	@Rollback(true)
	public final void t01_TestAssociateCommunityAndInstitution() {
		Community community1 = organisationsService.createCommunity("my community", "mc", null);
		Institution institution1 = organisationsService.createInstitution("my institution", "mi", null);
		assertTrue("The association of the Community and Institution failed", organisationsService.associateCommunityAndInstitution(community1.getId(), institution1.getId()));

		Community community1bis = organisationsService.retrieveCommunity(community1.getId(), true);
		Institution institution1bis = organisationsService.retrieveInstitution(institution1.getId(), true);
		assertTrue("The community doesn't reference the institution.", community1bis.getInstitutions().contains(institution1bis));
		assertTrue("The institution doesn't reference the community.", institution1bis.getCommunities().contains(community1bis));
	}

	/**
	 * DissociateCommunityAndInstitution
	 */
	@Test
	@Transactional
	@Rollback(true)
	public final void t02_TestDissociateCommunityAndInstitution() {
		Community community1 = organisationsService.createCommunity("my community", "mc", null);
		Institution institution1 = organisationsService.createInstitution("my institution", "mi", null);

		assertTrue("The association of the Community and Institution failed", organisationsService.associateCommunityAndInstitution(community1.getId(), institution1.getId()));

		assertTrue("The dissociation of the Community and Institution failed", organisationsService.dissociateCommunityAndInstitution(community1.getId(), institution1.getId()));

		Community community1bis = organisationsService.retrieveCommunity(community1.getId(), true);
		Institution institution1bis = organisationsService.retrieveInstitution(institution1.getId(), true);
		assertFalse("The community still references the institution.",community1bis.getInstitutions().contains(institution1bis));
		assertFalse("The institution still references the community.",institution1bis.getCommunities().contains(community1bis));
	}

	/**
	 * AssociateTeachingDepartmentAndInstitution
	 */
	@Test
	@Transactional
	@Rollback(true)
	public final void t03_TestAssociateTeachingDepartmentAndInstitution() {
		TeachingDepartment teachingDepartment1 = organisationsService.createTeachingDepartment("my TeachingDepartment", "td", null);
		Institution institution1 = organisationsService.createInstitution("my institution", "mi", null);
		assertTrue("The association of the Institution and TeachingDepartment failed", organisationsService.associateInstitutionAndTeachingDepartment(institution1.getId(), teachingDepartment1.getId()));

		TeachingDepartment teachingDepartment1bis = organisationsService.retrieveTeachingDepartment(teachingDepartment1.getId(), true);
		Institution institution1bis = organisationsService.retrieveInstitution(institution1.getId(), true);
		assertTrue("The TeachingDepartment doesn't reference the institution.", teachingDepartment1bis.getInstitutions().contains(institution1bis));
		assertTrue("The institution doesn't reference the TeachingDepartment.", institution1bis.getTeachingDepartments().contains(teachingDepartment1bis));
	}

	/**
	 * DissociateTeachingDepartmentAndInstitution
	 */
	@Test
	@Transactional
	@Rollback(true)
	public final void t04_TestDissociateTeachingDepartmentAndInstitution() {
		TeachingDepartment teachingDepartment1 = organisationsService.createTeachingDepartment("my TeachingDepartment", "td", null);
		Institution institution1 = organisationsService.createInstitution("my institution", "mi", null);

		assertTrue("The association of the Institution and TeachingDepartment failed", organisationsService.associateInstitutionAndTeachingDepartment(institution1.getId(), teachingDepartment1.getId()));

		assertTrue("The dissociation of the Institution and TeachingDepartment failed", organisationsService.dissociateInstitutionAndTeachingDepartment(institution1.getId(), teachingDepartment1.getId()));

		TeachingDepartment teachingDepartment1bis = organisationsService.retrieveTeachingDepartment(teachingDepartment1.getId(), true);
		Institution institution1bis = organisationsService.retrieveInstitution(institution1.getId(), true);
		assertFalse("The TeachingDepartment still references the institution.",teachingDepartment1bis.getInstitutions().contains(institution1bis));
		assertFalse("The institution still references the TeachingDepartment.",institution1bis.getTeachingDepartments().contains(teachingDepartment1bis));
	}

	/**
	 * AssociateAdministrativeDepartmentAndInstitution
	 */
	@Test
	@Transactional
	@Rollback(true)
	public final void t05_TestAssociateAdministrativeDepartmentAndInstitution() {
		AdministrativeDepartment administrativeDepartment1 = organisationsService.createAdministrativeDepartment("my AdministrativeDepartment", "ad", null);
		Institution institution1 = organisationsService.createInstitution("my institution", "mi", null);
		assertTrue("The association of the Institution and AdministrativeDepartment failed", organisationsService.associateInstitutionAndAdministrativeDepartment(institution1.getId(), administrativeDepartment1.getId()));

		AdministrativeDepartment administrativeDepartment1bis = organisationsService.retrieveAdministrativeDepartment(administrativeDepartment1.getId(), true);
		Institution institution1bis = organisationsService.retrieveInstitution(institution1.getId(), true);
		assertTrue("The AdministrativeDepartment doesn't reference the institution.", administrativeDepartment1bis.getInstitutions().contains(institution1bis));
		assertTrue("The institution doesn't reference the AdministrativeDepartment.", institution1bis.getAdministrativeDepartments().contains(administrativeDepartment1bis));
	}

	/**
	 * DissociateAdministrativeDepartmentAndInstitution
	 */
	@Test
	@Transactional
	@Rollback(true)
	public final void t06_TestDissociateAdministrativeDepartmentAndInstitution() {
		AdministrativeDepartment administrativeDepartment1 = organisationsService.createAdministrativeDepartment("my AdministrativeDepartment", "ad", null);
		Institution institution1 = organisationsService.createInstitution("my institution", "mi", null);

		assertTrue("The association of the Institution and AdministrativeDepartment failed", organisationsService.associateInstitutionAndAdministrativeDepartment(institution1.getId(), administrativeDepartment1.getId()));

		assertTrue("The dissociation of the Institution and AdministrativeDepartment failed", organisationsService.dissociateInstitutionAndAdministrativeDepartment(institution1.getId(), administrativeDepartment1.getId()));

		AdministrativeDepartment administrativeDepartment1bis = organisationsService.retrieveAdministrativeDepartment(administrativeDepartment1.getId(), true);
		Institution institution1bis = organisationsService.retrieveInstitution(institution1.getId(), true);
		assertFalse("The AdministrativeDepartment still references the institution.",administrativeDepartment1bis.getInstitutions().contains(institution1bis));
		assertFalse("The institution still references the AdministrativeDepartment.",institution1bis.getAdministrativeDepartments().contains(administrativeDepartment1bis));
	}

	/**
	 * AssociateAdministrativeDepartmentAndPedagogicalAnswer
	 */
	@Test
	@Transactional
	@Rollback(true)
	public final void t01_TestAssociateAdministrativeDepartmentAndPedagogicalAnswer() {
		AdministrativeDepartment administrativeDepartment1 = organisationsService.createAdministrativeDepartment("my administrativeDepartment", "mc", null);
		PedagogicalAnswer pedagogicalAnswer1 = needsAndAnswersService.createPedagogicalAnswer("my pedagogicalAnswer");
		assertTrue("The association of the PedagogicalAnswer and AdministrativeDepartment failed", organisationsService.associatePedagogicalAnswerAndAdministrativeDepartment(pedagogicalAnswer1.getId(), administrativeDepartment1.getId()));

		AdministrativeDepartment administrativeDepartment1bis = organisationsService.retrieveAdministrativeDepartment(administrativeDepartment1.getId(), true);
		PedagogicalAnswer pedagogicalAnswer1bis = needsAndAnswersService.retrievePedagogicalAnswer(pedagogicalAnswer1.getId(), true);
		assertTrue("The administrativeDepartment doesn't reference the pedagogicalAnswer.", administrativeDepartment1bis.getPedagogicalAnswers().contains(pedagogicalAnswer1bis));
		assertTrue("The pedagogicalAnswer doesn't reference the administrativeDepartment.", pedagogicalAnswer1bis.getAdministrativeDepartments().contains(administrativeDepartment1bis));
	}

	/**
	 * DissociateAdministrativeDepartmentAndPedagogicalAnswer
	 */
	@Test
	@Transactional
	@Rollback(true)
	public final void t02_TestDissociateAdministrativeDepartmentAndPedagogicalAnswer() {
		AdministrativeDepartment administrativeDepartment1 = organisationsService.createAdministrativeDepartment("my administrativeDepartment", "mc", null);
		PedagogicalAnswer pedagogicalAnswer1 = needsAndAnswersService.createPedagogicalAnswer("my pedagogicalAnswer");

		assertTrue("The association of the PedagogicalAnswer and AdministrativeDepartment failed", organisationsService.associatePedagogicalAnswerAndAdministrativeDepartment(pedagogicalAnswer1.getId(), administrativeDepartment1.getId()));

		assertTrue("The dissociation of the PedagogicalAnswer and AdministrativeDepartment failed", organisationsService.dissociatePedagogicalAnswerAndAdministrativeDepartment(pedagogicalAnswer1.getId(), administrativeDepartment1.getId()));

		AdministrativeDepartment administrativeDepartment1bis = organisationsService.retrieveAdministrativeDepartment(administrativeDepartment1.getId(), true);
		PedagogicalAnswer pedagogicalAnswer1bis = needsAndAnswersService.retrievePedagogicalAnswer(pedagogicalAnswer1.getId(), true);
		assertFalse("The administrativeDepartment still references the pedagogicalAnswer.",administrativeDepartment1bis.getPedagogicalAnswers().contains(pedagogicalAnswer1bis));
		assertFalse("The pedagogicalAnswer still references the administrativeDepartment.",pedagogicalAnswer1bis.getAdministrativeDepartments().contains(administrativeDepartment1bis));
	}
	
}
