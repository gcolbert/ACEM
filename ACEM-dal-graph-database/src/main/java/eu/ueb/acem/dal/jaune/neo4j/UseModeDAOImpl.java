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
package eu.ueb.acem.dal.jaune.neo4j;

import javax.inject.Inject;

import org.springframework.stereotype.Repository;

import eu.ueb.acem.dal.jaune.UseModeDAO;
import eu.ueb.acem.dal.neo4j.AbstractDAO;
import eu.ueb.acem.dal.neo4j.GenericRepository;
import eu.ueb.acem.domain.beans.jaune.UseMode;
import eu.ueb.acem.domain.beans.jaune.neo4j.UseModeNode;

/**
 * @author Grégoire Colbert
 * @since 2014-03-19
 * 
 */
@Repository("useModeDAO")
public class UseModeDAOImpl extends AbstractDAO<UseMode, UseModeNode> implements UseModeDAO<Long> {

	/**
	 * For serialization.
	 */
	private static final long serialVersionUID = -1874254078249425495L;

	@Inject
	private UseModeRepository repository;

	@Override
	protected final GenericRepository<UseModeNode> getRepository() {
		return repository;
	}

	@Override
	protected final void initializeCollections(UseMode entity) {
		if (entity != null) {
			neo4jOperations.fetch(entity.getResources());
		}
	}

	@Override
	public UseMode create(String name) {
		return super.create(new UseModeNode(name));
	}

}
