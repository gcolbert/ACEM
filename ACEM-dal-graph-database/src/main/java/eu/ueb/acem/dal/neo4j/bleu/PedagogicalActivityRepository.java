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

import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.repository.query.Param;

import eu.ueb.acem.dal.neo4j.GenericRepository;
import eu.ueb.acem.domain.beans.neo4j.bleu.PedagogicalActivityNode;

/**
 * The Spring Data Neo4j repository of PedagogicalActivityNode instances.
 * 
 * @author Grégoire Colbert
 * @since 2013-11-20
 */
public interface PedagogicalActivityRepository extends GenericRepository<PedagogicalActivityNode> {

	/**
	 * This method is used to count the {@link PedagogicalActivityNode}s having
	 * the given "id" property value. Since the "id" is a unique identifier,
	 * this method ought to return 1 or 0.
	 * 
	 * @param id
	 *            The "id" of the PedagogicalActivityNode to look for.
	 * @return 1 if a PedagogicalActivityNode with the given id has been found,
	 *         0 otherwise
	 */
	@Override
	@Query(value = "MATCH (n:PedagogicalActivity) WHERE id(n)=({id}) RETURN count(n)")
	Long count(@Param("id") Long id);

	/**
	 * This method is used to collect all {@link PedagogicalActivityNode}s
	 * having the given "name" property value.
	 * 
	 * @param name
	 *            The "name" of the PedagogicalActivityNode to look for.
	 * @return the collection of PedagogicalActivityNodes having the name
	 *         property
	 */
	@Override
	@Query(value = "MATCH (n:PedagogicalActivity) WHERE n.name=({name}) RETURN n")
	Iterable<PedagogicalActivityNode> findByName(@Param("name") String name);

	/**
	 * This method is used to collect all {@link PedagogicalActivityNode}s that
	 * have no predecessors and which are associated with the
	 * PedagogicalSessionNode whose "id" is given.
	 * 
	 * @param sessionId
	 *            The "id" of a PedagogicalSessionNode
	 * @return the collection of PedagogicalActivityNodes without predecessors
	 *         which are associated to the PedagogicalSession having the given
	 *         id
	 */
	@Query(value = "MATCH (act:PedagogicalActivity)-[:activityForSession]->(ses:PedagogicalSession) WHERE id(ses)=({sessionId}) AND not (ses)<-[:activityForSession]-()-[:next]->(act) RETURN act")
	Iterable<PedagogicalActivityNode> findFirstActivitiesOfSession(@Param("sessionId") Long sessionId);

}
