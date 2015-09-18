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
package eu.ueb.acem.web.viewbeans.bleu;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import eu.ueb.acem.domain.beans.bleu.PedagogicalSession;

/**
 * @author Grégoire Colbert
 * @since 2015-09-08
 * 
 */
public class PedagogicalSessionViewBean extends AbstractPedagogicalUnitViewBean<PedagogicalSession> implements Serializable {

	/**
	 * For serialization.
	 */
	private static final long serialVersionUID = -6214668546964214975L;

	@SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory.getLogger(PedagogicalSessionViewBean.class);

	private List<PedagogicalActivityViewBean> pedagogicalActivityViewBeans;

	public PedagogicalSessionViewBean() {
		pedagogicalActivityViewBeans = new ArrayList<PedagogicalActivityViewBean>();
	}

	public PedagogicalSessionViewBean(PedagogicalSession session) {
		this();
		setSession(session);
	}

	public List<PedagogicalActivityViewBean> getPedagogicalActivityViewBeans() {
		return pedagogicalActivityViewBeans;
	}

	public void setSession(PedagogicalSession domainBean) {
		super.setDomainBean(domainBean);
	}

}
