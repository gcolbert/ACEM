/**
 *     Copyright Grégoire COLBERT 2015
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
package eu.ueb.acem.dal.tests.vert;

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

import eu.ueb.acem.dal.vert.neo4j.BuildingDAO;
import eu.ueb.acem.domain.beans.vert.Building;
import eu.ueb.acem.domain.beans.vert.neo4j.BuildingNode;

/**
 * @author Grégoire Colbert
 * @since 2015-02-13
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/dal-test-context.xml")
public class BuildingDAOTest extends TestCase {

	/**
	 * For Logging.
	 */
	@SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory.getLogger(BuildingDAOTest.class);

	@Inject
	private BuildingDAO buildingDAO;

	public BuildingDAOTest() {

	}

	/**
	 * Create building
	 */
	@Test
	@Transactional
	@Rollback(true)
	public final void t01_TestCreateBuilding() {
		// We create a new object in the datastore
		String name = "SUPTICE Rennes 1";
		Double latitude = new Double("48.119090");
		Double longitude = new Double("-1.641221");

		Building building = new BuildingNode(name);
		building.setLatitude(latitude);
		building.setLongitude(longitude);

		// We create our object
		building = buildingDAO.create(building);

		// We check that "create" is idempotent (multiple calls must not
		// duplicate data)
		building = buildingDAO.create(building);

		// There must exactly 1 object in the datastore
		assertEquals("There should be exactly one object in the datastore", new Long(1), buildingDAO.count());

		Building buildingReloaded = buildingDAO.retrieveById(building.getId(), false);
		assertNotNull("The reloaded building is null", buildingReloaded);

		assertEquals("RetrieveByName didn't return the correct number of Building", new Long(1), new Long(buildingDAO
				.retrieveByName(building.getName()).size()));

		// The name must be good
		assertEquals("The reloaded name is not good", name, buildingReloaded.getName());

		// The latitude must be good
		assertEquals("The reloaded latitude is not good", latitude, buildingReloaded.getLatitude());

		// The longitude must be good
		assertEquals("The reloaded longitude is not good", longitude, buildingReloaded.getLongitude());
	}
}
