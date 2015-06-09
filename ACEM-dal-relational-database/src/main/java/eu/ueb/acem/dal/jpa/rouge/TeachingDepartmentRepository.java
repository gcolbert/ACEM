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
package eu.ueb.acem.dal.jpa.rouge;

import java.util.Set;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import eu.ueb.acem.dal.jpa.GenericRepository;
import eu.ueb.acem.domain.beans.rouge.jpa.TeachingDepartmentEntity;

/**
 * @author Grégoire Colbert
 * @since 2015-06-03
 * 
 */
public interface TeachingDepartmentRepository extends GenericRepository<TeachingDepartmentEntity> {

	@Override
	@Query("SELECT d FROM TeachingDepartment d WHERE d.name = :name")
	Iterable<TeachingDepartmentEntity> findByName(@Param("name") String name);

	// @Query(value =
	// "MATCH (p:Person)-[:worksForOrganisation]->(o:Organisation)-[*0..2]->(:Organisation)-[:possessesResource|:accessesResource|:supportsResource]->(r:Resource)<-[:supportsResource]-(s:TeachingDepartment) WHERE id(p)=({personId}) RETURN s")
	@Query("SELECT DISTINCT (supportService) FROM TeachingDepartment supportService JOIN supportService.supportedResources d "
			+ " WHERE d.id IN ( SELECT r.id FROM Resource r, Organisation o, Person u "
			+ "              WHERE u.id = :personId "
			+ "                AND o MEMBER OF u.worksForOrganisations "
			+ "                AND (   o = r.organisationPossessingResource "
			+ "                     OR o = r.organisationSupportingResource "
			+ "                     OR o MEMBER of r.organisationsHavingAccessToResource"
			+ "                    )"
			+ "             )"
			+ "     OR d.id IN ( SELECT r.id FROM Resource r, Organisation o "
			+ "              WHERE TYPE(o) IN (Institution) "
			+ "                AND (   o = r.organisationPossessingResource "
			+ "                     OR o = r.organisationSupportingResource "
			+ "                     OR o MEMBER of r.organisationsHavingAccessToResource"
			+ "                    )"
			+ "                AND o.id IN ("
			+ "                              SELECT i.id FROM Institution i, AdministrativeDepartment ad, Person u "
			+ "                              WHERE u.id = :personId "
			+ "                                AND ad MEMBER OF u.worksForOrganisations "
			+ "                                AND ad MEMBER OF i.administrativeDepartments"
			+ "                            )"
			+ "             )"
			+ "     OR d.id IN ( SELECT r.id FROM Resource r, Organisation o "
			+ "              WHERE TYPE(o) IN (Institution) "
			+ "                AND (   o = r.organisationPossessingResource "
			+ "                     OR o = r.organisationSupportingResource "
			+ "                     OR o MEMBER of r.organisationsHavingAccessToResource"
			+ "                    )"
			+ "                AND o.id IN ("
			+ "                              SELECT i.id FROM Institution i, TeachingDepartment td, Person u "
			+ "                              WHERE u.id = :personId "
			+ "                                AND td MEMBER OF u.worksForOrganisations "
			+ "                                AND td MEMBER OF i.teachingDepartments"
			+ "                            )"
			+ "             )"
			+ "     OR d.id IN ( SELECT d.id FROM Resource r, Organisation o "
			+ "              WHERE TYPE(o) IN (Community) "
			+ "                AND (   o = r.organisationPossessingResource "
			+ "                     OR o = r.organisationSupportingResource "
			+ "                     OR o MEMBER of r.organisationsHavingAccessToResource"
			+ "                    )"
			+ "                AND o.id IN ("
			+ "                              SELECT c.id FROM Community c, Institution i, AdministrativeDepartment ad, Person u "
			+ "                              WHERE u.id = :personId "
			+ "                                AND ad MEMBER OF u.worksForOrganisations "
			+ "                                AND ad MEMBER OF i.administrativeDepartments"
			+ "                                AND c MEMBER OF i.communities"
			+ "                            )"
			+ "             )"
			+ "     OR d.id IN ( SELECT r.id FROM Resource r, Organisation o "
			+ "              WHERE TYPE(o) IN (Community) "
			+ "                AND (   o = r.organisationPossessingResource "
			+ "                     OR o = r.organisationSupportingResource "
			+ "                     OR o MEMBER of r.organisationsHavingAccessToResource"
			+ "                    )"
			+ "                AND o.id IN ("
			+ "                              SELECT c.id FROM Community c, Institution i, TeachingDepartment td, Person u "
			+ "                              WHERE u.id = :personId "
			+ "                                AND td MEMBER OF u.worksForOrganisations "
			+ "                                AND td MEMBER OF i.teachingDepartments"
			+ "                                AND c MEMBER OF i.communities"
			+ "                            )"
			+ "             )"
			+ "     OR d.id IN ( SELECT r.id FROM Resource r, Organisation o "
			+ "              WHERE TYPE(o) IN (Community) "
			+ "                AND (   o = r.organisationPossessingResource "
			+ "                     OR o = r.organisationSupportingResource "
			+ "                     OR o MEMBER of r.organisationsHavingAccessToResource"
			+ "                    )"
			+ "                AND o.id IN ("
			+ "                              SELECT c.id FROM Community c, Institution i, Person u "
			+ "                              WHERE u.id = :personId "
			+ "                                AND i MEMBER OF u.worksForOrganisations "
			+ "                                AND c MEMBER OF i.communities"
			+ "                            )"
			+ "             )")
	Set<TeachingDepartmentEntity> getSupportServicesForPerson(@Param("personId") Long personId);

}
