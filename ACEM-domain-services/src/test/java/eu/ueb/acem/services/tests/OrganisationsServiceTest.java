package eu.ueb.acem.services.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import eu.ueb.acem.domain.beans.rouge.Communaute;
import eu.ueb.acem.domain.beans.rouge.Etablissement;
import eu.ueb.acem.services.OrganisationsService;

/**
 * @author Grégoire Colbert @since 2014-02-26
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/domain-services-test-context.xml")
public class OrganisationsServiceTest {

	@Autowired
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
		Communaute community1 = organisationsService.createCommunity("my community", "mc");
		Etablissement institution1 = organisationsService.createInstitution("my institution", "mi");
		assertTrue(organisationsService.associateCommunityAndInstitution(community1.getId(), institution1.getId()));

		Communaute community1bis = organisationsService.retrieveCommunity(community1.getId());
		Etablissement institution1bis = organisationsService.retrieveInstitution(institution1.getId());
		assertTrue(community1bis.getInstitutions().contains(institution1bis));
	}

	/**
	 * DissociateCommunityAndInstitution
	 */
	@Test
	@Transactional
	public final void t02_TestDissociateCommunityAndInstitution() {
		Communaute community1 = organisationsService.createCommunity("my community", "mc");

		Etablissement institution1 = organisationsService.createInstitution("my institution", "mi");

		assertTrue(organisationsService.associateCommunityAndInstitution(community1.getId(), institution1.getId()));
		
		assertTrue(organisationsService.dissociateCommunityAndInstitution(community1.getId(), institution1.getId()));
	}
	
}
