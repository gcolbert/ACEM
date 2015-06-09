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
package eu.ueb.acem.dal.jpa.jaune;

import java.util.Set;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import eu.ueb.acem.dal.jpa.GenericRepository;
import eu.ueb.acem.domain.beans.jaune.jpa.EquipmentEntity;
import eu.ueb.acem.domain.beans.jaune.jpa.ResourceCategoryEntity;

/**
 * @author Grégoire Colbert
 * @since 2015-05-29
 * 
 */
public interface EquipmentRepository extends GenericRepository<EquipmentEntity> {

	@Override
	@Query("SELECT e FROM Equipment e WHERE e.name = :name")
	Iterable<EquipmentEntity> findByName(@Param("name") String name);
	
	//@Query(value = "MATCH (:Equipment)<-[:categoryContains]-(c:ResourceCategory) RETURN c")
	@Query("SELECT rc FROM ResourceCategory rc, Equipment r WHERE r MEMBER OF rc.resources")
	Set<ResourceCategoryEntity> getCategories();

	//@Query(value = "MATCH (p:Person)-[:worksForOrganisation]->(:Organisation)-[*0..2]->(:Organisation)-[:possessesResource|:accessesResource|:supportsResource]->(:Equipment)<-[:categoryContains]-(c:ResourceCategory) WHERE id(p)=({personId}) RETURN c")
	@Query("SELECT DISTINCT (rc) FROM ResourceCategory rc JOIN rc.resources d "
			+ " WHERE d.id IN ( SELECT e.id FROM Equipment e, Organisation o, Person u "
			+ "              WHERE u.id = :personId "
			+ "                AND o MEMBER OF u.worksForOrganisations "
			+ "                AND (   o = e.organisationPossessingResource "
			+ "                     OR o = e.organisationSupportingResource "
			+ "                     OR o MEMBER of e.organisationsHavingAccessToResource"
			+ "                    )"
			+ "             )"
		    + "     OR d.id IN ( SELECT d.id FROM Equipment d, Organisation o "
			+ "              WHERE TYPE(o) IN (Institution) "
			+ "                AND (   o = d.organisationPossessingResource "
			+ "                     OR o = d.organisationSupportingResource "
			+ "                     OR o MEMBER of d.organisationsHavingAccessToResource"
			+ "                    )"
			+ "                AND o.id IN ("
			+ "                              SELECT i.id FROM Institution i, AdministrativeDepartment ad, Person u "
			+ "                              WHERE u.id = :personId "
			+ "                                AND ad MEMBER OF u.worksForOrganisations "
			+ "                                AND ad MEMBER OF i.administrativeDepartments"
			+ "                            )"
			+ "             )"
		    + "     OR d.id IN ( SELECT d.id FROM Equipment d, Organisation o "
			+ "              WHERE TYPE(o) IN (Institution) "
			+ "                AND (   o = d.organisationPossessingResource "
			+ "                     OR o = d.organisationSupportingResource "
			+ "                     OR o MEMBER of d.organisationsHavingAccessToResource"
			+ "                    )"
			+ "                AND o.id IN ("
			+ "                              SELECT i.id FROM Institution i, TeachingDepartment td, Person u "
			+ "                              WHERE u.id = :personId "
			+ "                                AND td MEMBER OF u.worksForOrganisations "
			+ "                                AND td MEMBER OF i.teachingDepartments"
			+ "                            )"
			+ "             )"
		    + "     OR d.id IN ( SELECT d.id FROM Equipment d, Organisation o "
			+ "              WHERE TYPE(o) IN (Community) "
			+ "                AND (   o = d.organisationPossessingResource "
			+ "                     OR o = d.organisationSupportingResource "
			+ "                     OR o MEMBER of d.organisationsHavingAccessToResource"
			+ "                    )"
			+ "                AND o.id IN ("
			+ "                              SELECT c.id FROM Community c, Institution i, AdministrativeDepartment ad, Person u "
			+ "                              WHERE u.id = :personId "
			+ "                                AND ad MEMBER OF u.worksForOrganisations "
			+ "                                AND ad MEMBER OF i.administrativeDepartments"
			+ "                                AND c MEMBER OF i.communities"
			+ "                            )"
			+ "             )"
		    + "     OR d.id IN ( SELECT d.id FROM Equipment d, Organisation o "
			+ "              WHERE TYPE(o) IN (Community) "
			+ "                AND (   o = d.organisationPossessingResource "
			+ "                     OR o = d.organisationSupportingResource "
			+ "                     OR o MEMBER of d.organisationsHavingAccessToResource"
			+ "                    )"
			+ "                AND o.id IN ("
			+ "                              SELECT c.id FROM Community c, Institution i, TeachingDepartment td, Person u "
			+ "                              WHERE u.id = :personId "
			+ "                                AND td MEMBER OF u.worksForOrganisations "
			+ "                                AND td MEMBER OF i.teachingDepartments"
			+ "                                AND c MEMBER OF i.communities"
			+ "                            )"
			+ "             )"
		    + "     OR d.id IN ( SELECT d.id FROM Equipment d, Organisation o "
			+ "              WHERE TYPE(o) IN (Community) "
			+ "                AND (   o = d.organisationPossessingResource "
			+ "                     OR o = d.organisationSupportingResource "
			+ "                     OR o MEMBER of d.organisationsHavingAccessToResource"
			+ "                    )"
			+ "                AND o.id IN ("
			+ "                              SELECT c.id FROM Community c, Institution i, Person u "
			+ "                              WHERE u.id = :personId "
			+ "                                AND i MEMBER OF u.worksForOrganisations "
			+ "                                AND c MEMBER OF i.communities"
			+ "                            )"
			+ "             )"
			)
	Set<ResourceCategoryEntity> getCategoriesForPerson(@Param("personId") Long personId);

