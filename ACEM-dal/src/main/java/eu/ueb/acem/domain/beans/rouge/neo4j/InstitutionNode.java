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
package eu.ueb.acem.domain.beans.rouge.neo4j;

import static org.neo4j.graphdb.Direction.INCOMING;
import static org.neo4j.graphdb.Direction.OUTGOING;

import java.util.HashSet;
import java.util.Set;

import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.neo4j.annotation.Fetch;
import org.springframework.data.neo4j.annotation.Indexed;
import org.springframework.data.neo4j.annotation.NodeEntity;
import org.springframework.data.neo4j.annotation.RelatedTo;
import org.springframework.transaction.annotation.Transactional;

import eu.ueb.acem.domain.beans.rouge.Community;
import eu.ueb.acem.domain.beans.rouge.TeachingDepartment;
import eu.ueb.acem.domain.beans.rouge.Institution;
import eu.ueb.acem.domain.beans.rouge.AdministrativeDepartment;

/**
 * @author Grégoire Colbert
 * @since 2013-11-20
 * 
 */
@NodeEntity
@TypeAlias("Institution")
public class InstitutionNode extends OrganisationNode implements Institution {

	/**
	 * For serialization.
	 */
	private static final long serialVersionUID = 4218521116992739925L;

	@Indexed
	private String name;

	@RelatedTo(elementClass = CommunityNode.class, type = "institutionMemberOfCommunity", direction = OUTGOING)
	@Fetch
	private Set<CommunityNode> communities;

	@RelatedTo(elementClass = TeachingDepartmentNode.class, type = "teachingDepartmentPartOfInstitution", direction = INCOMING)
	@Fetch
	private Set<TeachingDepartmentNode> teachingDepartments;

	@RelatedTo(elementClass = AdministrativeDepartmentNode.class, type = "administrativeDepartmentPartOfInstitution", direction = INCOMING)
	@Fetch
	private Set<AdministrativeDepartmentNode> administrativeDepartments;

	public InstitutionNode() {
		communities = new HashSet<CommunityNode>();
		teachingDepartments = new HashSet<TeachingDepartmentNode>();
		administrativeDepartments = new HashSet<AdministrativeDepartmentNode>();
	}

	public InstitutionNode(String name, String shortname, String iconFileName) {
		this();
		this.setName(name);
		this.setShortname(shortname);
		this.setIconFileName(iconFileName);
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}
	
	@Override
	public Set<? extends Community> getCommunities() {
		return communities;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void setCommunities(Set<? extends Community> communities) {
		this.communities = (Set<CommunityNode>) communities;
	}

	@Override
	public Set<? extends TeachingDepartment> getTeachingDepartments() {
		return teachingDepartments;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void setTeachingDepartments(Set<? extends TeachingDepartment> teachingDepartments) {
		this.teachingDepartments = (Set<TeachingDepartmentNode>) teachingDepartments;
	}

	@Override
	public Set<? extends AdministrativeDepartment> getAdministrativeDepartments() {
		return administrativeDepartments;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void setAdministrativeDepartments(Set<? extends AdministrativeDepartment> administrativeDepartments) {
		this.administrativeDepartments = (Set<AdministrativeDepartmentNode>) administrativeDepartments;
	}

	@Override
	public void addCommunity(Community community) {
		if (! communities.contains(community)) {
			communities.add((CommunityNode)community);
		}
		if (! community.getInstitutions().contains(this)) {
			community.addInstitution(this);
		}
	}

	@Override
	@Transactional
	public void removeCommunity(Community community) {
		if (communities.contains(community)) {
			communities.remove(community);
		}
		if (community.getInstitutions().contains(this)) {
			community.removeInstitution(this);
		}
	}

	@Override
	@Transactional
	public void addAdministrativeDepartment(AdministrativeDepartment administrativeDepartment) {
		if (! administrativeDepartments.contains(administrativeDepartment)) {
			administrativeDepartments.add((AdministrativeDepartmentNode)administrativeDepartment);
		}
		if (! administrativeDepartment.getInstitutions().contains(this)) {
			administrativeDepartment.addInstitution(this);
		}
	}

	@Override
	@Transactional
	public void removeAdministrativeDepartment(AdministrativeDepartment administrativeDepartment) {
		if (administrativeDepartments.contains(administrativeDepartment)) {
			administrativeDepartments.remove(administrativeDepartment);
		}
		if (administrativeDepartment.getInstitutions().contains(this)) {
			administrativeDepartment.removeInstitution(this);
		}
	}

	@Override
	@Transactional
	public void addTeachingDepartment(TeachingDepartment teachingDepartment) {
		if (! teachingDepartments.contains(teachingDepartment)) {
			teachingDepartments.add((TeachingDepartmentNode)teachingDepartment);
		}
		if (! teachingDepartment.getInstitutions().contains(this)) {
			teachingDepartment.addInstitution(this);
		}
	}

	@Override
	@Transactional
	public void removeTeachingDepartment(TeachingDepartment teachingDepartment) {
		if (teachingDepartments.contains(teachingDepartment)) {
			teachingDepartments.remove(teachingDepartment);
		}
		if (teachingDepartment.getInstitutions().contains(this)) {
			teachingDepartment.removeInstitution(this);
		}
	}

}
