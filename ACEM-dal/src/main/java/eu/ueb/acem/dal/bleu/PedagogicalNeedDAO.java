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

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import eu.ueb.acem.dal.DAO;
import eu.ueb.acem.dal.bleu.neo4j.PedagogicalNeedRepository;
import eu.ueb.acem.domain.beans.bleu.Besoin;
import eu.ueb.acem.domain.beans.bleu.neo4j.BesoinNode;

/**
 * @author Grégoire Colbert
 * @since 2013-11-20
 * 
 */
@Repository("pedagogicalNeedDAO")
public class PedagogicalNeedDAO implements DAO<Long, Besoin> {

	@SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory.getLogger(PedagogicalNeedDAO.class);

	@Inject
	private PedagogicalNeedRepository repository;

	public PedagogicalNeedDAO() {

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
	public Besoin create(Besoin need) {
		return repository.save((BesoinNode) need);
	}

	@Override
	public Besoin retrieveById(Long id) {
		return (id != null) ? repository.findOne(id) : null;
	}

	@Override
	public Collection<Besoin> retrieveByName(String name) {
		Iterable<BesoinNode> needNodes = repository.findByName(name);
		Collection<Besoin> needs = new HashSet<Besoin>();
		for (Besoin need : needNodes) {
			needs.add(need);
		}
		return needs;
	}

	@Override
	public Collection<Besoin> retrieveAll() {
		Iterable<BesoinNode> endResults = repository.findAll();
		Collection<Besoin> collection = new HashSet<Besoin>();
		if (endResults.iterator() != null) {
			Iterator<BesoinNode> iterator = endResults.iterator();
			while (iterator.hasNext()) {
				collection.add(iterator.next());
			}
		}
		return collection;
	}

	@Override
	public Besoin update(Besoin need) {
		return repository.save((BesoinNode) need);
	}

	@Override
	public void delete(Besoin need) {
		repository.delete((BesoinNode) need);
	}

	@Override
	public void deleteAll() {
		repository.deleteAll();
	}

	@Override
	public Long count() {
		return repository.count();
	}

	public Set<Besoin> retrieveNeedsAtRoot() {
		Set<BesoinNode> nodes = repository.findRoots();
		Set<Besoin> needs = new HashSet<Besoin>();
		for (BesoinNode need : nodes) {
			needs.add(need);
		}
		return needs;
	}

}
