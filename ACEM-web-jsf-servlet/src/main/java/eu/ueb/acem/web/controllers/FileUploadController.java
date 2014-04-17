package eu.ueb.acem.web.controllers;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

@Controller("fileUploadController")
@Scope("session")
public class FileUploadController extends AbstractContextAwareController {

	private static final long serialVersionUID = 8331050456345272270L;

	private static final Logger logger = LoggerFactory.getLogger(FileUploadController.class);

	private String destination = "D:/tmp/";

	private DefaultStreamedContent uploadedFile;

	private String uploadedFileName;

	public void upload(FileUploadEvent event) {
		logger.info("upload");
		FacesMessage msg = new FacesMessage(getString("FILEUPLOAD.UPLOAD_SUCCESSFUL.TITLE"), getString(
				"FILEUPLOAD.UPLOAD_SUCCESSFUL.DETAILS", event.getFile().getFileName()));
		FacesContext.getCurrentInstance().addMessage(null, msg);
		// Do what you want with the file
		try {
			copyFile(event.getFile().getFileName(), event.getFile().getInputstream());
			uploadedFile = new DefaultStreamedContent(new ByteArrayInputStream(Files.readAllBytes(Paths.get(destination
					+ event.getFile().getFileName()))));
			uploadedFileName = event.getFile().getFileName();
			logger.info("successful");
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void copyFile(String fileName, InputStream in) {
		try {
			// write the inputStream to a FileOutputStream
			OutputStream out = new FileOutputStream(new File(destination + fileName));

			int read = 0;
			byte[] bytes = new byte[1024];

			while ((read = in.read(bytes)) != -1) {
				out.write(bytes, 0, read);
			}

			in.close();
			out.flush();
			out.close();

			System.out.println("New file created!");
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
		logger.info("reset()");
		uploadedFile = null;
		uploadedFileName = null;
	}

}
