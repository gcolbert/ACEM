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
package eu.ueb.acem.dal.neo4j;

import java.io.Serializable;

import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.data.neo4j.repository.RelationshipOperationsRepository;

/**
 * Generic repository for Neo4j nodes. It extends GraphRepository and
 * RelationshipOperationsRepository, which are part of Spring Data Neo4j, and
 * declares all the common methods. All interfaces that extend this interface
 * are to be automatically implemented by Spring Data Neo4j.
 * 
 * @author Grégoire Colbert
 * @see <a
 *      href="http://docs.spring.io/spring-data/neo4j/docs/current/reference/html/#reference_programming-model_repositories">[Spring
 *      Data Neo4j documentation] CRUD with repositories</a>
 * @since 2013-11-20
 * 
 */
public interface GenericRepository<N> extends Serializable, GraphRepository<N>, RelationshipOperationsRepository<N> {

	Iterable<N> findByName(String name);

	Long count(Long id);

}
