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
import org.springframework.data.repository.query.Param;

import eu.ueb.acem.dal.GenericRepository;
import eu.ueb.acem.domain.beans.bleu.neo4j.PedagogicalScenarioNode;

/**
 * @author Grégoire Colbert
 * @since 2013-11-20
 * 
 */
public interface PedagogicalScenarioRepository extends GenericRepository<PedagogicalScenarioNode> {

	@Override
	@Query(value = "MATCH (n:PedagogicalScenario) WHERE id(n)=({id}) RETURN count(n)")
	Long count(@Param("id") Long id);

	@Override
	@Query(value = "MATCH (n:PedagogicalScenario) WHERE n.name=({name}) RETURN n")
	Iterable<PedagogicalScenarioNode> findByName(@Param("name") String name);

	@Query(value = "START n=node({personId})  MATCH (n)-[:authorsScenario]->(scenario) RETURN scenario")
	Set<PedagogicalScenarioNode> findScenariosWithAuthor(@Param("personId") Long id);

	@Query(value = "START n=node({pedagogicalAnswerId}) MATCH (n:PedagogicalAnswer)-[:answeredUsingResourceCategory]->(:ResourceCategory)<-[:activityRequiringResourceFromCategory]-(:PedagogicalActivity)-[:activityForScenario]->(s:PedagogicalScenario) RETURN s")
	Set<PedagogicalScenarioNode> findScenariosAssociatedWithPedagogicalAnswer(@Param("pedagogicalAnswerId") Long id);

	@Query(value = "START n=node({resourceCategoryId}) MATCH (n:ResourceCategory)<-[:activityRequiringResourceFromCategory]-(:PedagogicalActivity)-[:activityForScenario]->(s:PedagogicalScenario) RETURN s")
	Set<PedagogicalScenarioNode> findScenariosAssociatedWithResourceCategory(@Param("resourceCategoryId") Long id);

}
