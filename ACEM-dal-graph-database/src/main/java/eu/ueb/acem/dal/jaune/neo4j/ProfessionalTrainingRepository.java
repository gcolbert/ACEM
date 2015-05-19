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
package eu.ueb.acem.dal.jaune.neo4j;

import java.util.Set;

import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.repository.query.Param;

import eu.ueb.acem.dal.GenericRepository;
import eu.ueb.acem.domain.beans.jaune.neo4j.ProfessionalTrainingNode;
import eu.ueb.acem.domain.beans.jaune.neo4j.ResourceCategoryNode;

/**
 * @author Grégoire Colbert
 * @since 2013-11-20
 * 
 */
public interface ProfessionalTrainingRepository extends GenericRepository<ProfessionalTrainingNode> {

	@Override
	@Query(value = "MATCH (r:ProfessionalTraining) WHERE id(r)=({id}) RETURN count(r)")
	Long count(@Param("id") Long id);

	@Override
	@Query(value = "MATCH (r:ProfessionalTraining) WHERE r.name=({name}) RETURN r")
	Iterable<ProfessionalTrainingNode> findByName(@Param("name") String name);

	@Query(value = "MATCH (:ProfessionalTraining)<-[:categoryContains]-(m:ResourceCategory) RETURN m")
	Set<ResourceCategoryNode> getCategories();

	@Query(value = "MATCH (p:Person)-[:worksForOrganisation]->(:Organisation)-[*0..2]->(:Organisation)-[:possessesResource|:accessesResource|:supportsResource]->(:ProfessionalTraining)<-[:categoryContains]-(c:ResourceCategory) WHERE id(p)=({personId}) RETURN c")
	Set<ResourceCategoryNode> getCategoriesForPerson(@Param("personId") Long personId);

	@Query(value = "MATCH (r:ProfessionalTraining)<-[:categoryContains]-(c:ResourceCategory) WHERE id(c)=({categoryId}) RETURN r")
	Set<ProfessionalTrainingNode> getEntitiesWithCategory(@Param("categoryId") Long categoryId);

	@Query(value = "MATCH (p:Person)-[:worksForOrganisation]->(:Organisation)-[*0..2]->(:Organisation)-[:possessesResource|:accessesResource|:supportsResource]->(r:ProfessionalTraining)<-[:categoryContains]-(c:ResourceCategory) WHERE id(p)=({personId}) AND id(c)=({categoryId}) RETURN r")
	Set<ProfessionalTrainingNode> getResourcesInCategoryForPerson(@Param("categoryId") Long categoryId, @Param("personId") Long personId);

}
