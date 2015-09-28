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
import eu.ueb.acem.domain.beans.jpa.rouge.AdministrativeDepartmentEntity;
import eu.ueb.acem.domain.beans.rouge.AdministrativeDepartment;

/**
 * @author Grégoire Colbert
 * @since 2015-06-03
 * 
 */
@Repository("administrativeDepartmentDAO")
public class AdministrativeDepartmentDAO extends AbstractDAO<AdministrativeDepartment, AdministrativeDepartmentEntity>
		implements OrganisationDAO<Long, AdministrativeDepartment> {

	/**
	 * For serialization.
	 */
	private static final long serialVersionUID = -3156159594445340421L;

	@Inject
	private AdministrativeDepartmentRepository repository;

	@Override
	protected final GenericRepository<AdministrativeDepartmentEntity> getRepository() {
		return repository;
	}

	@Override
	protected final void initializeCollections(AdministrativeDepartment entity) {
		if (entity != null) {
			entity.getPossessedResources().size();
			entity.getViewedResources().size();
			entity.getUseModes().size();
			entity.getInstitutions().size();
		}
	}

	@Override
	public Collection<AdministrativeDepartment> retrieveSupportServicesForPerson(Person person) {
		Iterable<AdministrativeDepartmentEntity> endResults = repository.getSupportServicesForPerson(person.getId());
		Collection<AdministrativeDepartment> collection = new HashSet<AdministrativeDepartment>();
		if (endResults.iterator() != null) {
			Iterator<AdministrativeDepartmentEntity> iterator = endResults.iterator();
			while (iterator.hasNext()) {
				collection.add(iterator.next());
			}
		}
		return collection;
	}

	@Override
	public AdministrativeDepartment create(String name, String shortname, String iconFileName) {
		return super.create(new AdministrativeDepartmentEntity(name, shortname, iconFileName));
	}

}
