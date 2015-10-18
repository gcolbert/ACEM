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

import eu.ueb.acem.dal.common.bleu.TeachingModeDAO;
import eu.ueb.acem.dal.jpa.AbstractDAO;
import eu.ueb.acem.dal.jpa.GenericRepository;
import eu.ueb.acem.domain.beans.bleu.TeachingMode;
import eu.ueb.acem.domain.beans.jpa.bleu.TeachingModeEntity;

/**
 * The Spring Data JPA implementation of TeachingModeDAO.
 * 
 * @author Grégoire Colbert
 * @since 2015-06-15
 * 
 */
@Repository("teachingModeDAO")
public class TeachingModeDAOImpl extends AbstractDAO<TeachingMode, TeachingModeEntity> implements TeachingModeDAO<Long> {

	/**
	 * For serialization.
	 */
	private static final long serialVersionUID = 8439497515824945264L;

	@Inject
	private TeachingModeRepository repository;

	@Override
	protected final GenericRepository<TeachingModeEntity> getRepository() {
		return repository;
	}

	@Override
	protected final void initializeCollections(TeachingMode entity) {
		if (entity != null) {
		}
	}

	@Override
	public TeachingMode create(String name, String description) {
		return super.create(new TeachingModeEntity(name, description));
	}

}
