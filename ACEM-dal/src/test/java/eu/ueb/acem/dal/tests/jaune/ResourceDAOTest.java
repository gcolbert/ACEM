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

import junit.framework.TestCase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import eu.ueb.acem.dal.jaune.EquipmentDAO;
import eu.ueb.acem.dal.jaune.PedagogicalAndDocumentaryResourcesDAO;
import eu.ueb.acem.dal.jaune.ProfessionalTrainingDAO;
import eu.ueb.acem.dal.jaune.ResourceCategoryDAO;
import eu.ueb.acem.dal.jaune.SoftwareDAO;
import eu.ueb.acem.dal.jaune.SoftwareDocumentationDAO;
import eu.ueb.acem.dal.jaune.UseModeDAO;
import eu.ueb.acem.domain.beans.jaune.Applicatif;
import eu.ueb.acem.domain.beans.jaune.DocumentationApplicatif;
import eu.ueb.acem.domain.beans.jaune.neo4j.ApplicatifNode;
import eu.ueb.acem.domain.beans.jaune.neo4j.DocumentationApplicatifNode;

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

	@Autowired
	private UseModeDAO useModeDAO;

	@Autowired
	private ResourceCategoryDAO resourceCategoryDAO;

	@Autowired
	private SoftwareDAO softwareDAO;

	@Autowired
	private SoftwareDocumentationDAO softwareDocumentationDAO;

	@Autowired
	private EquipmentDAO equipmentDAO;

	@Autowired
	private PedagogicalAndDocumentaryResourcesDAO pedagogicalAndDocumentaryResourcesDAO;

	@Autowired
	private ProfessionalTrainingDAO professionalTrainingDAO;

	public ResourceDAOTest() {
	}

	@Before
	public void before() {
		useModeDAO.deleteAll();
		assertEquals(new Long(0), useModeDAO.count());

		resourceCategoryDAO.deleteAll();
		assertEquals(new Long(0), resourceCategoryDAO.count());

		softwareDAO.deleteAll();
		assertEquals(new Long(0), softwareDAO.count());

		softwareDocumentationDAO.deleteAll();
		assertEquals(new Long(0), softwareDocumentationDAO.count());

		equipmentDAO.deleteAll();
		assertEquals(new Long(0), equipmentDAO.count());

		pedagogicalAndDocumentaryResourcesDAO.deleteAll();
		assertEquals(new Long(0), pedagogicalAndDocumentaryResourcesDAO.count());

		professionalTrainingDAO.deleteAll();
		assertEquals(new Long(0), professionalTrainingDAO.count());
	}

	@After
	public void after() {
		before();
	}

	/**
	 * Test Resource creation and ResourceCategory association
	 */
	@Test
	public final void t01_TestDAOCreate() {

	}

	/**
	 * Test Resource creation and ResourceCategory association
	 */
	@Test
	public final void t02_TestAssociateSoftwareAndDocumentation() {
		Applicatif software = new ApplicatifNode("Moodle");
		software = softwareDAO.create(software);

		DocumentationApplicatif softwareDocumentation = new DocumentationApplicatifNode("Tutorial for Moodle");
		softwareDocumentation = softwareDocumentationDAO.create(softwareDocumentation);

		software.addDocumentation(softwareDocumentation);
		
		software = softwareDAO.update(software);
		softwareDocumentation = softwareDocumentationDAO.update(softwareDocumentation);

		Applicatif softwareBis = softwareDAO.retrieveById(software.getId());
		assertEquals(new Long(1), new Long(softwareBis.getDocumentations().size()));

		DocumentationApplicatif softwareDocumentationBis = softwareDocumentationDAO.retrieveById(softwareDocumentation
				.getId());
		assertTrue(softwareBis.getDocumentations().contains(softwareDocumentationBis));

		softwareBis.removeDocumentation(softwareDocumentationBis);
		assertEquals(new Long(0), new Long(softwareBis.getDocumentations().size()));

		softwareBis = softwareDAO.update(softwareBis);
		assertFalse(softwareBis.getDocumentations().contains(softwareDocumentationBis));
	}

}
