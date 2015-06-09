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

import eu.ueb.acem.dal.common.vert.CampusDAO;
import eu.ueb.acem.dal.neo4j.AbstractDAO;
import eu.ueb.acem.dal.neo4j.GenericRepository;
import eu.ueb.acem.domain.beans.neo4j.vert.CampusNode;
import eu.ueb.acem.domain.beans.vert.Campus;

/**
 * @author Grégoire Colbert
 * @since 2014-02-07
 * 
 */
@Repository("campusDAO")
public class CampusDAOImpl extends AbstractDAO<Campus, CampusNode> implements CampusDAO<Long> {

	/**
	 * For serialization.
	 */
	private static final long serialVersionUID = 7713450512674141045L;

	@Inject
	private CampusRepository repository;

	@Override
	protected final GenericRepository<CampusNode> getRepository() {
		return repository;
	}

	@Override
	protected final void initializeCollections(Campus entity) {
		if (entity != null) {
			neo4jOperations.fetch(entity.getBuildings());
		}
	}

	public Campus create(String name, Double latitude, Double longitude) {
		return super.create(new CampusNode(name, latitude, longitude));
	}

}
