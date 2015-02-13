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

import eu.ueb.acem.domain.beans.jaune.UseMode;
import eu.ueb.acem.domain.beans.rouge.AdministrativeDepartment;
import eu.ueb.acem.domain.beans.rouge.Community;
import eu.ueb.acem.domain.beans.rouge.Institution;
import eu.ueb.acem.domain.beans.rouge.TeachingDepartment;
import eu.ueb.acem.services.OrganisationsService;
import eu.ueb.acem.services.ResourcesService;

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
	private ResourcesService resourcesService;

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
	 * AssociateOrganisationWithUseMode
	 */
	@Test
	@Transactional
	@Rollback(true)
	public final void t07_TestAssociateOrganisationAndUseMode() {
		AdministrativeDepartment administrativeDepartment1 = organisationsService.createAdministrativeDepartment("my administrativeDepartment", "ad", null);
		UseMode useMode1 = resourcesService.createUseMode("this is a use mode referring an administrative department");
		assertTrue("The association of the Organisation and UseMode failed", resourcesService.associateUseModeAndOrganisation(useMode1.getId(), administrativeDepartment1.getId()));

		AdministrativeDepartment administrativeDepartment1bis = organisationsService.retrieveAdministrativeDepartment(administrativeDepartment1.getId(), true);
		UseMode useMode1bis = resourcesService.retrieveUseMode(useMode1.getId(), true);
		assertTrue("The administrative department doesn't reference the use mode.", administrativeDepartment1bis.getUseModes().contains(useMode1bis));
		assertTrue("The use mode doesn't reference the administrative department.", useMode1bis.getReferredOrganisations().contains(administrativeDepartment1bis));
	}

	/**
	 * DissociateOrganisationWithUseMode
	 */
	@Test
	@Transactional
	@Rollback(true)
	public final void t08_TestDissociateOrganisationAndUseMode() {
		AdministrativeDepartment administrativeDepartment1 = organisationsService.createAdministrativeDepartment("my administrativeDepartment", "ad", null);
		UseMode useMode1 = resourcesService.createUseMode("this is a use mode referring an administrative department");

		assertTrue("The association of the administrative department and use mode failed", resourcesService.associateUseModeAndOrganisation(useMode1.getId(), administrativeDepartment1.getId()));

		assertTrue("The dissociation of the administrative department and use mode failed", resourcesService.dissociateUseModeAndOrganisation(useMode1.getId(), administrativeDepartment1.getId()));

		AdministrativeDepartment administrativeDepartment1bis = organisationsService.retrieveAdministrativeDepartment(administrativeDepartment1.getId(), true);
		UseMode useMode1bis = resourcesService.retrieveUseMode(useMode1.getId(), true);
		assertFalse("The administrative department still references the use mode.",administrativeDepartment1bis.getUseModes().contains(useMode1bis));
		assertFalse("The use mode still references the administrative department.",useMode1bis.getReferredOrganisations().contains(administrativeDepartment1bis));
	}
	
}
