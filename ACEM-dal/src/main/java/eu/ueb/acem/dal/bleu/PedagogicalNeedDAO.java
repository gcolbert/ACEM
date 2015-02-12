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
import java.util.Set;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import eu.ueb.acem.dal.AbstractDAO;
import eu.ueb.acem.dal.DAO;
import eu.ueb.acem.dal.GenericRepository;
import eu.ueb.acem.dal.bleu.neo4j.PedagogicalNeedRepository;
import eu.ueb.acem.domain.beans.bleu.PedagogicalNeed;
import eu.ueb.acem.domain.beans.bleu.neo4j.PedagogicalNeedNode;

/**
 * @author Grégoire Colbert
 * @since 2013-11-20
 * 
 */
@Repository("pedagogicalNeedDAO")
public class PedagogicalNeedDAO extends AbstractDAO<PedagogicalNeed, PedagogicalNeedNode> implements DAO<Long, PedagogicalNeed> {

	/**
	 * For serialization.
	 */
	private static final long serialVersionUID = -2890608278433660504L;

	@SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory.getLogger(PedagogicalNeedDAO.class);

	@Inject
	private PedagogicalNeedRepository repository;

	@Override
	protected final GenericRepository<PedagogicalNeedNode> getRepository() {
		return repository;
	}

	@Override
	public void initializeCollections(PedagogicalNeed entity) {
		neo4jOperations.fetch(entity.getChildren());
		neo4jOperations.fetch(entity.getAnswers());
		neo4jOperations.fetch(entity.getParents());
	}

	public Set<PedagogicalNeed> retrieveNeedsAtRoot() {
		Set<PedagogicalNeedNode> nodes = repository.findRoots();
		Set<PedagogicalNeed> needs = new HashSet<PedagogicalNeed>();
		for (PedagogicalNeedNode need : nodes) {
			initializeCollections(need);
			needs.add(need);
		}
		return needs;
	}

}
