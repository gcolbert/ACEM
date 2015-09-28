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
package eu.ueb.acem.dal.jpa.vert;


import javax.inject.Inject;

import org.springframework.stereotype.Repository;

import eu.ueb.acem.dal.common.vert.RoomDAO;
import eu.ueb.acem.dal.jpa.AbstractDAO;
import eu.ueb.acem.dal.jpa.GenericRepository;
import eu.ueb.acem.domain.beans.jpa.vert.RoomEntity;
import eu.ueb.acem.domain.beans.vert.Room;

/**
 * @author Grégoire Colbert
 * @since 2015-06-04
 * 
 */
@Repository("roomDAO")
public class RoomDAOImpl extends AbstractDAO<Room, RoomEntity> implements RoomDAO<Long> {

	/**
	 * For serialization.
	 */
	private static final long serialVersionUID = 3973929108212607685L;

	@Inject
	private RoomRepository repository;

	@Override
	protected final GenericRepository<RoomEntity> getRepository() {
		return repository;
	}

	@Override
	protected final void initializeCollections(Room entity) {
		if (entity != null) {
			entity.getFloor();
		}
	}

	@Override
	public Room create(String name, Integer roomCapacity, Boolean hasWifiAccess) {
		return super.create(new RoomEntity(name, roomCapacity, hasWifiAccess));
	}

}
