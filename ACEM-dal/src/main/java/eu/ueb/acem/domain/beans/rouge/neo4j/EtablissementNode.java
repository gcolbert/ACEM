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

import static org.neo4j.graphdb.Direction.OUTGOING;
import static org.neo4j.graphdb.Direction.INCOMING;

import java.util.HashSet;
import java.util.Set;

import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.neo4j.annotation.Fetch;
import org.springframework.data.neo4j.annotation.Indexed;
import org.springframework.data.neo4j.annotation.NodeEntity;
import org.springframework.data.neo4j.annotation.RelatedTo;

import eu.ueb.acem.domain.beans.rouge.Communaute;
import eu.ueb.acem.domain.beans.rouge.Composante;
import eu.ueb.acem.domain.beans.rouge.Etablissement;
import eu.ueb.acem.domain.beans.rouge.Service;

/**
 * @author Grégoire Colbert @since 2013-11-20
 * 
 */
@NodeEntity
@TypeAlias("Organisation:Teaching_institution")
public class EtablissementNode extends OrganisationNode implements Etablissement {

	private static final long serialVersionUID = 4218521116992739925L;

	@Indexed(indexName = "indexOfInstitutions")
	private String name;

	@RelatedTo(elementClass = CommunauteNode.class, type = "institutionMemberOfCommunity", direction = OUTGOING)
	@Fetch
	private Set<CommunauteNode> communities;

	@RelatedTo(elementClass = ComposanteNode.class, type = "teachingDepartmentPartOfInstitution", direction = INCOMING)
	@Fetch
	private Set<ComposanteNode> teachingDepartments;

	@RelatedTo(elementClass = ServiceNode.class, type = "administrativeDepartmentPartOfInstitution", direction = INCOMING)
	@Fetch
	private Set<ServiceNode> administrativeDepartments;

	public EtablissementNode() {
		communities = new HashSet<CommunauteNode>();
		teachingDepartments = new HashSet<ComposanteNode>();
		administrativeDepartments = new HashSet<ServiceNode>();
	}

	public EtablissementNode(String name, String shortname) {
		this();
		this.setName(name);
		this.setShortname(shortname);
	}

	@Override
	public Set<? extends Communaute> getCommunities() {
		return communities;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void setCommunities(Set<? extends Communaute> communities) {
		this.communities = (Set<CommunauteNode>) communities;
	}

	@Override
	public Set<? extends Composante> getTeachingDepartments() {
		return teachingDepartments;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void setTeachingDepartments(Set<? extends Composante> teachingDepartments) {
		this.teachingDepartments = (Set<ComposanteNode>) teachingDepartments;
	}

	@Override
	public Set<? extends Service> getAdministrativeDepartments() {
		return administrativeDepartments;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void setAdministrativeDepartments(Set<? extends Service> administrativeDepartments) {
		this.administrativeDepartments = (Set<ServiceNode>) administrativeDepartments;
	}

	@Override
	public void addCommunity(Communaute community) {
		if (! communities.contains(community)) {
			communities.add((CommunauteNode)community);
		}
		if (! community.getInstitutions().contains(this)) {
			community.addInstitution(this);
		}
	}

	@Override
	public void removeCommunity(Communaute community) {
		if (communities.contains(community)) {
			communities.remove((CommunauteNode)community);
		}
		if (community.getInstitutions().contains(this)) {
			community.removeInstitution(this);
		}
	}

}
