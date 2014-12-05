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
package eu.ueb.acem.web.viewbeans.jaune;

import java.io.Serializable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import eu.ueb.acem.domain.beans.jaune.UseMode;
import eu.ueb.acem.web.viewbeans.Pickable;

/**
 * @author Grégoire Colbert
 * @since 2014-05-22
 * 
 */
public class UseModeViewBean implements Serializable, Pickable, Comparable<UseModeViewBean> {
	
	private static final long serialVersionUID = 6232506617579076158L;

	@SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory.getLogger(UseModeViewBean.class);

	private UseMode useMode;
	
	private Long id;

	private String name;
	
	private String description;

	public UseModeViewBean() {
	}

	public UseModeViewBean(UseMode useMode) {
		this();
		setUseMode(useMode);
	}

	@Override
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
		useMode.setName(name);
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
		useMode.setDescription(description);
	}
	
	public UseMode getDomainBean() {
		return useMode;
	}
	
	public void setDomainBean(UseMode useMode) {
		setUseMode(useMode);
	}
	
	public UseMode getUseMode() {
		return useMode;
	}

	public void setUseMode(UseMode useMode) {
		this.useMode = useMode;
		setId(useMode.getId());
		setName(useMode.getName());
		setDescription(useMode.getDescription());
	}
	
	@Override
	public int compareTo(UseModeViewBean o) {
		return name.compareTo(o.getName());
	}

}

