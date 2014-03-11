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
package eu.ueb.acem.dal.bleu;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import eu.ueb.acem.dal.DAO;
import eu.ueb.acem.dal.bleu.neo4j.PedagogicalActivityRepository;
import eu.ueb.acem.domain.beans.bleu.ActivitePedagogique;
import eu.ueb.acem.domain.beans.bleu.neo4j.ActivitePedagogiqueNode;

/**
 * @author Grégoire Colbert @since 2013-11-20
 * 
 */
@Repository("pedagogicalActivityDAO")
public class PedagogicalActivityDAO implements DAO<Long, ActivitePedagogique> {

	/**
	 * For Logging.
	 */
	@SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory.getLogger(PedagogicalActivityDAO.class);

	@Autowired
	private PedagogicalActivityRepository repository;

	public PedagogicalActivityDAO() {

	}

	@Override
	public Boolean exists(Long id) {
		return (id != null) ? repository.exists(id) : false;
	}

	@Override
	public ActivitePedagogique create(ActivitePedagogique entity) {
		return repository.save((ActivitePedagogiqueNode) entity);
	}

	@Override
	public ActivitePedagogique retrieveById(Long id) {
		return (id != null) ? repository.findOne(id) : null;
	}

	@Override
	public Collection<ActivitePedagogique> retrieveByName(String name) {
		Iterable<ActivitePedagogiqueNode> nodes = repository.findByName(name);
		Collection<ActivitePedagogique> entities = new HashSet<ActivitePedagogique>();
		for (ActivitePedagogiqueNode node : nodes) {
			entities.add(node);
		}
		return entities;
	}

	@Override
	public Set<ActivitePedagogique> retrieveAll() {
		Iterable<ActivitePedagogiqueNode> endResults = repository.findAll();
		Set<ActivitePedagogique> collection = new HashSet<ActivitePedagogique>();
		if (endResults.iterator() != null) {
			Iterator<ActivitePedagogiqueNode> iterator = endResults.iterator();
			while (iterator.hasNext()) {
				collection.add(iterator.next());
			}
		}
		return collection;
	}

	@Override
	public ActivitePedagogique update(ActivitePedagogique entity) {
		return repository.save((ActivitePedagogiqueNode) entity);
	}

	@Override
	public void delete(ActivitePedagogique entity) {
		repository.delete((ActivitePedagogiqueNode) entity);
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
