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
package eu.ueb.acem.dal.rouge;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import eu.ueb.acem.dal.AbstractDAO;
import eu.ueb.acem.dal.GenericRepository;
import eu.ueb.acem.dal.rouge.neo4j.AdministrativeDepartmentRepository;
import eu.ueb.acem.domain.beans.rouge.AdministrativeDepartment;
import eu.ueb.acem.domain.beans.rouge.neo4j.AdministrativeDepartmentNode;

/**
 * @author Grégoire Colbert
 * @since 2014-02-07
 * 
 */
@Repository("administrativeDepartmentDAO")
public class AdministrativeDepartmentDAO extends AbstractDAO<AdministrativeDepartment, AdministrativeDepartmentNode> {

	/**
	 * For serialization.
	 */
	private static final long serialVersionUID = 515628413371430770L;

	@SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory.getLogger(AdministrativeDepartmentDAO.class);

	@Inject
	private AdministrativeDepartmentRepository repository;

	@Override
	protected final GenericRepository<AdministrativeDepartmentNode> getRepository() {
		return repository;
	}

	@Override
	protected final void initializeCollections(AdministrativeDepartment entity) {
		neo4jOperations.fetch(entity.getPossessedResources());
		neo4jOperations.fetch(entity.getViewedResources());
		neo4jOperations.fetch(entity.getUseModes());
		neo4jOperations.fetch(entity.getInstitutions());
	}

}
