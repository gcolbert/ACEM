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
package eu.ueb.acem.dal.neo4j.jaune;

import java.util.Set;

import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.repository.query.Param;

import eu.ueb.acem.dal.neo4j.GenericRepository;
import eu.ueb.acem.domain.beans.neo4j.jaune.PedagogicalAndDocumentaryResourceNode;
import eu.ueb.acem.domain.beans.neo4j.jaune.ResourceCategoryNode;

/**
 * @author Grégoire Colbert
 * @since 2013-11-20
 * 
 */
public interface PedagogicalAndDocumentaryResourcesRepository extends
		GenericRepository<PedagogicalAndDocumentaryResourceNode> {

	@Override
	@Query(value = "MATCH (r:PedagogicalAndDocumentaryResource) WHERE id(r)=({id}) RETURN count(r)")
	Long count(@Param("id") Long id);

	@Override
	@Query(value = "MATCH (r:PedagogicalAndDocumentaryResource) WHERE r.name=({name}) RETURN r")
	Iterable<PedagogicalAndDocumentaryResourceNode> findByName(@Param("name") String name);

	@Query(value = "MATCH (:PedagogicalAndDocumentaryResource)<-[:categoryContains]-(c:ResourceCategory) RETURN c")
	Set<ResourceCategoryNode> getCategories();

	@Query(value = "MATCH (p:Person)-[:worksForOrganisation]->(:Organisation)-[*0..2]->(:Organisation)-[:possessesResource|:accessesResource|:supportsResource]->(:PedagogicalAndDocumentaryResource)<-[:categoryContains]-(c:ResourceCategory) WHERE id(p)=({personId}) RETURN c")
	Set<ResourceCategoryNode> getCategoriesForPerson(@Param("personId") Long personId);

	@Query(value = "MATCH (r:PedagogicalAndDocumentaryResource)<-[:categoryContains]-(c:ResourceCategory) WHERE id(c)=({categoryId}) RETURN r")
	Set<PedagogicalAndDocumentaryResourceNode> getEntitiesWithCategory(@Param("categoryId") Long categoryId);

	@Query(value = "MATCH (p:Person)-[:worksForOrganisation]->(:Organisation)-[*0..2]->(:Organisation)-[:possessesResource|:accessesResource|:supportsResource]->(r:PedagogicalAndDocumentaryResource)<-[:categoryContains]-(c:ResourceCategory) WHERE id(p)=({personId}) AND id(c)=({categoryId}) RETURN r")
	Set<PedagogicalAndDocumentaryResourceNode> getResourcesInCategoryForPerson(@Param("categoryId") Long categoryId, @Param("personId") Long personId);

}
