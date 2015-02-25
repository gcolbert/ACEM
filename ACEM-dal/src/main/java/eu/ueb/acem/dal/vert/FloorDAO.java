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
package eu.ueb.acem.dal.vert;

import javax.inject.Inject;

import org.springframework.stereotype.Repository;

import eu.ueb.acem.dal.AbstractDAO;
import eu.ueb.acem.dal.GenericRepository;
import eu.ueb.acem.dal.vert.neo4j.FloorRepository;
import eu.ueb.acem.domain.beans.vert.Floor;
import eu.ueb.acem.domain.beans.vert.neo4j.FloorNode;

/**
 * @author Grégoire Colbert
 * @since 2014-02-07
 * 
 */
@Repository("floorDAO")
public class FloorDAO extends AbstractDAO<Floor, FloorNode> {

	/**
	 * For serialization.
	 */
	private static final long serialVersionUID = -916181043585068038L;

	@Inject
	private FloorRepository repository;

	@Override
	protected final GenericRepository<FloorNode> getRepository() {
		return repository;
	}

	@Override
	protected final void initializeCollections(Floor entity) {
		neo4jOperations.fetch(entity.getBuilding());
		neo4jOperations.fetch(entity.getRooms());
	}

}
