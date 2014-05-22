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

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import eu.ueb.acem.dal.DAO;
import eu.ueb.acem.dal.jaune.neo4j.UseModeRepository;
import eu.ueb.acem.domain.beans.jaune.ModaliteUtilisation;
import eu.ueb.acem.domain.beans.jaune.neo4j.ModaliteUtilisationNode;

/**
 * @author Grégoire Colbert
 * @since 2014-03-19
 * 
 */
@Repository("useModeDAO")
public class UseModeDAO implements DAO<Long, ModaliteUtilisation> {

	@SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory.getLogger(UseModeDAO.class);

	@Inject
	private UseModeRepository repository;

	public UseModeDAO() {

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
	public ModaliteUtilisation create(ModaliteUtilisation entity) {
		return repository.save((ModaliteUtilisationNode) entity);
	}

	@Override
	public ModaliteUtilisation retrieveById(Long id) {
		return (id != null) ? repository.findOne(id) : null;
	}

	@Override
	public Collection<ModaliteUtilisation> retrieveByName(String name) {
		Iterable<ModaliteUtilisationNode> useModeNodes = repository.findByName(name);
		Collection<ModaliteUtilisation> useModes = new HashSet<ModaliteUtilisation>();
		for (ModaliteUtilisation useMode : useModeNodes) {
			useModes.add(useMode);
		}
		return useModes;
	}

	@Override
	public Collection<ModaliteUtilisation> retrieveAll() {
		Iterable<ModaliteUtilisationNode> endResults = repository.findAll();
		Collection<ModaliteUtilisation> collection = new HashSet<ModaliteUtilisation>();
		if (endResults.iterator() != null) {
			Iterator<ModaliteUtilisationNode> iterator = endResults.iterator();
			while (iterator.hasNext()) {
				collection.add(iterator.next());
			}
		}
		return collection;
	}

	@Override
	public ModaliteUtilisation update(ModaliteUtilisation entity) {
		return repository.save((ModaliteUtilisationNode) entity);
	}

	@Override
	public void delete(ModaliteUtilisation entity) {
		repository.delete((ModaliteUtilisationNode) entity);
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
