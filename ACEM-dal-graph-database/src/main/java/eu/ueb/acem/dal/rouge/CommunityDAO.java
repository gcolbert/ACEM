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
package eu.ueb.acem.dal.rouge;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;

import javax.inject.Inject;

import org.springframework.stereotype.Repository;

import eu.ueb.acem.dal.AbstractDAO;
import eu.ueb.acem.dal.GenericRepository;
import eu.ueb.acem.dal.rouge.neo4j.CommunityRepository;
import eu.ueb.acem.domain.beans.gris.Person;
import eu.ueb.acem.domain.beans.rouge.Community;
import eu.ueb.acem.domain.beans.rouge.neo4j.CommunityNode;

/**
 * @author Grégoire Colbert
 * @since 2014-02-07
 * 
 */
@Repository("communityDAO")
public class CommunityDAO extends AbstractDAO<Community, CommunityNode> implements OrganisationDAO<Long, Community> {

	/**
	 * FOr serialization.
	 */
	private static final long serialVersionUID = -6005681827386719691L;

	@Inject
	private CommunityRepository repository;

	@Override
	protected final GenericRepository<CommunityNode> getRepository() {
		return repository;
	}

	@Override
	protected final void initializeCollections(Community entity) {
		if (entity != null) {
			neo4jOperations.fetch(entity.getPossessedResources());
			neo4jOperations.fetch(entity.getViewedResources());
			neo4jOperations.fetch(entity.getUseModes());
			neo4jOperations.fetch(entity.getInstitutions());
		}
	}

	@Override
	public Collection<Community> retrieveSupportServicesForPerson(Person person) {
		Iterable<CommunityNode> endResults = repository.getSupportServicesForPerson(person.getId());
		Collection<Community> collection = new HashSet<Community>();
		if (endResults.iterator() != null) {
			Iterator<CommunityNode> iterator = endResults.iterator();
			while (iterator.hasNext()) {
				collection.add(iterator.next());
			}
		}
		return collection;
	}

	@Override
	public Community create(String name, String shortname, String iconFileName) {
		return super.create(new CommunityNode(name, shortname, iconFileName));
	}

}
