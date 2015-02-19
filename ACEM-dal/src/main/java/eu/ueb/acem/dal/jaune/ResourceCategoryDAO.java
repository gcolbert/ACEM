/**
 *     Copyright Grégoire COLBERT 2013
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
package eu.ueb.acem.dal.jaune;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import eu.ueb.acem.dal.AbstractDAO;
import eu.ueb.acem.dal.GenericRepository;
import eu.ueb.acem.dal.jaune.neo4j.ResourceCategoryRepository;
import eu.ueb.acem.domain.beans.jaune.ResourceCategory;
import eu.ueb.acem.domain.beans.jaune.neo4j.ResourceCategoryNode;

/**
 * @author Grégoire Colbert
 * @since 2014-04-09
 * 
 */
@Repository("resourceCategoryDAO")
public class ResourceCategoryDAO extends AbstractDAO<ResourceCategory, ResourceCategoryNode> {

	/**
	 * For serialization.
	 */
	private static final long serialVersionUID = -340108444569929110L;

	@SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory.getLogger(ResourceCategoryDAO.class);

	@Inject
	private ResourceCategoryRepository repository;

	@Override
	protected final GenericRepository<ResourceCategoryNode> getRepository() {
		return repository;
	}

	@Override
	protected final void initializeCollections(ResourceCategory entity) {
		neo4jOperations.fetch(entity.getAnswers());
		neo4jOperations.fetch(entity.getPedagogicalActivities());
		neo4jOperations.fetch(entity.getResources());
	}

}
