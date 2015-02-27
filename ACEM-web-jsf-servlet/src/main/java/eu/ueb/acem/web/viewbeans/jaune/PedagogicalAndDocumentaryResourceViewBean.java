/**
 *     Copyright Grégoire COLBERT 2015
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

import javax.inject.Named;

import eu.ueb.acem.domain.beans.jaune.PedagogicalAndDocumentaryResource;

/**
 * @author Grégoire Colbert
 * @since 2014-03-19
 * 
 */
@Named
public class PedagogicalAndDocumentaryResourceViewBean extends AbstractResourceViewBean<PedagogicalAndDocumentaryResource> implements Serializable {

	/**
	 * For serialization.
	 */
	private static final long serialVersionUID = 9043064613804072989L;

	public PedagogicalAndDocumentaryResourceViewBean() {
		super();
		setType("RESOURCE_TYPE_PEDAGOGICAL_AND_DOCUMENTARY_RESOURCE");
	}

	public PedagogicalAndDocumentaryResourceViewBean(PedagogicalAndDocumentaryResource documentaryAndPedagogicalResource) {
		this();
		setDomainBean(documentaryAndPedagogicalResource);
	}

}
