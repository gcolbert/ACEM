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

import javax.inject.Inject;

import org.springframework.stereotype.Repository;

import eu.ueb.acem.dal.common.bleu.PedagogicalKeywordDAO;
import eu.ueb.acem.dal.neo4j.AbstractDAO;
import eu.ueb.acem.dal.neo4j.GenericRepository;
import eu.ueb.acem.domain.beans.bleu.PedagogicalKeyword;
import eu.ueb.acem.domain.beans.neo4j.bleu.PedagogicalKeywordNode;

/**
 * The Spring Data Neo4j implementation of PedagogicalKeywordDAO.
 * 
 * @author Grégoire Colbert
 * @since 2015-06-04
 * 
 */
@Repository("pedagogicalKeywordDAO")
public class PedagogicalKeywordDAOImpl extends AbstractDAO<PedagogicalKeyword, PedagogicalKeywordNode> implements PedagogicalKeywordDAO<Long> {

	/**
	 * For serialization.
	 */
	private static final long serialVersionUID = -2182870198414635416L;

	@Inject
	private PedagogicalKeywordRepository repository;

	@Override
	protected final GenericRepository<PedagogicalKeywordNode> getRepository() {
		return repository;
	}

	@Override
	protected final void initializeCollections(PedagogicalKeyword entity) {
		if (entity != null) {
			neo4jOperations.fetch(entity.getPedagogicalUnits());
		}
	}

	@Override
	public PedagogicalKeyword create(String name) {
		return super.create(new PedagogicalKeywordNode(name));
	}

}
