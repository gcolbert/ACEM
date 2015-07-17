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

import eu.ueb.acem.dal.common.TimeTicker;
import eu.ueb.acem.dal.common.bleu.PedagogicalSequenceDAO;
import eu.ueb.acem.dal.neo4j.AbstractDAO;
import eu.ueb.acem.dal.neo4j.GenericRepository;
import eu.ueb.acem.domain.beans.bleu.PedagogicalSequence;
import eu.ueb.acem.domain.beans.neo4j.bleu.PedagogicalSequenceNode;

/**
 * @author Grégoire Colbert
 * @since 2015-07-15
 * 
 */
@Repository("pedagogicalSequenceDAO")
public class PedagogicalSequenceDAOImpl extends AbstractDAO<PedagogicalSequence, PedagogicalSequenceNode> implements PedagogicalSequenceDAO<Long> {

	/**
	 * For serialization.
	 */
	private static final long serialVersionUID = -8881244523104209893L;

	@Inject
	private PedagogicalSequenceRepository repository;

	@Override
	protected final GenericRepository<PedagogicalSequenceNode> getRepository() {
		return repository;
	}

	@Override
	protected final void initializeCollections(PedagogicalSequence entity) {
		if (entity != null) {
			neo4jOperations.fetch(entity.getPedagogicalScenario());
			neo4jOperations.fetch(entity.getFirstPedagogicalSession());
		}
	}

	@Override
	public PedagogicalSequence create(PedagogicalSequence entity) {
		entity.setCreationDate(TimeTicker.tick());
		return super.create(entity);
	}

	@Override
	public PedagogicalSequence create(String name) {
		PedagogicalSequence entity = new PedagogicalSequenceNode(name);
		entity.setCreationDate(TimeTicker.tick());
		return super.create(entity);
	}

	@Override
	public PedagogicalSequence create(String name, String objective) {
		PedagogicalSequence entity = new PedagogicalSequenceNode(name, objective);
		entity.setCreationDate(TimeTicker.tick());
		return super.create(entity);
	}

	@Override
	public PedagogicalSequence update(PedagogicalSequence entity) {
		entity.setModificationDate(TimeTicker.tick());
		return super.update(entity);
	}

}
