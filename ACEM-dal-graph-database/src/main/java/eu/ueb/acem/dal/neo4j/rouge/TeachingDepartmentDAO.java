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
package eu.ueb.acem.dal.neo4j.rouge;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;

import javax.inject.Inject;

import org.springframework.stereotype.Repository;

import eu.ueb.acem.dal.common.rouge.OrganisationDAO;
import eu.ueb.acem.dal.neo4j.AbstractDAO;
import eu.ueb.acem.dal.neo4j.GenericRepository;
import eu.ueb.acem.domain.beans.gris.Person;
import eu.ueb.acem.domain.beans.neo4j.rouge.TeachingDepartmentNode;
import eu.ueb.acem.domain.beans.rouge.TeachingDepartment;

/**
 * @author Grégoire Colbert
 * @since 2014-02-07
 * 
 */
@Repository("teachingDepartmentDAO")
public class TeachingDepartmentDAO extends AbstractDAO<TeachingDepartment, TeachingDepartmentNode> implements OrganisationDAO<Long, TeachingDepartment> {

	/**
	 * For serialization.
	 */
	private static final long serialVersionUID = 1601784002431717278L;

	@Inject
	private TeachingDepartmentRepository repository;

	@Override
	protected final GenericRepository<TeachingDepartmentNode> getRepository() {
		return repository;
	}

	@Override
	protected final void initializeCollections(TeachingDepartment entity) {
		if (entity != null) {
			neo4jOperations.fetch(entity.getPossessedResources());
			neo4jOperations.fetch(entity.getViewedResources());
			neo4jOperations.fetch(entity.getUseModes());
			neo4jOperations.fetch(entity.getInstitutions());
		}
	}

	@Override
	public Collection<TeachingDepartment> retrieveSupportServicesForPerson(Person person) {
		Iterable<TeachingDepartmentNode> endResults = repository.getSupportServicesForPerson(person.getId());
		Collection<TeachingDepartment> collection = new HashSet<TeachingDepartment>();
		if (endResults.iterator() != null) {
			Iterator<TeachingDepartmentNode> iterator = endResults.iterator();
			while (iterator.hasNext()) {
				collection.add(iterator.next());
			}
		}
		return collection;
	}

	@Override
	public TeachingDepartment create(String name, String shortname, String iconFileName) {
		return super.create(new TeachingDepartmentNode(name, shortname, iconFileName));
	}

}
