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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import eu.ueb.acem.domain.beans.jaune.PedagogicalAndDocumentaryResource;
import eu.ueb.acem.domain.beans.jaune.Resource;

/**
 * @author Grégoire Colbert
 * @since 2014-03-19
 * 
 */
public class DocumentaryAndPedagogicalResourceViewBean extends AbstractResourceViewBean implements Serializable {

	/**
	 * For serialization.
	 */
	private static final long serialVersionUID = 9043064613804072989L;

	@SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory.getLogger(DocumentaryAndPedagogicalResourceViewBean.class);

	private PedagogicalAndDocumentaryResource documentaryAndPedagogicalResource;

	public DocumentaryAndPedagogicalResourceViewBean() {
		super();
	}

	public DocumentaryAndPedagogicalResourceViewBean(PedagogicalAndDocumentaryResource documentaryAndPedagogicalResource) {
		this();
		setDocumentaryAndPedagogicalResource(documentaryAndPedagogicalResource);
	}

	@Override
	public PedagogicalAndDocumentaryResource getDomainBean() {
		return documentaryAndPedagogicalResource;
	}

	@Override
	public void setDomainBean(Resource resource) {
		setDocumentaryAndPedagogicalResource((PedagogicalAndDocumentaryResource) resource);
	}

	public PedagogicalAndDocumentaryResource getDocumentaryAndPedagogical() {
		return documentaryAndPedagogicalResource;
	}

	public void setDocumentaryAndPedagogicalResource(PedagogicalAndDocumentaryResource documentaryAndPedagogicalResource) {
		this.documentaryAndPedagogicalResource = documentaryAndPedagogicalResource;
		super.setDomainBean(documentaryAndPedagogicalResource);
	}

}
