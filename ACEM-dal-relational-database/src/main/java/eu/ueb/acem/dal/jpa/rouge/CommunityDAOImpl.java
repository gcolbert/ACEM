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

import eu.ueb.acem.dal.common.rouge.CommunityDAO;
import eu.ueb.acem.dal.jpa.AbstractDAO;
import eu.ueb.acem.dal.jpa.GenericRepository;
import eu.ueb.acem.domain.beans.gris.Person;
import eu.ueb.acem.domain.beans.jpa.rouge.CommunityEntity;
import eu.ueb.acem.domain.beans.rouge.Community;

/**
 * The Spring Data JPA implementation of OrganisationDAO for Community domain
 * beans.
 * 
 * @author Grégoire Colbert
 * @since 2015-06-03
 * 
 */
@Repository("communityDAO")
public class CommunityDAOImpl extends AbstractDAO<Community, CommunityEntity> implements CommunityDAO<Long> {

	/**
	 * For serialization.
	 */
	private static final long serialVersionUID = -6693442534736155050L;

	@Inject
	private CommunityRepository repository;

	@Override
	protected final GenericRepository<CommunityEntity> getRepository() {
		return repository;
	}

	@Override
	protected final void initializeCollections(Community entity) {
		if (entity != null) {
			entity.getPossessedResources().size();
			entity.getViewedResources().size();
			entity.getUseModes().size();
			entity.getInstitutions().size();
		}
	}

	@Override
	public Collection<Community> retrieveSupportServicesForPerson(Person person) {
		Iterable<CommunityEntity> endResults = repository.getSupportServicesForPerson(person.getId());
		Collection<Community> collection = new HashSet<Community>();
		if (endResults.iterator() != null) {
			Iterator<CommunityEntity> iterator = endResults.iterator();
			while (iterator.hasNext()) {
				collection.add(iterator.next());
			}
		}
		return collection;
	}

	@Override
	public Community create(String name, String shortname, String iconFileName) {
		return super.create(new CommunityEntity(name, shortname, iconFileName));
	}


	@Override
	public Community retrieveBySupannEtablissement(String supannEtablissement) {
		return repository.findBySupannEtablissement(supannEtablissement);
	}

}
