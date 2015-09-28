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
import eu.ueb.acem.domain.beans.jpa.rouge.InstitutionEntity;
import eu.ueb.acem.domain.beans.rouge.Institution;

/**
 * @author Grégoire Colbert
 * @since 2015-06-03
 * 
 */
@Repository("institutionDAO")
public class InstitutionDAO extends AbstractDAO<Institution, InstitutionEntity> implements
		OrganisationDAO<Long, Institution> {

	/**
	 * For serialization.
	 */
	private static final long serialVersionUID = 129914253180127789L;

	@Inject
	private InstitutionRepository repository;

	@Override
	protected final GenericRepository<InstitutionEntity> getRepository() {
		return repository;
	}

	@Override
	protected final void initializeCollections(Institution entity) {
		if (entity != null) {
			entity.getPossessedResources().size();
			entity.getViewedResources().size();
			entity.getUseModes().size();
			entity.getCommunities().size();
			entity.getAdministrativeDepartments().size();
			entity.getTeachingDepartments().size();
		}
	}

	@Override
	public Collection<Institution> retrieveSupportServicesForPerson(Person person) {
		Iterable<InstitutionEntity> endResults = repository.getSupportServicesForPerson(person.getId());
		Collection<Institution> collection = new HashSet<Institution>();
		if (endResults.iterator() != null) {
			Iterator<InstitutionEntity> iterator = endResults.iterator();
			while (iterator.hasNext()) {
				collection.add(iterator.next());
			}
		}
		return collection;
	}

	@Override
	public Institution create(String name, String shortname, String iconFileName) {
		return super.create(new InstitutionEntity(name, shortname, iconFileName));
	}

}
