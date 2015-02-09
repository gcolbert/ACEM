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
package eu.ueb.acem.web.controllers;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.inject.Inject;

import org.apache.commons.lang3.Validate;
import org.springframework.context.annotation.Scope;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.stereotype.Controller;

import eu.ueb.acem.domain.beans.gris.Person;
import eu.ueb.acem.services.util.file.FileUtil;
import eu.ueb.acem.web.viewbeans.gris.PersonViewBean;

/**
 * An abstract class inherited by all the beans for them to get:
 * 
 * <ul>
 * <li>the context of the application (sessionController).</li>
 * <li>the domain service (domainService).</li>
 * <li>the application service (applicationService).</li>
 * <li>the i18n service (i18nService).
 * </ul>
 */
@Controller("abstractContextAwareController")
@Scope("session")
public abstract class AbstractContextAwareController extends AbstractDomainAwareBean {

	/*
	 * ****************** PROPERTIES ********************
	 */

	/**
	 * For serialization.
	 */
	private static final long serialVersionUID = -1826458262448752328L;

	/**
	 * The SessionController.
	 */
	@Inject
	private SessionController sessionController;

	/**
	 * The reloadable resource bundle message source (for i18n strings).
	 */
	@Inject
	protected ReloadableResourceBundleMessageSource msgs;

	/*
	 * ****************** INIT ********************
	 */

	/**
	 * Constructor.
	 */
	protected AbstractContextAwareController() {
		super();
	}

	/**
	 * @see eu.ueb.acem.web.controllers.AbstractDomainAwareBean#afterPropertiesSetInternal()
	 */
	@Override
	public void afterPropertiesSetInternal() {
		Validate.notNull(this.sessionController, "property sessionController of class " + this.getClass().getName()
				+ " can not be null");
	}

	/*
	 * ****************** CALLBACK ********************
	 */

	/*
	 * ****************** METHODS ********************
	 */

	/**
	 * @see eu.ueb.acem.web.controllers.AbstractDomainAwareBean#getCurrentUser()
	 */
	@Override
	public Person getCurrentUser() throws Exception {
		return sessionController.getCurrentUser();
	}

	public PersonViewBean getCurrentUserViewBean() {
		return sessionController.getCurrentUserViewBean();
	}
	
	/**
	 * @param sessionController
	 *            the sessionController to set
	 */
	public void setSessionController(final SessionController sessionController) {
		this.sessionController = sessionController;
	}

	/**
	 * @return the sessionController
	 */
	public SessionController getSessionController() {
		return sessionController;
	}

	/**
	 * Moves the image at temporaryFilePath to the images's directory,
	 * and gives it the name "imageFileName".
	 * @param temporaryFilePath
	 * @param imageFileName
	 */
	// TODO : move this to services layer?
	protected void moveUploadedIconToImagesFolder(Path temporaryFilePath, String imageFileName) {
		// We move the file from the temporary folder to the images folder,
		// and give it its original file name
		String destinationFilePath = FileUtil.getNormalizedFilePath(getDomainService().getImagesDirectory()
				+ File.separator + imageFileName);
		if (Files.notExists(Paths.get(destinationFilePath), LinkOption.NOFOLLOW_LINKS)) {
			FileUtil.renameDirectoryOrFile(temporaryFilePath.toString(), destinationFilePath);
		}
	}

	/**
	 * Remove temporary file, if it exists.
	 * @param temporaryFilePath A path to the temporary file to delete.
	 */
	protected void deleteTemporaryFileIfExists(Path temporaryFilePath) {
		if (temporaryFilePath != null) {
			File oldFile = temporaryFilePath.toFile();
			if (oldFile.exists()) {
				oldFile.delete();
			}
		}
	}

}
