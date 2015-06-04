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
package eu.ueb.acem.dal.vert.jpa;

import javax.inject.Inject;

import org.springframework.stereotype.Repository;

import eu.ueb.acem.dal.jpa.AbstractDAO;
import eu.ueb.acem.dal.jpa.GenericRepository;
import eu.ueb.acem.dal.vert.BuildingDAO;
import eu.ueb.acem.domain.beans.vert.Building;
import eu.ueb.acem.domain.beans.vert.jpa.BuildingEntity;

/**
 * @author Grégoire Colbert
 * @since 2015-06-04
 * 
 */
@Repository("buildingDAO")
public class BuildingDAOImpl extends AbstractDAO<Building, BuildingEntity> implements BuildingDAO<Long> {

	/**
	 * For serialization.
	 */
	private static final long serialVersionUID = -7860594193144565750L;

	@Inject
	private BuildingRepository repository;

	@Override
	protected final GenericRepository<BuildingEntity> getRepository() {
		return repository;
	}

	@Override
	protected final void initializeCollections(Building entity) {
		if (entity != null) {
			entity.getCampus();
			entity.getFloors().size();
		}
	}

	@Override
	public Building create(String name, Double latitude, Double longitude) {
		return super.create(new BuildingEntity(name, latitude, longitude));
	}

}
