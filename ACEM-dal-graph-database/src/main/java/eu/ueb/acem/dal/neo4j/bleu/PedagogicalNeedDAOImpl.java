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
package eu.ueb.acem.dal.neo4j.bleu;

import java.util.HashSet;
import java.util.Set;

import javax.inject.Inject;

import org.springframework.stereotype.Repository;

import eu.ueb.acem.dal.common.bleu.PedagogicalNeedDAO;
import eu.ueb.acem.dal.neo4j.AbstractDAO;
import eu.ueb.acem.dal.neo4j.GenericRepository;
import eu.ueb.acem.domain.beans.bleu.PedagogicalNeed;
import eu.ueb.acem.domain.beans.neo4j.bleu.PedagogicalNeedNode;

/**
 * The Spring Data Neo4j implementation of PedagogicalNeedDAO.
 * 
 * @author Grégoire Colbert
 * @since 2013-11-20
 * 
 */
@Repository("pedagogicalNeedDAO")
public class PedagogicalNeedDAOImpl extends AbstractDAO<PedagogicalNeed, PedagogicalNeedNode> implements PedagogicalNeedDAO<Long> {

	/**
	 * For serialization.
	 */
	private static final long serialVersionUID = -2890608278433660504L;

	@Inject
	private PedagogicalNeedRepository repository;

	@Override
	protected final GenericRepository<PedagogicalNeedNode> getRepository() {
		return repository;
	}

	@Override
	protected final void initializeCollections(PedagogicalNeed entity) {
		if (entity != null) {
			neo4jOperations.fetch(entity.getChildren());
			neo4jOperations.fetch(entity.getAnswers());
			neo4jOperations.fetch(entity.getParents());
		}
	}

	@Override
	public Set<PedagogicalNeed> retrieveNeedsAtRoot() {
		Set<PedagogicalNeedNode> nodes = repository.findRoots();
		Set<PedagogicalNeed> needs = new HashSet<PedagogicalNeed>();
		for (PedagogicalNeedNode need : nodes) {
			initializeCollections(need);
			needs.add(need);
		}
		return needs;
	}

	@Override
	public PedagogicalNeed create(String name) {
		return super.create(new PedagogicalNeedNode(name));
	}

	@Override
	public PedagogicalNeed create(String name, String description) {
		return super.create(new PedagogicalNeedNode(name, description));
	}

}
