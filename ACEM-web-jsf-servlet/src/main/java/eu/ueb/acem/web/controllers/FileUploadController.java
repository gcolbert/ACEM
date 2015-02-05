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

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import eu.ueb.acem.web.utils.MessageDisplayer;

/**
 * @author Grégoire Colbert
 * @since 2014-01-22
 * 
 */
@Controller("fileUploadController")
@Scope("session")
public class FileUploadController extends AbstractContextAwareController {

	/**
	 * For serialization.
	 */
	private static final long serialVersionUID = 8331050456345272270L;

	/**
	 * For logging.
	 */
	@SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory.getLogger(FileUploadController.class);

	private DefaultStreamedContent uploadedFile;

	private String uploadedFileName;

	@Value("${tmp.path}")
	private String localPathToUploadFolder;

	public void setLocalPathToUploadFolder(String localPath) {
		if (!localPath.endsWith(File.separator)) {
			localPath += File.separator;
		}
		this.localPathToUploadFolder = localPath;
	}

	public void upload(FileUploadEvent event) {
		MessageDisplayer.info(
				msgs.getMessage("FILEUPLOAD.UPLOAD_SUCCESSFUL.TITLE", null, getCurrentUserLocale()),
				msgs.getMessage("FILEUPLOAD.UPLOAD_SUCCESSFUL.DETAILS", new String[] {event.getFile().getFileName()},
						getCurrentUserLocale()));
		// Do what you want with the file
		try {
			copyFile(event.getFile().getFileName(), event.getFile().getInputstream());
			uploadedFile = new DefaultStreamedContent(new ByteArrayInputStream(Files.readAllBytes(Paths
					.get(localPathToUploadFolder + event.getFile().getFileName()))));
			uploadedFileName = event.getFile().getFileName();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void copyFile(String fileName, InputStream in) {
		try {
			// write the inputStream to a FileOutputStream
			OutputStream out = new FileOutputStream(new File(localPathToUploadFolder + fileName));

			int read = 0;
			byte[] bytes = new byte[1024];

			while ((read = in.read(bytes)) != -1) {
				out.write(bytes, 0, read);
			}

			in.close();
			out.flush();
			out.close();
		}
		catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}

	public DefaultStreamedContent getUploadedFile() {
		return uploadedFile;
	}

	public String getUploadedFileName() {
		return uploadedFileName;
	}

	public void reset() {
		uploadedFile = null;
		uploadedFileName = null;
	}

}
