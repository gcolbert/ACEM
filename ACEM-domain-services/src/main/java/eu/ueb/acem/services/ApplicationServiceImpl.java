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

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * A service that can return Application level information.
 * 
 * @author Grégoire Colbert
 * @since 2013-09-20
 */
@Service("applicationService")
public class ApplicationServiceImpl implements ApplicationService {

	/**
	 * The serialization id.
	 */
	private static final long serialVersionUID = -5370486400815436620L;

	/**
	 * A logger.
	 */
	private static final Logger logger = LoggerFactory
			.getLogger(ApplicationServiceImpl.class);

	/**
	 * The version as a string.
	 */
	@Value("${project.version}")
	private String version;

	/**
	 * The path for the tmp directory
	 */
	@Value("${tmp.path}")
	private String tmpPath;

	/**
	 * Bean constructor.
	 */
	public ApplicationServiceImpl() {
	}

	@PostConstruct
	public void init() {
		logger.info("Starting version " + getVersion() + "...");
	}

	/**
	 * @return The composed version
	 */
	@Override
	public String getVersion() {
		return version;
	}

	/**
	 * @see eu.ueb.acem.services.ApplicationService#getTemporaryDirectory()
	 */
	@Override
	public String getTemporaryDirectory() {
		return this.tmpPath;
	}

}
