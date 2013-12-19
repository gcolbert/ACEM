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

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import eu.ueb.acem.dal.DAO;
import eu.ueb.acem.dal.bleu.neo4j.BesoinRepository;
import eu.ueb.acem.domain.beans.bleu.Besoin;
import eu.ueb.acem.domain.beans.bleu.neo4j.BesoinNode;

/**
 * @author gcolbert @since 2013-11-20
 *
 */
@Repository("besoinDAO")
public class BesoinDAO implements DAO<Besoin> {

	/**
	 * For Logging.
	 */
	private final static Logger logger = LoggerFactory.getLogger(BesoinDAO.class);

	@Autowired
	private BesoinRepository repository;

	public BesoinDAO() {
		
	}

	@Override
	public void create(Besoin besoin) {
		logger.debug("Persisting Besoin with nom '{}'", besoin.getNom());
		repository.save((BesoinNode) besoin);
	}

	@Override
	public Besoin retrieve(String nom) {
		return repository.findByPropertyValue("nom", nom);
	}

	public Set<Besoin> retrieveChildrenOf(Besoin besoin) {
		logger.info("retrieveChildrenOf({})", besoin);

		Set<BesoinNode> nodes;
		if (besoin != null) {
			logger.info("calling BesoinRepository.findChildrenOf({})",besoin.getNom());
			nodes = repository.findChildrenOf(besoin.getNom());
		}
		else {
			logger.info("calling BesoinRepository.findRoots()");
			nodes = repository.findRoots();
		}

		Set<Besoin> children = new HashSet<Besoin>();
		for (BesoinNode childNode : nodes) {
			logger.info("retrieved child {}", childNode.getNom());
			children.add(childNode);
		}
		return children;
	}

	@Override
	public Set<Besoin> retrieveAll() {
		Iterable<BesoinNode> endResults = repository.findAll();
		Set<Besoin> set = new HashSet<Besoin>();
		if (endResults.iterator() != null) {
			Iterator<BesoinNode> iterator = endResults.iterator();
			while (iterator.hasNext()) {
				set.add(iterator.next());
			}
		}
		return set;
	}

	@Override
	public Besoin update(Besoin besoin) {
		return repository.save((BesoinNode) besoin);
	}

	@Override
	public void delete(Besoin besoin) {
		repository.delete((BesoinNode) besoin);
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
