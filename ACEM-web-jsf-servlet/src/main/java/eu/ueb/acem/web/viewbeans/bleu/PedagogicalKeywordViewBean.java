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

import eu.ueb.acem.domain.beans.bleu.PedagogicalKeyword;
import eu.ueb.acem.web.viewbeans.AbstractViewBean;

/**
 * @author Grégoire Colbert
 * @since 2015-09-08
 * 
 */
public class PedagogicalKeywordViewBean extends AbstractViewBean implements Serializable {

	/**
	 * For serialization.
	 */
	private static final long serialVersionUID = 8918637885135158883L;

	private PedagogicalKeyword domainBean;
	
	private String name;

	private List<PedagogicalUnitViewBean> pedagogicalUnitViewBeans;

	public PedagogicalKeywordViewBean() {
		pedagogicalUnitViewBeans = new ArrayList<PedagogicalUnitViewBean>(0);
	}

	public PedagogicalKeywordViewBean(PedagogicalKeyword domainBean) {
		setDomainBean(domainBean);
	}

	public PedagogicalKeyword getDomainBean() {
		return domainBean;
	}

	public void setDomainBean(PedagogicalKeyword domainBean) {
		this.domainBean = domainBean;
		setName(domainBean.getName());
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<PedagogicalUnitViewBean> getPedagogicalUnitViewBeans() {
		return pedagogicalUnitViewBeans;
	}

}
