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
import eu.ueb.acem.domain.beans.rouge.Organisation;
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
	 * AssociateOrganisationAndUseMode
	 */
	@Test
	@Transactional
	@Rollback(true)
	public final void t07_TestAssociateOrganisationAndUseMode() {
		Organisation referredOrganisation = organisationsService.createAdministrativeDepartment("my administrativeDepartment", "ad", null);
		UseMode useMode = resourcesService.createUseMode("this is a use mode referring an organisation", referredOrganisation);
		assertTrue("The use mode must reference the organisation", useMode.getReferredOrganisation().equals(referredOrganisation));
		assertTrue("The organisation must reference the use mode", referredOrganisation.getUseModes().contains(useMode));

		AdministrativeDepartment administrativeDepartment1bis = organisationsService.retrieveAdministrativeDepartment(referredOrganisation.getId(), true);
		UseMode useMode1bis = resourcesService.retrieveUseMode(useMode.getId(), true);
		assertTrue("The administrative department doesn't reference the use mode.", administrativeDepartment1bis.getUseModes().contains(useMode1bis));
		assertTrue("The use mode doesn't reference the administrative department.", useMode1bis.getReferredOrganisation().equals(administrativeDepartment1bis));
	}

}
