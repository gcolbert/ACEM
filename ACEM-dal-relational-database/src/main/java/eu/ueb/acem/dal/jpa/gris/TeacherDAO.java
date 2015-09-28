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
package eu.ueb.acem.dal.jpa.gris;

import javax.inject.Inject;

import org.springframework.stereotype.Repository;

import eu.ueb.acem.dal.common.gris.PersonDAO;
import eu.ueb.acem.dal.jpa.AbstractDAO;
import eu.ueb.acem.dal.jpa.GenericRepository;
import eu.ueb.acem.domain.beans.gris.Teacher;
import eu.ueb.acem.domain.beans.jpa.gris.TeacherEntity;

/**
 * @author Grégoire Colbert
 * @since 2015-05-29
 * 
 */
@Repository("teacherDAO")
public class TeacherDAO extends AbstractDAO<Teacher, TeacherEntity> implements PersonDAO<Long, Teacher> {

	/**
	 * For serialization
	 */
	private static final long serialVersionUID = 1065914889359157896L;

	@Inject
	private TeacherRepository repository;

	@Override
	protected final GenericRepository<TeacherEntity> getRepository() {
		return repository;
	}

	@Override
	protected final void initializeCollections(Teacher entity) {
		if (entity != null) {
			entity.getFavoriteToolCategories().size();
			entity.getPedagogicalScenarios().size();
			entity.getTeachingUnits().size();
			entity.getWorksForOrganisations().size();
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
		return super.create(new TeacherEntity(name, login, encodedPassword));
	}

}
