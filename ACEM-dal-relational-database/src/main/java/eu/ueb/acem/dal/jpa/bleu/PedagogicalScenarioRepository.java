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
package eu.ueb.acem.dal.jpa.bleu;

import java.util.Set;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import eu.ueb.acem.dal.jpa.GenericRepository;
import eu.ueb.acem.domain.beans.jpa.bleu.PedagogicalScenarioEntity;

/**
 * @author Grégoire Colbert
 * @since 2015-05-28
 */
public interface PedagogicalScenarioRepository extends GenericRepository<PedagogicalScenarioEntity> {

	@Override
	@Query("SELECT ps FROM PedagogicalScenario ps WHERE ps.name = :name")
	Iterable<PedagogicalScenarioEntity> findByName(@Param("name") String name);

	//@Query(value = "MATCH (t:Teacher)-[:authorsScenario]->(s:PedagogicalScenario) WHERE id(t)=({teacherId}) RETURN s")
	@Query("SELECT ps FROM PedagogicalScenario ps, Teacher t WHERE t.id = :teacherId AND t MEMBER OF ps.authors")
	Set<PedagogicalScenarioEntity> findScenariosWithAuthor(@Param("teacherId") Long id);

	//@Query(value = "MATCH (a:PedagogicalAnswer)-[:answeredUsingResourceCategory]->(:ResourceCategory)<-[:activityRequiringResourceFromCategory]-(:PedagogicalActivity)-[:activityForSession]->(ses:PedagogicalSession)-[:sessionForSequence]->(seq:PedagogicalSequence)-[:sequenceForScenario]->(sce:PedagogicalScenario) WHERE id(a)=({pedagogicalAnswerId}) RETURN sce")
	@Query("SELECT sce FROM PedagogicalScenario sce, PedagogicalSequence seq, PedagogicalSession ses, PedagogicalActivity act, ResourceCategory rc, PedagogicalAnswer ans WHERE ans.id = :pedagogicalAnswerId AND rc MEMBER OF ans.resourceCategories AND act MEMBER OF rc.pedagogicalActivities AND act MEMBER OF ses.pedagogicalActivities AND ses MEMBER OF seq.pedagogicalSessions AND seq MEMBER OF sce.pedagogicalSequences")
	Set<PedagogicalScenarioEntity> findScenariosAssociatedWithPedagogicalAnswer(@Param("pedagogicalAnswerId") Long id);

	//@Query(value = "MATCH (c:ResourceCategory)<-[:activityRequiringResourceFromCategory]-(:PedagogicalActivity)-[:activityForSession]->(ses:PedagogicalSession)-[:sessionForSequence]->(seq:PedagogicalSequence)-[:sequenceForScenario]->(sce:PedagogicalScenario) WHERE id(c)=({resourceCategoryId}) RETURN sce")
	@Query("SELECT sce FROM PedagogicalScenario sce, PedagogicalSequence seq, PedagogicalSession ses, PedagogicalActivity act, ResourceCategory rc WHERE rc.id = :resourceCategoryId AND act MEMBER OF rc.pedagogicalActivities AND act MEMBER OF ses.pedagogicalActivities AND ses MEMBER OF seq.pedagogicalSessions AND seq MEMBER OF sce.pedagogicalSequences")
	Set<PedagogicalScenarioEntity> findScenariosAssociatedWithResourceCategory(@Param("resourceCategoryId") Long id);

}
