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

import java.util.HashSet;
import java.util.Set;

import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.neo4j.annotation.Fetch;
import org.springframework.data.neo4j.annotation.Indexed;
import org.springframework.data.neo4j.annotation.NodeEntity;
import org.springframework.data.neo4j.annotation.RelatedTo;
import org.springframework.transaction.annotation.Transactional;

import eu.ueb.acem.domain.beans.rouge.Community;
import eu.ueb.acem.domain.beans.rouge.Institution;

/**
 * @author Grégoire Colbert
 * @since 2013-11-20
 * 
 */
@NodeEntity
@TypeAlias("Community")
public class CommunityNode extends OrganisationNode implements Community {

	/**
	 * For serialization.
	 */
	private static final long serialVersionUID = 1861762804925897713L;

	@Indexed
	private String name;

	@RelatedTo(elementClass = InstitutionNode.class, type = "institutionMemberOfCommunity", direction = INCOMING)
	@Fetch
	private Set<InstitutionNode> institutions;

	public CommunityNode() {
		institutions = new HashSet<InstitutionNode>();
	}

	public CommunityNode(String name, String shortname, String iconFileName) {
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
	public Set<? extends Institution> getInstitutions() {
		return institutions;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void setInstitutions(Set<? extends Institution> institutions) {
		this.institutions = (Set<InstitutionNode>) institutions;
	}

	@Override
	@Transactional
	public void addInstitution(Institution institution) {
		if (!institutions.contains(institution)) {
			institutions.add((InstitutionNode) institution);
		}
		if (!institution.getCommunities().contains(this)) {
			institution.addCommunity(this);
		}
	}

	@Override
	@Transactional
	public void removeInstitution(Institution institution) {
		if (this.getInstitutions().contains(institution)) {
			institutions.remove((InstitutionNode) institution);
		}
		if (institution.getCommunities().contains(this)) {
			institution.removeCommunity(this);
		}
	}

}
