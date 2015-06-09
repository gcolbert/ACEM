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

import eu.ueb.acem.dal.common.jaune.ResourceCategoryDAO;
import eu.ueb.acem.dal.jpa.AbstractDAO;
import eu.ueb.acem.dal.jpa.GenericRepository;
import eu.ueb.acem.domain.beans.jaune.ResourceCategory;
import eu.ueb.acem.domain.beans.jaune.jpa.ResourceCategoryEntity;

/**
 * @author Grégoire Colbert
 * @since 2015-05-29
 * 
 */
@Repository("resourceCategoryDAO")
public class ResourceCategoryDAOImpl extends AbstractDAO<ResourceCategory, ResourceCategoryEntity> implements ResourceCategoryDAO<Long> {

	/**
	 * For serialization.
	 */
	private static final long serialVersionUID = -713525304026431284L;

	@Inject
	private ResourceCategoryRepository repository;

	@Override
	protected final GenericRepository<ResourceCategoryEntity> getRepository() {
		return repository;
	}

	@Override
	protected final void initializeCollections(ResourceCategory entity) {
		if (entity != null) {
			entity.getAnswers().size();
			entity.getPedagogicalActivities().size();
			entity.getResources().size();
		}
	}

	@Override
	public ResourceCategory create(String name, String description, String iconFileName) {
		return super.create(new ResourceCategoryEntity(name, description, iconFileName));
	}
	
}
