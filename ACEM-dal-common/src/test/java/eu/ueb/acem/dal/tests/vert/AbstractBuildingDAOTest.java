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

import junit.framework.TestCase;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import eu.ueb.acem.dal.vert.BuildingDAO;
import eu.ueb.acem.domain.beans.vert.Building;

/**
 * @author Grégoire Colbert
 * @since 2015-02-13
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/dal-common-test-context.xml")
public abstract class AbstractBuildingDAOTest extends TestCase {

	/**
	 * For Logging.
	 */
	@SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory.getLogger(AbstractBuildingDAOTest.class);

	public AbstractBuildingDAOTest() {

	}
	
	protected abstract BuildingDAO<Long> getBuildingDAO();

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

		// We create our object
		Building building = getBuildingDAO().create(name, latitude, longitude);

		// We check that "create" is idempotent (multiple calls must not
		// duplicate data)
		building = getBuildingDAO().create(building);

		// There must exactly 1 object in the datastore
		assertEquals("There should be exactly one object in the datastore", new Long(1), getBuildingDAO().count());

		Building buildingReloaded = getBuildingDAO().retrieveById(building.getId(), false);
		assertNotNull("The reloaded building is null", buildingReloaded);

		assertEquals("RetrieveByName didn't return the correct number of Building", new Long(1), new Long(getBuildingDAO()
				.retrieveByName(building.getName()).size()));

		// The name must be good
		assertEquals("The reloaded name is not good", name, buildingReloaded.getName());

		// The latitude must be good
		assertEquals("The reloaded latitude is not good", latitude, buildingReloaded.getLatitude());

		// The longitude must be good
		assertEquals("The reloaded longitude is not good", longitude, buildingReloaded.getLongitude());
	}
}
