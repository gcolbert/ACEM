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
import eu.ueb.acem.domain.beans.neo4j.bleu.PedagogicalSequenceNode;

/**
 * @author Grégoire Colbert
 * @since 2015-07-15
 * 
 */
public interface PedagogicalSequenceRepository extends GenericRepository<PedagogicalSequenceNode> {

	@Override
	@Query(value = "MATCH (n:PedagogicalSequence) WHERE id(n)=({id}) RETURN count(n)")
	Long count(@Param("id") Long id);

	@Override
	@Query(value = "MATCH (n:PedagogicalSequence) WHERE n.name=({name}) RETURN n")
	Iterable<PedagogicalSequenceNode> findByName(@Param("name") String name);

	@Query(value = "MATCH (seq:PedagogicalSequence)-[:sequenceForScenario]->(sce:PedagogicalScenario) WHERE not (sce)<-[:sequenceForScenario]-()-[:next]->(seq) AND id(sce)=({scenarioId}) RETURN seq")
	Iterable<PedagogicalSequenceNode> findFirstSequencesOfScenario(@Param("scenarioId") Long scenarioId);

}