	//@Query(value = "MATCH (r:Equipment)<-[:categoryContains]-(c:ResourceCategory) WHERE id(c)=({categoryId}) RETURN r")
	@Query("SELECT r FROM Equipment r, ResourceCategory rc WHERE rc.id = :categoryId AND rc MEMBER OF r.categories")
	Set<EquipmentEntity> getEntitiesWithCategory(@Param("categoryId") Long categoryId);

	//@Query(value = "MATCH (p:Person)-[:worksForOrganisation]->(:Organisation)-[*0..2]->(:Organisation)-[:possessesResource|:accessesResource|:supportsResource]->(r:Equipment)<-[:categoryContains]-(c:ResourceCategory) WHERE id(p)=({personId}) AND id(c)=({categoryId}) RETURN r")
	@Query("SELECT DISTINCT (d) FROM Equipment d JOIN d.categories rc "
			+ "WHERE rc.id = :categoryId "
			+ "  AND ("
			+ "        d.id IN ( SELECT e.id FROM Equipment e, Organisation o, Person u "
			+ "              WHERE u.id = :personId "
			+ "                AND o MEMBER OF u.worksForOrganisations "
			+ "                AND (   o = e.organisationPossessingResource "
			+ "                     OR o = e.organisationSupportingResource "
			+ "                     OR o MEMBER of e.organisationsHavingAccessToResource"
			+ "                    )"
			+ "             )"
		    + "     OR d.id IN ( SELECT d.id FROM Equipment d, Organisation o "
			+ "              WHERE TYPE(o) IN (Institution) "
			+ "                AND (   o = d.organisationPossessingResource "
			+ "                     OR o = d.organisationSupportingResource "
			+ "                     OR o MEMBER of d.organisationsHavingAccessToResource"
			+ "                    )"
			+ "                AND o.id IN ("
			+ "                              SELECT i.id FROM Institution i, AdministrativeDepartment ad, Person u "
			+ "                              WHERE u.id = :personId "
			+ "                                AND ad MEMBER OF u.worksForOrganisations "
			+ "                                AND ad MEMBER OF i.administrativeDepartments"
			+ "                            )"
			+ "             )"
		    + "     OR d.id IN ( SELECT d.id FROM Equipment d, Organisation o "
			+ "              WHERE TYPE(o) IN (Institution) "
			+ "                AND (   o = d.organisationPossessingResource "
			+ "                     OR o = d.organisationSupportingResource "
			+ "                     OR o MEMBER of d.organisationsHavingAccessToResource"
			+ "                    )"
			+ "                AND o.id IN ("
			+ "                              SELECT i.id FROM Institution i, TeachingDepartment td, Person u "
			+ "                              WHERE u.id = :personId "
			+ "                                AND td MEMBER OF u.worksForOrganisations "
			+ "                                AND td MEMBER OF i.teachingDepartments"
			+ "                            )"
			+ "             )"
		    + "     OR d.id IN ( SELECT d.id FROM Equipment d, Organisation o "
			+ "              WHERE TYPE(o) IN (Community) "
			+ "                AND (   o = d.organisationPossessingResource "
			+ "                     OR o = d.organisationSupportingResource "
			+ "                     OR o MEMBER of d.organisationsHavingAccessToResource"
			+ "                    )"
			+ "                AND o.id IN ("
			+ "                              SELECT c.id FROM Community c, Institution i, AdministrativeDepartment ad, Person u "
			+ "                              WHERE u.id = :personId "
			+ "                                AND ad MEMBER OF u.worksForOrganisations "
			+ "                                AND ad MEMBER OF i.administrativeDepartments"
			+ "                                AND c MEMBER OF i.communities"
			+ "                            )"
			+ "             )"
		    + "     OR d.id IN ( SELECT d.id FROM Equipment d, Organisation o "
			+ "              WHERE TYPE(o) IN (Community) "
			+ "                AND (   o = d.organisationPossessingResource "
			+ "                     OR o = d.organisationSupportingResource "
			+ "                     OR o MEMBER of d.organisationsHavingAccessToResource"
			+ "                    )"
			+ "                AND o.id IN ("
			+ "                              SELECT c.id FROM Community c, Institution i, TeachingDepartment td, Person u "
			+ "                              WHERE u.id = :personId "
			+ "                                AND td MEMBER OF u.worksForOrganisations "
			+ "                                AND td MEMBER OF i.teachingDepartments"
			+ "                                AND c MEMBER OF i.communities"
			+ "                            )"
			+ "             )"
		    + "     OR d.id IN ( SELECT d.id FROM Equipment d, Organisation o "
			+ "              WHERE TYPE(o) IN (Community) "
			+ "                AND (   o = d.organisationPossessingResource "
			+ "                     OR o = d.organisationSupportingResource "
			+ "                     OR o MEMBER of d.organisationsHavingAccessToResource"
			+ "                    )"
			+ "                AND o.id IN ("
			+ "                              SELECT c.id FROM Community c, Institution i, Person u "
			+ "                              WHERE u.id = :personId "
			+ "                                AND i MEMBER OF u.worksForOrganisations "
			+ "                                AND c MEMBER OF i.communities"
			+ "                            )"
			+ "             )"
			+ "      )"
			)
	Set<EquipmentEntity> getResourcesInCategoryForPerson(@Param("categoryId") Long categoryId, @Param("personId") Long personId);

}
