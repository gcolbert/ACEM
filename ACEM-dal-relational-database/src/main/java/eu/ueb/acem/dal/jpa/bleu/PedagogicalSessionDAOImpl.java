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
package eu.ueb.acem.dal.jpa.bleu;

import javax.inject.Inject;

import org.springframework.stereotype.Repository;

import eu.ueb.acem.dal.common.bleu.PedagogicalSessionDAO;
import eu.ueb.acem.dal.jpa.AbstractDAO;
import eu.ueb.acem.dal.jpa.GenericRepository;
import eu.ueb.acem.domain.beans.bleu.PedagogicalSession;
import eu.ueb.acem.domain.beans.bleu.jpa.PedagogicalSessionEntity;

/**
 * @author Grégoire Colbert
 * @since 2015-07-15
 */
@Repository("pedagogicalSessionDAO")
public class PedagogicalSessionDAOImpl extends AbstractDAO<PedagogicalSession, PedagogicalSessionEntity> implements PedagogicalSessionDAO<Long> {

	/**
	 * For serialization.
	 */
	private static final long serialVersionUID = 7481441503141618301L;

	@Inject
	private PedagogicalSessionRepository repository;

	@Override
	protected final GenericRepository<PedagogicalSessionEntity> getRepository() {
		return repository;
	}

	@Override
	protected final void initializeCollections(PedagogicalSession entity) {
		if (entity != null) {
			entity.getPedagogicalSequences().size();
			entity.getFirstPedagogicalActivity();
		}
	}

	@Override
	public PedagogicalSession create(String name) {
		return super.create(new PedagogicalSessionEntity(name));
	}

	@Override
	public PedagogicalSession create(String name, String objective) {
		return super.create(new PedagogicalSessionEntity(name, objective));
	}

}
