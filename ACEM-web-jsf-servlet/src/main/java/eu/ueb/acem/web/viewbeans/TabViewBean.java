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
package eu.ueb.acem.web.viewbeans;

import java.io.Serializable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * @author Grégoire Colbert
 * @since 2014-03-04
 * 
 */
@Component("tabViewBean")
@Scope("view")
// TODO : choose whether we should keep this for correction of issue https://github.com/gcolbert/ACEM/issues/3
public class TabViewBean implements Serializable {

	private static final long serialVersionUID = 4556222445851594028L;

	private static final Logger logger = LoggerFactory.getLogger(TabViewBean.class);

	Integer activeIndex;

	public TabViewBean() {

	}

	public Integer getActiveIndex() {
		return activeIndex;
	}

	public void setActiveIndex(Integer activeIndex) {
		logger.info("setActiveIndex({})", activeIndex);
		this.activeIndex = activeIndex;
	}

}
