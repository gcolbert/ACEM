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
package eu.ueb.acem.web.viewbeans.rouge;

import java.io.Serializable;

import eu.ueb.acem.domain.beans.rouge.Communaute;
import eu.ueb.acem.domain.beans.rouge.Organisation;
import eu.ueb.acem.web.viewbeans.Pickable;

/**
 * @author Grégoire Colbert @since 2014-02-25
 * 
 */
public class CommunityViewBean implements OrganisationViewBean, Pickable, Serializable, Comparable<CommunityViewBean> {

	private static final long serialVersionUID = -116654020465612191L;

	private Communaute community;

	private Long id;

	private String name;

	private String shortname;

	public CommunityViewBean() {
	}

	public CommunityViewBean(Communaute community) {
		this();
		setCommunity(community);
	}

	@Override
	public Organisation getDomainBean() {
		return getCommunity();
	}
	
	@Override
	public void setDomainBean(Organisation organisation) {
		setCommunity((Communaute) organisation);
	}

	public Communaute getCommunity() {
		return community;
	}

	public void setCommunity(Communaute community) {
		this.community = community;
		setId(community.getId());
		setName(community.getName());
		setShortname(community.getShortname());
	}

	@Override
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getShortname() {
		return shortname;
	}

	public void setShortname(String shortname) {
		this.shortname = shortname;
	}
	
	@Override
	public int compareTo(CommunityViewBean o) {
		return name.compareTo(o.getName());
	}

}
