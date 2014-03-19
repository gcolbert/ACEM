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
import eu.ueb.acem.domain.beans.jaune.neo4j.EquipementNode;

/**
 * @author Grégoire Colbert
 * @since 2013-11-20
 * 
 */
public interface EquipmentRepository extends GenericRepository<EquipementNode> {

	@Query(value = "MATCH (n:Equipment) WHERE id(n)=({id}) RETURN count(n)")
	Long count(@Param("id") Long id);

	@Query(value = "MATCH (n:Equipment) RETURN DISTINCT n.category")
	Set<String> getCategories();

	@Query(value = "MATCH (n:Equipment) WHERE n.category=({category}) RETURN n")
	Set<EquipementNode> getEntitiesWithCategory(@Param("category") String category);
	
}
