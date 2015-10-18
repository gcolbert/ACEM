/**
 *     Copyright Université Européenne de Bretagne 2012-2015
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
package eu.ueb.acem.dal.neo4j.vert;

import javax.inject.Inject;

import org.springframework.stereotype.Repository;

import eu.ueb.acem.dal.common.vert.BuildingDAO;
import eu.ueb.acem.dal.neo4j.AbstractDAO;
import eu.ueb.acem.dal.neo4j.GenericRepository;
import eu.ueb.acem.domain.beans.neo4j.vert.BuildingNode;
import eu.ueb.acem.domain.beans.vert.Building;

/**
 * The Spring Data Neo4j implementation of BuildingDAO.
 * 
 * @author Grégoire Colbert
 * @since 2014-02-07
 * 
 */
@Repository("buildingDAO")
public class BuildingDAOImpl extends AbstractDAO<Building, BuildingNode> implements BuildingDAO<Long> {

	/**
	 * For serialization.
	 */
	private static final long serialVersionUID = -8261719192237944983L;

	@Inject
	private BuildingRepository repository;

	@Override
	protected final GenericRepository<BuildingNode> getRepository() {
		return repository;
	}

	@Override
	protected final void initializeCollections(Building entity) {
		if (entity != null) {
			neo4jOperations.fetch(entity.getFloors());
		}
	}

	@Override
	public Building create(String name, Double latitude, Double longitude) {
		return super.create(new BuildingNode(name, latitude, longitude));
	}

}
