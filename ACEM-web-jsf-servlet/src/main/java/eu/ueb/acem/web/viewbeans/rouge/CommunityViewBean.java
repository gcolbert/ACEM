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
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import eu.ueb.acem.domain.beans.rouge.Communaute;
import eu.ueb.acem.domain.beans.rouge.Etablissement;
import eu.ueb.acem.web.viewbeans.ViewBean;

/**
 * @author Grégoire Colbert @since 2014-02-25
 * 
 */
public class CommunityViewBean implements ViewBean, Serializable, Comparable<CommunityViewBean> {

	private static final long serialVersionUID = -116654020465612191L;

	private Communaute community;

	private List<InstitutionViewBean> institutionViewBeans;

	private Long id;

	private String name;

	private String shortname;

	public CommunityViewBean() {
		institutionViewBeans = new ArrayList<InstitutionViewBean>();
	}

	public CommunityViewBean(Communaute community) {
		this();
		setCommunity(community);
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

	@PostConstruct
	public void initCommunityViewBean() {
		if (community != null) {
			for (Etablissement institution : community.getInstitutions()) {
				institutionViewBeans.add(new InstitutionViewBean(institution));
			}
		}
	}

	public List<InstitutionViewBean> getInstitutionViewBeans() {
		return institutionViewBeans;
	}

	public void setInstitutions(List<InstitutionViewBean> institutionViewBeans) {
		this.institutionViewBeans = institutionViewBeans;
	}

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

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

	public void setInstitutionViewBeans(List<InstitutionViewBean> institutionViewBeans) {
		this.institutionViewBeans = institutionViewBeans;
	}

	@Override
	public int compareTo(CommunityViewBean o) {
		return name.compareTo(o.getName());
	}

}
