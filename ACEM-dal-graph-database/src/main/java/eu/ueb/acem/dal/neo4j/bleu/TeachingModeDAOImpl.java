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

import eu.ueb.acem.dal.common.bleu.TeachingModeDAO;
import eu.ueb.acem.dal.neo4j.AbstractDAO;
import eu.ueb.acem.dal.neo4j.GenericRepository;
import eu.ueb.acem.domain.beans.bleu.TeachingMode;
import eu.ueb.acem.domain.beans.neo4j.bleu.TeachingModeNode;

/**
 * @author Grégoire Colbert
 * @since 2015-06-15
 * 
 */
@Repository("teachingModeDAO")
public class TeachingModeDAOImpl extends AbstractDAO<TeachingMode, TeachingModeNode> implements TeachingModeDAO<Long> {

	/**
	 * For serialization.
	 */
	private static final long serialVersionUID = 3623359380433232847L;

	@Inject
	private TeachingModeRepository repository;

	@Override
	protected final GenericRepository<TeachingModeNode> getRepository() {
		return repository;
	}

	@Override
	protected final void initializeCollections(TeachingMode entity) {
		if (entity != null) {
		}
	}

	@Override
	public TeachingMode create(String name, String description) {
		return super.create(new TeachingModeNode(name, description));
	}

}
