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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import eu.ueb.acem.dal.AbstractDAO;
import eu.ueb.acem.dal.DAO;
import eu.ueb.acem.dal.GenericRepository;
import eu.ueb.acem.dal.violet.neo4j.ClassRepository;
import eu.ueb.acem.domain.beans.violet.Class;
import eu.ueb.acem.domain.beans.violet.neo4j.ClassNode;

/**
 * @author Grégoire Colbert
 * @since 2014-02-07
 * 
 */
@Repository("classDAO")
public class ClassDAO extends AbstractDAO<Class, ClassNode> implements DAO<Long, Class> {

	/**
	 * For serialization.
	 */
	private static final long serialVersionUID = 3463144134744279313L;

	@SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory.getLogger(ClassDAO.class);

	@Inject
	private ClassRepository repository;

	@Override
	protected final GenericRepository<ClassNode> getRepository() {
		return repository;
	}

	@Override
	public void initializeCollections(Class entity) {
		neo4jOperations.fetch(entity.getCourse());
		neo4jOperations.fetch(entity.getPedagogicalScenarios());
		neo4jOperations.fetch(entity.getLocation());
	}

}
