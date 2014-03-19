/*******************************************************************************
 * Copyright (c) 2013 gcolbert.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v3.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/gpl.html
 * 
 * Contributors:
 *     gcolbert - initial API and implementation
 ******************************************************************************/
package eu.ueb.acem.dal.gris.neo4j;

import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.repository.query.Param;

import eu.ueb.acem.dal.GenericRepository;
import eu.ueb.acem.domain.beans.gris.neo4j.EnseignantNode;

/**
 * @author Grégoire Colbert
 * @since 2013-11-20
 * 
 */
public interface TeacherRepository extends GenericRepository<EnseignantNode> {

	@Query(value = "MATCH (n:Teacher) WHERE id(n)=({id}) RETURN count(n)")
	Long count(@Param("id") Long id);
	
	@Query(value = "match (n:Teacher) where n.login=({login}) return n")
	EnseignantNode findByLogin(@Param("login") String login);

}
