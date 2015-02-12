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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import eu.ueb.acem.dal.AbstractDAO;
import eu.ueb.acem.dal.DAO;
import eu.ueb.acem.dal.GenericRepository;
import eu.ueb.acem.dal.bleu.neo4j.PedagogicalAnswerRepository;
import eu.ueb.acem.domain.beans.bleu.PedagogicalAnswer;
import eu.ueb.acem.domain.beans.bleu.neo4j.PedagogicalAnswerNode;

/**
 * @author Grégoire Colbert
 * @since 2013-11-26
 * 
 */
@Repository("pedagogicalAnswerDAO")
public class PedagogicalAnswerDAO extends AbstractDAO<PedagogicalAnswer, PedagogicalAnswerNode> implements
		DAO<Long, PedagogicalAnswer> {

	/**
	 * For serialization.
	 */
	private static final long serialVersionUID = -4117462676183855035L;

	/**
	 * For Logging.
	 */
	@SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory.getLogger(PedagogicalAnswerDAO.class);

	@Inject
	private PedagogicalAnswerRepository repository;

	@Override
	protected final GenericRepository<PedagogicalAnswerNode> getRepository() {
		return repository;
	}

	@Override
	protected final void initializeCollections(PedagogicalAnswer entity) {
		neo4jOperations.fetch(entity.getResourceCategories());
		neo4jOperations.fetch(entity.getNeeds());
	}

}
