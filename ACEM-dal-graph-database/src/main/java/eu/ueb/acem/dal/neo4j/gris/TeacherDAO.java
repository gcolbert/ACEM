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
package eu.ueb.acem.dal.neo4j.gris;

import javax.inject.Inject;

import org.springframework.stereotype.Repository;

import eu.ueb.acem.dal.common.gris.PersonDAO;
import eu.ueb.acem.dal.neo4j.AbstractDAO;
import eu.ueb.acem.dal.neo4j.GenericRepository;
import eu.ueb.acem.domain.beans.gris.Teacher;
import eu.ueb.acem.domain.beans.neo4j.gris.TeacherNode;

/**
 * The Spring Data Neo4j implementation of PersonDAO for Teacher domain beans.
 * 
 * @author Grégoire Colbert
 * @since 2013-12-11
 * 
 */
@Repository("teacherDAO")
public class TeacherDAO extends AbstractDAO<Teacher, TeacherNode> implements PersonDAO<Long, Teacher> {

	/**
	 * For serialization
	 */
	private static final long serialVersionUID = 920105894951436261L;

	@Inject
	private TeacherRepository repository;

	@Override
	protected final GenericRepository<TeacherNode> getRepository() {
		return repository;
	}

	@Override
	protected final void initializeCollections(Teacher entity) {
		if (entity != null) {
			neo4jOperations.fetch(entity.getFavoriteToolCategories());
			neo4jOperations.fetch(entity.getPedagogicalScenarios());
			neo4jOperations.fetch(entity.getTeachingUnits());
			neo4jOperations.fetch(entity.getWorksForOrganisations());
		}
	}

	@Override
	public Teacher retrieveByLogin(String id, boolean initialize) {
		Teacher entity = repository.findByLogin(id);
		if (initialize) {
			initializeCollections(entity);
		}
		return entity;
	}

	@Override
	public Teacher create(String name, String login, String encodedPassword) {
		return super.create(new TeacherNode(name, login, encodedPassword));
	}

}
