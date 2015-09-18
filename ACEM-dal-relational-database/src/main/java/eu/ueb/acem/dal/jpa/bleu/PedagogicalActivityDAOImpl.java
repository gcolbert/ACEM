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

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;

import javax.inject.Inject;

import org.springframework.stereotype.Repository;

import eu.ueb.acem.dal.common.TimeTicker;
import eu.ueb.acem.dal.common.bleu.PedagogicalActivityDAO;
import eu.ueb.acem.dal.jpa.AbstractDAO;
import eu.ueb.acem.dal.jpa.GenericRepository;
import eu.ueb.acem.domain.beans.bleu.PedagogicalActivity;
import eu.ueb.acem.domain.beans.bleu.PedagogicalSession;
import eu.ueb.acem.domain.beans.bleu.jpa.PedagogicalActivityEntity;

/**
 * @author Grégoire Colbert
 * @since 2015-05-29
 */
@Repository("pedagogicalActivityDAO")
public class PedagogicalActivityDAOImpl extends AbstractDAO<PedagogicalActivity, PedagogicalActivityEntity> implements PedagogicalActivityDAO<Long> {

	/**
	 * For serialization.
	 */
	private static final long serialVersionUID = -7995216423182546851L;

	@Inject
	private PedagogicalActivityRepository repository;

	@Override
	protected final GenericRepository<PedagogicalActivityEntity> getRepository() {
		return repository;
	}

	@Override
	protected final void initializeCollections(PedagogicalActivity entity) {
		if (entity != null) {
			entity.getResourceCategories().size();
		}
	}

	@Override
	public PedagogicalActivity create(String name) {
		PedagogicalActivity entity = new PedagogicalActivityEntity(name);
		entity.setCreationDate(TimeTicker.tick());
		return super.create(entity);
	}

	@Override
	public PedagogicalActivity create(String name, String objective) {
		PedagogicalActivity entity = new PedagogicalActivityEntity(name, objective);
		entity.setCreationDate(TimeTicker.tick());
		return super.create(entity);
	}

	@Override
	public PedagogicalActivity update(PedagogicalActivity entity) {
		entity.setModificationDate(TimeTicker.tick());
		return super.update(entity);
	}

	@Override
	public Collection<PedagogicalActivity> retrieveFirstActivitiesOfSession(PedagogicalSession session) {
		Iterable<PedagogicalActivityEntity> endResults = repository.findFirstActivitiesOfSession(session.getId());
		Collection<PedagogicalActivity> collection = new HashSet<PedagogicalActivity>();
		if (endResults.iterator() != null) {
			Iterator<PedagogicalActivityEntity> iterator = endResults.iterator();
			while (iterator.hasNext()) {
				PedagogicalActivity entity = iterator.next();
				initializeCollections(entity);
				collection.add(entity);
			}
		}
		return collection;
	}

}
