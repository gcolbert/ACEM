package eu.ueb.acem.services.tests;

import javax.inject.Inject;

import junit.framework.TestCase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import eu.ueb.acem.domain.beans.rouge.Community;
import eu.ueb.acem.domain.beans.rouge.Institution;
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

	public OrganisationsServiceTest() {
	}

	@Before
	public void before() {
		organisationsService.deleteAllCommunities();
		assertEquals(new Long(0), organisationsService.countCommunities());

		organisationsService.deleteAllInstitutions();
		assertEquals(new Long(0), organisationsService.countInstitutions());

		organisationsService.deleteAllAdministrativeDepartments();
		assertEquals(new Long(0), organisationsService.countAdministrativeDepartments());

		organisationsService.deleteAllTeachingDepartments();
		assertEquals(new Long(0), organisationsService.countTeachingDepartments());
	}

	@After
	public void after() {
		before();
	}

	/**
	 * AssociateCommunityAndInstitution
	 */
	@Test
	public final void t01_TestAssociateCommunityAndInstitution() {
		Community community1 = organisationsService.createCommunity("my community", "mc", null);
		Institution institution1 = organisationsService.createInstitution("my institution", "mi", null);
		assertTrue(organisationsService.associateCommunityAndInstitution(community1.getId(), institution1.getId()));

		Community community1bis = organisationsService.retrieveCommunity(community1.getId());
		Institution institution1bis = organisationsService.retrieveInstitution(institution1.getId());
		assertTrue(community1bis.getInstitutions().contains(institution1bis));
	}

	/**
	 * DissociateCommunityAndInstitution
	 */
	@Test
	@Transactional
	public final void t02_TestDissociateCommunityAndInstitution() {
		Community community1 = organisationsService.createCommunity("my community", "mc", null);

		Institution institution1 = organisationsService.createInstitution("my institution", "mi", null);

		assertTrue(organisationsService.associateCommunityAndInstitution(community1.getId(), institution1.getId()));
		
		assertTrue(organisationsService.dissociateCommunityAndInstitution(community1.getId(), institution1.getId()));
	}
	
}
