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
package eu.ueb.acem.dal.jpa.jaune;

import javax.inject.Inject;

import org.springframework.stereotype.Repository;

import eu.ueb.acem.dal.common.jaune.UseModeDAO;
import eu.ueb.acem.dal.jpa.AbstractDAO;
import eu.ueb.acem.dal.jpa.GenericRepository;
import eu.ueb.acem.domain.beans.jaune.UseMode;
import eu.ueb.acem.domain.beans.jpa.jaune.UseModeEntity;

/**
 * The Spring Data JPA implementation of UseModeDAO.
 * 
 * @author Grégoire Colbert
 * @since 2015-05-29
 * 
 */
@Repository("useModeDAO")
public class UseModeDAOImpl extends AbstractDAO<UseMode, UseModeEntity> implements UseModeDAO<Long> {

	/**
	 * For serialization.
	 */
	private static final long serialVersionUID = -2498568742305690088L;

	@Inject
	private UseModeRepository repository;

	@Override
	protected final GenericRepository<UseModeEntity> getRepository() {
		return repository;
	}

	@Override
	protected final void initializeCollections(UseMode entity) {
		if (entity != null) {
			entity.getResources().size();
		}
	}

	@Override
	public UseMode create(String name) {
		return super.create(new UseModeEntity(name));
	}

}
