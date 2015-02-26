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
package eu.ueb.acem.dal.bleu;

import javax.inject.Inject;

import org.springframework.stereotype.Repository;

import eu.ueb.acem.dal.AbstractDAO;
import eu.ueb.acem.dal.GenericRepository;
import eu.ueb.acem.dal.bleu.neo4j.PedagogicalActivityRepository;
import eu.ueb.acem.domain.beans.bleu.PedagogicalActivity;
import eu.ueb.acem.domain.beans.bleu.neo4j.PedagogicalActivityNode;

/**
 * @author Grégoire Colbert
 * @since 2013-11-20
 * 
 */
@Repository("pedagogicalActivityDAO")
public class PedagogicalActivityDAO extends AbstractDAO<PedagogicalActivity, PedagogicalActivityNode> {

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
			neo4jOperations.fetch(entity.getScenarios());
		}
	}

}
