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
package eu.ueb.acem.web.utils.include;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.nio.file.FileSystems;
import java.nio.file.Path;

import org.apache.commons.lang3.RandomUtils;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import eu.ueb.acem.services.util.file.FileUtil;



/**
 * A visual bean for the dialog Upload.
 * 
 * @author rlorthio
 *
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
	 * @param caller
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
	 * Get the file Uploaded
	 * 
	 * @param event
	 */
	public void handleFileUpload(FileUploadEvent event) {
		fileUploaded = (UploadedFile) event.getFile();
		if (caller != null){
			Path temporaryFilePath = FileSystems.getDefault().getPath(FileUtil.getNormalizedFilePath(caller.getDomainService().getTemporaryDirectory() + File.separator
					+ fileUploaded.getFileName() + RandomUtils.nextInt(0,100000)));
			try {
				FileUtil.copyInputStream(temporaryFilePath.toString(), fileUploaded.getInputstream());
			} catch (IOException e) {
				logger.error(e.getMessage());
				temporaryFilePath = null;
			}
			caller.setSelectedFromCommonUploadOneDialog(temporaryFilePath, fileUploaded.getFileName());
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
		logger.debug("CommonUploadOneDialog cancelAction ");
	}

}
