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

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import eu.ueb.acem.dal.jaune.EquipmentDAO;
import eu.ueb.acem.dal.jaune.PedagogicalAndDocumentaryResourcesDAO;
import eu.ueb.acem.dal.jaune.ProfessionalTrainingDAO;
import eu.ueb.acem.dal.jaune.ResourceCategoryDAO;
import eu.ueb.acem.dal.jaune.SoftwareDAO;
import eu.ueb.acem.dal.jaune.SoftwareDocumentationDAO;
import eu.ueb.acem.dal.jaune.UseModeDAO;
import eu.ueb.acem.domain.beans.jaune.Software;
import eu.ueb.acem.domain.beans.jaune.SoftwareDocumentation;
import eu.ueb.acem.domain.beans.jaune.ResourceCategory;
import eu.ueb.acem.domain.beans.jaune.neo4j.SoftwareNode;
import eu.ueb.acem.domain.beans.jaune.neo4j.SoftwareDocumentationNode;
import eu.ueb.acem.domain.beans.jaune.neo4j.ResourceCategoryNode;

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
	private UseModeDAO useModeDAO;

	@Inject
	private ResourceCategoryDAO resourceCategoryDAO;

	@Inject
	private SoftwareDAO softwareDAO;

	@Inject
	private SoftwareDocumentationDAO softwareDocumentationDAO;

	@Inject
	private EquipmentDAO equipmentDAO;

	@Inject
	private PedagogicalAndDocumentaryResourcesDAO pedagogicalAndDocumentaryResourcesDAO;

	@Inject
	private ProfessionalTrainingDAO professionalTrainingDAO;

	public ResourceDAOTest() {
	}

	@Before
	public void before() {
	}

	@After
	public void after() {
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

		moodle.addCategory(learningManagementSystem);
		moodle = softwareDAO.update(moodle);
		learningManagementSystem = resourceCategoryDAO.update(learningManagementSystem);
		
		Software moodleBis = softwareDAO.retrieveById(moodle.getId());
		ResourceCategory learningManagementSystemBis = resourceCategoryDAO.retrieveById(learningManagementSystem.getId());

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

		SoftwareDocumentation softwareDocumentation = new SoftwareDocumentationNode("Tutorial for Moodle", null);
		softwareDocumentation = softwareDocumentationDAO.create(softwareDocumentation);

		software.addDocumentation(softwareDocumentation);

		software = softwareDAO.update(software);
		softwareDocumentation = softwareDocumentationDAO.update(softwareDocumentation);

		Software softwareBis = softwareDAO.retrieveById(software.getId());
		assertEquals(new Long(1), new Long(softwareBis.getDocumentations().size()));

		SoftwareDocumentation softwareDocumentationBis = softwareDocumentationDAO.retrieveById(softwareDocumentation
				.getId());
		assertTrue(softwareBis.getDocumentations().contains(softwareDocumentationBis));

		softwareBis.removeDocumentation(softwareDocumentationBis);
		assertEquals(new Long(0), new Long(softwareBis.getDocumentations().size()));

		softwareBis = softwareDAO.update(softwareBis);
		assertFalse(softwareBis.getDocumentations().contains(softwareDocumentationBis));
	}

}
