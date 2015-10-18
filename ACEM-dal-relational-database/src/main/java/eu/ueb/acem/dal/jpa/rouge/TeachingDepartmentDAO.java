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
package eu.ueb.acem.dal.jpa.rouge;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;

import javax.inject.Inject;

import org.springframework.stereotype.Repository;

import eu.ueb.acem.dal.common.rouge.OrganisationDAO;
import eu.ueb.acem.dal.jpa.AbstractDAO;
import eu.ueb.acem.dal.jpa.GenericRepository;
import eu.ueb.acem.domain.beans.gris.Person;
import eu.ueb.acem.domain.beans.jpa.rouge.TeachingDepartmentEntity;
import eu.ueb.acem.domain.beans.rouge.TeachingDepartment;

/**
 * The Spring Data JPA implementation of OrganisationDAO for TeachingDepartment
 * domain beans.
 * 
 * @author Grégoire Colbert
 * @since 2015-06-03
 * 
 */
@Repository("teachingDepartmentDAO")
public class TeachingDepartmentDAO extends AbstractDAO<TeachingDepartment, TeachingDepartmentEntity> implements
		OrganisationDAO<Long, TeachingDepartment> {

	/**
	 * For serialization.
	 */
	private static final long serialVersionUID = 7917729549009233635L;

	@Inject
	private TeachingDepartmentRepository repository;

	@Override
	protected final GenericRepository<TeachingDepartmentEntity> getRepository() {
		return repository;
	}

	@Override
	protected final void initializeCollections(TeachingDepartment entity) {
		if (entity != null) {
			entity.getPossessedResources().size();
			entity.getViewedResources().size();
			entity.getUseModes().size();
			entity.getInstitutions().size();
		}
	}

	@Override
	public Collection<TeachingDepartment> retrieveSupportServicesForPerson(Person person) {
		Iterable<TeachingDepartmentEntity> endResults = repository.getSupportServicesForPerson(person.getId());
		Collection<TeachingDepartment> collection = new HashSet<TeachingDepartment>();
		if (endResults.iterator() != null) {
			Iterator<TeachingDepartmentEntity> iterator = endResults.iterator();
			while (iterator.hasNext()) {
				collection.add(iterator.next());
			}
		}
		return collection;
	}

	@Override
	public TeachingDepartment create(String name, String shortname, String iconFileName) {
		return super.create(new TeachingDepartmentEntity(name, shortname, iconFileName));
	}

}
