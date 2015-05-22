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
package eu.ueb.acem.dal.vert.neo4j;


import javax.inject.Inject;

import org.springframework.stereotype.Repository;

import eu.ueb.acem.dal.neo4j.AbstractDAO;
import eu.ueb.acem.dal.neo4j.GenericRepository;
import eu.ueb.acem.dal.vert.RoomDAO;
import eu.ueb.acem.domain.beans.vert.Room;
import eu.ueb.acem.domain.beans.vert.neo4j.RoomNode;

/**
 * @author Grégoire Colbert
 * @since 2014-02-07
 * 
 */
@Repository("roomDAO")
public class RoomDAOImpl extends AbstractDAO<Room, RoomNode> implements RoomDAO<Long> {

	/**
	 * For serialization.
	 */
	private static final long serialVersionUID = 8372049333627234514L;

	@Inject
	private RoomRepository repository;

	@Override
	protected final GenericRepository<RoomNode> getRepository() {
		return repository;
	}

	@Override
	protected final void initializeCollections(Room entity) {
		if (entity != null) {
			neo4jOperations.fetch(entity.getFloor());
		}
	}

	@Override
	public Room create(String name, Integer roomCapacity, Boolean hasWifiAccess) {
		return super.create(new RoomNode(name, roomCapacity, hasWifiAccess));
	}

}
