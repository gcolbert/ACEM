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
package eu.ueb.acem.dal.neo4j.violet;

import javax.inject.Inject;

import org.springframework.stereotype.Repository;

import eu.ueb.acem.dal.common.violet.ClassDAO;
import eu.ueb.acem.dal.neo4j.AbstractDAO;
import eu.ueb.acem.dal.neo4j.GenericRepository;
import eu.ueb.acem.domain.beans.neo4j.violet.ClassNode;
import eu.ueb.acem.domain.beans.violet.Class;

/**
 * The Spring Data Neo4j implementation of ClassDAO.
 * 
 * @author Grégoire Colbert
 * @since 2014-02-07
 * 
 */
@Repository("classDAO")
public class ClassDAOImpl extends AbstractDAO<Class, ClassNode> implements ClassDAO<Long> {

	/**
	 * For serialization.
	 */
	private static final long serialVersionUID = 3463144134744279313L;

	@Inject
	private ClassRepository repository;

	@Override
	protected final GenericRepository<ClassNode> getRepository() {
		return repository;
	}

	@Override
	protected final void initializeCollections(Class entity) {
		if (entity != null) {
			neo4jOperations.fetch(entity.getCourse());
			neo4jOperations.fetch(entity.getPedagogicalScenario());
			neo4jOperations.fetch(entity.getLocation());
		}
	}

	@Override
	public Class create(String name) {
		return super.create(new ClassNode(name));
	}

}
