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
package eu.ueb.acem.dal.rouge;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import eu.ueb.acem.dal.DAO;
import eu.ueb.acem.dal.rouge.neo4j.TeachingDepartmentRepository;
import eu.ueb.acem.domain.beans.rouge.Composante;
import eu.ueb.acem.domain.beans.rouge.neo4j.ComposanteNode;

/**
 * @author Grégoire Colbert @since 2014-02-07
 * 
 */
@Repository("teachingDepartmentDAO")
public class TeachingDepartmentDAO implements DAO<Long, Composante> {

	@SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory.getLogger(TeachingDepartmentDAO.class);

	@Autowired
	private TeachingDepartmentRepository repository;

	public TeachingDepartmentDAO() {

	}

	@Override
	public Boolean exists(Long id) {
		return (id != null) ? repository.exists(id) : false;
	}

	@Override
	public Composante create(Composante entity) {
		return repository.save((ComposanteNode) entity);
	}

	@Override
	public Composante retrieveById(Long id) {
		return (id != null) ? repository.findOne(id) : null;
	}

	@Override
	public Collection<Composante> retrieveByName(String name) {
		Iterable<ComposanteNode> nodes = repository.findByName(name);
		Collection<Composante> entities = new HashSet<Composante>();
		for (ComposanteNode node : nodes) {
			entities.add(node);
		}
		return entities;
	}

	@Override
	public Collection<Composante> retrieveAll() {
		Iterable<ComposanteNode> endResults = repository.findAll();
		Collection<Composante> collection = new HashSet<Composante>();
		if (endResults.iterator() != null) {
			Iterator<ComposanteNode> iterator = endResults.iterator();
			while (iterator.hasNext()) {
				collection.add(iterator.next());
			}
		}
		return collection;
	}

	@Override
	public Composante update(Composante entity) {
		return repository.save((ComposanteNode) entity);
	}

	@Override
	public void delete(Composante entity) {
		repository.delete((ComposanteNode) entity);
	}

	@Override
	public void deleteAll() {
		repository.deleteAll();
	}

	@Override
	public Long count() {
		return repository.count();
	}

}
