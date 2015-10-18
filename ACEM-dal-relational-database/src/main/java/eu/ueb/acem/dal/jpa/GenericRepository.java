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
package eu.ueb.acem.dal.jpa;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

/**
 * Generic repository for JPA entities. It extends JpaRepository which is part
 * of Spring Data JPA and declares all the common methods. All interfaces that
 * extend this interface are to be automatically implemented by Spring Data JPA.
 * 
 * @author Grégoire Colbert
 * @see <a
 *      href="http://static.springsource.org/spring-data/data-jpa/docs/current/reference/html/#repositories.custom-behaviour-for-all-repositories">[Spring
 *      Data JPA documentation] Adding custom behaviour repository</a>
 * @since 2013-11-20
 */
@NoRepositoryBean
public interface GenericRepository<N> extends Serializable, JpaRepository<N, Long> {

	/**
	 * This query will be automatically implemented by it's name, "findBy" is
	 * the action, and "Name" is parsed as the criteria.
	 *
	 * @param name
	 *            the "name" value of the entities to look for
	 * @return the collection of entities having the given value for the "name"
	 *         property
	 */
	Iterable<N> findByName(String name);

}
