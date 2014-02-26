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
import eu.ueb.acem.dal.rouge.neo4j.CommunauteRepository;
import eu.ueb.acem.domain.beans.rouge.Communaute;
import eu.ueb.acem.domain.beans.rouge.neo4j.CommunauteNode;

/**
 * @author Grégoire Colbert @since 2014-02-07
 * 
 */
@Repository("communityDAO")
public class CommunauteDAO implements DAO<Long, Communaute> {

	@SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory.getLogger(CommunauteDAO.class);

	@Autowired
	private CommunauteRepository repository;

	public CommunauteDAO() {

	}

	@Override
	public Boolean exists(Long id) {
		return (id != null) ? repository.exists(id) : false;
	}

	@Override
	public Communaute create(Communaute entity) {
		return repository.save((CommunauteNode) entity);
	}

	@Override
	public Communaute retrieveById(Long id) {
		return (id != null) ? repository.findOne(id) : null;
	}

	@Override
	public Communaute retrieveByName(String name) {
		return repository.findByPropertyValue("name", name);
	}

	@Override
	public Collection<Communaute> retrieveAll() {
		Iterable<CommunauteNode> endResults = repository.findAll();
		Collection<Communaute> collection = new HashSet<Communaute>();
		if (endResults.iterator() != null) {
			Iterator<CommunauteNode> iterator = endResults.iterator();
			while (iterator.hasNext()) {
				collection.add(iterator.next());
			}
		}
		return collection;
	}

	@Override
	public Communaute update(Communaute entity) {
		return repository.save((CommunauteNode) entity);
	}

	@Override
	public void delete(Communaute entity) {
		repository.delete((CommunauteNode) entity);
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
