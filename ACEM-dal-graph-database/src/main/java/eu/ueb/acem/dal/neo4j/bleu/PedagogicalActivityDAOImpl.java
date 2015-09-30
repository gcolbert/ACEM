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

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;

import javax.inject.Inject;

import org.springframework.stereotype.Repository;

import eu.ueb.acem.dal.common.bleu.PedagogicalActivityDAO;
import eu.ueb.acem.dal.neo4j.AbstractDAO;
import eu.ueb.acem.dal.neo4j.GenericRepository;
import eu.ueb.acem.domain.beans.bleu.PedagogicalActivity;
import eu.ueb.acem.domain.beans.bleu.PedagogicalSession;
import eu.ueb.acem.domain.beans.neo4j.bleu.PedagogicalActivityNode;

/**
 * The Spring Data Neo4j implementation of PedagogicalActivityDAO.
 * 
 * @author Grégoire Colbert
 * @since 2013-11-20
 * 
 */
@Repository("pedagogicalActivityDAO")
public class PedagogicalActivityDAOImpl extends AbstractDAO<PedagogicalActivity, PedagogicalActivityNode> implements
		PedagogicalActivityDAO<Long> {

	/**
	 * For serialization.
	 */
	private static final long serialVersionUID = -8533892314452651184L;

	@Inject
	private PedagogicalActivityRepository repository;

	@Override
	protected final GenericRepository<PedagogicalActivityNode> getRepository() {
		return repository;
	}

	@Override
	protected final void initializeCollections(PedagogicalActivity entity) {
		if (entity != null) {
			neo4jOperations.fetch(entity.getResourceCategories());
		}
	}

	@Override
	public PedagogicalActivity create(String name) {
		return super.create(new PedagogicalActivityNode(name));
	}

	@Override
	public PedagogicalActivity create(String name, String objective) {
		return super.create(new PedagogicalActivityNode(name, objective));
	}

	@Override
	public Collection<PedagogicalActivity> retrieveFirstActivitiesOfSession(PedagogicalSession session) {
		Iterable<PedagogicalActivityNode> endResults = repository.findFirstActivitiesOfSession(session.getId());
		Collection<PedagogicalActivity> collection = new HashSet<PedagogicalActivity>();
		if (endResults.iterator() != null) {
			Iterator<PedagogicalActivityNode> iterator = endResults.iterator();
			while (iterator.hasNext()) {
				PedagogicalActivity entity = iterator.next();
				initializeCollections(entity);
				collection.add(entity);
			}
		}
		return collection;
	}

}
