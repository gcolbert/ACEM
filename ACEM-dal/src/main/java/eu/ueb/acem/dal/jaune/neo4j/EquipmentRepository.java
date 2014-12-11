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
package eu.ueb.acem.dal.jaune.neo4j;

import java.util.Set;

import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.repository.query.Param;

import eu.ueb.acem.dal.GenericRepository;
import eu.ueb.acem.domain.beans.jaune.neo4j.EquipmentNode;
import eu.ueb.acem.domain.beans.jaune.neo4j.ResourceCategoryNode;

/**
 * @author Grégoire Colbert
 * @since 2013-11-20
 * 
 */
public interface EquipmentRepository extends GenericRepository<EquipmentNode> {

	@Query(value = "MATCH (n:Equipment) WHERE id(n)=({id}) RETURN count(n)")
	Long count(@Param("id") Long id);

	@Query(value = "MATCH (n:Equipment) WHERE n.name=({name}) RETURN n")
	Iterable<EquipmentNode> findByName(@Param("name") String name);
	
	@Query(value = "MATCH (n:Equipment)<-[r:categoryContains]-(m:ResourceCategory) RETURN m")
	Set<ResourceCategoryNode> getCategories();

	@Query(value = "MATCH (n:Equipment)<-[r:categoryContains]-(m:ResourceCategory) WHERE id(m)=({categoryId}) RETURN n")
	Set<EquipmentNode> getEntitiesWithCategory(@Param("categoryId") Long categoryId);
	
}
