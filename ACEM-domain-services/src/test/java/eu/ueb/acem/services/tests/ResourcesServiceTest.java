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

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import eu.ueb.acem.domain.beans.jaune.Resource;
import eu.ueb.acem.domain.beans.jaune.ResourceCategory;
import eu.ueb.acem.domain.beans.jaune.UseMode;
import eu.ueb.acem.domain.beans.rouge.Organisation;
import eu.ueb.acem.services.OrganisationsService;
import eu.ueb.acem.services.ResourcesService;

/**
 * @author Grégoire Colbert
 * @since 2015-02-23
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/domain-services-test-context.xml")
public class ResourcesServiceTest extends TestCase {

	@Inject
	private OrganisationsService organisationsService;

	@Inject
	private ResourcesService resourcesService;

	/**
	 * AssociateResourceAndUseMode
	 */
	@Test
	@Transactional
	@Rollback(true)
	public final void t01_TestCreateResource() {
		// We create an organisation that will possess the resource
		Organisation ownerOrganisation = organisationsService.createInstitution("The best university", "TBU", null);

		// We create an organisation that will support the resource
		Organisation supportOrganisation = organisationsService.createAdministrativeDepartment("The best support service", "TBSS", null);

		// We create a category that the resource will be in
		ResourceCategory category = resourcesService.createResourceCategory("Interactive whiteboards", "Interactive whiteboards are computerized whiteboards.", null);

		// Finally we create the resource
		Resource resource = resourcesService.createResource(category, ownerOrganisation, supportOrganisation,  resourcesService.getResourceType_RESOURCE_TYPE_EQUIPMENT(), "Interactive whiteboard number 42", null);

		// We reload the resource and initialize the collections (because the
		// asserts only check the pointers and don't access the objects inside
		// the collections, 'false' would work too)
		Resource resourceReloaded = resourcesService.retrieveResource(resource.getId(), true);

		// It's time to check whether the associations have been done correctly
		assertTrue("The resource must know its possessing organisation", resourceReloaded.getOrganisationPossessingResource().equals(ownerOrganisation));
		assertTrue("The owner organisation must reference the resource", ownerOrganisation.getPossessedResources().contains(resourceReloaded));

		assertTrue("The resource must know its supporting organisation", resourceReloaded.getOrganisationSupportingResource().equals(supportOrganisation));
		assertTrue("The support organisation must reference the resource", supportOrganisation.getSupportedResources().contains(resourceReloaded));

		assertTrue("The category must reference the resource", category.getResources().contains(resourceReloaded));
		assertTrue("The resource must reference the category", resourceReloaded.getCategories().contains(category));

		// We create an organisation that will have access to the resource
		Organisation partnerOrganisation = organisationsService.createInstitution("Another institution", "AI", null);
		resourceReloaded.getOrganisationsHavingAccessToResource().add(partnerOrganisation);
		partnerOrganisation.getViewedResources().add(resourceReloaded);
		Resource resourceReloadedAgain = resourcesService.updateResource(resourceReloaded);
		Organisation partnerOrganisationReloaded = organisationsService.retrieveOrganisation(partnerOrganisation.getId(), true);
		assertTrue("The resource must know the organisations that have access to it", resourceReloadedAgain.getOrganisationsHavingAccessToResource().contains(partnerOrganisation));
		assertTrue("The organisation that have access to the resource must reference it", partnerOrganisationReloaded.getViewedResources().contains(resourceReloadedAgain));
	}

	/**
	 * AssociateResourceAndUseMode
	 */
	@Test
	@Transactional
	@Rollback(true)
	public final void t02_TestAssociateResourceAndUseMode() {
		// We create an organisation that will possess the resource
		Organisation ownerOrganisation = organisationsService.createInstitution("The best university", "TBU", null);

		// We create an organisation that will support the resource
		Organisation supportOrganisation = organisationsService.createAdministrativeDepartment("The best support service", "TBSS", null);

		// We create a category that the resource will be in
		ResourceCategory category = resourcesService.createResourceCategory("Interactive whiteboards", "Interactive whiteboards are computerized whiteboards.", null);

		// Finally we create the resource
		Resource resource = resourcesService.createResource(category, ownerOrganisation, supportOrganisation, resourcesService.getResourceType_RESOURCE_TYPE_EQUIPMENT(), "Interactive whiteboard number 42", null);

		// It's time to check whether the associations have been done correctly
		assertTrue("The resource must know its possessing organisation", resource.getOrganisationPossessingResource().equals(ownerOrganisation));
		assertTrue("The owner organisation must reference the resource", ownerOrganisation.getPossessedResources().contains(resource));

		assertTrue("The resource must know its supporting organisation", resource.getOrganisationSupportingResource().equals(supportOrganisation));
		assertTrue("The support organisation must reference the resource", supportOrganisation.getSupportedResources().contains(resource));

		assertTrue("The category must reference the resource", category.getResources().contains(resource));
		assertTrue("The resource must reference the category", resource.getCategories().contains(category));

		// Now we create a use mode for our resource
		UseMode useMode = resourcesService.createUseMode(
						"To reserve a whiteboard, please fill the form at http://here.we.go. If you need help using whiteboards, call 1234 and ask for Mr Doe.",
						supportOrganisation);
		
		UseMode useModeReloaded = resourcesService.retrieveUseMode(useMode.getId(), false);
		Resource resourceReloaded = resourcesService.retrieveResource(resource.getId(), false);
		assertTrue("The association of the use mode and the resource has failed", resourcesService.associateUseModeAndResource(useModeReloaded, resourceReloaded));
		assertTrue("The use mode must reference the resource", useModeReloaded.getResources().contains(resourceReloaded));
		assertTrue("The resource must reference the use mode", resourceReloaded.getUseModes().contains(useModeReloaded));
		assertTrue("The use mode must reference the referred organisation", useModeReloaded.getReferredOrganisation().equals(supportOrganisation));
		assertTrue("The referred organisation must reference the use mode", supportOrganisation.getUseModes().contains(useModeReloaded));
	}

}
