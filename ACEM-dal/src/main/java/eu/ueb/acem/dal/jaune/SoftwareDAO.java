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
package eu.ueb.acem.dal.jaune;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import eu.ueb.acem.dal.DAO;
import eu.ueb.acem.dal.jaune.neo4j.SoftwareRepository;
import eu.ueb.acem.domain.beans.jaune.Applicatif;
import eu.ueb.acem.domain.beans.jaune.neo4j.ApplicatifNode;

/**
 * @author Grégoire Colbert
 * @since 2014-03-11
 * 
 */
@Repository("softwareDAO")
public class SoftwareDAO implements DAO<Long, Applicatif> {

	@SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory.getLogger(SoftwareDAO.class);

	@Autowired
	private SoftwareRepository repository;

	public SoftwareDAO() {

	}

	@Override
	public Boolean exists(Long id) {
		//return (id != null) ? repository.exists(id) : false;
		if (id == null) {
			return false;
		}
		else {
			return repository.count(id) > 0 ? true : false;
		}
	}

	@Override
	public Applicatif create(Applicatif entity) {
		return repository.save((ApplicatifNode) entity);
	}

	@Override
	public Applicatif retrieveById(Long id) {
		return (id != null) ? repository.findOne(id) : null;
	}

	@Override
	public Collection<Applicatif> retrieveByName(String name) {
		Iterable<ApplicatifNode> softwareNodes = repository.findByName(name);
		Collection<Applicatif> softwares = new HashSet<Applicatif>();
		for (Applicatif software : softwareNodes) {
			softwares.add(software);
		}
		return softwares;
	}

	@Override
	public Collection<Applicatif> retrieveAll() {
		Iterable<ApplicatifNode> endResults = repository.findAll();
		Collection<Applicatif> collection = new HashSet<Applicatif>();
		if (endResults.iterator() != null) {
			Iterator<ApplicatifNode> iterator = endResults.iterator();
			while (iterator.hasNext()) {
				collection.add(iterator.next());
			}
		}
		return collection;
	}

	@Override
	public Applicatif update(Applicatif entity) {
		return repository.save((ApplicatifNode) entity);
	}

	@Override
	public void delete(Applicatif entity) {
		repository.delete((ApplicatifNode) entity);
	}

	@Override
	public void deleteAll() {
		repository.deleteAll();
	}

	@Override
	public Long count() {
		return repository.count();
	}

	public Collection<String> getCategories() {
		return repository.getCategories();
	}

	public Collection<Applicatif> retrieveAllWithCategory(String category) {
		Iterable<ApplicatifNode> endResults = repository.getEntitiesWithCategory(category);
		Collection<Applicatif> collection = new HashSet<Applicatif>();
		if (endResults.iterator() != null) {
			Iterator<ApplicatifNode> iterator = endResults.iterator();
			while (iterator.hasNext()) {
				collection.add(iterator.next());
			}
		}
		return collection;
	}

}
