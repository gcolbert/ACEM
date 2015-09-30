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

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import eu.ueb.acem.dal.jpa.GenericRepository;
import eu.ueb.acem.domain.beans.jpa.bleu.PedagogicalActivityEntity;

/**
 * @author Grégoire Colbert
 * @since 2015-05-28
 */
public interface PedagogicalActivityRepository extends GenericRepository<PedagogicalActivityEntity> {

	@Override
	@Query("SELECT pa FROM PedagogicalActivity pa WHERE pa.name = :name")
	Iterable<PedagogicalActivityEntity> findByName(@Param("name") String name);

	@Query("SELECT pa FROM PedagogicalActivity pa, PedagogicalSession ses WHERE ses.id = :sessionId AND pa MEMBER OF ses.pedagogicalActivities AND pa NOT IN ( SELECT pa2 FROM PedagogicalActivity pa2, PedagogicalActivity pa3 WHERE pa3.nextPedagogicalUnit = pa2 AND pa3 MEMBER OF ses.pedagogicalActivities )")	
	Iterable<PedagogicalActivityEntity> findFirstActivitiesOfSession(@Param("sessionId") Long sessionId);

}
