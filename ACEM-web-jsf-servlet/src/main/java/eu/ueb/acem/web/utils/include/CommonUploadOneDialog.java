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
package eu.ueb.acem.web.utils.include;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.commons.lang.math.RandomUtils;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import eu.ueb.acem.services.util.file.FileUtil;

/**
 * A visual bean for the dialog Upload.
 * 
 * @author Grégoire Colbert
 */
public class CommonUploadOneDialog implements Serializable {

	/**
	 * For serialize
	 */
	private static final long serialVersionUID = -869577106823997934L;

	/**
	 * A logger.
	 */
	private static final Logger logger = LoggerFactory
			.getLogger(CommonUploadOneDialog.class);

	/**
	 * File uploaded
	 */
	private UploadedFile fileUploaded;

	/**
	 * The Bean to return the value selected (should implement the interface)
	 */
	private CommonUploadOneDialogInterface caller = null;

	/*
	 * ****************** INIT ********************
	 */

	/**
	 * Bean constructor private to force use of the interface.
	 */
	@SuppressWarnings("unused")
	private CommonUploadOneDialog() {
		super();
	}

	/**
	 * Bean constructor.
	 * 
	 * @param caller
	 *            The controller that will control the opening and closing of
	 *            the upload dialog.
	 */
	public CommonUploadOneDialog(CommonUploadOneDialogInterface caller) {
		super();
		this.caller = caller;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return getClass().getSimpleName() + "#" + hashCode();
	}

	/**
	 * Rest the bean
	 */
	public void reset() {
		this.fileUploaded = null;
	}

	/*
	 * ****************** Modal for Upload ********************
	 */

	/**
	 * Accesses the uploaded file on the local storage and sets the caller's
	 * setSelectedFromCommonUploadOneDialog method.
	 * 
	 * @param event
	 *            A Primefaces FileUploadEvent
	 */
	public void handleFileUpload(FileUploadEvent event) {
		fileUploaded = (UploadedFile) event.getFile();
		if (caller != null) {
			Path temporaryFilePath = FileSystems.getDefault().getPath(
					FileUtil.getNormalizedFilePath(caller.getApplicationService().getTemporaryDirectory() + File.separator
							+ fileUploaded.getFileName() + RandomUtils.nextInt(100000)));
			try {
				FileUtil.copyInputStream(temporaryFilePath.toString(), fileUploaded.getInputstream());
			}
			catch (IOException e) {
				logger.error(e.getMessage());
				temporaryFilePath = null;
			}
			caller.setSelectedFromCommonUploadOneDialog(temporaryFilePath);
		}
	}

	/**
	 * Get the File uploaded name
	 * 
	 * @return UploadedFile name
	 */
	public String getFileUploadedName() {
		if (fileUploaded != null) {
			return fileUploaded.getFileName();
		} else {
			return "";
		}
	}

	/**
	 * Cancel Selection of the Model
	 * 
	 */
	public void cancelAction() {
	}

	/*
	 * ***** UTILITIES FOR CONTROLLERS IMPLEMENTING COMMON UPLOAD ONE *****
	 */

	/**
	 * Method used in controllers implementing CommonUploadOneDialogInterface,
	 * to move the image from "temporaryFilePath" to the images's directory, and
	 * to give it the name "imageFileName", when saving the modified object.
	 * 
	 * @param temporaryFilePath
	 *            a path to the temporary file to move, including filename
	 * @param imageFileName
	 *            the filename to give to the file once written in the
	 *            ImagesDirectory
	 */
	public void moveUploadedIconToImagesFolder(Path temporaryFilePath, String imageFileName) {
		// We move the file from the temporary folder to the images folder,
		// and give it its original file name
		String destinationFilePath = FileUtil.getNormalizedFilePath(caller.getImagesService().getImagesDirectory()
				+ File.separator + imageFileName);
		if (Files.notExists(Paths.get(destinationFilePath), LinkOption.NOFOLLOW_LINKS)) {
			FileUtil.renameDirectoryOrFile(temporaryFilePath.toString(), destinationFilePath);
		}
	}

	/**
	 * Method used in controllers implementing CommonUploadOneDialogInterface,
	 * to delete the temporary file after the user closes the create/modify
	 * dialog without saving the modified object.
	 * 
	 * @param temporaryFilePath
	 *            A path (including filename) to the temporary file to delete.
	 */
	public void deleteTemporaryFileIfExists(Path temporaryFilePath) {
		if (temporaryFilePath != null) {
			File oldFile = temporaryFilePath.toFile();
			if (oldFile.exists()) {
				oldFile.delete();
			}
		}
	}

}
