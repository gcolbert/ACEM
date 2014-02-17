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
package eu.ueb.acem.dal.bleu.neo4j;

import java.util.Set;

import org.springframework.data.neo4j.annotation.Query;
import eu.ueb.acem.dal.GenericRepository;
import eu.ueb.acem.domain.beans.bleu.neo4j.BesoinNode;

/**
 * @author Grégoire Colbert @since 2013-11-20
 * 
 */
public interface BesoinRepository extends GenericRepository<BesoinNode> {

	@Query(value = "start n=node(*) where has(n.__type__) and n.__type__ = 'Pedagogical_need' and not (n)-[:hasParentNeed]->() return n")
	Set<BesoinNode> findRoots();

	/*
	 * @Query(value =
	 * "start need=node:indexOfNeeds(name={name}) match (n)-[:hasParentNeed]->(need) return n"
	 * ) Collection<BesoinNode> findChildrenOf(@Param("name") String name);
	 * 
	 * @Query(value =
	 * "start need=node:indexOfNeeds(name={name}) match (need)-[:needAnsweredBy]->(n) return n"
	 * ) Collection<ReponseNode> findAnswersOf(@Param("name") String name);
	 */

	/*
	@Query(value = "start need=node({id}) match (n)-[:hasParentNeed]->(need) return n")
	Set<BesoinNode> findAssociatedNeedsOf(@Param("id") Long id);

	@Query(value = "start need=node({id}) match (need)-[:needAnsweredBy]->(n) return n")
	Set<ReponseNode> findAssociatedAnswersOf(@Param("id") Long id);
	*/
}
