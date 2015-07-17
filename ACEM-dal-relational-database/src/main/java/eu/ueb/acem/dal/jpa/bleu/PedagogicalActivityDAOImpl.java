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

import eu.ueb.acem.dal.common.bleu.PedagogicalActivityDAO;
import eu.ueb.acem.dal.jpa.AbstractDAO;
import eu.ueb.acem.dal.jpa.GenericRepository;
import eu.ueb.acem.domain.beans.bleu.PedagogicalActivity;
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
			entity.getPedagogicalSession();
		}
	}

	@Override
	public PedagogicalActivity create(String name) {
		return super.create(new PedagogicalActivityEntity(name));
	}

	@Override
	public PedagogicalActivity create(String name, String objective) {
		return super.create(new PedagogicalActivityEntity(name, objective));
	}

}
