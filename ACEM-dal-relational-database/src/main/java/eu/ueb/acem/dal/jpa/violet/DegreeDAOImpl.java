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
package eu.ueb.acem.dal.jpa.violet;

import javax.inject.Inject;

import org.springframework.stereotype.Repository;

import eu.ueb.acem.dal.common.violet.DegreeDAO;
import eu.ueb.acem.dal.jpa.AbstractDAO;
import eu.ueb.acem.dal.jpa.GenericRepository;
import eu.ueb.acem.domain.beans.jpa.violet.DegreeEntity;
import eu.ueb.acem.domain.beans.violet.Degree;

/**
 * The Spring Data JPA implementation of DegreeDAO.
 * 
 * @author Grégoire Colbert
 * @since 2015-06-04
 * 
 */
@Repository("degreeDAO")
public class DegreeDAOImpl extends AbstractDAO<Degree, DegreeEntity> implements DegreeDAO<Long> {

	/**
	 * For serialization.
	 */
	private static final long serialVersionUID = 9108859242567624112L;

	@Inject
	private DegreeRepository repository;

	@Override
	protected final GenericRepository<DegreeEntity> getRepository() {
		return repository;
	}

	@Override
	protected final void initializeCollections(Degree entity) {
		if (entity != null) {
			entity.getCredits().size();
		}
	}

	@Override
	public Degree create(String name) {
		return super.create(new DegreeEntity(name));
	}

}
