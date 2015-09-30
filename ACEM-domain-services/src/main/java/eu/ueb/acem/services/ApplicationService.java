/**
 *     Copyright Université Européenne de Bretagne 2012-2015
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
package eu.ueb.acem.services;

import java.io.Serializable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * A service that can return the application version.
 */
@Service("applicationService")
public class ApplicationService implements InitializingBean, Serializable {

	/**
	 * The serialization id.
	 */
	private static final long serialVersionUID = -5370486400815436620L;

	/**
	 * A logger.
	 */
	private static final Logger logger = LoggerFactory
			.getLogger(ApplicationService.class);

	/**
	 * The version as a string.
	 */
	@Value("${project.version}")
	private String version;

	/**
	 * Bean constructor.
	 */
	public ApplicationService() {
	}

	@Override
	public void afterPropertiesSet() {
		logger.info("Starting version " + getVersion() + "...");
	}

	/**
	 * @return The composed version
	 */
	public String getVersion() {
		return version;
	}

}
