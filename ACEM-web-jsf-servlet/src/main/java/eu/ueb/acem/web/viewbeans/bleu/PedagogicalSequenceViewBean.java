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

import eu.ueb.acem.domain.beans.bleu.PedagogicalSequence;

/**
 * @author Grégoire Colbert
 * @since 2015-09-08
 * 
 */
public class PedagogicalSequenceViewBean extends AbstractPedagogicalUnitViewBean<PedagogicalSequence> implements Serializable {

	/**
	 * For serialization.
	 */
	private static final long serialVersionUID = -6528393484591043624L;

	@SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory.getLogger(PedagogicalSequenceViewBean.class);

	private List<PedagogicalSessionViewBean> pedagogicalSessionViewBeans;

	public PedagogicalSequenceViewBean() {
		pedagogicalSessionViewBeans = new ArrayList<PedagogicalSessionViewBean>();
	}

	public PedagogicalSequenceViewBean(PedagogicalSequence sequence) {
		this();
		setSequence(sequence);
	}

	public List<PedagogicalSessionViewBean> getPedagogicalSessionViewBeans() {
		return pedagogicalSessionViewBeans;
	}

	public void setSequence(PedagogicalSequence domainBean) {
		super.setDomainBean(domainBean);
	}

}
