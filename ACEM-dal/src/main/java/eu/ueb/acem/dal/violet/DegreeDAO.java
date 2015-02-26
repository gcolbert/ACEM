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
package eu.ueb.acem.dal.violet;

import javax.inject.Inject;

import org.springframework.stereotype.Repository;

import eu.ueb.acem.dal.AbstractDAO;
import eu.ueb.acem.dal.GenericRepository;
import eu.ueb.acem.dal.violet.neo4j.DegreeRepository;
import eu.ueb.acem.domain.beans.violet.Degree;
import eu.ueb.acem.domain.beans.violet.neo4j.DegreeNode;

/**
 * @author Grégoire Colbert
 * @since 2014-02-07
 * 
 */
@Repository("diplomaDAO")
public class DegreeDAO extends AbstractDAO<Degree, DegreeNode> {

	/**
	 * For serialization.
	 */
	private static final long serialVersionUID = 20097765170954629L;

	@Inject
	private DegreeRepository repository;

	@Override
	protected final GenericRepository<DegreeNode> getRepository() {
		return repository;
	}

	@Override
	protected final void initializeCollections(Degree entity) {
		if (entity != null) {
			neo4jOperations.fetch(entity.getCredits());
		}
	}

}
